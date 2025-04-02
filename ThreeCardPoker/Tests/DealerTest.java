import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DealerTest {
    // Arrange
    // Instance Variables
    private Dealer dealer;
    @Before
    public void setUp()
    {
        // Act-Instantiate the dealer variable.
        dealer = new Dealer(1500);
    }

    @After
    public void tearDown()
    {
        // set instance variables to null this removes references to the objects
        // allowing the garbage collector to reclaim the memory
        dealer = null;
    }

    @Test
    public void dealerConstructorTest()
    {
        // Act & Assert-verify that the dealer was instantiated correctly
        assertEquals(dealer.getBankroll(), 1500);
        assertNotNull(dealer.deal());
    }

    @Test
    public void dealerResetDeckTest()
    {
        // Arrange-Deal all the cards so there are none left
        for (int i = 0; i < 52; i++)
        {
            dealer.deal();
        }
        // Act-Reset the deck
        dealer.resetDeck();

        // Deal a card to verify the deck has been reset
        Card card = dealer.deal();

        // Assert-Verify the deck has been reset with 52 cards
        // meaning the newly dealt card should not be null
        assertNotNull(card);
    }

    @Test
    public void dealerDealTest()
    {
        // Act-Deal a card
        Card card = dealer.deal();

        // Assert-verifying a Card was dealt
        assertNotNull(card);
    }
    @Test
    public void dealerNameTest() {
        // Verify that the dealer's name is set correctly
        assertEquals("Dealer", dealer.getName());
    }

    @Test
    public void dealEmptyDeckTest() {
        // Arrange - Deal all 52 cards
        for (int i = 0; i < 52; i++) {
            dealer.deal();
        }

        // Act & Assert - Attempting to deal from an empty deck should throw an exception
        assertThrows(IllegalStateException.class, () -> {
            dealer.deal();
        });
    }

    @Test
    public void multipleDeckResetsTest() {
        // Arrange - Deal some cards
        for (int i = 0; i < 20; i++) {
            dealer.deal();
        }

        // Act - Reset the deck multiple times
        dealer.resetDeck();
        Card firstCardAfterFirstReset = dealer.deal();

        dealer.resetDeck();
        Card firstCardAfterSecondReset = dealer.deal();

        // Assert - Verify that reset deck creates a new, shuffled deck
        assertNotNull(firstCardAfterFirstReset);
        assertNotNull(firstCardAfterSecondReset);
    }

    @Test
    public void dealerBankrollTest() {
        // Verify initial bankroll
        assertEquals(1500, dealer.getBankroll());

        // Test adding from bankroll
        dealer.setBankroll(dealer.getBankroll() + 500);
        assertEquals(2000, dealer.getBankroll());

        // Test subtracting from bankroll
        dealer.setBankroll(dealer.getBankroll() - 1000);
        assertEquals(1000, dealer.getBankroll());
    }
}
