package oxforddictionaries.model.request.responseclasses;

import java.util.List;

public class InlineModel2 {
    private List<Domain> domains; // optional
    private List<ExampleText> examples; // optional
    private List<CategorizedText> notes; // optional
    private List<Region> regions; // optional
    private List<Register> registers; // optional
    private String text;

    public List<Domain> getDomains() {
        return domains;
    }

    public List<ExampleText> getExamples() {
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
