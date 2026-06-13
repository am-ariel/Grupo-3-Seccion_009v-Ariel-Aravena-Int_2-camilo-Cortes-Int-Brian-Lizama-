# Guia breve para la defensa

Este proyecto esta separado en microservicios. Cada microservicio tiene su propia base de datos, sus migraciones Flyway y sus capas `controller`, `service`, `repository` y `model`.

## Como explicar el patron CSR

- `Controller`: recibe la peticion REST, valida el JSON con `@Valid` y devuelve la respuesta HTTP.
- `Service`: contiene la logica de negocio, por ejemplo validar fechas, evitar pagos repetidos o revisar si existe un registro antes de eliminar.
- `Repository`: se conecta con la base de datos usando `JpaRepository`.
- `Model`: representa la tabla de la base de datos y tiene validaciones como campos obligatorios, montos positivos o correos validos.

Frase util:

> El controller no guarda directamente ni decide reglas. Solo recibe la peticion y llama al service. El service aplica la logica y el repository hace la persistencia.

## CRUD completo

Los microservicios principales tienen:

- `GET /`: listar registros.
- `GET /{id}`: buscar por id.
- `POST /`: crear.
- `PUT /{id}`: actualizar.
- `DELETE /{id}`: eliminar.

Para probar en vivo, conviene mostrar un `POST`, despues un `PUT` al mismo id y finalmente un `GET /{id}` para demostrar que el cambio quedo guardado.

## Validaciones

Se agrego `spring-boot-starter-validation` y anotaciones como:

- `@NotBlank`: texto obligatorio.
- `@NotNull`: dato obligatorio.
- `@Positive`: id mayor a cero.
- `@DecimalMin`: monto mayor a cero.
- `@Email`: correo con formato valido.
- `@Size`: largo maximo del texto.

Frase util:

> Las validaciones evitan que entren datos incompletos o incoherentes antes de llegar a la base de datos.

## Reglas de negocio

Ejemplos faciles de defender:

- En arriendos, la fecha de fin no puede ser anterior a la fecha de inicio.
- En inventario, si el stock es menor o igual al stock minimo, el producto queda marcado con `aumento`.
- En pagos, se consulta el microservicio de ventas para validar que la venta exista.
- En pagos, una venta no puede tener dos pagos asociados.
- En clientes y proveedores, el RUT no se puede repetir.

## Excepciones y codigos HTTP

Los servicios tienen un `GlobalExceptionHandler` para responder errores en JSON:

- `400 Bad Request`: datos invalidos o regla mal aplicada.
- `404 Not Found`: registro inexistente.
- `409 Conflict`: dato repetido, por ejemplo RUT o pago duplicado.
- `502 Bad Gateway`: error al consultar otro microservicio.

Frase util:

> El manejo global de errores permite que todos los endpoints respondan de forma ordenada y facil de probar en Postman.

## Logs

Los services registran acciones importantes:

- Crear registros.
- Actualizar registros.
- Eliminar registros.
- Consultar otro microservicio.
- Detectar errores en comunicacion remota.

Frase util:

> Los logs sirven para seguir el flujo de la operacion y saber en que capa ocurrio un problema.

## Comunicacion entre microservicios

Hay comunicacion con OpenFeign:

- Servicio tecnico consulta clientes antes de crear una orden.
- Pagos consulta ventas antes de registrar un pago.

Frase util:

> Como cada microservicio tiene su propia base de datos, no usamos claves foraneas entre bases distintas. La integridad entre servicios se controla consultando al microservicio correspondiente.

## Que mostrar si piden cambio en vivo

Opciones simples:

1. Agregar una validacion nueva, por ejemplo `@Size(max = 50)` en un campo de texto.
2. Cambiar un mensaje de error en un modelo.
3. Agregar un `log.info` en un metodo del service.
4. Modificar una regla simple, por ejemplo que inventario marque `aumento` solo cuando `stock < stockMinimo`.
5. Probar un `PUT` en Postman para demostrar actualizacion.
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                