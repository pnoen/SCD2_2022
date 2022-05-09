package oxforddictionaries.view;

import oxforddictionaries.model.request.responseclasses.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class EntryDisplayVbox {
    private VBox vbox;
    private Insets padding;
    private Insets listPadding;
    private List<String> synAntTexts;
    private List<HBox> synAntHboxes;
    private CustomItemBuilder customItemBuilder;

    public EntryDisplayVbox() {
        this.padding = new Insets(0, 0, 0, 20);
        this.listPadding = new Insets(0, 0, 10, 15);
        this.synAntTexts = new ArrayList<>();
        this.synAntHboxes = new ArrayList<>();
        this.customItemBuilder = new CustomItemBuilder();
    }

    public VBox create(RetrieveEntry retrieveEntry) {
        synAntTexts.clear();
        synAntHboxes.clear();

        this.vbox = new VBox(5);

        Label titleLbl = new Label(retrieveEntry.getWord());
        titleLbl.setWrapText(true);
        titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        TreeItem<CustomItem> root = new TreeItem<>();

        TreeView<CustomItem> entryTree = new TreeView<>(root);
        entryTree.setShowRoot(false);
        entryTree.setStyle("-fx-base: #d0d0d0;-fx-focus-color: #d0d0d0;-fx-faint-focus-color: #d0d0d0;");

        vbox.getChildren().addAll(titleLbl, entryTree);

        handleStringLbl(retrieveEntry.getId(), root, "ID: ", false);

        if (retrieveEntry.getMetadata() != null) {
            customItemBuilder.newItem();
            Label metaLbl = new Label("Metadata: ");
            metaLbl.setWrapText(true);
            customItemBuilder.setLabel(metaLbl);
            TreeItem<CustomItem> metaItem = new TreeItem<>(customItemBuilder.getCustomItem());
            root.getChildren().add(metaItem);

            createMetadata(retrieveEntry.getMetadata(), metaItem);
        }

        if (retrieveEntry.getResults() != null) {
            customItemBuilder.newItem();
            Label resultsLbl = new Label("Results: ");
            resultsLbl.setWrapText(true);
            customItemBuilder.setLabel(resultsLbl);
            TreeItem<CustomItem> resultsItem = new TreeItem<>(customItemBuilder.getCustomItem());
            root.getChildren().add(resultsItem);

            for (HeadwordEntry result : retrieveEntry.getResults()) {
                createHeadwordEntry(result, resultsItem);
            }
        }

        handleStringLbl(retrieveEntry.getWord(), root, "Word: ", false);
        return vbox;
    }

    public void createMetadata(Metadata metadata, TreeItem<CustomItem> parent) {
        if (metadata.getOperation() != null) {
            handleStringLbl(metadata.getOperation(), parent, "Operation: ", false);
        }

        if (metadata.getProvider() != null) {
            handleStringLbl(metadata.getProvider(), parent, "Provider: ", false);
        }

        if (metadata.getSchema() != null) {
            handleStringLbl(metadata.getSchema(), parent, "Schema: ", false);
        }
    }

    public void createHeadwordEntry(HeadwordEntry headwordEntry, TreeItem<CustomItem> parent) {
        handleStringLbl(headwordEntry.getId(), parent, "ID: ", false);
        handleStringLbl(headwordEntry.getLanguage(), parent, "Language: ", false);

        if (headwordEntry.getLexicalEntries() != null) {
            customItemBuilder.newItem();
            Label lexLbl = new Label("Lexical Entries: ");
            lexLbl.setWrapText(true);
            customItemBuilder.setLabel(lexLbl);
            TreeItem<CustomItem> lexItem = new TreeItem<>(customItemBuilder.getCustomItem());
            parent.getChildren().add(lexItem);

            int count = 1;
            for (LexicalEntry lex : headwordEntry.getLexicalEntries()) {
                customItemBuilder.newItem();
                Label lexChildLbl = new Label(String.valueOf(count));
                lexChildLbl.setWrapText(true);
                customItemBuilder.setLabel(lexChildLbl);
                TreeItem<CustomItem> lexChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
                lexItem.getChildren().add(lexChildItem);

                createLexicalEntry(lex, lexChildItem);
                count += 1;
            }
        }

        if (headwordEntry.getPronunciations() != null) {
            handlePronunciations(headwordEntry.getPronunciations(), parent);
        }

        if (headwordEntry.getType() != null) {
            handleStringLbl(headwordEntry.getType(), parent, "Type: ", false);
        }

        handleStringLbl(headwordEntry.getWord(), parent, "Word: ", false);
    }

    public void createLexicalEntry(LexicalEntry lexicalEntry, TreeItem<CustomItem> parent) {
        if (lexicalEntry.getCompounds() != null) {
            handleRelatedEntries(lexicalEntry.getCompounds(), parent, "Compounds: ");
        }

        if (lexicalEntry.getDerivativeOf() != null) {
            handleRelatedEntries(lexicalEntry.getDerivativeOf(), parent, "Derivative Of: ");
        }

        if (lexicalEntry.getDerivatives() != null) {
            handleRelatedEntries(lexicalEntry.getDerivatives(), parent, "Derivatives: ");
        }

        if (lexicalEntry.getEntries() != null) {
            customItemBuilder.newItem();
            Label entrLbl = new Label("Entries: ");
            entrLbl.setWrapText(true);
            customItemBuilder.setLabel(entrLbl);
            TreeItem<CustomItem> entrItem = new TreeItem<>(customItemBuilder.getCustomItem());
            parent.getChildren().add(entrItem);

            int count = 1;
            for (Entry entry : lexicalEntry.getEntries()) {
                customItemBuilder.newItem();
                Label entrChildLbl = new Label(String.valueOf(count));
                entrChildLbl.setWrapText(true);
                customItemBuilder.setLabel(entrChildLbl);
                TreeItem<CustomItem> entrChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
                entrItem.getChildren().add(entrChildItem);

                createEntry(entry, entrChildItem);
                count += 1;
            }
        }

        if (lexicalEntry.getGrammaticalFeatures() != null) {
            handleGrammaticalFeatures(lexicalEntry.getGrammaticalFeatures(), parent);
        }

        handleStringLbl(lexicalEntry.getLanguage(), parent, "Language: ", false);
        handleLexicalCategory(lexicalEntry.getLexicalCategory(), parent);

        if (lexicalEntry.getNotes() != null) {
            handleNotes(lexicalEntry.getNotes(), parent);
        }

        if (lexicalEntry.getPhrasalVerbs() != null) {
            handleRelatedEntries(lexicalEntry.getPhrasalVerbs(), parent, "Phrasal Verbs: ");
        }

        if (lexicalEntry.getPhrases() != null) {
            handleRelatedEntries(lexicalEntry.getPhrases(), parent, "Phrases: ");
        }

        if (lexicalEntry.getPronunciations() != null) {
            handlePronunciations(lexicalEntry.getPronunciations(), parent);
        }

        if (lexicalEntry.getRoot() != null) {
            handleStringLbl(lexicalEntry.getRoot(), parent, "Root: ", false);
        }

        handleStringLbl(lexicalEntry.getText(), parent, "Text: ", false);

        if (lexicalEntry.getVariantForms() != null) {
            handleVariantForms(lexicalEntry.getVariantForms(), parent);
        }
    }

    public void createPronunciation(Pronunciation pronunciation, TreeItem<CustomItem> parent) {
        if (pronunciation.getAudioFile() != null) {
            customItemBuilder.newItem();
            Label audLbl = new Label("Audio: ");
            audLbl.setWrapText(true);
            customItemBuilder.setLabel(audLbl);

            Media proMedia = new Media(pronunciation.getAudioFile());
            MediaPlayer proPlayer = new MediaPlayer(proMedia);
            proPlayer.setOnEndOfMedia(() -> {
                proPlayer.stop();
            });

            Button proBtn = new Button("Play");
            proBtn.setOnAction((event) -> {
                proPlayer.play();
            });
            customItemBuilder.setBtn(proBtn);
            TreeItem<CustomItem> audItem = new TreeItem<>(customItemBuilder.getCustomItem());
            parent.getChildren().add(audItem);
        }

        if (pronunciation.getDialects() != null) {
            handleStrings(pronunciation.getDialects(), parent, "Dialects: ");
        }

        if (pronunciation.getPhoneticNotation() != null) {
            handleStringLbl(pronunciation.getPhoneticNotation(), parent, "Phonetic Notation: ", false);
        }

        if (pronunciation.getPhoneticSpelling() != null) {
            handleStringLbl(pronunciation.getPhoneticSpelling(), parent, "Phonetic Spelling: ", false);
        }

        if (pronunciation.getRegions() != null) {
            handleRegions(pronunciation.getRegions(), parent);
        }

        if (pronunciation.getRegisters() != null) {
            handleRegisters(pronunciation.getRegisters(), parent);
        }
    }

    public void createRelatedEntry(RelatedEntry relatedEntry, TreeItem<CustomItem> parent) {
        if (relatedEntry.getDomains() != null) {
            handleDomains(relatedEntry.getDomains(), parent);
        }

        handleStringLbl(relatedEntry.getId(), parent, "ID: ", false);

        if (relatedEntry.getLanguage() != null) {
            handleStringLbl(relatedEntry.getLanguage(), parent, "Language: ", false);
        }

        if (relatedEntry.getRegions() != null) {
            handleRegions(relatedEntry.getRegions(), parent);
        }

        if (relatedEntry.getRegisters() != null) {
            handleRegisters(relatedEntry.getRegisters(), parent);
        }

        handleStringLbl(relatedEntry.getText(), parent, "Text: ", false);

    }

    public void createEntry(Entry entry, TreeItem<CustomItem> parent) {
        if (entry.getCrossReferenceMarkers() != null) {
            handleStrings(entry.getCrossReferenceMarkers(), parent, "Cross Reference Markers: ");
        }

        if (entry.getCrossReferences() != null) {
            handleCrossReferences(entry.getCrossReferences(), parent);
        }

        if (entry.getEtymologies() != null) {
            handleStrings(entry.getEtymologies(), parent, "Etymologies: ");
        }

        if (entry.getGrammaticalFeatures() != null) {
            handleGrammaticalFeatures(entry.getGrammaticalFeatures(), parent);
        }

        if (entry.getHomographNumber() != null) {
            handleStringLbl(entry.getHomographNumber(), parent, "Homograph Number: ", false);
        }

        if (entry.getInflections() != null) {
            handleInflections(entry.getInflections(), parent);
        }

        if (entry.getNotes() != null) {
            handleNotes(entry.getNotes(), parent);
        }

        if (entry.getPronunciations() != null) {
            handlePronunciations(entry.getPronunciations(), parent);
        }

        if (entry.getSenses() != null) {
            handleSenses(entry.getSenses(), parent, "Senses: ");
        }

        if (entry.getVariantForms() != null) {
            handleVariantForms(entry.getVariantForms(), parent);
        }
    }

    public void createGrammaticalFeature(GrammaticalFeature grammaticalFeature, TreeItem<CustomItem> parent) {
        handleStringLbl(grammaticalFeature.getId(), parent, "ID: ", false);
        handleStringLbl(grammaticalFeature.getText(), parent, "Text: ", false);
        handleStringLbl(grammaticalFeature.getType(), parent, "Type: ", false);
    }

    public void createLexicalCategory(LexicalCategory lexicalCategory, TreeItem<CustomItem> parent) {
        handleStringLbl(lexicalCategory.getId(), parent, "ID: ", false);
        handleStringLbl(lexicalCategory.getText(), parent, "Text: ", false);
    }

    public void createCategorizedText(CategorizedText categorizedText, TreeItem<CustomItem> parent) {
        if (categorizedText.getId() != null) {
            handleStringLbl(categorizedText.getId(), parent, "ID: ", false);
        }

        handleStringLbl(categorizedText.getText(), parent, "Text: ", false);
        handleStringLbl(categorizedText.getType(), parent, "Type: ", false);
    }

    public void createVariantForm(VariantForm variantForm, TreeItem<CustomItem> parent) {
        if (variantForm.getDomains() != null) {
            handleDomains(variantForm.getDomains(), parent);
        }

        if (variantForm.getNotes() != null) {
            handleNotes(variantForm.getNotes(), parent);
        }

        if (variantForm.getPronunciations() != null) {
            handlePronunciations(variantForm.getPronunciations(), parent);
        }

        if (variantForm.getRegions() != null) {
            handleRegions(variantForm.getRegions(), parent);
        }

        if (variantForm.getRegisters() != null) {
            handleRegisters(variantForm.getRegisters(), parent);
        }

        handleStringLbl(variantForm.getText(), parent, "Text: ", false);
    }

    public void createString(String str, TreeItem<CustomItem> parent) {
        customItemBuilder.newItem();
        Label strLbl = new Label(str);
        strLbl.setWrapText(true);
        customItemBuilder.setLabel(strLbl);
        TreeItem<CustomItem> strItem = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(strItem);
    }

    public void createRegion(Region region, TreeItem<CustomItem> parent) {
        handleStringLbl(region.getId(), parent, "ID: ", false);
        handleStringLbl(region.getText(), parent, "Text: ", false);
    }

    public void createRegister(Register register, TreeItem<CustomItem> parent) {
        handleStringLbl(register.getId(), parent, "ID: ", false);
        handleStringLbl(register.getText(), parent, "Text: ", false);
    }

    public void createDomain(Domain domain, TreeItem<CustomItem> parent) {
        handleStringLbl(domain.getId(), parent, "ID: ", false);
        handleStringLbl(domain.getText(), parent, "Text: ", false);
    }

    public void createCrossReference(CrossReference crossReference, TreeItem<CustomItem> parent) {
        handleStringLbl(crossReference.getId(), parent, "ID: ", false);
        handleStringLbl(crossReference.getText(), parent, "Text: ", false);
        handleStringLbl(crossReference.getType(), parent, "Type: ", false);
    }

    public void createInflectedForm(InflectedForm inflectedForm, TreeItem<CustomItem> parent) {
        if (inflectedForm.getDomains() != null) {
            handleDomains(inflectedForm.getDomains(), parent);
        }

        if (inflectedForm.getGrammaticalFeatures() != null) {
            handleGrammaticalFeatures(inflectedForm.getGrammaticalFeatures(), parent);
        }

        handleStringLbl(inflectedForm.getInflectedForm(), parent, "Inflected Form: ", false);

        if (inflectedForm.getLexicalCategory() != null) {
            handleLexicalCategory(inflectedForm.getLexicalCategory(), parent);
        }

        if (inflectedForm.getPronunciations() != null) {
            handlePronunciations(inflectedForm.getPronunciations(), parent);
        }

        if (inflectedForm.getRegions() != null) {
            handleRegions(inflectedForm.getRegions(), parent);
        }

        if (inflectedForm.getRegisters() != null) {
            handleRegisters(inflectedForm.getRegisters(), parent);
        }
    }

    public void createSense(Sense sense, TreeItem<CustomItem> parent) {
        if (sense.getAntonyms() != null) {
            handleSynonymsAntonyms(sense.getAntonyms(), parent, "Antonyms: ");
        }

        if (sense.getConstructions() != null) {
            customItemBuilder.newItem();
            Label conLbl = new Label("Constructions: ");
            conLbl.setWrapText(true);
            customItemBuilder.setLabel(conLbl);
            TreeItem<CustomItem> conItem = new TreeItem<>(customItemBuilder.getCustomItem());
            parent.getChildren().add(conItem);

            int count = 1;
            for (InlineModel2 con : sense.getConstructions()) {
                customItemBuilder.newItem();
                Label conChildLbl = new Label(String.valueOf(count));
                conChildLbl.setWrapText(true);
                customItemBuilder.setLabel(conChildLbl);
                TreeItem<CustomItem> conChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
                conItem.getChildren().add(conChildItem);

                createConstruction(con, conChildItem);
                count += 1;
            }
        }

        if (sense.getCrossReferenceMarkers() != null) {
            handleStrings(sense.getCrossReferenceMarkers(), parent, "Cross Reference Markers: ");
        }

        if (sense.getCrossReferences() != null) {
            handleCrossReferences(sense.getCrossReferences(), parent);
        }

        if (sense.getDefinitions() != null) {
            handleStrings(sense.getDefinitions(), parent, "Definitions: ");
        }

        if (sense.getDomainClasses() != null) {
            customItemBuilder.newItem();
            Label domLbl = new Label("Domain Classes: ");
            domLbl.setWrapText(true);
            customItemBuilder.setLabel(domLbl);
            TreeItem<CustomItem> domItem = new TreeItem<>(customItemBuilder.getCustomItem());
            parent.getChildren().add(domItem);

            int count = 1;
            for (DomainClass dom : sense.getDomainClasses()) {
                customItemBuilder.newItem();
                Label domChildLbl = new Label(String.valueOf(count));
                domChildLbl.setWrapText(true);
                customItemBuilder.setLabel(domChildLbl);
                TreeItem<CustomItem> domChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
                domItem.getChildren().add(domChildItem);

                createDomainClass(dom, domChildItem);
                count += 1;
            }
        }

        if (sense.getDomains() != null) {
            handleDomains(sense.getDomains(), parent);
        }

        if (sense.getEtymologies() != null) {
            handleStrings(sense.getEtymologies(), parent, "Etymologies: ");
        }

        if (sense.getExamples() != null) {
            customItemBuilder.newItem();
            Label examLbl = new Label("Examples: ");
            examLbl.setWrapText(true);
            customItemBuilder.setLabel(examLbl);
            TreeItem<CustomItem> examItem = new TreeItem<>(customItemBuilder.getCustomItem());
            parent.getChildren().add(examItem);

            int count = 1;
            for (Example exam : sense.getExamples()) {
                customItemBuilder.newItem();
                Label examChildLbl = new Label(String.valueOf(count));
                examChildLbl.setWrapText(true);
                customItemBuilder.setLabel(examChildLbl);
                TreeItem<CustomItem> examChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
                examItem.getChildren().add(examChildItem);

                createExample(exam, examChildItem);
                count += 1;
            }
        }

        if (sense.getId() != null) {
            handleStringLbl(sense.getId(), parent, "ID: ", false);
        }

        if (sense.getInflections() != null) {
            handleInflections(sense.getInflections(), parent);
        }

        if (sense.getNotes() != null) {
            handleNotes(sense.getNotes(), parent);
        }

        if (sense.getPronunciations() != null) {
            handlePronunciations(sense.getPronunciations(), parent);
        }

        if (sense.getRegions() != null) {
            handleRegions(sense.getRegions(), parent);
        }

        if (sense.getRegisters() != null) {
            handleRegisters(sense.getRegisters(), parent);
        }

        if (sense.getSemanticClasses() != null) {
            customItemBuilder.newItem();
            Label semLbl = new Label("Semantic Classes: ");
            semLbl.setWrapText(true);
            customItemBuilder.setLabel(semLbl);
            TreeItem<CustomItem> semItem = new TreeItem<>(customItemBuilder.getCustomItem());
            parent.getChildren().add(semItem);

            int count = 1;
            for (SemanticClass sem : sense.getSemanticClasses()) {
                customItemBuilder.newItem();
                Label semChildLbl = new Label(String.valueOf(count));
                semChildLbl.setWrapText(true);
                customItemBuilder.setLabel(semChildLbl);
                TreeItem<CustomItem> semChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
                semItem.getChildren().add(semChildItem);

                createSemanticClass(sem, semChildItem);
                count += 1;
            }
        }

        if (sense.getShortDefinitions() != null) {
            handleStrings(sense.getShortDefinitions(), parent, "Short Definitions: ");
        }

        if (sense.getSubsenses() != null) {
            handleSenses(sense.getSubsenses(), parent, "Sub-senses: ");
        }

        if (sense.getSynonyms() != null) {
            handleSynonymsAntonyms(sense.getSynonyms(), parent, "Synonyms: ");
        }

        if (sense.getThesaurusLinks() != null) {
            customItemBuilder.newItem();
            Label theLbl = new Label("Thesaurus Links: ");
            theLbl.setWrapText(true);
            customItemBuilder.setLabel(theLbl);
            TreeItem<CustomItem> theItem = new TreeItem<>(customItemBuilder.getCustomItem());
            parent.getChildren().add(theItem);

            int count = 1;
            for (ThesaurusLink the : sense.getThesaurusLinks()) {
                customItemBuilder.newItem();
                Label theChildLbl = new Label(String.valueOf(count));
                theChildLbl.setWrapText(true);
                customItemBuilder.setLabel(theChildLbl);
                TreeItem<CustomItem> theChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
                theItem.getChildren().add(theChildItem);

                createThesaurusLink(the, theChildItem);
                count += 1;
            }
        }

        if (sense.getVariantForms() != null) {
            handleVariantForms(sense.getVariantForms(), parent);
        }
    }

    public void createSynonymAntonym(SynonymsAntonyms synonymAntonym, TreeItem<CustomItem> parent) {
        if (synonymAntonym.getDomains() != null) {
            handleDomains(synonymAntonym.getDomains(), parent);
        }

        if (synonymAntonym.getId() != null) {
            handleStringLbl(synonymAntonym.getId(), parent, "ID: ", false);
        }

        if (synonymAntonym.getLanguage() != null) {
            handleStringLbl(synonymAntonym.getLanguage(), parent, "Language: ", false);
        }

        if (synonymAntonym.getRegions() != null) {
            handleRegions(synonymAntonym.getRegions(), parent);
        }

        if (synonymAntonym.getRegisters() != null) {
            handleRegisters(synonymAntonym.getRegisters(), parent);
        }

        handleStringLbl(synonymAntonym.getText(), parent, "Text: ", true);
    }

    public void createConstruction(InlineModel2 construction, TreeItem<CustomItem> parent) {
        if (construction.getDomains() != null) {
            handleDomains(construction.getDomains(), parent);
        }

        if (construction.getExamples() != null) {
            customItemBuilder.newItem();
            Label examLbl = new Label("Examples: ");
            examLbl.setWrapText(true);
            customItemBuilder.setLabel(examLbl);
            TreeItem<CustomItem> examItem = new TreeItem<>(customItemBuilder.getCustomItem());
            parent.getChildren().add(examItem);

            int count = 1;
            for (ExampleText exam : construction.getExamples()) {
                customItemBuilder.newItem();
                Label examChildLbl = new Label(String.valueOf(count));
                examChildLbl.setWrapText(true);
                customItemBuilder.setLabel(examChildLbl);
                TreeItem<CustomItem> examChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
                examItem.getChildren().add(examChildItem);

                createExampleText(exam, examChildItem);
                count += 1;
            }
        }

        if (construction.getNotes() != null) {
            handleNotes(construction.getNotes(), parent);
        }

        if (construction.getRegions() != null) {
            handleRegions(construction.getRegions(), parent);
        }

        if (construction.getRegisters() != null) {
            handleRegisters(construction.getRegisters(), parent);
        }

        handleStringLbl(construction.getText(), parent, "Text: ", false);
    }

    public void createDomainClass(DomainClass domainClass, TreeItem<CustomItem> parent) {
        handleStringLbl(domainClass.getId(), parent, "ID: ", false);
        handleStringLbl(domainClass.getText(), parent, "Text: ", false);
    }

    public void createExample(Example example, TreeItem<CustomItem> parent) {
        if (example.getDefinitions() != null) {
            handleStrings(example.getDefinitions(), parent, "Definitions: ");
        }

        if (example.getDomains() != null) {
            handleDomains(example.getDomains(), parent);
        }

        if (example.getNotes() != null) {
            handleNotes(example.getNotes(), parent);
        }

        if (example.getRegions() != null) {
            handleRegions(example.getRegions(), parent);
        }

        if (example.getRegisters() != null) {
            handleRegisters(example.getRegisters(), parent);
        }

        if (example.getSenseIds() != null) {
            handleStrings(example.getSenseIds(), parent, "Sense IDs: ");
        }

        handleStringLbl(example.getText(), parent, "Text: ", false);
    }

    public void createSemanticClass(SemanticClass semanticClass, TreeItem<CustomItem> parent) {
        handleStringLbl(semanticClass.getId(), parent, "ID: ", false);
        handleStringLbl(semanticClass.getText(), parent, "Text: ", false);
    }

    public void createThesaurusLink(ThesaurusLink thesaurusLink, TreeItem<CustomItem> parent) {
        handleStringLbl(thesaurusLink.getEntryId(), parent, "Entry ID: ", false);
        handleStringLbl(thesaurusLink.getSenseId(), parent, "Sense ID: ", false);
    }

    public void createExampleText(ExampleText exampleText, TreeItem<CustomItem> parent) {
        handleStringLbl(exampleText.getText(), parent, "Text: ", false);
    }

    public void handlePronunciations(List<Pronunciation> pronunciations, TreeItem<CustomItem> parent) {
        customItemBuilder.newItem();
        Label proLbl = new Label("Pronunciations: ");
        proLbl.setWrapText(true);
        customItemBuilder.setLabel(proLbl);
        TreeItem<CustomItem> proItem = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(proItem);

        int count = 1;
        for (Pronunciation pro : pronunciations) {
            customItemBuilder.newItem();
            Label proChildLbl = new Label(String.valueOf(count));
            proChildLbl.setWrapText(true);
            customItemBuilder.setLabel(proChildLbl);
            TreeItem<CustomItem> proChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
            proItem.getChildren().add(proChildItem);

            createPronunciation(pro, proChildItem);
            count += 1;
        }
    }

    public void handleGrammaticalFeatures(List<GrammaticalFeature> grammaticalFeatures, TreeItem<CustomItem> parent) {
        customItemBuilder.newItem();
        Label gramLbl = new Label("Grammatical Features: ");
        gramLbl.setWrapText(true);
        customItemBuilder.setLabel(gramLbl);
        TreeItem<CustomItem> gramItem = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(gramItem);

        int count = 1;
        for (GrammaticalFeature gram :  grammaticalFeatures) {
            customItemBuilder.newItem();
            Label gramChildLbl = new Label(String.valueOf(count));
            gramChildLbl.setWrapText(true);
            customItemBuilder.setLabel(gramChildLbl);
            TreeItem<CustomItem> gramChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
            gramItem.getChildren().add(gramChildItem);

            createGrammaticalFeature(gram, gramChildItem);
            count += 1;
        }
    }

    public void handleRelatedEntries(List<RelatedEntry> relatedEntries, TreeItem<CustomItem> parent, String label) {
        customItemBuilder.newItem();
        Label relaLbl = new Label(label);
        relaLbl.setWrapText(true);
        customItemBuilder.setLabel(relaLbl);
        TreeItem<CustomItem> relaItem = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(relaItem);

        int count = 1;
        for (RelatedEntry entry : relatedEntries) {
            customItemBuilder.newItem();
            Label relaChildLbl = new Label(String.valueOf(count));
            relaChildLbl.setWrapText(true);
            customItemBuilder.setLabel(relaChildLbl);
            TreeItem<CustomItem> relaChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
            relaItem.getChildren().add(relaChildItem);

            createRelatedEntry(entry, relaChildItem);
            count += 1;
        }
    }

    public void handleNotes(List<CategorizedText> notes, TreeItem<CustomItem> parent) {
        customItemBuilder.newItem();
        Label notesLbl = new Label("Notes: ");
        notesLbl.setWrapText(true);
        customItemBuilder.setLabel(notesLbl);
        TreeItem<CustomItem> notesItem = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(notesItem);

        int count = 1;
        for (CategorizedText note : notes) {
            customItemBuilder.newItem();
            Label notesChildLbl = new Label(String.valueOf(count));
            notesChildLbl.setWrapText(true);
            customItemBuilder.setLabel(notesChildLbl);
            TreeItem<CustomItem> notesChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
            notesItem.getChildren().add(notesChildItem);

            createCategorizedText(note, notesChildItem);
            count += 1;
        }
    }

    public void handleVariantForms(List<VariantForm> variantForms, TreeItem<CustomItem> parent) {
        customItemBuilder.newItem();
        Label varLbl = new Label("Variant Forms: ");
        varLbl.setWrapText(true);
        customItemBuilder.setLabel(varLbl);
        TreeItem<CustomItem> varItem = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(varItem);

        int count = 1;
        for (VariantForm var : variantForms) {
            customItemBuilder.newItem();
            Label varChildLbl = new Label(String.valueOf(count));
            varChildLbl.setWrapText(true);
            customItemBuilder.setLabel(varChildLbl);
            TreeItem<CustomItem> varChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
            varItem.getChildren().add(varChildItem);

            createVariantForm(var, varChildItem);
            count += 1;
        }
    }

    public void handleStrings(List<String> strings, TreeItem<CustomItem> parent, String label) {
        customItemBuilder.newItem();
        Label strLbl = new Label(label);
        strLbl.setWrapText(true);
        customItemBuilder.setLabel(strLbl);
        TreeItem<CustomItem> strItem = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(strItem);

        int count = 1;
        for (String str : strings) {
            customItemBuilder.newItem();
            Label strChildLbl = new Label(String.valueOf(count));
            strChildLbl.setWrapText(true);
            customItemBuilder.setLabel(strChildLbl);
            TreeItem<CustomItem> strChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
            strItem.getChildren().add(strChildItem);

            createString(str, strChildItem);
            count += 1;
        }
    }

    public void handleRegions(List<Region> regions, TreeItem<CustomItem> parent) {
        customItemBuilder.newItem();
        Label regLbl = new Label("Regions: ");
        regLbl.setWrapText(true);
        customItemBuilder.setLabel(regLbl);
        TreeItem<CustomItem> regItem = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(regItem);

        int count = 1;
        for (Region reg : regions) {
            customItemBuilder.newItem();
            Label regChildLbl = new Label(String.valueOf(count));
            regChildLbl.setWrapText(true);
            customItemBuilder.setLabel(regChildLbl);
            TreeItem<CustomItem> regChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
            regItem.getChildren().add(regChildItem);

            createRegion(reg, regChildItem);
            count += 1;
        }
    }

    public void handleRegisters(List<Register> registers, TreeItem<CustomItem> parent) {
        customItemBuilder.newItem();
        Label regLbl = new Label("Registers: ");
        regLbl.setWrapText(true);
        customItemBuilder.setLabel(regLbl);
        TreeItem<CustomItem> regItem = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(regItem);

        int count = 1;
        for (Register reg : registers) {
            customItemBuilder.newItem();
            Label regChildLbl = new Label(String.valueOf(count));
            regChildLbl.setWrapText(true);
            customItemBuilder.setLabel(regChildLbl);
            TreeItem<CustomItem> regChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
            regItem.getChildren().add(regChildItem);

            createRegister(reg, regChildItem);
            count += 1;
        }
    }

    public void handleDomains(List<Domain> domains, TreeItem<CustomItem> parent) {
        customItemBuilder.newItem();
        Label domLbl = new Label("Domains: ");
        domLbl.setWrapText(true);
        customItemBuilder.setLabel(domLbl);
        TreeItem<CustomItem> domItem = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(domItem);

        int count = 1;
        for (Domain dom : domains) {
            customItemBuilder.newItem();
            Label domChildLbl = new Label(String.valueOf(count));
            domChildLbl.setWrapText(true);
            customItemBuilder.setLabel(domChildLbl);
            TreeItem<CustomItem> domChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
            domItem.getChildren().add(domChildItem);

            createDomain(dom, domChildItem);
            count += 1;
        }
    }

    public void handleCrossReferences(List<CrossReference> crossReferences, TreeItem<CustomItem> parent) {
        customItemBuilder.newItem();
        Label crossLbl = new Label("Cross Reference: ");
        crossLbl.setWrapText(true);
        customItemBuilder.setLabel(crossLbl);
        TreeItem<CustomItem> crossItem = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(crossItem);

        int count = 1;
        for (CrossReference cross : crossReferences) {
            customItemBuilder.newItem();
            Label crossChildLabel = new Label(String.valueOf(count));
            crossChildLabel.setWrapText(true);
            customItemBuilder.setLabel(crossChildLabel);
            TreeItem<CustomItem> crossChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
            crossItem.getChildren().add(crossChildItem);

            createCrossReference(cross, crossChildItem);
            count += 1;
        }
    }

    public void handleInflections(List<InflectedForm> inflectedForms, TreeItem<CustomItem> parent) {
        customItemBuilder.newItem();
        Label infLbl = new Label("Inflections: ");
        infLbl.setWrapText(true);
        customItemBuilder.setLabel(infLbl);
        TreeItem<CustomItem> infItem = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(infItem);

        int count = 1;
        for (InflectedForm inf : inflectedForms) {
            customItemBuilder.newItem();
            Label infChildLbl = new Label(String.valueOf(count));
            infChildLbl.setWrapText(true);
            customItemBuilder.setLabel(infChildLbl);
            TreeItem<CustomItem> infChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
            infItem.getChildren().add(infChildItem);

            createInflectedForm(inf, infChildItem);
            count += 1;
        }
    }

    public void handleSenses(List<Sense> senses, TreeItem<CustomItem> parent, String label) {
        customItemBuilder.newItem();
        Label senLbl = new Label(label);
        senLbl.setWrapText(true);
        customItemBuilder.setLabel(senLbl);
        TreeItem<CustomItem> senItem = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(senItem);

        int count = 1;
        for (Sense sen : senses) {
            customItemBuilder.newItem();
            Label senChildLbl = new Label(String.valueOf(count));
            senChildLbl.setWrapText(true);
            customItemBuilder.setLabel(senChildLbl);
            TreeItem<CustomItem> senChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
            senItem.getChildren().add(senChildItem);

            createSense(sen, senChildItem);
            count += 1;
        }
    }

    public void handleStringLbl(String string, TreeItem<CustomItem> parent, String label, boolean synAnt) {
        if (synAnt) {
            synAntHboxes.add(parent.getValue());
            synAntTexts.add(string);
        }

        customItemBuilder.newItem();
        Label strLbl = new Label(label + string);
        strLbl.setWrapText(true);
        customItemBuilder.setLabel(strLbl);
        TreeItem<CustomItem> strTree = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(strTree);
    }

    public void handleLexicalCategory(LexicalCategory lexicalCategory, TreeItem<CustomItem> parent) {
        customItemBuilder.newItem();
        Label lexiCateLbl = new Label("Lexical Category: ");
        lexiCateLbl.setWrapText(true);
        customItemBuilder.setLabel(lexiCateLbl);
        TreeItem<CustomItem> lexiCateItem = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(lexiCateItem);

        createLexicalCategory(lexicalCategory, lexiCateItem);
    }

    public void handleSynonymsAntonyms(List<SynonymsAntonyms> synonymsAntonyms, TreeItem<CustomItem> parent, String label) {
        customItemBuilder.newItem();
        Label synAntLbl = new Label(label);
        synAntLbl.setWrapText(true);
        customItemBuilder.setLabel(synAntLbl);
        TreeItem<CustomItem> synAntItem = new TreeItem<>(customItemBuilder.getCustomItem());
        parent.getChildren().add(synAntItem);

        int count = 1;
        for (SynonymsAntonyms synAnt : synonymsAntonyms) {
            customItemBuilder.newItem();
            Label synAntChildLbl = new Label(String.valueOf(count));
            synAntChildLbl.setWrapText(true);
            customItemBuilder.setLabel(synAntChildLbl);
            TreeItem<CustomItem> synAntChildItem = new TreeItem<>(customItemBuilder.getCustomItem());
            synAntItem.getChildren().add(synAntChildItem);

            createSynonymAntonym(synAnt, synAntChildItem);
            count += 1;
        }
    }

    public List<HBox> getSynAntHboxes() {
        return synAntHboxes;
    }

    public String getSynAntText(int ind) {
        return synAntTexts.get(ind);
    }
}
