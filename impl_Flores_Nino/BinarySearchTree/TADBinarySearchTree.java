package impl_Flores_Nino.BinarySearchTree;

import impl_Flores_Nino.Excepciones.*;

/**
 * Interfaz que define las operaciones básicas de un BST genérica.
 * 
 * @param E el tipo de elementos que almacena el BST.
 */
public interface TADBinarySearchTree <E> {
    public void insert(E data) throws ExceptionItemDuplicated;
    public E search(E data) throws ExceptionItemNotFound;
    public void delete(E data) throws ExceptionIsEmpty;
    public boolean isEmpty();
    public void destroyNodes() throws ExceptionIsEmpty;
    public int countAllNodes(); //Nodos no hojas
    public int height(E subRoot);
    public int amplitude(int level) throws IllegalArgumentException, ExceptionIsEmpty;
    public String inOrden();
    public String postOrden();
    public String preOrden();
    public E getMax() throws ExceptionIsEmpty;
    public E getMin() throws ExceptionIsEmpty;
}
