package com.example.minilang.codegen;

public class ArrayValue {
    public String pointer;        // The i32* register/pointer
    public int size;              // Current number of elements
    public int capacity;          // Allocated capacity
    public String elementType;    // "i32", "double", "i8*", etc.
    
    public ArrayValue(String pointer, int size, int capacity, String elementType) {
        this.pointer = pointer;
        this.size = size;
        this.capacity = capacity;
        this.elementType = elementType;
    }
}