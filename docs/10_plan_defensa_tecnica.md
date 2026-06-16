# 10 Plan defensa tecnica

## Que debo explicar primero

Primero explicar que MasterBikes esta dividido por dominios de negocio. Cada microservicio tiene su propia base de datos, su propio puerto, sus capas CSR y sus migraciones Flyway. Luego reconocer que el Gateway centraliza el acceso y que Docker Compose orquesta servicios y bases.

## Como explicar la arquitectura

Respuesta sugerida:

> La arquitectura separa responsabilidades por dominio. Cada microservicio administra sus propios datos y expone endpoints REST. Cuando un servicio necesita validar informacion de otro dominio, no consulta su base directamente: consume el microservicio correspondiente mediante REST. Asi mantenemos independencia y evitamos acoplar bases de datos.

## Como explicar un microservicio

Elegir uno simple, por ejemplo inventario:

- El controller recibe HTTP.
- El service aplica regla de negocio.
- El repository persiste con JPA.
- El model representa la tabla y valida datos.
- Flyway crea y carga datos iniciales.

## Como explicar CSR

Respuesta sugerida:

> CSR significa separar Controller, Service y Repository/Model. El controller no decide reglas ni guarda datos; solo recibe la peticion y llama al service. El service contiene la logica de negocio. El repository usa JPA para acceder a la base. El model define la entidad y sus validaciones.

## Como explicar Swagger

Respuesta sugerida:

> Swagger/OpenAPI es la documentacion viva de la API. Permite ver rutas, parametros, request body, modelos y codigos de respuesta. Lo importante es que la documentacion coincida con el comportamiento real del endpoint.

Puntos a mostrar:

- URL de Swagger UI.
- Un `GET`.
- Un `POST` con request body.
- Respuestas `200`, `201`, `400`, `404` o `409`.
- Modelo con validaciones.

## Como explicar pruebas unitarias

Respuesta sugerida:

> Las pruebas unitarias validan una unidad aislada, normalmente el service. Uso Mockito para simular repositorios o clientes Feign, asi la prueba no depende de MySQL ni de red. Sigo Given-When-Then: preparo datos y mocks, ejecuto el metodo, y verifico resultado o excepcion.

Prueba recomendada para defensa:

- `InventarioService.guardar` marca `aumento = true` si `stock <= stockMinimo`.
- `PagoService.guardar` lanza conflicto si ya existe pago para la venta.
- `ArriendoService.guardar` lanza `400` si fecha fin es anterior.

## Como explicar Gateway

Respuesta sugerida:

> El Gateway es el punto unico de entrada. El cliente no necesita conocer todos los puertos internos. El Gateway evalua predicados como el path, aplica filtros basicos y redirige la solicitud al microservicio correcto.

Puntos a mostrar:

- Ruta publica.
- URI interna.
- Predicado `Path`.
- Filtro simple, por ejemplo agregar header o log.

## Como explicar YAML

Respuesta sugerida:

> YAML es util para configuraciones jerarquicas como las rutas del Gateway. Permite definir listas de rutas con `id`, `uri`, `predicates` y `filters` de forma mas legible que properties. Tambien sirve para perfiles y variables por entorno.

## Como explicar Docker

Respuesta sugerida:

> Docker empaqueta cada microservicio como imagen. Docker Compose levanta todos los contenedores, sus bases MySQL, puertos, variables de entorno y volumenes. Los nombres de servicio permiten que los contenedores se comuniquen dentro de la red de Compose.

## Preguntas probables del profesor

| Pregunta | Respuesta sugerida |
| --- | --- |
| Por que microservicios y no monolito? | Porque cada dominio evoluciona de forma independiente y tiene su propia base de datos. |
| Donde esta la regla de negocio? | En la capa service, por ejemplo `InventarioService` y `PagoService`. |
| Por que no usar foreign keys entre servicios? | Porque cada microservicio posee su propia base; la consistencia entre dominios se valida por REST. |
| Que hace Mockito? | Simula dependencias como repositories o clientes Feign para probar el service aislado. |
| Que demuestra Swagger? | Que la API esta documentada y se puede consumir con parametros, modelos y respuestas claras. |
| Que hace el Gateway? | Centraliza el acceso y enruta por path hacia cada microservicio. |
| Por que YAML? | Porque las rutas y perfiles son estructuras anidadas mas legibles en YAML. |
| Como sabes que Docker funciona? | Con `docker compose up --build`, logs limpios y pruebas de endpoints. |
| Que pasa si ventas no responde cuando pago consulta? | Pagos debe devolver un error controlado, como `502 Bad Gateway`. |
| Que falta del estado inicial? | Clientes falta y servicio tecnico esta incompleto; se planifica corregirlo antes de implementar Gateway completo. |
