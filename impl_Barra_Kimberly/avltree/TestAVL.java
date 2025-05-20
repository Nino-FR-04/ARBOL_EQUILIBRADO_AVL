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

            System.out.println("\n-------------------------------------------------------------------------");
            System.out.println("Caso 4: RDL");
            avl.insert(45);
            avl.insert(48);
            System.out.println(avl);
            System.out.println("\n-------------------------------------------------------------------------");

            System.out.println("Caso 5: RSR (5, 3)");
            avl.insert(5);
            avl.insert(3); 
            System.out.println(avl);

            System.out.println("\n-------------------------------------------------------------------------");

            System.out.println("Caso 6: RSL (60, 70)");
            avl.insert(60);
            avl.insert(70); 
            System.out.println(avl);

            System.out.println("\n-------------------------------------------------------------------------");
            System.out.println("Caso 7: RDR (4)");
            avl.insert(4); 
            System.out.println(avl);

            System.out.println("\n-------------------------------------------------------------------------");

            System.out.println("Caso 8: RDL (65)");
            avl.insert(65); 
            System.out.println(avl);


        } catch (ExceptionItemDuplicated e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}

