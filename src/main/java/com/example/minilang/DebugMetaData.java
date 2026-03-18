package com.example.minilang;

import java.util.HashMap;
import java.util.Map;

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
        Integer id = innerMap.get(line);
        
        if(id == null) {
            id = IdCounter;
            IdCounter++;
            innerMap.put(line, id);
            return id;
        } else 
            return id;
    }

    public String emit() {
        StringBuilder sb = new StringBuilder();
        sb.append("!llvm.dbg.cu = !{!0}\n");
        // This is the first 2 lines, sourceFIle and and file
        sb.append("!0 = !DICompileUnit(language: DW_LANG_C, file: !1, producer: \"mylang\", isOptimized: false, runtimeVersion: 0, emissionKind: LineTablesOnly)\n");
        sb.append("!1 = !DIFile(filename: \"")
        .append(fileName)
        .append("\", directory: \".\")\n");

        // Go through each function and its line and print out
        for (Map.Entry<String, Integer> entry : funcNameIds.entrySet()) {
            Integer funcLine = funcNameLines.get(entry.getKey());
            // !2 = distinct !DISubprogram(name: "helloWorld", scope: !1, 
            // file: !1, line: 3, type: !DISubroutineType(types: !{}), unit: !0)
            sb.append("!").append(entry.getValue())
            .append("  = distinct !DISubprogram(name: \"")
            .append(entry.getKey())
            .append("\", scope: !1, file: !1, line: ")
            .append(funcLine)
            .append(", type: !DISubroutineType(types: !{}), unit: !0) \n");

            Map<Integer, Integer> innerMap = locationIds.get(entry.getKey());
            for (Map.Entry<Integer, Integer> innerEntry : innerMap.entrySet()) {
                //!18 = !DILocation(line: 5, column: 11, scope: !17) example
                Integer funcId = entry.getValue();
                sb.append("!").append(innerEntry.getValue())
                .append(" = !DILocation(line: ")
                .append(innerEntry.getKey()).append(", scope: ")
                .append(funcId).append(")\n");
            }
        }
        return sb.toString();
    }
}
