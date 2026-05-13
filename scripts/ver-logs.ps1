param(
    [string]$Servicio = "",
    [int]$Tail = 100,
    [switch]$Seguir
)

$ErrorActionPreference = "Stop"
$RepoRoot = Split-Path -Parent $PSScriptRoot
Set-Location $RepoRoot

$dockerArgs = @("compose", "logs", "--tail", "$Tail")

if ($Seguir) {
    $dockerArgs += "-f"
}

if ($Servicio) {
    $dockerArgs += $Servicio
}

docker @dockerArgs
