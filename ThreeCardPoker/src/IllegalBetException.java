/**
 * IllegalBetException - represents an illegal ante or bet in three card poker
 * @author Katie Copeland
 * @version 2024.06.13
 */
public class IllegalBetException extends Exception {
    protected String message;

    IllegalBetException(String message)
    {
        super(message);
        this.message = message;
    }

    @Override
    public String toString()
    {
        return("Ante or Play Bet Exception Occurred: " + message);
    }

}
