# Implementing-a-new-programming-language
New modern programming language
## Goals
 * Easy syntax
 * Strongly typed
 * Language Server Protocol support
 * More!

## Language server
The extension should detect any ``.ml`` file opened in the new VSCode instance. 

### Development installation
To install the language server enter ``npm install`` into the terminal at the project root directory.

Installation only need to be done once, or if ``package.json`` has been modified. 

Once finished, to build the extension enter 
* ``Ctrl+shift+B`` (windows)
* ``⌘+shift+B`` (mac)
The extension needs to be built each new session of VSCode

To launch a new testing-instance of VSCode with the language server and language tools enabled navigate to the ``Run and Debug`` tab (default shortcut: ``Ctrl+shift+D``) and press ``Launch Client``. Alternativelty press ``F5``.

### Extension installation
The latest "stable" version of the extension is avalliable in the root repository as ``mylang-x.y.z.vsix`` and can be installed through the *Extensions* tab on VSCode, extending the context menue (top right ``...``) and then selecting ``Install from VSIX``
<img width="746" height="308" alt="image" src="https://github.com/user-attachments/assets/6e793c3a-0a4e-4cee-93fc-7da22ab8903a" />


