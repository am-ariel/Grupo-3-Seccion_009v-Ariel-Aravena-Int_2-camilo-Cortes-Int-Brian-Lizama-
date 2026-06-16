# 14 Plan Docker despliegue

## Objetivo

Lograr despliegue local estable con Docker Compose y dejar preparado el discurso de despliegue remoto si corresponde.

## Estado actual

- Existe `docker-compose.yml`.
- Hay MySQL por servicio.
- Hay Dockerfile por servicio visible.
- Compose define 10 servicios de aplicacion y los 10 tienen carpeta/codigo base.
- `masterbikes-clientes` apunta a `./masterbike-vm`, carpeta creada.
- `masterbikes-servicio-tecnico` apunta a `./masterbike-vm-atencion`, carpeta completada con `src`.
- `docker compose config` fue ejecutado correctamente.
- Los 10 microservicios compilaron con Maven Wrapper y JDK 21.
- `docker compose build masterbikes-clientes masterbikes-servicio-tecnico` no pudo completarse porque Docker Desktop no estaba activo.

## Correcciones necesarias

1. Ejecutar build con Docker Desktop activo.
2. Levantar `mysql-cliente`, `mysql-servicio-tecnico`, `masterbikes-clientes` y `masterbikes-servicio-tecnico`.
3. Agregar Gateway al compose cuando exista.
4. Revisar `depends_on` si se agregan healthchecks.
5. Validar variables de entorno.
6. Revisar puertos para evitar conflictos.
7. Ajustar scripts PowerShell si nombran rutas antiguas.

## Verificaciones recomendadas

- `docker compose config`
- `.\masterbike-vm-atencion\mvnw.cmd -f <microservicio>\pom.xml clean package -DskipTests`
- `docker compose build masterbikes-clientes masterbikes-servicio-tecnico`
- `docker compose build masterbikes-ventas masterbikes-pagos`
- `docker compose up --build`
- `docker compose ps`
- `docker compose logs masterbikes-clientes`
- `docker compose logs masterbikes-servicio-tecnico`

## Pruebas manuales minimas

Clientes:

- `GET http://localhost:8081/api/v1/clientes`
- `GET http://localhost:8081/api/v1/clientes/1`
- `GET http://localhost:8081/api/v1/clientes/rut/11111111-1`
- `POST http://localhost:8081/api/v1/clientes`
- `PUT http://localhost:8081/api/v1/clientes/{id}`
- `DELETE http://localhost:8081/api/v1/clientes/{id}`

Atencion:

- `GET http://localhost:8082/api/v1/atenciones`
- `GET http://localhost:8082/api/v1/atenciones/1`
- `GET http://localhost:8082/api/v1/atenciones/cliente/1`
- `POST http://localhost:8082/api/v1/atenciones`
- `PUT http://localhost:8082/api/v1/atenciones/{id}`
- `DELETE http://localhost:8082/api/v1/atenciones/{id}`

## Evidencia para defensa

- Captura o salida de servicios arriba.
- Prueba de endpoint por servicio y luego por Gateway cuando exista.
- Logs de Clientes y Atencion.
- Explicacion de variables `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`.
- Explicacion de volumenes MySQL.

## Despliegue remoto

Solo conviene abordarlo despues de tener despliegue local estable. Para Railway/Render:

- Usar variable `PORT` si la plataforma lo exige.
- Usar base remota o servicio administrado.
- Separar perfiles local/remoto.
- No depender de `localhost` entre servicios remotos.

## Riesgos

- Docker Desktop no disponible durante defensa.
- Descarga de imagenes Maven/MySQL lenta.
- Conflictos de puertos 3306-3315 o 8080-8090.
- Builds lentos por compilar todos los servicios.

## Decision recomendada

Defender primero despliegue local completo con Docker Compose. El despliegue remoto puede quedar como extension si hay tiempo y acceso a plataforma.
