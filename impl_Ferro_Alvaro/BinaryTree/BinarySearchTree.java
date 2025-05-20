package avltree;

public interface BinarySearchTree<E>{
	public void insert(E data) throws ItemDuplicated;
    public E search(E data) throws ItemNoFound,ExceptionIsEmpty;
    public void delete(E data) throws ExceptionIsEmpty,ItemNoFound;
    public boolean isEmpty();
    public void destroyNodes() throws ExceptionIsEmpty;
    public int countAllNodes(); 
    public int height(E subRoot) throws ExceptionIsEmpty;
    public int amplitude(int level) throws IllegalArgumentException, ExceptionIsEmpty;
    public int areaBST() throws ExceptionIsEmpty; 
    public String inOrden();
    public String postOrden();
    public String preOrden();
    public E getMax() throws ExceptionIsEmpty;
    public E getMin() throws ExceptionIsEmpty;
}