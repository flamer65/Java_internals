package module07.examples;

/**
 * Demonstrates block scoping rules in Java 21 LTS.
 *
 * <p>Key concepts covered:
 * <ul>
 *   <li>Basic block scope: variables declared inside a block {@code { ... }} are limited to that block.</li>
 *   <li>Nested blocks: inner blocks can access enclosing variables, but cannot redeclare them.</li>
 *   <li>Sequential blocks: sibling blocks can reuse variable identifiers without collision.</li>
 *   <li>Standalone blocks: creating localized scopes to restrict variable lifetime and assist JVM slot reuse.</li>
 *   <li>Empty blocks: syntactic validity of {@code { }}.</li>
 * </ul>
 *
 * @author Java Mastery Curriculum
 * @version 21.0
 */
public class BlockScope {

    public static void main(String[] args) {
        demonstrateBasicBlockScope();
        demonstrateNestedBlockScope();
        demonstrateSequentialBlocks();
        demonstrateStandaloneBlockSlotReuse();
        demonstrateEmptyBlock();
    }

    /**
     * Demonstrates that variables declared inside an arbitrary block are inaccessible
     * once control leaves that block.
     */
    public static void demonstrateBasicBlockScope() {
        System.out.println("=== 1. Basic Block Scope ===");
        int outerVar = 10;

        {
            // Block begins
            int innerVar = 20;
            System.out.println("Inside block: outerVar = " + outerVar + ", innerVar = " + innerVar);
            // innerVar is live and in scope here
        } // Block ends: innerVar's scope terminates here

        System.out.println("Outside block: outerVar = " + outerVar);
        // The following line would cause a compile-time error:
        // System.out.println(innerVar); // error: cannot find symbol variable innerVar
    }

    /**
     * Demonstrates nested scopes: inner blocks can read outer block variables,
     * but Java forbids shadowing or redeclaring an outer local variable.
     */
    public static void demonstrateNestedBlockScope() {
        System.out.println("\n=== 2. Nested Block Scope ===");
        int level1 = 100;

        {
            int level2 = 200;
            System.out.println("Inner Level 2: level1 = " + level1 + ", level2 = " + level2);

            {
                int level3 = 300;
                System.out.println("Innermost Level 3: level1 = " + level1
                        + ", level2 = " + level2 + ", level3 = " + level3);

                // Note: C/C++ permits shadowing outer local variables in an inner block.
                // Java strictly forbids this to prevent subtle bugs:
                // int level1 = 999; // COMPILE ERROR: variable level1 is already defined in method
            }
            // level3 is out of scope here
        }
        // level2 is out of scope here
    }

    /**
     * Demonstrates sequential (sibling) blocks where the same variable name can be
     * reused because each block has an independent lifetime and lexical scope.
     */
    public static void demonstrateSequentialBlocks() {
        System.out.println("\n=== 3. Sequential Blocks Scope ===");

        // First sibling block
        {
            String status = "ACTIVE";
            int count = 42;
            System.out.println("Block A: status = " + status + ", count = " + count);
        }

        // Second sibling block: redeclaring 'status' and 'count' is completely legal
        // because the previous variables' scopes have closed.
        {
            int status = 200; // Even the type can be different!
            double count = 99.5;
            System.out.println("Block B: status = " + status + ", count = " + count);
        }
    }

    /**
     * Demonstrates standalone blocks used to control variable lifetime and limit
     * potential leakage of intermediate scratch variables.
     */
    public static void demonstrateStandaloneBlockSlotReuse() {
        System.out.println("\n=== 4. Standalone Blocks for Lifetime Control ===");

        long checksum;
        {
            // Temporary working buffer confined to this scope
            byte[] scratchBuffer = new byte[]{0x10, 0x20, 0x30, 0x40};
            long sum = 0;
            for (byte b : scratchBuffer) {
                sum += b;
            }
            checksum = sum;
            // scratchBuffer and sum leave scope here, making scratchBuffer eligible
            // for garbage collection if no other references exist.
        }

        System.out.println("Computed checksum: " + checksum);
    }

    /**
     * Demonstrates an empty block, which is syntactically a valid statement (JLS 14.2).
     */
    public static void demonstrateEmptyBlock() {
        System.out.println("\n=== 5. Empty Block ===");
        {
            // Empty block: legal, produces no bytecode instructions
        }
        System.out.println("Empty block executed successfully.");
    }
}
