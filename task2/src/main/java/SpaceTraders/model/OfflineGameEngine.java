package SpaceTraders.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OfflineGameEngine implements GameEngine {
    private String status;
    private String statusIconColour;
    private DummyAPI dummyAPI;
    private Token currentToken;
    private List<Loan> availableLoans;
    private List<Ship> availableShips;
    private List<Goods> goods;
    private List<Location> locations;
    private FlightPlan flightPlan;
    private Status serverStatus;

    public OfflineGameEngine(DummyAPI dummyAPI) {
        this.status = "Offline";
        this.statusIconColour = "FF0000";
        this.dummyAPI = dummyAPI;
    }

    public String getStatus() {
        return this.status;
    }

    public String getStatusIconColour() {
        return this.statusIconColour;
    }

    public List<String> register(String username) {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getRegisterJson();
        Token token = gson.fromJson(rawJson, Token.class);
        this.currentToken = token;

        return new ArrayList<String>();
    }

    public List<String> getAccountDetails(String authToken) {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getAccountDetailsJson();
        Token token = gson.fromJson(rawJson, Token.class);
        token.setToken(authToken);
        this.currentToken = token;

        return new ArrayList<String>();
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public void logout() {
        this.currentToken = null;
    }

    public List<String> getAvailableLoans() {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getAvailableLoanJson();

        Map<String, Object> map = gson.fromJson(rawJson, Map.class);
        String json = gson.toJson(map.get("loans"));
        Loan[] loans = gson.fromJson(json, Loan[].class);
        this.availableLoans = Arrays.asList(loans);

        return new ArrayList<String>();
    }

    public List<Loan> getAvailableLoansList() {
        return this.availableLoans;
    }

    public List<String> takeLoan(String loanType) {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getTakeLoanJson();
        User user = gson.fromJson(rawJson, User.class);
        this.currentToken.setUser(user);

        return new ArrayList<String>();
    }

    public List<String> activeLoans() {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getActiveLoansJson();
        User user = gson.fromJson(rawJson, User.class);
        this.currentToken.setUser(user);

        return new ArrayList<String>();
    }

    public List<String> availableShips(String shipClass) {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getAvailableShipsJson(shipClass);
        Map<String, Object> map = gson.fromJson(rawJson, Map.class);
        String json = gson.toJson(map.get("shipListings"));
        Ship[] ships  = gson.fromJson(json, Ship[].class);
        this.availableShips = Arrays.asList(ships);

        return new ArrayList<String>();
    }

    public List<Ship> getAvailableShips() {
        return this.availableShips;
    }

    public List<String> purchaseShip(String location, String type) {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getPurchaseShip();
        User user = gson.fromJson(rawJson, User.class);
        this.currentToken.setUser(user);

        return new ArrayList<String>();
    }

    public List<String> getUserShips() {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getUserShipsJson();
        User user = gson.fromJson(rawJson, User.class);
        this.currentToken.setUser(user);

        return new ArrayList<String>();
    }

    public List<String> viewMarketPlace(String location) {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getViewMarketPlaceJson(location);
        Map<String, Object> map = gson.fromJson(rawJson, Map.class);
        String json = gson.toJson(map.get("marketplace"));
        Goods[] goods = gson.fromJson(json, Goods[].class);
        this.goods = Arrays.asList(goods);

        return new ArrayList<String>();
    }

    public List<Goods> getGoods() {
        return this.goods;
    }

    public List<String> purchaseGoods(String shipId, String goods, String quantity) {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getPurchaseGoodsJson();
        User user = gson.fromJson(rawJson, User.class);
        this.currentToken.setUser(user);

        return new ArrayList<String>();
    }

    public List<String> sellGoods(String shipId, String goods, String quantity) {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getSellGoodsJson();
        User user = gson.fromJson(rawJson, User.class);
        this.currentToken.setUser(user);

        return new ArrayList<String>();
    }

    public List<String> getShipInfo(String shipId) {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getShipInfoJson(shipId);
        User user = gson.fromJson(rawJson, User.class);
        this.currentToken.setUser(user);

        return new ArrayList<String>();
    }

    public List<String> findNearbyLocations(String type) {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getFindNearbyLocationsJson(type);
        Map<String, Object> map = gson.fromJson(rawJson, Map.class);
        String json = gson.toJson(map.get("locations"));
        Location[] locations = gson.fromJson(json, Location[].class);
        this.locations = Arrays.asList(locations);

        return new ArrayList<String>();
    }

    public List<Location> getLocations() {
        return this.locations;
    }

    public List<String> createFlightPlan(String shipId, String destination) {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getCreateFlightPlanJson();
        Map<String, Object> map = gson.fromJson(rawJson, Map.class);
        String json = gson.toJson(map.get("flightPlan"));
        FlightPlan flightPlan = gson.fromJson(json, FlightPlan.class);
        this.flightPlan = flightPlan;

        return new ArrayList<String>();
    }

    public FlightPlan getFlightPlan() {
        return this.flightPlan;
    }

    public List<String> viewFlightPlan(String flightId) {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getViewFlightPlanJson();
        Map<String, Object> map = gson.fromJson(rawJson, Map.class);
        String json = gson.toJson(map.get("flightPlan"));
        FlightPlan flightPlan = gson.fromJson(json, FlightPlan.class);
        this.flightPlan = flightPlan;

        return new ArrayList<String>();
    }

    public List<String> checkServerStatus() {
        Gson gson = new Gson();

        String rawJson = this.dummyAPI.getCheckServerStatus();
        Status status = gson.fromJson(rawJson, Status.class);
        this.serverStatus = status;

        return new ArrayList<String>();
    }

    public Status getServerStatus() {
        return this.serverStatus;
    }
}
