public class Product {
    private final String id;
    private String name;
    private double price;
    private final int quantity;
    private final String type;

    public Product(String id ,String name, String price, String quantity, String type) {
        this.id = id;
        this.name = name;
        // Remove the dollar sign and convert the price to a double
        this.price = Double.parseDouble(price.substring(1)) * 34;
        // Convert the quantity to int
        this.quantity = Integer.parseInt(quantity);
        this.type = type;
    }

    public String getName() { return name; }
    public void setName(String newName) { this.name = newName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() {
        return quantity;
    }
    public String getType() {
        return type;
    }
    public String getId() {
        return id;
    }

    public void printItem(int roleID) {
        double discountPrice = this.price;
        if (roleID == 2) {
            discountPrice -= (this.price * 5) / 100;
            //System.out.printf("%-15s %-6.2f (%-2.2f) %-6d\n", this.name, discountPrice,  this.price, this.quantity);
            //print in format ""
            System.out.printf("%-15s %-6.2f (%-1.2f) \t %-1d \n", this.name, discountPrice, this.price, this.quantity);
        } else if (roleID == 3) {
            discountPrice -= (this.price * 10) / 100;
            System.out.printf("%-15s %-6.2f (%-1.2f) \t %-1d \n", this.name, discountPrice, this.price, this.quantity);
        } else {
            System.out.printf("%-15s %-7.2f %10s \n", this.name, this.price, this.quantity);
        }
    }
}
