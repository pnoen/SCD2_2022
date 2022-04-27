package oxfordDictionaries.model.request.responseClasses;

import java.util.List;

// Inline model 5
public class VariantForm {
    private List<Domain> domains; // optional
    private List<CategorizedText> notes; // optional
    private List<Pronunciation> pronunciations; // optional
    private List<Region> regions; // optional
    private List<Register> registers; // optional
    private String text;

    public List<Domain> getDomains() {
        return domains;
    }

    public List<CategorizedText> getNotes() {
        return notes;
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

    public String getText() {
        return text;
    }
}
