package module07.examples;

/**
 * Demonstrates method parameter scope, local variable scope, definite assignment analysis,
 * and scope termination upon method return in Java 21 LTS.
 *
 * <p>Key concepts covered:
 * <ul>
 *   <li>Method parameters: accessible across the entire method body; cannot be redeclared.</li>
 *   <li>Local variables: scope begins strictly at declaration and extends to the end of the enclosing block.</li>
 *   <li>Definite assignment (JLS §16): local variables must be guaranteed to have a value before read.</li>
 *   <li>Conditional initialization and branch-definite assignment.</li>
 *   <li>Stack frame lifecycle: destruction of method scope upon return.</li>
 * </ul>
 *
 * @author Java Mastery Curriculum
 * @version 21.0
 */
public class MethodScope {

    public static void main(String[] args) {
        System.out.println("=== 1. Parameter and Local Scope ===");
        int result = calculateTax(1000, 0.15, true);
        System.out.println("Calculated tax: " + result);

        System.out.println("\n=== 2. Definite Assignment in Branches ===");
        demonstrateDefiniteAssignment(true);
        demonstrateDefiniteAssignment(false);

        System.out.println("\n=== 3. Scope and Early Return ===");
        String grade = evaluateScore(85);
        System.out.println("Grade: " + grade);

        System.out.println("\n=== 4. Recursive Frame Allocation ===");
        int fact5 = factorial(5);
        System.out.println("5! = " + fact5);
    }

    /**
     * Demonstrates that parameters 'baseAmount', 'rate', and 'applyDiscount'
     * are in scope for the entire method body.
     *
     * @param baseAmount    the starting amount
     * @param rate          tax percentage rate
     * @param applyDiscount whether discount applies
     * @return final tax value rounded to nearest integer
     */
    public static int calculateTax(int baseAmount, double rate, boolean applyDiscount) {
        // baseAmount, rate, applyDiscount are accessible from line 1 of the body.
        // Attempting to redeclare a parameter causes a compiler error:
        // int baseAmount = 50; // COMPILE ERROR: variable baseAmount is already defined

        double discount = 0.0; // Local variable declared here
        if (applyDiscount) {
            discount = 10.0;
            System.out.println("Discount applied: " + discount);
        }

        // Local variable 'tax' is computed from parameters and local variable
        double tax = (baseAmount * rate) - discount;
        return (int) Math.max(0, tax);
    } // Here, all parameters and locals (baseAmount, rate, applyDiscount, discount, tax) are popped

    /**
     * Demonstrates Java's compile-time Definite Assignment rules (JLS §16).
     * The compiler ensures every execution path assigns a value before reading.
     *
     * @param flag branch selector
     */
    public static void demonstrateDefiniteAssignment(boolean flag) {
        int assignedInBoth; // Declared without an initial value

        if (flag) {
            assignedInBoth = 100;
        } else {
            assignedInBoth = 200;
        }

        // Legal because javac proves 'assignedInBoth' is assigned on every branch.
        System.out.println("assignedInBoth is definitely assigned: " + assignedInBoth);

        // Conditional single branch initialization:
        int conditionallyAssigned;
        if (flag) {
            conditionallyAssigned = 42;
            // Reading here is safe and legal:
            System.out.println("Inside flag branch: conditionallyAssigned = " + conditionallyAssigned);
        }
        // Reading conditionallyAssigned here would fail compilation:
        // System.out.println(conditionallyAssigned);
        // Error: variable conditionallyAssigned might not have been initialized
    }

    /**
     * Demonstrates early return: upon return, execution terminates immediately,
     * discarding current stack frame locals.
     *
     * @param score test score (0-100)
     * @return letter grade
     */
    public static String evaluateScore(int score) {
        if (score < 0 || score > 100) {
            return "INVALID"; // Exits immediately; subsequent code and locals never reached
        }

        if (score >= 90) {
            String honorPrefix = "EXCELLENT: ";
            return honorPrefix + "A";
        }

        if (score >= 80) {
            return "B";
        }

        return "C or below";
    }

    /**
     * Demonstrates recursive method invocation. Each call allocates a NEW stack frame
     * with its own fresh copies of parameter 'n' and local frame storage.
     *
     * @param n non-negative integer
     * @return factorial of n
     */
    public static int factorial(int n) {
        if (n <= 1) {
            return 1;
        }
        // 'n' in this frame is preserved while a new frame for factorial(n - 1) executes.
        return n * factorial(n - 1);
    }
}
