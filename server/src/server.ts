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
  TextEdit
} from "vscode-languageserver/node";

import { TextDocument } from "vscode-languageserver-textdocument";

import {spawn } from 'child_process';
import * as path from 'path';
import { URI } from "vscode-uri";

// Create a connection for the server, using Node's IPC as a transport.
// Also include all preview / proposed LSP features.
const connection = createConnection(ProposedFeatures.all);

// Create a simple text document manager.
const documents: TextDocuments<TextDocument> = new TextDocuments(TextDocument);

let hasConfigurationCabability : boolean = false;
let hasWorkspaceFolderCapability : boolean = false;
let hasDiagnosticRelatedInformationCapability  : boolean = false;
let hasHoverCapability: boolean = false;

type InferenceSuggestion = {
  name: string;
  inferredType: string;
  line: number;
  column: number;
  endLine: number;
  endColumn: number;
  replacement: string;
};

const inferenceSuggestionMap = new Map<string, InferenceSuggestion[]>();

console.error("SERVER STARTED")
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
      },
      signatureHelpProvider: {

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

// Map to store which version was changed latest
const latestDocumentVersions: Map<string, number> = new Map();

documents.onDidChangeContent(change => {
  

  const uri = change.document.uri;
  const text = change.document.getText();
  const version = change.document.version;
  latestDocumentVersions.set(uri, version); // Set document version on change, used to discard outdated analysis results
  console.error("TEXXXT " + change.document.getText())

  // Type check & inference phase
  inferenceAnalysis(uri, text, version);
});



async function inferenceAnalysis(uri : string, text : string, version: number) {
    try {
      const result = await runJavaAnalysis(text);
      
      // Inference suggestions are returned as part of the analysis result, extract and store them in a map for later retrieval when applying edits
      console.error("RESULT " + JSON.stringify(result)) // Debug the JSON result from java
      const suggestions: InferenceSuggestion[] = result ?? [];

      inferenceSuggestionMap.set(uri, suggestions); // tror inte detta behövs längre men sparar för nu

      // Before applying edits, check if the document version has changed
      // Prevent old analysis results from being applied to a newer document version
      const latestVersion = latestDocumentVersions.get(uri);
      if (latestVersion !== version) {
        connection.console.warn(`Outdated analysis result for ${uri} (version ${version}), latest version is ${latestVersion}`);
        return; // Discard outdated result
      }

      // Apply edit in document for each inference suggestion
      for (const s of suggestions) {
        await connection.workspace.applyEdit({
          changes: {
            [uri]: [
              TextEdit.insert(
                {
                  // For some reason detta va rätt place
                  line: s.line - 1,
                  character: s.column, 
                },
                `${s.inferredType} `
              )
            ]
          }
        });
      } 
      inferenceSuggestionMap.delete(uri); // Clear suggestions after applying edits
  }
    catch (error) {
      connection.console.error("Error running Java analysis: " + error);
  }
  
  
}

// Run the Java analysis as a child process, return a promise that resolves with the parsed JSON result from Java
function runJavaAnalysis(text: string): Promise<any> {
  return new Promise((resolve, reject) => {
    // Path to the compiled JAR file
    // Build with: mvn package (creates target/LLVMINI-1.0-SNAPSHOT.jar)
    const jarPath = path.join(__dirname, '../../target/LLVMINI-1.0-SNAPSHOT.jar');
    
    const java = spawn("java", ["-jar", jarPath, text]);

    // Capture stdout and stderr from the Java process
    let stdout = "";
    let stderr = "";

    java.stdout.on("data", (data) => {
      stdout += data.toString();
    });

    // Capture stderr for error reporting (from java code)
    java.stderr.on("data", (data) => {
      stderr += data.toString();
      connection.console.error("Java stderr: " + data.toString());
    });

    // Handle process exit
    java.on("close", (code) => {
      if (code !== 0) { // code 0 is success code
        connection.console.error("Java process failed with code " + code + ": " + stderr);
        reject(new Error(stderr || `Java process exited with code ${code}`));
        return;
      }

      try {
        const result = JSON.parse(stdout); // Parse JSON result from Java
        resolve(result);
      } catch (e) {
        connection.console.error("Failed to parse JSON from Java: " + stdout);
        reject(new Error("Failed to parse JSON from Java: " + stdout));
      }
    });
  });
}


// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// Diagnostics

// global defult settings if client does not support workspace/configuration requests
interface ServerSettings {
  maxNumberOfProblems: number;
}

const defaultSettings: ServerSettings = {maxNumberOfProblems: 1000}
let globalSettings: ServerSettings = defaultSettings;

let documentSettings: Map<string,Thenable<ServerSettings>> = new Map();

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


connection.onHover((params: HoverParams, token, progrss, result) => {
  const uri = params.textDocument.uri
  const posC = params.position.character
  const posL = params.position.line
  let res : Hover = {contents:{language:"markdown",value:"###Detecting hover \n@"+uri+"\n:"+posC+"."+posL}}
  return res
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

// Make the text document manager listen on the connection
// for open, change and close text document events
documents.listen(connection);

// Listen on the connection
connection.listen();


