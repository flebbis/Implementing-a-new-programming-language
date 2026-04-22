# Language tools
In addition to the core language features, FIKA provides a set of powerful tools to enhance the development experience. These tools are adapted to Visual Studio Code, and they include:
- **Language Server**: FIKA comes with a language server that provides features such as syntax highlighting and error checking. This allows developers to write code more efficiently and with fewer errors.
- **Live compilation** : To enhance the learning of assembly, FIKA provides a live compilation feature that allows developers to see the generated assembly code in real-time as they write their FIKA code. This can be a valuable tool for understanding how high-level code translates into low-level instructions.
- **Automatic inference insertions**: FIKA's language server can automatically insert type annotations based on the inferred types. This can help developers understand the types of their variables and expressions, and it can also serve as a learning tool for understanding how type inference works in FIKA.

## Installation
To install the tools for FIKA, you must use Visual Studio Code. You must install the extension for FIKA, which includes the language server and live compilation features. The latest "stable" version of the extension is available in the root repository as `mylang-x.y.z.vsix` and can be installed through the *Extensions* tab on VSCode, extending the context menu (top right `...`) and then selecting `Install from VSIX`.

Press `ctrl-shirt-P` and search for `FIKA: Show generated assembly` to see the generated assembly code in real-time as you write your FIKA code. This can be a valuable tool for understanding how high-level code translates into low-level instructions.

You can also manage the extension settings for the inference insertions in the settings for the extension. Here, you can alter between inserting type annotations as you write, when you save, or as hints instead of actual code insertions.