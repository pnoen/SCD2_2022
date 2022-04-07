package SpaceTraders.model;

public class Goods {
    private int pricePerUnit;
    private int purchasePricePerUnit;
    private int quantityAvailable;
    private int sellPricePerUnit;
    private int spread;
    private String symbol;
    private int volumePerUnit;

    public int getPricePerUnit() {
        return pricePerUnit;
    }

    public int getPurchasePricePerUnit() {
        return purchasePricePerUnit;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public int getSellPricePerUnit() {
        return sellPricePerUnit;
    }

    public int getSpread() {
        return spread;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getVolumePerUnit() {
        return volumePerUnit;
    }
}
