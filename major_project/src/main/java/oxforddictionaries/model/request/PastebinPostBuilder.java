package oxforddictionaries.model.request;

/**
 * Builds the PastebinPost as there may be situations where the object will have different attributes set
 */
public class PastebinPostBuilder {
    private PastebinPost pastebinPost;

    /**
     * Creates a new PastebinPost object
     * @param apiDevKey pastebin dev key
     * @param apiOption pastebin option
     * @param apiPasteCode pastebin code
     * @param apiPastePrivate pastebin privacy
     */
    public void newItem(String apiDevKey, String apiOption, String apiPasteCode, int apiPastePrivate) {
        this.pastebinPost = new PastebinPost(apiDevKey, apiOption, apiPasteCode, apiPastePrivate);
    }

    /**
     * Sets the PastebinPost user key
     * @param apiUserKey pastebin user key
     */
    public void setUserKey(String apiUserKey) {
        pastebinPost.setApiUserKey(apiUserKey);
    }

    /**
     * Sets the PastebinPost post name
     * @param apiPasteName pastebin post name
     */
    public void setPasteName(String apiPasteName) {
        pastebinPost.setApiPasteName(apiPasteName);
    }

    /**
     * Sets the PastebinPost expiration length
     * @param apiPasteExpireDate pastebin expiration length
     */
    public void setPasteExpireDate(String apiPasteExpireDate) {
        pastebinPost.setApiPasteExpireDate(apiPasteExpireDate);
    }

    /**
     * Sets the PastebinPost folder key
     * @param apiFolderKey pastebin folder key
     */
    public void setFolderKey(String apiFolderKey) {
        pastebinPost.setApiFolderKey(apiFolderKey);
    }

    /**
     * @return pastebin post
     */
    public PastebinPost getPastebinPost() {
        return pastebinPost;
    }


}
