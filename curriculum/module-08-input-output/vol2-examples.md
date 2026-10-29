# Java Mastery Curriculum — Module 8: Input & Output
## Volume 2: Examples & Bytecode Deep Dive

**Target Environment:** Java 21 LTS (OpenJDK 21 / HotSpot VM) & IntelliJ IDEA  
**Perspective:** Senior Java Engineer, JVM Runtime Engineer, and OpenJDK Compiler Engineer  

---

## Table of Contents
1. [Overview & JVM I/O Subsystem Architecture](#1-overview--jvm-io-subsystem-architecture)
2. [Simple Examples (10)](#2-simple-examples)
   - [2.1 `System.out.println()` with All Types: Primitives, Objects, and Null](#21-systemoutprintln-with-all-types-primitives-objects-and-null)
   - [2.2 `System.out.print()` vs `System.out.println()`: Line Terminators and Buffer Flushing](#22-systemoutprint-vs-systemoutprintln-line-terminators-and-buffer-flushing)
   - [2.3 Scanner Reading Primitives: `nextInt()`, `nextDouble()`, and `nextLine()`](#23-scanner-reading-primitives-nextint-nextdouble-and-nextline)
   - [2.4 THE SCANNER TRAP: `nextInt()` Followed by `nextLine()`](#24-the-scanner-trap-nextint-followed-by-nextline)
   - [2.5 Scanner Reading from an In-Memory String Source](#25-scanner-reading-from-an-in-memory-string-source)
   - [2.6 BufferedReader Reading Lines: `readLine()` Until EOF](#26-bufferedreader-reading-lines-readline-until-eof)
   - [2.7 `System.err.println()`: Standard Error vs Standard Output Separation](#27-systemerrprintln-standard-error-vs-standard-output-separation)
   - [2.8 `printf` Basics: `%d`, `%f`, `%s`, `%c`, `%b`, and Cross-Platform `%n`](#28-printf-basics-d-f-s-c-b-and-cross-platform-n)
   - [2.9 `printf` Width, Precision, and Numeric Flags](#29-printf-width-precision-and-numeric-flags)
   - [2.10 `String.format()` vs Modern `String.formatted()` (Java 15+)](#210-stringformat-vs-modern-stringformatted-java-15)
3. [Intermediate Examples (8)](#3-intermediate-examples)
   - [3.1 Console Reading: `System.console().readLine()` with Mandatory Null Check](#31-console-reading-systemconsolereadline-with-mandatory-null-check)
   - [3.2 Console Password Reading: `readPassword()` Returning `char[]` & Memory Zeroing](#32-console-password-reading-readpassword-returning-char-and-memory-zeroing)
   - [3.3 Scanner with Custom Delimiter: Robust CSV Parsing](#33-scanner-with-custom-delimiter-robust-csv-parsing)
   - [3.4 BufferedReader with Streams: Functional Processing via `lines()`](#34-bufferedreader-with-streams-functional-processing-via-lines)
   - [3.5 Redirecting Standard Output: Capturing Console Output for Testing](#35-redirecting-standard-output-capturing-console-output-for-testing)
   - [3.6 Printf Argument Indexing: Reusing and Reordering with `%1$s` and `%<s`](#36-printf-argument-indexing-reusing-and-reordering-with-1s-and-s)
   - [3.7 Printf Date and Time Formatting: Temporal Specifiers](#37-printf-date-and-time-formatting-temporal-specifiers)
   - [3.8 InputStreamReader with Explicit Charset: `StandardCharsets.UTF_8`](#38-inputstreamreader-with-explicit-charset-standardcharsetssutf_8)
4. [Advanced Examples (5)](#4-advanced-examples)
   - [4.1 High-Throughput Performance Benchmark: Scanner vs BufferedReader (100K Lines)](#41-high-throughput-performance-benchmark-scanner-vs-bufferedreader-100k-lines)
   - [4.2 Bytecode Disassembly: Deep Dive into `System.out.println("Hello")`](#42-bytecode-disassembly-deep-dive-into-systemoutprintlnhello)
   - [4.3 Dynamic Tabular Reporting Using Printf Width Calculations](#43-dynamic-tabular-reporting-using-printf-width-calculations)
   - [4.4 Enterprise Testing Harness: AutoCloseable Dual Stream Redirection](#44-enterprise-testing-harness-autocloseable-dual-stream-redirection)
   - [4.5 Modern Java I/O: `Files.readString()`, `writeString()`, and `transferTo()`](#45-modern-java-io-filesreadstring-writestring-and-transferto)
5. [Production Examples (3)](#5-production-examples)
   - [5.1 Interactive CLI Menu System with Validation and Non-Blocking Error Recovery](#51-interactive-cli-menu-system-with-validation-and-non-blocking-error-recovery)
   - [5.2 Production Structured Log Formatter with Printf and Thread Tracking](#52-production-structured-log-formatter-with-printf-and-thread-tracking)
   - [5.3 Production Configuration File Reader with Comment and Whitespace Handling](#53-production-configuration-file-reader-with-comment-and-whitespace-handling)
6. [Wrong Examples — Compiler Errors (8)](#6-wrong-examples--compiler-errors)
   - [6.1 Attempting to Call `readLine()` on Raw `System.in`](#61-attempting-to-call-readline-on-raw-systemin)
   - [6.2 Type Mismatch: Assigning `scanner.nextInt()` to `String`](#62-type-mismatch-assigning-scannernextint-to-string)
   - [6.3 Assigning a Value to a Method Reference: `System.out.println = ...`](#63-assigning-a-value-to-a-method-reference-systemoutprintln--)
   - [6.4 Using `Scanner` Without an Import Statement](#64-using-scanner-without-an-import-statement)
   - [6.5 Format Specifier Type Incompatibility: `%d` with Floating-Point Literal](#65-format-specifier-type-incompatibility-d-with-floating-point-literal)
   - [6.6 Variable Scope Leakage Across Closed Resources](#66-variable-scope-leakage-across-closed-resources)
   - [6.7 Direct Instantiation of `java.io.Console`](#67-direct-instantiation-of-javaautoconsole)
   - [6.8 Missing Resource Closure: Compilation Warnings with `-Xlint:all`](#68-missing-resource-closure-compilation-warnings-with--xlintall)
7. [Wrong Examples — Runtime Errors (5)](#7-wrong-examples--runtime-errors)
   - [7.1 Parsing Non-Numeric Input: `InputMismatchException`](#71-parsing-non-numeric-input-inputmismatchexception)
   - [7.2 Parsing Empty String with `Integer.parseInt()`: `NumberFormatException`](#72-parsing-empty-string-with-integerparseint-numberformatexception)
   - [7.3 Unchecked `System.console()` Invocation in IDE: `NullPointerException`](#73-unchecked-systemconsole-invocation-in-ide-nullpointerexception)
   - [7.4 Mismatched Printf Arguments: `IllegalFormatConversionException`](#74-mismatched-printf-arguments-illegalformatconversionexception)
   - [7.5 Invoking Operations on a Closed Scanner: `IllegalStateException`](#75-invoking-operations-on-a-closed-scanner-illegalstateexception)
8. [Edge Cases (6)](#8-edge-cases)
   - [8.1 `scanner.nextLine()` on Empty and Consecutive Newlines](#81-scannernextline-on-empty-and-consecutive-newlines)
   - [8.2 `System.out.print()` Without Flush in Pipe Buffering](#82-systemoutprint-without-flush-in-pipe-buffering)
   - [8.3 Reading Multilingual UTF-8 and Emojis via Scanner](#83-reading-multilingual-utf-8-and-emojis-via-scanner)
   - [8.4 Escaping the Literal Percent Sign in Format Strings](#84-escaping-the-literal-percent-sign-in-format-strings)
   - [8.5 Passing `null` to `println((Object) null)` vs `println((char[]) null)`](#85-passing-null-to-printlnobject-null-vs-printlnchar-null)
   - [8.6 `Scanner(System.in)` Never Reaches EOF Without Terminal Signal (Ctrl+D/Ctrl+Z)](#86-scannersystemin-never-reaches-eof-without-terminal-signal-ctrldctrlz)

---

## 1. Overview & JVM I/O Subsystem Architecture

At the Operating System and HotSpot Virtual Machine level, all Java input/output operations are layered abstractions over POSIX / Win32 OS kernel system calls (`read(2)`, `write(2)`, `select(2)`, `epoll(7)`, `kqueue(2)`).

When the JVM boots (`java.c` / `os_posix.cpp`), the runtime associates three standard POSIX file descriptors with public static final fields in `java.lang.System`:
- **Standard Input (stdin / fd 0):** Mapped to `System.in` (`java.io.InputStream`).
- **Standard Output (stdout / fd 1):** Mapped to `System.out` (`java.io.PrintStream`).
- **Standard Error (stderr / fd 2):** Mapped to `System.err` (`java.io.PrintStream`).

### JVM I/O Subsystem Architecture Diagram

```
+-----------------------------------------------------------------------------------+
|                              USER JAVA APPLICATION                                |
|                                                                                   |
|   +--------------------------+      +------------------+      +---------------+   |
|   | java.util.Scanner        |      | BufferedReader   |      | Files (Java11)|   |
|   | (Regex Tokenizer, 1KB buf|      | (char[8192] buf) |      | read/writeStr |   |
|   +------------+-------------+      +--------+---------+      +-------+-------+   |
|                |                             |                        |           |
|                v                             v                        |           |
|   +--------------------------+      +------------------+              |           |
|   | InputStreamReader        |      | InputStreamReader|              |           |
|   | (Charset Decoder: UTF-8) |      | (UTF-8 Decoder)  |              |           |
|   +------------+-------------+      +--------+---------+              |           |
|                |                             |                        |           |
+----------------+-----------------------------+------------------------+-----------+
| JVM RUNTIME    |                             |                        |           |
| (HotSpot)      v                             v                        v           |
|   +---------------------------------------------------------------------------+   |
|   | java.lang.System.in (fd 0) | System.out (fd 1) | System.err (fd 2)        |   |
|   | (BufferedInputStream wrapper in System.initPhase1)                         |   |
|   +---------------------------------------------------------------------------+   |
|   | JNI Layer: JVM_Read, JVM_Write (native c++ wrappers)                      |   |
+---+---------------------------------------------------------------------------+---+
| OS KERNEL                                                                         |
|   +---------------------------------------------------------------------------+   |
|   | Syscalls: read(0, buf, len), write(1, buf, len), write(2, buf, len)      |   |
|   | VFS Page Cache / TTY Driver / Pipe IPC / Socket Buffer                    |   |
+---+---------------------------------------------------------------------------+---+
| HARDWARE: Terminal Display, Keyboard Controller, NVMe SSD, Ethernet NIC           |
+-----------------------------------------------------------------------------------+
```

### Stream vs Reader/Writer Taxonomy
| Dimension | Stream API (`InputStream` / `OutputStream`) | Reader / Writer API (`Reader` / `Writer`) |
| :--- | :--- | :--- |
| **Fundamental Unit** | Raw 8-bit octets (`byte`, `0x00` - `0xFF`) | 16-bit Unicode characters (`char`, `U+0000` - `U+FFFF`) |
| **Charset Encoding** | Agnostic: transports raw binary data untouched | Obligate: decodes/encodes bytes using a `Charset` (default UTF-8) |
| **Primary Classes** | `FileInputStream`, `ByteArrayInputStream`, `PrintStream` | `FileReader`, `BufferedReader`, `StringWriter`, `PrintWriter` |
| **Buffering Strategy**| `BufferedInputStream` (default 8192 bytes) | `BufferedReader` (default 8192 chars = 16384 bytes) |
| **Ideal For** | Images, compiled `.class` files, binary IPC, network wires | Plain text files, JSON/XML documents, source code, consoles |

---

## 2. SIMPLE EXAMPLES

### 2.1 `System.out.println()` with All Types: Primitives, Objects, and Null
`PrintStream.println()` provides 10 distinct method overloads in `java.io.PrintStream`, each tailored to a specific primitive type, array, or `Object` reference.

#### Complete Java Code
```java
package module08.examples;

import java.util.Arrays;

public class SimplePrintlnAllTypes {
    public static void main(String[] args) {
        String stringVal = "Java 21 LTS";
        int intVal = 42;
        double doubleVal = 3.141592653589793;
        boolean booleanVal = true;
        char charVal = 'J';
        Object nullRef = null;

        System.out.println("String  : " + stringVal);
        System.out.println("int     : " + intVal);
        System.out.println("double  : " + doubleVal);
        System.out.println("boolean : " + booleanVal);
        System.out.println("char    : " + charVal);
        System.out.println("null ref: " + nullRef);

        int[] numbers = {1, 2, 3};
        System.out.println("Array default toString(): " + numbers);
        System.out.println("Array deep formatting   : " + Arrays.toString(numbers));
    }
}
```

#### Explanation
- `System.out` is an instance of `java.io.PrintStream`.
- When passed an `int`, `double`, `boolean`, or `char`, `PrintStream` dispatches to dedicated overloaded methods (`println(int)`, `println(double)`, etc.), converting primitives directly into character arrays without heap boxing.
- When passed an `Object`, `println(Object)` delegates to `String.valueOf(x)`, which explicitly evaluates `(x == null) ? "null" : x.toString()`.
- Passing raw arrays prints the JVM memory identifier (e.g., `[I@266474c2`) because arrays do not override `Object.toString()`. Always format arrays with `Arrays.toString()`.

#### Compiler & JVM Mechanics
- **javac:** Generates `invokevirtual` targeting the specific method signature:
  - `println(I)V` for `int`
  - `println(D)V` for `double`
  - `println(Ljava/lang/String;)V` for `String`
  - `println(Ljava/lang/Object;)V` for generic references
- **JVM:** `PrintStream.write()` utilizes an internal `BufferedWriter` backed by an `OutputStreamWriter`. Each `println()` call writes characters, appends the OS line separator, and initiates an automatic flush (`autoFlush = true` is enabled by default on `System.out`).

#### Expected Output
```text
String  : Java 21 LTS
int     : 42
double  : 3.141592653589793
boolean : true
char    : J
null ref: null
Array default toString(): [I@266474c2
Array deep formatting   : [1, 2, 3]
```

---

### 2.2 `System.out.print()` vs `System.out.println()`: Line Terminators and Buffer Flushing
`print()` writes characters without appending a trailing newline, while `println()` appends the platform-specific line separator and flushes the underlying buffer.

#### Complete Java Code
```java
package module08.examples;

public class SimplePrintVsPrintln {
    public static void main(String[] args) {
        System.out.print("Step 1: Initializing");
        System.out.print(" -> ");
        System.out.print("Step 2: Processing");
        System.out.print(" -> ");
        System.out.print("Step 3: Complete");

        // Appends OS line terminator and flushes buffer
        System.out.println();

        System.out.println("Platform line separator: " +
                (System.lineSeparator().equals("\n") ? "POSIX LF (\\n)" : "Windows CRLF (\\r\\n)"));
    }
}
```

#### Explanation
- `System.out.print()` leaves the terminal cursor immediately after the last printed character.
- Calling `System.out.println()` with no arguments executes `newLine()`, appending `System.lineSeparator()`.

#### Compiler & JVM Mechanics
- **javac:** Translates `System.out.print(String)` to `invokevirtual #... Method PrintStream.print:(Ljava/lang/String;)V` and `System.out.println()` to `invokevirtual #... Method PrintStream.println:()V`.
- **JVM:** `PrintStream` checks `autoFlush`. In standard terminal mode, `print()` writes to the internal byte buffer. The buffer might not flush to the operating system's stdout file descriptor until a newline character (`\n`) is written or `flush()` is explicitly invoked.

#### Expected Output
```text
Step 1: Initializing -> Step 2: Processing -> Step 3: Complete
Platform line separator: POSIX LF (\n)
```

---

### 2.3 Scanner Reading Primitives: `nextInt()`, `nextDouble()`, and `nextLine()`
`java.util.Scanner` is a regular-expression-based text scanner that tokenizes input using whitespace delimiters by default.

#### Complete Java Code
```java
package module08.examples;

import java.util.Scanner;

public class SimpleScannerReading {
    public static void main(String[] args) {
        String inputData = "42 3.14159 true Engineering\n";
        
        try (Scanner scanner = new Scanner(inputData)) {
            int age = scanner.nextInt();
            double piEstimate = scanner.nextDouble();
            boolean isProd = scanner.nextBoolean();
            String department = scanner.next();

            System.out.println("Parsed int     : " + age);
            System.out.println("Parsed double  : " + piEstimate);
            System.out.println("Parsed boolean : " + isProd);
            System.out.println("Parsed token   : " + department);
        }
    }
}
```

#### Explanation
- `nextInt()` skips leading whitespace, scans digits conforming to the current locale, and parses an `int`.
- `nextDouble()` parses IEEE 754 floating-point text according to the active `Locale`.
- `nextBoolean()` parses case-insensitive `"true"` or `"false"`.
- `next()` reads the next contiguous non-whitespace token.

#### Compiler & JVM Mechanics
- **javac:** Compiles `Scanner` method invocations into normal bytecode calls. The try-with-resources compiles into synthetic `try {} finally { scanner.close(); }` blocks with suppressed exception handling (JLS §14.20.3).
- **JVM:** `Scanner` internally compiles regex patterns (`Pattern.compile()`) and matches input chunks buffered inside a 1024-character buffer (`char[1024]`).

#### Expected Output
```text
Parsed int     : 42
Parsed double  : 3.14159
Parsed boolean : true
Parsed token   : Engineering
```

---

### 2.4 THE SCANNER TRAP: `nextInt()` Followed by `nextLine()`
The most notorious pitfall for Java developers is the **Scanner Newline Trap**, occurring when token-based extraction (`nextInt()`, `nextDouble()`, `next()`) is followed immediately by line-based extraction (`nextLine()`).

#### Complete Java Code
```java
package module08.examples;

import java.util.Scanner;

public class SimpleScannerTrapDemo {
    public static void main(String[] args) {
        String simulatedStream = "25\nAlice Smith\n";

        // --- RECREATING THE BUG ---
        System.out.println("--- 1. The Buggy Execution ---");
        Scanner bugScanner = new Scanner(simulatedStream);
        int age = bugScanner.nextInt();
        String name = bugScanner.nextLine(); // BUG: Reads residual '\n'
        System.out.println("Age : " + age);
        System.out.println("Name: [" + name + "] (Length: " + name.length() + ")");

        // --- FIX 1: DUMMY CONSUMPTION ---
        System.out.println("\n--- 2. Fix 1: Dummy nextLine() ---");
        Scanner fixScanner1 = new Scanner(simulatedStream);
        int ageFix1 = fixScanner1.nextInt();
        fixScanner1.nextLine(); // Consume residual newline
        String nameFix1 = fixScanner1.nextLine(); // Now reads actual input
        System.out.println("Age : " + ageFix1);
        System.out.println("Name: [" + nameFix1 + "]");

        // --- FIX 2: UNIFORM PARSING (INDUSTRY STANDARD) ---
        System.out.println("\n--- 3. Fix 2: Always Read Lines and Parse ---");
        Scanner fixScanner2 = new Scanner(simulatedStream);
        int ageFix2 = Integer.parseInt(fixScanner2.nextLine().trim());
        String nameFix2 = fixScanner2.nextLine().trim();
        System.out.println("Age : " + ageFix2);
        System.out.println("Name: [" + nameFix2 + "]");
    }
}
```

#### Step-by-Step Buffer Mechanics Trace
| Step | Action | Input Buffer State | Value Captured |
| :--- | :--- | :--- | :--- |
| **0** | Initial State | `['2', '5', '\n', 'A', 'l', 'i', 'c', 'e', ...]` | None |
| **1** | `scanner.nextInt()` | `['\n', 'A', 'l', 'i', 'c', 'e', ...]` | `25` (consumed only digits) |
| **2** | `scanner.nextLine()` (BUG) | `['A', 'l', 'i', 'c', 'e', ...]` | `""` (stopped at `'\n'`) |
| **3** | Residual Stream | `['A', 'l', 'i', 'c', 'e', ...]` | User input skipped! |

#### Expected Output
```text
--- 1. The Buggy Execution ---
Age : 25
Name: [] (Length: 0)

--- 2. Fix 1: Dummy nextLine() ---
Age : 25
Name: [Alice Smith]

--- 3. Fix 2: Always Read Lines and Parse ---
Age : 25
Name: [Alice Smith]
```

---

### 2.5 Scanner Reading from an In-Memory String Source
Scanner is not limited to `System.in` or file channels; it can parse arbitrary in-memory strings.

#### Complete Java Code
```java
package module08.examples;

import java.util.Scanner;

public class SimpleScannerFromString {
    public static void main(String[] args) {
        String payload = "HTTP/1.1 200 OK";

        try (Scanner scanner = new Scanner(payload)) {
            String protocol = scanner.next();
            int statusCode = scanner.nextInt();
            String statusText = scanner.next();

            System.out.printf("Protocol: %s | Status: %d | Message: %s%n",
                    protocol, statusCode, statusText);
        }
    }
}
```

#### Explanation
- Passing a `String` to `new Scanner(String)` creates a `StringReader` internally.
- Memory allocation is minimal because no OS socket or file handles are opened.

#### Expected Output
```text
Protocol: HTTP/1.1 | Status: 200 | Message: OK
```

---

### 2.6 BufferedReader Reading Lines: `readLine()` Until EOF
`BufferedReader` is the primary workhorse for line-by-line character input in enterprise Java applications.

#### Complete Java Code
```java
package module08.examples;

import java.io.BufferedReader;
import java.io.StringReader;

public class SimpleBufferedReaderLines {
    public static void main(String[] args) {
        String multilinePayload = """
                Release: OpenJDK 21 LTS
                Runtime: HotSpot 64-Bit Server VM
                Garbage Collector: ZGC Generational
                """;

        try (BufferedReader reader = new BufferedReader(new StringReader(multilinePayload))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                System.out.printf("[%02d] %s%n", lineNumber++, line);
            }
        } catch (Exception e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}
```

#### Explanation
- `readLine()` reads characters until encountering `\n`, `\r`, or `\r\n`. It strips the line terminator before returning the `String`.
- When end-of-stream (EOF) is reached, `readLine()` returns `null`. This makes `while ((line = reader.readLine()) != null)` the canonical idiom.

#### Compiler & JVM Mechanics
- **JVM:** `BufferedReader` maintains a `char[8192]` internal buffer. When reading from an underlying stream, it requests up to 8192 characters in a single batch, minimizing native context switches to the OS kernel.

#### Expected Output
```text
[01] Release: OpenJDK 21 LTS
[02] Runtime: HotSpot 64-Bit Server VM
[03] Garbage Collector: ZGC Generational
```

---

### 2.7 `System.err.println()`: Standard Error vs Standard Output Separation
`System.err` writes to OS file descriptor 2 (stderr), while `System.out` writes to file descriptor 1 (stdout).

#### Complete Java Code
```java
package module08.examples;

public class SimpleErrVsOut {
    public static void main(String[] args) {
        System.out.println("[STDOUT] Processing batch job #4001...");
        System.err.println("[STDERR] Warn: Network latency spike detected (45ms)");
        System.out.println("[STDOUT] Batch job #4001 completed successfully.");

        System.out.flush();
        System.err.flush();
    }
}
```

#### Explanation
- Operating systems and container orchestrators (Docker, Kubernetes) treat stdout and stderr separately: stdout is typically streamed to logging pipelines, while stderr triggers alert monitors.
- In terminal environments, stdout and stderr can interleave asynchronously because they are two separate I/O channels.

#### Expected Output
```text
[STDOUT] Processing batch job #4001...
[STDERR] Warn: Network latency spike detected (45ms)
[STDOUT] Batch job #4001 completed successfully.
```

---

### 2.8 `printf` Basics: `%d`, `%f`, `%s`, `%c`, `%b`, and Cross-Platform `%n`
`PrintStream.printf()` enables format-string-based output based on the ANSI C `printf` standard.

#### Complete Java Code
```java
package module08.examples;

public class SimplePrintfBasics {
    public static void main(String[] args) {
        String runtimeName = "HotSpot";
        int cpuCores = 16;
        double loadAverage = 1.4582;
        char licenseTier = 'A';
        boolean isClustered = true;

        System.out.printf("JVM Engine   : %s%n", runtimeName);
        System.out.printf("CPU Cores    : %d%n", cpuCores);
        System.out.printf("Load Average : %.2f%n", loadAverage);
        System.out.printf("Tier Code    : %c%n", licenseTier);
        System.out.printf("Clustered    : %b%n", isClustered);
    }
}
```

#### Format Conversion Table
| Specifier | Name | Expected Argument Type | Example Output |
| :--- | :--- | :--- | :--- |
| `%d` | Decimal Integer | `byte`, `short`, `int`, `long`, `BigInteger` | `42` |
| `%f` | Decimal Floating Point | `float`, `double`, `BigDecimal` | `3.141593` |
| `%s` | String / Object | Any `Object` (`toString()`), primitive | `"Java"` |
| `%c` | Character | `char`, `Character` | `'J'` |
| `%b` | Boolean | `boolean`, `Boolean`, or Object (null -> false) | `true` |
| `%n` | Platform Line Break | *No argument* | `\n` or `\r\n` |

> [!IMPORTANT]
> **Always use `%n` instead of `\n` in format strings.** `%n` queries `System.lineSeparator()` at runtime, ensuring proper CRLF formatting on Windows and LF on UNIX/Linux/macOS.

#### Expected Output
```text
JVM Engine   : HotSpot
CPU Cores    : 16
Load Average : 1.46
Tier Code    : A
Clustered    : true
```

---

### 2.9 `printf` Width, Precision, and Numeric Flags
Control alignment, zero-padding, signs, and grouping separators using format specifier flags.

#### Complete Java Code
```java
package module08.examples;

import java.util.Locale;

public class SimplePrintfFlags {
    public static void main(String[] args) {
        int count = 42;
        double balance = 1250000.75;
        String label = "Account Balance";

        // Right-aligned in 10 spaces
        System.out.printf("Right-aligned : [%10d]%n", count);

        // Zero-padded in 8 spaces
        System.out.printf("Zero-padded   : [%08d]%n", count);

        // Explicit positive sign
        System.out.printf("Signed        : [%+10d]%n", count);

        // Left-aligned in 20 spaces
        System.out.printf("Left-aligned  : [%-20s]%n", label);

        // Thousands separators and 2 decimal places (US Locale)
        System.out.printf(Locale.US, "Currency      : [$%,.2f]%n", balance);
    }
}
```

#### Format Syntax Anatomy
```
%[argument_index$][flags][width][.precision]conversion
       │             │      │          │         └─ 'd', 'f', 's', 'n'
       │             │      │          └─ decimal places for floating-point
       │             │      └─ minimum character width
       │             └─ '-', '+', '0', ',', ' '
       └─ '1$', '2$', '<' (re-reference argument)
```

#### Expected Output
```text
Right-aligned : [        42]
Zero-padded   : [00000042]
Signed        : [       +42]
Left-aligned  : [Account Balance     ]
Currency      : [$1,250,000.75]
```

---

### 2.10 `String.format()` vs Modern `String.formatted()` (Java 15+)
Java 15 introduced `formatted(Object... args)` directly on `java.lang.String`.

#### Complete Java Code
```java
package module08.examples;

public class SimpleStringFormatted {
    public static void main(String[] args) {
        String service = "AuthService";
        int port = 8443;

        // Classic static method
        String classic = String.format("Service %s listening on port %d", service, port);

        // Modern Java 15+ instance method (ideal with Text Blocks)
        String modern = "Service %s listening on port %d".formatted(service, port);

        System.out.println("Classic: " + classic);
        System.out.println("Modern : " + modern);

        // Modern formatted() chained with Java 15+ Text Blocks
        String jsonPayload = """
                {
                  "service": "%s",
                  "port": %d,
                  "active": true
                }
                """.formatted(service, port);

        System.out.println("\nJSON Payload:\n" + jsonPayload);
    }
}
```

#### Compiler & JVM Mechanics
- `String.formatted(Object... args)` is syntactic sugar implemented in `java.lang.String`:
  ```java
  public String formatted(Object... args) {
      return new Formatter().format(this, args).toString();
  }
  ```
- It allows fluent chaining on string literals and multiline text blocks without wrapping the whole statement inside `String.format(...)`.

#### Expected Output
```text
Classic: Service AuthService listening on port 8443
Modern : Service AuthService listening on port 8443

JSON Payload:
{
  "service": "AuthService",
  "port": 8443,
  "active": true
}
```

---

## 3. INTERMEDIATE EXAMPLES

### 3.1 Console Reading: `System.console().readLine()` with Mandatory Null Check
`System.console()` provides direct access to the character-based console device attached to the current JVM.

#### Complete Java Code
```java
package module08.examples;

import java.io.Console;

public class IntermediateConsoleCheck {
    public static void main(String[] args) {
        Console console = System.console();

        if (console == null) {
            System.out.println("[WARN] No interactive console attached to JVM.");
            System.out.println("Reason: Executing inside an IDE run window, pipe, or background daemon.");
            System.out.println("Use terminal emulation in IntelliJ: Edit Configurations -> 'Emulate terminal in output console'.");
            return;
        }

        // Only executed when attached to a real OS terminal (TTY/PTY)
        String username = console.readLine("Enter username: ");
        console.printf("Welcome, %s!%n", username);
    }
}
```

#### Explanation
- `System.console()` returns `null` if the JVM is started without an attached TTY (e.g. redirected I/O, background service, or default IntelliJ IDEA output console).
- Unconditionally calling `System.console().readLine()` in an IDE without a null check throws `NullPointerException` immediately.

#### Expected Output (Inside IDE Run Window)
```text
[WARN] No interactive console attached to JVM.
Reason: Executing inside an IDE run window, pipe, or background daemon.
Use terminal emulation in IntelliJ: Edit Configurations -> 'Emulate terminal in output console'.
```

---

### 3.2 Console Password Reading: `readPassword()` Returning `char[]` & Memory Zeroing
Secure credential handling demands `char[]` arrays rather than immutable `String` objects.

#### Complete Java Code
```java
package module08.examples;

import java.io.Console;
import java.util.Arrays;

public class IntermediatePasswordSecurity {
    public static void main(String[] args) {
        Console console = System.console();

        char[] password;
        if (console != null) {
            password = console.readPassword("Enter DB secret: ");
        } else {
            // Simulated secure credential for demonstration
            password = new char[]{'k', 'e', 'y', '-', '9', '8', '7', '6'};
        }

        try {
            System.out.println("Step 1: Authenticating credential...");
            authenticate(password);
        } finally {
            // STEP 2: ZERO OUT MEMORY IMMEDIATELY
            Arrays.fill(password, '\0');
            System.out.println("Step 3: Password scrubbed from memory: " + Arrays.toString(password));
        }
    }

    private static void authenticate(char[] pwd) {
        System.out.println("Step 2: Authentication successful (length = " + pwd.length + ")");
    }
}
```

#### Security Rationale: Why `char[]` Beats `String`
```
+-------------------------------------------------------------------------+
|                              JVM HEAP MEMORY                            |
+-------------------------------------------------------------------------+
| String "secret123"                                                      |
| - Value is immutable! System cannot overwrite underlying byte[]         |
| - Remains in OldGen / YoungGen until Garbage Collection triggers        |
| - Vulnerable to: jcmd heap_dump, OS core dump, memory scraper attacks   |
+-------------------------------------------------------------------------+
| char[] {'s', 'e', 'c', 'r', 'e', 't', '1', '2', '3'}                   |
| - Array content is MUTABLE!                                             |
| - Arrays.fill(pwd, '\0') immediately overwrites memory with zeroes     |
| - Window of vulnerability is minimized to microseconds                 |
+-------------------------------------------------------------------------+
```

#### Expected Output
```text
Step 1: Authenticating credential...
Step 2: Authentication successful (length = 8)
Step 3: Password scrubbed from memory: [\0, \0, \0, \0, \0, \0, \0, \0]
```

---

### 3.3 Scanner with Custom Delimiter: Robust CSV Parsing
Configure Scanner delimiters using regular expressions to parse structured tabular text.

#### Complete Java Code
```java
package module08.examples;

import java.util.Scanner;

public class IntermediateScannerDelimiter {
    public static void main(String[] args) {
        String csv = """
                101, Alice Smith , 98500.00, Core Platform
                102, Bob Jones   , 87200.50, Site Reliability
                """;

        try (Scanner lineScanner = new Scanner(csv)) {
            while (lineScanner.hasNextLine()) {
                String line = lineScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                // Delimiter matches comma surrounded by optional whitespace
                try (Scanner tokenScanner = new Scanner(line)) {
                    tokenScanner.useDelimiter("\\s*,\\s*");

                    int id = tokenScanner.nextInt();
                    String name = tokenScanner.next();
                    double salary = tokenScanner.nextDouble();
                    String team = tokenScanner.next();

                    System.out.printf("ID: %d | Name: %-12s | Team: %-16s | Salary: $%,.2f%n",
                            id, name, team, salary);
                }
            }
        }
    }
}
```

#### Explanation
- `tokenScanner.useDelimiter("\\s*,\\s*")` tells Scanner to treat any comma preceded or followed by zero or more whitespace characters as a token boundary.
- Nested Scanners prevent line spillover: if a row has missing columns, the error stays localized to that row.

#### Expected Output
```text
ID: 101 | Name: Alice Smith  | Team: Core Platform   | Salary: $98,500.00
ID: 102 | Name: Bob Jones    | Team: Site Reliability | Salary: $87,200.50
```

---

### 3.4 BufferedReader with Streams: Functional Processing via `lines()`
Java 8 added `BufferedReader.lines()`, bridging character readers to the functional `Stream<String>` API.

#### Complete Java Code
```java
package module08.examples;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

public class IntermediateBufferedReaderStream {
    public static void main(String[] args) {
        String serverLogs = """
                [INFO]  2026-10-28 10:00:01 Status 200 GET /health
                [ERROR] 2026-10-28 10:00:05 Status 500 POST /checkout
                [WARN]  2026-10-28 10:00:12 Status 404 GET /favicon.ico
                [ERROR] 2026-10-28 10:00:20 Status 503 GET /inventory
                """;

        try (BufferedReader reader = new BufferedReader(new StringReader(serverLogs))) {
            List<String> failedEndpoints = reader.lines()
                    .filter(line -> line.contains("[ERROR]"))
                    .map(line -> line.substring(line.lastIndexOf(' ') + 1))
                    .sorted()
                    .toList();

            System.out.println("Critical Failed Endpoints: " + failedEndpoints);
        } catch (Exception e) {
            System.err.println("Processing failed: " + e.getMessage());
        }
    }
}
```

#### Compiler & JVM Mechanics
- `reader.lines()` creates a lazy `Stream` backed by an internal `Spliterator` (`StreamSupport.stream(Spliterators.spliteratorUnknownSize(...))`).
- Memory footprint is extremely light because lines are streamed on demand; the JVM does not load the entire payload into a list upfront.

#### Expected Output
```text
Critical Failed Endpoints: [/checkout, /inventory]
```

---

### 3.5 Redirecting Standard Output: Capturing Console Output for Testing
Automated testing often requires intercepting console output to assert correct behavior.

#### Complete Java Code
```java
package module08.examples;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class IntermediateSystemOutRedirection {
    public static void main(String[] args) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            // Redirect standard output to our in-memory buffer
            System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));

            // Run business logic that writes to stdout
            generateReport();

        } finally {
            // ALWAYS restore original stdout in finally block
            System.setOut(originalOut);
        }

        // Verify captured content
        String output = buffer.toString(StandardCharsets.UTF_8);
        System.out.println("Captured Output:\n" + output);
        System.out.println("Contains 'AUDIT PASS': " + output.contains("AUDIT PASS"));
    }

    private static void generateReport() {
        System.out.println("SECURITY REPORT: AUDIT PASS");
        System.out.println("Checksum: 0xDEADBEEF");
    }
}
```

#### Expected Output
```text
Captured Output:
SECURITY REPORT: AUDIT PASS
Checksum: 0xDEADBEEF

Contains 'AUDIT PASS': true
```

---

### 3.6 Printf Argument Indexing: Reusing and Reordering with `%1$s` and `%<s`
Format specifiers support explicit indexing (`1$`, `2$`) and relative indexing (`<`).

#### Complete Java Code
```java
package module08.examples;

public class IntermediateArgumentIndexing {
    public static void main(String[] args) {
        String first = "Ada";
        String last = "Lovelace";

        // Reordering parameters: 2$ is second argument, 1$ is first
        System.out.printf("Western Order: %1$s %2$s%n", first, last);
        System.out.printf("Eastern Order: %2$s, %1$s%n", first, last);

        // Relative index '<' reuses the argument from the previous format specifier
        int byteVal = 255;
        System.out.printf("Decimal: %d | Hex: 0x%<02X | Octal: 0%<o (using %%< relative)%n", byteVal);
    }
}
```

#### Expected Output
```text
Western Order: Ada Lovelace
Eastern Order: Lovelace, Ada
Decimal: 255 | Hex: 0xFF | Octal: 0377 (using %< relative)
```

---

### 3.7 Printf Date and Time Formatting: Temporal Specifiers
The formatter includes a rich set of temporal specifiers prefixed by `t` or `T`.

#### Complete Java Code
```java
package module08.examples;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class IntermediateDateTimeFormatting {
    public static void main(String[] args) {
        LocalDateTime local = LocalDateTime.of(2026, 10, 28, 14, 30, 45);
        ZonedDateTime zoned = local.atZone(ZoneId.of("UTC"));

        // %tF: ISO Date (YYYY-MM-DD)
        // %tT: 24-hour time (HH:MM:SS)
        // %tD: US Date (MM/DD/YY)
        // %tB: Full month name
        // %tc: Complete date/time (requires Zone information)
        System.out.printf("ISO Date   (%%tF) : %tF%n", local);
        System.out.printf("Time       (%%tT) : %tT%n", local);
        System.out.printf("US Date    (%%tD) : %tD%n", local);
        System.out.printf("Month Name (%%tB) : %tB%n", local);
        System.out.printf("Full Clock (%%tc) : %tc%n", zoned);
    }
}
```

#### Expected Output
```text
ISO Date   (%tF) : 2026-10-28
Time       (%tT) : 14:30:45
US Date    (%tD) : 10/28/26
Month Name (%tB) : October
Full Clock (%tc) : Wed Oct 28 14:30:45 UTC 2026
```

---

### 3.8 InputStreamReader with Explicit Charset: `StandardCharsets.UTF_8`
Always bridge byte streams to character readers using an explicit `Charset` object.

#### Complete Java Code
```java
package module08.examples;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class IntermediateExplicitCharset {
    public static void main(String[] args) {
        byte[] utf8Data = "Microservice \u26A1 [Online] \u6771\u4eac".getBytes(StandardCharsets.UTF_8);
        InputStream is = new ByteArrayInputStream(utf8Data);

        // Explicitly specifying StandardCharsets.UTF_8 avoids platform encoding discrepancies
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String decoded = reader.readLine();
            System.out.println("Decoded String: " + decoded);
        } catch (Exception e) {
            System.err.println("Decode error: " + e.getMessage());
        }
    }
}
```

#### Architectural Note: JEP 400 (UTF-8 by Default in Java 18+)
> [!NOTE]
> Starting with Java 18 (JEP 400), the default charset of the Java runtime environment is standardized to `UTF-8`. However, enterprise standards strictly mandate passing `StandardCharsets.UTF_8` explicitly to guarantee immutability across legacy environments, Windows systems, and custom JVM launch configurations (`-Dfile.encoding`).

#### Expected Output
```text
Decoded String: Microservice ⚡ [Online] 東京
```

---

## 4. ADVANCED EXAMPLES

### 4.1 High-Throughput Performance Benchmark: Scanner vs BufferedReader (100K Lines)
Comparing throughput and execution time when processing 100,000 text lines.

#### Complete Java Code
```java
package module08.examples;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Scanner;

public class AdvancedPerformanceBenchmark {
    public static void main(String[] args) {
        int lineCount = 100_000;
        StringBuilder sb = new StringBuilder(lineCount * 28);
        for (int i = 0; i < lineCount; i++) {
            sb.append("Payload record sequence index #").append(i).append("\n");
        }
        String data = sb.toString();

        // Benchmark BufferedReader
        long startBuffered = System.nanoTime();
        int bufferedLines = 0;
        try (BufferedReader reader = new BufferedReader(new StringReader(data))) {
            while (reader.readLine() != null) {
                bufferedLines++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long durationBufferedMs = (System.nanoTime() - startBuffered) / 1_000_000;

        // Benchmark Scanner
        long startScanner = System.nanoTime();
        int scannerLines = 0;
        try (Scanner scanner = new Scanner(data)) {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                scannerLines++;
            }
        }
        long durationScannerMs = (System.nanoTime() - startScanner) / 1_000_000;

        System.out.printf("BufferedReader parsed %,d lines in : %4d ms%n", bufferedLines, durationBufferedMs);
        System.out.printf("Scanner        parsed %,d lines in : %4d ms%n", scannerLines, durationScannerMs);

        double speedup = (double) durationScannerMs / Math.max(1, durationBufferedMs);
        System.out.printf("Performance Verdict: BufferedReader is ~%.1fx faster than Scanner.%n", speedup);
    }
}
```

#### Why BufferedReader Wins
1. **Buffer Size:** `BufferedReader` uses an internal `char[8192]`, reading massive blocks at once. `Scanner` uses a `char[1024]` buffer.
2. **Regex Parsing Overhead:** `Scanner` matches every token against compiled regular expression DFAs (`java.util.regex.Matcher`), creating allocation pressure and CPU cycles. `BufferedReader.readLine()` scans directly for `\n` or `\r`.
3. **Synchronization:** `BufferedReader` methods synchronize on an internal lock object, but modern HotSpot biased locking / lock elision minimizes lock overhead.

#### Expected Output
```text
BufferedReader parsed 100,000 lines in :   23 ms
Scanner        parsed 100,000 lines in :  129 ms
Performance Verdict: BufferedReader is ~5.6x faster than Scanner.
```

---

### 4.2 Bytecode Disassembly: Deep Dive into `System.out.println("Hello")`
Disassembling the bytecode generated by `javac` using `javap -c -v`.

#### Complete Java Code
```java
package module08.examples;

public class AdvancedBytecodeHello {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
```

#### Bytecode Disassembly (`javap -c -v`)
```bytecode
Classfile /tmp/AdvancedBytecodeHello.class
  minor version: 0
  major version: 65 (Java 21)
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #2.#3          // java/lang/Object."<init>":()V
   #2 = Class              #4             // java/lang/Object
   #3 = NameAndType        #5:#6          // "<init>":()V
   #7 = Fieldref           #8.#9          // java/lang/System.out:Ljava/io/PrintStream;
   #8 = Class              #10            // java/lang/System
   #9 = NameAndType        #11:#12        // out:Ljava/io/PrintStream;
  #10 = Utf8               java/lang/System
  #11 = Utf8               out
  #12 = Utf8               Ljava/io/PrintStream;
  #13 = String             #14            // Hello
  #14 = Utf8               Hello
  #15 = Methodref          #16.#17        // java/io/PrintStream.println:(Ljava/lang/String;)V
  #16 = Class              #18            // java/io/PrintStream
  #17 = NameAndType        #19:#20        // println:(Ljava/lang/String;)V
{
  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=1, args_size=1
         0: getstatic     #7                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #13                 // String Hello
         5: invokevirtual #15                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: return
}
```

#### Step-by-Step JVM Instruction Analysis
1. `0: getstatic #7`: Reads the static field `System.out` from the JVM class mirror on the heap and pushes the `PrintStream` object reference onto the operand stack (`stack depth: 1`).
2. `3: ldc #13`: Loads the constant String `"Hello"` from the constant pool (resolved in the String Intern Table) and pushes its reference onto the operand stack (`stack depth: 2`).
3. `5: invokevirtual #15`: Pops the string argument and the receiver `PrintStream` reference. Resolves the virtual method table (vtable) entry for `println(String)` on the concrete `PrintStream` class and executes it.
4. `8: return`: Returns `void` to caller, deallocating the stack frame.

---

### 4.3 Dynamic Tabular Reporting Using Printf Width Calculations
Building dynamic CLI tables with aligned columns based on runtime data.

#### Complete Java Code
```java
package module08.examples;

import java.util.Locale;

public class AdvancedDynamicTable {
    public record ServerMetrics(String host, int port, double cpuPercent, long memoryBytes) {}

    public static void main(String[] args) {
        ServerMetrics[] servers = {
                new ServerMetrics("prod-eu-west-01.internal", 8080, 12.45, 17_179_869_184L),
                new ServerMetrics("prod-us-east-02.internal", 8443, 78.90, 34_359_738_368L),
                new ServerMetrics("dev-sandbox.local", 9000, 3.10, 4_294_967_296L)
        };

        String headerFormat = "| %-26s | %5s | %8s | %12s |%n";
        String rowFormat    = "| %-26s | %5d | %7.2f%% | %9.2f GB |%n";
        String divider      = "+----------------------------+-------+----------+--------------+";

        System.out.println(divider);
        System.out.printf(headerFormat, "HOSTNAME", "PORT", "CPU LOAD", "RAM USAGE");
        System.out.println(divider);

        for (ServerMetrics s : servers) {
            double memGb = s.memoryBytes() / (1024.0 * 1024.0 * 1024.0);
            System.out.printf(Locale.US, rowFormat, s.host(), s.port(), s.cpuPercent(), memGb);
        }

        System.out.println(divider);
    }
}
```

#### Expected Output
```text
+----------------------------+-------+----------+--------------+
| HOSTNAME                   |  PORT | CPU LOAD |    RAM USAGE |
+----------------------------+-------+----------+--------------+
| prod-eu-west-01.internal   |  8080 |   12.45% |    16.00 GB |
| prod-us-east-02.internal   |  8443 |   78.90% |    32.00 GB |
| dev-sandbox.local          |  9000 |    3.10% |     4.00 GB |
+----------------------------+-------+----------+--------------+
```

---

### 4.4 Enterprise Testing Harness: AutoCloseable Dual Stream Redirection
A thread-safe test capture utility implementing `AutoCloseable` for clean resource isolation.

#### Complete Java Code
```java
package module08.examples;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class AdvancedTestingHarness {

    public static class SystemCapture implements AutoCloseable {
        private final PrintStream originalOut = System.out;
        private final PrintStream originalErr = System.err;
        private final ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();
        private final ByteArrayOutputStream capturedErr = new ByteArrayOutputStream();

        public SystemCapture() {
            System.setOut(new PrintStream(capturedOut, true, StandardCharsets.UTF_8));
            System.setErr(new PrintStream(capturedErr, true, StandardCharsets.UTF_8));
        }

        public String getStdout() {
            return capturedOut.toString(StandardCharsets.UTF_8);
        }

        public String getStderr() {
            return capturedErr.toString(StandardCharsets.UTF_8);
        }

        @Override
        public void close() {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }

    public static void main(String[] args) {
        try (SystemCapture capture = new SystemCapture()) {
            System.out.println("Processing transaction #90210");
            System.err.println("WARN: Retry attempt 1");

            // Verify assertions inside test block
            assert capture.getStdout().contains("transaction #90210");
            assert capture.getStderr().contains("Retry attempt 1");
        }

        // Outside try-with-resources: original streams are guaranteed restored
        System.out.println("Dual stream redirection harness restored stdout & stderr successfully.");
    }
}
```

#### Expected Output
```text
Dual stream redirection harness restored stdout & stderr successfully.
```

---

### 4.5 Modern Java I/O: `Files.readString()`, `writeString()`, and `transferTo()`
Java 9 and 11 eliminated decades of boilerplate stream wrapping with high-performance convenience methods.

#### Complete Java Code
```java
package module08.examples;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class AdvancedModernIO {
    public static void main(String[] args) throws IOException {
        // 1. Files.writeString and Files.readString (Java 11)
        Path temp = Files.createTempFile("modern-io-", ".txt");
        try {
            String text = "Java 21 Modern I/O: Concise, Safe, Fast.";
            Files.writeString(temp, text, StandardCharsets.UTF_8, StandardOpenOption.CREATE);

            String readContent = Files.readString(temp, StandardCharsets.UTF_8);
            System.out.println("Files.readString(): " + readContent);
        } finally {
            Files.deleteIfExists(temp);
        }

        // 2. InputStream.transferTo(OutputStream) (Java 9)
        byte[] rawBytes = "Direct stream transfer pipeline".getBytes(StandardCharsets.UTF_8);
        InputStream source = new ByteArrayInputStream(rawBytes);
        ByteArrayOutputStream destination = new ByteArrayOutputStream();

        long bytesTransferred = source.transferTo(destination);
        System.out.printf("transferred %,d bytes via transferTo(): %s%n",
                bytesTransferred, destination.toString(StandardCharsets.UTF_8));

        // 3. Null Streams (Java 9) - /dev/null equivalent
        try (OutputStream nullOut = OutputStream.nullOutputStream()) {
            nullOut.write("Discarded bytes".getBytes(StandardCharsets.UTF_8));
            System.out.println("OutputStream.nullOutputStream() discarded payload without allocation.");
        }
    }
}
```

#### Expected Output
```text
Files.readString(): Java 21 Modern I/O: Concise, Safe, Fast.
transferred 31 bytes via transferTo(): Direct stream transfer pipeline
OutputStream.nullOutputStream() discarded payload without allocation.
```

---

## 5. PRODUCTION EXAMPLES

### 5.1 Interactive CLI Menu System with Validation and Non-Blocking Error Recovery
An enterprise-grade console menu demonstrating robust input loops, error suppression, and clean exit semantics.

#### Complete Java Code
```java
package module08.examples;

import java.util.Scanner;

public class ProductionMenuSystem {

    public static void main(String[] args) {
        String simulatedInputs = "99\nfoo\n1\n3\n";
        Scanner scanner = new Scanner(simulatedInputs);

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter choice (1-3): ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> handleViewProfile();
                case "2" -> handleUpdateSettings(scanner);
                case "3" -> {
                    System.out.println("Shutting down CLI session. Goodbye!");
                    running = false;
                }
                default -> System.err.printf("Invalid choice: '%s'. Please enter 1, 2, or 3.%n%n", input);
            }
        }
    }

    private static void printMenu() {
        System.out.println("=== SYSTEM ADMINISTRATION CONSOLE ===");
        System.out.println("1. View Node Metrics");
        System.out.println("2. Modify Configuration");
        System.out.println("3. Exit");
    }

    private static void handleViewProfile() {
        System.out.println(">> [METRICS] All cluster nodes reporting HEALTHY (0 alarms).\n");
    }

    private static void handleUpdateSettings(Scanner scanner) {
        System.out.println(">> [CONFIG] Configuration menu placeholder.\n");
    }
}
```

#### Expected Output
```text
=== SYSTEM ADMINISTRATION CONSOLE ===
1. View Node Metrics
2. Modify Configuration
3. Exit
Enter choice (1-3): Invalid choice: '99'. Please enter 1, 2, or 3.

=== SYSTEM ADMINISTRATION CONSOLE ===
1. View Node Metrics
2. Modify Configuration
3. Exit
Enter choice (1-3): Invalid choice: 'foo'. Please enter 1, 2, or 3.

=== SYSTEM ADMINISTRATION CONSOLE ===
1. View Node Metrics
2. Modify Configuration
3. Exit
Enter choice (1-3): >> [METRICS] All cluster nodes reporting HEALTHY (0 alarms).

=== SYSTEM ADMINISTRATION CONSOLE ===
1. View Node Metrics
2. Modify Configuration
3. Exit
Enter choice (1-3): Shutting down CLI session. Goodbye!
```

---

### 5.2 Production Structured Log Formatter with Printf and Thread Tracking
High-efficiency, zero-dependency structured console logger adhering to enterprise standards.

#### Complete Java Code
```java
package module08.examples;

import java.time.LocalDateTime;

public class ProductionStructuredLogger {

    public enum LogLevel { DEBUG, INFO, WARN, ERROR }

    public static void log(LogLevel level, String component, String message, Object... args) {
        String formattedMessage = (args.length > 0) ? String.format(message, args) : message;
        LocalDateTime now = LocalDateTime.now();
        String threadName = Thread.currentThread().getName();

        // ISO timestamp | LEVEL | [Thread] Component - Message
        System.out.printf("%tF %<tT.%<tL | %-5s | [%-15s] %-18s : %s%n",
                now, level, threadName, component, formattedMessage);
    }

    public static void main(String[] args) {
        log(LogLevel.INFO,  "Bootstrap", "JVM version: %s (PID: %d)", System.getProperty("java.version"), ProcessHandle.current().pid());
        log(LogLevel.WARN,  "DatabasePool", "Connection pool usage at 85%% (%d/%d connections)", 85, 100);
        log(LogLevel.ERROR, "SecurityAuth", "Failed login attempt from IP: %s", "192.168.1.50");
    }
}
```

#### Expected Output
```text
2026-10-28 14:30:45.120 | INFO  | [main           ] Bootstrap          : JVM version: 21.0.11 (PID: 74218)
2026-10-28 14:30:45.122 | WARN  | [main           ] DatabasePool       : Connection pool usage at 85% (85/100 connections)
2026-10-28 14:30:45.123 | ERROR | [main           ] SecurityAuth       : Failed login attempt from IP: 192.168.1.50
```

---

### 5.3 Production Configuration File Reader with Comment and Whitespace Handling
Parser for `.env` / `.properties` files supporting inline comments and empty line skipping.

#### Complete Java Code
```java
package module08.examples;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductionConfigReader {

    public static Map<String, String> parseConfiguration(String text) {
        Map<String, String> properties = new LinkedHashMap<>();

        try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
            String rawLine;
            int lineNumber = 0;

            while ((rawLine = reader.readLine()) != null) {
                lineNumber++;
                String line = rawLine.trim();

                // Skip blank lines and full-line comments
                if (line.isEmpty() || line.startsWith("#") || line.startsWith("//")) {
                    continue;
                }

                int delimiterIndex = line.indexOf('=');
                if (delimiterIndex == -1) {
                    System.err.printf("[WARN] Line %d malformed (missing '='): '%s'%n", lineNumber, line);
                    continue;
                }

                String key = line.substring(0, delimiterIndex).trim();
                String value = line.substring(delimiterIndex + 1).trim();

                // Strip trailing comments
                int commentIndex = value.indexOf('#');
                if (commentIndex != -1) {
                    value = value.substring(0, commentIndex).trim();
                }

                properties.put(key, value);
            }
        } catch (Exception e) {
            System.err.println("Failed reading config: " + e.getMessage());
        }

        return properties;
    }

    public static void main(String[] args) {
        String configSource = """
                # Cluster Configuration
                cluster.name = Andromeda-Prod
                cluster.nodes = 12 # Total active nodes
                
                // Networking
                http.port = 8080
                http.ssl.enabled = true
                invalid_line_without_equals
                """;

        Map<String, String> config = parseConfiguration(configSource);
        System.out.println("Parsed Configuration Properties:");
        config.forEach((k, v) -> System.out.printf("  %-18s -> '%s'%n", k, v));
    }
}
```

#### Expected Output
```text
[WARN] Line 8 malformed (missing '='): 'invalid_line_without_equals'
Parsed Configuration Properties:
  cluster.name       -> 'Andromeda-Prod'
  cluster.nodes      -> '12'
  http.port          -> '8080'
  http.ssl.enabled   -> 'true'
```

---

## 6. WRONG EXAMPLES — COMPILER ERRORS

### 6.1 Attempting to Call `readLine()` on Raw `System.in`
```java
package module08.examples;

public class WrongSystemInReadLine {
    public static void main(String[] args) {
        // ERROR: cannot find symbol method readLine() in class java.io.InputStream
        // String input = System.in.readLine();
    }
}
```
- **Error:** `error: cannot find symbol method readLine()`
- **Cause:** `System.in` has static type `java.io.InputStream`. Raw byte input streams only provide low-level byte-reading methods (`read()`, `read(byte[])`). Character- and line-reading operations require decorating `System.in` with `InputStreamReader` and `BufferedReader`, or wrapping it with `Scanner`.

---

### 6.2 Type Mismatch: Assigning `scanner.nextInt()` to `String`
```java
package module08.examples;

import java.util.Scanner;

public class WrongScannerReturnType {
    public static void main(String[] args) {
        Scanner scanner = new Scanner("123");
        // ERROR: incompatible types: int cannot be converted to java.lang.String
        // String s = scanner.nextInt();
    }
}
```
- **Error:** `error: incompatible types: int cannot be converted to String`
- **Cause:** `Scanner.nextInt()` returns primitive `int`. The compiler strictly forbids implicit conversion from primitive `int` to reference type `java.lang.String`. Use `scanner.next()` or `String.valueOf(scanner.nextInt())`.

---

### 6.3 Assigning a Value to a Method Reference: `System.out.println = ...`
```java
package module08.examples;

public class WrongMethodAssignment {
    public static void main(String[] args) {
        // ERROR: cannot find symbol variable println
        // System.out.println = "hello";
    }
}
```
- **Error:** `error: cannot find symbol variable println`
- **Cause:** `println` is a method identifier, not a field. In Java syntax, method identifiers cannot appear on the left-hand side of an assignment operator (`=`).

---

### 6.4 Using `Scanner` Without an Import Statement
```java
package module08.examples;

public class WrongMissingImport {
    public static void main(String[] args) {
        // ERROR: cannot find symbol class Scanner
        // Scanner sc = new Scanner(System.in);
    }
}
```
- **Error:** `error: cannot find symbol class Scanner`
- **Cause:** Only classes in `java.lang` are imported implicitly. `Scanner` resides in `java.util.Scanner`. You must include `import java.util.Scanner;` or use the fully qualified name `java.util.Scanner sc = ...`.

---

### 6.5 Format Specifier Type Incompatibility: `%d` with Floating-Point Literal
```java
package module08.examples;

public class WrongPrintfTypeSpecifier {
    public static void main(String[] args) {
        // Note: The OpenJDK compiler does not check printf format strings at compile time!
        // This compiles cleanly, but crashes AT RUNTIME with IllegalFormatConversionException:
        System.out.printf("%d", 3.14);
    }
}
```
- **Compiler Status:** Compiles cleanly! (Unless an external static analyzer like IntelliJ IDEA or ErrorProne is active).
- **Runtime Error:** `java.util.IllegalFormatConversionException: d != java.lang.Double`
- **Cause:** Java's standard compiler (`javac`) treats format strings as regular `String` parameters; it does not parse format syntax during compilation. IntelliJ IDEA will flag this as a warning: *"Format specifier '%d' is not applicable to argument '3.14'"*.

---

### 6.6 Variable Scope Leakage Across Closed Resources
```java
package module08.examples;

import java.util.Scanner;

public class WrongResourceScope {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner("42")) {
            int token = sc.nextInt();
        }
        // ERROR: cannot find symbol variable sc
        // sc.nextLine();
    }
}
```
- **Error:** `error: cannot find symbol variable sc`
- **Cause:** Resources declared in the try-with-resources header have their lexical scope strictly confined to the try block (JLS §14.20.3). Access outside the block is a compile-time symbol resolution error.

---

### 6.7 Direct Instantiation of `java.io.Console`
```java
package module08.examples;

import java.io.Console;

public class WrongConsoleConstructor {
    public static void main(String[] args) {
        // ERROR: Console() has private access in java.io.Console
        // Console console = new Console();
    }
}
```
- **Error:** `error: Console() has private access in java.io.Console`
- **Cause:** `java.io.Console` employs the Singleton pattern and is instantiated exclusively by the JVM runtime during boot. Application code must obtain the instance via `System.console()`.

---

### 6.8 Missing Resource Closure: Compilation Warnings with `-Xlint:all`
```java
package module08.examples;

import java.io.FileReader;
import java.io.IOException;

public class WrongUnclosedResource {
    public static void main(String[] args) throws IOException {
        // COMPILE WARNING with -Xlint:all: [try] resource FileReader is never closed
        FileReader reader = new FileReader("config.txt");
        int ch = reader.read();
    }
}
```
- **Compiler Warning:** `warning: [try] resource 'reader' is never closed`
- **Cause:** `FileReader` implements `AutoCloseable`. Failing to manage it via try-with-resources risks OS file descriptor exhaustion under high load.

---

## 7. WRONG EXAMPLES — RUNTIME ERRORS

### 7.1 Parsing Non-Numeric Input: `InputMismatchException`
```java
package module08.examples;

import java.util.Scanner;

public class RuntimeInputMismatch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner("abc");
        // Throws java.util.InputMismatchException
        int value = scanner.nextInt();
    }
}
```
- **Exception:** `java.util.InputMismatchException`
- **Stack Trace:**
  ```text
  Exception in thread "main" java.util.InputMismatchException
      at java.base/java.util.Scanner.throwFor(Scanner.java:947)
      at java.base/java.util.Scanner.next(Scanner.java:1602)
      at java.base/java.util.Scanner.nextInt(Scanner.java:2267)
  ```
- **Remedy:** Always verify with `scanner.hasNextInt()` before invoking `scanner.nextInt()`.

---

### 7.2 Parsing Empty String with `Integer.parseInt()`: `NumberFormatException`
```java
package module08.examples;

public class RuntimeEmptyParseInt {
    public static void main(String[] args) {
        String userInput = "";
        // Throws java.lang.NumberFormatException: For input string: ""
        int parsed = Integer.parseInt(userInput);
    }
}
```
- **Exception:** `java.lang.NumberFormatException: For input string: ""`
- **Cause:** `Integer.parseInt()` requires at least one valid ASCII digit (or leading +/- sign). Passing an empty string or whitespace triggers immediate parse failure.
- **Remedy:** Sanitize inputs with `.trim()` and check `!input.isEmpty()` before parsing.

---

### 7.3 Unchecked `System.console()` Invocation in IDE: `NullPointerException`
```java
package module08.examples;

public class RuntimeConsoleNPE {
    public static void main(String[] args) {
        // Inside IntelliJ IDEA or Eclipse: System.console() returns null!
        // Throws java.lang.NullPointerException
        String user = System.console().readLine("Username: ");
    }
}
```
- **Exception:** `java.lang.NullPointerException: Cannot invoke "java.io.Console.readLine(String, Object[])" because the return value of "java.lang.System.console()" is null`
- **Remedy:** Always guard with `if (System.console() != null)`.

---

### 7.4 Mismatched Printf Arguments: `IllegalFormatConversionException`
```java
package module08.examples;

public class RuntimePrintfConversion {
    public static void main(String[] args) {
        // Throws java.util.IllegalFormatConversionException: d != java.lang.String
        System.out.printf("Count: %d%n", "forty-two");
    }
}
```
- **Exception:** `java.util.IllegalFormatConversionException: d != java.lang.String`
- **Cause:** Specifier `%d` mandates an integral type (`byte`, `short`, `int`, `long`, `BigInteger`). The runtime Formatter checks `java.lang.Class` assignments dynamically and throws if mismatched.

---

### 7.5 Invoking Operations on a Closed Scanner: `IllegalStateException`
```java
package module08.examples;

import java.util.Scanner;

public class RuntimeClosedScanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner("alpha beta");
        scanner.close();
        // Throws java.lang.IllegalStateException: Scanner closed
        scanner.next();
    }
}
```
- **Exception:** `java.lang.IllegalStateException: Scanner closed`
- **Cause:** `Scanner.close()` sets an internal boolean flag `closed = true`. All subsequent read operations (`next()`, `nextInt()`, `hasNext()`) check this state and reject invocations.

---

## 8. EDGE CASES

### 8.1 `scanner.nextLine()` on Empty and Consecutive Newlines
When a stream contains consecutive newline characters (`\n\n`), `scanner.nextLine()` returns empty strings (`""`) with length 0.

#### Code Snippet
```java
Scanner scanner = new Scanner("\n\nHello\n");
System.out.println("Line 1: [" + scanner.nextLine() + "]"); // Line 1: []
System.out.println("Line 2: [" + scanner.nextLine() + "]"); // Line 2: []
System.out.println("Line 3: [" + scanner.nextLine() + "]"); // Line 3: [Hello]
```

---

### 8.2 `System.out.print()` Without Flush in Pipe Buffering
When standard output is redirected to a pipeline or file (`java App | grep ...`), the operating system switches libc from line-buffered to fully-buffered mode (typically 4096 or 8192 bytes). `System.out.print("Waiting...")` will not appear on the downstream consumer until the buffer fills or `System.out.flush()` is explicitly invoked.

---

### 8.3 Reading Multilingual UTF-8 and Emojis via Scanner
Characters outside the Basic Multilingual Plane (BMP), such as emojis (e.g. `\uD83D\uDE80` 🚀), require surrogate pairs (two 16-bit `char` elements in UTF-16). `Scanner` tokenizes code points correctly when decoding from UTF-8 streams.

#### Code Snippet
```java
String emojiText = "Rocket 🚀 Status: Orbit";
try (Scanner sc = new Scanner(emojiText)) {
    System.out.println(sc.next()); // Rocket
    System.out.println(sc.next()); // 🚀
}
```

---

### 8.4 Escaping the Literal Percent Sign in Format Strings
To print a literal `%` character in `printf` or `String.formatted()`, you must escape it with double percent signs: `%%`.

#### Code Snippet
```java
int percentage = 99;
// Prints: Progress: 99% complete
System.out.printf("Progress: %d%% complete%n", percentage);
```

---

### 8.5 Passing `null` to `println((Object) null)` vs `println((char[]) null)`
A lethal overload resolution trap:
```java
Object objNull = null;
char[] arrNull = null;

System.out.println(objNull);  // Prints literal string: "null"
System.out.println(arrNull);  // THROWS NullPointerException at runtime!
```
- `println(Object)` converts `null` to `"null"`.
- `println(char[])` attempts to iterate over the array elements directly: `write(buf, 0, buf.length)`. Dereferencing `buf.length` throws `NullPointerException`!

---

### 8.6 `Scanner(System.in)` Never Reaches EOF Without Terminal Signal (Ctrl+D/Ctrl+Z)
`System.in` is backed by the operating system standard input stream. Calling `scanner.hasNext()` or `scanner.hasNextLine()` on `System.in` blocks indefinitely waiting for more keystrokes. It only returns `false` (EOF) when the user inputs an OS EOF character:
- **UNIX / macOS:** `Ctrl + D`
- **Windows:** `Ctrl + Z` followed by `Enter`

---

## IntelliJ IDEA & Compiler Pro-Tips

1. **Terminal Emulation for `System.console()`:**
   - In IntelliJ IDEA, select **Run** -> **Edit Configurations...**.
   - Click **Modify options** -> Check **Emulate terminal in output console**.
   - Now `System.console()` will return a valid `Console` instance rather than `null`.
2. **Scanner Trap Inspection:**
   - IntelliJ IDEA detects unread Scanner tokens when using inspection *"Result of 'Scanner.nextLine()' is ignored"*.
3. **Format String Parameter Validation:**
   - IntelliJ automatically validates format string specifiers against argument types in `printf`, `String.format`, and `String.formatted`, highlighting type mismatches in yellow or red before compilation.
