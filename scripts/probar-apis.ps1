$ErrorActionPreference = "Continue"

$apis = @(
    @{ Nombre = "Clientes"; Url = "http://localhost:8081/api/v1/clientes" },
    @{ Nombre = "Servicio tecnico"; Url = "http://localhost:8082/api/v1/ordenes-servicio" },
    @{ Nombre = "Usuarios"; Url = "http://localhost:8083/api/v1/usuarios" },
    @{ Nombre = "Bicicletas"; Url = "http://localhost:8084/api/v1/bicicletas" },
    @{ Nombre = "Arriendos"; Url = "http://localhost:8085/api/v1/arriendos" },
    @{ Nombre = "Inventario"; Url = "http://localhost:8086/api/v1/inventario" },
    @{ Nombre = "Ventas"; Url = "http://localhost:8087/api/v1/ventas" },
    @{ Nombre = "Despachos"; Url = "http://localhost:8088/api/v1/despachos" }
)

foreach ($api in $apis) {
    try {
        $response = Invoke-WebRequest -Uri $api.Url -UseBasicParsing -TimeoutSec 10
        Write-Host "[OK] $($api.Nombre) -> HTTP $($response.StatusCode) $($api.Url)"
    } catch {
        Write-Host "[ERROR] $($api.Nombre) -> $($api.Url)"
        Write-Host "        $($_.Exception.Message)"
    }
}
