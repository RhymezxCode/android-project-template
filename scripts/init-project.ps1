param(
    [Parameter(Mandatory = $true)]
    [string]$ProjectName,
    [Parameter(Mandatory = $true)]
    [string]$PackageName
)

$ErrorActionPreference = "Stop"

if ($ProjectName -notmatch '^[A-Za-z][A-Za-z0-9_ -]*$') {
    throw "Project name must start with a letter and contain only letters, numbers, spaces, _ or -."
}
if ($PackageName -notmatch '^[a-z][a-z0-9_]*(\.[a-z][a-z0-9_]*)+$') {
    throw "Package must look like com.example.application and use lowercase segments."
}

$projectDir = Split-Path -Parent $PSScriptRoot
Set-Location $projectDir
$classPrefix = $ProjectName -replace '[^A-Za-z0-9]', ''
$oldPackagePath = Join-Path "com" (Join-Path "example" "modularapp")
$newPackagePath = $PackageName -replace '\.', [IO.Path]::DirectorySeparatorChar
$textExtensions = @('.kt', '.kts', '.xml', '.toml', '.md', '.yml', '.yaml', '.properties', '.gradle')

$files = Get-ChildItem -File -Recurse | Where-Object {
    $textExtensions -contains $_.Extension -and
    $_.FullName -notmatch '[\\/](\.git|\.gradle|build)[\\/]' -and
    $_.Name -notin @('init-project.sh', 'init-project.ps1')
}

foreach ($file in $files) {
    $content = [IO.File]::ReadAllText($file.FullName)
    $content = $content.Replace('com.example.modularapp', $PackageName)
    $content = $content.Replace('ModularAndroidTemplate', $classPrefix)
    $content = $content.Replace('Modular Android Starter', $ProjectName)
    $content = $content.Replace('StarterApplication', "${classPrefix}Application")
    $content = $content.Replace('StarterTheme', "${classPrefix}Theme")
    $content = $content.Replace('StarterApp', "${classPrefix}App")
    $content = $content.Replace('Theme.Starter', "Theme.${classPrefix}")
    [IO.File]::WriteAllText($file.FullName, $content)
}

$packageDirs = Get-ChildItem -Directory -Recurse | Where-Object {
    $_.FullName.EndsWith($oldPackagePath) -and
    $_.FullName -notmatch '[\\/](\.git|\.gradle|build)[\\/]'
} | Sort-Object { $_.FullName.Length } -Descending

foreach ($oldDir in $packageDirs) {
    $sourceRoot = Split-Path (Split-Path (Split-Path $oldDir.FullName -Parent) -Parent) -Parent
    $newDir = Join-Path $sourceRoot $newPackagePath
    New-Item -ItemType Directory -Force -Path (Split-Path $newDir -Parent) | Out-Null
    Move-Item $oldDir.FullName $newDir
    $oldParent = Join-Path $sourceRoot (Join-Path 'com' 'example')
    if ((Test-Path $oldParent) -and -not (Get-ChildItem $oldParent)) {
        Remove-Item $oldParent
    }
}

Get-ChildItem -File -Recurse -Filter 'StarterApplication.kt' | ForEach-Object {
    Rename-Item $_.FullName "${classPrefix}Application.kt"
}
Get-ChildItem -File -Recurse -Filter 'StarterTheme.kt' | ForEach-Object {
    Rename-Item $_.FullName "${classPrefix}Theme.kt"
}

Write-Host "Initialized $ProjectName ($PackageName)."
Write-Host "Run .\scripts\setup.ps1 and .\scripts\verify.ps1, then commit the generated changes."
