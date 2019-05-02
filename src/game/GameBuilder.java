package game;


import game.board.Card;
import game.board.Square;
import game.properties.Company;
import game.properties.Land;
import game.properties.Property;
import game.properties.RailRoad;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

@SuppressWarnings("unchecked")
public class GameBuilder {
    private String mPropertyFilename;
    private String mCardListFilename;

    private Game mGame;

    public GameBuilder(String propertyFilename, String cardListFilename) {
        mPropertyFilename = propertyFilename;
        mCardListFilename = cardListFilename;
        mGame = Game.getInstance();
    }

    private void initProperties() throws IOException, ParseException {
        JSONObject propertiesJSONObject = (JSONObject) new JSONParser().parse(new FileReader(mPropertyFilename));
        for (int i = 1; i < 4; i++) {
            JSONArray landArray = (JSONArray) propertiesJSONObject.get(String.valueOf(i));
            for (JSONObject land : (Iterable<JSONObject>) landArray) {
                String name = (String) land.get("name");
                double cost = Double.valueOf((String) land.get("cost"));
                Property propertyObject = (i == 1) ?
                        new Land(name, cost) : (i == 2) ?
                        new RailRoad(name, cost) :
                        new Company(name, cost);
                mGame.addProperty(propertyObject);
            }
        }
    }

    private void initCards() throws IOException, ParseException {
        JSONObject cardsJSONObject = (JSONObject) new JSONParser().parse(new FileReader(mCardListFilename));

        JSONArray chanceArray = (JSONArray) cardsJSONObject.get("chanceList");
        for (JSONObject chance : (Iterable<JSONObject>) chanceArray) {
            String content = (String) chance.get("item");
            mGame.getBoard().addCard(new Card(Card.Type.CHANCE, content));
        }

        JSONArray communityChestList = (JSONArray) cardsJSONObject.get("communityChestList");
        for (JSONObject communityChest : (Iterable<JSONObject>) communityChestList) {
            String content = (String) communityChest.get("item");
            mGame.getBoard().addCard(new Card(Card.Type.COMMUNITY_CHEST, content));
        }

    }

    private void initBoard() throws IOException, ParseException {
        JSONObject propertiesJSONObject = (JSONObject) new JSONParser().parse(new FileReader(mPropertyFilename));
        for (int i = 1; i < 4; i++) {
            JSONArray landArray = (JSONArray) propertiesJSONObject.get(String.valueOf(i));
            for (JSONObject land : (Iterable<JSONObject>) landArray) {
                int place = Integer.valueOf((String) land.get("id"));
                String name = (String) land.get("name");
                mGame.getBoard().addSquare(new Square(place, name, Square.Type.PROPERTY_SQUARE));
            }
        }
        mGame.getBoard().addSquare(new Square(1, "GO", Square.Type.GO_SQUARE));
        mGame.getBoard().addSquare(new Square(3, "Community Chest", Square.Type.ACTION_SQUARE));
        mGame.getBoard().addSquare(new Square(5, "Income Tax", Square.Type.TAX_SQUARE));
        mGame.getBoard().addSquare(new Square(8, "Chance", Square.Type.ACTION_SQUARE));
        mGame.getBoard().addSquare(new Square(11, "Jail", Square.Type.JAIL_SQUARE));
        mGame.getBoard().addSquare(new Square(18, "Community Chest", Square.Type.ACTION_SQUARE));
        mGame.getBoard().addSquare(new Square(21, "Free Parking", Square.Type.FREE_PARKING_SQUARE));
        mGame.getBoard().addSquare(new Square(23, "Chance", Square.Type.ACTION_SQUARE));
        mGame.getBoard().addSquare(new Square(31, "Go to Jail", Square.Type.GO_TO_JAIL_SQUARE));
        mGame.getBoard().addSquare(new Square(34, "Community Chest", Square.Type.ACTION_SQUARE));
        mGame.getBoard().addSquare(new Square(37, "Chance", Square.Type.ACTION_SQUARE));
        mGame.getBoard().addSquare(new Square(39, "Super Tax", Square.Type.TAX_SQUARE));
    }

    public Game build() throws IOException, ParseException {
        initBoard();
        initProperties();
        initCards();
        return mGame;
    }
}
