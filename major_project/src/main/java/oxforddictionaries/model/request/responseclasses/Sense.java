package oxforddictionaries.model.request.responseclasses;

import java.util.List;

/**
 * POJO
 */
public class Sense {
    private List<SynonymsAntonyms> antonyms; // optional
    private List<InlineModel2> constructions; // optional
    private List<String> crossReferenceMarkers; // optional
    private List<CrossReference> crossReferences; // optional
    private List<String> definitions; // optional
    private List<DomainClass> domainClasses; // optional
    private List<Domain> domains; // optional
    private List<String> etymologies; // optional
    private List<Example> examples; // optional
    private String id; // optional
    private List<InflectedForm> inflections; // optional
    private List<CategorizedText> notes; // optional
    private List<Pronunciation> pronunciations; // optional
    private List<Region> regions; // optional
    private List<Register> registers; // optional
    private List<SemanticClass> semanticClasses; // optional
    private List<String> shortDefinitions; // optional
    private List<Sense> subsenses; // optional
    private List<SynonymsAntonyms> synonyms; // optional
    private List<ThesaurusLink> thesaurusLinks; // optional
    private List<VariantForm> variantForms; // optional

    /**
     * @return antonyms
     */
    public List<SynonymsAntonyms> getAntonyms() {
        return antonyms;
    }

    /**
     * @return constructions
     */
    public List<InlineModel2> getConstructions() {
        return constructions;
    }

    /**
     * @return cross reference markers
     */
    public List<String> getCrossReferenceMarkers() {
        return crossReferenceMarkers;
    }

    /**
     * @return cross references
     */
    public List<CrossReference> getCrossReferences() {
        return crossReferences;
    }

    /**
     * @return definitions
     */
    public List<String> getDefinitions() {
        return definitions;
    }

    /**
     * @return domain classes
     */
    public List<DomainClass> getDomainClasses() {
        return domainClasses;
    }

    /**
     * @return domains
     */
    public List<Domain> getDomains() {
        return domains;
    }

    /**
     * @return etymologies
     */
    public List<String> getEtymologies() {
        return etymologies;
    }

    /**
     * @return examples
     */
    public List<Example> getExamples() {
        return examples;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @return inflections
     */
    public List<InflectedForm> getInflections() {
        return inflections;
    }

    /**
     * @return notes
     */
    public List<CategorizedText> getNotes() {
        return notes;
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

    /**
     * @return semantic classes
     */
    public List<SemanticClass> getSemanticClasses() {
        return semanticClasses;
    }

    /**
     * @return short definitions
     */
    public List<String> getShortDefinitions() {
        return shortDefinitions;
    }

    /**
     * @return sub senses
     */
    public List<Sense> getSubsenses() {
        return subsenses;
    }

    /**
     * @return synonyms
     */
    public List<SynonymsAntonyms> getSynonyms() {
        return synonyms;
    }

    /**
     * @return thesaurus links
     */
    public List<ThesaurusLink> getThesaurusLinks() {
        return thesaurusLinks;
    }

    /**
     * @return variant forms
     */
    public List<VariantForm> getVariantForms() {
        return variantForms;
    }
}
