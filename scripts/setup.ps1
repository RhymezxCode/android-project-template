$ErrorActionPreference = "Stop"
$projectDir = Split-Path -Parent $PSScriptRoot
Set-Location $projectDir

if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    throw "Java is missing. Install JDK 17 or newer."
}

$sdkDir = if ($env:ANDROID_HOME) { $env:ANDROID_HOME } else { $env:ANDROID_SDK_ROOT }
if (-not $sdkDir -or -not (Test-Path $sdkDir)) {
    throw "Set ANDROID_HOME or ANDROID_SDK_ROOT to an installed Android SDK."
}

& .\gradlew.bat --version
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
Write-Host "Environment ready. Run .\scripts\verify.ps1 next."
