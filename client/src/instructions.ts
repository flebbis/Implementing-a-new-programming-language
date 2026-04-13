type arguments = (arg1?: string, arg2?: string, arg3?: string, arg4?: string) => string;
// registers %eax, x0, w8 etc
const isReg   = (a?: string) => !!a && (a.startsWith('%') || /^[xwbsdqv]\d+$/.test(a) || /^r\d+$/.test(a) || ['wzr','xzr','sp','lr','fp'].includes(a));
// constants $5 (x86), #5 (arm/arm64), 5 (plain)
const isConst = (a?: string) => !!a && (a.startsWith('$') || a.startsWith('#') || /^\d+$/.test(a));
// variable x, i, counter
const isVar   = (a?: string) => !!a && !isReg(a) && !isConst(a) && !a.startsWith('_') && !a.startsWith('L') && !a.startsWith('[');
// strip $ or # prefix to get the raw value
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
        arg3 && isVar(arg1) && isConst(arg3) ? `${arg1} += ${val(arg3!)}` :
        arg3 && isConst(arg3)                ? `Add ${val(arg3!)} to register` :
        arg3                                 ? `Add register to register` :
        isVar(arg2) && isConst(arg1)         ? `${arg2} += ${val(arg1!)}` :
        isVar(arg2)                          ? `Add register to ${arg2}` :
        `Add to register`,
    
    // arm/arm64 sub x, x0, #1     
    // arm/arm64 sub x0, x1, #1    
    // arm/arm64 sub x0, x1, x2    
    // x86/64 subl $1, -20(%rsp)   
    // x86/64 subl %eax, -20(%rsp) 
    sub: (arg1, arg2, arg3) => 
        arg3 && isVar(arg1) && isConst(arg3) ? `${arg1} -= ${val(arg3!)}` :
        arg3 && isConst(arg3)                ? `Subtract ${val(arg3!)} from register` :
        arg3                                 ? `Subtract register from register` :
        isVar(arg2) && isConst(arg1)         ? `${arg2} -= ${val(arg1!)}` :
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

    // ----- JUMPS ----
    'b.eq': (arg1)           => `Jump if equal == to ${arg1}`,
    'b.ne': (arg1)           => `Jump if not equal != ${arg1}`,
    'b.ge': (arg1)           => `Jump if greater equal than >= ${arg1}`,
    'b.le': (arg1)           => `Jump if lesser equal than <= ${arg1}`,
    'b.lt': (arg1)           => `Jump if lesser than < ${arg1}`,
    'b.gt': (arg1)           => `Jump if greater than > ${arg1}`,

    // arm32
    // bic sp, sp, #7  
    bic: (_arg1, _arg2, arg3) =>
        isConst(arg3) ? `Clear bits ${val(arg3!)} in register` :
        `Bitwise AND NOT`,

    bge: (arg1) => `Jump if >= to ${arg1}`,
    bgt: (arg1) => `Jump if > to ${arg1}`,
    ble: (arg1) => `Jump if <= to ${arg1}`,
    blt: (arg1) => `Jump if < to ${arg1}`,
    beq: (arg1) => `Jump if == to ${arg1}`,
    bne: (arg1) => `Jump if != to ${arg1}`,

    cmp: (arg1, arg2)        => `Compare ${arg1} and ${arg2}`,
    b:   (arg1)              => `Jump to ${arg1}`,
    bl:  (arg1)              => `Call ${arg1}`,
    ret: ()                  => `Return`,
    push: (arg1)             => `Push ${arg1} onto stack`,
    pop: (arg1)              => `Pop from stack into ${arg1}`,
    stp: (arg1, arg2, arg3, arg4) => `Save ${arg1} and ${arg2} to stack at (${arg3} + ${arg4})`,

    // --- arm64 ---

    // adrp x8, LCPI0_0@PAGE 
    adrp: (_arg1, _arg2) => `Load page address into register`,

    // fmov d0, #2.0e+0  
    // fmov d0, x0   
    fmov: (_arg1, arg2) =>
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

    ldp:  (arg1, arg2, arg3, arg4) => arg4
        ? `Load offset ${arg4} into reg ${arg1} and reg ${arg2}`
        : `Load reg ${arg3} into reg ${arg1} and reg ${arg2}`,

    lsl:  (arg1, arg2, arg3) =>
        `Shift reg ${arg2} left by ${arg3}, store in reg ${arg1}`,

    lsr:  (arg1, arg2, arg3) =>
        `Shift reg ${arg2} right by ${arg3}, store in reg ${arg1}`,

    sdiv: () => `Divide registers (signed)`,

    // ---- x86, x86-64------

    // leaq -4(%rsp), %rax  
    leaq: (arg1, _arg2) =>
        isVar(arg1) ? `Load address of ${arg1}` :
        `Load effective address`,

    // movsd %xmm0, -8(%rbp)   
    // movsd -8(%rbp), %xmm0  
    movsd: (arg1, arg2) =>
        isVar(arg1) && isReg(arg2) ? `Load ${arg1} into register` :
        isReg(arg1) && isVar(arg2) ? `${arg2} = register` :
        `Copy float register`,

    jmp:   (arg1)            => `Jump to ${arg1}`,
    call:  (arg1)            => `Call ${arg1}`,
    je: (arg1)               => `Jump if equal == to ${arg1}`,
    jne: (arg1)              => `Jump if not equal != ${arg1}`,
    jge: (arg1)              => `Jump if greater equal than >= ${arg1}`,
    jle: (arg1)              => `Jump if lesser equal than <= ${arg1}`,
    jl: (arg1)               => `Jump if lesser than < ${arg1}`,
    jg: (arg1)               => `Jump if greater than > ${arg1}`,
    idiv: (arg1)             => `Divide by ${arg1}`,
}

// --------------- EXTENDED INSTRUCTIONS ---------------

export const extendedInstructions: Record<string, arguments> = {

    str: (arg1, arg2, arg3) =>
  `## str — Store to memory\n\n` +
  `Writes the value in \`${arg1}\` into memory.\n\n` +
  `- **${arg1}** — register holding the value to store\n` +
  `- **${arg2}** — base address register (stack pointer)\n` +
  (arg3 ? `- **${arg3}** — offset in bytes from the stack pointer\n\n` : `\n`) +
  (isVar(arg2) ? `This saves a computed result back into **${arg2}** in your source code.` : '')
}