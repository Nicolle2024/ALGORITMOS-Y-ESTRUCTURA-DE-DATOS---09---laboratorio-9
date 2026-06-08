package Libro; 

public class Libro implements Comparable<Libro> {

    private String isbn;
    private String titulo;
    private String autor;
    private int    anio;

    public Libro(String isbn, String titulo, String autor, int anio) {
        this.isbn   = isbn;
        this.titulo = titulo;
        this.autor  = autor;
        this.anio   = anio;
    }

    public String getIsbn()   { 
    	return isbn;   
    	}
    public String getTitulo() { 
    	return titulo; 
    	}
    public String getAutor()  { 
    	return autor;  
    }
    public int    getAnio()   { 
    	return anio;   
    	}

   
    @Override
    public int compareTo(Libro otro) {
        return this.isbn.compareTo(otro.isbn);
    }

    @Override
    public String toString() {
        return String.format("ISBN: %-16s | %-35s | %-20s | %d",
                isbn, titulo, autor, anio);
    }

   
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Libro)) return false;
        return this.isbn.equals(((Libro) obj).isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}