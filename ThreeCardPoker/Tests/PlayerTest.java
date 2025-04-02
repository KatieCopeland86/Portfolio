import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    // Arrange
    // Instance Variables
    private Player player;
    private Card aceOfSpades;
    private Card aceOfDiamonds;
    private Card aceOfHearts;
    private Card twoOfDiamonds;
    private Card kingOfHearts;
    private Card twoOfHearts;
    private Card threeOfHearts;
    private Card fourOfHearts;
    private Card threeOfClubs;
    private Card fourOfSpades;

    @Before
    public void setUp() {
        // Arrange-Instantiate the variables
        player = new Player();
        aceOfHearts = new Card(Suit.Hearts, "Ace", true, 14);
        aceOfDiamonds = new Card(Suit.Diamonds, "Ace", true, 14);
        aceOfSpades = new Card(Suit.Spades, "Ace", true, 14);
        kingOfHearts = new Card(Suit.Hearts, "King", true, 13);
        twoOfDiamonds = new Card(Suit.Diamonds, "2", true, 2);
        twoOfHearts = new Card(Suit.Hearts, "2", true, 2);
        threeOfHearts = new Card(Suit.Hearts, "3", true, 3);
        threeOfClubs = new Card(Suit.Clubs, "3", true, 3);
        fourOfHearts = new Card(Suit.Hearts, "4", true, 4);
        fourOfSpades = new Card(Suit.Spades, "4", true, 4);

    }

    @After
    public void tearDown() {
        // set instance variables to null this removes references to the objects
        // allowing the garbage collector to reclaim the memory
        player = null;
        aceOfHearts = null;
        aceOfDiamonds = null;
        aceOfSpades = null;
        kingOfHearts = null;
        twoOfDiamonds = null;
        twoOfHearts = null;
        threeOfHearts = null;
        threeOfClubs = null;
        fourOfHearts = null;
        fourOfSpades = null;
    }

    @Test
    public void defaultConstructorTest() {
        // Act and Assert-verify name and bankroll set correctly
        // and verify the players hand is initially empty
        assertEquals("Player", player.getName());
        assertEquals(1000, player.getBankroll());
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    public void overloadedConstructorTest()
    {   // Arrange-Create a new player using the overloaded constructor
        player = new Player("Test", 2000);
        // Act and Assert-verify name and bankroll set correctly
        // and verify the players hand is initially empty
        assertEquals("Test", player.getName());
        assertEquals(2000, player.getBankroll());
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    public void setBankrollTest()
    {   //Act - valid amount
        player.setBankroll(500);
        //Assert - verify amount
        assertEquals(500, player.getBankroll());
        //Act - invalid amount
        player.setBankroll(-100);
        //Assert - should stay at initial setting of 500
        assertEquals(500, player.getBankroll());
    }

    @Test
    public void getCardFromDealerTest()
    {   // Act - get a card from the dealer
        player.getCardFromDealer(aceOfDiamonds,true);
        // Assert - verify that there is 1 card in the players hand,
        // that it is the correct card, and that it is not hidden.
        assertEquals(1,player.getHand().size());
        assertTrue(player.getHand().contains(aceOfDiamonds));
        assertTrue(aceOfDiamonds.isVisible());
    }

    @Test
    public void clearHandTest()
    {
        // Act - Get cards from the dealer
        player.getCardFromDealer(aceOfDiamonds,true);
        player.getCardFromDealer(threeOfClubs,true);
        // Assert - verify hand does have cards before clearing
        assertEquals(2,player.getHand().size());

        // Act-Get rid of the cards in the players hand
        player.clearHand();
        // Assert-verify no cards left in the players hand
        assertEquals(0, player.getHand().size());
    }

    @Test
    public void scoreHandHighCardTest()
    {
        // Act-get cards from the dealer
        player.getCardFromDealer(aceOfDiamonds,true);
        player.getCardFromDealer(threeOfClubs,true);
        player.getCardFromDealer(fourOfHearts, true);
        // Assert-Verify this is a high card scoring hand.
        assertTrue(player.scoreHand() >= 100 && player.scoreHand() < 200);
    }

    @Test
    public void scoreHandPairTest()
    {
        // Act-get cards from the dealer
        player.getCardFromDealer(twoOfDiamonds,true);
        player.getCardFromDealer(threeOfClubs,true);
        player.getCardFromDealer(twoOfHearts,true);
        // Assert-Verify this is a Pair scoring hand.
        assertTrue(player.scoreHand() >= 200 && player.scoreHand() < 400);
    }

    @Test
    public void scoreHandFlushTest()
    {
        // Act-get cards from the dealer
        player.getCardFromDealer(aceOfHearts,true);
        player.getCardFromDealer(kingOfHearts,true);
        player.getCardFromDealer(twoOfHearts,true);
        // Assert-Verify this is a Flush scoring hand.
        assertTrue(player.scoreHand() >= 400 && player.scoreHand() < 600);
    }

    @Test
    public void scoreHandStraightTest()
    {
        // Act-get cards from the dealer
        player.getCardFromDealer(twoOfHearts,true);
        player.getCardFromDealer(threeOfClubs,true);
        player.getCardFromDealer(fourOfSpades,true);
        // Assert-Verify this is a straight scoring hand.
        assertTrue(player.scoreHand() >= 600 && player.scoreHand() < 800);
    }

    @Test
    public void scoreHandThreeOfAKindTest()
    {
        // Act-get cards from the dealer
        player.getCardFromDealer(aceOfHearts,true);
        player.getCardFromDealer(aceOfDiamonds,true);
        player.getCardFromDealer(aceOfSpades,true);
        // Assert-Verify this is a three of a kind scoring hand
        assertTrue(player.scoreHand() >= 800 && player.scoreHand() < 1000);
    }

    @Test
    public void scoreHandStraightFlushTest()
    {
        // Act-get cards from the dealer
        player.getCardFromDealer(twoOfHearts,true);
        player.getCardFromDealer(threeOfHearts,true);
        player.getCardFromDealer(fourOfHearts,true);
        // Assert-Verify this is a straight flush scoring hand
        assertTrue(player.scoreHand() >= 1000);
    }

    @Test
    public void scoreHandStraightFlushAceLowTest() {
        // Arrange - Create a straight flush with Ace, 2, 3 of the same suit
        player.getCardFromDealer(new Card(Suit.Hearts, "Ace", true, 14), true);
        player.getCardFromDealer(new Card(Suit.Hearts, "2", true, 2), true);
        player.getCardFromDealer(new Card(Suit.Hearts, "3", true, 3), true);
        // Assert - Verify the hand is scored as a straight flush
        assertTrue(player.scoreHand() >= 1000);
    }

    @Test
    public void showAllCardsTest()
    {
        // Act-get cards from the dealer
        player.getCardFromDealer(aceOfSpades,false);
        player.getCardFromDealer(kingOfHearts,false);
        // Assert-verify cards currently hidden
        assertFalse(aceOfSpades.isVisible());
        assertFalse(kingOfHearts.isVisible());
        // Act-Change card visibility
        player.showAllCards();
        // Assert-Verify all cards are now visible
        assertTrue(aceOfSpades.isVisible());
        assertTrue(kingOfHearts.isVisible());
    }

    @Test
    public void setBankrollMaxValueTest() {
        // Act - Set bankroll to the maximum integer value
        player.setBankroll(Integer.MAX_VALUE);
        // Assert - Verify the bankroll is set correctly
        assertEquals(Integer.MAX_VALUE, player.getBankroll());
    }

    @Test(expected = IllegalStateException.class)
    public void scoreHandWithInsufficientCardsTest() {
        player.getCardFromDealer(aceOfHearts, true);
        player.getCardFromDealer(kingOfHearts, true);
        // Only 2 cards - should throw an exception
        player.scoreHand();
    }

    @Test
    public void getBankrollAfterMultipleModificationsTest() {
        player.setBankroll(500);
        player.setBankroll(-100); // Should not change
        assertEquals(500, player.getBankroll());

        player.setBankroll(1500);
        assertEquals(1500, player.getBankroll());
    }

    @Test
    public void getCardFromDealerWithDifferentVisibilityTest() {
        player.getCardFromDealer(aceOfHearts, false);
        assertEquals(1, player.getHand().size());
        assertFalse(aceOfHearts.isVisible());

        player.getCardFromDealer(kingOfHearts, true);
        assertEquals(2, player.getHand().size());
        assertTrue(kingOfHearts.isVisible());
    }

    @Test
    public void scoreHandWithMixedStraightTest() {
        player.getCardFromDealer(twoOfHearts, true);
        player.getCardFromDealer(threeOfClubs, true);
        player.getCardFromDealer(fourOfSpades, true);

        assertTrue(player.scoreHand() >= 600 && player.scoreHand() < 800);
    }


}
