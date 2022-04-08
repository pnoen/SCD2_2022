package SpaceTraders.model;

import java.util.List;

public class OfflineGameEngine implements GameEngine {
    private String status;
    private String statusIconColour;

    public OfflineGameEngine() {
        this.status = "Offline";
        this.statusIconColour = "FF0000";
    }

    public String getStatus() {
        return this.status;
    }

    public String getStatusIconColour() {
        return this.statusIconColour;
    }

    public List<String> register(String username) {
        return null;
    }

    public List<String> getAccountDetails(String authToken) {
        return null;
    }

    public Token getCurrentToken() {
        return null;
    }

    public void logout() {

    }

    public List<String> getAvailableLoans() {
        return null;
    }

    public List<Loan> getAvailableLoansList() {
        return null;
    }

    public List<String> takeLoan(String loanType) {
        return null;
    }

    public List<String> activeLoans() {
        return null;
    }

    public List<String> availableShips(String shipClass) {
        return null;
    }

    public List<Ship> getAvailableShips() {
        return null;
    }

    public List<String> purchaseShip(String location, String type) {
        return null;
    }

    public List<String> getUserShips() {
        return null;
    }

    public List<String> viewMarketPlace(String location) {
        return null;
    }

    public List<Goods> getGoods() {
        return null;
    }

    public List<String> purchaseGoods(String shipId, String goods, String quantity) {
        return null;
    }

    public List<String> sellGoods(String shipId, String goods, String quantity) {
        return null;
    }

    public List<String> getShipInfo(String shipId) {
        return null;
    }

    public List<String> findNearbyLocations(String type) {
        return null;
    }

    public List<Location> getLocations() {
        return null;
    }

    public List<String> createFlightPlan(String shipId, String destination) {
        return null;
    }

    public FlightPlan getFlightPlan() {
        return null;
    }

    public List<String> viewFlightPlan(String flightId) {
        return null;
    }

    public List<String> checkServerStatus() {
        return null;
    }

    public Status getServerStatus() {
        return null;
    }
}
