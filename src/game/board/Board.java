package game.board;

import java.util.ArrayList;

public class Board {
    private Square[] mSquares;
    private ArrayList<Card> mCommunityChestCards;
    private ArrayList<Card> mChanceCards;

    public Board() {
        mSquares = new Square[40];
        mCommunityChestCards = new ArrayList<>();
        mChanceCards = new ArrayList<>();
    }

    public void addSquare(Square square) {
        mSquares[square.getPlace() - 1] = square;
    }

    public Square getSquare(int place) {
        return mSquares[place - 1];
    }

    public void addCard(Card card) {
        switch (card.getType()) {
            case COMMUNITY_CHEST:
                mCommunityChestCards.add(card);
                break;
            case CHANCE:
                mChanceCards.add(card);
                break;
        }
    }

    public Card getCard(Card.Type type) {
        switch (type) {
            case CHANCE:
                Card card = mChanceCards.get(0);
                mChanceCards.remove(0);
                mChanceCards.add(mChanceCards.size(), card);
                return card;

            case COMMUNITY_CHEST:
                Card cardC = mCommunityChestCards.get(0);
                mCommunityChestCards.remove(0);
                mCommunityChestCards.add(mCommunityChestCards.size(), cardC);
                return cardC;

        }
        return null;
    }
}
