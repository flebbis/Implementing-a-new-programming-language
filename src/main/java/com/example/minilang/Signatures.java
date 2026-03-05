//DENNA FIL BEHÖVS INTE EGENTLIGEN HAR JAG NU INSETT. MEN SPARAR FÖR SÄKERHETS SKULL

package com.example.minilang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Signatures {
    
    private HashMap<String, FunctionInfo> signatures = new HashMap<>();
    
    public Signatures() {
        createBuiltInFunctions();
    }
    
    private void createBuiltInFunctions() {
        
        /*
        // printInt
        List<Ast.Arg> printIntArgs = new ArrayList<>();
        printIntArgs.add(new Ast.Arg("x", Ast.Type.TInt, null));
        addFunction("", "printInt", printIntArgs, Ast.Type.TVoid);
        
        // printDouble
        List<Ast.Arg> printDoubleArgs = new ArrayList<>();
        printDoubleArgs.add(new Ast.Arg("x", Ast.Type.TDouble, null));
        addFunction("", "printDouble", printDoubleArgs, Ast.Type.TVoid);
        
        // readInt
        List<Ast.Arg> readIntArgs = new ArrayList<>();
        addFunction("", "readInt", readIntArgs, Ast.Type.TInt);
        
        // readDouble
        List<Ast.Arg> readDoubleArgs = new ArrayList<>();
        addFunction("", "readDouble", readDoubleArgs, Ast.Type.TDouble);
    */} 
    
    public void addFunction(String fileName, String name, 
                           List<Ast.Arg> args, Ast.Type returnType) {
        signatures.put(name, new FunctionInfo(fileName, name, args, returnType));
    }
    
    public Ast.Type getReturnType(String id) {
        return signatures.get(id).returnType;
    }
    
    public List<Ast.Arg> getArgs(String id) {
        return signatures.get(id).args;
    }
    
    public String lookUpName(String id) {
        return signatures.get(id).name;
    }
    
    public int lookupReturnTypeSize(String id) {
        if(signatures.get(id).returnType.equals(Ast.Type.TVoid)) {
            return 0;
        }
        return 1;
    }
    
    public int lookupArgWordSize(String id) {
        return signatures.get(id).args.size();
    }
    
    // ← THE MISSING INNER CLASS
    private class FunctionInfo {
        String fileName;
        String name;
        List<Ast.Arg> args;
        Ast.Type returnType;
        
        // Constructor
        public FunctionInfo(String fileName, String name, List<Ast.Arg> args, Ast.Type returnType) {
            this.fileName = fileName;
            this.name = name;
            this.args = args;
            this.returnType = returnType;
        }
    }
}