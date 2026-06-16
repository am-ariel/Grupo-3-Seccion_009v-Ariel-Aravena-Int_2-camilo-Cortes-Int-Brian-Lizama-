# 16 Validacion compilacion Docker

## Objetivo

Validar que los 10 microservicios compilen y que Docker Compose este correcto antes de avanzar a pruebas unitarias, Swagger/OpenAPI o API Gateway.

## Comandos ejecutados

### Lectura y auditoria

```powershell
Get-Content -Raw contexto.md
Get-Content -Raw docs\06_plan_de_implementacion.md
Get-Content -Raw docs\07_mapa_del_codigo.md
Get-Content -Raw docs\08_trazabilidad_rubrica.md
Get-Content -Raw docs\14_plan_docker_despliegue.md
Get-Content -Raw docs\15_riesgos_y_pendientes.md
```

### Estructura minima

```powershell
Get-ChildItem -Recurse -Filter *Controller.java
Get-ChildItem -Recurse -Filter application.properties
```

Se verifico que los 10 microservicios tengan:

- `pom.xml`
- `src/main/java`
- `src/main/resources`
- clase `Application.java`
- controller
- service
- repository
- model/entity
- `application.properties`
- `dockerfile`
- migraciones Flyway

### Maven Wrapper

Se corrigio el wrapper existente de `masterbike-vm-atencion` agregando:

```text
masterbike-vm-atencion/.mvn/wrapper/maven-wrapper.properties
```

El wrapper se uso como ejecutor Maven para todos los `pom.xml` mediante `-f`.

Comando base usado:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21.0.10'
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
.\masterbike-vm-atencion\mvnw.cmd -f <microservicio>\pom.xml clean package -DskipTests -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true
```

## Resultado por microservicio

| Microservicio | Puerto app | Puerto BD | Ruta base | Estado compilacion | Estado Docker |
| ------------- | ---------: | --------: | --------- | ------------------ | ------------- |
| `masterbike-vm` | 8081 | 3306 | `/api/v1/clientes` | OK `clean package -DskipTests` | Compose OK; build pendiente por Docker Desktop inactivo |
| `masterbike-vm-atencion` | 8082 | 3307 | `/api/v1/atenciones` | OK `clean package -DskipTests` | Compose OK; build pendiente por Docker Desktop inactivo |
| `masterbikes-usuarios` | 8083 | 3308 | `/api/v1/usuarios` | OK `clean package -DskipTests` | Compose OK; build no ejecutado en esta fase |
| `masterbikes-bicicletas` | 8084 | 3309 | `/api/v1/bicicletas` | OK `clean package -DskipTests` | Compose OK; build no ejecutado en esta fase |
| `masterbikes-arriendos` | 8085 | 3310 | `/api/v1/arriendos` | OK `clean package -DskipTests` | Compose OK; build no ejecutado en esta fase |
| `masterbikes-inventario` | 8086 | 3311 | `/api/v1/inventario` | OK `clean package -DskipTests` | Compose OK; build no ejecutado en esta fase |
| `masterbikes-ventas` | 8087 | 3312 | `/api/v1/ventas` | OK `clean package -DskipTests` | Compose OK; build no ejecutado en esta fase |
| `masterbikes-despachos` | 8088 | 3313 | `/api/v1/despachos` | OK `clean package -DskipTests` | Compose OK; build no ejecutado en esta fase |
| `masterbikes-pagos` | 8089 | 3314 | `/api/v1/pagos` | OK `clean package -DskipTests` | Compose OK; build no ejecutado en esta fase |
| `masterbikes-proveedores` | 8090 | 3315 | `/api/v1/proveedores` | OK `clean package -DskipTests` | Compose OK; build no ejecutado en esta fase |

## Errores encontrados

1. `mvn` global no existe en PATH.
2. El Maven Wrapper de `masterbike-vm-atencion` estaba incompleto porque faltaba `.mvn/wrapper/maven-wrapper.properties`.
3. Maven inicialmente uso un JRE sin compilador; se corrigio seteando `JAVA_HOME` a JDK 21.
4. Maven Central presento problema de certificados PKIX; se uso flags SSL de Maven para completar la resolucion de plugins.
5. Docker Desktop no esta activo: `dockerDesktopLinuxEngine` no responde.

## Correcciones realizadas

- Se agrego `masterbike-vm-atencion/.mvn/wrapper/maven-wrapper.properties`.
- Se uso JDK 21 local: `C:\Program Files\Java\jdk-21.0.10`.
- Se valido `clean package -DskipTests` en los 10 microservicios.

## Servicios validados

Compilacion y empaquetado validados:

- `masterbike-vm`
- `masterbike-vm-atencion`
- `masterbikes-usuarios`
- `masterbikes-bicicletas`
- `masterbikes-arriendos`
- `masterbikes-inventario`
- `masterbikes-ventas`
- `masterbikes-despachos`
- `masterbikes-pagos`
- `masterbikes-proveedores`

## Servicios pendientes

Pendiente de validacion Docker real:

- Build Docker de `masterbikes-clientes`.
- Build Docker de `masterbikes-servicio-tecnico`.
- `docker compose up --build` completo.
- Pruebas manuales por HTTP.

## Evidencia de Docker Compose config

Comando ejecutado:

```powershell
docker compose config
docker compose config --services
```

Resultado:

- `docker compose config` finalizo correctamente.
- `docker compose config --services` listo 20 servicios: 10 aplicaciones y 10 bases MySQL.
- No hay referencias a carpetas inexistentes.

## Evidencia de build Docker

Comando ejecutado:

```powershell
docker compose build masterbikes-clientes masterbikes-servicio-tecnico
```

Resultado:

```text
error during connect: Head "http://%2F%2F.%2Fpipe%2FdockerDesktopLinuxEngine/_ping":
open //./pipe/dockerDesktopLinuxEngine: The system cannot find the file specified.
```

Interpretacion: bloqueo de entorno por Docker Desktop inactivo, no error demostrado de codigo.

## Endpoints manuales probados

No se pudieron probar endpoints HTTP porque los servicios no quedaron levantados por Docker Desktop inactivo.

Endpoints pendientes:

Clientes:

- `GET http://localhost:8081/api/v1/clientes`
- `GET http://localhost:8081/api/v1/clientes/1`
- `GET http://localhost:8081/api/v1/clientes/rut/11111111-1`

Atenciones:

- `GET http://localhost:8082/api/v1/atenciones`
- `GET http://localhost:8082/api/v1/atenciones/1`
- `GET http://localhost:8082/api/v1/atenciones/cliente/1`

## Proximo paso recomendado

Activar Docker Desktop y ejecutar:

```powershell
docker compose build masterbikes-clientes masterbikes-servicio-tecnico
docker compose up --build
```

Si Docker levanta correctamente, probar los endpoints manuales y luego avanzar a la Fase 3: revision fina de CSR y reglas de negocio.
