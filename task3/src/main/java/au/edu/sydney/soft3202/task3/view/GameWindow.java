package au.edu.sydney.soft3202.task3.view;

import au.edu.sydney.soft3202.task3.model.GameBoard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Alert.AlertType;

/**
 * This is the overall window scene for the application. It creates and contains the different elements in the
 * top, bottom, center, and right side of the window, along with linking them to the model.
 *
 * Identify the mutable couplings between the View and the Model: This class and the BoardPane are the only 2 View
 * classes that mutate the Model, and all mutations go first to the GameBoard. There is coupling in other ways
 * from the View to the Model, but they are accessor methods only.
 *
 * Also note that while this represents the game window, it *contains* the Scene that JavaFX needs, and does not
 * inherit from Scene. This is true for all JavaFX components in this application, they are contained, not extended.
 */
public class GameWindow {
    private final BoardPane boardPane;
    private final Scene scene;
    private MenuBar menuBar;
    private VBox sideButtonBar;

    private final GameBoard model;

    public GameWindow(GameBoard model) {
        this.model = model;

        BorderPane pane = new BorderPane();
        this.scene = new Scene(pane);

        this.boardPane = new BoardPane(model);
        StatusBarPane statusBar = new StatusBarPane(model);
        buildMenu();
        buildSideButtons();
        buildKeyListeners();

        pane.setCenter(boardPane.getPane());
        pane.setTop(menuBar);
        pane.setRight(sideButtonBar);
        pane.setBottom(statusBar.getStatusBar());

        startDB();
        login();
    }

    private void buildKeyListeners() {
        // This allows keyboard input. Note that the scene is used, so any time
        // the window is in focus the keyboard input will be registered.
        // More often, keyboard input is more closely linked to a specific
        // node that must have focus, i.e. the Enter key in a text input to submit
        // a form.

        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.isControlDown() && event.getCode() == KeyCode.N) {
                newGameAction();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.S) {
                saveGameAction();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.L) {
                loadGameAction();
            }
        });
    }

    private void buildSideButtons() {
        Button newGameBtn = new Button("New Game");
        newGameBtn.setOnAction((event) -> newGameAction());

        Button saveGameBtn = new Button("Save game");
        saveGameBtn.setOnAction((event) -> saveGameAction());

        Button loadGameBtn = new Button("Load game");
        loadGameBtn.setOnAction((event) -> loadGameAction());

        this.sideButtonBar = new VBox(newGameBtn, saveGameBtn, loadGameBtn);
        sideButtonBar.setSpacing(10);
    }

    private void buildMenu() {
        Menu actionMenu = new Menu("Actions");

        MenuItem newGameItm = new MenuItem("New Game");
        newGameItm.setOnAction((event)-> newGameAction());

        MenuItem saveGameItem = new MenuItem("Save game");
        saveGameItem.setOnAction((event)-> saveGameAction());

        MenuItem loadGameItem = new MenuItem("Load game");
        loadGameItem.setOnAction((event)-> loadGameAction());

        actionMenu.getItems().addAll(newGameItm, saveGameItem, loadGameItem);

        this.menuBar = new MenuBar();
        menuBar.getMenus().add(actionMenu);
    }

    private void newGameAction() {
        // Note the separation here between newGameAction and doNewGame. This allows
        // for the validation aspects to be separated from the operation itself.

        if (null == model.getCurrentTurn()) { // no current game
            doNewGame();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New Game Warning");
        alert.setHeaderText("Starting a new game now will lose all current progress.");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
         doNewGame();
        }
    }

    private void saveGameAction() {
        if (null == model.getCurrentTurn()) { // no current game
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save Error");
            alert.setHeaderText("There is no game to save!");

            alert.showAndWait();
            return;
        }

        TextInputDialog textInput = new TextInputDialog("");
        textInput.setTitle("Save game");
        textInput.setHeaderText("Enter save name:");

        Optional<String> input = textInput.showAndWait();
        if (input.isPresent()) {
            String name = input.get();
            List<String> names = this.model.getUserSaveNames();
            String error = null;
            if (names.contains(name)) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Duplicate name");
                alert.setContentText("Do you want to override the existing save?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    error = this.model.overrideSave(name);
                }
            }
            else {
                error = this.model.saveGame(name);
            }

            if (error != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Save");
                alert.setHeaderText("Failed to save game!");
                alert.setContentText(error);
                alert.showAndWait();
            }
        }
    }

    private void loadGameAction() {
        List<String> names = this.model.getUserSaveNames();
        ChoiceDialog<String> dialog = new ChoiceDialog<>();
        dialog.getItems().addAll(names);

        dialog.setTitle("Load game");
        dialog.setHeaderText("Select a save");
        dialog.setContentText("Choose your save:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
//            System.out.println(result.get());
            String error;

            try {
                error = this.model.loadGame(result.get());
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Serialisation Error");
                alert.setHeaderText(e.getMessage());

                alert.showAndWait();
                return;
            }

            if (error != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Load");
                alert.setHeaderText("Failed to load game!");
                alert.setContentText(error);
                alert.showAndWait();
            }
            else {
                boardPane.updateBoard();
            }
        }

    }

    private void doNewGame() {
        // Here we have an action that we know would likely mutate the state of the model, and so the view should
        // update. Unlike the StatusBarPane that uses the observer pattern to do this, here we can just trigger it
        // because we know the model will mutate as a result of our call to it.
        // Generally speaking the observer pattern is superior - I would recommend using it instead of
        // doing it this way.

        model.newGame();
        boardPane.updateBoard();
    }

    public Scene getScene() {
        return this.scene;
    }

    public void startDB() {
        String error = this.model.startDB();
        if (error != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database");
            alert.setHeaderText("Failed to establish database!");
            alert.setContentText(error);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Platform.exit();
            }
        }
    }

    public void login() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Enter username");

        ButtonType loginBtnType = new ButtonType("Login", ButtonData.OK_DONE);
        ButtonType cancelBtnType = new ButtonType("Cancel", ButtonData.YES);
        dialog.getDialogPane().getButtonTypes().setAll(loginBtnType, cancelBtnType);

        TextField usernameTxt = new TextField();
        usernameTxt.setPromptText("Username");

        Label usernameLbl = new Label("Username: ");

        HBox hbox = new HBox(usernameLbl, usernameTxt);
        Label requirement = new Label();
        VBox vbox = new VBox(hbox, requirement);
        dialog.getDialogPane().setContent(vbox);

        Node loginBtn = dialog.getDialogPane().lookupButton(loginBtnType);
        loginBtn.setDisable(true);

        loginBtn.addEventFilter(ActionEvent.ACTION, event -> {
            String error = this.model.login(usernameTxt.getText());
            if (error != null) {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database");
                alert.setHeaderText("Failed to establish database!");
                alert.setContentText(error);
                alert.showAndWait();
            }
        });

        Node cancelBtn = dialog.getDialogPane().lookupButton(cancelBtnType);

        cancelBtn.addEventFilter(ActionEvent.ACTION, event -> {
            event.consume();
            requirement.setText("Username required!!");
        });

        usernameTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            loginBtn.setDisable(newValue.trim().isEmpty());
        });

        dialog.showAndWait();
    }
}
