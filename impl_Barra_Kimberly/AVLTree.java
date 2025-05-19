package avltree;

import BinarySearchTree.LinkedBST;
import Excepciones.ExceptionItemDuplicated;
import Nodes.NodeAVL;
import Nodes.NodeTree;

public class AVLTree<E extends Comparable <E>> extends LinkedBST<E> {
	//definomos la clase NodeAVL en otro paquete
	private boolean height; //indicador de cambio de altura
	
	public void insert(E x) throws ExceptionItemDuplicated {
		this.height = false;
		this.root = insert(x, (NodeAVL<E>)this.root);
		
	}
	protected NodeTree<E> insert(E x, NodeAVL<E> node) throws ExceptionItemDuplicated{
		NodeAVL<E> fat = node;
		
		if (node == null) {
			this.height = true;
			fat = new NodeAVL<E> (x);
		}
		else {
			int resC = node.data.compareTo(x);
			if(resC == 0) throw new ExceptionItemDuplicated(x+" ya se ecnuetra en el arbol");
			if(resC < 0) {
				fat.right = insert(x, (NodeAVL<E>)node.right);
				if(this.height) {
					switch (fat.bf) {
					case -1:	
								fat.bf = 0;
								this.height = false;
								break;
					case 0:
								fat.bf = 1;
								this.height=true;
								break;
					case 1: //bf = 2
								fat = balanceToLeft (fat);
								this.height = false;
								break;
					}					
				}
					
			}else {
				fat.left = insert(x, (NodeAVL<E>) node.left);
				if (this.height) {
					switch (fat.bf) {
					case -1:
								fat.bf = 0;
								this.height = false;
								break;
					case 0: 
								fat.bf = -1;
								this.height=true;
								break;
					case 1:
								fat = balanceToRight(fat);
								this.height = false;
								break;
								
					}
				}
				
			}

		}
		return fat;
	}
	
	private NodeAVL<E> balanceToLeft(NodeAVL<E> node) {
		NodeAVL<E> hijo = (NodeAVL<E>)node.right;
		switch(hijo.bf) {
		case 1:
					node.bf = 0;
					hijo.bf = 0;
					node = rotateSL(node);
					break;
		case -1:
					NodeAVL<E> nieto = (NodeAVL<E>)hijo.left;
					switch(nieto.bf) {
						case -1: node.bf = 0; hijo.bf = 1; break;
						case 0 : node.bf = 0; hijo.bf = 0; break;
						case 1: node.bf = 1; hijo.bf = 0; break;
					}
					nieto.bf = 0;
					
					node.right=rotateSR(hijo);
					node = rotateSL(node);
					break;
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
	
	private NodeAVL<E> rotateSL (NodeAVL<E> node) {
		NodeAVL<E> p = (NodeAVL<E>)node.right;
		node.right=p.left;
		p.left=node;
		node = p;
		return node;
		
	}
	
	private NodeAVL<E> rotateSR (NodeAVL<E> node) {
		NodeAVL<E> p = (NodeAVL<E>)node.left;
		node.left=p.right;
		p.right=node;
		node = p;
		return node;
	}
	
}

		
