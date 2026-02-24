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
// felix JAR
const JAR_PATH ='/Users/felixtan/Documents/Uni/ar3/kandidat/Implementing-a-new-programming-language/target/LLVMINI-1.0-SNAPSHOT.jar';
class AsmProvider implements vscode.TextDocumentContentProvider {
  private content = '; No assembly yet.\n; Open a file and run "Show Assembly".';
  private emitter = new vscode.EventEmitter<vscode.Uri>();  // tell vsCode that the file has changed
  readonly onDidChange = this.emitter.event;

  static readonly uri = vscode.Uri.parse('asm-preview://output/assembly.asm');

  setContent(text: string) {
      this.content = text;
      this.emitter.fire(AsmProvider.uri);
  }

  provideTextDocumentContent(): string {
      return this.content;
  }
}

let client: LanguageClient;

export function activate(context: ExtensionContext) {
  // The server is implemented in node
  const serverModule = context.asAbsolutePath(
    path.join("server", "out", "server.js")
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
     
  // ------ Show assembly -------- AI ville bara se hur det skulle se ut
  const asmProvider = new AsmProvider();
    context.subscriptions.push(
        vscode.workspace.registerTextDocumentContentProvider('asm-preview', asmProvider)
    );

    const showAsmCmd = vscode.commands.registerCommand('assembly-preview.show', async () => {
        const editor = vscode.window.activeTextEditor;
        if (!editor) {
            vscode.window.showErrorMessage('Open a source file first.');
            return;
        }

        await editor.document.save();
        const srcPath = editor.document.uri.fsPath;

        let asm: string;
        try {
            const result = execFileSync('java', ['-jar', JAR_PATH, srcPath]);
            asm = result.toString();
        } catch (e: any) {
            const stderr = e.stderr?.toString() ?? '';
            asm = '; ── Compiler Error ──\n' +
                  (stderr || e.message).split('\n').map((l: string) => '; ' + l).join('\n');
        }

        asmProvider.setContent(asm);

        const doc = await vscode.workspace.openTextDocument(AsmProvider.uri);
        await vscode.window.showTextDocument(doc, vscode.ViewColumn.Two, true);
    });

    context.subscriptions.push(showAsmCmd);
}

export function deactivate(): Thenable<void> | undefined {
  if (!client) {
    return undefined;
  }
  return client.stop();
}
