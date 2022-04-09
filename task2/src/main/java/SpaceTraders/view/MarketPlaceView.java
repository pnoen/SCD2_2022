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

public class MarketPlaceView {

    public void viewMarketPlace(String location, GameEngine model, VBox centerVbox) {
        centerVbox.getChildren().clear();

        if (location.equals("")) {
            setCenterVboxTitle("Market Place", centerVbox);
        }
        else {
            setCenterVboxTitle("Market Place - " + location, centerVbox);
        }

        List<String> msg = model.getUserShips();
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Ship> ships = model.getCurrentToken().getUser().getShips();
            List<String> shipLocations = new ArrayList<String>();
            List<String> shipIds = new ArrayList<String>();
            for (Ship ship : ships) {
                String shipLocation = ship.getLocation();
                if (shipLocation != null) {
                    if (!shipLocations.contains(shipLocation)) {
                        shipLocations.add(shipLocation);
                    }

                    if (shipLocation.equals(location)) {
                        shipIds.add(ship.getId());
                    }
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
                    viewMarketPlace(shipLocationMenu.getValue(), model, centerVbox);
                }
            });

            HBox shipLocationsHbox = new HBox(shipLocationLbl, shipLocationMenu, shipLocationBtn);

            centerVbox.getChildren().add(shipLocationsHbox);

            if (!location.equals("")) {
                msg = model.viewMarketPlace(location);
                if (msg.size() > 0) {
                    handleError(msg);
                }
                else {
                    List<Goods> goods = model.getGoods();
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

                        centerVbox.getChildren().addAll(goodCountLbl, goodsContentVbox);

                        goodSymbols.add(symbol);
                    }

                    makePurchaseOrder(shipIds, goodSymbols, model, centerVbox);
                }
            }
        }
    }

    public void makePurchaseOrder(List<String> shipIds, List<String> goods, GameEngine model, VBox centerVbox) {
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
                List<String> msg = model.purchaseGoods(shipIdsMenu.getValue(), goodsMenu.getValue(), quantityTxt.getText());
                if (msg.size() > 0) {
                    handleError(msg);
                }
                else {
                    goodsAlertBox("Purchase market place goods", "Goods purchased!", model, centerVbox);
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

        centerVbox.getChildren().addAll(purchaseLabel, purchaseMenuVbox);
    }

    public void getShipCargo(String shipId, GameEngine model, VBox centerVbox) {
        centerVbox.getChildren().clear();

        if (shipId.equals("")) {
            setCenterVboxTitle("Sell goods", centerVbox);
        }
        else {
            setCenterVboxTitle("Sell goods - Ship " + shipId, centerVbox);
        }

        List<String> msg = model.getUserShips();
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Ship> ships = model.getCurrentToken().getUser().getShips();
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
                    getShipCargo(shipIdMenu.getValue(), model, centerVbox);
                }
            });

            HBox shipLocationsHbox = new HBox(shipIdLbl, shipIdMenu, shipIdBtn);

            centerVbox.getChildren().add(shipLocationsHbox);

            if (!shipId.equals("")) {
                msg = model.getShipInfo(shipId);
                if (msg.size() > 0) {
                    handleError(msg);
                }
                else {
                    User user = model.getCurrentToken().getUser();
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

                        centerVbox.getChildren().addAll(goodCountLbl, goodsContentVbox);

                        goodsSymbols.add(good);
                    }
                    sellGoods(shipId, goodsSymbols, model, centerVbox);
                }
            }
        }
    }

    public void sellGoods(String shipId, List<String> goods, GameEngine model, VBox centerVbox) {
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
                List<String> msg = model.sellGoods(shipId, goodsMenu.getValue(), quantityTxt.getText());
                if (msg.size() > 0) {
                    handleError(msg);
                }
                else {
                    goodsAlertBox("Sell goods", "Goods sold!", model, centerVbox);
                }
            }
        }));

        Pane menuSpacer = new Pane();
        menuSpacer.setPrefHeight(5);

        Pane btnSpacer = new Pane();
        btnSpacer.setPrefHeight(10);

        VBox sellMenuVbox = new VBox(goodsHbox, menuSpacer, quantityHbox, btnSpacer, sellBtn);
        sellMenuVbox.setPadding(new Insets(0, 0, 0, 10));

        centerVbox.getChildren().addAll(sellMenuVbox);
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
