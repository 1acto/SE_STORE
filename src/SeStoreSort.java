import java.io.*;
import java.util.*;

public class SeStoreSort {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int input;

        // Loop for main menu
        while (true) {
            System.out.println("===== SE STORE =====");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.println("====================");
            System.out.print("Select (1-2): ");

            if (scanner.hasNextInt()) { // ตรวจสอบว่าผู้ใช้ป้อนเลขจำนวนเต็มหรือไม่
                input = scanner.nextInt();
                if (input == 1) {
                    logIn("C:\\Users\\Windows11\\IdeaProjects\\Term1\\src\\Member.txt");
                } else if (input == 2) {
                    System.out.println("Thank you for using our service :3");
                    break;
                } else {
                    System.out.println("Invalid selection. Please enter a number between 1 and 2.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 2.");
                scanner.next(); // อ่านค่าที่ไม่ใช่จำนวนเต็มเพื่อข้ามไป
            }
        }
    }

    public static void logIn(String filepath) throws IOException {
        String inputEmail, inputPassword;
        List<String[]> members = readMembers(filepath);
        Scanner scanner = new Scanner(System.in);
        int attempts = 0; // Reset attempts for each login attempt
        while (attempts < 3) {
            System.out.println("==== LOGIN ====");
            System.out.print("Email: ");
            inputEmail = scanner.next();
            System.out.print("Password: ");
            inputPassword = scanner.next();

            boolean loginSuccess = false;
            // Loop through the members to check for email and password match
            for (String[] member : members) {
                String email = member[3];
                String password = member[4];

                // Check if input email matches
                if (inputEmail.equalsIgnoreCase(email)) {
                    // Check if input password is correct
                    if (inputPassword.length() == 6 &&
                            inputPassword.charAt(0) == password.charAt(9) &&
                            inputPassword.charAt(1) == password.charAt(10) &&
                            inputPassword.charAt(2) == password.charAt(13) &&
                            inputPassword.charAt(3) == password.charAt(14) &&
                            inputPassword.charAt(4) == password.charAt(15) &&
                            inputPassword.charAt(5) == password.charAt(16)) {

                        // Check status if 0 Non-active / 1 Active
                        char status = password.charAt(2);
                        if (status == '1') {
                            // Check the role from password's 6th character (index 5)
                            char roleChar = password.charAt(6);
                            // Check the 6th position
                            if (roleChar == '0') {
                                staffMenu(scanner, member, roleChar);
                            } else if (roleChar == '1') {
                                MemberMenu(scanner, member, roleChar);
                            } else if (roleChar == '2') {
                                MemberMenu(scanner, member, roleChar);
                            } else if (roleChar == '3') {
                                MemberMenu(scanner, member, roleChar);

                            }
                            return; // Exit loop if login is successful
                        } else if (status == '0') {
                            System.out.println("Error! - Email or Password is Expired");
                            return;
                        }
                    }
                }
            }
            // If login was not successful, increment attempts
            if (!loginSuccess) {
                attempts++;
                System.out.println("Error! - Email or Password is Incorrect (" + attempts + ")");
                if (attempts == 3) {
                    break;
                }
            }
        }
    }

    private static void staffMenu(Scanner kb, String[] member, char userRole) throws IOException {
        while (true) {
            String nameBeforeAdd, mailAfterAdd;
            float points;
            System.out.println("Hello, " + member[2].charAt(0) + ". " + member[1] + " " + "(STAFF)");
            nameBeforeAdd = member[3].substring(0, 2) + "***";  // Email mask
            mailAfterAdd = member[3].substring(member[3].indexOf('@') + 1, member[3].indexOf('@') + 3) + "***"; // Email domain mask
            System.out.println("Email : " + nameBeforeAdd + "@" + mailAfterAdd);
            System.out.println("Phone : " + member[5].substring(0, 3) + "-" + member[5].substring(3, 6) + "-" + member[5].substring(6, 10));
            points = Float.parseFloat(member[6]);
            System.out.printf("You have " + "%.0f", points);
            System.out.println(" points");
            System.out.println("===== STAFF MENU =====");
            System.out.println("1. Show Category");
            System.out.println("2. Add Member");
            System.out.println("3. Edit Member");
            System.out.println("4. Edit Product");
            System.out.println("5. Logout");
            System.out.println("======================");
            System.out.print("Select (1-4): ");
            String select = kb.next();
            switch (select) {
                case "1":
                    // Call function to show categories
                    showCategory(userRole);
                    break;
                case "2":
                    // Call function to add member
                    addMember();
                    break;
                case "3":
                    // Call function to edit member
                    editMember("C:\\Users\\Windows11\\IdeaProjects\\Term1\\src\\Member.txt");
                    break;// return to fill email
                case "4":
                    editProduct(userRole);
                    break;
                case "5":
                    return; // Exit the staff menu
                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }
        }
    }

    // Method for reading members from the file
    public static List<String[]> readMembers(String filePath) throws IOException {
        List<String[]> members = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        // Read the file
        while ((line = reader.readLine()) != null) {
            String[] memberData = line.split("\t");
            members.add(memberData);
        }
        reader.close();
        return members;
    }
    //สำหรับเพิ่มสมาชิก
    public static void addMember() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("==== ADD MEMBER ====");
            // Step 1: Collect member information
            System.out.print("Firstname : ");
            String firstName = scanner.next();
            System.out.print("LastName : ");
            String lastName = scanner.next();
            System.out.print("Email : ");
            String email = scanner.next();
            System.out.print("Phone: ");
            String phone = scanner.next();
            // ความยาวทั้งหมดในอีเมล - ตัวที่มี@(เมื่อทำการลบแล้วจะมีค่า = 1 1เท่านั้นหรือมี @ ตัวเดียว)
            int atCount = email.length() - email.replace("@", "").length();

            if (firstName.length() < 2 || lastName.length() < 2 || phone.length() < 10
                    || email.length() < 2 || atCount != 1){
                // ถ้าชื่อหรือนามสกุลมีความยาวน้อยกว่า 2 ตัวอักษร
                System.out.println("Error! - Your Information are Incorrect!");
                break;
            } else {
                int newID = getNewID(); // call method for new id
                String generatePass = generatePassword();
                String generatePassSix = generatePass.substring(9, 11) + generatePass.substring(13, 17);
                System.out.println("Success - New Member has been created!");
                System.out.println(firstName.toUpperCase().charAt(0) + firstName.substring(1) + "'s" + " Password is " + generatePassSix);
                try (BufferedWriter writeMember = new BufferedWriter(new FileWriter("C:\\Users\\Windows11\\IdeaProjects\\Term1\\src\\Member.txt", true))) {
                    writeMember.write(newID + "\t" + firstName + "\t" + lastName + "\t" + email + "\t" + generatePass + "\t" + phone + "\t" + "0.00" + "\n");
                } catch (IOException e) {
                    System.out.println("Error writing to file: " + e.getMessage());
                }
                return;
            }
        }
    }

    public static void editMember(String filePath) throws IOException {
        List<String[]> members =readMembers(filePath);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("===== SE STORE's Member =====");
            System.out.printf("%-5s %-30s %-50s%n", "#", "Name", "Email"); // เปลี่ยนเป็น %s เพื่อแสดงข้อความ
            for (int i = 0; i < members.size(); i++) {
                String[] member = members.get(i);  //i เป็นดัชนีที่บ่งบอกถึงตำแหน่งของสมาชิกในรายการนั้นๆ
                //member[1] = FirstName, member[2] = LastName, member[3] = Email
                System.out.printf("%-5s %-30s %-50s%n", (i + 1), member[1] + " " + member[2], member[3]);
            }
            // Show menu to select a member to edit
            System.out.println("Type Member Number, You want to edit or Press Q to Exit: ");
            System.out.print("Select (1- " + members.size() + "): ");
            String input = scanner.nextLine();

            // If user chooses Q, return to the calling method
            if (input.equalsIgnoreCase("Q")) {
                return;  // Go back to the calling method
            }
            try {
                int memberNumber = Integer.parseInt(input);
                if (memberNumber < 1 || memberNumber > members.size()) {
                    System.out.println("Invalid member number!");
                    continue; // Continue to let user select again
                }

                String[] member = members.get(memberNumber - 1);
                System.out.println("====Editing info of " + member[1] + " " + member[2] + "====");
                System.out.println("Type new info or Hyphen (-) for none edit.");


                System.out.print("FirstName : ");
                String newName = scanner.nextLine();

                System.out.print("LastName : ");
                String newLastName = scanner.nextLine();

                System.out.print("Email : ");
                String email = scanner.nextLine();

                System.out.print("Phone : ");
                String phone = scanner.nextLine();
                boolean checkInfo = true;
                //กรณีที่จะไม่ทำการอัปเดตข้อมูล
                if (!newName.equals("-") && newName.length() <= 2) {//ชื่อต้องมากกว่า 2 ตัวอักษร
                    checkInfo = false;
                }
                //นามสกุลต้องมากกว่า 2 ตัวอักษร
                if (!newLastName.equals("-") && newLastName.length() <= 2) {
                    checkInfo = false;
                }
                //อีเมลต้องมากกว่า 2 ตัวอักษรและมี @
                int atCount = email.length() - email.replace("@", "").length();
                if (!email.equals("-") && (atCount != 1 || email.length() <= 2)) {
                    checkInfo = false;
                }
                //เบอร์โทรต้องมี 10 ตัว
                if (!phone.equals("-") && phone.length() != 10) {
                    checkInfo = false;
                }
                if (!checkInfo) {
                    System.out.println("Error! - Your Information are Incorrect!");
                }

                if (checkInfo) { // ถ้าข้อมูลถูกต้อง ให้อัปเดตสมาชิกและเขียนกลับลงไฟล์
                    if (!newName.equals("-")) { //ถ้าชื่อที่ป้อนเข้ามาไม่ใช่ (-) ให้เอาชื่อไปไว้ตำแหน่งที่ 1
                        member[1] = newName;
                    }
                    if (!newLastName.equals("-")) { //ถ้านามสกุลที่ป้อนเข้ามาไม่ใช่ (-) ให้เอานามสกุลไปไว้ตำแหน่งที่ 2
                        member[2] = newLastName;
                    }
                    if (!email.equals("-")) { //ถ้าอีเมลที่ป้อนเข้ามาไม่ใช่(-) ให้เอาอีเมลไปไว้ในตำแหน่งที่ 3
                        member[3] = email;
                    }
                    if (!phone.equals("-")) { //ถ้าเบอร์ที่ป้อนเข้ามาไม่ใช่(-) ให้เอาเบอร์ไปไว้ในตำแหน่งที่ 5
                        member[5] = phone;
                    }
                    // Write the updated members back to the file only if changes were made
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                    for (String[] m : members) {
                        writer.write(m[0] + "\t" + m[1] + "\t" + m[2] + "\t" + m[3]
                                + "\t" + m[4] + "\t" + m[5] + "\t" + m[6]);
                        writer.newLine();
                    }
                    writer.close();
                    System.out.println("Member has been updated!");
                    break; // Exit to Staff Menu
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private static void editProduct(char memberRole) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            //เรียกใช้เมธอดสำหรับอ่านค่าโปรดักซ์
            List<String[]> products = readAllProductsFromFile();
            //แสดงโปรดักซ์ทั้งหมดหลังจากลดราคาแล้ว
            showProducts(products, memberRole);
            System.out.println("Type Product Number, You want to edit or Press Q to Exit: ");
            System.out.print("Select (1 - " + products.size() + ") :");
            String input = scanner.next();

            if (input.equalsIgnoreCase("Q")) {
                return;
            } else {
                // ตรวจสอบว่า input เป็นตัวเลขทั้งหมด
                if (input.matches("\\d+")) {

                    //ทำให้เป็นตัวเลข
                    int selectedProductNum = Integer.parseInt(input);

                    // ตรวจสอบว่าผู้ใช้เลือกสินค้าหมายเลข 1-50 หรือกด Q เพื่อออก
                    if (selectedProductNum > 0 && selectedProductNum <= products.size()) {
                        int lineNum = Integer.parseInt(input) - 1; // index ของ product
                        String[] selectedProduct = products.get(lineNum);
                        boolean checkInfo; // ตัวแปรตรวจสอบความถูกต้องของข้อมูล

                        // แก้ไขข้อมูลสินค้า
                        System.out.println("==== Edit info of " + selectedProduct[1] + " ====");
                        System.out.print("Name : ");
                        String addName = scanner.next();

                        // ตรวจสอบว่าแก้ไขชื่อหรือไม่ ถ้าผู้ใช้ไม่กรอก - ให้ชื่อเท่ากับชื่อที่ผู้ใช้กรอกเข้ามา
                        if (!addName.equals("-")) {
                            selectedProduct[1] = addName;
                        }

                        // แก้ไขจำนวนสินค้า
                        System.out.print("Quantity (+ or -) : ");
                        String addQuantity = scanner.next();


                        // ตรวจสอบว่าผู้ใช้ป้อนเครื่องหมาย + หรือ - ตามด้วยตัวเลข
                        if (addQuantity.matches("\\+\\d+")) { // กรณีมีเครื่องหมาย +
                            int currentQuantity = Integer.parseInt(selectedProduct[3]);//จำนวนสินค้าเก่า
                            int changeQuantity = Integer.parseInt(addQuantity.substring(1)); // ตัด + ออก
                            selectedProduct[3] = String.valueOf(currentQuantity + changeQuantity); // เพิ่มจำนวนสินค้า
                            checkInfo = true;
                        } else if (addQuantity.matches("-\\d+")) { // กรณีมีเครื่องหมาย -
                            int currentQuantity = Integer.parseInt(selectedProduct[3]);//จำนวนสินค้าเก่า
                            int changeQuantity = Integer.parseInt(addQuantity); // ค่าเป็น - อยู่แล้ว
                            selectedProduct[3] = String.valueOf(currentQuantity + changeQuantity); // ลดจำนวนสินค้า
                            checkInfo = true;
                        } else if (addQuantity.equals("-")) {
                            // กรณีที่ผู้ใช้กรอก "-" ไม่ต้องแก้ไขข้อมูล
                            checkInfo = true; // ถือว่าข้อมูลถูกต้อง
                        } else {
                            System.out.println("Error! - Your Information is Incorrect!");
                            continue; // กลับไปยังหน้าจอการแก้ไขใหม่
                        }
                        // หากข้อมูลถูกต้อง
                        if (checkInfo) {
                            // อัปเดตข้อมูลใน productFile
                            saveProductsToFile(products);
                            System.out.println("Success - " + selectedProduct[1] + " has been updated!");
                            break; // กลับไป Display#1
                        }
                    } else if (input.equalsIgnoreCase("q")) {
                        break; // ออกจากฟังก์ชัน
                    } else {
                        System.out.println("Invalid input. Please press Q to exit.");
                    }
                }
            }
        }
    }
    //Read File Products
    public static List<String[]> readProductsByCategory(String idCategory) throws FileNotFoundException {
        List<String[]> productList = new ArrayList<>();
        try (Scanner scanFile = new Scanner(new File("C:\\Users\\Windows11\\IdeaProjects\\Term1\\src\\product.txt"))) {
            while (scanFile.hasNextLine()) {
                String[] products = scanFile.nextLine().split("\t");
                if (products[4].equals(idCategory)) {
                    productList.add(products);
                }
            }
        }

        return productList;
    }

    public static List<String[]> readAllProductsFromFile() throws FileNotFoundException {
        List<String[]> productList = new ArrayList<>();
        try (Scanner scanFile = new Scanner(new File("C:\\Users\\Windows11\\IdeaProjects\\Term1\\src\\product.txt"))) {
            while (scanFile.hasNextLine()) {
                String[] products = scanFile.nextLine().split("\t");
                productList.add(products); // เพิ่มสินค้าเข้าไปใน List โดยไม่ต้องกรอง
            }
        }
        return productList;
    }

    //Read File Categories
    private static List<String[]> getCategories() throws FileNotFoundException {
        List<String[]> categories = new ArrayList<>();
        //สร้างลิสต์ชื่อ categories เก็บข้อมูลแบบอาร์เรย์สตริง
        try (Scanner readFile = new Scanner(new File("C:\\Users\\Windows11\\IdeaProjects\\Term1\\src\\Category.txt"))) {
            while (readFile.hasNextLine()) {
                //จะทำงานตราบใดที่ยังมีบรรทัดใหม่ให้อ่าน
                String[] data = readFile.nextLine().split("\t");
                //ใช้การแยกข้อมูลแต่ละบรรทัดโดยใช้เครื่องหมายแท็บ \t เพื่อแบ่งข้อมูลเป็นสองส่วน แล้วเก็บลงใน String[] data
                if (data.length == 2) {//หากข้อมูลแบ่งเป็นสองส่วน
                    categories.add(data);// เพิ่มข้อมูลหมวดหมู่ลงใน List
                }
            }
        }
        return categories;
    }

    private static void saveProductsToFile(List<String[]> products) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\Windows11\\IdeaProjects\\Term1\\src\\product.txt"))) {
            for (String[] product : products) {
                writer.println(String.join("\t", product));  // เขียนข้อมูลสินค้าแต่ละรายการกลับลงไฟล์
            }
        }
    }

    private static void MemberMenu(Scanner scanner, String[] member, char role) throws IOException {
        while (true) {
            String nameBeforeAdd, mailAfterAdd;
            float points;
            String userRole;
            switch (role) {
                case '1':
                    userRole = "(REGULAR)";
                    break;
                case '2':
                    userRole = "(SILVER)";
                    break;
                case '3':
                    userRole = "(GOLD)";
                    break;
                default:
                    userRole = "(UNKNOWN)";
            }
            System.out.println("Hello, " + member[2].charAt(0) + ". " + member[1] + " " + userRole);
            nameBeforeAdd = member[3].substring(0, 2) + "***";  // Email mask
            mailAfterAdd = member[3].substring(member[3].indexOf('@') + 1, member[3].indexOf('@') + 3) + "***"; // Email domain mask
            System.out.println("Email : " + nameBeforeAdd + "@" + mailAfterAdd);
            System.out.println("Phone : " + member[5].substring(0, 3) + "-" + member[5].substring(3, 6) + "-" + member[5].substring(6, 10));
            points = Float.parseFloat(member[6]);
            String memberId = member[0];
            System.out.printf("You have " + "%.0f", points);
            System.out.println(" points");
            System.out.println("===== MEMBER MENU =====");
            System.out.println("1. Show Category");
            System.out.println("2. Order Product");
            System.out.println("3. Logout");
            System.out.println("======================");
            System.out.print("Select (1-2): ");
            String select = scanner.next();
            switch (select) {
                case "1":
                    // Call function to show categories
                    showCategory(role);
                    break;
                case "2":
                    orderProduct(memberId, role);
                    break;
                case "3":
                    // Exit the member menu
                    return;
                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }
        }
    }

    public static void orderProduct(String memberId, char role) throws IOException {
        Scanner scanner = new Scanner(System.in);
        //เรียกใช้เมธอดสำหรับอ่านค่าโปรดักซ์
        List<String[]> products = readAllProductsFromFile();
        List<String[]>cart = new ArrayList<>();
        //แสดงโปรดักซ์ทั้งหมดหลังจากลดราคาแล้ว
        showProductsNoEuro(products, role);
        System.out.print("Enter the product number followed by the quantity.\n" +
                "\t1. How to Order\n" +
                "\t2. List Products\n" +
                "\tQ. Exit\n");
        while (true) {
            System.out.print("Enter : ");
            String select = scanner.nextLine();
            if (select.equalsIgnoreCase("q")) {
                // บันทึกข้อมูลตะกร้าสินค้าลงไฟล์ CART.txt
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Windows11\\IdeaProjects\\Term1\\src\\Cart.txt"))) {
                    for (String[] cartItem : cart) {  // วนลูปข้อมูลในตะกร้าสินค้า
                        writer.write(String.join("\t", cartItem));  // เขียนข้อมูลแต่ละรายการลงไฟล์ด้วยการแยกด้วย "\t"
                        writer.newLine();  // ขึ้นบรรทัดใหม่สำหรับแต่ละรายการสินค้า
                    }
                }
                System.out.println("Your cart has been saved!");  // แสดงข้อความแจ้งว่าตะกร้าถูกบันทึกแล้ว
                break;  // ออกจากลูปและโปรแกรม
            }

            String[] values = select.split("\\s+");  // แยกคำสั่งที่ผู้ใช้ป้อนด้วยช่องว่าง

            // ถ้าผู้ใช้ป้อนตัวเลือกเดียว เช่น "1", "2", หรือ "Q"
            if (values.length == 1) {
                if (values[0].equalsIgnoreCase("1")) {  // ถ้าผู้ใช้เลือก "1" สำหรับวิธีการสั่งซื้อ
                    System.out.println("How to Order:");
                    System.out.println("\tTo Add Product:");
                    System.out.println("\t\tEnter the product number followed by the quantity.");
                    System.out.println("\t\tExample: 1 50 (Adds 50 chips)");
                    System.out.println("\tTo Adjust Quantity:");
                    System.out.println("\t\t+ to add more items: 1 +50 (Adds 50 more chips)");
                    System.out.println("\t\t- to reduce items: 1 -50 (Removes 50 chips)");

                } else if (values[0].equalsIgnoreCase("2")) {  // ถ้าผู้ใช้เลือก "2" เพื่อดูรายการสินค้า
                    System.out.println("=========== SE STORE's Products ===========");
                    showProductsNoEuro(products,role); // เรียกฟังก์ชันแสดงรายการสินค้า

                } else {
                    System.out.println("Your input is invalid!");  // ถ้าผู้ใช้ป้อนคำสั่งไม่ถูกต้อง
                }

            } else if (values.length == 2) {  // ถ้าผู้ใช้ป้อนหมายเลขสินค้าและจำนวน (หรือการปรับจำนวน)
                String productNumber = values[0];  // เก็บหมายเลขสินค้าที่ผู้ใช้ป้อน
                String quantityInput = values[1];  // เก็บจำนวนที่ผู้ใช้ป้อน

                // ตรวจสอบว่าหมายเลขสินค้าเป็นตัวเลขหรือไม่
                if (!productNumber.matches("\\d+")) {
                    System.out.println("Invalid product number!");  // ถ้าหมายเลขสินค้าไม่เป็นตัวเลข
                    continue;  // ข้ามการทำงานในลูปและเริ่มรอบใหม่
                }

                int productIndex = Integer.parseInt(productNumber) - 1;  // แปลงหมายเลขสินค้าเป็นดัชนีในลิสต์สินค้า

                // ตรวจสอบว่าหมายเลขสินค้าที่ผู้ใช้ป้อนไม่เกินขอบเขตของลิสต์สินค้า
                if (productIndex < 0 || productIndex >= products.size()) {
                    System.out.println("Product number out of range!");  // ถ้าหมายเลขสินค้าเกินจากที่มีในลิสต์
                    continue;  // ข้ามการทำงานในลูปและเริ่มรอบใหม่
                }

                String[] selectedProduct = products.get(productIndex);  // ดึงข้อมูลสินค้าที่ผู้ใช้เลือกจากลิสต์สินค้า
                String productId = selectedProduct[0];  // เก็บ product ID
                String productName = selectedProduct[1];  // เก็บชื่อสินค้า
                int availableStock = Integer.parseInt(selectedProduct[3]);  // ดึงจำนวนสินค้าในสต็อกจากลิสต์สินค้า

                // โหลดข้อมูลตะกร้าสินค้าจากไฟล์ CART.txt
                cart = new ArrayList<>();  // เคลียร์ตะกร้าสินค้า
                try (Scanner cartFile = new Scanner(new File("C:\\Users\\Windows11\\IdeaProjects\\Term1\\src\\Cart.txt"))) {
                    while (cartFile.hasNextLine()) {  // อ่านข้อมูลจากไฟล์ CART.txt ทีละบรรทัด
                        String[] cartData = cartFile.nextLine().split("\t");  // แยกข้อมูลด้วย "\t"
                        if (cartData.length == 3) {  // ถ้าข้อมูลมี 3 คอลัมน์
                            cart.add(cartData);  // เพิ่มข้อมูลลงในตะกร้าสินค้า
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Cart file not found!");  // ถ้าไม่พบไฟล์ตะกร้า
                    continue;  // ข้ามการทำงานในลูปและเริ่มรอบใหม่
                }

                // ตรวจสอบว่าจำนวนสินค้าที่ผู้ใช้ป้อนเป็นรูปแบบที่ถูกต้อง (เพิ่ม, ลด, หรือแทนที่)
                if (quantityInput.matches("\\+\\d+") || quantityInput.matches("-\\d+") || quantityInput.matches("\\d+")) {
                    int changeQuantity = Integer.parseInt(quantityInput.replace("+", ""));  // แปลงจำนวนที่ผู้ใช้ป้อนเป็นตัวเลข (ถ้ามี + ให้เอาออก)
                    boolean found = false;  // ตัวแปรสำหรับตรวจสอบว่าสินค้านี้มีอยู่ในตะกร้าหรือไม่

                    // วนลูปเพื่อเช็คว่าสินค้านี้มีอยู่ในตะกร้าหรือไม่
                    for (String[] cartItem : cart) {
                        if (cartItem[0].equals(memberId) && cartItem[1].equals(productId)) {  // ถ้ารหัสสมาชิกและสินค้าในตะกร้าตรงกัน
                            int currentQuantity = Integer.parseInt(cartItem[2]);  // ดึงจำนวนสินค้าที่มีอยู่ในตะกร้า


                            // กรณีที่ผู้ใช้ต้องการเพิ่มสินค้า
                            if (quantityInput.startsWith("+")) {
                                int newQuantity = currentQuantity + changeQuantity;  // คำนวณจำนวนสินค้าใหม่หลังจากเพิ่ม
                                if (newQuantity > availableStock) {  // ตรวจสอบว่าจำนวนสินค้าใหม่เกินสต็อกหรือไม่
                                    System.out.println("Not enough stock available for " + productName + "!");  // แจ้งเตือนถ้าสินค้าในสต็อกไม่พอ
                                } else {
                                    cartItem[2] = String.valueOf(newQuantity);  // อัปเดตจำนวนสินค้าในตะกร้า
                                    System.out.println(productName + " quantity increased to " + cartItem[2]);  // แจ้งจำนวนสินค้าที่เพิ่มขึ้น
                                }
                                // กรณีที่ผู้ใช้ต้องการลดสินค้า
                            } else if (quantityInput.startsWith("-")) {
                                int newQuantity = currentQuantity + changeQuantity;  // คำนวณจำนวนสินค้าใหม่หลังจากลด
                                if (newQuantity <= 0) {  // ถ้าจำนวนสินค้าเหลือ 0 หรือน้อยกว่า
                                    cart.remove(cartItem);  // ลบสินค้านั้นออกจากตะกร้า
                                    System.out.println(productName + " removed from cart.");  // แจ้งว่าสินค้าถูกลบ
                                } else {
                                    cartItem[2] = String.valueOf(newQuantity);  // อัปเดตจำนวนสินค้าในตะกร้า
                                    System.out.println(productName + " quantity decreased to " + cartItem[2]);  // แจ้งจำนวนสินค้าที่ลดลง
                                }
                                // กรณีที่ผู้ใช้ระบุจำนวนที่ต้องการแทนที่
                            } else {
                                if (changeQuantity > availableStock) {  // ถ้าจำนวนสินค้าเกินสต็อก
                                    System.out.println("Not enough stock available for " + productName + "!");  // แจ้งเตือนว่าจำนวนสินค้าเกินสต็อก
                                } else {
                                    cartItem[2] = String.valueOf(changeQuantity);  // อัปเดตจำนวนสินค้าในตะกร้า
                                    System.out.println(productName + " quantity set to " + cartItem[2]);  // แจ้งจำนวนสินค้าที่แทนที่
                                }
                            }
                            found = true;  // กำหนดว่าพบสินค้านี้ในตะกร้าแล้ว
                            break;  // หยุดการค้นหาสินค้าในตะกร้า
                        }
                    }

                    // ถ้าไม่พบสินค้านี้ในตะกร้าและจำนวนที่ผู้ใช้ป้อนไม่ใช่การลด
                    if (!found && !quantityInput.startsWith("-")) {
                        if (changeQuantity > availableStock) {  // ตรวจสอบว่าจำนวนสินค้าเกินสต็อกหรือไม่
                            System.out.println("Not enough stock available for " + productName + "!");  // แจ้งเตือนว่าจำนวนสินค้าเกินสต็อก
                        } else {
                            String[] newItem = { memberId, productId, String.valueOf(changeQuantity) };  // สร้างรายการใหม่ในตะกร้า
                            cart.add(newItem);  // เพิ่มรายการสินค้าในตะกร้า
                            System.out.println(productName + " added to cart with quantity " + changeQuantity);  // แจ้งสินค้าที่ถูกเพิ่ม
                        }
                    }

                    // บันทึกตะกร้าสินค้าลงในไฟล์ CART.txt
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Windows11\\IdeaProjects\\Term1\\src\\Cart.txt"))) {
                        for (String[] cartItem : cart) {  // วนลูปเพื่อเขียนข้อมูลตะกร้าลงไฟล์
                            writer.write(String.join("\t", cartItem));  // เขียนข้อมูลแต่ละรายการลงไฟล์ด้วยการแยกด้วย "\t"
                            writer.newLine();  // ขึ้นบรรทัดใหม่สำหรับแต่ละรายการสินค้า
                        }
                    } catch (IOException e) {
                        System.out.println("Error writing to cart file!");  // แสดงข้อความเตือนถ้ามีข้อผิดพลาดในการเขียนไฟล์
                    }

                } else {
                    System.out.println("Invalid quantity input!");  // ถ้าผู้ใช้ป้อนจำนวนสินค้าไม่ถูกต้อง
                }
            } else {
                System.out.println("Your input is invalid!");  // ถ้าผู้ใช้ป้อนคำสั่งผิดรูปแบบ
            }
        }
    }


    // เมธอด Bubble Sort
    public static void sort_bubble(List<String[]> products, int sortIndex) {
        boolean swapped ;  // Flag สำหรับตรวจสอบการสลับ
        // ลูปเพื่อวนซ้ำตามจำนวนรายการสินค้า (เริ่มจาก 1 ถึงขนาดของรายการ)
        for (int i = 1; i < products.size(); i++) {
            swapped = false; // รีเซ็ต flag ทุกครั้งเมื่อเริ่มลูปใหม่
            // ลูปเปรียบเทียบแต่ละรายการจากท้ายไปต้น (j ไล่จากขนาดรายการ - 1 ไปยัง i)
            for (int j = products.size() - 1; j >= i; j--) {
                // ดึงค่าของฟิลด์ที่จะใช้เรียงลำดับจากตำแหน่ง j และ j - 1
                String valueJ = products.get(j)[sortIndex];
                String valueJMinus1 = products.get(j - 1)[sortIndex];
                // ตรวจสอบว่าเป็นการเรียงลำดับชื่อหรือปริมาณ
                // ถ้าเป็นการเรียงชื่อ
                if (sortIndex == 1) {
                    if (valueJ.compareTo(valueJMinus1) > 0) {
                        // สลับตำแหน่ง
                        swap(products,j,j-1);
                        swapped = true;
                    }
                    // ถ้าเป็นการเรียงปริมาณ
                } else if (sortIndex == 3) {
                    // แปลงค่าปริมาณจาก String เป็น int เพื่อเปรียบเทียบ
                    int quantityJ = Integer.parseInt(valueJ);
                    int quantityJMinus1 = Integer.parseInt(valueJMinus1);
                    if (quantityJ < quantityJMinus1) {
                        swap(products,j,j-1);
                        // สลับตำแหน่งสินค้า
                        swapped = true;
                    }
                }
            }
            if(!swapped)break;
        }
    }
    private static void swap(List<String[]> products, int i, int j) {
        String[] temp = products.get(i);
        products.set(i, products.get(j));
        products.set(j, temp);
    }
    public static void showNameDesc(List<String[]> products, char memberRole) {
        // เรียกใช้ Bubble Sort โดยกำหนดให้เรียงตามชื่อ (index 1)
        sort_bubble(products, 1);
        showProducts(products, memberRole); // แสดงสินค้า

    }

    public static void showQuantityAsc(List<String[]> products, char memberRole){
        // เรียกใช้ Bubble Sort โดยกำหนดให้เรียงตามปริมาณ (index 3)
        sort_bubble(products, 3);
        showProducts(products, memberRole); // แสดงสินค้า

    }


    private static double[] calculatePrices(String[] product, char memberRole) {
        double priceInDollar = Double.parseDouble(product[2].replace("$", ""));
        double convertToBaht; // ใช้อัตราแลกเปลี่ยน
        convertToBaht = priceInDollar * 34.00;
        double oldPrice = convertToBaht;
        double finalPrice = oldPrice;

        // คำนวณราคาใหม่ตามประเภทสมาชิก
        if (memberRole == '2') { // Silver
            finalPrice *= 0.95; // 5% discount
        } else if (memberRole == '3') { // Gold
            finalPrice *= 0.90; // 10% discount
        }
        return new double[]{oldPrice, finalPrice};
    }


    //สำหรับรันรหัสเพิ่มต่อจากเดิม
    private static int getNewID() throws IOException {
        File fileMember = new File("C:\\Users\\Windows11\\IdeaProjects\\Term1\\src\\Member.txt");
        BufferedReader readFileMember = new BufferedReader(new FileReader(fileMember));  // ประมวลผลแต่ละบรรทัดของข้อมูล
        // หา ID สูงสุดในไฟล์
        String line;
        int maxID = 0;
        while ((line = readFileMember.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] memberData = line.split("\t");
            int currentID = Integer.parseInt(memberData[0].trim());  // ฟิลด์แรกคือ ID
            maxID = Math.max(maxID,currentID);
        }
        // เพิ่ม ID ใหม่โดยใช้ maxID + 1
        int newID = maxID + 1;
        readFileMember.close();
        return newID;
    }

    //random password
    public static String generatePassword() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//เก็บตัวอักษร
        String digits = "0123456789";//เก็บเลข
        Random random = new Random();
        StringBuilder password = new StringBuilder("                   ");//เก็บรหัสผ่าน
        int[] letterIndexes = {0, 1, 3, 4, 5, 7, 8, 11, 12, 17, 18};
        for (int index : letterIndexes) {
            password.setCharAt(index, alphabet.charAt(random.nextInt(alphabet.length())));
        }
        // สุ่มตัวเลขในตำแหน่งที่ 9, 10, 13, 14, 15, 16
        int[] numberIndexes = {9, 10, 13, 14, 15, 16};
        for (int index : numberIndexes) {
            password.setCharAt(index, digits.charAt(random.nextInt(digits.length())));
        }
        // กำหนดสถานะบัญชีที่ตำแหน่งที่ 2 เป็น '1' (Active)
        password.setCharAt(2, '1');
        // กำหนด Role ที่ตำแหน่งที่ 6 เป็น '1' (Regular Member)
        password.setCharAt(6, '1');
        return password.toString();
    }


    //สำหรับอสดงสินค้าทั้งที่เป็นแบบยูโรและบาท
    public static void showProducts(List<String[]> products, char memberRole)  {
        System.out.println("=========== SE STORE's Products ===========\t");
        System.out.println("#\t\tName\t\tPrice(฿)\t\t\t\tQuantity");
        for (int i = 0; i < products.size(); i++) {
            String[] product = products.get(i);
            double[] prices = calculatePrices(product, memberRole);
            double oldPrice = prices[0];
            double finalPrice = prices[1];

            if (memberRole == '0' || memberRole == '1') {
                System.out.printf("%-5d %-10s %8.2f  %19s%n", i + 1, product[1], oldPrice, product[3]);
            } else if (memberRole == '2' || memberRole == '3') {
                System.out.printf("%-5d %-10s %8.2f (%7.2f) %10s%n", i + 1, product[1], oldPrice, finalPrice, product[3]);
            }
        }
        System.out.println("===========================================");
    }
    //สำหรับการแสดงสินค้าที่เอาแต่สินค้าราคาบาท
    public static void showProductsNoEuro(List<String[]> products, char memberRole){
        System.out.println("=========== SE STORE's Products ===========\t");
        System.out.println("#\tName\t\tPrice(฿)\t\tQuantity");
        for (int i = 0; i < products.size(); i++) {
            String[] product = products.get(i);
            double[] prices = calculatePrices(product, memberRole);
            double finalPrice = prices[1];

            if (memberRole == '0' || memberRole == '1') {
                System.out.printf("%-5d %-10s  %19s%n", i + 1, product[1], product[3]);
            } else if (memberRole == '2' || memberRole == '3') {
                System.out.printf("%-5d %-10s %8.2f  %10s%n", i + 1, product[1], finalPrice, product[3]);
            }
        }
        System.out.println("===========================================");
    }
    // ฟังก์ชัน Sequential Search เพื่อค้นหาหมวดหมู่
    public static String[] sequentialSearch(List<String[]> categories, String target) {
        for (String[] category : categories) {
            if (category[0].equals(target)) { // เปรียบเทียบ ID หมวดหมู่
                return category; // คืนค่าหมวดหมู่ที่พบ
            }
        }
        return null; // ถ้าไม่พบ
    }
    public static void showCategory(char memberRole) throws IOException {
        List<String[]> categories = getCategories();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            //วนลูปไม้สิ้นสุดเพื่อให้ผู้ใช้สามารถเลือกหมวดหมู่สินค้าได้ซ้ำๆจนกว่าจะออก
            System.out.println("===== SE STORE's Product Categories =====");
            // แสดงหัวข้อหลักที่เป็นหัวเรื่องของรายการหมวดหมู่สินค้า
            System.out.println("#\tCategory");
            // พิมพ์แถวสำหรับบอกลำดับและหมวดหมู่ โดยมี `\t` เป็นตัวแทนการเว้นวรรคแบบแท็บ (tab)
            for (int i = 0; i < categories.size(); i++) {
                //วนลูปแสดงข้อมูลหมวดหมู่ทั้งหมดใน categories
                System.out.println((i + 1) + "\t" + categories.get(i)[1]);
                //แสดงผลตั้งแต่ตำแหน่งที่ i+1 เพราะ i เริ่มที่ 0 และแสดงข้อมูลหมวดหมู่จาก Array ของ categories ตำแหน่งที่ i ของแต่ละ array
            }
            System.out.println("=========================================");
            System.out.print("Select Category to Show Product (1-" + categories.size() + ") or Q for exit: ");
            String select = scanner.next();
            //อินพุตของผู้ใช้ (select) เป็นเลข 1 ถึง 9 หรือไม่ ถ้าใช่ ก็ทำงานต่อไป
            if (select.matches("[1-9]|10")) {

                int lineNum = Integer.parseInt(select) - 1;  // แปลงค่า select เป็น index
                String idCategory = categories.get(lineNum)[0];  // ดึง ID หมวดหมู่
                String[] selectedCategory = sequentialSearch(categories, idCategory); // เรียกใช้ sequentialSearch

                if (selectedCategory != null) {
                    List<String[]> products = readProductsByCategory(idCategory); //ทำการอ่านไฟล์ จาก products
                    showProducts(products, memberRole);  // เรียกใช้ showProducts เพื่อแสดงสินค้าทั้งหมด
                    while (true) {
                        System.out.println("1.Show Name By DESC");
                        System.out.println("2.Show Quantity By ASC");
                        System.out.print("or Press Q to Exit : ");
                        String input = scanner.next();
                        if (input.equalsIgnoreCase("1")) {
                            showNameDesc(products, memberRole);
                        } else if (input.equalsIgnoreCase("2")) {
                            showQuantityAsc(products, memberRole);
                        } else if (input.equalsIgnoreCase("q")) {
                            break;
                        } else {
                            System.out.println("Input invalid.please input (1-2) or Q to exit");
                        }
                    }
                }
            } else if (select.equalsIgnoreCase("q")) {
                break;   // ถ้าผู้ใช้กรอก 'Q' หรือ 'q' จะออกจากโปรแกรมทไปหน้า show category
            } else {
                System.out.println("Invalid input. Please press Q to exit.");
            }
        }
    }
}

