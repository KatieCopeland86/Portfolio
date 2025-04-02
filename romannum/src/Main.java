public class Main {
    public static void main(String[] args)
    {
        RomanNumeral solution = new RomanNumeral();
        RNToInt revert = new RNToInt();
        String str = solution.intToRomanNumeral(2777);
        System.out.println(str);
        System.out.println(revert.romanToInt(str));

    }
}
