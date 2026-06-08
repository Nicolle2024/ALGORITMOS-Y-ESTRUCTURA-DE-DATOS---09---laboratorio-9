package Biblioteca;

import btree.BTree;
import java.io.*;

import Libro.Libro;

public class Biblioteca {

    private BTree<Libro> arbol;

    public Biblioteca() {
        this.arbol = new BTree<>(4);
    }

    public Biblioteca(int orden) {
        this.arbol = new BTree<>(orden);
    }

    public void agregarLibro(Libro libro) {
        arbol.insert(libro);
    }

    public void eliminarLibro(String isbn) {
        Libro proxy = new Libro(isbn, "", "", 0);
        arbol.remove(proxy);
    }

    public void mostrarArbol() {
        System.out.println(arbol);
    }

    public boolean buscarPorISBN(String isbn) {
        Libro proxy = new Libro(isbn, "", "", 0);
        System.out.println("Buscando ISBN: " + isbn);
        return arbol.searchWithPath(proxy);
    }

    public void mostrarLibrosOrdenados() {
        System.out.println("\n Libros ordenados por ISBN ");
        arbol.inOrder();
    }

    public int altura() {
        return arbol.height();
    }

    public int totalLibros() {
        return arbol.countKeys();
    }

    public void cargarDesdeArchivo(String ruta) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(ruta));

        int orden = Integer.parseInt(br.readLine().trim());
        this.arbol = new BTree<>(orden);

        String linea;
        int contador = 0;

        while ((linea = br.readLine()) != null) {
            linea = linea.trim();

            if (linea.isEmpty())
                continue;

            String[] partes = linea.split(",", 4);

            if (partes.length < 4) {
                System.out.println("Línea ignorada (formato inválido): " + linea);
                continue;
            }

            Libro libro = new Libro(
                    partes[0].trim(),
                    partes[1].trim(),
                    partes[2].trim(),
                    Integer.parseInt(partes[3].trim())
            );

            arbol.insert(libro);
            contador++;
        }

        br.close();

        System.out.println( contador + " libros cargados desde: " + ruta);
    }
}