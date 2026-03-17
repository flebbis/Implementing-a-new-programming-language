export function buildLineMap(llLines: string[], asmLines: string[]) {
    let irSrcLinesMap: Map<string, number[]> = new Map();
    let asmMapSrc: Map<number, number> = new Map();
    let srcMapAsm: Map<number, number[]> = new Map();
    let currentSrcLine: number = 0;
    let currentFunction: string = '';
    const functionsWithSrc = new Set<string>();

    // Pass 1: build per-function irSrcLines
    llLines.forEach(line => {
        if (line.startsWith('define')) {
            const match = line.match(/@(\w+)\(/);
            if (match) {
                currentFunction = match[1];
                currentSrcLine = 0;
            }
            return;
        }

        if (line.includes("; src:")) {
            functionsWithSrc.add(currentFunction);
            currentSrcLine = Number(line.split("; src:")[1].trim());
            return;
        }

        if (line.startsWith(" ") && !line.trim().endsWith(":") && currentSrcLine > 0) {
            if (!irSrcLinesMap.has(currentFunction)) {
                irSrcLinesMap.set(currentFunction, []);
            }
            irSrcLinesMap.get(currentFunction)!.push(currentSrcLine);
        }
    });

    console.log('irSrcLinesMap:', Object.fromEntries(
        Array.from(irSrcLinesMap.entries()).map(([k, v]) => [k, v])
    ));
    console.log('functionsWithSrc:', Array.from(functionsWithSrc));

    // Pass 2: walk assembly, switching to each function's irSrcLines cursor
    let asmLine: number = 0;
    let currSrcLine: number = 0;
    let currentAsmFunction: string = '';
    let currentIrSrcLines: number[] = [];
    let skipFunction: boolean = false;

    asmLines.forEach(line => {
        const trimmed = line.trim();
        const isLBBLabel = trimmed.match(/^LBB\d+_\d+:$/) !== null;
        const isFunctionLabel = trimmed.endsWith(":") && !isLBBLabel;

        if (isFunctionLabel) {
            const rawName = trimmed.replace(':', '');
            const funcName = rawName.startsWith('_') ? rawName.slice(1) : rawName;

            currentAsmFunction = funcName;
            skipFunction = !functionsWithSrc.has(funcName);
            currentIrSrcLines = irSrcLinesMap.get(funcName) ?? [];
            currSrcLine = 0;

            asmLine++;
            return;
        }

        if (skipFunction) {
            asmLine++;
            return;
        }

        if (!isLBBLabel) {
            const srcline = currentIrSrcLines[currSrcLine];
            if (srcline !== undefined) {
                asmMapSrc.set(asmLine, srcline);
                if (!srcMapAsm.has(srcline)) {
                    srcMapAsm.set(srcline, []);
                }
                srcMapAsm.get(srcline)!.push(asmLine);
            }
            currSrcLine++;
        }

        asmLine++;
    });

    return { asmMapSrc, srcMapAsm };
}