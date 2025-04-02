import java.util.LinkedHashMap;
import java.util.Map;

public class RomanNumeral {
    // Create a map to store Roman numeral symbols and their corresponding integer values
    static Map<Integer, String> RomanNumerals = new LinkedHashMap<>();

    // Static block to populate the map with Roman numeral values and their symbols
    static {
        // The map contains the Roman numeral symbols in descending order of their values
        // In Roman numerals, you start with the largest possible value and then work your way down.
        // It is done this way so that the number is represented using the fewest symbols possible and
        // follows the standard rules of Roman numeral representation.
        // Included Subtractive notation so that way 4 doesn't get input as IIII etc.
        RomanNumerals.put(1000, "M");   // 1000 -> M
        RomanNumerals.put(900, "CM");   // 900 -> CM
        RomanNumerals.put(500, "D");    // 500 -> D
        RomanNumerals.put(400, "CD");   // 400 -> CD
        RomanNumerals.put(100, "C");    // 100 -> C
        RomanNumerals.put(90, "XC");    // 90 -> XC
        RomanNumerals.put(50, "L");     // 50 -> L
        RomanNumerals.put(40, "XL");    // 40 -> XL
        RomanNumerals.put(10, "X");     // 10 -> X
        RomanNumerals.put(9, "IX");     // 9 -> IX
        RomanNumerals.put(5, "V");      // 5 -> V
        RomanNumerals.put(4, "IV");     // 4 -> IV
        RomanNumerals.put(1, "I");      // 1 -> I
    }

    // Method to convert an integer (num) to its Roman numeral equivalent
    public String intToRomanNumeral(int num) {
        // Use StringBuilder to construct the Roman numeral string
        StringBuilder result = new StringBuilder();

        // Check if the input number is valid for Roman numerals (between 1 and 3999)
        if (num <= 3999 && num >= 1) {
            // Loop through each Roman numeral value in the map (starting from the largest)
            for (Map.Entry<Integer, String> startingPoint : RomanNumerals.entrySet()) {
                int currentValue = startingPoint.getKey();  // Get the integer value (e.g., 1000)
                String romanNumeral = startingPoint.getValue();  // Get the Roman symbol (e.g., "M")

                // Repeat the process of subtracting the value from num and adding the Roman symbol
                // to the result until num is smaller than the current value
                while (num >= currentValue) {
                    result.append(romanNumeral);  // Append the Roman symbol to the result string
                    num -= currentValue;  // Subtract the current value from the input number
                }
            }
        } else {
            // If the input number is not in the valid range, print an error message
            System.out.println("Invalid number: Roman numerals start at 1 and stop at 3999.");
        }

        // Return the final Roman numeral as a string
        return result.toString();
    }
}

