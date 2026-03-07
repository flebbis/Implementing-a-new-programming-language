// New Integration Test combining many features
int i = 0
int max = 100
int result = 0
// Recursive function definition
int func factorial(int n) {
    if (n <= 1) {
        return 1
    }
    return n * factorial(n - 1)
}
// Complex loop with nested conditions
while (i < max) {
    if (i % 2 == 0) {
        result = result + factorial(i % 5)
    } else {
        result = result - (i * 2)
    }
    i++
}
// Logic check
bool success = false
if (result > 0) {
    success = true
} else if (result == 0) {
    // Edge case
    success = true
} else {
    success = false
}
// Nested Do and While
int j = 0
while (j < 10) {
    do 3 {
        // Increment nested counter or perform logic
        j++
    }
    if (j > 5) {
        j = 20 // break condition
    }
}
