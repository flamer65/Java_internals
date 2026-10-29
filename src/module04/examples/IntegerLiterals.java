package module04.examples;

/**
 * Demonstrates all integer literal bases in Java.
 */
public class IntegerLiterals {
    public static void main(String[] args) {
        int dec = 42;       // Decimal
        int bin = 0b101010; // Binary
        int oct = 052;      // Octal
        int hex = 0x2A;     // Hexadecimal

        System.out.println("Decimal: " + dec);
        System.out.println("Binary (0b101010): " + bin);
        System.out.println("Octal (052): " + oct);
        System.out.println("Hex (0x2A): " + hex);

        // Long literal requiring 'L'
        long maxVal = 2_147_483_648L; 
        System.out.println("Long with L suffix: " + maxVal);
    }
}
