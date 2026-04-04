package com.example.minilang.codegen;

import com.example.minilang.TypeConverter;
import com.example.minilang.ast.Ast;
import com.example.minilang.ast.Ast.*;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.example.minilang.codegen.LabelGenerator.generateLabel;
import static com.example.minilang.codegen.RegisterGenerator.generateRegister;
import com.example.minilang.codegen.ArrayValue;

public class ExpressionCodeGen extends Helper {
    private static Map<String, String> stringRegisterToGlobal = new HashMap<>();
    private static Map<String, Integer> stringGlobalToLength = new HashMap<>();
    private static Map<String, ArrayValue> arrayNameToValue = new HashMap<>();
    private StringBuilder sb;
    private StringBuilder globals;
    private StringBuilder globalStrings;
    private int stringCounter = 0;
    private HashSet<String> functionVariables;
    private Environment environment;
    public ExpressionCodeGen(StringBuilder sb, StringBuilder globals, StringBuilder globalStrings, HashSet<String> functionVariables, Environment environment) {
        this.sb = sb;
        this.globals = globals;
        this.globalStrings = globalStrings;
        this.functionVariables = functionVariables;
        this.environment = environment;
    }



    public String generateExpression(Exp exp) {
            if (exp instanceof EInt intExp) return generateInt(intExp);
            if (exp instanceof EDouble doubleExp) return generateDouble(doubleExp);
            if (exp instanceof EString stringExp) return generateString(stringExp);
            if (exp instanceof EBool boolExp) return generateBool(boolExp);
            if (exp instanceof EId idExp) return generateId(idExp);
            if (exp instanceof ECall callExp) return generateCall(callExp);
            if (exp instanceof ENot notExp) return generateNot(notExp);
            if (exp instanceof EPower powerExp) return generatePower(powerExp);
            if (exp instanceof EOpp oppExp) return generateOpp(oppExp);
            if (exp instanceof ECmp cmpExp) return generateCmp(cmpExp);
            if (exp instanceof ELogic logicExp) return generateLogic(logicExp);
            if (exp instanceof EAss assExp) return generateAss(assExp);
            if (exp instanceof EArray arrayExp) return generateArray(arrayExp);
            if (exp instanceof EArrayIndexAssign arrayIndexAssignExp) return generateArrayIndexAssign(arrayIndexAssignExp);
            if (exp instanceof EArrayIndex arrayIndexExp) return generateArrayIndex(arrayIndexExp);
            if (exp instanceof EUnary unaryExp) return generateUnary(unaryExp);
            if (exp instanceof EDInt dIntExp) return generateDInt(dIntExp);
            if (exp instanceof EStringCast sStringCast) return generateStringCast(sStringCast);
            if (exp instanceof EAppend appendExp) return generateAppend(appendExp);
            return "0";
    }

    private String generateInt(EInt intExp) {
        return String.valueOf(intExp.value());
    }
    private String generateDouble(EDouble doubleExp) {
        return String.valueOf(doubleExp.value());
    }
    private String generateString(EString stringExp) {
        String rawString = stringExp.value();
        rawString = rawString.substring(1, rawString.length() - 1);

        // Handle escape sequences in the raw string (e.g., convert "\\n" to an actual newline character, etc.)
        rawString = handleEscapeSequences(rawString);

        // Convert the raw string to bytes to handle non-printable characters and escape sequences correctly
        byte[] bytes = rawString.getBytes(StandardCharsets.UTF_8);
        String value = generatePrintableString(bytes);


        int length = bytes.length + 1; // +1 for null terminator
        String globalName = "@.str." + stringCounter++;
        String register = generateRegister();
        globalStrings.append(globalName).append(" = private constant [").append(length).append(" x i8] c\"").append(value).append("\\00\"\n");
        sb.append(register).append(" = getelementptr inbounds [").append(length).append(" x i8], [").append(length).append(" x i8]* ").append(globalName).append(", i32 0, i32 0\n");
        stringRegisterToGlobal.put(register, globalName);
        stringGlobalToLength.put(globalName, length);
        return register;
    }

    /**
     * This method takes a byte array and generates a string representation that can be used in LLVM IR string constants.
     * It converts non-printable characters to their hexadecimal character codes, å = \C3\A5 for instance
     */
    private String generatePrintableString(byte[] bytes) {
        StringBuilder unsignedBytesString = new StringBuilder();
        for(int i = 0; i < bytes.length; i++) {
            int ub = bytes[i] & 0xFF; // Convert to unsigned (-128 will become 128, -1 will become 255, etc.)
            if (ub >= 32 && ub <= 126 && ub != '\\' && ub != '"') {
                unsignedBytesString.append((char) ub); // Normal printable character, append as is
            } else {
                unsignedBytesString.append(String.format("\\%02X", ub)); // Escape non-printable characters and backslashes/double quotes
            }
        }
        return unsignedBytesString.toString();
    }

    /**
     * This method takes a raw string (with escape sequences) and converts it into a format suitable for LLVM IR string constants.
     * It handles common escape sequences like \n, \t, \", and \\.
     */
    private String handleEscapeSequences(String rawString) {
        return rawString
                .replace("\\n", "\n")
                .replace("\\t", "\t")
                .replace("\\r", "\r")
                .replace("\\b", "\b")
                .replace("\\f", "\f")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
    }

    private String generateBool(EBool boolExp) {
        return String.valueOf(boolExp.value());
    }

    private String generateId(EId idExp) {
        if (!functionVariables.contains(idExp.name())){
            if(idExp.type() instanceof Ast.TArray) {
                return environment.lookup(idExp.name());
            }

            String variableRegister = environment.lookup(idExp.name());
            String register = generateRegister();
            sb.append(register).append(" = load ").append(convertType(idExp.type())).append(", ").append(convertType(idExp.type())).append("* ").append(variableRegister).append("\n");
            return register;

        } else {
            // If we are not currently in the base scope,
            // we need to check if the variable exists in the current scope and load it if it does.
            if(!environment.isInBaseScope()) {
                // Check if the variable exists in the current scope
                if(environment.existsInCurrentScope(idExp.name())) {
                    // If it does, we need to load it from memory
                    String variableRegister = environment.lookup(idExp.name());
                    String register = generateRegister();
                    sb.append(register).append(" = load ").append(convertType(idExp.type())).append(", ").append(convertType(idExp.type())).append("* ").append(variableRegister).append("\n");
                    return register;
                }
            }
            return "%" + idExp.name();
        }

    }
    private String generateCall(ECall callExp) {

        if (callExp.name().equals("print")){
            String register = generatePrintCall(callExp);
            if (register != null) return register;
        } else {
            if(callExp.type() instanceof Ast.TUnknown) {
                // Should not return anything, so we can just call the function without storing the result in a register
                sb.append("call void @").append(callExp.name()).append("(");
                for (int i = 0; i < callExp.args().size(); i++) {
                    Exp arg = callExp.args().get(i);
                    String argValue = generateExpression(arg);
                    sb.append(convertType(arg.type())).append(" ").append(argValue);
                    if (i < callExp.args().size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(")\n");
                return "";
            }

            String register = generateRegister();

            sb.append(register).append(" = call ").append(convertType(callExp.type())).append(" @").append(callExp.name()).append("(");
            for (int i = 0; i < callExp.args().size(); i++) {
                Exp arg = callExp.args().get(i);
                String argValue = generateExpression(arg);
                sb.append(convertType(arg.type())).append(" ").append(argValue);
                if (i < callExp.args().size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")\n");


            return register;
        }
        return "Something went wrong with function call generation";

    }

    private String generatePrintCall(ECall callExp) {
        Exp arg = callExp.args().get(0);
        System.out.println(callExp);
        String value = generateExpression(arg); // since wrapped in EStringCast, this will give us the correct string representation of the value to print
		String register = generateRegister();
        
        sb.append(register).append(" = call i32 @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.fmt.string, i32 0, i32 0), i8* ").append(value).append(")\n");
        return register;
    }

    private String generateNot(ENot notExp) {
        String value = generateExpression(notExp.exp());
        String register = generateRegister();
        sb.append(register).append(" = xor i1 ").append(value).append(", 1\n");
        return register;

    }
    private String generatePower(EPower powerExp) {
        String base = generateExpression(powerExp.base());
        String exponent = generateExpression(powerExp.exponent());
        String register = generateRegister();
        sb.append(register).append(" = call ").append(convertType(powerExp.type())).append(" @pow(").append(convertType(powerExp.base().type())).append(" ").append(base).append(", ").append(convertType(powerExp.exponent().type())).append(" ").append(exponent).append(")\n");
        return register;

    }
    private String generateOpp(EOpp oppExp) {
        String left = generateExpression(oppExp.left());
        String right = generateExpression(oppExp.right());
        String register = generateRegister();
        Ast.Type type = oppExp.type();
        switch (oppExp.op()) {
            case ADD -> {handleEAdd(left,right,register, oppExp.type());}
            case SUB -> {
                generateOppInstruction(type, "sub", left, right, register);
            }
            case MUL -> {
                generateOppInstruction(type, "mul", left, right, register);
            }
            case DIV -> {
                if(type instanceof Ast.TDouble) {
                    generateOppInstruction(type, "div", left, right, register);
                } else {
                    generateOppInstruction(type, "sdiv", left, right, register);
                }
            }
            case MOD -> {
                sb.append(register).append(" = srem ").append(convertType(oppExp.type())).append(" ").append(left).append(", ").append(right).append("\n");
            }
        }
        return register;

    }

    private String handleEAdd(String left, String right, String register, Type type) {
        if(type instanceof Ast.TString) {
            sb.append("  ").append(register)
                    .append(" = call i8* @string_concat(i8* ")
                    .append(left)
                    .append(", i8* ")
                    .append(right)
                    .append(")\n");
        } else {
            generateOppInstruction(type, "add", left, right, register);
        }
        return register;
    }

    private void generateOppInstruction(Ast.Type type, String baseInstruction, String left, String right, String register) {
        if(type instanceof Ast.TInt) {
            sb.append(register).append(" = ").append(baseInstruction).append(" ").append(convertType(type)).append(" ").append(left).append(", ").append(right).append("\n");
        } else if(type instanceof Ast.TDouble) {
            sb.append(register).append(" = f").append(baseInstruction).append(" ").append(convertType(type)).append(" ").append(left).append(", ").append(right).append("\n");
        }
    }

    private String generateCmp(ECmp cmpExp) {
        String left = generateExpression(cmpExp.left());
        String right = generateExpression(cmpExp.right());
        String register = generateRegister();
        switch (cmpExp.op()) {
            case LT -> {sb.append(register).append(" = icmp slt ").append(convertType(cmpExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}
            case GT -> {sb.append(register).append(" = icmp sgt ").append(convertType(cmpExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}
            case LE -> {sb.append(register).append(" = icmp sle ").append(convertType(cmpExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}
            case GE -> {sb.append(register).append(" = icmp sge ").append(convertType(cmpExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}
            case EQ -> {sb.append(register).append(" = icmp eq ").append(convertType(cmpExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}
            case NE -> {sb.append(register).append(" = icmp ne ").append(convertType(cmpExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}
        }
        return register;

    }
    private String generateLogic(ELogic logicExp) {
        String left = generateExpression(logicExp.left());
        String right = generateExpression(logicExp.right());
        String register = generateRegister();
        switch (logicExp.op()) {
            case AND -> {sb.append(register).append(" = and ").append(convertType(logicExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}
            case OR -> {sb.append(register).append(" = or ").append(convertType(logicExp.left().type())).append(" ").append(left).append(", ").append(right).append("\n");}    
        }
        return register;

    }
    private String generateAss(EAss assExp) {
        String value = generateExpression(assExp.value());
        if (assExp.op() == AssOp.ASSIGN) {
        sb.append("store ").append(convertType(assExp.value().type())).append(" ").append(value).append(", ").append(convertType(assExp.value().type())).append("* ").append(environment.lookup(assExp.name())).append("\n");
        return value; }
            
        String operation = switch (assExp.op()) {
            case PLUS_ASSIGN -> "add";
            case MINUS_ASSIGN -> "sub";
            case MULT_ASSIGN -> "mul";
            case DIV_ASSIGN -> "sdiv";
            default -> "default assignment value";
        };
        String register = generateRegister();
        sb.append(register).append(" = load ").append(convertType(assExp.value().type())).append(", ").append(convertType(assExp.value().type())).append("* ").append(environment.lookup(assExp.name())).append("\n");
        String returnRegister = generateRegister();
        sb.append(returnRegister).append(" = ").append(operation).append(" ").append(convertType(assExp.value().type())).append(" ").append(register).append(", ").append(value).append("\n");
        sb.append("store ").append(convertType(assExp.value().type())).append(" ").append(returnRegister).append(", ").append(convertType(assExp.value().type())).append("* ").append(environment.lookup(assExp.name())).append("\n");
        return returnRegister;    
    }   
        
    // private String Eappend(Eappend array){



    // }
    
    private String generateArray(EArray arrayExp) {

        int numElements = arrayExp.elements().size();
        String arrayType = convertType(arrayExp.type());
		String basePointer = generateRegister();
		String spacePointer = generateRegister();
        int allocatedSpace = (arrayType.equals("double")) ? numElements * 8 * 2 : numElements * 4 * 2; 
        sb.append(spacePointer).append(" = call i8* @malloc(i64 ").append(allocatedSpace).append(")\n");
        sb.append(basePointer).append(" = bitcast i8* ").append(spacePointer).append(" to i32*\n");
        String arrayGlobal = generateLabel("array");

        globals.append("@").append(arrayGlobal).append(" = private constant [").append(numElements).append(" x ").append(arrayType).append("] [");
        if (arrayType.equals("i8*")){
            for (int i = 0; i < numElements; i++){
                Exp elementExp = arrayExp.elements().get(i);
                String value = generateExpression(elementExp);
                String globalName = stringRegisterToGlobal.get(value);
                int length = stringGlobalToLength.get(globalName);
                globals.append("i8* getelementptr inbounds ([").append(length).append(" x i8], [").append(length).append(" x i8]* ").append(globalName).append(", i32 0, i32 0)");
                if (i < numElements - 1) {
                    globals.append(", ");
                }
            }
        } else {
            for (int i = 0; i < numElements; i++) {
            Exp elementExp = arrayExp.elements().get(i);
            String value = generateExpression(elementExp);
            globals.append(arrayType).append(" ").append(value);
            if (i < numElements - 1) {
                globals.append(", ");
            }
        }
        }

        globals.append("]\n");

        String counter = generateRegister();
        sb.append(counter).append(" = alloca i32\n");
        sb.append("store i32 ").append(0).append(", i32* ").append(counter).append("\n");
        String loop = generateLabel("loop");
        sb.append("br label %").append(loop).append("\n");
        sb.append(loop).append(":\n");
        String currentIndex = generateRegister();
        sb.append(currentIndex).append(" = load i32, i32* ").append(counter).append("\n");
        String condition = generateRegister();
        sb.append(condition).append(" = icmp slt i32 ").append(currentIndex).append(", ").append(numElements).append("\n");
        String loopMain = generateLabel("loopMain");
        String loopEnd = generateLabel("loopEnd");
        sb.append("br i1 ").append(condition).append(", label %").append(loopMain).append(", label %").append(loopEnd).append("\n");
        sb.append(loopMain).append(":\n");
        String globalElemPointer = generateRegister();
        sb.append(globalElemPointer).append(" = getelementptr inbounds [").append(numElements).append(" x ").append(arrayType).append("], [").append(numElements).append(" x ").append(arrayType).append("]* @").append(arrayGlobal).append(", i32 0, i32 ").append(currentIndex).append("\n");
        String globalValue = generateRegister();
        sb.append(globalValue).append(" = load ").append(arrayType).append(", ").append(arrayType).append("* ").append(globalElemPointer).append("\n");

        String elemPointer = generateRegister();
        sb.append(elemPointer).append(" = ").append("getelementptr inbounds ").append(arrayType).append(", ").append("i32* ").append(basePointer).append(", i32 ").append(currentIndex).append("\n");
        sb.append("store ").append(arrayType).append(" ").append(globalValue).append(", ").append("i32* ").append(elemPointer).append("\n");
        String nextIndex = generateRegister();


        sb.append(nextIndex).append(" = add i32 ").append(currentIndex).append(", 1\n");
        sb.append("store i32 ").append(nextIndex).append(", i32* ").append(counter).append("\n");
        sb.append("br label %").append(loop).append("\n");
        sb.append(loopEnd).append(":\n");

       arrayNameToValue.put(basePointer, new ArrayValue(basePointer, numElements, numElements * 2, arrayType));
        return basePointer;
    }


    private String generateArrayIndex(EArrayIndex arrayIndexExp) { // THIS WILL NOT WORK IF ARRAY DOES NOT HAVE A NAME, I.E. IF IT'S NOT STORED IN A VARIABLE, BUT INSTEAD IS AN INLINE ARRAY LITERAL. THIS IS BECAUSE WE NEED THE NAME TO LOOK UP THE BASE POINTER OF THE ARRAY IN THE ENVIRONMENT. THIS CAN BE FIXED BY FIRST GENERATING THE ARRAY AS USUAL, STORING THE BASE POINTER IN A REGISTER, AND THEN USING THAT REGISTER AS THE BASE POINTER FOR THE GETELEMENTPTR IN THIS METHOD. CURRENTLY, THIS METHOD ASSUMES THAT ALL ARRAYS ARE STORED IN VARIABLES WITH NAMES, AND THAT THE BASE POINTERS OF THESE ARRAYS ARE STORED IN THE ENVIRONMENT UNDER THEIR NAMES.
    
		String register = generateRegister();
		String returnRegister = generateRegister();
		String arrayType = convertType(arrayIndexExp.array().type());
        String index = generateExpression(arrayIndexExp.index());
		String arrayName = ((EId) arrayIndexExp.array()).name();

		sb.append(register).append(" = ").append("getelementptr inbounds ").append(arrayType).append(", ").append("i32* ").append(environment.lookup(arrayName)).append(", i32 ").append(index).append("\n");
        sb.append(returnRegister).append(" = load ").append(arrayType).append(", ").append(arrayType).append("* ").append(register).append("\n");
        
		return returnRegister;
    }

    private String generateArrayIndexAssign(EArrayIndexAssign arrayIndexAssignExp) {

		String register = generateRegister();
		String returnRegister = generateRegister();
		String arrayType = convertType(arrayIndexAssignExp.array().type());
        String index = generateExpression(arrayIndexAssignExp.index());
		String arrayName = ((EId) arrayIndexAssignExp.array()).name();
        String value = generateExpression(arrayIndexAssignExp.value());

		sb.append(register).append(" = ").append("getelementptr inbounds ").append(arrayType).append(", ").append("i32* ").append(environment.lookup(arrayName)).append(", i32 ").append(index).append("\n");
        sb.append("store ").append(arrayType).append(" ").append(value).append(", ").append(arrayType).append("* ").append(register).append("\n");
        
		return returnRegister;
    }

    private String generateAppend(EAppend appendExp) {
        String value = generateExpression(appendExp.element());
        String arrayType = convertType(appendExp.array().type());
        String basePointer = generateExpression(appendExp.array());
        ArrayValue arrayValue = arrayNameToValue.get(basePointer);
        if (arrayValue.size < arrayValue.capacity){
            String elemPointer = generateRegister();
            sb.append(elemPointer).append(" = ").append("getelementptr inbounds ").append(arrayType).append(", ").append("i32* ").append(basePointer).append(", i32 ").append(arrayValue.size).append("\n");
            sb.append("store ").append(arrayType).append(" ").append(value).append(", ").append(arrayType).append("* ").append(elemPointer).append("\n");
            arrayValue.size++;
        } 
        
        return basePointer;
    }


    private String generateUnary(EUnary unaryExp) {
        String value = generateExpression(unaryExp.exp());
        String register = generateRegister();
        switch (unaryExp.op()) {
            case INC -> {sb.append(register).append(" = add ").append(convertType(unaryExp.type())).append(" ").append(value).append(", 1\n");}
            case DEC -> {sb.append(register).append(" = sub ").append(convertType(unaryExp.type())).append(" ").append(value).append(", 1\n");}
        }
         sb.append("store ").append(convertType(unaryExp.type())).append(" ").append(register).append(", ").append(convertType(unaryExp.type())).append("* ").append(environment.lookup(((EId) unaryExp.exp()).name())).append("\n");
 
        
        return register;

    }

    private String generateDInt(EDInt dIntExp) {
        String value = generateExpression(dIntExp.exp());
        String register = generateRegister();
        sb.append(register).append(" = sitofp i32 ").append(value).append(" to double\n");
        return register;
    }

    private String generateStringCast(EStringCast sStringCast) {
        String value = generateExpression(sStringCast.exp());
        Type type = sStringCast.exp().type();

        if(type instanceof Ast.TString) {
            return value; // No need to cast if it's already a string
        }

        String register = generateRegister();

        if (type instanceof TInt) {
            generateStringCastInt(value, register);
        } else if (type instanceof TDouble) {
            generateStringCastDouble(value, register);
        } else if (type instanceof TBool) {
            generateStringCastBool(value, register);
        } else if(type instanceof TArray) {
            generateArrayString(sStringCast, register);
        }
        else {
            throw new IllegalArgumentException("Unsupported type for string cast " + TypeConverter.typeToString(type));
        }
        return register;

    }

    private void generateArrayString(EStringCast sStringCast, String register) {
        String arrayRegister = generateExpression(sStringCast.exp()); // generate pointer to register
        Ast.TArray type = (Ast.TArray) sStringCast.exp().type(); // cast to tarray
        int size = type.arraySize();
        Ast.Type elementType = type.elementType();
        String elementTypeStr = convertType(elementType); // Get LLVM IR type string for the array element type

        String funcName;
        if(elementType instanceof Ast.TInt) {
            funcName = "@array_int_to_string";
        } else if (elementType instanceof Ast.TDouble) {
            funcName = "@array_double_to_string";
        } else if (elementType instanceof Ast.TBool) {
            funcName = "@array_bool_to_string";
        } else if(elementType instanceof Ast.TString) {
            funcName = "@array_string_to_string";
        } else {
            throw new IllegalArgumentException("Unsupported array element type for string cast " + TypeConverter.typeToString(elementType));
        }

        // call runtime function to convert it to string
        sb.append("  ").append(register)
                .append(" = call i8* ").append(funcName)
                .append("(").append(elementTypeStr).append("* ").append(arrayRegister)
                .append(", i32 ").append(size).append(")\n");

    }

    private void generateStringCastInt(String value, String register) {
        sb.append("  ").append(register)
                .append(" = call i8* @int_to_string(i32 ")
                .append(value).append(")\n");
    }

    private void generateStringCastDouble(String value, String register) {
        sb.append("  ").append(register)
                .append(" = call i8* @double_to_string(double ")
                .append(value).append(")\n");
    }

    private void generateStringCastBool(String value, String register) {
        sb.append("  ").append(register)
                .append(" = call i8* @bool_to_string(i1 " ).append(value).append(")\n");
    }

}
