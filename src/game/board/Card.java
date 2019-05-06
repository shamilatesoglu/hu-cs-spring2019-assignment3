package game.board;

public class Card {
    private Type mType;
    private String mContent;

    public Card(Type type, String content) {
        mType = type;
        mContent = content;
    }

    public String getContent() {
        return mContent;
    }

    public Type getType() {
        return mType;
    }

    public enum Type {
        CHANCE, COMMUNITY_CHEST
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", getContent(), getType());
    }
}
