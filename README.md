# SGC - Sistema de Gestión de Condominios

Backend API RESTful para la administración integral de condominios, desarrollado con **Java 25** y **Spring Boot 4.0.6** siguiendo una arquitectura hexagonal (puertos y adaptadores).

## 🏗️ Arquitectura

El proyecto sigue los principios de **Arquitectura Hexagonal** (Clean Architecture):

```
web/controllers → application/services → application/usecases → domain/ports
                                                                      ↓
                                                         infrastructure/adapters
                                                                      ↓
                                                         persistence/repositories → PostgreSQL
```

- **`domain/`** — Modelos de negocio puros (POJOs), puertos de salida (interfaces de repositorio), excepciones de dominio y enumeraciones.
- **`application/`** — Casos de uso (interfaces e implementaciones), DTOs de entrada y servicios facade.
- **`infrastructure/`** — Adaptadores JPA (entidades, repositorios, mappers, especificaciones), seguridad, cliente de email y configuración de Spring.
- **`web/`** — Controladores REST, DTOs de respuesta y manejador global de excepciones.

## 🧰 Stack Tecnológico

| Categoría          | Tecnología                                         |
|--------------------|-----------------------------------------------------|
| **Lenguaje**       | Java 25                                             |
| **Framework**      | Spring Boot 4.0.6, Spring MVC, Spring Security, Spring Data JPA |
| **Base de datos**  | PostgreSQL 16 (Supabase)                            |
| **Autenticación**  | OAuth2 Resource Server + JWT (jjwt 0.12.6)          |
| **Documentación**  | SpringDoc OpenAPI 2.8.5 (Swagger UI)                |
| **Email**          | Resend API                                          |
| **Build**          | Maven 3.9.14 (Maven Wrapper)                        |
| **Tests**          | Spring Boot Test, JUnit, H2 (in-memory)             |
| **Container**      | Docker (multi-stage, Alpine)                        |
| **Deploy**         | Render.com (Docker runtime)                         |

## 🚀 Ejecución Local

### Requisitos

- Java 25 JDK
- Docker (opcional)

### Pasos

```bash
# 1. Clonar el repositorio
git clone <repo-url>
cd SistemaGestionCondominios-Backend

# 2. Crear archivo .env con las variables de entorno necesarias
# (ver sección Variables de Entorno)

# 3. Compilar el proyecto
./mvnw clean package -DskipTests

# 4. Ejecutar tests
./mvnw test

# 5. Iniciar la aplicación
./mvnw spring-boot:run
```

La aplicación se iniciará en `http://localhost:8080`.

### Docker

```bash
docker build -t sgc-backend .
docker run -p 8080:8080 --env-file .env sgc-backend
```

## 📋 Variables de Entorno

| Variable                | Descripción                             |
|-------------------------|-----------------------------------------|
| `SUPABASE_URL`          | URL del proyecto Supabase               |
| `SUPABASE_DB_HOST`      | Host de la base de datos PostgreSQL     |
| `SUPABASE_DB_PORT`      | Puerto de la base de datos              |
| `SUPABASE_DB_NAME`      | Nombre de la base de datos              |
| `SUPABASE_DB_USER`      | Usuario de la base de datos             |
| `SUPABASE_DB_PASSWORD`  | Contraseña de la base de datos          |
| `JWT_SECRET`            | Secreto para firmar tokens JWT          |
| `RESEND_API_KEY`        | API Key de Resend para envío de correos |
| `MAIL_FROM`             | Dirección remitente de correos          |
| `APP_BASE_URL`          | URL base del frontend                   |
| `PORT`                  | Puerto del servidor (default: 8080)     |

## 🔐 Autenticación y Seguridad

- **JWT** en cookies (no en `Authorization` header) mediante `CookieBearerTokenResolver`
- **Roles**: `SUPER_ADMINISTRADOR`, `ADMINISTRADOR_CONDOMINIO`, `RESIDENTE`, `GUARDIA`
- **Tokens**: Access token (15 min), Refresh token (7 días), Remember-me (30 días)
- **Endpoints públicos**: `/api/auth/login`, `/api/auth/refresh`, `/api/auth/forgot-password`, `/api/auth/reset-password`, `/api/auth/verificar-email`, `/api/health`, Swagger UI
- **Contraseñas** hasheadas con BCrypt

## 📚 API Endpoints

| Grupo                    | Prefijo                    |
|--------------------------|----------------------------|
| Autenticación            | `/api/auth/*`              |
| Usuarios                 | `/api/usuarios/*`          |
| Condominios              | `/api/condominios/*`       |
| Torres                   | `/api/torres/*`            |
| Pisos                    | `/api/pisos/*`             |
| Apartamentos             | `/api/apartamentos/*`      |
| Inquilinos               | `/api/inquilinos/*`        |
| Vehículos                | `/api/vehiculos/*`         |
| Estacionamientos         | `/api/estacionamientos/*`  |
| Carritos compartidos     | `/api/carritos/*`          |
| Configuración            | `/api/configuracion/*`     |
| Log acceso vehicular     | `/api/log-acceso-vehicular/*` |
| Log préstamo carrito     | `/api/log-prestamo-carrito/*` |
| Health check             | `/api/health`              |

### Documentación Swagger

Disponible en `/swagger-ui.html` (OpenAPI spec en `/v3/api-docs`).

## 🗄️ Entidades del Dominio

- **Condominio** → **Torre** → **Piso** → **Apartamento** → **Inquilino**
- **Usuario** — usuarios del sistema con roles
- **Vehículo** — registro vehicular con log de entrada/salida
- **Estacionamiento** — espacios de estacionamiento
- **Carrito** — carritos compartidos con préstamo/devolución
- **Configuración** — configuración por condominio

## 🧪 Tests

```bash
./mvnw test
```

Los tests usan **H2** como base de datos en memoria.

## 📦 Despliegue

### Render.com

La API está desplegada en Render.com y se puede consultar en:

**🔗 Swagger UI:** [https://sistemagestioncondominios-backend.onrender.com/swagger-ui/index.html](https://sistemagestioncondominios-backend.onrender.com/swagger-ui/index.html#/health-controller/health_1)

El archivo `render.yaml` define el servicio `sgc-backend` usando runtime Docker (plan gratuito). Las variables de entorno marcadas como `sync: false` deben configurarse manualmente en el dashboard de Render.

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/condominios/sgc/
│   │   ├── SgcApplication.java
│   │   ├── application/
│   │   │   ├── dto/           # Request DTOs
│   │   │   ├── impl/          # Implementaciones de casos de uso
│   │   │   ├── service/       # Servicios facade
│   │   │   └── usecase/       # Interfaces de casos de uso
│   │   ├── domain/
│   │   │   ├── auxiliar/      # Enums y value objects
│   │   │   ├── dto/           # DTOs de dominio
│   │   │   ├── exception/     # Excepciones de dominio
│   │   │   ├── model/         # Modelos de dominio (POJOs)
│   │   │   └── port/          # Puertos de salida
│   │   ├── infrastructure/
│   │   │   ├── adapter/       # Adaptadores de puertos
│   │   │   ├── client/        # Clientes externos (Resend)
│   │   │   ├── config/        # Configuración Spring
│   │   │   ├── persistence/   # JPA entities, mappers, repositories, specifications
│   │   │   ├── security/      # Spring Security config
│   │   │   └── util/          # Utilidades
│   │   └── web/
│   │       ├── controller/    # Controladores REST
│   │       ├── dto/           # Response DTOs
│   │       └── exception/     # Global exception handler
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/condominios/sgc/
```