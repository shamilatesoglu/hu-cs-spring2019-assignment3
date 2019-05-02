package game.exceptions;

public class OutOfMoneyException extends RuntimeException {
    public OutOfMoneyException(String name) {
        super(String.format("%s is out of money.", name));
    }
}
