// Large Integration Test
int result = 0
int max = 100
func multiply(int a, int b) {
    return a * b
}
func factorial(int n) {
    if (n <= 1) {
        return 1
    }
    return n * factorial(n - 1)
}
// Main logic
int i = 0
while (i < max) {
    if (i % 2 == 0) {
        result += i
    } else {
        result -= i
    }
    // Check prime-ish logic or similar
    if (result > 1000) {
        result = 0
    }
    i++
}
int fact10 = factorial(10)
// Logic tests
bool flag = true
if (flag and (result < 500 or i == max)) {
    // Success path
}
do 5 {
    // Repeat action
    fact10 = fact10 + 1
}
