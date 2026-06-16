# Analisis de estado inicial

## Estructura observada

Servicios con codigo fuente Java visible:

| Carpeta | Dominio | Puerto documentado | Estado inicial |
| --- | --- | ---: | --- |
| `masterbikes-usuarios` | Usuarios | 8083 | Codigo fuente, CRUD, validaciones, Flyway, Dockerfile. |
| `masterbikes-bicicletas` | Bicicletas | 8084 | Codigo fuente, CRUD, validaciones, Flyway, Dockerfile. |
| `masterbikes-arriendos` | Arriendos | 8085 | Codigo fuente, CRUD, regla de fechas, Flyway, Dockerfile. |
| `masterbikes-inventario` | Inventario | 8086 | Codigo fuente, CRUD, regla de stock/aumento, endpoint adicional por aumento. |
| `masterbikes-ventas` | Ventas | 8087 | Codigo fuente, CRUD, validaciones, Flyway, Dockerfile. |
| `masterbikes-despachos` | Despachos | 8088 | Codigo fuente, CRUD, validaciones, Flyway, Dockerfile. |
| `masterbikes-pagos` | Pagos | 8089 | Codigo fuente, CRUD, Feign hacia ventas, regla de pago unico por venta. |
| `masterbikes-proveedores` | Proveedores | 8090 | Codigo fuente, CRUD, busqueda por RUT, validaciones, Flyway, Dockerfile. |

Carpetas inconsistentes o incompletas:

- `masterbike-vm-atencion`: existe, pero solo se observaron `pom.xml`, wrappers y `dockerfile`; no hay `src/main/java`.
- `masterbike-vm`: aparece en `README.md` y `docker-compose.yml` como servicio de clientes en puerto 8081, pero no existe en el arbol actual.

## Arquitectura y capas

El patron CSR esta parcialmente bien encaminado en los servicios con codigo:

- `controllers`: exponen rutas REST y usan `@Valid`.
- `services`: concentran reglas como fechas de arriendo, stock minimo y validacion de venta antes de pago.
- `repositories`: extienden JPA.
- `models`: entidades JPA con validaciones Bean Validation.
- `exceptions`: hay `GlobalExceptionHandler` por servicio.

Riesgo: se debe revisar si todos los services tienen reglas reales y si los handlers son consistentes entre servicios.

## Endpoints REST visibles

La mayoria de servicios implementan CRUD:

- `GET /`
- `GET /{id}`
- `POST /`
- `PUT /{id}`
- `DELETE /{id}`

Endpoints adicionales detectados o documentados:

- Inventario: `GET /api/v1/inventario/aumento/{aumento}`.
- Proveedores: `GET /api/v1/proveedores/rut/{rut}`.
- README menciona Clientes y Servicio Tecnico, pero el codigo fuente de esos servicios no esta disponible en el estado inicial.

## Comunicacion entre microservicios

Evidencia positiva:

- `masterbikes-pagos` usa OpenFeign (`VentaClient`) para consultar ventas antes de registrar o actualizar un pago.
- `PagoService` maneja `FeignException.NotFound` como `404` y otros errores Feign como `502 Bad Gateway`.

Brecha:

- La comunicacion servicio tecnico -> clientes esta mencionada en README, pero no se puede validar porque falta codigo fuente de clientes y atencion.
- No se observaron timeouts configurados.

## Persistencia y datos

Evidencia positiva:

- Cada servicio visible tiene `application.properties`.
- Hay migraciones Flyway `V1__create_*` y `V2__insert_*`.
- `docker-compose.yml` define una base MySQL por servicio con volumen dedicado.

Riesgos:

- Compose referencia servicios ausentes/incompletos.
- La configuracion esta en `.properties`, pero la rubrica pide YAML para rutas, perfiles, variables y puertos.

## Swagger/OpenAPI

Estado inicial: no implementado.

No se detecto:

- Dependencia `springdoc-openapi-starter-webmvc-ui`.
- Configuracion de Swagger UI.
- Anotaciones `@Tag`, `@Operation`, `@ApiResponse`, `@Schema`.

Impacto: afecta IE 3.2.1 (4%) y defensa IE 3.2.2 (5%).

## HATEOAS

Estado inicial: no implementado.

No se detecto:

- Dependencia `spring-boot-starter-hateoas`.
- Assemblers.
- `EntityModel`, `CollectionModel`, `linkTo`, `methodOn`.
- Endpoints versionados `/api/v2`.

Impacto: no aparece como indicador separado en la rubrica final, pero las guias lo piden como actividad del modulo. Recomendacion: implementar HATEOAS en uno o dos servicios representativos si el tiempo alcanza, idealmente inventario o pagos.

## Pruebas unitarias

Estado inicial: no implementado.

Evidencia:

- Hay dependencias `spring-boot-starter-test` en los `pom.xml`.
- No se encontraron carpetas `src/test`.
- No se encontraron archivos `*Test.java`.
- No se detecto JaCoCo.

Impacto: riesgo critico por IE 3.1.1 (8%), IE 3.1.2 (7%) e IE 3.1.3 (13%).

Servicios candidatos para primeras pruebas:

- `PagoService`: venta inexistente, error remoto, pago duplicado, guardado exitoso.
- `ArriendoService`: fecha fin anterior a fecha inicio.
- `InventarioService`: marca `aumento` cuando `stock <= stockMinimo`.
- `ProveedorService`: RUT duplicado si el repositorio lo valida.

## API Gateway y YAML

Estado inicial: no implementado.

No se detecto:

- Microservicio Gateway.
- Dependencia `spring-cloud-starter-gateway`.
- `application.yml` con rutas.
- Rutas centralizadas por Gateway.

Impacto: afecta IE 3.3.2 (5%), IE 3.3.3 (2%), IE 3.3.4 (3%), IE 3.3.5 (4%) e IE 3.3.7 (10%).

## Despliegue

Evidencia positiva:

- Existe `docker-compose.yml`.
- Cada servicio visible tiene `dockerfile`.
- Hay scripts PowerShell para iniciar, detener, revisar estado, logs y probar APIs.

Riesgos:

- `docker-compose.yml` no es confiable mientras referencie `./masterbike-vm` ausente.
- `masterbike-vm-atencion` no tiene codigo fuente, por lo que su build podria fallar.
- No se verifico ejecucion de Docker en esta etapa.
- No hay evidencia de despliegue remoto.

## Estado Git

Se detectaron cambios previos no realizados en esta etapa:

- `.gitignore`
- `masterbikes-arriendos/dockerfile`

Decision: preservarlos y no revertirlos.

## Diagnostico general

El proyecto tiene una base CRUD con buenas piezas para defender CSR, validaciones, Flyway, Docker y una comunicacion Feign real en pagos. Sin embargo, frente a la rubrica de Evaluacion Parcial 3, faltan elementos centrales: pruebas unitarias, Swagger/OpenAPI, API Gateway, YAML y cierre de despliegue. Tambien hay una inconsistencia estructural fuerte por servicios mencionados pero ausentes o incompletos.
