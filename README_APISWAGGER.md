# Finance Management API – Documentación y Guía rápida

Este documento resume los endpoints de la API, cómo autenticarse, cómo visualizar la documentación con Swagger UI y cómo generar/exportar la especificación OpenAPI (JSON/YAML) para compartir o publicar documentación (HTML/PDF) automáticamente.


## URLs de documentación (Swagger/OpenAPI)
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- OpenAPI YAML: `http://localhost:8080/v3/api-docs.yaml`

Notas:
- El proyecto por defecto arranca en el puerto 8080. Si cambias el puerto, sustituye en las URLs.
- En `SecurityConfig` están permitidos sin autenticación: `/swagger-ui/**`, `/v3/api-docs/**` y `/swagger-ui.html`.


## Autenticación
- Esquema: JWT `bearerAuth`
- Header: `Authorization: Bearer <TOKEN>`
- Endpoints públicos (no requieren token):
  - `POST /api/register`
  - `POST /api/login`
  - Swagger/OpenAPI (ver arriba)

Ejemplo de login (curl):
```bash
curl -X POST "http://localhost:8080/api/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"user@example.com","password":"secret"}'
```

Ejemplo de llamada autenticada:
```bash
curl -X GET "http://localhost:8080/account" \
  -H "Authorization: Bearer <TOKEN_JWT>"
```


## Endpoints por módulo

### Autenticación (`/api`)
- `POST /api/register` – Registrar usuario nuevo (Body: `SaveUserDto`). Respuestas: `200`, `400`, `409`. Público.
- `POST /api/login` – Autenticar y devolver token JWT (Body: `AuthenticationRequest`). Respuestas: `200`, `400`, `401`. Público.

### Cuentas (`/account`) – requiere JWT
- `POST /account` – Crear cuenta (Body: `AccountDto`). Respuestas: `200`, `400`, `401`.
- `GET /account` – Listar cuentas del usuario. Respuestas: `200`, `401`.
- `PUT /account` – Actualizar cuenta (Body: `AccountDto`). Respuestas: `200`, `400`, `401`, `404`.
- `DELETE /account?accountId={id}` – Eliminar cuenta. Respuestas: `200`, `401`, `404`.
- `GET /account/total-balance` – Balance total del usuario. Respuestas: `200`, `401`.
- `GET /account/name/{name}` – Buscar cuentas por nombre. Respuestas: `200`, `401`.

### Transacciones (`/transaction`) – requiere JWT
- `POST /transaction` – Crear transacción (Body: `TransactionDto`). Respuestas: `200`, `400`, `401`.
- `PUT /transaction?transactionId={id}` – Editar transacción (Body: `TransactionDto`). Respuestas: `200`, `400`, `401`, `404`.
- `GET /transaction` – Listar transacciones del usuario. Respuestas: `200`, `401`.

### Presupuestos (`/budget`) – requiere JWT
- `POST /budget` – Crear presupuesto (Body: `BudgetDto`). Respuestas: `200`, `400`, `401`.
- `GET /budget` – Listar presupuestos. Respuestas: `200`, `401`.
- `PUT /budget` – Actualizar presupuesto (Body: `BudgetDto`). Respuestas: `200`, `400`, `401`, `404`.
- `DELETE /budget?budgetId={id}` – Eliminar presupuesto. Respuestas: `200`, `401`, `404`.

### Inversiones (`/investment`) – requiere JWT
- `POST /investment` – Crear inversión (Body: `InvestmentDto`). Respuestas: `200`, `400`, `401`.
- `GET /investment` – Listar inversiones. Respuestas: `200`, `401`.
- `PUT /investment` – Actualizar inversión (Body: `InvestmentDto`). Respuestas: `200`, `400`, `401`, `404`.
- `DELETE /investment/{id}` – Eliminar inversión por ID. Respuestas: `200`, `401`, `404`.

### Metas de ahorro (`/saving`) – requiere JWT
- `POST /saving` – Crear meta de ahorro (Body: `SavingGoalDto`). Respuestas: `200`, `400`, `401`.
- `PUT /saving` – Actualizar meta de ahorro (Body: `SavingGoalDto`). Respuestas: `200`, `400`, `401`, `404`.
- `DELETE /saving?id={id}` – Eliminar meta de ahorro. Respuestas: `200`, `401`, `404`.
- `GET /saving/user` – Listar metas de ahorro del usuario. Respuestas: `200`, `401`.
- `POST /saving/manual-streak` – Actualizar rachas manualmente. Respuestas: `200`, `401`.

Nota: En el código actual `getById(@PathVariable id)` no tiene mapping `/{id}` en `@GetMapping`. Si quieres exponer `GET /saving/{id}`, hay que ajustar el mapping en `SavingGoalController`.

### Reportes (`/report`) – requiere JWT
- `GET /report` – Resumen (cuentas, ingresos, gastos, presupuesto). Respuestas: `200`, `401`.


## Tutorial: cómo generar la documentación de la API
A continuación, varias formas prácticas para generar, exportar y compartir la documentación usando la especificación OpenAPI que expone Springdoc.

### Opción 1: Visualizar con Swagger UI (recomendada en desarrollo)
1. Arranca la aplicación (`./gradlew bootRun` o desde tu IDE).
2. Abre `http://localhost:8080/swagger-ui/index.html`.
3. Usa el botón "Authorize" para introducir tu token JWT (`Bearer <token>`).

### Opción 2: Exportar OpenAPI (JSON/YAML) con curl
Puedes guardar la especificación a archivos para versionarla o publicarla:
```bash
# JSON
curl -s http://localhost:8080/v3/api-docs -o openapi.json

# YAML
curl -s http://localhost:8080/v3/api-docs.yaml -o openapi.yaml
```

### Opción 3: Generar documentación estática (HTML/PDF) con Redoc CLI
Si tienes Node.js, puedes usar `redoc-cli` para generar un HTML estático profesional:
```bash
# Instalar de forma local en tu máquina (una vez)
npm i -g redoc-cli

# Generar HTML desde JSON o YAML
redoc-cli bundle openapi.json -o api-docs.html
# o
redoc-cli bundle openapi.yaml -o api-docs.html
```
El archivo `api-docs.html` resultante puedes publicarlo en cualquier hosting estático o adjuntarlo en un wiki.

Para PDF, Redocly ofrece opciones comerciales; como alternativa gratuita, abre `api-docs.html` en el navegador e imprime a PDF.

### Opción 4 (opcional): Generar OpenAPI en build con Gradle
Si prefieres automatizar la exportación durante el build, puedes usar el plugin OpenAPI de Springdoc. Añade al `build.gradle` (opcional, no está incluido por defecto en este proyecto):
```gradle
plugins {
    id "org.springdoc.openapi-gradle-plugin" version "1.9.0"
}

openApi {
    apiDocsUrl.set("http://localhost:8080/v3/api-docs")
    outputDir.set(file("build/openapi"))
    outputFileName.set("openapi.json")
}
```
Luego:
```bash
# Arranca la app en segundo plano (para que /v3/api-docs esté disponible)
./gradlew bootRun &
# En otra consola, ejecuta la tarea del plugin para exportar
./gradlew openApiGenerate
```
Esto generará `build/openapi/openapi.json`. Puedes encadenarlo con un paso de Redoc CLI para producir `api-docs.html` en CI/CD.

### Consejos de mantenimiento
- Mantén las anotaciones Swagger en controladores (`@Operation`, `@ApiResponses`, `@Tag`, etc.).
- Documenta modelos DTO con anotaciones de validación y descripciones si es necesario.
- Añade ejemplos (`@ExampleObject`) para mejorar la claridad en Swagger UI.
- Verifica la compatibilidad entre versiones de Spring Boot/Spring Framework y Springdoc.


## Compatibilidad de versiones (importante)
- Spring Boot 3.3.x (Spring Framework 6.1.x) funciona correctamente con Springdoc 2.6.x.
- Si usas Spring Boot 3.4.x (Framework 6.2.x), asegúrate de usar una versión de Springdoc compatible.
- Este proyecto actualmente usa:
  - Spring Boot: 3.3.5
  - Springdoc: 2.6.0 (`springdoc-openapi-starter-webmvc-ui`)


## Enlaces útiles
- Springdoc OpenAPI: https://springdoc.org/
- Redoc CLI: https://github.com/Redocly/redoc/blob/main/cli/README.md
- Especificación OpenAPI: https://www.openapis.org/