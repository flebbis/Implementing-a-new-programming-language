import {
  createConnection,
  TextDocuments,
  Diagnostic,
  DiagnosticSeverity,
  ProposedFeatures,
  InitializeParams,
  DidChangeConfigurationNotification,
  TextDocumentSyncKind,
  InitializeResult,
  HoverParams,
  Hover,
  DocumentDiagnosticReportKind,
  DocumentDiagnosticReport,
  MarkupContent,
  Range,
  MarkupKind,
  TextEdit,
  Position
} from "vscode-languageserver/node";

import { CharStreams, CommonTokenStream, Recognizer } from "antlr4ts";
import { GrammarLexer } from "./parser/GrammarLexer";
import { GrammarParser } from "./parser/GrammarParser";
import { ANTLRErrorListener, RecognitionException } from "antlr4ts";
import { TextDocument } from "vscode-languageserver-textdocument";
import { URI } from "vscode-uri";
import { checkTypes } from "./typechecker/TypeCheckerBridge.js"

import { spawn } from 'child_process';
import * as path from 'path';

// Create a connection for the server, using Node's IPC as a transport.
// Also include all preview / proposed LSP features.
const connection = createConnection(ProposedFeatures.all);

// Create a simple text document manager.
const documents: TextDocuments<TextDocument> = new TextDocuments(TextDocument);

let hasConfigurationCabability: boolean = false;
let hasWorkspaceFolderCapability: boolean = false;
let hasDiagnosticRelatedInformationCapability: boolean = false;
let hasHoverCapability: boolean = false;
let hasWillSaveWaitCapabilities: boolean = false;

/* Setup server */
connection.onInitialize((params: InitializeParams) => {
  const capabilities = params.capabilities;

  // probe client capabilites
  hasConfigurationCabability = !!(
    capabilities.workspace &&
    !!capabilities.workspace.configuration
  ),
    hasWorkspaceFolderCapability = !!(
      capabilities.workspace &&
      !!capabilities.workspace.workspaceFolders
    );
  hasDiagnosticRelatedInformationCapability = !!(
    capabilities.textDocument &&
    capabilities.textDocument.publishDiagnostics &&
    !!capabilities.textDocument.publishDiagnostics.relatedInformation
  );
  hasHoverCapability = !!(
    capabilities.textDocument &&
    !!capabilities.textDocument.hover
  );
  hasWillSaveWaitCapabilities = !!(
    capabilities.textDocument &&
    capabilities.textDocument.synchronization &&
    !!capabilities.textDocument.synchronization.willSaveWaitUntil
  );


  const result: InitializeResult = {
    capabilities: {
      textDocumentSync: {
        change: TextDocumentSyncKind.Incremental,
        openClose: true, // Apparently this is needed if the {}-options are used ._.
        willSaveWaitUntil: true,
      },
      diagnosticProvider: {
        // lmiting diagnostics, only consider currently active file "trusts" other files work as expected
        interFileDependencies: false,
        workspaceDiagnostics: false
      },
      hoverProvider: true,
      // signatureHelpProvider: {},
      inlayHintProvider: true
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
// settings stuff
enum TypeInferenceSetting {
  Nill = "nill",
  OnSave = "onSave",
  InsertOnChange = "insertOnChange",
}

// global defult settings if client does not support workspace/configuration requests
interface ServerSettings {
  maxNumberOfProblems: number;
  insertionIntensity: TypeInferenceSetting;
  inlineTypeHint: boolean;
}

// default settings if client does not support 
const defaultSettings: ServerSettings = {
  maxNumberOfProblems: 1000,
  insertionIntensity: TypeInferenceSetting.Nill,
  inlineTypeHint: false,
}

let globalSettings: ServerSettings = defaultSettings;

let documentSettings: Map<string, Thenable<ServerSettings>> = new Map();


// reads the server-side extension settings
function getDocumentSettings(recource: string): Thenable<ServerSettings> {
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
    "onInitialized: *fika* Language server extension initialized successfully!"
  );
});


//update configurations
connection.onDidChangeConfiguration(change => {
  if (hasConfigurationCabability) {
    // Reset all cached document settings
    documentSettings.clear();
  } else {
    globalSettings = <ServerSettings>(
      (change.settings.languageServer || defaultSettings)
    );
  }
  // Validate all open text documents
  documents.all().forEach(validateTextDocument);
});

connection.onDidChangeWatchedFiles(_change => {
  connection.console.log('Recieved file change event')
})


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
  if (doc != undefined) {
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
  settings: ServerSettings, document: TextDocument, diagnostics: Diagnostic[]): Diagnostic[] {
  //Validator matching
  let text = document.getText();
  let d = Array.from(diagnostics)
  let m: RegExpExecArray | null
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
      source: 'fika-diagnostics'
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
const wordChar: RegExp = /[A-z0-9\_]/
const excluded: RegExp = /[0-9]+(.[0-9]+)?|\b(true|false|do|while|if|else|return|func|int|bool|string|double)\b/
const opChar: RegExp = /\-|\<|\>|\=|\!|\+|\*|\%/

function positionString(pos: Position): string {
  return `{${pos.line}, ${pos.character}}`
}

function inc(pos: Position): Position {
  return { line: pos.line, character: pos.character + 1 }
}

function dec(pos: Position): Position {
  return { line: pos.line, character: pos.character - 1 }
}

function lineFromTo(line: number, from: number, to: number): Range {
  return {
    start: { line, character: from },
    end: { line, character: to }
  }
}

/**
 * get entire line (up to ``endAt`` characters) 
 */
function lineRange(line: number, endAt: number): Range {
  return lineFromTo(line, 0, endAt)
}

connection.onHover((params: HoverParams) => {
  // connection.console.log("onHover:")
  const doc = documents.get(params.textDocument.uri)
  let res: Hover | null = null
  let pos = params.position;
  if (doc != undefined) {
    if (!inComment(pos, doc) //don't match inside comments
      && !inString(pos, doc) // don't match inside strings
      && wordChar.test(doc.getText(charRange(pos)))) { //only match if word
      // connection.console.log("onHover: WORD DETECTED")
      const { word, range } = findWordAt(pos, doc)
      // connection.console.log("onHover: " + word)
      let m: RegExpMatchArray | null = doc.getText().match("\\b" + word + "\\b");
      // firstDefInScope(doc, word, range) //find first occurences 
      let description = ""
      // connection.console.log("onHover: length = " + m?.length)
      // connection.console.log("onHover: index = " + m?.index)
      if (!excluded.test(word) //exclude numerics, litterals and types
        && m && (m.index || m.index == 0)) { //ensures there is only one match (always true), 0 = false :/
        const mPos = doc.positionAt(m.index);
        // connection.console.log(positionString(mPos))
        let description = firstDefInScope(doc, word, mPos, pos); //remove trailing and leading whitespace 
        let contents: MarkupContent = {
          kind: MarkupKind.Markdown,
          value: [
            `### ${word}`,
            '```',
            `${description}`,
            '```'
          ].join('\n')
        }
        res = { contents, range }
      }
    }
  }
  return res
})

function firstDefInScope(doc: TextDocument, word: string, mPos: Position, hoverPos: Position): string {
  const start = shiftChar(mPos, word.length)
  // connection.console.log("finding scope: " + positionString(mPos) + " - " + positionString(hoverPos))
  if (inSameScope(doc, start, hoverPos)) {
    // connection.console.log("Same scope")
    // also handles function
    let r: Range = lineRange(mPos.line, 150);
    return doc.getText(r).trim(); //remove trailing and leading whitespace ;
  } else {
    // connection.console.log("Not same scope")
    // not in same scope

    // connection.console.log("" + positionString(start) + " | " + positionString(hoverPos))
    // connection.console.log("new search text:")
    // connection.console.log(doc.getText({ start, end: hoverPos }))
    let m: RegExpMatchArray | null = doc.getText({ start, end: hoverPos }).match("\\b" + word + "\\b");
    // connection.console.log("" + m)

    if (m && m.index) {
      // Exists another occurence in lower scope
      // connection.console.log("Recursion call")
      // connection.console.log("found position: " + positionString(doc.positionAt(doc.offsetAt(start) + m.index)))
      return firstDefInScope(doc, word, doc.positionAt(doc.offsetAt(start) + m.index), hoverPos)
    } else {
      // Only hovered word in scope
      return doc.getText(lineRange(hoverPos.line, 999)).trim();
    }
  }
}

function shiftChar(pos: Position, by: number): Position {
  return { line: pos.line, character: pos.character + by }
}

function shiftLine(pos: Position, by: number): Position {
  return { line: pos.line + by, character: pos.character }
}
/* ignore functions for now */
function inSameScope(doc: TextDocument, start: Position, end: Position): boolean {
  // Same scope if number of `{` equal `}` or have one more `{` and in function definition
  const textBetween = doc.getText({ start, end })
  // connection.console.log("Text: " + textBetween)
  const nrOpen = textBetween.match(/\{/g)?.length || 0
  const nrClose = textBetween.match(/\}/g)?.length || 0
  // connection.console.log("#{ = " + nrOpen)
  // connection.console.log("#} = " + nrClose)
  const diff = nrOpen - nrClose
  // connection.console.log("diff = " + diff)
  if (diff >= 0) {
    return true
  }
  return false
  // return (diff == 1 && /\bfunc\b/.test(doc.getText(lineFromTo(start.line, 0, start.character))))
}


function charRange(pos: Position): Range {
  return { start: pos, end: inc(pos) }
}


function findWordAt(pos: Position, doc: TextDocument): { word: string; range: Range; } {
  const range = {
    start: lim(wordChar, pos, dec, doc),
    end: inc(lim(wordChar, pos, inc, doc))
  }
  return { word: doc.getText(range), range };
}

function lim(reg: RegExp, start: Position, step: (p: Position) => Position, doc: TextDocument): Position {
  let pos: Position = start
  let next: Position = step(start)
  while (reg.test(doc.getText(charRange(next)))) {
    pos = next
    next = step(next)
  }
  return pos
}

function inComment(pos: Position, doc: TextDocument): boolean {
  // connection.console.log("onHover: ? IN COMMENT ?")
  return inLineComment(pos, doc) || inBlockComment(pos, doc)
}

const lineComment: RegExp = /.*\/\/.*/
function inLineComment(pos: Position, doc: TextDocument): boolean {
  // connection.console.log("onHover: ? lINE COMMENT ?")
  const lineRangeBefore: Range = lineRange(pos.line, pos.character)
  const lineText: string = doc.getText(lineRangeBefore)
  return lineComment.test(lineText)
}

const unclosedOpeningBlockComment: RegExp = /\/\*(?!.*\*\/)/;
const unopenedClosingBlockComment: RegExp = /(?<!\/\*.*)\*\//;
const openingBlockComment: RegExp = /\/\*/;
const closingBlockComment: RegExp = /\*\//;

function inBlockComment(pos: Position, doc: TextDocument): boolean {
  // check if /* before
  //  /\/\*(?!.*\*\/)/
  // or */ after point 
  //  /(?<!\/\*.*)\*\//
  // connection.console.log("onHover: ? BLOCK COMMENT ?")
  let res: boolean
  const lineNr = pos.line
  // connection.console.log("onHover: LINE_start = " + lineNr)

  const lineBefore = doc.getText(lineFromTo(lineNr, 0, pos.character))
  const lineAfter = doc.getText(lineFromTo(lineNr, pos.character, 999))
  if (unclosedOpeningBlockComment.test(lineBefore)
    // there is an unclosed block-comment opening on the same line before hover position
    || unopenedClosingBlockComment.test(lineAfter)) {
    // there is an unopened block-comment closing on the same line after  hover position
    res = true
  } else if (closingBlockComment.test(lineBefore)
    // there is a  closing block-comment on same line before hover position
    || openingBlockComment.test(lineAfter)
    // there is an opening block-comment on same line after  hover position
    || lineNr == 0) {
    // nothin on same line and on first line of document
    res = false
  } else if (lineNr < doc.lineCount / 2) { //check if unclosed block-comment opening above
    // step up, /\/\*(?!.*\*\/)/ => true, /\*\// => false
    res = findBlockLim(lineNr - 1, doc, (n => { return n - 1 }), 0, unclosedOpeningBlockComment, closingBlockComment)
  } else { // check if unopened block-comment closes below
    // step down, /(?<!\/\*.*)\*\// => true, /\/\*/ => false
    res = findBlockLim(lineNr + 1, doc, (n => { return n + 1 }), doc.lineCount, unopenedClosingBlockComment, openingBlockComment)
  }
  return res
}

function findBlockLim(lineNr: number, doc: TextDocument, step: (p: number) => number, end: number, accept: RegExp, reject: RegExp): boolean {
  // connection.console.log("onHover: LINE = " + lineNr)
  let res: boolean
  const line = doc.getText(lineRange(lineNr, 999))
  if (accept.test(line)) {
    res = true
  } else if (reject.test(line) //rejection criteria met
    || lineNr - end == 0) { //reached end (or start) of file without accepting
    res = false
  } else {
    res = findBlockLim(step(lineNr), doc, step, end, accept, reject)
  }
  return res
}

function inString(pos: Position, doc: TextDocument): boolean {
  // connection.console.log("onHover: ? IN STRING ?")
  // idea: count number of ``"`` characters to left or right of hover position,
  // odd -> true
  // even -> false
  const left = lineFromTo(pos.line, 0, pos.character)
  const leftLine = doc.getText(left)
  // connection.console.log("onHover: LINE = " + leftLine)
  const m = leftLine.match(/\"/g)
  // connection.console.log("onHover: matches = " + m?.toString())
  const count = m?.length || 0
  // connection.console.log("onHover: stringlims = " + count)
  let res = (count % 2) == 1
  // connection.console.log("onHover: " + res)
  return (count % 2) == 1
}
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// onSave -- not needed for show-assembly but sendRequest might be usefull for other things

// documents.onDidSave(async (saved) => {
//     connection.sendRequest(mehtod, arg)
// })


// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// Type inference

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



// Map to store which version was changed latest
const latestDocumentVersions: Map<string, number> = new Map();

function isLatest(uri: string, version: number): boolean {
  const latestVersion = latestDocumentVersions.get(uri);
  if (latestVersion !== version) {
    // connection.console.warn(`Outdated analysis result for ${uri} (version ${version}), latest version is ${latestVersion}`);
    return false; // Discard outdated result
  }
  return true;
}

async function inferenceAnalysis(uri: string, document: TextDocument, version: number) {
  if (isLatest(uri, version)) {
    try {
      // connection.console.log("run analysis...")
      let result = await runJavaAnalysis(uri, document);
      // Inference suggestions are returned as part of the analysis result, extract and store them in a map for later retrieval when applying edits
      const suggestions: InferenceSuggestion[] = result.inferenceSuggestions ?? [];

      if (isLatest(uri, version)) {
        inferenceSuggestionMap.set(uri, suggestions);
      }
    }
    catch (error) {
      connection.console.error("Error running Java analysis: " + error);
    }
  }
}

// Run the Java analysis as a child process, return a promise that resolves with the parsed JSON result from Java
function runJavaAnalysis(uri: string, document: TextDocument): Promise<any> {
  // connection.console.log("running java...")

  return new Promise((resolve, reject) => {
    // Path to the compiled JAR file

    const jarPath = path.join(__dirname, '../../LLVMINI-1.0-SNAPSHOT.jar');


    const java = spawn("java", ["-jar", jarPath, URI.parse(uri).fsPath, document.getText()]);

    // Capture stdout and stderr from the Java process
    let stdout = "";
    let stderr = "";

    java.stdout.on("data", (data) => {
      stdout += data.toString();
    });

    // Capture stderr for error reporting (from java code)
    java.stderr.on("data", (data) => {
      stderr += data.toString();
    });

    // Handle process exit
    java.on("close", (code) => {
      connection.console.log("OUTTT " + stdout)
      //connection.console.log("errout " + stderr)


      if (code !== 0) { // code 0 is success code
        connection.console.error("Java process failed with code " + code + ": " + stderr);
        reject(new Error(stderr || `Java process exited with code ${code}`));
        return;
      }

      try {
        const result = JSON.parse(stdout); // Parse JSON result from Java
        resolve(result);
      } catch (e) {
        reject(new Error("Failed to parse JSON from Java: " + stdout));
      }
    });
  });
}


async function insertInfered(uri: string) {
  const suggestions = inferenceSuggestionMap.get(uri) ?? [];
  // connection.console.log(suggestions.toString())
  // Apply edit in document for each inference suggestion
  for (const s of suggestions) {
    // connection.console.log("inserting: ")
    // connection.console.log(s.name + ":" + s.inferredType)
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
  // connection.console.log("clearing...")
  inferenceSuggestionMap.clear();
}

function inferedInserts(uri: string): TextEdit[] {
  const suggestions = inferenceSuggestionMap.get(uri) ?? [];
  let changes: TextEdit[] = []
  for (let i = 0; i < suggestions.length; i++) {
    let s = suggestions[i];
    changes[i] = TextEdit.insert({
      // For some reason detta va rätt place
      line: s.line - 1,
      character: s.column
    },
      `${s.inferredType} `)
  }
  return changes;
}

// -------------------------------------------------------------------------------------------------
// on change
documents.onDidChangeContent(async change => {
  const uri = change.document.uri;
  const settings = await getDocumentSettings(uri)
  const doc = change.document
  const version = doc.version;
  latestDocumentVersions.set(uri, version); // Set document version on change, used to discard outdated analysis results

  // Type check & inference phase
  if (settings.inlineTypeHint || settings.insertionIntensity == TypeInferenceSetting.InsertOnChange) {

    if (isLatest(uri, version)) {
      await inferenceAnalysis(uri, doc, version);
      connection.console.log(inferenceSuggestionMap.size.toString())
    }

    // Apply on change immediately if possible
    if (isLatest(uri, version) && settings.insertionIntensity == TypeInferenceSetting.InsertOnChange) {
      // connection.console.log("inserting...")
      insertInfered(uri);
    }
    if (isLatest(uri, version) && settings.inlineTypeHint) { // Refresh inlay hints
      connection.languages.inlayHint.refresh();
    }
    // connection.console.log("done.")
  }
});


// -------------------------------------------------------------------------------------------------
// Handle inlay hint requests
// Apply on change as inline hints
connection.languages.inlayHint.on(async (params) => {
  const uri = params.textDocument.uri;
  const settings = await getDocumentSettings(uri)
  if (settings.inlineTypeHint) {
    const suggestions = inferenceSuggestionMap.get(uri) ?? [];
    connection.console.log("Suggestiosn : " + suggestions)
    return suggestions.map((s) => ({
      position: {
        line: s.line - 1,
        character: s.column
      },
      label: `${s.inferredType} `,
      kind: 1,
      textEdits: [{
        range: {
          start: { line: s.line - 1, character: s.column },
          end: { line: s.line - 1, character: s.column }
        },
        newText: `${s.inferredType} `
      }],
      paddingRight: true,
    }));
  }
});


// -------------------------------------------------------------------------------------------------
// Handle document save events 
// analysiss occur on change, but only applied on save
documents.onWillSaveWaitUntil(async (params) => {
  const uri = params.document.uri;
  const doc = params.document
  const settings = await getDocumentSettings(uri)
  let changes: TextEdit[] = []
  if (settings.insertionIntensity == TypeInferenceSetting.OnSave) {
    const version = params.document.version;
    latestDocumentVersions.set(uri, version); // Set document version on change, used to discard outdated analysis results

    // Type check & inference phase
    inferenceAnalysis(uri, doc, version);

    if (isLatest(uri, version)) {
      // connection.console.log("inserting...")
      changes = inferedInserts(uri);
      // clear suggestions after insertion
      // connection.console.log("clearing...")
      inferenceSuggestionMap.clear();
    }
  }
  if (settings.inlineTypeHint) {
    connection.languages.inlayHint.refresh();
  }
  // connection.console.log("done.")
  return changes
})

// -----------------------------------ERROR STUFF--------------------------------------------------
// Syntax error collector

documents.onDidChangeContent((change) => {
  validateDocument(change.document.uri, change.document);
});


documents.onDidOpen((event) => {
  validateDocument(event.document.uri, event.document);
});

class SyntaxErrorCollector implements ANTLRErrorListener<any> {
  private diagnostics: Diagnostic[] = []
  private documentText: string = "";

  setDocumentText(text: string) {
    this.documentText = text;
  }

  syntaxError(
    recognizer: Recognizer<any, any>,
    offendingSymbol: any,
    line: number,
    charPositionInLine: number,
    msg: string,
    e: RecognitionException | undefined
  ): void {

    const userMessage = this.translateError(msg, offendingSymbol)
      .replace(/\\n/g, 'line break')
      .replace(/\\r/g, 'line break')
      .replace(/'\n'/, "'line break'");

    this.diagnostics.push({
      severity: DiagnosticSeverity.Error,
      range: {
        start: { line: line - 1, character: charPositionInLine },
        end: { line: line - 1, character: charPositionInLine + (offendingSymbol?.text?.length || 1) }
      },
      message: userMessage
    });
  }

  private hasUnclosedBraces(): boolean {
    const openBraces = (this.documentText.match(/\{/g) || []).length;
    const closeBraces = (this.documentText.match(/\}/g) || []).length;
    return openBraces > closeBraces;
  }

  private hasUnclosedParens(): boolean {
    const openParens = (this.documentText.match(/\(/g) || []).length;
    const closeParens = (this.documentText.match(/\)/g) || []).length;
    return openParens > closeParens;
  }
  private translateError(msg: string, offendingSymbol: any): string {

    // Missing closing brace
    if (msg.includes("extraneous input '<EOF>'") && this.hasUnclosedBraces()) {
      return "Missing closing brace '}'. Did you forget to close a block?";
    }

    // Missing opening brace
    if (msg.includes("missing '{'")) {
      return "Missing opening brace '{' to start a block.";
    }

    // Missing closing parenthesis
    if (msg.includes("missing ')'")) {
      return "Missing closing parenthesis ')'. Did you forget to close parentheses?";
    }

    // Missing opening parenthesis
    if (msg.includes("missing '('")) {
      return "Missing opening parenthesis '('.";
    }


    // Missing closing bracket
    if (msg.includes("missing ']'")) {
      return "Missing closing bracket ']'. Did you forget to close an array?";
    }

    // Generic incomplete statement
    if (msg.includes("<EOF>")) {
      return "Incomplete code. Check for unclosed braces, parentheses, or brackets.";
    }

    // Default - simplify the message
    return msg
      .replace(/expecting \{.*?\}/, '')
      .replace(/mismatched input/, 'Unexpected')
      .substring(0, 200);
  }

  getDiagnostics(): Diagnostic[] {
    return this.diagnostics;
  }


}

// Validate document - checks both syntax and type errors
async function validateDocument(uri: string, document: TextDocument): Promise<void> {
  const text = document.getText();
  const allDiagnostics: Diagnostic[] = [];

  try {
    // Start with syntax checking
    const inputStream = CharStreams.fromString(text);
    const lexer = new GrammarLexer(inputStream);
    const tokenStream = new CommonTokenStream(lexer);
    const parser = new GrammarParser(tokenStream);

    const errorCollector = new SyntaxErrorCollector();
    errorCollector.setDocumentText(text);
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
        const typeErrors = await checkTypes(uri, text);
        console.log(`Type checker returned ${typeErrors.length} errors`);

        const typeDiagnostics = typeErrors.map((error: any) => ({
          severity: error.severity === 'error' ? DiagnosticSeverity.Error : DiagnosticSeverity.Warning,
          range: {
            start: { line: error.line - 1, character: error.column },
            end: { line: error.endLine, character: error.endColumn }
          },
          message: error.message
        }));

        allDiagnostics.push(...typeDiagnostics);

        if (typeDiagnostics.length > 0) {
          console.log(`Found ${typeDiagnostics.length} type errors in ${document.uri}`);
        }
      } catch (typeError) {
        console.error('Type checker error:', typeError);
      }
    }
    /**
     * mismatched input '\n' expecting {'(', '{', 'return', 'while', 'do', 'if', TYPE, STRING, INT, DOUBLE, BOOL, 'not', ID, '['}
     */
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
        message: `Fatal parser error: ${error}`

      }]
    });
  }
}




// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------

// Make the text document manager listen on the connection
// for open, change and close text document events
documents.listen(connection);

// Listen on the connection
connection.listen();
