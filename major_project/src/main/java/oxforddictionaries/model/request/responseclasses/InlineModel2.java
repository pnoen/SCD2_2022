package oxforddictionaries.model.request.responseclasses;

import java.util.List;

/**
 * POJO
 */
public class InlineModel2 {
    private List<Domain> domains; // optional
    private List<ExampleText> examples; // optional
    private List<CategorizedText> notes; // optional
    private List<Region> regions; // optional
    private List<Register> registers; // optional
    private String text;

    /**
     * @return domains
     */
    public List<Domain> getDomains() {
        return domains;
    }

    /**
     * @return examples
     */
    public List<ExampleText> getExamples() {
        return examples;
    }

    /**
     * @return notes
     */
    public List<CategorizedText> getNotes() {
        return notes;
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

    /**
     * @return text
     */
    public String getText() {
        return text;
    }
}
