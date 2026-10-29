package module03.examples;

/**
 * Demonstrates the char type as a 16-bit numeric type and Unicode support.
 */
public class CharUnicodeDemo {

    public static void main(String[] args) {
        System.out.println("--- Char and Unicode Demo ---");

        // 1. Char as numeric
        char c = 'A';
        int numericValue = c; // Implicit cast to int
        System.out.println("'A' as integer: " + numericValue);

        // 2. Char arithmetic
        int nextCharInt = c + 1; // char + int -> int
        char nextChar = (char) (c + 1); // Need to cast back to char
        System.out.println("'A' + 1 = " + nextCharInt + " (int), '" + nextChar + "' (char)");

        // 3. Null character
        char nullChar = 0;
        System.out.println("Null character (integer value): " + (int) nullChar);

        // 4. Unicode assignment
        char smiley = '\u263A';
        System.out.println("Unicode smiley: " + smiley);
        
        // 5. Surrogate Pairs (Emojis outside BMP)
        // Some emojis require two chars (a String) because they exceed 16 bits
        String rocket = "\uD83D\uDE80"; 
        System.out.println("Rocket (Surrogate pair): " + rocket);
        System.out.println("Rocket length in chars: " + rocket.length());
    }
}
