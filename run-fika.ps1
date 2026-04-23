param(
    [string]$Source = ".\src\test\resources\syntax\good\good.fika"
)

$ErrorActionPreference = "Stop"
$PSNativeCommandUseErrorActionPreference = $false

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path

$jar = Join-Path $scriptDir "LLVMINI-1.0-SNAPSHOT.jar"
$runtime = Join-Path $scriptDir "runtime\runtime.c"

if (!(Test-Path $Source)) {
    throw "Source file not found: $Source"
}

$ll = $Source -replace '\.fika$', '.ll'
$exeName = [System.IO.Path]::GetFileNameWithoutExtension($Source) + ".exe"
$exe = Join-Path "." $exeName

# Hide Java stdout (JSON), but still fail if Java exits non-zero
java -jar $jar $Source > $null
if ($LASTEXITCODE -ne 0) {
    throw "Java compilation failed."
}

if (!(Test-Path $ll)) {
    throw "LLVM IR file was not generated: $ll"
}

# Capture clang output instead of letting PowerShell treat stderr as fatal
$clangOutput = & clang -Wno-override-module $ll $runtime -o $exe 2>&1
$clangExit = $LASTEXITCODE

if ($clangExit -ne 0) {
    $clangOutput
    throw "Clang compilation failed."
}

# Run program normally so prints + runtime errors are visible
& $exe
$programExit = $LASTEXITCODE