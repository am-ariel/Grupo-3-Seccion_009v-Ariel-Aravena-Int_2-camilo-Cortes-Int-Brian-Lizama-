# 17 Pruebas unitarias resultado

## Objetivo

Registrar la implementacion y ejecucion de pruebas unitarias de la Fase 3 para los 10 microservicios. Las pruebas se enfocan en capa `Service`, usan JUnit 5 y Mockito, no levantan Spring, no usan Docker y no dependen de MySQL real.

## Comandos ejecutados

Se uso el Maven Wrapper disponible en `masterbike-vm-atencion` para todos los `pom.xml`:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21.0.10'
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
.\masterbike-vm-atencion\mvnw.cmd -f <microservicio>\pom.xml clean test jacoco:report "-Dmaven.wagon.http.ssl.insecure=true" "-Dmaven.wagon.http.ssl.allowall=true" "-Dmaven.wagon.http.ssl.ignore.validity.dates=true"
```

Los flags SSL se usaron solo en la ejecucion por el problema PKIX ya documentado del entorno Maven. No quedaron guardados en el codigo.

## Resultado por microservicio

| Microservicio | Clase testeada | Archivo test | Casos cubiertos | Comando ejecutado | Resultado | Cobertura JaCoCo | Estado |
| ------------- | -------------- | ------------ | --------------- | ----------------- | --------- | ---------------- | ------ |
| `masterbike-vm` | `ClienteService` | `masterbike-vm/src/test/java/com/masterbikes/clientes/services/ClienteServiceTest.java` | 11: listar, buscar por ID, ID inexistente, buscar por RUT, crear valido, RUT duplicado, validacion RUT obligatorio, actualizar, actualizar inexistente, eliminar, eliminar inexistente | `.\masterbike-vm-atencion\mvnw.cmd -f masterbike-vm\pom.xml clean test jacoco:report ...` | OK: 11 tests, 0 fallas | Global 46.67%; Service 99.25% | Aprobado |
| `masterbike-vm-atencion` | `AtencionService` | `masterbike-vm-atencion/src/test/java/com/masterbikes/atencion/services/AtencionServiceTest.java` | 11: listar, buscar por ID, ID inexistente, buscar por cliente, crear valido, idCliente obligatorio, descripcion obligatoria, actualizar, actualizar inexistente, eliminar, eliminar inexistente | `.\masterbike-vm-atencion\mvnw.cmd -f masterbike-vm-atencion\pom.xml clean test jacoco:report ...` | OK: 11 tests, 0 fallas | Global 46.44%; Service 100.00% | Aprobado |
| `masterbikes-usuarios` | `UsuarioService` | `masterbikes-usuarios/src/test/java/com/masterbikes/usuarios/services/UsuarioServiceTest.java` | 9: listar, buscar por ID, ID inexistente, crear valido, correo obligatorio, actualizar, actualizar inexistente, eliminar, eliminar inexistente | `.\masterbike-vm-atencion\mvnw.cmd -f masterbikes-usuarios\pom.xml clean test jacoco:report ...` | OK: 9 tests, 0 fallas | Global 39.21%; Service 100.00% | Aprobado |
| `masterbikes-bicicletas` | `BicicletaService` | `masterbikes-bicicletas/src/test/java/com/masterbikes/bicicletas/services/BicicletaServiceTest.java` | 10: listar, buscar por ID, ID inexistente, crear valido, codigo obligatorio, tarifa mayor a cero, actualizar, actualizar inexistente, eliminar, eliminar inexistente | `.\masterbike-vm-atencion\mvnw.cmd -f masterbikes-bicicletas\pom.xml clean test jacoco:report ...` | OK: 10 tests, 0 fallas | Global 40.26%; Service 100.00% | Aprobado |
| `masterbikes-arriendos` | `ArriendoService` | `masterbikes-arriendos/src/test/java/com/masterbikes/arriendos/services/ArriendoServiceTest.java` | 10: listar, buscar por ID, ID inexistente, crear valido, fecha fin anterior, idCliente obligatorio, actualizar, actualizar inexistente, eliminar, eliminar inexistente | `.\masterbike-vm-atencion\mvnw.cmd -f masterbikes-arriendos\pom.xml clean test jacoco:report ...` | OK: 10 tests, 0 fallas | Global 47.33%; Service 100.00% | Aprobado |
| `masterbikes-inventario` | `InventarioService` | `masterbikes-inventario/src/test/java/com/masterbikes/inventario/services/InventarioServiceTest.java` | 11: listar, buscar por ID, ID inexistente, stock bajo marca aumento, stock suficiente desmarca aumento, nombre obligatorio, filtrar por aumento, actualizar, actualizar inexistente, eliminar, eliminar inexistente | `.\masterbike-vm-atencion\mvnw.cmd -f masterbikes-inventario\pom.xml clean test jacoco:report ...` | OK: 11 tests, 0 fallas | Global 47.06%; Service 100.00% | Aprobado |
| `masterbikes-ventas` | `VentaService` | `masterbikes-ventas/src/test/java/com/masterbikes/ventas/services/VentaServiceTest.java` | 10: listar, buscar por ID, ID inexistente, crear valido, idCliente obligatorio, total mayor a cero, actualizar, actualizar inexistente, eliminar, eliminar inexistente | `.\masterbike-vm-atencion\mvnw.cmd -f masterbikes-ventas\pom.xml clean test jacoco:report ...` | OK: 10 tests, 0 fallas | Global 40.26%; Service 100.00% | Aprobado |
| `masterbikes-despachos` | `DespachoService` | `masterbikes-despachos/src/test/java/com/masterbikes/despachos/services/DespachoServiceTest.java` | 10: listar, buscar por ID, ID inexistente, crear valido, idVenta obligatorio, direccion obligatoria, actualizar, actualizar inexistente, eliminar, eliminar inexistente | `.\masterbike-vm-atencion\mvnw.cmd -f masterbikes-despachos\pom.xml clean test jacoco:report ...` | OK: 10 tests, 0 fallas | Global 40.26%; Service 100.00% | Aprobado |
| `masterbikes-pagos` | `PagoService` | `masterbikes-pagos/src/test/java/com/masterbikes/pagos/services/PagoServiceTest.java` | 11: listar, buscar por ID, ID inexistente, crear valido validando venta, venta inexistente, pago duplicado, idVenta obligatorio, actualizar, actualizar inexistente, eliminar, eliminar inexistente | `.\masterbike-vm-atencion\mvnw.cmd -f masterbikes-pagos\pom.xml clean test jacoco:report ...` | OK: 11 tests, 0 fallas | Global 51.74%; Service 91.62% | Aprobado |
| `masterbikes-proveedores` | `ProveedorService` | `masterbikes-proveedores/src/test/java/com/masterbikes/proveedores/services/ProveedorServiceTest.java` | 11: listar, buscar por ID, ID inexistente, buscar por RUT, crear valido, RUT duplicado, RUT obligatorio, actualizar, actualizar inexistente, eliminar, eliminar inexistente | `.\masterbike-vm-atencion\mvnw.cmd -f masterbikes-proveedores\pom.xml clean test jacoco:report ...` | OK: 11 tests, 0 fallas | Global 43.49%; Service 90.07% | Aprobado |

## Errores encontrados

1. La primera ejecucion en sandbox fallo por permisos al escribir en `C:\Users\MA-Alumno\.m2\repository`.
2. La segunda ejecucion fallo por pasar flags `-D` sin comillas a `mvnw.cmd`; Maven los interpreto como fases de ciclo de vida.
3. Se corrigio ejecutando Maven con permisos fuera del sandbox y flags `-D` entre comillas.
4. Mockito mostro advertencias de auto-adjuncion de agente Byte Buddy en JDK 21. No bloquean la ejecucion actual, pero conviene revisarlas si se actualiza JDK o Mockito.

## Interpretacion de cobertura

La cobertura global queda entre 39% y 52% porque JaCoCo cuenta tambien controllers, handlers, application classes y models. La cobertura de la capa `Service`, que era el foco solicitado, queda sobre 90% en todos los microservicios.

Para acercarse al 80% global de la rubrica, el siguiente trabajo deberia agregar pruebas de controllers con `@WebMvcTest`, handlers de excepciones y validaciones de request.

## Proximo paso recomendado

Continuar con pruebas de controllers y handlers si se prioriza cobertura global 80%, o avanzar a Swagger/OpenAPI si el equipo necesita cubrir antes la documentacion viva de APIs.
