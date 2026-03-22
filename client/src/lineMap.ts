export function buildLineMap(asmLines: string[]) {
    let asmMapSrc: Map<number, number> = new Map();
    let srcMapAsm: Map<number, number[]> = new Map();
    let currentSrcLine: number = 0;
    let currentAsmLine: number = 0;

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
            }
            continue;
        }   
        // Filter the same as the assembly file filter out
        const isDirective = trimmed.startsWith('.') && !trimmed.startsWith('.LBB');
        const isComment   = trimmed.startsWith(';');
        const isLSymbol   = trimmed.startsWith('l_');
        const isHash      = trimmed.startsWith('#');
        const isBlankSpace = trimmed === '';
        // Labels that are NOT LBB labels and NOT function labels
        const isNonLBBLabel = (trimmed.startsWith('L') || trimmed.endsWith(':'))
                            && !trimmed.startsWith('LBB')
                            && !trimmed.startsWith('_');
 
        const filtered = isDirective || isComment || isLSymbol || isHash || isBlankSpace || isNonLBBLabel;
        const newFunction = trimmed.startsWith("_") && trimmed.includes(":");
        let isFunction = false; // check if its a function or not

        if(filtered) continue;
        // peek ahead to find the next .loc line number
        // so prologue instructions map to the function's first source line
        if(newFunction) {
            if(trimmed.includes("main")){
                isFunction = false;
            } else {
                isFunction = true;
            }

            for (let k = i + 1; k < asmLines.length; k++) {
                const linePeek = asmLines[k].trim();
                const lineNumberPeek = linePeek.split(/\s+/);
                const lineNumber = Number(lineNumberPeek[2]);
                if(linePeek.startsWith(".loc" ) && lineNumber != 0) {
                    currentSrcLine = lineNumber;
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
                recordMap(asmMapSrc,srcMapAsm,currentAsmLine,currentSrcLine);
            } else {
                const isPrologue = trimmed.startsWith('sub') && trimmed.includes('sp, sp');
                const isEpilogue = (trimmed.startsWith('add') && trimmed.includes('sp, sp')) 
                || trimmed === 'ret';

                if(!isPrologue && !isEpilogue) {
                    recordMap(asmMapSrc,srcMapAsm,currentAsmLine,currentSrcLine);
                }
            }
        }
        currentAsmLine++;
    }

    return {asmMapSrc, srcMapAsm};
}

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