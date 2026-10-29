package module05.examples;

/**
 * Demonstrates conversions between Strings and primitives,
 * and safe parsing mechanisms for user input.
 */
public class StringConversion {
    public static void main(String[] args) {
        // 1. Primitive to String (Concatenation)
        String concat1 = "" + 42;
        String concat2 = "" + null;
        String concat3 = "" + new Object();
        
        System.out.println("--- Concatenation ---");
        System.out.println(concat1);
        System.out.println(concat2);
        System.out.println(concat3); // prints java.lang.Object@...
        
        // 2. String to Primitive (Parsing)
        System.out.println("\n--- Parsing Strings ---");
        int num = Integer.parseInt("123");
        double d = Double.parseDouble("3.14");
        boolean b = Boolean.parseBoolean("true");
        System.out.println("Parsed: " + num + ", " + d + ", " + b);
        
        // 3. Exception Handling during Parsing
        System.out.println("\n--- Safe Parsing ---");
        System.out.println("Parsing 'abc': " + safeParseInt("abc", -1));
        System.out.println("Parsing '404': " + safeParseInt("404", -1));
    }
    
    /**
     * Safely converts user input string to int with a fallback default.
     */
    public static int safeParseInt(String input, int defaultVal) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }
}
