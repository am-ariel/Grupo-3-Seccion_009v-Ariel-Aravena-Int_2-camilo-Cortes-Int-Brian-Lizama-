# 04 Requisitos no funcionales

## Arquitectura

- Microservicios independientes por dominio.
- Una base de datos MySQL por microservicio.
- Separacion CSR: Controller, Service, Repository y Model.
- Comunicacion REST entre servicios cuando exista dependencia de dominio.

## Calidad

- Pruebas unitarias con JUnit 5 y Mockito.
- Cobertura objetivo de 80% cuando sea posible.
- Pruebas aisladas sin base de datos real.
- Manejo global de errores con respuestas HTTP claras.
- Logs en operaciones relevantes.

## Documentacion

- Swagger/OpenAPI para endpoints principales.
- README actualizado con comandos reales.
- Documentacion de arquitectura, Gateway, Docker, pruebas y defensa.
- Trazabilidad directa entre rubrica, brecha y accion.

## Configuracion

- Uso de variables de entorno para credenciales y URLs.
- Uso de YAML para Gateway, rutas, perfiles, puertos y configuracion relevante.
- Configuraciones separables para local, Docker y remoto si se despliega fuera del equipo.

## Despliegue

- Dockerfile por microservicio.
- `docker-compose.yml` funcional.
- Puertos documentados.
- Bases de datos con volumen persistente.
- Scripts de apoyo revisados y alineados al compose.

## Observabilidad minima

- Logs de arranque.
- Logs en reglas de negocio y comunicacion remota.
- Comandos para revisar estado y logs durante defensa.

## Seguridad minima

- No guardar secretos reales en codigo.
- Usar variables de entorno para credenciales.
- Mantener credenciales de desarrollo solo como valores locales de prueba.
