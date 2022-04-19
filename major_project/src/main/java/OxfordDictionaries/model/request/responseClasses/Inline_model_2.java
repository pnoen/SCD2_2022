package OxfordDictionaries.model.request.responseClasses;

import java.util.List;

public class Inline_model_2 {
    private List<Domain> domains; // optional
    private List<String> examples; // optional
    private List<CategorizedText> notes; // optional
    private List<Region> regions; // optional
    private List<Register> registers; // optional
    private String text;

    public List<Domain> getDomains() {
        return domains;
    }

    public List<String> getExamples() {
        return examples;
    }

    public List<CategorizedText> getNotes() {
        return notes;
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
