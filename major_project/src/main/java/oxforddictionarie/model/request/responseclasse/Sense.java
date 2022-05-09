package oxforddictionarie.model.request.responseclasse;

import java.util.List;

public class Sense {
    private List<SynonymsAntonyms> antonyms; // optional
    private List<Inline_model_2> constructions; // optional
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

    public List<SynonymsAntonyms> getAntonyms() {
        return antonyms;
    }

    public List<Inline_model_2> getConstructions() {
        return constructions;
    }

    public List<String> getCrossReferenceMarkers() {
        return crossReferenceMarkers;
    }

    public List<CrossReference> getCrossReferences() {
        return crossReferences;
    }

    public List<String> getDefinitions() {
        return definitions;
    }

    public List<DomainClass> getDomainClasses() {
        return domainClasses;
    }

    public List<Domain> getDomains() {
        return domains;
    }

    public List<String> getEtymologies() {
        return etymologies;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public String getId() {
        return id;
    }

    public List<InflectedForm> getInflections() {
        return inflections;
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

    public List<SemanticClass> getSemanticClasses() {
        return semanticClasses;
    }

    public List<String> getShortDefinitions() {
        return shortDefinitions;
    }

    public List<Sense> getSubsenses() {
        return subsenses;
    }

    public List<SynonymsAntonyms> getSynonyms() {
        return synonyms;
    }

    public List<ThesaurusLink> getThesaurusLinks() {
        return thesaurusLinks;
    }

    public List<VariantForm> getVariantForms() {
        return variantForms;
    }
}
