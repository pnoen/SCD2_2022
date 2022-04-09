package SpaceTraders.view;

import SpaceTraders.model.FlightPlan;
import SpaceTraders.model.GameEngine;
import SpaceTraders.model.Location;
import SpaceTraders.model.Ship;
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

public class FlightView {

    public void createFlightPlan(GameEngine model, VBox centerVbox) {
        centerVbox.getChildren().clear();

        setCenterVboxTitle("Create a flight plan", centerVbox);

        List<String> msg = model.getUserShips();
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Ship> ships = model.getCurrentToken().getUser().getShips();
            List<String> shipIds = new ArrayList<String>();
            for (Ship ship : ships) {
                shipIds.add(ship.getId());
            }

            Label shipIdLbl = new Label("Select ship: ");
            shipIdLbl.setWrapText(true);

            ComboBox<String> shipIdMenu = new ComboBox<String>();
            shipIdMenu.getItems().addAll(shipIds);

            HBox shipIdsHbox = new HBox(shipIdLbl, shipIdMenu);

            msg = model.findNearbyLocations("");
            if (msg.size() > 0) {
                handleError(msg);
            }
            else {
                List<String> locations = new ArrayList<String>();
                for (Location location : model.getLocations()) {
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
                        List<String> errorMsg = model.createFlightPlan(shipIdMenu.getValue(), destinationMenu.getValue());
                        if (errorMsg.size() > 0) {
                            handleError(errorMsg);
                        }
                        else {
                            FlightPlan flightPlan = model.getFlightPlan();
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

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
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

                centerVbox.getChildren().addAll(purchaseMenuVbox);
            }

        }
    }

    public void viewFlightPlan(GameEngine model, VBox centerVbox) {
        centerVbox.getChildren().clear();

        setCenterVboxTitle("View flight plan", centerVbox);

        List<String> msg = model.getUserShips();
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Ship> ships = model.getCurrentToken().getUser().getShips();
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

            centerVbox.getChildren().addAll(flightPlanLbl, viewFlightHbox, spacer, flightPlanVbox);

            viewBtn.setOnAction((event -> {
                if (flightPlanIdMenu.getValue() == null) {
                    List<String> errorMsg = new ArrayList<String>();
                    errorMsg.add("Flight plan was not selected to view");
                    handleError(errorMsg);
                }
                else {
                    List<String> errorMsg = model.viewFlightPlan(flightPlanIdMenu.getValue());
                    if (errorMsg.size() > 0) {
                        handleError(errorMsg);
                    }
                    else {
                        flightPlanVbox.getChildren().clear();
                        FlightPlan flightPlan = model.getFlightPlan();

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
