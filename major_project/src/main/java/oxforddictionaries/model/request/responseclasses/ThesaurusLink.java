package oxforddictionaries.model.request.responseclasses;

import com.google.gson.annotations.SerializedName;

/**
 * POJO
 */
public class ThesaurusLink {
    @SerializedName(value = "entry_id")
    private String entryId;
    @SerializedName(value = "sense_id")
    private String senseId;

    /**
     * @return entry id
     */
    public String getEntryId() {
        return entryId;
    }

    /**
     * @return sense id
     */
    public String getSenseId() {
        return senseId;
    }
}
