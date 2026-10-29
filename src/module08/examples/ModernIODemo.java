package module08.examples;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Demonstrates modern concise I/O methods introduced in Java 9, 10, and 11+.
 *
 * <p>Key concepts covered:
 * <ul>
 *   <li>{@link Files#writeString} and {@link Files#readString} (Java 11).</li>
 *   <li>{@link InputStream#transferTo(OutputStream)} direct stream piping (Java 9).</li>
 *   <li>{@link java.io.Reader#transferTo(java.io.Writer)} character stream piping (Java 10).</li>
 *   <li>Stream discards via {@link OutputStream#nullOutputStream()} (Java 9).</li>
 *   <li>Lazy line streaming via {@link Files#lines(Path, java.nio.charset.Charset)}.</li>
 * </ul>
 *
 * @author Java Mastery Curriculum
 * @version 21.0
 */
public class ModernIODemo {

    public static void main(String[] args) throws IOException {
        System.out.println("==================================================");
        System.out.println("   MODULE 08: MODERN JAVA I/O (JAVA 9 - 21)       ");
        System.out.println("==================================================\n");

        demonstrateReadWriteString();
        demonstrateStreamTransferTo();
        demonstrateReaderTransferTo();
        demonstrateNullStreams();
        demonstrateFilesLinesStream();
    }

    /**
     * Demonstrates Java 11 single-line file read and write operations.
     */
    public static void demonstrateReadWriteString() throws IOException {
        System.out.println("--- 1. Files.writeString() and Files.readString() (Java 11+) ---");

        Path tempFile = Files.createTempFile("java21-modern-io-", ".txt");
        try {
            String content = """
                    Module 08: Input and Output
                    Target: OpenJDK 21 LTS
                    Modern File API: Eliminates boilerplate streams and readers.
                    """;

            // Write entire string with explicit charset and options
            Files.writeString(tempFile, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Wrote content to temporary file: " + tempFile);

            // Read entire string with explicit charset
            String readBack = Files.readString(tempFile, StandardCharsets.UTF_8);
            System.out.println("Read back content successfully:\n" + readBack);

        } finally {
            Files.deleteIfExists(tempFile);
            System.out.println("Cleaned up temporary file: " + tempFile);
        }
        System.out.println();
    }

    /**
     * Demonstrates Java 9 {@link InputStream#transferTo(OutputStream)} which copies
     * all bytes from an input stream to an output stream without manual byte array buffers.
     */
    public static void demonstrateStreamTransferTo() throws IOException {
        System.out.println("--- 2. InputStream.transferTo(OutputStream) (Java 9+) ---");

        byte[] sourceData = "High-throughput binary packet transferred via transferTo()".getBytes(StandardCharsets.UTF_8);
        InputStream sourceStream = new ByteArrayInputStream(sourceData);
        ByteArrayOutputStream destinationStream = new ByteArrayOutputStream();

        // Single call eliminates the classic byte[] buffer loop
        long bytesTransferred = sourceStream.transferTo(destinationStream);

        System.out.printf("Transferred %,d bytes directly between streams.%n", bytesTransferred);
        System.out.println("Destination contents: " + destinationStream.toString(StandardCharsets.UTF_8));
        System.out.println();
    }

    /**
     * Demonstrates Java 10 {@link java.io.Reader#transferTo(java.io.Writer)} for character streams.
     */
    public static void demonstrateReaderTransferTo() throws IOException {
        System.out.println("--- 3. Reader.transferTo(Writer) (Java 10+) ---");

        String text = "Character-based pipeline using Reader.transferTo(Writer)";
        StringReader reader = new StringReader(text);
        StringWriter writer = new StringWriter();

        long charsTransferred = reader.transferTo(writer);

        System.out.printf("Transferred %,d characters.%n", charsTransferred);
        System.out.println("Writer output: " + writer);
        System.out.println();
    }

    /**
     * Demonstrates discarding output or feeding empty input using Java 9 standard null streams.
     * Analogous to {@code /dev/null}.
     */
    public static void demonstrateNullStreams() throws IOException {
        System.out.println("--- 4. Java 9 Null Streams (Dev Null equivalent) ---");

        // Useful for benchmarking or discarding verbose output
        try (OutputStream devNull = OutputStream.nullOutputStream()) {
            devNull.write("This content is discarded with zero allocation.".getBytes(StandardCharsets.UTF_8));
            System.out.println("Successfully wrote data to OutputStream.nullOutputStream()");
        }

        try (InputStream emptyIn = InputStream.nullInputStream()) {
            int byteRead = emptyIn.read();
            System.out.println("Reading from InputStream.nullInputStream() returns EOF (-1): " + byteRead);
        }
        System.out.println();
    }

    /**
     * Demonstrates memory-efficient lazy line processing using {@link Files#lines(Path)}.
     */
    public static void demonstrateFilesLinesStream() throws IOException {
        System.out.println("--- 5. Lazy Stream Processing via Files.lines() ---");

        Path tempConfig = Files.createTempFile("config-sample-", ".properties");
        try {
            Files.writeString(tempConfig, """
                    # Database settings
                    db.host=localhost
                    db.port=5432
                    db.user=postgres
                    # Cache settings
                    cache.enabled=true
                    cache.ttl=3600
                    """);

            // Files.lines() returns a Stream<String> that MUST be used within a try-with-resources
            // block so that the underlying file descriptor is guaranteed to close.
            try (var lines = Files.lines(tempConfig, StandardCharsets.UTF_8)) {
                List<String> activeKeys = lines
                        .map(String::trim)
                        .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                        .map(line -> line.substring(0, line.indexOf('=')))
                        .toList();

                System.out.println("Extracted Active Configuration Keys: " + activeKeys);
            }
        } finally {
            Files.deleteIfExists(tempConfig);
        }
        System.out.println();
    }
}
