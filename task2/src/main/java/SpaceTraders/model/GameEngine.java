package SpaceTraders.model;

import javafx.scene.paint.Color;

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

    Ship getShip();

    List<String> getUserShips();

//    List<String> purchaseShipFuel(String shipId, String quantity);

    List<String> viewMarketPlace(String location);

    List<Goods> getGoods();

    List<String> purchaseGoods(String shipId, String good, String quantity);

    Order getOrder();

    List<String> sellGoods(String shipId, String good, String quantity);

    List<String> getShipInfo(String shipId);

}
