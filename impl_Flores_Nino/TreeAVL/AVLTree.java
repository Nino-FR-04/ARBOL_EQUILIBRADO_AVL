package impl_Flores_Nino.TreeAVL;

import impl_Flores_Nino.BinarySearchTree.LinkedBST;
import impl_Flores_Nino.Excepciones.ExceptionItemDuplicated;
import impl_Flores_Nino.Nodes.NodeAVL;

/**
 * Clase que implementa un árbol AVL, que es un árbol binario de búsqueda auto-balanceado.
 * Extiende la clase LinkedBST y utiliza nodos de tipo NodeAVL para mantener el balance.
 * 
 * @param E tipo de dato comparable almacenado en el árbol.
 */
public class AVLTree<E extends Comparable<E>> extends LinkedBST<E> {

    // Flag para indicar si cambió la altura
    private boolean height;

    // Constructor
    public AVLTree() {
        super();
        this.height = false;
    }

    /**
     * Inserta un nuevo elemento en el árbol AVL.
     * 
     * @param data el elemento a insertar.
     * @throws ExceptionItemDuplicated si el elemento ya existe en el árbol.
     */
    @Override
    public void insert(E data) throws ExceptionItemDuplicated {
        this.root = insertRecursivo((NodeAVL<E>) this.root, data);
    }

    /**
     * Método recursivo que inserta un nuevo nodo y realiza el balanceo necesario.
     * 
     * @param node el nodo actual en la recursión.
     * @param data el dato a insertar.
     * @return el nodo actualizado tras la inserción y balanceo.
     * @throws ExceptionItemDuplicated si el elemento ya existe.
     */
    private NodeAVL<E> insertRecursivo(NodeAVL<E> node, E data) throws ExceptionItemDuplicated {
        if(node == null) {
            this.height = true;
            return new NodeAVL<E>(data);
        }

        int cmp = data.compareTo(node.getData());

        if(cmp < 0) {
            node.setLeft(insertRecursivo((NodeAVL<E>)node.getLeft(), data));
            
            if(this.height){
                node.setFactEquilibrio(node.getFactEquilibrio() - 1);
            }
            
        }else if(cmp > 0) {
            node.setRight(insertRecursivo((NodeAVL<E>)node.getRight(), data));
            
            if (this.height) {
                node.setFactEquilibrio(node.getFactEquilibrio() + 1);
            }

        }else {
            throw new ExceptionItemDuplicated("Item duplicado");
        }

        if (this.height) {
            int fe = node.getFactEquilibrio();

            // Caso 1: Desbalance a la izquierda
            if (fe == -2) {
                NodeAVL<E> hijoIzq = (NodeAVL<E>) node.getLeft();
                if (hijoIzq.getFactEquilibrio() <= 0) {
                    node = rotateSR(node); // Rotación simple derecha
                } else {
                    node = rotateDR(node); // Rotación doble derecha (izquierda-derecha)
                }
                this.height = false;
            }

            // Caso 2: Desbalance a la derecha
            else if (fe == 2) {
                NodeAVL<E> hijoDer = (NodeAVL<E>) node.getRight();
                if (hijoDer.getFactEquilibrio() >= 0) {
                    node = rotateSL(node); // Rotación simple izquierda
                } else {
                    node = rotateDL(node); // Rotación doble izquierda (derecha-izquierda)
                }
                this.height = false;
            }

            // Si el FE quedó en 0, se detiene la propagación
            else if (fe == 0) {
                this.height = false;
            }
        }

        return node;
    }

    /**
     * Realiza una rotación simple a la derecha (SR).
     * 
     * @param node el nodo desbalanceado.
     * @return el nuevo nodo raíz tras la rotación.
     */
    protected NodeAVL<E> rotateSR(NodeAVL<E> node) {
        NodeAVL<E> hijo = (NodeAVL<E>) node.getLeft();
        node.setLeft(hijo.getRight());
        hijo.setRight(node);

        // Actualizar factores de equilibrio
        if (hijo.getFactEquilibrio() == -1) {
            node.setFactEquilibrio(0);
            hijo.setFactEquilibrio(0);
        } else {
            node.setFactEquilibrio(-1);
            hijo.setFactEquilibrio(1);
        }

        return hijo;
    }
    
    /**
     * Realiza una rotación simple a la izquierda (SL).
     * 
     * @param node el nodo desbalanceado.
     * @return el nuevo nodo raíz tras la rotación.
     */
    protected NodeAVL<E> rotateSL(NodeAVL<E> node) {
        NodeAVL<E> hijo = (NodeAVL<E>) node.getRight();
        node.setRight(hijo.getLeft());
        hijo.setLeft(node);

        // Actualización de factores de equilibrio
        if (hijo.getFactEquilibrio() == 1) {
            node.setFactEquilibrio(0);
            hijo.setFactEquilibrio(0);
        } else {
            node.setFactEquilibrio(1);
            hijo.setFactEquilibrio(-1);
        }

        return hijo;
    }

    /**
     * Realiza una rotación doble derecha (DR), que es una rotación izquierda en el hijo izquierdo
     * seguida de una rotación derecha en el nodo.
     * 
     * @param node el nodo desbalanceado.
     * @return el nuevo nodo raíz tras la rotación.
     */
    protected NodeAVL<E> rotateDR(NodeAVL<E> node) {
        NodeAVL<E> hijo = (NodeAVL<E>) node.getLeft();
        NodeAVL<E> nieto = (NodeAVL<E>) hijo.getRight();

        hijo.setRight(nieto.getLeft());
        node.setLeft(nieto.getRight());
        nieto.setLeft(hijo);
        nieto.setRight(node);

        // Actualización de factores de equilibrio
        switch (nieto.getFactEquilibrio()) {
            case -1:
                node.setFactEquilibrio(1);
                hijo.setFactEquilibrio(0);
                break;
            case 0:
                node.setFactEquilibrio(0);
                hijo.setFactEquilibrio(0);
                break;
            case 1:
                node.setFactEquilibrio(0);
                hijo.setFactEquilibrio(-1);
                break;
        }

        nieto.setFactEquilibrio(0);
        return nieto;
    }

    /**
     * Realiza una rotación doble izquierda (DL), que es una rotación derecha en el hijo derecho
     * seguida de una rotación izquierda en el nodo.
     * 
     * @param node el nodo desbalanceado.
     * @return el nuevo nodo raíz tras la rotación.
     */
    protected NodeAVL<E> rotateDL(NodeAVL<E> node) {
        NodeAVL<E> hijo = (NodeAVL<E>) node.getRight();
        NodeAVL<E> nieto = (NodeAVL<E>) hijo.getLeft();

        hijo.setLeft(nieto.getRight());
        node.setRight(nieto.getLeft());
        nieto.setRight(hijo);
        nieto.setLeft(node);

        // Actualización de factores de equilibrio
        switch (nieto.getFactEquilibrio()) {
            case 1:
                node.setFactEquilibrio(-1);
                hijo.setFactEquilibrio(0);
                break;
            case 0:
                node.setFactEquilibrio(0);
                hijo.setFactEquilibrio(0);
                break;
            case -1:
                node.setFactEquilibrio(0);
                hijo.setFactEquilibrio(1);
                break;
        }

        nieto.setFactEquilibrio(0);
        return nieto;
    }

    

    // Método para imprimir el árbol - test
    public void printTree() {
        printTree((NodeAVL<E>) this.root, 0);
    }

    private void printTree(NodeAVL<E> node, int level) {
        if (node == null) return;
        printTree((NodeAVL<E>) node.getRight(), level + 1);
        for (int i = 0; i < level; i++) System.out.print("    ");
        System.out.println(node);
        printTree((NodeAVL<E>) node.getLeft(), level + 1);
    }

}
