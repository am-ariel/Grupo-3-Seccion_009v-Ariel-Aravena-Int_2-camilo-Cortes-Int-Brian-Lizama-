# 18 Guia defensa pruebas unitarias

## Que es una prueba unitaria

Una prueba unitaria valida una unidad pequena de codigo de forma aislada. En este proyecto la unidad principal es la capa `Service`, porque ahi estan las reglas de negocio y las decisiones antes de persistir datos.

## Por que no usa base de datos real

No se usa MySQL real porque una prueba unitaria debe ser rapida, repetible y aislada. La base de datos se prueba en integracion o manualmente. Aqui el repository se simula con Mockito.

## Que es Mockito

Mockito permite crear objetos simulados, llamados mocks. Con esos mocks se define que debe devolver una dependencia sin conectarse a sistemas externos.

## Que es un mock

Un mock reemplaza una dependencia real. Por ejemplo, `ClienteRepository` se mockea para devolver un cliente cuando se llama `findById(1L)`, sin abrir conexion a MySQL.

## Que es JUnit

JUnit 5 ejecuta los metodos `@Test`, compara resultados con asserts y reporta si una prueba pasa o falla.

## Que es JaCoCo

JaCoCo mide que partes del codigo fueron ejecutadas por los tests. En esta fase se genero `target/site/jacoco/index.html` en cada microservicio.

## Que significa Given-When-Then

- Given: se preparan datos y mocks.
- When: se ejecuta el metodo del service.
- Then: se verifica el resultado, la excepcion o la interaccion con el repository.

## Como explicar un test de Service

Ejemplo: en `InventarioServiceTest`, se crea un producto con `stock = 2` y `stockMinimo = 5`. Se simula `inventarioRepository.save(...)`. Al llamar `guardar`, el service aplica la regla `stock <= stockMinimo` y marca `aumento = true`.

## Como explicar un assert

Un assert confirma el resultado esperado. `assertTrue(resultado.getAumento())` demuestra que la regla de stock bajo se cumplio.

## Como explicar una simulacion de repository

`when(repository.findById(1L)).thenReturn(Optional.of(modelo))` define la respuesta esperada del repository. Asi se prueba el service sin consultar base de datos.

## Como defender una prueba de busqueda por ID

Se configura el mock para devolver un registro existente o `Optional.empty()`. Luego se valida que el service retorne presente o vacio segun corresponda.

Respuesta sugerida: "Estoy probando que el service responda correctamente ante ambos escenarios de negocio: existe el dato y no existe el dato. No necesito MySQL porque el objetivo no es probar JPA, sino la logica de la capa service".

## Como defender una prueba de creacion valida

Se arma un modelo valido, se simula que el repository guarda y se verifica que el resultado tenga el dato esperado. En pagos, ademas se simula la venta existente con `VentaClient`.

## Como defender una prueba de error o excepcion

Se usa `assertThrows(ResponseStatusException.class, ...)` y luego se valida el codigo HTTP esperado, por ejemplo `404 NOT_FOUND`, `400 BAD_REQUEST` o `409 CONFLICT`.

## Preguntas probables del profesor

| Pregunta | Respuesta sugerida |
| --- | --- |
| Por que mockean el repository? | Porque es una prueba unitaria; queremos aislar la regla de negocio y no depender de MySQL. |
| Que diferencia hay entre test unitario e integracion? | El unitario prueba una clase aislada con mocks. El de integracion levanta mas componentes, por ejemplo Spring, controller o base de datos. |
| Donde esta la regla mas clara? | En inventario: si `stock <= stockMinimo`, `aumento` queda en `true`. Tambien en pagos: se valida venta existente y pago duplicado. |
| Como saben que no se guarda un duplicado? | En el test se simula `findByRut` o `findByIdVenta` con un registro existente y se valida `409 CONFLICT` mas `verify(repository, never()).save(...)`. |
| Por que la cobertura global no llega a 80%? | Porque esta fase cubre services. JaCoCo global tambien cuenta controllers, handlers, application classes y models. La cobertura Service supera 90%; queda pendiente ampliar controllers/handlers para 80% global. |
| Como agregarias una prueba nueva en vivo? | Copiaria el patron: crear modelo, configurar `when(...)`, ejecutar service y validar con `assertEquals`, `assertTrue` o `assertThrows`. |

## Prueba rapida para defensa

Una prueba facil de crear en vivo:

1. Abrir `InventarioServiceTest`.
2. Crear un inventario con `stock = 1` y `stockMinimo = 3`.
3. Mockear `inventarioRepository.save(inventario)`.
4. Ejecutar `inventarioService.guardar(inventario)`.
5. Validar `assertTrue(resultado.getAumento())`.

Esta prueba es defendible porque demuestra una regla de negocio real, no solo un CRUD.
