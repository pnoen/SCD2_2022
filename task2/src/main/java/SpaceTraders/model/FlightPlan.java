package SpaceTraders.model;

public class FlightPlan {
    private String id;
    private String shipId;
    private String createdAt;
    private String arrivesAt;
    private String destination;
    private String departure;
    private int distance;
    private int fuelConsumed;
    private int fuelRemaining;
    private String terminatedAt;
    private int timeRemainingInSeconds;

    public String getId() {
        return id;
    }

    public String getShipId() {
        return shipId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getArrivesAt() {
        return arrivesAt;
    }

    public String getDestination() {
        return destination;
    }

    public String getDeparture() {
        return departure;
    }

    public int getDistance() {
        return distance;
    }

    public int getFuelConsumed() {
        return fuelConsumed;
    }

    public int getFuelRemaining() {
        return fuelRemaining;
    }

    public String getTerminatedAt() {
        return terminatedAt;
    }

    public int getTimeRemainingInSeconds() {
        return timeRemainingInSeconds;
    }
}
