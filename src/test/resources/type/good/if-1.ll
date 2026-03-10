declare i32 @printf(i8*, ...)
declare double @pow(double, double)

@.fmt.int = private constant [4 x i8] c"%d\0A\00"
@.fmt.double = private constant [4 x i8] c"%f\0A\00"
@.fmt.string = private constant [4 x i8] c"%s\0A\00"
@.fmt.newline = private constant [2 x i8] c"\0A\00"

define void @main() {
entry:
  %x.addr = alloca i32
  store i32 2, i32* %x.addr
  %r_0 = load i32, i32* %x.addr
  %r_1 = icmp eq i32 %r_0, 2
  br i1 %r_1, label %then_2, label %else_3
then_2:
  store i32 3, i32* %x.addr
  br label %end_4
else_3:
  br label %end_4
end_4:
  ret void
}

