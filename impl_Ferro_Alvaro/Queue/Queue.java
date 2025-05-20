package avltree;

public interface Queue <E> {
    public void enqueue(E obj);
    public E dequeue() throws ExceptionIsEmpty;
    public void destroyQueue();
    public boolean isEmpty();
    public E front() throws ExceptionIsEmpty;
    public E back() throws ExceptionIsEmpty;
}
