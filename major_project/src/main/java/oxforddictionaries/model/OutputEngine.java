package oxforddictionaries.model;

import oxforddictionaries.model.request.responseclasses.RetrieveEntry;

import java.util.List;

/**
 * The application has the option to use an online or offline engine for the POST requests.
 */
public interface OutputEngine {
    /**
     * Creates the data to send and performs a POST request.
     * @param retrieveEntry POJO
     * @param apiPastePrivate pastebin privacy
     * @param apiPasteName pastebin post name
     * @param userApiKey pastebin user api key
     * @param apiPasteExpireDate pastebin expiration length
     * @param apiFolderKey pastebin folder key
     * @return list of errors
     */
    List<String> sendReport(RetrieveEntry retrieveEntry, int apiPastePrivate, String apiPasteName, String userApiKey,
                            String apiPasteExpireDate, String apiFolderKey);

    /**
     * @return pastebin link
     */
    String getPastebinLink();
}
