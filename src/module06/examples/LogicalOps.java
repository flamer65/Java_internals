package module06.examples;

/**
 * Demonstrates short-circuit vs non-short-circuit logical operations.
 */
public class LogicalOps {
    public static void main(String[] args) {
        // Short-circuit
        boolean b1 = false && sideEffect("&&");
        System.out.println("Short-circuit result: " + b1);
        
        // Non short-circuit
        boolean b2 = false & sideEffect("&");
        System.out.println("Non short-circuit result: " + b2);
    }
    
    private static boolean sideEffect(String op) {
        System.out.println("Side effect for " + op);
        return true;
    }
}
