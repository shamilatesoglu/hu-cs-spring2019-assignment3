package game;

import game.board.Card;
import game.board.Square;
import game.exceptions.OutOfMoneyException;
import game.player.Banker;
import game.player.Player;
import game.properties.Property;

import java.io.*;

public class GameFlow {

    private static int sLastDice;
    private static Player sLastPlayer;
    private Game mGame;
    private String mCommandsFilename;

    public GameFlow(Game game, String commandsFilename) {
        mGame = game;
        mCommandsFilename = commandsFilename;
    }

    public static void setLastDice(int dice) {
        sLastDice = dice;
    }

    public static int getLastDice() {
        return sLastDice;
    }

    public static Player getLastPlayer() {
        return sLastPlayer;
    }

    public static void setLastPlayer(Player lastPlayer) {
        sLastPlayer = lastPlayer;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void
    startGame() throws IOException {
        File file = new File("myoutput.txt");
        if (!file.exists()) file.createNewFile();
        System.setOut(new PrintStream("myoutput.txt"));
        BufferedReader bufferedReader = new BufferedReader(new FileReader(mCommandsFilename));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            try {
                if (line.length() > 0)
                    this.executeCommand(line);
            } catch (OutOfMoneyException ome) {
                //
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.print(mGame);

        bufferedReader.close();
    }

    private void executeCommand(String command) {
        switch (command.trim()) {
            case "show()":
                System.out.print(mGame);
                break;
            default:
                String[] turn = command.split(";");
                String name = turn[0];
                int dice = Integer.valueOf(turn[1]);
                rollDice(name, dice);
        }
    }

    private void rollDice(String playerName, int dice) {
        Player player = mGame.getPlayer(playerName);
        setLastDice(dice);
        setLastPlayer(player);
        if (!player.isInJail() && !player.isInFreeParking()) {
            player.advance(dice);
            play(player);
        } else if (player.isInJail()) {
            player.incrementDaysInJail();
            printCurrentTurn(player, String.format("%s is in jail (count=%d)", player.getName(), player.getDaysInJail()));
        } else {
            player.setInFreeParking(false);
        }
    }

    private void play(Player player) {
        Square square = mGame.getBoard().getSquare(player.getPlaceOnBoard());
        switch (square.getType()) {
            case PROPERTY_SQUARE:
                Property property = mGame.getPropertyByName(square.getName());
                if (property.getOwner() == null) {
                    player.buyProperty(property);
                    printCurrentTurn(player, String.format("%s bought %s", player.getName(), property.getName()));
                } else {
                    property.getOwner().requestMoneyFrom(player, property.getRent());
                    printCurrentTurn(player, String.format("%s paid rent for %s", player.getName(), property.getName()));
                }
                break;
            case ACTION_SQUARE:
                Card card = mGame.getBoard().getCard(square.getName().equals("Chance") ? Card.Type.CHANCE : Card.Type.COMMUNITY_CHEST);
                playCard(card, player);
                break;
            case TAX_SQUARE:
                getBanker().requestMoneyFrom(player, 100);
                printCurrentTurn(player, String.format("%s paid tax", player.getName()));
                break;
            case JAIL_SQUARE:
                player.setInJail(true);
                printCurrentTurn(player, String.format("%s went to jail", player.getName()));
                break;
            case GO_TO_JAIL_SQUARE:
                player.advance(20);
                player.setInJail(true);
                printCurrentTurn(player, String.format("%s went to jail", player.getName()));
                break;
            case FREE_PARKING_SQUARE:
                player.setInFreeParking(true);
                printCurrentTurn(player, String.format("%s is in Free Parking", player.getName()));
                break;
        }
    }

    private void playCard(Card card, Player player) {
        switch (card.getContent()) {
            case "Advance to Go (Collect $200)":
                player.advance(41 - player.getPlaceOnBoard());
                printCurrentTurn(player, String.format("%s advanced to go (collect 200)", player.getName()));
                break;
            case "Advance to Leicester Square":
                player.advance(27 - player.getPlaceOnBoard());
                play(player);
                break;
            case "Go back 3 spaces":
                player.advance(-3);
                play(player);
                break;
            case "Pay poor tax of $15":
                getBanker().requestMoneyFrom(player, 15);
                printCurrentTurn(player, String.format("%s drew Chance Card [Pay poor tax of $15]", player.getName()));
                break;
            case "Your building loan matures - collect $150":
                player.requestMoneyFrom(getBanker(), 150);
                printCurrentTurn(player, String.format("%s drew Chance Card [Your building loan matures - collect $150]", player.getName()));
                break;
            case "You have won a crossword competition - collect $100":
                player.requestMoneyFrom(getBanker(), 100);
                printCurrentTurn(player, String.format("%s drew Chance Card [You have won a crossword competition - collect $100]", player.getName()));
                break;
            case "Bank error in your favor - collect $75":
                player.requestMoneyFrom(getBanker(), 75);
                printCurrentTurn(player, String.format("%s drew Community Chest Card [Bank error in your favor - collect $75]", player.getName()));
                break;
            case "Doctor's fees - Pay $50":
                getBanker().requestMoneyFrom(player, 50);
                printCurrentTurn(player, String.format("%s drew Community Chest Card [Doctor's fees - Pay $50]", player.getName()));
                break;
            case "It is your birthday Collect $10 from each player":
                player.requestMoneyFrom(mGame.getOtherPlayer(player), 10);
                printCurrentTurn(player, String.format("%s drew Community Chest Card [It is your birthday Collect $10 from each player]", player.getName()));
                break;
            case "Grand Opera Night - collect $50 from every player for opening night seats":
                player.requestMoneyFrom(mGame.getOtherPlayer(player), 50);
                printCurrentTurn(player, String.format("%s drew Community Chest Card [Grand Opera Night - collect $50 from every player for opening night seats]", player.getName()));
                break;
            case "Income Tax refund - collect $20":
                player.requestMoneyFrom(getBanker(), 20);
                printCurrentTurn(player, String.format("%s drew Community Chest Card [Income Tax refund - collect $20]", player.getName()));
                break;
            case "Life Insurance Matures - collect $100":
                player.requestMoneyFrom(getBanker(), 100);
                printCurrentTurn(player, String.format("%s drew Community Chest Card [Life Insurance Matures - collect $100]", player.getName()));
                break;
            case "Pay Hospital Fees of $100":
                getBanker().requestMoneyFrom(player, 100);
                printCurrentTurn(player, String.format("%s drew Community Chest Card [Pay Hospital Fees of $100]", player.getName()));
                break;
            case "Pay School Fees of $50":
                getBanker().requestMoneyFrom(player, 50);
                printCurrentTurn(player, String.format("%s drew Community Chest Card [Pay School Fees of $50]", player.getName()));
                break;
            case "You inherit $100":
                player.requestMoneyFrom(getBanker(), 100);
                printCurrentTurn(player, String.format("%s drew Community Chest Card [You inherit $100]", player.getName()));
                break;
            case "From sale of stock you get $50":
                player.requestMoneyFrom(getBanker(), 50);
                printCurrentTurn(player, String.format("%s drew Community Chest Card [From sale of stock you get $50]", player.getName()));
                break;
        }
    }

    private Banker getBanker() {
        return mGame.getBanker();
    }

    private void printCurrentTurn(Player player, String operation) {
        System.out.println(String.format("%s\t%d\t%d\t%.0f\t%.0f\t%s", player.getName(),
                getLastDice(), player.getPlaceOnBoard(), mGame.getPlayer("Player 1").getMoney(),
                mGame.getPlayer("Player 2").getMoney(), operation));
    }
}
