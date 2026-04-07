# The FIKA Language

FIKA is a modern programming language designed to make low-level concepts more accessible through a simple and readable syntax. It combines a clean, minimal design with a strong type system and interactive tooling such as live compilation and type inference feedback.

The language is particularly focused on education, allowing users to understand how high-level code maps to lower-level representations.

## Basic Code Example
The following example demonstrates the basic syntax of FIKA, including variable declaration, function definition, and the use of a `do` loop to repeat an action multiple times. The types of variables and expressions are inferred automatically by the language, providing immediate feedback on the types being used.
```
x = 5

func printThreeTimes(value) {
  do 3 {
      print(value)
  }
}

printThreeTimes(x + 10)
```

Output:
```
15
15 
15
```

## Table of Contents
In the following sections, we will explore the syntax and features of FIKA in more detail, including its type system, control flow constructs, functions, arrays, comments, and more on type inference. The limited standard library will also be covered.
- [Types](#types)
- [Basic Syntax](#basic-syntax)
  - [Variables](#variables)
  - [Control Flow](#control-flow)
    - [If Statements](#if-statements)
    - [While Loops](#while-loops)
    - [Do Loops](#do-loops)
  - [Functions](#functions)
    - [Function Definitions](#function-definitions)
    - [Function Calls](#function-calls)
  - [Arrays](#arrays)
  - [Comments](#comments)
  - [More on inference](#more-on-inference)
    - [Simple inference example](#simple-inference-example)
    - [int to double promotion](#int-to-double-promotion)
    - [String concatenation](#string-concatenation)
    - [Function type inference](#function-type-inference)
- [Standard Library](#standard-library)

## Types
FIKA has four primitive types: `int`, `double`, `bool`, and `string`. 

Dynamic arrays are also supported, and can be declared putting a primitive type inside of squared brackets. For example, `[int]` represents an array of integers, and `[string]` represents an array of strings.
The size of the array does not need to be specified when declaring the array, as FIKA will automatically manage the memory for you.


## Basic Syntax
In the following section, the basic syntax of FIKA is explained, including variable declarations, control flow constructs, function definitions and calls, array usage, and comments. Additionally, the powerful type inference system of FIKA is discussed in more detail.

### Variables
Variables in FIKA are declared using a simple assignment syntax. The type of the variable is inferred from the assigned value, 
but can also be explicitly specified if desired.

For instance, both of the following examples are valid variable initializations of an integer variable named `x`:
```
x = 2
int x = 2
```

Semi-colons are optional, and can be used to separate multiple statements on the same line if desired. Example:
```
x = 2; y = 3;
```

Declaration works in a similar manner, explicit type declaration is optional, but can be used for clarity or to specify a different type than the inferred one. 

Example of variable declarations (both are valid):
```
x;
int x;
```

### Control Flow
FIKA supports standard control flow constructs such as `if`, `else`, `while`, and a unique `do` loop that executes a block of code a specified number of times.

#### If Statements
If statements are used for conditional execution of code blocks. The syntax is straightforward, with the condition enclosed in parentheses and the code block enclosed in curly braces.

Example:
```
x = 5
if (x > 0) {
    print("x is positive")
} else {
    print("x is non-positive")
}
```
will output:
```
x is positive
```

`else if` statements can also be used for multiple conditions. 

Example:
```
x = -1
if (x > 0) {
    print("x is positive")
} else if (x < 0) {
    print("x is negative")
} else {
    print("x is zero")
}
```
will output:
```
x is negative
```

#### While Loops
While loops allow for repeated execution of a block of code as long as a specified condition is true. The syntax is similar to if statements, with the condition enclosed in parentheses and the code block enclosed in curly braces.

Example:
```
x = 0
while (x < 3) {
    print(x)
    x += 1
}
```
will output:
```
0
1
2
```

#### Do Loops
The `do` loop is a unique control flow construct in FIKA that allows for executing a block of code a specified number of times. The syntax is `do <number> { ... }`, where `<number>` is the number of times to execute the block.

Example:
```
do 5 {
    print("Hello, FIKA!")
}
```
will output:
```
Hello, FIKA!
Hello, FIKA!
Hello, FIKA!
Hello, FIKA!
Hello, FIKA!
```

### Functions
#### Function Definitions
Functions are defined by using the `func` keyword, followed by the function name, a list of comma-separated parameters in parentheses, and the function body enclosed in curly braces. 
The return type can be specified before the func keyword, but is optional if it can be inferred.

Example:
```
func add(a, b) {
    return a + b
}
```
This defines a function named `add` that takes two parameters `a` and `b`, and returns their sum. The types of `a` and `b` are inferred from the context in which the function is called.

The same function can also be defined with explicit types:
```
int func add(int a, int b) {
    return a + b
}
```

Functions can also be defined without a return value, in which case they are considered to return `void`. In that case, no explicit return type should be specified, and the function body can simply perform actions without returning a value:
```
func printMessage(string message) {
    print(message)
}
```

#### Function Calls
Functions are called by using the function name followed by a list of comma-separated arguments in parentheses.

Example of calling the `add` function defined earlier:
```
result = add(5, 10)
print(result)
```
will output:
```
15
```

### Arrays
The syntax for declaring and using arrays is defined by enclosing the type of the array inside square brackets, eg `[double]`. All arrays are dynamic, so it is not needed to specify the size of the array when declaring it.

Example of declaring and initializing an array of integers:
```
[int] numbers = [1, 2, 3, 4, 5]
```

Appending to an array can be done using the `append` function:
```
numbers.append(6)
```
will result in `numbers` being `[1, 2, 3, 4, 5, 6]`.

Note that the same array can also be declared without an explicit type, and the type will be inferred from the assigned value:
```
numbers = [1, 2, 3, 4, 5]
```

Elements are accessed using index notation with square brackets. For example, `numbers[0]` will access the first element of the array, which is `1`.


### Comments
Comments in FIKA are denoted by `//` for single-line comments and `/* ... */` for multi-line comments. Comments are ignored by the compiler and are used to provide explanations or annotations within the code.
```
// This is a single-line comment

/*
This is a multi-line comment
*/
```

### Type system and inference
The following section explains the type system of FIKA in more detail, with a focus on its powerful type inference capabilities. The language is a statically typed language, which means that the types of variables and expressions are determined at compile time. However, FIKA also has a powerful type inference system that allows for a more concise and readable code, while still maintaining the benefits of a strongly typed language.


#### Simple inference example
To understand how type inference works in FIKA, consider the following example:
```
x
x = 5
```
In this example, we declare a variable `x` without specifying its type. When we assign the value `5` to `x`, the language automatically infers that `x` is of type `int`. This means that we can use `x` in any context where an integer is expected, and the language will treat it as an integer.

It is important to note that once a variable as been assigned a type, it cannot change type automatically. If x is later on assigned a value of a different type, such as a string, the language will raise a static type error inside the IDE, since `x` has already been inferred to be of type `int`. This is one of the key features of FIKA's type system, which combines the benefits of static typing with the flexibility of dynamic typing.

#### int to double promotion
Although the language is statically typed, it provides a lot of flexibility. For instance, ints are automatically promoted to doubles when used in an expression with a double, and the result will be inferred as a double. This allows for seamless integration of different types without the need for explicit type conversions.
An example of this is:
```
x = 5
y = 2.5
result = x + y
print(result)
```
will output:
```
7.5
```
Where result will automatically be inferred as a double, since it is the result of adding an int and a double.

#### String concatenation
Similarly to int to double promotion, when using the `+` operator with strings, the language will automatically concatenate them into a result of type `string`. For example:
```
greeting = "Hello, "
name = "FIKA"
message = greeting + name
print(message)
```
will output:
```
Hello, FIKA
```

When using the `+` operator with a string and a non-string type, the non-string type will be converted to a string before concatenation. For example:
```
number = 42
message = "The answer is: " + number
print(message)
```
will output:
```
The answer is: 42
```
with `message` being inferred as a string automatically.

#### Function type inference
FIKA also supports inference of function return types. If a function does not have an explicitly specified return type, the language will infer the return type based on the types of the values returned by the function. For example:
```
func add(a, b) {
    return a + b
}
```
In this example, the return type of the `add` function is inferred based on the types of `a` and `b`. If both `a` and `b` are integers, the return type will be inferred as `int`. If one of them is a double, the return type will be inferred as `double`.

Likewise, the types of the parameters `a` and `b` can also be inferred based on the context in which the function is called. For example, if we call `add(5, 10)`, the language will infer that both `a` and `b` are of type `int`. If we call `add(5.0, 10)`, the language will infer that `a` is of type `double` and `b` is of type `int`, and the return type will be inferred as `double`.

For instance, in the following example:
```
func mixOfTypes(a, b, c) {
    return c
}
mixOfTypes(5, 2.5, "hello")
```
The types of the parameters `a`, `b`, and `c` will be inferred as `int`, `double`, and `string` respectively, based on the types of the arguments passed to the function. The return type of the function will be inferred as `string`, since it returns the value of `c`, which is a string.
The same function could also be defined with explicit types:
```
string func mixOfTypes(int a, double b, string c) {
    return c
}
```




## Standard Library
The language has a limited standard library, that includes one basic function for output, `print`, which can be used to display values to the console. The `print` function can take any type of argument and will convert it to a string representation before outputting it.

Example:
```
print("Hello, FIKA!")
print(42)
print([1, 2, 3])
print("1 + 2 = " + (1 + 2)")
```
will output:
```
Hello, FIKA!
42
[1, 2, 3]
1+2 = 3
```