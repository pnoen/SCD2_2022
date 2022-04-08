package SpaceTraders.view;

import SpaceTraders.model.*;
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
        Color statusColour = Color.web(this.model.getStatusIconColour());

        Circle statusIcon = new Circle(5, statusColour);
        Label statusLbl = new Label(status);
        statusLbl.setPadding(new Insets(0, 0, 0, 3));

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(1, 1);

        Button serverStatusBtn = new Button("Check server status");
        serverStatusBtn.setOnAction((event -> {
            List<String> msg = this.model.checkServerStatus();
            if (msg.size() > 0) {
                handleError(msg);
            }
            else {
                Status serverStatus = this.model.getServerStatus();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Server status");
                alert.setHeaderText("Server status");
                alert.setContentText(serverStatus.getStatus());

                alert.showAndWait();
            }
        }));

        this.bottomHbox.getChildren().addAll(statusIcon, statusLbl, spacer, serverStatusBtn);
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

            if (msg.size() > 0) {
                handleError(msg);
            }
            else {
                Token token = this.model.getCurrentToken();
                User user = token.getUser();
                String content = "Token: " + token.getToken();
                content += "\nUser: ";
                content += "\n\tUsername: " + user.getUsername();
                content += "\n\tCredits: " + user.getCredits();
                content += "\n\tShips: ";
                content += "\n\tLoans: ";

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.getButtonTypes().add(copy);
                alert.setTitle("Registration");
                alert.setHeaderText("Success!");
                alert.setContentText(content);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.orElse(ButtonType.OK) == copy) {
//                System.out.println("copy");
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent clipboardContent = new ClipboardContent();
                    clipboardContent.putString(this.model.getCurrentToken().getToken());
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

                Button activeLoansBtn = new Button("Active loans");
                activeLoansBtn.setPrefWidth(subBtnWidth);
                activeLoansBtn.setOnAction((subEvent) -> {
                    getActiveLoans();
                });

                loansSubBtnsVbox.getChildren().addAll(availLoansBtn, activeLoansBtn);
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
                    availableShips("");
                });

                Button yourShipsBtn = new Button("My ships");
                yourShipsBtn.setPrefWidth(subBtnWidth);
                yourShipsBtn.setOnAction((subEvent) -> {
                    getUserShips();
                });

                shipsSubBtnsVbox.getChildren().addAll(availShipsBtn, yourShipsBtn);
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
                    viewMarketPlace("");
                });


                Button sellGoodsBtn = new Button("Sell goods");
                sellGoodsBtn.setPrefWidth(subBtnWidth);
                sellGoodsBtn.setOnAction((subEvent) -> {
                    getShipCargo("");
                });

                marketplaceSubBtnsVbox.getChildren().addAll(viewMarketplaceBtn, sellGoodsBtn);
            }
            else {
                marketplaceSubBtnsVbox.getChildren().clear();
            }

        });
        marketplaceBtn.setPrefWidth(btnWidth);

        Button locationsBtn = new Button("Locations");
        locationsBtn.setOnAction((event) -> {
            findNearbyLocations("");
        });
        locationsBtn.setPrefWidth(btnWidth);

        Button flightsBtn = new Button("Flights");
        flightsBtn.setOnAction((event) -> {
            if (flightsSubBtnsVbox.getChildren().size() == 0) {
                clearVboxes(vboxes);

                Button createFlightBtn = new Button("Create flight");
                createFlightBtn.setPrefWidth(subBtnWidth);
                createFlightBtn.setOnAction((subEvent) -> {
                    createFlightPlan();
                });

                Button viewFlightBtn = new Button("View flight");
                viewFlightBtn.setPrefWidth(subBtnWidth);
                viewFlightBtn.setOnAction((subEvent) -> {
                    viewFlightPlan();
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
            List<String> loanTypes = new ArrayList<String>();
            for (int i = 0; i < loans.size(); i++) {
                Loan loan = loans.get(i);
                String type = loan.getType();

                Label loanLbl = new Label("Loan " + (i+1));
                loanLbl.setWrapText(true);
                Label typeLbl = new Label("Type: " + type);
                typeLbl.setWrapText(true);
                Label amountLbl = new Label("Amount: " + loan.getAmount());
                amountLbl.setWrapText(true);
                Label rateLbl = new Label("Rate: " + loan.getRate());
                rateLbl.setWrapText(true);
                Label termInDaysLbl = new Label("Term in Days: " + loan.getTermInDays());
                termInDaysLbl.setWrapText(true);
                Label collReqLbl = new Label("Collateral Required: " + loan.isCollateralRequired());
                collReqLbl.setWrapText(true);

                VBox loanContentVbox = new VBox(typeLbl, amountLbl, rateLbl, termInDaysLbl, collReqLbl);
                loanContentVbox.setPadding(new Insets(5, 0, 15, 10));

                this.centerVbox.getChildren().addAll(loanLbl, loanContentVbox);

                loanTypes.add(type);
            }

            takeLoan(loanTypes);
        }
    }

    public void takeLoan(List<String> loanTypes) {
        Label loanTypeLbl = new Label("Select a loan to obtain: ");
        loanTypeLbl.setWrapText(true);

        VBox radioBtnsVbox = new VBox();
        radioBtnsVbox.setPadding(new Insets(5, 0, 10, 10));

        ToggleGroup radioGroup = new ToggleGroup();
        for (int i = 0; i < loanTypes.size(); i++) {
            Label loanLbl = new Label("Loan " + (i+1));
            loanLbl.setWrapText(true);
            RadioButton radioBtn = new RadioButton(loanTypes.get(i));
            radioBtn.setToggleGroup(radioGroup);
            radioBtnsVbox.getChildren().addAll(loanLbl, radioBtn);
        }

        Button obtainBtn = new Button("Obtain");
        obtainBtn.setOnAction((event -> {
            RadioButton selRadio = (RadioButton) radioGroup.getSelectedToggle();
            if (selRadio == null) {
                List<String> msg = new ArrayList<String>();
                msg.add("Loan was not selected.");
                handleError(msg);
            }
            else {
                String loanType = selRadio.getText();
                List<String> msg = this.model.takeLoan(loanType);

                if (msg.size() > 0) {
                    handleError(msg);
                }
                else {
                    User user = this.model.getCurrentToken().getUser();
                    String content = "Credits: " + user.getCredits();

                    Loan loan = this.model.getCurrentToken().getUser().getLoan();
                    content += "\nLoan";
                    content += "\n\tDue: " + loan.getDue();
                    content += "\n\tID: " + loan.getId();
                    content += "\n\tRepayment amount: " + loan.getAmount();
                    content += "\n\tStatus: " + loan.getStatus();
                    content += "\n\tType: " + loan.getType();

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Take Loan");
                    alert.setHeaderText("Loan obtained!");
                    alert.setContentText(content);

                    alert.showAndWait();
                }
            }
        }));

        this.centerVbox.getChildren().addAll(loanTypeLbl, radioBtnsVbox, obtainBtn);
    }

    public void handleError(List<String> msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occurred!");
//        String content = "Error code: " + msg.get(0);
        String content = "";
        for (int i = 0; i < msg.size(); i++) {
            if (msg.get(i) != null) {
                content += "\n" + msg.get(i);
            }
        }
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void getActiveLoans() {
        this.centerVbox.getChildren().clear();

        setCenterVboxTitle("Active Loans");

        List<String> msg = this.model.activeLoans();
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Loan> loans = this.model.getCurrentToken().getUser().getLoans();
            for (int i = 0; i < loans.size(); i++) {
                Loan loan = loans.get(i);

                if (loan.getStatus().equals("CURRENT")) {
                    Label loanLbl = new Label("Loan ");
                    loanLbl.setWrapText(true);
                    Label idLbl = new Label("ID: " + loan.getId());
                    idLbl.setWrapText(true);
                    Label dueLbl = new Label("Due: " + loan.getDue());
                    dueLbl.setWrapText(true);
                    Label amountLbl = new Label("Repayment amount: " + loan.getAmount());
                    amountLbl.setWrapText(true);
                    Label statusLbl = new Label("Status: " + loan.getStatus());
                    statusLbl.setWrapText(true);
                    Label typeLbl = new Label("Type: " + loan.getType());
                    typeLbl.setWrapText(true);

                    VBox loanContentVbox = new VBox(idLbl, dueLbl, amountLbl, statusLbl, typeLbl);
                    loanContentVbox.setPadding(new Insets(5, 0, 15, 10));

                    this.centerVbox.getChildren().addAll(loanLbl, loanContentVbox);
                }
            }
        }
    }

    public void availableShips(String shipClass) {
        this.centerVbox.getChildren().clear();

        setCenterVboxTitle("Available Ships");

        List<String> msg = this.model.availableShips(shipClass);
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Ship> ships = this.model.getAvailableShips();
            List<String> shipClasses = new ArrayList<String>();
            List<String> locations = new ArrayList<String>();
            List<String> types = new ArrayList<String>();

            for (int i = 0; i < ships.size(); i++) {
                Ship ship = ships.get(i);
                String type = ship.getType();
                String shipCl = ship.getShipClass();
                List<String> restrictedGoods = ships.get(i).getRestrictedGoods();

                Label shipCountLbl = new Label("Ship " + (i+1));
                shipCountLbl.setWrapText(true);
                Label typeLbl = new Label("Type: " + type);
                typeLbl.setWrapText(true);
                Label shipClLbl = new Label("Class: " + shipCl);
                shipClLbl.setWrapText(true);
                Label maxCargoLbl = new Label("Max cargo: " + ship.getMaxCargo());
                maxCargoLbl.setWrapText(true);
                Label loadingSpeedLbl = new Label("Loading speed: " + ship.getLoadingSpeed());
                loadingSpeedLbl.setWrapText(true);
                Label speedLbl = new Label("Speed: " + ship.getSpeed());
                speedLbl.setWrapText(true);
                Label manufacturerLbl = new Label("Manufacturer: " + ship.getManufacturer());
                manufacturerLbl.setWrapText(true);
                Label platingLbl = new Label("Plating: " + ship.getPlating());
                platingLbl.setWrapText(true);
                Label weaponsLbl = new Label("Weapons: " + ship.getWeapons());
                weaponsLbl.setWrapText(true);

                Label purchaseLocationLbl = new Label("Purchase locations: ");
                purchaseLocationLbl.setWrapText(true);

                VBox shipContentVbox = new VBox(typeLbl, shipClLbl, maxCargoLbl, loadingSpeedLbl,
                        speedLbl, manufacturerLbl, platingLbl, weaponsLbl, purchaseLocationLbl);
                shipContentVbox.setPadding(new Insets(5, 0, 15, 10));

                for (PurchaseLocation purchaseLocation : ship.getPurchaseLocations()) {
                    String location = purchaseLocation.getLocation();

                    Label systemLbl = new Label("System: " + purchaseLocation.getSystem());
                    systemLbl.setWrapText(true);
                    Label locationLbl = new Label("Location: " + location);
                    locationLbl.setWrapText(true);
                    Label priceLbl = new Label("Price: " + purchaseLocation.getPrice());
                    priceLbl.setWrapText(true);

                    VBox purLocationVbox = new VBox(systemLbl, locationLbl, priceLbl);
                    purLocationVbox.setPadding(new Insets(0, 0, 5, 10));

                    shipContentVbox.getChildren().add(purLocationVbox);

                    if (!locations.contains(location)) {
                        locations.add(location);
                    }
                }

                if (restrictedGoods != null) {
                    Label restrictedGoodsLbl = new Label("Restricted goods: ");
                    restrictedGoodsLbl.setWrapText(true);

                    shipContentVbox.getChildren().add(restrictedGoodsLbl);

                    for (String good : restrictedGoods) {
                        Label goodLbl = new Label(good);
                        goodLbl.setWrapText(true);

                        VBox restrictedGoodsVbox = new VBox(goodLbl);
                        restrictedGoodsVbox.setPadding(new Insets(0, 0, 5, 10));

                        shipContentVbox.getChildren().add(restrictedGoodsVbox);
                    }
                }

                this.centerVbox.getChildren().addAll(shipCountLbl, shipContentVbox);

                if (!shipClasses.contains(shipCl)) {
                    shipClasses.add(shipCl);
                }
                if (!types.contains(type)) {
                    types.add(type);
                }
            }
            filterAvailableShips(shipClasses);
            purchaseShip(locations, types);

        }
    }

    public void filterAvailableShips(List<String> shipClasses) {
        Label filterLbl = new Label("Filter ship classes: ");
        filterLbl.setWrapText(true);

        ComboBox<String> shipClassMenu = new ComboBox<String>();
        shipClassMenu.getItems().addAll(shipClasses);

        Button filterBtn = new Button("Filter");
        filterBtn.setOnAction((event) -> {
            if (shipClassMenu.getValue() == null) {
                List<String> msg = new ArrayList<String>();
                msg.add("Class ship was not selected for filter.");
                handleError(msg);
            }
            else {
                availableShips(shipClassMenu.getValue());
            }

        });

        HBox filterHbox = new HBox(shipClassMenu, filterBtn);
        filterHbox.setPadding(new Insets(0, 0, 20, 10));

        this.centerVbox.getChildren().addAll(filterLbl, filterHbox);
    }

    public void purchaseShip(List<String> locations, List<String> types) {
        Label purchaseLbl = new Label("Purchase a ship: ");
        purchaseLbl.setWrapText(true);

        Label locationLbl = new Label("Select location: ");
        locationLbl.setWrapText(true);

        ComboBox<String> locationsMenu = new ComboBox<String>();
        locationsMenu.getItems().addAll(locations);

        HBox locationHbox = new HBox(locationLbl, locationsMenu);

        Label typesLbl = new Label("Select type: ");
        typesLbl.setWrapText(true);

        ComboBox<String> typesMenu = new ComboBox<String>();
        typesMenu.getItems().addAll(types);

        HBox typesHbox = new HBox(typesLbl, typesMenu);

        Button purchaseBtn = new Button("Purchase");
        purchaseBtn.setOnAction((event) -> {
            if (locationsMenu.getValue() == null || typesMenu.getValue() == null) {
                List<String> msg = new ArrayList<String>();
                if (locationsMenu.getValue() == null) {
                    msg.add("Location was not selected for ship purchase.");
                }
                if (typesMenu.getValue() == null) {
                    msg.add("Type was not selected for ship purchase.");
                }
                handleError(msg);
            }
            else {
                List<String> msg = this.model.purchaseShip(locationsMenu.getValue(), typesMenu.getValue());
                if (msg.size() > 0) {
                    handleError(msg);
                }
                else {
                    User user = this.model.getCurrentToken().getUser();
                    String content = "Credits: " + user.getCredits();

                    Ship ship = user.getShip();
                    content += "\nShip:";
                    content += "\n\tID: " + ship.getId();
                    content += "\n\tLocation: " + ship.getLocation();
                    content += "\n\tX: " + ship.getX();
                    content += "\n\tY: " + ship.getY();
                    content += "\n\tCargo: ";
                    for (Cargo item : ship.getCargo()) {
                        content += "\n\t\tGood: " + item.getGood();
                        content += "\n\t\tQuantity: " + item.getQuantity();
                        content += "\n\t\tTotal volume: " + item.getTotalVolume();
                    }
                    content += "\n\tSpace available: " + ship.getSpaceAvailable();
                    content += "\n\tType: " + ship.getType();
                    content += "\n\tClass: " + ship.getShipClass();
                    content += "\n\tMax cargo: " + ship.getMaxCargo();
                    content += "\n\tLoading speed: " + ship.getLoadingSpeed();
                    content += "\n\tSpeed: " + ship.getSpeed();
                    content += "\n\tManufacturer: " + ship.getManufacturer();
                    content += "\n\tPlating: " + ship.getPlating();
                    content += "\n\tWeapons: " + ship.getWeapons();

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Purchase ship");
                    alert.setHeaderText("Success!");
                    alert.setContentText(content);

                    alert.showAndWait();
                }
            }
        });

        Pane menuSpacer = new Pane();
        menuSpacer.setPrefHeight(5);

        Pane btnSpacer = new Pane();
        btnSpacer.setPrefHeight(10);

        VBox purchaseMenuVbox = new VBox(locationHbox, menuSpacer, typesHbox, btnSpacer, purchaseBtn);
        purchaseMenuVbox.setPadding(new Insets(0, 0, 0, 10));

        this.centerVbox.getChildren().addAll(purchaseLbl, purchaseMenuVbox);
    }

    public void getUserShips() {
        this.centerVbox.getChildren().clear();

        setCenterVboxTitle("My ships");

        List<String> msg = this.model.getUserShips();
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Ship> ships = this.model.getCurrentToken().getUser().getShips();
            List<String> shipIds = new ArrayList<String>();

            for (int i = 0; i < ships.size(); i++) {
                Ship ship = ships.get(i);
                String id = ship.getId();

                Label shipCountLbl = new Label("Ship " + (i+1));
                shipCountLbl.setWrapText(true);
                Label idLbl = new Label("ID: " + id);
                idLbl.setWrapText(true);
                Label locationLbl = new Label("Location: " + ship.getLocation());
                locationLbl.setWrapText(true);

                Label xLbl = new Label("X: " + ship.getX());
                xLbl.setWrapText(true);
                Label yLbl = new Label("Y: " + ship.getY());
                yLbl.setWrapText(true);
                Label spaceAvailableLbl = new Label("Space available: " + ship.getSpaceAvailable());
                spaceAvailableLbl.setWrapText(true);
                Label typeLbl = new Label("Type: " + ship.getType());
                typeLbl.setWrapText(true);
                Label shipClassLbl = new Label("Class: " + ship.getShipClass());
                shipClassLbl.setWrapText(true);
                Label maxCargoLbl = new Label("Max cargo: " + ship.getMaxCargo());
                maxCargoLbl.setWrapText(true);
                Label loadingSpeedLbl = new Label("Loading speed: " + ship.getLoadingSpeed());
                loadingSpeedLbl.setWrapText(true);
                Label speedLbl = new Label("Speed: " + ship.getSpeed());
                speedLbl.setWrapText(true);
                Label manufacturerLbl = new Label("Manufacturer: " + ship.getManufacturer());
                manufacturerLbl.setWrapText(true);
                Label platingLbl = new Label("Plating: " + ship.getPlating());
                platingLbl.setWrapText(true);
                Label weaponsLbl = new Label("Weapons: " + ship.getWeapons());
                weaponsLbl.setWrapText(true);
                Label cargoLbl = new Label ("Cargo: ");
                cargoLbl.setWrapText(true);

                VBox shipContentVbox = new VBox(idLbl, xLbl, yLbl, spaceAvailableLbl, typeLbl, shipClassLbl,
                        maxCargoLbl, loadingSpeedLbl, speedLbl, manufacturerLbl, platingLbl, weaponsLbl, cargoLbl);
                shipContentVbox.setPadding(new Insets(5, 0, 15, 10));

                for (Cargo goodCargo : ship.getCargo()) {
                    Label goodLbl = new Label("Good: " + goodCargo.getGood());
                    goodLbl.setWrapText(true);
                    Label quantityLbl = new Label("Quantity: " + goodCargo.getQuantity());
                    quantityLbl.setWrapText(true);
                    Label totalVolumeLbl = new Label("Total volume: " + goodCargo.getTotalVolume());
                    totalVolumeLbl.setWrapText(true);

                    VBox cargoVbox = new VBox(goodLbl, quantityLbl, totalVolumeLbl);
                    cargoVbox.setPadding(new Insets(0, 0, 5, 10));

                    shipContentVbox.getChildren().addAll(cargoVbox);
                }

                if (ship.getFlightPlanId() != null) {
                    Label flightPlanIdLbl = new Label("Flight plan ID: " + ship.getFlightPlanId());
                    flightPlanIdLbl.setWrapText(true);
                    shipContentVbox.getChildren().add(flightPlanIdLbl);
                }

                this.centerVbox.getChildren().addAll(shipCountLbl, shipContentVbox);

                shipIds.add(id);
            }

            purchaseShipFuel(shipIds);
        }
    }

    public void purchaseShipFuel(List<String> shipIds) {
        Label purchaseLbl = new Label("Purchase ship fuel: ");
        purchaseLbl.setWrapText(true);

        Label shipIdLbl = new Label("Select ship id: ");
        shipIdLbl.setWrapText(true);

        ComboBox<String> shipIdsMenu = new ComboBox<String>();
        shipIdsMenu.getItems().addAll(shipIds);

        HBox shipIdsHbox = new HBox(shipIdLbl, shipIdsMenu);

        Label quantityLbl = new Label("Enter quantity: ");
        quantityLbl.setWrapText(true);

        TextField quantityTxt = new TextField();

        HBox quantityHbox = new HBox(quantityLbl, quantityTxt);

        Button purchaseBtn = new Button("Purchase fuel");
        purchaseBtn.setOnAction((event -> {
            if (shipIdsMenu.getValue() == null) {
                List<String> msg = new ArrayList<String>();
                msg.add("Ship ID was not selected for ship fuel purchase.");
                handleError(msg);
            }
            else {
                List<String> msg = this.model.purchaseGoods(shipIdsMenu.getValue(), "FUEL", quantityTxt.getText());
                if (msg.size() > 0) {
                    handleError(msg);
                }
                else {
                    goodsAlertBox("Purchase ship fuel", "Fuel purchased!");
                }
            }
        }));

        Pane menuSpacer = new Pane();
        menuSpacer.setPrefHeight(5);

        Pane btnSpacer = new Pane();
        btnSpacer.setPrefHeight(10);

        VBox purchaseMenuVbox = new VBox(shipIdsHbox, menuSpacer, quantityHbox, btnSpacer, purchaseBtn);
        purchaseMenuVbox.setPadding(new Insets(0, 0, 0, 10));

        this.centerVbox.getChildren().addAll(purchaseLbl, purchaseMenuVbox);
    }

    public void viewMarketPlace(String location) {
        this.centerVbox.getChildren().clear();

        if (location.equals("")) {
            setCenterVboxTitle("Market Place");
        }
        else {
            setCenterVboxTitle("Market Place - " + location);
        }

        List<String> msg = this.model.getUserShips();
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Ship> ships = this.model.getCurrentToken().getUser().getShips();
            List<String> shipLocations = new ArrayList<String>();
            List<String> shipIds = new ArrayList<String>();
            for (Ship ship : ships) {
                String shipLocation = ship.getLocation();
                if (!shipLocations.contains(shipLocation)) {
                    shipLocations.add(shipLocation);
                }

                if (shipLocation.equals(location)) {
                    shipIds.add(ship.getId());
                }
            }

            Label shipLocationLbl = new Label("Select location: ");
            shipLocationLbl.setWrapText(true);

            ComboBox<String> shipLocationMenu = new ComboBox<String>();
            shipLocationMenu.getItems().addAll(shipLocations);

            Button shipLocationBtn = new Button("Search");
            shipLocationBtn.setOnAction((event) -> {
                if (shipLocationMenu.getValue() == null) {
                    List<String> errorMsg = new ArrayList<String>();
                    errorMsg.add("Location was not selected for locating market place.");
                    handleError(errorMsg);
                }
                else {
                    viewMarketPlace(shipLocationMenu.getValue());
                }
            });

            HBox shipLocationsHbox = new HBox(shipLocationLbl, shipLocationMenu, shipLocationBtn);

            this.centerVbox.getChildren().add(shipLocationsHbox);

            if (!location.equals("")) {
                msg = this.model.viewMarketPlace(location);
                if (msg.size() > 0) {
                    handleError(msg);
                }
                else {
                    List<Goods> goods = this.model.getGoods();
                    List<String> goodSymbols = new ArrayList<String>();

                    for (int i = 0; i < goods.size(); i++) {
                        Goods goodsItm = goods.get(i);
                        String symbol = goodsItm.getSymbol();

                        Label goodCountLbl = new Label("Good " + (i+1));
                        goodCountLbl.setWrapText(true);
                        Label pricePerUnitLbl = new Label("Price per unit: " + goodsItm.getPricePerUnit());
                        pricePerUnitLbl.setWrapText(true);
                        Label purchasePricePerUnitLbl = new Label("Purchase price per unit: " + goodsItm.getPurchasePricePerUnit());
                        purchasePricePerUnitLbl.setWrapText(true);
                        Label quantityAvailableLbl = new Label("Quantity available: " + goodsItm.getQuantityAvailable());
                        quantityAvailableLbl.setWrapText(true);
                        Label sellPricePerUnitLbl = new Label("Sell price per unit: " + goodsItm.getSellPricePerUnit());
                        sellPricePerUnitLbl.setWrapText(true);
                        Label spreadLbl = new Label("Spread: " + goodsItm.getSpread());
                        spreadLbl.setWrapText(true);
                        Label symbolLbl = new Label("Symbol: " + symbol);
                        symbolLbl.setWrapText(true);
                        Label volumePerUnitLbl = new Label("Volume per unit: " + goodsItm.getVolumePerUnit());
                        volumePerUnitLbl.setWrapText(true);

                        VBox goodsContentVbox = new VBox(pricePerUnitLbl, purchasePricePerUnitLbl, quantityAvailableLbl,
                                sellPricePerUnitLbl, spreadLbl, symbolLbl, volumePerUnitLbl);
                        goodsContentVbox.setPadding(new Insets(5, 0, 15, 10));

                        this.centerVbox.getChildren().addAll(goodCountLbl, goodsContentVbox);

                        goodSymbols.add(symbol);
                    }

                    makePurchaseOrder(shipIds, goodSymbols);
                }
            }
        }
    }

    public void makePurchaseOrder(List<String> shipIds, List<String> goods) {
        Label purchaseLabel = new Label("Purchase good");
        purchaseLabel.setWrapText(true);

        Label shipIdLbl = new Label("Select ship id: ");
        shipIdLbl.setWrapText(true);

        ComboBox<String> shipIdsMenu = new ComboBox<String>();
        shipIdsMenu.getItems().addAll(shipIds);

        HBox shipIdsHbox = new HBox(shipIdLbl, shipIdsMenu);

        Label goodsLbl = new Label("Select a good: ");
        goodsLbl.setWrapText(true);

        ComboBox<String> goodsMenu = new ComboBox<String>();
        goodsMenu.getItems().addAll(goods);

        HBox goodsHbox = new HBox(goodsLbl, goodsMenu);

        Label quantityLbl = new Label("Enter quantity: ");
        quantityLbl.setWrapText(true);

        TextField quantityTxt = new TextField();

        HBox quantityHbox = new HBox(quantityLbl, quantityTxt);

        Button purchaseBtn = new Button("Purchase goods");
        purchaseBtn.setOnAction((event -> {
            if (shipIdsMenu.getValue() == null || goodsMenu.getValue() == null) {
                List<String> msg = new ArrayList<String>();
                if (shipIdsMenu.getValue() == null) {
                    msg.add("Ship ID was not selected for goods purchase.");
                }
                if (goodsMenu.getValue() == null) {
                    msg.add("Good was not selected for goods purchase.");
                }
                handleError(msg);
            }
            else {
                List<String> msg = this.model.purchaseGoods(shipIdsMenu.getValue(), goodsMenu.getValue(), quantityTxt.getText());
                if (msg.size() > 0) {
                    handleError(msg);
                }
                else {
                    goodsAlertBox("Purchase market place goods", "Goods purchased!");
                }
            }
        }));

        Pane menuSpacer = new Pane();
        menuSpacer.setPrefHeight(5);

        Pane tFieldSpacer = new Pane();
        tFieldSpacer.setPrefHeight(5);

        Pane btnSpacer = new Pane();
        btnSpacer.setPrefHeight(10);

        VBox purchaseMenuVbox = new VBox(shipIdsHbox, menuSpacer, goodsHbox, tFieldSpacer,
                quantityHbox, btnSpacer, purchaseBtn);
        purchaseMenuVbox.setPadding(new Insets(0, 0, 0, 10));

        this.centerVbox.getChildren().addAll(purchaseLabel, purchaseMenuVbox);
    }

    public void getShipCargo(String shipId) {
        this.centerVbox.getChildren().clear();

        if (shipId.equals("")) {
            setCenterVboxTitle("Sell goods");
        }
        else {
            setCenterVboxTitle("Sell goods - Ship " + shipId);
        }

        List<String> msg = this.model.getUserShips();
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Ship> ships = this.model.getCurrentToken().getUser().getShips();
            List<String> shipIds = new ArrayList<String>();
            for (Ship ship : ships) {
                if (ship.getCargo().size() > 0) {
                    shipIds.add(ship.getId());
                }
            }

            Label shipIdLbl = new Label("Select ship: ");
            shipIdLbl.setWrapText(true);

            ComboBox<String> shipIdMenu = new ComboBox<String>();
            shipIdMenu.getItems().addAll(shipIds);

            Button shipIdBtn = new Button("Get goods");
            shipIdBtn.setOnAction((event) -> {
                if (shipIdMenu.getValue() == null) {
                    List<String> errorMsg = new ArrayList<String>();
                    errorMsg.add("Ship was not selected for selling goods.");
                    handleError(errorMsg);
                }
                else {
                    getShipCargo(shipIdMenu.getValue());
                }
            });

            HBox shipLocationsHbox = new HBox(shipIdLbl, shipIdMenu, shipIdBtn);

            this.centerVbox.getChildren().add(shipLocationsHbox);

            if (!shipId.equals("")) {
                msg = this.model.getShipInfo(shipId);
                if (msg.size() > 0) {
                    handleError(msg);
                }
                else {
                    User user = this.model.getCurrentToken().getUser();
                    List<Cargo> cargo = user.getShip().getCargo();
                    List<String> goodsSymbols = new ArrayList<String>();

                    for (int i = 0; i < cargo.size(); i++) {
                        Cargo cargoGood = cargo.get(i);
                        String good = cargoGood.getGood();

                        Label goodCountLbl = new Label("Item " + (i+1));
                        goodCountLbl.setWrapText(true);
                        Label goodLbl =  new Label("Good: " + good);
                        goodLbl.setWrapText(true);
                        Label quantityLbl = new Label("Quantity: " + cargoGood.getQuantity());
                        quantityLbl.setWrapText(true);
                        Label totalVolumeLbl = new Label("Total volume: " + cargoGood.getTotalVolume());
                        totalVolumeLbl.setWrapText(true);


                        VBox goodsContentVbox = new VBox(goodLbl, quantityLbl, totalVolumeLbl);
                        goodsContentVbox.setPadding(new Insets(5, 0, 15, 10));

                        this.centerVbox.getChildren().addAll(goodCountLbl, goodsContentVbox);

                        goodsSymbols.add(good);
                    }
                    sellGoods(shipId, goodsSymbols);
                }
            }
        }
    }

    public void sellGoods(String shipId, List<String> goods) {
        Label goodsLbl = new Label("Select a good: ");
        goodsLbl.setWrapText(true);

        ComboBox<String> goodsMenu = new ComboBox<String>();
        goodsMenu.getItems().addAll(goods);

        HBox goodsHbox = new HBox(goodsLbl, goodsMenu);

        Label quantityLbl = new Label("Enter quantity: ");
        quantityLbl.setWrapText(true);

        TextField quantityTxt = new TextField();

        HBox quantityHbox = new HBox(quantityLbl, quantityTxt);

        Button sellBtn = new Button("Sell goods");
        sellBtn.setOnAction((event -> {
            if (goodsMenu.getValue() == null) {
                List<String> msg = new ArrayList<String>();
                msg.add("Good was not selected for goods purchase.");
                handleError(msg);
            }
            else {
                List<String> msg = this.model.sellGoods(shipId, goodsMenu.getValue(), quantityTxt.getText());
                if (msg.size() > 0) {
                    handleError(msg);
                }
                else {
                    goodsAlertBox("Sell goods", "Goods sold!");
                }
            }
        }));

        Pane menuSpacer = new Pane();
        menuSpacer.setPrefHeight(5);

        Pane btnSpacer = new Pane();
        btnSpacer.setPrefHeight(10);

        VBox sellMenuVbox = new VBox(goodsHbox, menuSpacer, quantityHbox, btnSpacer, sellBtn);
        sellMenuVbox.setPadding(new Insets(0, 0, 0, 10));

        this.centerVbox.getChildren().addAll(sellMenuVbox);
    }

    public void goodsAlertBox(String title, String header) {
        User user = this.model.getCurrentToken().getUser();
        String content = "Credits: " + user.getCredits();

        Order order = user.getOrder();
        content += "\nOrder: ";
        content += "\n\tGood: " + order.getGood();
        content += "\n\tQuantity: " + order.getQuantity();
        content += "\n\tPrice per unit: " + order.getPricePerUnit();
        content += "\n\tTotal: " + order.getTotal();

        Ship ship = user.getShip();
        content += "\nShip:";
        content += "\n\tID: " + ship.getId();
        content += "\n\tLocation: " + ship.getLocation();
        content += "\n\tX: " + ship.getX();
        content += "\n\tY: " + ship.getY();
        content += "\n\tCargo: ";
        for (Cargo item : ship.getCargo()) {
            content += "\n\t\tGood: " + item.getGood();
            content += "\n\t\tQuantity: " + item.getQuantity();
            content += "\n\t\tTotal volume: " + item.getTotalVolume();
        }
        content += "\n\tSpace available: " + ship.getSpaceAvailable();
        content += "\n\tType: " + ship.getType();
        content += "\n\tClass: " + ship.getShipClass();
        content += "\n\tMax cargo: " + ship.getMaxCargo();
        content += "\n\tLoading speed: " + ship.getLoadingSpeed();
        content += "\n\tSpeed: " + ship.getSpeed();
        content += "\n\tManufacturer: " + ship.getManufacturer();
        content += "\n\tPlating: " + ship.getPlating();
        content += "\n\tWeapons: " + ship.getWeapons();

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public void findNearbyLocations(String type) {
        this.centerVbox.getChildren().clear();

        setCenterVboxTitle("Nearby locations");

        List<String> msg = this.model.findNearbyLocations(type);
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Location> locations = this.model.getLocations();
            List<String> types = new ArrayList<String>();

            for (int i = 0; i < locations.size(); i++) {
                Location location = locations.get(i);
                String locationType = location.getType();

                Label locationCountLbl = new Label("Location " + (i+1));
                locationCountLbl.setWrapText(true);
                Label symbolLbl = new Label("Symbol: " + location.getSymbol());
                symbolLbl.setWrapText(true);
                Label typeLbl = new Label("Type: " + locationType);
                typeLbl.setWrapText(true);
                Label nameLbl = new Label("Name: " + location.getName());
                nameLbl.setWrapText(true);
                Label xLbl = new Label("X: " + location.getX());
                xLbl.setWrapText(true);
                Label yLbl = new Label("Y: " + location.getY());
                yLbl.setWrapText(true);
                Label allowsConstructionLbl = new Label("Allows construction: " + location.getAllowsConstruction());
                allowsConstructionLbl.setWrapText(true);
                Label traitsLbl = new Label("Traits: ");
                traitsLbl.setWrapText(true);

                VBox locationsVbox = new VBox(symbolLbl, typeLbl, nameLbl, xLbl, yLbl, allowsConstructionLbl, traitsLbl);
                locationsVbox.setPadding(new Insets(5, 0, 15, 10));

                for (String trait : location.getTraits()) {
                    Label traitLbl = new Label(trait);
                    traitLbl.setWrapText(true);

                    VBox traitVbox = new VBox(traitLbl);
                    traitVbox.setPadding(new Insets(0, 0, 5, 10));

                    locationsVbox.getChildren().add(traitVbox);
                }

                if (location.getMessages() != null) {
                    Label messagesLbl = new Label("Messages: ");
                    messagesLbl.setWrapText(true);

                    locationsVbox.getChildren().add(messagesLbl);
                    for (String message : location.getMessages()) {
                        Label messageLbl = new Label(message);
                        messageLbl.setWrapText(true);

                        VBox messagesVbox = new VBox(messageLbl);
                        messagesVbox.setPadding(new Insets(0, 0, 5, 10));

                        locationsVbox.getChildren().add(messagesVbox);
                    }
                }

                this.centerVbox.getChildren().addAll(locationCountLbl, locationsVbox);

                if (!types.contains(locationType)) {
                    types.add(locationType);
                }
            }
            filterLocations(types);
        }
    }

    public void filterLocations(List<String> types) {
        Label filterLbl = new Label("Filter locations: ");
        filterLbl.setWrapText(true);

        ComboBox<String> locationsMenu = new ComboBox<String>();
        locationsMenu.getItems().addAll(types);

        Button filterBtn = new Button("Filter");
        filterBtn.setOnAction((event) -> {
            if (locationsMenu.getValue() == null) {
                List<String> msg = new ArrayList<String>();
                msg.add("Type was not selected for filter.");
                handleError(msg);
            }
            else {
                findNearbyLocations(locationsMenu.getValue());
            }
        });

        HBox filterHbox = new HBox(locationsMenu, filterBtn);
        filterHbox.setPadding(new Insets(0, 0, 20, 10));

        this.centerVbox.getChildren().addAll(filterLbl, filterHbox);
    }

    public void createFlightPlan() {
        this.centerVbox.getChildren().clear();

        setCenterVboxTitle("Create a flight plan");

        List<String> msg = this.model.getUserShips();
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Ship> ships = this.model.getCurrentToken().getUser().getShips();
            List<String> shipIds = new ArrayList<String>();
            for (Ship ship : ships) {
                shipIds.add(ship.getId());
            }

            Label shipIdLbl = new Label("Select ship: ");
            shipIdLbl.setWrapText(true);

            ComboBox<String> shipIdMenu = new ComboBox<String>();
            shipIdMenu.getItems().addAll(shipIds);

            HBox shipIdsHbox = new HBox(shipIdLbl, shipIdMenu);

            msg = this.model.findNearbyLocations("");
            if (msg.size() > 0) {
                handleError(msg);
            }
            else {
                List<String> locations = new ArrayList<String>();
                for (Location location : this.model.getLocations()) {
                    String symbol = location.getSymbol();
                    if (!locations.contains(symbol)) {
                        locations.add(symbol);
                    }
                }

                Label destinationLbl = new Label("Enter destination symbol: ");
                destinationLbl.setWrapText(true);

                ComboBox<String> destinationMenu = new ComboBox<String>();
                destinationMenu.getItems().addAll(locations);

                HBox destinationsHbox = new HBox(destinationLbl, destinationMenu);

                Button submitBtn = new Button("Submit");
                submitBtn.setOnAction((event) -> {
                    if (shipIdMenu.getValue() == null || destinationMenu.getValue() == null) {
                        List<String> errorMsg = new ArrayList<String>();
                        if (shipIdMenu.getValue() == null) {
                            errorMsg.add("Ship was not selected for the flight plan.");
                        }
                        if (destinationMenu.getValue() == null) {
                            errorMsg.add("Destination was not selected for the flight plan");
                        }
                        handleError(errorMsg);
                    }
                    else {
                        List<String> errorMsg = this.model.createFlightPlan(shipIdMenu.getValue(), destinationMenu.getValue());
                        if (errorMsg.size() > 0) {
                            handleError(errorMsg);
                        }
                        else {
                            FlightPlan flightPlan = this.model.getFlightPlan();
                            String content = "Flight plan";
                            content += "\n\tID: " + flightPlan.getId();
                            content += "\n\tShip ID: " + flightPlan.getShipId();
                            content += "\n\tCreated at: " + flightPlan.getCreatedAt();
                            content += "\n\tArrives at: " + flightPlan.getArrivesAt();
                            content += "\n\tDestination: " + flightPlan.getDestination();
                            content += "\n\tDeparture: " + flightPlan.getDeparture();
                            content += "\n\tDistance: " + flightPlan.getDistance();
                            content += "\n\tFuel consumed: " + flightPlan.getFuelConsumed();
                            content += "\n\tFuel remaining: " + flightPlan.getFuelRemaining();
                            content += "\n\tTerminated at: " + flightPlan.getTerminatedAt();
                            content += "\n\tTime remaining in seconds: " + flightPlan.getTimeRemainingInSeconds();

                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Flight plan");
                            alert.setHeaderText("Created flight plan!");
                            alert.setContentText(content);

                            alert.showAndWait();
                        }
                    }
                });

                Pane menuSpacer = new Pane();
                menuSpacer.setPrefHeight(5);

                Pane btnSpacer = new Pane();
                btnSpacer.setPrefHeight(10);

                VBox purchaseMenuVbox = new VBox(shipIdsHbox, menuSpacer, destinationsHbox, btnSpacer, submitBtn);
                purchaseMenuVbox.setPadding(new Insets(0, 0, 0, 10));

                this.centerVbox.getChildren().addAll(purchaseMenuVbox);
            }

        }
    }

    public void viewFlightPlan() {
        this.centerVbox.getChildren().clear();

        setCenterVboxTitle("View flight plan");

        List<String> msg = this.model.getUserShips();
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Ship> ships = this.model.getCurrentToken().getUser().getShips();
            List<String> flightPlanIds = new ArrayList<String>();
            for (Ship ship : ships) {
                if (ship.getFlightPlanId() != null) {
                    flightPlanIds.add(ship.getFlightPlanId());
                }
            }

            Label flightPlanLbl = new Label("Select flight plan");
            flightPlanLbl.setWrapText(true);

            ComboBox<String> flightPlanIdMenu = new ComboBox<String>();
            flightPlanIdMenu.getItems().addAll(flightPlanIds);

            Button viewBtn = new Button("View");

            HBox viewFlightHbox = new HBox(flightPlanIdMenu, viewBtn);
            VBox flightPlanVbox = new VBox();

            Pane spacer = new Pane();
            spacer.setPrefHeight(20);

            this.centerVbox.getChildren().addAll(flightPlanLbl, viewFlightHbox, spacer, flightPlanVbox);

            viewBtn.setOnAction((event -> {
                if (flightPlanIdMenu.getValue() == null) {
                    List<String> errorMsg = new ArrayList<String>();
                    errorMsg.add("Flight plan was not selected to view");
                    handleError(errorMsg);
                }
                else {
                    List<String> errorMsg = this.model.viewFlightPlan(flightPlanIdMenu.getValue());
                    if (errorMsg.size() > 0) {
                        handleError(errorMsg);
                    }
                    else {
                        flightPlanVbox.getChildren().clear();
                        FlightPlan flightPlan = this.model.getFlightPlan();

                        Label idLbl = new Label("ID: " + flightPlan.getId());
                        idLbl.setWrapText(true);
                        Label shipIdLbl = new Label("Ship ID: " + flightPlan.getShipId());
                        shipIdLbl.setWrapText(true);
                        Label createdAtLbl = new Label("Created at: " + flightPlan.getCreatedAt());
                        createdAtLbl.setWrapText(true);
                        Label arrivesAtLbl = new Label("Arrives at: " + flightPlan.getArrivesAt());
                        arrivesAtLbl.setWrapText(true);
                        Label destinationLbl = new Label("Destination: " + flightPlan.getDestination());
                        destinationLbl.setWrapText(true);
                        Label departureLbl = new Label("Departure: " + flightPlan.getDeparture());
                        departureLbl.setWrapText(true);
                        Label distanceLbl = new Label("Distance: " + flightPlan.getDistance());
                        distanceLbl.setWrapText(true);
                        Label fuelConsumedLbl = new Label("Fuel consumed: " + flightPlan.getFuelConsumed());
                        fuelConsumedLbl.setWrapText(true);
                        Label fuelRemainingLbl = new Label("Fuel remaining: " + flightPlan.getFuelRemaining());
                        fuelRemainingLbl.setWrapText(true);
                        Label terminatedAtLbl = new Label("Terminated at: " + flightPlan.getTerminatedAt());
                        terminatedAtLbl.setWrapText(true);
                        Label timeRemainingInSecondsLbl = new Label("Time remaining in seconds: " + flightPlan.getTimeRemainingInSeconds());
                        timeRemainingInSecondsLbl.setWrapText(true);

                        flightPlanVbox.getChildren().addAll(idLbl, shipIdLbl, createdAtLbl, arrivesAtLbl, destinationLbl,
                                departureLbl, distanceLbl, fuelConsumedLbl, fuelRemainingLbl, terminatedAtLbl, timeRemainingInSecondsLbl);
                    }
                }
            }));
        }
    }

}

