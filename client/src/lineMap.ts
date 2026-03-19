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
        const isLabel = line.endsWith(":");
        const isEntry = line.startsWith("_") || line.startsWith(";");
        const isNotLoc = line.trim().startsWith(".") && !line.trim().startsWith(".loc");
        const trimmed = line.trim();
        const isBlankSpace = trimmed === '';
        const isInstuction = !isLabel && !isEntry && !isNotLoc && !isBlankSpace;
        if(trimmed.startsWith(".loc")) {
            const words = trimmed.split(/\s+/);
            currentSrcLine = Number(words[2]);

        }
        if(isInstuction && currentSrcLine > 0) {
            let asmsForSrcLine = srcMapAsm.get(currentSrcLine);
            if(!asmsForSrcLine){
                asmsForSrcLine = [];
                asmsForSrcLine.push(currentAsmLine);
            } else {
                asmsForSrcLine.push(currentAsmLine);
            }
            srcMapAsm.set(currentSrcLine, asmsForSrcLine);
            asmMapSrc.set(currentAsmLine, currentSrcLine);
            currentAsmLine++;
        }
    });

    return {asmMapSrc, srcMapAsm};
}