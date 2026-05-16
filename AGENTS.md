# sgc — Sistema Gestión Condominios

## Stack
Spring Boot 4.0.6 / Java 25 / Maven 3.9.x (wrapper: `mvnw.cmd` on Windows)
PostgreSQL (runtime), H2 (dev/test)
Spring Security + OAuth2 RS deps present but **not yet configured**

## Commands
```
mvnw.cmd test                    # run tests
mvnw.cmd spring-boot:run         # start dev server (H2 in-memory)
mvnw.cmd clean package           # build JAR
```

## Architecture (Hexagonal / Ports & Adapter)
```
web.controller       → @RestController, @RestControllerAdvice
application.usecase  → interface + impl (mediates controller ↔ domain)
domain.model         → pure POJOs (no framework annotations)
domain.port          → repository interfaces
infrastructure.adapter  → implements domain.port
infrastructure.persistence.entity → JPA @Entity (lombok @Getter @Setter @NoArgsConstructor)
infrastructure.persistence.mapper → static toEntity() / toModel()
infrastructure.config → @Configuration with explicit @Bean wiring
```

## Conventions (agent-critical)
1. **No `@Service`, `@Component`, `@Repository`** — all beans wired manually in `@Configuration` classes
2. **Domain models are NEVER JPA entities** — separate `*Model` (domain) and `*Entity` (JPA) classes
3. **Entity↔Model mapping** via static `*Mapper.toEntity()` / `*Mapper.toModel()` (no MapStruct)
4. **Controllers depend on use case interfaces**, never on repositories directly
5. **Exception hierarchy**: `DominioException` base class → `TipoError` enum (NOT_FOUND / BAD_REQUEST) → static factory methods per exception class
6. **DTOs are Java `record`s** in two packages: `application.dto` → request objects, `web.dto` → response objects
7. **`spring.jpa.open-in-view=false`** — lazy loading must be handled explicitly
8. **`spring.jpa.hibernate.ddl-auto=update`** — schema auto-managed
9. **Lombok only on `@Entity` classes**, never on domain models
10. **Java 25** — verify IDE/toolchain compatibility

## Domain completeness
Only `Configuracion` is fully wired (controller → use case → adapter → repository).
Other domains (Condominio, Torre, Piso, Apartamento, Usuario, Vehiculo, Estacionamiento, Carrito, Inquilino, Log*) have `*Model`, `*Entity`, `*Repository`, `*Mapper`, `*Specifications`, `*Exception` defined but lack controllers, use cases, and adapters.
