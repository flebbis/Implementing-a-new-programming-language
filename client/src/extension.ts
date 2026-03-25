import * as path from "path";
import { workspace, ExtensionContext } from "vscode";
import * as vscode from 'vscode';
import { execFileSync } from 'child_process';
import { instructions } from './instructions';
import { buildLineMap } from "./lineMap";
import {
  LanguageClient,
  LanguageClientOptions,
  ServerOptions,
  TransportKind,
} from "vscode-languageclient/node";
import { countReset } from "console";
import { PROFILES } from "./architecture";

// my own jar file,, fix later
// const JAR_PATH ='/Users/felixtan/Documents/Uni/ar3/kandidat/Implementing-a-new-programming-language/target/LLVMINI-1.0-SNAPSHOT.jar';

// a custom API, that makes the document readonly 
class AsmProvider implements vscode.TextDocumentContentProvider {
  private content = ''; // content of the file
  private emitter = new vscode.EventEmitter<vscode.Uri>();  //a emitter to update the file 
  readonly onDidChange = this.emitter.event;

  static readonly uri = vscode.Uri.parse('asm-preview://output/assembly.asm'); // creates a Uri object
  // display the assembly code, vscode calls this to when a file is open and 
  // when emitter.fire is called 
  provideTextDocumentContent(uri: vscode.Uri): string {
    return this.content;
  }

  setContent(text: string) {
      this.content = text;
      // emitter.fire notify vsCode that the document has changed
      this.emitter.fire(AsmProvider.uri);
  }

  getContent(): string {
      return this.content;
  }
}

let client: LanguageClient;

export function activate(context: ExtensionContext) {
  // The server is implemented in node
  const serverModule = context.asAbsolutePath(
    path.join("..","server", "out", "server.js")
  );

  // If the extension is launched in debug mode then the debug server options are used
  // Otherwise the run options are used
  const serverOptions: ServerOptions = {
    run: { module: serverModule, transport: TransportKind.ipc },
    debug: {
      module: serverModule,
      transport: TransportKind.ipc,
    },
  };

  // Options to control the language client
  const clientOptions: LanguageClientOptions = {
    // Register the server for all documents by default
    documentSelector: [{ scheme: "file", language: "mylang" }],
    // documentSelector: [{ scheme: "file", language: "plaintext" }],
    synchronize: {
      // Notify the server about file changes to '.clientrc files contained in the workspace
      fileEvents: workspace.createFileSystemWatcher("**/.clientrc"),
    },
  };

  // Create the language client and start the client.
  client = new LanguageClient(
    "mylangLanguageServer",
    "MyLang language server testing",
    serverOptions,
    clientOptions
  );

  // Start the client. This will also launch the server
  client.start();

  // Makes so you cant edit the assembly file
  context.subscriptions.push(
    vscode.workspace.onDidChangeTextDocument(e => {
        if (e.document.uri.toString() === AsmProvider.uri.toString()) {
            vscode.commands.executeCommand('undo');
            vscode.window.showErrorMessage("Can´t edit")
        }
    })
);
     
  // ------ Show assembly -------- 
  // ------- Kommer behöva ändras för att runna på att llc läser fil istället ----

  const asmProvider = new AsmProvider();
  // clean up from memory when the extension closes down subscriptions.push

  // ask asmProvider for the content when a document is opened with asm-preview://
  context.subscriptions.push(
    vscode.workspace.registerTextDocumentContentProvider('asm-preview', asmProvider)
  );
  
  let lastPath: string | undefined;
  // keeps control for which editor is the active one 
  let activeEditor: vscode.TextEditor | undefined;
  // sets decoration to a color
  const decoration = vscode.window.createTextEditorDecorationType({
        backgroundColor: 'rgba(61, 145, 197, 0.56)'
      })
  let lineMap: {asmMapSrc: Map<number, number>, srcMapAsm: Map<number, number[]>} | undefined;
  let optLevel: string
  let assembly: string

  async function showAssembly() {

    const workspaceFolder = vscode.workspace.workspaceFolders?.[0].uri.fsPath;
    if (!workspaceFolder) {
    vscode.window.showErrorMessage('No workspace folder open!');
    return;
    }
    const JAR_PATH = path.join(workspaceFolder, 'target', 'LLVMINI-1.0-SNAPSHOT.jar');

    vscode.window.showInformationMessage(`Architecture: ${assembly}  and Optimazation level: ${optLevel}`);
    
    if (optLevel !== '-O0') {
      vscode.window.showWarningMessage(
        `Mapping may be inaccurate at ${optLevel} (accurate at no optimazation)`
      );
    }
    try {
      // get the editor and the filepath then save the file
      const editor = vscode.window.activeTextEditor;
        
      if (editor && editor.document.uri.scheme === 'file') {
        lastPath = editor.document.uri.fsPath;
        await editor.document.save();
    }
        
      if (!lastPath) {
          vscode.window.showErrorMessage('No active .mylang file!');
          return;
      }
        
          // no file open
      if (!vscode.window.activeTextEditor) {
          vscode.window.showErrorMessage('No active editor!');
          return;
      }
/*
      //execute the file, store the content into a string and run setContent
      //fires the emitter which tells vsCode that the file has changed
      const result = execFileSync('java', ['-jar', JAR_PATH, lastPath, optLevel]);
      const asm = result.toString(); 
      asmProvider.setContent(asm);
            */

      execFileSync('java', ['-jar', JAR_PATH, lastPath]);

      // run llc on the .ll file to produce assembly
      const llFile = lastPath.replace('.ml', '.ll');
      const asmFile = lastPath.replace('.ml', '.s');
      execFileSync('llc', [`-march=${assembly}`,'-filetype=asm', optLevel, llFile, '-o', asmFile]);

      // read the assembly file
      const fs = require('fs');
      const asm = fs.readFileSync(asmFile, 'utf8');

      const llContent = fs.readFileSync(llFile, 'utf8');
      //fs.unlinkSync(llFile);
      //fs.unlinkSync(asmFile);

      // filter away lines. that start with (. or ends with :) ,l_, ;, !==, 
      // take away the comments from the assembly output
      const filtered = asm.split('\n')
      .filter((line: string) => !line.trim().startsWith('.') || line.trim().startsWith('.LBB'))
      .filter((line: string) => !line.trim().startsWith(';'))
      .filter((line: string) => !line.trim().startsWith('l_'))
      .filter((line: string) => !line.trim().startsWith('#'))
      .filter((line: string) => line.trim() !== '')
      .filter((line: string) => (!line.trim().startsWith("L") && !line.endsWith(":")) || line.startsWith("LBB"))
      .map((line: string) => line.split(";")[0].split('  #')[0].trimEnd())
      .join('\n');

      // add padding for inlayHints
      const lines = filtered.split('\n').map((line: string) => line.replace(/\t/g, '    '));
      // Retrieve asmfile for 
      lineMap = buildLineMap(asm.split("\n"), PROFILES[assembly]);
      if(!lineMap){
        console.log("buildlinemap not active")
        vscode.window.showErrorMessage("buildLineMap not active")
      }

      let maxLength: number = 0;
      for (let i = 0; i < lines.length; i++) {
        if(lines[i].length > maxLength) {
          maxLength = lines[i].length;
        }
      }

      for (let i = 0; i < lines.length; i++) {
        lines[i] = lines[i].padEnd(maxLength + 10);
      }

      const padding = lines.join('\n');

      const doc = await vscode.workspace.openTextDocument(AsmProvider.uri);
      await vscode.languages.setTextDocumentLanguage(doc, 'asm-preview');
      asmProvider.setContent(padding);
      
      // open and show the assembly document to the right of the screen
      await vscode.window.showTextDocument(doc, vscode.ViewColumn.Two, true);
    } catch(e: any) {
      vscode.window.showErrorMessage(`Error:   e.message`);
    }
  }

  // ------------ SHOW TEXT TO ASSEMBLYCODE -------------------------------------
  // This solution are meant to show for arm64 and arm86 
  // Register inlayHints for assembly

 context.subscriptions.push(
    vscode.languages.registerInlayHintsProvider({scheme: 'asm-preview'}, new class implements vscode.InlayHintsProvider {
      provideInlayHints(document: vscode.TextDocument, range: vscode.Range): vscode.InlayHint[] {
        console.log('provideInlayHints called, lines:', document.lineCount);
        const hints: vscode.InlayHint[] = [];
        // loop through the lines, reetrieve the arguments and trim away l q to match x86 and x86-64
        // If there is matching operand Then apply the inylayhints for the given line
        for (let i = 0; i < document.lineCount; i++) {
          const tokens = document.lineAt(i).text.trim().split(/\s+/).map((t: string) => t.replace(",", ""))
          .map((t: string) => t.replace("#", ""));
          const [op, arg1, arg2, arg3, arg4] = tokens;
          const clearOp = op.replace(/[lq]$/, '')
          const operand = instructions[clearOp];
          if (operand) {
          const text = operand(arg1, arg2, arg3, arg4);
          const pos = new vscode.Position(i, document.lineAt(i).text.length);
          const il = new vscode.InlayHint(pos, text);
          hints.push(il);
          } 
        }
        return hints;
      }
    })
  );

    // ------------ Click for assembly to source and soruce to assembly -------------------------------------

    context.subscriptions.push(
    vscode.window.onDidChangeTextEditorSelection(e => {
      // check that you are in a mylang file
      if(e.textEditor.document.languageId !== 'mylang') return;
      // clear old decoration
      if(activeEditor) {
          activeEditor.setDecorations(decoration, []);
          activeEditor = undefined;
      }
      // retrieve the clickedline and srcMapAsm
      const clickedLine = e.selections[0].active.line + 1;
      const srcMapAsm = lineMap?.srcMapAsm;
      if(!srcMapAsm) return;

      const asmLines = srcMapAsm.get(clickedLine);
      if(!asmLines || asmLines.length === 0) return;
       // retrieve asm editor if it exists
      const asmEditor = vscode.window.visibleTextEditors
        .find(doc => doc.document.uri.scheme === 'asm-preview');
      if(!asmEditor) return;

      // make the asm linees into a range
      const asmRanges = asmLines.map(line => asmEditor.document.lineAt(line).range);
      // set decoration on asm line
      asmEditor.setDecorations(decoration, asmRanges);
      activeEditor = asmEditor;
    })
  )

  context.subscriptions.push(
    vscode.window.onDidChangeTextEditorSelection(e => {
      // check that you are in a asm file
      if(e.textEditor.document.uri.scheme === 'asm-preview') {
        // clear old decoration
      if(activeEditor) {
        activeEditor.setDecorations(decoration, []);
        activeEditor = undefined;
      }
        // retrieve the clickedline and asmMapSrc
      const clickedLine = e.selections[0].active.line;
      const asmMapSrc = lineMap?.asmMapSrc;
      if(!asmMapSrc) return;

      const srcLine = asmMapSrc.get(clickedLine);
      if(srcLine === undefined) return;
      // retrieve src editor if it exists

      const srcEditor = vscode.window.visibleTextEditors
      .find(doc => doc.document.languageId === 'mylang');
      if(!srcEditor) return;

      // make the source line into a range
      const srcRange = srcEditor.document.lineAt(srcLine - 1).range;
      // set decoration on source line
      srcEditor.setDecorations(decoration, [srcRange]);
      activeEditor = srcEditor;
      } 
    })
  )

  // commands for optimazation
  //vsCode trigger this when show assembly opens, runs what is inside
  context.subscriptions.push(
    vscode.commands.registerCommand('assembly-preview.show', async() => {
    optLevel = '-O0';
    assembly ='x86-64'
    showAssembly()
}));
  context.subscriptions.push(
    vscode.commands.registerCommand('opt.buttons', async() => {})
  )

  context.subscriptions.push(
    vscode.commands.registerCommand('O0', async() => {
      optLevel = '-O0';
      showAssembly();
    })
  )

  context.subscriptions.push(
    vscode.commands.registerCommand('O1', async() => {
      optLevel = '-O1';
      showAssembly();
    })
  )

  context.subscriptions.push(
    vscode.commands.registerCommand('O2', async() => {
      optLevel = '-O2';
      showAssembly();
    })
  )

  context.subscriptions.push(
    vscode.commands.registerCommand('O3', async() => {
      optLevel = '-O3';
      showAssembly();
    })
  )

  context.subscriptions.push(
    vscode.commands.registerCommand('mylang.x86-64', async() => {
      assembly = 'x86-64';
      showAssembly();
    })
  )

   context.subscriptions.push(
    vscode.commands.registerCommand('mylang.arm64', async() => {
      assembly = 'aarch64';
      showAssembly();
    })
  )



}

export function deactivate(): Thenable<void> | undefined {
  if (!client) {
    return undefined;
  }
  return client.stop();
}


