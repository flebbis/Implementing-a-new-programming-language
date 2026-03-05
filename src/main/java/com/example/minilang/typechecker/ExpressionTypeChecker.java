package com.example.minilang.typechecker;

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
            case Ast.EArrayIndex eArrayIndex -> typeCheck(eArrayIndex);
            case Ast.EUnary eUnary -> typeCheck(eUnary);

            // More to be implemented
            default -> throw new TypeException("Unknown expression type: " + exp.getClass().getSimpleName(), exp.pos());
        };
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
        if(exp.type() == Ast.Type.TUnknown) {
            return new Ast.ENot(exp, Ast.Type.TUnknown, enot.pos());
        }
        if(exp.type() == Ast.Type.TBool) {
            return new Ast.ENot(exp, Ast.Type.TBool, enot.pos());
        } else {
            throw new TypeException("Logical NOT operator requires boolean operand", enot.pos());
        }
    }

    public Ast.Exp typeCheck(Ast.EPower ePower){
        Ast.Exp base = typeCheck(ePower.base());
        Ast.Exp exponent = typeCheck(ePower.exponent());

        // Handle TUnknown to allow inference pass to continue
        if (base.type() == Ast.Type.TUnknown || exponent.type() == Ast.Type.TUnknown) {
            return new Ast.EPower(base, exponent, Ast.Type.TUnknown, ePower.pos());
        }

        if (base.type() == Ast.Type.TInt || base.type() == Ast.Type.TDouble) {
            if (exponent.type() == Ast.Type.TInt || exponent.type() == Ast.Type.TDouble) {
                // If either is double, the result is double
                Ast.Type resultType = (base.type() == Ast.Type.TDouble || exponent.type() == Ast.Type.TDouble) ? Ast.Type.TDouble : Ast.Type.TInt;
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
        if (left.type() == Ast.Type.TUnknown || right.type() == Ast.Type.TUnknown) {
            return new Ast.ECmp(left, right, eCmp.op(), Ast.Type.TUnknown, eCmp.pos());
        }

        Ast.Type leftType = left.type();
        Ast.Type rightType = right.type();

        // Int and double can be compared
        if((leftType == Ast.Type.TDouble || leftType == Ast.Type.TInt)
                && (rightType == Ast.Type.TDouble || rightType == Ast.Type.TInt)) {
            return new Ast.ECmp(left, right, eCmp.op(), Ast.Type.TBool, eCmp.pos()); // Return the original expression with the same type
        } else {
            throw new TypeException("Cannot compare type " + leftType + " with type " + rightType, eCmp.pos());
        }
    }

    public Ast.Exp typeCheck(Ast.ELogic eLogic) {
        Ast.Exp left = typeCheck(eLogic.left());
        Ast.Exp right = typeCheck(eLogic.right());

        // Handle TUnknown to allow inference pass to continue
        if (left.type() == Ast.Type.TUnknown || right.type() == Ast.Type.TUnknown) {
            return new Ast.ELogic(left, right, eLogic.op(), Ast.Type.TUnknown, eLogic.pos());
        }

        if(left.type() == Ast.Type.TBool && right.type() == Ast.Type.TBool) {
            return new Ast.ELogic(left, right, eLogic.op(), Ast.Type.TBool, eLogic.pos());
        } else {
            throw new TypeException("Logical operators require boolean operands", eLogic.pos());
        }
    }

    public Ast.Exp typeCheck(Ast.EOpp eOpp) {
        Ast.Exp left = typeCheck(eOpp.left());
        Ast.Exp right = typeCheck(eOpp.right());

        // Inference Support: If any operand is Unknown, return Unknown to avoid blocking the pass
        if (left.type() == Ast.Type.TUnknown || right.type() == Ast.Type.TUnknown) {
            return new Ast.EOpp(left, right, eOpp.op(), Ast.Type.TUnknown, eOpp.pos());
        }

        // Modulus operator only works for integers
        if(eOpp.op() == Ast.Op.MOD) {
            if(left.type() == Ast.Type.TInt && right.type() == Ast.Type.TInt) {
                return new Ast.EOpp(left, right, eOpp.op(), Ast.Type.TInt, eOpp.pos());
            } else {
                throw new TypeException("Modulus operator requires integer operands", eOpp.pos());
            }
        }

        // String addition
        if(eOpp.op() == Ast.Op.ADD) {
            if(left.type() == Ast.Type.TString && right.type() == Ast.Type.TString) {
                return new Ast.EOpp(left, right, eOpp.op(), Ast.Type.TString, eOpp.pos());
            }
            else if(left.type() == Ast.Type.TString || right.type() == Ast.Type.TString) {
                // Wrap non-string operand in EStringCast
                if(left.type() != Ast.Type.TString) {
                    left = new Ast.EStringCast(left, Ast.Type.TString, left.pos());
                }
                if(right.type() != Ast.Type.TString) {
                    right = new Ast.EStringCast(right, Ast.Type.TString, right.pos());
                }
                return new Ast.EOpp(left, right, eOpp.op(), Ast.Type.TString, eOpp.pos());
            }
        }

        // For other arithmetic operators, we allow int and double, with implicit conversion from int to double if needed
        if(left.type() == Ast.Type.TInt && right.type() == Ast.Type.TInt) {
            return new Ast.EOpp(left, right, eOpp.op(), Ast.Type.TInt, eOpp.pos());
        } else if(left.type() == Ast.Type.TDouble && right.type() == Ast.Type.TDouble) {
            return new Ast.EOpp(left, right, eOpp.op(), Ast.Type.TDouble, eOpp.pos());
        } else if(left.type() == Ast.Type.TInt && right.type() == Ast.Type.TDouble) {
            return new Ast.EOpp(new Ast.EDInt(left, left.type(), left.pos()), right, eOpp.op(), Ast.Type.TDouble, eOpp.pos());
        } else if(left.type() == Ast.Type.TDouble && right.type() == Ast.Type.TInt) {
            return new Ast.EOpp(left, new Ast.EDInt(right, right.type(), right.pos()), eOpp.op(), Ast.Type.TDouble, eOpp.pos());
        } else {
            throw new TypeException("Arithmetic operators require numeric operands", eOpp.pos());
        }
    }


    public Ast.EUnary typeCheck(Ast.EUnary eUnary) {
        Ast.Exp exp = typeCheck(eUnary.exp());

        // Handle TUnknown to allow inference pass to continue
        if (exp.type() == Ast.Type.TUnknown) {
            return new Ast.EUnary(exp, eUnary.op(), Ast.Type.TUnknown, eUnary.pos());
        }

        if(exp.type() == Ast.Type.TInt) {
            return new Ast.EUnary(exp, eUnary.op(), exp.type(), eUnary.pos());
        }
        if(eUnary.op() == Ast.UnaryOp.INC) {
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
        if (value.type() == Ast.Type.TUnknown) {
            return new Ast.EAss(eAss.name(), value, eAss.op(), varType, eAss.pos());
        }

        if (varType != value.type()) {
            // Allow implicit conversion from int to double
            if (varType == Ast.Type.TDouble && value.type() == Ast.Type.TInt) {
                value = new Ast.EDInt(value, value.type(), value.pos());
            } else {
                throw new TypeException("Cannot assign type " + value.type() + " to variable of type " + varType, eAss.pos());
            }
        }
        return new Ast.EAss(eAss.name(), value, eAss.op(), varType, eAss.pos());
    }

    public Ast.ECall typeCheck(Ast.ECall eCall) {
        // Type check the arguments first
        List<Ast.Exp> args = new ArrayList<>();
        for(Ast.Exp arg : eCall.args()) {
            args.add(typeCheck(arg));
        }

        // Then look up the function signature
        if(functionSignatures.containsKey(eCall.name())) {
            int i = 0;
            for(Ast.Type paramType : functionSignatures.get(eCall.name()).paramTypes) {
                // Check mismatch
                if(args.get(i).type() != paramType) {
                    // Inference: If param is TUnknown, infer from arg
                    if ((paramType == Ast.Type.TUnknown) && (args.get(i).type() != Ast.Type.TUnknown)) {
                        functionSignatures.get(eCall.name()).paramTypes.set(i, args.get(i).type());
                        paramType = args.get(i).type(); // Update local variable for subsequent checks
                    }

                    // Allow implicit conversion from int to double
                    if(paramType == Ast.Type.TDouble && args.get(i).type() == Ast.Type.TInt) {
                        args.set(i, new Ast.EDInt(args.get(i), args.get(i).type(), args.get(i).pos()));
                    } else if (paramType != args.get(i).type()) {
                        // Only throw if types still don't match after inference/conversion
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

    public Ast.Exp typeCheck(Ast.EArrayIndex eArrayIndex) {
        // Assuming implementation or stub
        Ast.Exp array = typeCheck(eArrayIndex.array());
        Ast.Exp index = typeCheck(eArrayIndex.index());
        return new Ast.EArrayIndex(array, index, Ast.Type.TUnknown, eArrayIndex.pos()); // Placeholder return type
    }
}
