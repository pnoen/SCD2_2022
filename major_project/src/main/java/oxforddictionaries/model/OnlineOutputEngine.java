package oxforddictionaries.model;

import oxforddictionaries.model.request.PastebinFormatter;
import oxforddictionaries.model.request.PastebinPost;
import oxforddictionaries.model.request.PastebinPostBuilder;
import oxforddictionaries.model.request.Request;
import oxforddictionaries.model.request.responseclasses.RetrieveEntry;

import java.util.List;

/**
 * Calls the Pastebin Api with Post requests
 */
public class OnlineOutputEngine implements OutputEngine {
    private final String PASTEBIN_API_KEY;
    private Request request;
    private PastebinPostBuilder pastebinPostBuilder;
    private String pastebinLink;
    private PastebinFormatter pastebinFormatter;

    /**
     * @param pastebinApiKey pastebin api key
     * @param request request
     * @param pastebinPostBuilder pastebin post builder
     * @param pastebinFormatter pastebin post formatter
     */
    public OnlineOutputEngine(String pastebinApiKey, Request request, PastebinPostBuilder pastebinPostBuilder, PastebinFormatter pastebinFormatter) {
        this.PASTEBIN_API_KEY = pastebinApiKey;
        this.request = request;
        this.pastebinPostBuilder = pastebinPostBuilder;
        this.pastebinFormatter = pastebinFormatter;
    }

    /**
     * Creates the data to send and performs a POST request. If the response is OK, then return an empty list
     * @param retrieveEntry POJO
     * @param apiPastePrivate pastebin privacy
     * @param apiPasteName pastebin post name
     * @param apiUserKey pastebin user key
     * @param apiPasteExpireDate pastebin expiration length
     * @param apiFolderKey pastebin folder key
     * @return list of error messages
     */
    public List<String> sendReport(RetrieveEntry retrieveEntry, int apiPastePrivate, String apiPasteName, String apiUserKey,
                           String apiPasteExpireDate, String apiFolderKey) {
        String uri = "https://pastebin.com/api/api_post.php";
        String entry = pastebinFormatter.format(retrieveEntry);
//        System.out.println(entry);

        PastebinPost pastebinPost = createPastebinPost(entry, apiPastePrivate, apiPasteName, apiUserKey, apiPasteExpireDate, apiFolderKey);

        String postBody = pastebinPost.toString();
        List<String> response = request.postRequest(uri, postBody);

//        System.out.println(response);
        if (response.size() == 2) {
            int statusCode = Integer.parseInt(response.get(0));
//            System.out.println("Response body was:\n" + response.get(1));

            if (statusCode >= 200 && statusCode < 300) {
                this.pastebinLink = response.get(1);
                response.clear();
            }
        }
        return response;
    }

    /**
     * @return pastebin link
     */
    public String getPastebinLink() {
        return pastebinLink;
    }

    /**
     * Builds the pastebin post
     * @param entry entry
     * @param apiPastePrivate pastebin privacy
     * @param apiPasteName pastebin post name
     * @param apiUserKey pastebin user key
     * @param apiPasteExpireDate pastebin expiration length
     * @param apiFolderKey pastebin folder key
     * @return pastebin post
     */
    public PastebinPost createPastebinPost(String entry, int apiPastePrivate, String apiPasteName, String apiUserKey, String apiPasteExpireDate, String apiFolderKey) {
        pastebinPostBuilder.newItem(PASTEBIN_API_KEY, "paste", entry, apiPastePrivate);
        pastebinPostBuilder.setPasteName(apiPasteName);
        pastebinPostBuilder.setUserKey(apiUserKey);
        pastebinPostBuilder.setPasteExpireDate(apiPasteExpireDate);
        pastebinPostBuilder.setFolderKey(apiFolderKey);
        return pastebinPostBuilder.getPastebinPost();
    }
}
