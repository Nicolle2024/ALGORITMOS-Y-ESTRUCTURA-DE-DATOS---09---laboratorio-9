package Main; 

import btree.BNode;
import btree.BTree;

public class Main2{

    public static void main(String[] args) {

        BNode.resetIdCounter();

        // 10,15,20,25,30,35,40,45 (orden 4)
        BTree<Integer> tree = new BTree<>(4);
        int[] valores = {10, 15, 20, 25, 30, 35, 40, 45};
        for (int v : valores) tree.insert(v);

        System.out.println("\nEstructura del árbol B (orden 4):");
        System.out.println(tree);

        // Caso 1: Rango válido existente (del enunciado) 
        System.out.println(" Rango existente [20, 40] ");
        tree.searchRange(20, 40);

        // Caso 2: Rango inválido (min > max)
        System.out.println("\n Rango inválido (min > max): [40, 20]");
        tree.searchRange(40, 20);

        // Caso 3: Rango sin claves dentro del árbol 
        System.out.println("\n Rango inexistente [50, 100] ");
        tree.searchRange(50, 100);

        // Caso 4: Rango que abarca todo el árbol─
        System.out.println("\n Rango completo [10, 45]");
        tree.searchRange(10, 45);

        // Caso 5: Rango de un solo elemento
        System.out.println("\nRango de un solo elemento [30, 30]");
        tree.searchRange(30, 30);

        // Caso 6: Rango que cae entre dos claves existentes
        System.out.println("\nRango entre claves no existentes [11, 14]");
        tree.searchRange(11, 14);
    }
}
