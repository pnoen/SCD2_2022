package SpaceTraders.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OnlineGameEngine implements GameEngine{
    private String status;
    private String statusIconColour;
    private Token currentToken;
    private List<Loan> availableLoans;
    private List<Ship> availableShips;
    private List<Goods> goods;
    private List<Location> locations;
    private FlightPlan flightPlan;
    private Status serverStatus;
    private Request request;

    public OnlineGameEngine(Request request) {
        this.status = "Online";
        this.statusIconColour = "#01c629";
        this.availableLoans = new ArrayList<Loan>();
        this.availableShips = new ArrayList<Ship>();
        this.request = request;
    }

    public String getStatus() {
        return this.status;
    }

    public String getStatusIconColour() {
        return this.statusIconColour;
    }

    public List<String> register(String username) {
        String uri = "https://api.spacetraders.io/users/" + username + "/claim";
        List<String> msg = request.postRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                Token token = gson.fromJson(msg.get(2), Token.class);
                this.currentToken = token;
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public List<String> getAccountDetails(String authToken) {
        String uri = "https://api.spacetraders.io/my/account?token=" + authToken;
        List<String> msg = request.getRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                Token token = gson.fromJson(msg.get(2), Token.class);
                token.setToken(authToken);
                this.currentToken = token;
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public void logout() {
        this.currentToken = null;
    }

    public List<String> getAvailableLoans() {
        String authToken = this.currentToken.getToken();
        String uri = "https://api.spacetraders.io/types/loans?token=" + authToken;
        List<String> msg = request.getRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                Map<String, Object> map = gson.fromJson(msg.get(2), Map.class);
                String json = gson.toJson(map.get("loans"));
                Loan[] loans = gson.fromJson(json, Loan[].class);
                this.availableLoans = Arrays.asList(loans);
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public List<Loan> getAvailableLoansList() {
        return this.availableLoans;
    }

    public List<String> takeLoan(String loanType) {
        String authToken = this.currentToken.getToken();
        String uri = "https://api.spacetraders.io/my/loans?token=" + authToken + "&type=" + loanType;
        List<String> msg = request.postRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                User user = gson.fromJson(msg.get(2), User.class);
                this.currentToken.setUser(user);
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public List<String> activeLoans() {
        String authToken = this.currentToken.getToken();
        String uri = "https://api.spacetraders.io/my/loans?token=" + authToken;
        List<String> msg = request.getRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                User user = gson.fromJson(msg.get(2), User.class);
                this.currentToken.setUser(user);
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public List<String> handleErrorReq(String body) {
        Gson gson = new Gson();
        List<String> msg = new ArrayList<String>();
        Map<String, Map<String, Object>> errorMap = gson.fromJson(body, Map.class);

        String code = String.valueOf(errorMap.get("error").get("code"));
        String[] codeClean = code.split("\\.");
        msg.add("Error code: " + codeClean[0]);

        msg.add(String.valueOf(errorMap.get("error").get("message")));
        System.out.println(errorMap.get("error"));
        if (errorMap.get("error").get("data") != null) {
            String data = gson.toJson(errorMap.get("error").get("data"));
            Map<String, List<String>> map = gson.fromJson(data, Map.class);
            if (map.get("type") != null) {
                System.out.println(map.get("type").size());
                for (String type : map.get("type")) {
                    System.out.println(type);
                    msg.add(type);
                }
            }
            if (map.get("class") != null) {
                for (String cl : map.get("class")) {
                    System.out.println(cl);
                    msg.add(cl);
                }
            }
            if (map.get("location") != null) {
                for (String location : map.get("location")) {
                    System.out.println(location);
                    msg.add(location);
                }
            }
            if (map.get("shipId") != null) {
                for (String shipId : map.get("shipId")) {
                    System.out.println(shipId);
                    msg.add(shipId);
                }
            }
            if (map.get("loadingSpeed") != null) {
                String loadingSpeed = String.valueOf(map.get("loadingSpeed"));
                String[] loadingSpeedClean = loadingSpeed.split("\\.");
                msg.add("loadingSpeed: " + loadingSpeedClean[0]);
            }
            if (map.get("good") != null) {
                for (String good : map.get("good")) {
                    System.out.println(good);
                    msg.add(good);
                }
            }
            if (map.get("quantity") != null) {
                for (String quantity : map.get("quantity")) {
                    System.out.println(quantity);
                    msg.add(quantity);
                }
            }
        }
        return msg;
    }

    public List<String> availableShips(String shipClass) {
        String authToken = this.currentToken.getToken();
        String uri = "https://api.spacetraders.io/systems/OE/ship-listings?token=" + authToken;
        if (shipClass.length() != 0) {
            uri += "&class=" + shipClass;
        }
        List<String> msg = request.getRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                Map<String, Object> map = gson.fromJson(msg.get(2), Map.class);
                String json = gson.toJson(map.get("shipListings"));
                Ship[] ships  = gson.fromJson(json, Ship[].class);
                this.availableShips = Arrays.asList(ships);
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public List<Ship> getAvailableShips() {
        return this.availableShips;
    }

    public List<String> purchaseShip(String location, String type) {
        String authToken = this.currentToken.getToken();
        String uri = "https://api.spacetraders.io/my/ships?token=" + authToken + "&location=" + location + "&type=" + type;
        List<String> msg = request.postRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                User user = gson.fromJson(msg.get(2), User.class);
                this.currentToken.setUser(user);
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public List<String> getUserShips() {
        String authToken = this.currentToken.getToken();
        String uri = "https://api.spacetraders.io/my/ships?token=" + authToken;
        List<String> msg = request.getRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                User user = gson.fromJson(msg.get(2), User.class);
                this.currentToken.setUser(user);
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public List<String> viewMarketPlace(String location) {
        String authToken = this.currentToken.getToken();
        String uri = "https://api.spacetraders.io/locations/" + location + "/marketplace?token=" + authToken;
        List<String> msg = request.getRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                Map<String, Object> map = gson.fromJson(msg.get(2), Map.class);
                String json = gson.toJson(map.get("marketplace"));
                Goods[] goods = gson.fromJson(json, Goods[].class);
                this.goods = Arrays.asList(goods);
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public List<Goods> getGoods() {
        return this.goods;
    }

    public List<String> purchaseGoods(String shipId, String goods, String quantity) {
        String authToken = this.currentToken.getToken();
        String uri = "https://api.spacetraders.io/my/purchase-orders?token=" + authToken + "&shipId=" +
                shipId + "&good=" + goods + "&quantity=" + quantity;
        List<String> msg = request.postRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                User user = gson.fromJson(msg.get(2), User.class);
                this.currentToken.setUser(user);
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public List<String> sellGoods(String shipId, String goods, String quantity) {
        String authToken = this.currentToken.getToken();
        String uri = "https://api.spacetraders.io/my/sell-orders?token=" + authToken + "&shipId=" +
                shipId + "&good=" + goods + "&quantity=" + quantity;
        List<String> msg = request.postRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                User user = gson.fromJson(msg.get(2), User.class);
                this.currentToken.setUser(user);
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public List<String> getShipInfo(String shipId) {
        String authToken = this.currentToken.getToken();
        String uri = "https://api.spacetraders.io/my/ships/" + shipId + "?token=" + authToken;
        List<String> msg = request.getRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                User user = gson.fromJson(msg.get(2), User.class);
                this.currentToken.setUser(user);
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public List<String> findNearbyLocations(String type) {
        String authToken = this.currentToken.getToken();
        String uri = "https://api.spacetraders.io/systems/OE/locations?token=" + authToken;
        if (type.length() != 0) {
            uri += "&type=" + type;
        }
        List<String> msg = request.getRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                Map<String, Object> map = gson.fromJson(msg.get(2), Map.class);
                String json = gson.toJson(map.get("locations"));
                Location[] locations = gson.fromJson(json, Location[].class);
                this.locations = Arrays.asList(locations);
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public List<Location> getLocations() {
        return this.locations;
    }

    public List<String> createFlightPlan(String shipId, String destination) {
        String authToken = this.currentToken.getToken();
        String uri = "https://api.spacetraders.io/my/flight-plans?token=" + authToken + "&shipId=" +
                shipId + "&destination=" + destination;
        List<String> msg = request.postRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                Map<String, Object> map = gson.fromJson(msg.get(2), Map.class);
                String json = gson.toJson(map.get("flightPlan"));
                FlightPlan flightPlan = gson.fromJson(json, FlightPlan.class);
                this.flightPlan = flightPlan;
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public FlightPlan getFlightPlan() {
        return this.flightPlan;
    }

    public List<String> viewFlightPlan(String flightId) {
        String authToken = this.currentToken.getToken();
        String uri = "https://api.spacetraders.io/my/flight-plans/" + flightId + "?token=" + authToken;
        List<String> msg = request.getRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                Map<String, Object> map = gson.fromJson(msg.get(2), Map.class);
                String json = gson.toJson(map.get("flightPlan"));
                FlightPlan flightPlan = gson.fromJson(json, FlightPlan.class);
                this.flightPlan = flightPlan;
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public List<String> checkServerStatus() {
        String uri = "https://api.spacetraders.io/game/status";
        List<String> msg = request.getRequest(uri);

        if (msg.size() == 3) {
            int statusCode = Integer.parseInt(msg.get(0));
            System.out.println("Response status code was: " + statusCode);
//            System.out.println("Response headers were: " + response.headers());
            System.out.println("Response body was:\n" + msg.get(2));
            if (statusCode >= 200 && statusCode < 300) {
                Gson gson = new Gson();
                Status status = gson.fromJson(msg.get(2), Status.class);
                this.serverStatus = status;
                msg.clear();
            }
            else if (statusCode >= 400 && statusCode < 500) {
                msg = handleErrorReq(msg.get(2));
            }
        }
        return msg;
    }

    public Status getServerStatus() {
        return this.serverStatus;
    }
}
