package module05.examples;

/**
 * Demonstrates widening conversions in Java.
 * A widening conversion (upcasting) changes a smaller primitive type to a larger one.
 * These are safe, performed automatically, but may lose precision in some cases.
 */
public class WideningDemo {
    public static void main(String[] args) {
        // 1. Complete chain of widening
        byte b = 42;
        short s = b;
        int i = s;
        long l = i;
        float f = l;
        double d = f;
        
        System.out.println("--- Widening Chain ---");
        System.out.println("byte: " + b);
        System.out.println("short: " + s);
        System.out.println("int: " + i);
        System.out.println("long: " + l);
        System.out.println("float: " + f);
        System.out.println("double: " + d);
        
        // 2. Widening with char
        char c = 'A';
        int charToInt = c;
        System.out.println("\nchar 'A' widened to int: " + charToInt); // 65
        
        // 3. Widening with precision loss (int -> float, long -> float/double)
        int hugeInt = 123_456_789;
        float preciseFloat = hugeInt;
        System.out.println("\n--- Precision Loss ---");
        System.out.println("Original int: " + hugeInt);
        // Note that 1.23456792E8 means the least significant digits are lost!
        System.out.println("Widened float: " + preciseFloat);
        
        long hugeLong = 9_223_372_036_854_775_807L;
        double preciseDouble = hugeLong;
        System.out.println("Original long: " + hugeLong);
        System.out.println("Widened double: " + preciseDouble);
    }
}
