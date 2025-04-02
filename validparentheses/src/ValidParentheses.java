import java.util.Stack;
class ValidParentheses{

    ValidParentheses()
    {
        System.out.println(STR."() is Valid \{isValid("()")}");
        System.out.println(STR."()[]{} is Valid \{isValid("()[]{}")}");
        System.out.println(STR."(] is Valid \{isValid("(]")}");
        System.out.println(STR."([]) is Valid \{isValid("([])")}");

    }
    public boolean isValid(String s) {
        Stack<Character> stringStack = new Stack<>();

        for (int i = 0; i < s.length();  i++)
        {
            char character = s.charAt(i);
            if ((character == '(') || (character == '{') || (character == '['))
            {
                stringStack.push(character);
            }
            else if ((character == ')') || (character == '}') || (character == ']')) {
                if (stringStack.isEmpty()) {
                    return false;
                }
                char charAtTop = stringStack.pop();
                if (((character == ')') && charAtTop != '(') || ((character == '}') && charAtTop != '{') || ((character == ']') && charAtTop != '['))
                    return false;
            }

        }

        return stringStack.isEmpty();
    }
    public static void main(String[] args) {
        new ValidParentheses();  // Calling the constructor to run test cases
    }
}
