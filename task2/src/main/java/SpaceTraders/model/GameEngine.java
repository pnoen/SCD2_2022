package SpaceTraders.model;

import java.util.List;

public interface GameEngine {
    String getStatus();

    String getStatusIconColour();

    List<String> register(String username);

    List<String> getAccountDetails(String authToken);

    Token getCurrentToken();

    void logout();

    List<String> getAvailableLoans();

    List<Loan> getAvailableLoansList();

    List<String> takeLoan(String loanType);

    List<String> activeLoans();

    List<String> availableShips(String shipClass);

    List<Ship> getAvailableShips();

    List<String> purchaseShip(String location, String type);

    List<String> getUserShips();

    List<String> viewMarketPlace(String location);

    List<Goods> getGoods();

    List<String> purchaseGoods(String shipId, String good, String quantity);

    List<String> sellGoods(String shipId, String good, String quantity);

    List<String> getShipInfo(String shipId);

    List<String> findNearbyLocations(String type);

    List<Location> getLocations();

    List<String> createFlightPlan(String shipId, String destination);

    FlightPlan getFlightPlan();

    List<String> viewFlightPlan(String flightId);

    List<String> checkServerStatus();

    Status getServerStatus();
}
