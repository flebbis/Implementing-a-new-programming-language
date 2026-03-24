import {
  createConnection,
  TextDocuments,
  Diagnostic,
  DiagnosticSeverity,
  ProposedFeatures,
  InitializeParams,
  DidChangeConfigurationNotification,
  TextDocumentPositionParams,
  TextDocumentSyncKind,
  InitializeResult,
  HoverParams,
  Hover,
  DocumentDiagnosticReportKind,
  DocumentDiagnosticReport,
  MarkupContent,
  Range,
  MarkupKind,
} from "vscode-languageserver/node";

import { Position, TextDocument } from "vscode-languageserver-textdocument";
import * as vscode from "vscode"

// Create a connection for the server, using Node's IPC as a transport.
// Also include all preview / proposed LSP features.
const connection = createConnection(ProposedFeatures.all);

// Create a simple text document manager.
const documents: TextDocuments<TextDocument> = new TextDocuments(TextDocument);

let hasConfigurationCabability : boolean = false;
let hasWorkspaceFolderCapability : boolean = false;
let hasDiagnosticRelatedInformationCapability  : boolean = false;
let hasHoverCapability : boolean = false;

/* Setup server */
connection.onInitialize((params: InitializeParams) => {
	const capabilities = params.capabilities;

  // probe client capabilites
  hasConfigurationCabability = !!(
    capabilities.workspace && 
    !!capabilities.workspace.configuration
  ),
  hasWorkspaceFolderCapability = !!(
    capabilities.workspace && !!capabilities.workspace.workspaceFolders
  );
  hasDiagnosticRelatedInformationCapability = !!(
    capabilities.textDocument && 
    capabilities.textDocument.publishDiagnostics && 
    capabilities.textDocument.publishDiagnostics.relatedInformation
  );
  hasHoverCapability = !!(
    capabilities.textDocument && 
    !!capabilities.textDocument.hover
  );


  const result: InitializeResult = {
    capabilities: {
      textDocumentSync: TextDocumentSyncKind.Incremental,
      diagnosticProvider: {
        // lmiting diagnostics, only consider currently active file "trusts" other files work as expected
        interFileDependencies: false,
        workspaceDiagnostics: false
      },
      hoverProvider: {
        // workDoneProgress: ?
      }
    },
  };

  // If client suports workspace folder
if (hasWorkspaceFolderCapability) {
    result.capabilities.workspace = {
      workspaceFolders: {
        supported: true
      }
    };
  }

  return result;
});


// global defult settings if client does not support workspace/configuration requests
interface ServerSettings {
  maxNumberOfProblems: number;
  showAssemblyOnSave: boolean;
}

const defaultSettings: ServerSettings = {
  maxNumberOfProblems: 1000,
  showAssemblyOnSave: false}

let globalSettings: ServerSettings = defaultSettings;

let documentSettings: Map<string,Thenable<ServerSettings>> = new Map();

// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------


connection.onInitialized(() => {
  if (hasConfigurationCabability) {
    connection.client.register(DidChangeConfigurationNotification.type, undefined);
  }
  if (hasWorkspaceFolderCapability) {
		connection.workspace.onDidChangeWorkspaceFolders(_event => {
			connection.console.log('Workspace folder change event received.');
		});
	}
  connection.window.showInformationMessage(
    "onInitialized: *mylang* Language server extension initialized successfully!"
  );
});


//update configurations
connection.onDidChangeConfiguration(change => {
  if (hasConfigurationCabability) {
    // Reset all cached document settings
    documentSettings.clear();
  } else {
    globalSettings = <ServerSettings> (
      (change.settings.languageServer || defaultSettings)
    );
  }
  // Validate all open text documents
  documents.all().forEach(validateTextDocument);
});

connection.onDidChangeWatchedFiles(_change => {
  connection.console.log('Recieved file change event')
})

// Discard settings for closed documents
documents.onDidClose(e => {
  documentSettings.delete(e.document.uri);
});

documents.onDidChangeContent(change => {
  connection.console.log(
    "onDidChangeContent: " + change.document.version
  );
});


// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// Diagnostics


// Will send error message on startup if this is not included
connection.languages.diagnostics.on(async (params) => {
    const doc = documents.get(params.textDocument.uri)
    if(doc != undefined){
      return {
        kind: DocumentDiagnosticReportKind.Full,
        items: await validateTextDocument(doc)
      } satisfies DocumentDiagnosticReport;
    } else {
      return {
        kind: DocumentDiagnosticReportKind.Full,
        items: []
      } satisfies DocumentDiagnosticReport;
    }
})

// reads the set extension settings
function getDocumentSettings(recource : string) : Thenable<ServerSettings> {
  if (!hasConfigurationCabability) {
    return Promise.resolve(globalSettings);
  } 
  let result = documentSettings.get(recource);
  if (!result) {
    result = connection.workspace.getConfiguration({
      scopeUri: recource,
      section: 'languageServer'
    });
    documentSettings.set(recource, result)
  }
  return result;
}


async function validateTextDocument(document: TextDocument): Promise<Diagnostic[]> {
  //lookup document settings
  let settings = await getDocumentSettings(document.uri);

  let alternatingCaps = /\b(([a-z][A-Z]){2,}|([A-Z][a-z]){2,})\b/g
  let notRecVarName: RegExp = /\b(if|else|while|do|true|false|return|and|or)(?= *\=)/g
  let doInf = /do *(inf|\(inf\))/g
  let whileTrue = /\bwhile *(true|\(true\))/g
  let diagnostics: Diagnostic[] = [];
  
  diagnostics = diagnosePattern(alternatingCaps, 'alternating upper/lower-case test', DiagnosticSeverity.Warning, settings, document, diagnostics);
  diagnostics = diagnosePattern(notRecVarName, 'not reccomended variable name', DiagnosticSeverity.Warning, settings, document, diagnostics);
  diagnostics = diagnosePattern(doInf, 'DON\'T DO THIS', DiagnosticSeverity.Error, settings, document, diagnostics);
  diagnostics = diagnosePattern(whileTrue, 'This may never terminate', DiagnosticSeverity.Warning, settings, document, diagnostics);
  
  // don't send computed diagnostics, that is handeled by the diagnostics handler
  // connection.sendDiagnostics({uri:document.uri, diagnostics})
  return diagnostics
}


function diagnosePattern(pattern: RegExp, message: string, severity: DiagnosticSeverity, 
      settings: ServerSettings, document: TextDocument, diagnostics: Diagnostic[] ): Diagnostic[] {
  //Validator matching
  let text = document.getText();
  let d = Array.from(diagnostics) // mutate-by-copy
  let m :RegExpExecArray | null
  let problems = 0;
  while ((m = pattern.exec(text)) && problems < settings.maxNumberOfProblems) {
    problems++;
    let diagnostic: Diagnostic = {
      severity: severity,
      range: {
        start: document.positionAt(m.index),
        end: document.positionAt(m.index + m[0].length)
      },
      message: `${m[0]} is detected!`,
      source: 'mylang-diagnostics'
    };
    if (hasDiagnosticRelatedInformationCapability) {
      diagnostic.relatedInformation = [
        {
          location: {
            uri: document.uri,
            range: Object.assign({}, diagnostic.range)
          },
          message: message
        }
      ];
    }
    d.push(diagnostic);
  }
  return d
}


// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// Hover
function inc(pos: Position): Position{
  return {line: pos.line, character: pos.character+1}
}

connection.onHover((params: HoverParams, token, progrss, result) => {
  const doc = documents.get(params.textDocument.uri)
  
  let pos = params.position;
  if(doc != undefined){
    // This didn't end up working, 

    // let document = vscode.workspace.textDocuments.find((tdoc)=>{
    //   if(tdoc.uri.toString() == doc.uri){
    //     return tdoc
    //   }
    // })

    // const r = document?.getWordRangeAtPosition(new vscode.Position(pos.line,pos.character))

    // new idea: send request to client for word/range
    const r: Range= {start: pos, end: inc(pos)}
    const word = doc.getText(r);
    let content: MarkupContent = {kind: MarkupKind.Markdown, 
      value: [
        `# ${word}`,
        'Some text',
        '```',
        'hover information should appear here',
        '```'
      ].join('\n')}
    let res : Hover = {contents: content, range: r}
    return res
  }
  else{
    return null;
  }
})


// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// Signatures


/* connection.onSignatureHelp((handler)=> {
  connection.console.log("signature help request detected");
  
  handler.context
}) */

// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// onSave -- not needed for show-assembly but sendRequest might be usefull for other things

// documents.onDidSave(async (saved) => {
//   let settings = await getDocumentSettings(saved.document.uri)
//   if (settings.showAssemblyOnSave) {
//     connection.sendRequest("showAssembly", "-O0")
//   }
// })


// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------

// Make the text document manager listen on the connection
// for open, change and close text document events
documents.listen(connection);

// Listen on the connection
connection.listen();
