package oxfordDictionaries.model.request.responseClasses;

import java.util.List;

public class LexicalEntry {
    private List<RelatedEntry> compounds; // optional
    private List<RelatedEntry> derivativeOf; // optional
    private List<RelatedEntry> derivatives; // optional
    private List<Entry> entries; // optional
    private List<GrammaticalFeature> grammaticalFeatures; // optional
    private String language;
    private LexicalCategory lexicalCategory;
    private List<CategorizedText> notes; // optional
    private List<RelatedEntry> phrasalVerbs; // optional
    private List<RelatedEntry> phrases; // optional
    private List<Pronunciation> pronunciations; // optional
    private String root; // optional
    private String text;
    private List<VariantForm> variantForms; // optional
    private List<Inflection> inflectionOf;

    public List<RelatedEntry> getCompounds() {
        return compounds;
    }

    public List<RelatedEntry> getDerivativeOf() {
        return derivativeOf;
    }

    public List<RelatedEntry> getDerivatives() {
        return derivatives;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public List<GrammaticalFeature> getGrammaticalFeatures() {
        return grammaticalFeatures;
    }

    public String getLanguage() {
        return language;
    }

    public LexicalCategory getLexicalCategory() {
        return lexicalCategory;
    }

    public List<CategorizedText> getNotes() {
        return notes;
    }

    public List<RelatedEntry> getPhrasalVerbs() {
        return phrasalVerbs;
    }

    public List<RelatedEntry> getPhrases() {
        return phrases;
    }

    public List<Pronunciation> getPronunciations() {
        return pronunciations;
    }

    public String getRoot() {
        return root;
    }

    public String getText() {
        return text;
    }

    public List<VariantForm> getVariantForms() {
        return variantForms;
    }

    public List<Inflection> getInflectionOf() {
        return inflectionOf;
    }
}
