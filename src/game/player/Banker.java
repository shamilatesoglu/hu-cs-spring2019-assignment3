package game.player;

public class Banker extends Person {
    public Banker(String name, double money) {
        super(name, money);
    }

    @Override
    public String toString() {
        return String.format("%s\t%.0f", getName(), getMoney());
    }
}
