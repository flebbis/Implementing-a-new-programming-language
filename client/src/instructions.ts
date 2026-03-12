type arguments = (arg1?: string, arg2?: string, arg3?: string) => string;

// This deals with arm64 and x86, x86-64
export const instructions: Record<string, arguments> = {
    mov: (arg1, arg2)        => arg1?.endsWith(')') ? `Load ${arg2} from adress ${arg1}` :
                                arg2?.endsWith(')') ? `Store ${arg1} at adress ${arg2}` :
                                (arg1?.startsWith('%') || arg2?.startsWith('%')) ?
                                `Move ${arg1} into ${arg2}` : `Move ${arg2} into ${arg1}`, 
    ldr: (arg1, arg2, arg3)  => arg3 ? `Load ${arg1} from adress ${arg2} + ${arg3}` :
                                `Load ${arg1} from adress ${arg2}`, 
    str: (arg1, arg2, arg3)  => arg3 ? `Store ${arg1} at adress ${arg2} +${arg3}` :
                                `Store ${arg1} at adress ${arg2}`, 

    add: (arg1, arg2, arg3)  => arg3 ? `Add ${arg3} to ${arg2}, store in ${arg1}` :
                                `Add ${arg1} to ${arg2}, store in ${arg2}`,
    sub: (arg1, arg2, arg3)  => arg3 ? `Subtract ${arg3} from ${arg2}, store in ${arg1}`:
                                `Subtract ${arg1} from ${arg2}, store in ${arg2}`, 
    mul: (arg1, arg2, arg3)  => `Multiply ${arg3} with ${arg2}, store in ${arg1}`,
    imul: (arg1, arg2)       => `Multiply ${arg1} with ${arg2}, store in ${arg2}`,
    subs: (arg1, arg2, arg3) => `${arg1} = ${arg2} - ${arg3} (set flags)`,
    sdiv: (arg1, arg2, arg3) => `Divide ${arg2} / ${arg3}, store in ${arg1}`,
    idiv: (arg1, arg2)       => `Divide ${arg2} / ${arg1}`,
    'b.eq': (arg1)           => `Jump if equal == to ${arg1}`,
    'b.ne': (arg1)           => `Jump if not equal != ${arg1}`,
    'b.ge': (arg1)           => `Jump if greater equal than >= ${arg1}`,
    'b.le': (arg1)           => `Jump if lesser equal than <= ${arg1}`,
    'b.lt': (arg1)           => `Jump if lesser than < ${arg1}`,
    'b.gt': (arg1)           => `Jump if greater than > ${arg1}`,

    cmp: (arg1, arg2)        => `Compare ${arg1} and ${arg2}`,
    b:   (arg1)              => `Jump to ${arg1}`, 
    bl:  (arg1)              => `Call ${arg1}`,
    ret: ()                  => `Return`,
    push: (arg1)             => `Push ${arg1} onto stack`,
    pop: (arg1)              => `Pop from stack into ${arg1}`,

    // x86, x86-64
    jmp:   (arg1)            => `Jump to ${arg1}`, 
    call:  (arg1)            => `Call ${arg1}`,
    je: (arg1)               => `Jump if equal == to ${arg1}`,
    jne: (arg1)              => `Jump if not equal != ${arg1}`,
    jge: (arg1)              => `Jump if greater equal than >= ${arg1}`,
    jle: (arg1)              => `Jump if lesser equal than <= ${arg1}`,
    jl: (arg1)               => `Jump if lesser than < ${arg1}`,
    jg: (arg1)               => `Jump if greater than > ${arg1}`,

}