# 📋 taskManager

Aplicación de gestión de tareas con autenticación.

---

## 🚀 Cómo correr el proyecto

Este proyecto usa **Maven Wrapper**, por lo tanto no necesitás tener Maven instalado globalmente.

### 🔧 Compilar y levantar el backend

#### En macOS / Linux:
```bash
./mvnw clean install
./mvnw spring-boot:run
```

#### En Windows:
```bash
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

El backend se iniciará en:  
`http://localhost:8081`

---

## 🔐 Endpoints de autenticación

### ➕ POST `/auth/register`

Registra un nuevo usuario.

**Body JSON**:
```json
{
  "username": "lemon",
  "password": "EsMejorQueBelo"
}
```

**Respuesta**:  
`200 OK` con token JWT:
```json
{
  "token": "blabla..."
}
```

---

### 🔑 POST `/auth/login`

Inicia sesión con usuario registrado.

**Body JSON**:
```json
{
  "username": "lemon",
  "password": "EsMejorQueBelo"
}
```

**Respuesta**:
```json
{
  "token": "blabla..."
}
```

---

## 📌 Endpoints protegidos

### 🔒 GET `/tasks`

Requiere header de autorización con el token recibido al registrarse o loguearse.

**Headers**:
```
Authorization: Bearer <token>
```

**Respuesta sin token válido**:
- `401 Unauthorized`: Token inválido o faltante
- `403 Forbidden`: Usuario autenticado pero sin permisos suficientes

---

## 🧪 Tests

Para correr los tests:
```bash
./mvnw test
```

Hay tests de integración para verificar:
- Acceso denegado sin token
- Acceso correcto con token válido
- Registro y login funcionando correctamente

---

## 🛠️ Stack tecnológico

- Java 21
- Spring Boot 3
- Spring Security
- JWT (via `jjwt`)
- Maven Wrapper
- JUnit 5 + AssertJ
- Mockito (para tests unitarios)
- SLF4J + Logback con formato customizado

---

## 🗃️ Configuración útil

**Puerto por defecto**: `8081`  
Se puede cambiar en `src/main/resources/application.yml`:
```yaml
server:
  port: 8081
```

**Clave JWT**: No está en un .env para que sea más sencillo de correr para ustedes.
En cualquier otro caso debería estar como un secret para no quedar expuesta.
Está definida en `application.yml` como:
```yaml
jwt:
  secret: 
```


---
Hecho por Ignacio Ramirez :p
