import * as path from "path";
import { workspace, ExtensionContext } from "vscode";
import * as vscode from 'vscode';
import { execFileSync } from 'child_process';
import { instructions } from './instructions';

import {
  LanguageClient,
  LanguageClientOptions,
  ServerOptions,
  TransportKind,
} from "vscode-languageclient/node";

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
     
  // ------ Show assembly -------- 
  // ------- Kommer behöva ändras för att runna på att llc läser fil istället ----

  const asmProvider = new AsmProvider();
  // clean up from memory when the extension closes down subscriptions.push

  // ask asmProvider for the content when a document is opened with asm-preview://
  context.subscriptions.push(
    vscode.workspace.registerTextDocumentContentProvider('asm-preview', asmProvider)
  );
  
  let lastPath: string | undefined;
  async function showAssembly(optLevel: string) {


    const workspaceFolder = vscode.workspace.workspaceFolders?.[0].uri.fsPath;
    if (!workspaceFolder) {
    vscode.window.showErrorMessage('No workspace folder open!');
    return;
    }
    const JAR_PATH = path.join(workspaceFolder, 'target', 'LLVMINI-1.0-SNAPSHOT.jar');

    vscode.window.showInformationMessage('Optimazation level' + optLevel);
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
      execFileSync('llc', ['-filetype=asm', optLevel, llFile, '-o', asmFile]);

      

      // read the assembly file
      const fs = require('fs');
      const asm = fs.readFileSync(asmFile, 'utf8');

      //fs.unlinkSync(llFile);
      fs.unlinkSync(asmFile);
      const filtered = asm.split('\n')
      // filter away lines. that start with (. or ends with :) ,l_, ;, !==, 
      // take away the comments from the assembly output
      .filter((line: string) => !line.trim().startsWith('.') || line.trim().startsWith('.LBB'))
      .filter((line: string) => !line.trim().startsWith(';'))
      .filter((line: string) => !line.trim().startsWith('l_'))
      .filter((line: string) => !line.trim().startsWith('#'))
      .filter((line: string) => line.trim() !== '')
      .map((line: string) => line.split(";")[0].split('  #')[0].trimEnd())
      .join('\n');

      // add padding for inlayHints
      const lines = filtered.split('\n').map((line: string) => line.replace(/\t/g, '    '));
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

      asmProvider.setContent(padding);
      
      // open and show the assembly document to the right of the screen
      const doc = await vscode.workspace.openTextDocument(AsmProvider.uri);
      await vscode.window.showTextDocument(doc, vscode.ViewColumn.Two, true);
      } catch(e: any) {
        vscode.window.showErrorMessage('Error: ' + e.message);
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
 
  // commands for optimazation
  //vsCode trigger this when show assembly opens, runs what is inside
  context.subscriptions.push(
    vscode.commands.registerCommand('assembly-preview.show', async() => showAssembly('-O0'))
);
  context.subscriptions.push(
    vscode.commands.registerCommand('opt.buttons', async() => {})
  )

  context.subscriptions.push(
    vscode.commands.registerCommand('O0', async() => {
      showAssembly('-O0');
    })
  )

  context.subscriptions.push(
    vscode.commands.registerCommand('O1', async() => {
      showAssembly('-O1');
    })
  )

  context.subscriptions.push(
    vscode.commands.registerCommand('O2', async() => {
      showAssembly('-O2');
    })
  )

  context.subscriptions.push(
    vscode.commands.registerCommand('O3', async() => {
      showAssembly('-O3');
    })
  )



}

export function deactivate(): Thenable<void> | undefined {
  if (!client) {
    return undefined;
  }
  return client.stop();
}


