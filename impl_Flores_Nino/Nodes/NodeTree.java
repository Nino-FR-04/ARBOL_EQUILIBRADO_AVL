package impl_Flores_Nino.Nodes;

/**
 * Clase que representa el nodo de un BST este contiene el valor
 * a guardar y referencias a otros dos nodos.
 * @param E valor generico que representa el valor a guardar.
 */

public class NodeTree <E> {
    
    //Atributos
    protected E data;
    protected NodeTree<E> right;
    protected NodeTree<E> left;

    //Constructor
    public NodeTree(E data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    //Getters y Setters
    public E getData() {return this.data;}
    public NodeTree<E> getLeft() {return this.left;}
    public NodeTree<E> getRight() {return this.right;}

    public void setLeft(NodeTree<E> left) {this.left = left;}
    public void setRight(NodeTree<E> right) {this.right = right;}

}