import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Player - Represents a Three Card Poker player
 * @author Katie Copeland
 * @version 2024.06.13
 */
public class Player {
    private String name;
    private int bankroll;
    private List<Card> hand;

    /**
     * Default Constructor for Player class
     */
    public Player()
    {
        hand = new ArrayList<Card>();
        name = "Player";
        setBankroll(1000);
    }

    /**
     * Overloaded Constructor for Player Class to allow name and bankroll entry
     * @param name Name of the player
     * @param bankroll Amount of money the player will start with
     */
    public Player(String name, int bankroll)
    {
        hand = new ArrayList<>();
        this.name = name;
        setBankroll(bankroll);
    }

    /**
     * Get a card from the dealer
     * @param card A card from the deck of cards
     * @param isVisible Set the card face-up (true) or face-down (false)
     */
    public void getCardFromDealer(Card card, boolean isVisible)
    {
        if (isVisible) {
            card.show();
        } else {
            card.hide();
        }
        hand.add(card);
    }

    /**
     * Sets the amount of money the player has available to place an ante, must be >= 0
     * @param bankroll the amount of money to set
     */
    public void setBankroll(int bankroll)
    {
        if (bankroll >= 0)
        {
            this.bankroll = bankroll;
        }
    }

    /**
     * Gets the amount of money the player has available to place an ante.
     * @return the player's bankroll of money
     */
    public int getBankroll() {
        return bankroll;
    }

    /**
     * Get the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the players' hand.
     */
    public List<Card> getHand()
    {
        return hand;
    }

    /**
     * Clears the player's hand, removing all cards.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Scores the player's hand according to rules of this version of Three Card Poker
     * @return the rank of the player's hand
     */
    public int scoreHand() {
        if (hand.size() != 3) {
            throw new IllegalStateException("Hand must contain exactly 3 cards.");
        }

        //Sort cards by their value in descending order
        hand.sort(Comparator.comparing(Card::getValue).reversed());

        //Check for any rankings. The order here is important.
        if (isStraightFlush()) {
            return 1000 + calculateCardRankSum();
        } else if (isThreeOfAKind()) {
            return 800 + calculateCardRankSum();
        } else if (isStraight()) {
            return 600 + calculateCardRankSum();
        } else if (isFlush()) {
            return 400 + calculateCardRankSum();
        } else if (isPair()) {
            return 200 + calculateCardRankSum();
        } else {
            return 100 + calculateCardRankSum();
        }
    }

    /**
     * Calculates the sum of card ranks for standard scoring
     * @return sum of card ranks
     */
    private int calculateCardRankSum() {
        return hand.stream()
                .mapToInt(Card::getValue)
                .sum();
    }

    /**
     * Checks if the hand is a Straight Flush.
     * @return true if the hand is a Straight Flush, false otherwise
     */
    protected boolean isStraightFlush() {
        return isStraight() && isFlush();
    }

    /**
     * Checks if the hand is a Three of a Kind.
     * @return true if the hand is a Three of a Kind, false otherwise
     */
    protected boolean isThreeOfAKind() {
        return hand.get(0).getValue() == hand.get(1).getValue() &&
                hand.get(1).getValue() == hand.get(2).getValue();
    }

    /**
     * Checks if the hand is a Straight.
     * @return true if the hand is a Straight, false otherwise
     */
    protected boolean isStraight() {
        hand.sort(Comparator.comparing(Card::getValue));
        int value1 = hand.get(0).getValue();
        int value2 = hand.get(1).getValue();
        int value3 = hand.get(2).getValue();

        // Check for standard consecutive straight
        if (value1 + 1 == value2 && value2 + 1 == value3) {
            return true;
        }

        // Special case for A-2-3 straight
        return (value1 == 2 && value2 == 3 && value3 == 14);
    }

    /**
     * Checks if the hand is a Flush.
     * @return true if the hand is a Flush, false otherwise
     */
    protected boolean isFlush() {
        return hand.get(0).getSuit() == hand.get(1).getSuit() &&
                hand.get(1).getSuit() == hand.get(2).getSuit();
    }

    /**
     * Checks if the hand is a Pair.
     * @return true if the hand is a Pair, false otherwise
     */
    protected boolean isPair() {
        return hand.get(0).getValue() == hand.get(1).getValue() ||
                hand.get(1).getValue() == hand.get(2).getValue() ||
                hand.get(0).getValue() == hand.get(2).getValue();
    }

    /**
     * Shows all cards in the player's hand.
     */
    public void showAllCards() {
        for (Card card : hand) {
            card.show();
        }
    }
}
