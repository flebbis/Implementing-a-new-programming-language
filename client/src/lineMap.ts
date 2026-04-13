import { architecture } from "./architecture"
export function buildLineMap(asmLines: string[], profile: architecture) {
    let asmMapSrc: Map<number, number> = new Map();
    let srcMapAsm: Map<number, number[]> = new Map();
    let currentSrcLine: number = 0;
    let currentAsmLine: number = 0;
    let isFunction = false; // check if its a function or not
    let inPrologue = false;

    for(let i = 0; i < asmLines.length; i++) {
        const line = asmLines[i];
        // For each line in .s, check that it is a real instruction for assembly output
        // Then check if the line starts with .loc, retrieve the sourceline
        // If the line is a instruction set the value for the assemblyline to the sourceline
        // and sourceline to the assemblyline
        const trimmed = line.trim();

        if(trimmed.startsWith(".loc")) {
            const words = trimmed.split(/\s+/);
            const parsed = Number(words[2]);
            if(parsed > 0) {
                currentSrcLine = parsed;
                inPrologue = false;
            }
            continue;
        }   
        // Filter the same as the assembly file filter out
        const isDirective = trimmed.startsWith('.') && !trimmed.startsWith('.LBB');
        const isComment   = trimmed.startsWith(';');
        const isArmComment = trimmed.startsWith('@'); 
        const isLSymbol   = trimmed.startsWith('l_');
        const isHash      = trimmed.startsWith('#');
        const isBlankSpace = trimmed === '';
        // Labels that are NOT LBB labels and NOT function labels
        const isNonLBBLabel = (trimmed.startsWith('L') || trimmed.endsWith(':'))
                            && !trimmed.startsWith('LBB')
                            && !trimmed.startsWith('_');

        const filtered = isDirective || isComment || isArmComment || isLSymbol || isHash || isBlankSpace || isNonLBBLabel;
        const newFunction = trimmed.startsWith("_") && trimmed.includes(":");


        if(filtered) continue;
        // peek ahead to find the next .loc line number
        // so prologue instructions map to the function's first source line
        if(newFunction) {
            if(trimmed.includes("main")){
                isFunction = false;
                inPrologue = false;
            } else {
                isFunction = true;
                inPrologue = profile.name === 'arm';
            }

            for (let k = i + 1; k < asmLines.length; k++) {
                const linePeek = asmLines[k].trim();
                const lineNumberPeek = linePeek.split(/\s+/);
                const lineNumber = Number(lineNumberPeek[2]);
                if(linePeek.startsWith(".loc") && lineNumber != 0) {
                    currentSrcLine = isFunction ? lineNumber - 1 : lineNumber;
                    if (isFunction) {
                        recordMap(asmMapSrc, srcMapAsm, currentAsmLine, lineNumber - 1);
                    }
                    break;
                }
            }
            currentAsmLine++;
            continue;
        }

        // if there is a source line, check if its a function and not main. Then recordMaps
        // if its main, recordMap on lines except assembly given from llvm
        if(currentSrcLine > 0) {
            if(isFunction) {
                let effectiveSrcLine = currentSrcLine;
                if (inPrologue) {
                    for (let k = i + 1; k < asmLines.length; k++) {
                        const next = asmLines[k].trim();
                        if (next === '' || next.startsWith('@')) continue;
                        if (next.startsWith('.') && !next.startsWith('.loc')) continue;
                        // skip Ltmp/Lfunc style labels (same as isNonLBBLabel filter)
                        if ((next.startsWith('L') || next.endsWith(':'))
                                && !next.startsWith('LBB') && !next.startsWith('_')) continue;
                        if (next.startsWith('.loc')) {
                            const parts = next.split(/\s+/);
                            const num = Number(parts[2]);
                            if (num > 0) effectiveSrcLine = num;
                        }
                        break;
                    }
                }
                recordMap(asmMapSrc,srcMapAsm,currentAsmLine,effectiveSrcLine);
            } else {
                // check if it match with one of the patterns if it does not record
                if(!profile.patterns.some(pattern => pattern.test(trimmed))) {
                    recordMap(asmMapSrc,srcMapAsm,currentAsmLine,currentSrcLine);
                }
            }
        }
        currentAsmLine++;
    }
    return {asmMapSrc, srcMapAsm};
}
    // record values into the maps
function recordMap(asmMap: Map<number, number>, srcMap: Map<number, number[]>,
    asmLine: number, srcLine: number) {
    asmMap.set(asmLine,srcLine);
    let asmsForSrcLine = srcMap.get(srcLine);
    if(!asmsForSrcLine) {
        asmsForSrcLine = [];
        srcMap.set(srcLine,asmsForSrcLine);
    }
    asmsForSrcLine.push(asmLine)
}