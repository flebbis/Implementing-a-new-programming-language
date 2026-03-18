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

// Create a connection for the server, using Node's IPC as a transport.
// Also include all preview / proposed LSP features.
const connection = createConnection(ProposedFeatures.all);

// Create a simple text document manager.
const documents: TextDocuments<TextDocument> = new TextDocuments(TextDocument);

connection.onInitialize((params: InitializeParams) => {
  const result: InitializeResult = {
    capabilities: {
      textDocumentSync: TextDocumentSyncKind.Incremental,
      diagnosticProvider: {
        interFileDependencies: false,
        workspaceDiagnostics: false
      }
    },
  };
  connection.window.showInformationMessage(
    "*mylang* Language server extension initialized successfully!"
  );
  return result;
});

interface ParseState {
  inString: boolean;
  stringType: '"' | "'" | null;
  stringStartLine: number;
  stringStartChar: number;
  inBlockComment: boolean;
  commentStartLine: number;
  commentStartChar: number;
  braceStack: { char: string; line: number; charPos: number }[];
  parenStack: { line: number; charPos: number }[];
  bracketStack: { line: number; charPos: number }[];
}

function validateDocument(document: TextDocument): void {
  const text = document.getText();
  const diagnostics: Diagnostic[] = [];

  const lines = text.split('\n');

  const state: ParseState = {
    inString: false,
    stringType: null,
    stringStartLine: 0,
    stringStartChar: 0,
    inBlockComment: false,
    commentStartLine: 0,
    commentStartChar: 0,
    braceStack: [],
    parenStack: [],
    bracketStack: []
  };
  
  for (let lineNum = 0; lineNum < lines.length; lineNum++) {
    const line = lines[lineNum];

    let col = 0;
    while (col < line.length) {
      let char = line[col];

      let nextChar;
      if (col + 1 < line.length) {
        nextChar = line[col + 1];
      }
      else {
        nextChar = null;
      }
      
      let prevChar;
      if (col > 0) {
        prevChar = line[col - 1];
      }
      else {
        prevChar = null;
      }

      if ((char === '"' || char === "'") && !state.inBlockComment) {
        if (prevChar === '\\') {
          col++;
          continue;
        }

        if (!state.inString) {
          state.inString = true;
          state.stringType = char;
          state.stringStartLine = lineNum;
          state.stringStartChar = col;
        }
        else if (state.stringType === char) {
          state.inString = false;
          state.stringType = null;
        }
        col++;
        continue;
      }

      if (char === '/' && nextChar === '/' && !state.inString && !state.inBlockComment) {
        break;
      }

      if (char === '/' && nextChar === '*' && !state.inString && !state.inBlockComment) {
        state.inBlockComment = true;
        state.commentStartLine = lineNum;
        state.commentStartChar = col;
        col += 2;
        continue;
      }
      
      if (char === '*' && nextChar === '/' && state.inBlockComment) {
        state.inBlockComment = false;
        col += 2;
        continue;
      }

      if (state.inString || state.inBlockComment) {
        col++;
        continue;
      }

      if (char === '{') {
        state.braceStack.push({ char, line: lineNum, charPos: col});
      } else if (char === '}') {
        if (state.braceStack.length == 0) {
          diagnostics.push({
            severity: DiagnosticSeverity.Error,
            range: {
              start: {line: lineNum, character: col},
              end: {line: lineNum, character: col + 1}
            },
            message: "Unexpected '}' with no matching '{'",
            source: "mylang"
          });
        } else {
          state.braceStack.pop();
        }
      }

      //
      if (char === '(') {
        state.parenStack.push({ line: lineNum, charPos: col });
      } else if (char === ')') {
        if (state.parenStack.length === 0) {
          diagnostics.push({
            severity: DiagnosticSeverity.Error,
            range: {
              start: { line: lineNum, character: col },
              end: { line: lineNum, character: col + 1 }
            },
            message: "Unexpected ')' with no matching '('",
            source: "mylang"
          });
        } else {
          state.parenStack.pop();
        }
      }

      //
      if (char === '[') {
        state.bracketStack.push({ line: lineNum, charPos: col })
      }
      else if (char === ']') {
        if (state.bracketStack.length === 0) {
          diagnostics.push({
            severity: DiagnosticSeverity.Error,
            range: {
              start: {line: lineNum, character: col},
              end: {line: lineNum, character: col + 1}
            },
            message: "Unexpected ']' with no matching '['",
            source: "mylang"
          });
        }
        else {
          state.bracketStack.pop();
        }
      }
      

      //
    
      const validChars = /[a-zA-Z0-9_+\-*/=<>!&|(){}[\] \t\r\n;:.,'"@]/;
      if (!validChars.test(char)) {
        diagnostics.push({
          severity: DiagnosticSeverity.Error,
          range: {
            start: { line: lineNum, character: col },
            end:  { line: lineNum, character: col + 1 }
          },
          message: `Invalid character '${char}'`,
          source: "mylang"
        });
      }
      col++;
    }

    //
    const trimmed = line.trim();

    if (trimmed.startsWith('func')) {
      const afterFunc = trimmed.substring(5).trim();
      if (!afterFunc.match(/^[a-zA-Z_][a-zA-Z0-9_]*\s*\(/)) {
        diagnostics.push({
          severity: DiagnosticSeverity.Error,
          range: { 
            start: {line: lineNum, character: line.indexOf('func')},
            end: {line: lineNum, character: line.indexOf('func') + 4}
          },
          message: "Invalid function declaration. Expected: func name(...)",
          source: "mylang"
        });
      }
    }
    if (trimmed.startsWith('return') && !text.includes('func')) {
      diagnostics.push({
        severity: DiagnosticSeverity.Error,
        range: {
          start: { line: lineNum, character: line.indexOf('return') },
          end: { line: lineNum, character: line.indexOf('return') + 6 }
        },
        message: "'return' outside function",
        source: "mylang"
      });
    }

    if (trimmed.includes('=') && !trimmed.includes('==') && !trimmed.includes('!=') && 
        !trimmed.includes('<=') && !trimmed.includes('>=')) {
      const beforeEq = trimmed.substring(0, trimmed.indexOf('=')).trim();
      if (!beforeEq.match(/^[a-zA-Z_][a-zA-Z0-9_]*(\[[^\]]+\])?$/)) {
        diagnostics.push({
          severity: DiagnosticSeverity.Error,
          range: {
            start: { line: lineNum, character: line.indexOf('=') },
            end: { line: lineNum, character: line.indexOf('=') + 1 }
          },
          message: "Invalid left-hand side in assignment",
          source: "mylang"
        });
      }
    }

    if (trimmed.match(/[+\-*/=<>!&|]$/) && !trimmed.endsWith('==') 
      && !trimmed.endsWith('!=') && !trimmed.endsWith('<=') && !trimmed.endsWith('>=')
    && !trimmed.endsWith('++') && !trimmed.endsWith('--')) {
      diagnostics.push({
        severity: DiagnosticSeverity.Error,
        range: {
          start: { line: lineNum, character: line.length - 1 },
          end: { line: lineNum, character: line.length }
        },
        message: "Expression cannot end with operator",
        source: "mylang"
      });
    } 
  }
  if (state.inString) {
    diagnostics.push({
      severity: DiagnosticSeverity.Error,
      range: {
        start: { line: state.stringStartLine, character: state.stringStartChar },
        end: { line: state.stringStartLine, character: state.stringStartChar + 1 }
      },
      message: `Unclosed ${state.stringType} string literal`,
      source: "mylang"
    });
  }

  if (state.inBlockComment) {
    diagnostics.push({
      severity: DiagnosticSeverity.Error,
      range: {
        start: { line: state.commentStartLine, character: state.commentStartChar },
        end: { line: state.commentStartLine, character: state.commentStartChar + 2}
      },
      message: "Unclosed block comment",
      source: "mylang"
    });
  }

  while (state.braceStack.length > 0) {
    const unclosed = state.braceStack.pop()!;
    diagnostics.push({
      severity: DiagnosticSeverity.Error,
      range: {
        start: { line: unclosed.line, character: unclosed.charPos },
        end: { line: unclosed.line, character: unclosed.charPos + 1 }
      },
      message: "Unclosed '{",
      source: "mylang"
    });
  }

  while (state.parenStack.length > 0) {
    const unclosed = state.parenStack.pop()!;
    diagnostics.push({
      severity: DiagnosticSeverity.Error,
      range: {
        start: { line: unclosed.line, character: unclosed.charPos },
        end: { line: unclosed.line, character: unclosed.charPos + 1 }
      },
      message: `Unclosed '('`,
      source: "mylang"
    });
  }

  while (state.bracketStack.length > 0) {
    const unclosed = state.bracketStack.pop()!;
    diagnostics.push({
      severity: DiagnosticSeverity.Error,
      range: {
        start: { line: unclosed.line, character: unclosed.charPos },
        end: { line: unclosed.line, character: unclosed.charPos + 1 }
      },
      message: `Unclosed '['`,
      source: "mylang"
    });
  }
  connection.sendDiagnostics({ uri: document.uri, diagnostics });

  if (diagnostics.length > 0) {
    connection.window.showInformationMessage(
      `Found ${diagnostics.length} error(s) in ${document.uri.split('/').pop()}`
    );
  }
}
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
