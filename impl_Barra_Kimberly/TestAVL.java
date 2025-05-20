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

            System.out.println("\nCaso 2: RSL");
            avl.insert(40);
            avl.insert(50);  
            System.out.println(avl);

            System.out.println("\nCaso 3: RDR");
            avl.insert(25);   
            System.out.println(avl);

            System.out.println("\nCaso 4: RDL");
            avl.insert(45);
            avl.insert(48);  
            System.out.println(avl);

            System.out.println("\nCaso 5: RSR");
            avl.insert(5);
            avl.insert(1);
            System.out.println(avl);

            System.out.println("\nCaso 6: RSL");
            avl.insert(60);
            avl.insert(70);
            System.out.println(avl);

            System.out.println("\nCaso 7: RDR");
            avl.insert(23);
            System.out.println(avl);

            System.out.println("\nCaso 8: RDL");
            avl.insert(46);
            System.out.println(avl);

        } catch (ExceptionItemDuplicated e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
