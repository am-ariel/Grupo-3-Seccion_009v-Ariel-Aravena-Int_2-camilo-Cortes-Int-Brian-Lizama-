$ErrorActionPreference = "Stop"
$RepoRoot = Split-Path -Parent $PSScriptRoot
Set-Location $RepoRoot

docker compose ps
