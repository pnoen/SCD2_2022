package oxforddictionaries.model.request.responseclasses;

import java.util.List;

// Inline model 10
public class SynonymsAntonyms {
    private List<Domain> domains; // optional
    private String id; // optional
    private String language; // optional
    private List<Region> regions; // optional
    private List<Register> registers; // optional
    private String text;

    public List<Domain> getDomains() {
        return domains;
    }

    public String getId() {
        return id;
    }

    public String getLanguage() {
        return language;
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
