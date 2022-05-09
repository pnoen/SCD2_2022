package oxforddictionarie.model;

import oxforddictionarie.model.request.Request;
import oxforddictionarie.model.request.responseclasse.RetrieveEntry;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OnlineInputEngine implements InputEngine {
    private Request request;
    private RetrieveEntry retrieveEntry;
    private List<List<String>> history;
    private int currentPageInd;

    public OnlineInputEngine(Request request) {
        this.request = request;
        this.history = new ArrayList<>();
    }

    public List<String> entrySearch(String lang, String word, String field, String gramFeat, String lexiCate,
                            String domains, String registers, String match, boolean newSearch, boolean historyEntry, boolean lemma) {
        String uri = "https://od-api.oxforddictionaries.com/api/v2/entries/" + lang + "/" + word;

        uri = createUriFields(uri, field, gramFeat, lexiCate, domains, registers, match);
        uri = uriEscape(uri);

        List<String> response = response = request.getRequest(uri);

//        System.out.println(response);
        if (response.size() == 2) {
            int statusCode = Integer.parseInt(response.get(0));

//            System.out.println("Response body was:\n" + response.get(1));

            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                this.retrieveEntry = gson.fromJson(response.get(1), RetrieveEntry.class);
                response.clear();

                if (!historyEntry) {
                    List<String> search = createHistoryEntry(lang, word, field, gramFeat, lexiCate, domains, registers, match, newSearch);
                    if (!newSearch) {
                        List<String> currentEntry = history.remove(currentPageInd);
                        history.add(currentEntry);
                    }

                    history.add(search);
                    currentPageInd = history.size() - 1;
                }
            }
            else if (statusCode >= 400 && statusCode < 500) {
                response = handleErrorReq(response.get(0), response.get(1));
                if (response.get(0).equals("404") && !lemma) {
                    response = null;
                }
            }
        }
        return response;
    }

    public RetrieveEntry getRetrieveEntry() {
        return retrieveEntry;
    }

    public List<String> lemmaSearch(String lang, String word, String gramFeat, String lexiCate) {
        String uri = "https://od-api.oxforddictionaries.com/api/v2/lemmas/" + lang + "/" + word;

        uri = createUriFields(uri, null, gramFeat, lexiCate, null, null, null);
        uri = uriEscape(uri);
//        System.out.println(uri);

        List<String> response = request.getRequest(uri);

//        System.out.println(response);
        if (response.size() == 2) {
            int statusCode = Integer.parseInt(response.get(0));
//            System.out.println("Response body was:\n" + response.get(1));

            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                this.retrieveEntry = gson.fromJson(response.get(1), RetrieveEntry.class);
                response.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                response = handleErrorReq(response.get(0), response.get(1));
                if (response.get(0).equals("404")) {
                    response = null;
                }
            }
        }
        return response;
    }

    public List<String> handleErrorReq(String code, String body) {
        Gson gson = new Gson();
        List<String> response = new ArrayList<>();
        response.add(code);
        Map<String, String> errorMap = gson.fromJson(body, Map.class);
        String msg = errorMap.get("error");
        response.add(msg);
        return response;
    }

    public List<List<String>> getHistory() {
        return history;
    }

    public void setCurrentPageInd(int ind) {
        this.currentPageInd = ind;
    }

    public String uriEscape(String uri) {
        String uriClean = uri.replace("%", "%25");
        uriClean = uriClean.replace(" ", "%20");
        uriClean = uriClean.replace("$", "%24");
        return uriClean;
    }

    public String createUriFields(String uri, String field, String gramFeat, String lexiCate, String domains, String registers, String match) {
        List<String> parameters = new ArrayList<>();
        parameters.add("fields=");
        parameters.add("grammaticalFeatures=");
        parameters.add("lexicalCategory=");
        parameters.add("domains=");
        parameters.add("registers=");
        parameters.add("strictMatch=");

        List<String> filters = new ArrayList<>();
        filters.add(field);
        filters.add(gramFeat);
        filters.add(lexiCate);
        filters.add(domains);
        filters.add(registers);
        filters.add(match);

        List<String> valid = new ArrayList<>();
        for (int i = 0; i < filters.size(); i++) {
            String filter = filters.get(i);
            if (filter != null) {
                String trim = filter.trim();
                if (!trim.equals("")) {
                    valid.add(parameters.get(i) + filter);
                }
            }
        }

        if (valid.size() >= 1) {
            String validStr = String.join("&", valid);
            uri += "?" + validStr;
        }

        return uri;
    }

    public List<String> createHistoryEntry(String lang, String word, String field, String gramFeat, String lexiCate, String domains,
                                           String registers, String match, boolean newSearch) {
        if (field == null) {
            field = "";
        }
        if (gramFeat == null) {
            gramFeat = "";
        }
        if (lexiCate == null) {
            lexiCate = "";
        }
        if (domains == null) {
            domains = "";
        }
        if (registers == null) {
            registers = "";
        }
        if (match == null) {
            match = "";
        }
        List<String> search = new ArrayList<>();
        search.add(lang);
        search.add(word);
        search.add(field);
        search.add(gramFeat);
        search.add(lexiCate);
        search.add(domains);
        search.add(registers);
        search.add(match);
        String newEntry = "Searched";
        if (!newSearch) {
            newEntry = "Synonym/Antonym of " + history.get(currentPageInd).get(1);

        }
        search.add(newEntry);

        return search;
    }

    public int getCurrentPageInd() {
        return currentPageInd;
    }

}
