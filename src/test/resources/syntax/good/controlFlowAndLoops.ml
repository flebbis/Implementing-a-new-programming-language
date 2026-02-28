// Control Flow and Loops
int i = 0
int max = 10
// If-Else basic
if (i < max) {
    i = i + 1
}
// If-Else Block
if (i > max) {
    i = max
} else {
    i = i + 2
}
// Chained Logic
if (i == 0) {
    i = 1
} else if (i == 1) {
    i = 2
} else {
    i = 0
}
// While Loop
while (i < 20) {
    i++
}
// Do Loop (appears to be 'do N times')
do 5 {
    i--
}
// Nested Control Flow
while (i > 0) {
    if (i % 2 == 0) {
        i = i / 2
    } else {
        i = i - 1
    }
}
