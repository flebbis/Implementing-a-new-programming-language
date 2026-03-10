declare i32 @printf(i8*, ...)
declare double @pow(double, double)

@.fmt.int = private constant [4 x i8] c"%d\0A\00"
@.fmt.double = private constant [4 x i8] c"%f\0A\00"
@.fmt.string = private constant [4 x i8] c"%s\0A\00"
@.fmt.newline = private constant [2 x i8] c"\0A\00"

define void @main() {
entry:
  %r_0 = call i32 @inc(i32 5, i1 1, i8* ""hello"", i32 0)
  ret void
}

define i32 @inc(i32 %x, i1 %y, i8* %z, double %w) {
entry:
  %x.addr = alloca i32
  store i32 %x, i32* %x.addr
  %y.addr = alloca i1
  store i1 %y, i1* %y.addr
  %z.addr = alloca i8*
  store i8* %z, i8** %z.addr
  %w.addr = alloca double
  store double %w, double* %w.addr
  %r_1 = load i32, i32* %x.addr
  %r_2 = add i32 %r_1, 1
  ret i32 %r_2
}
