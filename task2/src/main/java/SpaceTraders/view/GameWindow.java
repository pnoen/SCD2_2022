package SpaceTraders.view;

import SpaceTraders.model.GameEngine;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class GameWindow {
    private final int width;
    private final int height;
    private GameEngine model;
    private Scene scene;
    private BorderPane borderPane;
    private HBox bottomHbox;
    private VBox centerVBox;
    private VBox leftVbox;
    private VBox rightVbox;
    private VBox topVBox;


    public GameWindow(int width, int height, GameEngine model) {
        this.width = width;
        this.height = height;
        this.model = model;

        this.borderPane = new BorderPane();
        this.scene = new Scene(borderPane, width, height);

        this.bottomHbox = new HBox();
        this.borderPane.setBottom(this.bottomHbox);

        this.centerVBox = new VBox();
        this.borderPane.setCenter(this.centerVBox);

        this.leftVbox = new VBox();
        this.borderPane.setLeft(this.leftVbox);

        this.rightVbox = new VBox();
        this.borderPane.setRight(this.rightVbox);

        this.topVBox = new VBox();
        this.borderPane.setTop(this.topVBox);
    }

    public Scene getScene() {
        return this.scene;
    }

    public void draw() {
        setupMainBorderPane();

        status();
        createAuthLogin();

    }

    public void setupMainBorderPane() {
        this.borderPane.setStyle("-fx-background-color: #d0d0d0;"); // light grey

        Insets insets = new Insets(5);
        this.bottomHbox.setPadding(insets);
        this.bottomHbox.setAlignment(Pos.CENTER_LEFT);
        this.bottomHbox.setMinHeight(40);

        this.topVBox.setMinHeight(40);

        this.leftVbox.setMinWidth(100);

        this.rightVbox.setMinWidth(100);

    }

    public void status() {
        String status = this.model.getStatus();
        Color statusColour = this.model.getStatusIconColour();

        Circle statusIcon = new Circle(5, statusColour);
        Label statusLbl = new Label(status);
        statusLbl.setPadding(new Insets(0, 0, 0, 3));

        this.bottomHbox.getChildren().addAll(statusIcon, statusLbl);
    }

    public void createAuthLogin() {

        Label authLbl = new Label("Enter auth token: ");
        TextField authInput = new TextField();
        Pane inputSpacer = new Pane();
        inputSpacer.setPrefHeight(10);
//        authPane.setPadding(new Insets(10));


        Button login = new Button("Login");
        login.setOnAction((event -> {

        }));
        login.setPrefWidth(50);

        Button register = new Button("Register");
        register.setOnAction((event -> {

        }));
        register.setPrefWidth(60);

        Pane btnSpacer = new Pane();
        HBox.setHgrow(btnSpacer, Priority.ALWAYS);
        btnSpacer.setMinSize(10, 1);
        btnSpacer.setMaxWidth(100);

        HBox authBtns = new HBox(login, btnSpacer, register);
        authBtns.setAlignment(Pos.CENTER);
        this.centerVBox.getChildren().addAll(authLbl, authInput, inputSpacer, authBtns);
    }

}
