# 📝 taskManager

Aplicación full stack para gestión colaborativa de tareas.

## 📦 Requisitos

Para poder correr el proyecto localmente se recomienda tener instalado:

- **Java 21**
- **Node.js 20.17+** (idealmente 20.17 o 20.19 para evitar errores de compatibilidad con Vite)
- **Make** (opcional pero les facilitaría correr las apps)

---

## 🚀 Iniciar el proyecto

Este proyecto incluye un `Makefile` para facilitar el inicio tanto del backend como del frontend.

### ▶️ Build

🔧 Construye el backend y prepara el frontend para ejecutarse.
```bash
make build
```

### ▶️ Backend

```bash
make run-backend
```

Ejecuta el backend con Spring Boot y lo deja corriendo en:

http://localhost:8081

### ▶️ Frontend

```bash
make run-frontend
```

Levanta la interfaz con Vite en modo desarrollo en:

http://localhost:8080

ℹ️ Ambos comandos corren en terminales separadas automáticamente (macOS).

---

## 🧪 Testing

El backend incluye tests de integración que validan:

- Registro de usuarios
- Login y generación de token
- Acceso denegado sin token
- Acceso permitido con token válido

Para correrlos:

```bash
make test
# o directamente:
./mvnw test
```

---

## 📁 Otros documentos

Este README resume el proyecto a alto nivel.

Para detalles más específicos:

- `frontend/readme-frontend.md`
- `backend/readme-backend.md`

---


## 📌 API Endpoints

### 🧑‍💻 Autenticación

#### POST `/auth/register`

Registra un nuevo usuario y devuelve un JWT.

```json
{
  "username": "lemon",
  "password": "EsMejorQueBelo"
}
```

#### POST `/auth/login`

Inicia sesión y devuelve un JWT válido.

```json
{
  "username": "lemon",
  "password": "EsMejorQueBelo"
}
```

---

### ✅ Tareas

#### GET `/tasks`

Requiere token en el header:

```http
Authorization: Bearer <token>
```

- `401 Unauthorized`: Token inválido o ausente.
- `403 Forbidden`: Usuario autenticado sin permisos (a futuro cuando haya roles).
- `200 OK`: Lista de tareas del usuario.

---

## 🔐 Seguridad

- Los tokens se generan y validan con firma HMAC usando una clave secreta definida en `application.yml`.
- Actualmente no se usa una base de datos real: los datos son volátiles.
- En ambientes productivos, la clave JWT debería moverse a un entorno seguro como variables `.env` o secrets del sistema operativo.

---


## 🧑‍🎓 Autor

Ignacio Ramírez · [LinkedIn](https://www.linkedin.com/in/ignacio-ramirez-guembe/) · [GitHub](https://github.com/NachitoTez/taskManager)
