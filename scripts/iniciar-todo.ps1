param(
    [switch]$SinBuild
)

$ErrorActionPreference = "Stop"
$RepoRoot = Split-Path -Parent $PSScriptRoot
Set-Location $RepoRoot

Write-Host "Iniciando contenedores desde: $RepoRoot"

if ($SinBuild) {
    docker compose up -d
} else {
    docker compose up -d --build
}

if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

docker compose ps
