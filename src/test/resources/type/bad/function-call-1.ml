int func wrongType() {
    return 5;
}

bool s = wrongType(); // should be invalid, wrongType returns an int but s is a bool