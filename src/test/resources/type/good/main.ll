declare i32 @printf(i8*, ...)
declare double @pow(double, double)

@.fmt.int = private constant [4 x i8] c"%d\0A\00"
@.fmt.double = private constant [4 x i8] c"%f\0A\00"
@.fmt.string = private constant [4 x i8] c"%s\0A\00"
@.fmt.newline = private constant [2 x i8] c"\0A\00"

define void @main() {
entry:
  %x.addr = alloca i32
  store i32 4, i32* %x.addr
  %r_0 = load i32, i32* %x.addr
  %r_1 = call i32 @increase(i32 %r_0, i32 2)
  %r_2 = getelementptr [4 x i8], [4 x i8]* @.fmt.int, i32 0, i32 0
  %r_3 = call i32 (i8*, ...) @printf(i8* %r_2, i32 %r_1)
  ret void
}

define i32 @increase(i32 %a, i32 %x) {
entry:
  %a.addr = alloca i32
  store i32 %a, i32* %a.addr
  %x.addr = alloca i32
  store i32 %x, i32* %x.addr
  %r_4 = load i32, i32* %x.addr
  %counter_5 = alloca i32
  store i32 0, i32* %counter_5
  br label %cond_6
cond_6:
  %0 = load i32, i32* %counter_5
  %1 = icmp slt i32 %0, %r_4
  br i1 %1, label %body_7, label %end_8
body_7:
  %r_9 = load i32, i32* %a.addr
  %r_10 = add i32 %r_9, 1
  store i32 %r_10, i32* %a.addr
  %2 = add i32 %0, 1
  store i32 %2, i32* %counter_5
  br label %cond_6
end_8:
  %r_11 = load i32, i32* %a.addr
  ret i32 %r_11
}
