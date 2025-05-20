package avltree;

import BinarySearchTree.LinkedBST;
import Excepciones.ExceptionItemDuplicated;
import Nodes.NodeAVL;
import Nodes.NodeTree;

public class AVLTree<E extends Comparable <E>> extends LinkedBST<E> {
    private boolean height;

    public void insert(E x) throws ExceptionItemDuplicated {
        height = false;
        this.root = insert(x, (NodeAVL<E>) this.root);
    }

    protected NodeTree<E> insert(E x, NodeAVL<E> node) throws ExceptionItemDuplicated {
        NodeAVL<E> fat = node;

        if (node == null) {
            height = true;
            return new NodeAVL<>(x);
        }

        int resC = x.compareTo(node.data);

        if (resC == 0) {
            throw new ExceptionItemDuplicated(x + " ya se encuentra en el árbol");
        }

        if (resC < 0) {
            fat.left = insert(x, (NodeAVL<E>) node.left);

            if (height) {
                switch (fat.bf) {
                    case 1:
                        fat.bf = 0;
                        height = false;
                        break;
                    case 0:
                        fat.bf = -1;
                        height = true;
                        break;
                    case -1:
                        fat = balanceToLeft(fat); // ahora va a la izquierda
                        height = false;
                        break;
                }
            }
        } else {
            fat.right = insert(x, (NodeAVL<E>) node.right);

            if (height) {
                switch (fat.bf) {
                    case -1:
                        fat.bf = 0;
                        height = false;
                        break;
                    case 0:
                        fat.bf = 1;
                        height = true;
                        break;
                    case 1:
                        fat = balanceToRight(fat); // ahora va a la derecha
                        height = false;
                        break;
                }
            }
        }

        return fat;
    }

    private NodeAVL<E> balanceToLeft(NodeAVL<E> node) {
        NodeAVL<E> hijo = (NodeAVL<E>) node.left;

        if (hijo.bf == -1) {
            // Rotación simple a la derecha
            node.bf = 0;
            hijo.bf = 0;
            node = rotateSR(node);
        } else {
            // Rotación doble izquierda-derecha
            NodeAVL<E> nieto = (NodeAVL<E>) hijo.right;

            switch (nieto.bf) {
                case -1:
                    node.bf = 1;
                    hijo.bf = 0;
                    break;
                case 0:
                    node.bf = 0;
                    hijo.bf = 0;
                    break;
                case 1:
                    node.bf = 0;
                    hijo.bf = -1;
                    break;
            }

            if (nieto != null) nieto.bf = 0;

            node.left = rotateSL(hijo);
            node = rotateSR(node);
        }

        return node;
    }

    private NodeAVL<E> balanceToRight(NodeAVL<E> node) {
        NodeAVL<E> hijo = (NodeAVL<E>) node.right;

        if (hijo.bf == 1) {
            // Rotación simple a la izquierda
            node.bf = 0;
            hijo.bf = 0;
            node = rotateSL(node);
        } else {
            // Rotación doble derecha-izquierda
            NodeAVL<E> nieto = (NodeAVL<E>) hijo.left;

            switch (nieto.bf) {
                case 1:
                    node.bf = -1;
                    hijo.bf = 0;
                    break;
                case 0:
                    node.bf = 0;
                    hijo.bf = 0;
                    break;
                case -1:
                    node.bf = 0;
                    hijo.bf = 1;
                    break;
            }

            if (nieto != null) nieto.bf = 0;

            node.right = rotateSR(hijo);
            node = rotateSL(node);
        }

        return node;
    }

    private NodeAVL<E> rotateSL(NodeAVL<E> node) {
        NodeAVL<E> p = (NodeAVL<E>) node.right;

        node.right = p.left;
        p.left = node;

        updateBalanceFactor(node);
        updateBalanceFactor(p);

        return p;
    }

    private NodeAVL<E> rotateSR(NodeAVL<E> node) {
        NodeAVL<E> p = (NodeAVL<E>) node.left;

        node.left = p.right;
        p.right = node;

        updateBalanceFactor(node);
        updateBalanceFactor(p);

        return p;
    }
    /**
     * Actualiza el factor de balance de un nodo AVL.
     * @param node Nodo al cual se le actualizará el balance.
     */

    private void updateBalanceFactor(NodeAVL<E> node) {
        if (node != null) {
            node.bf = height((NodeAVL<E>) node.left) - height((NodeAVL<E>) node.right);
        }
    }
    /**
     * Calcula la altura de un nodo AVL de forma recursiva.
     * @param node Nodo del que se desea conocer la altura.
     * @return Altura del nodo.
     */

    private int height(NodeAVL<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(height((NodeAVL<E>) node.left), height((NodeAVL<E>) node.right));
    }

    @Override
    public String toString() {
        return inOrdenAVL((NodeAVL<E>) this.root).toString();
    }

    private StringBuilder inOrdenAVL(NodeAVL<E> node) {
        StringBuilder sb = new StringBuilder();
        if (node != null) {
            sb.append(inOrdenAVL((NodeAVL<E>) node.getLeft()));
            sb.append(node.toString()).append(" ");
            sb.append(inOrdenAVL((NodeAVL<E>) node.getRight()));
        }
        return sb;
    }

}

		
