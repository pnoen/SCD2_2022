package oxforddictionaries.view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the display pane for the entry input
 */
public class EntryInputVbox {
    private VBox vbox;
    private GridPane gridPane;
    private ComboBox<String> langsMenu;
    private TextField wordTxt;
    private ComboBox<String> fieldMenu;
    private TextField gramFeatTxt;
    private TextField lexiCateTxt;
    private TextField domainsTxt;
    private TextField registersTxt;
    private ComboBox<String> matchMenu;
    private Button searchBtn;

    /**
     * Creates the input fields for language, word, field, grammatical feature,
     * lexical category, domains, registers and strict match
     * @return vbox
     */
    public VBox create() {
        this.gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label titleLbl = new Label("Search for an entry");
        titleLbl.setWrapText(true);
        titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        createLangHbox();
        createWordHbox();
        createFieldHBox();
        createGramFeatHbox();
        createLexiCateHbox();
        createDomainsHbox();
        createRegistersHbox();
        createMatchHbox();

        Label delimiterLbl = new Label("The filters are comma-delimited.");
        delimiterLbl.setWrapText(true);

        this.searchBtn = new Button("Search");

        gridPane.add(delimiterLbl, 0, 8);
        gridPane.add(searchBtn, 0, 9);

        this.vbox = new VBox(5);
        vbox.getChildren().addAll(titleLbl, gridPane);

        return vbox;
    }

    /**
     * Creates the dropdown menu for language
     */
    public void createLangHbox() {
        Label langLbl = new Label("Language: ");
        langLbl.setWrapText(true);
        List<String> langs = new ArrayList<>();
        langs.add("en-gb");
        langs.add("en-us");
        this.langsMenu = new ComboBox<>();
        langsMenu.getItems().addAll(langs);
        langsMenu.getSelectionModel().selectFirst();

        gridPane.add(langLbl, 0, 0);
        gridPane.add(langsMenu, 1, 0);
    }

    /**
     * Creates the text field for word
     */
    public void createWordHbox() {
        Label wordLbl = new Label("Word: ");
        wordLbl.setWrapText(true);
        this.wordTxt = new TextField();

        gridPane.add(wordLbl, 0, 1);
        gridPane.add(wordTxt, 1, 1);
    }

    /**
     * Creates the dropdown for field
     */
    public void createFieldHBox() {
        Label fieldLbl = new Label("Field: (Opt.) ");
        fieldLbl.setWrapText(true);
        List<String> fields = new ArrayList<>();
        fields.add("");
        fields.add("definitions");
        fields.add("domains");
        fields.add("etymologies");
        fields.add("examples");
        fields.add("pronunciations");
        fields.add("regions");
        fields.add("registers");
        fields.add("variantForms");
        this.fieldMenu = new ComboBox<>();
        fieldMenu.getItems().addAll(fields);
        fieldMenu.getSelectionModel().selectFirst();

        gridPane.add(fieldLbl, 0, 2);
        gridPane.add(fieldMenu, 1, 2);
    }

    /**
     * Creates the text field for grammatical features
     */
    public void createGramFeatHbox() {
        Label gramFeatLbl = new Label("Grammatical Feature: (Opt.) ");
        gramFeatLbl.setWrapText(true);
        this.gramFeatTxt = new TextField();

        gridPane.add(gramFeatLbl, 0, 3);
        gridPane.add(gramFeatTxt, 1, 3);
    }

    /**
     * Creates the text field for lexical categories
     */
    public void createLexiCateHbox() {
        Label lexiCateLbl = new Label("Lexical Category: (Opt.) ");
        lexiCateLbl.setWrapText(true);
        this.lexiCateTxt = new TextField();

        gridPane.add(lexiCateLbl, 0, 4);
        gridPane.add(lexiCateTxt, 1, 4);
    }

    /**
     * Creates the text field for domains
     */
    public void createDomainsHbox() {
        Label domainsLbl = new Label("Domains: (Opt.) ");
        domainsLbl.setWrapText(true);
        this.domainsTxt = new TextField();

        gridPane.add(domainsLbl, 0, 5);
        gridPane.add(domainsTxt, 1, 5);
    }

    /**
     * Creates the text field for registers
     */
    public void createRegistersHbox() {
        Label registersLbl = new Label("Registers: (Opt.) ");
        registersLbl.setWrapText(true);
        this.registersTxt = new TextField();

        gridPane.add(registersLbl, 0, 6);
        gridPane.add(registersTxt, 1, 6);
    }

    /**
     * Create the dropdown menu for strict match
     */
    public void createMatchHbox() {
        Label matchLbl = new Label("Strict Match: (Opt.) ");
        matchLbl.setWrapText(true);
        List<String> matches = new ArrayList<>();
        matches.add("");
        matches.add("false");
        matches.add("true");
        this.matchMenu = new ComboBox<>();
        matchMenu.getItems().addAll(matches);
        matchMenu.getSelectionModel().selectFirst();

        gridPane.add(matchLbl, 0, 7);
        gridPane.add(matchMenu, 1, 7);
    }

    /**
     * Gets the language from the dropdown
     * @return language
     */
    public String getLang() {
        return langsMenu.getValue();
    }

    /**
     * Gets the word from the dropdown. If the word is empty, return null.
     * @return word
     */
    public String getWord() {
        if (wordTxt.getText().trim().equals("")) {
            return null;
        }
        return wordTxt.getText();
    }

    /**
     * Gets the field from the dropdown
     * @return field
     */
    public String getField() {
        return fieldMenu.getValue();
    }

    /**
     * Gets the grammatical features from the text field
     * @return grammatical features
     */
    public String getGramFeat() {
        return gramFeatTxt.getText();
    }

    /**
     * Gets the lexical categories from the text field
     * @return lexical categories
     */
    public String getLexiCate() {
        return lexiCateTxt.getText();
    }

    /**
     * Gets the domains from the text field
     * @return domains
     */
    public String getDomains() {
        return domainsTxt.getText();
    }

    /**
     * Gets the registers from the text field
     * @return registers
     */
    public String getRegisters() {
        return registersTxt.getText();
    }

    /**
     * Gets the strict match from the dropdown
     * @return match
     */
    public String getMatch() {
        return matchMenu.getValue();
    }

    /**
     * Gets the search button
     * @return search button
     */
    public Button getSearchBtn() {
        return searchBtn;
    }
}
