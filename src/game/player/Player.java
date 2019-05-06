package game.player;

import game.Game;
import game.properties.Property;

import java.util.ArrayList;

public class Player extends Person {
    private int mPlaceOnBoard;
    private boolean mInJail;
    private boolean mIsInFreeParking;
    private int mDaysInJail;
    private ArrayList<Property> mOwnedProperties;

    public Player(String name, double money, int placeOnBoard) {
        super(name, money);
        setPlaceOnBoard(placeOnBoard);
        mOwnedProperties = new ArrayList<>();
    }

    public boolean incrementDaysInJail() {
        mDaysInJail++;
        if (mDaysInJail > 3) {
            setInJail(false);
            mDaysInJail = 0;
            return true;
        }
        return false;
    }

    public boolean isInFreeParking() {
        return mIsInFreeParking;
    }

    public void setInFreeParking(boolean inFreeParking) {
        mIsInFreeParking = inFreeParking;
    }

    public int getPlaceOnBoard() {
        return mPlaceOnBoard;
    }

    public void setPlaceOnBoard(int placeOnBoard) {
        mPlaceOnBoard = placeOnBoard;
    }

    public void advance(int dice, boolean forward) {
        if (((getPlaceOnBoard() + dice) > 40 || (forward && dice < 0)) && !isInJail())
            requestMoneyFrom(Game.getInstance().getBanker(), 200);
        setPlaceOnBoard((getPlaceOnBoard() + dice == 40) ? 40 : Math.floorMod(getPlaceOnBoard() + dice, 40));
    }

    public boolean isInJail() {
        return mInJail;
    }

    public int getDaysInJail() {
        return mDaysInJail;
    }

    public void setInJail(boolean inJail) {
        mInJail = inJail;
    }

    public ArrayList<Property> getOwnedProperties() {
        return mOwnedProperties;
    }

    public void buyProperty(Property property) {
        Game.getInstance().getBanker().requestMoneyFrom(this, property.getCost());
        property.setOwner(this);
        mOwnedProperties.add(property);
    }

    private String getPropertiesString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getOwnedProperties().size(); i++) {
            Property property = getOwnedProperties().get(i);
            builder.append(property.getName());
            if (i != getOwnedProperties().size() - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return String.format("%s\t%.0f\thas: %s", getName(), getMoney(), getPropertiesString());
    }
}


