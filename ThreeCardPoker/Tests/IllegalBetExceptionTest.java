import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IllegalBetExceptionTest {

    @Test
    public void testExceptionConstructor() {
        // Arrange
        String errorMessage = "Test error";
        // Act
        IllegalBetException exception = new IllegalBetException(errorMessage);
        // Assert
        assertEquals(errorMessage, exception.message);
    }

    @Test
    public void testExceptionMessage() {
        // Arrange
        String errorMessage = "Invalid amount";
        // Act
        IllegalBetException exception = new IllegalBetException(errorMessage);
        // Assert
        assertEquals(errorMessage, exception.message);
    }

    @Test
    public void testToString() {
        // Arrange
        String errorMessage = "Invalid amount";
        String expectedString = "Ante or Play Bet Exception Occurred: " + errorMessage;
        // Act
        IllegalBetException exception = new IllegalBetException(errorMessage);
        // Assert
        assertEquals(expectedString, exception.toString());
    }
    @Test
    public void testExceptionMessageWithEmptyString() {
        // Arrange
        String errorMessage = "";
        // Act
        IllegalBetException exception = new IllegalBetException(errorMessage);
        // Assert
        assertEquals("Ante or Play Bet Exception Occurred: ", exception.toString());
    }
    @Test
    public void testExceptionMessageWithNullString() {
        // Arrange
        String errorMessage = null;
        // Act
        IllegalBetException exception = new IllegalBetException(errorMessage);
        // Assert
        assertEquals("Ante or Play Bet Exception Occurred: null", exception.toString());
    }

    @Test
    public void testExceptionMessageWithSpecialCharacters() {
        // Arrange
        String errorMessage = "Invalid bet: $1000";
        // Act
        IllegalBetException exception = new IllegalBetException(errorMessage);
        // Assert
        assertEquals("Ante or Play Bet Exception Occurred: Invalid bet: $1000", exception.toString());
    }

    @Test
    public void testExceptionInheritance() {
        // Arrange
        String errorMessage = "Test error";
        // Act
        IllegalBetException exception = new IllegalBetException(errorMessage);
        // Assert
        assertTrue(exception instanceof Exception);
    }

    @Test
    public void testExceptionHandling() {
        try {
            throw new IllegalBetException("Test exception");
        } catch (IllegalBetException e) {
            assertEquals("Ante or Play Bet Exception Occurred: Test exception", e.toString());
        }
    }
}
