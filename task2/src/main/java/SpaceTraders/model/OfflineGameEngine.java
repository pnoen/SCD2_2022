package SpaceTraders.model;

import javafx.scene.paint.Color;

import java.util.List;

public class OfflineGameEngine implements GameEngine {
    private String status;
    private Color statusIconColour;

    public OfflineGameEngine() {
        this.status = "Offline";
        this.statusIconColour = Color.RED;
    }

    public String getStatus() {
        return this.status;
    }

    public Color getStatusIconColour() {
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

}
