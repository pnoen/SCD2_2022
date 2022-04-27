package oxfordDictionaries.model.request.responseClasses;

import java.util.List;

// Inline model 2
public class RelatedEntry {
    private List<Domain> domains; // optional
    private String id;
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
