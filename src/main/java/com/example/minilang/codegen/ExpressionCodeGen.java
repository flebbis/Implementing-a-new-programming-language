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
    private ArrayCodeGenHelper arrayCodeGenHelper;
    public ExpressionCodeGen(StringBuilder sb, StringBuilder globals, StringBuilder globalStrings, HashSet<String> functionVariables, Environment environment) {
        this.sb = sb;
        this.globals = globals;
        this.globalStrings = globalStrings;
        this.functionVariables = functionVariables;
        this.environment = environment;
        this.arrayCodeGenHelper = new ArrayCodeGenHelper(sb);
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
        String variableRegister = environment.lookup(idExp.name());
        String register = generateRegister();
        sb.append(register).append(" = load ").append(convertType(idExp.type())).append(", ").append(convertType(idExp.type())).append("* ").append(variableRegister).append("\n");
        return register;
    }

    private String generateCall(ECall callExp) {
        if (callExp.name().equals("print")) {
            String register = generatePrintCall(callExp);
            if (register != null) return register;
        }

        String[] argValues = new String[callExp.args().size()];
        String[] argTypes = new String[callExp.args().size()];

        // Generate all argument expressions first
        for (int i = 0; i < callExp.args().size(); i++) {
            Exp arg = callExp.args().get(i);
            argValues[i] = generateExpression(arg);
            argTypes[i] = convertType(arg.type());
        }

        // Void function call
        if (callExp.type() instanceof Ast.TUnknown) {
            sb.append("call void @").append(callExp.name()).append("(");
            for (int i = 0; i < callExp.args().size(); i++) {
                sb.append(argTypes[i]).append(" ").append(argValues[i]);
                if (i < callExp.args().size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")\n");
            return "";
        }

        // Function call with return value
        String register = generateRegister();
        sb.append(register)
                .append(" = call ")
                .append(convertType(callExp.type()))
                .append(" @")
                .append(callExp.name())
                .append("(");

        for (int i = 0; i < callExp.args().size(); i++) {
            sb.append(argTypes[i]).append(" ").append(argValues[i]);
            if (i < callExp.args().size() - 1) {
                sb.append(", ");
            }
        }

        sb.append(")\n");
        return register;
    }

    private String generatePrintCall(ECall callExp) {
        Exp arg = callExp.args().get(0);
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
        String type = convertType(cmpExp.left().type());
        if (type.equals("double")) {
            switch (cmpExp.op()) {
                case LT -> {sb.append(register).append(" = fcmp olt ").append(type).append(" ").append(left).append(", ").append(right).append("\n");}
                case GT -> {sb.append(register).append(" = fcmp ogt ").append(type).append(" ").append(left).append(", ").append(right).append("\n");}
                case LE -> {sb.append(register).append(" = fcmp ole ").append(type).append(" ").append(left).append(", ").append(right).append("\n");}
                case GE -> {sb.append(register).append(" = fcmp oge ").append(type).append(" ").append(left).append(", ").append(right).append("\n");}
                case EQ -> {sb.append(register).append(" = fcmp oeq ").append(type).append(" ").append(left).append(", ").append(right).append("\n");}
                case NE -> {sb.append(register).append(" = fcmp one ").append(type).append(" ").append(left).append(", ").append(right).append("\n");}
            }
        } else {
            switch (cmpExp.op()) {
                case LT -> {sb.append(register).append(" = icmp slt ").append(type).append(" ").append(left).append(", ").append(right).append("\n");}
                case GT -> {sb.append(register).append(" = icmp sgt ").append(type).append(" ").append(left).append(", ").append(right).append("\n");}
                case LE -> {sb.append(register).append(" = icmp sle ").append(type).append(" ").append(left).append(", ").append(right).append("\n");}
                case GE -> {sb.append(register).append(" = icmp sge ").append(type).append(" ").append(left).append(", ").append(right).append("\n");}
                case EQ -> {sb.append(register).append(" = icmp eq ").append(type).append(" ").append(left).append(", ").append(right).append("\n");}
                case NE -> {sb.append(register).append(" = icmp ne ").append(type).append(" ").append(left).append(", ").append(right).append("\n");}
        }
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

    private String generateArray(Ast.EArray arrayExp) {
        Ast.TArray arrayTypeAst = (Ast.TArray) arrayExp.type();

        String structType = getArrayStructType(arrayExp.type());          // e.g. %array_i32
        String elementType = getArrayElementType(arrayExp.type());        // e.g. i32
        String dataPointerType = getArrayDataPointerType(arrayExp.type()); // e.g. i32* or i8**
        int elementSize = getTypeSizeBytes(arrayTypeAst.elementType());

        int numElements = arrayExp.elements().size();
        int capacity = Math.max(1, numElements * 2);

        String rawStructPtr = generateRegister();
        String arrPtr = generateRegister();
        String rawDataPtr = generateRegister();
        String dataPtr = generateRegister();

        // Allocate struct
        // We hardcode 16 bytes for { i32, i32, ptr } on 64-bit
        sb.append(rawStructPtr)
                .append(" = call i8* @malloc(i64 16)\n");
        sb.append(arrPtr)
                .append(" = bitcast i8* ")
                .append(rawStructPtr)
                .append(" to ")
                .append(structType)
                .append("*\n");

        // Allocate data buffer
        long totalBytes = (long) capacity * elementSize;
        sb.append(rawDataPtr)
                .append(" = call i8* @malloc(i64 ")
                .append(totalBytes)
                .append(")\n");
        sb.append(dataPtr)
                .append(" = bitcast i8* ")
                .append(rawDataPtr)
                .append(" to ")
                .append(dataPointerType)
                .append("\n");

        // Store size
        String sizePtr = generateRegister();
        sb.append(sizePtr)
                .append(" = getelementptr inbounds ")
                .append(structType)
                .append(", ")
                .append(structType)
                .append("* ")
                .append(arrPtr)
                .append(", i32 0, i32 0\n");
        sb.append("store i32 ")
                .append(numElements)
                .append(", i32* ")
                .append(sizePtr)
                .append("\n");

        // Store capacity
        String capPtr = generateRegister();
        sb.append(capPtr)
                .append(" = getelementptr inbounds ")
                .append(structType)
                .append(", ")
                .append(structType)
                .append("* ")
                .append(arrPtr)
                .append(", i32 0, i32 1\n");
        sb.append("store i32 ")
                .append(capacity)
                .append(", i32* ")
                .append(capPtr)
                .append("\n");

        // Store data pointer
        String dataFieldPtr = generateRegister();
        sb.append(dataFieldPtr)
                .append(" = getelementptr inbounds ")
                .append(structType)
                .append(", ")
                .append(structType)
                .append("* ")
                .append(arrPtr)
                .append(", i32 0, i32 2\n");
        sb.append("store ")
                .append(dataPointerType)
                .append(" ")
                .append(dataPtr)
                .append(", ")
                .append(dataPointerType)
                .append("* ")
                .append(dataFieldPtr)
                .append("\n");

        // Store initial elements
        for (int i = 0; i < numElements; i++) {
            String value = generateExpression(arrayExp.elements().get(i));

            String elemPtr = generateRegister();
            sb.append(elemPtr)
                    .append(" = getelementptr inbounds ")
                    .append(elementType)
                    .append(", ")
                    .append(dataPointerType)
                    .append(" ")
                    .append(dataPtr)
                    .append(", i32 ")
                    .append(i)
                    .append("\n");

            sb.append("store ")
                    .append(elementType)
                    .append(" ")
                    .append(value)
                    .append(", ")
                    .append(elementType)
                    .append("* ")
                    .append(elemPtr)
                    .append("\n");
        }

        return arrPtr;
    }

    private String generateArrayIndex(Ast.EArrayIndex arrayIndexExp) {

        String arrayRegister = generateExpression(arrayIndexExp.array()); // %array_i32*
        String indexRegister = generateExpression(arrayIndexExp.index()); // i32 (index:int to value)
        String structType = getArrayStructType(arrayIndexExp.array().type()); // e.g. %array_i32
        String elementType = getArrayElementType(arrayIndexExp.array().type()); // e.g. i32
        String dataPointerType = getArrayDataPointerType(arrayIndexExp.array().type()); // e.g. i32* or i8**

        arrayCodeGenHelper.generateArrayBoundsCheck(arrayRegister, indexRegister);

        // Get pointer to data field: &arr->data
        String dataFieldPtr = generateRegister();
        sb.append(dataFieldPtr)
                .append(" = getelementptr inbounds ")
                .append(structType)
                .append(", ")
                .append(structType)
                .append("* ")
                .append(arrayRegister)
                .append(", i32 0, i32 2\n");

        // Load actual data pointer: arr->data
        String dataPtr = generateRegister();
        sb.append(dataPtr)
                .append(" = load ")
                .append(dataPointerType)
                .append(", ")
                .append(dataPointerType)
                .append("* ")
                .append(dataFieldPtr)
                .append("\n");

        // Get pointer to the indexed element: &data[index]
        String elemPtr = generateRegister();
        sb.append(elemPtr)
                .append(" = getelementptr inbounds ")
                .append(elementType)
                .append(", ")
                .append(dataPointerType)
                .append(" ")
                .append(dataPtr)
                .append(", i32 ")
                .append(indexRegister)
                .append("\n");

        // Load the value
        String valueRegister = generateRegister();

        sb.append(valueRegister)
                .append(" = load ")
                .append(elementType)
                .append(", ")
                .append(elementType)
                .append("* ")
                .append(elemPtr)
                .append("\n");

        return valueRegister;
    }

    private String generateArrayIndexAssign(Ast.EArrayIndexAssign arrayIndexAssignExp) {

        String arrPtr = generateExpression(arrayIndexAssignExp.array()); // %array_i32*
        String indexRegister = generateExpression(arrayIndexAssignExp.index()); // i32 (index:int to value)
        String structType = getArrayStructType(arrayIndexAssignExp.array().type()); // e.g. %array_i32
        String elementType = getArrayElementType(arrayIndexAssignExp.array().type()); // e.g. i32
        String dataPointerType = getArrayDataPointerType(arrayIndexAssignExp.array().type()); // e.g. i32* or i8**

        arrayCodeGenHelper.generateArrayBoundsCheck(arrPtr, indexRegister);

        String dataFieldPtr = generateRegister();
        sb.append(dataFieldPtr)
                .append(" = getelementptr inbounds ")
                .append(structType)
                .append(", ")
                .append(structType)
                .append("* ")
                .append(arrPtr)
                .append(", i32 0, i32 2\n");

        // load actual data pointer: arr->data
        String dataPtr = generateRegister();
        sb.append(dataPtr)
                .append(" = load ")
                .append(dataPointerType)
                .append(", ")
                .append(dataPointerType)
                .append("* ")
                .append(dataFieldPtr)
                .append("\n");

        // Get pointer to the indexed element: &data[index]
        String elemPtr = generateRegister();
        sb.append(elemPtr)
                .append(" = getelementptr inbounds ")
                .append(elementType)
                .append(", ")
                .append(dataPointerType)
                .append(" ")
                .append(dataPtr)
                .append(", i32 ")
                .append(indexRegister)
                .append("\n");

        String value = generateExpression(arrayIndexAssignExp.value()); // i32 (value:int to value)
        sb.append("store ")
                .append(elementType)
                .append(" ")
                .append(value)
                .append(", ")
                .append(elementType)
                .append("* ")
                .append(elemPtr)
                .append("\n");

        return value;
    }


    private String generateAppend(Ast.EAppend appendExp) {
        String arrPtr = generateExpression(appendExp.array());   // %array_i32*
        String value = generateExpression(appendExp.element());  // i32
        String structType = getArrayStructType(appendExp.array().type()); // e.g. %array_i32
        String elementType = getArrayElementType(appendExp.array().type()); // e.g. i32
        String dataPointerType = getArrayDataPointerType(appendExp.array().type()); // e.g. i32* or i8**
        int elementSize = getTypeSizeBytes(appendExp.type());

        // &arr->size
        String sizePtr = generateRegister();
        sb.append(sizePtr)
                .append(" = getelementptr inbounds ")
                .append(structType)
                .append(", ")
                .append(structType)
                .append("* ")
                .append(arrPtr)
                .append(", i32 0, i32 0\n");

        // size
        String sizeValue = generateRegister();
        sb.append(sizeValue)
                .append(" = load i32, i32* ")
                .append(sizePtr)
                .append("\n");

        // &arr->capacity
        String capPtr = generateRegister();
        sb.append(capPtr)
                .append(" = getelementptr inbounds ")
                .append(structType)
                .append(", ")
                .append(structType)
                .append("* ")
                .append(arrPtr)
                .append(", i32 0, i32 1\n");

        // capacity
        String capValue = generateRegister();
        sb.append(capValue)
                .append(" = load i32, i32* ")
                .append(capPtr)
                .append("\n");

        // &arr->data
        String dataFieldPtr = generateRegister();
        sb.append(dataFieldPtr)
                .append(" = getelementptr inbounds ")
                .append(structType)
                .append(", ")
                .append(structType)
                .append("* ")
                .append(arrPtr)
                .append(", i32 0, i32 2\n");

        // arr->data
        String dataPtr = generateRegister();
        sb.append(dataPtr)
                .append(" = load ")
                .append(dataPointerType)
                .append(", ")
                .append(dataPointerType)
                .append("* ")
                .append(dataFieldPtr)
                .append("\n");

        // size < capacity ?
        String hasRoom = generateRegister();
        sb.append(hasRoom)
                .append(" = icmp slt i32 ")
                .append(sizeValue)
                .append(", ")
                .append(capValue)
                .append("\n");

        String appendOk = generateLabel("append_ok");
        String appendGrow = generateLabel("append_grow");
        String appendEnd = generateLabel("append_end");

        // jump to append_ok if there is room, otherwise jump to append_grow
        sb.append("br i1 ")
                .append(hasRoom)
                .append(", label %")
                .append(appendOk)
                .append(", label %")
                .append(appendGrow)
                .append("\n");

        arrayCodeGenHelper.appendNotGrow(appendOk, dataPtr, sizeValue, value, sizePtr, appendEnd, structType, elementType, dataPointerType);

        arrayCodeGenHelper.appendGrow(appendGrow, capValue, sizeValue, dataPtr, value, sizePtr, dataFieldPtr, capPtr, elementType, dataPointerType, elementSize,appendEnd);


        //sb.append("br label %").append(appendEnd).append("\n");

        sb.append(appendEnd).append(":\n");

        return arrPtr;
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

        String arrayRegister = generateExpression(sStringCast.exp());
        Ast.TArray arrayType = (Ast.TArray) sStringCast.exp().type();
        Ast.Type elementType = arrayType.elementType();
        String structPointer = convertType(arrayType); // e.g. %array_i32*, array_double*, etc.
        String structType = getArrayStructType(arrayType); // e.g. %array_i32, %array_double, etc.
        String arrayDataPointerType = getArrayDataPointerType(arrayType); // e.g. i32* for int arrays, double* for double arrays, etc.

        // Load size from struct field 0
        String sizePtr = generateRegister();
        sb.append(sizePtr)
                .append(" = getelementptr inbounds ").append(structType).append(", ").append(structPointer)
                .append(arrayRegister)
                .append(", i32 0, i32 0\n");

        String sizeValue = generateRegister();
        sb.append(sizeValue)
                .append(" = load i32, i32* ")
                .append(sizePtr)
                .append("\n");

        // Load data pointer from struct field 2
        String dataFieldPtr = generateRegister();
        sb.append(dataFieldPtr).append(" = getelementptr inbounds ").append(structType).append(", ").append(structPointer)
                .append(arrayRegister)
                .append(", i32 0, i32 2\n");

        String dataPtr = generateRegister();
        sb.append(dataPtr)
                .append(" = load ").append(arrayDataPointerType).append(", ").append(arrayDataPointerType + "*")
                .append(dataFieldPtr)
                .append("\n");

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
                .append("(").append(arrayDataPointerType).append(dataPtr)
                .append(", i32 ").append(sizeValue).append(")\n");
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
