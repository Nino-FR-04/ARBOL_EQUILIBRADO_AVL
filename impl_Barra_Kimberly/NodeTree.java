package Nodes;


public class NodeTree <E extends Comparable<E>> {
    
    public E data;
    public NodeTree<E> right;
    public NodeTree<E> left;

    public NodeTree(E data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public E getData() {
    	return this.data;
    }
    public NodeTree<E> getLeft() {
    	return this.left;
    }
    public NodeTree<E> getRight() {
    	return this.right;
    }

    public void setLeft(NodeTree<E> left) {
    	this.left = left;
    }
    public void setRight(NodeTree<E> right) {
    	this.right = right;
    }

}