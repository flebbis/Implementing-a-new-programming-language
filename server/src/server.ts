import {
  createConnection,
  TextDocuments,
  ProposedFeatures,
  InitializeParams,
  TextDocumentSyncKind,
  InitializeResult,
  Diagnostic,
  DiagnosticSeverity
} from "vscode-languageserver/node";

import { TextDocument } from "vscode-languageserver-textdocument";
import { CharStreams, CommonTokenStream, Recognizer } from "antlr4ts";
import { GrammarLexer } from "./parser/GrammarLexer";
import { GrammarParser } from "./parser/GrammarParser";
import { ANTLRErrorListener, RecognitionException } from "antlr4ts";

import { checkTypes } from "./typechecker/TypeCheckerBridge.js"

// Create a connection for the server, using Node's IPC as a transport.
// Also include all preview / proposed LSP features.
const connection = createConnection(ProposedFeatures.all);

// Create a simple text document manager.
const documents: TextDocuments<TextDocument> = new TextDocuments(TextDocument);

// Syntax error collector 
class SyntaxErrorCollector implements ANTLRErrorListener<any> {
  private diagnostics: Diagnostic[] = []

  syntaxError(
    recognizer: Recognizer<any, any>,
    offendingSymbol: any,
    line: number, 
    charPositionInLine: number,
    msg: string,
    e: RecognitionException | undefined
  ): void {
    this.diagnostics.push({
      severity: DiagnosticSeverity.Error,
      range: {
        start: { line: line - 1, character: charPositionInLine},
        end: { line: line - 1, character: charPositionInLine + (offendingSymbol?.text?.length || 1)}
      },
      message: msg,
      source: "mylang"
    });
  }

  getDiagnostics(): Diagnostic[] {
    return this.diagnostics;
  }
}

// Validate document - checks both syntax and type errors
async function validateDocument(document: TextDocument): Promise<void> {
  const text = document.getText();
  const allDiagnostics: Diagnostic[] = [];

  try {
    // Start with syntax checking
    const inputStream = CharStreams.fromString(text);
    const lexer = new GrammarLexer(inputStream);
    const tokenStream = new CommonTokenStream(lexer);
    const parser = new GrammarParser(tokenStream);

    const errorCollector = new SyntaxErrorCollector();
    parser.removeErrorListeners();
    parser.addErrorListener(errorCollector);

    parser.program();

    // Add syntax errors to diagnostics
    const syntaxDiagnostics = errorCollector.getDiagnostics();
    allDiagnostics.push(...syntaxDiagnostics);

    //connection.sendDiagnostics({ uri: document.uri, diagnostics});

    if (syntaxDiagnostics.length > 0) {
      console.log(`Found ${syntaxDiagnostics.length} syntax errors in ${document.uri}`);
    }

    // Type checking
    // Run type checking if there are no syntax errors
    if (syntaxDiagnostics.length === 0) {
      try {
        console.log(`Running type checker for ${document.uri}`);
        const typeErrors = await checkTypes(text);
        console.log(`Type checker returned ${typeErrors.length} errors`);

        const typeDiagnostics = typeErrors.map((error: any) => ({
          severity: error.severity === 'error' ? DiagnosticSeverity.Error: DiagnosticSeverity.Warning,
          range: {
            start: { line: error.line - 1, character: error.column },
            end: { line: error.endLine - 1, character: error.endColumn }
          },
          message: error.message,
          source: "mylang-type",
          code: "type-error"
        }));

        allDiagnostics.push(...typeDiagnostics);

        if (typeDiagnostics.length > 0) {
          console.log(`Found ${typeDiagnostics.length} type errors in ${document.uri}`);
        }
      } catch (typeError) {
        console.error('Type checker error:', typeError);
        allDiagnostics.push({
          severity: DiagnosticSeverity.Error,
          range: {
            start: { line: 0, character: 0 },
            end: { line: 0, character: 1 }
          },
          message: 'Type checker failed to run. Check server logs.',
          source: "mylang-type"
        });
      }
    }
    
    // Send all diagnostics to the editor
    connection.sendDiagnostics({ uri: document.uri, diagnostics: allDiagnostics });

  } catch (error) {
    console.error('Parser error:', error);

    connection.sendDiagnostics({
      uri: document.uri,
      diagnostics: [{
        severity: DiagnosticSeverity.Error,
        range: {
          start: { line: 0, character: 0 },
          end: { line: 0, character: 1 }
        },
        message: `Fatal parser error: ${error}`,
        source: "mylang"
        
      }]
    });
  }
}

// LSP handlers
connection.onInitialize((params: InitializeParams) => {
  const result: InitializeResult = {
    capabilities: {
      textDocumentSync: TextDocumentSyncKind.Incremental,
    },
  };
  connection.window.showInformationMessage(
    "*mylang* Language server extension initialized successfully!"
  );
  return result;
});

documents.onDidChangeContent((change) => {
  connection.window.showInformationMessage(
    "onDidChangeContent: " + change.document.uri
  );
  validateDocument(change.document);
});

documents.onDidOpen((event) => {
  validateDocument(event.document);
});

documents.onDidClose((event) => {
  connection.sendDiagnostics({ uri: event.document.uri, diagnostics: [] });
});

// Make the text document manager listen on the connection
// for open, change and close text document events
documents.listen(connection);

// Listen on the connection
connection.listen();
