package impl_Flores_Nino.Nodes;

/**
 * Clase que representa el nodo de un BST AVL este contiene el valor
 * a guardar, referencias a otros dos nodos y el Factor de equilibrio.
 * @param E valor generico que representa el valor a guardar.
 */
public class NodeAVL<E> extends NodeTree<E> {

    //Atributos
    protected int fe; //Representa el factor de equilibrio

    //Constructor
    public NodeAVL(E data) {
        super(data);
        this.fe = 0;
    }

    //getter y setter
    public int getFactEquilibrio() {return this.fe;}
    public void setFactEquilibrio(int fe) {this.fe = fe;}
    public void setData(E data) {this.data = data;}

    //ToString
    @Override
    public String toString() {
        return "(" + this.data + ", " + this.fe + ")";
    }
}
