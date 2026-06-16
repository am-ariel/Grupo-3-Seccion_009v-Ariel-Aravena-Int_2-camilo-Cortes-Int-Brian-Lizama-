# Contexto del proyecto MasterBikes

## Alcance de esta etapa

Analisis inicial solicitado: leer material adjunto, revisar proyecto base, interpretar la rubrica y crear documentacion Markdown trazable. En esta etapa no se modifican funcionalidades importantes del sistema.

## Fuentes revisadas

- `C:/Users/MA-Alumno/Downloads/Evaluacion Parcial 3_Estudiante.pdf`
- `C:/Users/MA-Alumno/Downloads/3.1.1 Documentacion de Microservicios.pdf`
- `C:/Users/MA-Alumno/Downloads/3.1.2 Guia practica - Proyecto Biblioteca Duoc - Swagger.pdf`
- `C:/Users/MA-Alumno/Downloads/3.1.3 Implementacion de HATEOAS en la Documentacion de APIs.pdf`
- `C:/Users/MA-Alumno/Downloads/3.1.4 Guia practica y ejercicios - Proyecto Biblioteca Duoc - HATEOAS.pdf`
- `C:/Users/MA-Alumno/Downloads/3.1.5 Pruebas unitarias en proyectos de microservicios.pdf`
- `C:/Users/MA-Alumno/Downloads/3.2.1 API Gateway.pdf`
- `C:/Users/MA-Alumno/Downloads/3.2.2 Despligue de microservicios.pdf`
- Proyecto base en la raiz del repositorio.

Textos extraidos de apoyo: `tmp/pdfs/*.txt`.

## Documentacion creada

- `docs/rubrica-requisitos.md`: interpretacion de la rubrica y evidencias esperadas.
- `docs/analisis-estado-inicial.md`: diagnostico del proyecto base contra los requisitos.
- `docs/guia-defensa-inicial.md`: argumentos iniciales para explicar el proyecto y prepararse para la defensa.

## Decisiones iniciales

- Mantener el analisis y la documentacion en espanol, con foco en trazabilidad para defensa.
- No alterar comportamiento funcional durante esta primera etapa.
- Priorizar cumplimiento de rubrica sobre agregados cosmeticos.
- Tratar Swagger/OpenAPI, pruebas unitarias, API Gateway, YAML y despliegue como los principales faltantes tecnicos.
- Preservar cambios previos detectados en `.gitignore` y `masterbikes-arriendos/dockerfile`.

## Hallazgos principales

- El proyecto contiene 8 microservicios con codigo fuente completo: usuarios, bicicletas, arriendos, inventario, ventas, despachos, pagos y proveedores.
- `docker-compose.yml` tambien referencia `masterbike-vm` como servicio de clientes, pero esa carpeta no existe en el arbol actual.
- `masterbike-vm-atencion` existe, pero no contiene `src/main/java` ni `src/main/resources`; solo se observaron `pom.xml`, wrappers y `dockerfile`.
- No se detectaron clases de prueba `*Test.java` ni carpetas `src/test`.
- Hay dependencia `spring-boot-starter-test` en los `pom.xml`, pero no hay evidencia de pruebas implementadas.
- No se detectaron dependencias ni anotaciones de Swagger/OpenAPI (`springdoc`, `@Operation`, `@Tag`, etc.).
- No se detecto HATEOAS (`spring-boot-starter-hateoas`, assemblers, `EntityModel`, `linkTo`, etc.).
- No se detecto API Gateway ni Spring Cloud Gateway.
- La configuracion esta principalmente en `application.properties`; la rubrica pide YAML para puertos, rutas, perfiles y variables relevantes.
- Existe comunicacion REST mediante OpenFeign en pagos hacia ventas; la documentacion tambien menciona servicio tecnico hacia clientes, pero el codigo de atencion/clientes no esta disponible en el estado actual.

## Riesgos

- Riesgo alto: la rubrica exige al menos 10 microservicios por equipo, pero el repositorio visible no contiene 10 microservicios funcionales con codigo fuente.
- Riesgo alto: el `docker-compose.yml` puede fallar al construir por la referencia a `./masterbike-vm`, carpeta ausente.
- Riesgo alto: pruebas unitarias pesan 8% en entrega y 20% en defensa si se consideran explicacion e implementacion en vivo; actualmente no hay pruebas.
- Riesgo alto: API Gateway pesa 5% en entrega, 2% de interoperabilidad por rutas/filtros y 10% en ejecucion/defensa; actualmente no existe.
- Riesgo medio: Swagger/OpenAPI pesa 4% en entrega y 5% en defensa; actualmente no hay documentacion viva.
- Riesgo medio: HATEOAS aparece en guias de clase y puede ser esperado por el docente, aunque no aparece como indicador separado en la rubrica final.
- Riesgo medio: falta evidencia de Trello u otra herramienta colaborativa.
- Riesgo medio: Git tiene cambios previos no hechos en esta etapa; no revertir sin instruccion.

## Pendientes priorizados

1. Resolver inconsistencia de microservicios: decidir si se restaura/crea `masterbike-vm` y si se completa `masterbike-vm-atencion`, o si se ajusta la documentacion y compose al alcance real.
2. Agregar API Gateway como microservicio independiente con `application.yml`, rutas a cada servicio y, si conviene, filtros simples de trazabilidad.
3. Incorporar Swagger/OpenAPI en servicios clave o en todos los servicios, con `springdoc` y anotaciones defendibles.
4. Implementar pruebas unitarias con JUnit 5 y Mockito, empezando por reglas de negocio de `PagoService`, `ArriendoService`, `InventarioService` y servicios con validaciones de duplicados.
5. Agregar JaCoCo o evidencia equivalente de cobertura para acercarse al 80%.
6. Convertir o complementar configuraciones con YAML cuando aporte a rubrica: Gateway, perfiles, puertos, variables de entorno y rutas.
7. Revisar despliegue Docker: Dockerfiles, build context, puertos, dependencias y scripts.
8. Preparar evidencia de defensa: comandos, capturas o pasos reproducibles para Swagger, Gateway, pruebas y Docker.

## Proximos pasos sugeridos

- Primero corregir estructura/base de ejecucion para que el sistema levante.
- Luego implementar Gateway y YAML porque desbloquea defensa de rutas centralizadas.
- Despues implementar Swagger/OpenAPI para documentacion viva.
- Luego pruebas unitarias y cobertura, dejando al menos un patron claro para agregar una prueba en vivo.
- Finalmente actualizar `README.md`, `GUIA_DEFENSA.md` y documentacion con comandos exactos validados.

## Actualizacion fase: plan tecnico de implementacion y normalizacion documental

Se leyeron nuevamente:

- `contexto.md`
- `docs/rubrica-requisitos.md`
- `docs/analisis-estado-inicial.md`
- `docs/guia-defensa-inicial.md`
- `docker-compose.yml`
- Estructura de microservicios, controllers, services, repositories, models, properties, Dockerfiles y dependencias relevantes.

### Documentacion numerada creada

- `docs/01_resumen_asignatura.md`
- `docs/02_rubrica_interpretada.md`
- `docs/03_requisitos_funcionales.md`
- `docs/04_requisitos_no_funcionales.md`
- `docs/05_estado_inicial_proyecto.md`
- `docs/06_plan_de_implementacion.md`
- `docs/07_mapa_del_codigo.md`
- `docs/08_trazabilidad_rubrica.md`
- `docs/09_arquitectura_objetivo.md`
- `docs/10_plan_defensa_tecnica.md`
- `docs/11_plan_pruebas_unitarias.md`
- `docs/12_plan_swagger_openapi.md`
- `docs/13_plan_api_gateway_yaml.md`
- `docs/14_plan_docker_despliegue.md`
- `docs/15_riesgos_y_pendientes.md`

Los documentos anteriores de analisis se conservan como antecedentes:

- `docs/rubrica-requisitos.md`
- `docs/analisis-estado-inicial.md`
- `docs/guia-defensa-inicial.md`

### Diagnostico confirmado

- Microservicios reales completos: 8 (`usuarios`, `bicicletas`, `arriendos`, `inventario`, `ventas`, `despachos`, `pagos`, `proveedores`).
- Microservicio incompleto: `masterbike-vm-atencion`. Problema exacto: existe `pom.xml`, wrappers y `dockerfile`, pero no existe `src`; el Dockerfile copia `src`, por lo que el build falla.
- Microservicio faltante: `masterbike-vm`. Problema exacto: `docker-compose.yml` lo usa como build context de `masterbikes-clientes`, pero la carpeta no existe.
- `docker-compose.yml` define 10 servicios de aplicacion y 10 bases MySQL, pero solo 8 servicios de aplicacion son construibles con codigo completo.
- No se implementaron funcionalidades grandes en esta fase.

### Proximo paso recomendado actualizado

Fase 2: completar el minimo de 10 microservicios antes de agregar Gateway, Swagger o tests. La prioridad tecnica es reconstruir/restaurar `masterbike-vm` como Clientes y completar `masterbike-vm-atencion` como Servicio Tecnico, manteniendo CSR, Flyway, Dockerfile y comunicacion Feign hacia clientes.

## Actualizacion fase 2: minimo real de 10 microservicios

Se implemento la Fase 2 sin agregar Swagger, API Gateway, HATEOAS ni pruebas unitarias.

### Microservicios creados o completados

- `masterbike-vm`: creado como microservicio de Clientes.
  - Paquete base: `com.masterbikes.clientes`.
  - Puerto: 8081.
  - Endpoint base: `/api/v1/clientes`.
  - Capas: `controllers`, `services`, `repositories`, `models`, `exceptions`.
  - Incluye Flyway `V1__create_cliente_table.sql` y `V2__insert_clientes.sql`.
- `masterbike-vm-atencion`: completado como microservicio de Atencion/Servicio Tecnico.
  - Paquete base: `com.masterbikes.atencion`.
  - Puerto: 8082.
  - Endpoint base: `/api/v1/atenciones`.
  - Capas: `controllers`, `services`, `repositories`, `models`, `exceptions`.
  - Incluye Flyway `V1__create_atencion_table.sql` y `V2__insert_atenciones.sql`.

### Decisiones tecnicas de esta fase

- Clientes sigue el estilo de Proveedores: RUT unico, busqueda por RUT, validaciones de correo, telefono y direccion.
- Atencion se relaciona con cliente y bicicleta mediante `idCliente` e `idBicicleta`, sin acoplar bases ni agregar Feign nuevo, tal como se pidio para esta fase.
- Se removio de `masterbike-vm-atencion/pom.xml` la dependencia OpenFeign y su dependency management porque esta fase no implementa comunicacion remota nueva.
- Se removio de `docker-compose.yml` la variable `CLIENTE_SERVICE_URL` de servicio tecnico porque no se usa en esta fase.

### Verificaciones realizadas

- Se confirmo estructura de 10 controllers en el proyecto.
- Se confirmo existencia de `application.properties` para Clientes y Atencion.
- `docker compose config` ejecuto correctamente y ya no apunta a carpetas inexistentes.
- No se pudo compilar con `mvn` local porque Maven no esta instalado en PATH.
- El Maven Wrapper de `masterbike-vm-atencion` no es usable porque falta `.mvn/wrapper/maven-wrapper.properties`.
- `docker compose build masterbikes-clientes masterbikes-servicio-tecnico` no pudo completarse porque Docker Desktop no esta activo: `dockerDesktopLinuxEngine` no responde.

### Estado actualizado

- Microservicios reales completos: 10.
- Incompletos: 0 detectados a nivel de estructura/codigo base.
- Faltantes: 0 detectados.
- Pendiente tecnico inmediato: validar build y arranque cuando Docker Desktop este activo.

### Proximo paso recomendado

Continuar con la Fase 3: revisar CSR y reglas de negocio en los 10 microservicios. En paralelo, activar Docker Desktop y validar `docker compose build masterbikes-clientes masterbikes-servicio-tecnico`.

## Actualizacion fase: validacion compilacion y Docker

Se valido la estructura minima de los 10 microservicios:

- `pom.xml`
- `src/main/java`
- `src/main/resources`
- `Application.java`
- controller
- service
- repository
- model/entity
- `application.properties`
- `dockerfile`
- migraciones Flyway

### Maven Wrapper

- Solo existia `mvnw.cmd` en `masterbike-vm-atencion`.
- Estaba incompleto porque faltaba `.mvn/wrapper/maven-wrapper.properties`.
- Se agrego `masterbike-vm-atencion/.mvn/wrapper/maven-wrapper.properties`.
- Se uso ese wrapper para compilar todos los microservicios con `-f <servicio>/pom.xml`.
- Se seteo `JAVA_HOME` a `C:\Program Files\Java\jdk-21.0.10`.

### Resultado de compilacion

Los 10 microservicios compilaron y empaquetaron correctamente con:

```powershell
.\masterbike-vm-atencion\mvnw.cmd -f <servicio>\pom.xml clean package -DskipTests
```

Fue necesario usar flags SSL de Maven por un problema de certificados PKIX al resolver plugins desde Maven Central:

```powershell
-Dmaven.wagon.http.ssl.insecure=true
-Dmaven.wagon.http.ssl.allowall=true
-Dmaven.wagon.http.ssl.ignore.validity.dates=true
```

### Resultado Docker

- `docker compose config` fue exitoso.
- `docker compose config --services` mostro 20 servicios: 10 aplicaciones y 10 bases MySQL.
- `docker compose build masterbikes-clientes masterbikes-servicio-tecnico` no pudo ejecutarse porque Docker Desktop no esta activo: `dockerDesktopLinuxEngine` no responde.
- No se probaron endpoints HTTP porque los servicios no quedaron levantados.

### Documentacion creada

- `docs/16_validacion_compilacion_docker.md`

### Proximo paso recomendado actualizado

Activar Docker Desktop y ejecutar build/up de Compose. Si Docker levanta correctamente, probar endpoints manuales de Clientes y Atenciones. Despues continuar con Fase 3 o pasar a pruebas unitarias segun prioridad del equipo.

## Actualizacion fase 3: pruebas unitarias con JUnit, Mockito y JaCoCo

Se implementaron pruebas unitarias para la capa `Service` de los 10 microservicios:

- `ClienteServiceTest`
- `AtencionServiceTest`
- `UsuarioServiceTest`
- `BicicletaServiceTest`
- `ArriendoServiceTest`
- `InventarioServiceTest`
- `VentaServiceTest`
- `DespachoServiceTest`
- `PagoServiceTest`
- `ProveedorServiceTest`

### Decisiones

- Las pruebas no usan MySQL real, Docker ni contexto Spring completo.
- Se usa JUnit 5 con Mockito para mockear repositories y `VentaClient` en pagos.
- Se agrego `jacoco-maven-plugin` version `0.8.12` en los 10 `pom.xml`.
- Se mantienen las validaciones de campos obligatorios mediante Bean Validation en los tests.
- No se implemento Swagger, API Gateway, HATEOAS ni cambios grandes de arquitectura.

### Resultado

- Total tests ejecutados: 105.
- Resultado: 105 OK, 0 fallas, 0 errores.
- Cobertura JaCoCo por capa `Service`: sobre 90% en todos los microservicios.
- Cobertura JaCoCo global: entre 39% y 52%, porque incluye controllers, handlers, application classes y models no cubiertos en esta fase.

### Errores de entorno

- Maven fallo inicialmente en sandbox por no poder escribir en `C:\Users\MA-Alumno\.m2\repository`.
- Fue necesario ejecutar Maven Wrapper con permisos fuera del sandbox.
- Los flags SSL por PKIX se usaron solo en comando, no se guardaron en codigo.
- PowerShell requirio pasar los flags `-D` entre comillas.

### Pendientes

1. Ampliar cobertura global si se exige 80% total, agregando pruebas de controllers y handlers.
2. Activar Docker Desktop y validar `docker compose up --build`.
3. Avanzar a Swagger/OpenAPI.
4. Luego implementar API Gateway con YAML.

### Proximo paso recomendado

Si la defensa prioriza pruebas, ampliar tests de controllers y exception handlers. Si el equipo necesita cubrir mas indicadores nuevos de rubrica, continuar con Swagger/OpenAPI.
