import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class DeckTest {
    //  Arrange
    // Instance variables
    private Deck deck;
    private Deck deck2;
    @Before
    public void setUp() {
        // Arrange- Instantiate 2 different decks
        deck = new Deck();
        deck2 = new Deck();
    }

    @After
    public void tearDown() {
        // set instance variables to null this removes references to the objects
        // allowing the garbage collector to reclaim the memory
        deck = null;
        deck2 = null;
    }

    @Test
    public void createDeckTest()
    {
        // Assert-verify there are 52 cards in the deck when it is created
        assertEquals(52, deck.cardsLeftInDeck());
    }
    @Test
    public void shuffleDeckTest() {
        // Assert-Because the decks are shuffled upon creation,
        // they can be converted to a string for comparison.
        assertNotEquals(deck.toString(), deck2.toString());
    }

    @Test
    public void deckDealTest() {
        // Act-Deal a card
        Card card = deck.deal();
        // Assert-Verify the card is dealt by making sure it is not null, and the deck has 51 cards left in it.
        assertNotNull(card);
        assertEquals(51, deck.cardsLeftInDeck());
    }

    @Test
    public void dealFromEmptyDeckTest() {
        // Act-Deal all the cards in the deck so that the deck is empty
        for (int i = 0; i < 52; i++) {
            deck.deal();
        }
        // Assert-verify an IllegalStateException is thrown when dealing another card from
        // a deck that is empty.
        Exception ex = assertThrows(IllegalStateException.class,() -> deck.deal());
        String expectedMessage = "No cards left in the deck";
        String actualMessage = ex.getMessage();

        // Assert-verify that the exception message is correct.
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void toStringOverrideTest() {
        // Arrange-Create a string representation of a standard deck of 52 playing cards
        // noting the form is "name" of "suit" (ex:Ace of Spades)
        String deckString = deck.toString();
        String[] words = deckString.split(" ");
        ArrayList<String> cards = new ArrayList<>();
        for (int i = 0; i < words.length; i += 3) {
            String card = words[i] + " " + words[i + 1] + " " + words[i + 2];
            cards.add(card);
        }

        // Assert-verify string representation of the deck equals 52
        assertEquals(52, cards.size());

        // Arrange-A hashset does not allow duplicates, and uses the equals() method for comparison
        // making a Hashset great for verifying that each string representation of a card in a deck
        // is not the same
        HashSet<String> uniqueCards = new HashSet<>(cards);

        // Assert-that there are 52 unique string representations of the cards
        assertEquals(52, uniqueCards.size());
    }

    @Test
    public void shufflePartiallyDealtDeckTest() {
        // Deal 10 cards
        for (int i = 0; i < 10; i++) {
            deck.deal();
        }
        // Shuffle the remaining deck
        deck.shuffleDeck();
        // Verify the deck still has 42 cards
        assertEquals(42, deck.cardsLeftInDeck());
    }

    @Test
    public void toStringPartiallyDealtDeckTest() {
        // Deal 10 cards
        for (int i = 0; i < 10; i++) {
            deck.deal();
        }
        // Verify the string representation of the remaining deck
        String deckString = deck.toString();
        String[] cards = deckString.split(" ");
        assertEquals(42 * 3, cards.length); // Each card takes 3 words (e.g., "Ace of Spades")
    }

    @Test
    public void testMultipleShuffles() {
        // Verify that multiple shuffles produce different orders
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        // Shuffle both decks multiple times
        for (int i = 0; i < 5; i++) {
            deck1.shuffleDeck();
            deck2.shuffleDeck();
        }

        // Unlikely that two completely identical shuffles would occur
        assertNotEquals(deck1.toString(), deck2.toString());
    }

}

