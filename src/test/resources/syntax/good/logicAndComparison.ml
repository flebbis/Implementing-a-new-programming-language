// Complex Boolean Expressions
bool a = true
bool b = false
bool c = true
// AND, OR
bool result1 = a and b
bool result2 = a or b
// Negation
bool result3 = not a
bool result4 = not (a or b)
// Comparisons
int x = 10
int y = 5
bool test1 = x > y
bool test2 = x <= y
bool test3 = x == 10
bool test4 = x != y
// Combined
if ((x > 5 and y < 10) or (x == 10)) {
    // Should enter here
}
// Order of operations (AND binds tighter or parenthesized)
if (a or b and c) {
    // Depends on precedence rules
}
if ((a or b) and c) {
    // Explicit grouping
}
