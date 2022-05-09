package oxforddictionaries.view;

import oxforddictionaries.model.request.responseclasses.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class LemmaDisplayVbox {
    private VBox vbox;
    private List<List<String>> lemmas;
    private ComboBox<Integer> lemmaMenu;
    private Button selectBtn;

    public LemmaDisplayVbox() {
        this.lemmas = new ArrayList<>();
    }

    public VBox create(RetrieveEntry retrieveEntry) {
        Label titleLbl = new Label("Couldn't find the entry");
        titleLbl.setWrapText(true);
        titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        Label descLbl = new Label("Did you mean any of these words?");
        descLbl.setWrapText(true);
        descLbl.setFont(new Font(14));

        this.lemmas = createData(retrieveEntry);

        TableView<List<String>> table = new TableView<>();
        table.setFixedCellSize(25);
        table.setPrefHeight(25*lemmas.size() + 27);

        TableColumn<List<String>, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().get(0)));
        TableColumn<List<String>, String> wordCol = new TableColumn<>("Word");
        wordCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().get(1)));
        TableColumn<List<String>, String> lexiCol = new TableColumn<>("Lexical Category");
        lexiCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().get(2)));
        TableColumn<List<String>, String> gramCol = new TableColumn<>("Grammatical Feature");
        gramCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().get(3)));
        table.getColumns().addAll(idCol, wordCol, lexiCol, gramCol);

        for (TableColumn col : table.getColumns()) {
            col.setReorderable(false);
            col.setSortable(false);
        }

        for (List<String> lemma : lemmas) {
            table.getItems().add(lemma);
        }

        Label lemmaLbl = new Label("Select a word (ID): ");
        lemmaLbl.setWrapText(true);

        List<Integer> choices = new ArrayList<>();
        for (int i = 0; i < lemmas.size(); i++) {
            choices.add(i + 1);
        }

        this.lemmaMenu = new ComboBox<>();
        lemmaMenu.getItems().addAll(choices);
        lemmaMenu.getSelectionModel().selectFirst();

        this.selectBtn = new Button("Select");
        HBox lemmaMenuHBox = new HBox(lemmaLbl, lemmaMenu, selectBtn);

        this.vbox = new VBox(5);
        vbox.getChildren().addAll(titleLbl, descLbl, table, lemmaMenuHBox);
        return vbox;
    }

    public List<List<String>> createData(RetrieveEntry retrieveEntry) {
        List<List<String>> lemmas = new ArrayList<>();
        if (retrieveEntry.getResults() != null) {
            for (HeadwordEntry headwordEntry : retrieveEntry.getResults()) {
                for (LexicalEntry entry : headwordEntry.getLexicalEntries()) {
                    for (Inflection inflection : entry.getInflectionOf()) {
                        List<String> lemma = new ArrayList<>();
                        lemma.add(String.valueOf(lemmas.size() + 1));
                        lemma.add(inflection.getId());
                        lemma.add(entry.getLexicalCategory().getId());

                        if (entry.getGrammaticalFeatures() != null) {
                            for (GrammaticalFeature gramFeat : entry.getGrammaticalFeatures()) {
                                List<String> lemmaCopy = new ArrayList<>(lemma);
                                lemmaCopy.add(gramFeat.getId());
                                lemmas.add(lemmaCopy);
                            }
                            continue;
                        }
                        lemma.add("");
                        lemmas.add(lemma);
                    }
                }
            }
        }
        return lemmas;
    }

    public Integer getLemmaId() {
        return lemmaMenu.getValue();
    }

    public Button getSelectBtn() {
        return selectBtn;
    }

    public List<String> getLemma(int id) {
        return lemmas.get(id);
    }

    public int getLemmaSize() {
        return lemmas.size();
    }
}
