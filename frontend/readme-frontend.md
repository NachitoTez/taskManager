# Lemon Task Manager - Frontend (React + Vite + TS)

Este frontend fue desarrollado utilizando React con TypeScript y Vite. A continuación se detallan las decisiones tomadas, funcionalidades implementadas, y tareas pendientes.

---

## ⚙️ Tecnologías principales

- **React + TypeScript**: Para una UI declarativa, predecible y fuertemente tipada.
- **Vite**: Elegido como bundler por su velocidad y simplicidad.
- **Zod + React Hook Form**: Para validaciones de formularios, con foco en performance y DX.
- **Axios**: Para las llamadas al backend.
- **Sass**: Para estilos con anidamiento y limpieza estructural.

---

## 📁 Estructura de carpetas

```bash
src/
├── assets/                # Logos e imágenes
├── components/            # Componentes reutilizables (Navbar, Sidebar, Toast, ProtectedLayout)
├── context/               # Contextos globales (AuthContext próximamente)
├── pages/                 # Páginas principales (Login, Register, TaskList, TaskDetail)
│   ├── Auth/              # Componente Auth compartido entre login/register
│   ├── Login/             # Wrapper para <Auth isLogin />
│   └── Register/          # Wrapper para <Auth isLogin={false} />
├── services/              # Axios config + logger personalizado
├── styles/                # Estilos globales y variables Sass (variables, globals)
└── App.tsx                # Router principal
```

---

## 🔐 Autenticación

Se agregó un `AuthContext` que maneja:

- Usuario autenticado global
- Validación de expiración del token
- Logout automático

Además, se cuenta con una `ProtectedLayout` que bloquea rutas si no hay token presente.

---

## 🧠 Componentización inteligente: Auth.tsx

En vez de tener un formulario de login y otro de registro por separado, se creó un solo componente `Auth.tsx` con un `prop isLogin`. Según ese valor:

- Se cambia el título: "Bienvenido/a" vs "Crea tu cuenta"
- Se adapta el texto del botón y el footer
- Se llama a `login()` o `register()` dinámicamente

Además:
- Se derivó el `username` a partir del email (usando `email` como identificador único)
- Se manejó el loading con un spinner centrado
- Se agregó un `toast` de error que desaparece al hacer `navigate`

---

## ✅ Validaciones implementadas

- Email válido (`zod.email()` con mensaje custom)
- Contraseña mínima de 6 caracteres
- Deshabilitación del botón en `loading`
- Trim y lowercase de email antes de envío (interno)

---

## ✅ Funcionalidades actuales

- Login y registro funcional
- Vista principal (`/tasks`) protegida por autenticación
- Sidebar con navegación entre secciones
- Navbar con logout, bienvenida y filtros
- Listado de tareas principales (`TaskList`)
- Responsividad en el login

---


## 👤 Asignación visible

Cada tarea ahora muestra a qué usuario está asignada.

---
## 📝 Cosas que no llegué a implementar


## Vista de detalle de tarea

Cada tarea puede ser consultada desde `/tasks/:taskId`.

> Esta vista mostrará toda la información editable de la tarea (nombre, descripción, estado, asignado).

Actualmente está en desarrollo. Se prevé que desde esta vista se pueda:

- Editar campos básicos
- Cambiar el estado (ej: backlog, in progress, done)
- Reasignar responsable/desasignarse.

---

## 🔎 Filtros en el listado

Está todo listo para implementarlo, pero no me dio el tiempo. Se filtraría por

- Estado de la tarea (ej: All, Active, Backlog, Done)
- Responsable asignado

Esto permitirá reducir el ruido visual en equipos grandes.


## 🧪 Eliminación de tareas

En próximas versiones se incluirá la posibilidad de eliminar una tarea desde la lista o el detalle.

---

## 🧱 Organización futura por proyecto o componente

Actualmente el listado de tareas es global.  
Una vez finalizada la sección de creación y visualización de proyectos, se espera:

- Listar tareas agrupadas por proyecto
- Alternativamente, permitir verlas agrupadas por componente funcional

---

## ❌ Lo que se evitó por simplicidad

- No se usó `i18n` ni variables para los textos (el copy está estático)
- No se usó ninguna librería de UI (MUI, Chakra, etc.) para mantener el bundle liviano
- No se incluyó aún paginación ni scroll infinito

---

## 📦 Consideraciones extra

- CORS configurado en backend Spring Boot para permitir llamadas desde Vite
- Proyecto corre en el puerto 8080 (frontend) y 8081 (backend)