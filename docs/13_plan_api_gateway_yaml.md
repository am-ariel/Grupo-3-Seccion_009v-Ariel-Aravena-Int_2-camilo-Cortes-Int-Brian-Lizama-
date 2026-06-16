# 13 Plan API Gateway YAML

## Objetivo

Implementar un API Gateway con Spring Cloud Gateway para centralizar el acceso a los microservicios y cumplir la rubrica de rutas, predicados, filtros y YAML.

## Alcance

- Crear microservicio `masterbikes-gateway`.
- Puerto recomendado: 8080.
- Configuracion principal en `application.yml`.
- Rutas por Path hacia cada microservicio.
- Filtro basico por ruta o default filter para trazabilidad.

## Rutas objetivo

| ID ruta | Predicado | URI destino Docker |
| --- | --- | --- |
| clientes | `/api/v1/clientes/**` | `http://masterbikes-clientes:8081` |
| servicio-tecnico | `/api/v1/ordenes-servicio/**` | `http://masterbikes-servicio-tecnico:8082` |
| usuarios | `/api/v1/usuarios/**` | `http://masterbikes-usuarios:8083` |
| bicicletas | `/api/v1/bicicletas/**` | `http://masterbikes-bicicletas:8084` |
| arriendos | `/api/v1/arriendos/**` | `http://masterbikes-arriendos:8085` |
| inventario | `/api/v1/inventario/**` | `http://masterbikes-inventario:8086` |
| ventas | `/api/v1/ventas/**` | `http://masterbikes-ventas:8087` |
| despachos | `/api/v1/despachos/**` | `http://masterbikes-despachos:8088` |
| pagos | `/api/v1/pagos/**` | `http://masterbikes-pagos:8089` |
| proveedores | `/api/v1/proveedores/**` | `http://masterbikes-proveedores:8090` |

## Filtros basicos recomendados

- `AddRequestHeader=X-Gateway, MasterBikes`
- `AddResponseHeader=X-Gateway-Response, MasterBikes`
- Logging simple si se implementa filtro Java.

## YAML minimo esperado

```yaml
server:
  port: 8080

spring:
  application:
    name: masterbikes-gateway
  cloud:
    gateway:
      routes:
        - id: inventario
          uri: http://masterbikes-inventario:8086
          predicates:
            - Path=/api/v1/inventario/**
          filters:
            - AddRequestHeader=X-Gateway, MasterBikes
```

## Como probar

- Levantar un servicio destino.
- Levantar Gateway.
- Consumir `http://localhost:8080/api/v1/inventario`.
- Revisar respuesta.
- Revisar logs.

## Riesgos

- Crear Gateway antes de corregir clientes/atencion deja rutas rotas.
- Rutas Docker no funcionan fuera de Compose si no se define perfil/local.
- Spring Cloud Gateway usa stack reactivo; no mezclar con dependencias MVC innecesarias.

## Decision recomendada

Corregir primero los 10 microservicios y despues crear Gateway. Si se necesita avanzar en paralelo, crear Gateway con rutas de los 8 servicios completos y dejar clientes/atencion marcados como pendientes.
