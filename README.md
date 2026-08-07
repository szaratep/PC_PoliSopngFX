# PoliSongFX

Aplicación de escritorio para la compra y venta de música (canciones, discos MP3 y vinilos), desarrollada en Java con JavaFX y MySQL como proyecto académico.

Simula un marketplace musical con tres roles distintos: **usuarios** que compran y arman playlists, **proveedores** que registran y gestionan sus productos, y **administradores** que supervisan inventario y usuarios.

## Funcionalidades

**Usuario**
- Registro e inicio de sesión
- Explorar catálogo y buscar productos
- Carrito de compras y checkout
- Historial de compras
- Crear y gestionar playlists
- Calificar productos

**Proveedor**
- Registrar canciones, discos MP3 y vinilos
- Editar y gestionar sus productos
- Ver pedidos pendientes

**Administrador**
- Gestión de inventario general
- Gestión de usuarios y proveedores
- Edición de productos del sistema

## Stack técnico

- **Java 21**
- **JavaFX** (interfaz gráfica, FXML + CSS)
- **MySQL** (persistencia de datos)
- **JDBC** (driver `mysql-connector-j`)
- Patrón **MVC + DAO**, con una capa adicional de lógica de negocio (`negocio`)

## Arquitectura

El proyecto está organizado en capas:

```
src/
├── application/          # Punto de entrada de la app (Main.java) y vista principal
└── co/edu/poli/
    ├── controller/        # Controladores de cada vista FXML
    ├── view/               # Archivos .fxml (interfaz gráfica)
    ├── negocio/            # Lógica de negocio (managers): auth, carrito, catálogo, pedidos, playlists...
    ├── datos/              # Acceso a datos (DAOs) y conexión a BD
    └── model/              # Entidades del dominio: usuario, proveedor, cancion, pedido, playlist...
```

## Requisitos previos

- JDK 21
- JavaFX SDK (o el plugin de JavaFX para Eclipse, `e(fx)clipse`)
- MySQL Server
- Eclipse IDE (el proyecto incluye configuración `.project` / `.classpath`)

## Instalación y configuración

1. Clona el repositorio:
   ```bash
   git clone https://github.com/szaratep/PC_PoliSopngFX.git
   ```
2. Crea una base de datos MySQL llamada `polisongdb`.
3. Configura tus credenciales de conexión en `co.edu.poli.datos.DBConnection` (usuario, contraseña y URL). **No subas tus credenciales reales al repo** — considera moverlas a un archivo `.properties` ignorado por git.
4. Importa el proyecto en Eclipse como *Existing Projects into Workspace*.
5. Asegúrate de tener instalado el plugin de JavaFX para Eclipse.
6. Ejecuta `application.Main` como Java Application.

## Autores

- [@szaratep](https://github.com/szaratep)
- Compañero de equipo — proyecto desarrollado en conjunto para la Politécnico Grancolombiano

## Estado del proyecto

Proyecto académico, desarrollado como práctica de POO, bases de datos y desarrollo de interfaces con JavaFX.
