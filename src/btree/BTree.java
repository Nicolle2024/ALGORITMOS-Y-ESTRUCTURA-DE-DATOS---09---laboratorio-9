package btree;


public class BTree<E extends Comparable<E>> {

    protected BNode<E> root;   // Nodo raíz del árbol
    protected int orden;       // Orden del árbol (máximo de hijos por nodo)
    private boolean up;        // indica si hay promoción pendiente tras división
    private BNode<E> nDes;     // Nodo nuevo creado en una división (nodo derecho)

    public BTree(int orden) {
        this.orden = orden;
        this.root  = null;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    
    public void insert(E cl) {
        up = false;
        E mediana = push(this.root, cl);
        if (up) {
            // La raíz se dividió: crear nueva raíz con la mediana
            BNode<E> pnew = new BNode<>(this.orden);
            pnew.count = 1;
            pnew.keys.set(0, mediana);
            pnew.childs.set(0, this.root);
            pnew.childs.set(1, nDes);
            this.root = pnew;
        }
    }

   
    private E push(BNode<E> current, E cl) {
        int[] pos = new int[1];
        E mediana;

        if (current == null) {
            // Llegamos a un nodo nulo: la clave debe insertarse aquí
            up   = true;
            nDes = null;
            return cl;
        } else {
            boolean found = current.searchNode(cl, pos, Comparable::compareTo);
            if (found) {
                System.out.println("Item duplicado: " + cl);
                up = false;
                return null;
            }
            // Descender por el hijo correspondiente
            mediana = push(current.childs.get(pos[0]), cl);
            if (up) {
                if (current.nodeFull(this.orden - 1)) {
                    // Nodo lleno: dividir y promover la mediana
                    mediana = dividedNode(current, mediana, pos[0]);
                } else {
                    // Nodo con espacio: insertar directamente
                    up = false;
                    putNode(current, mediana, nDes, pos[0]);
                }
            }
            return mediana;
        }
    }

    
    private void putNode(BNode<E> current, E cl, BNode<E> rd, int k) {
        for (int i = current.count - 1; i >= k; i--) {
            current.keys.set(i + 1, current.keys.get(i));
            current.childs.set(i + 2, current.childs.get(i + 1));
        }
        current.keys.set(k, cl);
        current.childs.set(k + 1, rd);
        current.count++;
    }

   
    private E dividedNode(BNode<E> current, E cl, int k) {
        BNode<E> rd = nDes;
        int posMdna;

        // Posición de la mediana según dónde cae la nueva clave
        posMdna = (k <= this.orden / 2) ? this.orden / 2 : this.orden / 2 + 1;
        nDes = new BNode<>(this.orden);

        // Copiar claves e hijos de la mitad derecha al nuevo nodo
        for (int i = posMdna; i < this.orden - 1; i++) {
            nDes.keys.set(i - posMdna, current.keys.get(i));
            nDes.childs.set(i - posMdna + 1, current.childs.get(i + 1));
        }
        nDes.count   = (this.orden - 1) - posMdna;
        current.count = posMdna;

        // Insertar la nueva clave en el nodo correcto
        if (k <= this.orden / 2)
            putNode(current, cl, rd, k);
        else
            putNode(nDes, cl, rd, k - posMdna);

        // La mediana es la última clave del nodo izquierdo
        E median = current.keys.get(current.count - 1);
        nDes.childs.set(0, current.childs.get(current.count));
        current.count--;
        return median;
    }

    // writeTree
    
    @Override
    public String toString() {
        if (isEmpty()) return "BTree está vacío...";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-25s %-10s %-15s%n",
                "Id.Nodo", "Claves Nodo", "Id.Padre", "Id.Hijos"));
        sb.append("─".repeat(65)).append("\n");
        writeTree(this.root, null, sb);
        return sb.toString();
    }

  
    private void writeTree(BNode<E> current, BNode<E> parent, StringBuilder sb) {
        if (current == null) return;

        // Padre
        String padreStr = (parent == null) ? "--" : String.valueOf(parent.idNode);

        // Hijos
        StringBuilder hijosStr = new StringBuilder("[");
        boolean tieneHijos = false;
        for (int i = 0; i <= current.count; i++) {
            if (current.childs.get(i) != null) {
                hijosStr.append(current.childs.get(i).idNode).append(", ");
                tieneHijos = true;
            }
        }
        if (tieneHijos) {
            hijosStr.setLength(hijosStr.length() - 2);
            hijosStr.append("]");
        } else {
            hijosStr = new StringBuilder("--");
        }

        // Claves
        StringBuilder keysStr = new StringBuilder("(");
        for (int i = 0; i < current.count; i++) {
            keysStr.append(current.keys.get(i));
            if (i < current.count - 1) keysStr.append(", ");
        }
        keysStr.append(")");

        sb.append(String.format("%-10d %-25s %-10s %-15s%n",
                current.idNode, keysStr, padreStr, hijosStr));

        // Recursión sobre los hijos (de izquierda a derecha)
        for (int i = 0; i <= current.count; i++) {
            writeTree(current.childs.get(i), current, sb);
        }
    }

     // EJERCICIO 01 — search(E cl)
   
    public boolean search(E cl) {
        return searchRec(this.root, cl);
    }

    private boolean searchRec(BNode<E> current, E cl) {
        if (current == null) return false;           // Clave no existe en el árbol

        int[] pos = new int[1];
        boolean found = current.searchNode(cl, pos, Comparable::compareTo);

        if (found) {
            System.out.println("→ " + cl + " se encuentra en el nodo "
                    + current.idNode + " en la posición " + pos[0]);
            return true;
        }
        // Descender por el hijo que corresponde (pos[0])
        return searchRec(current.childs.get(pos[0]), cl);
    }

    // EJERCICIO 02 — searchRange(E min, E max)
   

    public void searchRange(E min, E max) {
        if (min.compareTo(max) > 0) {
            System.out.println("Rango inválido: min (" + min + ") > max (" + max + ")");
            return;
        }
        System.out.print("Claves en rango [" + min + ", " + max + "]: ");
        boolean[] found = {false};
        searchRangeRec(this.root, min, max, found);
        if (!found[0]) System.out.print("(ninguna clave en ese rango)");
        System.out.println();
    }

    
    private void searchRangeRec(BNode<E> current, E min, E max, boolean[] found) {
        if (current == null) return;

        for (int i = 0; i < current.count; i++) {
            E key = current.keys.get(i);

            // Descender por hijo izquierdo de la clave i solo si puede haber claves >= min allí
            // (el subárbol childs[i] contiene claves menores que keys[i])
            if (key.compareTo(min) >= 0) {
                // El subárbol izquierdo puede tener claves >= min
                searchRangeRec(current.childs.get(i), min, max, found);
            }

            // Procesar la clave actual si está dentro del rango
            if (key.compareTo(min) >= 0 && key.compareTo(max) <= 0) {
                System.out.print(key + " ");
                found[0] = true;
            }

            // Si la clave supera max, no tiene sentido continuar a la derecha
            if (key.compareTo(max) > 0) return;
        }
        // Descender por el hijo más a la derecha (mayores que la última clave)
        searchRangeRec(current.childs.get(current.count), min, max, found);
    }

    // EJERCICIO 03 — remove(E cl)
   
    public void remove(E cl) {
        if (isEmpty()) {
            System.out.println("El árbol está vacío.");
            return;
        }

        delete(root, cl);

        if (root != null && root.count == 0)
            root = root.childs.get(0);
    }

    private void delete(BNode<E> node, E cl) {
        int[] pos = new int[1];
        boolean found = node.searchNode(cl, pos, Comparable::compareTo);

        if (isLeaf(node)) {
            if (found)
                removeFromNode(node, pos[0]);
            else
                System.out.println("Clave " + cl + " no encontrada.");
            return;
        }

        if (found) {
            replaceWithSuccessor(node, pos[0]);
            checkUnderflow(node, pos[0] + 1);
        } else {
            delete(node.childs.get(pos[0]), cl);
            checkUnderflow(node, pos[0]);
        }
    }

    private boolean isLeaf(BNode<E> node) {
        return node.childs.get(0) == null;
    }

    private void removeFromNode(BNode<E> node, int pos) {
        for (int i = pos; i < node.count - 1; i++) {
            node.keys.set(i, node.keys.get(i + 1));
            node.childs.set(i + 1, node.childs.get(i + 2));
        }

        node.keys.set(node.count - 1, null);
        node.childs.set(node.count, null);
        node.count--;
    }

    private void replaceWithSuccessor(BNode<E> node, int pos) {
        BNode<E> suc = node.childs.get(pos + 1);

        while (suc.childs.get(0) != null)
            suc = suc.childs.get(0);

        E key = suc.keys.get(0);

        node.keys.set(pos, key);

        delete(node.childs.get(pos + 1), key);
    }

    private int minKeys() {
        return (int)Math.ceil(orden / 2.0) - 1;
    }

    private void checkUnderflow(BNode<E> parent, int idx) {
        BNode<E> child = parent.childs.get(idx);

        if (child == null || child.count >= minKeys())
            return;

        if (idx > 0 &&
            parent.childs.get(idx - 1).count > minKeys()) {
            redistributeFromLeft(parent, idx);
            return;
        }

        if (idx < parent.count &&
            parent.childs.get(idx + 1) != null &&
            parent.childs.get(idx + 1).count > minKeys()) {
            redistributeFromRight(parent, idx);
            return;
        }

        mergeNodes(parent, idx > 0 ? idx - 1 : idx);
    }

    private void redistributeFromLeft(BNode<E> parent, int idx) {
        BNode<E> child = parent.childs.get(idx);
        BNode<E> left = parent.childs.get(idx - 1);

        for (int i = child.count; i > 0; i--) {
            child.keys.set(i, child.keys.get(i - 1));
            child.childs.set(i + 1, child.childs.get(i));
        }

        child.childs.set(1, child.childs.get(0));
        child.keys.set(0, parent.keys.get(idx - 1));
        child.childs.set(0, left.childs.get(left.count));

        parent.keys.set(idx - 1, left.keys.get(left.count - 1));

        child.count++;
        left.keys.set(left.count - 1, null);
        left.childs.set(left.count, null);
        left.count--;
    }

    private void redistributeFromRight(BNode<E> parent, int idx) {
        BNode<E> child = parent.childs.get(idx);
        BNode<E> right = parent.childs.get(idx + 1);

        child.keys.set(child.count, parent.keys.get(idx));
        child.childs.set(child.count + 1, right.childs.get(0));
        child.count++;

        parent.keys.set(idx, right.keys.get(0));

        for (int i = 0; i < right.count - 1; i++) {
            right.keys.set(i, right.keys.get(i + 1));
            right.childs.set(i, right.childs.get(i + 1));
        }

        right.childs.set(right.count - 1,
                         right.childs.get(right.count));

        right.keys.set(right.count - 1, null);
        right.childs.set(right.count, null);
        right.count--;
    }

    private void mergeNodes(BNode<E> parent, int k) {
        BNode<E> left = parent.childs.get(k);
        BNode<E> right = parent.childs.get(k + 1);

        left.keys.set(left.count, parent.keys.get(k));
        left.childs.set(left.count + 1, right.childs.get(0));
        left.count++;

        for (int i = 0; i < right.count; i++) {
            left.keys.set(left.count, right.keys.get(i));
            left.childs.set(left.count + 1, right.childs.get(i + 1));
            left.count++;
        }

        for (int i = k; i < parent.count - 1; i++) {
            parent.keys.set(i, parent.keys.get(i + 1));
            parent.childs.set(i + 1, parent.childs.get(i + 2));
        }

        parent.keys.set(parent.count - 1, null);
        parent.childs.set(parent.count, null);
        parent.count--;
    }

  
    // Métodos para ejercicio 04
    
    
    public int height() {
        if (isEmpty()) return 0;
        int h = 0;
        BNode<E> current = this.root;
        while (current != null) {
            h++;
            current = current.childs.get(0);
        }
        return h;
    }

  
    public int countKeys() {
        return countKeysRec(this.root);
    }

    private int countKeysRec(BNode<E> current) {
        if (current == null) return 0;
        int total = current.count;
        for (int i = 0; i <= current.count; i++) {
            total += countKeysRec(current.childs.get(i));
        }
        return total;
    }

   
    public boolean searchWithPath(E cl) {
        System.out.print("Camino: ");
        return searchPathRec(this.root, cl);
    }

    private boolean searchPathRec(BNode<E> current, E cl) {
        if (current == null) {
            System.out.println("\n  → Clave no encontrada.");
            return false;
        }
        System.out.print("[Nodo " + current.idNode + "] ");
        int[] pos = new int[1];
        boolean found = current.searchNode(cl, pos, Comparable::compareTo);
        if (found) {
            System.out.println("\n  → Encontrado en nodo " + current.idNode
                    + " posición " + pos[0]);
            return true;
        }
        return searchPathRec(current.childs.get(pos[0]), cl);
    }


    public void inOrder() {
        inOrderRec(this.root);
        System.out.println();
    }

    private void inOrderRec(BNode<E> current) {
        if (current == null) return;
        for (int i = 0; i < current.count; i++) {
            inOrderRec(current.childs.get(i));
            System.out.println("  " + current.keys.get(i));
        }
        inOrderRec(current.childs.get(current.count));
    }
}