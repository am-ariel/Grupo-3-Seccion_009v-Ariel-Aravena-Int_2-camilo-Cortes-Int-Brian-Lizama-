# Rubrica y requisitos interpretados

## Resumen ejecutivo

La evaluacion combina entrega tecnica grupal (40%) y defensa (60%). La rubrica no evalua solo que el codigo exista: exige poder ejecutarlo, explicar decisiones, demostrar pruebas, recorrer Swagger/OpenAPI, justificar YAML, levantar servicios y realizar cambios en vivo.

## Entrega de encargo: 40%

| Indicador | Peso | Requisito interpretable | Evidencia esperada |
| --- | ---: | --- | --- |
| IE 1.2.1 CSR | 2% | Separacion real Controller-Service-Repository/Model. Controller orquesta, Service concentra logica, Repository persiste. | Paquetes por capa, controllers delgados, services con reglas. |
| IE 2.2.1 Reglas de negocio | 3% | Validaciones, consistencia e integridad del dominio. | Bean Validation, reglas en services, errores HTTP coherentes. |
| IE 2.4.1 Comunicacion REST | 3% | Comunicacion entre microservicios con manejo de errores, codigos HTTP y flujo estable. | Feign/WebClient, DTOs, timeouts o manejo de fallas, logs. |
| IE 2.5.1 GitHub | 3% | Commits tecnicos, progresivos y ordenados. | Historial claro y participacion del equipo. |
| IE 2.5.2 Trello u otra herramienta | 2% | Tareas, responsables y avance visible. | Tablero o evidencia exportable/capturable. |
| IE 3.1.1 Pruebas unitarias | 8% | Pruebas con al menos 80% de cobertura, Given-When-Then, asserts precisos y reglas esenciales. | `src/test`, JUnit 5, Mockito, JaCoCo o reporte de cobertura. |
| IE 3.2.1 Swagger/OpenAPI | 4% | Documentar endpoints, parametros, modelos, ejemplos y codigos HTTP; debe coincidir con comportamiento real. | Dependencia `springdoc`, UI Swagger, anotaciones `@Tag`, `@Operation`, `@ApiResponse`, `@Schema`. |
| IE 3.3.1 Despliegue | 5% | Despliegue local/remoto estable con variables, puertos y parametros correctos. | Docker Compose funcional y/o PaaS, logs, comandos reproducibles. |
| IE 3.3.2 API Gateway | 5% | Gateway centraliza rutas, predicados, filtros y mapeos. | Microservicio Gateway con Spring Cloud Gateway o equivalente. |
| IE 3.3.3 Interoperabilidad por Gateway | 2% | Rutas/filtros/reglas coherentes para comunicacion estable. | Pruebas de endpoints entrando por Gateway. |
| IE 3.3.4 YAML | 3% | YAML valido para propiedades, perfiles, rutas, variables y puertos. | `application.yml` especialmente en Gateway y configuraciones relevantes. |

## Defensa: 60%

| Indicador | Peso | Que se debe poder defender |
| --- | ---: | --- |
| IE 2.2.2 Codigo y decisiones | 5% | Explicar relaciones, logica, responsabilidades e impacto en dominio. |
| IE 2.4.2 Interoperabilidad | 5% | Explicar flujo remoto, endpoints, DTOs, validaciones y coherencia entre servicios. |
| IE 2.5.3 Aporte personal | 5% | Explicar commits, tareas, rol y participacion individual. |
| IE 3.1.2 Explicacion de pruebas | 7% | Explicar Given-When-Then, mocks, asserts, reglas validadas y resultados. |
| IE 3.1.3 Prueba en vivo | 13% | Crear una nueva prueba funcional en tiempo acotado, con mocks y asserts correctos. |
| IE 3.2.2 Explicar Swagger | 5% | Recorrer rutas, parametros, modelos, request bodies, respuestas y relacion con logica real. |
| IE 3.3.5 Explicar YAML | 4% | Explicar propiedades, perfiles, rutas, variables y efecto en despliegue/comunicacion. |
| IE 3.3.6 Explicar despliegue | 6% | Describir plataforma, variables, puertos, logs, arranque y errores resueltos. |
| IE 3.3.7 Ejecutar sin apoyo | 10% | Levantar servicios, ajustar puertos/rutas/variables y resolver problemas durante defensa. |

## Requisitos minimos explicitos del encargo

- Mantener al menos 10 microservicios por equipo.
- Integrar pruebas unitarias con JUnit y Mockito u otro framework de mocking.
- Ejecutar pruebas antes de la defensa.
- Documentar con Swagger/OpenAPI.
- Configurar API Gateway.
- Usar YAML para propiedades de entorno, rutas, perfiles, variables y puertos.
- Desplegar local o remotamente con Docker, Railway, Render u otro.
- Entregar listado de microservicios, rutas principales del Gateway y enlaces Swagger.
- Buscar cobertura de pruebas de al menos 80%.

## Requisitos desde guias de clase

- Swagger/OpenAPI:
  - Dependencia sugerida: `org.springdoc:springdoc-openapi-starter-webmvc-ui`.
  - Propiedades sugeridas: habilitar api-docs y swagger-ui, definir ruta de UI.
  - Documentar con `@Tag`, `@Operation`, `@ApiResponse`, `@ApiResponses`, `@Parameter`, `@Schema`, `@Content`, `@ExampleObject`.
- HATEOAS:
  - Dependencia sugerida: `spring-boot-starter-hateoas`.
  - Crear assemblers.
  - Usar versionado de endpoints, por ejemplo `/api/v2`.
  - Responder recursos con enlaces navegables.
- Testing:
  - Aislar unidad bajo prueba.
  - Usar mocks para repositorios o clientes remotos.
  - Seguir Given-When-Then.
  - Evitar base de datos real en pruebas unitarias.
- Gateway/YAML:
  - Gateway como punto unico de entrada.
  - Rutas con predicados y filtros.
  - YAML por legibilidad en rutas anidadas y perfiles.
- Despliegue:
  - Docker Compose para entorno multiservicio.
  - Variables de entorno para URLs, puertos y credenciales.
  - Perfiles local/remoto cuando aplique.

## Criterio de prioridad

1. Pruebas unitarias y defensa de pruebas: mayor peso combinado.
2. API Gateway, YAML y ejecucion: necesarios para demostrar arquitectura distribuida.
3. Swagger/OpenAPI: evidencia visible y defendible.
4. Despliegue Docker estable: indispensable para defensa practica.
5. HATEOAS: no aparece como indicador separado en rubrica, pero si en guias; conviene implementarlo al menos en un servicio representativo si el tiempo lo permite.
