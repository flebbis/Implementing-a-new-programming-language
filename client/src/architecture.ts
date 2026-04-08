export interface architecture {
    name: string;
    patterns: RegExp[];
    stackSlot: RegExp;
}

export const PROFILES = {
    "x86-64": {name: "x86-64", patterns: [
        /^\s*retq?/
    ],
    stackSlot: /mov[lqbw]\s+\$[\d-]+,\s*(-?\d+\(%(?:ebp|esp|eax|ecx)\))/
    },

    "x86": {name: "x86", patterns: [
        /^\s*ret/,
        /^\s*pushl/,
        /^\s*popl/
    ],
    stackSlot: /mov[lqbw]\s+\$[\d-]+,\s*(-?\d+\(%(?:ebp|esp|eax|ecx)\))/
    },

    "aarch64": {name: "aarch64",  patterns: [
        /^\s*sub\s+sp,\s+sp,/,
        /^\s*add\s+sp,\s+sp,/,
        /^\s*ret/
    ],
    stackSlot: /s?tur\s+(?:wzr|xzr|w\d+|x\d+),\s*(\[(?:x29|sp|x\d+),\s*#?-?\d+\])/
    },

    "arm": {name: "arm", patterns: [
        /^\s*sub\s+sp,\s+sp,/,     // ARM: sub sp, sp, #N
        /^\s*sub\s+sp,\s+#/,       // Thumb: sub sp, #N
        /^\s*add\s+sp,\s+sp,/,
        /^\s*push\s+\{/,            // prologue push
        /^\s*pop\s+\{[^}]*pc/,     // epilogue pop restoring pc
        /^\s*bx\s+lr/,             // ARM/Thumb return via bx lr
        /^\s*mov\s+pc,\s+lr/       // old ARM return
    ],
    stackSlot: /str\s+(?:r\d+),\s*(\[(?:r7|r11|fp|sp|r\d+),\s*#?-?\d+\])/
}
}
