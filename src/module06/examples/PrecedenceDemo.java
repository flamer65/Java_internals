package module06.examples;

/**
 * Demonstrates complex expressions to prove precedence rules.
 */
public class PrecedenceDemo {
    public static void main(String[] args) {
        int result1 = 2 + 3 * 4 - 1; 
        int result2 = (2 + (3 * 4)) - 1;
        System.out.println("Precedence match: " + (result1 == result2)); // true
        
        int a = 1, b = 2, c = 3, d = 4;
        int x = a++ + ++b * c-- - --d; 
        // 1 + 3 * 3 - 3 = 7
        System.out.println("Complex expression result: " + x);
        
        System.out.println("String concat left-to-right:");
        System.out.println(1 + 2 + "3" + 4 + 5); // "3345"
    }
}
