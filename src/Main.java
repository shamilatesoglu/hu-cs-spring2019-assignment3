import game.Game;
import game.GameBuilder;
import game.GameFlow;
import game.exceptions.GameOver;
import game.exceptions.OutOfMoneyException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static final String PROPERTY_FILENAME = "build/production/assignment3/property.json";
    public static final String LIST_FILENAME = "build/production/assignment3/list.json";
    public static final String COMMANDS_FILENAME = "build/production/assignment3/command.txt";

    public static void main(String[] args) {
        try {
            Game game = new GameBuilder(PROPERTY_FILENAME, LIST_FILENAME).build();
            GameFlow gameFlow = new GameFlow(game, COMMANDS_FILENAME);
            gameFlow.startGame();
        }  catch (GameOver gameOver) {
            System.out.print(Game.getInstance());
        } catch (IOException | ParseException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
