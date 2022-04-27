package oxfordDictionaries.model;

import oxfordDictionaries.model.request.PastebinPost;
import oxfordDictionaries.model.request.PastebinPostBuilder;
import oxfordDictionaries.model.request.Request;
import oxfordDictionaries.model.request.responseClasses.RetrieveEntry;
import com.google.gson.Gson;

import java.util.List;

public class OnlineOutputEngine implements OutputEngine {
    private String PASTEBIN_API_KEY;
    private Request request;
    private PastebinPostBuilder pastebinPostBuilder;
    private String pastebinLink;

    public OnlineOutputEngine(String PASTEBIN_API_KEY, Request request, PastebinPostBuilder pastebinPostBuilder) {
        this.PASTEBIN_API_KEY = PASTEBIN_API_KEY;
        this.request = request;
        this.pastebinPostBuilder = pastebinPostBuilder;
    }

    public List<String> sendReport(RetrieveEntry retrieveEntry, int apiPastePrivate, String apiPasteName, String apiUserKey,
                           String apiPasteExpireDate, String apiFolderKey) {
        String uri = "https://pastebin.com/api/api_post.php";
        Gson gson = new Gson();
        String entry = gson.toJson(retrieveEntry);

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

    public String getPastebinLink() {
        return pastebinLink;
    }

    public PastebinPost createPastebinPost(String entry, int apiPastePrivate, String apiPasteName, String apiUserKey, String apiPasteExpireDate, String apiFolderKey) {
        pastebinPostBuilder.newItem(PASTEBIN_API_KEY, "paste", entry, apiPastePrivate);
        pastebinPostBuilder.setPasteFormat("json");
        pastebinPostBuilder.setPasteName(apiPasteName);
        pastebinPostBuilder.setUserKey(apiUserKey);
        pastebinPostBuilder.setPasteExpireDate(apiPasteExpireDate);
        pastebinPostBuilder.setFolderKey(apiFolderKey);
        return pastebinPostBuilder.getPastebinPost();
    }
}
