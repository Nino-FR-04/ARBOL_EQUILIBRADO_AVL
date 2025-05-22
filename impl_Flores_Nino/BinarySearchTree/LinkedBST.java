package impl_Flores_Nino.BinarySearchTree;

import impl_Flores_Nino.Nodes.NodeTree;
import impl_Flores_Nino.Queue.QueueLink;
import impl_Flores_Nino.Excepciones.*;

public class LinkedBST <E extends Comparable<E>> implements TADBinarySearchTree <E> {

    //Atributos
    protected NodeTree<E> root;

    //Constructor
    public LinkedBST() {
        this.root = null;
    }

    /**
     * Inserta un nuevo elemento en el árbol binario de búsqueda.
     *
     * <p>Si el árbol está vacío, el nuevo nodo se convierte en la raíz.
     * Si el valor ya existe en el árbol, se lanza una excepción para evitar duplicados.
     *
     * @param data el elemento que se desea insertar en el árbol
     * @throws ExceptionItemDuplicated si el elemento ya existe en el árbol
     * @throws IllegalArgumentException si el valor proporcionado es {@code null}
     */
    @Override
    public void insert(E data) throws ExceptionItemDuplicated {
        
        NodeTree<E> nuevo = new NodeTree<>(data);
        
        //Caso 1: Raiz vacia. Se le asigna un nuevo nodo a la raiz.
        if(this.root == null){
            this.root = nuevo;
            return;
        }

        //Caso 2: Mas nodos
        /*
         * Actual: Nodo actual - Sirve para recorrer el arbol
         * Padre: Nodo que sirve para hacer un seguimiento al padre del
         * nodo actual. 
         */
        NodeTree<E> actual = this.root;
        NodeTree<E> padre = null;

        while(true) {
            
            padre = actual;

            /*
             * Si el resultado de compareTo es 0, se inserta un dato
             * duplicado por lo que lanza esa excepcion
             */
            if(data.compareTo(actual.getData()) == 0) 
                throw new ExceptionItemDuplicated("No se puede insertar un valor duplicado");

            /*
             * Si la data a insertar es mayor que mi nodo actual (1) se
             * continua por el lado derecho. Caso contrario se actualiza
             * actual a su nodo izquierdo.
             */
            if(data.compareTo(actual.getData()) > 0) {
                actual = actual.getRight();
            }else {
                actual = actual.getLeft();
            }

            /*
             * Si mi var actual es null se llego al final del recorrido
             * por lo que se rompe el bucle
             */
            if(actual == null) break;
        }

        /*
         * Comparacion final: si la data a insertar es mayor que el valor de la
         * data del nodo padre, se inserta a la derecha, caso contrario la insercio
         * se hace a la izquierda.
         */
        if(data.compareTo(padre.getData()) > 0) {
            padre.setRight(nuevo);
        }else {
            padre.setLeft(nuevo);
        }
    }

    /**
     * Busca un elemento en el árbol binario de búsqueda.
     *
     * <p>El recorrido se realiza de forma iterativa desde la raíz.
     * Si el árbol está vacío o el elemento no se encuentra, se lanzan las excepciones correspondientes.
     *
     * @param data el elemento a buscar en el árbol
     * @return el dato almacenado que coincide con {@code data}
     * @throws ExceptionIsEmpty si el árbol está vacío
     * @throws ExceptionItemNotFound si el elemento no se encuentra en el árbol
     */
    @Override
    public E search(E data) throws ExceptionItemNotFound, ExceptionIsEmpty {
        if(this.isEmpty())
            throw new ExceptionIsEmpty("El arbol se encuentra vacio");
        
        //Sirve para recorrer el arbol
        NodeTree<E> actual = this.root;

        while(actual != null) {
            
            if(data.compareTo(actual.getData()) == 0) {
                return actual.getData();
            }

            /*
             * Si la data a buscar es mayor que mi nodo actual (1) se
             * continua por el lado derecho. Caso contrario se actualiza
             * actual a su nodo izquierdo.
             */
            if(data.compareTo(actual.getData()) > 0) {
                actual = actual.getRight();
            }else {
                actual = actual.getLeft();
            }
        }

        /*
         * Si mi var actual es null se llego al final del recorrido
         * por lo que se lanza la excepcion que corresponde.
         */
        throw new ExceptionItemNotFound("Item no encontrado");
    }

    /**
     * Busca y retorna el nodo cuyo dato sea igual al valor especificado.
     *
     * <p>Este método recorre el árbol binario de búsqueda de forma iterativa, 
     * comparando el valor dado con los datos de los nodos para determinar 
     * si debe avanzar por el subárbol izquierdo o derecho.</p>
     *
     * @param data el valor a buscar en el árbol
     * @return el nodo que contiene el dato especificado, o {@code null} si no se encuentra
     * @throws ExceptionIsEmpty si el árbol está vacío
     */
    protected NodeTree<E> searchNode(E data) throws ExceptionIsEmpty {
        if(this.isEmpty())
            throw new ExceptionIsEmpty("El arbol se encuentra vacio");
        
        //Sirve para recorrer el arbol
        NodeTree<E> actual = this.root;

        while(actual != null) {
            
            if(data.compareTo(actual.getData()) == 0) {
                return actual;
            }

            /*
             * Si la data a buscar es mayor que mi nodo actual (1) se
             * continua por el lado derecho. Caso contrario se actualiza
             * actual a su nodo izquierdo.
             */
            if(data.compareTo(actual.getData()) > 0) {
                actual = actual.getRight();
            }else {
                actual = actual.getLeft();
            }
        }

        /*
         * Si mi var actual es null se llego al final del recorrido
         * por lo que se retorna null.
         */
        return null;
    }

    /**
     * Elimina un elemento específico del árbol binario de búsqueda.
     *
     * @param data el elemento que se desea eliminar del árbol
     * @throws ExceptionIsEmpty si el árbol está vacío
     * @throws ExceptionItemNotFound si el elemento no se encuentra en el árbol
     */
    @Override
    public void delete(E data) throws ExceptionIsEmpty, ExceptionItemNotFound {
        if(this.isEmpty()) 
            throw new ExceptionIsEmpty("Arbol vacio");

        this.root = deleteRecursivo(this.root,data);
    }

    //Metodo auxiliar - Recursivo
    /**
     * Método auxiliar recursivo que elimina un nodo del árbol binario de búsqueda.
     *
     * <p>Busca el nodo con el valor proporcionado y lo elimina siguiendo las reglas del
     * BST:</p>
     * <ul>
     *   <li>Si el nodo es una hoja, simplemente se elimina.</li>
     *   <li>Si el nodo tiene un solo hijo, se reemplaza por su hijo.</li>
     *   <li>Si el nodo tiene dos hijos, se reemplaza por el nodo con el menor valor del subárbol derecho
     *       , y luego se elimina ese sucesor del subárbol derecho.</li>
     * </ul>
     *
     * @param rootSub el nodo raíz del subárbol actual
     * @param data el valor a eliminar
     * @return la nueva raíz del subárbol después de la eliminación
     * @throws ExceptionItemNotFound si el nodo con el valor no se encuentra
     */
    protected NodeTree<E> deleteRecursivo(NodeTree<E> rootSub,E data) throws ExceptionItemNotFound {
        if(rootSub == null) 
            throw new ExceptionItemNotFound("Item no encontrado");

        int cmp = data.compareTo(rootSub.getData());

        if(cmp < 0) {
            rootSub.setLeft(deleteRecursivo(rootSub.getLeft(), data));
        }else if(cmp > 0) {
            rootSub.setRight(deleteRecursivo(rootSub.getRight(), data));
        }else {
            //Se encontro el nodo

            //Caso 1: Es un nodo hoja
            if(rootSub.getLeft() == null && rootSub.getRight() == null) {
                return null;
            }

            //Caso 2: Nodo con un solo hijo
            if(rootSub.getRight() == null) {
                return rootSub.getLeft();
            }

            if(rootSub.getLeft() == null) {
                return rootSub.getRight();
            }

            //caso 3: Nodo con dos hijos

            NodeTree<E> minSubRight = this.getNodeMin(rootSub.getRight());
            rootSub.setRight(deleteRecursivo(rootSub.getRight(), minSubRight.getData()));
            
            minSubRight.setLeft(rootSub.getLeft());
            minSubRight.setRight(rootSub.getRight());

            return minSubRight;
        }

        return rootSub;
    }

    /**
     * Verifica si el árbol binario de búsqueda está vacío.
     *
     * @return {@code true} si el árbol no contiene ningún nodo, {@code false} en caso contrario
     */
    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * Elimina todos los nodos del árbol binario de búsqueda.
     * 
     * <p>Este método destruye todo el árbol asignando {@code null} a la raíz,
     * lo que permite que el recolector de basura de Java libere la memoria
     * ocupada por los nodos, siempre que no existan referencias externas a ellos.</p>
     *
     * @throws ExceptionIsEmpty si el árbol ya está vacío
     */
    @Override
    public void destroyNodes() throws ExceptionIsEmpty {
        if(this.isEmpty()) throw new ExceptionIsEmpty("Arbol vacío");

        this.root = null;
    }

    /**
     * Cuenta la cantidad de nodos no-hoja (nodos internos) en el árbol binario de búsqueda.
     *
     * <p>Un nodo no-hoja es aquel que tiene al menos un hijo. Este método hace uso de una
     * función auxiliar recursiva para recorrer el árbol y contar sólo los nodos que cumplen
     * esa condición.</p>
     *
     * @return el número total de nodos no-hoja en el árbol
     */
    @Override
    public int countAllNodes() {
        return countNodes(this.root);
    }

    //Metodo recursivo para contar los nodos
    protected int countNodes(NodeTree<E> node) {
        if(node == null || 
                node.getLeft() == null && node.getRight() == null) {
            return 0;
        }
        return 1 + countNodes(node.getLeft()) + countNodes(node.getRight()); 
    }

    /**
     * Calcula la altura del subárbol cuya raíz tiene como dato el valor especificado.
     * <p>
     * La altura se define como la longitud del camino más largo desde ese nodo hasta una hoja.
     * El cálculo se realiza de forma iterativa usando recorrido por niveles (BFS).
     * Si el nodo con el valor especificado no se encuentra en el árbol, se retorna -1.
     * </p>
     *
     * @param subRoot el valor del nodo raíz del subárbol a evaluar.
     * @return la altura del subárbol, o -1 si no se encuentra el nodo.
     * @throws ExceptionIsEmpty si el árbol está vacío.
     */
    @Override
    public int height(E subRoot) throws ExceptionIsEmpty {
        if (this.isEmpty()) {
            throw new ExceptionIsEmpty("El árbol está vacío");
        }

        NodeTree<E> nodoActual = this.searchNode(subRoot); //Si retorna null, no existe el nodo
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
                altura++; // Fin de un nivel, incrementamos altura
                if (!cola.isEmpty()) {
                    cola.enqueue(null); // Agregamos separador para el siguiente nivel
                }
                /*
                 * Asegura que no se ejecute lo que sigue en la iteracion
                 * evitar un NullPointerException
                */
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

    /**
     * Calcula la amplitud (cantidad de nodos) en un nivel específico del árbol binario.
     *
     * @param level el nivel del árbol cuyo número de nodos se desea calcular
     * @return el número de nodos presentes en el nivel especificado
     * @throws IllegalArgumentException si el nivel es negativo
     * @throws ExceptionIsEmpty si el árbol está vacío
     */
    @Override
    public int amplitude(int level) throws IllegalArgumentException, ExceptionIsEmpty {
        
        if (this.isEmpty()) {
            throw new ExceptionIsEmpty("El árbol está vacío");
        }

        if(level < 0) {
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
                nivelActual++; // Fin de un nivel, incrementamos altura
                
                // Si ya se supero el nivel deseado o no hay más nodos, se sale del bucle
                if (nivelActual > level || cola.isEmpty()) {
                    break;
                }

                cola.enqueue(null); // Agregamos separador para el siguiente nivel
                /*
                 * Asegura que no se ejecute lo que sigue en la iteracion sirve
                 * para evitar un NullPointerException.
                */
                continue; 
            }

            // Si estamos en el nivel que buscamos, se incrementa el contador de nodos
            if(nivelActual == level) cantNodes++;

            if (nodo.getLeft() != null) {
                cola.enqueue(nodo.getLeft());
            }

            if (nodo.getRight() != null) {
                cola.enqueue(nodo.getRight());
            }

        }
        return cantNodes;
    }

    //--------------------------Recorridos
    /**
     * Realiza un recorrido en preorden del árbol binario.
     * 
     * @return Una cadena con los elementos del árbol en preorden, entre corchetes y separados por espacios.
     */
    @Override
    public String preOrden() {
        return "[" + this.preOrden(this.root).toString() + "]";
    }

    /**
     * Método auxiliar recursivo para realizar el recorrido en preorden.
     * 
     * En el recorrido en pón
     *
     * @param node El nodo actual desde el cual se comienza el recorrido.
     * @return Un StringBuilder con los elementos en preorden.
     */
    protected StringBuilder preOrden(NodeTree<E> node) {
        
        StringBuilder sb = new StringBuilder();

        if (node != null) {
            sb.append(node.getData()).append(" ");      // Nodo actual
            sb.append(preOrden(node.getLeft()));         // Subárbol izquierdo
            sb.append(preOrden(node.getRight()));        // Subárbol derecho
        }
        return sb;
    }

    /**
     * Realiza un recorrido en inorden del árbol binario.
     * 
     * @return Una cadena con los elementos del árbol en inorden, entre corchetes y separados por espacios.
     */
    @Override
    public String inOrden() {
        return "[" + this.inOrden(this.root).toString() + "]";
    }

    /**
     * Método auxiliar recursivo para realizar el recorrido en inorden.
     * 
     * En el recorrido en inorden se procesa primero el subárbol izquierdo, luego el nodo actual y finalmente el subárbol derecho.
     *
     * @param node El nodo actual desde el cual se comienza el recorrido.
     * @return Un StringBuilder con los elementos en inorden.
     */
    protected StringBuilder inOrden(NodeTree<E> node) {
        
        StringBuilder sb = new StringBuilder();

        if (node != null) {
            sb.append(inOrden(node.getLeft()));         // Subárbol izquierdo
            sb.append(node.getData()).append(" ");      // Nodo actual
            sb.append(inOrden(node.getRight()));        // Subárbol derecho
        }
        return sb;
    }

    /**
     * Realiza un recorrido en postorden del árbol binario.
     * 
     * @return Una cadena con los elementos del árbol en postorden, entre corchetes y separados por espacios.
     */
    @Override
    public String postOrden() {
        return "[" + this.postOrden(this.root).toString() + "]";
    }

    /**
     * Método auxiliar recursivo para realizar el recorrido en postorden.
     * 
     * En el recorrido en postorden se procesan primero el subárbol izquierdo y derecho, y por último el nodo actual.
     *
     * @param node El nodo actual desde el cual se comienza el recorrido.
     * @return Un StringBuilder con los elementos en postorden.
     */
    protected StringBuilder postOrden(NodeTree<E> node) {
        
        StringBuilder sb = new StringBuilder();

        if (node != null) {
            sb.append(postOrden(node.getLeft()));         // Subárbol izquierdo
            sb.append(postOrden(node.getRight()));        // Subárbol derecho
            sb.append(node.getData()).append(" ");      // Nodo actual
        }
        return sb;
    }

    /**
     * Obtiene el valor mínimo almacenado en el árbol binario de búsqueda.
     * 
     * @return El elemento mínimo.
     */
    @Override
    public E getMin() {
        return this.getNodeMin(this.root).getData();
    }

    /**
     * Método auxiliar que encuentra el nodo con el valor mínimo en el subárbol dado.
     *
     * @param rootSub La raíz del subárbol donde se buscará el valor mínimo.
     * @return El nodo que contiene el valor mínimo.
     */
    protected NodeTree<E> getNodeMin(NodeTree<E> rootSub) {
        NodeTree<E> actual = rootSub;

        while(actual.getLeft() != null) {
            actual = actual.getLeft();
        }

        return actual;
    }

    /**
     * Obtiene el valor máximo almacenado en el árbol binario de búsqueda.
     * 
     * @return El elemento máximo.
     */
    @Override
    public E getMax() {
        return this.getNodeMax(this.root).getData();
    }

    /**
     * Método auxiliar que encuentra el nodo con el valor máximo en el subárbol dado.

     * @param rootSub La raíz del subárbol donde se buscará el valor máximo.
     * @return El nodo que contiene el valor máximo.
     */
    protected NodeTree<E> getNodeMax(NodeTree<E> rootSub) {
        NodeTree<E> actual = rootSub;

        while(actual.getRight() != null) {
            actual = actual.getRight();
        }

        return actual;
    }

    /**
     * Calcula el "área" del árbol binario de búsqueda, definida como:
     * <pre>
     * área = altura del árbol * cantidad de nodos hoja
     * </pre>
     * 
     * La altura se calcula de forma iterativa desde la raíz del árbol.
     * 
     * @return el área del árbol.
     * @throws ExceptionIsEmpty si el árbol está vacío.
     */
    public int areaBST() throws ExceptionIsEmpty {
        if(this.isEmpty()) {
            throw new ExceptionIsEmpty("El arbol esta vacio");
        }

        int altura = this.height(this.root.getData());
        int numLeafNodes = this.countLeafNodes(this.root);

        return altura * numLeafNodes;
    }

    /**
     * Cuenta la cantidad de nodos hoja en el subárbol con la raíz dada.
     * Un nodo hoja es aquel que no tiene hijos izquierdo ni derecho.
     *
     * @param node la raíz del subárbol a evaluar.
     * @return la cantidad total de nodos hoja.
     */
    protected int countLeafNodes(NodeTree<E> node) {

        QueueLink<NodeTree<E>> cola = new QueueLink<>();
        cola.enqueue(node);

        int cantidadHojas = 0;

        while (!cola.isEmpty()) {
            NodeTree<E> nodo = cola.dequeue();

            // Si el nodo es hoja, se incrementa el contador
            if (isLeaf(nodo)) {
                cantidadHojas++;
            }

            // Si tiene hijo izquierdo, se encola
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

    /**
     * Determina si un nodo es hoja.
     * 
     * @param node el nodo a evaluar.
     * @return true si el nodo no tiene hijos, false en caso contrario.
     */
    protected boolean isLeaf(NodeTree<E> node) {
        return node.getLeft() == null && node.getRight() == null;
    } 

    //Representación en cadena de texto del arbol
    @Override
    public String toString(){
        return "[ " + this.inOrden(this.root) + "]";
    }
}
