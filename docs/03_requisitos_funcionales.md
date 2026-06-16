# 03 Requisitos funcionales

## Requisitos actuales por dominio

| Dominio | Requisito funcional | Estado |
| --- | --- | --- |
| Usuarios | Registrar, listar, buscar, actualizar y eliminar usuarios. | Implementado en `masterbikes-usuarios`. |
| Bicicletas | Administrar bicicletas, tipo, estado y tarifa. | Implementado en `masterbikes-bicicletas`. |
| Arriendos | Registrar arriendos asociados a cliente y bicicleta. | Implementado en `masterbikes-arriendos`. |
| Inventario | Administrar productos, stock, stock minimo y marca de aumento. | Implementado en `masterbikes-inventario`. |
| Ventas | Registrar ventas asociadas a clientes. | Implementado en `masterbikes-ventas`. |
| Despachos | Administrar despachos asociados a ventas. | Implementado en `masterbikes-despachos`. |
| Pagos | Registrar pagos y validar venta existente. | Implementado en `masterbikes-pagos`. |
| Proveedores | Administrar proveedores y buscarlos por RUT. | Implementado en `masterbikes-proveedores`. |
| Clientes | Administrar clientes. | Faltante: `docker-compose.yml` apunta a `./masterbike-vm`, pero no existe. |
| Servicio tecnico | Administrar ordenes de servicio y consultar clientes. | Incompleto: existe `masterbike-vm-atencion`, pero sin codigo fuente. |

## Endpoints funcionales esperados

Cada microservicio completo debe exponer:

- `GET /api/v1/{recurso}` para listar.
- `GET /api/v1/{recurso}/{id}` para buscar por id.
- `POST /api/v1/{recurso}` para crear.
- `PUT /api/v1/{recurso}/{id}` para actualizar.
- `DELETE /api/v1/{recurso}/{id}` para eliminar.

Endpoints adicionales actuales:

- `GET /api/v1/inventario/aumento/{aumento}`.
- `GET /api/v1/proveedores/rut/{rut}`.

## Reglas de negocio actuales

- Arriendos: `fechaFin` no puede ser anterior a `fechaInicio`.
- Inventario: `aumento` se marca en `true` cuando `stock <= stockMinimo`.
- Pagos: valida que la venta exista consultando el microservicio de ventas.
- Pagos: no permite dos pagos para la misma venta.
- Proveedores: debe mantener consistencia de RUT si el servicio/repository lo valida.

## Requisitos funcionales pendientes

- Restaurar o crear microservicio de clientes para cumplir el servicio `masterbikes-clientes`.
- Completar microservicio de servicio tecnico o excluirlo de forma justificada y reemplazarlo por otro microservicio funcional.
- Alcanzar al menos 10 microservicios reales y ejecutables.
- Enrutar todos los endpoints principales por API Gateway.
- Exponer documentacion Swagger por cada microservicio principal.
