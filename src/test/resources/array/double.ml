

x = [1.5]
do 1000 {
    x.append(1.5)
}
x[500] = 38.18
do 10000 {
    x.append(1.51)
}
x[9000] = 85.37

y = x
do 100000 {
    x.append(1.5)
}

x[99000] = 3999999.43

do 3000000 {
    x.append(1.5)
}

x[2999999] = 2.43

print(x[500])
print(x[9000])
print(x[99000])
print(x[2999999])
print(x[2937652])
print(x[1])
print(x[10023])
print (y[99000])


