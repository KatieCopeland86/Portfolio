import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;


public class ThreeCardPokerTest {
    //Arrange
    //Instance Variables
    private ThreeCardPoker threeCardPoker;
    private Card aceOfDiamonds;
    private Card aceOfHearts;
    private Card sixOfDiamonds;
    private Card fourOfHearts;
    private Card threeOfHearts;
    private Card threeOfClubs;
    private Card fourOfSpades;
    private Card fourOfDiamonds;
    private Card threeOfDiamonds;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        // Arrange-Instantiate the variables
        threeCardPoker = new ThreeCardPoker();
        aceOfHearts = new Card(Suit.Hearts, "Ace", true, 14);
        aceOfDiamonds = new Card(Suit.Diamonds, "Ace", true, 14);
        sixOfDiamonds = new Card(Suit.Diamonds, "6", true, 6);
        fourOfHearts = new Card(Suit.Hearts, "4", true, 4);
        threeOfHearts = new Card(Suit.Hearts, "3", true, 3);
        threeOfClubs = new Card(Suit.Clubs, "3", true, 3);
        fourOfSpades = new Card(Suit.Spades, "4", true, 4);
        fourOfDiamonds = new Card(Suit.Diamonds, "4", true, 4);
        threeOfDiamonds = new Card(Suit.Diamonds, "3", true, 3);
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown()
    {
        // Set instance variables to null, this removes references to the objects
        // allowing the garbage collector to reclaim the memory
        threeCardPoker = null;
        aceOfHearts = null;
        aceOfDiamonds = null;
        sixOfDiamonds = null;
        fourOfHearts = null;
        threeOfHearts = null;
        threeOfClubs = null;
        fourOfSpades = null;
        System.setOut(originalOut);
    }

    @Test
    public void testThreeCardPokerConstructor() {
        // Assert-Verify the pot is initialized to 0
        assertEquals(0, threeCardPoker.getPot());

        // Assert-Verify folded is initialized to false
        assertFalse(threeCardPoker.isFolded());

        // Assert-Verify the player is not null and is initialized with the default constructor values
        assertNotNull(threeCardPoker.getPlayer());
        assertEquals("Player", threeCardPoker.getPlayer().getName());
        assertEquals(1000, threeCardPoker.getPlayer().getBankroll());
        assertTrue(threeCardPoker.getPlayer().getHand().isEmpty());

        // Assert-Verify the dealer is not null and is initialized with twice the player's bankroll
        assertNotNull(threeCardPoker.getDealer());
        assertEquals(2000, threeCardPoker.getDealer().getBankroll());
    }

    @Test
    public void placeValidAnteTest() throws IllegalBetException
    {
        // Keep in mind the player starts with 1000 in their bankroll and the dealer twice that amount (2000)
        // and pot starts at 0
        // Arrange-valid Ante
        int validAnte = 100;


        // Act- Place valid ante
        threeCardPoker.placeAnte(validAnte);

        // Assert-Verify ante was placed, correct amounts were removed from the dealer bankroll,
        // the player bankroll, and then added to the pot.
        assertEquals(100, threeCardPoker.getAnte());
        assertEquals(900, threeCardPoker.getPlayer().getBankroll());
        assertEquals(1900, threeCardPoker.getDealer().getBankroll());
        assertEquals(200, threeCardPoker.getPot());

    }

    @Test
    public void placeInvalidAnteNegativeTest() {
        // Keep in mind the player starts with 1000 in their bankroll and the dealer twice that amount (2000)
        // and pot starts at 0

        // Arrange-invalid Ante
        int invalidAnte = -100;

        // Act & Assert-Verify Illegal bet exception was thrown, ante was not placed,
        // nothing was removed from the dealer bankroll, or the player bankroll,
        // and nothing was added to the pot.
        assertThrows(IllegalBetException.class, () -> threeCardPoker.placeAnte(invalidAnte));
        assertEquals(0, threeCardPoker.getAnte());
        assertEquals(1000, threeCardPoker.getPlayer().getBankroll());
        assertEquals(2000, threeCardPoker.getDealer().getBankroll());
        assertEquals(0, threeCardPoker.getPot());

    }
    @Test
    public void placeInvalidAnteExceedsBankrollTest() {
        // Arrange - Player bankroll is 1000
        int invalidAnte = 1001; // Ante exceeds bankroll
        // Act & Assert - Verify IllegalBetException is thrown
        assertThrows(IllegalBetException.class, () -> threeCardPoker.placeAnte(invalidAnte));
    }
    @Test
    public void scoreGameSameHandDifferentSuitsTest() throws IllegalBetException {
        // Arrange - Set up hands with the same ranks but different suits
        threeCardPoker.getPlayer().getCardFromDealer(aceOfDiamonds, true);
        threeCardPoker.getPlayer().getCardFromDealer(threeOfDiamonds, true);
        threeCardPoker.getPlayer().getCardFromDealer(fourOfDiamonds, true);
        threeCardPoker.getDealer().getCardFromDealer(aceOfHearts, true);
        threeCardPoker.getDealer().getCardFromDealer(threeOfHearts, true);
        threeCardPoker.getDealer().getCardFromDealer(fourOfHearts, true);
        threeCardPoker.placeAnte(100);
        threeCardPoker.placePlayBet(200);
        // Act - Score the game
        threeCardPoker.scoreGame();
        // Assert - Verify the result is a push
        assertEquals(1000, threeCardPoker.getPlayer().getBankroll());
        assertEquals(2000, threeCardPoker.getDealer().getBankroll());
        assertEquals(0, threeCardPoker.getPot());
    }
    @Test
    public void placeValidPlayBetTest() throws IllegalBetException
    {
        // Keep in mind the player starts with 1000 in their bankroll and the dealer twice that amount (2000)
        // and pot starts at 0
        int validPlayBet = 200;

        // Act-Place valid playBet, because the ante is used for determining the dealer playBet
        // it needs to be placed here.
        threeCardPoker.placeAnte(100);
        threeCardPoker.placePlayBet(validPlayBet);

        // Assert-Verify playBet was placed, correct amounts were removed from the dealer bankroll,
        // the player bankroll, and then added to the pot.
        assertEquals(100, threeCardPoker.getAnte());
        assertEquals(200,threeCardPoker.getPlayBet());
        assertEquals(700, threeCardPoker.getPlayer().getBankroll());
        assertEquals(1800, threeCardPoker.getDealer().getBankroll());
        assertEquals(500, threeCardPoker.getPot());

    }

    @Test
    public void placeInvalidPlayBetNegativeTest() throws IllegalBetException
    {
        // Keep in mind the player starts with 1000 in their bankroll and the dealer twice that amount (2000)
        // and pot starts at 0

        // Arrange-invalid playBet, because the ante is used for determining the dealer playBet
        // it needs to be placed here.
        threeCardPoker.placeAnte(100);
        int invalidPlayBet = -100;

        // Act & Assert-Verify Illegal bet exception was thrown, playBet was not placed,
        // nothing was removed from the dealer bankroll, or the player bankroll,
        // and nothing was added to the pot.
        assertThrows(IllegalBetException.class, () -> threeCardPoker.placePlayBet(invalidPlayBet));
        assertEquals(100, threeCardPoker.getAnte());
        assertEquals(0, threeCardPoker.getPlayBet());
        assertEquals(900, threeCardPoker.getPlayer().getBankroll());
        assertEquals(1900, threeCardPoker.getDealer().getBankroll());
        assertEquals(200, threeCardPoker.getPot());
    }

    @Test
    public void placeInvalidPlayBetExceedsBankrollTest() throws IllegalBetException {
        // Arrange - Place a valid ante
        threeCardPoker.placeAnte(100);
        // Act & Assert - Verify IllegalBetException is thrown for invalid play bet
        assertThrows(IllegalBetException.class, () -> threeCardPoker.placePlayBet(901)); // Exceeds bankroll
    }

    @Test
    public void placePlayBetLessThanAnteTest() throws IllegalBetException {
        // Arrange - Place a valid ante
        threeCardPoker.placeAnte(100);
        // Act & Assert - Verify IllegalBetException is thrown for invalid play bet
        assertThrows(IllegalBetException.class, () -> threeCardPoker.placePlayBet(50)); // Less than ante
    }

    @Test
    public void placeAnteEntireBankrollTest() throws IllegalBetException {
        // Arrange - Player bankroll is 1000
        int validAnte = 1000; // Bet entire bankroll
        // Act - Place the ante
        threeCardPoker.placeAnte(validAnte);
        // Assert - Verify bankroll and pot are updated correctly
        assertEquals(0, threeCardPoker.getPlayer().getBankroll());
        assertEquals(2000, threeCardPoker.getPot()); // 1000 from player + 1000 from dealer
    }

    @Test
    public void declareWinnerPlayerTest()
    {   // Arrange- set player bankroll to zero
        threeCardPoker.getPlayer().setBankroll(0);

        // Act & Assert-verify declare winner returns true
        assertTrue(threeCardPoker.declareWinner());
    }

    @Test
    public void declareWinnerDealerTest()
    {   // Arrange- set player bankroll to zero
        threeCardPoker.getDealer().setBankroll(0);

        // Act & Assert-verify declare winner returns true
        assertTrue(threeCardPoker.declareWinner());
    }

    @Test
    public void declareWinnerNoWinnerTest()
    {
        // Act && Assert-verify there is no winner if player and dealer are not bankrupt
        assertFalse(threeCardPoker.declareWinner());
    }

    @Test
    public void scoreGameDealerQualifiesPlayerWinsTest() throws IllegalBetException {
        // Arrange - set up so the dealer qualifies and player wins
        threeCardPoker.getPlayer().getCardFromDealer(aceOfHearts,true);
        threeCardPoker.getPlayer().getCardFromDealer(threeOfHearts,true);
        threeCardPoker.getPlayer().getCardFromDealer(fourOfHearts,true);
        threeCardPoker.getDealer().getCardFromDealer(threeOfClubs,true);
        threeCardPoker.getDealer().getCardFromDealer(fourOfSpades, true);
        threeCardPoker.getDealer().getCardFromDealer(aceOfDiamonds, true);
        threeCardPoker.placeAnte(100);
        threeCardPoker.placePlayBet(200);

        // Act-Score the game
        threeCardPoker.scoreGame();

        // Assert-Verify the dealer qualified and player won receiving the correct winnings
        // pot returns to clean slate for a new hand

        assertEquals(1200, threeCardPoker.getPlayer().getBankroll());
        assertEquals(1800,threeCardPoker.getDealer().getBankroll());
        assertEquals(0,threeCardPoker.getPot());
    }

    @Test
    public void scoreGameDealerQualifiesDealerWinsTest() throws IllegalBetException {
        // Arrange - set up so the dealer qualifies and dealer wins
        threeCardPoker.getDealer().getCardFromDealer(aceOfHearts,true);
        threeCardPoker.getDealer().getCardFromDealer(threeOfHearts,true);
        threeCardPoker.getDealer().getCardFromDealer(fourOfHearts,true);
        threeCardPoker.getPlayer().getCardFromDealer(threeOfClubs,true);
        threeCardPoker.getPlayer().getCardFromDealer(fourOfSpades, true);
        threeCardPoker.getPlayer().getCardFromDealer(aceOfDiamonds, true);
        threeCardPoker.placeAnte(100);
        threeCardPoker.placePlayBet(200);

        // Act-Score the game
        threeCardPoker.scoreGame();

        // Assert-Verify the dealer qualified and dealer won receiving the correct winnings
        // pot returns to clean slate for a new hand

        assertEquals(700, threeCardPoker.getPlayer().getBankroll());
        assertEquals(2300,threeCardPoker.getDealer().getBankroll());
        assertEquals(0,threeCardPoker.getPot());

    }

    @Test
    public void scoreGameDealerQualifiesPlayerFoldsTest() throws IllegalBetException {
        // Arrange - set up so the dealer qualifies and player folded
        threeCardPoker.getDealer().getCardFromDealer(aceOfHearts,true);
        threeCardPoker.getDealer().getCardFromDealer(threeOfHearts,true);
        threeCardPoker.getDealer().getCardFromDealer(fourOfHearts,true);
        threeCardPoker.getPlayer().getCardFromDealer(threeOfClubs,true);
        threeCardPoker.getPlayer().getCardFromDealer(fourOfSpades, true);
        threeCardPoker.getPlayer().getCardFromDealer(sixOfDiamonds, true);
        threeCardPoker.placeAnte(100);
        threeCardPoker.setFolded(true);

        // Act-Score the game
        threeCardPoker.scoreGame();

        // Assert-Verify the dealer qualified and player folded dealers wins
        // pot returns to clean slate for a new hand

        assertEquals(900, threeCardPoker.getPlayer().getBankroll());
        assertEquals(2100,threeCardPoker.getDealer().getBankroll());
        assertEquals(0,threeCardPoker.getPot());

    }

    @Test
    public void scoreGameDealerQualifiesPushTest() throws IllegalBetException {
        // Arrange - set up so the dealer qualifies and result is a push
        threeCardPoker.getDealer().getCardFromDealer(aceOfHearts,true);
        threeCardPoker.getDealer().getCardFromDealer(threeOfHearts,true);
        threeCardPoker.getDealer().getCardFromDealer(fourOfSpades,true);
        threeCardPoker.getPlayer().getCardFromDealer(threeOfClubs,true);
        threeCardPoker.getPlayer().getCardFromDealer(fourOfHearts, true);
        threeCardPoker.getPlayer().getCardFromDealer(aceOfDiamonds, true);
        threeCardPoker.placeAnte(100);
        threeCardPoker.placePlayBet(200);

        // Act-Score the game
        threeCardPoker.scoreGame();

        // Assert-Verify the dealer qualified and there was a Tie both player and dealer receive correct winnings
        // pot returns to clean slate for a new hand

        assertEquals(1000, threeCardPoker.getPlayer().getBankroll());
        assertEquals(2000,threeCardPoker.getDealer().getBankroll());
        assertEquals(0,threeCardPoker.getPot());

    }

    @Test
    public void scoreGameDealerDoesNotQualifyPlayerBetTest() throws IllegalBetException {
        // Arrange - set up so the dealer does not qualify
        threeCardPoker.getDealer().getCardFromDealer(sixOfDiamonds,true);
        threeCardPoker.getDealer().getCardFromDealer(threeOfHearts,true);
        threeCardPoker.getDealer().getCardFromDealer(fourOfSpades,true);
        threeCardPoker.getPlayer().getCardFromDealer(threeOfClubs,true);
        threeCardPoker.getPlayer().getCardFromDealer(fourOfHearts, true);
        threeCardPoker.getPlayer().getCardFromDealer(aceOfDiamonds, true);
        threeCardPoker.placeAnte(100);
        threeCardPoker.placePlayBet(200);

        // Act-Score the game
        threeCardPoker.scoreGame();

        // Assert-Verify play bet returned as push, dealer and player receive correct winnings and
        // pot returns to clean slate for a new hand

        assertEquals(1100, threeCardPoker.getPlayer().getBankroll());
        assertEquals(1900,threeCardPoker.getDealer().getBankroll());
        assertEquals(0,threeCardPoker.getPot());
    }

    @Test
    public void scoreGameDealerDoesNotQualifyPlayerFoldedTest() throws IllegalBetException {
        // Arrange - set up so the dealer does not qualify and player folded
        threeCardPoker.getDealer().getCardFromDealer(sixOfDiamonds,true);
        threeCardPoker.getDealer().getCardFromDealer(threeOfHearts,true);
        threeCardPoker.getDealer().getCardFromDealer(fourOfSpades,true);
        threeCardPoker.getPlayer().getCardFromDealer(threeOfClubs,true);
        threeCardPoker.getPlayer().getCardFromDealer(fourOfHearts, true);
        threeCardPoker.getPlayer().getCardFromDealer(aceOfDiamonds, true);
        threeCardPoker.placeAnte(100);
        threeCardPoker.setFolded(true);

        // Act-Score the game
        threeCardPoker.scoreGame();

        // Assert-Verify the dealer qualified and player folded dealers wins
        // pot returns to clean slate for a new hand

        assertEquals(900, threeCardPoker.getPlayer().getBankroll());
        assertEquals(2000,threeCardPoker.getDealer().getBankroll());
        assertEquals(100,threeCardPoker.getPot());

    }

    @Test
    public void foldWithMaximumAnteTest() throws IllegalBetException {
        // Arrange - Player places the maximum ante (entire bankroll)
        threeCardPoker.placeAnte(1000);

        // Set up the dealer's hand to qualify (Queen or higher)
        threeCardPoker.getPlayer().getCardFromDealer(fourOfHearts, true);
        threeCardPoker.getPlayer().getCardFromDealer(fourOfSpades, true);
        threeCardPoker.getPlayer().getCardFromDealer(threeOfHearts, true);
        threeCardPoker.getDealer().getCardFromDealer(aceOfDiamonds, true);
        threeCardPoker.getDealer().getCardFromDealer(threeOfClubs, true);
        threeCardPoker.getDealer().getCardFromDealer(sixOfDiamonds, true);

        // Act - Fold
        threeCardPoker.setFolded(true);
        threeCardPoker.scoreGame();

        // Assert - Verify player loses entire bankroll, dealer gains ante
        assertEquals(0, threeCardPoker.getPlayer().getBankroll());
        assertEquals(3000, threeCardPoker.getDealer().getBankroll()); // 2000 + 1000
        assertEquals(0, threeCardPoker.getPot());
    }
    @Test
    public void testCompareHandsPlayerWinsWithHigherHandType() {
        // Assign scores representing different hand types
        // Straight Flush (higher rank) vs High Card (lower rank)
        int playerHandScore = 600;  // Representing Straight Flush
        int dealerHandScore = 50;   // Representing High Card

        assertTrue("Player with Straight Flush should beat Dealer with High Card",
                threeCardPoker.compareHands(playerHandScore, dealerHandScore) > 0);
    }

    @Test
    public void testCompareHandsDealerWinsWithHigherHandType() {
        // Dealer has a higher hand type
        int playerHandScore = 50;   // Representing High Card
        int dealerHandScore = 600;  // Representing Straight Flush

        assertTrue("Dealer with Straight Flush should beat Player with High Card",
                threeCardPoker.compareHands(playerHandScore, dealerHandScore) < 0);
    }

    @Test
    public void testCompareHandsSameHandTypeDifferentScores() {
        // Same hand type (e.g., Pair) but different scores
        int playerHandScore = 180;  // Higher Pair score
        int dealerHandScore = 120;  // Lower Pair score

        assertTrue("Player with higher Pair score should win",
                threeCardPoker.compareHands(playerHandScore, dealerHandScore) > 0);
    }

    @Test
    public void testCompareHandsSameHandTypeEqualScores() {
        // Exactly the same hand type and score
        int playerHandScore = 250;  // Same score
        int dealerHandScore = 250;  // Same score

        assertEquals("Identical hand types and scores should result in a tie",
                0, threeCardPoker.compareHands(playerHandScore, dealerHandScore));
    }

    @Test
    public void testCompareHandsEdgeCaseLowestVsHighestScores() {
        // Lowest possible score vs highest possible score
        int lowestScore = 1;
        int highestScore = Integer.MAX_VALUE;

        assertTrue("Lowest score should lose to highest score",
                threeCardPoker.compareHands(lowestScore, highestScore) < 0);
    }

    @Test
    public void testCompareHandsWithinSameHandTypeNearlyEqualScores() {
        // Two nearly equal scores within the same hand type
        int playerHandScore = 251;
        int dealerHandScore = 250;

        assertTrue("Slightly higher score should win",
                threeCardPoker.compareHands(playerHandScore, dealerHandScore) > 0);
    }

    @Test
    public void testCompareHandsNegativeScores() {
        // Test with negative scores to ensure consistent behavior
        int playerHandScore = -100;
        int dealerHandScore = -50;

        assertTrue("Less negative score should win",
                threeCardPoker.compareHands(playerHandScore, dealerHandScore) < 0);
    }

    @Test
    public void getUserInputValidOneTest()
    {   // Arrange-Use system in to take in a string representing user input
        String input = "1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        threeCardPoker = new ThreeCardPoker(scanner);
        int userInput = threeCardPoker.getUserInput();
        assertEquals(1, userInput);
    }
    @Test
    public void getUserInputValidTwoTest()
    {   // Arrange-Use system in to take in a string representing user input
        String input = "2\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        threeCardPoker = new ThreeCardPoker(scanner);
        int userInput = threeCardPoker.getUserInput();
        assertEquals(2, userInput);
    }

    @Test
    public void getUserInputInvalidInputThenValidTest() {
        String input = "3\n2\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        threeCardPoker = new ThreeCardPoker(scanner);
        int userInput = threeCardPoker.getUserInput();
        assertEquals(2, userInput);
        assertTrue(outContent.toString().contains("3 is invalid. Please enter a 1 or a 2."));
    }

    @Test
    public void getUserInputMultipleInvalidInputsThenValidTest() {
        String input = "4\n5\n2\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        threeCardPoker = new ThreeCardPoker(scanner);
        int userInput = threeCardPoker.getUserInput();
        assertEquals(2, userInput);
        assertTrue(outContent.toString().contains("4 is invalid. Please enter a 1 or a 2."));
        assertTrue(outContent.toString().contains("5 is invalid. Please enter a 1 or a 2."));
    }

    @Test
    public void getUserInputInputMismatchThenValidTest() {
        String input = "abc\n1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        threeCardPoker = new ThreeCardPoker(scanner);
        int userInput = threeCardPoker.getUserInput();
        assertEquals(1, userInput);
        assertTrue(outContent.toString().contains("Input is invalid. Please enter a 1 or a 2."));
    }

    @Test
    public void placeAnteMinimumValidAmountTest() throws IllegalBetException {
        // Arrange
        int minimumAnte = 1;

        // Act
        threeCardPoker.placeAnte(minimumAnte);

        // Assert
        assertEquals(minimumAnte, threeCardPoker.getAnte());
        assertEquals(999, threeCardPoker.getPlayer().getBankroll());
        assertEquals(1999, threeCardPoker.getDealer().getBankroll());
        assertEquals(2, threeCardPoker.getPot());
    }

    @Test
    public void getUserInputWithFloatingPointNumberTest() {
        // Arrange
        String input = "1.5\n2\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        threeCardPoker = new ThreeCardPoker(scanner);

        // Act
        int userInput = threeCardPoker.getUserInput();

        // Assert
        assertEquals(2, userInput);
        assertTrue(outContent.toString().contains("Input is invalid. Please enter a 1 or a 2."));
    }

    @Test
    public void getUserInputWithVeryLargeNumberTest() {
        // Arrange
        String input = "1000000\n2\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        threeCardPoker = new ThreeCardPoker(scanner);

        // Act
        int userInput = threeCardPoker.getUserInput();

        // Assert
        assertEquals(2, userInput);
        assertTrue(outContent.toString().contains("1000000 is invalid. Please enter a 1 or a 2."));
    }

    @Test
    public void getHandTypeTest() {
        // Test various hand score ranges
        assertEquals("Straight Flush", threeCardPoker.getHandType(1200));
        assertEquals("Three of a Kind", threeCardPoker.getHandType(900));
        assertEquals("Straight", threeCardPoker.getHandType(700));
        assertEquals("Flush", threeCardPoker.getHandType(500));
        assertEquals("Pair", threeCardPoker.getHandType(300));
        assertEquals("High Card", threeCardPoker.getHandType(100));
    }

    @Test
    public void compareHandsWithNearlyIdenticalScoresTest() {
        // Very close scores within the same hand type
        int playerHandScore = 601;
        int dealerHandScore = 600;

        assertTrue("Slightly higher score should win",
                threeCardPoker.compareHands(playerHandScore, dealerHandScore) > 0);
    }

    @Test
    public void placePlayBetExactlyEqualToAnteTest() throws IllegalBetException {
        // Arrange
        threeCardPoker.placeAnte(100);

        // Act
        threeCardPoker.placePlayBet(100);

        // Assert
        assertEquals(100, threeCardPoker.getPlayBet());
        assertEquals(800, threeCardPoker.getPlayer().getBankroll());
        assertEquals(1800, threeCardPoker.getDealer().getBankroll());
        assertEquals(400, threeCardPoker.getPot());
    }

    @Test
    public void resetGameStateTest() throws IllegalBetException {
        // Arrange
        threeCardPoker.placeAnte(100);
        threeCardPoker.placePlayBet(200);

        // Act
        threeCardPoker.resetGameState();

        // Assert
        assertEquals(0, threeCardPoker.getAnte());
        assertEquals(0, threeCardPoker.getPlayBet());
    }
}

