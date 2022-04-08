package SpaceTraders.model;

public class DummyAPI {
    public String getRegisterJson() {
        String json = "{\"token\":\"d4d16c42-42c8-4a09-8f19-6ee13a015319\",\"user\":" +
                "{\"username\":\"user298374\",\"credits\":0,\"ships\":[]," +
                "\"loans\":[]}}";
        return json;
    }

    public String getAccountDetailsJson() {
        String json = "{\"user\": {\"username\": \"user298374\",\"shipCount\": 0,\"" +
                "structureCount\": 0,\"joinedAt\": \"2022-04-08T11:13:49.944Z\",\"" +
                "credits\": 0}}";
        return json;
    }

    public String getAvailableLoanJson() {
        String json = "{\"loans\": [{\"type\": \"STARTUP\",\"amount\": 200000," +
                "\"rate\": 40,\"termInDays\": 2,\"collateralRequired\": false}," +
                "{\"type\": \"COOL\",\"amount\": 549700,\"rate\": 11,\"termInDays" +
                "\": 1,\"collateralRequired\": true}]}";
        return json;
    }

    public String getTakeLoanJson() {
        String json = "{\"credits\": 549700,\"loan\": {\"id\": \"" +
                "cl1qekbvr42017615s6f9paautq\",\"due\": \"2022-04-10T12:26:52.070Z" +
                "\",\"repaymentAmount\": 50000000,\"status\": \"CURRENT\",\"type\": " +
                "\"COOL\"}}";
        return json;
    }

    public String getActiveLoansJson() {
        String json = "{\"loans\":[{\"id\":\"cl1qekbvr42017615s6f9paautq\",\"due" +
                "\":\"2022-04-10T12:26:52.070Z\",\"repaymentAmount\":50000000,\"" +
                "status\":\"CURRENT\",\"type\":\"COOL\"}]}";
        return json;
    }

    public String getAvailableShipsJson(String shipClass) {
        String json = "";
        if (shipClass.equals("")) {
            json = "{\"shipListings\":[{\"type\":\"JW-MK-I\",\"class\":\"MK-I\",\"maxCargo\":50,\"loadingSpeed\":25,\"speed\":1,\"manufacturer\":\"" +
                    "Jackshaw\",\"plating\":5,\"weapons\":5,\"purchaseLocations\":[{\"system\":\"OE\",\"location\":\"OE-PM-TR\",\"price\":21125}]},{" +
                    "\"type\":\"JW-MK-II\",\"class\":\"MK-II\",\"maxCargo\":100,\"loadingSpeed\":25,\"speed\":2,\"manufacturer\":\"Jackshaw\",\"" +
                    "plating\":10,\"weapons\":10,\"purchaseLocations\":[{\"system\":\"OE\",\"location\":\"OE-PM-TR\",\"price\":83300}]},{\"" +
                    "type\":\"ZA-MK-III\",\"class\":\"MK-III\",\"maxCargo\":300,\"loadingSpeed\":100,\"speed\":2,\"manufacturer\":\"Zetra\",\"plating\":10," +
                    "\"weapons\":10,\"restrictedGoods\":[\"FUEL\"],\"purchaseLocations\":[{\"system\":\"OE\",\"location\":\"OE-PM-TR\",\"price\":213900}," +
                    "{\"system\":\"OE\",\"location\":\"OE-UC-OB\",\"price\":213900}]}]}";
        }
        else if (shipClass.equals("MK-I")) {
            json = "{\"shipListings\":[{\"type\":\"JW-MK-I\",\"class\":\"MK-I\",\"maxCargo\":50,\"loadingSpeed\":25,\"speed\":1,\"manufacturer\":\"" +
                    "Jackshaw\",\"plating\":5,\"weapons\":5,\"purchaseLocations\":[{\"system\":\"OE\",\"location\":\"OE-PM-TR\",\"price\":21125}]}]}";
        }
        else if (shipClass.equals("MK-II")) {
            json = "{\"shipListings\":[{\"type\":\"JW-MK-II\",\"class\":\"MK-II\",\"maxCargo\":100,\"loadingSpeed\":25,\"speed\":2,\"manufacturer\":\"" +
                    "Jackshaw\",\"plating\":10,\"weapons\":10,\"purchaseLocations\":[{\"system\":\"OE\",\"location\":\"OE-PM-TR\",\"price\":83300}]}]}";
        }
        else if (shipClass.equals("MK-III")) {
            json = "{\"shipListings\":[{\"type\":\"ZA-MK-III\",\"class\":\"MK-III\",\"maxCargo\":300,\"loadingSpeed\":100,\"speed\":2,\"manufacturer" +
                    "\":\"Zetra\",\"plating\":10,\"weapons\":10,\"restrictedGoods\":[\"FUEL\"],\"purchaseLocations\":[{\"system\":\"OE\",\"location\"" +
                    ":\"OE-PM-TR\",\"price\":213900},{\"system\":\"OE\",\"location\":\"OE-UC-OB\",\"price\":213900}]}]}";
        }
        return json;
    }

    public String getPurchaseShip() {
        String json = "{\"credits\":30000,\"ship\":{\"id\":\"cl1qfrc7y54362315s31bnkjciq\",\"location\":\"OE-PM-TR\",\"x\":-20,\"y\":5,\"cargo\":[]," +
                "\"spaceAvailable\":50,\"type\":\"JW-MK-I\",\"class\":\"MK-I\",\"maxCargo\":50,\"loadingSpeed\":25,\"speed\":1,\"manufacturer\":\"" +
                "Jackshaw\",\"plating\":5,\"weapons\":5}}";
        return json;
    }

    public String getUserShipsJson() {
        String json = "";
        return json;
    }

    public String getViewMarketPlaceJson() {
        String json = "";
        return json;
    }

    public String getPurchaseGoodsJson() {
        String json = "";
        return json;
    }

    public String getSellGoodsJson() {
        String json = "";
        return json;
    }

    public String getShipInfoJson() {
        String json = "";
        return json;
    }

    public String getFindNearbyLocationsJson() {
        String json = "";
        return json;
    }

    public String getCreateFlightPlanJson() {
        String json = "";
        return json;
    }

    public String getViewFlightPlanJson() {
        String json = "";
        return json;
    }

    public String getCheckServerStatus() {
        String json = "{\"status\":\"offline dummy api is working :)\"}";
        return json;
    }
}
