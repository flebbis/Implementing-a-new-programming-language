declare i32 @printf(i8*, ...)
declare double @pow(double, double)

@.fmt.int = private constant [4 x i8] c"%d\0A\00"
@.fmt.double = private constant [4 x i8] c"%f\0A\00"
@.fmt.string = private constant [4 x i8] c"%s\0A\00"
@.fmt.newline = private constant [2 x i8] c"\0A\00"

define void @main() {
entry:
  %i.addr = alloca i32
  store i32 2, i32* %i.addr
  %r_0 = load i32, i32* %i.addr
  %r_1 = add i32 %r_0, 2
  store i32 %r_1, i32* %i.addr
  %r_2 = load i32, i32* %i.addr
  %r_3 = getelementptr [4 x i8], [4 x i8]* @.fmt.int, i32 0, i32 0
  %r_4 = call i32 (i8*, ...) @printf(i8* %r_3, i32 %r_2)
  ret void
}

