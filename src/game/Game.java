package game;

import game.board.Board;
import game.player.Banker;
import game.player.Player;
import game.properties.Property;

import java.util.ArrayList;

public class Game {
    private Board mBoard;
    private Banker mBanker;
    private ArrayList<Player> mPlayers;

    private ArrayList<Property> mProperties;

    private static Game INSTANCE;

    public static Game getInstance() {
        return (INSTANCE == null) ? INSTANCE = new Game() : INSTANCE;
    }

    private Game() {
        mBoard = new Board();
        mBanker = new Banker("Banker", 100000);
        mPlayers = new ArrayList<>();
        mPlayers.add(new Player("Player 1", 15000, 1));
        mPlayers.add(new Player("Player 2", 15000, 1));
        mProperties = new ArrayList<>();
    }

    public Player getOtherPlayer(Player player) {
        return mPlayers.stream().filter(otherPlayer -> !otherPlayer.equals(player)).findAny().orElse(null);
    }

    public Board getBoard() {
        return mBoard;
    }

    public Banker getBanker() {
        return mBanker;
    }

    public void addProperty(Property property) {
        mProperties.add(property);
    }

    public Player getPlayer(String name) {
        return mPlayers.stream().filter(player -> player.getName().equals(name)).findFirst().orElse(null);
    }

    public ArrayList<Property> getProperties() {
        return mProperties;
    }

    public Property getPropertyByName(String name) {
        return getProperties().stream().filter((property -> property.getName().equals(name))).findAny().orElse(null);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("-----------------------------------------------------------------------------------------------------------\n");
        for (Player player : mPlayers) {
            builder.append(player).append("\n");
        }
        builder.append(getBanker()).append("\n");
        builder.append(String.format("Winner\t%s", getWinningPlayerName())).append("\n");
        builder.append("-----------------------------------------------------------------------------------------------------------\n");
        return builder.toString();
    }

    private String getWinningPlayerName() {
        return getPlayer("Player 1").getMoney() > getPlayer("Player 2").getMoney() ? "Player 1" : "Player 2";
    }
}
