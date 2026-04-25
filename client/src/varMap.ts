import { architecture } from "./architecture";
// .ll file produce  define void @main() !dbg !2 {
//                   entry:
//                   %register0 = alloca i32, !dbg !3
//                   #dbg_declare(ptr %register0, !5, !DIExpression(), !3)
//                   !5 = !DILocalVariable(name: "x", scope: !2, file: !1, line: 1, type: !4)

// To retrieve the variable it first check of the line match !DILOcalVariable, and retrieve the meta id and variable
// After it checks if the line match with dbg declare and retireve the register and meta id 
// Then for each function it checks lines with alloca (this is where variables is declared)
// If it sees an alloca get the meta id through the register and then variabel through meta id
// Then push the variable for the function

export function buildVarMap(llLines: string) {
    let funcVarMap: Map<string, string[]> = new Map();
    const lines = llLines.split('\n');

    //!DILocalVariable meta id -> variable name, "!8" → "i"
    const varMetaMap = new Map<string, string>();
    for (const line of lines) {
        const match = line.match(/^(!\d+) = .*!DILocalVariable\(name: "([^"]+)"/);
        if (match) varMetaMap.set(match[1], match[2]);
    }

    //register -> meta id from #dbg_declare, "%register1" → "!8"
    const registerVarMap = new Map<string, string>();
    for (const line of lines) {
        const match = line.match(/#dbg_declare\(ptr (%[a-zA-Z0-9_]+),\s*(!?\d+)/);
        if (match) registerVarMap.set(match[1], match[2]);
    }

    //walk functions, collect alloca registers that have debug names
    let currentFunction: string | undefined;
    for (const line of lines) {
        const trimmed = line.trim();
        const funcMatch = trimmed.match(/^define .* @([a-zA-Z_][a-zA-Z0-9_]*)\(/);
        if (funcMatch) {
            currentFunction = funcMatch[1];
        }
        if (!currentFunction) continue;

        const allocaMatch = trimmed.match(/^(%[a-zA-Z0-9_]+) = alloca/);
        if (allocaMatch) {
            const reg = allocaMatch[1];
            const metaId = registerVarMap.get(reg);
            if (!metaId) continue;
            const varName = varMetaMap.get(metaId);
            if (!varName) continue;
            let varList = funcVarMap.get(currentFunction);
            if (!varList) { varList = []; funcVarMap.set(currentFunction, varList); }
            if (!varList.includes(varName)) varList.push(varName);
        }
    }
    return {funcVarMap};
}

// funcStackMap: "helloWorld" → ["-8(%rbp)", "-16(%rbp)" for example for x86-64
export function buildStackMap(asmLines: string[],profile: architecture) {
    let funcStackMap: Map<string, string[]> = new Map();
    let stack: string | undefined;
    let functionName: string | undefined;

    asmLines.forEach(line => {
        const trimmed = line.trim();
        const splitLine = trimmed.split(/\s+/)[0];
        const isFunction = splitLine.endsWith(':') &&
            !splitLine.startsWith('LBB') && !splitLine.startsWith('L') && !splitLine.startsWith('.');
        if(isFunction) {
            functionName = trimmed.match(/_?([a-zA-Z_][a-zA-Z0-9_]*):/)?.[1];
        }
        
        if(functionName) {
            if(profile.stackSlot.test(trimmed)) {
                stack = trimmed.match(profile.stackSlot)?.[1];
                if(stack) {
                    let stackList = funcStackMap.get(functionName);
                    if(!stackList) {
                        stackList = []
                        funcStackMap.set(functionName,stackList);
                    }
                    if(!stackList.includes(stack)) {
                        stackList.push(stack);
                    }
                }
            }
        }
    });
    return {funcStackMap}
}

// zipp varMap and stackMap 
// zippedVarMap: "helloWorld" → { "i" → "-8(%rbp)", "felixTan" → "-16(%rbp)" }
export function zippedVarMap(funcVarMap: Map<string,string[]>, funcStackMap: Map<string,string[]>)
: Map<string, Map<string, string>> {
    let zipped: Map<string, Map<string, string>> = new Map();
    for (const entry of funcVarMap) {
        const funcVar = entry[0];
        const stacks =  funcStackMap.get(funcVar);
        if(!stacks) continue;

        const zippedMap = new Map<string, string>();    
        zipped.set(funcVar,zippedMap);
        
        const variables = entry[1];
        const minLength = Math.min(variables.length, stacks.length);
        for (let j = 0; j < minLength; j++) {
            zippedMap.set(variables[j],stacks[j])
        }
    }
    return zipped
}