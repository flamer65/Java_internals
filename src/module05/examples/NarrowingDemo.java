package module05.examples;

/**
 * Demonstrates narrowing conversions (explicit casting).
 * Narrowing takes a larger primitive and fits it into a smaller type,
 * requiring a cast operator () and potentially resulting in data loss.
 */
public class NarrowingDemo {
    public static void main(String[] args) {
        // 1. Truncation and Wrapping
        int largeInt = 300;
        byte narrowedByte = (byte) largeInt;
        
        System.out.println("--- Overflow / Truncation ---");
        System.out.println("Original int: " + largeInt);
        System.out.println("Narrowed byte: " + narrowedByte); // Output: 44 (300 % 256)
        
        // 2. Floating-point to Integer truncation
        float fVal = 9.99f;
        int narrowedInt = (int) fVal;
        System.out.println("\n--- Floating Point Truncation ---");
        System.out.println("Original float: " + fVal);
        System.out.println("Narrowed int: " + narrowedInt); // Output: 9 (decimals truncated)
        
        // 3. Narrowing out of bounds values
        System.out.println("\n--- Special Floating Point Cases ---");
        System.out.println("NaN to int: " + (int) Double.NaN); // 0
        System.out.println("Infinity to int: " + (int) Double.POSITIVE_INFINITY); // MAX_VALUE
        
        // 4. Narrowing into char
        int asciiValue = 66;
        char c = (char) asciiValue;
        System.out.println("\nInt to char: " + c); // 'B'
        
        // 5. Chained casting edge case
        long complexValue = 42L;
        byte chainResult = (byte)(short)(int) complexValue;
        System.out.println("\nChained cast result: " + chainResult); // 42
    }
}
