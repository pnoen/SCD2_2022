package oxforddictionaries.model.request;

/**
 * Holds all the information that is going to be added to the POST uri
 */
public class PastebinPost {
    private String apiDevKey;
    private String apiOption;
    private String apiPasteCode;
    private int apiPastePrivate;
    private String apiUserKey;
    private String apiPasteName;
    private String apiPasteFormat;
    private String apiPasteExpireDate;
    private String apiFolderKey;

    /**
     * Creates the PastebinPost
     * @param apiDevKey pastebin dev key
     * @param apiOption pastebin option
     * @param apiPasteCode pastebin code
     * @param apiPastePrivate pastebin privacy
     */
    public PastebinPost(String apiDevKey, String apiOption, String apiPasteCode, int apiPastePrivate) {
        this.apiDevKey = apiDevKey;
        this.apiOption = apiOption;
        this.apiPasteCode = apiPasteCode;
        this.apiPastePrivate = apiPastePrivate;
    }

    /**
     * Sets the pastebin user key
     * @param apiUserKey pastebin user key
     */
    public void setApiUserKey(String apiUserKey) {
        this.apiUserKey = apiUserKey;
    }

    /**
     * Sets the pastebin post name
     * @param apiPasteName pastebin post name
     */
    public void setApiPasteName(String apiPasteName) {
        this.apiPasteName = apiPasteName;
    }

    /**
     * Sets the pastebin post expire length
     * @param apiPasteExpireDate pastebin post expire length
     */
    public void setApiPasteExpireDate(String apiPasteExpireDate) {
        this.apiPasteExpireDate = apiPasteExpireDate;
    }

    /**
     * Sets the pastebin folder key
     * @param apiFolderKey pastebin folder key
     */
    public void setApiFolderKey(String apiFolderKey) {
        this.apiFolderKey = apiFolderKey;
    }

    /**
     * Formats the string to include every attribute that is not null
     * E.g. api_dev_key=key{@literal &}api_option=option{@literal &}api_paste_code=code{@literal &}api_paste_private=0
     * @return string form of PastebinPost
     */
    @Override
    public String toString() {
        String str = "api_dev_key=" + apiDevKey + "&api_option=" + apiOption + "&api_paste_code=" + apiPasteCode + "&api_paste_private=" + apiPastePrivate;
        if (apiPasteName != null) {
            str += "&api_paste_name=" + apiPasteName;
        }
        if (apiPasteFormat != null) {
            str += "&api_paste_format=" + apiPasteFormat;
        }
        if (apiUserKey != null) {
            str += "&api_user_key=" + apiUserKey;
        }
        if (apiPasteExpireDate != null) {
            str += "&api_paste_expire_date=" + apiPasteExpireDate;
        }
        if (apiFolderKey != null) {
            str += "&api_folder_key=" + apiFolderKey;
        }
        return str;
    }
}
