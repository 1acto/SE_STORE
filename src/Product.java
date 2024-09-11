public class Product {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String type;

    public Product(String id ,String name, String price, String quantity, String type) {
        this.id = id;
        this.name = name;
        // Remove the dollar sign and convert the price to a double
        this.price = Double.parseDouble(price.substring(1)) * 34;
        // Convert the quantity to int
        this.quantity = Integer.parseInt(quantity);
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
    public String getType() {
        return type;
    }
    public String getId() {
        return id;
    }


    public void printItem() {
        System.out.printf("%-15s %-7.2f %10s \n", this.name, this. price, this.quantity);
    }
}
