# 🛒 Tienda Online

Aplicación web de comercio electrónico con panel de administración y experiencia de compra para usuarios. Desarrollada con **React + Vite** en el frontend y **Spring Boot + JPA** en el backend, usando **SQLite** como base de datos.

---

## 📋 Descripción

La aplicación permite dos tipos de acceso desde la pantalla principal:

- **Administrador**: gestión completa de productos, categorías y pedidos.
- **Usuario**: navegación por el catálogo, añadir productos al carrito y realizar compras.

---

## 🖥️ Requisitos del sistema

| Herramienta | Versión mínima recomendada |
|-------------|---------------------------|
| Node.js | 18.x o superior |
| npm | 9.x o superior |
| Java | 17 o superior |
| Maven | 3.8 o superior |
| SQLite 3 Editor | Extensión de VS Code |

---

## 🚀 Tecnologías utilizadas

### Frontend
- [React](https://reactjs.org/) — biblioteca para construir la interfaz de usuario
- [Vite](https://vitejs.dev/) — herramienta de desarrollo y bundler

### Backend
- [Spring Boot](https://spring.io/projects/spring-boot) — framework para el desarrollo de la API REST
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) — gestión de la persistencia de datos
- [SQLite](https://www.sqlite.org/) — base de datos relacional ligera

---

## 📁 Estructura del proyecto

```
proyecto/
├── frontend/                  # Aplicación React + Vite
│   └── src/
│       ├── assets/            # Recursos estáticos (imágenes, iconos, etc.)
│       ├── components/        # Componentes de la aplicación
│       │   ├── AdminPanel.jsx      # Panel de gestión para administradores
│       │   ├── CustomerSidebar.jsx # Barra lateral del carrito de usuario
│       │   └── StoreView.jsx       # Vista principal de la tienda
│       ├── App.jsx            # Componente raíz y enrutamiento
│       └── App.css            # Estilos globales de la aplicación
│
└── backend/                   # API REST con Spring Boot
    └── src/main/java/
        ├── entities/          # Entidades JPA (modelos de datos)
        ├── controllers/       # Controladores REST
        ├── services/          # Interfaces de servicios
        │   └── impl/          # Implementaciones de los servicios
        ├── repositories/      # Repositorios JPA
        └── util/              # Datos de prueba e inicialización
```

---
## Diagrama UML


<img width="621" height="646" alt="civiShop" src="https://github.com/user-attachments/assets/8e40073d-3c5c-49b9-8e76-77914ae8dcc7" />


---
## Video Defensa

🔗 [Enlace al Vídeo de Defensa en YouTube](https://youtu.be/BjLOLYUIveQ)

### ⏱️ Marcas de Tiempo
- **00:00** - Explicación general del proyecto y objetivos.
- **01:47** - Arquitectura del software (Estructura del código y capas).
- **07:31** - Pruebas de Endpoints y API en Thunder Client.
- **10:15** - Demostración de flujos de interfaz (Frontend de Usuario y Administrador).