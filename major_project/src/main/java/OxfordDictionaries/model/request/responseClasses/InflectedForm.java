package OxfordDictionaries.model.request.responseClasses;

import java.util.List;

public class InflectedForm {
    private List<Domain> domains; // optional
    private List<GrammaticalFeature> grammaticalFeatures; // optional
    private String inflectedForm;
    private LexicalCategory lexicalCategory; // optional
    private List<Pronunciation> pronunciations; // optional
    private List<Region> regions; // optional
    private List<Register> registers; // optional

    public List<Domain> getDomains() {
        return domains;
    }

    public List<GrammaticalFeature> getGrammaticalFeatures() {
        return grammaticalFeatures;
    }

    public String getInflectedForm() {
        return inflectedForm;
    }

    public LexicalCategory getLexicalCategory() {
        return lexicalCategory;
    }

    public List<Pronunciation> getPronunciations() {
        return pronunciations;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public List<Register> getRegisters() {
        return registers;
    }
}
