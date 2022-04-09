package SpaceTraders.view;

import SpaceTraders.model.GameEngine;
import SpaceTraders.model.Location;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class LocationView {

    public void findNearbyLocations(String type, GameEngine model, VBox centerVbox) {
        centerVbox.getChildren().clear();

        setCenterVboxTitle("Nearby locations", centerVbox);

        List<String> msg = model.findNearbyLocations(type);
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Location> locations = model.getLocations();
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

                centerVbox.getChildren().addAll(locationCountLbl, locationsVbox);

                if (!types.contains(locationType)) {
                    types.add(locationType);
                }
            }
            filterLocations(types, model, centerVbox);
        }
    }

    public void filterLocations(List<String> types, GameEngine model, VBox centerVbox) {
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
                findNearbyLocations(locationsMenu.getValue(), model, centerVbox);
            }
        });

        HBox filterHbox = new HBox(locationsMenu, filterBtn);
        filterHbox.setPadding(new Insets(0, 0, 20, 10));

        centerVbox.getChildren().addAll(filterLbl, filterHbox);
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
}
