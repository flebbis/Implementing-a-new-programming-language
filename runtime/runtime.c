#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

// for EStringCast int to string
char* int_to_string(int x) {
    char* buf = malloc(32);
    sprintf(buf, "%d", x);
    return buf;
}

// for EStringCast double to string
char* double_to_string(double x) {
    char* buf = malloc(64);
    sprintf(buf, "%f", x);
    return buf;
}

// for EStringCast bool to string
char* bool_to_string(bool x) {
    if(x) {
        return "true";
    } else {
        return "false";
    }
}

// for "s" + "s"
char* string_concat(const char* a, const char* b) {
    size_t len1 = strlen(a);
    size_t len2 = strlen(b);
    char* result = malloc(len1 + len2 + 1);
    memcpy(result, a, len1);
    memcpy(result + len1, b, len2 + 1);
    return result;
}