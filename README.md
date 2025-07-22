# 📋 taskManager

Aplicación de gestión de tareas con autenticación.

---

## 🚀 Cómo correr el proyecto

Cree un makefile para que sea tan sencillo como tocar dos botones.
Si no tienen el plugin: En la terminal parados en taskManager hacen make run-backend, y en otra terminal make run-frontend.
Esto hace un install + run para el front y back

El backend se iniciará en:  
`http://localhost:8081`

El frontend se iniciará en:  
`http://localhost:8080`

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
