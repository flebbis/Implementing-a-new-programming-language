package com.example.minilang.typechecker;

import com.example.minilang.TypeConverter;
import com.example.minilang.ast.Ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpressionTypeChecker {

    private Context context = new Context();
    private HashMap<String, Signature> functionSignatures;

    public ExpressionTypeChecker(Context context, HashMap<String, Signature> signature) {
        this.context = context;
        this.functionSignatures = signature;
    }

    public Ast.Exp typeCheck(Ast.Exp exp) {
        return switch(exp) {
            case Ast.EInt eInt -> typeCheck(eInt);
            case Ast.EDouble eDouble -> typeCheck(eDouble);
            case Ast.EString eString -> typeCheck(eString);
            case Ast.EBool eBool -> typeCheck(eBool);
            case Ast.EId eId -> typeCheck(eId);
            case Ast.ECall eCall -> typeCheck(eCall);
            case Ast.ENot eNot -> typeCheck(eNot);
            case Ast.EPower ePower -> typeCheck(ePower);
            case Ast.EOpp eOpp -> typeCheck(eOpp);
            case Ast.ECmp cmp -> typeCheck(cmp);
            case Ast.ELogic eLogic -> typeCheck(eLogic);
            case Ast.EAss eAss -> typeCheck(eAss);
            case Ast.EArray eArray -> typeCheck(eArray);
            case Ast.EArrayIndex eArrayIndex -> typeCheck(eArrayIndex);
            case Ast.EUnary eUnary -> typeCheck(eUnary);
            case Ast.EDInt edInt -> typeCheck(edInt);
            case Ast.EStringCast eStringCast -> typeCheck(eStringCast);

            // More to be implemented
            default -> throw new TypeException("Unknown expression type: " + exp.getClass().getSimpleName(), exp.pos());
        };
    }

    public Ast.Exp typeCheck(Ast.EStringCast eStringCast) {
        return new Ast.EStringCast(typeCheck(eStringCast.exp()), new Ast.TString(), eStringCast.pos());
    }

    public Ast.Exp typeCheck(Ast.EDInt edInt) {
        return new Ast.EDInt(edInt.exp(), new Ast.TDouble(), edInt.pos());
    }

    public Ast.Exp typeCheck(Ast.EInt eInt) {return eInt;}

    public Ast.Exp typeCheck(Ast.EDouble eDouble) {return eDouble;}

    public Ast.Exp typeCheck(Ast.EString eString) {
        return  eString;
    }

    public Ast.Exp typeCheck(Ast.EBool eBool) {
        return eBool;
    }

    public Ast.Exp typeCheck(Ast.EId eId) {
        Ast.Type type = context.lookupLatest(eId.name());
        if(type != null) {
            eId = new Ast.EId(eId.name(), type, eId.pos());
            return eId;
        }
        else {
            throw new TypeException("Variable " + eId.name() + " is not declared", eId.pos());
        }
    }

    public Ast.Exp typeCheck(Ast.ENot enot){
        Ast.Exp exp = typeCheck(enot.exp());
        // Handle TUnknown to allow inference pass to continue
        if (exp.type() instanceof Ast.TUnknown) {
            return new Ast.ENot(exp, new Ast.TUnknown(), enot.pos());
        }
        if (exp.type() instanceof Ast.TBool) {
            return new Ast.ENot(exp, new Ast.TBool(), enot.pos());
        } else {
            throw new TypeException("Logical NOT operator requires boolean operand", enot.pos());
        }
    }

    public Ast.Exp typeCheck(Ast.EPower ePower){
        Ast.Exp base = typeCheck(ePower.base());
        Ast.Exp exponent = typeCheck(ePower.exponent());

        // Handle TUnknown to allow inference pass to continue
        if (base.type() instanceof Ast.TUnknown || exponent.type() instanceof Ast.TUnknown) {
            return new Ast.EPower(base, exponent, new Ast.TUnknown(), ePower.pos());
        }

        if (base.type() instanceof Ast.TInt || base.type() instanceof Ast.TDouble) {
            if (exponent.type() instanceof Ast.TInt || exponent.type() instanceof Ast.TDouble) {
                // If either is double, the result is double
                Ast.Type resultType = (base.type() instanceof Ast.TDouble || exponent.type() instanceof Ast.TDouble) ? new Ast.TDouble() : new Ast.TInt();
                return new Ast.EPower(base, exponent, resultType, ePower.pos());
            } else {
                throw new TypeException("Exponent must be of type int or double", ePower.pos());
            }
        }
        throw new TypeException("Power base must be of type int or double", ePower.pos());
    }

    public Ast.Exp typeCheck(Ast.ECmp eCmp) {
        Ast.Exp left = typeCheck(eCmp.left());
        Ast.Exp right = typeCheck(eCmp.right());

        // Handle TUnknown to allow inference pass to continue
        if (left.type() instanceof Ast.TUnknown || right.type() instanceof Ast.TUnknown) {
            return new Ast.ECmp(left, right, eCmp.op(), new Ast.TUnknown(), eCmp.pos());
        }

        Ast.Type leftType = left.type();
        Ast.Type rightType = right.type();

        // Int and double can be compared
        if ((leftType instanceof Ast.TDouble || leftType instanceof Ast.TInt)
                && (rightType instanceof Ast.TDouble || rightType instanceof Ast.TInt)) {
            return new Ast.ECmp(left, right, eCmp.op(), new Ast.TBool(), eCmp.pos());
        } else {
            throw new TypeException("Cannot compare type " + leftType + " with type " + rightType, eCmp.pos());
        }
    }

    public Ast.Exp typeCheck(Ast.ELogic eLogic) {
        Ast.Exp left = typeCheck(eLogic.left());
        Ast.Exp right = typeCheck(eLogic.right());

        // Handle TUnknown to allow inference pass to continue
        if (left.type() instanceof Ast.TUnknown || right.type() instanceof Ast.TUnknown) {
            return new Ast.ELogic(left, right, eLogic.op(), new Ast.TUnknown(), eLogic.pos());
        }

        if (left.type() instanceof Ast.TBool && right.type() instanceof Ast.TBool) {
            return new Ast.ELogic(left, right, eLogic.op(), new Ast.TBool(), eLogic.pos());
        } else {
            throw new TypeException("Logical operators require boolean operands", eLogic.pos());
        }
    }

    public Ast.Exp typeCheck(Ast.EOpp eOpp) {
        Ast.Exp left = typeCheck(eOpp.left());
        Ast.Exp right = typeCheck(eOpp.right());

        // Inference Support: If any operand is Unknown, return Unknown to avoid blocking the pass
        if (left.type() instanceof Ast.TUnknown || right.type() instanceof Ast.TUnknown) {
            return new Ast.EOpp(left, right, eOpp.op(), new Ast.TUnknown(), eOpp.pos());
        }

        // Modulus operator only works for integers
        if (eOpp.op() == Ast.Op.MOD) {
            if (left.type() instanceof Ast.TInt && right.type() instanceof Ast.TInt) {
                return new Ast.EOpp(left, right, eOpp.op(), new Ast.TInt(), eOpp.pos());
            } else {
                throw new TypeException("Modulus operator requires integer operands", eOpp.pos());
            }
        }

        // String addition
        if (eOpp.op() == Ast.Op.ADD) {
            if (left.type() instanceof Ast.TString && right.type() instanceof Ast.TString) {
                return new Ast.EOpp(left, right, eOpp.op(), new Ast.TString(), eOpp.pos());
            } else if (left.type() instanceof Ast.TString || right.type() instanceof Ast.TString) {
                // Wrap non-string operand in EStringCast
                if (!(left.type() instanceof Ast.TString)) {
                    left = new Ast.EStringCast(left, new Ast.TString(), left.pos());
                }
                if (!(right.type() instanceof Ast.TString)) {
                    right = new Ast.EStringCast(right, new Ast.TString(), right.pos());
                }
                return new Ast.EOpp(left, right, eOpp.op(), new Ast.TString(), eOpp.pos());
            }
        }

        // For other arithmetic operators, we allow int and double, with implicit conversion from int to double if needed
        if (left.type() instanceof Ast.TInt && right.type() instanceof Ast.TInt) {
            return new Ast.EOpp(left, right, eOpp.op(), new Ast.TInt(), eOpp.pos());
        } else if (left.type() instanceof Ast.TDouble && right.type() instanceof Ast.TDouble) {
            return new Ast.EOpp(left, right, eOpp.op(), new Ast.TDouble(), eOpp.pos());
        } else if (left.type() instanceof Ast.TInt && right.type() instanceof Ast.TDouble) {
            return new Ast.EOpp(new Ast.EDInt(left, new Ast.TDouble(), left.pos()), right, eOpp.op(), new Ast.TDouble(), eOpp.pos());
        } else if (left.type() instanceof Ast.TDouble && right.type() instanceof Ast.TInt) {
            return new Ast.EOpp(left, new Ast.EDInt(right, new Ast.TDouble(), right.pos()), eOpp.op(), new Ast.TDouble(), eOpp.pos());
        } else {
            throw new TypeException("Arithmetic operators require numeric operands", eOpp.pos());
        }
    }

    public Ast.EUnary typeCheck(Ast.EUnary eUnary) {
        Ast.Exp exp = typeCheck(eUnary.exp());

        // Handle TUnknown to allow inference pass to continue
        if (exp.type() instanceof Ast.TUnknown) {
            return new Ast.EUnary(exp, eUnary.op(), new Ast.TUnknown(), eUnary.pos());
        }

        if (exp.type() instanceof Ast.TInt) {
            return new Ast.EUnary(exp, eUnary.op(), exp.type(), eUnary.pos());
        }
        if (eUnary.op() == Ast.UnaryOp.INC) {
            throw new TypeException("Increment operator requires integer operand", eUnary.pos());
        } else {
            throw new TypeException("Decrement operator requires integer operand", eUnary.pos());
        }
    }

    public Ast.EAss typeCheck(Ast.EAss eAss) {
        Ast.Exp value = typeCheck(eAss.value());
        Ast.Type varType = context.lookupLatest(eAss.name());
        if (varType == null) {
            throw new TypeException("Variable " + eAss.name() + " is not declared", eAss.pos());
        }

        // Allow assignment if value is TUnknown (inference phase)
        if (value.type() instanceof Ast.TUnknown) {
            return new Ast.EAss(eAss.name(), value, eAss.op(), varType, eAss.pos());
        }

        if(varType instanceof Ast.TUnknown) {
            // If variable type is unknown, we can infer it from the value
            context.update(eAss.name(), value.type());
            varType = value.type();
        }

        if (!varType.equals(value.type())) {
            // Allow implicit conversion from int to double
            if (varType instanceof Ast.TDouble && value.type() instanceof Ast.TInt) {
                value = new Ast.EDInt(value, value.type(), value.pos());
            } else {
                throw new TypeException("Cannot assign type " + TypeConverter.typeToString(value.type()) + " to variable of type " + TypeConverter.typeToString(varType), eAss.pos());
            }
        }
        return new Ast.EAss(eAss.name(), value, eAss.op(), varType, eAss.pos());
    }

    public Ast.ECall typeCheck(Ast.ECall eCall) {
        // PRINT special case
        if(eCall.name().equals("print")) {
            if(eCall.args().size() != 1) {
                throw new TypeException("print function expects exactly one argument", eCall.pos());
            }
            // Special case for print: allow one argument of any type
            List<Ast.Exp> args = new ArrayList<>();
            for (Ast.Exp arg : eCall.args()) {
                args.add(typeCheck(arg));
            }
            return new Ast.ECall(eCall.name(), args, new Ast.TUnknown(), eCall.pos());
        }

        // Type check the arguments first
        List<Ast.Exp> args = new ArrayList<>();
        for (Ast.Exp arg : eCall.args()) {
            args.add(typeCheck(arg));
        }

        // Then look up the function signature
        if (functionSignatures.containsKey(eCall.name())) {
            int i = 0;
            if(functionSignatures.get(eCall.name()).paramTypes.size() != eCall.args().size()) {
                throw new TypeException("Function " + eCall.name() + " expects " + functionSignatures.get(eCall.name()).paramTypes.size() + " arguments but got " + eCall.args().size(), eCall.pos());
            }
            for (Ast.Type paramType : functionSignatures.get(eCall.name()).paramTypes) {
                // Check mismatch
                if (!args.get(i).type().equals(paramType)) {
                    // Inference: If param is TUnknown, infer from arg
                    if ((paramType instanceof Ast.TUnknown) && !(args.get(i).type() instanceof Ast.TUnknown)) {
                        functionSignatures.get(eCall.name()).paramTypes.set(i, args.get(i).type());
                        paramType = args.get(i).type();
                    }

                    // Allow implicit conversion from int to double
                    if (paramType instanceof Ast.TDouble && args.get(i).type() instanceof Ast.TInt) {
                        args.set(i, new Ast.EDInt(args.get(i), args.get(i).type(), args.get(i).pos()));
                    } else if (!paramType.equals(args.get(i).type())) {
                        throw new TypeException("Argument " + (i+1) + " of function " + eCall.name() + " expects type " + paramType + " but got type " + args.get(i).type(), eCall.pos());
                    }
                }
                i++;
            }
        } else {
            throw new TypeException("Function " + eCall.name() + " is not declared", eCall.pos());
        }

        return new Ast.ECall(eCall.name(), args, functionSignatures.get(eCall.name()).returnType, eCall.pos());
    }

    public Ast.Exp typeCheck(Ast.EArray eArray) {
        List<Ast.Exp> checkedElements = new ArrayList<>();
        for (Ast.Exp element : eArray.elements()) {
            checkedElements.add(typeCheck(element));
        }

        Ast.TArray type = (Ast.TArray) eArray.type();
        if(type.elementType() instanceof Ast.TUnknown) {
            //TODO: observe this
            // If the array type is unknown, we can infer it from the first element
            if (!checkedElements.isEmpty()) {
                Ast.Type inferredType = checkedElements.getFirst().type();
                type = new Ast.TArray(inferredType);
            } else {
                // If the array is empty and the type is unknown, we can't infer the element type
                throw new TypeException("Cannot infer type of empty array", eArray.pos());
            }
        }

        for (Ast.Exp element : checkedElements) {
            if (!element.type().equals(type.elementType())) {
                throw new TypeException("Array elements must be of the same type. Expected " + TypeConverter.typeToString(type.elementType())
                        + " but got " + TypeConverter.typeToString(element.type()), element.pos());
            }
        }

        return new Ast.EArray(checkedElements, type, eArray.pos());
    }

    public Ast.Exp typeCheck(Ast.EArrayIndex eArrayIndex) {
        Ast.Exp array = typeCheck(eArrayIndex.array());
        Ast.Exp index = typeCheck(eArrayIndex.index());

        if(!(array.type() instanceof Ast.TArray)) {
            throw new TypeException("Attempting to index a non-array type: " + TypeConverter.typeToString(array.type()), array.pos());
        }

        if (!(index.type() instanceof Ast.TInt)) {
            throw new TypeException("Array index must be of type int", index.pos());
        }

        // The result type is the element type of the array

        Ast.Type resultType = (array.type() instanceof Ast.TArray arr) ? arr.elementType() : new Ast.TUnknown();
        return new Ast.EArrayIndex(array, index, resultType, eArrayIndex.pos());
    }
}
