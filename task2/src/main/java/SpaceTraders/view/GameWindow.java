package SpaceTraders.view;
// 01d12489-f16b-4f66-96a1-ad06e3231a0b
import SpaceTraders.model.GameEngine;
import SpaceTraders.model.Loan;
import SpaceTraders.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.FontPosture;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameWindow {
    private final int width;
    private final int height;
    private GameEngine model;
    private Scene scene;
    private BorderPane borderPane;
    private HBox bottomHbox;
    private VBox centerVbox;
    private ScrollPane centerScrollPane;
    private VBox leftVbox;
    private ScrollPane leftScrollPane;
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

        this.centerVbox = new VBox();
        this.centerScrollPane = new ScrollPane();
        this.centerScrollPane.setContent(this.centerVbox);
        this.borderPane.setCenter(this.centerScrollPane);

        this.leftVbox = new VBox();
        this.leftScrollPane = new ScrollPane();
        this.leftScrollPane.setContent(this.leftVbox);
        this.borderPane.setLeft(this.leftScrollPane);

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

        this.centerScrollPane.setStyle("-fx-background: #d0d0d0; -fx-background-color:transparent;"); // make same colour as background and remove outline
        this.centerScrollPane.setFitToWidth(true);

        this.leftScrollPane.setStyle("-fx-background: #d0d0d0; -fx-background-color:transparent;"); // make same colour as background and remove outline
        this.leftScrollPane.setPrefWidth(145);
        this.leftScrollPane.setFitToWidth(true);
        this.leftScrollPane.setFitToHeight(true);

        Insets insets = new Insets(5);
        this.bottomHbox.setPadding(insets);
        this.bottomHbox.setAlignment(Pos.CENTER_LEFT);
        this.bottomHbox.setMinHeight(40);

        this.centerVbox.setPadding(insets);

        this.topVBox.setMinHeight(40);

        this.leftVbox.setMinWidth(130);
        this.leftVbox.setPadding(insets);

        this.rightVbox.setMinWidth(145);

    }

    public void status() {
        String status = this.model.getStatus();
        Color statusColour = this.model.getStatusIconColour();

        Circle statusIcon = new Circle(5, statusColour);
        Label statusLbl = new Label(status);
        statusLbl.setPadding(new Insets(0, 0, 0, 3));

        this.bottomHbox.getChildren().addAll(statusIcon, statusLbl);
    }

    public void setCenterVboxTitle(String title) {
        Label titleLbl = new Label(title);
        titleLbl.setWrapText(true);
        titleLbl.setFont(new Font(25));

        Pane titleSpacer = new Pane();
        titleSpacer.setPrefHeight(15);

        this.centerVbox.getChildren().addAll(titleLbl, titleSpacer);
    }

    public void createAuthRegister() {
        this.centerVbox.getChildren().clear();

        setCenterVboxTitle("Register");

        Label usernameLbl = new Label("Enter username: ");
        usernameLbl.setWrapText(true);
        TextField usernameInput = new TextField();
        Pane inputSpacer = new Pane();
        inputSpacer.setPrefHeight(10);

        Button register = new Button("Register");
        register.setOnAction((event -> {
            String username = usernameInput.getText();
            List<String> msg = this.model.register(username);


            ButtonType copy = new ButtonType("Copy token");


            if (msg.size() > 1) {
                handleError(msg);
            }
            else {
                String title = "Registration";
                String header = "Success!";
                String content = "Token - " + msg.get(0);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.getButtonTypes().add(copy);
                alert.setTitle(title);
                alert.setHeaderText(header);
                alert.setContentText(content);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.orElse(ButtonType.OK) == copy) {
//                System.out.println("copy");
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent clipboardContent = new ClipboardContent();
                    clipboardContent.putString(msg.get(0));
                    clipboard.setContent(clipboardContent);
                }
            }
        }));
        register.setPrefWidth(60);

        HBox registerBtns = new HBox(register);
        registerBtns.setAlignment(Pos.CENTER);

        Pane loginSpacer = new Pane();
        loginSpacer.setPrefHeight(15);

        Label loginLbl = new Label("Have an authentication token already: ");
        loginLbl.setPadding(new Insets(0, 10, 0, 0));
        loginLbl.setWrapText(true);
        Button loginBtn = new Button("Login");
        loginBtn.setOnAction((event -> {
            createAuthLogin();
        }));
        loginBtn.setPrefWidth(50);

        HBox loginHBox = new HBox(loginLbl, loginBtn);
        loginHBox.setAlignment(Pos.CENTER_LEFT);

        this.centerVbox.getChildren().addAll(usernameLbl, usernameInput, inputSpacer, registerBtns, loginSpacer, loginHBox);
    }

    public void createAuthLogin() {
        this.centerVbox.getChildren().clear();

        setCenterVboxTitle("Login");

        Label authLbl = new Label("Enter authentication token: ");
        authLbl.setWrapText(true);
        TextField authInput = new TextField();
        Pane inputSpacer = new Pane();
        inputSpacer.setPrefHeight(10);

        Button login = new Button("Login");
        login.setOnAction((event -> {
            String authToken = authInput.getText();
            List<String> msg = this.model.getAccountDetails(authToken);
            if (msg.size() > 0) {
                handleError(msg);
            }
            else {
                createSideButtons();
                System.out.println();
                accountInfo();
                this.borderPane.setRight(null);
            }
        }));
        login.setPrefWidth(50);

        HBox authBtns = new HBox(login);
        authBtns.setAlignment(Pos.CENTER);

        Pane registerSpacer = new Pane();
        registerSpacer.setPrefHeight(15);

        Label registerLbl = new Label("Register for an authentication token: ");
        registerLbl.setPadding(new Insets(0, 10, 0, 0));
        registerLbl.setWrapText(true);
        Button registerBtn = new Button("Register");
        registerBtn.setOnAction((event -> {
            createAuthRegister();
        }));
        registerBtn.setPrefWidth(60);

        HBox registerHBox = new HBox(registerLbl, registerBtn);
        registerHBox.setAlignment(Pos.CENTER_LEFT);

        this.centerVbox.getChildren().addAll(authLbl, authInput, inputSpacer, authBtns, registerSpacer, registerHBox);
    }

    public void accountInfo() {
        this.centerVbox.getChildren().clear();

        setCenterVboxTitle("Account details");

        List<String> msg = this.model.getAccountDetails(this.model.getCurrentToken().getToken());
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            String token = this.model.getCurrentToken().getToken();
            User user = this.model.getCurrentToken().getUser();
            String username = user.getUsername();
            int credits = user.getCredits();
            String joinedAt = user.getJoinedAt();
            int shipCount = user.getShipCount();
            int structureCount = user.getStructureCount();

            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent clipboardContent = new ClipboardContent();

            Label tokenLbl = new Label("Authentication token: " + token);
            tokenLbl.setOnMouseClicked((event -> {
                clipboardContent.putString(token);
                clipboard.setContent(clipboardContent);
            }));
            tokenLbl.setWrapText(true);

            Label usernameLbl = new Label("Username: " + username);
            usernameLbl.setOnMouseClicked((event -> {
                clipboardContent.putString(username);
                clipboard.setContent(clipboardContent);
            }));
            usernameLbl.setWrapText(true);

            Label creditsLbl = new Label("Credits: " + credits);
            creditsLbl.setOnMouseClicked((event -> {
                clipboardContent.putString(String.valueOf(credits));
                clipboard.setContent(clipboardContent);
            }));
            creditsLbl.setWrapText(true);

            Label joinedLbl = new Label("Joined at: " + joinedAt);
            joinedLbl.setOnMouseClicked((event -> {
                clipboardContent.putString(joinedAt);
                clipboard.setContent(clipboardContent);
            }));
            joinedLbl.setWrapText(true);

            Label shipLbl = new Label("Ship count: " + shipCount);
            shipLbl.setOnMouseClicked((event -> {
                clipboardContent.putString(String.valueOf(shipCount));
                clipboard.setContent(clipboardContent);
            }));
            shipLbl.setWrapText(true);

            Label structureLbl = new Label("Structure count: " + structureCount);
            structureLbl.setOnMouseClicked((event -> {
                clipboardContent.putString(String.valueOf(structureCount));
                clipboard.setContent(clipboardContent);
            }));
            structureLbl.setWrapText(true);

            Pane spacer = new Pane();
            spacer.setPrefHeight(20);
            Label infoLbl = new Label("Click on the text to copy the values.");
            infoLbl.setWrapText(true);


            this.centerVbox.getChildren().addAll(tokenLbl, usernameLbl, creditsLbl, joinedLbl, shipLbl, structureLbl,
                    spacer, infoLbl);
        }
    }

    public void createSideButtons() {
        int btnWidth = 85;
        int subBtnWidth = 110;

        Button accountBtn = new Button("Account");
        accountBtn.setOnAction((event -> {
            accountInfo();
        }));
        accountBtn.setPrefWidth(btnWidth);

        Insets subVboxInsets = new Insets(0, 0, 0, 10);
        VBox loansSubBtnsVbox = new VBox();
        loansSubBtnsVbox.setPadding(subVboxInsets);

        VBox shipsSubBtnsVbox = new VBox();
        shipsSubBtnsVbox.setPadding(subVboxInsets);

        VBox marketplaceSubBtnsVbox = new VBox();
        marketplaceSubBtnsVbox.setPadding(subVboxInsets);

        VBox flightsSubBtnsVbox = new VBox();
        flightsSubBtnsVbox.setPadding(subVboxInsets);

        List<VBox> vboxes = new ArrayList<VBox>();
        vboxes.add(loansSubBtnsVbox);
        vboxes.add(shipsSubBtnsVbox);
        vboxes.add(marketplaceSubBtnsVbox);
        vboxes.add(flightsSubBtnsVbox);

        Button loansBtn = new Button("Loans");
        loansBtn.setOnAction((event) -> {
            if (loansSubBtnsVbox.getChildren().size() == 0) {
                clearVboxes(vboxes);

                Button availLoansBtn = new Button("Available loans");
                availLoansBtn.setPrefWidth(subBtnWidth);
                availLoansBtn.setOnAction((subEvent) -> {
                    getAvailableLoans();
                });

                Button obtainLoansBtn = new Button("Obtain loan");
                obtainLoansBtn.setPrefWidth(subBtnWidth);
                obtainLoansBtn.setOnAction((subEvent) -> {
                    takeLoan();
                });

                Button activeLoansBtn = new Button("Active loans");
                activeLoansBtn.setPrefWidth(subBtnWidth);
                activeLoansBtn.setOnAction((subEvent) -> {

                });

                loansSubBtnsVbox.getChildren().addAll(availLoansBtn, obtainLoansBtn, activeLoansBtn);
            }
            else {
                loansSubBtnsVbox.getChildren().clear();
            }
        });
        loansBtn.setPrefWidth(btnWidth);

        Button shipsBtn = new Button("Ships");
        shipsBtn.setOnAction((event) -> {
            if (shipsSubBtnsVbox.getChildren().size() == 0) {
                clearVboxes(vboxes);

                Button availShipsBtn = new Button("Available ships");
                availShipsBtn.setPrefWidth(subBtnWidth);
                availShipsBtn.setOnAction((subEvent) -> {

                });

                Button buyShipBtn = new Button("Buy ship");
                buyShipBtn.setPrefWidth(subBtnWidth);
                buyShipBtn.setOnAction((subEvent) -> {

                });

                Button yourShipsBtn = new Button("Your ships");
                yourShipsBtn.setPrefWidth(subBtnWidth);
                yourShipsBtn.setOnAction((subEvent) -> {

                });

                Button shipFuelBtn = new Button("Buy ship fuel");
                shipFuelBtn.setPrefWidth(subBtnWidth);
                shipFuelBtn.setOnAction((subEvent) -> {

                });

                shipsSubBtnsVbox.getChildren().addAll(availShipsBtn, buyShipBtn, yourShipsBtn, shipFuelBtn);
            }
            else {
                shipsSubBtnsVbox.getChildren().clear();
            }
        });
        shipsBtn.setPrefWidth(btnWidth);

        Button marketplaceBtn = new Button("Marketplace");
        marketplaceBtn.setOnAction((event) -> {
            if (marketplaceSubBtnsVbox.getChildren().size() == 0) {
                clearVboxes(vboxes);

                Button viewMarketplaceBtn = new Button("View marketplace");
                viewMarketplaceBtn.setPrefWidth(subBtnWidth);
                viewMarketplaceBtn.setOnAction((subEvent) -> {

                });

                Button buyGoodsBtn = new Button("Buy goods");
                buyGoodsBtn.setPrefWidth(subBtnWidth);
                buyGoodsBtn.setOnAction((subEvent) -> {

                });

                Button sellGoodsBtn = new Button("Sell goods");
                sellGoodsBtn.setPrefWidth(subBtnWidth);
                sellGoodsBtn.setOnAction((subEvent) -> {

                });

                marketplaceSubBtnsVbox.getChildren().addAll(viewMarketplaceBtn, buyGoodsBtn, sellGoodsBtn);
            }
            else {
                marketplaceSubBtnsVbox.getChildren().clear();
            }
        });
        marketplaceBtn.setPrefWidth(btnWidth);

        Button locationsBtn = new Button("Locations");
        locationsBtn.setOnAction((event) -> {

        });
        locationsBtn.setPrefWidth(btnWidth);

        Button flightsBtn = new Button("Flights");
        flightsBtn.setOnAction((event) -> {
            if (flightsSubBtnsVbox.getChildren().size() == 0) {
                clearVboxes(vboxes);

                Button createFlightBtn = new Button("Create flight");
                createFlightBtn.setPrefWidth(subBtnWidth);
                createFlightBtn.setOnAction((subEvent) -> {

                });

                Button viewFlightBtn = new Button("View flight");
                viewFlightBtn.setPrefWidth(subBtnWidth);
                viewFlightBtn.setOnAction((subEvent) -> {

                });

                flightsSubBtnsVbox.getChildren().addAll(createFlightBtn, viewFlightBtn);
            }
            else {
                flightsSubBtnsVbox.getChildren().clear();
            }
        });
        flightsBtn.setPrefWidth(btnWidth);

        Pane logoutSpacer = new Pane();
        VBox.setVgrow(logoutSpacer, Priority.ALWAYS);
        logoutSpacer.setMinSize(1, 1);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction((event) -> {
            this.model.logout();
            logout();
        });
        logoutBtn.setPrefWidth(btnWidth);

        this.leftVbox.getChildren().addAll(accountBtn, loansBtn, loansSubBtnsVbox, shipsBtn, shipsSubBtnsVbox,
                marketplaceBtn, marketplaceSubBtnsVbox, locationsBtn, flightsBtn, flightsSubBtnsVbox,
                logoutSpacer, logoutBtn);
    }

    public void clearVboxes(List<VBox> vboxes) {
        for (VBox vbox : vboxes) {
            vbox.getChildren().clear();
        }
    }

    public void logout() {
        this.leftVbox.getChildren().clear();
        this.borderPane.setRight(this.rightVbox);
        createAuthLogin();
    }

    public void getAvailableLoans() {
        this.centerVbox.getChildren().clear();

        setCenterVboxTitle("Available Loans");

        List<String> msg = this.model.getAvailableLoans();
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Loan> loans = this.model.getAvailableLoansList();
            for (int i = 0; i < loans.size(); i++) {
                String type = loans.get(i).getType();
                int amount =  loans.get(i).getAmount();
                int rate = loans.get(i).getRate();
                int termInDays = loans.get(i).getTermInDays();
                boolean collateralRequired = loans.get(i).isCollateralRequired();

                Label loanLbl = new Label("Loan " + (i+1));
                loanLbl.setWrapText(true);
                Label typeLbl = new Label("Type: " + type);
                typeLbl.setWrapText(true);
                Label amountLbl = new Label("Amount: " + amount);
                amountLbl.setWrapText(true);
                Label rateLbl = new Label("Rate: " + rate);
                rateLbl.setWrapText(true);
                Label termInDaysLbl = new Label("Term in Days: " + termInDays);
                termInDaysLbl.setWrapText(true);
                Label collReqLbl = new Label("Collateral Required: " + collateralRequired);
                collReqLbl.setWrapText(true);

                VBox loanContentVbox = new VBox(typeLbl, amountLbl, rateLbl, termInDaysLbl, collReqLbl);
                loanContentVbox.setPadding(new Insets(5, 0, 15, 10));

                this.centerVbox.getChildren().addAll(loanLbl, loanContentVbox);
            }
        }
    }

    public void handleError(List<String> msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occurred!");
        String content = "Error code: " + msg.get(0);
        if (msg.size() > 1) {
            for (int i = 1; i < msg.size(); i++) {
                if (msg.get(i) != null) {
                    content += "\n" + msg.get(i);
                }
            }

        }
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void takeLoan() {
        this.centerVbox.getChildren().clear();

        setCenterVboxTitle("Take Loan");

        Label loanTypeLbl = new Label("Enter loan type: ");
        loanTypeLbl.setWrapText(true);
        TextField typeInput = new TextField();
        Button obtainBtn = new Button("Obtain");
        obtainBtn.setOnAction((event -> {
            String loanType = typeInput.getText();
            List<String> msg = this.model.takeLoan(loanType);

            if (msg.size() > 0) {
                handleError(msg);
            }
            else {
                Alert alert = new Alert(AlertType.INFORMATION);
                String title = "Take Loan";
                String header = "Success!";
                String content = "Loan obtained";
                alert.setTitle(title);
                alert.setHeaderText(header);
                alert.setContentText(content);

                alert.showAndWait();
            }
        }));
        HBox inputBtns = new HBox(obtainBtn);
        inputBtns.setAlignment(Pos.CENTER);

        this.centerVbox.getChildren().addAll(loanTypeLbl, typeInput, inputBtns);
    }

    public void getActiveLoans() {

    }



}
