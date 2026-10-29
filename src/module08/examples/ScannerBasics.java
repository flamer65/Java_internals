package module08.examples;

import java.util.Scanner;

/**
 * Demonstrates essential usage patterns, pitfalls, and best practices for {@link java.util.Scanner}.
 *
 * <p>Key concepts covered:
 * <ul>
 *   <li>Parsing primitives ({@code int}, {@code double}, {@code boolean}) and tokens.</li>
 *   <li>The infamous <b>Scanner Newline Trap</b> (mixing token-based methods with {@code nextLine()}).</li>
 *   <li>The two standard industrial solutions to the Scanner Trap.</li>
 *   <li>Tokenizing in-memory {@link String} instances instead of raw I/O streams.</li>
 *   <li>The catastrophic hazard of closing a Scanner wrapped around {@code System.in}.</li>
 * </ul>
 *
 * @author Java Mastery Curriculum
 * @version 21.0
 */
public class ScannerBasics {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   MODULE 08: SCANNER BASICS & NEWLINE TRAP       ");
        System.out.println("==================================================\n");

        demonstrateReadingAllTypes();
        demonstrateTheScannerTrapBug();
        demonstrateTheScannerTrapFixes();
        demonstrateScannerFromString();
        demonstrateSystemInClosingHazard();
    }

    /**
     * Demonstrates parsing different primitive types and strings using Scanner
     * over a simulated in-memory input stream.
     */
    public static void demonstrateReadingAllTypes() {
        System.out.println("--- 1. Reading Primitives and Tokens ---");

        String simulatedInput = "42 3.14159 true Engineering\n";
        try (Scanner scanner = new Scanner(simulatedInput)) {
            int age = scanner.nextInt();
            double piEstimate = scanner.nextDouble();
            boolean isProductionReady = scanner.nextBoolean();
            String department = scanner.next();

            System.out.println("Parsed int     : " + age);
            System.out.println("Parsed double  : " + piEstimate);
            System.out.println("Parsed boolean : " + isProductionReady);
            System.out.println("Parsed token   : " + department);
        }
        System.out.println();
    }

    /**
     * Recreates the classic "Scanner Trap":
     * When {@code nextInt()} is called, it reads only the integer characters and leaves the
     * trailing newline ('\n' or '\r\n') in the buffer.
     * The subsequent call to {@code nextLine()} immediately encounters that newline and returns an
     * empty string, effectively skipping user input!
     */
    public static void demonstrateTheScannerTrapBug() {
        System.out.println("--- 2. THE SCANNER TRAP: The Bug ---");

        // Simulated user typed "25" and hit Enter, then meant to type "Alice Smith" and hit Enter:
        String faultyInput = "25\nAlice Smith\n";
        Scanner scanner = new Scanner(faultyInput);

        System.out.println("Reading age via nextInt()...");
        int age = scanner.nextInt();
        System.out.println("Age captured: " + age);

        System.out.println("Now reading name via nextLine() WITHOUT consuming leftover newline...");
        String name = scanner.nextLine(); // BUG: Consumes leftover '\n' immediately!

        System.out.println("Name captured: [" + name + "] (Length: " + name.length() + ")");
        if (name.isEmpty()) {
            System.out.println("-> BUG CONFIRMED: The name string was skipped because nextLine() swallowed the residual newline!");
        }
        System.out.println();
    }

    /**
     * Demonstrates the two standard production patterns to fix the Scanner Trap.
     */
    public static void demonstrateTheScannerTrapFixes() {
        System.out.println("--- 3. THE SCANNER TRAP: The Fixes ---");

        String input = "25\nAlice Smith\n";

        // FIX 1: Consume the leftover newline with an explicit dummy nextLine() call
        System.out.println("Fix 1: Explicitly consume residual newline via scanner.nextLine()");
        Scanner scanner1 = new Scanner(input);
        int age1 = scanner1.nextInt();
        scanner1.nextLine(); // Discard the leftover newline character
        String name1 = scanner1.nextLine(); // Successfully waits for/reads full line
        System.out.println("Result 1 -> Age: " + age1 + ", Name: [" + name1 + "]");

        // FIX 2: Universal Best Practice: Always read strings using nextLine() and parse manually
        System.out.println("\nFix 2 (Recommended): Uniform nextLine() reads combined with type parsing");
        Scanner scanner2 = new Scanner(input);
        int age2 = Integer.parseInt(scanner2.nextLine().trim());
        String name2 = scanner2.nextLine().trim();
        System.out.println("Result 2 -> Age: " + age2 + ", Name: [" + name2 + "]");
        System.out.println();
    }

    /**
     * Demonstrates using Scanner to tokenize arbitrary strings without any I/O disk or network streams.
     */
    public static void demonstrateScannerFromString() {
        System.out.println("--- 4. Scanner Reading from a Pure String ---");

        String payload = "HTTP/1.1 200 OK";
        try (Scanner scanner = new Scanner(payload)) {
            String protocol = scanner.next();
            int statusCode = scanner.nextInt();
            String statusMessage = scanner.next();

            System.out.printf("Protocol: %s | Status: %d | Message: %s%n",
                    protocol, statusCode, statusMessage);
        }
        System.out.println();
    }

    /**
     * Demonstrates the lethal pitfall of closing a Scanner wrapped over System.in.
     * System.in is a JVM-wide singleton InputStream tied to standard input file descriptor 0.
     * Calling {@code scanner.close()} delegates to {@code System.in.close()}, which shuts down
     * stdin permanently for the lifetime of the JVM!
     */
    public static void demonstrateSystemInClosingHazard() {
        System.out.println("--- 5. The System.in Closing Hazard ---");
        System.out.println("Rule of thumb: NEVER close a Scanner wrapping System.in inside utility methods!");
        System.out.println("Doing so closes the OS file descriptor 0, breaking all future console input in the JVM.");
        System.out.println("If Scanner is used with System.in, manage its lifecycle at the application boundary (main method).");
        System.out.println();
    }
}
