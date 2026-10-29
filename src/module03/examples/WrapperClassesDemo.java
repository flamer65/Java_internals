package module03.examples;

/**
 * Demonstrates the usage of wrapper classes in Java.
 * Covers creation, parsing, and conversion.
 */
public class WrapperClassesDemo {

    @SuppressWarnings("removal")
    public static void main(String[] args) {
        System.out.println("--- Wrapper Classes Demo ---");

        // 1. Wrapper Creation
        // Recommended: Using valueOf() - enables caching
        Integer cachedInt = Integer.valueOf(42);
        
        // Deprecated (since Java 9): Using constructor
        Integer constructedInt = new Integer(42); 
        
        System.out.println("valueOf(42) == new Integer(42): " + (cachedInt == constructedInt)); // false, different objects

        // 2. Parsing Strings
        try {
            int parsedInt = Integer.parseInt("123");
            double parsedDouble = Double.parseDouble("3.14");
            System.out.println("Parsed int: " + parsedInt);
            System.out.println("Parsed double: " + parsedDouble);
            
            // This will throw NumberFormatException
            Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            System.out.println("Error parsing invalid string: " + e.getMessage());
        }

        // 3. String Conversion
        String intStr = String.valueOf(42);
        String boolStr = String.valueOf(true);
        System.out.println("int as String: " + intStr);
        System.out.println("boolean as String: " + boolStr);
    }
}
