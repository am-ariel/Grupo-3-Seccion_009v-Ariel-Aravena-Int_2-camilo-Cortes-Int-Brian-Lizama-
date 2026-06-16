# 15 Riesgos y pendientes

## Riesgos criticos

| Riesgo | Impacto | Mitigacion |
| --- | --- | --- |
| Build Docker no validado por Docker Desktop inactivo. | Riesgo en despliegue y defensa. | Ejecutar `docker compose build` y `docker compose up` con Docker activo. |
| Cobertura global JaCoCo bajo 80%. | Puede perder puntaje si el docente exige cobertura total del microservicio y no solo service. | Agregar pruebas de controllers, handlers y validaciones de request. |
| No hay Gateway. | No cumple rubrica Gateway/YAML. | Crear `masterbikes-gateway` con `application.yml`. |
| Compose completo no esta levantado aun. | Alto riesgo en defensa practica. | Probar `docker compose up --build` y endpoints CRUD. |

## Riesgos altos

| Riesgo | Impacto | Mitigacion |
| --- | --- | --- |
| No hay Swagger/OpenAPI. | No se puede recorrer documentacion viva. | Agregar Springdoc y anotaciones. |
| No hay YAML. | No se puede explicar configuracion solicitada. | Usar YAML en Gateway y perfiles. |
| Advertencias de Mockito/Byte Buddy en JDK 21. | No falla hoy, pero podria requerir configuracion adicional en versiones futuras de JDK. | Mantener dependencias actualizadas y revisar configuracion de agente Mockito si aparece bloqueo. |
| Servicio tecnico depende de clientes. | Si clientes falla, atencion puede quedar bloqueado en compose. | Validar ambos servicios juntos y considerar healthchecks despues. |

## Riesgos medios

| Riesgo | Impacto | Mitigacion |
| --- | --- | --- |
| HATEOAS no implementado. | Puede ser preguntado por guias de clase. | Implementar en un servicio si hay tiempo o explicar como mejora pendiente. |
| Falta evidencia Trello. | Pierde 2%. | Preparar captura/enlace y documentar roles. |
| README no esta alineado al nuevo endpoint de atencion. | Puede confundir defensa. | Actualizar README despues de validar servicios. |
| Cambios previos en Git. | Riesgo de pisar trabajo ajeno. | No revertir `.gitignore` ni `masterbikes-arriendos/dockerfile` sin permiso. |

## Pendientes ordenados

1. Validar build Docker de clientes y atencion con Docker Desktop activo.
2. Probar CRUD manual de clientes y atenciones.
3. Validar CSR y reglas por servicio.
4. Ampliar cobertura global con controllers/handlers si se prioriza llegar a 80% total.
5. Agregar Swagger/OpenAPI.
6. Crear Gateway con YAML.
7. Probar Docker Compose completo.
8. Actualizar README y guia de defensa.
9. Preparar evidencia Trello/Git.

## Proximo paso recomendado

Si la prioridad es maximizar puntaje de tests, ampliar pruebas a controllers y handlers para subir cobertura global. Si la prioridad es cubrir nuevas secciones de rubrica, avanzar a Swagger/OpenAPI y luego API Gateway con YAML.
