declare i32 @printf(i8*, ...)
declare double @pow(double, double)

@.fmt.int = private constant [4 x i8] c"%d\0A\00"
@.fmt.double = private constant [4 x i8] c"%f\0A\00"
@.fmt.string = private constant [4 x i8] c"%s\0A\00"
@.fmt.newline = private constant [2 x i8] c"\0A\00"

define void @main() {
entry:
  %r_0 = mul double 0, 3.3
  %r_1 = sdiv double 0, 1.1
  %r_2 = add double 0, 3.3
  %r_3 = sub double 0, 1.1
  ret void
}

