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
} from "vscode-languageserver/node";

import { TextDocument } from "vscode-languageserver-textdocument";

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


// Discard settings for closed documents
documents.onDidClose(e => {
  documentSettings.delete(e.document.uri);
});

// Valudate document the first time it's opened and each time it is content is changed 
documents.onDidChangeContent(change => {
  validateTextDocument(change.document)
  connection.console.log(
    "onDidChangeContent: " + change.document.version
  );
});


// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------

// global defult settings if client does not support workspace/configuration requests
interface ServerSettings {
  maxNumberOfProblems: number;
}

const defaultSettings: ServerSettings = {maxNumberOfProblems: 1000}
let globalSettings: ServerSettings = defaultSettings;

let documentSettings: Map<string,Thenable<ServerSettings>> = new Map();


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


async function validateTextDocument(document: TextDocument): Promise<void> {
  //lookup document settings
  let settings = await getDocumentSettings(document.uri);

  //Validator matching
  // TODO
   // testing: match any word of four or more characters that alternate upper and lowercase

  let text = document.getText();
  let pattern = /\b([a-z][A-Z]){2,}\b/g
  let m: RegExpExecArray | null;

  let problems = 0;
  let diagnostics: Diagnostic[] = [];
  while ((m = pattern.exec(text)) && problems < settings.maxNumberOfProblems) {
    problems++;
    let diagnostic : Diagnostic = {
      severity: DiagnosticSeverity.Warning,
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
          message: 'alternating upper/lower-case test'
        }
      ];
    }
    diagnostics.push(diagnostic);
  }
  // send computed diagnostics
  connection.sendDiagnostics({uri:document.uri, diagnostics})
}

connection.onDidChangeWatchedFiles(_change => {
  connection.console.log('Recieved file change event')
})

// Make the text document manager listen on the connection
// for open, change and close text document events
documents.listen(connection);

// Listen on the connection
connection.listen();
