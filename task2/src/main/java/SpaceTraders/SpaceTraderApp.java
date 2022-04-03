package SpaceTraders;

import SpaceTraders.model.GameEngine;
import SpaceTraders.model.OfflineGameEngine;
import SpaceTraders.model.OnlineGameEngine;
import SpaceTraders.view.GameWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.List;

public class SpaceTraderApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        List<String> args = getParameters().getUnnamed();
        GameEngine gameEngine = getEngine(args);
        if (gameEngine == null) {
            System.out.println("Incorrect arguments");
            System.exit(0);
        }

        int width = 800;
        int height = 640;
        GameWindow gameWindow = new GameWindow(width, height, gameEngine);

        primaryStage.setTitle("Space Traders");
        primaryStage.setScene(gameWindow.getScene());
        primaryStage.setMinHeight(180);
        primaryStage.setMinWidth(300);
        primaryStage.show();

        gameWindow.draw();

    }

    public GameEngine getEngine(List<String> args) {
        if (args.size() == 1) {
            if (args.get(0).equals("offline")) {
                return new OfflineGameEngine();
            }
            else if (args.get(0).equals("online")) {
                return new OnlineGameEngine();
            }
        }
        return null;
    }



    public static void main(String[] args) {
        launch(args);
    }
}
