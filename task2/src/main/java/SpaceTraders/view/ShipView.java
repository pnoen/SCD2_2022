package SpaceTraders.view;

import SpaceTraders.model.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class ShipView {

    public void availableShips(String shipClass, GameEngine model, VBox centerVbox) {
        centerVbox.getChildren().clear();

        setCenterVboxTitle("Available Ships", centerVbox);

        List<String> msg = model.availableShips(shipClass);
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Ship> ships = model.getAvailableShips();
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

                centerVbox.getChildren().addAll(shipCountLbl, shipContentVbox);

                if (!shipClasses.contains(shipCl)) {
                    shipClasses.add(shipCl);
                }
                if (!types.contains(type)) {
                    types.add(type);
                }
            }
            filterAvailableShips(shipClasses, model, centerVbox);
            purchaseShip(locations, types, model, centerVbox);
        }
    }

    public void filterAvailableShips(List<String> shipClasses, GameEngine model, VBox centerVbox) {
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
                availableShips(shipClassMenu.getValue(), model, centerVbox);
            }

        });

        HBox filterHbox = new HBox(shipClassMenu, filterBtn);
        filterHbox.setPadding(new Insets(0, 0, 20, 10));

        centerVbox.getChildren().addAll(filterLbl, filterHbox);
    }

    public void purchaseShip(List<String> locations, List<String> types, GameEngine model, VBox centerVbox) {
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
                List<String> msg = model.purchaseShip(locationsMenu.getValue(), typesMenu.getValue());
                if (msg.size() > 0) {
                    handleError(msg);
                }
                else {
                    User user = model.getCurrentToken().getUser();
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

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
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

        centerVbox.getChildren().addAll(purchaseLbl, purchaseMenuVbox);
    }

    public void getUserShips(GameEngine model, VBox centerVbox) {
        centerVbox.getChildren().clear();

        setCenterVboxTitle("My ships", centerVbox);

        List<String> msg = model.getUserShips();
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Ship> ships = model.getCurrentToken().getUser().getShips();
            List<String> shipIds = new ArrayList<String>();

            for (int i = 0; i < ships.size(); i++) {
                Ship ship = ships.get(i);
                String id = ship.getId();

                Label shipCountLbl = new Label("Ship " + (i+1));
                shipCountLbl.setWrapText(true);
                Label idLbl = new Label("ID: " + id);
                idLbl.setWrapText(true);


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
                else {
                    Label locationLbl = new Label("Location: " + ship.getLocation());
                    locationLbl.setWrapText(true);
                    shipContentVbox.getChildren().add(locationLbl);
                }

                centerVbox.getChildren().addAll(shipCountLbl, shipContentVbox);

                shipIds.add(id);
            }

            purchaseShipFuel(shipIds, model, centerVbox);
        }
    }

    public void purchaseShipFuel(List<String> shipIds, GameEngine model, VBox centerVbox) {
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
                List<String> msg = model.purchaseGoods(shipIdsMenu.getValue(), "FUEL", quantityTxt.getText());
                if (msg.size() > 0) {
                    handleError(msg);
                }
                else {
                    goodsAlertBox("Purchase ship fuel", "Fuel purchased!", model, centerVbox);
                }
            }
        }));

        Pane menuSpacer = new Pane();
        menuSpacer.setPrefHeight(5);

        Pane btnSpacer = new Pane();
        btnSpacer.setPrefHeight(10);

        VBox purchaseMenuVbox = new VBox(shipIdsHbox, menuSpacer, quantityHbox, btnSpacer, purchaseBtn);
        purchaseMenuVbox.setPadding(new Insets(0, 0, 0, 10));

        centerVbox.getChildren().addAll(purchaseLbl, purchaseMenuVbox);
    }

    public void setCenterVboxTitle(String title, VBox centerVbox) {
        Label titleLbl = new Label(title);
        titleLbl.setWrapText(true);
        titleLbl.setFont(new Font(25));

        Pane titleSpacer = new Pane();
        titleSpacer.setPrefHeight(15);

        centerVbox.getChildren().addAll(titleLbl, titleSpacer);
    }

    public void handleError(List<String> msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has occurred!");
        String content = "";
        for (int i = 0; i < msg.size(); i++) {
            if (msg.get(i) != null) {
                content += "\n" + msg.get(i);
            }
        }
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void goodsAlertBox(String title, String header, GameEngine model, VBox centerVbox) {
        User user = model.getCurrentToken().getUser();
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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
