import * as vscode from 'vscode';

export class descriptionPanel implements x {
  private view?: vscode.WebviewView;

  resolveWebviewView(webviewView: vscode.WebviewView) {
    this.view = webviewView;
    webviewView.webview.html = this.getHtml('Click an instruction to see its description.');
   }

  update(text: string) {
    if (this.view) {
      this.view.webview.html = this.getHtml(text);
    }
  }

private getHtml(content: string) {
  const html = content
    .replace(/^## (.+)$/gm, '<h2>$1</h2>')
    .replace(/^### (.+)$/gm, '<h3>$1</h3>')
    .replace(/\*\*([^*]+)\*\*/g, '<strong>$1</strong>')
    .replace(/`([^`]+)`/g, '<code>$1</code>')
    .replace(/^- (.+)$/gm, '<li>$1</li>')
    .replace(/(<li>.*<\/li>)/gs, '<ul>$1</ul>')
    .replace(/\n\n/g, '<br>');

  return `<!DOCTYPE html>
<html lang="en">
<head>
<style>
  body {
    font-family: var(--vscode-font-family);
    font-size: var(--vscode-font-size);
    color: var(--vscode-foreground);
    padding: 8px 12px;
    line-height: 1.6;
  }
  h2 {
    font-size: 1em;
    margin: 0 0 6px 0;
    color: var(--vscode-foreground);
    border-bottom: 1px solid var(--vscode-panel-border);
    padding-bottom: 4px;
  }
  h3 { font-size: 0.95em; margin: 8px 0 4px 0; }
  ul { margin: 4px 0; padding-left: 16px; }
  li { margin: 3px 0; }
  code {
    font-family: var(--vscode-editor-font-family);
    font-size: 0.9em;
    background: var(--vscode-textCodeBlock-background);
    padding: 1px 4px;
    border-radius: 3px;
  }
  strong { color: var(--vscode-foreground); }
</style>
</head>
<body>${html}</body>
</html>`;
}
}