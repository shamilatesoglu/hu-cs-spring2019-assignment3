package game.properties;

import game.Game;
import game.GameFlow;

public class RailRoad extends Property {
    public RailRoad(String name, double cost) {
        super(name, cost);
    }

    @Override
    public double getRent() {
        return Game.getInstance()
                .getOtherPlayer(GameFlow.getLastPlayer())
                .getOwnedProperties().stream()
                .filter(property -> property instanceof RailRoad).toArray().length * 25;
    }
}
