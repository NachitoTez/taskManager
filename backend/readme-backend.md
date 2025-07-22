# 🧠 Lemon Task Manager - Backend (Java + Spring Boot)

Este backend fue desarrollado como parte del challenge técnico de Lemon, utilizando Spring Boot con Java 21. A continuación se documentan las decisiones técnicas, arquitectura utilizada, cómo correr el proyecto y cómo se implementaron los requerimientos.

## ⚙️ Tecnologías principales

- **Java 21 + Spring Boot 3.5**
- **Maven Wrapper** (`./mvnw`)
- **JWT (jjwt)** para autenticación segura
- **Spring Security**
- **Spring Data JPA + H2 (en memoria)**
- **Logback** con logs custom
- **JUnit 5 + Mockito** para testing

## 📁 Estructura del proyecto

```bash
src/main/java/com/lemon/taskmanager
├── auth/                # Login, registro, JWT, AuthController
├── config/              # Configuración de seguridad (SecurityFilterChain, CORS)
├── exceptions/          # Manejador global y errores personalizados
├── tasks/               # Endpoints y lógica para manejar tareas
├── userEntity/                # Entidad User y acceso a datos
├── utils/               # Clases auxiliares (ej. métodos JWT)
└── TaskmanagerApplication.java
```

## 🔐 Seguridad y JWT

- El login y registro devuelven un **JWT** que debe enviarse en cada request bajo el header `Authorization: Bearer <token>`.
- Se implementó un filtro `JwtAuthenticationFilter` para validar tokens antes de ejecutar cualquier endpoint.
- Si el token es inválido o está ausente, se devuelve:
  - `401 Unauthorized` si no se proveyó token
  - `403 Forbidden` si se proveyó un token inválido

## ✅ Tests implementados

- **Test de integración**: Validan que `/tasks` devuelve `401` sin token y `200` con token.
- **Mockito**: Usado para testear servicios y lógica de negocio de forma aislada (ej. login, register, authService).

## 🔧 Logs personalizados

Se definió un `logback.xml` con el siguiente formato:

```
[MM-dd'T'HH:mm:ss] [INFO ] TaskController │ Tarea creada exitosamente
[MM-dd'T'HH:mm:ss] [WARN ] JwtFilter      │ Token inválido recibido
```

- Se eliminó la metadata innecesaria (PID, hilos, paths completos)
- Colores: `WARN` en amarillo, `ERROR` en rojo

## 🧪 Base de datos

- Para simplificar la ejecución local, se usó **H2 en memoria** (`jdbc:h2:mem:taskdb`)
- No requiere instalación
- Se accede a la consola desde `/h2-console`
- Se puede migrar fácilmente a PostgreSQL o MySQL agregando el datasource en `application.properties`

## 🚀 Cómo correr el back

Este proyecto usa **Maven Wrapper**, por lo que no es necesario tener Maven instalado globalmente.

### En Linux / macOS:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

### En Windows:

```bash
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

Corre en: `http://localhost:8081`

## 🔄 Endpoints

| Método | Ruta             | Descripción                       | Protegido |
|--------|------------------|-----------------------------------|-----------|
| POST   | `/auth/register` | Crea un usuario                   | ❌        |
| POST   | `/auth/login`    | Devuelve JWT                      | ❌        |
| GET    | `/tasks`         | Lista tareas                      | ✅        |
| POST   | `/tasks`         | Crea una nueva tarea              | ✅        |

## 📦 Consideraciones adicionales

- Se planea agregar `Dockerfile` para facilitar la ejecución
- Todas las entidades están desacopladas usando **DTOs**
- La arquitectura respeta el patrón **controller → service → repository**
- El código es fácilmente escalable a múltiples usuarios y roles
