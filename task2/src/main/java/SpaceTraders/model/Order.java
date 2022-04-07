package SpaceTraders.model;

public class Order {
    private String good;
    private int quantity;
    private int pricePerUnit;
    private int total;

    public String getGood() {
        return good;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }

    public int getTotal() {
        return total;
    }
}
