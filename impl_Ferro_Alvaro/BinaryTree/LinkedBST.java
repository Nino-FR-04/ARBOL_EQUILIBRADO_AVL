package avltree;
public class LinkedBST<E extends Comparable<E>> implements BinarySearchTree<E> {
    protected NodeTree<E> root;

    public LinkedBST() {
        this.root = null;
    }

    @Override
    public void insert(E data) throws ItemDuplicated {
        NodeTree<E> nuevo = new NodeTree<>(data);
        if (this.root == null) {
            this.root = nuevo;
            return;
        }

        NodeTree<E> actual = this.root;
        NodeTree<E> padre = null;

        while (true) {
            padre = actual;
            if (data.compareTo(actual.getData()) == 0)
                throw new ItemDuplicated("No se puede insertar un valor duplicado");
            if (data.compareTo(actual.getData()) > 0) {
                actual = actual.getRight();
            } else {
                actual = actual.getLeft();
            }
            if (actual == null) break;
        }

        if (data.compareTo(padre.getData()) > 0) {
            padre.setRight(nuevo);
        } else {
            padre.setLeft(nuevo);
        }
    }

    @Override
    public E search(E data) throws ItemNoFound, ExceptionIsEmpty{
        if (this.isEmpty())
            throw new ExceptionIsEmpty("El arbol se encuentra vacio");

        NodeTree<E> actual = this.root;

        while (actual != null) {
            if (data.compareTo(actual.getData()) == 0) {
                return actual.getData();
            }
            if (data.compareTo(actual.getData()) > 0) {
                actual = actual.getRight();
            } else {
                actual = actual.getLeft();
            }
        }

        throw new ItemNoFound("Item no encontrado");
    }

    protected NodeTree<E> searchNode(E data) throws ExceptionIsEmpty {
        if (this.isEmpty())
            throw new ExceptionIsEmpty("El arbol se encuentra vacio");

        NodeTree<E> actual = this.root;

        while (actual != null) {
            if (data.compareTo(actual.getData()) == 0) {
                return actual;
            }
            if (data.compareTo(actual.getData()) > 0) {
                actual = actual.getRight();
            } else {
                actual = actual.getLeft();
            }
        }

        return null;
    }

    @Override
    public void delete(E data) throws ExceptionIsEmpty, ItemNoFound {
        if (this.isEmpty())
            throw new ExceptionIsEmpty("Arbol vacio");

        this.root = deleteRecursivo(this.root, data);
    }

    protected NodeTree<E> deleteRecursivo(NodeTree<E> rootSub, E data) throws ItemNoFound {
        if (rootSub == null)
            throw new ItemNoFound("Item no encontrado");

        int cmp = data.compareTo(rootSub.getData());

        if (cmp < 0) {
            rootSub.setLeft(deleteRecursivo(rootSub.getLeft(), data));
        } else if (cmp > 0) {
            rootSub.setRight(deleteRecursivo(rootSub.getRight(), data));
        } else {
            if (rootSub.getLeft() == null && rootSub.getRight() == null) {
                return null;
            }
            if (rootSub.getRight() == null) {
                return rootSub.getLeft();
            }
            if (rootSub.getLeft() == null) {
                return rootSub.getRight();
            }

            NodeTree<E> minSubRight = this.getNodeMin(rootSub.getRight());
            rootSub.setRight(deleteRecursivo(rootSub.getRight(), minSubRight.getData()));
            minSubRight.setLeft(rootSub.getLeft());
            minSubRight.setRight(rootSub.getRight());
            return minSubRight;
        }

        return rootSub;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public void destroyNodes() throws ExceptionIsEmpty {
        if (this.isEmpty()) throw new ExceptionIsEmpty("Arbol vacío");
        this.root = null;
    }

    @Override
    public int countAllNodes() {
        return countNodes(this.root);
    }

    protected int countNodes(NodeTree<E> node) {
        if (node == null || node.getLeft() == null && node.getRight() == null) {
            return 0;
        }
        return 1 + countNodes(node.getLeft()) + countNodes(node.getRight());
    }

    @Override
    public int height(E subRoot) throws ExceptionIsEmpty {
        if (this.isEmpty()) {
            throw new ExceptionIsEmpty("El árbol está vacío");
        }

        NodeTree<E> nodoActual = this.searchNode(subRoot);
        if (nodoActual == null) {
            return -1;
        }

        QueueLink<NodeTree<E>> cola = new QueueLink<>();
        cola.enqueue(nodoActual);
        cola.enqueue(null);
        int altura = -1;

        while (!cola.isEmpty()) {
            NodeTree<E> nodo = cola.dequeue();

            if (nodo == null) {
                altura++;
                if (!cola.isEmpty()) {
                    cola.enqueue(null);
                }
                continue;
            }

            if (nodo.getLeft() != null) {
                cola.enqueue(nodo.getLeft());
            }
            if (nodo.getRight() != null) {
                cola.enqueue(nodo.getRight());
            }
        }

        return altura;
    }

    @Override
    public int amplitude(int level) throws IllegalArgumentException, ExceptionIsEmpty {
        if (this.isEmpty()) {
            throw new ExceptionIsEmpty("El árbol está vacío");
        }

        if (level < 0) {
            throw new IllegalArgumentException("El nivel no puede ser negativo");
        }

        QueueLink<NodeTree<E>> cola = new QueueLink<>();
        cola.enqueue(this.root);
        cola.enqueue(null);
        int nivelActual = 0;
        int cantNodes = 0;

        while (!cola.isEmpty()) {
            NodeTree<E> nodo = cola.dequeue();

            if (nodo == null) {
                nivelActual++;
                if (nivelActual > level || cola.isEmpty()) {
                    break;
                }
                cola.enqueue(null);
                continue;
            }

            if (nivelActual == level) cantNodes++;

            if (nodo.getLeft() != null) {
                cola.enqueue(nodo.getLeft());
            }
            if (nodo.getRight() != null) {
                cola.enqueue(nodo.getRight());
            }
        }

        return cantNodes;
    }

	 @Override
	    public String inOrden() {
	        return "[" + this.inOrden(this.root).toString() + "]";
	    }

	    protected StringBuilder inOrden(NodeTree<E> node) {
	        
	        StringBuilder sb = new StringBuilder();

	        if (node != null) {
	            sb.append(inOrden(node.getLeft()));        
	            sb.append(node.getData()).append(" ");      
	            sb.append(inOrden(node.getRight()));        
	        }
	        return sb;
	    }
	    @Override
	    public String postOrden() {
	        return "[" + this.postOrden(this.root).toString() + "]";
	    }

	    protected StringBuilder postOrden(NodeTree<E> node) {
	        
	        StringBuilder sb = new StringBuilder();

	        if (node != null) {
	            sb.append(postOrden(node.getLeft()));         
	            sb.append(postOrden(node.getRight()));        
	            sb.append(node.getData()).append(" ");      
	        }
	        return sb;
	    }

	    @Override
	    public String preOrden() {
	        return "[" + this.preOrden(this.root).toString() + "]";
	    }
	    protected StringBuilder preOrden(NodeTree<E> node) {
	        
	        StringBuilder sb = new StringBuilder();

	        if (node != null) {
	            sb.append(node.getData()).append(" ");      
	            sb.append(preOrden(node.getLeft()));         
	            sb.append(preOrden(node.getRight()));        
	        }
	        return sb;
	    }

	    @Override
	    public E getMax() {
	        return this.getNodeMax(this.root).getData();
	    }
	    protected NodeTree<E> getNodeMax(NodeTree<E> rootSub) {
	        NodeTree<E> actual = rootSub;

	        while(actual.getRight() != null) {
	            actual = actual.getRight();
	        }

	        return actual;
	    }

	 @Override
	    public E getMin() {
	        return this.getNodeMin(this.root).getData();
	    }
	    protected NodeTree<E> getNodeMin(NodeTree<E> rootSub) {
	        NodeTree<E> actual = rootSub;

	        while(actual.getLeft() != null) {
	            actual = actual.getLeft();
	        }

	        return actual;
	    }
	    public int areaBST() throws ExceptionIsEmpty {
	        if(this.isEmpty()) {
	            throw new ExceptionIsEmpty("El arbol esta vacio");
	        }

	        int altura = this.height(this.root.getData());
	        int numLeafNodes = this.countLeafNodes(this.root);

	        return altura * numLeafNodes;
	    }

	    protected int countLeafNodes(NodeTree<E> node) throws ExceptionIsEmpty {

	        QueueLink<NodeTree<E>> cola = new QueueLink<>();
	        cola.enqueue(node);

	        int cantidadHojas = 0;

	        while (!cola.isEmpty()) {
	            NodeTree<E> nodo = cola.dequeue();

	            if (isLeaf(nodo)) {
	                cantidadHojas++;
	            }

	            if (nodo.getLeft() != null) {
	                cola.enqueue(nodo.getLeft());
	            }

	            // Si tiene hijo derecho, se encola
	            if (nodo.getRight() != null) {
	                cola.enqueue(nodo.getRight());
	            }
	        }

	        return cantidadHojas;
	    }

	    protected boolean isLeaf(NodeTree<E> node) {
	        return node.getLeft() == null && node.getRight() == null;
	    } 

	    @Override
	    public String toString(){
	        return "[ " + this.inOrden(this.root) + "]";
	    }
	}

