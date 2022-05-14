package oxforddictionaries;

import oxforddictionaries.model.*;
import oxforddictionaries.model.request.*;
import oxforddictionaries.view.GameWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

/**
 * Main class of the application
 */
public class Main extends Application {
    private String inputApiAppId;
    private String inputAppKey;

    /**
     * Checks if the arguments exist, exit if it doesn't. Start the JavaFx stage.
     * @param primaryStage stage
     */
    @Override
    public void start(Stage primaryStage) {
        List<String> args = getParameters().getUnnamed();
        if (args.size() != 2) {
            System.out.println("Incorrect arguments");
            System.exit(-1);
        }
        InputEngine inputEngine = getInputEngine(args.get(0));
        if (inputEngine == null) {
            System.out.println("Incorrect arguments");
            System.exit(-1);
        }

        OutputEngine outputEngine = getOutputEngine(args.get(1));
        if (outputEngine == null) {
            System.out.println("Incorrect arguments");
            System.exit(-1);
        }

        int width = 800;
        int height = 640;
        GameWindow gameWindow = new GameWindow(width, height, inputEngine, outputEngine);

        primaryStage.setTitle("Oxford Dictionaries");
        primaryStage.setScene(gameWindow.getScene());
        primaryStage.setMinHeight(180);
        primaryStage.setMinWidth(300);
        primaryStage.show();

        gameWindow.draw();
    }

    /**
     * Decides which input engine to use. If it is neither online or offline, return null. If it is online, it gets the environment variables.
     *      * If the environment variables do not exist, exit the program.
     * @param engine argument
     * @return input engine
     */
    public InputEngine getInputEngine(String engine) {
        if (engine.equals("offline")) {
            return new OfflineInputEngine(new DummyAPI(), new LemmaProcessor(), new AboutData());
        }
        else if (engine.equals("online")) {
            this.inputApiAppId = System.getenv("INPUT_API_APP_ID");
            this.inputAppKey = System.getenv("INPUT_API_KEY");
            if (inputApiAppId == null || inputAppKey == null) {
                System.out.println("Environment variables not set");
                System.exit(-1);
            }
            return new OnlineInputEngine(new Request(inputApiAppId, inputAppKey), new LemmaProcessor(), new SqlDatabase(), new AboutData());
        }
        return null;
    }

    /**
     * Decides which output engine to use. If it is neither online or offline, return null. If it is online, it gets the environment variables.
     * If the environment variables do not exist, exit the program.
     * @param engine argument
     * @return output engine
     */
    public OutputEngine getOutputEngine(String engine) {
        if (engine.equals("offline")) {
            return new OfflineOutputEngine(new DummyAPI());
        }
        else if (engine.equals("online")) {
            String pastebinKey = System.getenv("PASTEBIN_API_KEY");
            if (pastebinKey == null) {
                System.out.println("Environment variables not set");
                System.exit(-1);
            }
            return new OnlineOutputEngine(pastebinKey, new Request(inputApiAppId, inputAppKey), new PastebinPostBuilder(), new PastebinFormatter());
        }
        return null;
    }

    /**
     * Run the application
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
