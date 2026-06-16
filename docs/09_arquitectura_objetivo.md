# 09 Arquitectura objetivo

## Objetivo

Llegar a un ecosistema defendible de al menos 10 microservicios Spring Boot, con Gateway como punto unico de entrada, bases MySQL independientes, Swagger, pruebas unitarias y despliegue local por Docker Compose.

## Componentes objetivo

| Componente | Rol |
| --- | --- |
| API Gateway | Punto unico de entrada, rutas, predicados y filtros basicos. |
| Clientes | Gestion de clientes, requerido por servicio tecnico. |
| Servicio tecnico | Gestion de ordenes de servicio, consume clientes. |
| Usuarios | Gestion de usuarios internos. |
| Bicicletas | Gestion de bicicletas. |
| Arriendos | Gestion de arriendos. |
| Inventario | Gestion de stock y alerta de aumento. |
| Ventas | Gestion de ventas. |
| Despachos | Gestion de despachos. |
| Pagos | Gestion de pagos, consume ventas. |
| Proveedores | Gestion de proveedores. |
| MySQL por servicio | Persistencia aislada por dominio. |

## Rutas objetivo del Gateway

| Ruta publica | Destino interno |
| --- | --- |
| `/api/v1/clientes/**` | `masterbikes-clientes:8081` |
| `/api/v1/ordenes-servicio/**` | `masterbikes-servicio-tecnico:8082` |
| `/api/v1/usuarios/**` | `masterbikes-usuarios:8083` |
| `/api/v1/bicicletas/**` | `masterbikes-bicicletas:8084` |
| `/api/v1/arriendos/**` | `masterbikes-arriendos:8085` |
| `/api/v1/inventario/**` | `masterbikes-inventario:8086` |
| `/api/v1/ventas/**` | `masterbikes-ventas:8087` |
| `/api/v1/despachos/**` | `masterbikes-despachos:8088` |
| `/api/v1/pagos/**` | `masterbikes-pagos:8089` |
| `/api/v1/proveedores/**` | `masterbikes-proveedores:8090` |

## Comunicaciones internas objetivo

- Pagos -> Ventas: validar existencia de venta antes de pagar.
- Servicio tecnico -> Clientes: validar existencia de cliente antes de crear orden.
- Opcional futuro: Despachos -> Ventas para validar venta antes de despacho.
- Opcional futuro: Arriendos -> Bicicletas/Clientes para validar disponibilidad y cliente.

## Configuracion objetivo

- Gateway con `application.yml`.
- Perfiles o variables para local/Docker.
- URLs remotas mediante variables de entorno.
- Docker Compose con red interna y nombres de servicio.

## Documentacion objetivo

- Swagger UI por microservicio.
- README con tabla de rutas Gateway y links Swagger.
- Guia de defensa por modulo.
- Trazabilidad rubrica actualizada tras cada fase.
