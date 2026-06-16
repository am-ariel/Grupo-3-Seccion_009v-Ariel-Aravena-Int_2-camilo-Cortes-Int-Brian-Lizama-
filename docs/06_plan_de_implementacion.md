# 06 Plan de implementacion

## Fase 1 - Normalizacion documental y decision de alcance

## Objetivo

Dejar claro el estado real del proyecto, las brechas contra rubrica y el orden de trabajo.

## Archivos a modificar

- `docs/01_resumen_asignatura.md` a `docs/15_riesgos_y_pendientes.md`.
- `contexto.md`.

## Riesgo

Bajo. No cambia comportamiento funcional.

## Como probar

Revisar que la documentacion sea coherente con el arbol real y `docker-compose.yml`.

## Que punto de rubrica cubre

Apoya defensa completa, README, trazabilidad y planificacion.

## Que debo estudiar para defenderlo

Explicar brechas sin esconderlas y justificar el orden de implementacion.

## Fase 2 - Corregir minimo de 10 microservicios

Estado: completada a nivel de estructura, codigo base y compilacion Maven Wrapper. Pendiente verificar build Docker cuando Docker Desktop este activo.

## Objetivo

Resolver `masterbike-vm` faltante y `masterbike-vm-atencion` incompleto para llegar a 10 microservicios reales.

## Archivos a modificar

- `masterbike-vm`: creado como microservicio de clientes.
- `masterbike-vm-atencion/src/main/java`: completado.
- `masterbike-vm-atencion/src/main/resources`: completado.
- `masterbike-vm-atencion/pom.xml`: ajustado para no usar Feign en esta fase.
- `docker-compose.yml`: ajustado para quitar variable remota no usada y confirmar rutas reales.
- Documentacion de fase actualizada.

## Riesgo

Medio. La estructura y compilacion Maven estan validadas, pero falta validar build y arranque con Docker Desktop activo.

## Como probar

- Compilar ambos servicios con Maven o Docker.
- Levantar sus bases de datos.
- Probar CRUD de clientes y atenciones.
- Ejecutar `docker compose config`.
- Ejecutar `docker compose build masterbikes-clientes masterbikes-servicio-tecnico` con Docker Desktop activo.

## Que punto de rubrica cubre

Minimo de 10 microservicios, CSR, reglas, despliegue y comunicacion REST por dominio.

## Que debo estudiar para defenderlo

Por que clientes y atencion son microservicios separados, como se relacionan por IDs simples y por que no se acoplan bases de datos.

## Fase 3 - Revisar CSR y reglas de negocio

## Objetivo

Confirmar que cada microservicio tenga controller delgado, service con reglas, repository de persistencia y model validado.

## Archivos a modificar

- Controllers, services, repositories y models de cada microservicio si se detectan brechas.
- `GlobalExceptionHandler` si hay respuestas inconsistentes.

## Riesgo

Medio. Puede cambiar comportamiento de validaciones y errores.

## Como probar

- Pruebas manuales de CRUD.
- Casos invalidos con `POST` y `PUT`.
- Verificar codigos `400`, `404`, `409`, `502` donde corresponda.

## Que punto de rubrica cubre

IE 1.2.1, IE 2.2.1, IE 2.2.2.

## Que debo estudiar para defenderlo

Responsabilidad de cada capa y reglas de negocio por dominio.

## Fase 4 - Pruebas unitarias y cobertura

Estado: completada para capa `Service` en los 10 microservicios. Tests ejecutados correctamente con Maven Wrapper, JUnit 5, Mockito y JaCoCo. Pendiente ampliar cobertura global a controllers/handlers si se exige 80% global.

## Objetivo

Agregar tests JUnit 5/Mockito para reglas de negocio y preparar cobertura objetivo 80%.

## Archivos a modificar

- `src/test/java` en los 10 microservicios.
- `pom.xml` de los 10 microservicios con `jacoco-maven-plugin`.
- `docs/17_pruebas_unitarias_resultado.md`.
- `docs/18_guia_defensa_pruebas_unitarias.md`.

## Riesgo

Medio. No deberia cambiar produccion, pero puede revelar errores.

## Como probar

- Ejecutar `.\masterbike-vm-atencion\mvnw.cmd -f <microservicio>\pom.xml clean test jacoco:report`.
- Abrir `target/site/jacoco/index.html`.
- Verificar que no se use MySQL real ni Docker.
- Revisar tests con Given-When-Then y mocks de repositories/Feign.

## Que punto de rubrica cubre

IE 3.1.1, IE 3.1.2, IE 3.1.3.

## Que debo estudiar para defenderlo

JUnit, Mockito, mocks de repository/clientes Feign, asserts y lectura de reporte de cobertura.

## Fase 5 - Swagger/OpenAPI

## Objetivo

Agregar documentacion viva en microservicios principales y dejar endpoints explorables.

## Archivos a modificar

- `pom.xml` de microservicios.
- Configuracion Swagger/OpenAPI.
- Controllers y models con anotaciones.
- README con URLs Swagger.

## Riesgo

Medio. Cambia dependencias y puede fallar build si hay incompatibilidad de versiones.

## Como probar

- Levantar servicio.
- Abrir `/swagger-ui/index.html` o ruta definida.
- Verificar endpoints, modelos, request bodies y respuestas.

## Que punto de rubrica cubre

IE 3.2.1, IE 3.2.2.

## Que debo estudiar para defenderlo

OAS, Swagger UI, `@Tag`, `@Operation`, `@ApiResponse`, `@Schema`.

## Fase 6 - API Gateway y YAML

## Objetivo

Crear Gateway con rutas centralizadas, predicados por Path y filtros basicos.

## Archivos a modificar

- Nuevo microservicio Gateway.
- `application.yml` del Gateway.
- `docker-compose.yml`.
- README y docs de rutas.

## Riesgo

Alto. Impacta forma de consumir APIs y despliegue.

## Como probar

- Levantar servicios destino.
- Consumir endpoints por Gateway.
- Ver logs/filtros.
- Validar rutas incorrectas.

## Que punto de rubrica cubre

IE 3.3.2, IE 3.3.3, IE 3.3.4, IE 3.3.5, IE 3.3.7.

## Que debo estudiar para defenderlo

Gateway como punto unico de entrada, rutas, predicados, filtros, YAML e impacto en despliegue.

## Fase 7 - Docker y despliegue local

## Objetivo

Corregir Dockerfiles, Compose y scripts para levantar el ecosistema completo.

## Archivos a modificar

- `docker-compose.yml`.
- Dockerfiles.
- `scripts/*.ps1`.
- README.

## Riesgo

Alto. Requiere entorno Docker y puede aparecer conflicto de puertos.

## Como probar

- `docker compose config`.
- `docker compose build masterbikes-clientes masterbikes-servicio-tecnico`.
- `docker compose up --build`.
- Probar endpoints por servicio y luego por Gateway cuando exista.
- Revisar logs y estado.

## Que punto de rubrica cubre

IE 3.3.1, IE 3.3.6, IE 3.3.7.

## Que debo estudiar para defenderlo

Imagen, contenedor, volumen, red, variables de entorno, puertos y logs.

## Fase 8 - README y defensa individual

## Objetivo

Preparar la entrega explicable y el guion de defensa.

## Archivos a modificar

- `README.md`.
- `GUIA_DEFENSA.md`.
- `docs/10_plan_defensa_tecnica.md`.

## Riesgo

Bajo. Documental.

## Como probar

Seguir README desde cero y verificar que los comandos funcionen.

## Que punto de rubrica cubre

Defensa completa, aporte individual, Swagger, tests, YAML, Docker y ejecucion.

## Que debo estudiar para defenderlo

Flujo completo desde arquitectura hasta prueba en vivo.
