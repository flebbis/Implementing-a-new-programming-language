export function buildLineMap(asmLines: string[]) {
    let asmMapSrc: Map<number, number> = new Map();
    let srcMapAsm: Map<number, number[]> = new Map();
    let currentSrcLine: number = 0;
    let currentAsmLine: number = 0;

    asmLines.forEach(line => {
        // For each line in .s, check that it is a real instruction for assembly output
        // Then check if the line starts with .loc, retrieve the sourceline 
        // If the line is a instruction set the value for the assemblyline to the sourceline
        // and sourceline to the assemblyline
        const trimmed = line.trim();
        if(trimmed.startsWith(".loc")) {
            console.log('found loc line:', trimmed);
            const words = trimmed.split(/\s+/);
            const parsed = Number(words[2]);
            if(parsed > 0) {
                currentSrcLine = parsed;
            }
            return;
        }
        
        const isDirective = trimmed.startsWith('.') && !trimmed.startsWith('.LBB');
        const isComment   = trimmed.startsWith(';');
        const isLSymbol   = trimmed.startsWith('l_');
        const isHash      = trimmed.startsWith('#');
        const isBlankSpace = trimmed === '';
        const isNonLBBLabel = (trimmed.startsWith('L') || trimmed.endsWith(':'))
                            && !trimmed.startsWith('LBB');
        
        const filtered = isDirective || isComment || isLSymbol || isHash || isBlankSpace || isNonLBBLabel;

        if(filtered) {
            return;
        }

        if(currentSrcLine > 0) {
            asmMapSrc.set(currentAsmLine, currentSrcLine);

            let asmsForSrcLine = srcMapAsm.get(currentSrcLine);
            if(!asmsForSrcLine){
                asmsForSrcLine = [];
                asmsForSrcLine.push(currentAsmLine);
            } else {
                asmsForSrcLine.push(currentAsmLine);
            }
            srcMapAsm.set(currentSrcLine, asmsForSrcLine);
        }
        currentAsmLine++;
    });

    return {asmMapSrc, srcMapAsm};
}