# SGC — Sistema de Gestión de Condominios

Backend REST API para la administración integral de condominios, edificios y
conjuntos residenciales. Implementa cuatro roles con operaciones específicas:
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
| PUT    | `/api/super-admin/administrators/{id}/assign-condo`        | Asignar, reasignar o desasignar condominio a admin |
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

---

## Requerimientos Funcionales

### RF-01 · Módulo de Autenticación y Sesión
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-01.01 | El sistema debe permitir a los usuarios iniciar sesión con correo y contraseña. Debe soportar la opción "recuérdame" para sesiones extendidas. | POST | `/api/auth/login` |
| RF-01.02 | El sistema debe renovar tokens de acceso usando el refresh token almacenado en cookies. | POST | `/api/auth/refresh` |
| RF-01.03 | El sistema debe cerrar la sesión activa del usuario y limpiar las cookies de autenticación. | POST | `/api/auth/logout` |
| RF-01.04 | El sistema debe permitir solicitar el restablecimiento de contraseña vía correo electrónico. | POST | `/api/auth/forgot-password` |
| RF-01.05 | El sistema debe permitir restablecer la contraseña usando un token de recuperación. | POST | `/api/auth/reset-password` |
| RF-01.06 | El sistema debe permitir verificar una dirección de correo electrónico pendiente usando un token. | POST | `/api/auth/verify-email` |
| RF-01.07 | El sistema debe devolver la información del usuario autenticado actual. | GET | `/api/auth/me` |
| RF-01.08 | Los usuarios autenticados deben poder cambiar su contraseña proporcionando la actual y la nueva. | PUT | `/api/auth/change-password` |
| RF-01.09 | Los usuarios autenticados deben poder solicitar el cambio de su correo electrónico. | PUT | `/api/auth/email` |

### RF-02 · Módulo de Perfil de Usuario
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-02.01 | Los usuarios autenticados deben poder visualizar su perfil completo. | GET | `/api/profile` |
| RF-02.02 | Los usuarios autenticados deben poder editar su perfil (nombres, apellidos, teléfono). | PUT | `/api/profile` |

### RF-03 · Módulo de Catálogos
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-03.01 | El sistema debe permitir listar los países disponibles en el catálogo. | GET | `/api/catalogs/countries` |
| RF-03.02 | El sistema debe permitir listar las ciudades de un país específico. | GET | `/api/catalogs/countries/{countryId}/cities` |

### RF-04 · Módulo Super Admin — Dashboard
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-04.01 | El dashboard debe mostrar métricas globales: total de condominios, administradores y usuarios. | GET | `/api/super-admin/dashboard/metrics` |
| RF-04.02 | El dashboard debe listar los administradores creados más recientemente. | GET | `/api/super-admin/dashboard/recent-admins` |
| RF-04.03 | El dashboard debe listar los condominios creados más recientemente. | GET | `/api/super-admin/dashboard/recent-condos` |

### RF-05 · Módulo Super Admin — Gestión de Administradores
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-05.01 | El sistema debe listar administradores con paginación y filtros opcionales por búsqueda textual y estado activo/inactivo. | GET | `/api/super-admin/administrators` |
| RF-05.02 | El sistema debe permitir crear un administrador con: nombres, apellidos, correo, teléfono y contraseña. | POST | `/api/super-admin/administrators` |
| RF-05.03 | El sistema debe permitir editar un administrador (nombres, apellidos, teléfono). | PUT | `/api/super-admin/administrators/{id}` |
| RF-05.04 | El sistema debe permitir activar o desactivar un administrador. | PATCH | `/api/super-admin/administrators/{id}` |
| RF-05.05 | El sistema debe permitir eliminar un administrador. | DELETE | `/api/super-admin/administrators/{id}` |
| RF-05.06 | El sistema debe permitir asignar, reasignar o desasignar un condominio a un administrador (enviar {} para desasignar). | PUT | `/api/super-admin/administrators/{id}/assign-condo` |
| RF-05.07 | El sistema debe listar administradores disponibles (sin condominio asignado). | GET | `/api/super-admin/administrators/available` |

### RF-06 · Módulo Super Admin — Gestión de Condominios
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-06.01 | El sistema debe listar condominios con paginación y filtros por búsqueda y estado. | GET | `/api/super-admin/condominiums` |
| RF-06.02 | El sistema debe listar condominios sin administrador asignado. | GET | `/api/super-admin/condominiums/unassigned` |
| RF-06.03 | El sistema debe permitir crear un condominio con: nombre, país, ciudad y dirección. | POST | `/api/super-admin/condominiums` |
| RF-06.04 | El sistema debe permitir editar un condominio (nombre, país, ciudad, dirección). | PUT | `/api/super-admin/condominiums/{id}` |
| RF-06.05 | El sistema debe permitir activar o desactivar un condominio. | PATCH | `/api/super-admin/condominiums/{id}` |
| RF-06.06 | El sistema debe permitir eliminar un condominio. | DELETE | `/api/super-admin/condominiums/{id}` |

### RF-07 · Módulo Super Admin — Usuarios Globales
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-07.01 | El sistema debe listar todos los usuarios del sistema con paginación y filtros por búsqueda, rol y estado activo. | GET | `/api/super-admin/users` |
| RF-07.02 | El sistema debe permitir activar o desactivar cualquier usuario. | PATCH | `/api/super-admin/users/{id}/status` |
| RF-07.03 | El sistema debe permitir invalidar la sesión activa de un usuario. | POST | `/api/super-admin/users/{id}/invalidate-session` |
| RF-07.04 | El sistema debe permitir forzar el cambio de contraseña de un usuario. | PUT | `/api/super-admin/users/{id}/force-password` |

### RF-08 · Módulo Admin Condominio — Dashboard
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-08.01 | El dashboard del condominio debe mostrar métricas: total de torres, pisos, apartamentos, propietarios, agentes, vehículos y carritos. | GET | `/api/admin/dashboard/metrics` |

### RF-09 · Módulo Admin Condominio — Información y Configuración
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-09.01 | El administrador debe poder consultar la información general de su condominio (incluye configuración). | GET | `/api/admin/condominium/my-info` |
| RF-09.02 | El administrador debe poder editar el nombre y la dirección de su condominio. | PUT | `/api/admin/condominium/my-info` |
| RF-09.03 | El administrador debe poder consultar la configuración detallada del condominio. | GET | `/api/admin/condominium/configuracion` |
| RF-09.04 | El administrador debe poder editar la configuración (max autos, max motos, penalización/min, max tiempo préstamo, max estacionamientos, max carritos, max vehículos, max inquilinos). | PUT | `/api/admin/condominium/configuracion` |

### RF-10 · Módulo Admin Condominio — Estructura Física
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-10.01 | El sistema debe mostrar la estructura completa del condominio en formato jerárquico (Torres → Pisos → Apartamentos). | GET | `/api/admin/structure` |
| RF-10.02 | El sistema debe permitir crear nodos de la estructura (torre, piso o apartamento) con parámetros específicos según el tipo. | POST | `/api/admin/structure/nodes` |
| RF-10.03 | El sistema debe permitir eliminar un nodo de la estructura especificando su tipo (TORRE, PISO, APARTAMENTO). | DELETE | `/api/admin/structure/nodes/{id}?type=` |

### RF-11 · Módulo Admin Condominio — Usuarios
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-11.01 | El sistema debe listar los usuarios del condominio con paginación y filtros por búsqueda, rol y estado. | GET | `/api/admin/users` |
| RF-11.02 | El sistema debe permitir crear usuarios (propietarios o agentes de seguridad) con: nombres, apellidos, correo, teléfono, contraseña y rol. | POST | `/api/admin/users` |
| RF-11.03 | El sistema debe permitir editar un usuario (nombres, apellidos, teléfono). | PUT | `/api/admin/users/{id}` |
| RF-11.04 | El sistema debe permitir activar o desactivar un usuario del condominio. | PATCH | `/api/admin/users/{id}/status` |

### RF-12 · Módulo Admin Condominio — Apartamentos
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-12.01 | El sistema debe listar apartamentos con su propietario e inquilinos (paginado). | GET | `/api/admin/apartments` |
| RF-12.02 | El sistema debe permitir asignar un propietario a un apartamento. | PUT | `/api/admin/apartments/{id}/assign-owner` |
| RF-12.03 | El sistema debe permitir actualizar la lista completa de inquilinos de un apartamento (con tipo de documento y número). | PUT | `/api/admin/apartments/{id}/occupants` |

### RF-13 · Módulo Admin Condominio — Activos
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-13.01 | El sistema debe listar activos filtrados por tipo (CARRITO o ESTACIONAMIENTO) con paginación. | GET | `/api/admin/assets?type=` |
| RF-13.02 | El sistema debe permitir crear un activo especificando tipo, código y número. | POST | `/api/admin/assets` |
| RF-13.03 | El sistema debe permitir actualizar el estado de un activo (tipo, estado, disponibilidad, tipo de vehículo, capacidad máxima). | PUT | `/api/admin/assets/{id}/status` |

### RF-14 · Módulo Admin Condominio — Logs
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-14.01 | El sistema debe listar los registros de actividad filtrados por tipo (VEHICULAR o CARRITO), con filtros opcionales por usuario y rango de fechas. Las fechas se reciben en formato ISO-8601 (Instant.parse). | GET | `/api/admin/logs?type=` |

### RF-15 · Módulo Agente de Seguridad — Dashboard
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-15.01 | El dashboard de seguridad debe mostrar el estado operativo actual (estacionamientos ocupados, préstamos activos, movimientos recientes). | GET | `/api/security/dashboard/status` |
| RF-15.02 | El sistema debe listar los estacionamientos con su disponibilidad en tiempo real. | GET | `/api/security/parking-slots` |
| RF-15.03 | El sistema debe listar los préstamos de carritos actualmente activos. | GET | `/api/security/asset-loans/active-carts` |

### RF-16 · Módulo Agente de Seguridad — Control Vehicular
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-16.01 | El sistema debe verificar un vehículo por su placa al momento de ingreso, retornando si está registrado y sus datos. | GET | `/api/security/vehicles/verify/{plate}` |
| RF-16.02 | El sistema debe registrar la entrada de un vehículo indicando: placa, método de entrada (OCR/MANUAL), tipo de ocupante (PROPIETARIO/INQUILINO) y datos del inquilino (si aplica). | POST | `/api/security/access-logs/entry` |
| RF-16.03 | El sistema debe registrar la salida de un vehículo usando el ID del log de acceso. | PUT | `/api/security/access-logs/exit` |

### RF-17 · Módulo Agente de Seguridad — Préstamos de Carritos
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-17.01 | El sistema debe permitir registrar el préstamo de un carrito indicando: código del carrito, número de apartamento, nombre y DNI del solicitante. | POST | `/api/security/asset-loans` |
| RF-17.02 | El sistema debe permitir registrar la devolución de un carrito, calculando penalización si excede el tiempo máximo. | PUT | `/api/security/asset-loans/{id}/return` |

### RF-18 · Módulo Propietario — Dashboard y Apartamento
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-18.01 | El propietario debe poder ver un resumen de su apartamento (torre, piso, inquilinos, vehículos). | GET | `/api/homeowner/dashboard/summary` |
| RF-18.02 | El propietario debe poder ver el detalle completo de su apartamento (inquilinos y vehículos). | GET | `/api/homeowner/apartment/details` |

### RF-19 · Módulo Propietario — Inquilinos
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-19.01 | El propietario debe poder listar los inquilinos de su apartamento. | GET | `/api/homeowner/tenants` |
| RF-19.02 | El propietario debe poder agregar un inquilino (nombres, apellidos, tipo de documento, número de documento). | POST | `/api/homeowner/tenants` |
| RF-19.03 | El propietario debe poder eliminar un inquilino. | DELETE | `/api/homeowner/tenants/{id}` |

### RF-20 · Módulo Propietario — Vehículos
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-20.01 | El propietario debe poder listar sus vehículos. | GET | `/api/homeowner/vehicles` |
| RF-20.02 | El propietario debe poder agregar un vehículo (marca, color, modelo, placa, tipo). | POST | `/api/homeowner/vehicles` |
| RF-20.03 | El propietario debe poder eliminar un vehículo. | DELETE | `/api/homeowner/vehicles/{id}` |

### RF-21 · Módulo Propietario — Historial
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-21.01 | El propietario debe poder consultar sus logs de actividad filtrados por tipo (VEHICULAR/CARRITO), con filtros opcionales por rango de fechas y paginación. | GET | `/api/homeowner/logs?type=` |

### RF-22 · Salud del Sistema
| ID | Requerimiento | Método | Endpoint |
|---|---|---|---|
| RF-22.01 | El sistema debe responder con un mensaje de bienvenida en la ruta raíz. | GET | `/` |
| RF-22.02 | El sistema debe proveer un endpoint de health check que retorne `{"status": "ok"}`. | GET | `/api/health` |

---

## Requerimientos No Funcionales

### RNF-01 · Seguridad
| ID | Requerimiento |
|---|---|
| RNF-01.01 | Autenticación basada en JWT con tokens de acceso y refresco. |
| RNF-01.02 | Tokens enviados/almacenados en cookies HTTP (no en headers Authorization directamente). |
| RNF-01.03 | Sesión stateless (sin estado en servidor). |
| RNF-01.04 | Protección CORS habilitada con configuración personalizada. |
| RNF-01.05 | CSRF deshabilitado (adecuado para API REST stateless). |
| RNF-01.06 | Autorización basada en roles usando @PreAuthorize a nivel de controlador. |
| RNF-01.07 | Rol extraído del claim rol del JWT y prefijado con ROLE_. |

### RNF-02 · Manejo de Errores
| Código HTTP | Excepción(es) Mapeada(s) | Escenario |
|---|---|---|
| 400 Bad Request | DominioException, ValueObjectException, MethodArgumentNotValidException, MissingServletRequestParameterException | Datos inválidos, validación fallida, parámetros faltantes |
| 401 Unauthorized | AutenticacionException, TokenException, JwtException | Token expirado, credenciales inválidas |
| 403 Forbidden | AccessDeniedException | Sin permisos para la acción |
| 409 Conflict | DataIntegrityViolationException, ObjectOptimisticLockingFailureException | Datos duplicados, conflicto de concurrencia |
| 500 Internal Server Error | Exception (fallback) | Error inesperado |

### RNF-03 · Documentación
| ID | Requerimiento |
|---|---|
| RNF-03.01 | El sistema debe exponer documentación OpenAPI/Swagger UI. |

### RNF-04 · Resiliencia
| ID | Requerimiento |
|---|---|
| RNF-04.01 | El sistema debe soportar patrones de resiliencia (circuit breaker, retry, rate limiter). |
| RNF-04.02 | El sistema debe soportar reintentos automáticos en operaciones críticas. |

### RNF-05 · Despliegue
| ID | Requerimiento |
|---|---|
| RNF-05.01 | El sistema debe ser contenerizable con Docker. |
| RNF-05.02 | El sistema debe contar con scripts de datos iniciales (seed). |
