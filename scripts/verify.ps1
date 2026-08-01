$ErrorActionPreference = "Stop"
$projectDir = Split-Path -Parent $PSScriptRoot
Set-Location $projectDir

& .\gradlew.bat test lint detekt lintKotlin koverXmlReport koverVerify assembleDebug
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
