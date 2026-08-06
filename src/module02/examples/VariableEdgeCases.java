package module02.examples;

/**
 * Edge cases: var as identifier, char as number, overflow, underscore variable.
 */
public class VariableEdgeCases {
    public static void main(String[] args) {
        // 1. var as an identifier
        // 'var' is a restricted local variable type identifier, not a reserved keyword!
        int var = 42; 
        System.out.println("Variable named 'var': " + var);
        
        // 2. char as a numeric type
        // chars are just 16-bit unsigned integers under the hood
        char c = 65; 
        System.out.println("Numeric char 65 prints as: " + c); // Prints 'A'
        
        // 3. Integer Overflow
        // Java does not throw an exception on integer overflow; it wraps around silently.
        int max = Integer.MAX_VALUE;
        int overflowed = max + 1;
        System.out.println("Max int: " + max);
        System.out.println("Overflowed (Max + 1): " + overflowed); // Prints -2147483648
        
        // 4. Underscore in numeric literals (Java 7+)
        // Improves readability for large numbers
        long millions = 1_000_000_000L;
        System.out.println("Millions with underscores: " + millions);
        
        // Note: '_' as a variable name was valid but is a keyword/restricted in newer Java versions.
        // int _ = 5; // COMPILER ERROR in Java 9+
    }
}
