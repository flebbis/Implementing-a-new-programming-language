define void @main() {
entry:
  %x = alloca i32
  store i32 0, i32* %x
  %a = alloca i32
  %1 = load i32, i32* %x
  store i32 %1, i32* %a
  ret void
}

define i32 @increase(i32 %i, i32 %times) {
entry:
  %1 = load i32, i32* %times
  %counter_0 = alloca i32
  store i32 0, i32* %counter_0
  br label %cond_1
cond_1:
  %0 = load i32, i32* %counter_0
  %1 = icmp slt i32 %0, %1
  br i1 %1, label %body_2, label %end_3
body_2:
  %1 = load i32, i32* %i
  %2 = add i32 %0, 1
  store i32 %2, i32* %counter_0
  br label %cond_1
end_3:
  %1 = load i32, i32* %i
  ret i32 %1
}
