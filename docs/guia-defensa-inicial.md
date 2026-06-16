# Guia de defensa inicial

## Mensaje base del proyecto

MasterBikes esta planteado como un ecosistema de microservicios Spring Boot, donde cada dominio se separa en su propio servicio, base de datos MySQL y migraciones Flyway. La separacion por capas permite explicar que el controller recibe la solicitud, el service aplica reglas de negocio y el repository delega persistencia a JPA.

## Lo que ya se puede defender

- Arquitectura por dominios: usuarios, bicicletas, arriendos, inventario, ventas, despachos, pagos y proveedores.
- Patron CSR en los servicios con codigo fuente.
- CRUD REST en la mayoria de servicios.
- Validaciones con Bean Validation (`@NotBlank`, `@NotNull`, `@Positive`, `@DecimalMin`, `@Email`, `@Size`).
- Manejo global de errores con `GlobalExceptionHandler`.
- Flyway para crear tablas y cargar datos iniciales.
- Docker Compose con una base de datos por servicio.
- Comunicacion REST en pagos hacia ventas mediante OpenFeign.

## Ejemplos de reglas defendibles

- Arriendos: la fecha de fin no puede ser anterior a la fecha de inicio.
- Inventario: si el stock es menor o igual al stock minimo, el producto queda marcado con `aumento`.
- Pagos: antes de registrar un pago se valida que la venta exista consultando el microservicio de ventas.
- Pagos: una venta no puede tener dos pagos asociados.
- Proveedores: el endpoint por RUT permite busqueda especifica de proveedor.

## Explicacion sugerida para comunicacion REST

En pagos no se usa una clave foranea directa hacia la base de ventas, porque en microservicios cada servicio mantiene su propia base de datos. Para preservar la consistencia, `PagoService` consulta a ventas mediante `VentaClient`. Si ventas responde que no existe, pagos devuelve `404`; si la comunicacion remota falla, devuelve `502 Bad Gateway`.

## Brechas que se deben reconocer y cerrar

- Faltan pruebas unitarias reales.
- Falta cobertura del 80%.
- Falta Swagger/OpenAPI vivo.
- Falta API Gateway.
- Falta YAML para rutas, perfiles y variables relevantes.
- Falta resolver servicios ausentes/incompletos mencionados en README/Compose.
- Falta evidencia de Trello u organizacion colaborativa.

## Como preparar la defensa tecnica

1. Levantar el sistema con Docker Compose despues de corregir servicios ausentes.
2. Probar al menos un flujo por Gateway, por ejemplo:
   - `GET /api/v1/inventario`
   - `POST /api/v1/pagos` validando venta existente
   - `GET /api/v1/proveedores/rut/{rut}`
3. Mostrar Swagger UI y abrir un endpoint con:
   - parametros
   - request body
   - modelo
   - codigos de respuesta
   - ejemplo
4. Ejecutar pruebas unitarias y mostrar resultado exitoso.
5. Explicar una prueba usando Given-When-Then:
   - Given: repositorio o cliente remoto mockeado.
   - When: se llama el metodo del service.
   - Then: se valida resultado o excepcion esperada.
6. Tener preparada una prueba sencilla para implementar en vivo.

## Prueba candidata para implementar en vivo

Servicio: `InventarioService`.

Caso:

- Given: un producto con `stock = 2` y `stockMinimo = 5`, y el repositorio mockeado devuelve el producto guardado.
- When: se llama `guardar`.
- Then: el producto queda con `aumento = true` y se invoca `save`.

Ventaja: no depende de red ni base de datos, es facil de explicar y valida una regla de negocio clara.

## Explicacion sugerida de YAML/Gateway

El Gateway debe ser el punto unico de entrada. En lugar de recordar ocho puertos distintos, el cliente entra por un solo puerto del Gateway y las rutas se resuelven por path. YAML es adecuado porque las rutas de Spring Cloud Gateway son listas anidadas con `id`, `uri`, `predicates` y `filters`, lo que queda mas legible que en `.properties`.

## Evidencias que conviene tener al final

- `README.md` actualizado con comandos reales.
- Tabla de microservicios, puertos y rutas Gateway.
- Links Swagger locales por servicio.
- Reporte de pruebas/cobertura.
- Comandos Docker validados.
- Captura o enlace de Trello.
- Seccion de decisiones tecnicas para explicar por que se eligio Feign, Gateway, Flyway, Docker y YAML.
