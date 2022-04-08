package SpaceTraders.model;

import java.util.List;

public class Location {
    private boolean allowsConstruction;
    private String name;
    private String symbol;
    private String type;
    private int x;
    private int y;
    private List<String> traits;
    private List<String> messages;

    public boolean getAllowsConstruction() {
        return allowsConstruction;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<String> getTraits() {
        return traits;
    }

    public List<String> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return name + " " + symbol;
    }

}
