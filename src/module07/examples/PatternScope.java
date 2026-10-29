package module07.examples;

/**
 * Demonstrates pattern matching variable scoping and flow-sensitive typing
 * introduced in Java 16+ and Java 21 LTS (JEP 394 and JEP 441).
 *
 * <p>Key JVM and Compiler concepts covered:
 * <ul>
 *   <li>Flow-sensitive scoping: A pattern variable is in scope only where javac proves
 *       that the pattern matched with certainty.</li>
 *   <li>Conditional AND ({@code &&}): Pattern variable is in scope on the right-hand operand.</li>
 *   <li>Conditional OR ({@code ||}): Pattern variable is NOT in scope on the right-hand operand
 *       because the left operand evaluating to false does not guarantee a match.</li>
 *   <li>Negated conditions and early exit: When {@code !(obj instanceof Type t)} is guarded by
 *       a branch that returns or throws, {@code t} is in scope for the remainder of the block.</li>
 *   <li>Pattern matching in {@code switch} (Java 21 LTS): Pattern variables are scoped strictly
 *       to their respective case arms.</li>
 * </ul>
 *
 * @author Java Mastery Curriculum
 * @version 21.0
 */
public class PatternScope {

    public static void main(String[] args) {
        demonstrateAndFlowScope("Open Source Java");
        demonstrateAndFlowScope(42);

        demonstrateEarlyExitScope("Microservice Architecture");
        demonstrateEarlyExitScope(999);

        demonstrateSwitchPatternScope("Java 21 LTS");
        demonstrateSwitchPatternScope(100.50);
        demonstrateSwitchPatternScope(null);
    }

    /**
     * Demonstrates flow-sensitive scope using {@code &&}.
     * The pattern variable 's' is only in scope on the RHS of '&&' and inside the 'if' body.
     *
     * @param obj target object
     */
    public static void demonstrateAndFlowScope(Object obj) {
        System.out.println("=== 1. Pattern Variable Scope with '&&' ===");

        // 's' is in scope on RHS of && because 'obj instanceof String' must be true
        if (obj instanceof String s && s.length() > 5) {
            System.out.println("Matched long string: \"" + s + "\" with length " + s.length());
        } else {
            // 's' is NOT in scope here!
            System.out.println("Did not match or string length <= 5: " + obj);
            // System.out.println(s); // COMPILE ERROR: cannot find symbol variable s
        }

        // Note on '||':
        // if (obj instanceof String s || s.length() > 5) { ... }
        // COMPILE ERROR: cannot find symbol variable s on RHS of ||
    }

    /**
     * Demonstrates flow scoping with negated condition and early return.
     * Since execution returns if 'obj' is NOT a String, 'str' is guaranteed to be
     * assigned and in scope for all subsequent statements in the method.
     *
     * @param obj target object
     */
    public static void demonstrateEarlyExitScope(Object obj) {
        System.out.println("\n=== 2. Pattern Variable Scope with Early Exit ===");

        if (!(obj instanceof String str)) {
            System.out.println("Guard clause: obj is not a String. Exiting early.");
            return;
        }

        // Flow-sensitive analysis guarantees 'str' is initialized and in scope here:
        System.out.println("Post-guard execution! Length of '" + str + "' is: " + str.length());
        System.out.println("Uppercase: " + str.toUpperCase());
    }

    /**
     * Demonstrates pattern matching in switch statements (Java 21 LTS feature).
     * Pattern variables are isolated to their specific switch case branch.
     *
     * @param obj target object
     */
    public static void demonstrateSwitchPatternScope(Object obj) {
        System.out.println("\n=== 3. Pattern Matching in Switch (Java 21 LTS) ===");

        switch (obj) {
            case null -> System.out.println("Received null pointer.");
            case String s when s.isBlank() ->
                System.out.println("String pattern matched: Blank String of length " + s.length());
            case String s ->
                System.out.println("String pattern matched: \"" + s + "\" (upper: " + s.toUpperCase() + ")");
            case Number num ->
                System.out.println("Number pattern matched: double value = " + num.doubleValue());
            default ->
                System.out.println("Default fallback for: " + obj.getClass().getSimpleName());
        }
    }
}
