import java.util.Objects;

/**
 * Card - Represents a card from a standard deck of 52 playing cards
 * @author Katie Copeland
 * @version 2024.06.13
 */
public class Card implements Comparable<Card>{
    private Suit suit;
    private String name;
    private boolean visible;
    private int value;

    /**
     * Constructor for the Card class.
     * @param suit The suit of the card.
     * @param name The name of the card.
     * @param visible The setting to show or hide the card from view.
     * @param value The point value of the card from 2 to 14 inclusive. (11-14 represent face cards)
     */
    public Card(Suit suit, String name, boolean visible, int value)
    {
        this.suit = suit;
        this.name = name;
        this.visible = visible;
        //ensures value is 2-14 inclusive.
        if (value >= 2 && value <= 14)
        {
            this.value = value;
        }
    }

    /**
     * Get the suit of the card
     * @return The suit of the card
     */
    public Suit getSuit()
    {
        return suit;
    }

    /**
     * Get the card's point value
     * @return A number 2 - 14 of the card's point value
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Get the card's name
     * @return The name of the card 2 - 10, Jack, Queen, King, or Ace
     */
    public String getName()
    {
        return name;
    }

    /**
     * Report if the card is visible or hidden
     * @return true if the card is visible, false if hidden
     */
    public boolean isVisible()
    {
        return visible;
    }

    /**
     * Set the card as visible, so the name and value are be shown
     */
    public void show()
    {
        visible = true;
    }

    /**
     * Set the card as hidden, so the name and value are hidden is shown
     */
    public void hide()
    {
        visible = false;
    }

    /**
     * Shows or Hides the card based on the visible field variable.
     * @return return If the card is visible, returns the name and suit of the card (ex: Queen of Spades)
     * If the card is not visible, return the string literal "Hidden Card"
     */
    @Override
    public String toString() {

        String cardName = getName();
        if (visible) {
            return cardName + " of " + suit.name();
        }
        else {
            return "Hidden Card";
        }
    }

    /**
     * Overriding compareTo method of the comparable class
     * and compares the value of one card with that of another card
     * @param card the card to be compared.
     * @return a negative integer, zero, or a positive integer as this card is less than, equal to, or greater than the specified card
     */
    @Override
    public int compareTo(Card card) {
        return Integer.compare(this.value, card.value);
    }

    /**
     * Overriding equals method due to best practice when overriding compareTo
     * @param object object to compare
     * @return true if the state of the instance object equals that of the provided object, false if not
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Card card = (Card) object;
        return value == card.value &&
                name.equals(card.name) &&
                suit.equals(card.suit);
    }

    /**
     * Overriding hashCode method due to best practice when overriding compareTo
     * @return the hash code of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(value, name, suit);
    }
}
