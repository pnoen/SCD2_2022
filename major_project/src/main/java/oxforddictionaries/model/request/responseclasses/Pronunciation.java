package oxforddictionaries.model.request.responseclasses;

import java.util.List;

/**
 * POJO
 */
// Inline model 1
public class Pronunciation {
    private String audioFile; // optional
    private List<String> dialects; // optional
    private String phoneticNotation; // optional
    private String phoneticSpelling; // optional
    private List<Region> regions; // optional
    private List<Register> registers; // optional

    /**
     * @return audio file
     */
    public String getAudioFile() {
        return audioFile;
    }

    /**
     * @return dialects
     */
    public List<String> getDialects() {
        return dialects;
    }

    /**
     * @return phonetic notation
     */
    public String getPhoneticNotation() {
        return phoneticNotation;
    }

    /**
     * @return phonetic spelling
     */
    public String getPhoneticSpelling() {
        return phoneticSpelling;
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
}
