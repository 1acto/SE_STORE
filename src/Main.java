/************************************************************************************/
/* Program Assignment: SE_STORE#2 */
/* Student ID: 66160109 */
/* Student Name: Patyot Sompran */
/* Date: 21 Aug 2024 */
/* Description: a program for showing detail about product in shop */
/*************************************************************************************/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //Prepare to read file
        File productFile = new File("src/PRODUCT.txt");
        File categoryFile = new File("src/CATEGORY.txt");
        Scanner productScanner = new Scanner(productFile);
        Scanner categoryScanner = new Scanner(categoryFile);
        //Set up scanner for user input
        Scanner kb = new Scanner(System.in);
        //Set up array list to store products
        ArrayList<Product> products = new ArrayList<>();
        //Read file and put products in array list
        while (productScanner.hasNextLine()) {
            String id = productScanner.next();
            String name = productScanner.next();
            String price = productScanner.next();
            String quantity = productScanner.next();
            String type = productScanner.next();
            Product importedProduct = new Product(id, name, price, quantity, type);
            products.add(importedProduct);
        }
        //Set up array list to store categories
        ArrayList<Category> categories = new ArrayList<>();
        while (categoryScanner.hasNextLine()) {
            String temp = categoryScanner.nextLine();
            //spilt the line into id and name
            String[] split = temp.split("\t");
            String id = split[0];
            String name = split[1];
            Category importedCategory = new Category(id, name);
            categories.add(importedCategory);
        }
        productScanner.close();
        categoryScanner.close();
        //Menu
        String choice = "";
        while (true) {
            //Display#1 Menu
            System.out.print("===== SE STORE =====\n" +
                    "1. Show Category\n" +
                    "2. Exit\n" +
                    "====================\n" +
                    "Select (1-2) : ");
            try {
                choice = kb.next();
                if (choice.equals("1")) {
                    while (true) {
                        //Display#2 Show Category
                        System.out.println("===== SE STORE's Product Categories =====");
                        System.out.printf("%-4s %-15s", "#", "Category Name");
                        System.out.println();
                        int i = 1;
                        //Print all categories
                        for (Category category : categories) {
                            System.out.printf("%-4s %-15s", i, category.getName());
                            System.out.println();
                            i++;
                        }
                        System.out.println("===========================================");
                        System.out.print("Select Category to Show Product (1-" + (categories.size()) + ") or Q for exit \n" +
                                "select : ");
                        try {
                            String categoryChoice = kb.next();
                            //If user input Q, exit
                            if (categoryChoice.equalsIgnoreCase("q")) {
                                break;
                                //If user input number, show product in that category
                            } else if (Integer.parseInt(categoryChoice) - 1 <= categories.size()) {
                                System.out.println("============ " + categories.get(Integer.parseInt(categoryChoice) - 1).getName() + " ============");
                                System.out.printf("%-4s %-15s %-15s %-15s", "#", "Name", "Price", "Quantity");
                                System.out.println();
                                //Print all products in that category
                                int index = 1;
                                for (int j = 0; j < products.size(); j++) {
                                    if (products.get(j).getType().equals(categories.get(Integer.parseInt(categoryChoice) - 1).getId())) {
                                        System.out.printf("%-5s", index);
                                        products.get(j).printItem();
                                        index++;
                                    }
                                }
                                System.out.println("================================");
                                //Exit to display#2
                                while (true) {
                                    System.out.print("Press Q to Exit: ");
                                    String exitChoice = kb.next();
                                    if (exitChoice.equalsIgnoreCase("q")) {
                                        break;
                                    }
                                    else {
                                        System.out.println("!!! Error: Input only Q for exit !!!");
                                    }
                                }
                            }
                        }catch (Exception e){
                            System.out.println("!!! Error: Input only 1-" + categories.size() + " or Q for exit !!!");
                        }
                    }
                } else if (choice.equals("2")) {
                    //Display Exit quote
                    System.out.print("===== SE STORE =====\n" +
                            "Thank you for using our service :3");
                    System.exit(0);
                } else {
                    System.out.println("!!! Error: Input only 1 or 2 !!!");
                }
            }catch (Exception e){
                System.out.println("!!! Error: Input only 1 or 2 !!!");
            }
        }
    }
}