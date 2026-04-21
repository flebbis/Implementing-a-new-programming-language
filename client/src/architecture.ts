export interface architecture {
    name: string;
    patterns: RegExp[];
    stackSlot: RegExp;
}

export const PROFILES = {
    "x86-64": {name: "x86-64", patterns: [
        /^\s*retq?/,
        /^\s*subq\s+\$\d+,\s+%rsp/,
        /^\s*addq\s+\$\d+,\s+%rsp/,
    ],
    stackSlot: /movs?[dlqbw]\s+.*,\s*(-?\d+\(%(?:rbp|rsp|ebp|esp)\))/
    },

    "x86": {name: "x86", patterns: [
        /^\s*ret/,
        /^\s*pushl/,
        /^\s*popl/
    ],
    stackSlot: /movs?[dlqbw]\s+.*,\s*(-?\d+\(%(?:rbp|rsp|ebp|esp)\))/
    },

    "aarch64": {name: "aarch64",  patterns: [
        /^\s*sub\s+sp,\s+sp,/,
        /^\s*add\s+sp,\s+sp,/,
        /^\s*ret/,
        /^\s*stp\s+x29,\s+x30,/,
        /^\s*ldp\s+x29,\s+x30,/,
    ],
    stackSlot: /s?tr\s+(?:wzr|xzr|w\d+|x\d+|d\d+|s\d+|q\d+),\s*(\[(?:x29|sp|x\d+)(?:,\s*#?-?\d+)?\])/
    },

    "arm": {name: "arm", patterns: [
        /^\s*sub\s+sp,\s+sp,/,    
        /^\s*sub\s+sp,\s+#/,      
        /^\s*add\s+sp,\s+sp,/,
        /^\s*push\s+\{/,          
        /^\s*pop\s+\{[^}]*pc/,    
        /^\s*bx\s+lr/,            
        /^\s*mov\s+pc,\s+lr/      
    ],
    stackSlot: /str\s+(?:r\d+),\s*(\[(?:r7|r11|fp|sp|r\d+)(?:,\s*#?-?\d+)?\])/
}
}
