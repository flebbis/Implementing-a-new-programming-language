param(
    [string]$Source = ".\src\test\resources\syntax\good\good.fika"
)

$ErrorActionPreference = "Stop"

$jar = ".\LLVMINI-1.0-SNAPSHOT.jar"
$runtime = ".\runtime\runtime.c"

if (!(Test-Path $Source)) {
    throw "Source file not found: $Source"
}

$ll = $Source -replace '\.fika$', '.ll'
$exeName = [System.IO.Path]::GetFileNameWithoutExtension($Source) + ".exe"
$exe = Join-Path "." $exeName

Write-Host "Generating LLVM IR..."
java -jar $jar $Source
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

if (!(Test-Path $ll)) {
    throw "LLVM IR file was not generated: $ll"
}

Write-Host "Compiling with clang..."
clang $ll $runtime -o $exe
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

Write-Host "Running $exe ..."
& $exe