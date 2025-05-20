package avltree;


public class Node <E> {
    protected E data;
    protected Node <E> next;

    //Getters
    public E getData() {return this.data;}
    public Node<E> getNext() {return this.next;}
    
    //Setters
    public void setNext(Node<E> next) {this.next = next;}

    public Node(E data) {
        this.data = data;
        this.next = null;
    }
}