package oxforddictionaries.model.request.responseclasses;

import java.util.List;

/**
 * POJO
 */
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

    /**
     * @return compounds
     */
    public List<RelatedEntry> getCompounds() {
        return compounds;
    }

    /**
     * @return derivative of
     */
    public List<RelatedEntry> getDerivativeOf() {
        return derivativeOf;
    }

    /**
     * @return derivatives
     */
    public List<RelatedEntry> getDerivatives() {
        return derivatives;
    }

    /**
     * @return entries
     */
    public List<Entry> getEntries() {
        return entries;
    }

    /**
     * @return grammatical features
     */
    public List<GrammaticalFeature> getGrammaticalFeatures() {
        return grammaticalFeatures;
    }

    /**
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @return lexical category
     */
    public LexicalCategory getLexicalCategory() {
        return lexicalCategory;
    }

    /**
     * @return notes
     */
    public List<CategorizedText> getNotes() {
        return notes;
    }

    /**
     * @return phrasal verbs
     */
    public List<RelatedEntry> getPhrasalVerbs() {
        return phrasalVerbs;
    }

    /**
     * @return phrases
     */
    public List<RelatedEntry> getPhrases() {
        return phrases;
    }

    /**
     * @return pronunciations
     */
    public List<Pronunciation> getPronunciations() {
        return pronunciations;
    }

    /**
     * @return root
     */
    public String getRoot() {
        return root;
    }

    /**
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * @return variant forms
     */
    public List<VariantForm> getVariantForms() {
        return variantForms;
    }

    /**
     * @return inflection of
     */
    public List<Inflection> getInflectionOf() {
        return inflectionOf;
    }
}
