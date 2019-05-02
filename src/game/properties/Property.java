package game.properties;

import game.player.Player;

public abstract class Property {
    private String mName;
    private double mCost;
    private Player mOwner;

    public Property(String name, double cost) {
        setName(name);
        setCost(cost);
    }

    public void setOwner(Player player) {
        mOwner = player;
    }

    public Player getOwner() {
        return mOwner;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getCost() {
        return mCost;
    }

    public void setCost(double cost) {
        mCost = cost;
    }

    public abstract double getRent();
}
