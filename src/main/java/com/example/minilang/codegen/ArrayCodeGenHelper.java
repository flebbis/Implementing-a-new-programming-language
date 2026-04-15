package com.example.minilang.codegen;

import com.example.minilang.ast.Ast;

import static com.example.minilang.codegen.LabelGenerator.generateLabel;
import static com.example.minilang.codegen.RegisterGenerator.generateRegister;

public class ArrayCodeGenHelper {

    private StringBuilder sb;

    public ArrayCodeGenHelper(StringBuilder sb) {
        this.sb = sb;
    }

    /**
     * Generate bounds check for array access: arr[index]
     * This will check if index is within [0, size) and branch to error handling if out of bounds.
     * @param arrPtr - the pointer to the array struct
     * @param indexRegister - the register containing the index value to check
     */
    protected void generateArrayBoundsCheck(String arrPtr, String indexRegister) {
        String sizePtr = generateRegister();
        sb.append(sizePtr)
                .append(" = getelementptr inbounds %array_i32, %array_i32* ")
                .append(arrPtr)
                .append(", i32 0, i32 0\n");

        String sizeValue = generateRegister();
        sb.append(sizeValue)
                .append(" = load i32, i32* ")
                .append(sizePtr)
                .append("\n");

        String lowerCheck = generateRegister();
        sb.append(lowerCheck)
                .append(" = icmp sge i32 ")
                .append(indexRegister)
                .append(", 0\n");

        String upperCheck = generateRegister();
        sb.append(upperCheck)
                .append(" = icmp slt i32 ")
                .append(indexRegister)
                .append(", ")
                .append(sizeValue)
                .append("\n");

        String inBounds = generateRegister();
        sb.append(inBounds)
                .append(" = and i1 ")
                .append(lowerCheck)
                .append(", ")
                .append(upperCheck)
                .append("\n");

        String okLabel = generateLabel("index_ok");
        String errorLabel = generateLabel("index_oob");

        sb.append("br i1 ")
                .append(inBounds)
                .append(", label %")
                .append(okLabel)
                .append(", label %")
                .append(errorLabel)
                .append("\n");

        sb.append(errorLabel).append(":\n");
        sb.append("call void @array_index_out_of_bounds(i32 "+ indexRegister + ", i32 " + sizeValue + ")\n");
        sb.append("unreachable\n"); // Indicates that this code path should never be reached after the call to the error handler

        sb.append(okLabel).append(":\n");
    }

    /**
     * Append to array that requires growing the underlying data buffer
     */
    protected void appendGrow(
            String appendGrow,
            String capValue,
            String sizeValue,
            String dataPtr,
            String value,
            String sizePtr,
            String dataFieldPtr,
            String capPtr,
            String elementType,
            String dataPointerType,
            int elementSizeBytes,
            String appendEnd
    ) {
        sb.append(appendGrow).append(":\n");

        // new capacity = capacity * 2
        String newCap = generateRegister();
        sb.append(newCap)
                .append(" = mul i32 ")
                .append(capValue)
                .append(", 2\n");

        // bytes = newCap * elementSizeBytes
        String bytes = generateRegister();
        sb.append(bytes)
                .append(" = mul i32 ")
                .append(newCap)
                .append(", ")
                .append(elementSizeBytes)
                .append("\n");

        // allocate new data buffer
        String newDataRaw = generateRegister();
        sb.append(newDataRaw)
                .append(" = call i8* @malloc(i32 ")
                .append(bytes)
                .append(")\n");

        String newData = generateRegister();
        sb.append(newData)
                .append(" = bitcast i8* ")
                .append(newDataRaw)
                .append(" to ")
                .append(dataPointerType)
                .append("\n");

        // counter = 0
        String counterPtr = generateRegister();
        sb.append(counterPtr).append(" = alloca i32\n");
        sb.append("store i32 0, i32* ").append(counterPtr).append("\n");

        String copyLoop = generateLabel("copy_loop");
        String copyBody = generateLabel("copy_body");
        String copyEnd = generateLabel("copy_end");

        sb.append("br label %").append(copyLoop).append("\n");

        // copy_loop:
        sb.append(copyLoop).append(":\n");

        String i = generateRegister();
        sb.append(i)
                .append(" = load i32, i32* ")
                .append(counterPtr)
                .append("\n");

        String cond = generateRegister();
        sb.append(cond)
                .append(" = icmp slt i32 ")
                .append(i)
                .append(", ")
                .append(sizeValue)
                .append("\n");

        sb.append("br i1 ")
                .append(cond)
                .append(", label %")
                .append(copyBody)
                .append(", label %")
                .append(copyEnd)
                .append("\n");

        // copy_body:
        sb.append(copyBody).append(":\n");

        // &oldData[i]
        String oldElemPtr = generateRegister();
        sb.append(oldElemPtr)
                .append(" = getelementptr inbounds ")
                .append(elementType)
                .append(", ")
                .append(dataPointerType)
                .append(" ")
                .append(dataPtr)
                .append(", i32 ")
                .append(i)
                .append("\n");

        // oldValue = oldData[i]
        String oldValue = generateRegister();
        sb.append(oldValue)
                .append(" = load ")
                .append(elementType)
                .append(", ")
                .append(elementType)
                .append("* ")
                .append(oldElemPtr)
                .append("\n");

        // &newData[i]
        String newElemPtr = generateRegister();
        sb.append(newElemPtr)
                .append(" = getelementptr inbounds ")
                .append(elementType)
                .append(", ")
                .append(dataPointerType)
                .append(" ")
                .append(newData)
                .append(", i32 ")
                .append(i)
                .append("\n");

        // newData[i] = oldValue
        sb.append("store ")
                .append(elementType)
                .append(" ")
                .append(oldValue)
                .append(", ")
                .append(elementType)
                .append("* ")
                .append(newElemPtr)
                .append("\n");

        // i = i + 1
        String nextI = generateRegister();
        sb.append(nextI)
                .append(" = add i32 ")
                .append(i)
                .append(", 1\n");
        sb.append("store i32 ")
                .append(nextI)
                .append(", i32* ")
                .append(counterPtr)
                .append("\n");

        sb.append("br label %").append(copyLoop).append("\n");

        // copy_end:
        sb.append(copyEnd).append(":\n");

        // arr->data = newData
        sb.append("store ")
                .append(dataPointerType)
                .append(" ")
                .append(newData)
                .append(", ")
                .append(dataPointerType)
                .append("* ")
                .append(dataFieldPtr)
                .append("\n");

        // arr->capacity = newCap
        sb.append("store i32 ")
                .append(newCap)
                .append(", i32* ")
                .append(capPtr)
                .append("\n");

        // free old data
        String oldDataAsI8 = generateRegister();
        sb.append(oldDataAsI8)
                .append(" = bitcast ")
                .append(dataPointerType)
                .append(" ")
                .append(dataPtr)
                .append(" to i8*\n");
        sb.append("call void @free(i8* ")
                .append(oldDataAsI8)
                .append(")\n");

        // &newData[size]
        String appendPtr = generateRegister();
        sb.append(appendPtr)
                .append(" = getelementptr inbounds ")
                .append(elementType)
                .append(", ")
                .append(dataPointerType)
                .append(" ")
                .append(newData)
                .append(", i32 ")
                .append(sizeValue)
                .append("\n");

        // newData[size] = value
        sb.append("store ")
                .append(elementType)
                .append(" ")
                .append(value)
                .append(", ")
                .append(elementType)
                .append("* ")
                .append(appendPtr)
                .append("\n");

        // size = size + 1
        String grownSize = generateRegister();
        sb.append(grownSize)
                .append(" = add i32 ")
                .append(sizeValue)
                .append(", 1\n");

        sb.append("store i32 ")
                .append(grownSize)
                .append(", i32* ")
                .append(sizePtr)
                .append("\n");

        sb.append("br label %").append(appendEnd).append("\n");
    }

    /**
     * Append to array that does not require to grow
     */
    protected void appendNotGrow(String appendOk, String dataPtr, String sizeValue, String value, String sizePtr, String appendEnd,
                                 String structType, String elementType, String dataPointerType) {
        // append_ok:
        sb.append(appendOk).append(":\n");

        // &data[size]
        String elemPtr = generateRegister();
        sb.append(elemPtr)
                .append(" = getelementptr inbounds ")
                .append(elementType)
                .append(", ")
                .append(dataPointerType)
                .append(" ")
                .append(dataPtr)
                .append(", i32 ")
                .append(sizeValue)
                .append("\n");

        // store new element
        sb.append("store ")
                .append(elementType)
                .append(" ")
                .append(value)
                .append(", ")
                .append(elementType)
                .append("* ")
                .append(elemPtr)
                .append("\n");

        // size = size + 1
        String newSize = generateRegister();
        sb.append(newSize)
                .append(" = add i32 ")
                .append(sizeValue)
                .append(", 1\n");

        sb.append("store i32 ")
                .append(newSize)
                .append(", i32* ")
                .append(sizePtr)
                .append("\n");

        sb.append("br label %").append(appendEnd).append("\n");
    }
}
