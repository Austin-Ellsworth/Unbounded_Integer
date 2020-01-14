//Author: Austin Ellsworth

import java.util.Scanner;
import java.util.InputMismatchException;

public class LargeNumberTest {
	
	public static void main(String [] args) {
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Welcome to my UnboundedInt Test program!");
		
		UnboundedInt bigInt1 = new UnboundedInt("5");
		UnboundedInt bigInt2 = new UnboundedInt("5");
		
		int choice = 0;
		while(choice != 7) {
			choice = menu();
			switch(choice) {
			case 1: 
				System.out.println("1. Display both numbers (with commas)");
				System.out.println();
				System.out.println("1:  " + bigInt1.toString());
				System.out.println("2:  " + bigInt2.toString());
				System.out.println();
				break;
				
			case 2:
				System.out.println("2. Input two new numbers (without commas)");
				System.out.print("First Number: ");
				bigInt1 = new UnboundedInt(keyboard.nextLine());
				System.out.print("Second Number: ");
				bigInt2 = new UnboundedInt(keyboard.nextLine());
				System.out.println();
				break;
				
			case 3:
				System.out.println("3. Check if numbers are equal");
				System.out.println(bigInt1.equals(bigInt2));
				System.out.println();
				break;
				
			case 4:
				System.out.println("4. Report the sum of the two numbers");
				System.out.println(bigInt1.add(bigInt2));
				System.out.println();
				break;
				
			case 5:
				System.out.println("5. Report the multiplication of the two numbers");
				System.out.println(bigInt1.multiply(bigInt2));
				System.out.println();
				break;
				
			case 6:
				System.out.println("6. Create and output the clone of the first number");
				System.out.println((bigInt1.clone()).toString());
				System.out.println();
				break;
				
			case 7: 
				System.out.println("7. Quit");
				
				System.out.println("Thank you for using my program.");
				break;
					
			}
		}
	}


	public static int menu() {
	    int choice = 0;
	    Scanner keyboard = new Scanner(System.in);
	    while(choice < 1 || choice > 16) {
	    	System.out.println("1. Display both numbers (with commas)");
	    	System.out.println("2. Input two new numbers (without commas)");
	    	System.out.println("3. Check if numbers are equal");
	    	System.out.println("4. Report the sum of the two numbers");
	    	System.out.println("5. Report the multiplication of the two numbers");
	    	System.out.println("6. Create and output the clone of the first number");
	    	System.out.println("7. Quit");
	        System.out.print("Choice : ");
	       try {
	          choice = keyboard.nextInt();
	       }
	       catch(InputMismatchException e) {
	          System.out.println("Please enter a number!");
	       }
	       keyboard.nextLine(); //clear buffer
	       System.out.println();
	    }
	    return choice;
	}//end menu
}
