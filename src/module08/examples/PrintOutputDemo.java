package module08.examples;

import java.io.PrintStream;
import java.util.Arrays;

/**
 * Demonstrates the fundamentals of output streams in Java 21 LTS.
 *
 * <p>Key concepts covered:
 * <ul>
 *   <li>{@code System.out.println()} overloads for primitive types, references, and {@code null}.</li>
 *   <li>{@code System.out.print()} vs {@code System.out.println()} and newline handling.</li>
 *   <li>Format specifiers with {@code System.out.printf()} and platform line separator {@code %n}.</li>
 *   <li>Standard output ({@code System.out}) vs Standard error ({@code System.err}).</li>
 *   <li>Pitfalls with {@code char[]} vs {@code Object} overloads when passing null.</li>
 * </ul>
 *
 * @author Java Mastery Curriculum
 * @version 21.0
 */
public class PrintOutputDemo {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   MODULE 08: PRINT OUTPUT DEMONSTRATIONS         ");
        System.out.println("==================================================\n");

        demonstratePrintlnAllTypes();
        demonstratePrintVsPrintln();
        demonstratePrintfBasics();
        demonstrateSystemErrVsSystemOut();
        demonstrateNullHandling();
    }

    /**
     * Demonstrates {@code System.out.println()} overloads across all primitive types
     * and reference types.
     */
    public static void demonstratePrintlnAllTypes() {
        System.out.println("--- 1. System.out.println() with All Types ---");

        String stringVal = "Java 21 LTS";
        int intVal = 42;
        double doubleVal = 3.141592653589793;
        boolean booleanVal = true;
        char charVal = 'J';
        Object nullRef = null;

        // PrintStream provides dedicated overloads for each primitive type
        System.out.println("String  : " + stringVal);
        System.out.println("int     : " + intVal);
        System.out.println("double  : " + doubleVal);
        System.out.println("boolean : " + booleanVal);
        System.out.println("char    : " + charVal);

        // Object overload internally invokes String.valueOf(obj), which returns "null"
        System.out.println("null ref: " + nullRef);

        // Printing arrays directly invokes Object.toString(), producing type + identity hash
        int[] numbers = {1, 2, 3};
        System.out.println("Array default toString(): " + numbers);
        System.out.println("Array deep formatting   : " + Arrays.toString(numbers));
        System.out.println();
    }

    /**
     * Highlights the architectural difference between {@code print()} and {@code println()},
     * emphasizing buffering and line terminators.
     */
    public static void demonstratePrintVsPrintln() {
        System.out.println("--- 2. System.out.print() vs System.out.println() ---");

        // print() writes characters without appending a newline
        System.out.print("Step 1: Initializing");
        System.out.print(" -> ");
        System.out.print("Step 2: Processing");
        System.out.print(" -> ");
        System.out.print("Step 3: Complete");

        // println() flushes the buffer and writes the OS-specific line separator
        System.out.println();

        System.out.println("Notice that all preceding steps appeared on a single terminal line.");
        System.out.println("System.lineSeparator() on this platform is: "
                + Arrays.toString(System.lineSeparator().getBytes()));
        System.out.println();
    }

    /**
     * Demonstrates essential {@code printf} specifiers for structured output.
     */
    public static void demonstratePrintfBasics() {
        System.out.println("--- 3. System.out.printf() Basic Specifiers ---");

        String name = "Oracle OpenJDK";
        int version = 21;
        double heapGigabytes = 16.5;
        char releaseType = 'G'; // General Availability
        boolean isLts = true;

        // %d: decimal integer, %f: floating-point, %s: string, %c: character, %b: boolean, %n: platform newline
        System.out.printf("Product      : %s%n", name);
        System.out.printf("Version      : %d (LTS: %b)%n", version, isLts);
        System.out.printf("Release Tier : %c%n", releaseType);
        System.out.printf("Heap Size    : %.2f GB%n", heapGigabytes);

        // Always prefer %n over \n in format strings for cross-platform compliance:
        // On Windows %n emits \r\n, on Linux/macOS %n emits \n.
        System.out.printf("Cross-platform line break via %%n demonstrates portability.%n");
        System.out.println();
    }

    /**
     * Explains the separation between Standard Output (stdout / file descriptor 1)
     * and Standard Error (stderr / file descriptor 2).
     */
    public static void demonstrateSystemErrVsSystemOut() {
        System.out.println("--- 4. System.err vs System.out ---");

        System.out.println("[STDOUT] Normal operation log: Starting transaction #1001");
        System.err.println("[STDERR] Diagnostic warning: Transaction took 12ms (threshold: 10ms)");
        System.out.println("[STDOUT] Normal operation log: Transaction #1001 committed successfully");

        // Note: Because System.out and System.err represent two separate OS streams,
        // their output buffers may interleave non-deterministically in IDE consoles
        // unless explicitly flushed.
        System.out.flush();
        System.err.flush();
        System.out.println();
    }

    /**
     * Demonstrates an insidious Java compiler overload trap when handling nulls
     * with {@code PrintStream.println()}.
     */
    public static void demonstrateNullHandling() {
        System.out.println("--- 5. Null Handling Overload Resolution Trap ---");

        Object nullObject = null;
        String nullString = null;

        // Both invoke String.valueOf(obj), which returns the literal string "null"
        System.out.println(nullObject);
        System.out.println(nullString);

        // THE TRAP:
        // System.out.println((char[]) null);
        // The above line throws NullPointerException at runtime!
        // PrintStream has an overload println(char[] x) that iterates over the array directly:
        // write(x, 0, x.length) -> throws NullPointerException when dereferencing x.length!

        try {
            char[] nullCharArray = null;
            PrintStream out = System.out;
            out.println(nullCharArray); // Throws NPE!
        } catch (NullPointerException npe) {
            System.out.println("Caught expected NPE from println((char[]) null): " + npe.getClass().getName());
        }
        System.out.println();
    }
}
