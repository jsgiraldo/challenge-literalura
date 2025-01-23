# � Proyecto Literalura

**Literalura** es una aplicación de consola desarrollada en Java Spring Boot que permite gestionar libros y autores de manera sencilla e interactiva. Este proyecto combina funcionalidad y simplicidad para ofrecer una experiencia eficiente en la administración de bibliotecas.

---

## � Características principales

- � **Buscar libros por título.**
- � **Explorar el listado completo de libros registrados.**
- ✒️ **Consultar todos los autores registrados.**
- � **Listar autores vivos en un año específico.**
- � **Filtrar libros por idioma.**
- ➕ **Registrar nuevos libros directamente desde la consola.**

---

## �️ Tecnologías utilizadas

El proyecto utiliza las siguientes herramientas y tecnologías:

- **Java** (v17 o superior recomendado)
- **Spring Boot**
- **Maven** para la gestión de dependencias
- **PostgreSQL** como base de datos relacional

---

## ⚙️ Configuración inicial

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/usuario/proyecto-literalura.git
   cd proyecto-literalura

2. **Configurar la base de datos:**

   Edita el archivo `application.properties` en `src/main/resources/` con tus credenciales de PostgreSQL:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_base_datos
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña

2. **Ejecutar la aplicación:**

   Desde un IDE como IntelliJ IDEA o Eclipse, ejecuta la clase principal. O utiliza Maven desde la línea de comandos:
   ```properties
   mvn spring-boot:run

### 👩‍💻 Cómo usar la aplicación

Una vez iniciada, la aplicación ofrece un menú interactivo en consola con las siguientes opciones:

```plaintext
📝 Menú principal:
1 - Buscar libro por título
2 - Listar todos los libros registrados
3 - Listar todos los autores registrados
4 - Listar autores vivos en un año específico
5 - Filtrar libros por idioma
6 - Registrar un nuevo libro
0 - Salir
```
### Ejemplo de uso
***Buscar un libro:***
```plaintext
Ingrese el nombre del libro que desea buscar: Don Quijote
```
### Resultado
```markdown
--------- LIBRO ---------
Título: Don Quijote
Autor: Cervantes Saavedra, Miguel de
Idioma: Español
Número de descargas: 12454
-------------------------
```
### Registrar un nuevo libro:
```markdown
Ingrese el título del libro: El Principito
Ingrese el idioma (es, en, fr, pt): es
Ingrese el nombre del autor: Antoine de Saint-Exupéry
Ingrese los temas (separados por coma): literatura infantil, filosofía
Ingrese el número de descargas: 15000
```
✅ Libro registrado exitosamente.

## 📈 Mejoras recientes
En esta versión, se han implementado las siguientes mejoras:

🆕 Registro de nuevos libros desde la interfaz de consola.
📄 Listas paginadas para mejorar la navegación entre resultados extensos.
✅ Validación robusta para entradas de usuario, como idiomas y formatos.
🔍 Optimización en las búsquedas de libros y autores.