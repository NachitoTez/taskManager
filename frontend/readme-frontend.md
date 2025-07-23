# Lemon Task Manager - Frontend (React + Vite + TS)

Este frontend fue desarrollado como parte del challenge técnico de Lemon, utilizando React con TypeScript y Vite. A continuación se detallan las decisiones tomadas, librerías utilizadas y estructura del proyecto.

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
├── components/            # Componentes reutilizables (ej. layout protegido)
├── context/               # Contextos globales (AuthContext próximamente)
├── pages/                 # Páginas principales (Login, Register, TasksList)
│   ├── Auth/              # Componente Auth compartido entre login/register
│   ├── Login/             # Wrapper para <Auth isLogin />
│   └── Register/          # Wrapper para <Auth isLogin={false} />
├── services/              # Llamadas a ApiInterceptor externas (authService)
├── styles/                # Estilos globales y variables Sass (variables, globals)
└── App.tsx                # Router principal
```

---

## 🧠 Componentización inteligente: Auth.tsx

En vez de tener un formulario de login y otro de registro por separado, creé un solo componente `Auth.tsx` con un `prop isLogin`. Según ese valor:

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

## ❌ Lo que se evitó por simplicidad (y por qué)

- No se usó `i18n` ni variables para los textos porque:
    - El proyecto es chico y el copy está estático
    - No hay requerimiento de multi idioma
- No se usó ninguna librería de UI (MUI, Chakra, etc):
    - Para mantener el bundle liviano
    - Para tener control total sobre los estilos con Sass

---

## 📦 Consideraciones extra

- El token JWT se guarda en `localStorage` al iniciar sesión
- Se planea usar `AuthContext` para manejo global de login y rutas protegidas
- El proyecto corre en el puerto 8080 (frontend) y 8081 (backend)
- Se configuró CORS en backend Spring Boot para permitir llamadas desde Vite

---

## 💡 A mejorar o extender

- Agregar validaciones adicionales (ej: campos vacíos, uppercase)
- Agregar CAPTCHA o bloqueo por múltiples intentos
- Mejorar feedback visual con una librería de toasts (ej: react-hot-toast)
- Soporte a múltiples idiomas si es requerido
