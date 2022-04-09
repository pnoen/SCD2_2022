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
        String json = "{\"ships\":[{\"id\":\"cl1qfrc7y54362315s61enkjciq\",\"location\":\"OE-PM-TR\",\"x\":-20,\"y\":5,\"cargo\":[{\"good\":\"FUEL\",\"" +
                "quantity\":16,\"totalVolume\":16},{\"good\":\"CONSTRUCTION_MATERIALS\",\"quantity\":25,\"totalVolume\":25}],\"spaceAvailable\"" +
                ":9,\"type\":\"JW-MK-I\",\"class\":\"MK-I\",\"maxCargo\":50,\"loadingSpeed\":25,\"speed\":1,\"manufacturer\":\"Jackshaw\",\"plating\"" +
                ":5,\"weapons\":5},{\"id\":\"cl1fi4kmlkj362315s92rtyjciq\",\"flightPlanId\":\"cl1r9093365730415s62lr5q06e\",\"x\":-1,\"y\":30,\"cargo\"" +
                ":[],\"spaceAvailable\":50,\"type\":\"JW-MK-I\",\"class\":\"MK-I\",\"maxCargo\":50,\"loadingSpeed\":25,\"speed\":1,\"manufacturer\":\"" +
                "Jackshaw\",\"plating\":5,\"weapons\":5},{\"id\":\"cl1ei4koids082315s92rtyjpoq\",\"location\":\"OE-UC-OB\",\"x\":-18,\"y\":20,\"cargo" +
                "\":[{\"good\":\"FUEL\",\"quantity\":2,\"totalVolume\":2}],\"spaceAvailable\":48,\"type\":\"GR-MK-III\",\"class\":\"MK-III\",\"maxCargo" +
                "\":50,\"loadingSpeed\":25,\"speed\":1,\"manufacturer\":\"Peter\",\"plating\":2,\"weapons\":100}]}";
        return json;
    }

    public String getViewMarketPlaceJson(String location) {
        String json = "";

        if (location.equals("OE-PM-TR")) {
            json = "{\"marketplace\":[{\"symbol\":\"METALS\",\"volumePerUnit\":1,\"pricePerUnit\":3,\"spread\":1,\"purchasePricePerUnit\":4,\"" +
                    "sellPricePerUnit\":2,\"quantityAvailable\":90821},{\"symbol\":\"CONSTRUCTION_MATERIALS\",\"volumePerUnit\":1,\"pricePerUnit" +
                    "\":102,\"spread\":3,\"purchasePricePerUnit\":105,\"sellPricePerUnit\":99,\"quantityAvailable\":21313},{\"symbol\":\"" +
                    "FUSION_REACTORS\",\"volumePerUnit\":6,\"pricePerUnit\":20400,\"spread\":196,\"purchasePricePerUnit\":20596,\"sellPricePerUnit" +
                    "\":20204,\"quantityAvailable\":0},{\"symbol\":\"FUEL\",\"volumePerUnit\":1,\"pricePerUnit\":3,\"spread\":1,\"purchasePricePerUnit" +
                    "\":4,\"sellPricePerUnit\":2,\"quantityAvailable\":86134}]}";
        }
        else if (location.equals("OE-UC-OB")) {
            json = "{\"marketplace\":[{\"symbol\":\"FUEL\",\"volumePerUnit\":1,\"pricePerUnit\":30,\"spread\":1,\"purchasePricePerUnit\":31,\"" +
                    "sellPricePerUnit\":29,\"quantityAvailable\":25478},{\"symbol\":\"CHEMICALS\",\"volumePerUnit\":1,\"pricePerUnit\":5,\"spread" +
                    "\":1,\"purchasePricePerUnit\":6,\"sellPricePerUnit\":4,\"quantityAvailable\":94963},{\"symbol\":\"RARE_METALS\",\"" +
                    "volumePerUnit\":1,\"pricePerUnit\":137,\"spread\":7,\"purchasePricePerUnit\":144,\"sellPricePerUnit\":130,\"" +
                    "quantityAvailable\":7065},{\"symbol\":\"DRONES\",\"volumePerUnit\":1,\"pricePerUnit\":68,\"spread\":2,\"purchasePricePerUnit" +
                    "\":70,\"sellPricePerUnit\":66,\"quantityAvailable\":1854}]}";
        }

        return json;
    }

    public String getPurchaseGoodsJson() {
        String json = "{\"credits\":25000,\"order\":{\"good\":\"FUEL\",\"quantity\":15,\"pricePerUnit\":3,\"total\":45},\"ship\":{\"id\":\"" +
                "cl1qfrc7y54362315s61enkjciq\",\"location\":\"OE-PM-TR\",\"x\":-20,\"y\":5,\"cargo\":[{\"good\":\"CONSTRUCTION_MATERIALS\",\"" +
                "quantity\":16,\"totalVolume\":16},{\"good\":\"FUEL\",\"quantity\":25,\"totalVolume\":25}],\"spaceAvailable\":9,\"type\":\"" +
                "JW-MK-I\",\"class\":\"MK-I\",\"maxCargo\":50,\"loadingSpeed\":25,\"speed\":1,\"manufacturer\":\"Jackshaw\",\"plating\":5,\"" +
                "weapons\":5}}";
        return json;
    }

    public String getSellGoodsJson() {
        String json = "{\"credits\":103515,\"order\":{\"good\":\"CONSTRUCTION_MATERIALS\",\"quantity\":5,\"pricePerUnit\":106,\"total\":530}," +
                "\"ship\":{\"id\":\"cl1qfrc7y54362315s61enkjciq\",\"location\":\"OE-PM-TR\",\"x\":-20,\"y\":5,\"cargo\":[{\"good\":\"FUEL\"," +
                "\"quantity\":16,\"totalVolume\":16},{\"good\":\"CONSTRUCTION_MATERIALS\",\"quantity\":20,\"totalVolume\":20}],\"spaceAvailable" +
                "\":14,\"type\":\"JW-MK-I\",\"class\":\"MK-I\",\"maxCargo\":50,\"loadingSpeed\":25,\"speed\":1,\"manufacturer\":\"Jackshaw\"," +
                "\"plating\":5,\"weapons\":5}}";
        return json;
    }

    public String getShipInfoJson(String shipId) {
        String json = "{\"ships\":[{\"id\":\"cl1qfrc7y54362315s61enkjciq\",\"location\":\"OE-PM-TR\",\"x\":-20,\"y\":5,\"cargo\":[{\"good\":\"FUEL" +
                "\",\"quantity\":16,\"totalVolume\":16},{\"good\":\"CONSTRUCTION_MATERIALS\",\"quantity\":25,\"totalVolume\":25}],\"spaceAvailable" +
                "\":9,\"type\":\"JW-MK-I\",\"class\":\"MK-I\",\"maxCargo\":50,\"loadingSpeed\":25,\"speed\":1,\"manufacturer\":\"Jackshaw\",\"" +
                "plating\":5,\"weapons\":5},{\"id\":\"cl1ei4koids082315s92rtyjpoq\",\"location\":\"OE-UC-OB\",\"x\":-18,\"y\":20,\"cargo\":" +
                "[{\"good\":\"FUEL\",\"quantity\":2,\"totalVolume\":2}],\"spaceAvailable\":48,\"type\":\"GR-MK-III\",\"class\":\"MK-III\",\"maxCargo" +
                "\":50,\"loadingSpeed\":25,\"speed\":1,\"manufacturer\":\"Peter\",\"plating\":2,\"weapons\":100}]}";
        if (shipId.equals("cl1qfrc7y54362315s61enkjciq")) {
            json = "{\"ship\":{\"id\":\"cl1qfrc7y54362315s61enkjciq\",\"location\":\"OE-PM-TR\",\"x\":-20,\"y\":5,\"cargo\":[{\"good\":\"FUEL\",\"" +
                    "quantity\":16,\"totalVolume\":16},{\"good\":\"CONSTRUCTION_MATERIALS\",\"quantity\":25,\"totalVolume\":25}],\"spaceAvailable" +
                    "\":9,\"type\":\"JW-MK-I\",\"class\":\"MK-I\",\"maxCargo\":50,\"loadingSpeed\":25,\"speed\":1,\"manufacturer\":\"Jackshaw\",\"" +
                    "plating\":5,\"weapons\":5}}";
        }
        else if (shipId.equals("cl1ei4koids082315s92rtyjpoq")) {
            json = "{\"ship\":{\"id\":\"cl1ei4koids082315s92rtyjpoq\",\"location\":\"OE-UC-OB\",\"x\":-18,\"y\":20,\"cargo\":[{\"good\":\"FUEL\",\"" +
                    "quantity\":2,\"totalVolume\":2}],\"spaceAvailable\":48,\"type\":\"GR-MK-III\",\"class\":\"MK-III\",\"maxCargo\":50,\"" +
                    "loadingSpeed\":25,\"speed\":1,\"manufacturer\":\"Peter\",\"plating\":2,\"weapons\":100}}";
        }
        return json;
    }

    public String getFindNearbyLocationsJson(String type) {
        String json = "{\"locations\":[{\"symbol\":\"OE-PM\",\"type\":\"PLANET\",\"name\":\"Prime\",\"x\":-19,\"y\":3,\"allowsConstruction\":false," +
                "\"traits\":[\"METAL_ORES\",\"SOME_NATURAL_CHEMICALS\"]},{\"symbol\":\"OE-PM-TR\",\"type\":\"MOON\",\"name\":\"Tritus\",\"x\":-20," +
                "\"y\":5,\"allowsConstruction\":false,\"traits\":[\"NATURAL_CHEMICALS\"]},{\"symbol\":\"OE-CR\",\"type\":\"PLANET\",\"name\":\"Carth" +
                "\",\"x\":4,\"y\":-13,\"allowsConstruction\":false,\"traits\":[\"METAL_ORES\",\"NATURAL_CHEMICALS\",\"ABUNDANT_RARE_METAL_ORES\",\"" +
                "ABUNDANT_TECHNOLOGICAL_RUINS\"]},{\"symbol\":\"OE-KO\",\"type\":\"PLANET\",\"name\":\"Koria\",\"x\":-49,\"y\":1,\"allowsConstruction" +
                "\":false,\"traits\":[\"SOME_NATURAL_CHEMICALS\",\"SOME_RARE_METAL_ORES\"]},{\"symbol\":\"OE-UC-AD\",\"type\":\"MOON\",\"name\":\"" +
                "Ado\",\"x\":-15,\"y\":-73,\"allowsConstruction\":false,\"traits\":[\"SOME_METAL_ORES\",\"SOME_RARE_METAL_ORES\",\"TECHNOLOGICAL_RUINS" +
                "\"]},{\"symbol\":\"OE-NY\",\"type\":\"ASTEROID\",\"name\":\"Nyon\",\"x\":43,\"y\":-46,\"allowsConstruction\":true,\"traits\":[\"" +
                "SOME_NATURAL_CHEMICALS\",\"RARE_METAL_ORES\"]},{\"symbol\":\"OE-BO\",\"type\":\"GAS_GIANT\",\"name\":\"Bo\",\"x\":-59,\"y\":60,\"" +
                "allowsConstruction\":true,\"traits\":[\"SOME_HELIUM_3\",\"TECHNOLOGICAL_RUINS\"]},{\"symbol\":\"OE-W-XV\",\"type\":\"WORMHOLE" +
                "\",\"name\":\"Wormhole\",\"x\":5,\"y\":-101,\"allowsConstruction\":false,\"traits\":[],\"messages\":[\"Research has revealed " +
                "a partially functioning warp gate harnessing the power of an unstable.\",\"Enter at your own risk.\",\"POST " +
                "https://api.spacetraders.io/structures/:structureId/deposit shipId=:shipId good=:goodSymbol quantity=:quantity\"]}]}";
        if (type.equals("PLANET")) {
            json = "{\"locations\":[{\"symbol\":\"OE-PM\",\"type\":\"PLANET\",\"name\":\"Prime\",\"x\":-19,\"y\":3,\"allowsConstruction\":false,\"traits\"" +
                    ":[\"METAL_ORES\",\"SOME_NATURAL_CHEMICALS\"]},{\"symbol\":\"OE-CR\",\"type\":\"PLANET\",\"name\":\"Carth\",\"x\":4,\"y\":-13,\"" +
                    "allowsConstruction\":false,\"traits\":[\"METAL_ORES\",\"NATURAL_CHEMICALS\",\"ABUNDANT_RARE_METAL_ORES\",\"ABUNDANT_TECHNOLOGICAL_RUINS" +
                    "\"]},{\"symbol\":\"OE-KO\",\"type\":\"PLANET\",\"name\":\"Koria\",\"x\":-49,\"y\":1,\"allowsConstruction\":false,\"traits\":[\"" +
                    "SOME_NATURAL_CHEMICALS\",\"SOME_RARE_METAL_ORES\"]}]}";
        }
        else if (type.equals("MOON")) {
            json = "{\"locations\":[{\"symbol\":\"OE-PM-TR\",\"type\":\"MOON\",\"name\":\"Tritus\",\"x\":-20,\"y\":5,\"allowsConstruction\":false,\"traits" +
                    "\":[\"NATURAL_CHEMICALS\"]},{\"symbol\":\"OE-UC-AD\",\"type\":\"MOON\",\"name\":\"Ado\",\"x\":-15,\"y\":-73,\"allowsConstruction\":false" +
                    ",\"traits\":[\"SOME_METAL_ORES\",\"SOME_RARE_METAL_ORES\",\"TECHNOLOGICAL_RUINS\"]}]}";
        }
        else if (type.equals("ASTEROID")) {
            json = "{\"locations\":[{\"symbol\":\"OE-NY\",\"type\":\"ASTEROID\",\"name\":\"Nyon\",\"x\":43,\"y\":-46,\"allowsConstruction\":true,\"traits\":[" +
                    "\"SOME_NATURAL_CHEMICALS\",\"RARE_METAL_ORES\"]}]}";
        }
        else if (type.equals("GAS_GIANT")) {
            json = "{\"locations\":[{\"symbol\":\"OE-BO\",\"type\":\"GAS_GIANT\",\"name\":\"Bo\",\"x\":-59,\"y\":60,\"allowsConstruction\":true,\"traits\":[" +
                    "\"SOME_HELIUM_3\",\"TECHNOLOGICAL_RUINS\"]}]}";
        }
        else if (type.equals("WORMHOLE")) {
            json = "{\"locations\":[{\"symbol\":\"OE-W-XV\",\"type\":\"WORMHOLE\",\"name\":\"Wormhole\",\"x\":5,\"y\":-101,\"allowsConstruction\":false,\"" +
                    "traits\":[],\"messages\":[\"Research has revealed a partially functioning warp gate harnessing the power of an unstable.\",\"Enter " +
                    "at your own risk.\",\"POST https://api.spacetraders.io/structures/:structureId/deposit shipId=:shipId good=:goodSymbol quantity=:quantity" +
                    "\"]}]}";
        }
        return json;
    }

    public String getCreateFlightPlanJson() {
        String json = "{\"flightPlan\":{\"id\":\"cl1r9093365730415s62lr5q06e\",\"shipId\":\"cl1fi4kmlkj362315s92rtyjciq\",\"createdAt\":\"" +
                "2022-04-09T03:46:56.599Z\",\"arrivesAt\":\"2022-04-09T03:48:53.596Z\",\"destination\":\"OE-KO\",\"departure\":\"OE-PM-TR\",\"distance" +
                "\":29,\"fuelConsumed\":5,\"fuelRemaining\":11,\"terminatedAt\":null,\"timeRemainingInSeconds\":116}}";
        return json;
    }

    public String getViewFlightPlanJson() {
        String json = "{\"flightPlan\":{\"id\":\"cl1r9093365730415s62lr5q06e\",\"shipId\":\"cl1fi4kmlkj362315s92rtyjciq\",\"createdAt\":\"" +
                "2022-04-09T03:49:26.225Z\",\"arrivesAt\":\"2022-04-09T03:51:23.223Z\",\"destination\":\"OE-KO\",\"departure\":\"OE-PM-TR\",\"distance\"" +
                ":29,\"fuelConsumed\":5,\"fuelRemaining\":11,\"terminatedAt\":null,\"timeRemainingInSeconds\":50}}";
        return json;
    }

    public String getCheckServerStatus() {
        String json = "{\"status\":\"offline dummy api is working :)\"}";
        return json;
    }
}
