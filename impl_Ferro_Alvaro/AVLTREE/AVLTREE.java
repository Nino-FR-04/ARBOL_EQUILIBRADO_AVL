package avltree;

public class AVLTREE<E extends Comparable<E>> extends LinkedBST<E> {
	private boolean height;  
	public void insert(E x) throws ItemDuplicated{
		this.height=false ; 
		this.root=insert(x,(NodeAVL<E>)this.root); 
	}
	protected NodeTree<E> insert(E x, NodeAVL<E> node) throws ItemDuplicated {
	    NodeAVL<E> fat = node;
	    if (node == null) {
	        this.height = true;
	        fat = new NodeAVL<E>(x);
	    } else {
	        int resC = node.data.compareTo(x);
	        if (resC == 0) {
	            throw new ItemDuplicated(x + " no se puede ingresar valores iguales");
	        }

	        if (resC > 0) { // Insertar en izquierda
	            fat.left = insert(x, (NodeAVL<E>) node.left);
	            if (this.height) {
	                switch (fat.bf) {
	                    case -1:   //Caso 1: antes el padre tenía bf = -1 (subárbol izquierdo más alto)
	                        fat.bf = 0;
	                        this.height = false;
	                        break;
	                    case 0:  //Caso 2: antes el padre tenía bf	 = 0 (perfectamente equilibrado)
	                        fat.bf = -1;
	                        this.height = true;
	                        break;
	                    case 1:  // Caso 3: el subárbol derecho era más alto, ahora el desequilibrio es hacia la izquierda
	                        fat.bf = -2;
	                        fat = balanceToRight(fat);
	                        this.height = false;
	                        break;
	                }
	            }
	        } else {
	            fat.right = insert(x, (NodeAVL<E>) node.right);
	            if (this.height) {
	                switch (fat.bf) {
	                    case -1:
	                        fat.bf = 0;
	                        this.height = false;
	                        break;
	                    case 0:
	                        fat.bf = 1;
	                        this.height = true;
	                        break;
	                    case 1:
	                        fat.bf = 2;
	                        fat = balanceToLeft(fat); // CORREGIDO: ahora se guarda el nuevo nodo
	                        this.height = false;
	                        break;
	                }
	            }
	        }
	    }
	    return fat;
	}
		private NodeAVL balanceToLeft(NodeAVL node){
			NodeAVL hijo = (NodeAVL)node.right;
			switch(hijo.bf) {
			case 1: 
				node.bf = 0;
				hijo.bf = 0;
				node = rotateSL(node);
				break;
			case -1:
				NodeAVL nieto = (NodeAVL)hijo.left;
				switch(nieto.bf) {
				case -1: node.bf = 0; hijo.bf = 1; break;
				case 0: node.bf = 0; hijo.bf = 0; break;
				case 1: node.bf = 1; hijo.bf = 0; break;
				}
					nieto.bf =0;
					node.right =rotateSR(hijo); 
					node =rotateSL(node);
				}
				return node;
				}	
		private NodeAVL<E> balanceToRight(NodeAVL<E> node) {
			NodeAVL<E> hijo = (NodeAVL<E>)node.left;
			switch(hijo.bf) {
			case 1:
						node.bf = 0;
						hijo.bf = 0;
						node = rotateSR(node);
						break;
			case -1:
						NodeAVL<E> nieto = (NodeAVL<E>)hijo.right;
						int bfNieto = (nieto == null) ? 0:nieto.bf;
						switch(bfNieto) {
							case -1: node.bf = 0; hijo.bf = -1; break;
							case 0: node.bf = 0; hijo.bf = 0; break;
							case 1: node.bf = -1; hijo.bf = 0; break;
						}
						if (nieto != null) {
							nieto.bf = 0;
						}
						
						node.left=rotateSR(hijo);
						node = rotateSL(node);
						break;
			}
			return node;
		}
		private NodeAVL<E> rotateSL(NodeAVL<E> node) {
		    if (node == null || node.right == null) return node; // Protección contra null
		    NodeAVL<E> p = (NodeAVL<E>) node.right;
		    node.right = p.left;
		    p.left = node;
		    return p;
		}
		private NodeAVL<E> rotateSR(NodeAVL<E> node) {
		    if (node == null || node.left == null) return node; // Protección contra null
		    NodeAVL<E> p = (NodeAVL<E>) node.left;
		    node.left = p.right;
		    p.right = node;
		    return p;
		}
		
		@Override
		public void delete(E x) throws ExceptionIsEmpty, ItemNoFound {
		    if (isEmpty()) {
		        throw new ExceptionIsEmpty("arbol vacio");
		    }
		    root = deleteRecursivo(root, x);
		}
		protected NodeAVL<E> deleteRecursivo(NodeAVL<E> node, E x) {
		    if (node == null) return null;

		    int cmp = x.compareTo(node.data);
		    if (cmp < 0) {
		        node.left = deleteRecursivo((NodeAVL<E>) node.left, x);
		    } else if (cmp > 0) {
		        node.right = deleteRecursivo((NodeAVL<E>) node.right, x);
		    } else {
		        // Nodo a eliminar encontrado
		        if (node.left == null && node.right == null) return null;
		        if (node.left == null) return (NodeAVL<E>) node.right;
		        if (node.right == null) return (NodeAVL<E>) node.left;

		        NodeAVL<E> minNodeRight = (NodeAVL<E>) getNodeMin((NodeAVL<E>) node.right);
		        node.data = minNodeRight.data;
		        node.right = deleteRecursivo((NodeAVL<E>) node.right, minNodeRight.data);
		    }

		    actualizarBF(node);

		    if (node.bf == 2) { // Desbalance a la izquierda
		        NodeAVL<E> hijoIzquierdo = (NodeAVL<E>) node.left;
		        if (hijoIzquierdo.bf >= 0) {
		            node = rotateSR(node);
		        } else {
		            node = balanceToRight(node);
		        }
		    } else if (node.bf == -2) { // Desbalance a la derecha
		        NodeAVL<E> hijoDerecho = (NodeAVL<E>) node.right;
		        if (hijoDerecho.bf <= 0) {
		            node = rotateSL(node);
		        } else {
		            node = balanceToLeft(node);
		        }
		    }

		    return node;
		}
		public int height() {
		    return calcularAltura(root);
		}

		private int calcularAltura(NodeTree<E> nodo) {
		    if (nodo == null) return -1;
		    return 1 + Math.max(calcularAltura(nodo.getLeft()), calcularAltura(nodo.getRight()));
		}
		
		public void recorridoPorNiveles() {
		    int altura = height();
		    for (int nivel = 0; nivel <= altura; nivel++) {
		        recorrerNivel(root, nivel);
		    }
		}
		private void recorrerNivel(NodeTree<E> nodo, int nivel) {
		    if (nodo == null) return;

		    if (nivel == 0) {
		        System.out.print(nodo.getData() + " ");
		    } else {
		        recorrerNivel(nodo.getLeft(), nivel - 1);
		        recorrerNivel(nodo.getRight(), nivel - 1);
		    }
		}
		
		public void preorden() {
		    preordenRec(root);
		}

		private void preordenRec(NodeTree<E> nodo) {
		    if (nodo == null) return;
		    System.out.print(nodo.getData() + " ");  // Visitar nodo raíz
		    preordenRec(nodo.getLeft());             // Recorrer subárbol izquierdo
		    preordenRec(nodo.getRight());            // Recorrer subárbol derecho
		}

		public void printInOrder() {
		    printInOrder((NodeAVL<E>) this.root);
		}

		private void printInOrder(NodeAVL<E> node) {
		    if (node != null) {
		        printInOrder((NodeAVL<E>) node.left);
		        System.out.println("Nodo: " + node.data + " | BF: " + node.bf);
		        printInOrder((NodeAVL<E>) node.right);
		    }
		}
		public void printTree() {
		    printTree((NodeAVL<E>) this.root, 0);
		}

		private void printTree(NodeAVL<E> node, int level) {
		    if (node == null) return;
		    printTree((NodeAVL<E>) node.right, level + 1);  // Primero el hijo derecho (se imprime más arriba)
		    System.out.println("    ".repeat(level) + node.data + " (bf=" + node.bf + ")");
		    printTree((NodeAVL<E>) node.left, level + 1);   // Luego el hijo izquierdo
		}
		private void actualizarBF(NodeAVL<E> node) {
		    if (node == null) return;
		    int alturaIzq = height((NodeAVL<E>) node.left);
		    int alturaDer = height((NodeAVL<E>) node.right);
		    node.bf = alturaIzq - alturaDer;
		}
		private int height(NodeAVL<E> node) {
	        if (node == null) return -1;
	        return 1 + Math.max(height((NodeAVL<E>) node.left), height((NodeAVL<E>) node.right));
	    }

	}
	
	
	
