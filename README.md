# MasterBike

Proyecto de microservicios para la gestion de clientes, servicio tecnico, usuarios, bicicletas, arriendos, inventario, ventas y despachos de MasterBike.

## Tecnologias

- Java 21
- Spring Boot
- Spring Data JPA
- MySQL 8
- Flyway
- Docker Compose
- OpenFeign en el servicio tecnico para consultar clientes

## Estructura del proyecto

| Carpeta | Servicio | Puerto |
| --- | --- | --- |
| `masterbike-vm` | Clientes | 8081 |
| `masterbike-vm-atencion` | Servicio tecnico | 8082 |
| `masterbikes-usuarios` | Usuarios | 8083 |
| `masterbikes-bicicletas` | Bicicletas | 8084 |
| `masterbikes-arriendos` | Arriendos | 8085 |
| `masterbikes-inventario` | Inventario | 8086 |
| `masterbikes-ventas` | Ventas | 8087 |
| `masterbikes-despachos` | Despachos | 8088 |

Cada microservicio tiene su propia base de datos MySQL y sus migraciones Flyway en `src/main/resources/db/migration`.

## Requisitos

- Docker y Docker Compose
- Java 21, solo si se ejecutan los servicios fuera de Docker
- Maven, solo si se compilan los servicios fuera de Docker

## Ejecucion con Docker Compose

Desde la raiz del proyecto:

```bash
docker compose up --build
```

Para detener los contenedores:

```bash
docker compose down
```

Para detenerlos y eliminar los volumenes de base de datos:

```bash
docker compose down -v
```

## Servicios y endpoints principales

| Servicio | Base URL | Endpoints |
| --- | --- | --- |
| Clientes | `http://localhost:8081/api/v1/clientes` | `GET /`, `GET /{id}`, `GET /rut/{rut}`, `POST /`, `DELETE /{id}` |
| Servicio tecnico | `http://localhost:8082/api/v1/ordenes-servicio` | `GET /`, `GET /{id}`, `GET /cliente/{idCliente}`, `POST /`, `PUT /{id}`, `DELETE /{id}` |
| Usuarios | `http://localhost:8083/api/v1/usuarios` | `GET /`, `GET /{id}`, `POST /`, `DELETE /{id}` |
| Bicicletas | `http://localhost:8084/api/v1/bicicletas` | `GET /`, `GET /{id}`, `POST /`, `DELETE /{id}` |
| Arriendos | `http://localhost:8085/api/v1/arriendos` | `GET /`, `GET /{id}`, `POST /`, `DELETE /{id}` |
| Inventario | `http://localhost:8086/api/v1/inventario` | `GET /`, `GET /{id}`, `POST /`, `DELETE /{id}` |
| Ventas | `http://localhost:8087/api/v1/ventas` | `GET /`, `GET /{id}`, `POST /`, `DELETE /{id}` |
| Despachos | `http://localhost:8088/api/v1/despachos` | `GET /`, `GET /{id}`, `POST /`, `DELETE /{id}` |

## Bases de datos

Docker Compose levanta una base de datos MySQL por servicio:

| Contenedor | Base de datos | Puerto local |
| --- | --- | --- |
| `mysql-cliente` | `cliente_db` | 3306 |
| `mysql-servicio-tecnico` | `servicio_tecnico_db` | 3307 |
| `mysql-usuario` | `usuario_db` | 3308 |
| `mysql-bicicleta` | `bicicleta_db` | 3309 |
| `mysql-arriendo` | `arriendo_db` | 3310 |
| `mysql-inventario` | `inventario_db` | 3311 |
| `mysql-venta` | `venta_db` | 3312 |
| `mysql-despacho` | `despacho_db` | 3313 |

Las credenciales configuradas para los servicios son:

```text
Usuario: root
Password: root123
```

## Notas

- El servicio tecnico depende del servicio de clientes mediante la variable `CLIENTE_SERVICE_URL`.
- Los datos iniciales se cargan con migraciones Flyway.
- Las carpetas `target`, `.vscode`, `.idea` y otros archivos locales estan excluidos por `.gitignore`.
