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

        const isDirective = trimmed.startsWith('.') && !trimmed.startsWith('.LBB');
        const isComment   = trimmed.startsWith(';');
        const isLSymbol   = trimmed.startsWith('l_');
        const isHash      = trimmed.startsWith('#');
        const isBlankSpace = trimmed === '';
        // Labels that are NOT LBB labels and NOT function labels
        const isNonLBBLabel = (trimmed.startsWith('L') || trimmed.endsWith(':'))
                            && !trimmed.startsWith('LBB')
                            && !trimmed.startsWith('_');
       /* const isPrologue = trimmed.startsWith('sub') && trimmed.includes('sp, sp');
        const isEpilogue = (trimmed.startsWith('add') && trimmed.includes('sp, sp')) 
                || trimmed === 'ret';
*/
        const filtered = isDirective || isComment || isLSymbol || isHash || isBlankSpace || isNonLBBLabel;
        const newFunction = trimmed.startsWith("_") && trimmed.includes(":");

        if(filtered) continue;

        if(newFunction) {
            // peek ahead to find the next .loc line number
            // so prologue instructions map to the function's first source line
            for(let j = i + 1; j < asmLines.length; j++) {
                const next = asmLines[j].trim();
                if(next.startsWith('.loc')) {
                    const parsed = Number(next.split(/\s+/)[2]);
                    if(parsed > 0) {
                        currentSrcLine = parsed;
                        break;
                    }
                }
            }
            currentAsmLine++;
            continue;
        }

        if(currentSrcLine > 0) {
            asmMapSrc.set(currentAsmLine, currentSrcLine);

            let asmsForSrcLine = srcMapAsm.get(currentSrcLine);
            if(!asmsForSrcLine){
                asmsForSrcLine = [];
                srcMapAsm.set(currentSrcLine, asmsForSrcLine);
            }
            asmsForSrcLine.push(currentAsmLine);
        }
        currentAsmLine++;
    }

    return {asmMapSrc, srcMapAsm};
}