import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

// Testing Enums is typically unnecessary unless there is additional logic or methods included in the enum class,
// testing here was included to demonstrate completeness.
public class SuitTest {
    @Test
    public void testEnumSuit()
    {
        // Arrange - Create an array of suit values in the expected order
        Suit[] expectedSuits = {Suit.Clubs, Suit.Spades, Suit.Hearts, Suit.Diamonds};
        // Act - Get the actual suit values
        Suit[] actualSuits = Suit.values();
        // Assert - Verify the order and number of suits
        assertArrayEquals(expectedSuits, actualSuits);
        assertEquals(4, actualSuits.length);
    }
}
