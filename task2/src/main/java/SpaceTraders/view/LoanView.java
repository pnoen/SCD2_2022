package SpaceTraders.view;

import SpaceTraders.model.GameEngine;
import SpaceTraders.model.Loan;
import SpaceTraders.model.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class LoanView {

    public void getAvailableLoans(GameEngine model, VBox centerVbox) {
        centerVbox.getChildren().clear();

        setCenterVboxTitle("Available Loans", centerVbox);

        List<String> msg = model.getAvailableLoans();
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Loan> loans = model.getAvailableLoansList();
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

                centerVbox.getChildren().addAll(loanLbl, loanContentVbox);

                loanTypes.add(type);
            }

            takeLoan(loanTypes, model, centerVbox);
        }
    }

    public void takeLoan(List<String> loanTypes, GameEngine model, VBox centerVbox) {
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
                List<String> msg = model.takeLoan(loanType);

                if (msg.size() > 0) {
                    handleError(msg);
                }
                else {
                    User user = model.getCurrentToken().getUser();
                    String content = "Credits: " + user.getCredits();

                    Loan loan = model.getCurrentToken().getUser().getLoan();
                    content += "\nLoan";
                    content += "\n\tDue: " + loan.getDue();
                    content += "\n\tID: " + loan.getId();
                    content += "\n\tRepayment amount: " + loan.getAmount();
                    content += "\n\tStatus: " + loan.getStatus();
                    content += "\n\tType: " + loan.getType();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Take Loan");
                    alert.setHeaderText("Loan obtained!");
                    alert.setContentText(content);

                    alert.showAndWait();
                }
            }
        }));

        centerVbox.getChildren().addAll(loanTypeLbl, radioBtnsVbox, obtainBtn);
    }

    public void getActiveLoans(GameEngine model, VBox centerVbox) {
        centerVbox.getChildren().clear();

        setCenterVboxTitle("Active Loans", centerVbox);

        List<String> msg = model.activeLoans();
        if (msg.size() > 0) {
            handleError(msg);
        }
        else {
            List<Loan> loans = model.getCurrentToken().getUser().getLoans();
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

                    centerVbox.getChildren().addAll(loanLbl, loanContentVbox);
                }
            }
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
