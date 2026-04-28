type arguments = (arg1?: string, arg2?: string, arg3?: string, arg4?: string) => string;
// registers %eax, x0, w8 etc
const isReg   = (a?: string) => !!a && (a.startsWith('%') || /^[xwbsdqv]\d+$/.test(a) || /^r\d+$/.test(a) || ['wzr','xzr','sp','lr','fp'].includes(a));
// constants $5, #5, 5
const isConst = (a?: string) => !!a && (a.startsWith('$') || a.startsWith('#') || /^\d+$/.test(a));
// variable x, i, counter
const isVar   = (a?: string) => !!a && !isReg(a) && !isConst(a) && !a.startsWith('_') && !a.startsWith('L') && !a.startsWith('[');
// raw value
const val     = (a: string)  => (a.startsWith('$') || a.startsWith('#')) ? a.slice(1) : a;

// Describtion for assembly this deals with arm64, arm and x86, x86-64
export const instructions: Record<string, arguments> = {

    // x86/64 movl $30, -4(%rsp)
    // x86/64 movl -4(%rsp), %eax   
    // x86/64 movl %eax, -4(%rsp)  
    // arm/arm64  mov x0, #30  
    mov: (arg1, arg2) => 
        isConst(arg1) && isVar(arg2) ? `${arg2} = ${val(arg1!)}` :
        isVar(arg1) && isReg(arg2)   ? `Load ${arg1} into register`    :
        isReg(arg1) && isVar(arg2)   ? `${arg2} = register` :
        isReg(arg1) && isConst(arg2) ? `Load ${val(arg2!)} into register`:
        `Copy register into register`,

// In instructions:
    movl: (arg1, arg2) => {
        const ripDst = arg2?.match(/^(.+)\(%rip\)$/);
        const ripSrc = arg1?.match(/^(.+)\(%rip\)$/);
        if (ripDst && isConst(arg1))  return `${ripDst[1]} = ${val(arg1!)}`;
        if (ripDst && isReg(arg1))    return `${ripDst[1]} = register`;
        if (ripSrc && isReg(arg2))    return `Load ${ripSrc[1]} into register`;
        return isConst(arg1) && isVar(arg2) ? `${arg2} = ${val(arg1!)}` :
            isVar(arg1)   && isReg(arg2) ? `Load ${arg1} into register` :
                isReg(arg1)   && isVar(arg2) ? `${arg2} = register` :
                    `Copy register into register`;
    },

    movq: (arg1, arg2) => {
        const ripDst = arg2?.match(/^(.+)\(%rip\)$/);
        const ripSrc = arg1?.match(/^(.+)\(%rip\)$/);
        if (ripDst && isConst(arg1))  return `${ripDst[1]} = ${val(arg1!)}`;
        if (ripDst && isReg(arg1))    return `${ripDst[1]} = register`;
        if (ripSrc && isReg(arg2))    return `Load ${ripSrc[1]} into register`;
        return isConst(arg1) && isVar(arg2) ? `${arg2} = ${val(arg1!)}` :
            isVar(arg1)   && isReg(arg2) ? `Load ${arg1} into register` :
                isReg(arg1)   && isVar(arg2) ? `${arg2} = register` :
                    `Copy register into register`;
    },

    // arm/arm64: ldr r0, [sp, #4]
    // arm/arm64: ldr r0, x       
    ldr: (_arg1, arg2) =>
        isVar(arg2) ? `Load ${arg2} into register` :
        `Load from address into register`,
    
    // arm64 str wzr, [sp, #4]                           
    // arm/arm64 w8|r0, [sp, #4]
    str: (arg1, arg2) => 
        (arg1 === 'wzr' || arg1 === 'xzr') ? `${arg2} = 0` :
        isVar(arg2)                        ? `${arg2} = register` :
        `Store register to address`,

    // ------------ ARITHMETIC ------------        

    // arm/arm64 add w8, x0, #2 
    // arm/arm64 add x0, x1, #2 
    // arm/arm64 add x0, x1, x2
    // x86/64 addl $2, -20(%rsp)
    // x86/64 addl %eax, -20(%rsp)
    add:  (arg1, arg2, arg3) =>
        ((arg1 === 'sp' && isConst(arg3 ?? arg2)) || (isConst(arg1) && (arg2 === '%rsp' || arg2 === '%esp')))
                                             ? `Restore stack space (function teardown)` :
        arg3 && isVar(arg1) && isConst(arg3) ? `${arg1} += ${val(arg3!)}` :
        arg3 && isConst(arg3)                ? `Add ${val(arg3!)} to register` :
        arg3                                 ? `Add register to register` :
        isConst(arg1) && isVar(arg2)         ? `${arg2} += ${val(arg1!)}` :
        isVar(arg2)                          ? `Add register to ${arg2}` :
        `Add to register`,
    
    // arm/arm64 sub x, x0, #1     
    // arm/arm64 sub x0, x1, #1    
    // arm/arm64 sub x0, x1, x2    
    // x86/64 subl $1, -20(%rsp)   
    // x86/64 subl %eax, -20(%rsp) 
    sub: (arg1, arg2, arg3) =>
        ((arg1 === 'sp' && isConst(arg3 ?? arg2)) || (isConst(arg1) && (arg2 === '%rsp' || arg2 === '%esp')))
                                             ? `Allocate stack space for local variables` :
        arg3 && isVar(arg1) && isConst(arg3) ? `${arg1} -= ${val(arg3!)}` :
        arg3 && isConst(arg3)                ? `Subtract ${val(arg3!)} from register` :
        arg3                                 ? `Subtract register from register` :
        isConst(arg1) && isVar(arg2)         ? `${arg2} -= ${val(arg1!)}` :
        isVar(arg2)                          ? `Subtract register from ${arg2}` :
        `Subtract from register`,

        // arm/arm64 mul x, x0, #2
        // arm/arm64 mul x0, x0, #2
    mul: (arg1, _arg2, arg3) =>
        arg3 && isVar(arg1) && isConst(arg3) ? `${arg1} *= ${val(arg3)}` :  
        arg3 && isConst(arg3)                ? `Multiply register by ${val(arg3)}` : 
        `Multiply registers`,                                                       

        // arm64: subs x0, x0, #1
        // arm64: subs x0, x1, x2
    subs: (_arg1, _arg2, arg3) =>
        arg3 && isConst(arg3) ? `Subtract ${val(arg3)} from register, set flags` :
        `Subtract registers, set flags`,                                            

        
        // arm32
        // bic sp, sp, #7  
        bic: (_arg1, _arg2, arg3) =>
            isConst(arg3) ? `Clear bits ${val(arg3!)} in register` :
        `Bitwise AND NOT`,
        
        // ----- JUMPS ----
    bge: (arg1) => `Jump if >= to ${arg1}`,
    bgt: (arg1) => `Jump if > to ${arg1}`,
    ble: (arg1) => `Jump if <= to ${arg1}`,
    blt: (arg1) => `Jump if < to ${arg1}`,
    beq: (arg1) => `Jump if == to ${arg1}`,
    bne: (arg1) => `Jump if != to ${arg1}`,

    // arm64 
    'b.eq': (arg1) => `Jump if == to ${arg1}`,
    'b.ne': (arg1) => `Jump if != to ${arg1}`,
    'b.ge': (arg1) => `Jump if >= to ${arg1}`,
    'b.le': (arg1) => `Jump if <= to ${arg1}`,
    'b.lt': (arg1) => `Jump if < to ${arg1}`,
    'b.gt': (arg1) => `Jump if > to ${arg1}`,


    cmp: (arg1, arg2)        => `Compare ${arg1} and ${arg2}`,
    b:   (arg1)              => `Jump to ${arg1}`,
    bl:  (arg1)              => `Call ${arg1}`,
    ret: ()                  => `Return`,
    push: (arg1)             =>
        (arg1 === '%rbp' || arg1 === '%ebp') ? `Save frame pointer (function setup)` :
        `Push ${arg1} onto stack`,
    pop: (arg1)              =>
        (arg1 === '%rbp' || arg1 === '%ebp') ? `Restore frame pointer (function teardown)` :
        `Pop from stack into ${arg1}`,
    stp: (arg1, arg2, arg3, arg4) =>
        (arg1 === 'x29' && arg2 === 'x30') ? `Save frame pointer and link register (function setup)` :
        arg4 ? `Store ${arg1} and ${arg2} to [${arg3} + ${arg4}]`
             : `Store ${arg1} and ${arg2} to [${arg3}]`,

    // --- arm64 ---

    // adrp x8, LCPI0_0@PAGE 
    adrp: () => `Load page address into register`,

    // fmov d0, #2.0e+0  
    // fmov d0, x0   
    fmov: (arg1, arg2) =>
        isConst(arg2) ? `Load ${val(arg2!)} into float register` :
        isVar(arg2)   ? `Load ${arg2} into float register` :
        `Copy float register`,

    // stur wzr, [sp, #-4]
    // arm64 stur w0, [sp, #-4]
    stur: (arg1, arg2) => 
        (arg1 === 'wzr' || arg1 === 'xzr') && isVar(arg2) ? `${arg2} = 0` :
        isVar(arg2)                                       ? `${arg2} = register` :
        `Store register to address`,

    // ldur w0, [sp, #-4] 
    ldur: (arg1, arg2) => 
        isVar(arg2) ? `Load ${arg2} into register` :
        `Load from address into register`,

    ldp:  (arg1, arg2, arg3, arg4) =>
        (arg1 === 'x29' && arg2 === 'x30') ? `Restore frame pointer and link register (function teardown)` :
        arg4 ? `Load [${arg3} + ${arg4}] into ${arg1} and ${arg2}`
             : `Load [${arg3}] into ${arg1} and ${arg2}`,

    lsl:  (arg1, arg2, arg3) =>
        `Shift ${arg2} left by ${arg3} into ${arg1}`,

    lsr:  (arg1, arg2, arg3) =>
        `Shift ${arg2} right by ${arg3} into ${arg1}`,

    sdiv: (_arg1, arg2, arg3) => arg3 ? `Divide ${arg2} by ${arg3} (signed)` : `Divide registers (signed)`,

    // ---- x86, x86-64------

    // leaq -4(%rsp), %rax  
    leaq: (arg1, _arg2) =>
        arg1?.includes('(%rip)') ? `Load address of string literal` :
        isVar(arg1)             ? `Load address of ${arg1}` :
        `Load effective address`,

    // movsd %xmm0, -8(%rbp)   
    // movsd -8(%rbp), %xmm0  
    movsd: (arg1, arg2) =>
        isVar(arg1) && isReg(arg2) ? `Load ${arg1} into register` :
        isReg(arg1) && isVar(arg2) ? `${arg2} = register` :
        `Copy float register`,

    movsq: ()                => `Copy 8 bytes from source to destination`,

    jmp:   (arg1)            => `Jump to ${arg1}`,
    call:  (arg1)            => `Call ${arg1}`,
    je: (arg1)               => `Jump if == to ${arg1}`,
    jne: (arg1)              => `Jump if != to ${arg1}`,
    jge: (arg1)              => `Jump if >= to ${arg1}`,
    jle: (arg1)              => `Jump if <= to ${arg1}`,
    jl: (arg1)               => `Jump if < to ${arg1}`,
    jg: (arg1)               => `Jump if > to ${arg1}`,
    idiv: (arg1)             => `Signed divide by ${arg1}`,


    xor:  (arg1, arg2)       => `${arg1} = ${arg1} XOR ${arg2}`,
    inc:  (arg1)             => `${arg1} += 1`,
    leal: (arg1, _arg2)      => isVar(arg1) ? `Load address of ${arg1}` : `Load effective address`,
    shll: (arg1, arg2)       => arg2 ? `Shift ${arg1} left by ${arg2}` : `Shift ${arg1} left by 1`,

    // arm32 
    ldm:  (arg1, arg2)       => `Load multiple registers from [${arg1}]`,
    ldrlt: (arg1, arg2)      => isVar(arg2) ? `Load ${arg2} if <` : `Load if <`,
    movlt: (arg1, arg2)      => isConst(arg2) ? `${arg1} = ${val(arg2!)} if <` : `Move if <`,

    // arm64 jumps
    cbz:  (arg1, arg2)       => `Jump to ${arg2} if ${arg1} == 0`,
    cbnz: (arg1, arg2)       => `Jump to ${arg2} if ${arg1} != 0`,
    bls:  (arg1)             => `Jump if <= (unsigned) to ${arg1}`,
    bhi:  (arg1)             => `Jump if > (unsigned) to ${arg1}`,
}

// --------------- EXTENDED INSTRUCTIONS ---------------

export const extendedInstructions: Record<string, arguments> = {

    mov: (arg1, arg2) =>
        isConst(arg1) && isVar(arg2) ?
            `## mov — Move value\n\n` +
            `Copies a constant value into a variable\n\n` +
            `- **${arg1}** — the literal value being assigned\n` +
            `- **${arg2}** — variable \`${arg2}\`, the destination on the stack\n\n` +
            `This is the compiler assigning a constant directly to **${arg2}** in your source code` :
        isVar(arg1) && isReg(arg2) ?
            `## mov — Move value\n\n` +
            `Loads a variable from the stack into a register\n\n` +
            `- **${arg1}** — variable \`${arg1}\`, loaded from the stack\n` +
            `- **${arg2}** — destination register\n\n` +
            `The compiler is reading **${arg1}** to use it in a computation` :
        isReg(arg1) && isVar(arg2) ?
            `## mov — Move value\n\n` +
            `Stores a register value back into a variable\n\n` +
            `- **${arg1}** — source register holding the value\n` +
            `- **${arg2}** — variable \`${arg2}\`, the destination on the stack\n\n` +
            `This saves a computed result back into **${arg2}** in your source code` :
        isReg(arg1) && isConst(arg2) ?
            `## mov — Move value\n\n` +
            `Loads a literal value into a register\n\n` +
            `- **${arg1}** — destination register\n` +
            `- **${arg2}** — the literal value being loaded\n\n` +
            `On arm, this is the first step before storing a value into a variable on the stack` :

            `## mov — Move value\n\n` +
            `Copies data between registers during a computation\n\n` +
            `- **${arg1}** — source register\n` +
            `- **${arg2}** — destination register`,
    movl: (arg1, arg2) => extendedInstructions.mov(arg1, arg2),
    movq: (arg1, arg2) => extendedInstructions.mov(arg1, arg2),

    ldr: (arg1, arg2, arg3) =>
        isReg(arg1) && isVar(arg2) ? 
            `## ldr - Load from memory\n\n` + 
            `Reads a value from memory into a register so it can be used in a computation\n\n` +
            `- **${arg1}** - destination register, will hold the loaded value\n` +
            `- **${arg2}** - variable \`${arg2}\`, read from the stack\n\n` +
            `The compiler is reading **${arg2}** from memory to use its value in the next operation` : 

            `## ldr — Load from memory\n\n` +
            `Reads a value from memory into a register so it can be used in a computation\n\n` +
            `- **${arg1}** - destination register, will hold the loaded value\n` +
            `- **${arg2}** - base adress register\n` +
            (arg3 ? `- **${arg3}** - offset in bytes from the base adress\n\n` : '') +
            `Reads a value from a memory adress into a register`,
            
    
    str: (arg1, arg2, arg3) =>
        (arg1 === 'wzr' || arg1 === 'xzr') && isVar(arg2) ?
            `## str — Store to memory\n\n` +
            `Stores the value zero into the variable **${arg2}**. The zero register always reads as 0\n\n` +
            `- **${arg1}** — the zero register, always contains the value 0\n` +
            `- **${arg2}** — variable \`${arg2}\`, the destination on the stack` :
        (arg1 === 'wzr' || arg1 === 'xzr') ?
            `## str — Store to memory\n\n` +
            `Stores the value zero to a memory address. The zero register always reads as 0\n\n` +
            `- **${arg1}** — the zero register, always contains the value 0\n` +
            `- **${arg2}** — base address register\n` +
            (arg3 ? `- **${arg3}** — byte offset from the base address` : '') :
        isReg(arg1) && isVar(arg2) ? 
            `## str — Store to memory\n\n` +
            `Stores the value in the register into the variable **${arg2}** on the stack\n\n` + 
            `- **${arg1}** — register holding the value to store\n` +
            `- **${arg2}** — variable \`${arg2}\`, the destination on the stack\n` +
            (arg3 ? `- **${arg3}** — offset in bytes from the stack pointer\n` : '') :

            `## str — Store to memory\n\n` +
            `Stores the value in the register into a memory address\n\n` + 
            `- **${arg1}** — register holding the value to store\n` +
            `- **${arg2}** — base adress register (stack pointer)\n` +
            (arg3?`- **${arg3}** — offset in bytes from the stack pointer\n` :''),

    // ------------ ARITHMETIC ------------ 

    add: (arg1, arg2, arg3) =>
        ((arg1 === 'sp' && isConst(arg3 ?? arg2)) || (isConst(arg1) && (arg2 === '%rsp' || arg2 === '%esp'))) ?
            `## add — Restore Stack Space (Function Teardown)\n\n` +
            `Moves the stack pointer back up to release the space reserved at function entry. This is generated by the compiler — it is the epilogue counterpart to the stack allocation done in the prologue\n\n` +
            `- **${arg2 ?? arg1}** — the stack pointer register, moved upward to release stack space\n` +
            `- **${isConst(arg1) ? arg1 : (arg3 ?? arg2)}** — the number of bytes being released` :
        arg3 && isVar(arg1) && isConst(arg3) ?
            `## add — Add values\n\n` +
            `Adds a constant to a variable\n\n` +
            `- **${arg1}** — variable \`${arg1}\`, the destination being updated\n` +
            `- **${arg2}** — source register\n` +
            `- **${arg3}** — the literal value being added\n\n` +
            `This is the compiler performing **${arg1} += ${val(arg3!)}** in your source code` :
        arg3 && isConst(arg3) ?
            `## add — Add values\n\n` +
            `Adds a constant to a register\n\n` +
            `- **${arg1}** — destination register, will hold the result\n` +
            `- **${arg2}** — source register\n` +
            `- **${arg3}** — the literal value being added` :
        arg3 ?
            `## add — Add values\n\n` +
            `Adds two register values together\n\n` +
            `- **${arg1}** — destination register, will hold the result\n` +
            `- **${arg2}** — first source register\n` +
            `- **${arg3}** — second source register` :
        isConst(arg1) && isVar(arg2) ?
            `## add — Add values\n\n` +
            `Adds a constant to a variable\n\n` +
            `- **${arg1}** — the literal value being added\n` +
            `- **${arg2}** — variable \`${arg2}\`, both the source and destination\n\n` +
            `This is the compiler performing **${arg2} += ${val(arg1!)}** in your source code` :
        isVar(arg2) ?
            `## add — Add values\n\n` +
            `Adds a register value into a variable\n\n` +
            `- **${arg1}** — register holding the value to add\n` +
            `- **${arg2}** — variable \`${arg2}\`, both the source and destination` :

            `## add — Add values\n\n` +
            `Adds a value to a register\n\n` +
            `- **${arg1}** — first value\n` +
            `- **${arg2}** — destination register`,

    sub: (arg1, arg2, arg3) =>
        ((arg1 === 'sp' && isConst(arg3 ?? arg2)) || (isConst(arg1) && (arg2 === '%rsp' || arg2 === '%esp'))) ?
            `## sub — Allocate Stack Space (Function Setup)\n\n` +
            `Moves the stack pointer down to reserve space for all local variables in this function. This is generated by the compiler — it is not part of your source code\n\n` +
            `- **${arg2 ?? arg1}** — the stack pointer register, moved downward to claim stack space\n` +
            `- **${isConst(arg1) ? arg1 : (arg3 ?? arg2)}** — the number of bytes reserved for local variables` :
        arg3 && isVar(arg1) && isConst(arg3) ?
            `## sub — Subtract values\n\n` +
            `Subtracts a constant from a variable\n\n` +
            `- **${arg1}** — variable \`${arg1}\`, the destination being updated\n` +
            `- **${arg2}** — source register\n` +
            `- **${arg3}** — the literal value being subtracted\n\n` +
            `This is the compiler performing **${arg1} -= ${val(arg3!)}** in your source code` :
        arg3 && isConst(arg3) ?
            `## sub — Subtract values\n\n` +
            `Subtracts a constant from a register\n\n` +
            `- **${arg1}** — destination register, will hold the result\n` +
            `- **${arg2}** — source register\n` +
            `- **${arg3}** — the literal value ${val(arg3!)} being subtracted` :
        arg3 ?
            `## sub — Subtract values\n\n` +
            `Subtracts two register values\n\n` +
            `- **${arg1}** — destination register, will hold the result\n` +
            `- **${arg2}** — first source register\n` +
            `- **${arg3}** — second source register` :
        isConst(arg1) && isVar(arg2) ?
            `## sub — Subtract values\n\n` +
            `Subtracts a constant from a variable\n\n` +
            `- **${arg1}** — the literal value being subtracted\n` +
            `- **${arg2}** — variable \`${arg2}\`, both the source and destination\n\n` +
            `This is the compiler performing **${arg2} -= ${val(arg1!)}** in your source code` :
        isVar(arg2) ?
            `## sub — Subtract values\n\n` +
            `Subtracts a register value from a variable\n\n` +
            `- **${arg1}** — register holding the value to subtract\n` +
            `- **${arg2}** — variable \`${arg2}\`, both the source and destination` :

            `## sub — Subtract values\n\n` +
            `Subtracts a value from a register\n\n` +
            `- **${arg1}** — first value\n` +
            `- **${arg2}** — destination register`,

    mul: (arg1, arg2, arg3) =>
        arg3 && isVar(arg1) && isConst(arg3) ?
            `## mul — Multiply values\n\n` +
            `Multiplies a variable by a constant\n\n` +
            `- **${arg1}** — variable \`${arg1}\`, the destination being updated\n` +
            `- **${arg2}** — source register\n` +
            `- **${arg3}** — the literal value being multiplied\n\n` +
            `This is the compiler performing **${arg1} *= ${val(arg3!)}** in your source code` :
        arg3 && isConst(arg3) ?
            `## mul — Multiply values\n\n` +
            `Multiplies a register by a constant\n\n` +
            `- **${arg1}** — destination register, will hold the result\n` +
            `- **${arg2}** — source register\n` +
            `- **${arg3}** — the literal value ${val(arg3!)} being multiplied` :

            `## mul — Multiply values\n\n` +
            `Multiplies two register values together\n\n` +
            `- **${arg1}** — destination register, will hold the result\n` +
            `- **${arg2}** — first source register\n` +
            (arg3 ? `- **${arg3}** — second source register` : ''),

    subs: (arg1, arg2, arg3) =>
        arg3 && isConst(arg3) ?
            `## subs — Subtract and set flags\n\n` +
            `Subtracts a constant from a register and updates the condition flags\n\n` +
            `- **${arg1}** — destination register, will hold the result\n` +
            `- **${arg2}** — source register\n` +
            `- **${arg3}** — the literal value ${val(arg3!)} being subtracted\n\n` +
            `The flags are used by a following branch instruction to decide if a loop or condition continues` :
        arg3 ?
            `## subs — Subtract and set flags\n\n` +
            `Subtracts two registers and updates the condition flags\n\n` +
            `- **${arg1}** — destination register, will hold the result\n` +
            `- **${arg2}** — first source register\n` +
            `- **${arg3}** — second source register\n\n` +
            `The flags are used by a following branch instruction to decide if a loop or condition continues` :

            `## subs — Subtract and set flags\n\n` +
            `Subtracts a value and updates the condition flags\n\n` +
            `- **${arg1}** — first value\n` +
            `- **${arg2}** — destination register`,    
        
    //arm32,arm64 jumps
    b: (arg1) => jump(arg1!),
    beq: (arg1) => jumpEqual(arg1!),
    bne: (arg1) => jumpNotEqual(arg1!),
    bge: (arg1) => jumpGreaterThan(arg1!),
    ble: (arg1) => jumpLesserThan(arg1!),
    blt: (arg1) => jumpLesser(arg1!),
    bgt: (arg1) => jumpGreater(arg1!),

    // arm64 
    'b.eq': (arg1) => jumpEqual(arg1!),
    'b.ne': (arg1) => jumpNotEqual(arg1!),
    'b.ge': (arg1) => jumpGreaterThan(arg1!),
    'b.le': (arg1) => jumpLesserThan(arg1!),
    'b.lt': (arg1) => jumpLesser(arg1!),
    'b.gt': (arg1) => jumpGreater(arg1!),

    //x86-64/86 jumps
    jmp: (arg1)   => jump(arg1!),
    je: (arg1)    => jumpEqual(arg1!),
    jne: (arg1)   => jumpNotEqual(arg1!),
    jge: (arg1)   => jumpGreaterThan(arg1!),
    jle: (arg1)   => jumpLesserThan(arg1!),
    jl: (arg1)    => jumpLesser(arg1!),
    jg: (arg1)    => jumpGreater(arg1!),

    cmp: (arg1, arg2) => 
        isVar(arg1) && isConst(arg2) ?
            `## cmp — Compare\n\n` +
            `Compares a variable against a constant value without storing the result\n` +
            `only the condition flags are updated. The following branch instruction reads those flags to decide whether to jump\n\n` +
            `- **${arg1}** — variable \`${arg1}\`, the value being compared\n` +
            `- **${arg2}** — the constant being compared against` :
        isReg(arg1) && isConst(arg2) ?
            `## cmp — Compare\n\n` +
            `Compares a register against a constant value without storing the result\n` +
            `only the condition flags are updated. The following branch instruction reads those flags to decide whether to jump\n\n` +
            `- **${arg1}** — register holding the value being compared\n` +
            `- **${arg2}** — the constant being compared against` :
        isReg(arg1) && isVar(arg2) ?
            `## cmp — Compare\n\n` +
            `Compares a register against a variable without storing the result\n` +
            `only the condition flags are updated. The following branch instruction reads those flags to decide whether to jump\n\n` +
            `- **${arg1}** — register holding the left hand side\n` +
            `- **${arg2}** — variable \`${arg2}\`, the right hand side` :

            `## cmp — Compare\n\n` +
            `Compares two values without storing the result\n` +
            `only the condition flags are updated. The following branch instruction reads those flags to decide whether to jump\n\n` +
            `- **${arg1}** — first value\n` +
            `- **${arg2}** — second value`,

    //arm32 - arm64

    bic: (arg1, arg2, arg3) =>
        isConst(arg3) ? 
            `## bic — Bit Clear\n\n` +
            `Clears the bits specified by the constant mask in the register\n` +
            `- **${arg1}** — destination register, will hold the result\n` +
            `- **${arg2}** — source register\n` + 
            `- **${arg3}** — the bit mask, bits set here will be cleared in **${arg2}**` :

            `## bic — Bit Clear\n\n` +
            `Clears bits in a register using another register as the mask\n` +
            `- **${arg1}** — destination register, will hold the result\n` +
            `- **${arg2}** — source register\n` + 
            `- **${arg3}** — register whose bits will be cleared from **${arg2}**`,

    bl: (arg1) => 
        `## bl - Branch with Link\n\n` +
        `Calls a function and saves the return adress so execution can return after the call\n\n` +
        `- **${arg1}** — the name of the function beign called\n`,

    //arm64
    
    stp: (arg1,arg2,arg3,arg4) =>
        (arg1 === 'x29' && arg2 === 'x30') ?
            `## stp — Save Frame Pointer and Link Register (Function Setup)\n\n` +
            `Saves the frame pointer (x29) and link register (x30) onto the stack. This is the first instruction of a function's prologue — x29 records where this stack frame starts, and x30 holds the return address so the function can return to its caller\n\n` +
            `- **x29** — frame pointer, points to the base of the current stack frame\n` +
            `- **x30** — link register, holds the return address (where to jump when the function returns)\n` +
            `- **${arg3}** — base address register\n` +
            (arg4 ? `- **${arg4}** — byte offset from the base address` : '') :

            `## stp — Store Pair\n\n` +
            `Store two registers to memory in a single instruction\n\n` +
            `- **${arg1}** — first register being stored\n` +
            `- **${arg2}** — second register being stored\n` +
            `- **${arg3}** — base adress register\n` +
            `- **${arg4}** — byte offset from the base adress`,

    adrp: (arg1, arg2) => 
        `## adrp - Adress of Page\n\n` +
        `Loads the page address of a symbol into a register\n `+
        `Used together with a following offset instruction to form the full address of global data\n` +
        `- **${arg1}** — destination register, will hold the page adress\n` +
        `- **${arg2}** — the symbol whose page adress is being loaded\n`,
    
    fmov: (arg1,arg2) =>
        isConst(arg2) ? 
            `## fmov - Float Move\n\n` +
            `Loads a float constant directly into a float register\n\n`+
            `- **${arg1}** — destination float register\n` +
            `- **${arg2}** — the float constant being loaded\n` :
        isVar(arg2) ? 
            `## fmov - Float Move\n\n` +
            `Loads a variable into a float register to use it in a float computation\n\n`+
            `- **${arg1}** — destination float register\n` +
            `- **${arg2}** — variable \`${arg1}\`, being loaded from the stack\n` :

            `## fmov - Float Move\n\n` +
            `Copies a value between float registers\n\n`+
            `- **${arg1}** — source float register\n` +
            `- **${arg2}** — destination float register\n`,

    stur: (arg1, arg2, arg3) =>
        (arg1 === 'wzr' || arg1 === 'xzr') && isVar(arg2) ?
            `## stur — Store Unscaled Register\n\n` +
            `Stores zero into a variable on the stack. The zero register always reads as 0\n\n` +
            `- **${arg1}** — the zero register, always contains the value 0\n` +
            `- **${arg2}** — variable \`${arg2}\`, the destination on the stack` :
        isVar(arg2) ?
            `## stur — Store Unscaled Register\n\n` +
            `Stores a register value into a variable on the stack\n\n` +
            `- **${arg1}** — register holding the value to store\n` +
            `- **${arg2}** — variable \`${arg2}\`, the destination on the stack` :

            `## stur — Store Unscaled Register\n\n` +
            `Stores a register value to a raw memory address\n\n` +
            `- **${arg1}** — register holding the value to store\n` +
            `- **${arg2}** — base address register\n` +
            (arg3 ? `- **${arg3}** — byte offset from the base address` : ''),

    ldur: (arg1, arg2, arg3) =>
        isVar(arg2) ?
            `## ldur — Load Unscaled Register\n\n` +
            `Loads a variable from the stack into a register to use it in a computation\n\n` +
            `- **${arg1}** — destination register, whill hold the loaded value\n` +
            `- **${arg2}** — variable \`${arg2}\`, read from the stack` :

            `## ldur — Load Unscaled Register\n\n` +
            `Loads a value from a raw memory adress into a register\n\n` +
            `- **${arg1}** — destination register, will hold the loaded value\n` +
            `- **${arg2}** — base adress register\n` +
            (arg3 ? `- **${arg3}** — byte offset from the base adress` : ''),

    ldp: (arg1, arg2, arg3, arg4) =>
        (arg1 === 'x29' && arg2 === 'x30') ?
            `## ldp — Restore Frame Pointer and Link Register (Function Teardown)\n\n` +
            `Restores the frame pointer (x29) and link register (x30) from the stack. This is part of the function's epilogue — it undoes the saves done at function entry and prepares the CPU to return to the caller\n\n` +
            `- **x29** — frame pointer, restored to the caller's stack frame base\n` +
            `- **x30** — link register, restored so the function can return to its caller\n` +
            `- **${arg3}** — base address register\n` +
            (arg4 ? `- **${arg4}** — byte offset from the base address` : '') :
        arg4 ?
            `## ldp — Load Pair\n\n` +
            `Loads two values from memory at a base address plus offset into two registers\n\n` +
            `- **${arg1}** — first destination register\n` +
            `- **${arg2}** — second destination register\n` +
            `- **${arg3}** — base address register\n` +
            `- **${arg4}** — byte offset from the base address` :

            `## ldp — Load Pair\n\n` +
            `Loads two values from a base address into two registers\n\n` +
            `- **${arg1}** — first destination register\n` +
            `- **${arg2}** — second destination register\n` +
            `- **${arg3}** — base address register`,

    lsl: (arg1, arg2, arg3) =>
        `## lsl — Logical Shift Left\n\n` +
        `Shifts the bits of a register left by a constant amount, equivalent to multiplying by a power of 2\n\n` +
        `- **${arg1}** — destination register, will hold the result\n` +
        `- **${arg2}** — source register being shifted\n` +
        `- **${arg3}** — number of bit positions to shift left`,

    lsr: (arg1, arg2, arg3) =>
        `## lsr — Logical Shift Right\n\n` +
        `Shifts the bits of a register right by a constant amount, equivalent to dividing by a power of 2\n\n` +
        `- **${arg1}** — destination register, will hold the result\n` +
        `- **${arg2}** — source register being shifted\n` +
        `- **${arg3}** — number of bit positions to shift right`,

    sdiv: (arg1, arg2, arg3) =>
        `## sdiv — Signed Divide\n\n` +
        `Divides two registers using signed integer division and stores the result\n\n` +
        `- **${arg1}** — destination register, will hold the result\n` +
        `- **${arg2}** — dividend register (the value being divided)\n` +
        `- **${arg3}** — divisor register (the value to divide by)`,

    //x86-64, 86

    push: (arg1) =>
        (arg1 === '%rbp' || arg1 === '%ebp') ?
            `## push — Save Frame Pointer (Function Setup)\n\n` +
            `Saves the caller's frame pointer onto the stack. This is the first instruction of a function's prologue — it preserves where the caller's stack frame starts so it can be restored when this function returns\n\n` +
            `- **${arg1}** — the frame pointer register being saved` :

            `## push - Push to stack\n\n` +
            `Store a register onto the stack, decreasing the stack pointer\n\n` +
            `- **${arg1}** — the register being stored onto the stack\n`,

    pop: (arg1) =>
        (arg1 === '%rbp' || arg1 === '%ebp') ?
            `## pop — Restore Frame Pointer (Function Teardown)\n\n` +
            `Restores the caller's frame pointer from the stack. This is part of the function's epilogue — it undoes the frame pointer save done at function entry and returns the stack to the caller's state\n\n` +
            `- **${arg1}** — the frame pointer register being restored` :

            `## pop - Pop from stack\n\n` +
            `Restores a register from the stack, increasing the stack pointer\n\n` +
            `- **${arg1}** — the register being restored from the stack\n`,

    call: (arg1) =>
        `## call - Call Function\n\n` +
        `Calls a function and saves the return adress on the stack\n\n` +
        `- **${arg1}** — the name of the function being called\n`,

    idiv: (arg1) =>
        `## idiv - Signed Integer Divide\n\n` +
        `Divides the accumulator register by the given reigster or value\n\n` +
        `- **${arg1}** — the value to divide by\n`,

    xor: (arg1, arg2) =>
        `## xor — Bitwise Exclusive OR\n\n` +
        `Performs a bitwise XOR between two values and stores the result. When both operands are the same register, this is the fastest way to set a register to zero\n\n` +
        `- **${arg1}** — destination register, will hold the result\n` +
        `- **${arg2}** — second operand`,

    inc: (arg1) =>
        `## inc — Increment\n\n` +
        `Adds 1 to a register or memory location\n\n` +
        `- **${arg1}** — the register being incremented`,

    leal: (arg1, arg2) =>
        isVar(arg1) ?
            `## leal — Load Effective Address\n\n` +
            `Loads the memory address of a variable into a register\n\n` +
            `- **${arg1}** — variable \`${arg1}\`, whose address is being loaded\n` +
            `- **${arg2}** — destination register, will hold the address` :

            `## leal — Load Effective Address\n\n` +
            `Computes a memory address and stores it in a register\n\n` +
            `- **${arg1}** — the memory expression being evaluated\n` +
            `- **${arg2}** — destination register, will hold the computed address`,

    shll: (arg1, arg2) =>
        `## shll — Shift Left\n\n` +
        `Shifts the bits of a register left by a constant amount, equivalent to multiplying by a power of 2\n\n` +
        `- **${arg1}** — register being shifted\n` +
        (arg2 ? `- **${arg2}** — number of bit positions to shift left` : `- shifts left by 1`),

    ldm: (arg1, arg2) =>
        `## ldm — Load Multiple\n\n` +
        `Loads multiple registers from consecutive memory locations in one instruction\n\n` +
        `- **${arg1}** — base address register to load from\n` +
        `- **${arg2}** — the list of registers being loaded`,

    ldrlt: (arg1, arg2) =>
        isVar(arg2) ?
            `## ldrlt — Load if Less Than\n\n` +
            `Loads a variable from memory only if the last comparison was <\n\n` +
            `- **${arg1}** — destination register\n` +
            `- **${arg2}** — variable \`${arg2}\`, read from the stack` :

            `## ldrlt — Load if Less Than\n\n` +
            `Loads from memory only if the last comparison was <\n\n` +
            `- **${arg1}** — destination register\n` +
            `- **${arg2}** — source memory address`,

    movlt: (arg1, arg2) =>
        isConst(arg2) ?
            `## movlt — Move if Less Than\n\n` +
            `Moves a constant into a register only if the last comparison was <\n\n` +
            `- **${arg1}** — destination register\n` +
            `- **${arg2}** — the constant value being moved` :

            `## movlt — Move if Less Than\n\n` +
            `Moves a value into a register only if the last comparison was <\n\n` +
            `- **${arg1}** — destination register\n` +
            `- **${arg2}** — source register`,

    cbz: (arg1, arg2) =>
        `## cbz — Compare and Branch if Zero\n\n` +
        `Jumps to the label if the register is zero, without needing a separate cmp instruction\n\n` +
        `- **${arg1}** — register being tested\n` +
        `- **${arg2}** — the label to jump to if ${arg1} == 0`,

    cbnz: (arg1, arg2) =>
        `## cbnz — Compare and Branch if Not Zero\n\n` +
        `Jumps to the label if the register is not zero, without needing a separate cmp instruction\n\n` +
        `- **${arg1}** — register being tested\n` +
        `- **${arg2}** — the label to jump to if ${arg1} != 0`,

    bls: (arg1) =>
        `## bls — Branch if Lower or Same\n\n` +
        `Jumps to the label if the last unsigned comparison was <=\n\n` +
        `- **${arg1}** — the label to jump to`,

    bhi: (arg1) =>
        `## bhi — Branch if Higher\n\n` +
        `Jumps to the label if the last unsigned comparison was >\n\n` +
        `- **${arg1}** — the label to jump to`,

    leaq: (arg1, arg2) =>
        arg1?.includes('(%rip)') ?
            `## leaq - Load Effective Address\n\n` +
            `Loads the address of a string literal or global constant into a register using RIP-relative addressing\n\n` +
            `- **${arg1}** — RIP-relative address of the string or global data in memory\n` +
            `- **${arg2}** — destination register, will hold the address\n` :
        isVar(arg1) ?
            `## leaq - Load Effective Adress\n\n` +
            `Loads the memory adress of a variable into a register\n\n` +
            `- **${arg1}** — variable \`${arg1}\`, whose address is being loaded\n` +
            `- **${arg2}** — destination register, will hold the adress\n` :

            `## leaq - Load Effective Adress\n\n` +
            `Computes a memory adress and stores it in a register\n\n` +
            `- **${arg1}** — the memory expression being evaluated\n` +
            `- **${arg2}** — destination register, will hold the computed adress\n`,

    movsd: (arg1, arg2) =>
        isVar(arg1) && isReg(arg2) ?
            `## movsd - Move Scalar Double\n\n` +
            `Loads a variable from the stack into a float register to use it in a computation\n\n` +
            `- **${arg1}** — variable \`${arg1}\`, read from the stack\n` +
            `- **${arg2}** — destination float register\n` :
        isReg(arg1) && isVar(arg2) ?
            `## movsd - Move Scalar Double\n\n` +
            `Stores a float register value into a variable on the stack\n\n` +
            `- **${arg1}** — source float register\n` +
            `- **${arg2}** — variable \`${arg2}\`, the destination on the stack\n` :

            `## movsd - Move Scalar Double\n\n` +
            `Copies a value between float registers\n\n` +
            `- **${arg1}** — source float register\n` +
            `- **${arg2}** — destination float register\n`,

    movsq: () =>
        `## movsq — Move String Quadword\n\n` +
        `Copies 8 bytes from a source memory address to a destination memory address, then advances both pointers to the next position\n\n` +
        `Used for bulk memory copy operations such as copying arrays or strings`,






}

const jump = (arg1: string) =>`## Jump\n\n` + `Jumps uncoditionally to **${arg1}**\n\n` +
    `Used to loop back to the start od a loop or skip an if-block`
const jumpEqual = (arg1: string) => `## Jump if equal\n\n` + `Jumps to **${arg1}** if the last comparison was ==\n\n` +
    `Used in condtions like if(a==b) or while (i==0)`
const jumpNotEqual = (arg1: string) => `## Jump if not equal\n\n` + `Jumps to **${arg1}** if the last comparison was !=\n\n` +
    `Used in condtions like if(a!=b) or while (i!=0)`
const jumpGreaterThan = (arg1: string) => `## Jump if greater or equal\n\n` + `Jumps to **${arg1}** if the last comparison was >=\n\n` +
    `Used in condtions like if(a>=b) or while (i>=0)`
const jumpLesserThan = (arg1: string) => `## Jump if less or equal\n\n` + `Jumps to **${arg1}** if the last comparison was <=\n\n` +
    `Used in condtions like if(a==b) or while (i==0)`
const jumpLesser = (arg1: string) => `## Jump if less than\n\n` + `Jumps to **${arg1}** if the last comparison was <\n\n` +
    `Used in condtions like if(a<b) or while (i<0)`
const jumpGreater = (arg1: string) => `## Jump if greater than\n\n` + `Jumps to **${arg1}**  if the last comparison was >\n\n` +
    `Used in condtions like if(a>b) or while (i>0)`