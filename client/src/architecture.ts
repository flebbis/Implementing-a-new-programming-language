export interface architecture {
    name: string;
    patterns: RegExp[];
}

export const PROFILES = {
    "x86-64": {name: "x86-64", patterns: [/^\s*retq?/]},
    "aarch64": {name: "aarch64",  patterns: [/^\s*sub\s+sp,\s+sp,/, /^\s*add\s+sp,\s+sp,/,/^\s*ret/]}
}
