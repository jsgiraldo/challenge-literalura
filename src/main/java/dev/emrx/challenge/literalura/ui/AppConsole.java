package dev.emrx.challenge.literalura.ui;

import dev.emrx.challenge.literalura.domain.AutorDto;
import dev.emrx.challenge.literalura.domain.LibroDto;
import dev.emrx.challenge.literalura.model.Datos;
import dev.emrx.challenge.literalura.model.DatosLibro;
import dev.emrx.challenge.literalura.model.entity.Autor;
import dev.emrx.challenge.literalura.model.entity.Idioma;
import dev.emrx.challenge.literalura.model.entity.Libro;
import dev.emrx.challenge.literalura.service.AutorService;
import dev.emrx.challenge.literalura.service.provider.ConsumoAPI;
import dev.emrx.challenge.literalura.service.provider.ConvierteDatos;
import dev.emrx.challenge.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


@Component
public class AppConsole {

    private static final String URL_BASE = "https://gutendex.com/books/";

    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = ConsumoAPI.getInstance();
    private ConvierteDatos conversor = ConvierteDatos.getInstance();

    public void ejecutarOperaciones() {
        int operacion = -1;
        while (operacion != 0) {
            mostrarMenu();
            try {
                operacion = teclado.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Opciones válidas: 0, 1, 2, 3, 4, 5");
                continue;
            } finally {
                teclado.nextLine();
            }
            switch (operacion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAnio();
                    break;
                case 5:
                    mostrarIdiomas();
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    registrarNuevoLibro();
                    break;
            }
            
        }
    }

    public void mostrarMenu() {
        ConsoleHelper.mostrarMensaje("""
            Menu de opciones:
            1- Buscar libro por título
            2- Listar libros registrados
            3- Listar autores registrados
            4- Listar autores vivos en un determinado año
            5- Listar libros por idioma
            6- Registrar un nuevo libro
            0- Salir
            Elija la opción a través de su número:
        """);
    }
    

    public void mostrarIdiomas() {
        for (Idioma idioma : Idioma.values()) {
            System.out.println(idioma.mostrar());
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        String titulo = teclado.nextLine();

        String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + titulo.replace(" ", "+"));

        DatosLibro libroBuscado = conversor.obtenerDatos(json, Datos.class).libros().get(0);
        LibroDto libroNuevo = libroService.guardarLibro(libroBuscado);
        System.out.println(libroNuevo);
    }

    private void listarLibrosRegistrados() {
        List<LibroDto> librosRegistrados = libroService.obtenerLibros();
        listarLibrosConPaginacion(librosRegistrados);
    }
    

    private void listarAutoresRegistrados() {
        List<AutorDto> autoresRegistrados = autorService.obtenerAutores();
        autoresRegistrados.forEach(System.out::println);
    }

    private void listarAutoresVivosPorAnio() {
        System.out.println("Ingrese el año de los autor(es) vivos que desea buscar: ");
        int anio = 0;
        try {
            anio = teclado.nextInt();
            List<AutorDto> autoresVivos = autorService.obtenerAutoresVivosPorAnio(anio);
            autoresVivos.forEach(System.out::println);
        } catch (InputMismatchException e) {
            System.out.println("Ingreso un año inválido. No se pudo realizar la búsqueda.");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Escriba el idioma deseado: ");
        String abreviatura = teclado.nextLine();
        try {
            Idioma idioma = Idioma.fromString(abreviatura);
            List<LibroDto> librosPorIdioma = libroService.obtenerLibrosPorIdioma(idioma);
            librosPorIdioma.forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            ConsoleHelper.mostrarMensaje("El idioma ingresado no es válido.");
        }        

        List<LibroDto> librosPorIdioma = libroService.obtenerLibrosPorIdioma(idioma);
        librosPorIdioma.forEach(System.out::println);
    }

    private void listarLibrosConPaginacion(List<LibroDto> libros) {
        final int PAGE_SIZE = 5;
        int totalPaginas = (int) Math.ceil((double) libros.size() / PAGE_SIZE);
    
        for (int pagina = 0; pagina < totalPaginas; pagina++) {
            ConsoleHelper.mostrarMensaje("Página " + (pagina + 1) + " de " + totalPaginas);
            libros.stream()
                    .skip(pagina * PAGE_SIZE)
                    .limit(PAGE_SIZE)
                    .forEach(System.out::println);
    
            if (pagina < totalPaginas - 1) {
                String opcion = ConsoleHelper.leerLinea("Presione Enter para ver la siguiente página o 'q' para salir.");
                if ("q".equalsIgnoreCase(opcion)) {
                    break;
                }
            }
        }
    }
    private void registrarNuevoLibro() {
        String titulo = ConsoleHelper.leerLinea("Ingrese el título del libro:");
        String idioma = ConsoleHelper.leerLinea("Ingrese el idioma (es, en, fr, pt):");
        String autor = ConsoleHelper.leerLinea("Ingrese el nombre del autor:");
        String temas = ConsoleHelper.leerLinea("Ingrese los temas (separados por coma):");
        int descargas = ConsoleHelper.leerNumero("Ingrese el número de descargas:");
    
        try {
            LibroDto nuevoLibro = new LibroDto(
                titulo,
                temas,
                idioma,
                descargas,
                autor
            );
            libroService.guardarLibro(nuevoLibro);
            ConsoleHelper.mostrarMensaje("Libro registrado exitosamente.");
        } catch (Exception e) {
            ConsoleHelper.mostrarMensaje("Ocurrió un error al registrar el libro: " + e.getMessage());
        }
    }
    
}

