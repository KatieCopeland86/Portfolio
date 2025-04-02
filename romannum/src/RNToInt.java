import java.util.HashMap;
import java.util.Map;

// Taken from
// https://medium.com/strategio/hashmaps-roman-numerals-you-a-simple-coding-challenge-e3bdf6b96e1f
// Just to use to verify my conversion worked properly
public class RNToInt {
    static Map<Character, Integer> RomanNumerals = new HashMap<>();

    public int romanToInt(String roman) {
        // BUILD ROMAN NUMERAL HASHMAP
        RomanNumerals.put('I', 1);
        RomanNumerals.put('V', 5);
        RomanNumerals.put('X', 10);
        RomanNumerals.put('L', 50);
        RomanNumerals.put('C', 100);
        RomanNumerals.put('D', 500);
        RomanNumerals.put('M', 1000);
        // INITIALIZE RESULT TO ZERO
        int result = 0;

        for (int i = 0; i < roman.length(); i++) {
            // SUBTRACT INT EQUIVALENT TO CHARACTER FROM HASHMAP IF THE NEXT
            // CHARACTER HAS A HIGHER VALUE
            if (i < roman.length() - 1 &&
                    RomanNumerals.get(roman.charAt(i + 1)) > RomanNumerals.get(roman.charAt(i)))
                result -= RomanNumerals.get(roman.charAt(i));
            else result += RomanNumerals.get(roman.charAt(i));
            // OTHERWISE ADD INT EQUIVALENT TO CHARACTER FROM HASHMAP
        }

        return result;
    }
}
