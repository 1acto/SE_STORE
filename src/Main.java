/************************************************************************************/
/* Program Assignment: SE_STORE#2 */
/* Student ID: 66160109 */
/* Student Name: Patyot Sompran */
/* Date: 21 Aug 2024 */
/* Description: a program for showing detail about product in shop */
/***********************************************************************************/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Prepare to read file
        try {
            File productFile = new File("src/PRODUCT.txt");
            File categoryFile = new File("src/CATEGORY.txt");
            File memberFile = new File("src/MEMBER.txt");
            Scanner productScanner = new Scanner(productFile);
            Scanner categoryScanner = new Scanner(categoryFile);
            Scanner memberScanner = new Scanner(memberFile);
            //Set up scanner for user input
            Scanner kb = new Scanner(System.in);
            //Set up array list to store products
            ArrayList<Product> products = new ArrayList<>();
            //Read file and put products in array list
            while (productScanner.hasNextLine()) {
                Product importedProduct = new Product(productScanner.next(), productScanner.next(), productScanner.next(), productScanner.next(), productScanner.next());
                products.add(importedProduct);
            }
            //Set up array list to store categories
            ArrayList<Category> categories = new ArrayList<>();
            while (categoryScanner.hasNextLine()) {
                //spilt the line into id and name
                String[] split = categoryScanner.nextLine().split("\t");
                Category importedCategory = new Category(split[0], split[1]);
                categories.add(importedCategory);
            }
            //เก็บค่าของ member
            ArrayList<Member> members = new ArrayList<>();
            while (memberScanner.hasNext()){
                Member importedMember = new Member(memberScanner.next(), memberScanner.next(), memberScanner.next(), memberScanner.next(), memberScanner.next(), memberScanner.next(), memberScanner.next());
                members.add(importedMember);
            }
            //close the scanner
            productScanner.close();
            categoryScanner.close();
            memberScanner.close();

            //Menu
            String choice = null;
            while (true) {
                //display#1
                System.out.print("\n===== SE STORE =====\n" +
                        "1. Login\n" +
                        "2. Exit\n" +
                        "====================\n" +
                        "Select (1-2) : ");
                choice = kb.next();
                if (choice.equals("1")) {
                    int failCount = 0; //Count the number of failed login attempts
                    while (true) {
                        //Login
                        System.out.print("\n===== LOGIN =====\n" +
                                "Email: ");
                        String email = kb.next();
                        System.out.print("Password: ");
                        String password = kb.next();
                        System.out.println("====================");
                        boolean accFound = false; //Check if the account is found
                        for (int i = 0; i < members.size(); i++) {
                            //email and password check
                            if (email.equals(members.get(i).getEmail()) && members.get(i).checkPassword(password)) {
                                //Status check
                                if (members.get(i).getStatus() == ('0')) {
                                    accFound = true;
                                    System.out.println("Error! - Your Account are Expired! ");
                                    break;
                                }
                                accFound = true;
                                while (true) {
                                    //Member menu
                                    System.out.print("\n===== SE STORE =====\n");
                                    System.out.print("Hello, " + members.get(i).getCensoredName());
                                    //Role check
                                    switch (members.get(i).getRoleID()) {
                                        case '0':
                                            System.out.print(" (Staff)\n");
                                            break;
                                        case '1':
                                            System.out.print(" (Regular)\n");
                                            break;
                                        case '2':
                                            System.out.print(" (Sliver)\n");
                                            break;
                                        case '3':
                                            System.out.print(" (Gold)\n");
                                            break;
                                    }
                                    System.out.println("Email: " + members.get(i).censorEmail());
                                    System.out.println("Phone: " + members.get(i).getPhoneNumber());
                                    System.out.println("You have " + members.get(i).getPoint() + " points");
                                    System.out.println("====================");

                                    //Menu
                                    int menuindex = 1;
                                    System.out.println(menuindex + ". Show Category");
                                    if (members.get(i).getRoleID() == '0') {
                                        menuindex += 1;
                                        System.out.println(menuindex + ". Add Member");
                                    }
                                    menuindex += 1;
                                    System.out.println(menuindex + ". Logout ");
                                    System.out.println("====================");
                                    System.out.print("Select (1-" + menuindex + ") : ");
                                    try {
                                        choice = kb.next();
                                        if (choice.equals("1")) {
                                            while (true) {
                                                //Show Category
                                                System.out.println("===== SE STORE's Product Categories =====");
                                                System.out.printf("%-4s %-15s", "#", "Category Name");
                                                System.out.println();
                                                int k = 1;
                                                //Print all categories
                                                for  (Category category : categories) {
                                                    System.out.printf("%-4s %-15s", k, category.getName());
                                                    System.out.println();
                                                    k++;
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
                                                        System.out.printf("%-4s %-15s %-15s %-15s", "#", "Name", "Price (฿)", "Quantity");
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
                                                        //Exit to previous menu
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
                                        }
                                        //Add member และ เป็น staff
                                        else if (choice.equals("2") && members.get(i).getRoleID() == '0') {
                                            //Display add category
                                            System.out.print("===== Add Member =====\n");
                                            //Input member information
                                            System.out.print("Enter Firstname: ");
                                            String firstname = kb.next();
                                            System.out.print("Enter Lastname: ");
                                            String lastname = kb.next();
                                            System.out.print("Enter Email: ");
                                            String inputEmail = kb.next();
                                            System.out.print("Enter Phone Number: ");
                                            String phoneNumber = kb.next();
                                            //Check if the information is correct
                                            if (firstname.length() < 2 || lastname.length() < 2 || inputEmail.length() < 2 || !inputEmail.contains("@") || phoneNumber.length() < 10) {
                                                System.out.println("Error! - Your Information are Incorrect!");
                                            }else {
                                                //find latest member id
                                                int latestID = 0;
                                                for (int j = 0; j < members.size(); j++) {
                                                    if (Integer.parseInt(members.get(j).id) > latestID) {
                                                        latestID = Integer.parseInt(members.get(j).id);
                                                    }
                                                }
                                                //บวกเลข 1 ให้กับ latestID
                                                latestID++;
                                                //แปลง latestID เป็น string
                                                String stringId = Integer.toString(latestID);
                                                //สร้าง password ใหม่
                                                String newPassword = generatePassword();
                                                //สร้าง member ใหม่
                                                Member newMember = new Member(stringId, firstname, lastname, inputEmail, newPassword, phoneNumber, "0");
                                                //เพิ่ม member ใหม่เข้าไปใน array list
                                                members.add(newMember);
                                                System.out.println("Added member successfully!");
                                                System.out.println(newMember.getFirstname() + "'s Password is " + newMember.Password);
                                                //Write to file
                                                try {
                                                    //เปลี่ยนจาก PrintWriter เป็น FileWriter เพราะ PW มันเขียนทับไฟล์เก่าไปเลย
                                                    //true คือเพื่อให้เขียนต่อจากข้อมูลเก่า (append)
                                                    FileWriter fileWriter = new FileWriter(memberFile, true);
                                                    //เขียนข้อมูลลงไฟล์
                                                    fileWriter.write("\n"+ newMember.id + "\t" + newMember.Firstname + "\t" + newMember.Lastname + "\t" + newMember.Email + "\t" + newMember.rawPassword + "\t" + newMember.PhoneNumber + "\t" + newMember.point);
                                                    fileWriter.close();
                                                } catch (Exception e) {
                                                    System.out.println("!!! Error: Cannot write to file !!!");
                                                }
                                                }
                                            }
                                        //Logout สำหรับสมาชิกทั่วไป
                                        else if (choice.equals("2") && members.get(i).getRoleID() != '0') {
                                            System.out.print("Logging out: See you!, "+ members.get(i).Firstname + ". \n");
                                            break;
                                        }
                                        //Logout สำหรับ staff
                                        else if (choice.equals("3") && members.get(i).getRoleID() == '0') {
                                            //Display Exit quote
                                            System.out.print("Logging out: See you!, "+ members.get(i).Firstname + ". \n");
                                            break;
                                        }
                                        else {
                                            System.out.println("!!! Error: Input only 1 or" + menuindex + " !!!");
                                        }
                                    }catch (Exception e){
                                        System.out.println("!!! Error: Input only 1 or" + menuindex + " !!!");
                                    }
                                }
                                break;
                            }
                        }
                        //ถ้าไม่พบ account
                        if (!accFound){
                            failCount++;
                            //แจ้ง
                            System.out.println("Error! - Email or Password is Incorrect (" + failCount + ")");
                            if (failCount == 3){
                                //ครบ 3 เด้ง
                                System.out.println("Sorry, Please try again later :(\n");
                                break;
                            }
                        }
                        //ถ้าพบ account ก็ออก loop
                        if (accFound){
                            break;
                        }
                    }
                } else if (choice.equals("2")) {
                    System.out.print("\n===== SE STORE =====\n" +
                            "Thank you for using our service :3\n");
                    System.exit(0);
                } else {
                    System.out.println("!!! Error: Input only 1 or 2 !!!");
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("!!! Error: File not found !!!");
            System.exit(0);
        }
    }
    public static String generatePassword(){
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
        for (int i = 0; i < 19; i++){
            //สุ่มตัวอักษร A-Z มาต่อกัน 19 ตัว
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
        //ไม่รู้ปริ้นไงก็เลยทำงี้ น่าจะมีวิํธีที่ดีกว่านี้มั้ง :)
        StringBuilder newPassword = new StringBuilder();
        for (String part : parts) {
            //เอา Array มาต่อกันเป็น String
            newPassword.append(part);
        }
        //return password ใหม่
        return newPassword.toString();
    }

}

