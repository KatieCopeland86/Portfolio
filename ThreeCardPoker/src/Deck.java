import java.util.ArrayList;
import java.util.Collections;

/**
 * Deck - Represents a standard deck of playing cards
 * @author Katie Copeland
 * @version 2024.06.13
 */
public class Deck {
    private ArrayList<Card> playingCards = new ArrayList<>(52);
    /**
     * Constructor for the Deck class.
     * Creates a shuffled 52 card deck.
     */
    public Deck()
    {
        createDeck();
        shuffleDeck();
    }

    /**
     * Creates a standard deck of 52 playing cards that has not been shuffled.
     */
    public void createDeck()
    {
        for (Suit suit : Suit.values()) {
            for (int index = 2; index <= 14; index++) {
                String cardName = getCardName(index);
                playingCards.add(new Card(suit, cardName, true, index));
            }
        }
    }

    /**
     * Returns the name of the card based on its index.
     * @param index the index of the card (2-14)
     * @return the name of the card (2-10, Jack, Queen, King, Ace)
     */
    protected String getCardName(int index) {
        return switch (index) {
            case 11 -> "Jack";
            case 12 -> "Queen";
            case 13 -> "King";
            case 14 -> "Ace";
            default -> String.valueOf(index);
        };
    }

    /**
     * Deals the first card from the deck.
     * @return the card that is dealt
     * @throws IllegalStateException if no cards are left in the deck
     */
    public Card deal() {
        if (playingCards.isEmpty()) {
            throw new IllegalStateException("No cards left in the deck");
        }
        return playingCards.removeFirst();
    }

    /**
     * Shuffle the deck of cards.
     */
    public void shuffleDeck() {
        Collections.shuffle(playingCards);
    }

    /**
     * Returns the number of cards left in the deck.
     * @return the size of the deck
     */
    public int cardsLeftInDeck(){ return playingCards.size(); }

    /**
     * Returns a string representation of the deck.
     * @return a string containing all cards in the deck separated by spaces
     */
    @Override
    public String toString() {
        StringBuilder cardNames = new StringBuilder();
        for (Card card : playingCards) {
            cardNames.append(card).append(" ");
        }
        return cardNames.toString();
    }
}

