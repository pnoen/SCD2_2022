package SpaceTraders.model;

import javafx.scene.paint.Color;

import java.util.List;

public interface GameEngine {
    String getStatus();

    Color getStatusIconColour();

    List<String> register(String username);

    List<String> login(String authToken);

    Token getCurrentToken();

    void logout();


}
