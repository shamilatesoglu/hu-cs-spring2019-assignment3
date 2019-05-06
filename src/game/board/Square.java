package game.board;

public class Square {
    private int mPlace;
    private String mName;
    private Type mSquareType;

    public Square(int place, String name, Type type) {
        setPlace(place);
        setName(name);
        setType(type);
    }

    public Type getType() {
        return mSquareType;
    }

    public void setType(Type type) {
        mSquareType = type;
    }

    public int getPlace() {
        return mPlace;
    }

    public void setPlace(int place) {
        mPlace = place;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public enum Type {
        PROPERTY_SQUARE, ACTION_SQUARE, TAX_SQUARE, GO_SQUARE, GO_TO_JAIL_SQUARE, FREE_PARKING_SQUARE, JAIL_SQUARE
    }

    @Override
    public String toString() {
        return String.format("%d. %s", getPlace(), getName());
    }
}
