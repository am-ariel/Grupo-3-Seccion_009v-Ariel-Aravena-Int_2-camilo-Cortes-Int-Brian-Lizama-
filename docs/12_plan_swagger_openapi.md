# 12 Plan Swagger OpenAPI

## Objetivo

Agregar documentacion viva de APIs con Springdoc OpenAPI para que los endpoints puedan explorarse y probarse desde Swagger UI.

## Alcance recomendado

Implementar en todos los microservicios principales. Si el tiempo es limitado, priorizar:

1. Pagos, porque incluye comunicacion remota.
2. Inventario, porque tiene regla de negocio y endpoint adicional.
3. Arriendos, porque tiene regla de fechas.
4. Ventas, porque es dependencia de pagos.
5. Proveedores, por busqueda por RUT.

## Dependencia esperada

```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.6.0</version>
</dependency>
```

La version final debe validarse con Spring Boot 3.5.6 antes de cerrar implementacion.

## Configuracion esperada

En `application.properties` o `application.yml`:

```properties
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.path=/doc/swagger-ui.html
```

## Anotaciones a usar

- `@Tag` en controller.
- `@Operation` en metodo.
- `@ApiResponse` y `@ApiResponses`.
- `@Parameter` para path variables.
- `@Schema` en models.
- `@Content` y ejemplos cuando aporte claridad.

## Evidencia para defensa

- URL Swagger por servicio.
- Endpoint `GET`.
- Endpoint `POST` con request body.
- Codigo `201` para creacion.
- Errores `400`, `404`, `409` o `502` donde corresponda.
- Modelo con campos y ejemplos.

## Riesgos

- Incompatibilidad de version Springdoc con Spring Boot.
- Documentacion que no coincida con comportamiento real.
- Duplicar anotaciones sin valor explicativo.

## Resultado esperado

Swagger debe servir para explicar como consumir cada microservicio y para demostrar que la documentacion esta alineada con el codigo.
