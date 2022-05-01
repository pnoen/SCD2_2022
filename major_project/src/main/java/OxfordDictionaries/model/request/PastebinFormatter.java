package OxfordDictionaries.model.request;

import OxfordDictionaries.model.request.responseClasses.*;

import java.util.List;

public class PastebinFormatter {

    public String format(RetrieveEntry retrieveEntry) {
        String formatted = "";
        int level = 0;

        formatted += handleString(retrieveEntry.getId(), "ID: ", level);

        if (retrieveEntry.getMetadata() != null) {
            formatted += createMetaData(retrieveEntry.getMetadata(), level);
        }

        if (retrieveEntry.getResults() != null) {
            formatted += "\n" + "\t".repeat(level) + "Results: ";
            for (HeadwordEntry result : retrieveEntry.getResults()) {
                formatted += createHeadwordEntry(result, level + 1);
            }
        }

        formatted += handleString(retrieveEntry.getWord(), "Word: ", level);

        return formatted;
    }

    public String createMetaData(Metadata metadata, int level) {
        String formatted = "\n" + "\t".repeat(level) + "Metadata: ";

        if (metadata.getOperation() != null) {
            formatted += handleString(metadata.getOperation(), "Operation: ", level + 1);
        }

        if (metadata.getProvider() != null) {
            formatted += handleString(metadata.getProvider(), "Provider: ", level + 1);
        }

        if (metadata.getSchema() != null) {
            formatted += handleString(metadata.getSchema(), "Schema: ", level + 1);
        }
        return formatted;
    }

    public String createHeadwordEntry(HeadwordEntry headwordEntry, int level) {
        String formatted = "";

        if (headwordEntry.getLexicalEntries() != null) {
            formatted += "\n" + "\t".repeat(level) + "Lexical Entries: ";
            for (LexicalEntry lex : headwordEntry.getLexicalEntries()) {
                formatted += createLexicalEntry(lex, level + 1);
            }
        }

        if (headwordEntry.getPronunciations() != null) {
            formatted += handlePronunciations(headwordEntry.getPronunciations(), level);
        }

        if (headwordEntry.getType() != null) {
            formatted += handleString(headwordEntry.getType(), "Type: ", level);
        }

        formatted += handleString(headwordEntry.getWord(), "Word: ", level);
        return formatted;
    }

    public String createLexicalEntry(LexicalEntry lexicalEntry, int level) {
        String formatted = "";
        if (lexicalEntry.getCompounds() != null) {
            formatted += handleRelatedEntries(lexicalEntry.getCompounds(), "Compounds: ", level);
        }

        if (lexicalEntry.getDerivativeOf() != null) {
            formatted += handleRelatedEntries(lexicalEntry.getDerivativeOf(), "Derivative Of: ", level);
        }

        if (lexicalEntry.getDerivatives() != null) {
            formatted += handleRelatedEntries(lexicalEntry.getDerivatives(), "Derivatives: ", level);
        }

        if (lexicalEntry.getEntries() != null) {
            formatted += "\n" + "\t".repeat(level) + "Entries: ";
            for (Entry entry : lexicalEntry.getEntries()) {
                formatted += createEntry(entry, level + 1);
            }
        }

        if (lexicalEntry.getGrammaticalFeatures() != null) {
            formatted += handleGrammaticalFeatures(lexicalEntry.getGrammaticalFeatures(), level);
        }

        formatted += handleString(lexicalEntry.getLanguage(), "Language: ", level);
        formatted += handleLexicalCategory(lexicalEntry.getLexicalCategory(), level);

        if (lexicalEntry.getNotes() != null) {
            formatted += handleNotes(lexicalEntry.getNotes(), level);
        }

        if (lexicalEntry.getPhrasalVerbs() != null) {
            formatted += handleRelatedEntries(lexicalEntry.getPhrasalVerbs(), "Phrasal Verbs: ", level);
        }

        if (lexicalEntry.getPhrases() != null) {
            formatted += handleRelatedEntries(lexicalEntry.getPhrases(), "Phrases: ", level);
        }

        if (lexicalEntry.getPronunciations() != null) {
            formatted += handlePronunciations(lexicalEntry.getPronunciations(), level);
        }

        if (lexicalEntry.getRoot() != null) {
            formatted += handleString(lexicalEntry.getRoot(), "Root: ", level);
        }

        formatted += handleString(lexicalEntry.getText(), "Text: ", level);

        if (lexicalEntry.getVariantForms() != null) {
            formatted += handleVariantForms(lexicalEntry.getVariantForms(), level);
        }

        return formatted;
    }

    public String createEntry(Entry entry, int level) {
        String formatted = "";

        if (entry.getCrossReferenceMarkers() != null) {
            formatted += handleStrings(entry.getCrossReferenceMarkers(), "Cross Reference Markers: ", level);
        }

        if (entry.getCrossReferences() != null) {
            formatted += handleCrossReferences(entry.getCrossReferences(), level);
        }

        if (entry.getEtymologies() != null) {
            formatted += handleStrings(entry.getEtymologies(), "Etymologies: ", level);
        }

        if (entry.getGrammaticalFeatures() != null) {
            formatted += handleGrammaticalFeatures(entry.getGrammaticalFeatures(), level);
        }

        if (entry.getHomographNumber() != null) {
            formatted += handleString(entry.getHomographNumber(), "Homograph Number: ", level);
        }

        if (entry.getInflections() != null) {
            formatted += handleInflections(entry.getInflections(), level);
        }

        if (entry.getNotes() != null) {
            formatted += handleNotes(entry.getNotes(), level);
        }

        if (entry.getPronunciations() != null) {
            formatted += handlePronunciations(entry.getPronunciations(), level);
        }

        if (entry.getSenses() != null) {
            formatted += handleSenses(entry.getSenses(), "Senses: ", level);
        }

        if (entry.getVariantForms() != null) {
            formatted += handleVariantForms(entry.getVariantForms(), level);
        }

        return formatted;
    }

    public String createPronunciation(Pronunciation pronunciation, int level) {
        String formatted = "";
        if (pronunciation.getAudioFile() != null) {
            formatted += handleString(pronunciation.getAudioFile(), "Audio: ", level);
        }

        if (pronunciation.getDialects() != null) {
            formatted += handleStrings(pronunciation.getDialects(), "Dialects: ", level);
        }

        if (pronunciation.getPhoneticNotation() != null) {
            formatted += handleString(pronunciation.getPhoneticNotation(), "Phonetic Notation: ", level);
        }

        if (pronunciation.getPhoneticSpelling() != null) {
            formatted += handleString(pronunciation.getPhoneticSpelling(), "Phonetic Spelling: ", level);
        }

        if (pronunciation.getRegions() != null) {
            formatted += handleRegions(pronunciation.getRegions(), level);
        }

        if (pronunciation.getRegisters() != null) {
            formatted += handleRegisters(pronunciation.getRegisters(), level);
        }
        return formatted;
    }

    public String createRelatedEntry(RelatedEntry relatedEntry, int level) {
        String formatted = "";
        if (relatedEntry.getDomains() != null) {
            formatted += handleDomains(relatedEntry.getDomains(), level);
        }

        formatted += handleString(relatedEntry.getId(), "ID: ", level);

        if (relatedEntry.getLanguage() != null) {
            formatted += handleString(relatedEntry.getLanguage(), "Language: ", level);
        }

        if (relatedEntry.getRegions() != null) {
            formatted += handleRegions(relatedEntry.getRegions(), level);
        }

        if (relatedEntry.getRegisters() != null) {
            formatted += handleRegisters(relatedEntry.getRegisters(), level);
        }

        formatted += handleString(relatedEntry.getText(), "Text: ", level);
        return formatted;
    }

    public String createGrammaticalFeature(GrammaticalFeature grammaticalFeature, int level) {
        String formatted = "";
        formatted += handleString(grammaticalFeature.getId(), "ID: ", level);
        formatted += handleString(grammaticalFeature.getText(), "Text: ", level);
        formatted += handleString(grammaticalFeature.getType(), "Type: ", level);
        return formatted;
    }

    public String createLexicalCategory(LexicalCategory lexicalCategory, int level) {
        String formatted = "";
        formatted += handleString(lexicalCategory.getId(), "ID: ", level);
        formatted += handleString(lexicalCategory.getText(), "Text: ", level);
        return formatted;
    }

    public String createCategorizedText(CategorizedText categorizedText, int level) {
        String formatted = "";
        if (categorizedText.getId() != null) {
            formatted += handleString(categorizedText.getId(), "ID: ", level);
        }

        formatted += handleString(categorizedText.getText(), "Text: ", level);
        formatted += handleString(categorizedText.getType(), "Type: ", level);
        return formatted;
    }

    public String createVariantForm(VariantForm variantForm, int level) {
        String formatted = "";
        if (variantForm.getDomains() != null) {
            formatted += handleDomains(variantForm.getDomains(), level);
        }

        if (variantForm.getNotes() != null) {
            formatted += handleNotes(variantForm.getNotes(), level);
        }

        if (variantForm.getPronunciations() != null) {
            formatted += handlePronunciations(variantForm.getPronunciations(), level);
        }

        if (variantForm.getRegions() != null) {
            formatted += handleRegions(variantForm.getRegions(), level);
        }

        if (variantForm.getRegisters() != null) {
            formatted += handleRegisters(variantForm.getRegisters(), level);
        }

        formatted += handleString(variantForm.getText(), "Text: ", level);
        return formatted;
    }

    public String createString(String str, int level) {
        String formatted = "\n" + "\t".repeat(level) + str;
        return formatted;
    }

    public String createRegion(Region region, int level) {
        String formatted = "";
        formatted += handleString(region.getId(), "ID: ", level);
        formatted += handleString(region.getText(), "Text: ", level);
        return formatted;
    }

    public String createRegister(Register register, int level) {
        String formatted = "";
        formatted += handleString(register.getId(), "ID: ", level);
        formatted += handleString(register.getText(), "Text: ", level);
        return formatted;
    }

    public String createDomain(Domain domain, int level) {
        String formatted = "";
        formatted += handleString(domain.getId(), "ID: ", level);
        formatted += handleString(domain.getText(), "Text: ", level);
        return formatted;
    }

    public String createCrossReference(CrossReference crossReference, int level) {
        String formatted = "";
        formatted += handleString(crossReference.getId(), "ID: ", level);
        formatted += handleString(crossReference.getText(), "Text: ", level);
        formatted += handleString(crossReference.getType(), "Type: ", level);
        return formatted;
    }

    public String createInflectedForm(InflectedForm inflectedForm, int level) {
        String formatted = "";

        if (inflectedForm.getDomains() != null) {
            formatted += handleDomains(inflectedForm.getDomains(), level);
        }

        if (inflectedForm.getGrammaticalFeatures() != null) {
            formatted += handleGrammaticalFeatures(inflectedForm.getGrammaticalFeatures(), level);
        }

        formatted += handleString(inflectedForm.getInflectedForm(), "Inflected Form: ", level);

        if (inflectedForm.getLexicalCategory() != null) {
            formatted += handleLexicalCategory(inflectedForm.getLexicalCategory(), level);
        }

        if (inflectedForm.getPronunciations() != null) {
            formatted += handlePronunciations(inflectedForm.getPronunciations(), level);
        }

        if (inflectedForm.getRegions() != null) {
            formatted += handleRegions(inflectedForm.getRegions(), level);
        }

        if (inflectedForm.getRegisters() != null) {
            formatted += handleRegisters(inflectedForm.getRegisters(), level);
        }
        return formatted;
    }

    public String createSense(Sense sense, int level) {
        String formatted = "";

        if (sense.getAntonyms() != null) {
            formatted += handleSynonymsAntonyms(sense.getAntonyms(), "Antonyms: ", level);
        }

        if (sense.getConstructions() != null) {
            formatted += "\n" + "\t".repeat(level) + "Constructions: ";
            for (Inline_model_2 con : sense.getConstructions()) {
                formatted += createConstruction(con, level + 1);
            }
        }

        if (sense.getCrossReferenceMarkers() != null) {
            formatted += handleStrings(sense.getCrossReferenceMarkers(), "Cross Reference Markers: ", level);
        }

        if (sense.getCrossReferences() != null) {
            formatted += handleCrossReferences(sense.getCrossReferences(), level);
        }

        if (sense.getDefinitions() != null) {
            formatted += handleStrings(sense.getDefinitions(), "Definitions: ", level);
        }

        if (sense.getDomainClasses() != null) {
            formatted += "\n" + "\t".repeat(level) + "Domain Classes: ";
            for (DomainClass dom : sense.getDomainClasses()) {
                formatted += createDomainClass(dom, level + 1);
            }
        }

        if (sense.getDomains() != null) {
            formatted += handleDomains(sense.getDomains(), level);
        }

        if (sense.getEtymologies() != null) {
            formatted += handleStrings(sense.getEtymologies(), "Etymologies: ", level);
        }

        if (sense.getExamples() != null) {
            formatted += "\n" + "\t".repeat(level) + "Examples: ";
            for (Example exam : sense.getExamples()) {
                formatted += createExample(exam, level + 1);
            }
        }

        if (sense.getId() != null) {
            formatted += handleString(sense.getId(), "ID: ", level);
        }

        if (sense.getInflections() != null) {
            formatted += handleInflections(sense.getInflections(), level);
        }

        if (sense.getNotes() != null) {
            formatted += handleNotes(sense.getNotes(), level);
        }

        if (sense.getPronunciations() != null) {
            formatted += handlePronunciations(sense.getPronunciations(), level);
        }

        if (sense.getRegions() != null) {
            formatted += handleRegions(sense.getRegions(), level);
        }

        if (sense.getRegisters() != null) {
            formatted += handleRegisters(sense.getRegisters(), level);
        }

        if (sense.getSemanticClasses() != null) {
            formatted += "\n" + "\t".repeat(level) + "Semantic Classes: ";
            for (SemanticClass sem : sense.getSemanticClasses()) {
                formatted += createSemanticClass(sem, level + 1);
            }
        }

        if (sense.getShortDefinitions() != null) {
            formatted += handleStrings(sense.getShortDefinitions(), "Short Definitions: ", level);
        }

        if (sense.getSubsenses() != null) {
            formatted += handleSenses(sense.getSubsenses(), "Sub-senses: ", level);
        }

        if (sense.getSynonyms() != null) {
            formatted += handleSynonymsAntonyms(sense.getSynonyms(), "Synonyms: ", level);
        }

        if (sense.getThesaurusLinks() != null) {
            formatted += "\n" + "\t".repeat(level) + "Thesaurus Links: ";
            for (ThesaurusLink the : sense.getThesaurusLinks()) {
                formatted += createThesaurusLink(the, level + 1);
            }
        }

        if (sense.getVariantForms() != null) {
            formatted += handleVariantForms(sense.getVariantForms(), level);
        }
        return formatted;
    }

    public String createSynonymAntonym(SynonymsAntonyms synonymAntonym, int level) {
        String formatted = "";
        if (synonymAntonym.getDomains() != null) {
            formatted += handleDomains(synonymAntonym.getDomains(), level);
        }

        if (synonymAntonym.getId() != null) {
            formatted += handleString(synonymAntonym.getId(), "ID: ", level);
        }

        if (synonymAntonym.getLanguage() != null) {
            formatted += handleString(synonymAntonym.getLanguage(), "Language: ", level);
        }

        if (synonymAntonym.getRegions() != null) {
            formatted += handleRegions(synonymAntonym.getRegions(), level);
        }

        if (synonymAntonym.getRegisters() != null) {
            formatted += handleRegisters(synonymAntonym.getRegisters(), level);
        }

        formatted += handleString(synonymAntonym.getText(), "Text: ", level);
        return formatted;
    }

    public String createConstruction(Inline_model_2 construction, int level) {
        String formatted = "";

        if (construction.getDomains() != null) {
            formatted += handleDomains(construction.getDomains(), level);
        }

        if (construction.getExamples() != null) {
            formatted += "\n" + "\t".repeat(level) + "Examples: ";
            for (ExampleText exam : construction.getExamples()) {
                formatted += createExampleText(exam, level + 1);
            }
        }

        if (construction.getNotes() != null) {
            formatted += handleNotes(construction.getNotes(), level);
        }

        if (construction.getRegions() != null) {
            formatted += handleRegions(construction.getRegions(), level);
        }

        if (construction.getRegisters() != null) {
            formatted += handleRegisters(construction.getRegisters(), level);
        }

        formatted += handleString(construction.getText(), "Text: ", level);
        return formatted;
    }

    public String createDomainClass(DomainClass domainClass, int level) {
        String formatted = "";

        formatted += handleString(domainClass.getId(), "ID: ", level);
        formatted += handleString(domainClass.getText(), "Text: ", level);
        return formatted;
    }

    public String createExample(Example example, int level) {
        String formatted = "";

        if (example.getDefinitions() != null) {
            formatted += handleStrings(example.getDefinitions(), "Definitions: ", level);
        }

        if (example.getDomains() != null) {
            formatted += handleDomains(example.getDomains(), level);
        }

        if (example.getNotes() != null) {
            formatted += handleNotes(example.getNotes(), level);
        }

        if (example.getRegions() != null) {
            formatted += handleRegions(example.getRegions(), level);
        }

        if (example.getRegisters() != null) {
            formatted += handleRegisters(example.getRegisters(), level);
        }

        if (example.getSenseIds() != null) {
            formatted += handleStrings(example.getSenseIds(), "Sense IDs: ", level);
        }

        formatted += handleString(example.getText(), "Text: ", level);
        return formatted;
    }

    public String createSemanticClass(SemanticClass semanticClass, int level) {
        String formatted = "";

        formatted += handleString(semanticClass.getId(), "ID: ", level);
        formatted += handleString(semanticClass.getText(), "Text: ", level);
        return formatted;
    }

    public String createThesaurusLink(ThesaurusLink thesaurusLink, int level) {
        String formatted = "";

        formatted += handleString(thesaurusLink.getEntry_id(), "Entry ID: ", level);
        formatted += handleString(thesaurusLink.getSense_id(), "Sense ID: ", level);
        return formatted;
    }

    public String createExampleText(ExampleText exampleText, int level) {
        String formatted = "";

        formatted += handleString(exampleText.getText(), "Text: ", level);
        return formatted;
    }

    public String handleString(String value, String key, int level) {
        String formatted = "\n" + "\t".repeat(level) + key + value;
        return formatted;
    }

    public String handlePronunciations(List<Pronunciation> pronunciations, int level) {
        String formatted = "\n" + "\t".repeat(level) + "Pronunciations: ";
        for (Pronunciation pro : pronunciations) {
            formatted += createPronunciation(pro, level + 1);
        }
        return formatted;
    }

    public String handleRelatedEntries(List<RelatedEntry> relatedEntries, String key, int level) {
        String formatted = "\n" + "\t".repeat(level) + key;
        for (RelatedEntry entry : relatedEntries) {
            formatted += createRelatedEntry(entry, level + 1);
        }
        return formatted;
    }

    public String handleGrammaticalFeatures(List<GrammaticalFeature> grammaticalFeatures, int level) {
        String formatted = "\n" + "\t".repeat(level) + "Grammatical Features: ";
        for (GrammaticalFeature gram :  grammaticalFeatures) {
            formatted += createGrammaticalFeature(gram, level + 1);
        }
        return formatted;
    }

    public String handleLexicalCategory(LexicalCategory lexicalCategory, int level) {
        String formatted = "\n" + "\t".repeat(level) + "Lexical Category: ";
        formatted += createLexicalCategory(lexicalCategory, level + 1);
        return formatted;
    }

    public String handleNotes(List<CategorizedText> notes, int level) {
        String formatted = "\n" + "\t".repeat(level) + "Notes: ";
        for (CategorizedText note : notes) {
            formatted += createCategorizedText(note, level + 1);
        }
        return formatted;
    }

    public String handleVariantForms(List<VariantForm> variantForms, int level) {
        String formatted = "\n" + "\t".repeat(level) + "Variant Forms: ";
        for (VariantForm var : variantForms) {
            formatted += createVariantForm(var, level + 1);
        }
        return formatted;
    }

    public String handleStrings(List<String> strings, String key, int level) {
        String formatted = "\n" + "\t".repeat(level) + key;
        for (String str : strings) {
            formatted += createString(str, level + 1);
        }
        return formatted;
    }

    public String handleCrossReferences(List<CrossReference> crossReferences, int level) {
        String formatted = "\n" + "\t".repeat(level) + "Cross Reference: ";
        for (CrossReference cross : crossReferences) {
            formatted += createCrossReference(cross, level + 1);
        }
        return formatted;
    }

    public String handleInflections(List<InflectedForm> inflectedForms, int level) {
        String formatted = "\n" + "\t".repeat(level) + "Inflections: ";
        for (InflectedForm inf : inflectedForms) {
            formatted += createInflectedForm(inf, level + 1);
        }
        return formatted;
    }

    public String handleSenses(List<Sense> senses, String key, int level) {
        String formatted = "\n" + "\t".repeat(level) + key;
        for (Sense sen : senses) {
            formatted += createSense(sen, level + 1);
        }
        return formatted;
    }

    public String handleRegions(List<Region> regions, int level) {
        String formatted = "\n" + "\t".repeat(level) + "Regions: ";
        for (Region reg : regions) {
            formatted += createRegion(reg, level + 1);
        }
        return formatted;
    }

    public String handleRegisters(List<Register> registers, int level) {
        String formatted = "\n" + "\t".repeat(level) + "Registers: ";
        for (Register reg : registers) {
            formatted += createRegister(reg, level + 1);
        }
        return formatted;
    }

    public String handleDomains(List<Domain> domains, int level) {
        String formatted = "\n" + "\t".repeat(level) + "Domains: ";
        for (Domain dom : domains) {
            formatted += createDomain(dom, level + 1);
        }
        return formatted;
    }

    public String handleSynonymsAntonyms(List<SynonymsAntonyms> synonymsAntonyms, String key, int level) {
        String formatted = "\n" + "\t".repeat(level) + key;
        for (SynonymsAntonyms synAnt : synonymsAntonyms) {
            formatted += createSynonymAntonym(synAnt, level + 1);
        }
        return formatted;
    }
}
