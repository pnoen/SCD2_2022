package oxforddictionaries.model;

import oxforddictionaries.model.request.responseclasses.RetrieveEntry;

import java.util.List;

public interface OutputEngine {
    List<String> sendReport(RetrieveEntry retrieveEntry, int apiPastePrivate, String apiPasteName, String userApiKey,
                            String apiPasteExpireDate, String apiFolderKey);

    String getPastebinLink();
}
