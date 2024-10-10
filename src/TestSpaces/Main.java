package TestSpaces;
/*
  Program Assignment: SE_STORE
  Student ID: 66160109
  Student Name: Patyot Sompran
  Project Start Date: 21 Aug 2024
  Last Modified: 02 Sep 2024
  Version: #8
  Description: Store Management System.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner productScanner = new Scanner(new File("src/TestSpaces/PRODUCT.txt"));
            Scanner categoryScanner = new Scanner(new File("src/TestSpaces/CATEGORY.txt"));
            Scanner memberScanner = new Scanner(new File("src/TestSpaces/MEMBER.txt"));
            Scanner kb = new Scanner(System.in);

            ArrayList<Product> products = readProducts(productScanner);
            ArrayList<Category> categories = readCategories(categoryScanner);
            ArrayList<Member> members = readMembers(memberScanner);

            while (true) {
                System.out.print("\n===== SE STORE =====\n1. Login\n2. Exit\n====================\nSelect (1-2) : ");
                String choice = kb.next();
                if (choice.equals("1")) {
                    handleLogin(kb, members, products, categories);
                } else if (choice.equals("2")) {
                    System.out.print("\n===== SE STORE =====\nThank you for using our service :3\n");
                    System.exit(0);
                } else {
                    System.out.println("!!! Error: Input only 1 or 2 !!!");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("!!! Error: File not found !!!");
            System.exit(0);
        } catch (InputMismatchException e) {
            System.out.println("!!! Error: Something went wrong !!!");
            System.exit(0);
        }
    }

    private static ArrayList<Product> readProducts(Scanner scanner) {
        ArrayList<Product> products = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().split("\t");
            if (tokens.length >= 5) {
                products.add(new Product(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]));
            }
        }
        scanner.close();
        return products;
    }

    private static ArrayList<Category> readCategories(Scanner scanner) {
        ArrayList<Category> categories = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().split("\t");
            if (tokens.length >= 2) {
                categories.add(new Category(tokens[0], tokens[1]));
            }
        }
        scanner.close();
        return categories;
    }

    private static ArrayList<Member> readMembers(Scanner scanner) {
        ArrayList<Member> members = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().split("\t");
            if (tokens.length >= 7) {
                members.add(new Member(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]));
            }
        }
        scanner.close();
        return members;
    }

    private static void handleLogin(Scanner kb, ArrayList<Member> members, ArrayList<Product> products, ArrayList<Category> categories) {
        int failCount = 0;
        while (true) {
            System.out.print("\n===== LOGIN =====\nEmail: ");
            String email = kb.next();
            System.out.print("Password: ");
            String password = kb.next();
            System.out.println("====================");
            boolean accFound = false;
            for (Member member : members) {
                if (email.equals(member.getEmail()) && member.checkPassword(password)) {
                    if (member.getStatus() == '0') {
                        accFound = true;
                        System.out.println("Error! - Your Account are Expired! ");
                        break;
                    }
                    accFound = true;
                    handleUserMenu(kb, member, members, categories, products);
                    break;
                }
            }
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

    private static void handleUserMenu(Scanner kb, Member member, ArrayList<Member> members, ArrayList<Category> categories, ArrayList<Product> products) {
        while (true) {
            System.out.print("\n===== SE STORE =====\nHello, " + member.getCensoredName());
            int role = member.getRoleID() - '0';
            System.out.println(" (" + getRoleName(role) + ")\nEmail: " + member.censorEmail() + "\nPhone: " + member.getPhoneNumber() + "\nYou have " + member.getPoint() + " points\n====================");

            int menuIndex = 1;
            System.out.println(menuIndex + ". Show Category");
            if (role == 0) {
                System.out.println(++menuIndex + ". Add Member");
                System.out.println(++menuIndex + ". Edit Member");
                System.out.println(++menuIndex + ". Edit Product");
            }
            System.out.println(++menuIndex + ". Logout\n====================");
            System.out.print("Select (1-" + menuIndex + ") : ");
            String choice = kb.next();

            if (choice.equals("1")) {
                showCategories(kb, categories, products, role);
            } else if (choice.equals("2") && role == 0) {
                addMember(kb, members);
            } else if (choice.equals("2") || (choice.equals("5") && role == 0)) {
                System.out.print("Logging out: See you!, " + member.getFirstname() + ". \n");
                break;
            } else if (choice.equals("3") && role == 0) {
                editMember(kb, members);
            } else if (choice.equals("4") && role == 0) {
                editProduct(kb, products);
            }
        }
    }

    private static String getRoleName(int role) {
        return switch (role) {
            case 0 -> "Staff";
            case 1 -> "Regular";
            case 2 -> "Silver";
            case 3 -> "Gold";
            default -> "Unknown";
        };
    }

    private static void showCategories(Scanner kb, ArrayList<Category> categories, ArrayList<Product> products, int role) {
        while (true) {
            System.out.println("===== SE STORE's Product Categories =====");
            for (int i = 0; i < categories.size(); i++) {
                System.out.printf("%-4s %-15s\n", (i + 1), categories.get(i).getName());
            }
            System.out.println("===========================================");
            System.out.print("Select Category to Show Product (1-" + categories.size() + ") or Q for exit \nselect : ");
            String categoryChoice = kb.next();
            if (categoryChoice.equalsIgnoreCase("q")) break;
            try {
                int categoryIndex = Integer.parseInt(categoryChoice) - 1;
                if (categoryIndex < categories.size()) {
                    showProducts(kb, products, categories.get(categoryIndex), role);
                }
            } catch (Exception e) {
                System.out.println("!!! Error: Input only 1-" + categories.size() + " or Q for exit !!!");
            }
        }
    }

    private static void showProducts(Scanner kb, ArrayList<Product> products, Category category, int role) {
        System.out.println("============ " + category.getName() + " ============");
        List<Product> productLists = new ArrayList<>();
        for (Product product : products) {
            if (product.getType().equals(category.getId())) {
                productLists.add(product);
            }
        }
        printItem(productLists, role);
        while (true) {
            System.out.print("1. Show Name By DESC\n2. Show Quantity By ASC\nor Press Q to Exit : ");
            String sortChoice = kb.next();
            if (sortChoice.equals("1")) {
                productLists.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
                printItem(productLists, role);
            } else if (sortChoice.equals("2")) {
                productLists.sort(Comparator.comparingInt(Product::getQuantity));
                printItem(productLists, role);
            } else if (sortChoice.equalsIgnoreCase("q")) {
                break;
            } else {
                System.out.println("!!! Error: Input only 1-3 !!!");
            }
        }
    }

    private static void addMember(Scanner kb, ArrayList<Member> members) {
        System.out.print("===== Add Member =====\nEnter Firstname: ");
        String firstname = kb.next();
        System.out.print("Enter Lastname: ");
        String lastname = kb.next();
        System.out.print("Enter Email: ");
        String inputEmail = kb.next();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = kb.next();
        if (isValidMemberInfo(firstname, lastname, inputEmail, phoneNumber)) {
            int latestID = getLatestID(members);
            String newPassword = generatePassword();
            Member newMember = new Member(String.valueOf(latestID), firstname, lastname, inputEmail, newPassword, phoneNumber, "0");
            members.add(newMember);
            System.out.println("Added member successfully!\n" + newMember.getFirstname() + "'s Password is " + newMember.Password);
            writeMembersToFile(members);
        } else {
            System.out.println("Error! - Your Information are Incorrect!");
        }
    }

    private static boolean isValidMemberInfo(String firstname, String lastname, String email, String phoneNumber) {
        return firstname.length() >= 2 && lastname.length() >= 2 && email.length() >= 2 && email.contains("@") && phoneNumber.length() >= 10;
    }

    private static void editMember(Scanner kb, ArrayList<Member> members) {
        while (true) {
            System.out.println("===== SE STORE's Member =====");
            for (int i = 0; i < members.size(); i++) {
                System.out.printf("%-4s %-25s %-30s\n", (i + 1), members.get(i).getFirstname() + " " + members.get(i).getLastname(), members.get(i).getEmail());
            }
            System.out.println("================================");
            System.out.print("Type Member Number, You want to edit or Press Q to Exit\nSelect (1-" + members.size() + ") : ");
            String memberChoice = kb.next();
            if (memberChoice.equalsIgnoreCase("q")) break;
            try {
                int memberIndex = Integer.parseInt(memberChoice) - 1;
                if (memberIndex < members.size()) {
                    editMemberInfo(kb, members.get(memberIndex));
                    writeMembersToFile(members);
                    System.out.println("Edit member successfully!");
                }
            } catch (Exception e) {
                System.out.println("!!! Error: Input only 1-" + members.size() + " or Q for exit !!!");
            }
        }
    }

    private static void editMemberInfo(Scanner kb, Member member) {
        System.out.println("===== Edit info of " + member.getFirstname() + " " + member.getLastname() + " ===== \nType new info or Hyphen (-) for none edit. \n");
        System.out.print("Enter Firstname: ");
        String newFirstname = kb.next();
        System.out.print("Enter Lastname: ");
        String newLastname = kb.next();
        System.out.print("Enter Email: ");
        String newEmail = kb.next();
        System.out.print("Enter Phone Number: ");
        String newPhoneNumber = kb.next();
        if (!newFirstname.equals("-")) member.Firstname = newFirstname;
        if (!newLastname.equals("-")) member.Lastname = newLastname;
        if (!newEmail.equals("-")) member.Email = newEmail;
        if (!newPhoneNumber.equals("-")) member.PhoneNumber = newPhoneNumber;
    }

    private static void editProduct(Scanner kb, ArrayList<Product> products) {
        while (true) {
            System.out.println("===== SE STORE's Product =====");
            printItem(products, 0);
            System.out.print("Type Product Number, You want to edit or Press Q to Exit\nSelect (1-" + products.size() + ") : ");
            String productChoice = kb.next();
            if (productChoice.equalsIgnoreCase("q")) break;
            try {
                int productIndex = Integer.parseInt(productChoice) - 1;
                if (productIndex < products.size()) {
                    editProductInfo(kb, products.get(productIndex));
                    writeProductsToFile(products);
                    System.out.println("Edit product successfully!");
                }
            } catch (Exception e) {
                System.out.println("!!! Error: Input only 1-" + products.size() + " or Q for exit !!!");
            }
        }
    }

    private static void editProductInfo(Scanner kb, Product product) {
        System.out.println("===== Edit info of " + product.getName() + " ===== \nType new info or Hyphen (-) for none edit. \n");
        System.out.print("Name: ");
        String newName = kb.next();
        System.out.print("Quantity (+ or -) : ");
        String newQuantity = kb.next();
        if (!newName.equals("-")) product.setName(newName);
        if (!newQuantity.equals("-")) product.setQuantity(Integer.parseInt(newQuantity.substring(1)), newQuantity.charAt(0));
    }

    private static void writeMembersToFile(ArrayList<Member> members) {
        try (FileWriter fileWriter = new FileWriter("src/TestSpaces/MEMBER.txt")) {
            for (Member member : members) {
                fileWriter.write(member.id + "\t" + member.Firstname + "\t" + member.Lastname + "\t" + member.Email + "\t" + member.rawPassword + "\t" + member.PhoneNumber + "\t" + member.point + "\n");
            }
        } catch (Exception e) {
            System.out.println("!!! Error: Cannot write to file !!!");
        }
    }

    private static void writeProductsToFile(ArrayList<Product> products) {
        try (FileWriter fileWriter = new FileWriter("src/TestSpaces/PRODUCT.txt")) {
            for (Product product : products) {
                fileWriter.write(product.getId() + "\t" + product.getName() + "\t" + product.getStringPrice() + "\t" + product.getQuantity() + "\t" + product.getType() + "\n");
            }
        } catch (Exception e) {
            System.out.println("!!! Error: Cannot write to file !!!");
        }
    }

    private static int getLatestID(ArrayList<Member> members) {
        int latestID = 0;
        for (Member member : members) {
            latestID = Math.max(latestID, Integer.parseInt(member.id));
        }
        return latestID + 1;
    }

    public static String generatePassword() {
        Random random = new Random();
        StringBuilder passcode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            passcode.append(random.nextInt(10));
        }
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 19; i++) {
            password.append((char) (random.nextInt(26) + 'A'));
        }
        String[] parts = password.toString().split("");
        parts[1] = "1";
        parts[6] = "1";
        parts[9] = passcode.charAt(0) + "";
        parts[10] = passcode.charAt(1) + "";
        parts[13] = passcode.charAt(2) + "";
        parts[14] = passcode.charAt(3) + "";
        parts[15] = passcode.charAt(4) + "";
        parts[16] = passcode.charAt(5) + "";
        return String.join("", parts);
    }

    static void printItem(List<Product> product, int roleID) {
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
        System.out.println("================================");
    }
}
