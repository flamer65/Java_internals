package module06.examples;

/**
 * Demonstrates all arithmetic operations, division edge cases, and modulus.
 */
public class ArithmeticOps {
    public static void main(String[] args) {
        int a = 10, b = 3;
        System.out.println("Addition: " + (a + b)); // 13
        System.out.println("Subtraction: " + (a - b)); // 7
        System.out.println("Multiplication: " + (a * b)); // 30
        System.out.println("Division: " + (a / b)); // 3
        System.out.println("Modulus: " + (a % b)); // 1
        
        System.out.println("Float Division: " + (10.0 / 3.0));
        
        // Edge cases
        System.out.println("Div by zero (float): " + (10.0 / 0)); // Infinity
        System.out.println("Overflow min val div -1: " + (Integer.MIN_VALUE / -1));
    }
}
