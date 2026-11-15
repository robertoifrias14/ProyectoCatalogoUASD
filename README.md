# Catálogo de Carreras de la UASD

Aplicación web para consultar las carreras de la UASD.
Los datos se guardan en MySQL, el backend está hecho en Java y el frontend en HTML, CSS y JavaScript.

## Estructura del proyecto

```
uasd-catalogo/
├── backend/
│   ├── lib/        # Driver JDBC de MySQL (.jar)
│   ├── out/        # Las clases que se generan al compilar
│   └── src/        # Código Java con todos los objetos
├── database/
│   └── schema.sql  # Base de datos
└── frontend/
    ├── css/styles.css
    ├── js/app.js
    └── index.html
```

## Requisitos

| Herramienta | Versión | Para qué sirve |
|---|---|---|
| JDK (Java Development Kit) | 21 o superior | Compilar y ejecutar el backend |
| MySQL Server | 8.0 o superior | Base de datos |
| MySQL Workbench | Cualquiera reciente | Lo recomendamos |
| Visual Studio Code | 
| Extension Pack for Java para VS Code |
| Navegador web | 

El driver de MySQL (`mysql-connector-j-26.10.0.jar`) ya está incluido en `backend/lib/`.

Para comprobar las versiones instaladas:

```
java -version
javac -version
mysql --version
```

## Instalación y ejecución

### 1. Clonar el repositorio

```
git clone <Link-del-repositorio>
cd uasd-catalogo
```

### 2. Encender MySQL

### 3. Crear la base de datos

**con MySQL Workbench**
1. Abre la conexión **Local instance MySQL80** e ingresa la contraseña de `root`.
2. Ve a **File → Open SQL Script** y selecciona `database/schema.sql`.
3. Presiona el rayito ⚡ para ejecutarlo.

Para verificar:

```sql
USE uasd_catalogo;
SELECT * FROM carreras;
```
Deben aparecer 8 escuelas y 25 carreras.

### 4. Configurar la contraseña de MySQL

El backend se conecta con el usuario `root`. La contraseña se lee de la variable de entorno `DB_PASSWORD`.

**Windows (PowerShell):**
```
$env:DB_PASSWORD="tu_contraseña"
```

**Mac/Linux:**
```
export DB_PASSWORD="tu_contraseña"
```

> La variable solo vale para esa terminal. Ejecuta el paso 5 en la misma ventana.
> Si usas otro usuario, puerto o nombre de base de datos, edita `backend/src/Database.java`.

### 5. Compilar y ejecutar el backend

Ejecuta estos comandos **desde la carpeta raíz `uasd-catalogo`** (no desde `backend`).

**Windows:**
```
javac -cp "backend/lib/mysql-connector-j-26.10.0.jar" -d backend/out backend/src/*.java
java -cp "backend/out;backend/lib/mysql-connector-j-26.10.0.jar" Main
```

Si todo va bien, la terminal mostrará:

```
Servidor listo en http://localhost:8080
```

Deja esa terminal abierta: si la cierras, el servidor se apaga.

### 6. Abrir la aplicación

Abre en el navegador:

```
http://localhost:8080
```

Selecciona una escuela y presiona **Ver carreras**.

## Tecnologías

- Java (servidor `com.sun.net.httpserver`, JDBC)
- MySQL 8
- HTML, CSS y JavaScript (fetch API)
