package module08.examples;

import java.time.LocalDateTime;
import java.util.Locale;

/**
 * Demonstrates comprehensive formatting capabilities in Java 21 LTS using
 * {@link java.util.Formatter}, {@link java.io.PrintStream#printf}, and {@link String#formatted(Object...)}.
 *
 * <p>Key concepts covered:
 * <ul>
 *   <li>Basic and advanced conversion specifiers ({@code %d}, {@code %f}, {@code %e}, {@code %s}, {@code %b}, {@code %h}, {@code %%}).</li>
 *   <li>Width, precision, zero-padding, left-justification, and grouping flags.</li>
 *   <li>Explicit argument indexing ({@code %1$s}, {@code %2$s}) and relative indexing ({@code %<s}).</li>
 *   <li>Date and time format specifiers ({@code %tF}, {@code %tT}, {@code %tB}, {@code %tc}).</li>
 *   <li>Comparison between {@link String#format} and modern {@link String#formatted(Object...)}.</li>
 *   <li>Dynamic ASCII tabular formatting.</li>
 * </ul>
 *
 * @author Java Mastery Curriculum
 * @version 21.0
 */
public class FormattingDemo {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   MODULE 08: FORMATTING ENGINE DEEP DIVE         ");
        System.out.println("==================================================\n");

        demonstrateWidthAndPrecision();
        demonstrateArgumentIndices();
        demonstrateDateTimeFormatting();
        demonstrateStringFormatVsFormatted();
        demonstrateDynamicAsciiTable();
    }

    /**
     * Demonstrates widths, precision limits, padding, and numerical flags.
     */
    public static void demonstrateWidthAndPrecision() {
        System.out.println("--- 1. Width, Precision, and Flags ---");

        int quantity = 42;
        double price = 1250.7589;
        String product = "Mechanical Keyboard";

        // Right aligned within 10 characters: %10d
        System.out.printf("Right-aligned int      : [%10d]%n", quantity);

        // Zero-padded to 8 digits: %08d
        System.out.printf("Zero-padded int        : [%08d]%n", quantity);

        // Always show sign (+/-): %+10d
        System.out.printf("Sign explicit int      : [%+10d]%n", quantity);

        // Left aligned within 25 characters: %-25s
        System.out.printf("Left-aligned string    : [%-25s]%n", product);

        // Precision on floating point with thousands separator: %,.2f
        System.out.printf("Currency formatted     : [$%,.2f]%n", price);

        // Scientific notation: %.3e
        System.out.printf("Scientific notation    : [%.3e]%n", price);

        // Hexadecimal representation: %x and %X
        System.out.printf("Hex integer (0x%X)     : [%X]%n", 255, 255);

        // Escaping literal percent sign: %%
        System.out.printf("Discount applied       : %d%%%n", 15);
        System.out.println();
    }

    /**
     * Demonstrates argument indexing to reorder parameters or format the same variable
     * multiple times without repeating arguments.
     */
    public static void demonstrateArgumentIndices() {
        System.out.println("--- 2. Argument Indexing (%1$s, %2$s, and %<s) ---");

        String firstName = "Ada";
        String lastName = "Lovelace";

        // Explicit index: %1$ references first argument, %2$ references second
        System.out.printf("First then Last        : %1$s %2$s%n", firstName, lastName);
        System.out.printf("Last, First            : %2$s, %1$s%n", firstName, lastName);

        // Relative index: %< references the PREVIOUS argument again
        int count = 255;
        System.out.printf("Decimal: %1$d | Hex: 0x%1$X | Octal: 0%1$o%n", count);
        System.out.printf("Decimal: %d | Hex: 0x%<X | Octal: 0%<o (using %%< relative flag)%n", count);
        System.out.println();
    }

    /**
     * Demonstrates temporal formatting flags with {@link LocalDateTime} and {@link java.time.ZonedDateTime}.
     */
    public static void demonstrateDateTimeFormatting() {
        System.out.println("--- 3. Date and Time Format Specifiers ---");

        LocalDateTime localNow = LocalDateTime.of(2026, 10, 28, 14, 30, 45);
        java.time.ZonedDateTime zonedNow = localNow.atZone(java.time.ZoneId.of("UTC"));

        // %tF: ISO-8601 date (YYYY-MM-DD)
        // %tT: 24-hour time (HH:MM:SS)
        // %tD: US date (MM/DD/YY)
        // %tB: Full month name
        // %tc: Complete date and time string (requires timezone component: ZonedDateTime or Date)
        System.out.printf("ISO Date   (%%tF)       : %tF%n", localNow);
        System.out.printf("24h Time   (%%tT)       : %tT%n", localNow);
        System.out.printf("US Date    (%%tD)       : %tD%n", localNow);
        System.out.printf("Month Name (%%tB)       : %tB%n", localNow);
        System.out.printf("Full Date/Time (%%tc)   : %tc%n", zonedNow);

        // Reusing same temporal argument via %<
        System.out.printf("Timestamp: %tF at %<tT (single argument)%n", localNow);
        System.out.println();
    }

    /**
     * Compares classic {@link String#format} with the modern instance method {@link String#formatted(Object...)}
     * introduced in Java 15.
     */
    public static void demonstrateStringFormatVsFormatted() {
        System.out.println("--- 4. String.format() vs modern String.formatted() (Java 15+) ---");

        String user = "Duke";
        int memoryLimitMb = 4096;

        // Classic static method:
        String classic = String.format("User: %s | Max Heap: %d MB", user, memoryLimitMb);

        // Modern instance method (fluent syntax, ideal with Text Blocks):
        String modern = "User: %s | Max Heap: %d MB".formatted(user, memoryLimitMb);

        System.out.println("Classic format()   : " + classic);
        System.out.println("Modern formatted() : " + modern);

        // Text block formatting with .formatted()
        String jsonPayload = """
                {
                  "user": "%s",
                  "allocatedHeapMb": %d
                }
                """.formatted(user, memoryLimitMb);
        System.out.println("\nText Block formatted:\n" + jsonPayload);
    }

    /**
     * Demonstrates formatted tabular reporting using dynamic widths.
     */
    public static void demonstrateDynamicAsciiTable() {
        System.out.println("--- 5. Dynamic Tabular ASCII Output ---");

        record InventoryItem(int id, String name, int stock, double unitPrice) {}

        InventoryItem[] items = {
                new InventoryItem(1001, "NVMe SSD 2TB", 45, 149.99),
                new InventoryItem(1002, "DDR5 RAM 64GB", 12, 219.50),
                new InventoryItem(1003, "Mechanical Switches (110x)", 150, 34.00),
                new InventoryItem(1004, "Curved Monitor 34\"", 8, 489.95)
        };

        String headerFormat = "| %-6s | %-28s | %8s | %12s |%n";
        String rowFormat    = "| %-6d | %-28s | %8d | $%,11.2f |%n";
        String separator    = "+--------+------------------------------+----------+--------------+";

        System.out.println(separator);
        System.out.printf(headerFormat, "ID", "PRODUCT NAME", "STOCK", "PRICE");
        System.out.println(separator);

        for (InventoryItem item : items) {
            System.out.printf(Locale.US, rowFormat, item.id(), item.name(), item.stock(), item.unitPrice());
        }

        System.out.println(separator);
        System.out.println();
    }
}
