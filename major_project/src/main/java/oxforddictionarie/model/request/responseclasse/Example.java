package oxforddictionarie.model.request.responseclasse;

import java.util.List;

// Inline model 12
public class Example {
    private List<String> definitions; // optional
    private List<Domain> domains; // optional
    private List<CategorizedText> notes; // optional
    private List<Region> regions; // optional
    private List<Register> registers; // optional
    private List<String> senseIds; // optional
    private String text;

    public List<String> getDefinitions() {
        return definitions;
    }

    public List<Domain> getDomains() {
        return domains;
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

    public List<String> getSenseIds() {
        return senseIds;
    }

    public String getText() {
        return text;
    }
}
