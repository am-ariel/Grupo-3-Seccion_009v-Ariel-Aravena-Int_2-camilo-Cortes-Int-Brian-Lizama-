# 07 Mapa del codigo

## Resumen

Microservicios reales completos: 10. Incompletos: 0. Faltantes: 0. Los 10 compilaron con Maven Wrapper y JDK 21. Todos los microservicios funcionales deberian pasar por Gateway cuando se implemente.

## Clientes

- Carpeta: `masterbike-vm`
- Responsabilidad: administrar clientes de MasterBikes.
- Paquetes principales: `com.masterbikes.clientes.controllers`, `services`, `repositories`, `models`, `exceptions`.
- Controller: `ClienteController`
- Service: `ClienteService`
- Repository/Model: `ClienteRepository`, `ClienteModel`
- Endpoints: `/api/v1/clientes`, `GET /`, `GET /{id}`, `GET /rut/{rut}`, `POST /`, `PUT /{id}`, `DELETE /{id}`
- Puerto: 8081.
- Estado: completo.
- Docker: participa como `masterbikes-clientes`, puerto 8081.
- Gateway: deberia pasar por Gateway cuando se implemente.
- Defensa: explicar validaciones de RUT unico, datos obligatorios y separacion CSR.

## Atencion

- Carpeta: `masterbike-vm-atencion`
- Responsabilidad: administrar atenciones o solicitudes de servicio tecnico asociadas logicamente a cliente y bicicleta mediante IDs simples.
- Paquetes principales: `com.masterbikes.atencion.controllers`, `services`, `repositories`, `models`, `exceptions`.
- Controller: `AtencionController`
- Service: `AtencionService`
- Repository/Model: `AtencionRepository`, `AtencionModel`
- Endpoints: `/api/v1/atenciones`, `GET /`, `GET /{id}`, `GET /cliente/{idCliente}`, `POST /`, `PUT /{id}`, `DELETE /{id}`
- Puerto: 8082.
- Estado: completo.
- Docker: participa como `masterbikes-servicio-tecnico`, puerto 8082.
- Gateway: deberia pasar por Gateway cuando se implemente.
- Defensa: explicar que usa IDs simples para relacion logica con clientes y bicicletas, sin acoplar bases de datos ni agregar Feign en esta fase.

## Usuarios

- Carpeta: `masterbikes-usuarios`
- Responsabilidad: administrar usuarios del sistema.
- Controller: `UsuarioController`
- Service: `UsuarioService`
- Repository/Model: `UsuarioRepository`, `UsuarioModel`
- Endpoints: `/api/v1/usuarios`, `GET /`, `GET /{id}`, `POST /`, `PUT /{id}`, `DELETE /{id}`
- Estado: completo.
- Docker: participa como `masterbikes-usuarios`, puerto 8083.
- Gateway: si, ruta esperada `/api/v1/usuarios/**`.
- Defensa: explicar validaciones de nombre, correo y rol, y separacion CSR.

## Bicicletas

- Carpeta: `masterbikes-bicicletas`
- Responsabilidad: administrar bicicletas, tipo, estado y tarifa.
- Controller: `BicicletaController`
- Service: `BicicletaService`
- Repository/Model: `BicicletaRepository`, `BicicletaModel`
- Endpoints: `/api/v1/bicicletas`, CRUD basico.
- Estado: completo.
- Docker: participa como `masterbikes-bicicletas`, puerto 8084.
- Gateway: si, ruta esperada `/api/v1/bicicletas/**`.
- Defensa: explicar validaciones de codigo, tipo, estado y tarifa positiva.

## Arriendos

- Carpeta: `masterbikes-arriendos`
- Responsabilidad: registrar arriendos de bicicletas.
- Controller: `ArriendoController`
- Service: `ArriendoService`
- Repository/Model: `ArriendoRepository`, `ArriendoModel`
- Endpoints: `/api/v1/arriendos`, CRUD basico.
- Estado: completo.
- Docker: participa como `masterbikes-arriendos`, puerto 8085.
- Gateway: si, ruta esperada `/api/v1/arriendos/**`.
- Defensa: explicar regla `fechaFin` no anterior a `fechaInicio`.

## Inventario

- Carpeta: `masterbikes-inventario`
- Responsabilidad: administrar stock y alerta de aumento.
- Controller: `InventarioController`
- Service: `InventarioService`
- Repository/Model: `InventarioRepository`, `InventarioModel`
- Endpoints: `/api/v1/inventario`, CRUD basico, `GET /aumento/{aumento}`
- Estado: completo.
- Docker: participa como `masterbikes-inventario`, puerto 8086.
- Gateway: si, ruta esperada `/api/v1/inventario/**`.
- Defensa: explicar regla `stock <= stockMinimo` marca `aumento`.

## Ventas

- Carpeta: `masterbikes-ventas`
- Responsabilidad: administrar ventas.
- Controller: `VentaController`
- Service: `VentaService`
- Repository/Model: `VentaRepository`, `VentaModel`
- Endpoints: `/api/v1/ventas`, CRUD basico.
- Estado: completo.
- Docker: participa como `masterbikes-ventas`, puerto 8087.
- Gateway: si, ruta esperada `/api/v1/ventas/**`.
- Defensa: explicar que pagos depende de ventas para validar existencia.

## Despachos

- Carpeta: `masterbikes-despachos`
- Responsabilidad: administrar despachos asociados a ventas.
- Controller: `DespachoController`
- Service: `DespachoService`
- Repository/Model: `DespachoRepository`, `DespachoModel`
- Endpoints: `/api/v1/despachos`, CRUD basico.
- Estado: completo.
- Docker: participa como `masterbikes-despachos`, puerto 8088.
- Gateway: si, ruta esperada `/api/v1/despachos/**`.
- Defensa: explicar relacion logica con ventas sin acoplar bases de datos.

## Pagos

- Carpeta: `masterbikes-pagos`
- Responsabilidad: administrar pagos y validar ventas remotas.
- Controller: `PagoController`
- Service: `PagoService`
- Repository/Model: `PagoRepository`, `PagoModel`
- Cliente/DTO: `VentaClient`, `VentaResponse`
- Endpoints: `/api/v1/pagos`, CRUD basico.
- Estado: completo.
- Docker: participa como `masterbikes-pagos`, puerto 8089.
- Gateway: si, ruta esperada `/api/v1/pagos/**`.
- Defensa: explicar Feign hacia ventas, `404` si no existe venta, `502` ante error remoto y `409` por pago duplicado.

## Proveedores

- Carpeta: `masterbikes-proveedores`
- Responsabilidad: administrar proveedores.
- Controller: `ProveedorController`
- Service: `ProveedorService`
- Repository/Model: `ProveedorRepository`, `ProveedorModel`
- Endpoints: `/api/v1/proveedores`, CRUD basico, `GET /rut/{rut}`
- Estado: completo.
- Docker: participa como `masterbikes-proveedores`, puerto 8090.
- Gateway: si, ruta esperada `/api/v1/proveedores/**`.
- Defensa: explicar busqueda por RUT y validaciones de correo, telefono y direccion.
