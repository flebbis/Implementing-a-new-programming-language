package com.example.minilang.codegen;

import java.util.HashSet;

import com.example.minilang.TypeConverter;
import com.example.minilang.ast.Ast;
import static com.example.minilang.codegen.RegisterGenerator.generateRegister;


public class FunctionCodeGen {
    // This class will contain the logic to convert our AST into LLVM IR code.
    // It will likely have methods like `generateFunction`, `generateStatement`, and `generateExpression`.
    private StringBuilder sb;
    private HashSet<String> declaredVariables;
    private Environment environment;
    private StringBuilder globals;
    private StringBuilder globalStrings;
    private HashSet<String> functionVariables;
    private StatementCodeGen stmtGen;

    public FunctionCodeGen(StringBuilder sb, Environment environment, StringBuilder globals, StringBuilder globalStrings, HashSet<String> functionVariables, StatementCodeGen stmtGen) {

        this.sb = sb;
        this.environment = environment;
        this.globals = globals;
        this.globalStrings = globalStrings;
        this.functionVariables = functionVariables;
        this.stmtGen = stmtGen;
    }

    public void generateFunction(Ast.Func function) {

        environment.pushNewScope();
        Helper helper = new Helper();

        sb.append("define " + helper.convertType(function.returnType()) + " @" + function.name() + "(");

        for (int i = 0; i < function.params().size(); i++) {
            Ast.Arg arg = function.params().get(i);
            sb.append(helper.convertType(arg.type()))
                    .append(" %")
                    .append(arg.name());

            if (i < function.params().size() - 1) {
                sb.append(", ");
            }
        }

        sb.append(") {\nentry:\n");

        // For each parameter, we need to create a local copy in the function's stack frame.
        for (Ast.Arg arg : function.params()) {
            String llvmType = helper.convertType(arg.type());
            String localPtr = "%" + arg.name() + ".addr";

            if (arg.type() instanceof Ast.TArray arrType) {
                localPtr = copyArrayParameter(arg, arrType, llvmType, localPtr);

            } else {
                copyNonArrayParameter(arg, localPtr, llvmType);
            }

            environment.pushToCurrentScope(arg.name(), localPtr);
        }

        stmtGen.generateStatement(function.body());
        if(function.returnType() instanceof Ast.TUnknown) {
            sb.append("  ret void\n");
        }
        sb.append("\n}\n");
        environment.popScope();
    }

    private void copyNonArrayParameter(Ast.Arg arg, String localPtr, String llvmType) {
        sb.append(localPtr)
                .append(" = alloca ")
                .append(llvmType)
                .append("\n");

        sb.append("store ")
                .append(llvmType)
                .append(" %")
                .append(arg.name())
                .append(", ")
                .append(llvmType)
                .append("* ")
                .append(localPtr)
                .append("\n");
    }

    private String copyArrayParameter(Ast.Arg arg, Ast.TArray arrType, String llvmType, String localPtr) {
        String copiedArray = generateRegister();
        String copyFunc = getArrayCopyFunctionName(arrType);

        // call runtime array copy function
        sb.append(copiedArray)
                .append(" = call ")
                .append(llvmType)
                .append(" ")
                .append(copyFunc)
                .append("(")
                .append(llvmType)
                .append(" %")
                .append(arg.name())
                .append(")\n");

        // allocate local space for the copied array and store the result
        sb.append(localPtr)
                .append(" = alloca ")
                .append(llvmType)
                .append("\n");

        // store the copied array into the local variable
        sb.append("store ")
                .append(llvmType)
                .append(" ")
                .append(copiedArray)
                .append(", ")
                .append(llvmType)
                .append("* ")
                .append(localPtr)
                .append("\n");

        return localPtr;
    }

    private String getArrayCopyFunctionName(Ast.TArray arrayType) {
        return switch (arrayType.elementType()) {
            case Ast.TInt() -> "@array_int_copy";
            case Ast.TDouble() -> "@array_double_copy";
            case Ast.TBool() -> "@array_bool_copy";
            case Ast.TString() -> "@array_string_copy";
            default -> throw new IllegalArgumentException(
                    "Unsupported array element type for array copy: " +
                            TypeConverter.typeToString(arrayType.elementType()));
        };
    }
}
