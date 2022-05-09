package oxforddictionaries.model.request.responseclasses;

import java.util.List;

// Inline model 1
public class Pronunciation {
    private String audioFile; // optional
    private List<String> dialects; // optional
    private String phoneticNotation; // optional
    private String phoneticSpelling; // optional
    private List<Region> regions; // optional
    private List<Register> registers; // optional

    public String getAudioFile() {
        return audioFile;
    }

    public List<String> getDialects() {
        return dialects;
    }

    public String getPhoneticNotation() {
        return phoneticNotation;
    }

    public String getPhoneticSpelling() {
        return phoneticSpelling;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public List<Register> getRegisters() {
        return registers;
    }
}
