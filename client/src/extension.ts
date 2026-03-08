import * as path from "path";
import { workspace, ExtensionContext } from "vscode";
import * as vscode from 'vscode';
import { execFileSync } from 'child_process';

import {
  LanguageClient,
  LanguageClientOptions,
  ServerOptions,
  TransportKind,
} from "vscode-languageclient/node";


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

let client: LanguageClient | undefined;

export async function activate(context: ExtensionContext) {
  // The server is implemented in node
  const serverModule = context.asAbsolutePath(
    path.join("server", "out", "server.js")
  );

  // server debugging options
  // --inspect=6009 uses Node's Inspector mode, allows debugging with the "attach to server" launch
  let debugOptions = {execArgv: ['--nolazy', '--inspect=6009']}

  // If the extension is launched in debug mode then the debug server options are used
  // Otherwise the run options are used
  const serverOptions: ServerOptions = {
    run: { module: serverModule, transport: TransportKind.ipc },
    debug: {
      module: serverModule,
      transport: TransportKind.ipc,
      options: debugOptions
    },
  };

  // Options to control the language client
  const clientOptions: LanguageClientOptions = {
    // Register the server for all 'mylang' files defined in package.json as having ending .ml
    documentSelector: [{ scheme: "file", language: "mylang" }],
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
      const filtered = asm.split('\n')
      .filter((line: string) => !line.trim().startsWith('.'))
      .filter((line: string) => !line.trim().startsWith(';'))
      .filter((line: string) => line.trim() !== '')
      .join('\n');
      asmProvider.setContent(filtered);
      
      // open and show the assembly document to the right of the screen
      const doc = await vscode.workspace.openTextDocument(AsmProvider.uri);
      await vscode.window.showTextDocument(doc, vscode.ViewColumn.Two, true);
      } catch(e: any) {
        vscode.window.showErrorMessage('Error: ' + e.message);
      }
  }
 
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
/* 
export function deactivate(): Thenable<void> | undefined {
  if (!client) {
    return undefined;
  }
  return client.stop();
} */
export async function deactivate() {
  await client?.dispose();
  client=undefined;
}
