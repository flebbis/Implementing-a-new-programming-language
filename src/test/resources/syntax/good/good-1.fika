declare i32 @printf(i8*, ...)
declare double @pow(double, double)
declare i8* @malloc(i64)
declare void @free(i8*)
declare i8* @int_to_string(i32)
declare i8* @double_to_string(double)
declare i8* @bool_to_string(i1)
declare i8* @string_concat(i8*, i8*)
declare i8* @array_int_to_string(i32*, i32)
declare i8* @array_double_to_string(double*, i32)
declare i8* @array_bool_to_string(i1*, i32)
declare i8* @array_string_to_string(i8*, i32)
declare void @array_index_out_of_bounds(i32, i32)

%array_i32 = type { i32, i32, i32* }
%array_double = type { i32, i32, double* }
%array_i1 = type { i32, i32, i1* }
%array_i8ptr = type { i32, i32, i8** }
declare %array_i32* @array_int_copy(%array_i32*)
declare %array_double* @array_double_copy(%array_double*)
declare %array_i1* @array_bool_copy(%array_i1*)
declare %array_i8ptr* @array_string_copy(%array_i8ptr*)

@.fmt.int = private constant [4 x i8] c"%d\0A\00"
@.fmt.double = private constant [4 x i8] c"%f\0A\00"
@.fmt.string = private constant [4 x i8] c"%s\0A\00"
@.fmt.newline = private constant [2 x i8] c"\0A\00"

define void @main() !dbg !2 {
entry:
%register0 = alloca void, !dbg !3
  #dbg_declare(ptr %register0, !5, !DIExpression(), !3)
  ret void, !dbg !3
}

!llvm.dbg.cu = !{!0}
!llvm.module.flags = !{!6}
!0 = distinct !DICompileUnit(language: DW_LANG_C, file: !1, producer: "mylang", isOptimized: false, runtimeVersion: 0, emissionKind: FullDebug)
!1 = !DIFile(filename: "good-1.ml", directory: ".")
!2 = distinct !DISubprogram(name: "main", scope: !1, file: !1, line: 1, type: !DISubroutineType(types: !{}), spFlags: DISPFlagDefinition, unit: !0, retainedNodes: !{})
!3 = !DILocation(line: 1, scope: !2)
!5 = !DILocalVariable(name: "declare", scope: !2, file: !1, line: 1, type: !4)
!4 = !DIBasicType(name: "string", size: 64, encoding: DW_ATE_address)
!6 = !{i32 2, !"Debug Info Version", i32 3}
