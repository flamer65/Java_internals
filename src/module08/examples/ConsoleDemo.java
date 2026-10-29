package module08.examples;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Demonstrates secure and direct terminal I/O using {@link java.io.Console}.
 *
 * <p>Key concepts covered:
 * <ul>
 *   <li>The nullability of {@code System.console()} inside IDEs and non-interactive environments.</li>
 *   <li>Secure password handling with {@code console.readPassword()} returning {@code char[]}.</li>
 *   <li>Memory security: zeroing out sensitive password character arrays with {@link Arrays#fill(char[], char)}.</li>
 *   <li>The danger of storing passwords in immutable {@link String} objects on the JVM heap.</li>
 *   <li>Graceful fallback pattern when {@code System.console()} is unavailable.</li>
 * </ul>
 *
 * @author Java Mastery Curriculum
 * @version 21.0
 */
public class ConsoleDemo {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   MODULE 08: CONSOLE DEMO & PASSWORD SECURITY    ");
        System.out.println("==================================================\n");

        demonstrateConsoleNullCheck();
        demonstratePasswordSecurityPhilosophy();
        demonstrateFallbackPattern();
    }

    /**
     * Demonstrates checking for {@code System.console()} presence.
     * When executed inside standard IDE runners (IntelliJ IDEA, Eclipse) or background daemons,
     * no OS terminal (TTY/PTY) is attached, causing {@code System.console()} to return {@code null}.
     */
    public static void demonstrateConsoleNullCheck() {
        System.out.println("--- 1. Checking System.console() Nullability ---");

        Console console = System.console();
        if (console == null) {
            System.out.println("[RESULT] System.console() is NULL.");
            System.out.println("  Explanation: This program is running in an environment without an attached interactive terminal");
            System.out.println("  (e.g., standard IDE run window, redirected I/O pipe, or CI/CD container).");
            System.out.println("  In IntelliJ IDEA, you can enable terminal mode in Run/Debug Configurations via");
            System.out.println("  'Modify options' -> 'Emulate terminal in output console'.");
        } else {
            System.out.println("[RESULT] System.console() is AVAILABLE.");
            System.out.printf("  Console encoding: %s%n", console.charset().displayName());
        }
        System.out.println();
    }

    /**
     * Explains the JVM memory rationale for why {@link Console#readPassword()} returns
     * {@code char[]} rather than {@link String}.
     */
    public static void demonstratePasswordSecurityPhilosophy() {
        System.out.println("--- 2. Why readPassword() returns char[] (Memory Security) ---");

        // Simulated secure credential extraction:
        char[] password = {'S', 'u', 'p', 'e', 'r', 'S', 'e', 'c', 'r', 'e', 't', '!', '9', '9'};

        System.out.println("Step 1: Password acquired in char[] array.");
        System.out.printf("  Password length: %d characters.%n", password.length);

        // Security best practice: Once authentication is complete, scrub the char[] immediately
        Arrays.fill(password, '\0');

        System.out.println("Step 2: Password cleared from memory using Arrays.fill(password, '\\0').");
        System.out.printf("  Password post-clearing: %s%n", Arrays.toString(password));

        System.out.println("\n  WHY NOT USE STRING FOR PASSWORDS?");
        System.out.println("  1. java.lang.String is IMMUTABLE. Its internal byte[] cannot be overwritten safely.");
        System.out.println("  2. Strings remain in heap memory until Garbage Collection collects them, leaving them");
        System.out.println("     vulnerable to process heap dumps, memory scanners, and core dumps.");
        System.out.println("  3. char[] allows explicit zeroing immediately after authentication completes.");
        System.out.println();
    }

    /**
     * Demonstrates an industrial-strength fallback strategy for reading credentials
     * when running both in CLI terminals and IDE runners.
     */
    public static void demonstrateFallbackPattern() {
        System.out.println("--- 3. Robust Console + IDE Fallback Architecture ---");

        Console console = System.console();
        if (console != null) {
            System.out.println("Executing in native terminal mode: reading via Console API...");
            // String username = console.readLine("Username: ");
            // char[] pwd = console.readPassword("Password: ");
            // Arrays.fill(pwd, '\0');
        } else {
            System.out.println("Executing in IDE/non-interactive mode: falling back to BufferedReader / Scanner...");
            System.out.println("  [Fallback active: Note that password echoing cannot be masked on standard System.in]");
            // Note: In production fallback, create a BufferedReader wrapping System.in without closing it,
            // or pass simulated input.
            String simulatedPromptResponse = "fallback_admin_user";
            System.out.println("  Simulated fallback read: " + simulatedPromptResponse);
        }
        System.out.println();
    }
}
