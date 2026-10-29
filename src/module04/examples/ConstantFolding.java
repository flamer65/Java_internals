package module04.examples;

/**
 * Demonstrates compile-time constant folding.
 */
public class ConstantFolding {
    public static void main(String[] args) {
        // Folded into a single "ABC" at compile time
        String s = "A" + "B" + "C";
        
        // Folded into 42 at compile time
        int x = 10 + 32;

        System.out.println("Folded string: " + s);
        System.out.println("Folded int: " + x);
    }
}
