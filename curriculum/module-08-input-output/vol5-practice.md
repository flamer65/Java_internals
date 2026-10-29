# Module 8: Input and Output — Volume 5 (Practice Exercises)

**Target:** Java 21 LTS | IntelliJ IDEA  
**Author:** Senior Java Engineer, JVM Engineer & OpenJDK Compiler Engineer  

> *Note: These exercises contain problem descriptions, technical constraints, JVM mechanics, and verification criteria only. No solutions are provided in this volume.*

---

## 1. SMALL EXERCISES (10)

### Exercise 1.1: Scanner Reading & Printf Formatting
- **Objective:** Construct a standalone Java class (`UserGreetingLab`) that prompts the user for their full name, age, and hourly wage using `java.util.Scanner`, then emits a structured, formatted welcome message via `System.out.printf`.
- **Requirements:**
  1. Instantiate a single `Scanner` instance bound to `System.in`.
  2. Prompt for and capture the user's full name (including spaces) using `nextLine()`.
  3. Prompt for and capture the user's age as an integer using `nextInt()`.
  4. Prompt for and capture the hourly wage as a `double` using `nextDouble()`.
  5. Print a single formatted greeting using `printf`:
     - Full name printed with a minimum field width of 20 characters, left-aligned (`%-20s`).
     - Age printed as a decimal integer right-aligned in a 4-character field (`%4d`).
     - Hourly wage formatted to exactly 2 decimal places with a dollar sign prefix, right-aligned in an 8-character field (`$%7.2f`).
     - Must end with the platform-independent line separator `%n`.
  6. Ensure that entering spaces in the full name does not prematurely split the token or cause skipped prompts.
- **Verification:** Execute the program, enter `Ada Lovelace`, `36`, and `85.5`, and confirm exact alignment against an 80-column terminal ruler.

---

### Exercise 1.2: The Scanner Newline Trap and Canonical Solutions
- **Objective:** Construct a test harness (`ScannerTrapLab`) demonstrating the classic "newline trap" where reading primitive tokens leaves a trailing newline character in the stream buffer, and demonstrate the idiomatic fix.
- **Requirements:**
  1. Prompt the user for an employee ID number (`int`) using `scanner.nextInt()`.
  2. Immediately prompt the user for their job title (`String`) using `scanner.nextLine()`.
  3. Intentionally demonstrate the bug: show that the program does NOT pause for the job title input, but instead immediately reads the leftover carriage return/newline byte sequence (`\r\n` or `\n`) and assigns an empty string `""` to the job title.
  4. Print the captured values to prove the job title is empty.
  5. Provide and document the two canonical engineering fixes in commented alternative blocks:
     - *Fix A (Buffer Drain):* Explicitly invoke `scanner.nextLine()` immediately following `scanner.nextInt()` to consume the leftover newline.
     - *Fix B (Uniform Line Parsing):* Exclusively read lines via `scanner.nextLine()` throughout the entire application and parse primitives via `Integer.parseInt(line.trim())`.
  6. Document in source code comments the internal buffer pointer mechanics of `java.util.Scanner` and why `next()`, `nextInt()`, and `nextDouble()` delimit on whitespace but do not consume trailing line terminators.
- **Verification:** Run both the defective version (proving failure to prompt) and both fixed versions (proving correct capture of multi-word job titles).

---

### Exercise 1.3: Aggregate Statistics with Formatted Tabulation
- **Objective:** Write a program (`StreamStatisticsCalculator`) that reads exactly 5 integer values from standard input and displays statistical aggregates (sum, arithmetic mean, minimum, maximum) formatted to precise alignment standards.
- **Requirements:**
  1. Prompt the user sequentially for 5 integer values using `Scanner`.
  2. Store values in a fixed-size primitive array `int[5]`.
  3. Compute:
     - Sum (`long` to prevent intermediate overflow).
     - Arithmetic mean (`double`).
     - Minimum value (`int`).
     - Maximum value (`int`).
  4. Display the results in a formatted two-column table:
     - Left column: Metric Name (`%-15s`).
     - Right column: Formatted Value.
     - Integers must be zero-padded to 6 digits (`%06d`).
     - Mean must be formatted with sign flag, thousands separator, and 3 decimal places (`%+,.3f`).
  5. Include horizontal separator lines composed of hyphens aligned with table borders.
- **Verification:** Test with inputs `-1200`, `450`, `0`, `32000`, `-5` and confirm that sign flags (`+` and `-`) and comma separators render accurately.

---

### Exercise 1.4: Dynamic Multiplication Grid with Aligned Matrix
- **Objective:** Create a utility (`MultiplicationTableGrid`) using `System.out.printf` that renders an aligned 1-to-10 multiplication matrix with row and column headers.
- **Requirements:**
  1. Render a top header row displaying column multipliers `1` through `10`, preceded by an empty corner cell.
  2. Render a horizontal dividing line containing `+` intersections and `-` dashes matching column widths.
  3. For each row `1` through `10`:
     - Display the row index followed by a vertical bar separator `|`.
     - Display the products of row and column indices.
     - Each product cell must be right-aligned with a uniform width of 4 characters (`%4d`).
  4. Utilize `%n` exclusively for line breaks to guarantee cross-platform consistency.
  5. Do not use string concatenation (`+`) inside the loop body for formatting.
- **Verification:** Output must produce a grid where single-digit, double-digit, and three-digit numbers (e.g., `100`) line up without column shifting.

---

### Exercise 1.5: Scanner vs. BufferedReader Comparison
- **Objective:** Construct a dual-mode CLI program (`InputComparisonLab`) that reads a user profile using `java.util.Scanner` and subsequently reads an identical profile using `java.io.BufferedReader` wrapped around `java.io.InputStreamReader`.
- **Requirements:**
  1. Implement method `readWithScanner()`:
     - Read name, age, and salary.
     - Note the handling of unchecked exceptions (`InputMismatchException`).
  2. Implement method `readWithBufferedReader()`:
     - Wrap `new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))`.
     - Explicitly handle checked exceptions (`IOException`).
     - Manually convert strings via `Integer.parseInt()` and `Double.parseDouble()`.
  3. In code comments, document:
     - Default internal buffer size of `BufferedReader` (8192 chars) vs `Scanner` (1024 chars).
     - Thread-safety differences (`BufferedReader` is synchronized on its lock object; `Scanner` is not thread-safe).
     - Parsing overhead: Regex pattern matching in `Scanner` vs simple byte-to-char buffering in `BufferedReader`.
- **Verification:** Ensure both methods produce identical data representations from identical keyboard inputs.

---

### Exercise 1.6: Modern String Formatting: `format()` vs. `formatted()`
- **Objective:** Build a data reporting class (`FormattingStylesLab`) comparing `String.format()`, `System.out.printf()`, and Java 15+ `String.formatted()`.
- **Requirements:**
  1. Define variables representing a financial transaction: `transactionId` (UUID string), `timestamp` (Instant), `amount` (double), `status` (boolean), `accountType` (char).
  2. Format the receipt using the static method `String.format(Locale.US, format, args...)`.
  3. Format an identical receipt using the Java 15 instance method `format.formatted(args...)`.
  4. Format the receipt inside a Java 15+ multiline Text Block (`""" ... """`) combined with `.formatted()`.
  5. Explain in source comments how `.formatted()` acts as a convenience instance wrapper around `String.format(this, args)`.
- **Verification:** Verify that all three formatted outputs are byte-for-byte identical.

---

### Exercise 1.7: Segregating Output Streams: `System.out` vs. `System.err`
- **Objective:** Write a diagnostic logging utility (`StreamSegregationLab`) that writes normal informational messages to `System.out` and diagnostic warnings/errors to `System.err`.
- **Requirements:**
  1. Accept user commands from `Scanner`: `STATUS`, `PROCESS <id>`, `FAIL`, and `EXIT`.
  2. On `STATUS` and `PROCESS`, write timestamps and progress messages to `System.out`.
  3. On `FAIL` or invalid input, write structured error alerts to `System.err` with prefix `[ERROR]`.
  4. Document the differing autoflush characteristics of `System.out` and `System.err` in HotSpot JVM (e.g., standard error is typically unbuffered or line-buffered, preventing lost logs during JVM crash).
  5. Provide terminal shell instructions in comments showing how an operator redirects stdout to `app.log` and stderr to `error.log` via `java StreamSegregationLab 1> app.log 2> error.log`.
- **Verification:** Run the application via terminal redirection and verify that `app.log` contains only normal output while `error.log` contains only errors.

---

### Exercise 1.8: Resilient Input Validation Loop
- **Objective:** Construct a console input loop (`ResilientInputReader`) that prompts for an integer age between 1 and 120 and refuses to crash or proceed until valid input is supplied.
- **Requirements:**
  1. Use `Scanner.hasNextInt()` to inspect the incoming token without consuming it.
  2. If `hasNextInt()` returns `false`, read the offending input using `scanner.next()`, print an error message indicating that the input was not a valid number, and re-prompt.
  3. If `hasNextInt()` returns `true`, read the integer using `scanner.nextInt()`.
  4. Perform boundary validation: ensure the age is between `1` and `120`. If out of bounds, print an out-of-range error and re-prompt.
  5. The program must never throw `InputMismatchException` under any arbitrary user input (including strings, symbols, empty returns, or floating-point numbers).
- **Verification:** Input `abc`, `!@#`, `3.14159`, `-5`, `150`, and finally `28`. Verify that the prompt repeats gracefully and terminates only on `28`.

---

### Exercise 1.9: Comprehensive Format Specifier Catalog
- **Objective:** Create a reference class (`SpecifierShowcase`) that exercises every fundamental conversion specifier supported by `java.util.Formatter`.
- **Requirements:**
  1. Implement formatted output covering:
     - `%d`: Base-10 signed integer.
     - `%f`: Decimal floating point.
     - `%s`: String representation (handling null values cleanly).
     - `%c`: Unicode character literal.
     - `%b`: Boolean logic (demonstrate behavior with `true`, `false`, `null`, and arbitrary objects).
     - `%x` / `%X`: Lowercase and uppercase hexadecimal representation of an integer.
     - `%o`: Octal representation of an integer.
     - `%e` / `%E`: Computerized scientific notation with exponent.
     - `%h` / `%H`: Hexadecimal representation of `hashCode()`.
     - `%%`: Escaped literal percent sign.
     - `%n`: Platform-specific line separator.
  2. For each specifier, print a descriptive label, the raw variable value, and the formatted output.
  3. Include an example demonstrating argument index referencing: `%1$s %2$s %1$s` to print the first argument twice without supplying it twice.
- **Verification:** Execute the class and verify that every specifier renders valid formatting without throwing `IllegalFormatConversionException`.

---

### Exercise 1.10: Field Width, Precision, and Flag Control
- **Objective:** Construct a tabular alignment demo (`AlignmentFlagsLab`) demonstrating all combinations of width, precision, and formatting flags.
- **Requirements:**
  1. Demonstrate width:
     - Right-aligned integer in a 10-character field: `%10d`.
     - Left-aligned integer with `-` flag in a 10-character field: `%-10d`.
  2. Demonstrate zero-padding:
     - Zero-padded integer with `0` flag: `%010d`.
  3. Demonstrate sign flags:
     - Always display sign (`+` or `-`): `%+d`.
     - Display leading space for positive numbers: `% d`.
  4. Demonstrate grouping separators:
     - Locale-specific thousands grouping: `%,d` and `%,.2f`.
  5. Demonstrate precision:
     - String truncation: `%.5s` (truncate string to maximum 5 characters).
     - Floating point precision: `%.4f` (round to 4 decimal places).
  6. Combine multiple flags: left-aligned, signed, grouped float in a 15-character field.
- **Verification:** Verify that numeric columns and string headers align exactly when viewed in a monospace terminal font.

---

## 2. MEDIUM EXERCISES (10)

### Exercise 2.1: Robust Interactive CLI Calculator
- **Objective:** Implement a full interactive command-line calculator (`InteractiveCalculator`) that continuously evaluates arithmetic expressions until the user terminates the session.
- **Requirements:**
  1. Loop continuously, displaying prompt: `calc> `.
  2. Read input line-by-line using `Scanner.nextLine()`.
  3. Support commands: `quit`, `exit`, and `help`.
  4. Parse expressions containing two operands and an operator: `<operand1> <operator> <operand2>` (e.g., `14.5 * 3.2`).
  5. Supported operators: `+`, `-`, `*`, `/`, `%`, `^` (power).
  6. Error handling:
     - Handle division by zero gracefully (print error message without throwing `ArithmeticException`).
     - Handle invalid numbers without throwing `NumberFormatException`.
     - Handle malformed expression syntax (e.g., missing operands or invalid operators).
  7. Output the result formatted to 4 decimal places: `Result: %.4f%n`.
- **Verification:** Test normal calculations, division by zero (`10 / 0`), negative operands (`-5.2 + 3.1`), malformed input (`foo bar`), and exit commands.

---

### Exercise 2.2: Academic Report Card Table Generator
- **Objective:** Build a formatted student grade report generator (`ReportCardGenerator`) that takes student and course data and renders an aligned ASCII transcript.
- **Requirements:**
  1. Prompt for student name, student ID, and semester.
  2. Prompt for the number of courses, then loop to collect: Course Code (e.g., `CS101`), Course Name (e.g., `Intro to Java`), Credits (`int`), and Percentage Score (`double`).
  3. Compute Letter Grade (`A`, `B`, `C`, `D`, `F`) and Grade Points (`4.0`, `3.0`, etc.) per course.
  4. Compute cumulative GPA: `(Sum of (Credits * Grade Points)) / Total Credits`.
  5. Format output with borders:
     - Top and bottom double-line borders (`=============================`).
     - Header block with student details.
     - Table columns: `Course Code (8 chars) | Course Name (22 chars) | Credits (7 chars) | Score (7 chars) | Grade (5 chars)`.
     - Footer showing Total Credits and Cumulative GPA (`%.2f`).
- **Verification:** Test with 4 courses; verify that long course names are either cleanly aligned or truncated via precision specifier (`%-22.22s`), and table borders remain unbroken.

---

### Exercise 2.3: In-Memory Stream Redirection & Assertions
- **Objective:** Construct a unit-testable class (`OutputCaptureHarness`) that redirects `System.out` to an in-memory buffer, captures printed output, and restores the original system stream.
- **Requirements:**
  1. Cache the original `System.out` reference (`PrintStream originalOut = System.out`).
  2. Create a `ByteArrayOutputStream baos = new ByteArrayOutputStream()`.
  3. Wrap it in a `PrintStream(baos, true, StandardCharsets.UTF_8)` and redirect standard out via `System.setOut(customOut)`.
  4. Invoke a legacy method `LegacyReporter.emitReport()` that writes directly to `System.out`.
  5. In a `finally` block, guarantee restoration of the standard output stream: `System.setOut(originalOut)`.
  6. Convert captured bytes to string using `baos.toString(StandardCharsets.UTF_8)`.
  7. Perform programmatic assertions on the captured string: verify that expected headers, data rows, and timestamps are present.
- **Verification:** Execute the harness and verify that output was captured in memory and nothing leaked to the physical terminal during execution.

---

### Exercise 2.4: Delimited CSV Ingestion with Custom Scanner Regex
- **Objective:** Build a CSV data parser (`CsvScannerParser`) that ingests structured records using `Scanner.useDelimiter()`.
- **Requirements:**
  1. Construct a multiline CSV string representing employee records: `ID,Name,Department,Salary,HireDate`.
  2. Instantiate a `Scanner` on the string.
  3. Configure a custom delimiter regex that splits on commas or line terminators: `scanner.useDelimiter(",|\\r?\\n")`.
  4. Read the header row and validate expected column names.
  5. In a loop, read each row's fields: `int id`, `String name`, `String dept`, `double salary`, `String hireDate`.
  6. Handle empty values or trailing whitespace gracefully.
  7. Render parsed records into an aligned table using `printf`.
- **Verification:** Verify that Windows line endings (`\r\n`) and Unix line endings (`\n`) are both handled without generating phantom empty tokens.

---

### Exercise 2.5: Secure Credential Ingestion via `Console`
- **Objective:** Construct a secure terminal login authentication module (`SecureConsoleLogin`) utilizing `java.io.Console`.
- **Requirements:**
  1. Retrieve the system console via `System.console()`.
  2. Handle the scenario where `System.console()` returns `null` (e.g., when executed inside IntelliJ IDEA or background daemon): print an informative warning and fall back to `Scanner` (with security warning).
  3. Prompt for username using `console.readLine("%s", "Enter Username: ")`.
  4. Prompt for password using `console.readPassword("%s", "Enter Password: ")` returning `char[]`.
  5. Implement password strength validation over the `char[]` (min 12 characters, at least 1 uppercase, 1 lowercase, 1 digit, 1 special character) without converting the `char[]` to a `java.lang.String`.
  6. Immediately overwrite the `char[]` in memory with zeros (`Arrays.fill(password, '0')`) after validation to eliminate heap memory dump vulnerability.
- **Verification:** Run from a native OS terminal shell to verify password masking (no characters echoed to screen) and memory zeroing.

---

### Exercise 2.6: Reactive Stream Ingestion with `BufferedReader.lines()`
- **Objective:** Implement a log analysis pipeline (`LogStreamProcessor`) that ingests lines of server log data using `BufferedReader.lines()` and Java Streams.
- **Requirements:**
  1. Open a buffered reader over a simulated multi-thousand-line log input stream.
  2. Invoke `reader.lines()` to obtain a `Stream<String>`.
  3. Filter lines to include only entries with level `[ERROR]` or `[WARN]`.
  4. Map lines to extract timestamp, thread name, and error message into a Java 21 `record LogEntry(Instant timestamp, String level, String thread, String message)`.
  5. Group log entries by level using `Collectors.groupingBy()`.
  6. Count occurrences and compute the most frequent error message per level.
  7. Output a formatted summary report using `printf`.
  8. Ensure the underlying `BufferedReader` is closed deterministically using try-with-resources.
- **Verification:** Ingest a sample log stream and verify accurate categorization and zero resource leaks.

---

### Exercise 2.7: Temporal Formatting with Printf Time Specifiers
- **Objective:** Create a temporal reporting utility (`TimeFormattingLab`) demonstrating the full suite of date and time conversion specifiers supported by `java.util.Formatter`.
- **Requirements:**
  1. Instantiate a `ZonedDateTime` representing the current instant in UTC and user's local timezone.
  2. Demonstrate composite date/time specifiers:
     - `%tF`: ISO 8601 complete date (`YYYY-MM-DD`).
     - `%tD`: US date format (`MM/DD/YY`).
     - `%tT`: 24-hour time (`HH:MM:SS`).
     - `%tr`: 12-hour time with AM/PM (`HH:MM:SS PM`).
     - `%tc`: Complete date and time string.
  3. Demonstrate individual components using argument index referencing (`%1$t...`):
     - Year (`%1$tY`), numeric month (`%1$tm`), month name (`%1$tB`), day of month (`%1$td`), day of week (`%1$tA`).
     - Hour (`%1$tH`), minute (`%1$tM`), second (`%1$tS`), millisecond (`%1$tL`), timezone offset (`%1$tz`).
  4. Render an internationalized schedule displaying identical temporal instants formatted in US, German, and Japanese locales.
- **Verification:** Run across multiple timezones and verify proper zero-padding and locale-sensitive month/day names.

---

### Exercise 2.8: Polyglot Radix Number Formatter
- **Objective:** Construct a base-conversion display tool (`RadixFormatter`) that prompts the user for an integer and prints its representation across multiple numeral systems.
- **Requirements:**
  1. Read a 64-bit signed integer (`long`) from standard input.
  2. Format and display:
     - Standard decimal notation: `%,d`.
     - Hexadecimal (lowercase): `0x%x`.
     - Hexadecimal (uppercase): `0x%X`.
     - Octal notation: `0%o`.
     - Binary notation (formatted with leading zeros to 64 bits, grouped in 8-bit nibbles): custom formatter.
     - Scientific exponential notation: `%15.6e`.
  3. Render all representations in an aligned tabular dashboard with labeled columns.
  4. Handle negative numbers correctly across two's-complement hexadecimal and binary representations.
- **Verification:** Test with `0`, `255`, `-1`, `1048576`, and `Long.MAX_VALUE`.

---

### Exercise 2.9: I/O Throughput Profiling: Scanner vs. BufferedReader
- **Objective:** Build an execution benchmark (`IoBenchmarkLab`) comparing the ingestion speed of `Scanner` versus `BufferedReader` when processing a large text payload (100,000 lines).
- **Requirements:**
  1. Programmatically generate a temporary test file containing 100,000 lines of structured data (e.g., `record_id,timestamp,metric_value`).
  2. Benchmark `Scanner` using `hasNextLine()` and `nextLine()`: measure elapsed time in milliseconds and memory consumption.
  3. Benchmark `BufferedReader` using `readLine()`: measure elapsed time in milliseconds.
  4. Benchmark `BufferedReader.lines()`: measure elapsed time.
  5. Print a comparative benchmark table displaying:
     - Method name.
     - Total elapsed time (ms).
     - Throughput (lines per second).
     - Speedup factor relative to baseline `Scanner`.
  6. Clean up temporary test files in a `finally` block or JVM shutdown hook.
- **Verification:** Run three consecutive iterations to account for JIT warmup and record typical 5x-10x performance disparity.

---

### Exercise 2.10: Modern Java 11/21 File I/O Convenience Methods
- **Objective:** Construct a configuration file manager (`ModernFileIoLab`) leveraging Java 11 `Files.readString()` and `Files.writeString()`.
- **Requirements:**
  1. Accept a file path and a map of configuration key-value pairs.
  2. Serialize the configuration data into JSON or properties format and write to disk using `Files.writeString(path, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)`.
  3. Read the file back using `Files.readString(path, StandardCharsets.UTF_8)`.
  4. Demonstrate appending to an existing file using `StandardOpenOption.APPEND`.
  5. Contrast `Files.readString()` with legacy `FileInputStream` / `ByteArrayOutputStream` boilerplates in terms of lines of code, buffer management, and automatic resource closure.
  6. Explain in source comments the memory constraints of `Files.readString()` (loads entire file into JVM heap; unsuitable for multi-gigabyte files).
- **Verification:** Verify that files written with UTF-8 special characters (e.g., Japanese, emojis, accents) round-trip with zero encoding corruption.

---

## 3. HARD EXERCISES (5)

### Exercise 3.1: Hierarchical Terminal Application Framework
- **Objective:** Build an enterprise CLI shell (`TerminalApplicationFramework`) featuring nested navigation menus, breadcrumb tracking, dynamic input prompt validation, and graceful termination.
- **Requirements:**
  1. Create a menu tree:
     - Main Menu: `[1] User Management`, `[2] System Settings`, `[3] Reports`, `[Q] Quit`.
     - Sub-Menus (e.g., under User Management): `[1] List Users`, `[2] Add User`, `[3] Delete User`, `[B] Back`.
  2. Maintain a navigation stack (`Deque<Menu>`) to track current menu depth and render breadcrumb prompts: `App > User Management > Add User > `.
  3. Implement typed prompt readers:
     - `promptString(String label, Pattern regexValidator, String errorMsg)`
     - `promptInt(String label, int min, int max)`
     - `promptDouble(String label, double min, double max)`
     - `promptConfirmation(String question)` (accepts `y/n`, case-insensitive)
  4. Ensure standard input errors never break menu loop navigation; catch EOF (Ctrl+D / Ctrl+Z) gracefully and perform clean exit.
  5. Render clear screen and refresh display using ANSI escape codes where supported.
- **Verification:** Navigate 3 levels deep, trigger validation errors on typed prompts, use `Back` commands to return to root, and exit cleanly.

---

### Exercise 3.2: Formal Printf Format String Parser
- **Objective:** Construct a standalone format string lexer and inspector (`FormatStringParser`) that accepts an arbitrary `printf` format string and outputs an architectural analysis of every conversion specifier.
- **Requirements:**
  1. Implement a parser adhering to the formal OpenJDK format specifier grammar:
     `%[argument_index$][flags][width][.precision][tT]conversion`
  2. Parse and extract for every token:
     - Raw token string (e.g., `%-10.2f`).
     - Argument index (explicit e.g., `1$`, relative `<` (prior argument), or default ordinary index).
     - Flags: list active flags (`-`, `#`, `+`, ` `, `0`, `,`, `(`).
     - Field width (numeric or unspecified).
     - Precision (numeric or unspecified).
     - Date/Time sub-specifier (`t` or `T` followed by conversion char).
     - Conversion character (`d`, `f`, `s`, `x`, etc.).
  3. Validate specifier semantics: detect illegal flag combinations (e.g., `0` flag with `-` left-alignment flag, or precision applied to integer `%d`).
  4. Output a formatted diagnostic report for the input format string.
- **Verification:** Test against complex format strings such as `"%1$0+12.4f %<s %-20.10s %tB %%%n"` and verify accurate component extraction.

---

### Exercise 3.3: High-Performance I/O Ingestion Benchmark Suite
- **Objective:** Build an exhaustive I/O benchmarking engine (`IoBenchmarkSuite`) that benchmarks and contrasts five distinct Java input reading strategies on a 50MB file.
- **Requirements:**
  1. Strategies to evaluate:
     - Strategy 1: `Scanner` with `hasNextLine()` / `nextLine()`.
     - Strategy 2: `BufferedReader` with `readLine()` loop.
     - Strategy 3: `BufferedReader.lines()` Stream API.
     - Strategy 4: `InputStream.readAllBytes()` with byte parsing.
     - Strategy 5: NIO `FileChannel` with direct `ByteBuffer` and custom character decoding.
  2. Metrics to record per strategy:
     - Execution time (wall-clock nanoseconds converted to milliseconds).
     - Megabytes per second (MB/s) ingestion throughput.
     - Estimated memory allocation (using `ManagementFactory.getThreadMXBean()` or `Runtime` heap deltas).
  3. Ensure JVM warm-up cycles (at least 2 untimed warmup iterations) to trigger JIT C2 compilation before measurement.
  4. Render results in an aligned benchmark comparison matrix sorted by throughput descending.
- **Verification:** Verify that `FileChannel` / direct `ByteBuffer` demonstrates order-of-magnitude throughput gains over `Scanner`.

---

### Exercise 3.4: Reusable JUnit 5 System Stream Interceptor
- **Objective:** Build an enterprise-grade testing utility (`SystemIoExtension`) that intercepts `System.out`, `System.err`, and simulates `System.in` for automated unit tests without race conditions.
- **Requirements:**
  1. Create a custom test harness class `IoSandbox` that implements `AutoCloseable`.
  2. Provide methods:
     - `provideInput(String simulatedUserInput)`: Replaces `System.in` with a `ByteArrayInputStream`.
     - `getCapturedOutput()`: Returns all strings written to `System.out`.
     - `getCapturedError()`: Returns all strings written to `System.err`.
  3. In `close()`, atomically restore original system streams (`System.in`, `System.out`, `System.err`).
  4. Handle thread-safety: ensure concurrent test executions do not cross-contaminate system streams (document limitations of JVM-global `System.setOut` and implement synchronization lock).
  5. Write unit test cases verifying that CLI prompt loops can be 100% automated and asserted headlessly.
- **Verification:** Run 5 distinct tests feeding automated inputs (`"yes\n"`, `"42\n"`) and assert expected output assertions pass green.

---

### Exercise 3.5: Character Set Encoding & Transcoding Engine
- **Objective:** Construct a character encoding laboratory (`CharsetTranscoderLab`) that demonstrates encoding differences, character corruption (Mojibake), and replacement strategies across charsets.
- **Requirements:**
  1. Define a text string containing multi-byte characters: English, German umlauts (`ä, ö`), Japanese Kanji (`日本語`), accented French (`é`), and modern 4-byte UTF-16 surrogate pairs/emojis (e.g., `🚀`).
  2. Encode the string to raw byte arrays using distinct charsets:
     - `StandardCharsets.UTF_8`
     - `StandardCharsets.ISO_8859_1`
     - `StandardCharsets.US_ASCII`
     - `StandardCharsets.UTF_16BE`
     - `StandardCharsets.UTF_16LE`
  3. Decode each byte array back using both the matching charset and mismatched charsets to produce intentional Mojibake.
  4. Configure a `CharsetDecoder` with:
     - `CodingErrorAction.REPORT`: throws exception on unmappable characters.
     - `CodingErrorAction.REPLACE`: replaces unmappable bytes with `?` or `\uFFFD`.
     - `CodingErrorAction.IGNORE`: silently discards invalid bytes.
  5. Print an exhaustive byte-dump and hex-representation analysis showing how character encoding loss occurs at the binary level.
- **Verification:** Confirm that UTF-8 handles all characters while US-ASCII and ISO-8859-1 trigger replacement or exceptions on emojis and Kanji.

---

## 4. PRODUCTION PROBLEMS (3)

### Problem 4.1: Production Log Parser Heap Saturation & Scanner CPU Throttle
- **Scenario:** A production microservice ingests 2GB daily audit logs using `Scanner(file)`. During peak ingestion, GC pauses spike to 12 seconds, CPU utilization reaches 100%, and the pod is terminated by Kubernetes OOMKilled. Heap dump analysis reveals millions of short-lived `java.util.regex.Matcher` and `char[]` instances allocated inside `Scanner.hasNextLine()`.
- **Task:**
  1. Reproduce the performance degradation using a 100MB synthetic log file.
  2. Trace OpenJDK source code for `Scanner`: explain why `Scanner` allocates regex matcher state for delimiter checks on every read.
  3. Refactor the ingestion engine to use `BufferedReader` reading into a reusable buffer, or Java NIO `Files.lines()` with parallel stream processing.
  4. Benchmark heap allocations and throughput before and after refactoring.
- **Acceptance Criteria:** Memory allocations reduced by > 90%; throughput increased by at least 4x; zero `OutOfMemoryError` failures.

---

### Problem 4.2: Windows CRLF Line Ending Corruption in Cross-Platform CI/CD
- **Scenario:** A legacy billing report generator hardcodes `\n` inside `printf` format strings: `System.out.printf("INVOICE #%d\nTOTAL: $%.2f\n", id, total)`. When running on Linux dev machines, files match expected golden masters. When running on Windows build agents, downstream parsers fail with unexpected line-ending errors, or files opened in legacy Windows editors display formatting on a single un-broken line.
- **Task:**
  1. Demonstrate the exact bytecode and character output of `\n` versus `%n`.
  2. Explain why `\n` emits literal byte `0x0A` (LF) regardless of operating system, whereas `%n` invokes `System.lineSeparator()` (emitting `0x0D 0x0A` on Windows and `0x0A` on Unix/Linux).
  3. Write a static analysis regex / inspection rule to detect and flag any instance of `\n` inside `printf`, `String.format`, or `formatted()` format strings.
  4. Refactor the billing generator to use `%n` exclusively.
- **Acceptance Criteria:** Generated report files dynamically match host platform line separator conventions; pass cross-platform validation.

---

### Problem 4.3: Heap Dump Security Breach via Immutable Password Strings
- **Scenario:** A financial trading terminal captures user database credentials using `Scanner.nextLine()`. A security audit reveals that database master passwords can be extracted in plaintext by dumping JVM heap memory hours after the user has logged out. The vulnerability stems from `String` immutability: `Scanner.nextLine()` creates a `java.lang.String` which is placed in the JVM heap and cannot be cleared by application code, remaining until garbage collected.
- **Task:**
  1. Reproduce the security flaw: read a credential with `Scanner.nextLine()`, null out the reference, trigger GC, and prove via heap memory scan that the string bytes persist.
  2. Implement the remediation: replace `Scanner` with `System.console().readPassword()`, which returns a mutable `char[]`.
  3. Process the credentials using the `char[]` directly.
  4. Implement an immediate memory zero-fill routine: `Arrays.fill(passwordChars, '\0')`.
  5. Provide a fallback reader using `BufferedReader` that reads characters directly into a `char[]` when `System.console()` is unavailable.
- **Acceptance Criteria:** Passwords exist in memory only for the duration of the authentication call; post-login heap inspection reveals zero plaintext credential artifacts.

---

## 5. REFACTORING TASKS (3)

### Task 5.1: The Fatal `scanner.close()` on `System.in`
- **Scenario:** A junior developer implements a modular utility method `promptUser(String msg)`:
  ```java
  public static String promptUser(String msg) {
      try (Scanner sc = new Scanner(System.in)) {
          System.out.print(msg);
          return sc.nextLine();
      }
  }
  ```
  When `promptUser()` is called a second time in the application, it crashes with `NoSuchElementException: No line found`.
- **Requirements:**
  1. Explain why closing a `Scanner` that wraps `System.in` closes the underlying OS standard input stream, rendering it permanently unreadable for the remainder of the JVM process life.
  2. Refactor the application architecture to manage `Scanner` lifecycle properly:
     - Approach 1: Pass an application-scoped `Scanner` instance as a dependency.
     - Approach 2: Implement a non-closing `InputStream` decorator (`CloseShieldInputStream`) that suppresses the `close()` call to `System.in`.
  3. Ensure the refactored code supports multiple consecutive prompt invocations without crashing.
- **Verification:** Execute 10 consecutive prompts in a loop and verify clean input capture.

---

### Task 5.2: Brittle String Concatenation Table to Type-Safe `Formatter`
- **Scenario:** A reporting class builds an inventory report via manual string concatenation:
  ```java
  public String buildRow(int id, String name, double price, int qty) {
      return "ID: " + id + " | Name: " + name + "       | Price: $" + price + " | Qty: " + qty;
  }
  ```
  The table exhibits broken column borders when prices have varying decimal lengths or when names are longer than expected.
- **Requirements:**
  1. Refactor the method to use `java.util.Formatter` or `String.formatted()`.
  2. Enforce strict column widths:
     - `ID`: 6 digits, right-aligned, zero-padded.
     - `Name`: 20 characters, left-aligned, truncated if exceeding 20 characters.
     - `Price`: 10 characters, right-aligned, currency grouped with 2 decimal places.
     - `Qty`: 5 characters, right-aligned.
  3. Guarantee table column boundaries remain perfectly vertical regardless of variable content lengths.
- **Verification:** Test with varying prices (`5`, `1234567.891`, `0.001`) and name lengths (4 chars to 35 chars).

---

### Task 5.3: Raw Unbuffered Byte-by-Byte Loop to Modern NIO Transfer
- **Scenario:** An archival backup tool copies data using a naive byte-by-byte `FileInputStream` loop:
  ```java
  public void copyFile(File src, File dest) throws IOException {
      FileInputStream in = new FileInputStream(src);
      FileOutputStream out = new FileOutputStream(dest);
      int b;
      while ((b = in.read()) != -1) {
          out.write(b);
      }
      in.close();
      out.close();
  }
  ```
  Copying a 50MB file requires millions of individual user-to-kernel mode context switches, taking over 45 seconds.
- **Requirements:**
  1. Refactor using try-with-resources to guarantee stream closure.
  2. Replace unbuffered byte reading with modern Java 9+ `InputStream.transferTo(OutputStream)` or Java NIO `Files.copy(Path, Path, CopyOption...)`.
  3. Measure the execution time difference on a 50MB payload.
  4. Document the internal OS zero-copy mechanism (`sendfile` system call) leveraged by NIO channels.
- **Verification:** Confirm file copy time drops from tens of seconds to under 100 milliseconds.

---

## 6. DEBUGGING TASKS (3)

### Task 6.1: The Vanishing Input Line
- **Code Snippet:**
  ```java
  public class RegistrationFlow {
      public static void main(String[] args) {
          Scanner scanner = new Scanner(System.in);
          System.out.print("Enter your age: ");
          int age = scanner.nextInt();
          
          System.out.print("Enter your bio: ");
          String bio = scanner.nextLine(); // Bug: skips user input!
          
          System.out.printf("Registration complete: Age=%d, Bio='%s'%n", age, bio);
      }
  }
  ```
- **Task:**
  1. Diagnose why `bio` is assigned an empty string when the user enters an age and presses Enter.
  2. Trace the exact character stream: user types `'2'`, `'5'`, `'\n'`. What does `nextInt()` consume? What does `nextLine()` consume?
  3. Provide two distinct repairs and explain the trade-offs of each.

---

### Task 6.2: The European Decimal Separator `InputMismatchException`
- **Code Snippet:**
  ```java
  public class InvoiceReader {
      public static void main(String[] args) {
          String inputData = "10.50 20.75 30.00";
          Scanner scanner = new Scanner(inputData);
          double sum = 0;
          while (scanner.hasNextDouble()) {
              sum += scanner.nextDouble();
          }
          System.out.printf("Sum: %.2f%n", sum);
      }
  }
  ```
- **Task:**
  1. Explain why this code works when run in the United States, but throws `InputMismatchException` or terminates prematurely when run on a machine in Germany or France (where `Locale.GERMANY` or `Locale.FRANCE` is default).
  2. Explain how `Scanner` inherits `Locale.getDefault(Locale.Category.FORMAT)` and expects `,` instead of `.` for decimal points in European locales.
  3. Fix the code to ensure deterministic parsing regardless of the host machine's default locale.

---

### Task 6.3: The Headless CI / IntelliJ `NullPointerException` on `System.console()`
- **Code Snippet:**
  ```java
  public class MasterPasswordPrompt {
      public static void main(String[] args) {
          Console console = System.console();
          char[] password = console.readPassword("Enter system key: "); // Throws NPE!
          System.out.println("Key accepted.");
          Arrays.fill(password, ' ');
      }
  }
  ```
- **Task:**
  1. Identify why `System.console()` evaluates to `null` when launched inside IntelliJ IDEA, Eclipse, or automated CI/CD pipelines (Jenkins, GitHub Actions).
  2. Explain the difference between an interactive TTY console and redirected standard I/O pipes.
  3. Refactor the code to gracefully detect a `null` console and fallback to an alternate input mechanism with appropriate user notifications.

---

## 7. CODE COMPLETION (3)

### Task 7.1: Fault-Tolerant CLI Prompt Engine
- **Description:** Complete the following resilient input reader class.
- **Template:**
  ```java
  public class PromptEngine {
      private final Scanner scanner;

      public PromptEngine(InputStream in) {
          this.scanner = new Scanner(in);
      }

      public int readInt(String prompt, int min, int max) {
          // TODO: Prompt user. Loop until valid integer between min and max is entered.
          // Handle non-numeric input gracefully using scanner.hasNextInt() and scanner.next().
          return 0;
      }

      public double readDouble(String prompt, double min, double max) {
          // TODO: Prompt user. Loop until valid double between min and max is entered.
          return 0.0;
      }

      public String readNonEmptyString(String prompt) {
          // TODO: Prompt user. Loop until non-empty trimmed string is entered.
          return null;
      }
  }
  ```

---

### Task 7.2: AutoCloseable Console Redirection Spy
- **Description:** Complete the following testing spy class that intercepts `System.out` and guarantees stream restoration upon exit from a try-with-resources block.
- **Template:**
  ```java
  public class ConsoleSpy implements AutoCloseable {
      private final PrintStream originalOut;
      private final ByteArrayOutputStream capturedBytes;
      private final PrintStream spyStream;

      public ConsoleSpy() {
          this.originalOut = System.out;
          this.capturedBytes = new ByteArrayOutputStream();
          // TODO: Initialize spyStream with UTF-8 and auto-flush, then redirect System.out
      }

      public String getOutput() {
          // TODO: Return captured output as UTF-8 string
          return null;
      }

      @Override
      public void close() {
          // TODO: Restore original System.out
      }
  }
  ```

---

### Task 7.3: Dynamic Tabular Grid Generator
- **Description:** Complete the table generator to dynamically format any two-dimensional string data array with aligned columns and border headers.
- **Template:**
  ```java
  public class TableGridFormatter {
      public static String formatTable(String[] headers, String[][] rows) {
          // TODO:
          // 1. Calculate maximum column width for each column across headers and all rows.
          // 2. Build horizontal border line using '+', '-', and '|'.
          // 3. Format header row with left-aligned strings (%-<width>s).
          // 4. Format all data rows.
          // 5. Return complete formatted table string using %n line separators.
          return null;
      }
  }
  ```

---

## 8. OUTPUT PREDICTION (5)

*Predict the exact console output of each program without executing it. Explain the underlying I/O, parsing, and formatting mechanics.*

### Snippet 8.1: Scanner Token vs. Line Parsing
```java
public class ScannerTrace {
    public static void main(String[] args) {
        String data = "100  Alpha\n200 Beta\n300 Gamma";
        Scanner sc = new Scanner(data);
        
        int n1 = sc.nextInt();
        String s1 = sc.next();
        String s2 = sc.nextLine();
        int n2 = sc.nextInt();
        
        System.out.printf("n1=[%d] s1=[%s] s2=[%s] n2=[%d]%n", n1, s1, s2, n2);
    }
}
```

---

### Snippet 8.2: Printf Argument Indices and Boolean Truthiness
```java
public class PrintfTrace {
    public static void main(String[] args) {
        String text = null;
        Object obj = "Hello";
        boolean flag = false;
        
        System.out.printf("%1$b %2$b %3$b%n", text, obj, flag);
        System.out.printf("%2$s %1$05d %2$S%n", 42, "duke");
    }
}
```

---

### Snippet 8.3: PrintStream Auto-Flush Mechanics
```java
public class FlushTrace {
    public static void main(String[] args) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, false); // autoflush = false
        
        ps.print("Part 1");
        System.out.println("Bytes written 1: " + baos.size());
        
        ps.println("Part 2");
        System.out.println("Bytes written 2: " + baos.size());
        
        ps.flush();
        System.out.println("Bytes written 3: " + baos.size());
    }
}
```

---

### Snippet 8.4: Formatter Locale Divergence
```java
public class LocaleTrace {
    public static void main(String[] args) {
        double val = 1234.56;
        String us = String.format(Locale.US, "%,.2f", val);
        String de = String.format(Locale.GERMANY, "%,.2f", val);
        
        System.out.println("US: " + us);
        System.out.println("DE: " + de);
    }
}
```

---

### Snippet 8.5: Standard Out vs. Standard Err Race Condition
```java
public class InterleavingTrace {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            System.out.print("OUT-" + i + " ");
            System.err.print("ERR-" + i + " ");
        }
        System.out.println();
    }
}
```

---

## 9. ERROR FINDING (3)

*Identify the exact compile-time or runtime exception, the offending line, and the technical explanation.*

### Error Snippet 9.1: IllegalFormatConversionException
```java
public class FormatMismatchBug {
    public static void main(String[] args) {
        double accountBalance = 1500.75;
        // Attempting to print a floating-point number using decimal integer specifier %d
        System.out.printf("Current Balance: $%d%n", accountBalance);
    }
}
```

---

### Error Snippet 9.2: Premature System.in Closure
```java
public class PrematureCloseBug {
    public static void main(String[] args) {
        readName();
        readAge(); // What exception is thrown here?
    }

    static void readName() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Name: ");
            sc.nextLine();
        }
    }

    static void readAge() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Age: ");
            int age = sc.nextInt();
        }
    }
}
```

---

### Error Snippet 9.3: Missing Format Argument Exception
```java
public class MissingArgumentBug {
    public static void main(String[] args) {
        String first = "James";
        String last = "Gosling";
        // Format string contains 3 specifiers, but only 2 arguments supplied
        System.out.printf("First: %s | Middle: %s | Last: %s%n", first, last);
    }
}
```

---

## 10. OPTIMIZATION PROBLEMS (2)

### Optimization Problem 10.1: Scanner Allocation Overhead vs. Custom Char Parsing
- **Context:** In ultra-high frequency financial order-entry pipelines, receiving market ticks over standard input or socket streams using `Scanner` introduces unacceptable GC pauses. `Scanner` creates temporary `String` instances, `Matcher` state, and internal `CharBuffer` reallocations for every single primitive integer or double parsed.
- **Task:**
  1. Implement a custom zero-allocation primitive reader `FastIntReader` wrapped directly around `InputStream`.
  2. Read raw bytes directly into a fixed-size byte buffer (`byte[]`).
  3. Parse ASCII integer digits manually by accumulating bytes: `val = (val * 10) + (b - '0')`.
  4. Benchmark 1,000,000 integer token reads: compare `Scanner.nextInt()` vs. `BufferedReader.readLine() + Integer.parseInt()` vs. `FastIntReader`.
  5. Demonstrate the memory allocation difference using JVM flags `-XX:+PrintGCDetails`.

---

### Optimization Problem 10.2: PrintStream Synchronization Bottleneck in Multi-Threaded Logging
- **Context:** `System.out` is an instance of `PrintStream`. Every call to `System.out.print()` or `System.out.println()` acquires an internal monitor lock (`synchronized(this)`). When 50 concurrent worker threads write trace logs to `System.out`, thread contention causes massive thread blocking and latency degradation.
- **Task:**
  1. Reproduce the lock contention: spin up 50 threads concurrently writing 10,000 lines each to `System.out`. Record elapsed time and capture a thread dump showing threads in `BLOCKED (on monitor)` state inside `PrintStream.write()`.
  2. Architect an asynchronous non-blocking logging pipeline using a lock-free queue (`MpscUnboundedArrayQueue` or `ArrayBlockingQueue`) and a dedicated single background consumer thread.
  3. Measure the throughput improvement and verify zero lock contention among worker threads.
