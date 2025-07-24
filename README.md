# 📝 taskManager

Aplicación full stack para la gestión colaborativa de tareas. Incluye autenticación, manejo de roles, componentes, tareas y un modelo de permisos simplificado para demostrar buenas prácticas de arquitectura y seguridad.

---

## 📦 Requisitos

- **Java 21**
- **Node.js 20.17+**
- **Make** (opcional, pero recomendado para ejecutar los comandos)

---

## 🚀 Iniciar el proyecto

Este repositorio incluye un `Makefile` para facilitar el arranque del backend y el frontend.
(Con make up + make run_frontend deberían tener lo que necesitan)


### ▶️ Docker con contenedores

```bash
make up
```

Construye el jar y levanta los contenedores con Docker (incluyendo backend y base de datos).

```bash
make run
```

Levanta los contenedores ya construidos (sin hacer rebuild).

```bash
make down
```

Detiene y elimina los contenedores de Docker.

```bash
make purge
```

Detiene y elimina los contenedores de Docker junto con los volúmenes persistentes.

### ▶️ Build completo

```bash
make build
```

Instala las dependencias del backend y frontend (útil para preparar el entorno local).

### ▶️ Solo Backend

```bash
make run-backend
```

Ejecuta el backend localmente con Spring Boot en:

📍 http://localhost:8081

### ▶️ Solo Frontend

```bash
make run-frontend
```

Inicia el frontend localmente en modo desarrollo en:

📍 http://localhost:8080

### ▶️ Construcción del JAR

```bash
make jar
```

Construye el jar del backend y lo copia con un nombre fijo para Docker.

ℹ️ En macOS se abren automáticamente en terminales separadas.

---

## 🧪 Testing

El backend contiene tests de integración para:

- Registro de usuarios
- Login + generación de JWT
- Accesos autorizados/denegados
- Operaciones con tareas

```bash
./mvnw test
```

---

## ⚙️ Arquitectura

El backend sigue una estructura basada en capas (Controller, Service, Repository, Domain, DTO, Mapper).

El frontend usa **React + Vite**.

---

## 📌 Funcionalidades principales

- Registro y login de usuarios
- JWT para autenticación
- Elección de rol (manager o member) al registrarse
- Asignación y actualización de tareas
- Control de estados con flujo permitido (ej. `BACKLOG → IN_PROGRESS → TESTING → DONE`)
- Estados especiales: `BLOCKED` y `WAITING_INFO` que solo permiten volver atrás
- Permisos granulares:
  - Cualquiera puede tomar una tarea sin asignado
  - Solo managers o el actual asignado pueden cambiar la asignación
  - Solo se ven tareas propias o de proyectos donde el usuario participa

---

## 📁 Organización

Las tareas se agrupan en componentes (TaskComponents) y estos a su vez en proyectos. Esta jerarquía permite estructurar de forma ordenada el trabajo de equipos y usuarios.

El nombre `Component` fue evitado por conflicto con la anotación `@Component` de Spring.

---

## 📚 Más detalles

Para ver más en profundidad:

- `backend/readme-backend.md`: estructura técnica, endpoints, lógica de dominio, testing
- `frontend/readme-frontend.md`: vistas, navegación, arquitectura de componentes, consumo de ApiInterceptor

---

## 🔐 Seguridad

- JWT con HMAC firmados (clave definida en `application.yml`)
- No hay base de datos persistente, se usa H2 in-memory
- En producción, las claves deben protegerse mediante variables de entorno

---

## 🧑‍🎓 Autor

Ignacio Ramírez · [LinkedIn](https://www.linkedin.com/in/ignacio-ramirez-guembe/) · [GitHub](https://github.com/NachitoTez/taskManager)