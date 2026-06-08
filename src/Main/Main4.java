package Main;

import btree.BNode;
import java.io.IOException;

import Biblioteca.Biblioteca;
import Libro.Libro;

public class Main4 {

    public static void main(String[] args) {

        BNode.resetIdCounter();

        Biblioteca bib = new Biblioteca();

        // Cargar libros
        System.out.println("\n A. Cargando desde biblioteca.txt ");
        try {
            bib.cargarDesdeArchivo("biblioteca.txt");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            System.out.println("Cargando datos de ejemplo manualmente...");
            cargarManual(bib);
        }

        // Mostrar árbol
        System.out.println("\n B. Estructura interna del Árbol B ");
        bib.mostrarArbol();

        // Mostrar estadísticas
        System.out.println(" C. Estadísticas ");
        System.out.println("Altura del árbol: " + bib.altura() + " niveles");
        System.out.println("Total de libros:  " + bib.totalLibros());

        // Mostrar libros ordenados
        System.out.println("\n D. Libros ordenados por ISBN ");
        bib.mostrarLibrosOrdenados();

        // Buscar libros
        System.out.println("E. Búsqueda por ISBN ");

        System.out.println();
        bib.buscarPorISBN("9780201633610");

        System.out.println();
        bib.buscarPorISBN("9780132350884");

        System.out.println();
        bib.buscarPorISBN("9999999999999");

        // Agregar libro
        System.out.println("\n F. Agregar nuevo libro ");
        Libro nuevo = new Libro(
                "9780743273565",
                "The Great Gatsby",
                "F. Scott Fitzgerald",
                1925);

        bib.agregarLibro(nuevo);

        System.out.println("Libro agregado: " + nuevo);
        System.out.println("Total ahora: " + bib.totalLibros());

        // Probar duplicado
        System.out.println("\n G. Intentar ISBN duplicado ");
        Libro dup = new Libro(
                "9780132350884",
                "Copia de Clean Code",
                "Otro",
                2020);

        bib.agregarLibro(dup);

        // Eliminar libro
        System.out.println("\n H. Eliminar libro (Design Patterns) ");
        bib.eliminarLibro("9780201633610");

        System.out.println("Total después de eliminar: " + bib.totalLibros());
        System.out.println("Árbol tras eliminación:");
        bib.mostrarArbol();

        // Ventajas del Árbol B
        System.out.println(" I. ¿Por qué Árbol B para bibliotecas/BD? ");
        System.out.println("""
                • Cada nodo guarda varios ISBN.
                • Las búsquedas son rápidas.
                • El árbol siempre está balanceado.
                • Permite búsquedas por rango.
                • Muy usado en bases de datos.
                """);
    }

    // Datos de ejemplo
    private static void cargarManual(Biblioteca bib) {
        bib.agregarLibro(new Libro("9780132350884", "Clean Code", "Robert Martin", 2008));
        bib.agregarLibro(new Libro("9780134494166", "Clean Architecture", "Robert Martin", 2017));
        bib.agregarLibro(new Libro("9780201633610", "Design Patterns", "GoF", 1994));
        bib.agregarLibro(new Libro("9780596009205", "Head First Java", "Kathy Sierra", 2005));
        bib.agregarLibro(new Libro("9780134685991", "Effective Java", "Joshua Bloch", 2018));
        bib.agregarLibro(new Libro("9780262033848", "Intro to Algorithms", "Cormen", 2009));

        System.out.println(" 6 libros cargados manualmente.");
    }
}