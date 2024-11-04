import java.text.DecimalFormat;

public class Product {
    private final String id;
    private String name;
    private double price;
    private int quantity;
    private String type;

    public Product(String id, String name, String price, String quantity, String type) {
        this.id = id;
        this.name = name;
        // Remove the dollar sign and convert the price to a double
        this.price = Double.parseDouble(price.substring(1)) * 34;
        // Convert the quantity to int
        this.quantity = Integer.parseInt(quantity);
        this.type = type;
    }

    public Product(String id, String name, double price, int quantity, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public double getPrice() {
        return price;
    }

    public String getStringPrice() {
        return "$" + String.format("%.2f", getPrice() / 34);
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

    public String singleToDoubleDigit(double price) {
        DecimalFormat df = new DecimalFormat("0.00");
        if (price < 10) {
            return "0" + df.format(price);
        } else {
            return df.format(price);
        }
    }

    public void printItem(int roleID) {
        double discountPrice = this.price;
        String stringDiscountPrice;
        String stringPrice = singleToDoubleDigit(this.price);
        if (roleID == 2) {
            discountPrice -= (this.price * 5) / 100;
            stringDiscountPrice = singleToDoubleDigit(discountPrice);
            //print in format ""
            System.out.printf("%-15s %-6s (%-1s) \t %-1d \n", this.name, stringDiscountPrice, stringPrice, this.quantity);
        } else if (roleID == 3) {
            discountPrice -= (this.price * 10) / 100;
            stringDiscountPrice = singleToDoubleDigit(discountPrice);
            System.out.printf("%-15s %-6s (%-1s) \t %-1d \n", this.name, stringDiscountPrice, stringPrice, this.quantity);
        } else {
            System.out.printf("%-15s %-7s %10s \n", this.name, stringPrice, this.quantity);
        }
    }

    public void setZeroQuantity() {
        this.quantity = 0;
    }

    public void replaceQuantity(int i) {
        if (i < 0) {
            System.out.println("Notice: Quantity can't be negative. Set to 0.");
            this.quantity = 0;
        }
        else {
            this.quantity = i;
        }
    }

    public void setQuantity(int i, char operator) {
        if (operator == '+') {
            this.quantity += i;
        } else {
            if (quantity - i >= 0) {
                this.quantity -= i;
            } else {
                this.quantity = 0;
            }
        }
    }

    public void setQuantity(int i, char operator, int limit) {
        if (operator == '+') {
            if (quantity + i <= limit) {
                if (quantity == limit) {
                    System.out.println("Notice: " + getName() + "'s hitting the stock limit.");
                }
                this.quantity += i;
            } else {
                this.quantity = limit;
                System.out.println("Notice: " + getName() + "'s hitting the stock limit and set to " + limit + " (Available stock).");
            }
        } else {
            if (quantity - i >= 0) {
                this.quantity -= i;
            } else {
                this.quantity = 0;
            }
        }
    }
}
