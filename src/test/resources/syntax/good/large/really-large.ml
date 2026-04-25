// =========================
// MiniLang Test Suite
// Prints "true" for PASS and "false" for FAIL
// =========================

// -------------------------
// Test helpers
// -------------------------

func pass() { print("true") }
func fail() { print("false") }

func assertTrue(cond) {
    if cond { pass() } else { fail() }
}

func assertFalse(cond) {
    if cond { fail() } else { pass() }
}

// Basic equality helpers for types you can compare directly
func assertEqInt(a, b) { assertTrue(a == b) }
func assertEqDouble(a, b) { assertTrue(a == b) }
//func assertEqBool(a, b) { assertTrue(a == b) }
//func assertEqString(a, b) { assertTrue(a == b) }

// -------------------------
// Basic literal + precedence tests
// -------------------------

assertEqInt(1, 1)
assertFalse(1 == 2)

assertEqDouble(1.5, 1.5)
assertFalse(1.5 == 2.5)

//assertEqBool(true, true)
//assertEqBool(false, false)
//assertFalse(true == false)

//assertEqString("a", "a")
//assertFalse("a" == "b")

// precedence: * before +
assertEqInt(2 + 3 * 4, 14)
assertEqInt((2 + 3) * 4, 20)

// subtraction associativity
assertEqInt(10 - 3 - 2, 5) // (10-3)-2

// division: int division expected if both int
assertEqInt(7 / 2, 3)

// modulo
assertEqInt(17 % 5, 2)

// power: right associative by grammar (powerExpr: unaryExpr (POWER powerExpr)?)
assertEqInt(2 ** 3, 8)
assertEqInt(2 ** 3 ** 2, 512) // 2 ** (3 ** 2) = 2 ** 9 = 512

// comparisons
assertTrue(1 < 2)
assertTrue(2 > 1)
assertTrue(2 <= 2)
assertTrue(2 >= 2)
assertFalse(3 < 1)
assertFalse(1 > 3)

// boolean logic
assertTrue(true and true)
assertFalse(true and false)
assertTrue(true or false)
assertFalse(false or false)
assertTrue(not false)
assertFalse(not true)

// precedence: not > and > or (via grammar: unaryExpr then andExpr then orExpr)
assertTrue(not false and true)
assertTrue(false or true and true) // true and true first => true

// string escape tests (your codegen handles \n \t \r \b \f \" \\)
//assertEqString("a\\nb", "a\nb")
//assertEqString("tab:\\tX", "tab:\tX")
//assertEqString("quote: \\\"", "quote: \"")
//assertEqString("slash: \\\\", "slash: \\")

// -------------------------
// Variables + assignments
// -------------------------

x = 10
assertEqInt(x, 10)

x = x + 5
assertEqInt(x, 15)

x += 5
assertEqInt(x, 20)

x -= 3
assertEqInt(x, 17)

x *= 2
assertEqInt(x, 34)

x /= 2
assertEqInt(x, 17)

// postfix inc/dec
i = 0
i++
assertEqInt(i, 1)
i++
assertEqInt(i, 2)
i--
assertEqInt(i, 1)

// -------------------------
// if / else if / else nesting
// -------------------------

a = 5
b = 10
c = 0

if a < b {
    c = 1
} else {
    c = 2
}
assertEqInt(c, 1)

if a > b {
    c = 100
} //else if (a == 5) {
   // c = 200
} else {
    c = 300
}
assertEqInt(c, 200)

// nested if inside if
z = 0
if true {
    if true {
        z = 7
    } else {
        z = 8
    }
} else {
    z = 9
}
assertEqInt(z, 7)

// -------------------------
// Loops: while + do
// -------------------------

sum = 0
n = 1
while n <= 10 {
    sum = sum + n
    n++
}
assertEqInt(sum, 55)

// do loop: do <exp> <stmt>
cnt = 0
do 10 {
    cnt++
}
assertEqInt(cnt, 10)

// nested do loops
outer = 0
innerTotal = 0
do 5 {
    outer++
    inner = 0
    do 3 {
        inner++
        innerTotal++
    }
    assertEqInt(inner, 3)
}
assertEqInt(outer, 5)
assertEqInt(innerTotal, 15)

// -------------------------
// Functions: simple, recursion, nested calls
// -------------------------

int func id(v) { return v }
assertEqInt(id(123), 123)

int func add2(x, y) { return x + y }
assertEqInt(add2(5, 7), 12)

// nested call depth: f(g(h(x)))
int func h(x) { return x + 1 }
int func g(x) { return x * 2 }
int func f(x) { return x - 3 }
assertEqInt(f(g(h(10))), f(g(11)))     // sanity
assertEqInt(f(g(h(10))), ( (10 + 1) * 2 ) - 3)

// recursion: factorial
int func fact(n) {
    if n <= 1 {
        return 1
    } else {
        return n * fact(n - 1)
    }
}
assertEqInt(fact(1), 1)
assertEqInt(fact(5), 120)

// recursion: fibonacci (small)
int func fib(n) {
    if n <= 1 {
        return n
    } else {
        return fib(n - 1) + fib(n - 2)
    }
}
assertEqInt(fib(0), 0)
assertEqInt(fib(1), 1)
assertEqInt(fib(6), 8)

// function that returns bool
bool func isEven(n) {
    return (n % 2) == 0
}
assertTrue(isEven(0))
assertTrue(isEven(10))
assertFalse(isEven(11))

// -------------------------
// Arrays: literals, indexing, assignment
// -------------------------

arr = [1, 2, 3, 4, 5]
assertEqInt(arr[0], 1)
assertEqInt(arr[4], 5)

arr[0] = 99
assertEqInt(arr[0], 99)

arr[4] = arr[0] + 1
assertEqInt(arr[4], 100)

// array passed into function (aliasing expected: same underlying struct pointer)
[int] func setFirstTo7(a) {
    a[0] = 7
    return a
}

brr = [0, 1, 2]
brr2 = setFirstTo7(brr)
assertEqInt(brr[0], 7)
assertEqInt(brr2[0], 7)

// -------------------------
// Arrays: append + growth + integrity
// -------------------------

x = [5]
x.append(1)
x.append(2)
x.append(3)
assertEqInt(x[0], 5)
assertEqInt(x[1], 1)
assertEqInt(x[2], 2)
assertEqInt(x[3], 3)

// force multiple grows; check a few sentinel values
d = [0]
k = 0
do 2000 {
    d.append(k)
    k++
}
assertEqInt(d[0], 0)
assertEqInt(d[1], 0)
assertEqInt(d[2], 1)
assertEqInt(d[100], 99)
assertEqInt(d[1999], 1998)

// aliasing after assignment y = x
p = [1]
do 100 {
    p.append(7)
}
q = p
p[50] = 12345
assertEqInt(q[50], 12345) // should be same array (reference semantics)

// -------------------------
// Arrays of double
// -------------------------

dx = [1.5]
do 200 {
    dx.append(1.5)
}
dx[50] = 9.25
assertEqDouble(dx[0], 1.5)
assertEqDouble(dx[50], 9.25)
assertEqDouble(dx[150], 1.5)

// -------------------------
// Arrays of string (your earlier example style)
// -------------------------

sx = ["A"]
sx.append("B")
sx.append("C")
assertEqString(sx[0], "A")
assertEqString(sx[1], "B")
assertEqString(sx[2], "C")

do 1000 {
    sx.append("Z")
}
sx[500] = "MID"
sx[900] = "END"
assertEqString(sx[500], "MID")
assertEqString(sx[900], "END")
assertEqString(sx[1002], "Z") // after initial A,B,C plus many Zs

// -------------------------
// Mixed control flow + function + arrays
// -------------------------

int func countEvensUpTo(n) {
    i = 0
    c = 0
    while i <= n {
        if (i % 2) == 0 {
            c++
        }
        i++
    }
    return c
}
assertEqInt(countEvensUpTo(0), 1)
assertEqInt(countEvensUpTo(1), 1)
assertEqInt(countEvensUpTo(2), 2)
assertEqInt(countEvensUpTo(10), 6)

// build array in function
[int] func buildRange(n) {
    r = [0]
    i = 1
    while i <= n {
        r.append(i)
        i++
    }
    return r
}

rr = buildRange(50)
assertEqInt(rr[0], 0)
assertEqInt(rr[1], 1)
assertEqInt(rr[10], 10)
assertEqInt(rr[50], 50)

// -------------------------
// Deep nesting of blocks / conditionals
// -------------------------

deep = 0
if true {
    if true {
        if true {
            do 5 {
                deep++
                if deep == 3 {
                    deep += 10
                } else {
                    deep += 1
                }
            }
        }
    }
}
assertTrue(deep > 0)
assertTrue(deep >= 5)

// -------------------------
// End marker (optional)
// -------------------------
print("true")