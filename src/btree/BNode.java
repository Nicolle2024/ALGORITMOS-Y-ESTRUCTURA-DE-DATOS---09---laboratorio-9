package btree;

import java.util.ArrayList;

public class BNode<E> {

    // Contador estático para asignar IDs únicos a cada nodo creado
    private static int idCounter = 1;

    protected int idNode;              // Identificador único del nodo
    protected ArrayList<E> keys;       // Lista de claves almacenadas
    protected ArrayList<BNode<E>> childs; // Lista de punteros a hijos
    protected int count;               // Cantidad actual de claves en el nodo

   
    public BNode(int n) {
        this.idNode = idCounter++;  // Asigna ID único y luego incrementa
        this.keys = new ArrayList<E>(n);
        this.childs = new ArrayList<BNode<E>>(n + 1);
        this.count  = 0;

        // Inicializar las listas con null para permitir set() por índice
        for (int i = 0; i < n; i++) {
            this.keys.add(null);
        }
        for (int i = 0; i <= n; i++) {
            this.childs.add(null);
        }
    }

    public boolean nodeFull(int maxKeys) {
        return this.count >= maxKeys;
    }

   
    public boolean nodeEmpty(int minKeys) {
        return this.count < minKeys;
    }

    
    public boolean searchNode(E cl, int[] pos, java.util.Comparator<E> comp) {
        pos[0] = 0;
        // Avanzar mientras la clave actual sea menor que cl
        while (pos[0] < this.count && comp.compare(cl, this.keys.get(pos[0])) > 0) {
            pos[0]++;
        }
        // Verificar si la posición actual coincide con la clave buscada
        if (pos[0] < this.count && comp.compare(cl, this.keys.get(pos[0])) == 0) {
            return true; // Clave encontrada
        }
        return false;    // No encontrada; pos[0] = índice del hijo a descender
    }

   
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < this.count; i++) {
            sb.append(this.keys.get(i));
            if (i < this.count - 1) sb.append(", ");
        }
        sb.append(")");
        return "Id.Nodo: " + idNode + " -> Claves: " + sb;
    }

   
    public static void resetIdCounter() {
        idCounter = 1;
    }
}