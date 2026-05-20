param(
    [string]$Source = ".\src\test\resources\syntax\good\good.fika",
    [string]$ProjectRoot = $PSScriptRoot  # fallback när man kör direkt
)

$ErrorActionPreference = "Stop"
$PSNativeCommandUseErrorActionPreference = $false

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path

$jar = Join-Path $scriptDir "LLVMINI-1.0-SNAPSHOT.jar"
# Ändra runtime-raden:
$runtime = Join-Path $ProjectRoot "runtime\runtime.c"

if (!(Test-Path $Source)) {
    throw "Source file not found: $Source"
}

$ll = $Source -replace '\.fika$', '.ll'
$exeName = [System.IO.Path]::GetFileNameWithoutExtension($Source) + ".exe"
$exe = Join-Path "." $exeName

# Run compiler — hide stdout (JSON for LSP), let stderr (type errors) show in terminal
java -jar $jar $Source > $null
if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed." -ForegroundColor Red
    exit 1
}

if (!(Test-Path $ll)) {
    Write-Host "Cannot run: errors in $Source" -ForegroundColor Red
    exit 1
}

$clangOutput = & clang -Wno-override-module $ll $runtime -o $exe 2>&1
$clangExit = $LASTEXITCODE

if ($clangExit -ne 0) {
    $clangOutput
    throw "Clang compilation failed."
}

& $exe
exit $LASTEXITCODE