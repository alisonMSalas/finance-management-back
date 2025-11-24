# Tutorial paso a paso: Implementación de Swagger/OpenAPI en este proyecto

> Objetivo: dejar documentada la API con Swagger UI y exponer la especificación OpenAPI (JSON/YAML) para exportarla o generar documentación estática.

---

## 1) Alinear dependencias compatibles

1. En `build.gradle`, agregar Springdoc (o actualizar a una versión compatible con Spring Boot 3.3.x):
   ```gradle
   dependencies {
       // ...
       implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0'
       // ...
   }
   ```
2. Justificación de versión: el proyecto usa Spring Boot `3.3.5` (Spring Framework 6.1.x), compatible con `springdoc` 2.6.x. Esto evita el error `NoSuchMethodError` con `ControllerAdviceBean`.
3. Verifica el árbol de dependencias (opcional, diagnóstico):
   ```bash
   ./gradlew dependencyInsight --dependency org.springdoc --configuration runtimeClasspath
   ```

---

## 2) Crear la configuración central de OpenAPI

1. Se añadió la clase `src/main/java/uta/ec/finance_manager/config/SwaggerConfig.java` para definir el bean `OpenAPI` con metadatos y seguridad JWT:
   ```java
   @Configuration
   public class SwaggerConfig {
       @Bean
       public OpenAPI customOpenAPI() {
           final String securitySchemeName = "bearerAuth";

           return new OpenAPI()
               .info(new Info()
                   .title("Finance Management API")
                   .description("API REST para la gestión de finanzas personales. Incluye módulos de cuentas, transacciones, presupuestos, metas de ahorro, inversiones y automatizaciones.")
                   .version("1.0.0")
                   .contact(new Contact()
                       .name("Finance Management Team")
                       .email("finance@uta.edu.ec")
                       .url("https://github.com/melanieAlban/finance-management"))
                   .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html")))
               .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
               .components(new Components()
                   .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                       .name(securitySchemeName)
                       .type(SecurityScheme.Type.HTTP)
                       .scheme("bearer")
                       .bearerFormat("JWT")
                       .description("Ingrese el token JWT (sin 'Bearer' al inicio)")));
       }
   }
   ```
2. Este bean:
   - Publica metadatos (título, descripción, contacto, licencia).
   - Declara el esquema de seguridad `bearerAuth` para JWT y lo aplica como `SecurityRequirement` global.

---

## 3) Ajustar seguridad para permitir Swagger sin autenticación

1. En `src/main/java/uta/ec/finance_manager/config/SecurityConfig.java` se habilitaron las rutas públicas para Swagger/OpenAPI:
   ```java
   .authorizeHttpRequests(auth -> auth
       .requestMatchers("/api/register", "/api/login").permitAll()
       // Swagger / OpenAPI sin autenticación
       .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
       .anyRequest().authenticated()
   )
   ```
2. Esto permite acceder a Swagger UI y a `/v3/api-docs` sin token, manteniendo protegidos el resto de endpoints.

---

## 4) Anotar controladores para enriquecer la documentación

1. Se añadieron anotaciones Swagger/OpenAPI en controladores clave:
   - `@Tag` para agrupar endpoints.
   - `@Operation` y `@ApiResponses` para resumir/ejemplificar cada operación.
   - `@SecurityRequirement(name = "bearerAuth")` en controladores que exigen JWT (por ejemplo, `AccountController`).

   Ejemplo real de `AccountController`:
   ```java
   @RestController
   @RequestMapping("/account")
   @RequiredArgsConstructor
   @Tag(name = "Cuentas", description = "API para la gestión de cuentas bancarias")
   @SecurityRequirement(name = "bearerAuth")
   public class AccountController {
       @PostMapping
       @Operation(summary = "Crear cuenta", description = "Crea una nueva cuenta bancaria para el usuario autenticado")
       @ApiResponses({
           @ApiResponse(responseCode = "200", description = "Cuenta creada exitosamente"),
           @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
           @ApiResponse(responseCode = "401", description = "No autorizado")
       })
       public AccountDto createAccount(@Valid @RequestBody AccountDto accountDto){
           return this.accountService.save(accountDto);
       }
       // ... resto de endpoints documentados
   }
   ```
2. `AuthController` también tiene `@Tag`, `@Operation` y `@ApiResponses` en `register` y `login`.

---

## 5) Construir y ejecutar la aplicación

1. Limpieza y build:
   ```bash
   ./gradlew clean build
   ```
2. Ejecutar:
   ```bash
   ./gradlew bootRun
   ```
3. Verificar URLs de documentación:
   - Swagger UI: `http://localhost:8080/swagger-ui/index.html`
   - OpenAPI (JSON): `http://localhost:8080/v3/api-docs`
   - OpenAPI (YAML): `http://localhost:8080/v3/api-docs.yaml`

---

## 6) Exportar la especificación y generar documentación estática

1. Exportar a archivos (para versionar o publicar):
   ```bash
   curl -s http://localhost:8080/v3/api-docs -o openapi.json
   curl -s http://localhost:8080/v3/api-docs.yaml -o openapi.yaml
   ```
2. Generar HTML con Redoc CLI (opcional):
   ```bash
   npm i -g redoc-cli
   redoc-cli bundle openapi.json -o api-docs.html
   # o
   redoc-cli bundle openapi.yaml -o api-docs.html
   ```
3. Publica `api-docs.html` en cualquier hosting estático o adjúntalo en tu wiki.

---

## 7) Documento de referencia dentro del repo

- Se añadió `README_APISWAGGER.md` (en la raíz) con:
  - Resumen de endpoints.
  - Instrucciones de autenticación JWT.
  - URLs de Swagger/OpenAPI.
  - Pasos para exportar OpenAPI y generar HTML con Redoc.
  - Nota de compatibilidad de versiones.

---

## 8) Compatibilidad de versiones y solución al error conocido

1. Matriz usada:
   - Spring Boot `3.3.x` (Framework 6.1.x) ↔ Springdoc `2.6.x`.
2. Si aparece el error:
   ```
   java.lang.NoSuchMethodError: 'void org.springframework.web.method.ControllerAdviceBean.<init>(java.lang.Object)'
   ```
   Acciones aplicadas en este proyecto:
   - Actualizar Springdoc a `2.6.0`.
   - Alinear Spring Boot a `3.3.5`.
   - Limpiar y reconstruir: `./gradlew clean build`.
   - Verificar que no haya otras versiones de `springdoc` en el classpath (con `dependencyInsight`).

---

## 9) Buenas prácticas para mantener la documentación

- Añade/actualiza `@Operation`, `@ApiResponses`, `@Tag`, `@SecurityRequirement` cuando cambies endpoints.
- Mantén DTOs claros y con validaciones; puedes usar descripciones y ejemplos para mayor claridad en Swagger UI.
- Considera automatizar la exportación en CI/CD (p.ej. tarea que haga curl de `/v3/api-docs` y publique `api-docs.html`).

---

## 10) Problemas observados y pendientes menores

- En `SavingGoalController`, el método `getById(@PathVariable Integer id)` está mapeado como `@GetMapping()` sin `/{id}`; por tanto, hoy no existe `GET /saving/{id}`. Si te interesa exponerlo y documentarlo en Swagger, debo ajustar el mapping a `@GetMapping("/{id}")`.

---

## Resultado

Con estos pasos, la API queda:
- Documentada automáticamente con Swagger UI.
- Exponiendo OpenAPI en JSON/YAML para exportación.
- Integrada con seguridad JWT mediante `bearerAuth` y botón "Authorize" en la UI.
