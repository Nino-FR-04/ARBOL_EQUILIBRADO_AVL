package avltree;

public class AVLTREE<E extends Comparable<E>> extends LinkedBST<E> {
	private boolean height;  
	public void insert(E x) throws ItemDuplicated{
		this.height=false ; 
		this.root=insert(x,(NodeAVL<E>)this.root); 
	}
	protected NodeTree<E> insert(E x,NodeAVL<E> node)throws ItemDuplicated{
		NodeAVL<E> fat=node; 
		if (node == null) {
			this.height = true;
			fat = new NodeAVL<E> (x);
		}
		else {
			int resC=node.data.compareTo(x); 
			if(resC==0) {
				throw new ItemDuplicated (x+"no se puede ingresar valores igaules"); 
			}
			if (resC<0) {
				fat.right=insert(x,(NodeAVL<E>)node.right); 
				if(this.height) {
					switch(fat.bf) {
					case -1: 
						fat.bf=0; 
						this.height=false; 
						break; 
					case 0:
						fat.bf=1; 
						this.height=true; 
						break;
					case 1:
						fat.bf=2; 
						balanceToLeft(fat);
						this.height=false; 
						break;
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
	
	
	
