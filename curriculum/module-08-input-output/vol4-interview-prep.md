# Module 8 Volume 4: Input and Output – Interview Preparation

*Authored by a Senior Java Engineer, JVM Engineer, and OpenJDK Compiler Engineer (25+ Years of Experience)*

Welcome to Volume 4 of the Java Mastery Curriculum for Module 8: Input and Output. This volume is an exhaustive, production-grade interview preparation guide tailored for senior, staff, and principal engineering candidates, as well as ambitious developers aiming to understand Java's I/O subsystems at the deepest possible level.

In top-tier technical interviews (FAANG, high-frequency trading firms, cloud infrastructure teams, and systems engineering groups), I/O questions are never merely about calling `System.out.println` or instantiating a `Scanner`. Interviewers use I/O to evaluate your mastery of low-level systems programming: operating system file descriptors, kernel-to-user space context switching, libc write buffers, JNI boundary crossings, character encoding mechanics, HotSpot monitor contention, thread pinning in Virtual Threads (Project Loom), memory zeroing for credentials, and zero-copy kernel transfers.

Target Platform: **Java 21 LTS**, **IntelliJ IDEA**.

---

## 1. BASIC QUESTIONS (10)

### Q1: How do you read user input in Java?
**Answer:**
Java provides three primary mechanisms for reading user input from the console:
1. **`java.util.Scanner` (Java 5+):** High-level, token-based lexical scanner. It parses primitive types and strings using regular expressions over `System.in`. Ideal for simple interactive CLI programs, quizzes, and classroom exercises.
2. **`java.io.BufferedReader` wrapping `java.io.InputStreamReader` (Java 1.1+):** High-throughput, line-oriented character stream. It buffers input from `System.in` in an 8192-character array. Ideal for high-volume input or processing large piped text streams.
3. **`java.io.Console` (Java 6+):** Dedicated terminal interface (`System.console()`). Provides secure password reading (`readPassword()`) with terminal echo suppression and direct line reading without creating wrapper objects. It returns `null` if the JVM is not attached to an interactive terminal (e.g., inside IDEs).

```java
// 1. Scanner approach
Scanner scanner = new Scanner(System.in);
String token = scanner.next();

// 2. BufferedReader approach (StandardCharsets explicitly specified)
BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
String line = reader.readLine();

// 3. Console approach (Requires interactive terminal)
Console console = System.console();
if (console != null) {
    char[] password = console.readPassword("Password: ");
}
```

---

### Q2: What is `Scanner` and how do you use it?
**Answer:**
`java.util.Scanner` is a simple text scanner which can parse primitive types and strings using regular expressions (JLS & Java SE API). It breaks its input into tokens using a delimiter pattern, which by default matches whitespace (as determined by `Character.isWhitespace()`).

**Usage Pattern:**
1. Instantiate `Scanner` with an underlying source: `InputStream`, `File`, `Path`, `ReadableByteChannel`, or `String`.
2. Inspect token availability using lookahead methods: `hasNext()`, `hasNextInt()`, `hasNextLine()`.
3. Parse and consume tokens using typed accessors: `next()`, `nextInt()`, `nextDouble()`, `nextLine()`.
4. Close the scanner when reading from files or network channels (never close when reading from `System.in` in shared library code).

```java
try (Scanner sc = new Scanner(Path.of("data.txt"), StandardCharsets.UTF_8)) {
    while (sc.hasNext()) {
        if (sc.hasNextInt()) {
            int value = sc.nextInt();
        } else {
            String str = sc.next();
        }
    }
}
```

---

### Q3: What is the difference between `print()` and `println()`?
**Answer:**
Both `print()` and `println()` are methods belonging to `java.io.PrintStream` (e.g., `System.out`) and `java.io.PrintWriter`.

| Feature | `print(T x)` | `println(T x)` |
| :--- | :--- | :--- |
| **Line Terminator** | Does not append a newline. Subsequent writes continue on the same line. | Appends the platform-specific line separator (`System.lineSeparator()`). |
| **Parameter Overloads** | Overloaded for all primitives, `char[]`, `String`, and `Object`. No zero-argument overload. | Overloaded for all primitives, `char[]`, `String`, `Object`, plus a parameterless `println()`. |
| **Buffer Flushing** | If auto-flush is enabled, `print()` does not automatically flush (unless writing a byte newline in `PrintStream`). | If auto-flush is enabled, `println()` unconditionally flushes the output buffer. |

At the implementation level in OpenJDK's `PrintStream`:
```java
public void println(String x) {
    synchronized (this) {
        print(x);
        newLine(); // Calls internal BufferedWriter.newLine() and condFlush()
    }
}
```

---

### Q4: What is `System.in`, `System.out`, and `System.err`?
**Answer:**
In `java.lang.System`, three predefined, globally accessible standard stream fields map directly to the operating system's standard POSIX file descriptors:
* **`System.in` (`InputStream`):** Maps to **File Descriptor 0 (`stdin`)**. It is an unbuffered byte input stream used for reading data passed to the process via keyboard input or pipe redirection.
* **`System.out` (`PrintStream`):** Maps to **File Descriptor 1 (`stdout`)**. Used for standard process output. In standard terminal sessions, `System.out` is line-buffered or auto-flushing.
* **`System.err` (`PrintStream`):** Maps to **File Descriptor 2 (`stderr`)**. Used for diagnostic error messages, warnings, and stack traces. Unlike `System.out`, `System.err` is typically **unbuffered** or aggressively flushed so that crash diagnostics appear immediately even if the JVM process crashes in the next instruction.

These fields are declared `public static final`, but they can be redirected programmatically via the native methods `System.setIn()`, `System.setOut()`, and `System.setErr()`.

---

### Q5: How do you format output with `printf`?
**Answer:**
`printf` (and its sister method `format()`) was introduced in Java 5 to provide C-style formatted output string generation via `java.util.Formatter`.

```java
System.out.printf(Locale.US, "User: %-15s | Balance: $%,10.2f | Status: %b%n", name, balance, active);
```
**Key Components:**
1. **Format String:** Contains plain text interspersed with format specifiers starting with `%`.
2. **Arguments:** Varargs list (`Object... args`) that are evaluated against the format specifiers.
3. **Locale (Optional):** Controls numeric grouping separators, currency formatting, and date representations.

---

### Q6: What is `BufferedReader` and when should you use it?
**Answer:**
`java.io.BufferedReader` is a character stream class that buffers input from an underlying `Reader` (such as `FileReader` or `InputStreamReader`) into an in-memory character array (default size: `8192` chars).

**When to Use It:**
* When reading text line-by-line using `readLine()`.
* When reading high volumes of text from disk or network sockets where single-character reads would degrade performance by triggering millions of kernel system calls.
* In high-throughput backend services and parsing pipelines where `Scanner`'s regex engine is too slow and consumes too much heap memory.

---

### Q7: What is the Scanner newline trap?
**Answer:**
The **Scanner newline trap** occurs when a call to `nextInt()`, `nextDouble()`, or `next()` is followed immediately by a call to `nextLine()`.

**The Mechanism:**
`nextInt()` reads only the numeric characters from the input stream. When the user types `42` and presses `Enter`, the stream contains `'4'`, `'2'`, `'\n'`. `nextInt()` consumes `'4'` and `'2'`, leaving the `'\n'` sitting in the buffer. When `nextLine()` is subsequently invoked, it scans until it encounters the next newline character. Finding the dangling `'\n'` immediately, it consumes it and returns an empty string `""` without waiting for the user to type anything!

**The Remedy:**
Insert a dummy `scanner.nextLine();` to consume the dangling newline character before reading text:
```java
int id = scanner.nextInt();
scanner.nextLine(); // Discard dangling newline
String description = scanner.nextLine(); // Now reads correctly
```

---

### Q8: What is `Console` and when is it `null`?
**Answer:**
`java.io.Console` (Java 6+) is a dedicated interface for interacting with the character-based terminal console associated with the current Java Virtual Machine.

`System.console()` returns `null` whenever:
1. The JVM is executed inside an IDE (IntelliJ IDEA, Eclipse, VS Code) whose standard execution console redirects standard I/O through pipes rather than allocating a real pseudo-terminal (pty).
2. The process is executed in the background as a service, daemon, or cron job.
3. The process's standard input or output has been redirected via shell redirection: `java MyApp < input.txt` or `java MyApp | tee output.log`.

**Best Practice:** Always perform a null check before calling methods on `System.console()` and provide a fallback to `Scanner(System.in)` or `BufferedReader`.

---

### Q9: What are format specifiers? List the common ones.
**Answer:**
A format specifier defines how an argument should be formatted and converted into text within a format string. The general syntax is:
`%[argument_index$][flags][width][.precision]conversion`

**Common Conversion Characters:**
* `%d`: Decimal integer (`byte`, `short`, `int`, `long`, `BigInteger`).
* `%s`: String representation (invokes `String.valueOf(arg)`).
* `%f`: Floating-point decimal number (`float`, `double`, `BigDecimal`).
* `%c`: Unicode character (`char`, `Character`).
* `%b`: Boolean (`true` or `false`). If argument is null, prints `"false"`.
* `%x` / `%X`: Hexadecimal integer (lowercase/uppercase).
* `%e` / `%E`: Scientific notation exponential format.
* `%t` / `%T`: Date/time prefix (e.g., `%tF` for ISO date `YYYY-MM-DD`).
* `%n`: Platform-specific line separator (emits `System.lineSeparator()`).
* `%%`: Literal percent sign escape.

**Common Flags:**
* `-`: Left-justify within the specified width (default is right-justify).
* `+`: Always include a plus sign for positive numbers.
* `0`: Zero-pad numeric fields up to the specified width.
* `,`: Include locale-specific grouping separators (e.g., `1,000,000`).
* `(`: Enclose negative numbers in parentheses: `(123.45)`.

---

### Q10: What is try-with-resources? Why use it with I/O?
**Answer:**
Introduced in Java 7 (JLS §14.20.3), the **try-with-resources statement** is a language construct that automates the deterministic closing of resources that implement `java.lang.AutoCloseable` or `java.io.Closeable`.

**Why It Is Essential for I/O:**
1. **Prevents Resource Leaks:** OS file descriptors and socket handles are finite kernel resources. Forgetting to close them leads to descriptor exhaustion (`Too many open files`).
2. **Exception Suppression (`Throwable.getSuppressed()`):** In a legacy manual `try-finally` block, if both the try block and the finally block throw an exception, the finally exception silently masks and destroys the primary business exception. Try-with-resources preserves the primary exception and attaches the close failure as a suppressed exception.
3. **Lexical Scope Containment:** The resource variable is strictly scoped to the try block, preventing accidental reads or writes to closed streams.

---

## 2. INTERMEDIATE QUESTIONS (10)

### Q1: `Scanner` vs `BufferedReader`: When to use which?
**Answer:**

```
+--------------------------------------------------------------------------+
|                      SCANNER VS BUFFEREDREADER                           |
|                                                                          |
|  Feature             Scanner                      BufferedReader         |
|  ----------------------------------------------------------------------  |
|  Primary Purpose     Lexical token parsing        Raw character buffer   |
|  Parsing Engine      Regex (Pattern/Matcher)      None (Direct array)    |
|  Buffer Size         1024 chars                   8192 chars             |
|  Token Parsing       Direct (nextInt, nextDouble) Manual (Integer.parse) |
|  Speed               5x - 25x Slower              Extremely Fast         |
|  Thread Safety       Not thread-safe              Not thread-safe        |
|  Memory Footprint    High (Regex state + objects) Very Low (Flat array)  |
|  EOF Handling        hasNext() returns false      readLine() returns null|
+--------------------------------------------------------------------------+
```

* **Use `Scanner` when:** You are writing an interactive command-line application where you need to parse mixed tokens (e.g., an integer followed by a word) directly from the user keyboard, or parsing small whitespace-separated in-memory strings.
* **Use `BufferedReader` when:** You are reading large files (> 1MB), processing lines in backend batch jobs, reading from network sockets, or competitive programming where I/O throughput dominates execution time.

---

### Q2: How does buffering improve I/O performance?
**Answer:**
Buffering dramatically improves performance by **amortizing the overhead of operating system system calls (syscalls)**.

**The Architectural Breakdown:**
1. **User/Kernel Context Switch Cost:** A native `read()` or `write()` syscall forces the CPU to switch from User Mode (Ring 3) to Kernel Mode (Ring 0). This involves saving CPU register state, validating permissions, invalidating instruction pipelines, and updating the Translation Lookaside Buffer (TLB).
2. **Without Buffering:** Reading 100,000 characters one by one via `FileReader.read()` triggers 100,000 JNI calls and 100,000 OS syscalls.
3. **With Buffering:** A `BufferedReader` reads a block of 8,192 characters in a single syscall into a contiguous Java heap array. The next 8,191 character requests are served directly from RAM using simple array index increments (`cb[nextChar++]`), completely bypassing the OS kernel. This reduces kernel boundary crossings by over 99.98%.

---

### Q3: What is `InputStreamReader` and why is it needed?
**Answer:**
`java.io.InputStreamReader` is a bridge from byte streams to character streams: it reads bytes from an underlying `InputStream` and decodes them into characters using a specified `Charset`.

```
[Underlying Storage: Disk/Socket]
               |
          (Raw Bytes)
               v
     [java.io.InputStream]  (e.g., FileInputStream, SocketInputStream)
               |
          (Raw Bytes: e.g., 0xE2 0x82 0xAC)
               v
  [java.io.InputStreamReader] <--- (java.nio.charset.Charset: UTF-8)
               |
    (Decoded Unicode Chars: '\u20AC' -> '€')
               v
    [java.io.BufferedReader]
```

**Why It Is Needed:**
Computers store and transmit raw binary data (bytes: 8-bit values `0x00`–`0xFF`). Humans read characters (Unicode code points). A single character in UTF-8 can span anywhere from 1 to 4 bytes. An `InputStream` knows nothing about characters, code points, or encodings—it only sees bytes. `InputStreamReader` encapsulates a stateful `CharsetDecoder` that handles multi-byte assembly, surrogate pairs, and invalid byte sequences.

---

### Q4: Explain character encoding in Java I/O.
**Answer:**
Character encoding is the algorithmic mapping between character code points in the Unicode character set and their binary byte representation for storage or network transit.

1. **Internal JVM Representation:**
   * Inside the JVM memory, Java `char` is a 16-bit UTF-16 code unit.
   * `java.lang.String` (since Java 9 Compact Strings) is internally backed by a `byte[]` array accompanied by a `coder` flag indicating either `LATIN1` (1 byte per char) or `UTF16` (2 bytes per char).
2. **I/O Serialization Boundary:**
   * When text is written to an external destination (`OutputStream`), characters must be **encoded** into bytes.
   * When bytes are read from an external source (`InputStream`), bytes must be **decoded** into characters.
3. **Variable-Length UTF-8:**
   * ASCII characters (`U+0000` to `U+007F`): 1 byte.
   * Latin, Greek, Arabic, Hebrew (`U+0080` to `U+07FF`): 2 bytes.
   * Asian scripts, common symbols (`U+0800` to `U+FFFF`): 3 bytes.
   * Emojis, historic scripts (`U+10000` to `U+10FFFF`): 4 bytes.
4. **Encoding Failure:** If an application decodes UTF-8 bytes using `ISO-8859-1` or `Windows-1252`, multi-byte sequences are broken into separate corrupted characters (Mojibake). Always specify `StandardCharsets.UTF_8`.

---

### Q5: How do you redirect `System.out` for testing?
**Answer:**
To verify console output in unit tests without polluting terminal logs, redirect `System.out` using `System.setOut(PrintStream)`.

```java
public class CliTest {
    @Test
    void testCliOutput() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream capturedBytes = new ByteArrayOutputStream();
        
        try (PrintStream testOut = new PrintStream(capturedBytes, true, StandardCharsets.UTF_8)) {
            System.setOut(testOut);
            
            // Invoke the business logic that prints to System.out
            new ReportGenerator().printWelcomeMessage();
            
            String output = capturedBytes.toString(StandardCharsets.UTF_8);
            assertTrue(output.contains("Welcome to Enterprise App"));
        } finally {
            // CRITICAL: Always restore original System.out to prevent test suite pollution
            System.setOut(originalOut);
        }
    }
}
```

---

### Q6: What is the difference between `PrintWriter` and `PrintStream`?
**Answer:**

| Architectural Aspect | `java.io.PrintStream` | `java.io.PrintWriter` |
| :--- | :--- | :--- |
| **Stream Category** | Byte Stream (subclasses `FilterOutputStream`) | Character Stream (subclasses `Writer`) |
| **Target Output** | Designed for writing raw bytes or character text to byte streams | Designed strictly for writing formatted character text to writers |
| **Encoding Support** | Encodes characters to bytes using platform default or supplied Charset | Writes directly to underlying `Writer` without repeated byte conversion |
| **Auto-Flush Behavior** | Flushes automatically when a byte array is written, or when `\n` is encountered (if autoFlush=true) | Flushes **only** on explicit calls to `println()`, `printf()`, or `format()` (never on `print("\n")`) |
| **Exception Handling** | Never throws `IOException`; traps errors internally (`checkError()`) | Never throws `IOException`; traps errors internally (`checkError()`) |

---

### Q7: How does `printf` handle argument indices?
**Answer:**
Java format strings support two indexing mechanisms to control argument mapping:
1. **Explicit Indexing (`n$`):** Specifies exactly which argument from the argument list should be placed at this position (1-based index).
2. **Relative Indexing (`<`):** Reuses the argument that was referenced by the immediately preceding format specifier.

```java
// 1. Explicit indexing: %2$s references 2nd arg ("World"), %1$s references 1st arg ("Hello")
System.out.printf("%2$s, %1$s!%n", "Hello", "World");
// Output: World, Hello!

// 2. Relative indexing: '<' flag reuses the preceding argument
int number = 255;
System.out.printf("Decimal: %d | Hex: %<X | Octal: %<o%n", number);
// Output: Decimal: 255 | Hex: FF | Octal: 377
```

---

### Q8: What modern I/O methods were added in Java 9–11?
**Answer:**
Modern Java introduced high-productivity, boilerplate-eliminating methods across core I/O classes:
* **Java 9:**
  * `InputStream.transferTo(OutputStream out)`: Transfers all remaining bytes directly from this input stream to the given output stream using an internal 8KB buffer.
  * `InputStream.readAllBytes()`: Reads all remaining bytes into a single `byte[]`.
  * `InputStream.readNBytes(byte[] b, int off, int len)`: Reads the exact requested number of bytes, blocking until available or EOF.
  * `Reader.transferTo(Writer out)`: Character-stream equivalent of `transferTo`.
  * `Scanner.tokens()`: Returns a `Stream<String>` of delimiter-separated tokens.
* **Java 11:**
  * `Files.readString(Path path, Charset cs)`: Reads an entire file into a `String`.
  * `Files.writeString(Path path, CharSequence csq, Charset cs, OpenOption... options)`: Writes a string directly to a file.
  * `FileReader(File, Charset)` and `FileWriter(File, Charset)`: Finally added constructors accepting a `Charset` directly, deprecating the error-prone platform-default constructors!

---

### Q9: How do you read passwords securely in Java?
**Answer:**
Passwords must **never** be read into a `java.lang.String`. Use `java.io.Console.readPassword()`:

```java
Console console = System.console();
if (console == null) {
    throw new IllegalStateException("Interactive console unavailable");
}

char[] password = console.readPassword("Enter Master Secret: ");
try {
    authenticate(password);
} finally {
    // CRITICAL SECURITY REQUIREMENT:
    // Overwrite the character array with zeros immediately after use!
    Arrays.fill(password, '\0');
}
```

**Why This Is Secure:**
1. **Echo Suppression:** The operating system terminal driver suppresses character echoing, preventing shoulder-surfing and terminal scrollback logging.
2. **Heap Sanitization:** Strings cannot be wiped because they are immutable. A `char[]` can be mutated immediately via `Arrays.fill()`, ensuring that plain-text passwords cannot be extracted from JVM memory dumps or swap files.

---

### Q10: What happens when you close `System.in`?
**Answer:**
When `System.in.close()` is called (either directly or indirectly via wrapping classes like `new Scanner(System.in).close()`), the underlying native OS file descriptor 0 (`stdin`) is closed.

**Consequences:**
1. **Irreversibility:** Once closed, file descriptor 0 **cannot be reopened** within the running JVM process.
2. **Subsequent Reads Fail:** Any subsequent read attempt by any thread on `System.in` or any wrapper stream will immediately throw `java.io.IOException: Stream closed` or `java.util.NoSuchElementException`.
3. **Application-Wide Destruction:** Because `System.in` is a shared global singleton, closing it in one isolated component breaks all other console-reading components across the entire application.

---

## 3. ADVANCED QUESTIONS (10)

### Q1: Explain the Java I/O class hierarchy (Streams, Readers, Bridges).
**Answer:**

```
+-------------------------------------------------------------------------+
|                        JAVA I/O CLASS HIERARCHY                         |
|                                                                         |
|  [BYTE STREAMS: 8-bit raw binary data]                                  |
|   - InputStream                                                         |
|      ├── FileInputStream (File source)                                  |
|      ├── ByteArrayInputStream (Memory source)                           |
|      └── FilterInputStream                                              |
|            ├── BufferedInputStream (8KB userspace cache)                |
|            └── DataInputStream (Binary primitives: readInt, readLong)   |
|   - OutputStream                                                        |
|      ├── FileOutputStream                                               |
|      ├── ByteArrayOutputStream                                          |
|      └── FilterOutputStream                                             |
|            ├── BufferedOutputStream                                     |
|            └── PrintStream (System.out, autoflush, printf)              |
|                                                                         |
|  [CHARACTER STREAMS: 16-bit Unicode characters]                         |
|   - Reader                                                              |
|      ├── CharArrayReader, StringReader (Memory)                         |
|      ├── BufferedReader (8192 char cache, readLine())                   |
|      └── InputStreamReader (BRIDGE: Byte Stream -> Character Stream)    |
|            └── FileReader (Convenience subclass)                        |
|   - Writer                                                              |
|      ├── CharArrayWriter, StringWriter                                  |
|      ├── BufferedWriter (8192 char cache, newLine())                    |
|      ├── PrintWriter (printf, checkError, formatted output)             |
|      └── OutputStreamWriter (BRIDGE: Character Stream -> Byte Stream)   |
|            └── FileWriter (Convenience subclass)                        |
+-------------------------------------------------------------------------+
```

* **Byte Streams (`InputStream`/`OutputStream`):** Handle raw 8-bit bytes. Subclasses handle specific physical media (files, memory, sockets).
* **Character Streams (`Reader`/`Writer`):** Handle 16-bit Unicode characters. They encapsulate character decoding/encoding.
* **Bridge Classes:** `InputStreamReader` and `OutputStreamWriter` bridge the two worlds. They hold a `CharsetDecoder` or `CharsetEncoder` to translate byte arrays into character arrays and vice versa.

---

### Q2: How does `System.out.println()` work at the bytecode and native level?
**Answer:**
Tracing `System.out.println("Hello")`:

```
[Java Code: System.out.println("Hello")]
    │
    ▼ (Bytecode: getstatic #Field System.out:LPrintStream; ldc #String "Hello"; invokevirtual #println)
[PrintStream.println(String)]
    │  (Synchronizes on 'this' monitor lock)
    ▼
[BufferedWriter / OutputStreamWriter] (Encodes UTF-16 String into byte[] via CharsetEncoder)
    │
    ▼
[FileOutputStream(FileDescriptor.out)] (Writes raw byte array)
    │
    ▼ (JNI Boundary Crossing)
[Native Method: Java_java_io_FileOutputStream_writeBytes] (HotSpot C++ implementation)
    │
    ▼
[C Standard Library: write() syscall] (POSIX write(1, buf, len) or Windows WriteFile)
    │
    ▼ (Kernel Space Ring 0)
[OS Page Cache / TTY Line Discipline Driver] (Terminal scrolls text onto screen)
```

1. **Bytecode:** The compiler emits `getstatic System.out` followed by `invokevirtual PrintStream.println(Ljava/lang/String;)V`.
2. **Synchronization:** Inside `PrintStream`, the thread acquires the intrinsic lock of the `System.out` instance (`synchronized (this)`).
3. **Character-to-Byte Encoding:** The characters are passed to an internal `OutputStreamWriter`, which invokes the configured `CharsetEncoder` to emit bytes into an internal buffer.
4. **JNI Invocation:** When the buffer flushes, `FileOutputStream.writeBytes` executes a native C call.
5. **Kernel Syscall:** HotSpot invokes the POSIX `write()` system call against file descriptor 1. The OS kernel's terminal driver buffers the line and dispatches drawing signals to the display hardware.

---

### Q3: What is the Decorator pattern in Java I/O?
**Answer:**
The **Decorator pattern** (GoF) dynamically attaches additional responsibilities to an object without modifying its source code. Java's `java.io` package is one of the software industry's classic implementations of the Decorator pattern.

```java
// Combining decorators to create a buffered, decompressed, data-reading stream
InputStream stream = 
    new DataInputStream(                  // Decorator 3: Reads binary primitives
        new BufferedInputStream(          // Decorator 2: Buffers in 8KB RAM array
            new GZIPInputStream(          // Decorator 1: Decompresses gzip on the fly
                new FileInputStream("data.gz") // Component: Physical file byte source
            )
        )
    );
```
* **Base Component:** `InputStream` defines the abstract contract (`read()`).
* **Concrete Component:** `FileInputStream` implements reading bytes from an OS file.
* **Decorator Root:** `FilterInputStream` extends `InputStream` and holds a `protected volatile InputStream in` reference.
* **Concrete Decorators:** `BufferedInputStream`, `GZIPInputStream`, `DataInputStream` override `read()` to inject buffering, decompression, or primitive decoding while delegating raw reads to the wrapped stream.

---

### Q4: How does `Scanner` use regex internally?
**Answer:**
`Scanner` converts token-based parsing into regular expression matching:
1. **Delimiter Pattern:** By default, `Scanner` initializes a `Pattern` matching whitespace: `\\p{javaWhitespace}+`.
2. **Sliding `CharBuffer`:** It reads characters from the underlying source into a 1024-char `CharBuffer`.
3. **Internal `Matcher`:** It maintains a `java.util.regex.Matcher` instance bounded to the `CharBuffer`.
4. **Tokenization Loop:** When `next()` is called:
   * The matcher searches for the delimiter pattern to locate token boundaries.
   * If a delimiter is found, the substring prior to the delimiter is extracted as the token.
   * If the buffer runs out of characters before finding a delimiter, the scanner shifts unconsumed characters to the beginning of the buffer and reads another chunk from the source stream.
5. **Typed Parsing:** When `nextInt()` is called, `Scanner` matches the token against a cached locale-specific regex pattern (e.g., handling negative signs and grouping commas) before invoking `Integer.parseInt()`.

---

### Q5: What was JEP 400 (UTF-8 by Default)?
**Answer:**
Delivered in **Java 18**, **JEP 400** standardized the default character set of the Java SE Platform to **UTF-8**.

**Prior to Java 18:**
* The default charset was determined at JVM startup by the host operating system's locale and configuration (e.g., `windows-1252` on Western Windows, `Shift_JIS` on Japanese Windows, `UTF-8` on Linux/macOS).
* Calling `new FileReader(file)` or `"str".getBytes()` produced different binary files depending on which operating system ran the JVM.
* Developers had to pass the VM property `-Dfile.encoding=UTF-8` to enforce predictability.

**After Java 18 (JEP 400):**
* `Charset.defaultCharset()` unconditionally returns `UTF-8` on all operating systems.
* Standard APIs (`FileReader`, `FileWriter`, `InputStreamReader`, `String.getBytes()`) default to UTF-8.
* To revert to legacy behavior for backward compatibility, the JVM flag `-Dfile.encoding=COMPAT` can be supplied.
* **Important Nuance:** Console output (`System.out`/`System.err`) may still use a platform-specific terminal encoding (`stdout.encoding` / `sun.stdout.encoding`) if the attached terminal does not support UTF-8.

---

### Q6: How does auto-flush work in `PrintStream` and `PrintWriter`?
**Answer:**
Auto-flush is a mechanism that automatically flushes the internal output buffer under specific conditions:

1. **`PrintStream`:**
   * Constructor: `new PrintStream(outputStream, autoFlush)`.
   * When `autoFlush` is `true`, the stream automatically flushes whenever:
     * A byte array is written (`write(byte[], int, int)`).
     * One of the `println()` methods is invoked.
     * A newline character (`'\n'`) is written inside a byte sequence.
2. **`PrintWriter`:**
   * Constructor: `new PrintWriter(writer, autoFlush)`.
   * When `autoFlush` is `true`, the writer flushes **only** when one of the following methods is called:
     * `println()` (any overload)
     * `printf()` (any overload)
     * `format()` (any overload)
   * **The Trap:** Calling `writer.print("\n")` or `writer.write("\n")` **does NOT flush** `PrintWriter`, even if `autoFlush` is enabled!

---

### Q7: What is the performance difference between `Scanner`, `BufferedReader`, and Files API?
**Answer:**
When reading a 500 MB text file with 10 million lines:

```
Methodology Benchmark Analysis:
1. Scanner(file):
   - Time: ~14.2 seconds
   - Allocations: ~4.5 GB of temporary objects (Matcher, MatchResult, String substrings)
   - CPU: Heavy branch misprediction in regex engine.

2. BufferedReader.readLine():
   - Time: ~1.1 seconds (13x faster than Scanner)
   - Allocations: ~800 MB (one String object per line)
   - CPU: Minimal. Dominated by array copies and char-to-byte decoding.

3. Files.lines(path):
   - Time: ~1.2 seconds
   - Allocations: ~850 MB (Stream pipeline + String per line)
   - Advantage: Lazy functional stream evaluation, parallel stream capability.

4. FileChannel + Memory Mapped Buffer (NIO):
   - Time: ~0.18 seconds (80x faster than Scanner)
   - Allocations: < 1 MB (Zero-copy kernel paging, scalar byte parsing)
```

---

### Q8: How does `InputStream.transferTo()` work internally?
**Answer:**
Introduced in Java 9, `long transferTo(OutputStream out)` transfers all remaining bytes from an input stream to an output stream until EOF.

**OpenJDK Source Implementation:**
```java
public long transferTo(OutputStream out) throws IOException {
    Objects.requireNonNull(out, "out");
    long transferred = 0;
    byte[] buffer = new byte[DEFAULT_BUFFER_SIZE]; // 8192 bytes
    int read;
    while ((read = this.read(buffer, 0, buffer.length)) >= 0) {
        out.write(buffer, 0, read);
        transferred += read;
    }
    return transferred;
}
```
**Key Architectural Details:**
* Allocates an 8,192-byte (`8KB`) heap transfer buffer.
* Loops synchronously: reads up to 8KB from `this`, then writes that slice directly to `out`.
* Does **not** close either stream upon completion. Both streams remain open for further operations.
* Subclasses can override this method: for example, `ChannelInputStream` can optimize transfers using native zero-copy kernel mechanisms (`FileChannel.transferTo` leveraging Linux `sendfile` system call).

---

### Q9: Explain the security implications of `String` vs `char[]` for passwords.
**Answer:**
Handling credentials in `String` vs `char[]` represents a fundamental security divergence:

```
+--------------------------------------------------------------------------+
|                     STRING VS CHAR[] MEMORY PROFILE                      |
|                                                                          |
|  String secret = "MyPassword";                                           |
|  +--------------------------------------------------------------------+  |
|  | Heap Object: [Header][hash][coder][byte[] -> 'M','y','P'...]       |  |
|  | * Immutable: No public method exists to wipe or zero these bytes.  |  |
|  | * GC Non-Deterministic: Sits in heap RAM for minutes or hours.     |  |
|  | * String Deduplication / Interning may make it permanent!          |  |
|  +--------------------------------------------------------------------+  |
|                                                                          |
|  char[] secret = console.readPassword();                                 |
|  +--------------------------------------------------------------------+  |
|  | Heap Object: [Header]['M', 'y', 'P', 'a', 's', 's'...]             |  |
|  |                                                                    |  |
|  | ==> Arrays.fill(secret, '\0');                                     |  |
|  |                                                                    |  |
|  | Heap Object: [Header]['\0', '\0', '\0', '\0', '\0', '\0'...]       |  |
|  | * Memory scrubbed instantly! Zero plain-text traces in RAM dumps.  |  |
|  +--------------------------------------------------------------------+  |
+--------------------------------------------------------------------------+
```

1. **Immutability:** A `String` cannot be modified. If an attacker initiates a core dump or memory scrape (`jcmd GC.heap_dump`), the password will be extracted in clear text.
2. **Sanitization:** A `char[]` is mutable. Calling `Arrays.fill(secret, '\0')` immediately after authentication clears the sensitive data from physical RAM before the garbage collector ever touches it.

---

### Q10: How does `System.setOut()` redirect output?
**Answer:**
`System.out` is declared in `java.lang.System` as:
```java
public static final PrintStream out = null;
```
Under normal Java language semantics (JLS §17.5), a `public static final` field **cannot be reassigned** by user code; attempting to do so triggers a compilation error.

**How `System.setOut` Bypasses This:**
`System.setOut(PrintStream newOut)` delegates to a private native method:
```java
public static void setOut(PrintStream out) {
    checkIO(); // SecurityManager permission check
    setOut0(out);
}
private static native void setOut0(PrintStream out);
```
At the JVM runtime level, `setOut0` is implemented in C++ inside the HotSpot JVM (`jvm.cpp`). The JVM modifies the memory address of the static field directly in the JVM's Metaspace/Class mirror representation, bypassing the compiler's `final` immutability constraints.

---

## 4. TRICKY / GOTCHA QUESTIONS (10)

### Q1: What happens with `scanner.nextInt()` followed by `scanner.nextLine()`?
**Answer:**
`scanner.nextLine()` immediately returns an empty string `""` without blocking for user input.

**Why:** `nextInt()` parses digits and stops immediately before the delimiter (the `\n` generated by pressing `Enter`). The `\n` remains in the scanner's internal buffer. `nextLine()` scans until the next newline character, finds the leftover `\n` instantly, consumes it, and returns an empty string.

---

### Q2: `System.console()` returns `null` in an IDE — True or False?
**Answer:**
**TRUE.**
When you run a Java program from inside IntelliJ IDEA, Eclipse, or NetBeans, the IDE starts the Java process as a sub-process whose `stdin` and `stdout` are redirected using operating system pipes (`pipe` IPC). Because the process is connected to a pipe rather than a true TTY character device, the JVM's native initialization detects that no terminal is attached and initializes `System.console()` to `null`.

---

### Q3: Is `printf("%d", 3.14)` a compile-time or runtime error?
**Answer:**
It is a **RUNTIME ERROR**.
Format strings are passed as standard `java.lang.String` literals. The Java compiler (`javac`) does not parse or validate format string syntax at compile time. At runtime, `java.util.Formatter` attempts to convert the `Double` argument into a decimal integer, fails, and throws:
`java.util.IllegalFormatConversionException: d != java.lang.Double`.

*(Note: Modern IDEs like IntelliJ IDEA perform static code inspection and will highlight this as an editor warning, but javac will compile it without errors).*

---

### Q4: Can you reopen `System.in` after closing it?
**Answer:**
**NO.**
Closing `System.in` closes the underlying native OS standard input file descriptor (descriptor 0). The JVM provides no mechanism to re-acquire or re-bind file descriptor 0 to the process. Any subsequent read attempts on `System.in` throw `IOException: Stream closed` or `NoSuchElementException`.

---

### Q5: `System.out.println(null)` — Does it compile?
**Answer:**
**It fails to compile with an AMBIGUITY ERROR!**

```java
System.out.println(null); // COMPILATION ERROR!
```
**Why:**
`PrintStream` has multiple overloaded `println()` methods:
* `println(String x)`
* `println(char[] x)`
* `println(Object x)`

Both `String` and `char[]` are reference types that can accept `null`. Because neither `String` nor `char[]` is a subtype of the other, the Java compiler cannot determine which method is the "most specific" (JLS §15.12.2.5). The compiler halts with:
`reference to println is ambiguous, both method println(char[]) and method println(String) match`.

**Fix:** Disambiguate by casting:
`System.out.println((String) null);` (Prints the text `"null"`).
`System.out.println((char[]) null);` (Throws a runtime `NullPointerException`!).

---

### Q6: What does `%n` do vs `\n` in `printf`?
**Answer:**
* `\n`: Hardcoded ASCII Line Feed character (`0x0A`). It always outputs a single LF byte, regardless of the host operating system.
* `%n`: The platform-specific line separator specifier. At runtime, `Formatter` queries `System.lineSeparator()`. It produces:
  * `\r\n` (CRLF, 2 bytes) on Windows.
  * `\n` (LF, 1 byte) on Linux and macOS.

Using `\n` in format strings causes malformed files and display anomalies when text files generated on Unix are read by Windows native utilities.

---

### Q7: Does `Scanner` implement `AutoCloseable`?
**Answer:**
**YES.**
`Scanner` implements `java.util.Iterator<String>`, `java.io.Closeable`, and `java.lang.AutoCloseable`.
When used inside a try-with-resources statement, its `close()` method is automatically called.
**Critical Caveat:** If the `Scanner` was constructed over an underlying stream (such as `new Scanner(System.in)` or `new Scanner(socket.getInputStream())`), closing the `Scanner` **closes the underlying stream as well**!

---

### Q8: What is the default buffer size of `BufferedReader`?
**Answer:**
The default buffer size is **8,192 characters** (`8K chars`).
Because Java characters are 16-bit UTF-16 values, this represents a **16 KB memory buffer** on the JVM heap. You can override this size using the constructor `new BufferedReader(reader, customSize)`.

---

### Q9: Can you use `Scanner` to read from a `String`?
**Answer:**
**YES.**
`Scanner` has a constructor `Scanner(String source)`. It creates an in-memory lexical scanner that tokenizes the provided string without involving any file or stream I/O.
```java
Scanner sc = new Scanner("100 200 300");
int sum = sc.nextInt() + sc.nextInt() + sc.nextInt(); // sum = 600
```

---

### Q10: Is `System.out` thread-safe?
**Answer:**
**YES, but with critical caveats.**
`System.out` is an instance of `PrintStream`. Individual method calls (such as `println()` or `write()`) are thread-safe because they synchronize internally on the `PrintStream` instance (`synchronized (this)`). Two threads calling `System.out.println()` concurrently will not corrupt the underlying byte stream or interleave individual bytes of a single line.

**The Caveat:**
Compound or multi-step print operations are **not atomic**:
```java
// NOT ATOMIC across threads:
System.out.print("User: ");
System.out.println(name);
```
If Thread 1 and Thread 2 execute this code concurrently, the output will interleave:
`User: User: Alice\nBob\n`. To prevent this, callers must manually synchronize on `System.out` or format the line into a single `println()` call.

---

## 5. ASSESSMENT & PRACTICE

---

### 5.1 Multiple Choice Questions (15)

#### MCQ 1
What is the return value of `System.console()` when executing a Java application inside IntelliJ IDEA's default Run tool window?  
A. An active `Console` instance  
B. `null`  
C. Throws `UnsupportedOperationException`  
D. A redirected `PrintStream`  
* **Answer:** **B**  
* **Deep Explanation:** IDE run windows run child processes connected via standard I/O pipes rather than an authentic OS terminal/TTY. In the absence of an interactive TTY, the JVM sets the console reference to `null`.

---

#### MCQ 2
Consider the following code snippet:
```java
Scanner scanner = new Scanner("42\nHello");
int num = scanner.nextInt();
String str = scanner.nextLine();
System.out.println("num=" + num + ", str='" + str + "'");
```
What is printed?  
A. `num=42, str='Hello'`  
B. `num=42, str=''`  
C. `num=42, str='\n'`  
D. Throws `NoSuchElementException`  
* **Answer:** **B**  
* **Deep Explanation:** `nextInt()` consumes only the token `"42"`, leaving the `\n` in the scanner buffer. The subsequent `nextLine()` reads and consumes up to that immediate newline, returning an empty string `""`.

---

#### MCQ 3
Which format specifier is used to emit a platform-independent newline in `printf`?  
A. `\n`  
B. `\r\n`  
C. `%n`  
D. `%l`  
* **Answer:** **C**  
* **Deep Explanation:** `%n` is the only format specifier that dynamically resolves the host platform's line separator (`System.lineSeparator()`). `\n` is hardcoded to LF.

---

#### MCQ 4
What happens if you run the following code?
```java
System.out.printf("%d", 10.5);
```
A. Prints `10` (truncates decimal)  
B. Prints `11` (rounds up)  
C. Compilation Error  
D. Throws `java.util.IllegalFormatConversionException` at runtime  
* **Answer:** **D**  
* **Deep Explanation:** `printf` format strings are evaluated at runtime. Passing a floating-point argument (`10.5`) to an integer specifier (`%d`) causes `IllegalFormatConversionException`.

---

#### MCQ 5
Which class serves as the bridge from byte streams to character streams?  
A. `FilterInputStream`  
B. `BufferedInputStream`  
C. `InputStreamReader`  
D. `OutputStreamWriter`  
* **Answer:** **C**  
* **Deep Explanation:** `InputStreamReader` reads bytes from an `InputStream` and decodes them into characters using a specified `Charset`.

---

#### MCQ 6
How does `BufferedReader.readLine()` signal that the end of the stream (EOF) has been reached?  
A. Throws `EOFException`  
B. Returns `""` (empty string)  
C. Returns `null`  
D. Returns `"-1"`  
* **Answer:** **C**  
* **Deep Explanation:** `readLine()` returns `null` when the end of the stream has been reached. In contrast, `read()` returns `-1`.

---

#### MCQ 7
What is the consequence of closing a `Scanner` created as `new Scanner(System.in)`?  
A. Only the scanner object is closed; `System.in` remains unaffected.  
B. `System.in` is closed and cannot be read from again for the lifetime of the JVM.  
C. `System.in` is flushed and reset to byte offset 0.  
D. Throws `UnsupportedOperationException`.  
* **Answer:** **B**  
* **Deep Explanation:** `Scanner.close()` closes its underlying `Readable` or `InputStream`. Closing `System.in` closes native file descriptor 0 permanently.

---

#### MCQ 8
Which method should be used to read a password securely from the command line?  
A. `new Scanner(System.in).nextLine()`  
B. `System.console().readLine()`  
C. `System.console().readPassword()`  
D. `new BufferedReader(new InputStreamReader(System.in)).readLine()`  
* **Answer:** **C**  
* **Deep Explanation:** `Console.readPassword()` disables terminal echo and returns a `char[]` that can be zeroed out in memory immediately after use.

---

#### MCQ 9
In Java 18 and later, what is the default character set specified by JEP 400?  
A. ISO-8859-1  
B. UTF-16  
C. UTF-8  
D. Operating system dependent  
* **Answer:** **C**  
* **Deep Explanation:** JEP 400 standardized the default charset across the Java platform to UTF-8.

---

#### MCQ 10
Under what condition does `PrintWriter` with `autoFlush = true` flush its buffer?  
A. After every character written  
B. When writing `\n` via `writer.print("\n")`  
C. Only on calls to `println()`, `printf()`, or `format()`  
D. When the buffer reaches 50% capacity  
* **Answer:** **C**  
* **Deep Explanation:** `PrintWriter` auto-flush triggers only on `println`, `printf`, or `format`. Writing `\n` with `print()` or `write()` does not trigger a flush.

---

#### MCQ 11
What is the default internal character buffer size of `BufferedReader`?  
A. 1,024 characters  
B. 4,096 characters  
C. 8,192 characters  
D. 16,384 characters  
* **Answer:** **C**  
* **Deep Explanation:** OpenJDK's `BufferedReader` specifies `defaultCharBufferSize = 8192`.

---

#### MCQ 12
Given:
```java
System.out.printf("%2$s %1$s %<s", "A", "B");
```
What is printed?  
A. `A B B`  
B. `B A A`  
C. `B A B`  
D. `A B A`  
* **Answer:** **B**  
* **Deep Explanation:** `%2$s` prints 2nd arg `"B"`. `%1$s` prints 1st arg `"A"`. `%<s` uses relative indexing to reuse the previous argument (`"A"`). Thus, `B A A`.

---

#### MCQ 13
Why does `PrintWriter` not declare `throws IOException` on its output methods?  
A. It never encounters I/O errors.  
B. It suppresses `IOException` and records error state internally via `checkError()`.  
C. It converts `IOException` to `UncheckedIOException`.  
D. It relies entirely on asynchronous NIO channels.  
* **Answer:** **B**  
* **Deep Explanation:** `PrintWriter` and `PrintStream` suppress all `IOException`s internally. Callers must invoke `checkError()` to detect failures.

---

#### MCQ 14
Which of the following creates a platform-independent newline string outside of `printf`?  
A. `"\n"`  
B. `System.lineSeparator()`  
C. `System.getProperty("line.sep")`  
D. `"\r\n"`  
* **Answer:** **B**  
* **Deep Explanation:** `System.lineSeparator()` (Java 7+) returns the cached platform newline string (`\r\n` on Windows, `\n` on Unix).

---

#### MCQ 15
What happens when you compile and run:
```java
public class Test {
    public static void main(String[] args) {
        System.out.println(null);
    }
}
```
A. Prints `null`  
B. Throws `NullPointerException` at runtime  
C. Compilation Error: reference to println is ambiguous  
D. Prints empty line  
* **Answer:** **C**  
* **Deep Explanation:** The call matches both `println(char[])` and `println(String)`. Because neither is more specific, the compiler emits an ambiguity error.

---

### 5.2 Output Prediction (10)

#### Prediction 1
```java
public class Output1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner("10 20 30");
        int a = sc.nextInt();
        String rest = sc.nextLine();
        System.out.println("a=" + a + ", rest='" + rest + "'");
    }
}
```
* **Output:** `a=10, rest=' 20 30'`
* **Line-by-line Breakdown:** `sc.nextInt()` consumes `10`. The scanner cursor is now positioned immediately before the space preceding `20`. `sc.nextLine()` consumes all remaining characters on the line, including the leading space: `' 20 30'`.

---

#### Prediction 2
```java
public class Output2 {
    public static void main(String[] args) {
        System.out.printf("%+08d%n", 42);
        System.out.printf("%(d%n", -42);
    }
}
```
* **Output:**
```text
+0000042
(42)
```
* **Line-by-line Breakdown:** 
1. `%+08d`: `+` forces positive sign, `0` zero-pads, `8` width. `+` plus `42` is 3 chars, padded with 5 zeros = `+0000042`.
2. `%(d`: `(` flag encloses negative numbers in parentheses = `(42)`.

---

#### Prediction 3
```java
public class Output3 {
    public static void main(String[] args) {
        String data = "apple,banana,orange";
        Scanner sc = new Scanner(data).useDelimiter(",");
        while (sc.hasNext()) {
            System.out.print(sc.next().toUpperCase() + " ");
        }
    }
}
```
* **Output:** `APPLE BANANA ORANGE `
* **Line-by-line Breakdown:** Delimiter is changed to `,`. Scanner tokenizes by comma instead of whitespace, yielding `"apple"`, `"banana"`, `"orange"`.

---

#### Prediction 4
```java
public class Output4 {
    public static void main(String[] args) {
        System.out.print("A");
        System.out.println("B");
        System.out.print("C");
    }
}
```
* **Output:**
```text
AB
C
```
* **Line-by-line Breakdown:** `print("A")` outputs `A` without newline. `println("B")` outputs `B` followed by platform newline. `print("C")` outputs `C` on the second line without newline.

---

#### Prediction 5
```java
public class Output5 {
    public static void main(String[] args) {
        System.out.printf("[%10s] [%-10s]%n", "Java", "Java");
    }
}
```
* **Output:** `[      Java] [Java      ]`
* **Line-by-line Breakdown:** `%10s` right-aligns with 6 spaces padding. `%-10s` uses the `-` flag to left-align with 6 trailing spaces.

---

#### Prediction 6
```java
public class Output6 {
    public static void main(String[] args) {
        System.out.printf("%.3f%n", 3.14159);
    }
}
```
* **Output:** `3.142`
* **Line-by-line Breakdown:** `%.3f` specifies precision of 3 decimal digits. The value `3.14159` is rounded half-up to `3.142`.

---

#### Prediction 7
```java
public class Output7 {
    public static void main(String[] args) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        ps.print("Hello");
        ps.close();
        System.out.println(baos.toString(StandardCharsets.UTF_8));
    }
}
```
* **Output:** `Hello`
* **Line-by-line Breakdown:** `ps.print("Hello")` writes bytes into `baos`. Closing `ps` flushes the bytes. `baos.toString()` decodes and returns the accumulated string.

---

#### Prediction 8
```java
public class Output8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner("true FALSE 1 0");
        System.out.print(sc.nextBoolean() + " ");
        System.out.print(sc.nextBoolean() + " ");
        System.out.print(sc.hasNextBoolean());
    }
}
```
* **Output:** `true false false`
* **Line-by-line Breakdown:** `nextBoolean()` parses `"true"` (case-insensitive) as `true`, `"FALSE"` as `false`. Next token is `"1"`, which is not a valid boolean in Java, so `hasNextBoolean()` returns `false`.

---

#### Prediction 9
```java
public class Output9 {
    public static void main(String[] args) {
        Object val = null;
        System.out.printf("%s %b%n", val, val);
    }
}
```
* **Output:** `null false`
* **Line-by-line Breakdown:** `%s` converts `null` to the string `"null"`. `%b` converts `null` to `false` (in Java format strings, `%b` is true if and only if the argument is non-null and not Boolean.FALSE).

---

#### Prediction 10
```java
public class Output10 {
    public static void main(String[] args) {
        String s = "123";
        Scanner sc = new Scanner(s);
        System.out.println(sc.nextInt());
        System.out.println(sc.hasNext());
    }
}
```
* **Output:**
```text
123
false
```
* **Line-by-line Breakdown:** `sc.nextInt()` consumes `"123"`. The input stream is now at EOF. `sc.hasNext()` evaluates to `false`.

---

### 5.3 Code Tracing (5)

#### Trace 1: Scanner Token Extraction State Machine
```java
Scanner sc = new Scanner("  99   alpha   100  ");
while (sc.hasNext()) {
    if (sc.hasNextInt()) {
        System.out.print("INT:" + sc.nextInt() + " ");
    } else {
        System.out.print("STR:" + sc.next() + " ");
    }
}
```
* **Execution Trace:**
  1. `sc.hasNext()`: Skips leading whitespace `"  "`. Next token starts at `'9'`. Returns `true`.
  2. `sc.hasNextInt()`: Evaluates token `"99"` against integer regex. Matches! Returns `true`.
  3. `sc.nextInt()`: Consumes `"99"`. Prints `INT:99 `. Scanner cursor sits at whitespace following `99`.
  4. `sc.hasNext()`: Skips whitespace `"   "`. Next token starts at `'a'`. Returns `true`.
  5. `sc.hasNextInt()`: Token `"alpha"` does not match integer pattern. Returns `false`.
  6. `sc.next()`: Consumes `"alpha"`. Prints `STR:alpha `. Cursor sits at whitespace following `alpha`.
  7. `sc.hasNext()`: Skips `"   "`. Next token is `"100"`. Returns `true`.
  8. `sc.hasNextInt()`: Token `"100"` matches. Returns `true`.
  9. `sc.nextInt()`: Consumes `"100"`. Prints `INT:100 `.
  10. `sc.hasNext()`: Trailing whitespace `"  "` is skipped; EOF reached. Returns `false`.
  * **Final Output:** `INT:99 STR:alpha INT:100 `

---

#### Trace 2: BufferedReader EOF and Line Extraction
```java
String data = "First\n\nThird";
BufferedReader reader = new BufferedReader(new StringReader(data));
String line;
int count = 0;
while ((line = reader.readLine()) != null) {
    count++;
    System.out.println(count + ": [" + line + "]");
}
```
* **Execution Trace:**
  1. Iteration 1: `reader.readLine()` scans until first `\n`. Extracts `"First"`. `count` becomes 1. Prints `1: [First]`.
  2. Iteration 2: Next character is immediately `\n`. Line is empty string `""` (NOT null!). `count` becomes 2. Prints `2: []`.
  3. Iteration 3: Scans `"Third"` followed by EOF. Extracts `"Third"`. `count` becomes 3. Prints `3: [Third]`.
  4. Iteration 4: Reader is at EOF. `reader.readLine()` returns `null`. Loop terminates.
  * **Final Output:**
    ```text
    1: [First]
    2: []
    3: [Third]
    ```

---

#### Trace 3: Try-With-Resources Multi-Resource Unwinding
```java
class MockRes implements AutoCloseable {
    private final String id;
    MockRes(String id) { this.id = id; }
    public void close() { System.out.print("CLOSE_" + id + " "); }
}

public class Trace3 {
    public static void main(String[] args) {
        try (MockRes r1 = new MockRes("A");
             MockRes r2 = new MockRes("B")) {
            System.out.print("BODY ");
        }
    }
}
```
* **Execution Trace:**
  1. `r1` initialized with `"A"`.
  2. `r2` initialized with `"B"`.
  3. Try body executes: prints `BODY `.
  4. Block terminates normally.
  5. Resources are closed in **reverse order of declaration**:
     * `r2.close()` executed: prints `CLOSE_B `.
     * `r1.close()` executed: prints `CLOSE_A `.
  * **Final Output:** `BODY CLOSE_B CLOSE_A `

---

#### Trace 4: Auto-Flush Mechanics in PrintWriter vs PrintStream
```java
ByteArrayOutputStream b1 = new ByteArrayOutputStream();
PrintWriter pw = new PrintWriter(b1, true); // autoFlush = true
pw.print("Line 1\n"); // print() with \n
System.out.print("B1_len=" + b1.size() + " ");

pw.println("Line 2"); // println()
System.out.print("B1_len=" + b1.size());
```
* **Execution Trace:**
  1. `pw.print("Line 1\n")`: In `PrintWriter`, `print()` **does not trigger auto-flush**, even when passing `\n`! The text remains buffered in memory.
  2. `b1.size()`: 0 bytes written to `b1`. Prints `B1_len=0 `.
  3. `pw.println("Line 2")`: `println()` triggers auto-flush. All accumulated characters ("Line 1\nLine 2\n") are converted and flushed into `b1`.
  4. `b1.size()`: Size is now 14 (or 15 on Windows). Prints `B1_len=14`.
  * **Final Output:** `B1_len=0 B1_len=14`

---

#### Trace 5: Lookahead Input Validation State Recovery
```java
Scanner sc = new Scanner("xyz 456");
while (!sc.hasNextInt()) {
    String bad = sc.next();
    System.out.print("DISCARD:" + bad + " ");
}
int val = sc.nextInt();
System.out.print("VAL:" + val);
```
* **Execution Trace:**
  1. Loop condition: `!sc.hasNextInt()`. Token is `"xyz"`, which is not an integer. Evaluates to `true`.
  2. `sc.next()`: Consumes and returns `"xyz"`. Prints `DISCARD:xyz `.
  3. Loop condition: `!sc.hasNextInt()`. Next token is `"456"`. It is an integer. Evaluates to `false`. Loop terminates.
  4. `sc.nextInt()`: Consumes and returns `456`. Prints `VAL:456`.
  * **Final Output:** `DISCARD:xyz VAL:456`

---

### 5.4 Real-World Scenario Questions (5)

#### Scenario 1: High-Throughput Log Processing CPU Bottleneck
**Problem:**
An observability agent ingesting 100 MB/sec of application logs experiences 100% CPU utilization and falls behind real-time streams. A CPU flame graph indicates that 75% of CPU time is consumed by `java.util.Scanner` and `java.util.regex.Matcher.find()`.
```java
// PROBLEM CODE:
public void processLogs(InputStream logStream) {
    Scanner scanner = new Scanner(logStream);
    while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        parseAndIndex(line);
    }
}
```
*Why is this failing, and how do you redesign it for maximum throughput?*

* **Root Cause Analysis:**
`Scanner.nextLine()` uses regular expression matching over an internal 1024-char buffer. For long log lines, it repeatedly shifts buffers and runs regex scans, creating massive instruction cache misses and object allocations.
* **Architectural Remediation:**
Replace `Scanner` with `BufferedReader` or Java 8+ `Files.lines()`. For extreme throughput, use `FileChannel` with direct byte parsing:
```java
public void processLogsOptimized(InputStream logStream) throws IOException {
    try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(logStream, StandardCharsets.UTF_8), 65536)) { // 64KB buffer
        String line;
        while ((line = reader.readLine()) != null) {
            parseAndIndex(line);
        }
    }
}
```
*Result:* CPU utilization drops from 100% to under 12%, and throughput increases by ~15x.

---

#### Scenario 2: Silent Data Corruption in Multi-Platform Cloud Deployment
**Problem:**
A financial calculation engine runs tests cleanly on macOS developer workstations. When deployed to a Windows Server container cluster, German currency values (`€1.500,50`) become `â‚¬1.500,50` in generated reports, failing compliance audits.
```java
// PROBLEM CODE:
public void writeSummary(Path path, String summary) throws IOException {
    try (FileWriter writer = new FileWriter(path.toFile())) {
        writer.write(summary);
    }
}
```
*Explain why the behavior changed between environments and provide the fix.*

* **Root Cause Analysis:**
On macOS, Java legacy I/O defaulted to `UTF-8`. On the Windows Server instance (running an earlier JVM or configured with `-Dfile.encoding=COMPAT`), `FileWriter` used the Windows-1252 character set. When writing the Euro symbol `€` (`0xE2 0x82 0xAC` in UTF-8), it was transcoded into Windows-1252 multi-byte representations, resulting in Mojibake (`â‚¬`).
* **Architectural Remediation:**
Never use default `FileWriter` constructors. Use the modern NIO.2 API with an explicitly specified charset:
```java
public void writeSummaryFixed(Path path, String summary) throws IOException {
    Files.writeString(
        path, 
        summary, 
        StandardCharsets.UTF_8, 
        StandardOpenOption.CREATE, 
        StandardOpenOption.TRUNCATE_EXISTING
    );
}
```

---

#### Scenario 3: Memory Dump Credential Vulnerability
**Problem:**
A security penetration test forces an OutOfMemoryError in an enterprise banking microservice and analyzes the resulting heap dump (`.hprof`). The tester extracts database passwords and master API keys in plain text.
```java
// VULNERABLE CODE:
public void promptCredentials() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Admin Password: ");
    String password = scanner.nextLine();
    authClient.authenticate(password);
}
```
*Explain why garbage collection did not protect the credentials and provide the secure implementation.*

* **Root Cause Analysis:**
`String` objects are immutable. The character data is copied into a `byte[]` inside the `String` instance. The developer cannot clear or overwrite these bytes. Even after `password` falls out of scope, the `String` remains on the heap until the next GC cycle. If the JVM generates a heap dump or swap file in the interim, the plain text is completely exposed.
* **Architectural Remediation:**
```java
public void promptCredentialsSecure() {
    Console console = System.console();
    if (console == null) {
        throw new SecurityException("Terminal console unavailable for secure input");
    }
    char[] password = console.readPassword("Admin Password: ");
    try {
        authClient.authenticate(password);
    } finally {
        // Zero out the array immediately
        Arrays.fill(password, '\0');
    }
}
```

---

#### Scenario 4: Socket Hang and Protocol Deadlock
**Problem:**
A custom client-server RPC protocol intermittently hangs during high load. Thread dumps show the client blocked in `SocketInputStream.read()` while the server is also blocked in `SocketInputStream.read()`.
```java
// CLIENT CODE:
PrintWriter out = new PrintWriter(socket.getOutputStream());
out.println("PING");
BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
String response = in.readLine(); // HANGS FOREVER!
```
*Explain the deadlock mechanism and fix it.*

* **Root Cause Analysis:**
`new PrintWriter(OutputStream)` constructs a writer with `autoFlush = false`. When `out.println("PING")` is invoked, the text is written into the `PrintWriter` internal memory buffer, but **is never dispatched to the OS TCP socket buffer**. The client then executes `in.readLine()`, blocking for a reply. The server is waiting to receive the request before replying. Both sides wait forever.
* **Architectural Remediation:**
Construct `PrintWriter` with `autoFlush = true`, or call `flush()` explicitly:
```java
PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
out.println("PING"); // Auto-flushes because autoFlush=true and println() was called
```

---

#### Scenario 5: Silent Data Loss in Automated Report Generator
**Problem:**
A nightly billing batch job generates customer invoices using `PrintWriter`. One night, the network storage mount runs out of disk space (`ENOSPC`). The batch job completes with exit code 0 (Success), reporting 10,000 invoices processed, but 4,000 invoices on disk are completely empty or truncated.
```java
// PROBLEM CODE:
public void generateInvoice(File file, List<InvoiceItem> items) {
    try (PrintWriter writer = new PrintWriter(file)) {
        for (InvoiceItem item : items) {
            writer.println(item.formatRow());
        }
    }
}
```
*Why was no exception thrown when the disk filled up, and how do you ensure zero data loss?*

* **Root Cause Analysis:**
`PrintWriter` explicitly catches and suppresses all `IOException`s from its underlying streams. When `writer.println()` encounters a disk-full error, it sets an internal boolean error flag and returns cleanly without throwing an exception. The application had no awareness of the failure.
* **Architectural Remediation:**
Option 1: Check `writer.checkError()` before completing:
```java
if (writer.checkError()) {
    throw new IOException("Failed to write complete invoice due to underlying I/O error");
}
```
Option 2 (Preferred): Use `BufferedWriter`, which throws real `IOException`s on failure:
```java
public void generateInvoiceSafe(Path path, List<InvoiceItem> items) throws IOException {
    try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
        for (InvoiceItem item : items) {
            writer.write(item.formatRow());
            writer.newLine();
        }
    } // If disk fills, write() or close() throws IOException immediately!
}
```
