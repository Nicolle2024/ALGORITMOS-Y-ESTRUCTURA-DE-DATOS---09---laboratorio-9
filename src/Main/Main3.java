package Main; 

import btree.BNode;
import btree.BTree;


public class Main3 {

    public static void main(String[] args) {

        BNode.resetIdCounter();
        BTree<Integer> tree = buildTree4();

        System.out.println("\nÁrbol inicial (orden 4):");
        System.out.println(tree);

        // hoja con más del mínimo de claves 
        eliminar(tree, 25, "Hoja con más del mínimo");
        eliminar(tree, 10, "Posible redistribución");
        eliminar(tree, 50, "Nodo interno (sucesor)");
        eliminar(tree, 70, "Posible fusión");
        eliminar(tree, 27, "Continuación");
        eliminar(tree, 5, "Fusión posible");
        eliminar(tree, 99, "Clave inexistente");

        System.out.println("\n Eliminar de árbol vacío ");
        BTree<Integer> emptyTree = new BTree<>(4);
        emptyTree.remove(10);
    }

   private static BTree<Integer> buildTree4() {
        BTree<Integer> t = new BTree<>(4);
        int[] ins = {50, 20, 70, 10, 30, 60, 80, 25, 27, 26, 65, 75, 85, 5};
        for (int v : ins) t.insert(v);
        return t;
    }

    private static void eliminar(BTree<Integer> tree, int clave, String desc) {
        System.out.println("\n Eliminar " + clave + " [" + desc + "] ");
        tree.remove(clave);
        System.out.println(tree);
    }
}
