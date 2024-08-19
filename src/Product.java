public class Product {
    private String id;
    private String name;
    private double price;
    private int quantity;

    public Product(String id ,String name, String price, String quantity) {
        this.id = id;
        this.name = name;
        // Remove the dollar sign and convert the price to a double
        this.price = Double.parseDouble(price.substring(1));
        // Convert the quantity to int
        this.quantity = Integer.parseInt(quantity);
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

    public void printItem() {
        System.out.printf("%-10s %-8s %-4s", this.name, this.price, this.quantity);
        System.out.println();
    }
}
