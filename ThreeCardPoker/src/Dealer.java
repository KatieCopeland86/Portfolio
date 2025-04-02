/**
 * Dealer - Represents a Three Card Poker dealer
 * @author Katie Copeland
 * @version 2024.06.13
 */
public class Dealer extends Player{
    private Deck deck;

    /**
     * Constructor for objects of class Dealer
     * @param bankroll Amount of money the dealer starts with
     */
    public Dealer(int bankroll)
    {
        super("Dealer", bankroll);
        this.deck = new Deck();
    }

    /**
     * Deal a card from the deck
     * @return Card a card from the deck
     */
    public Card deal()
    {
        return deck.deal();
    }

    /**
     * Replaces the deck with a newly shuffled 52 card deck
     */
    public void resetDeck()
    {
        deck = new Deck();
    }
}
