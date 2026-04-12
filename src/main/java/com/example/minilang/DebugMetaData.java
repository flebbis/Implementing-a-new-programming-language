package com.example.minilang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
Overview on how it conncets, ish

 First when you run a file. The compiler runs and calls on codeGen to generate IR.
codeGen calls createSubProgram. It takes in the function Name and the 
line it starts it and output the IDCOUNTER it was on.  
Then it remembers the functionName-> ID and functionName->line.  
Creates a new innerMap to locationIDs for the function.  
Then when you go through each line with stateMent and expressionCodeGen. 
getLineId gets called with the current line. 
It checks if that id to the line is NULL. 
if its null it save idCOUNTEr to a id varaible and increment IdCounter. 
Then it saves it in the innerMap for locationsID for the current function and 
return the id. If is not null just return the id. When everything is done.  
It emits. for each function IDS it prints out the information and 
then for each line in each function it prints out the infromation. 
Then loc uses this dbg !N to. with that it sees on the emit information and 
creats .loc with given soruceline for the function*/



public class DebugMetaData {
    String fileName;
    // start at 2 because of DICompileUnit and DIfile have !0 and !1
    int IdCounter = 2;
    // functionName -> ID
    Map<String, Integer> funcNameIds = new HashMap<>();
    // functionName -> Line
    Map<String, Integer> funcNameLines = new HashMap<>();
    //function name -> (sourceLine -> IdCounter)
    Map<String, Map<Integer, Integer>> locationIds = new HashMap<>();
    String currentFunction;
    //varId, funcId, line, typeId
    List<int[]> localVarEntries = new ArrayList<>();
    // varId -> name
    Map<Integer, String> localVarNames = new HashMap<>();
    // llvmtype -> metadata ID 
    Map<String, Integer> typeIds = new HashMap<>();

    public DebugMetaData(String fileName) {
        this.fileName = fileName;
    }

    // remember functionName belongs to what line and what id
    // creates a new innerMap for the function 
    // innerMap will have line->id for the given 
    // Ir instructions under that function, return ID for that function
    public int createSubProgram(String funcName, int line) {
        int id = IdCounter;
        currentFunction = funcName;

        funcNameIds.put(funcName, id);
        funcNameLines.put(funcName, line);
        locationIds.put(funcName, new HashMap<>());
        
        IdCounter++;
        return id;
    }

    // Get the innerMap from the currentFunction
    //Check if their is a id for that line, If not put line -> id
    // then return id else return id for that line
    public int getLineId(int line){
        Map<Integer, Integer> innerMap = locationIds.get(currentFunction);
        if(innerMap == null) {
            System.err.println("WARNING: getLineId called with no currentFunction");
            return createSubProgram("unknown", line);
        }
        Integer id = innerMap.get(line);
        
        if(id == null) {
            id = IdCounter;
            IdCounter++;
            innerMap.put(line, id);
            return id;
        } else 
        return id;
    }
    
    // emit it once, if it has a metadata ID, return otherwise allocate a new one
    // "i32" -> !IDCounter = !n
    private int getOrCreateTypeId(String llvmtype) {
        return typeIds.computeIfAbsent(llvmtype, k -> IdCounter++);
    }

    // Maps LLVM type string to a DWARF DIType definition string
    // If not it deferences null, it crash. With the string it emits the right dwarf .debuginfo
    private String llvmTypeToDIType(String llvmtype) {
        return switch (llvmtype) {
            case "i32"    -> "!DIBasicType(name: \"int\", size: 32, encoding: DW_ATE_signed)";
            case "double" -> "!DIBasicType(name: \"double\", size: 64, encoding: DW_ATE_float)";
            case "i1"     -> "!DIBasicType(name: \"bool\", size: 8, encoding: DW_ATE_boolean)";
            default       -> "!DIBasicType(name: \"string\", size: 64, encoding: DW_ATE_address)"; 
        };
    }

    public String declareVariable(String name, String llvmtype, String register, int line) {
        int funcId = funcNameIds.get(currentFunction);
        int typeId = getOrCreateTypeId(llvmtype);
        int varId = IdCounter++;
        int lineId = getLineId(line);
        localVarEntries.add(new int[]{varId, funcId, line, typeId});
        localVarNames.put(varId, name);
        // Use LLVM 17+ #dbg_declare format (ptr = opaque pointer, avoids upgrade crash in LLVM 21)
        return "  #dbg_declare(ptr " + register
          + ", !" + varId
          + ", !DIExpression(), !" + lineId + ")\n";
    }

    // It runs end of code generation. It takes what the maps have 
    // collected and turns into metaData text so LLVM understands
    public String emit() {
        int moduleFlagsId = IdCounter++;
        StringBuilder sb = new StringBuilder();
        sb.append("!llvm.dbg.cu = !{!0}\n");
        sb.append("!llvm.module.flags = !{!").append(moduleFlagsId).append("}\n");
        sb.append("!0 = distinct !DICompileUnit(language: DW_LANG_C, file: !1, producer: \"mylang\", isOptimized: false, runtimeVersion: 0, emissionKind: FullDebug)\n");
        sb.append("!1 = !DIFile(filename: \"")
        .append(fileName)
        .append("\", directory: \".\")\n");

        // Go through each function and its line and print out
        for (Map.Entry<String, Integer> entry : funcNameIds.entrySet()) {
            Integer funcLine = funcNameLines.get(entry.getKey());
            // !2 = distinct !DISubprogram(name: "helloWorld", scope: !1, 
            // file: !1, line: 3, type: !DISubroutineType(types: !{}), unit: !0)
            sb.append("!").append(entry.getValue())
            .append(" = distinct !DISubprogram(name: \"")
            .append(entry.getKey())
            .append("\", scope: !1, file: !1, line: ")
            .append(funcLine)
            .append(", type: !DISubroutineType(types: !{}), spFlags: DISPFlagDefinition, unit: !0, retainedNodes: !{})\n");

            Map<Integer, Integer> innerMap = locationIds.get(entry.getKey());
            for (Map.Entry<Integer, Integer> innerEntry : innerMap.entrySet()) {
                //!18 = !DILocation(line: 5, column: 11, scope: !17) example
                Integer funcId = entry.getValue();
                sb.append("!").append(innerEntry.getValue())
                .append(" = !DILocation(line: ")
                .append(innerEntry.getKey()).append(", scope: !")
                .append(funcId).append(")\n");
            }
        }
        // Go trough each variable and print out, the name or var, funciton id, file, line number, typeID -> IDCounter 
        // !7 = !DILocalVariable(name: "x", scope: !2, file: !1, line: 5, type: !9)
        for (int[] entry1 : localVarEntries) {
            int varId = entry1[0], funcId = entry1[1], line = entry1[2], typeId = entry1[3];
            sb.append("!").append(varId)
            .append(" = !DILocalVariable(name: \"").append(localVarNames.get(varId))
            .append("\", scope: !").append(funcId)
            .append(", file: !1, line: ").append(line)
            .append(", type: !").append(typeId).append(")\n");
        }
        // for each type convert type to string 
        // 9 = !DIBasicType(name: "int", size: 32, encoding: DW_ATE_signed)
        for (Map.Entry<String, Integer> typeEntry : typeIds.entrySet()) {
            sb.append("!").append(typeEntry.getValue())
            .append(" = ").append(llvmTypeToDIType(typeEntry.getKey())).append("\n");
        }
        sb.append("!").append(moduleFlagsId).append(" = !{i32 2, !\"Debug Info Version\", i32 3}\n");
        return sb.toString();
    }

}
