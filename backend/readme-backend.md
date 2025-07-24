# 🧠 Lemon Task Manager - Backend (Java + Spring Boot)

Este backend fue desarrollado utilizando Spring Boot con Java 21. A continuación se documentan las decisiones técnicas, arquitectura utilizada, cómo correr el proyecto y cómo se implementaron los requerimientos.

## ⚙️ Tecnologías principales

- **Java 21 + Spring Boot 3.5**
- **Maven Wrapper** (`./mvnw`)
- **JWT (jjwt)** para autenticación segura
- **Spring Security**
- **Spring Data JPA + PostgreSQL (productivo) / H2 (test)**
- **Logback** con logs custom
- **JUnit 5 + Mockito** para testing
- **Docker + Docker Compose** para levantar la base de datos y el backend

## 📁 Estructura del proyecto

```
src/main/java/com/lemon/taskmanager
├── auth/                # Login, registro, JWT, AuthController
├── config/              # Configuración de seguridad (SecurityFilterChain, CORS)
├── exceptions/          # Manejador global y errores personalizados
├── tasks/               # Endpoints y lógica para manejar tareas
├── user/                # Entidad User, UserController y servicio
├── utils/               # Clases auxiliares (ej. métodos JWT)
└── TaskmanagerApplication.java
```

## 🔐 Seguridad y JWT

- El login y registro devuelven un **JWT** que debe enviarse en cada request bajo el header `Authorization: Bearer <token>`.
- Se implementó un filtro `JwtAuthenticationFilter` para validar tokens antes de ejecutar cualquier endpoint.
- CORS habilitado desde frontend (`http://localhost:5173`)
- Si el token es inválido o está ausente, se devuelve:
  - `401 Unauthorized` si no se proveyó token
  - `403 Forbidden` si se proveyó un token inválido

## ✅ Tests implementados

- **Test de integración**: Validan que `/tasks` devuelve `401` sin token y `200` con token.
- **Mockito**: Usado para testear servicios como AuthService, UserService, etc.

## 🔧 Logs personalizados

Se definió un `logback.xml` con el siguiente formato:

```
[MM-dd'T'HH:mm:ss] [INFO ] TaskController │ Tarea creada exitosamente
[MM-dd'T'HH:mm:ss] [WARN ] JwtFilter      │ Token inválido recibido
```

- Se eliminó la metadata innecesaria (PID, hilos, paths completos)
- Colores: `WARN` en amarillo, `ERROR` en rojo

## 🧪 Base de datos

- **PostgreSQL en entorno productivo (via Docker)**
- **H2 en memoria en los tests**
- `data.sql` para datos iniciales
- Se validan las relaciones entre `Users`, `Tasks`, `Projects` y `Components`

## 🚀 Cómo correr el back

Este proyecto usa **Maven Wrapper**, por lo que no es necesario tener Maven instalado globalmente.

### Opción 1: Usar Docker con Makefile

```bash
make up         # Levanta backend + DB (requiere Docker)
make down       # Baja contenedores
```

### Opción 2: Correr manualmente en local

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Corre en: `http://localhost:8081`

## 🔄 Endpoints principales

| Método | Ruta                     | Descripción                               | Protegido |
|--------|--------------------------|-------------------------------------------|-----------|
| POST   | `/auth/register`         | Crea un usuario                           | ❌        |
| POST   | `/auth/login`            | Devuelve JWT                              | ❌        |
| GET    | `/auth/environment`      | Devuelve info básica del usuario          | ✅        |
| GET    | `/users/project-members` | Lista los miembros de los proyectos       | ✅        |
| GET    | `/tasks`                 | Lista tareas visibles                     | ✅        |
| POST   | `/tasks`                 | Crea una nueva tarea                      | ✅        |
| GET    | `/tasks/{id}`            | Obtiene una tarea por ID                  | ✅        |
| PATCH  | `/tasks/{id}/status`     | Cambia el estado de una tarea             | ✅        |
| PATCH  | `/tasks/{id}/assign`     | Asigna un usuario a una tarea             | ✅        |

## ✏️ Funcionalidades implementadas

- Asignación de usuarios a tareas (crear y update)
- Validación de permisos según rol (`MANAGER`, `MEMBER`)
- Validación de miembros de proyecto al asignar tareas
- Endpoint `/auth/environment` para poblar `environment.ts`
- UserController que expone los miembros disponibles del proyecto
- Arquitectura limpia: mappers, dtos, dominio desacoplado de entidad
- Soporte para edición de estado, asignación, visualización de tareas
- Validación granular: `canEdit`, `canView`, `assignTo(...)`
- Proyecto y componentes también se pueden crear (servicio en progreso)

