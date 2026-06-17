# SGC Backend — Agent Instructions

## Stack
- Java 25 + Spring Boot 4.1.0 + Maven wrapper (`./mvnw`)
- PostgreSQL (Supabase), H2 available
- Lombok, Thymeleaf, springdoc-openapi, Resend (email), jjwt 0.12.6

## ⚠️ Current branch state (`refactor-core`)
- **`SgcApplication.java` is deleted in working tree** — restore before compiling (`git restore src/main/java/com/condominios/sgc/SgcApplication.java`)
- `domain/`, `infrastructure/`, `web/` dirs are untracked (new hexagonal structure)
- `application/` exists but subdirs (`dto/`, `usecase/`, `facade/`, `impl/`) are **empty** — no controllers or use cases exist yet
- Only domain models/ports and infrastructure adapters are implemented

## Build & Run
```
./mvnw clean package -DskipTests -B   # build
./mvnw spring-boot:run                 # run dev (needs .env)
./mvnw test                            # run tests
```
Main class: `com.condominios.sgc.SgcApplication`

## Architecture (Hexagonal)
```
domain/          — models, port interfaces, filters, enums, exceptions, ValidacionUtil
application/     — use case inputs (empty), DTOs (empty), facades (empty)
infrastructure/  — persistence (JPA entities/repos/mappers/specs), security, HTTP clients, util
web/             — controllers (none yet), GlobalExceptionHandler, OpenApiConfig, response DTOs
```
- Ports go in `domain/port/`, entities in `infrastructure/persistence/entity/`, repos in `.../repository/`, mappers in `.../mapper/`, specs in `.../specification/`
- New controllers → `web/controller/`, use cases → `application/usecase/`, DTOs → `application/dto/{command,query,result}/`

## Security
- **Two filter chains** (by `@Order`):
  1. `@Order(1)` — public: `/swagger-ui/**`, `/v3/api-docs/**`, `/docs/**`, `/api-docs/**` — `permitAll`
  2. `@Order(2)` — private: `/api/**` — OAuth2 Resource Server with JWT, `authenticated()`
- `CookieJwtFilter` bridges cookie (`access_token`) → `Authorization: Bearer` header (inserted before `BearerTokenAuthenticationFilter`)
- `JwtAuthConverter`: extracts `rol` claim → `ROLE_<rol>` authority, principal = `correo`
- `@EnableMethodSecurity` — use `@PreAuthorize` on controller methods
- BCrypt via `PasswordEncoder` bean (`SecurityConfig.java:27`)
- CORS: origins from `cors.allowed-origins` env var, `allowCredentials(true)`, methods GET/POST/PUT/PATCH/DELETE/OPTIONS
- Cookies: httpOnly, `SameSite=None`, secure (configurable via `cookie.*` properties)

## Domain patterns
- **Rich models**: constructors validate via `ValidacionUtil` static methods (`requerido`, `noNulo`, `positivo`, `correoValido`, `distinto`), throw `DominioException` subclasses
- **Exception hierarchy**: `DominioException` (abstract, carries `httpStatus`) → concrete subclasses with private constructors + public static factory methods (e.g. `UsuarioException.noEncontrado()`, `CorreoException.errorEnvio(...)`)
- **Pagination**: custom `Pagina<T>` record + `Paginable` record (not Spring Page/Pageable)
- **Roles** (`Rol` enum): `SUPER_ADMINISTRADOR`, `ADMINISTRADOR_CONDOMINIO`, `PROPIETARIO`, `AGENTE_SEGURIDAD` — with `puedeAsignarA()` logic
- **Token types** (`TipoToken`): `ACCESS`, `REFRESH`, `VERIFICACION`, `REESTABLECIMIENTO`

## Code conventions
- **Lombok** only on JPA entities: `@Getter` + `@Setter` + `@NoArgsConstructor` + `@AllArgsConstructor`
- **Domain models**: manual constructors + manual getters (no Lombok)
- **Mappers**: manual `aModelo()` / `aEntidad()` in `@Component` classes (no MapStruct)
- **Validation**: inside domain model constructors, not in setters or services
- **Null/blank foreign keys**: use entity reference stubs in mappers (see `UsuarioMapper.aEntidad()` sets `CondominioEntity` with just ID)

## Database
- PostgreSQL via Supabase (Production), H2 also on classpath (runtime scope)
- `spring.jpa.hibernate.ddl-auto=update` — schema auto-managed
- `spring.jpa.open-in-view=false` — no OSIV; use `@Transactional` for lazy loading
- `spring.jpa.show-sql=true` + `hibernate.format_sql=true` — SQL logging on by default
- HikariCP with `prepareThreshold=0`, `SELECT 1` validation query

## Config
- `.env` file at project root loaded via `spring.config.import=optional:file:.env[.properties]`
- All `${...}` placeholders require `SUPABASE_DB_*`, `JWT_SECRET`, `RESEND_API_KEY`, `CORS_ALLOWED_ORIGINS`, `APP_BASE_URL`, `PORT`, `COOKIE_*` vars

## API Docs
- Swagger UI: `/docs` · OpenAPI JSON: `/api-docs`
- Custom info configured in `OpenApiConfig` (`web/documentation/`)

## Email
- Resend API via `ResendClient` (`infrastructure/client/`) — posts to `https://api.resend.com/emails`
- Email templates: `src/main/resources/templates/email/{verificacion,bienvenida,reseteo-contrasena}.html`
- Uses `RestTemplate` directly (not WebClient)

## Docker
- Multi-stage: `eclipse-temurin:25-jdk-alpine` (build) → `eclipse-temurin:25-jre-alpine` (run)
- `ENTRYPOINT ["java", "-jar", "app.jar"]`, exposes port 8080
- Build artifact: `target/sgc-0.0.1-SNAPSHOT.jar`

## Testing
- Single `@SpringBootTest` context-load test — no real coverage yet
