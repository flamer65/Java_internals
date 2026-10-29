package module08.examples;

import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Demonstrates advanced configurations of {@link java.util.Scanner}.
 *
 * <p>Key concepts covered:
 * <ul>
 *   <li>Custom regex delimiters for CSV and delimiter-separated formats.</li>
 *   <li>Locale-aware number parsing (e.g., German decimal commas vs US decimal periods).</li>
 *   <li>Defensive input validation using {@code hasNextInt()} / {@code hasNextDouble()}.</li>
 *   <li>Robust input loop pattern preventing infinite error loops.</li>
 *   <li>Regex token matching using {@code findInLine()} and {@code next(Pattern)}.</li>
 * </ul>
 *
 * @author Java Mastery Curriculum
 * @version 21.0
 */
public class ScannerAdvanced {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   MODULE 08: SCANNER ADVANCED PATTERNS           ");
        System.out.println("==================================================\n");

        demonstrateCustomDelimiterCsv();
        demonstrateLocaleFormatting();
        demonstrateDefensiveValidation();
        demonstrateRegexTokenization();
    }

    /**
     * Demonstrates parsing comma-separated values (CSV) with whitespace trimming
     * using custom regex delimiters.
     */
    public static void demonstrateCustomDelimiterCsv() {
        System.out.println("--- 1. CSV Parsing with Custom Delimiters ---");

        String csvData = "101,John Doe,89500.50,Engineering\n102,Jane Smith,94200.00,Marketing\n";
        try (Scanner lineScanner = new Scanner(csvData)) {
            while (lineScanner.hasNextLine()) {
                String record = lineScanner.nextLine().trim();
                if (record.isEmpty()) continue;

                // Delimiter is comma, surrounded by optional whitespace
                try (Scanner fieldScanner = new Scanner(record)) {
                    fieldScanner.useDelimiter("\\s*,\\s*");

                    int empId = fieldScanner.nextInt();
                    String name = fieldScanner.next();
                    double salary = fieldScanner.nextDouble();
                    String department = fieldScanner.next();

                    System.out.printf("Employee #%d: %-12s | Dept: %-12s | Salary: $%,.2f%n",
                            empId, name, department, salary);
                }
            }
        }
        System.out.println();
    }

    /**
     * Demonstrates how Scanner adheres to Locale rules for floating-point and integer symbols.
     * In Germany, "1.234,56" represents 1234.56, whereas in the US, "1,234.56" is used.
     */
    public static void demonstrateLocaleFormatting() {
        System.out.println("--- 2. Locale-Sensitive Parsing ---");

        String germanNumber = "1234,56";
        try (Scanner scannerDE = new Scanner(germanNumber)) {
            scannerDE.useLocale(Locale.GERMANY);
            double val = scannerDE.nextDouble();
            System.out.printf("Parsed with Locale.GERMANY ('%s') -> Value: %.2f%n", germanNumber, val);
        }

        String usNumber = "1,234.56";
        try (Scanner scannerUS = new Scanner(usNumber)) {
            scannerUS.useLocale(Locale.US);
            double val = scannerUS.nextDouble();
            System.out.printf("Parsed with Locale.US      ('%s') -> Value: %.2f%n", usNumber, val);
        }
        System.out.println();
    }

    /**
     * Demonstrates the defensive lookahead validation pattern.
     * Calling {@code nextInt()} blindly on non-numeric input throws {@link java.util.InputMismatchException}.
     * Checking {@code hasNextInt()} first guarantees safe extraction.
     */
    public static void demonstrateDefensiveValidation() {
        System.out.println("--- 3. Defensive Input Validation ---");

        // Simulated noisy input stream: letters, out-of-range tokens, and valid numbers
        String noisyInput = "abc foo 42 bar 99 done";
        try (Scanner scanner = new Scanner(noisyInput)) {
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    int validNumber = scanner.nextInt();
                    System.out.println("  -> Successfully extracted valid integer: " + validNumber);
                } else {
                    String discardedToken = scanner.next();
                    System.out.println("  [Skipped invalid token: \"" + discardedToken + "\"]");
                }
            }
        }
        System.out.println();
    }

    /**
     * Demonstrates pattern extraction directly using regular expressions with Scanner.
     */
    public static void demonstrateRegexTokenization() {
        System.out.println("--- 4. Pattern Tokenization via Scanner ---");

        String logLine = "2026-10-28 [ERROR] IP=192.168.1.105 Connection refused to port 8080";
        try (Scanner scanner = new Scanner(logLine)) {
            Pattern ipPattern = Pattern.compile("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b");
            String ip = scanner.findInLine(ipPattern);

            System.out.println("Log entry     : " + logLine);
            System.out.println("Extracted IP  : " + (ip != null ? ip : "Not found"));
        }
        System.out.println();
    }
}
