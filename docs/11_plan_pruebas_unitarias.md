# 11 Plan pruebas unitarias

## Objetivo

Implementar pruebas unitarias con JUnit 5 y Mockito para cubrir reglas de negocio y preparar defensa tecnica.

Estado: implementado para la capa `Service` de los 10 microservicios. Resultado documentado en `docs/17_pruebas_unitarias_resultado.md`.

## Estrategia

- Probar principalmente services.
- Mockear repositories y clientes Feign.
- Evitar base de datos real.
- Usar nombres descriptivos.
- Seguir Given-When-Then.
- Medir cobertura con JaCoCo si se incorpora.

## Prioridad de pruebas

| Servicio | Caso | Dependencias mock | Resultado esperado | Prioridad |
| --- | --- | --- | --- | --- |
| `InventarioService` | `stock <= stockMinimo` | `InventarioRepository` | `aumento = true` y `save` invocado. | Critica |
| `InventarioService` | `stock > stockMinimo` | `InventarioRepository` | `aumento = false`. | Alta |
| `ArriendoService` | fecha fin anterior | `ArriendoRepository` | `ResponseStatusException` 400. | Critica |
| `ArriendoService` | fechas validas | `ArriendoRepository` | guarda arriendo. | Alta |
| `PagoService` | venta existe y sin pago previo | `PagoRepository`, `VentaClient` | guarda pago. | Critica |
| `PagoService` | venta no existe | `VentaClient` | `ResponseStatusException` 404. | Critica |
| `PagoService` | error remoto | `VentaClient` | `ResponseStatusException` 502. | Alta |
| `PagoService` | pago duplicado | `PagoRepository`, `VentaClient` | `ResponseStatusException` 409. | Critica |
| `ProveedorService` | RUT duplicado | `ProveedorRepository` | conflicto o error segun implementacion. | Media |
| `UsuarioService` | eliminar inexistente | `UsuarioRepository` | `ResponseStatusException` 404. | Media |

## Estructura recomendada

```text
src/test/java/com/masterbikes/{servicio}/services/{Servicio}Test.java
```

## Patron de prueba defendible

```text
Given: se crean datos y se configuran mocks.
When: se llama al metodo del service.
Then: se valida resultado, excepcion o interaccion con repository.
```

## Cobertura

Meta de rubrica: 80%. Si no se logra en todos los servicios por tiempo, priorizar:

1. Services con reglas reales.
2. Caminos exitosos y errores.
3. Handlers si el tiempo permite.

Resultado actual:

- Tests ejecutados: 105.
- Fallas: 0.
- Errores: 0.
- Cobertura Service: sobre 90% en los 10 microservicios.
- Cobertura global JaCoCo: entre 39% y 52% porque incluye controllers, handlers, application classes y models no cubiertos en esta fase.

Pendiente si se busca 80% global: agregar pruebas de controllers con `@WebMvcTest`, pruebas de `GlobalExceptionHandler` y validaciones de request.

## Preparacion para prueba en vivo

Prueba candidata:

- Crear `InventarioServiceTest`.
- Mockear `InventarioRepository`.
- Probar `guardar` con `stock = 2`, `stockMinimo = 5`.
- Assert: `getAumento()` es `true`.
- Verify: `inventarioRepository.save(...)`.

Esta prueba es simple, rapida y explica una regla de negocio clara.
