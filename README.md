# Implementing-a-new-programming-language
New modern programming language
## Goals
 * Easy syntax
 * Strongly typed
 * Language Server Protocol support
 * More!

## Language server
To install the language server enter ``npm install`` into the terminal at the project root directory.

Installation only need to be done once, or if ``package.json`` has been modified. 

Once finished, to build the extension enter 
* ``Ctrl+shift+B`` (windows)
* ``⌘+shift+B`` (mac)
The extension needs to be built each new session of VSCode

To launch a new testing-instance of VSCode with the language server and language tools enabled navigate to the ``Run and Debug`` tab (default shortcut: ``Ctrl+shift+D``) and press ``Launch Client``. Alternativelty press ``F5``.

The extension should detect any ``.ml`` file opened in the new VSCode instance. 