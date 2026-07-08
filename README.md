# SGC — Sistema de Gestión de Condominios

Backend REST API para la administración integral de condominios, edificios y
conjuntos residenciales. Implementa 4 roles con operaciones específicas:
Super Administrador, Administrador de Condominio, Propietario y Agente de
Seguridad.

**URL de despliegue:** <https://sgc-backend-vfvl.onrender.com>

---

## Stack tecnológico

| Capa            | Tecnología                                              |
|-----------------|---------------------------------------------------------|
| Lenguaje        | Java 25                                                 |
| Framework       | Spring Boot 4.1.0 (Spring Framework 7)                  |
| Base de datos   | PostgreSQL 17 (Supabase con pooler)                     |
| ORM             | Hibernate 7 (JPA 3)                                     |
| Autenticación   | OAuth2 Resource Server + JWT HMAC-SHA256 en cookies     |
| Documentación   | SpringDoc OpenAPI (Swagger UI)                          |
| Construcción    | Maven Wrapper (`mvnw`)                                  |
| Contenerización | Docker (multi-stage, imagen `eclipse-temurin:25`)       |
| Correo          | Resend API                                              |
| Lombok          | Habilitado (annotation processor)                       |

**Dependencias principales:** JJWT 0.12.6, SpringDoc 2.8.5, BCrypt, Thymeleaf
(plantillas de correo), H2 (tests).

---

## Arquitectura (Hexagonal / Ports & Adapters)

```
com.condominios.sgc/
├── domain/                        ← Entidad de negocio pura
│   ├── model/                        Modelos de dominio
│   ├── service/                      Servicios de dominio
│   ├── shared/
│   │   ├── exception/                Excepciones de dominio
│   │   └── valueobject/              Value objects (Correo, PlacaVehiculo, etc.)
│   ├── type/                         Enums (Rol, TipoVehiculo, EstadoCarrito, etc.)
│   └── util/                         Validación utilitaria
├── application/                   ← Casos de uso (orquestación)
│   ├── dto/
│   │   ├── command/                   Comandos de entrada
│   │   ├── query/                     Queries de paginación
│   │   └── result/                    Resultados de salida
│   ├── port/
│   │   ├── in/                        Puertos de entrada (interfaces de use case)
│   │   └── out/                       Puertos de salida (repositorios + servicios)
│   └── service/                       Implementaciones de casos de uso
└── infrastructure/               ← Adaptadores (framework)
    ├── adapter/
    │   ├── in/web/                    Adaptadores primarios (REST)
    │   │   ├── controller/            Controladores Spring MVC
    │   │   ├── dto/request/           DTOs de petición
    │   │   ├── dto/response/          DTOs de respuesta
│   │               ├── handler/               GlobalExceptionHandler (400/401/403/409/500)
    │   │   └── mapper/                Mappers result → response
    │   └── out/persistence/           Adaptadores secundarios (JPA)
    │       ├── entity/                Entidades JPA
    │       ├── mapper/                Mappers entity → domain
    │       └── repository/            Interfaces JPA Repository
    ├── config/                        Beans de infraestructura (CORS, JwtDecoder, servicios)
    ├── security/                      SecurityConfig, CookieBearerTokenResolver
    ├── service/                       Implementaciones de servicios de aplicación
    └── util/                          JwtUtil, CookieUtils, SecurityUtils
```

**Regla fundamental:** `domain/` y `application/` **nunca importan** de
`infrastructure/`. Las dependencias apuntan hacia adentro (Dependency
Inversion).

---

## Modelo de datos (entidades JPA)

16 entidades mapean el dominio:

| Entidad               | Tabla                  | Propósito                                  |
|-----------------------|------------------------|--------------------------------------------|
| `CondominioEntity`    | `condominio`           | Condominio / conjunto residencial           |
| `TorreEntity`         | `torre`                | Torre o edificio dentro del condominio      |
| `PisoEntity`          | `piso`                 | Piso dentro de una torre                    |
| `ApartamentoEntity`   | `apartamento`          | Unidad habitacional                         |
| `UsuarioEntity`       | `usuario`              | Usuario del sistema (cualquier rol)         |
| `InquilinoEntity`     | `inquilino`            | Inquilino de un apartamento                 |
| `VehiculoEntity`      | `vehiculo`             | Vehículo registrado                         |
| `EstacionamientoEntity` | `estacionamiento`     | Plaza de estacionamiento                    |
| `CarritoEntity`       | `carrito`              | Carrito de supermercado (activo)            |
| `LogAccesoVehicularEntity` | `log_acceso_vehicular` | Registro de entrada/salida de vehículos   |
| `LogPrestamoCarritoEntity` | `log_prestamo_carrito` | Registro de préstamo/devolución de carritos |
| `TokenEntity`         | `token`                | Tokens JWT refresh                          |
| `PaisEntity`          | `pais`                 | País                                        |
| `CiudadEntity`        | `ciudad`               | Ciudad                                      |
| `MonedaEntity`        | `moneda`               | Moneda                                      |
| `ConfiguracionEntity` | `configuracion`        | Configuración del condominio (límites)       |

`ddl-auto=update` crea y actualiza las tablas automáticamente.

---

## Configuración de la Base de Datos

En la raíz del proyecto encontrarás la carpeta `db`, la cual contiene los scripts SQL (seeds) necesarios para poblar la base de datos con los datos iniciales o de prueba.

Dependiendo de tu entorno, puedes ejecutar estos scripts de las siguientes maneras:

### Opción 1: En la nube (Supabase)
Si estás utilizando el entorno en la nube de Supabase:
1. Abre tu proyecto en el panel de control de [Supabase](https://supabase.com/).
2. Ve a la sección **SQL Editor** en el menú lateral.
3. Haz clic en **+ New query**.
4. Copia el contenido de los archivos SQL que se encuentran en la carpeta `db`.
5. Pega el código en el editor y presiona **Run** para ejecutarlo.

### Opción 2: En local (pgAdmin)
Si tienes la base de datos PostgreSQL corriendo localmente y utilizas pgAdmin:
1. Abre **pgAdmin** y conéctate a tu servidor local.
2. Despliega tu servidor, ve a **Databases** y selecciona la base de datos del proyecto.
3. Haz clic en el botón de **Query Tool** (icono de base de datos con un rayo o lupa) en la barra de herramientas superior.
4. Tienes dos formas de ejecutar el script:
   - **Copiar y pegar:** Abre el archivo SQL de la carpeta `db` en cualquier editor de texto, copia el contenido, pégalo en el Query Tool y presiona **Execute/Refresh** (F5).
   - **Abrir archivo:** En el Query Tool, haz clic en el icono de "Open File" (carpeta), busca el archivo SQL dentro de tu carpeta `db`, ábrelo y presiona **Execute/Refresh** (F5).

---

## Roles y permisos

| Rol                        | Descripción                                      |
|----------------------------|--------------------------------------------------|
| `SUPER_ADMINISTRADOR`      | Gestión global: admins, condominios, usuarios    |
| `ADMINISTRADOR_CONDOMINIO` | Administración de un condominio específico       |
| `PROPIETARIO`              | Dueño de un apartamento                          |
| `AGENTE_SEGURIDAD`         | Control de acceso, estacionamientos y préstamos  |

Cada endpoint está protegido con `@PreAuthorize`. El token JWT transporta el
rol en el claim `rol`.

---

## Endpoints

### Públicos (sin autenticación)

| Método | Ruta                               | Descripción                       |
|--------|------------------------------------|-----------------------------------|
| GET    | `/`                                | Mensaje de bienvenida             |
| GET    | `/api/health`                       | Health check                      |
| POST   | `/api/auth/login`                  | Iniciar sesión (devuelve cookies) |
| POST   | `/api/auth/refresh`                | Refrescar token de acceso         |
| POST   | `/api/auth/forgot-password`        | Solicitar restablecimiento        |
| POST   | `/api/auth/reset-password`         | Restablecer contraseña            |
| GET    | `/api/auth/verify-email?token=`    | Verificar correo electrónico      |
| GET    | `/docs/swagger-ui.html`            | Swagger UI                        |
| GET    | `/docs/api-docs`                   | OpenAPI spec                      |

### Perfil (cualquier rol autenticado)

| Método | Ruta                     | Descripción                   |
|--------|--------------------------|-------------------------------|
| GET    | `/api/auth/me`           | Usuario autenticado actual    |
| POST   | `/api/auth/logout`       | Cerrar sesión                 |
| PUT    | `/api/auth/change-password` | Cambiar contraseña         |
| PUT    | `/api/auth/email`        | Actualizar correo             |
| GET    | `/api/profile`           | Obtener perfil                |
| PUT    | `/api/profile`           | Actualizar perfil             |

### Super Administrador

| Método | Ruta                                                       | Descripción                          |
|--------|------------------------------------------------------------|--------------------------------------|
| GET    | `/api/super-admin/dashboard/metrics`                       | Métricas del dashboard               |
| GET    | `/api/super-admin/dashboard/recent-admins`                 | Administradores recientes            |
| GET    | `/api/super-admin/dashboard/recent-condos`                 | Condominios recientes                |
| GET    | `/api/super-admin/administrators`                          | Listar administradores               |
| POST   | `/api/super-admin/administrators`                          | Crear administrador                  |
| PUT    | `/api/super-admin/administrators/{id}`                     | Actualizar administrador             |
| PATCH  | `/api/super-admin/administrators/{id}`                     | Activar/desactivar administrador     |
| DELETE | `/api/super-admin/administrators/{id}`                     | Eliminar administrador               |
| PUT    | `/api/super-admin/administrators/{id}/assign-condo`        | Asignar/reasignar/desasignar condominio a un `ADMINISTRADOR_CONDOMINIO`. Falla si el condominio ya tiene un admin asignado (validación 1-por-condominio). Para asignar condominio a `PROPIETARIO`/`AGENTE_SEGURIDAD`, usa `POST /api/admin/users?condominioId=X` |
| GET    | `/api/super-admin/administrators/available`                | Admins sin condominio asignado       |
| GET    | `/api/super-admin/condominiums`                            | Listar condominios                   |
| POST   | `/api/super-admin/condominiums`                            | Crear condominio                     |
| PUT    | `/api/super-admin/condominiums/{id}`                       | Actualizar condominio                |
| PATCH  | `/api/super-admin/condominiums/{id}`                       | Activar/desactivar condominio        |
| DELETE | `/api/super-admin/condominiums/{id}`                       | Eliminar condominio                  |
| GET    | `/api/super-admin/condominiums/unassigned`                 | Condominios sin admin                |
| GET    | `/api/super-admin/users`                                   | Listar todos los usuarios            |
| PATCH  | `/api/super-admin/users/{id}/status`                       | Activar/desactivar cualquier usuario |
| POST   | `/api/super-admin/users/{id}/invalidate-session`           | Invalidar sesión de usuario          |
| PUT    | `/api/super-admin/users/{id}/force-password`               | Forzar cambio de contraseña          |
| GET    | `/api/catalogs/countries`                                  | Listar países                        |
| GET    | `/api/catalogs/countries/{countryId}/cities`               | Listar ciudades por país             |

### Administrador de Condominio

> **Nota:** El `SUPER_ADMINISTRADOR` también puede usar estos endpoints agregando `?condominioId=X`. Es la forma recomendada para crear `PROPIETARIO`/`AGENTE_SEGURIDAD` directamente, sin pasar por `assign-condo`.

| Método | Ruta                                           | Descripción                           |
|--------|------------------------------------------------|---------------------------------------|
| GET    | `/api/admin/dashboard/metrics`                 | Métricas del dashboard del condominio |
| GET    | `/api/admin/condominium/my-info`               | Información del propio condominio (con configuración anidada) |
| PUT    | `/api/admin/condominium/my-info`               | Actualizar nombre y dirección          |
| GET    | `/api/admin/condominium/configuracion`         | Ver configuración (límites y penalización) |
| PUT    | `/api/admin/condominium/configuracion`         | Editar configuración                   |
| GET    | `/api/admin/structure`                         | Estructura (torres, pisos, aptos)     |
| POST   | `/api/admin/structure/nodes`                   | Crear nodo (torre/piso/apto)          |
| DELETE | `/api/admin/structure/nodes/{id}`              | Eliminar nodo                         |
| GET    | `/api/admin/users`                             | Usuarios del condominio               |
| POST   | `/api/admin/users`                             | Crear usuario del condominio          |
| PUT    | `/api/admin/users/{id}`                        | Actualizar usuario                    |
| PATCH  | `/api/admin/users/{id}/status`                 | Activar/desactivar usuario             |
| GET    | `/api/admin/apartments`                        | Listar apartamentos                   |
| PUT    | `/api/admin/apartments/{id}/assign-owner`      | Asignar propietario a apartamento     |
| PUT    | `/api/admin/apartments/{id}/assign-apartment`  | Asignar estacionamiento/carrito a apartamento |
| PUT    | `/api/admin/apartments/{id}/occupants`         | Actualizar ocupantes                  |
| GET    | `/api/admin/assets`                            | Listar activos (carritos/estacionamientos) |
| POST   | `/api/admin/assets`                            | Crear activo                          |
| PUT    | `/api/admin/assets/{id}/status`                | Actualizar estado de activo           |
| GET    | `/api/admin/logs`                              | Logs de actividad (filtros)           |

### Propietario

| Método | Ruta                                   | Descripción                      |
|--------|----------------------------------------|----------------------------------|
| GET    | `/api/homeowner/dashboard/summary`     | Resumen del dashboard            |
| GET    | `/api/homeowner/apartment/details`     | Detalle del apartamento propio   |
| GET    | `/api/homeowner/tenants`               | Listar inquilinos                |
| POST   | `/api/homeowner/tenants`               | Registrar inquilino              |
| DELETE | `/api/homeowner/tenants/{id}`          | Eliminar inquilino               |
| GET    | `/api/homeowner/vehicles`              | Listar vehículos                 |
| POST   | `/api/homeowner/vehicles`              | Registrar vehículo               |
| DELETE | `/api/homeowner/vehicles/{id}`         | Eliminar vehículo                |
| GET    | `/api/homeowner/logs`                  | Logs de actividad (propietario)  |

### Agente de Seguridad

| Método | Ruta                                      | Descripción                          |
|--------|-------------------------------------------|--------------------------------------|
| GET    | `/api/security/dashboard/status`          | Estado operativo del condominio      |
| GET    | `/api/security/parking-slots`             | Estado de estacionamientos           |
| GET    | `/api/security/vehicles/verify/{plate}`   | Verificar vehículo por placa         |
| GET    | `/api/security/asset-loans/active-carts`  | Préstamos de carritos activos        |
| POST   | `/api/security/asset-loans`               | Registrar préstamo de carrito        |
| PUT    | `/api/security/asset-loans/{id}/return`   | Registrar devolución de carrito      |
| POST   | `/api/security/access-logs/entry`         | Registrar entrada de vehículo        |
| PUT    | `/api/security/access-logs/exit`          | Registrar salida de vehículo         |

---

## Seguridad

### Autenticación

- **JWT en cookies HTTP-only** (no `Authorization` header).
- Cookie `access_token` (30 min) para cada petición.
- Cookie `refresh_token` (7 días, 30 con remember-me) para renovar el access
  token.
- Las cookies tienen `httpOnly=true`, `secure=true`, `sameSite=None`, `path=/`.
- Endpoints públicos y Swagger no requieren autenticación.

### Cookies

| Cookie         | Duración                | Propósito                  |
|----------------|-------------------------|----------------------------|
| `access_token` | 30 minutos              | Autenticación de requests  |
| `refresh_token` | 7 días (30 con rm)     | Renovación del access token|

### Jerarquía de roles

Los roles no tienen herencia automática. Cada endpoint declara su rol
específico con `@PreAuthorize`. El método `Rol.puedeAsignarRol()` controla qué
roles puede crear cada usuario.

### CORS

- Origen único configurable vía `CORS_ALLOWED_ORIGINS`.
- Credenciales habilitadas (necesario para cookies).

---

## Desarrollo local

### Requisitos

- Java 25 JDK
- PostgreSQL (o conexión a Supabase)
- Cuenta en Resend (opcional, para correos)

### Variables de entorno (`.env`)

```env
SUPABASE_DB_HOST=aws-0-...pooler.supabase.com
SUPABASE_DB_PORT=6543
SUPABASE_DB_NAME=postgres
SUPABASE_DB_USER=postgres.xxxxx
SUPABASE_DB_PASSWORD=...
JWT_SECRET=...
CORS_ALLOWED_ORIGINS=http://localhost:5173
RESEND_API_KEY=re_...
MAIL_FROM=noreply@tudominio.com
APP_BASE_URL=http://localhost:5173
PORT=8080
```

### Ejecutar

```powershell
# Compilar (sin tests)
./mvnw clean package -DskipTests

# Servidor de desarrollo
./mvnw spring-boot:run

# Tests
./mvnw test
./mvnw test -Dtest=SgcApplicationTests

# Ejecutar JAR
java -jar target/sgc-0.0.1-SNAPSHOT.jar
```

### Docker

```bash
# Construir imagen
docker build -t sgc-backend .

# Ejecutar contenedor
docker run -p 8080:8080 --env-file .env sgc-backend
```

### Swagger UI

Disponible en `/docs/swagger-ui.html` (o en el host de producción).

---

## Estructura del proyecto (src/main/java)

```
com.condominios.sgc/
├── domain/
│   ├── model/          ApartamentoModel, CarritoModel, CondominioModel,
│   │                   EstacionamientoModel, InquilinoModel,
│   │                   LogAccesoVehicularModel, LogPrestamoCarritoModel,
│   │                   TorreModel, PisoModel, UsuarioModel, VehiculoModel,
│   │                   TokenModel, PaisModel, CiudadModel, MonedaModel,
│   │                   ConfiguracionModel
│   ├── service/        RegistroAccesoService, PrestamoCarritoService
│   ├── shared/
│   │   ├── exception/  Excepciones por dominio (ApartamentoException,
│   │   │               VehiculoException, CarritoException, etc.)
│   │   └── valueobject/ Correo, NombreCompleto, NumeroDocumento,
│   │                     PlacaVehiculo, Telefono
│   ├── type/           EstadoCarrito, MetodoEntrada, Rol, TipoDocumento,
│   │                   TipoHabitante, TipoVehiculo
│   └── util/           ValidacionUtil
├── application/
│   ├── dto/
│   │   ├── command/    Comandos CQRS para cada operación de escritura
│   │   ├── query/      PaginaQuery
│   │   └── result/     DTOs de resultado (Admin*, Propietario*, Security*)
│   ├── port/
│   │   ├── in/         Interfaces de casos de uso
│   │   └── out/        Puertos de repositorio y servicios (Correo, Hash,
│   │                   JWT, Seguridad)
│   └── service/        Implementaciones de casos de uso (∼20 clases)
└── infrastructure/
    ├── adapter/
    │   ├── in/web/
│   │   ├── controller/    8 controladores REST
│   │   ├── dto/request/   15 DTOs de petición
│   │   ├── dto/response/  15 DTOs de respuesta
    │   │   ├── handler/       (vacío, reservado)
    │   │   └── mapper/        4 mappers (SuperAdmin, AdminCondominio,
    │   │                       Propietario, Seguridad)
    │   └── out/persistence/
    │       ├── entity/        16 entidades JPA
    │       ├── mapper/        16 mappers entity ↔ domain
    │       └── repository/    16 interfaces JPA Repository
    ├── config/          AutenticacionConfig, AppConfig
    ├── security/        SecurityConfig, CookieBearerTokenResolver
    ├── service/         CorreoService, HashService, JwtService
    └── util/            JwtUtil, CookieUtils, SecurityUtils
```