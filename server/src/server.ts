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

// Create a connection for the server, using Node's IPC as a transport.
// Also include all preview / proposed LSP features.
const connection = createConnection(ProposedFeatures.all);

// Create a simple text document manager.
const documents: TextDocuments<TextDocument> = new TextDocuments(TextDocument);

// 
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

function validateDocument(document: TextDocument): void {
  const text = document.getText();

  try {
    const inputStream = CharStreams.fromString(text);
    const lexer = new GrammarLexer(inputStream);
    const tokenStream = new CommonTokenStream(lexer);
    const parser = new GrammarParser(tokenStream);

    const errorCollector = new SyntaxErrorCollector();
    parser.removeErrorListeners();
    parser.addErrorListener(errorCollector);

    parser.program();


    const diagnostics = errorCollector.getDiagnostics();

    connection.sendDiagnostics({ uri: document.uri, diagnostics});

    if (diagnostics.length > 0) {
      console.log(`Found ${diagnostics.length} syntax errors in ${document.uri}`);
    }
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
