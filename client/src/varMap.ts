import { profile } from "node:console";
import { architecture } from "./architecture";

export function buildVarMap(llLines: string) {
    let funcVarMap:  Map<string, string[]> = new Map();
    let functionName: string | undefined;
    let variable: string | undefined;
    
    const splitted = llLines.split('\n')
    splitted.forEach(line => {
        
        const trimmed = line.trim();
        const isFunction =  trimmed.includes('@') && trimmed.includes('{')
        if(isFunction) {
            console.log('Found function line:', trimmed);
            console.log('Matched name:', trimmed.match(/@([a-zA-Z_][a-zA-Z0-9_]*)\(/)?.[1]);
            functionName = trimmed.match(/@([a-zA-Z_][a-zA-Z0-9_]*)\(/)?.[1];
        }

        // checkes that we are in a function
        if(functionName) {
           if(trimmed.includes('addr = alloca' )){
             console.log('Found alloca line:', trimmed);
               variable = trimmed.match(/%([a-zA-Z_][a-zA-Z0-9_]*)\.addr/)?.[1];
               console.log('Extracted variable:', variable);
                console.log('Current functionName:', functionName);
               let varList = funcVarMap.get(functionName);
               if(!varList) {
                   varList = [];
                   funcVarMap.set(functionName,varList);
                }
            if(variable && !varList.includes(variable)) {
                varList.push(variable);
            }
            }
        }
    });
    return {funcVarMap}
}

export function buildStackMap(asmLines: string[],profile: architecture) {
    let funcStackMap: Map<string, string[]> = new Map();
    let stack: string | undefined;
    let functionName: string | undefined;

    asmLines.forEach(line => {
        const trimmed = line.trim();
        const splitLine = trimmed.split(/\s+/)[0];
        const isFunction = splitLine.startsWith('_') && splitLine.endsWith(':')
        if(isFunction) {
            functionName = trimmed.match(/_([a-zA-Z_][a-zA-Z0-9_]*):/)?.[1];
              console.log('Stack: found function label:', trimmed);
                console.log('Stack: extracted name:', trimmed.match(/_([a-zA-Z_][a-zA-Z0-9_]*):/)?.[1]);
        }
        
        if(functionName) {
            if(profile.stackSlot.test(trimmed)) {
                console.log('Stack: found slot line:', trimmed);
                stack = trimmed.match(profile.stackSlot)?.[1];
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
    });
    return {funcStackMap}
}

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