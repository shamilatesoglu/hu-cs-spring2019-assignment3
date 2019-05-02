package game.properties;

import game.GameFlow;

public class Company extends Property {

    public Company(String name, double cost) {
        super(name, cost);
    }

    @Override
    public double getRent() {
        return 4 * GameFlow.getLastDice();
    }
}
