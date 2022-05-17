package oxforddictionaries.model;

import oxforddictionaries.model.request.DummyAPI;
import oxforddictionaries.model.request.responseclasses.RetrieveEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Calls from the dummy api instead of the real api to test the GUI
 */
public class OfflineOutputEngine implements OutputEngine {
    private DummyAPI dummyAPI;
    private String pastebinLink;

    /**
     * Creates the offline output engine
     * @param dummyAPI dummy api
     */
    public OfflineOutputEngine(DummyAPI dummyAPI) {
        this.dummyAPI = dummyAPI;
    }

    /**
     * Calls from the dummy api. Return an empty list
     * @param retrieveEntry POJO
     * @param apiPastePrivate pastebin privacy
     * @param apiPasteName pastebin post name
     * @param userApiKey pastebin user api key
     * @param apiPasteExpireDate pastebin expiration length
     * @param apiFolderKey pastebin folder key
     * @return empty list
     */
    public List<String> sendReport(RetrieveEntry retrieveEntry, int apiPastePrivate, String apiPasteName, String userApiKey,
                                   String apiPasteExpireDate, String apiFolderKey) {
        String response = dummyAPI.getSendReportResponse();
        this.pastebinLink = response;
        List<String> error = new ArrayList<>();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            error.add(e.getMessage());
        }
        return error;
    }

    /**
     * @return pastebin link
     */
    public String getPastebinLink() {
        return pastebinLink;
    }
}
