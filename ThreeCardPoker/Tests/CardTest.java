import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {
    //Arrange
    // Instance variables
    private Card queenOfClubs;
    private Card jackOfHearts;
    private Card queenOfSpades;


    @Before
    public void setUp() {
        //Arrange - Instantiate the cards.
        queenOfClubs = new Card(Suit.Clubs, "Queen", true, 12);
        jackOfHearts = new Card(Suit.Hearts, "Jack", false, 11);
        queenOfSpades = new Card(Suit.Spades, "Queen", true, 12);
    }

    @After
    public void tearDown() {
        // set instance variables to null, this removes references to the objects
        // allowing the garbage collector to reclaim the memory
        queenOfClubs = null;
        jackOfHearts = null;
        queenOfSpades = null;
    }

    @Test
    public void cardConstructorTest() {

        //Act & Assert Verify that a Card was instantiated correctly
        assertEquals(queenOfClubs.getSuit(), Suit.Clubs);
        assertEquals(queenOfClubs.getValue(), 12);
        assertEquals(queenOfClubs.getName(), "Queen");
        assertTrue(queenOfClubs.isVisible());

    }

    @Test
    public void cardShowHideTest() {
        //Act-hide a card and show a card
        queenOfClubs.hide();
        jackOfHearts.show();
        //Assert-Verify that a card is correctly hidden and a card is correctly visible
        assertFalse(queenOfClubs.isVisible());
        assertTrue(jackOfHearts.isVisible());
    }

    @Test
    public void compareToTest()
    {
        // Act & Assert-Verify the value of one card is less than the other, vice versa
        // Also verify that the same card of a different suit has equal value.
        assertTrue(jackOfHearts.compareTo(queenOfSpades) < 0);
        assertTrue(queenOfClubs.compareTo(jackOfHearts) > 0);
        assertEquals(0, queenOfSpades.compareTo(queenOfClubs));
    }

    @Test
    public void toStringOverrideTest()
    {
        //Act-Convert to string for comparison.
        String visibleCard = queenOfClubs.toString();
        String hiddenCard = jackOfHearts.toString();
        
        //Assert-Verify the correct string output of a visible and hidden card.
        assertEquals("Queen of Clubs", visibleCard);
        assertEquals("Hidden Card", hiddenCard);
    }

    @Test
    public void toStringEdgeCaseLowestCardTest() {
        Card twoOfClubs = new Card(Suit.Clubs, "2", true, 2);
        // Assert the string representation of the lowest card
        assertEquals("2 of Clubs", twoOfClubs.toString());
    }

    @Test
    public void toStringEdgeCaseHighestCardTest() {
        Card aceOfSpades = new Card(Suit.Spades, "Ace", true, 14);
        // Assert the string representation of the highest card
        assertEquals("Ace of Spades", aceOfSpades.toString());
    }

    @Test(expected = NullPointerException.class)
    public void compareToNullTest() {
        queenOfClubs.compareTo(null);
    }

    @Test
    public void isVisibleTest() {
        Card testCard = new Card(Suit.Diamonds, "King", true, 13);

        // Initial state
        assertTrue(testCard.isVisible());

        // Hide
        testCard.hide();
        assertFalse(testCard.isVisible());

        // Show again
        testCard.show();
        assertTrue(testCard.isVisible());
    }

    @Test
    public void HighLowCardComparisonTest() {
        Card twoOfClubs = new Card(Suit.Clubs, "2", true, 2);
        Card aceOfSpades = new Card(Suit.Spades, "Ace", true, 14);

        assertTrue(twoOfClubs.compareTo(aceOfSpades) < 0);
        assertTrue(aceOfSpades.compareTo(twoOfClubs) > 0);
    }

    @Test
    public void equalsAndHashCodeTest() {
        // Cards with same value but different suits
        Card queenOfClubsTwo = new Card(Suit.Clubs, "Queen", true, 12);

        // Test equality
        assertEquals(queenOfClubs, queenOfClubsTwo);
        assertEquals(queenOfClubs.hashCode(), queenOfClubsTwo.hashCode());

        // Test inequality
        assertNotEquals(queenOfClubs, jackOfHearts);
    }
}
