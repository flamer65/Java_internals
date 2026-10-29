package module08.examples;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * Demonstrates high-throughput line-oriented I/O using {@link java.io.BufferedReader}.
 *
 * <p>Key concepts covered:
 * <ul>
 *   <li>The classic {@code while ((line = reader.readLine()) != null)} idiom.</li>
 *   <li>Modern functional stream processing using {@link BufferedReader#lines()}.</li>
 *   <li>Explicit character decoding via {@link java.io.InputStreamReader} and {@link java.nio.charset.StandardCharsets}.</li>
 *   <li>100,000-line performance benchmark: {@code BufferedReader} vs {@code Scanner}.</li>
 *   <li>Architectural differences: 8KB buffer &amp; synchronization vs regex parsing.</li>
 * </ul>
 *
 * @author Java Mastery Curriculum
 * @version 21.0
 */
public class BufferedReaderDemo {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   MODULE 08: BUFFEREDREADER & PERFORMANCE        ");
        System.out.println("==================================================\n");

        demonstrateClassicLineReading();
        demonstrateStreamProcessing();
        demonstrateExplicitCharsetDecoding();
        run100kLinesBenchmark();
    }

    /**
     * Demonstrates the classic loop pattern for reading text lines until EOF (null).
     */
    public static void demonstrateClassicLineReading() {
        System.out.println("--- 1. Classic Line Reading (readLine() != null) ---");

        String multilineText = "Line 1: High-Performance Java\nLine 2: JVM Architecture\nLine 3: IO Subsystem\n";

        try (BufferedReader reader = new BufferedReader(new StringReader(multilineText))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                System.out.printf("[%d] %s%n", lineNumber++, line);
            }
        } catch (Exception e) {
            System.err.println("Error reading lines: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * Demonstrates Java 8+ {@link BufferedReader#lines()} yielding a lazy {@link java.util.stream.Stream}.
     */
    public static void demonstrateStreamProcessing() {
        System.out.println("--- 2. Functional Stream Processing via reader.lines() ---");

        String serverLog = """
                INFO 200 /api/v1/users
                WARN 404 /api/v1/missing
                ERROR 500 /api/v1/checkout
                INFO 200 /api/v1/orders
                ERROR 503 /api/v1/inventory
                """;

        try (BufferedReader reader = new BufferedReader(new StringReader(serverLog))) {
            List<String> errorEndpoints = reader.lines()
                    .filter(line -> line.startsWith("ERROR"))
                    .map(line -> line.substring(line.lastIndexOf(' ') + 1))
                    .toList();

            System.out.println("Extracted Error Endpoints: " + errorEndpoints);
        } catch (Exception e) {
            System.err.println("Stream error: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * Highlights the necessity of specifying an explicit {@link java.nio.charset.Charset}
     * when bridging an {@link InputStream} to a {@link java.io.Reader}.
     */
    public static void demonstrateExplicitCharsetDecoding() {
        System.out.println("--- 3. Explicit Charset Decoding with StandardCharsets.UTF_8 ---");

        // Simulated binary stream encoded in UTF-8 containing multilingual characters
        byte[] utf8Payload = "Java \u2615 \u2705 \u65e5\u672c\u8a9e".getBytes(StandardCharsets.UTF_8);
        InputStream inputStream = new ByteArrayInputStream(utf8Payload);

        // ALWAYS specify the charset explicitly rather than relying on system defaults
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String decoded = reader.readLine();
            System.out.println("Decoded UTF-8 content: " + decoded);
        } catch (Exception e) {
            System.err.println("Charset decoding failed: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * Benchmarks reading 100,000 lines between {@link BufferedReader} and {@link Scanner}.
     * Explains why BufferedReader is orders of magnitude faster.
     */
    public static void run100kLinesBenchmark() {
        System.out.println("--- 4. Benchmark: Scanner vs BufferedReader (100,000 lines) ---");

        int lineCount = 100_000;
        StringBuilder sb = new StringBuilder(lineCount * 24);
        for (int i = 0; i < lineCount; i++) {
            sb.append("Benchmark record index #").append(i).append("\n");
        }
        String payload = sb.toString();

        // 1. Benchmark BufferedReader
        long startBuffered = System.nanoTime();
        int bufferedLines = 0;
        try (BufferedReader reader = new BufferedReader(new StringReader(payload))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bufferedLines++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long durationBufferedMs = (System.nanoTime() - startBuffered) / 1_000_000;

        // 2. Benchmark Scanner
        long startScanner = System.nanoTime();
        int scannerLines = 0;
        try (Scanner scanner = new Scanner(payload)) {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                scannerLines++;
            }
        }
        long durationScannerMs = (System.nanoTime() - startScanner) / 1_000_000;

        System.out.printf("BufferedReader parsed %,d lines in : %4d ms%n", bufferedLines, durationBufferedMs);
        System.out.printf("Scanner        parsed %,d lines in : %4d ms%n", scannerLines, durationScannerMs);

        double speedup = durationScannerMs > 0 ? (double) durationScannerMs / Math.max(1, durationBufferedMs) : 1.0;
        System.out.printf("Performance Verdict: BufferedReader is approximately %.1fx faster than Scanner.%n", speedup);
        System.out.println("Architectural Reason: Scanner parses every token against regular expressions,");
        System.out.println("allocates Matcher objects, and maintains small 1024-char buffers.");
        System.out.println("BufferedReader uses an efficient 8192-char array with zero regex overhead.");
        System.out.println();
    }
}
