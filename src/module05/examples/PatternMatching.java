package module05.examples;

/**
 * Demonstrates modern Java Pattern Matching for instanceof and switch.
 * Targets Java 21 features.
 */
public class PatternMatching {
    public static void main(String[] args) {
        Object strObj = "Hello Pattern Matching";
        Object intObj = 42;
        Object nullObj = null;
        
        // 1. Pattern Matching for instanceof (Java 16+)
        System.out.println("--- instanceof Pattern Matching ---");
        if (strObj instanceof String s) {
            // No explicit cast required!
            System.out.println("Length is: " + s.length());
        }
        
        // 2. Pattern Matching for switch (Java 21+)
        System.out.println("\n--- Switch Pattern Matching ---");
        System.out.println(process(strObj));
        System.out.println(process(intObj));
        System.out.println(process(nullObj));
    }
    
    /**
     * Uses Java 21 switch expressions with pattern matching.
     */
    static String process(Object obj) {
        return switch (obj) {
            case Integer i -> "It's an integer: " + i;
            case String s -> "It's a string of length: " + s.length();
            case null -> "It's a null value!";
            default -> "Unknown object type";
        };
    }
}
