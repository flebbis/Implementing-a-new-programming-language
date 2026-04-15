

x = ["Bibliotek"]
do 1000 {
    x.append("Bibliotek")
}
x[500] = "Hushåll"
do 10000 {
    x.append("Bibliotek")
}
x[9000] = "Sakerna"

y = x
do 100000 {
    x.append("Bibliotek")
}

x[99000] = "Varför"

do 3000000 {
    x.append("Bibliotek")
}

x[2999999] = "KOMMER_DETTA_ATT_FUNGERA"

print(x[500])
print(x[9000])
print(x[99000])
print(x[2999999])
print(x[2937652])
print(x[1])
print(x[10023])
print (y[99000])