package Main;
import btree.BNode;
import btree.BTree;


public class Main1 {

    public static void main(String[] args) {

        // Reiniciar IDs para que los nodos empiecen en 1
        BNode.resetIdCounter();

        
        // Insertamos: 3,10,12,19,22,25,28,31,33,35,38,40,41,49,52,55,57,60,62,63,67,70,72
        BTree<Integer> tree = new BTree<>(4);
        int[] valores = {31, 12, 19, 3, 10, 22, 25, 28, 41, 57, 63, 33, 35, 38,
                         40, 49, 52, 55, 60, 62, 67, 70, 72};
        for (int v : valores) tree.insert(v);

        System.out.println("  Main 1");
        System.out.println("\nEstructura del árbol B (orden 4):");
        System.out.println(tree);

        // Caso 1: Clave en la raíz
        System.out.println("\n Buscar clave en la RAÍZ (31) ");
        boolean r1 = tree.search(31);
        System.out.println("Resultado: " + r1);

        // Caso 2: Clave en una hoja (extremo inicial)
        System.out.println("\n Clave en HOJA extremo inicial (3) ");
        boolean r2 = tree.search(3);
        System.out.println("Resultado: " + r2);

        // Caso 3: Clave en una hoja (extremo final) 
        System.out.println("\n Clave en HOJA extremo final (72) ");
        boolean r3 = tree.search(72);
        System.out.println("Resultado: " + r3);

        // Caso 4: Clave en hoja interior (la del enunciado: 52)
        System.out.println("\n Clave interior (52)");
        boolean r4 = tree.search(52);
        System.out.println("Resultado: " + r4);

        // Caso 5: Clave NO encontrada
        System.out.println("\n Clave NO encontrada (99) ");
        boolean r5 = tree.search(99);
        System.out.println("Resultado: " + r5);

        // Caso 6: Clave NO encontrada (entre dos existentes)
        System.out.println("\n Clave NO encontrada (50) ");
        boolean r6 = tree.search(50);
        System.out.println("Resultado: " + r6);
    }
}
