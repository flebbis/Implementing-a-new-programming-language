int func wrongParameter(int x, bool y) {
    return x + 1;
}

wrongParameter(5, 10); // should be invalid, second parameter should be a bool, not an int