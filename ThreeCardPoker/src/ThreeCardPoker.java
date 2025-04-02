import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Three Card Poker Game following rules in Readme.md file.
 * @author Katie Copeland
 * @version 2025.03.23
 */
public class ThreeCardPoker {
    private int ante;
    private int playBet;
    private int pot;
    private Player player;
    private Dealer dealer;
    private boolean folded;
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructor for ThreeCardPoker
     */
    public ThreeCardPoker()
    {
        pot = 0;
        folded = false;
        player = new Player();
        dealer = new Dealer(player.getBankroll() * 2);
    }

    /**
     * Overloaded Constructor for ThreeCardPoker tests
     */
    public ThreeCardPoker(Scanner scanner)
    {
        this.scanner = scanner;
        pot = 0;
        folded = false;
        player = new Player();
        dealer = new Dealer(player.getBankroll() * 2);
    }


    //Most getters and setters here are needed for testing only
    /**
     * Gets the pot
     * @return pot the money in the pot
     */
    public int getPot()
    {
        return pot;
    }

    /**
     * Gets if the hand has been folded or not
     * @return true if the hand has been folded, false otherwise
     */
    public boolean isFolded() {
        return folded;
    }

    /**
     * Set true if player folds, false otherwise
     * @param folded
     */
    protected void setFolded(boolean folded)
    {
        this.folded = folded;
    }

    /**
     * Gets the ante
     * @return the amount of the ante
     */
    public int getAnte() {
        return this.ante;
    }

    /**
     * Gets the playBet
     * @return the amount of the playBet
     */
    public  int getPlayBet(){return this.playBet;}

    /**
     * Gets the player
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Gets the dealer
     * @return the dealer
     */
    public Dealer getDealer() {
        return this.dealer;
    }
    /**
     * Places an ante for the player and the dealer's matches the players' ante
     * according to this version of Three Card Poker.
     *
     * @param ante The amount the player wishes to ante
     * @throws IllegalBetException If the bet is not a valid integer or if it is outside the allowable range.
     */
    public void placeAnte(int ante) throws IllegalBetException {
            if (ante > 0 && ante <= player.getBankroll()) {
                //Player ante valid
                player.setBankroll(player.getBankroll() - ante);
                // Dealer matches the player ante
                int dealerBet = ante;
                if (dealerBet > dealer.getBankroll()) {
                    dealerBet = dealer.getBankroll();
                    dealer.setBankroll(0);
                } else {
                    dealer.setBankroll(dealer.getBankroll() - dealerBet);
                }
                // Update the pot
                pot = pot + ante + dealerBet;
                //Save ante for use with other methods
                this.ante = ante;
            } else {
                //Player ante invalid
                throw new IllegalBetException("Illegal Ante. Ante must be greater than 0, less or equal to the bankroll," +
                        " and greater than or equal to player's bankroll.");
            }
    }
    /**
     * Places a bet for the player and the dealers' bet is matched to the players ante
     * according to this version of Three Card Poker.
     *
     * @param playBet The amount the player wishes to bet
     * @throws IllegalBetException If the bet is not a valid integer or if it is outside the allowable range.
     */
    public void placePlayBet(int playBet) throws IllegalBetException {
            if (playBet > 0 && playBet <= player.getBankroll() && playBet >= ante) {
                // Set the ante bet amount
                player.setBankroll(player.getBankroll() - playBet);
                // Dealer matches the ante bet only on playBets
                int dealerBet = ante;
                if (dealerBet > dealer.getBankroll()) {
                    dealerBet = dealer.getBankroll();
                    dealer.setBankroll(0);
                }
                else {
                    dealer.setBankroll(dealer.getBankroll() - dealerBet);
                }
                // Update the pot
                pot = pot + playBet + dealerBet;

                //Save playBet for use with other methods
                this.playBet = playBet;
            }
            else {
                throw new IllegalBetException("Illegal Bet. Bet must be greater than or equal to player's ante." +
                        "\nIf the player has nothing left to bet, the bet must = 0.");
            }
    }

    /**
     * Displays the main menu options to the player.
     */
    protected void displayMainMenu() {
        System.out.println("Three Card Poker");
        System.out.println("1. Play a Hand");
        System.out.println("2. Quit");
        System.out.println("Provide the number only for your selection:");
    }

    /**
     * Prompts the player to enter a valid ante and handles the ante logic.
     * Continues prompting until a valid ante is provided.
     */
    protected void inputValidAnte() {
        boolean validAnte = false;
        while (!validAnte) {
            try {
                printInfo();
                System.out.println("Place your ante: ");
                int ante = scanner.nextInt();
                placeAnte(ante);
                validAnte = true;
            } catch (IllegalBetException e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Deals cards to both the player and dealer according to Three Card Poker rules.
     * Each player receives three cards.
     */
    protected void dealCards() {
        player.getCardFromDealer(dealer.deal(), true);
        dealer.getCardFromDealer(dealer.deal(), false);
        player.getCardFromDealer(dealer.deal(), true);
        dealer.getCardFromDealer(dealer.deal(), false);
        player.getCardFromDealer(dealer.deal(), true);
        dealer.getCardFromDealer(dealer.deal(), true);
    }

    /**
     * Displays the current cards in both player's and dealer's hands.
     * Includes a pause for better user experience.
     */
    protected void displayHands() {
        System.out.println();
        pause();
        System.out.println(dealer.getHand());
        System.out.println(player.getHand());
    }

    /**
     * Presents fold or play options to the player and returns their choice.
     *
     * @return 1 for fold, 2 for play
     */
    protected int getFoldOrPlayChoice() {
        System.out.println();
        printInfo();
        System.out.println("1. Fold");
        System.out.println("2. Play");
        System.out.println("Provide the number only for your selection:");
        return getUserInput();
    }

    /**
     * Handles the logic when a player decides to fold.
     * Updates game state, scores the game, and displays results.
     */
    private void handlePlayerFold() {
        System.out.println("Player folds.");
        folded = true;
        pause();
        scoreGame();
        pause();
    }

    /**
     * Handles the logic when a player decides to place a play bet.
     * Collects the bet, scores the game, and displays results.
     */
    private void handlePlayerPlacedPlayBet() {
        inputValidPlayBet();
        pause();
        scoreGame();
        pause();
    }


    /**
     * Prompts the player to enter a valid play bet and handles the bet logic.
     * Continues prompting until a valid bet is provided.
     */
    protected void inputValidPlayBet() {
        boolean validPlayBet = false;
        folded = false;
        while (!validPlayBet) {
            try {
                printInfo();
                System.out.println("Place your play bet: ");
                int playBet = scanner.nextInt();
                if(isPlayerOutOfMoney(playBet)) {
                    validPlayBet = true;
                } else {
                    placePlayBet(playBet);
                    validPlayBet = true;
                }
            } catch (IllegalBetException e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Verifies the player has money left to bet.
     *
     * @param playBet The attempted play bet amount
     * @return true if player is out of money and bet is zero, false otherwise
     */
    protected boolean isPlayerOutOfMoney(int playBet) {
        if(player.getBankroll() == 0 && playBet == 0) {
            System.out.println(player.getName() + " has no money left to bet. " +
                    "Now scoring the game.");
            return true;
        }
        return false;
    }

    /**
     * Resets the game state for the next hand by clearing hands and resetting the deck.
     */
    protected void resetForNextHand() {
        player.clearHand();
        dealer.clearHand();
        dealer.resetDeck();
    }

    /**
     * Offers the player an option to start a new game after the current game is over.
     */
    private void offerNewGame() {
        System.out.println("Game Over! Do you want to start a new game?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("Provide the number of your selection:");
        int startOver = getUserInput();

        if(startOver == 1) {
            startGame();
        } else if (startOver == 2) {
            System.out.println("Thank you for playing Three Card Poker! Come back soon!");
        }
        scanner.close();
    }

    /**
     * Asks the player if they want to play another hand.
     *
     * @return true if player wants to quit, false if they want to continue
     */
    protected boolean askToPlayAnotherHand() {
        printInfo();
        System.out.println("Do you want to play another hand?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("Provide the number of your selection:");
        int continuePlaying = getUserInput();

        if (continuePlaying == 2) {
            System.out.println("Thank you for playing Three Card Poker! Come back soon!");
            return true;
        }
        return false;
    }

    /**
     * Starts the Three Card Poker game.
     */
    public void startGame() {
        boolean quit = false;

        // Only show the main menu at the very first iteration
        displayMainMenu();
        int menu = getUserInput();

        while (!quit) {
            if (menu == 1) {
                inputValidAnte();
                dealCards();
                displayHands();

                int playerChoice = getFoldOrPlayChoice();

                if (playerChoice == 1) {
                    handlePlayerFold();
                    if(declareWinner()) {
                        break; // Exit the game if bankrupt
                    }
                } else if (playerChoice == 2) {
                    handlePlayerPlacedPlayBet();
                    if(declareWinner()) {
                        break; // Exit the game if bankrupt
                    }
                }

                if (askToPlayAnotherHand()) {
                    break; // Quit if player doesn't want to continue
                }

                resetForNextHand();

                // For subsequent hands, don't show main menu again
                menu = 1;

            } else if (menu == 2) {
                System.out.println("Thank you for playing Three Card Poker! Come back soon!");
                quit = true;
            }
        }

        offerNewGame();
    }

    //Helper method for user input
    protected int getUserInput() {

        while (true) {
            try {
                int input = scanner.nextInt();
                if (input == 1 || input == 2) {
                    return input;
                } else {
                    System.out.println(input + " is invalid. Please enter a 1 or a 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input is invalid. Please enter a 1 or a 2.");
                scanner.next(); // clear the invalid input
            }
        }
    }

    /**
     * Pauses for 2 seconds.
     */
    private static void pause () {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Checks whether the player or dealer is bankrupt.
     * @return true if either the player or dealer is bankrupt, otherwise is false.
     */
    public boolean declareWinner()
    {
        // Check if either player or Dealer is bankrupt to determine winner
        if (player.getBankroll() <= 0) {
            printInfo();
            System.out.println(player.getName() + " is bankrupt. Dealer wins the game!");
            return true;
        } else if (dealer.getBankroll() <= 0) {
            printInfo();
            System.out.println("Dealer is bankrupt. " + player.getName() + " wins the game!");
            return true;
        }
        return false;
    }

    /**
     * Determines if the dealer's hand qualifies according to Three Card Poker rules.
     * Dealer qualifies if they have a Queen or higher.
     *
     * @return true if dealer qualifies, false otherwise
     */
    private boolean isDealerQualified() {
        return dealer.getHand().stream()
                .anyMatch(card -> card.getValue() >= 12);
    }

    /**
     * Displays the complete hands of both dealer and player.
     */
    private void displayResults() {
        dealer.showAllCards();
        System.out.println("Dealer's hand: " + dealer.getHand());
        System.out.println("Player's hand: " + player.getHand());
    }

    /**
     * Scores the current poker hand and determines the winner based on game rules.
     * This method handles different scenarios:
     * <ul>
     *   <li>If player folded: handles fold logic based on dealer qualification</li>
     *   <li>If player placed a bet: compares hands and determines winner if dealer qualifies</li>
     *   <li>If dealer doesn't qualify: handles special payout rules</li>
     * </ul>
     */
    public void scoreGame() {

        boolean dealerQualifies = isDealerQualified();
        displayResults();

        if (folded) {
            handlePlayerFolded(dealerQualifies);
        } else {
            int playerHandRanking = player.scoreHand();
            int dealerHandRanking = dealer.scoreHand();
            int handComparisonResult = compareHands(playerHandRanking, dealerHandRanking);

            handlePlayerBet(dealerQualifies, playerHandRanking, dealerHandRanking, handComparisonResult);
        }

        resetGameState();
    }

    /**
     * Determines the base hand type from the total hand score.
     * @param handScore the total hand score
     * @return the base hand type as a string
     */
    protected String getHandType(int handScore) {
        if (handScore >= 1000) return "Straight Flush";
        if (handScore >= 800) return "Three of a Kind";
        if (handScore >= 600) return "Straight";
        if (handScore >= 400) return "Flush";
        if (handScore >= 200) return "Pair";
        return "High Card";
    }
    /**
     * Compares two of this version of three card poker hands.
     * @param playerHandRanking the player's hand ranking
     * @param dealerHandRanking the dealer's hand ranking
     * @return a positive number if player wins, a negative number if dealer wins, 0 if tie
     */
    protected int compareHands(int playerHandRanking, int dealerHandRanking){
        // If hand types are different, compare based on hand type
        String playerHandType = getHandType(playerHandRanking);
        String dealerHandType = getHandType(dealerHandRanking);

        List<String> rankings = Arrays.asList("High Card", "Pair", "Flush", "Straight", "Three of a Kind", "Straight Flush");
        int playerRank = rankings.indexOf(playerHandType);
        int dealerRank = rankings.indexOf(dealerHandType);

        // If hand types are different, return the difference in rankings
        if (playerRank != dealerRank) {
            return Integer.compare(playerRank, dealerRank);
        }
        // If hand types are the same, compare the total scores
        return Integer.compare(playerHandRanking, dealerHandRanking);
    }

    /**
     * Resets game state variables after a hand is completed.
     */
    protected void resetGameState() {
        ante = 0;
        playBet = 0;
    }

    /**
     * Handles payout logic when the dealer does not qualify.
     */
    protected void handleDealerNotQualified() {
        System.out.println("Dealer does not qualify. Play bet is a Push!");
        player.setBankroll(player.getBankroll() + (ante * 2) + playBet);
        dealer.setBankroll(dealer.getBankroll() + ante);
        pot = 0;
    }

    /**
     * Handles the outcome when a player has folded.
     *
     * @param dealerQualifies Whether the dealer's hand qualifies
     */
    protected void handlePlayerFolded(boolean dealerQualifies) {
        if (dealerQualifies) {
            System.out.println("Player folded and Dealer qualifies, Dealer Wins!");
            dealer.setBankroll(dealer.getBankroll() + pot);
            pot = 0;
        } else {
            System.out.println("Player Folded and Dealer does not Qualify. Dealers' ante is returned.");
            System.out.println("Players' ante remains in the pot for another hand.");
            dealer.setBankroll(dealer.getBankroll() + ante);
            pot = pot - ante;
        }
    }

    /**
     * Handles the outcome when a player has placed a bet.
     *
     * @param dealerQualifies Whether the dealer's hand qualifies
     * @param playerHandRanking The ranking of the player's hand
     * @param dealerHandRanking The ranking of the dealer's hand
     * @param handComparisonResult The result of comparing the two hands
     */
    protected void handlePlayerBet(boolean dealerQualifies, int playerHandRanking, int dealerHandRanking, int handComparisonResult) {
        if (dealerQualifies) {
            String playerHandType = getHandType(playerHandRanking);
            String dealerHandType = getHandType(dealerHandRanking);

            if (handComparisonResult > 0) {
                System.out.println(player.getName() + " wins the hand with " + playerHandType + "!");
                player.setBankroll(player.getBankroll() + pot);
            } else if (handComparisonResult < 0) {
                System.out.println("Dealer wins the hand with " + dealerHandType + "!");
                dealer.setBankroll(dealer.getBankroll() + pot);
            } else {
                handlePush(playerHandType);
            }
            pot = 0;
        } else {
            handleDealerNotQualified();
        }
    }

    /**
     * Handles a tie (push) between player and dealer.
     *
     * @param handRanking The hand ranking that resulted in a tie
     */
    protected void handlePush(String handRanking) {
        System.out.println("This hand is a push with " + handRanking + "!");
        player.setBankroll(player.getBankroll() + ante + playBet);

        if (playBet > 0) {
            dealer.setBankroll(dealer.getBankroll() + ante + ante);
        } else {
            dealer.setBankroll(dealer.getBankroll() + ante);
        }
    }

    /**
     * Prints the PLayer, Dealer, and Pot information regarding money.
     */
    public void printInfo()
    {
        System.out.println("Player Bankroll: " + player.getBankroll());
        System.out.println("Dealer Bankroll: " + dealer.getBankroll());
        System.out.println("Pot: " + getPot());
    }
}
