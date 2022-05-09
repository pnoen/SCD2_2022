package oxforddictionarie.model;

import oxforddictionarie.model.request.DummyAPI;
import oxforddictionarie.model.request.responseclasse.RetrieveEntry;

import java.util.ArrayList;
import java.util.List;

public class OfflineOutputEngine implements OutputEngine {
    private DummyAPI dummyAPI;
    private String pastebinLink;

    public OfflineOutputEngine(DummyAPI dummyAPI) {
        this.dummyAPI = dummyAPI;
    }

    public List<String> sendReport(RetrieveEntry retrieveEntry, int apiPastePrivate, String apiPasteName, String userApiKey,
                                   String apiPasteExpireDate, String apiFolderKey) {
        String response = dummyAPI.getSendReportResponse();
        this.pastebinLink = response;
        return new ArrayList<>();
    }

    public String getPastebinLink() {
        return pastebinLink;
    }
}
