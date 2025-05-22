package avltree;

import Excepciones.ExceptionItemDuplicated;

public class TestAVL {
	public static void main(String[] args) {
        try {
            AVLTree<Integer> avl = new AVLTree<>();

            System.out.println("Caso 1: RSR");
            avl.insert(30);
            avl.insert(20);
            avl.insert(10);
            System.out.println(avl);
            System.out.println("\n-------------------------------------------------------------------------");
            System.out.println("Caso 2: RSL");
            avl.insert(40);
            avl.insert(50);
            System.out.println(avl);       
            System.out.println("\n-------------------------------------------------------------------------");
            System.out.println("Caso 3: RDR");
            avl.insert(25);   
            System.out.println(avl);

            avl.remove(20);;
            System.out.println(avl);


        } catch (ExceptionItemDuplicated e) {
            System.out.println("error: " + e.getMessage());
        }
        
    }
}

