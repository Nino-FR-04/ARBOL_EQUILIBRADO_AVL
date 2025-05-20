package avltree;

public class NodeTree <E extends Comparable<E>>{
		public E data;
		public NodeTree<E> right; 
		public NodeTree<E> left; 
	
	public NodeTree(E data) {
		this(data,null,null);
	}
	public NodeTree(E data, NodeTree right, NodeTree left) {
		this.data=data;
		this.right=right; 
		this.left=left; 
	}
	public E getData() {
		return this.data; 
	}
	public NodeTree<E> getRight(){
		return this.right; 
	}
	public NodeTree<E> getLeft(){
		return this.left; 
	}
	public void setRight(NodeTree<E>right) {
		this.right=right; 
	}
	public void setLeft(NodeTree<E>left) {
		this.left=left;
	}
}
