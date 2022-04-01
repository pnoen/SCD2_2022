package SpaceTraders.model;

import javafx.scene.paint.Color;

public class OfflineGameEngine implements GameEngine {
    private String status;
    private Color statusIconColour;

    public OfflineGameEngine(String status) {
        this.status = status;
        this.statusIconColour = Color.RED;
    }

    public String getStatus() {
        return this.status;
    }

    public Color getStatusIconColour() {
        return this.statusIconColour;
    }
}
