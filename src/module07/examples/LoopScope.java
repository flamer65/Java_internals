package module07.examples;

import java.util.List;

/**
 * Demonstrates variable scoping within loops: {@code for}, multi-variable {@code for},
 * {@code while}, {@code do-while}, and enhanced {@code for-each} in Java 21 LTS.
 *
 * <p>Key concepts covered:
 * <ul>
 *   <li>Standard {@code for} loop: loop control variable is scoped strictly to the loop header and body.</li>
 *   <li>Sequential loop variable reuse: re-declaring index variables across adjacent loops.</li>
 *   <li>Multi-variable {@code for} loop: comma-separated declarations sharing type and scope.</li>
 *   <li>{@code while} loop: body variables are re-instantiated conceptually on each iteration.</li>
 *   <li>{@code do-while} gotcha: the condition {@code while(expr)} cannot access variables declared in the body block.</li>
 *   <li>Enhanced {@code for} loop: iteration variable is effectively local to each loop cycle.</li>
 * </ul>
 *
 * @author Java Mastery Curriculum
 * @version 21.0
 */
public class LoopScope {

    public static void main(String[] args) {
        demonstrateForLoopScope();
        demonstrateSequentialForReuse();
        demonstrateMultiVariableFor();
        demonstrateWhileLoopScope();
        demonstrateDoWhileScopeGotcha();
        demonstrateEnhancedForScope();
    }

    /**
     * Demonstrates that the loop control variable {@code i} is only valid inside the loop header and body.
     */
    public static void demonstrateForLoopScope() {
        System.out.println("=== 1. Standard For Loop Scope ===");
        int sum = 0;

        for (int i = 1; i <= 5; i++) {
            sum += i;
            System.out.println("Iteration i = " + i + ", running sum = " + sum);
        }

        System.out.println("Final sum: " + sum);
        // Outside the loop, 'i' does not exist:
        // System.out.println(i); // COMPILE ERROR: cannot find symbol variable i
    }

    /**
     * Demonstrates sequential loop reuse where identical variable names (e.g., {@code i})
     * are legally declared in independent loops within the same method.
     */
    public static void demonstrateSequentialForReuse() {
        System.out.println("\n=== 2. Sequential For Loop Variable Reuse ===");

        System.out.print("First loop (counting up): ");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Legal because the previous 'i' has ceased to exist
        System.out.print("Second loop (counting down): ");
        for (int i = 5; i > 2; i--) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    /**
     * Demonstrates a multi-variable for loop header where multiple variables
     * of the same type are declared, initialized, and stepped together.
     */
    public static void demonstrateMultiVariableFor() {
        System.out.println("\n=== 3. Multi-Variable For Loop Header ===");

        // i and j are both scoped to this for loop only
        for (int i = 0, j = 10; i < j; i++, j--) {
            System.out.println("i = " + i + ", j = " + j + " -> diff = " + (j - i));
        }

        // Neither 'i' nor 'j' is accessible here
    }

    /**
     * Demonstrates scope inside a while loop body.
     * Variables declared inside the body block have their scope limited to a single iteration.
     */
    public static void demonstrateWhileLoopScope() {
        System.out.println("\n=== 4. While Loop Body Scope ===");
        int count = 0;

        while (count < 3) {
            // 'message' is re-declared in every pass through the loop body
            String message = "Pass #" + count;
            int iterationSecret = count * 100;
            System.out.println(message + " (Secret: " + iterationSecret + ")");
            count++;
        }

        // 'message' and 'iterationSecret' are not accessible here
    }

    /**
     * Demonstrates the subtle scoping rule of do-while loops:
     * The evaluation expression in {@code while(condition)} is outside the body block {@code { ... }}.
     */
    public static void demonstrateDoWhileScopeGotcha() {
        System.out.println("\n=== 5. Do-While Scope Gotcha ===");

        // If 'flag' were declared inside the { }, the condition while(flag) could NOT see it!
        // do {
        //     boolean flag = false;
        // } while (flag); // COMPILE ERROR: cannot find symbol variable flag

        // Correct approach: declare outside the do-while block
        boolean continueLoop;
        int step = 0;
        do {
            step++;
            continueLoop = (step < 3);
            System.out.println("Do-while step " + step + ", continue = " + continueLoop);
        } while (continueLoop);
    }

    /**
     * Demonstrates enhanced for-each loop scoping.
     * The iteration element is scoped strictly to the loop body.
     */
    public static void demonstrateEnhancedForScope() {
        System.out.println("\n=== 6. Enhanced For-Each Scope ===");
        List<String> frameworkNames = List.of("Spring", "Quarkus", "Micronaut");

        for (String framework : frameworkNames) {
            System.out.println("Framework: " + framework.toUpperCase());
        }

        // 'framework' is out of scope here
    }
}
