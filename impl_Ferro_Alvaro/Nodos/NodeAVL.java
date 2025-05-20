package avltree;

public class NodeAVL<E extends Comparable<E>> extends NodeTree<E>{
		protected int bf; 
		
		public NodeAVL(E data) {
			super(data);
			this.bf=0; 	
		}
		private int getBf() {
			return this.bf; 
		}
		private void setBf() {
			this.bf=bf; 
		}
		@Override 
		public String toString() {
			return "su valor es " + this.data +"su factor de equilibrio" + bf; 
		}
	}
	

