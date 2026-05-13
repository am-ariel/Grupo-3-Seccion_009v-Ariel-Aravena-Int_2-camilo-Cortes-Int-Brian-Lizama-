param(
    [switch]$Volumenes
)

$ErrorActionPreference = "Stop"
$RepoRoot = Split-Path -Parent $PSScriptRoot
Set-Location $RepoRoot

$dockerArgs = @("compose", "down")

if ($Volumenes) {
    $dockerArgs += "-v"
}

docker @dockerArgs
