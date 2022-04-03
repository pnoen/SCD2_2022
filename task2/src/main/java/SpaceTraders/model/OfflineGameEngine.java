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
}
