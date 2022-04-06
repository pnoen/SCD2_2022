package SpaceTraders.model;

import javafx.scene.paint.Color;

import java.util.List;

public interface GameEngine {
    String getStatus();

    Color getStatusIconColour();

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

}
