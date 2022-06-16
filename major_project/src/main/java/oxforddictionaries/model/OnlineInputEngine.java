package oxforddictionaries.model;

import oxforddictionaries.model.request.Request;
import oxforddictionaries.model.request.SqlDatabase;
import oxforddictionaries.model.request.responseclasses.RetrieveEntry;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Calls the Oxford Dictionaries Api with GET requests. Checks if the entry exists in the database before requesting.
 */
public class OnlineInputEngine implements InputEngine {
    private Request request;
    private RetrieveEntry retrieveEntry;
    private List<List<String>> history;
    private int currentPageInd;
    private LemmaProcessor lemmaProcessor;
    private SqlDatabase db;
    private AboutData aboutData;
    private List<List<String>> pronunciations;

    /**
     * Creates the online input engine. Setups the database.
     * @param request request
     * @param lemmaProcessor lemma processor
     * @param db sql database
     * @param aboutData about information
     */
    public OnlineInputEngine(Request request, LemmaProcessor lemmaProcessor, SqlDatabase db, AboutData aboutData) {
        this.request = request;
        this.history = new ArrayList<>();
        this.lemmaProcessor = lemmaProcessor;
        this.db = db;
        this.aboutData = aboutData;
        this.pronunciations = new ArrayList<>();

        db.setupDB();
    }

    /**
     * Check the database if the uri exists. If it doesn't then request from the api. If found, notify the user to
     * select if they want to use it or not. When the user selects a new request, update the database.
     * Check if the response is OK. If the response errors then return the list of errors.
     * If valid, create the POJO. If it is not a history search then add it to the history
     * and move the current entry to the end if it is not a new search. Return an empty list if valid.
     * @param lang language
     * @param word word
     * @param field field
     * @param gramFeat grammatical features
     * @param lexiCate lexical categories
     * @param domains domains
     * @param registers registers
     * @param match match
     * @param newSearch new search
     * @param historyEntry history search
     * @param lemma lemma search
     * @param cacheDecided notified user
     * @param useCache cache or request new data
     * @return list of error messages
     */
    public List<String> entrySearch(String lang, String word, String field, String gramFeat, String lexiCate,
                            String domains, String registers, String match, boolean newSearch, boolean historyEntry, boolean lemma,
                                    boolean cacheDecided, boolean useCache) {
        String uri = "https://od-api.oxforddictionaries.com/api/v2/entries/" + lang + "/" + word;

        uri = createUriFields(uri, field, gramFeat, lexiCate, domains, registers, match);
        uri = uriEscape(uri);

        List<String> response = db.getEntry(uri);
        boolean cached = true;
        if (response.size() == 0) {
            response = request.getRequest(uri);
            cached = false;
        }

        if (!cacheDecided && cached) {
            response.clear();
            response.add(null);
            return response;
        }

        boolean update = false;
        if (cacheDecided && !useCache) {
            response = request.getRequest(uri);
            update = true;
        }
//        System.out.println(response);
        if (response.size() == 2) {
            int statusCode = Integer.parseInt(response.get(0));
            if (!cached) {
                String error = db.addEntry(uri, response.get(1), statusCode);
                if (error != null) {
                    response.clear();
                    response.add(error);
                    return response;
                }
            }

            if (update) {
                String error = db.updateEntry(uri, response.get(1), statusCode);
                if (error != null) {
                    response.clear();
                    response.add(error);
                    return response;
                }
            }

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

    /**
     * Gets the POJO
     * @return entry
     */
    public RetrieveEntry getRetrieveEntry() {
        return retrieveEntry;
    }

    /**
     * Creates the uri and performs a GET request. Before requesting the api, it checks the database.
     * If found, notify the user to select if they want to use it or not.
     * When the user selects a new request, update the database.
     * If the response errors then return the list of errors.
     * If valid, create the POJO and return an empty list.
     * @param lang language
     * @param word word
     * @param gramFeat grammatical features
     * @param lexiCate lexical categories
     * @param cacheDecided cached been decided
     * @param useCache cache or request new data
     * @return list of error messages
     */
    public List<String> lemmaSearch(String lang, String word, String gramFeat, String lexiCate, boolean cacheDecided, boolean useCache) {
        String uri = "https://od-api.oxforddictionaries.com/api/v2/lemmas/" + lang + "/" + word;

        uri = createUriFields(uri, null, gramFeat, lexiCate, null, null, null);
        uri = uriEscape(uri);
//        System.out.println(uri);

        List<String> response = db.getLemma(uri);
        boolean cached = true;
        if (response.size() == 0) {
            response = request.getRequest(uri);
            cached = false;
        }

        if (!cacheDecided && cached) {
            response.clear();
            response.add(null);
            return response;
        }

        boolean update = false;
        if (cacheDecided && !useCache) {
            response = request.getRequest(uri);
            update = true;
        }

//        System.out.println(response);
        if (response.size() == 2) {
            int statusCode = Integer.parseInt(response.get(0));
//            System.out.println("Response body was:\n" + response.get(1));
            if (!cached) {
                String error = db.addLemma(uri, response.get(1), statusCode);
                if (error != null) {
                    response.clear();
                    response.add(error);
                    return response;
                }
            }

            if (update) {
                String error = db.updateLemma(uri, response.get(1), statusCode);
                if (error != null) {
                    response.clear();
                    response.add(error);
                    return response;
                }
            }

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

    /**
     * Maps the raw JSON and adds the status code and message to a list
     * @param code status code
     * @param body raw json
     * @return list of error messages
     */
    public List<String> handleErrorReq(String code, String body) {
        Gson gson = new Gson();
        List<String> response = new ArrayList<>();
        response.add(code);
        Map errorMap = gson.fromJson(body, Map.class);
        String msg = String.valueOf(errorMap.get("error"));
        response.add(msg);
        return response;
    }

    /**
     * @return history
     */
    public List<List<String>> getHistory() {
        return history;
    }

    /**
     * Updates the current page index
     * @param ind page index
     */
    public void setCurrentPageInd(int ind) {
        this.currentPageInd = ind;
    }

    /**
     * Formats the uri to remove %, $ and spaces to remove
     * @param uri uri
     * @return escaped uri
     */
    public String uriEscape(String uri) {
        String uriClean = uri.replace("%", "%25");
        uriClean = uriClean.replace(" ", "%20");
        uriClean = uriClean.replace("$", "%24");
        return uriClean;
    }

    /**
     * Adds the parameters to the uri if they exist
     * @param uri uri
     * @param field field
     * @param gramFeat grammatical features
     * @param lexiCate lexical categories
     * @param domains domains
     * @param registers registers
     * @param match match
     * @return uri
     */
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

    /**
     * Adds all the parameters to a list, if they are null, set them to an empty string.
     * @param lang language
     * @param word word
     * @param field field
     * @param gramFeat grammatical features
     * @param lexiCate lexical categories
     * @param domains domains
     * @param registers registers
     * @param match match
     * @param newSearch new search
     * @return history
     */
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

    /**
     * Gets the index it is currently at for the history
     * @return page index
     */
    public int getCurrentPageInd() {
        return currentPageInd;
    }

    /**
     * Finds the lemmas from the POJO
     * @return List of lemmas
     */
    public List<List<String>> findLemmas() {
        return lemmaProcessor.createData(retrieveEntry);
    }

    /**
     * Clears the database tables.
     * @return error message
     */
    public String clearCache() {
        return db.clearDatabase();
    }

    /**
     * Gets the application name from the about data
     * @return application name
     */
    public String getAboutAppName() {
        return aboutData.getAppName();
    }

    /**
     * Gets the developer name from the about data
     * @return developer name
     */
    public String getAboutDevName() {
        return aboutData.getDevName();
    }

    /**
     * Gets the references from the about data
     * @return references
     */
    public List<String> getAboutReferences() {
        return aboutData.getReferences();
    }


    /**
     * Adds pronunciation to the list of pronunciations. If the uri is already in the list, return false.
     * If the entry id is null, change the id to '-'.
     * @param entryId Entry ID
     * @param pronunciation Pronunciation URI
     * @return added or not
     */
    public boolean addPronunciation(String entryId, String pronunciation) {
        for (List<String> pro : pronunciations) {
            if (pro.contains(pronunciation)) {
                return false;
            }
        }

        List<String> newPronunciation = new ArrayList<>();
        String id = "-";
        if (entryId != null) {
            id = entryId;
        }
        newPronunciation.add(id);
        newPronunciation.add(pronunciation);
        pronunciations.add(newPronunciation);
        return true;
    }

    /**
     * Gets the list of pronunciations
     * @return pronunciations
     */
    public List<List<String>> getPronunciations() {
        return pronunciations;
    }

    /**
     * Removes pronunciation from the list of pronunciations
     * @param pronunciation Pronunciation URI
     * @return removed or not
     */
    public boolean removePronunciation(String pronunciation) {
        boolean contains = false;
        List<String> removeList = null;
        for (List<String> pro : pronunciations) {
            if (pro.contains(pronunciation)) {
                contains = true;
                removeList = pro;
            }
        }

        if (!contains) {
            return false;
        }

        pronunciations.remove(removeList);
        return true;
    }
}
