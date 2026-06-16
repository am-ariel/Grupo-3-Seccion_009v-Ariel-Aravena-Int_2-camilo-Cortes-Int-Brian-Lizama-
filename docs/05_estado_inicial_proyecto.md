# 05 Estado inicial proyecto

## Resumen actualizado

El proyecto tiene 10 microservicios reales con estructura CSR, configuracion minima, migraciones Flyway y participacion en Docker Compose. En esta fase se completo el minimo exigido por la rubrica.

## Microservicios completos

| Microservicio | Carpeta | Puerto | Estado |
| --- | --- | ---: | --- |
| Clientes | `masterbike-vm` | 8081 | Completo: controller, service, repository, model, properties, Flyway, Dockerfile. |
| Atencion | `masterbike-vm-atencion` | 8082 | Completo: controller, service, repository, model, properties, Flyway, Dockerfile. |
| Usuarios | `masterbikes-usuarios` | 8083 | Completo: controller, service, repository, model, properties, Flyway, Dockerfile. |
| Bicicletas | `masterbikes-bicicletas` | 8084 | Completo: controller, service, repository, model, properties, Flyway, Dockerfile. |
| Arriendos | `masterbikes-arriendos` | 8085 | Completo: controller, service, repository, model, properties, Flyway, Dockerfile. |
| Inventario | `masterbikes-inventario` | 8086 | Completo: controller, service, repository, model, properties, Flyway, Dockerfile. |
| Ventas | `masterbikes-ventas` | 8087 | Completo: controller, service, repository, model, properties, Flyway, Dockerfile. |
| Despachos | `masterbikes-despachos` | 8088 | Completo: controller, service, repository, model, properties, Flyway, Dockerfile. |
| Pagos | `masterbikes-pagos` | 8089 | Completo y con Feign hacia ventas. |
| Proveedores | `masterbikes-proveedores` | 8090 | Completo: controller, service, repository, model, properties, Flyway, Dockerfile. |

## Incompleto

| Microservicio | Carpeta | Problema exacto |
| --- | --- | --- |
| Ninguno detectado en esta fase | - | `masterbike-vm-atencion` ya cuenta con `src/main/java`, `src/main/resources`, Flyway y CRUD. |

## Faltante

| Microservicio | Carpeta esperada | Problema exacto |
| --- | --- | --- |
| Ninguno detectado en esta fase | - | `masterbike-vm` fue creado como servicio de clientes. |

## Estado de `docker-compose.yml`

Compose define 10 bases de datos y 10 servicios de aplicacion. Despues de esta fase:

- `masterbikes-clientes` apunta a `./masterbike-vm`, carpeta existente.
- `masterbikes-servicio-tecnico` apunta a `./masterbike-vm-atencion`, carpeta existente con codigo fuente.
- `masterbikes-servicio-tecnico` mantiene dependencia de arranque sobre `masterbikes-clientes`.
- `docker compose config` se ejecuto correctamente y ya no reporta carpetas inexistentes.

## Faltantes transversales

- No hay pruebas unitarias.
- No hay JaCoCo.
- No hay Swagger/OpenAPI.
- No hay HATEOAS.
- No hay API Gateway.
- No hay `application.yml`.
- El build Docker no se pudo completar porque Docker Desktop no estaba activo en el entorno local.

## Cambios previos detectados

Antes de esta fase ya existian cambios no realizados por esta ejecucion:

- `.gitignore`
- `masterbikes-arriendos/dockerfile`

Regla: no revertir ni eliminar nada sin instruccion explicita.
