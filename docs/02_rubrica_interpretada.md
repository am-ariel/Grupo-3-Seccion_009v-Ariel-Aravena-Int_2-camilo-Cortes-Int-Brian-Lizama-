# 02 Rubrica interpretada

## Lectura general

La rubrica combina dos dimensiones:

- Entrega grupal: 40%.
- Defensa: 60%.

La defensa pesa mas que la entrega, por lo que cada implementacion debe quedar explicable: que hace, por que se hizo, como se prueba y que evidencia muestra.

## Prioridad tecnica acordada

1. Completar o corregir el minimo de 10 microservicios.
2. Asegurar patron CSR en cada microservicio.
3. Agregar pruebas unitarias con JUnit y Mockito.
4. Lograr cobertura minima de 80% cuando sea posible.
5. Implementar Swagger/OpenAPI en microservicios principales.
6. Implementar o corregir API Gateway.
7. Configurar rutas, predicados y filtros basicos del Gateway.
8. Usar YAML para puertos, perfiles, rutas y configuracion.
9. Corregir Dockerfile y `docker-compose.yml`.
10. Preparar despliegue local y, si corresponde, remoto.
11. Preparar README principal.
12. Preparar guia de defensa individual.

## Indicadores de entrega

| Indicador | Peso | Interpretacion |
| --- | ---: | --- |
| IE 1.2.1 CSR | 2% | Controller, Service, Repository y Model separados, con responsabilidades reales. |
| IE 2.2.1 Reglas | 3% | Reglas de negocio, validaciones e integridad del dominio. |
| IE 2.4.1 REST | 3% | Comunicacion entre microservicios con errores y codigos HTTP adecuados. |
| IE 2.5.1 GitHub | 3% | Historial ordenado, progresivo y tecnico. |
| IE 2.5.2 Trello | 2% | Planificacion visible con roles y avance. |
| IE 3.1.1 Tests | 8% | JUnit/Mockito, Given-When-Then, asserts claros, cobertura objetivo 80%. |
| IE 3.2.1 Swagger | 4% | Endpoints, parametros, modelos, ejemplos y codigos HTTP documentados. |
| IE 3.3.1 Despliegue | 5% | Docker o plataforma remota estable, con variables y puertos correctos. |
| IE 3.3.2 Gateway | 5% | Rutas centralizadas, predicados, filtros y mapeos coherentes. |
| IE 3.3.3 Interoperabilidad | 2% | Flujo estable entre servicios a traves de rutas/filtros. |
| IE 3.3.4 YAML | 3% | Configuracion limpia para rutas, perfiles, variables y puertos. |

## Indicadores de defensa

| Indicador | Peso | Interpretacion |
| --- | ---: | --- |
| IE 2.2.2 Codigo | 5% | Explicar relaciones, logica y decisiones tecnicas. |
| IE 2.4.2 Interoperabilidad | 5% | Explicar flujo remoto, DTOs, endpoints y coherencia entre servicios. |
| IE 2.5.3 Aporte | 5% | Explicar rol personal, tareas y commits. |
| IE 3.1.2 Tests | 7% | Explicar mocks, asserts, Given-When-Then y regla probada. |
| IE 3.1.3 Test en vivo | 13% | Crear una prueba nueva funcional durante la defensa. |
| IE 3.2.2 Swagger | 5% | Recorrer UI y explicar consumo de endpoints. |
| IE 3.3.5 YAML | 4% | Explicar propiedades, perfiles y efecto en despliegue. |
| IE 3.3.6 Despliegue | 6% | Explicar Docker/PaaS, variables, puertos, logs y errores. |
| IE 3.3.7 Ejecucion | 10% | Levantar y ajustar servicios sin apoyo importante. |

## Lectura estrategica

Los puntos mas sensibles son pruebas, Gateway/YAML, despliegue y ejecucion en defensa. Swagger es visible y rapido de evaluar. HATEOAS aparece en guias, pero no como indicador separado; conviene considerarlo como mejora si el tiempo lo permite.
