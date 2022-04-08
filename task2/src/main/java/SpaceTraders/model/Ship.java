package SpaceTraders.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ship {
    @SerializedName(value = "class")
    private String shipClass;
    private String manufacturer;
    private int maxCargo;
    private int plating;
    private List<PurchaseLocation> purchaseLocations;
    private int speed;
    private String type;
    private int weapons;
    private int loadingSpeed;
    private List<String> restrictedGoods;
    private List<Cargo> cargo;
    private String id;
    private int spaceAvailable;
    private int x;
    private int y;
    private String location;
    private String flightPlanId;


    public String getShipClass() {
        return shipClass;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getMaxCargo() {
        return maxCargo;
    }

    public int getPlating() {
        return plating;
    }

    public List<PurchaseLocation> getPurchaseLocations() {
        return purchaseLocations;
    }

    public int getSpeed() {
        return speed;
    }

    public String getType() {
        return type;
    }

    public int getWeapons() {
        return weapons;
    }

    public int getLoadingSpeed() {
        return loadingSpeed;
    }

    public List<String> getRestrictedGoods() {
        return restrictedGoods;
    }

    public List<Cargo> getCargo() {
        return cargo;
    }

    public String getId() {
        return id;
    }

    public int getSpaceAvailable() {
        return spaceAvailable;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getLocation() {
        return location;
    }

    public String getFlightPlanId() {
        return flightPlanId;
    }

    @Override
    public String toString() {
        return this.shipClass + " " + type;
    }

}
