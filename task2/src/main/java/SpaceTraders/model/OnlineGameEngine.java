package SpaceTraders.model;

import javafx.scene.paint.Color;

public class OnlineGameEngine implements GameEngine{
    private String status;
    private Color statusIconColour;

    public OnlineGameEngine(String status) {
        this.status = status;
        this.statusIconColour = Color.web("#01c629");
    }

    public String getStatus() {
        return this.status;
    }

    public Color getStatusIconColour() {
        return this.statusIconColour;
    }
}
