/**
 * Program Assignment: SE_STORE
 * Student ID: 66160109
 * Student Name: Patyot Sompran
 * Project Start Date: 21 Aug 2024
 * Last Modified: 02 Sep 2024
 * Version: #8
 * Description: Store Management System.
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
            //Setting up
        try {
            //Set up scanner
            Scanner productScanner = new Scanner(new File("src/PRODUCT.txt"));
            Scanner categoryScanner = new Scanner(new File("src/CATEGORY.txt"));
            Scanner memberScanner = new Scanner(new File("src/MEMBER.txt"));
            Scanner userInput = new Scanner(System.in);

            //Read product.txt
            ArrayList<Product> products = new ArrayList<>();
            while (productScanner.hasNextLine()) {
                String line = productScanner.nextLine();
                String[] tokens = line.split("\t"); // Assuming tab-separated values
                if (tokens.length >= 5) {
                    products.add(new Product(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]));
                }
            }
            productScanner.close();

            //Read category.txt
            ArrayList<Category> categories = new ArrayList<>();
            while (categoryScanner.hasNextLine()) {
                String[] split = categoryScanner.nextLine().split("\t");
                if (split.length >= 2) { categories.add(new Category(split[0], split[1])); }
            }
            categoryScanner.close();

            //Read member.txt
            ArrayList<Member> members = new ArrayList<>();
            while (memberScanner.hasNext()) {
                String[] split = memberScanner.nextLine().split("\t");
                if (split.length >= 7) { members.add(new Member(split[0], split[1], split[2], split[3], split[4], split[5], split[6])); }
            }
            memberScanner.close();

            //Main Program
            while (true) {
                System.out.print("\n===== SE STORE =====\n1. Login\n2. Exit\n====================\nSelect (1-2) : ");
                String choice = userInput.next();
                if (choice.equals("1")) {
                    int failCount = 0;
                    while (true) {
                        System.out.print("\n===== LOGIN =====\nEmail: ");
                        String email = userInput.next();
                        System.out.print("Password: ");
                        String password = userInput.next();
                        System.out.println("====================");
                        boolean accFound = false;
                        for (Member member : members) {
                            //if email and password match
                            if (email.equals(member.getEmail()) && member.checkPassword(password)) {
                                //expire account check
                                if (member.getStatus() == '0') {
                                    accFound = true;
                                    System.out.println("Error! - Your Account are Expired! ");
                                    break;
                                }
                                accFound = true;
                                while (true) {
                                    System.out.print("\n===== SE STORE =====\nHello, " + member.getCensoredName());
                                    int role = member.getRoleID() - '0';
                                    switch (role) {
                                        case 0 -> System.out.print(" (Staff)\n");
                                        case 1 -> System.out.print(" (Regular)\n");
                                        case 2 -> System.out.print(" (Sliver)\n");
                                        case 3 -> System.out.print(" (Gold)\n");
                                    }
                                    System.out.println("Email: " + member.censorEmail());
                                    System.out.println("Phone: " + member.getPhoneNumber());
                                    System.out.println("You have " + member.getPoint() + " points");
                                    System.out.println("====================");

                                    int menuIndex = 1;
                                    System.out.println(menuIndex + ". Show Category");
                                    if (role == 0) {
                                        menuIndex++;
                                        System.out.println(menuIndex + ". Add Member");
                                        menuIndex++;
                                        System.out.println(menuIndex + ". Edit Member");
                                        menuIndex++;
                                        System.out.println(menuIndex + ". Edit Product");
                                    }
                                    if(role != 0){
                                        menuIndex++;
                                        System.out.println(menuIndex + ". Order Product");
                                    }
                                    menuIndex++;
                                    System.out.println(menuIndex + ". Logout ");
                                    System.out.println("====================");
                                    System.out.print("Select (1-" + menuIndex + ") : ");
                                    choice = userInput.next();
                                    //show category
                                    if (choice.equals("1")) {
                                        while (true) {
                                            System.out.println("===== SE STORE's Product Categories =====");
                                            System.out.printf("%-4s %-15s\n", "#", "Category Name");
                                            int k = 1;
                                            for (Category category : categories) {
                                                System.out.printf("%-4s %-15s\n", k, category.getName());
                                                k++;
                                            }
                                            System.out.println("===========================================");
                                            System.out.print("Select Category to Show Product (1-" + categories.size() + ") or Q for exit \nselect : ");
                                            String categoryChoice = userInput.next();
                                            if (categoryChoice.equalsIgnoreCase("q")) break;
                                            try {
                                                int categoryIndex = Integer.parseInt(categoryChoice) - 1;
                                                if (categoryIndex < categories.size()) {
                                                    System.out.println("============ " + categories.get(categoryIndex).getName() + " ============");
                                                    List<Product> productLists = new ArrayList<>();
                                                    for (Product product : products) {
                                                        if (product.getType().equals(categories.get(categoryIndex).getId())) {
                                                            productLists.add(product);
                                                        }
                                                    }
                                                    printItem(productLists, role);
                                                    while (true) {
                                                        System.out.print("1. Show Name By DESC\n2. Show Quantity By ASC\nor Press Q to Exit : ");
                                                        String sortChoice = userInput.next();
                                                        if (sortChoice.equals("1")) {
                                                            productLists.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
                                                            System.out.println("============ " + categories.get(categoryIndex).getName() + " ============");
                                                            printItem(productLists, role);
                                                        } else if (sortChoice.equals("2")) {
                                                            productLists.sort(Comparator.comparingInt(Product::getQuantity));
                                                            System.out.println("============ " + categories.get(categoryIndex).getName() + " ============");
                                                            printItem(productLists, role);
                                                        } else if (sortChoice.equalsIgnoreCase("q")) {
                                                            break;
                                                        } else {
                                                            System.out.println("!!! Error: Input only 1-3 !!!");
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                System.out.println("!!! Error: Input only 1-" + categories.size() + " or Q for exit !!!");
                                            }
                                        }
                                    }
                                    //add member
                                    else if (choice.equals("2") && role == 0) {
                                        System.out.print("===== Add Member =====\nEnter Firstname: ");
                                        String firstname = userInput.next();
                                        System.out.print("Enter Lastname: ");
                                        String lastname = userInput.next();
                                        System.out.print("Enter Email: ");
                                        String inputEmail = userInput.next();
                                        System.out.print("Enter Phone Number: ");
                                        String phoneNumber = userInput.next();
                                        if (firstname.length() < 2 || lastname.length() < 2 || inputEmail.length() < 2 || !inputEmail.contains("@") || phoneNumber.length() < 10) {
                                            System.out.println("Error! - Your Information are Incorrect!");
                                        } else {
                                            int latestID = getLatestID(members);
                                            String newPassword = generatePassword();
                                            Member newMember = new Member(String.valueOf(latestID), firstname, lastname, inputEmail, newPassword, phoneNumber, "0");
                                            members.add(newMember);
                                            System.out.println("Added member successfully!");
                                            System.out.println(newMember.getFirstname() + "'s Password is " + newMember.Password);
                                            try (FileWriter fileWriter = new FileWriter("src/MEMBER.txt", true)) {
                                                fileWriter.write("\n" + newMember.id + "\t" + newMember.Firstname + "\t" + newMember.Lastname + "\t" + newMember.Email + "\t" + newMember.rawPassword + "\t" + newMember.PhoneNumber + "\t" + newMember.point);
                                            } catch (Exception e) {
                                                System.out.println("!!! Error: Cannot write to file !!!");
                                            }
                                        }
                                    }
                                    //order product
                                    else if (choice.equals("2") ) {
                                        printItem(products, role);
                                        ArrayList<Product> charts = new ArrayList<>();
                                        System.out.print("""
                                                    Enter the product number followed by the quantity.
                                                    1. How to Order
                                                    2. List Products
                                                    Q. Exit
                                                    """);
                                        while (true) {
                                            System.out.print("Enter : ");
                                            String orderChoice = userInput.next();
                                            if (orderChoice.equals("1")) {
                                                System.out.println("""
                                                        How to Order:
                                                        • To Add Product:
                                                        \tEnter the product number followed by the quantity.
                                                        \tExample: 1 50 (Adds 50 chips)
                                                        • To Adjust Quantity:
                                                        \t+ to add more items: 1 +50 (Adds 50 more chips)
                                                        \t- to reduce items: 1 -50 (Removes 50 chips)""");
                                            }
                                            else if (orderChoice.equals("2")) {
                                                printItem(products, role);
                                                userInput.nextLine();
                                                while (true) {
                                                    System.out.print("Enter : ");
                                                    String[] usrInput = userInput.nextLine().split(" ");
                                                    if (usrInput[0].equalsIgnoreCase("q")) {
                                                        break;
                                                    }
                                                    if (usrInput.length != 2) {
                                                        System.out.println("Your input is invalid!");
                                                        continue;
                                                    }
                                                    try {
                                                        String productID = usrInput[0];
                                                        String Quantity = usrInput[1];
                                                        if (Quantity.charAt(0) != '-' && Quantity.charAt(0) != '+') {
                                                            Quantity = "+" + Quantity;
                                                        }
                                                        if (charts.isEmpty()) {
                                                            charts.add(products.get(Integer.parseInt(productID) - 1));
                                                            charts.getFirst().setZeroQuantity();
                                                            charts.getFirst().setQuantity(Integer.parseInt(Quantity.substring(1)), Quantity.charAt(0));
                                                            System.out.println("Product " + charts.getFirst().getName() + " " + charts.getFirst().getQuantity());
                                                        } else {
                                                            boolean found = false;
                                                            int location = -1;
                                                            for (Product chart : charts) {
                                                                if (chart.getId().equals(products.get(Integer.parseInt(productID) - 1).getId())) {
                                                                    found = true;
                                                                    //get location
                                                                    location = charts.indexOf(chart);
                                                                    break;
                                                                }
                                                            }
                                                            if (found) {
                                                                charts.get(location).setQuantity(Integer.parseInt(Quantity.substring(1)), Quantity.charAt(0));
                                                                System.out.println("Product " + charts.get(location).getName() + " " + charts.get(location).getQuantity());
                                                            }
                                                            if (!found) {
                                                                charts.add(products.get(Integer.parseInt(productID) - 1));
                                                                charts.getLast().setZeroQuantity();
                                                                charts.getLast().setQuantity(Integer.parseInt(Quantity.substring(1)), Quantity.charAt(0));
                                                                System.out.println("Product " + charts.getLast().getName() + " " + charts.getLast().getQuantity());
                                                            }
                                                        }
                                                    }catch (Exception e){
                                                        System.out.println("Your input is invalid!");
                                                    }
                                                }
                                                if (!charts.isEmpty()) {
                                                    try(FileWriter fileWriter = new FileWriter("src/CART.txt", true)) {
                                                        for (Product chart : charts) {
                                                            fileWriter.write("\n" + member.getId() + "\t" + chart.getId() + "\t" + chart.getQuantity());
                                                        }

                                                    }
                                                    catch (Exception e) {
                                                        System.out.println("!!! Error: Cannot write to file !!!");
                                                    }
                                                    System.out.println("Your cart has been saved!");
                                                }
                                                break;
                                            }
                                            else if (orderChoice.equalsIgnoreCase("q")) {
                                                break;
                                            }
                                            else {
                                                System.out.println("!!! Error: Input only 1-2 or Q for exit !!!");
                                            }
                                        }
                                    }
                                    //edit member
                                    else if (choice.equals("3") && role == 0) {
                                        while (true) {
                                            System.out.println("===== SE STORE's Member =====");
                                            //Print all member
                                            System.out.printf("%-4s %-25s %-30s\n", "#", "Name", "Email");
                                            int memberIndex = 1;
                                            for (Member member1 : members) {
                                                //#	Name			Email
                                                System.out.printf("%-4s %-25s %-30s\n", memberIndex, member1.getFirstname() + " " + member1.getLastname(), member1.getEmail());
                                                memberIndex++;
                                            }
                                            System.out.println("================================");
                                            //Type Member Number, You want to edit or Press Q to Exit
                                            //	Select (1-n) :
                                            System.out.print("Type Member Number, You want to edit or Press Q to Exit\n" +
                                                    "Select (1-" + members.size() + ") : ");
                                            String memberChoice = userInput.next();
                                            if (memberChoice.equalsIgnoreCase("q")) {
                                                break;
                                            } else if (Integer.parseInt(memberChoice) > members.size() || Integer.parseInt(memberChoice) < 1) {
                                                System.out.println("!!! Error: Input only 1-" + members.size() + " or Q for exit !!!");
                                            } else {
                                                Member selectedMember = members.get(Integer.parseInt(memberChoice) - 1);
                                                System.out.println("===== Edit info of " + selectedMember.getFirstname() + " " + selectedMember.getLastname() + " ===== \n" + "Type new info or Hyphen (-) for none edit. \n");
                                                System.out.print("Enter Firstname: ");
                                                String newFirstname = userInput.next();
                                                System.out.print("Enter Lastname: ");
                                                String newLastname = userInput.next();
                                                System.out.print("Enter Email: ");
                                                String newEmail = userInput.next();
                                                System.out.print("Enter Phone Number: ");
                                                String newPhoneNumber = userInput.next();
                                                if (!newFirstname.equals("-")) {
                                                    selectedMember.Firstname = newFirstname;
                                                }
                                                if (!newLastname.equals("-")) {
                                                    selectedMember.Lastname = newLastname;
                                                }
                                                if (!newEmail.equals("-")) {
                                                    selectedMember.Email = newEmail;
                                                }
                                                if (!newPhoneNumber.equals("-")) {
                                                    selectedMember.PhoneNumber = newPhoneNumber;
                                                }
                                                members.set(Integer.parseInt(memberChoice) - 1, selectedMember);
                                                //write all member to file
                                                try (FileWriter fileWriter = new FileWriter("src/MEMBER.txt")) {
                                                    for (Member member1 : members) {
                                                        fileWriter.write(member1.id + "\t" + member1.Firstname + "\t" + member1.Lastname + "\t" + member1.Email + "\t" + member1.rawPassword + "\t" + member1.PhoneNumber + "\t" + member1.point + "\n");
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("!!! Error: Cannot write to file !!!");
                                                }
                                                System.out.println("Edit member successfully!");
                                            }
                                        }
                                    }
                                    //edit product
                                    else if (choice.equals("4") && role == 0 ) {
                                        //edit product
                                        //print all product
                                        while (true) {
                                            System.out.println("===== SE STORE's Product =====");
                                            printItem(products, role);
                                            System.out.print("Type Product Number, You want to edit or Press Q to Exit\n" +
                                                    "Select (1-" + products.size() + ") : ");
                                            String productChoice = userInput.next();
                                            if (productChoice.equalsIgnoreCase("q")) {
                                                break;
                                            } else if (Integer.parseInt(productChoice) > products.size() || Integer.parseInt(productChoice) < 1) {
                                                System.out.println("!!! Error: Input only 1-" + products.size() + " or Q for exit !!!");
                                            } else {
                                                Product selectedProduct = products.get(Integer.parseInt(productChoice) - 1);
                                                System.out.println("===== Edit info of " + selectedProduct.getName() + " ===== \n" + "Type new info or Hyphen (-) for none edit.");
                                                System.out.print("Name: ");
                                                String newName = userInput.next();
                                                System.out.print("Quantity (+ or -) : ");
                                                String newQuantity = userInput.next();
                                                //check for -
                                                if (!newName.equals("-")) {
                                                    if (newName.length() <= 1) {
                                                        System.out.println("Error! - Your Information are Incorrect!");
                                                        break;
                                                    } else {
                                                        selectedProduct.setName(newName);
                                                    }

                                                }
                                                if (!newQuantity.equals("-")) {
                                                    //check for + or -
                                                    if (newQuantity.charAt(0) == '+' || newQuantity.charAt(0) == '-') {
                                                        if (newQuantity.contains(".")) {
                                                            System.out.println("Error! - Your Information are Incorrect!");
                                                            break;
                                                        } else {
                                                            selectedProduct.setQuantity(Integer.parseInt(newQuantity.substring(1)), newQuantity.charAt(0));
                                                        }
                                                    } else {
                                                        System.out.println("Error! - Your Information are Incorrect!");
                                                        break;
                                                    }
                                                }
                                                products.set(Integer.parseInt(productChoice) - 1, selectedProduct);
                                                //write all product to file
                                                try (FileWriter fileWriter = new FileWriter("src/PRODUCT.txt")) {
                                                    for (Product product : products) {
                                                        fileWriter.write(product.getId() + "\t" + product.getName() + "\t" + product.getStringPrice() + "\t" + product.getQuantity() + "\t" + product.getType() + "\n");
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("!!! Error: Cannot write to file !!!");
                                                }
                                                System.out.println("Success - " + selectedProduct.getName() + " has been updated!");
                                            }
                                        }
                                    }
                                    //exit
                                    else if (choice.equals("3") || choice.equals("5") && role == 0) {
                                        System.out.print("Logging out: See you!, " + member.Firstname + ". \n");
                                        break;
                                    }
                                }
                            }
                        }
                        //if email and password not match
                        if (!accFound) {
                            failCount++;
                            System.out.println("Error! - Email or Password is Incorrect (" + failCount + ")");
                            if (failCount == 3) {
                                System.out.println("Sorry, Please try again later :(\n");
                                break;
                            }
                        }
                        if (accFound) break;
                    }
                }
                //exit
                else if (choice.equals("2")) {
                    System.out.print("\n===== SE STORE =====\nThank you for using our service :3\n");
                    System.exit(0);
                }
                //out of available choice exception
                else {
                    System.out.println("!!! Error: Input only 1 or 2 !!!");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("!!! Error: File not found !!!");
            System.exit(0);
        }
    }

    //method
    //หา id ท้ายสุดในไพล์ แล้วบวกเพิ่ม 1
    private static int getLatestID(ArrayList<Member> members) {
        int latestID = 0;
        for (Member member : members) {
            if (Integer.parseInt(member.id) > latestID) {
                latestID = Integer.parseInt(member.id);
            }
        }
        //บวกเลข 1 ให้กับ latestID
        latestID++;
        return latestID;
    }

    public static String generatePassword() {
        //ไว้เก็บ passcode 6 หลักที่เป็นตัวเลข
        StringBuilder passcode = new StringBuilder();
        //ไว้สุ่ม
        Random random = new Random();
        //สุ่ม passcode 6 หลัก
        for (int i = 0; i < 6; i++) {
            //สุ่มตัวเลข 0-9 มาต่อกัน
            passcode.append(random.nextInt(10));
        }
        //สร้าง password 19 ตัว
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 19; i++) {
            //สุ่มตัวอักษร A-Z มาต่อกัน 19 ตัว
            //สุ่มเลขมาแล้วแปลงเป็น char แล้วเอามาต่อกัน
            password.append((char) (random.nextInt(26) + 'A'));
        }
        //แบ่ง password ออกเป็นตัวๆ
        String[] parts = password.toString().split("");
        //Status
        parts[1] = "1";
        //Role (default: Regular)
        parts[6] = "1";
        //แทนที่ด้วย passcode ที่สุ่มไว้
        parts[9] = passcode.charAt(0) + "";
        parts[10] = passcode.charAt(1) + "";
        parts[13] = passcode.charAt(2) + "";
        parts[14] = passcode.charAt(3) + "";
        parts[15] = passcode.charAt(4) + "";
        parts[16] = passcode.charAt(5) + "";
        StringBuilder newPassword = new StringBuilder();
        for (String part : parts) {
            //เอา Array มาต่อกันเป็น String
            newPassword.append(part);
        }
        //return password ใหม่
        return newPassword.toString();
    }

    static void printItem(List<Product> product, int roleID) {
        System.out.println("=========== SE STORE's Products ===========");
        if (roleID == 2 || roleID == 3) {
            System.out.printf("%-4s %-15s %-19s %-30s\n", "#", "Name", "Price (฿)", "Quantity");
        } else {
            System.out.printf("%-4s %-15s %-15s %-15s\n", "#", "Name", "Price (฿)", "Quantity");
        }
        int index = 1;
        for (Product value : product) {
            System.out.printf("%-5s", index);
            value.printItem(roleID);
            index++;
        }
        System.out.println("===========================================");
    }
}

