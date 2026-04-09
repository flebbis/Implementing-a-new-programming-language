#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <locale.h>
#ifdef _WIN32
#include <windows.h>
#include <io.h>
#include <fcntl.h>
#endif

// Ensure console uses UTF-8 so characters like å, ä, ö render correctly without manual shell tweaks
static void runtime_set_utf8(void) {
#ifdef _WIN32
    SetConsoleOutputCP(CP_UTF8);
    SetConsoleCP(CP_UTF8);
    setlocale(LC_ALL, ".UTF-8");
    _setmode(_fileno(stdout), _O_BINARY);
#else
    setlocale(LC_ALL, "");
#endif
}

__attribute__((constructor))
static void runtime_init(void) {
    runtime_set_utf8();
}

// out of bounds for array
void array_index_out_of_bounds(int index, int size) {
    fprintf(stderr, "\033[31mERROR:\033[0m Index %d out of bounds for array of size %d\n", index, size);
    fflush(stderr);
    exit(1);
}

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

// for array int to string
char* array_int_to_string(int* arr, int len) {
    if (len == 0) {
        char* empty = malloc(3);
        if (empty == NULL) return NULL;
        empty[0] = '[';
        empty[1] = ']';
        empty[2] = '\0';
        return empty;
    }
    // pessimistic buffer: each int up to 11 chars incl sign, plus comma/space
    size_t buf_size = (size_t)len * 14 + 3;
    char* buf = malloc(buf_size);
    char* p = buf;
    *p++ = '[';
    for(int i = 0; i < len; i++) {
        int written = sprintf(p, "%d", arr[i]);
        p += written;
        if(i != len - 1) {
            *p++ = ',';
            *p++ = ' ';
        }
    }
    *p++ = ']';
    *p = '\0';
    return buf;
}

// for array double to string
char* array_double_to_string(double* arr, int len) {
    if (len == 0) {
        char* empty = malloc(3);
        if (empty == NULL) return NULL;
        empty[0] = '[';
        empty[1] = ']';
        empty[2] = '\0';
        return empty;
    }
    // each double approx 24 chars incl decimals/sign, plus comma/space
    size_t buf_size = (size_t)len * 28 + 3;
    char* buf = malloc(buf_size);
    char* p = buf;
    *p++ = '[';
    for(int i = 0; i < len; i++) {
        int written = sprintf(p, "%g", arr[i]);
        p += written;
        if(i != len - 1) {
            *p++ = ',';
            *p++ = ' ';
        }
    }
    *p++ = ']';
    *p = '\0';
    return buf;
}

// for array bool to string
char* array_bool_to_string(bool* arr, int len) {
    if (len == 0) {
        char* empty = malloc(3);
        if (empty == NULL) return NULL;
        empty[0] = '[';
        empty[1] = ']';
        empty[2] = '\0';
        return empty;
    }
    size_t buf_size = (size_t)len * 6 + 3; // "false" len 5 + comma/space
    char* buf = malloc(buf_size);
    char* p = buf;
    *p++ = '[';
    for(int i = 0; i < len; i++) {
        const char* lit = arr[i] ? "true" : "false";
        size_t written = strlen(lit);
        memcpy(p, lit, written);
        p += written;
        if(i != len - 1) {
            *p++ = ',';
            *p++ = ' ';
        }
    }
    *p++ = ']';
    *p = '\0';
    return buf;
}

char* array_string_to_string(char** arr, int len) {
    if (len == 0) {
        char* empty = malloc(3);
        if (empty == NULL) return NULL;
        empty[0] = '[';
        empty[1] = ']';
        empty[2] = '\0';
        return empty;
    }
    size_t buf_size = 2; // for [ and ]
    for(int i = 0; i < len; i++) {
        buf_size += strlen(arr[i]) + 4; // for quotes and comma/space
    }
    char* buf = malloc(buf_size);
    char* p = buf;
    *p++ = '[';
    for(int i = 0; i < len; i++) {
        *p++ = '"';
        size_t written = strlen(arr[i]);
        memcpy(p, arr[i], written);
        p += written;
        *p++ = '"';
        if(i != len - 1) {
            *p++ = ',';
            *p++ = ' ';
        }
    }
    *p++ = ']';
    *p = '\0';
    return buf;
}
