package module03.examples;

/**
 * Demonstrates integer overflow/underflow and how to prevent it
 * using Math exact methods.
 */
public class OverflowDemo {

    public static void main(String[] args) {
        System.out.println("--- Integer Overflow Demo ---");

        // 1. Silent Overflow
        int max = Integer.MAX_VALUE;
        System.out.println("Integer.MAX_VALUE: " + max);
        
        int overflowed = max + 1; // Wraps around to MIN_VALUE silently
        System.out.println("max + 1 (Overflow): " + overflowed);

        // 2. Using Math.addExact to detect overflow
        System.out.println("\nUsing Math.addExact(max, 1):");
        try {
            Math.addExact(max, 1);
        } catch (ArithmeticException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }

        // 3. Underflow
        int min = Integer.MIN_VALUE;
        System.out.println("\nInteger.MIN_VALUE: " + min);
        System.out.println("min - 1 (Underflow): " + (min - 1));
        
        // 4. Type promotion avoiding overflow
        long noOverflow = (long) max + 1;
        System.out.println("Casting to long before addition: " + noOverflow);
    }
}
