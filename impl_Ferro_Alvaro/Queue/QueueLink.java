package avltree;
public class QueueLink <E> implements Queue <E> {

    private Node<E> first;
    private Node<E> last;

    //Constructor
    public QueueLink() {
        this.first = null;
        this.last = null;
    }
    @Override
    public void enqueue(E obj) {

        Node<E> nodeAux = new Node<> (obj);

        if(this.isEmpty()) {
            this.first = nodeAux;
            this.last = nodeAux;
        }else {
            this.last.setNext(nodeAux);
            this.last = nodeAux;
        }
    }
    
  
    @Override
    public E dequeue() throws ExceptionIsEmpty {
        if(this.isEmpty())
            throw new ExceptionIsEmpty("Cola vacia");
        
        E data = this.first.getData();
        this.first = this.first.getNext();

        if(this.first == null) {
            this.last = null;
        }

        return data;
    }

       @Override
    public void destroyQueue() {
        this.first = null;
        this.last = null;
    }

     @Override
    public boolean isEmpty() {
        return this.first == null;
    }

    @Override
    public E front() throws ExceptionIsEmpty {
        if(this.isEmpty())
            throw new ExceptionIsEmpty("Cola vacia");
        
        return this.first.getData();
    }

    @Override
    public E back() throws ExceptionIsEmpty {
        if(this.isEmpty())
            throw new ExceptionIsEmpty("Cola vacia");
        
        return this.last.getData();
    }

    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");

        Node<E> actual = this.first;

        while (actual != this.last) {
            sb.append(actual.getData() + ", ");
            actual = actual.getNext();
        }

        sb.append(actual.getData() + "]");
        return sb.toString();
    }
    
}

