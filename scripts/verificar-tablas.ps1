$ErrorActionPreference = "Continue"

$bases = @(
    @{ Servicio = "Clientes"; Contenedor = "mysql-cliente"; BaseDatos = "cliente_db"; Tabla = "cliente" },
    @{ Servicio = "Servicio tecnico"; Contenedor = "mysql-servicio-tecnico"; BaseDatos = "servicio_tecnico_db"; Tabla = "orden_servicio" },
    @{ Servicio = "Usuarios"; Contenedor = "mysql-usuario"; BaseDatos = "usuario_db"; Tabla = "usuario" },
    @{ Servicio = "Bicicletas"; Contenedor = "mysql-bicicleta"; BaseDatos = "bicicleta_db"; Tabla = "bicicleta" },
    @{ Servicio = "Arriendos"; Contenedor = "mysql-arriendo"; BaseDatos = "arriendo_db"; Tabla = "arriendo" },
    @{ Servicio = "Inventario"; Contenedor = "mysql-inventario"; BaseDatos = "inventario_db"; Tabla = "inventario" },
    @{ Servicio = "Ventas"; Contenedor = "mysql-venta"; BaseDatos = "venta_db"; Tabla = "venta" },
    @{ Servicio = "Despachos"; Contenedor = "mysql-despacho"; BaseDatos = "despacho_db"; Tabla = "despacho" },
    @{ Servicio = "Pagos"; Contenedor = "mysql-pago"; BaseDatos = "pago_db"; Tabla = "pago" },
    @{ Servicio = "Proveedores"; Contenedor = "mysql-proveedor"; BaseDatos = "proveedor_db"; Tabla = "proveedor" }
)

foreach ($base in $bases) {
    $running = docker inspect -f '{{.State.Running}}' $base.Contenedor 2>$null

    if ($running -ne "true") {
        Write-Host "[ERROR] $($base.Servicio) -> contenedor no esta corriendo: $($base.Contenedor)"
        continue
    }

    $query = "SHOW TABLES; SELECT COUNT(*) AS registros FROM $($base.Tabla);"

    Write-Host ""
    Write-Host "[$($base.Servicio)] $($base.BaseDatos)"
    docker exec $base.Contenedor mysql -uroot -proot123 $base.BaseDatos -e $query
}
