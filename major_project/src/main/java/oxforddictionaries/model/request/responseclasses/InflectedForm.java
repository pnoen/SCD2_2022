package oxforddictionaries.model.request.responseclasses;

import java.util.List;

/**
 * POJO
 */
public class InflectedForm {
    private List<Domain> domains; // optional
    private List<GrammaticalFeature> grammaticalFeatures; // optional
    private String inflectedForm;
    private LexicalCategory lexicalCategory; // optional
    private List<Pronunciation> pronunciations; // optional
    private List<Region> regions; // optional
    private List<Register> registers; // optional

    /**
     * @return domains
     */
    public List<Domain> getDomains() {
        return domains;
    }

    /**
     * @return grammatical features
     */
    public List<GrammaticalFeature> getGrammaticalFeatures() {
        return grammaticalFeatures;
    }

    /**
     * @return inflected form
     */
    public String getInflectedForm() {
        return inflectedForm;
    }

    /**
     * @return lexical category
     */
    public LexicalCategory getLexicalCategory() {
        return lexicalCategory;
    }

    /**
     * @return pronunciations
     */
    public List<Pronunciation> getPronunciations() {
        return pronunciations;
    }

    /**
     * @return regions
     */
    public List<Region> getRegions() {
        return regions;
    }

    /**
     * @return registers
     */
    public List<Register> getRegisters() {
        return registers;
    }
}
