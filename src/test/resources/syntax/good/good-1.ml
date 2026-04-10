
func test2(x) {
    x.append(1)
    print(x)
}

func test(x) {
    x.append(100)
    print(x)
}

y = [1,2,3]
print(y)
test2(y)
test(y)

y.append(5)
print(y)