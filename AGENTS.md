# AGENTS.md — SGC Backend

## Stack
- **Spring Boot 4.0.6 / Java 25 / Maven** (wrapper: `mvnw.cmd` on Windows, `mvnw` on Unix)
- **PostgreSQL** via Supabase (prod), **H2** (test, autoconfigured)
- **JWT auth** via Spring Security OAuth2 Resource Server (cookies for access/refresh tokens)
- **Resend** for email, **Springdoc OpenAPI** at `/swagger-ui.html`
- Deployed as **Docker** on **Render** (see `render.yaml`)

## Quick commands
```bash
./mvnw.cmd clean compile          # build only
./mvnw.cmd clean test -Dtest=SgcApplicationTests  # single test class
./mvnw.cmd clean package -DskipTests -B           # prod jar
./mvnw.cmd clean test                              # all tests
```

## Architecture (Hexagonal)
```
src/main/java/com/condominios/sgc/
├── application/       use cases (interface + impl), services, request DTOs
├── domain/            models, port interfaces, exceptions, enums, utilities
├── infrastructure/    adapters, JPA entities/mappers/repositories, security, clients
└── web/               controllers, response DTOs, global exception handler
```

- Domain models are **not** JPA entities — mapping via `*Mapper.java` in `infrastructure/persistence/mapper/`
- Ports (`domain/port/`) → Adapters (`infrastructure/adapter/`) bridge domain to persistence
- Controllers are thin — all logic in `application/` layer

## Key conventions
- `.env` file at project root loaded via `spring.config.import=optional:file:.env[.properties]` (not committed, see `.gitignore`)
- `spring.jpa.hibernate.ddl-auto=validate` — schema must exist externally (no Flyway/Liquibase)
- `spring.jpa.open-in-view=false` (explicitly disabled — no lazy loading in views)
- Lombok annotation processing configured via `maven-compiler-plugin` annotationProcessorPaths
- `cors.allowed-origins=*` with pattern-based matching (setAllowOriginPatterns)
- Public endpoints: `/api/auth/login`, `/api/auth/refresh`, `/api/auth/forgot-password`, `/api/auth/reset-password`, `/api/auth/verificar-email`, `/api/health`, `/swagger-ui/**`, `/v3/api-docs/**` — all others require auth via cookie-based JWT
- Auth uses `@PreAuthorize` with roles: `SUPER_ADMINISTRADOR`, `ADMINISTRADOR_CONDOMINIO`, and `isAuthenticated()`

## Controllers (all under `/api`)
- `AuthController` (`/api/auth`) — login, register, logout, refresh, forgot/reset/change-password, verify email, update email
- `HealthController` (`/api/health`)
- Per-entity CRUD: `Condominio`, `Torre`, `Piso`, `Apartamento`, `Inquilino`, `Usuario`, `Vehiculo`, `Estacionamiento`, `Carrito`, `LogAccesoVehicular`, `LogPrestamoCarrito`, `Configuracion`

## Testing
- Only one test exists: `SgcApplicationTests.contextLoads()`
- No `src/test/resources/` directory — tests rely on H2 auto-configuration
- Auth-based tests need cookie setup (JWT tokens via `CookieBearerTokenResolver`)
