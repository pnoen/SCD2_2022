package oxforddictionaries.view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
        Label titleLbl = new Label("Search for an entry");
        titleLbl.setWrapText(true);
        titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        HBox langHbox = createLangHbox();
        HBox wordHbox = createWordHbox();
        HBox fieldHbox = createFieldHBox();
        HBox gramFeatHbox = createGramFeatHbox();
        HBox lexiCateHbox = createLexiCateHbox();
        HBox domainsHbox = createDomainsHbox();
        HBox registersHbox = createRegistersHbox();
        HBox matchHbox = createMatchHbox();

        Label delimiterLbl = new Label("The filters are comma-delimited.");
        delimiterLbl.setWrapText(true);

        this.searchBtn = new Button("Search");

        this.vbox = new VBox(5);
        vbox.getChildren().addAll(titleLbl, langHbox, wordHbox, fieldHbox, gramFeatHbox,
                lexiCateHbox, domainsHbox, registersHbox, matchHbox, delimiterLbl, searchBtn);

        return vbox;
    }

    /**
     * Creates the dropdown menu for language
     * @return hbox
     */
    public HBox createLangHbox() {
        Label langLbl = new Label("Language: ");
        langLbl.setWrapText(true);
        List<String> langs = new ArrayList<>();
        langs.add("en-gb");
        langs.add("en-us");
        this.langsMenu = new ComboBox<>();
        langsMenu.getItems().addAll(langs);
        langsMenu.getSelectionModel().selectFirst();
        HBox langHbox = new HBox(langLbl, langsMenu);
        return langHbox;
    }

    /**
     * Creates the text field for word
     * @return hbox
     */
    public HBox createWordHbox() {
        Label wordLbl = new Label("Word: ");
        wordLbl.setWrapText(true);
        this.wordTxt = new TextField();
        HBox wordHbox = new HBox(wordLbl, wordTxt);
        return wordHbox;
    }

    /**
     * Creates the dropdown for field
     * @return hbox
     */
    public HBox createFieldHBox() {
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
        HBox fieldHbox = new HBox(fieldLbl, fieldMenu);
        return fieldHbox;
    }

    /**
     * Creates the text field for grammatical features
     * @return hbox
     */
    public HBox createGramFeatHbox() {
        Label gramFeatLbl = new Label("Grammatical Feature: (Opt.) ");
        gramFeatLbl.setWrapText(true);
        this.gramFeatTxt = new TextField();
        HBox gramFeatHbox = new HBox(gramFeatLbl, gramFeatTxt);
        return gramFeatHbox;
    }

    /**
     * Creates the text field for lexical categories
     * @return hbox
     */
    public HBox createLexiCateHbox() {
        Label lexiCateLbl = new Label("Lexical Category: (Opt.) ");
        lexiCateLbl.setWrapText(true);
        this.lexiCateTxt = new TextField();
        HBox lexiCateHbox = new HBox(lexiCateLbl, lexiCateTxt);
        return lexiCateHbox;
    }

    /**
     * Creates the text field for domains
     * @return hbox
     */
    public HBox createDomainsHbox() {
        Label domainsLbl = new Label("Domains: (Opt.) ");
        domainsLbl.setWrapText(true);
        this.domainsTxt = new TextField();
        HBox domainsHbox = new HBox(domainsLbl, domainsTxt);
        return domainsHbox;
    }

    /**
     * Creates the text field for registers
     * @return hbox
     */
    public HBox createRegistersHbox() {
        Label registersLbl = new Label("Registers: (Opt.) ");
        registersLbl.setWrapText(true);
        this.registersTxt = new TextField();
        HBox registersHbox = new HBox(registersLbl, registersTxt);
        return registersHbox;
    }

    /**
     * Create the dropdown menu for strict match
     * @return hbox
     */
    public HBox createMatchHbox() {
        Label matchLbl = new Label("Strict Match: (Opt.) ");
        matchLbl.setWrapText(true);
        List<String> matches = new ArrayList<>();
        matches.add("");
        matches.add("false");
        matches.add("true");
        this.matchMenu = new ComboBox<>();
        matchMenu.getItems().addAll(matches);
        matchMenu.getSelectionModel().selectFirst();
        HBox matchHbox = new HBox(matchLbl, matchMenu);
        return matchHbox;
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
