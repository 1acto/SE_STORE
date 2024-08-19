import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //Prepare to read file
        File file = new File("src/PRODUCT.txt");
        Scanner fileScanner = new Scanner(file);
        //Set up scanner for user input
        Scanner kb = new Scanner(System.in);
        //Set up array list to store products
        ArrayList<Product> products = new ArrayList<>();
        //Read file and put products in array list
        while (fileScanner.hasNextLine()) {
            String id = fileScanner.next();
            String name = fileScanner.next();
            String price = fileScanner.next();
            String quantity = fileScanner.next();
            Product importedProduct = new Product(id,name, price, quantity);
            products.add(importedProduct);
        }
        fileScanner.close();
        //Display#1 Menu for user
        int choice = 0;
        while (true){
            //main menu
            System.out.print("===== SE STORE =====\n" +
                    "1. Show Product\n" +
                    "2. Exit\n" +
                    "====================\n" +
                    "Select (1-2) : ");
            choice = kb.nextInt();
            if(choice == 1){
                //Display#2 Products
                //header
                System.out.println("=========== SE STORE's Products ===========");
                System.out.printf("%-4s %-15s %-10s %-4s","#", "Name", "Price", "Quantity");
                System.out.println();
                int i = 1;
                //Display products
                for (Product product : products) {
                    System.out.printf("%-4s %-15s %-10s %-4s", i , product.getName() , "$" + product.getPrice(), product.getQuantity());
                    System.out.println();
                    i++;
                }
                //footer
                System.out.println("===========================================");
            }else if (choice == 2){
                //Display#3 Exit
                System.out.print("===== SE STORE =====\n" +
                        "Thank you for using our service :3");
                break;
            }else{
                //A+ Requirement
                System.out.println("!!! Error: Input only 1 or 2 !!!");
            }
        }
    }

}
