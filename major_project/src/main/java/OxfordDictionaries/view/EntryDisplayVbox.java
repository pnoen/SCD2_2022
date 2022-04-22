package OxfordDictionaries.view;

import OxfordDictionaries.model.request.responseClasses.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private List<VBox> synAntVboxes;

    public EntryDisplayVbox() {
        this.padding = new Insets(0, 0, 0, 20);
        this.listPadding = new Insets(0, 0, 10, 15);
        this.synAntTexts = new ArrayList<>();
        this.synAntVboxes = new ArrayList<>();
    }

    public VBox create(RetrieveEntry retrieveEntry) {
        synAntTexts.clear();
        synAntVboxes.clear();

        this.vbox = new VBox(5);

        Label titleLbl = new Label(retrieveEntry.getWord());
        titleLbl.setWrapText(true);
        titleLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        vbox.getChildren().add(titleLbl);

        handleStringLbl(retrieveEntry.getId(), vbox, "ID: ");

        if (retrieveEntry.getMetadata() != null) {
            Label metaLbl = new Label("Metadata: ");
            metaLbl.setWrapText(true);
            VBox metaVbox = createMetadata(retrieveEntry.getMetadata());
            vbox.getChildren().addAll(metaLbl, metaVbox);
        }

        if (retrieveEntry.getResults() != null) {
            Label resultsLbl = new Label("Results: ");
            resultsLbl.setWrapText(true);
            vbox.getChildren().add(resultsLbl);

            for (HeadwordEntry result : retrieveEntry.getResults()) {
                VBox resultVbox = createHeadwordEntry(result);
                vbox.getChildren().add(resultVbox);
            }
        }

        handleStringLbl(retrieveEntry.getWord(), vbox, "Word: ");
        return vbox;
    }

    public VBox createMetadata(Metadata metadata) {
        VBox vbox = new VBox();
        vbox.setPadding(padding);

        if (metadata.getOperation() != null) {
            handleStringLbl(metadata.getOperation(), vbox, "Operation: ");
        }

        if (metadata.getProvider() != null) {
            handleStringLbl(metadata.getProvider(), vbox, "Provider: ");
        }

        if (metadata.getSchema() != null) {
            handleStringLbl(metadata.getSchema(), vbox, "Schema: ");
        }

        return vbox;
    }

    public VBox createHeadwordEntry(HeadwordEntry headwordEntry) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        handleStringLbl(headwordEntry.getId(), vbox, "ID: ");
        handleStringLbl(headwordEntry.getLanguage(), vbox, "Language: ");

        if (headwordEntry.getLexicalEntries() != null) {
            Label lexLbl = new Label("Lexical Entries: ");
            lexLbl.setWrapText(true);
            vbox.getChildren().add(lexLbl);

            for (LexicalEntry lex : headwordEntry.getLexicalEntries()) {
                VBox lexVbox = createLexicalEntry(lex);
                vbox.getChildren().add(lexVbox);
            }
        }

        if (headwordEntry.getPronunciations() != null) {
            handlePronunciations(headwordEntry.getPronunciations(), vbox);
        }

        if (headwordEntry.getType() != null) {
            handleStringLbl(headwordEntry.getType(), vbox, "Type: ");
        }

        handleStringLbl(headwordEntry.getWord(), vbox, "Word: ");
        return vbox;
    }

    public VBox createLexicalEntry(LexicalEntry lexicalEntry) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        if (lexicalEntry.getCompounds() != null) {
            handleRelatedEntries(lexicalEntry.getCompounds(), vbox, "Compounds: ");
        }

        if (lexicalEntry.getDerivativeOf() != null) {
            handleRelatedEntries(lexicalEntry.getDerivativeOf(), vbox, "Derivative Of: ");
        }

        if (lexicalEntry.getDerivatives() != null) {
            handleRelatedEntries(lexicalEntry.getDerivatives(), vbox, "Derivatives: ");
        }

        if (lexicalEntry.getEntries() != null) {
            Label entrLbl = new Label("Entries: ");
            entrLbl.setWrapText(true);
            vbox.getChildren().add(entrLbl);

            for (Entry entry : lexicalEntry.getEntries()) {
                VBox entryVbox = createEntry(entry);
                vbox.getChildren().add(entryVbox);
            }
        }

        if (lexicalEntry.getGrammaticalFeatures() != null) {
            handleGrammaticalFeatures(lexicalEntry.getGrammaticalFeatures(), vbox);
        }

        handleStringLbl(lexicalEntry.getLanguage(), vbox, "Language: ");
        handleLexicalCategory(lexicalEntry.getLexicalCategory(), vbox);

        if (lexicalEntry.getNotes() != null) {
            handleNotes(lexicalEntry.getNotes(), vbox);
        }

        if (lexicalEntry.getPhrasalVerbs() != null) {
            handleRelatedEntries(lexicalEntry.getPhrasalVerbs(), vbox, "Phrasal Verbs: ");
        }

        if (lexicalEntry.getPhrases() != null) {
            handleRelatedEntries(lexicalEntry.getPhrases(), vbox, "Phrases: ");
        }

        if (lexicalEntry.getPronunciations() != null) {
            handlePronunciations(lexicalEntry.getPronunciations(), vbox);
        }

        if (lexicalEntry.getRoot() != null) {
            handleStringLbl(lexicalEntry.getRoot(), vbox, "Root: ");
        }

        handleStringLbl(lexicalEntry.getText(), vbox, "Text: ");

        if (lexicalEntry.getVariantForms() != null) {
            handleVariantForms(lexicalEntry.getVariantForms(), vbox);
        }

        return vbox;
    }

    public VBox createPronunciation(Pronunciation pronunciation) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        if (pronunciation.getAudioFile() != null) {
            Label audLbl = new Label("Audio: ");
            audLbl.setWrapText(true);

            Media proMedia = new Media(pronunciation.getAudioFile());
            MediaPlayer proPlayer = new MediaPlayer(proMedia);
            proPlayer.setOnEndOfMedia(() -> {
                proPlayer.stop();
            });

            Button proBtn = new Button("Play");
            proBtn.setOnAction((event) -> {
                proPlayer.play();
            });

            HBox proHbox = new HBox(audLbl, proBtn);
            vbox.getChildren().addAll(proHbox);
        }

        if (pronunciation.getDialects() != null) {
            handleStrings(pronunciation.getDialects(), vbox, "Dialects: ");
        }

        if (pronunciation.getPhoneticNotation() != null) {
            handleStringLbl(pronunciation.getPhoneticNotation(), vbox, "Phonetic Notation: ");
        }

        if (pronunciation.getPhoneticSpelling() != null) {
            handleStringLbl(pronunciation.getPhoneticSpelling(), vbox, "Phonetic Spelling: ");
        }

        if (pronunciation.getRegions() != null) {
            handleRegions(pronunciation.getRegions(), vbox);
        }

        if (pronunciation.getRegisters() != null) {
            handleRegisters(pronunciation.getRegisters(), vbox);
        }

        return vbox;
    }

    public VBox createRelatedEntry(RelatedEntry relatedEntry) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        if (relatedEntry.getDomains() != null) {
            handleDomains(relatedEntry.getDomains(), vbox);
        }

        handleStringLbl(relatedEntry.getId(), vbox, "ID: ");

        if (relatedEntry.getLanguage() != null) {
            handleStringLbl(relatedEntry.getLanguage(), vbox, "Language: ");
        }

        if (relatedEntry.getRegions() != null) {
            handleRegions(relatedEntry.getRegions(), vbox);
        }

        if (relatedEntry.getRegisters() != null) {
            handleRegisters(relatedEntry.getRegisters(), vbox);
        }

        handleStringLbl(relatedEntry.getText(), vbox, "Text: ");

        return vbox;
    }

    public VBox createEntry(Entry entry) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        if (entry.getCrossReferenceMarkers() != null) {
            handleStrings(entry.getCrossReferenceMarkers(), vbox, "Cross Reference Markers: ");
        }

        if (entry.getCrossReferences() != null) {
            handleCrossReferences(entry.getCrossReferences(), vbox);
        }

        if (entry.getEtymologies() != null) {
            handleStrings(entry.getEtymologies(), vbox, "Etymologies: ");
        }

        if (entry.getGrammaticalFeatures() != null) {
            handleGrammaticalFeatures(entry.getGrammaticalFeatures(), vbox);
        }

        if (entry.getHomographNumber() != null) {
            handleStringLbl(entry.getHomographNumber(), vbox, "Homograph Number: ");
        }

        if (entry.getInflections() != null) {
            handleInflections(entry.getInflections(), vbox);
        }

        if (entry.getNotes() != null) {
            handleNotes(entry.getNotes(), vbox);
        }

        if (entry.getPronunciations() != null) {
            handlePronunciations(entry.getPronunciations(), vbox);
        }

        if (entry.getSenses() != null) {
            handleSenses(entry.getSenses(), vbox, "Senses: ");
        }

        if (entry.getVariantForms() != null) {
            handleVariantForms(entry.getVariantForms(), vbox);
        }

        return vbox;
    }

    public VBox createGrammaticalFeature(GrammaticalFeature grammaticalFeature) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        handleStringLbl(grammaticalFeature.getId(), vbox, "ID: ");
        handleStringLbl(grammaticalFeature.getText(), vbox, "Text: ");
        handleStringLbl(grammaticalFeature.getType(), vbox, "Type: ");
        return vbox;
    }

    public VBox createLexicalCategory(LexicalCategory lexicalCategory) {
        VBox vbox = new VBox();
        vbox.setPadding(padding);

        handleStringLbl(lexicalCategory.getId(), vbox, "ID: ");
        handleStringLbl(lexicalCategory.getText(), vbox, "Text: ");

        return vbox;
    }

    public VBox createCategorizedText(CategorizedText categorizedText) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        if (categorizedText.getId() != null) {
            handleStringLbl(categorizedText.getId(), vbox, "ID: ");
        }

        handleStringLbl(categorizedText.getText(), vbox, "Text: ");
        handleStringLbl(categorizedText.getType(), vbox, "Type: ");
        return vbox;
    }

    public VBox createVariantForm(VariantForm variantForm) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        if (variantForm.getDomains() != null) {
            handleDomains(variantForm.getDomains(), vbox);
        }

        if (variantForm.getNotes() != null) {
            handleNotes(variantForm.getNotes(), vbox);
        }

        if (variantForm.getPronunciations() != null) {
            handlePronunciations(variantForm.getPronunciations(), vbox);
        }

        if (variantForm.getRegions() != null) {
            handleRegions(variantForm.getRegions(), vbox);
        }

        if (variantForm.getRegisters() != null) {
            handleRegisters(variantForm.getRegisters(), vbox);
        }

        handleStringLbl(variantForm.getText(), vbox, "Text: ");

        return vbox;
    }

    public VBox createString(String str) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        Label strLbl = new Label(str);
        strLbl.setWrapText(true);

        vbox.getChildren().add(strLbl);
        return vbox;
    }

    public VBox createRegion(Region region) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        handleStringLbl(region.getId(), vbox, "ID: ");
        handleStringLbl(region.getText(), vbox, "Text: ");
        return vbox;
    }

    public VBox createRegister(Register register) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        handleStringLbl(register.getId(), vbox, "ID: ");
        handleStringLbl(register.getText(), vbox, "Text: ");
        return vbox;
    }

    public VBox createDomain(Domain domain) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        handleStringLbl(domain.getId(), vbox, "ID: ");
        handleStringLbl(domain.getText(), vbox, "Text: ");
        return vbox;
    }

    public VBox createCrossReference(CrossReference crossReference) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        handleStringLbl(crossReference.getId(), vbox, "ID: ");
        handleStringLbl(crossReference.getText(), vbox, "Text: ");
        handleStringLbl(crossReference.getType(), vbox, "Type: ");
        return vbox;
    }

    public VBox createInflectedForm(InflectedForm inflectedForm) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        if (inflectedForm.getDomains() != null) {
            handleDomains(inflectedForm.getDomains(), vbox);
        }

        if (inflectedForm.getGrammaticalFeatures() != null) {
            handleGrammaticalFeatures(inflectedForm.getGrammaticalFeatures(), vbox);
        }

        handleStringLbl(inflectedForm.getInflectedForm(), vbox, "Inflected Form: ");

        if (inflectedForm.getLexicalCategory() != null) {
            handleLexicalCategory(inflectedForm.getLexicalCategory(), vbox);
        }

        if (inflectedForm.getPronunciations() != null) {
            handlePronunciations(inflectedForm.getPronunciations(), vbox);
        }

        if (inflectedForm.getRegions() != null) {
            handleRegions(inflectedForm.getRegions(), vbox);
        }

        if (inflectedForm.getRegisters() != null) {
            handleRegisters(inflectedForm.getRegisters(), vbox);
        }

        return vbox;
    }

    public VBox createSense(Sense sense) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        if (sense.getAntonyms() != null) {
            handleSynonymsAntonyms(sense.getAntonyms(), vbox, "Antonyms: ");
        }

        if (sense.getConstructions() != null) {
            Label conLbl = new Label("Constructions: ");
            conLbl.setWrapText(true);
            vbox.getChildren().add(conLbl);

            for (Inline_model_2 con : sense.getConstructions()) {
                VBox conVbox = createConstruction(con);
                vbox.getChildren().add(conVbox);
            }
        }

        if (sense.getCrossReferenceMarkers() != null) {
            handleStrings(sense.getCrossReferenceMarkers(), vbox, "Cross Reference Markers: ");
        }

        if (sense.getCrossReferences() != null) {
            handleCrossReferences(sense.getCrossReferences(), vbox);
        }

        if (sense.getDefinitions() != null) {
            handleStrings(sense.getDefinitions(), vbox, "Definitions: ");
        }

        if (sense.getDomainClasses() != null) {
            Label domLbl = new Label("Domain Classes: ");
            domLbl.setWrapText(true);
            vbox.getChildren().add(domLbl);

            for (DomainClass dom : sense.getDomainClasses()) {
                VBox domVbox = createDomainClass(dom);
                vbox.getChildren().add(domVbox);
            }
        }

        if (sense.getDomains() != null) {
            handleDomains(sense.getDomains(), vbox);
        }

        if (sense.getEtymologies() != null) {
            handleStrings(sense.getEtymologies(), vbox, "Etymologies: ");
        }

        if (sense.getExamples() != null) {
            Label examLbl = new Label("Examples: ");
            examLbl.setWrapText(true);
            vbox.getChildren().add(examLbl);

            for (Example exam : sense.getExamples()) {
                VBox examVbox = createExample(exam);
                vbox.getChildren().add(examVbox);
            }
        }

        if (sense.getId() != null) {
            handleStringLbl(sense.getId(), vbox, "ID: ");
        }

        if (sense.getInflections() != null) {
            handleInflections(sense.getInflections(), vbox);
        }

        if (sense.getNotes() != null) {
            handleNotes(sense.getNotes(), vbox);
        }

        if (sense.getPronunciations() != null) {
            handlePronunciations(sense.getPronunciations(), vbox);
        }

        if (sense.getRegions() != null) {
            handleRegions(sense.getRegions(), vbox);
        }

        if (sense.getRegisters() != null) {
            handleRegisters(sense.getRegisters(), vbox);
        }

        if (sense.getSemanticClasses() != null) {
            Label semLbl = new Label("Semantic Classes: ");
            semLbl.setWrapText(true);
            vbox.getChildren().add(semLbl);

            for (SemanticClass sem : sense.getSemanticClasses()) {
                VBox semVbox = createSemanticClass(sem);
                vbox.getChildren().add(semVbox);
            }
        }

        if (sense.getShortDefinitions() != null) {
            handleStrings(sense.getShortDefinitions(), vbox, "Short Definitions: ");
        }

        if (sense.getSubsenses() != null) {
            handleSenses(sense.getSubsenses(), vbox, "Sub-senses: ");
        }

        if (sense.getSynonyms() != null) {
            handleSynonymsAntonyms(sense.getSynonyms(), vbox, "Synonyms: ");
        }

        if (sense.getThesaurusLinks() != null) {
            Label theLbl = new Label("Thesaurus Links: ");
            theLbl.setWrapText(true);
            vbox.getChildren().add(theLbl);

            for (ThesaurusLink the : sense.getThesaurusLinks()) {
                VBox theVbox = createThesaurusLink(the);
                vbox.getChildren().add(theVbox);
            }
        }

        if (sense.getVariantForms() != null) {
            handleVariantForms(sense.getVariantForms(), vbox);
        }

        return vbox;
    }

    public VBox createSynonymAntonym(SynonymsAntonyms synonymAntonym) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        if (synonymAntonym.getDomains() != null) {
            handleDomains(synonymAntonym.getDomains(), vbox);
        }

        if (synonymAntonym.getId() != null) {
            handleStringLbl(synonymAntonym.getId(), vbox, "ID: ");
        }

        if (synonymAntonym.getLanguage() != null) {
            handleStringLbl(synonymAntonym.getLanguage(), vbox, "Language: ");
        }

        if (synonymAntonym.getRegions() != null) {
            handleRegions(synonymAntonym.getRegions(), vbox);
        }

        if (synonymAntonym.getRegisters() != null) {
            handleRegisters(synonymAntonym.getRegisters(), vbox);
        }

        handleStringLbl(synonymAntonym.getText(), vbox, "Text: ");
        return vbox;
    }

    public VBox createConstruction(Inline_model_2 construction) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        if (construction.getDomains() != null) {
            handleDomains(construction.getDomains(), vbox);
        }

        if (construction.getExamples() != null) {
//            handleStrings(construction.getExamples(), vbox, "Examples: ");
            Label examLbl = new Label("Examples: ");
            examLbl.setWrapText(true);
            vbox.getChildren().add(examLbl);

            for (ExampleText exam : construction.getExamples()) {
                VBox examVbox = createExampleText(exam);
                vbox.getChildren().add(examVbox);
            }
        }

        if (construction.getNotes() != null) {
            handleNotes(construction.getNotes(), vbox);
        }

        if (construction.getRegions() != null) {
            handleRegions(construction.getRegions(), vbox);
        }

        if (construction.getRegisters() != null) {
            handleRegisters(construction.getRegisters(), vbox);
        }

        handleStringLbl(construction.getText(), vbox, "Text: ");
        return vbox;
    }

    public VBox createDomainClass(DomainClass domainClass) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        handleStringLbl(domainClass.getId(), vbox, "ID: ");
        handleStringLbl(domainClass.getText(), vbox, "Text: ");
        return vbox;
    }

    public VBox createExample(Example example) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        if (example.getDefinitions() != null) {
            handleStrings(example.getDefinitions(), vbox, "Definitions: ");
        }

        if (example.getDomains() != null) {
            handleDomains(example.getDomains(), vbox);
        }

        if (example.getNotes() != null) {
            handleNotes(example.getNotes(), vbox);
        }

        if (example.getRegions() != null) {
            handleRegions(example.getRegions(), vbox);
        }

        if (example.getRegisters() != null) {
            handleRegisters(example.getRegisters(), vbox);
        }

        if (example.getSenseIds() != null) {
            handleStrings(example.getSenseIds(), vbox, "Sense IDs: ");
        }

        handleStringLbl(example.getText(), vbox, "Text: ");
        return vbox;
    }

    public VBox createSemanticClass(SemanticClass semanticClass) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        handleStringLbl(semanticClass.getId(), vbox, "ID: ");
        handleStringLbl(semanticClass.getText(), vbox, "Text: ");
        return vbox;
    }

    public VBox createThesaurusLink(ThesaurusLink thesaurusLink) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        handleStringLbl(thesaurusLink.getEntry_id(), vbox, "Entry ID: ");
        handleStringLbl(thesaurusLink.getSense_id(), vbox, "Sense ID: ");
        return vbox;
    }

    public VBox createExampleText(ExampleText exampleText) {
        VBox vbox = new VBox();
        vbox.setPadding(listPadding);

        handleStringLbl(exampleText.getText(), vbox, "Text: ");
        return vbox;
    }

    public void handlePronunciations(List<Pronunciation> pronunciations, VBox vbox) {
        Label proLbl = new Label("Pronunciations: ");
        proLbl.setWrapText(true);
        vbox.getChildren().add(proLbl);

        for (Pronunciation pro : pronunciations) {
            VBox proVbox = createPronunciation(pro);
            vbox.getChildren().add(proVbox);
        }
    }

    public void handleGrammaticalFeatures(List<GrammaticalFeature> grammaticalFeatures, VBox vbox) {
        Label gramLbl = new Label("Grammatical Features: ");
        gramLbl.setWrapText(true);
        vbox.getChildren().add(gramLbl);

        for (GrammaticalFeature gram :  grammaticalFeatures) {
            VBox gramVbox = createGrammaticalFeature(gram);
            vbox.getChildren().add(gramVbox);
        }
    }

    public void handleRelatedEntries(List<RelatedEntry> relatedEntries, VBox vbox, String label) {
        Label relaLbl = new Label(label);
        relaLbl.setWrapText(true);
        vbox.getChildren().add(relaLbl);

        for (RelatedEntry entry : relatedEntries) {
            VBox entryVbox = createRelatedEntry(entry);
            vbox.getChildren().add(entryVbox);
        }
    }

    public void handleNotes(List<CategorizedText> notes, VBox vbox) {
        Label notesLbl = new Label("Notes: ");
        notesLbl.setWrapText(true);
        vbox.getChildren().add(notesLbl);

        for (CategorizedText note : notes) {
            VBox noteVbox = createCategorizedText(note);
            vbox.getChildren().add(noteVbox);
        }
    }

    public void handleVariantForms(List<VariantForm> variantForms, VBox vbox) {
        Label varLbl = new Label("Variant Forms: ");
        varLbl.setWrapText(true);
        vbox.getChildren().add(varLbl);

        for (VariantForm var : variantForms) {
            VBox varVbox = createVariantForm(var);
            vbox.getChildren().add(varVbox);
        }
    }

    public void handleStrings(List<String> strings, VBox vbox, String label) {
        Label strLbl = new Label(label);
        strLbl.setWrapText(true);
        vbox.getChildren().add(strLbl);

        for (String str : strings) {
            VBox strVbox = createString(str);
            vbox.getChildren().add(strVbox);
        }
    }

    public void handleRegions(List<Region> regions, VBox vbox) {
        Label regLbl = new Label("Regions: ");
        regLbl.setWrapText(true);
        vbox.getChildren().add(regLbl);

        for (Region reg : regions) {
            VBox regVbox = createRegion(reg);
            vbox.getChildren().add(regVbox);
        }
    }

    public void handleRegisters(List<Register> registers, VBox vbox) {
        Label regLbl = new Label("Registers: ");
        regLbl.setWrapText(true);
        vbox.getChildren().add(regLbl);

        for (Register reg : registers) {
            VBox regVbox = createRegister(reg);
            vbox.getChildren().add(regVbox);
        }
    }

    public void handleDomains(List<Domain> domains, VBox vbox) {
        Label domLbl = new Label("Domains: ");
        domLbl.setWrapText(true);
        vbox.getChildren().add(domLbl);

        for (Domain dom : domains) {
            VBox domVbox = createDomain(dom);
            vbox.getChildren().add(domVbox);
        }
    }

    public void handleCrossReferences(List<CrossReference> crossReferences, VBox vbox) {
        Label crossLbl = new Label("Cross Reference: ");
        crossLbl.setWrapText(true);
        vbox.getChildren().add(crossLbl);

        for (CrossReference cross : crossReferences) {
            VBox crossVbox = createCrossReference(cross);
            vbox.getChildren().add(crossVbox);
        }
    }

    public void handleInflections(List<InflectedForm> inflectedForms, VBox vbox) {
        Label infLbl = new Label("Inflections: ");
        infLbl.setWrapText(true);
        vbox.getChildren().add(infLbl);

        for (InflectedForm inf : inflectedForms) {
            VBox infVbox = createInflectedForm(inf);
            vbox.getChildren().add(infVbox);
        }
    }

    public void handleSenses(List<Sense> senses, VBox vbox, String label) {
        Label senLbl = new Label(label);
        senLbl.setWrapText(true);
        vbox.getChildren().add(senLbl);

        for (Sense sen : senses) {
            VBox senVbox = createSense(sen);
            vbox.getChildren().add(senVbox);
        }
    }

    public void handleStringLbl(String string, VBox vbox, String label) {
        Label strLbl = new Label(label + string);
        strLbl.setWrapText(true);
        vbox.getChildren().add(strLbl);
    }

    public void handleLexicalCategory(LexicalCategory lexicalCategory, VBox vbox) {
        Label lexiCateLbl = new Label("Lexical Category");
        lexiCateLbl.setWrapText(true);
        VBox lexiCateVbox = createLexicalCategory(lexicalCategory);
        vbox.getChildren().addAll(lexiCateLbl, lexiCateVbox);
    }

    public void handleSynonymsAntonyms(List<SynonymsAntonyms> synonymsAntonyms, VBox vbox, String label) {
        Label synAntLbl = new Label(label);
        synAntLbl.setWrapText(true);
        vbox.getChildren().add(synAntLbl);

        for (SynonymsAntonyms synAnt : synonymsAntonyms) {
            VBox synAntVBox = createSynonymAntonym(synAnt);
            vbox.getChildren().add(synAntVBox);

            synAntVboxes.add(synAntVBox);
            synAntTexts.add(synAnt.getText());
        }
    }

    public List<VBox> getSynAntVboxes() {
        return synAntVboxes;
    }

    public String getSynAntText(int ind) {
        return synAntTexts.get(ind);
    }
}
