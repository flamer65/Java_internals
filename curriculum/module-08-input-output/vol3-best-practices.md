# Module 8 Volume 3: Input and Output – Best Practices, Common Mistakes, and Debugging

*Authored by a Senior Java Engineer, JVM Engineer, and OpenJDK Compiler Engineer (25+ Years of Experience)*

Welcome to Volume 3 of the Java Mastery Curriculum for Module 8: Input and Output. In modern enterprise systems, input and output operations represent the critical interface between the deterministic, memory-managed world of the Java Virtual Machine and the untrusted, high-latency, hardware-bound domain of external environments: operating system kernels, physical storage media, network sockets, terminals, and human operators.

Java's I/O ecosystem has evolved over nearly three decades—from the original byte and character streams introduced in Java 1.0 and 1.1, to the New I/O (NIO/NIO.2) architecture in Java 1.4 and 7, to modern high-productivity conveniences added in Java 11 through Java 21 LTS. While this evolution provides tremendous flexibility, it also carries substantial historical baggage, subtle edge cases, and architectural traps. An unclosed socket descriptor will exhaust the operating system file table; an unbuffered stream will bring the C2 JIT compiler to its knees with context-switching thrashing; a misplaced newline read will corrupt interactive console workflows; and an implicit character encoding will silently corrupt multi-byte UTF-8 data across heterogeneous cloud environments.

This volume provides an exhaustive, battle-tested guide to mastering Java I/O in production systems (targeted at Java 21 LTS). We begin with 11 non-negotiable architectural best practices, dissect 22 concrete pitfalls across beginner, intermediate, and senior engineering tiers, and conclude with professional debugging and JVM inspection methodologies.

---

## PART 6: BEST PRACTICES

```
+-------------------------------------------------------------------------+
|                       ENTERPRISE JAVA I/O PRINCIPLES                    |
|                                                                         |
|  +-------------------------------------------------------------------+  |
|  | Resource Determinism: try-with-resources on every AutoCloseable   |  |
|  | Throughput Efficiency: Buffered I/O amortizing syscall overhead   |  |
|  | Semantic Precision: Explicit Charset (StandardCharsets.UTF_8)     |  |
|  | Safe Ingestion: Lookahead validation & bounded token parsing      |  |
|  | System Hygiene: Never close JVM-global descriptors (System.in)    |  |
|  | Defensive Security: Zeroable char[] over immutable Strings        |  |
|  +-------------------------------------------------------------------+  |
+-------------------------------------------------------------------------+
```

---

### 6.1 Always Use Try-With-Resources for I/O Resources

Prior to Java 7, releasing operating system I/O resources (file descriptors, sockets, pipes) required manual, cumbersome, and inherently brittle `try-finally` blocks. Developers frequently wrote code that either leaked resources during exceptional conditions or inadvertently masked primary business exceptions with secondary exceptions thrown during cleanup.

Java 7 introduced the **try-with-resources statement** (JLS §14.20.3), governing any object implementing `java.lang.AutoCloseable` or `java.io.Closeable`.

#### 1. The Mechanics of Try-With-Resources
When a resource is declared in the header of a try-with-resources block:
1. It is scoped strictly to the execution of that try block.
2. It is implicitly `final`; reassigning it inside the block causes a compilation error.
3. The OpenJDK compiler (`javac`) generates bytecode ensuring that `close()` is unconditionally invoked when the block exits—whether normally, via `return`, `break`, `continue`, or due to an uncaught `Throwable`.
4. If the body of the try block throws an exception and the subsequent implicit call to `close()` *also* throws an exception, Java does not swallow either! The primary exception is propagated to the caller, while the exception from `close()` is preserved and attached to the primary exception via `Throwable.addSuppressed(Throwable)`.

```java
// ANTI-PATTERN: Pre-Java 7 manual resource cleanup (Brittle & Leaky)
public static String readFirstLineLegacy(String path) throws IOException {
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(path));
        return reader.readLine();
    } finally {
        if (reader != null) {
            // DANGER 1: If readLine() threw an IOException AND close() throws an IOException,
            // the exception from close() obliterates the readLine() exception!
            // DANGER 2: Verbose, error-prone boilerplate.
            reader.close();
        }
    }
}

// IDIOMATIC: Modern try-with-resources (Robust, Safe, Clean)
public static String readFirstLine(Path path) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
        return reader.readLine();
    } // reader.close() is guaranteed here; any close exception is added as suppressed!
}
```

#### 2. Managing Multiple Resources and Close Ordering
When managing multiple streams or bridges, declare each within the try specification, separated by semicolons. Resources are initialized from left to right and are **closed in reverse order of declaration** (from right to left). This mirrors the destruction of stack frames and ensures dependent wrapper streams (e.g., `BufferedWriter`) flush and close before their underlying streams (e.g., `FileOutputStream`) are terminated.

```java
public static void copyFiles(Path src, Path dest) throws IOException {
    try (InputStream in = Files.newInputStream(src);
         OutputStream out = Files.newOutputStream(dest)) {
        in.transferTo(out);
    } // 'out' is closed first, then 'in'
}
```

---

### 6.2 Prefer `BufferedReader` for Performance-Critical Reading

When reading character data from an underlying stream or file, reading single characters directly using `Reader.read()` or unbuffered stream reads is catastrophic to performance.

```
+--------------------------------------------------------------------------+
|                       THE SYSCALL BOTTLENECK                             |
|                                                                          |
|  Unbuffered Read:                                                        |
|  [Java App] --(1 Char)--> [JNI] --(Syscall)--> [Kernel] --(Disk Read)   |
|  * 1,000,000 chars = 1,000,000 User-to-Kernel Context Switches!         |
|                                                                          |
|  Buffered Read (BufferedReader 8192 chars):                              |
|  [Java App] <-- In-Memory Array Loop -- [BufferedReader Buffer (8KB)]    |
|                                               ^                          |
|                                     (Single 8KB Syscall)                 |
|                                               |                          |
|                                      [OS Kernel Page]                    |
|  * 1,000,000 chars = ~122 Kernel Syscalls! (~8000x fewer context hops)   |
+--------------------------------------------------------------------------+
```

#### 1. Why Buffering Matters at the OS Boundary
Every native read operation requires:
* Transitioning CPU execution privilege from User Space (Ring 3) to Kernel Space (Ring 0).
* Saving CPU registers and swapping stack pointers.
* Executing the POSIX `read()` or Windows `ReadFile` system call.
* Verifying file descriptor permissions, updating file pointers, and copying memory from kernel page cache into user space.
* Transitioning back to User Space.

A standard `BufferedReader` allocates an internal character array buffer (default size: `8192` characters, or 16 KB of UTF-16 characters in memory). When you call `read()` or `readLine()`, it reads up to 8192 characters from the underlying source in a single batch operation. Subsequent calls consume data directly from in-memory heap arrays without crossing the JNI barrier.

#### 2. Comparison: `Scanner` vs `BufferedReader` vs `Files.readString`
* **`BufferedReader`:** Optimized strictly for streaming text. Low memory overhead, blazing fast line-by-line reading via `readLine()`, zero regular expression processing.
* **`Scanner`:** A multi-token lexical scanner. Relies heavily on `java.util.regex.Pattern` and synchronizes internal matcher state. It is 5x to 25x slower than `BufferedReader` when processing large text files.
* **`Files.readString()` / `Files.readAllLines()`:** Slurps the entire file into memory at once. Exceptional for configuration files and small documents (< 50 MB), but causes `OutOfMemoryError` on large datasets.

```java
// IDIOMATIC: High-performance line-by-line streaming
public static long countMatchingLines(Path path, String target) throws IOException {
    long count = 0;
    try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains(target)) {
                count++;
            }
        }
    }
    return count;
}
```

---

### 6.3 Always Specify Charset Explicitly (`StandardCharsets.UTF_8`)

One of the most persistent sources of enterprise production outages is the "works on my machine" encoding bug. A Java application tested on macOS (which defaulted to UTF-8) is deployed to an on-premise Windows Server (which historically defaulted to `windows-1252`), resulting in corrupted accents, mangled JSON payloads, and unparseable symbols.

#### 1. JEP 400 (UTF-8 by Default) and Why Explicit Specification Remains Essential
Java 18 introduced **JEP 400: UTF-8 by Default**, making UTF-8 the standard charset across all standard Java APIs when no charset is supplied. However, relying on implicit defaults remains an engineering anti-pattern for three reasons:
1. **Legacy Runtime Compatibility:** Code deployed on Java 8, 11, or 17 LTS will still honor the host operating system's native encoding or the `-Dfile.encoding` VM flag.
2. **Library Code Portability:** Third-party dependencies or legacy plugins running inside the same JVM might manipulate system properties.
3. **Intentionality:** Explicit code documents the engineering contract. It leaves zero ambiguity for security audits, data ingestion pipelines, and cross-platform clients.

#### 2. Always Use `StandardCharsets.UTF_8`
Never pass raw strings like `"UTF-8"` or `"utf-8"`.
* Using `StandardCharsets.UTF_8` references a pre-instantiated, immutable `java.nio.charset.Charset` singleton.
* Passing `"UTF-8"` as a string forces the JVM to execute `Charset.forName("UTF-8")`, which involves string hashing, synchronization over the charset provider lookup map, and checked exception handling (`UnsupportedEncodingException`).

```java
// ANTI-PATTERN: Relying on system default or string literal
InputStreamReader bad1 = new InputStreamReader(inputStream); // Uses system default!
byte[] bad2 = "Hello World".getBytes(); // Platform dependent!
byte[] bad3 = "Hello World".getBytes("UTF-8"); // Throws checked UnsupportedEncodingException!

// IDIOMATIC: Explicit, fast, statically verified
InputStreamReader good1 = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
byte[] good2 = "Hello World".getBytes(StandardCharsets.UTF_8);
BufferedReader good3 = Files.newBufferedReader(path, StandardCharsets.UTF_8);
```

---

### 6.4 Use `Scanner` Only for Simple Interactive Console Input

`java.util.Scanner` was introduced in Java 5 as a high-level utility designed primarily for classroom instruction and basic CLI applications. It is not an enterprise data-ingestion tool.

```
+--------------------------------------------------------------------------+
|                        SCANNER INTERNALS OVERVIEW                        |
|                                                                          |
|  [Underlying InputStream]                                                |
|             |                                                            |
|             v                                                            |
|  [Internal CharBuffer (1024 chars default)]                              |
|             |                                                            |
|             v                                                            |
|  [java.util.regex.Matcher Engine] <---> [Delimiter Pattern (\\p{javaWH})] |
|             |                                                            |
|             v                                                            |
|  [Locale-Specific Number Parsers (DecimalFormat, Grouping Separators)]   |
|             |                                                            |
|             v                                                            |
|  [Typed Token Return: int, double, String]                               |
+--------------------------------------------------------------------------+
```

#### 1. Why `Scanner` Is Inappropriate for Enterprise Ingestion
* **Heavyweight Regex Engine:** Every call to `hasNext()`, `next()`, `nextInt()`, etc., matches patterns using an internal `java.util.regex.Matcher`. This generates heavy CPU instruction overhead and causes continuous object allocations on the young-generation heap.
* **Small Default Buffer:** Scanner uses a 1024-character buffer, far smaller than `BufferedReader`'s 8192 characters. This leads to frequent re-buffering when processing long lines or large files.
* **Synchronized State:** Although `Scanner` is not thread-safe for concurrent use by multiple threads, its internal buffer management involves synchronization blocks that impose lock-elision/acquisition overhead.

#### 2. The Right Tool for the Right Job
* **Interactive CLI / Educational Exercises:** Use `Scanner(System.in)` for reading simple whitespace-delimited tokens from human users typing on a keyboard.
* **Batch Ingestion / Log Processing / Large Files:** Use `BufferedReader`, `Files.lines()`, or `java.nio.channels.FileChannel`.

---

### 6.5 Use `printf`/`format` for Complex Output Formatting

Naively concatenating strings with the `+` operator for tabular reports or formatted numbers produces illegible code and creates excessive intermediate `String` and `StringBuilder` allocations.

```java
// ANTI-PATTERN: Fragile, unreadable manual padding
String row = id + "    " + name + "       $" + (Math.round(price * 100.0) / 100.0);

// IDIOMATIC: Clean, declarative, localized format string
System.out.printf("%-10d %-25s $%10.2f%n", id, name, price);
```

#### Formatting Specifier Syntax Structure:
The full syntax for a format specifier in Java is:
`%[argument_index$][flags][width][.precision]conversion`

| Element | Purpose | Production Examples |
| :--- | :--- | :--- |
| **`argument_index$`** | References a specific argument index (1-based) or `<` to reuse previous. | `"%1$s %1$s"` (prints arg 1 twice), `"%d %<x"` (prints arg in decimal then hex) |
| **`flags`** | Modifies output: `-` (left-align), `+` (force sign), `0` (zero-pad), `,` (grouping separators), `(` (bracket negative). | `"%+d"` (+42), `"%08d"` (00000042), `"%,d"` (1,000,000), `"$(,10.2f"` |
| **`width`** | Minimum number of characters written to output. | `"%15s"` (padded with spaces to 15 chars) |
| **`.precision`** | Decimal places for floats, or max characters for strings. | `"%.2f"` (rounds to 2 decimals), `"%.10s"` (truncates string to max 10 chars) |
| **`conversion`** | The data type character. | `d` (integer), `f` (float), `s` (string), `b` (boolean), `c` (char), `x` (hex), `n` (newline) |

> [!TIP]
> While `printf` is fantastic for CLI utilities, avoid invoking `System.out.printf()` in ultra-high-throughput logging hot paths (e.g., millions of events/sec). The internal `java.util.Formatter` parses the format string using regular expressions and incurs boxing overhead for primitive arguments. For high-throughput logging, use structured zero-allocation loggers like Log4j2 or SLF4J with parameterized messages (`logger.info("Order {} processed", id)`).

---

### 6.6 Never Close `System.in` in Library Code

In Java, `System.in` is an instance of `InputStream` that wraps native file descriptor 0 (`stdin`). It is a **process-global, JVM-wide singleton**.

```
+--------------------------------------------------------------------------+
|                      THE SYSTEM.IN DESTRUCTION TRAP                      |
|                                                                          |
|  [Component A: Library Utility]                                          |
|  try (Scanner scanner = new Scanner(System.in)) {                        |
|      return scanner.nextLine();                                          |
|  } // scanner.close() triggers System.in.close() -> OS fd 0 CLOSED!     |
|                                                                          |
|  [Component B: Main Application Loop]                                    |
|  Scanner mainScanner = new Scanner(System.in);                           |
|  mainScanner.nextLine(); // CRASH!                                       |
|  ==> java.util.NoSuchElementException or java.io.IOException: Stream Closed|
|                                                                          |
|  * NATIVE FD 0 CANNOT BE RE-OPENED INSIDE THE RUNNING JVM!               |
+--------------------------------------------------------------------------+
```

When you wrap `System.in` in a `Scanner`, `BufferedReader`, or `InputStreamReader`, closing the wrapper automatically invokes `close()` on the underlying stream!

```java
// CATASTROPHIC ANTI-PATTERN in a reusable library/utility method
public class UserInputUtil {
    public static String promptUser(String prompt) {
        System.out.print(prompt);
        // BUG: try-with-resources closes the Scanner, which closes System.in!
        try (Scanner sc = new Scanner(System.in)) {
            return sc.nextLine();
        }
    }
}
// Calling UserInputUtil.promptUser("Name: "); a second time will crash the entire application!
```

#### Architectural Rules for `System.in`:
1. **Ownership Principle:** Only the root application entry point (e.g., `main()` or the dedicated CLI controller) may instantiate and manage the lifecycle of a `System.in` reader.
2. **Never Close System.in:** In standalone CLI applications, there is no need to close `System.in`—the operating system reclaims standard descriptors when the JVM process exits.
3. **Non-Closing Streams for Testing:** If you must use try-with-resources with code that might receive `System.in`, wrap it in a filter stream that overrides `close()` to do nothing (e.g., Apache Commons IO `CloseShieldInputStream`).

---

### 6.7 Use `System.console()` with Null Check for Password Reading

When a user enters sensitive credentials (passwords, encryption keys, tokens) at a terminal, two severe security risks arise:
1. **Shoulder Surfing / Visual Echo:** The terminal displays the typed characters on screen or writes them to terminal scrollback buffers.
2. **Memory Persistence of `java.lang.String`:** Strings in Java are immutable and allocated on the heap. Once constructed, a password stored as a `String` cannot be cleared from memory. It sits in heap memory until garbage collected and cleared, leaving it vulnerable to process memory dumps, heap profilers, and swap file inspection.

`java.io.Console` addresses both issues:
* `console.readPassword()` disables terminal echo at the OS terminal driver level.
* It returns a `char[]` rather than a `String`. Once the password is verified, the developer can explicitly overwrite the character array with zeros (`Arrays.fill(pwd, '\0')`).

```java
// IDIOMATIC: Secure password ingestion with defensive fallback
public static char[] promptSecurePassword(String prompt) {
    Console console = System.console();
    if (console != null) {
        // Safe: Echo disabled at OS driver level, returns char[]
        return console.readPassword("%s", prompt);
    } else {
        // DEFENSIVE FALLBACK: System.console() is NULL inside IDEs, pipes, and daemons!
        System.err.println("WARNING: Secure console unavailable. Input will echo.");
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toCharArray();
    }
}

// Memory sanitization workflow:
char[] password = promptSecurePassword("Enter Master Key: ");
try {
    authenticate(password);
} finally {
    // CRITICAL: Wipe sensitive plain-text bytes immediately from RAM
    Arrays.fill(password, '\0');
}
```

---

### 6.8 Prefer Modern Files API (Java 11+) for File Operations

The original `java.io.File` API introduced in Java 1.0 is deeply flawed:
* Many methods return boolean `false` on failure (e.g., `delete()`, `mkdir()`) with zero exception information explaining *why* it failed (permission denied, disk full, file locked).
* It does not support symbolic links or modern filesystem metadata attributes.
* Path manipulations do not scale well and perform blocking synchronous metadata lookups.

Java 7 introduced NIO.2 (`java.nio.file.Path`, `java.nio.file.Files`), and Java 11 enriched it with streamlined methods that eliminate virtually all boilerplate:

```java
// Reading an entire configuration file (Java 11+)
Path configPath = Path.of("app.properties");
String content = Files.readString(configPath, StandardCharsets.UTF_8);

// Writing atomic data to disk (Java 11+)
Files.writeString(
    Path.of("output.log"), 
    "Operation successful\n", 
    StandardCharsets.UTF_8, 
    StandardOpenOption.CREATE, 
    StandardOpenOption.APPEND
);

// High-throughput streaming of lines (Java 8+)
// NOTE: Files.lines() returns a Stream<String> that wraps an underlying open file channel.
// You MUST wrap the Stream in try-with-resources to ensure the file descriptor closes!
try (Stream<String> lines = Files.lines(Path.of("massive.log"), StandardCharsets.UTF_8)) {
    long errorCount = lines.filter(line -> line.startsWith("ERROR")).count();
}
```

---

### 6.9 Use `System.lineSeparator()` or `%n`, Not `\n` for Platform-Independent Newlines

Line terminators differ across operating systems:
* **Unix / Linux / macOS:** Line Feed (`\n`, ASCII `0x0A`, LF)
* **Windows:** Carriage Return + Line Feed (`\r\n`, ASCII `0x0D 0x0A`, CRLF)
* **Classic Mac (pre-OS X):** Carriage Return (`\r`, ASCII `0x0D`, CR)

Hardcoding `\n` into output streams generates malformed text files on Windows: Windows Notepad or legacy command-line tools may display the entire file on a single run-on line or miscalculate line offsets.

```java
// WRONG: Hardcoded Unix newline
System.out.print("First Line\nSecond Line\n");
System.out.printf("Total: %d\n", total);

// IDIOMATIC: Completely portable across Linux, Windows, macOS
// 1. Inside printf / String.format: Use %n (NOT \n)
System.out.printf("Total: %d%n", total);

// 2. In general string building or stream writing: Use System.lineSeparator()
String text = "First Line" + System.lineSeparator() + "Second Line" + System.lineSeparator();
writer.write(text);
```

> [!NOTE]
> `System.lineSeparator()` was introduced in Java 7. It returns a cached system property string, making it significantly faster than repeatedly calling `System.getProperty("line.separator")`.

---

### 6.10 Validate All User Input (`hasNextInt()` Before `nextInt()`)

The methods on `java.util.Scanner` like `nextInt()`, `nextDouble()`, and `nextLong()` are **non-forgiving**. If the next token in the input stream cannot be translated into the requested type, the scanner does not return a fallback value—it immediately throws an unchecked `InputMismatchException`.

Furthermore, **the invalid token remains unconsumed in the scanner's internal buffer**. If you catch the exception in a loop without clearing the token, your program will enter an infinite loop!

```java
// IDIOMATIC: Robust interactive input validation loop
public static int readValidInteger(Scanner scanner, String prompt) {
    while (true) {
        System.out.print(prompt);
        if (scanner.hasNextInt()) {
            return scanner.nextInt(); // Valid token consumed and returned
        } else {
            String invalidToken = scanner.next(); // CRITICAL: Consume the bad token to unblock!
            System.err.printf("Error: '%s' is not a valid integer. Please try again.%n", invalidToken);
        }
    }
}
```

---

### 6.11 Flush Output When Necessary (Especially `PrintWriter` Without Auto-Flush)

Output streams employ buffering at multiple levels: inside JVM heap buffers (`BufferedWriter`, `BufferedOutputStream`), within native C standard library buffers, and inside the OS kernel page cache. Data written to an output stream or writer is **not guaranteed** to be dispatched to the physical hardware or network socket until the buffer fills or an explicit `flush()` occurs.

#### The `PrintWriter` / `PrintStream` Auto-Flush Trap
Many developers assume `PrintWriter` always flushes on newlines. That is false!
* In `PrintWriter`, auto-flush is **disabled by default**. You must explicitly pass `true` in constructors: `new PrintWriter(out, true)`.
* Even with `autoFlush = true`, `PrintWriter` flushes **only** on invocations of `println()`, `printf()`, or `format()`. Calling `print("\n")` or `write("\n")` **does not trigger a flush**!
* In socket programming or client-server IPC, failing to flush an outgoing request results in a deadlock: the client waits forever for the server to reply, while the server waits forever because the client's request is still sitting unwritten in the client's local memory buffer.

```java
// Socket communication deadlock trap
try (Socket socket = new Socket("api.internal", 8080);
     PrintWriter writer = new PrintWriter(socket.getOutputStream(), false); // autoFlush FALSE
     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

    writer.println("GET_STATUS");
    // BUG: writer.println() does NOT flush! The byte array remains in the PrintWriter buffer.
    // writer.flush(); // MANDATORY! Without this, the server never receives the packet.

    String response = reader.readLine(); // DEADLOCK: Thread hangs indefinitely here!
}
```

---

## PART 7: COMMON MISTAKES

```
+-------------------------------------------------------------------------+
|                        JAVA I/O MISTAKE TAXONOMY                        |
|                                                                         |
|  [BEGINNER: Basic Syntax & API Usage]                                   |
|   1. Scanner newline trap          2. Resource leaks                    |
|   3. Scanner for large files       4. Missing imports                   |
|   5. Unhandled InputMismatch       6. System.console() in IDE           |
|   7. \n instead of %n              8. Unflushed output                  |
|   9. == instead of .equals()      10. Reading from closed Scanner       |
|                                                                         |
|  [INTERMEDIATE: Stream Mechanics & Character Sets]                      |
|   1. Undeclared platform charset   2. Closing System.in                 |
|   3. Scanner locale decimal bugs   4. BufferedReader null handling      |
|   5. printf format mismatch        6. Nested reader resource leaks      |
|   7. Ignoring PrintWriter errors (checkError)                           |
|                                                                         |
|  [SENIOR: Concurrency, Performance, HotSpot & Security]                 |
|   1. Production charset corruption 2. Suppressed exception leaks        |
|   3. System.out virtual thread contention                               |
|   4. Zero-copy vs buffered throughput bottlenecks                      |
|   5. String vs char[] password retention                                |
+-------------------------------------------------------------------------+
```

---

### 7.1 Beginner Mistakes (10)

#### Mistake 1: The Scanner Newline Trap (`nextInt()` followed by `nextLine()`)
This is the single most common pitfall encountered by developers starting with Java I/O.

```java
// BUGGY CODE:
Scanner scanner = new Scanner(System.in);
System.out.print("Enter your age: ");
int age = scanner.nextInt();

System.out.print("Enter your full name: ");
String name = scanner.nextLine(); // BUG: Skips input and assigns ""!

System.out.println("Age: " + age + ", Name: '" + name + "'");
```
* **Mechanics:** `nextInt()` parses and consumes only the numeric digits (e.g., `'2'`, `'5'`). When the user presses the `Enter` key, a newline character (`\n` or `\r\n`) is placed into the input buffer. `nextInt()` leaves that newline character sitting in the buffer. When `nextLine()` is called immediately afterward, it scans until it hits the very first newline character. Finding the leftover newline, it returns immediately with an empty string `""`!
* **The Fix:** Consume the dangling newline by placing an empty `scanner.nextLine()` call immediately after reading primitives:
```java
int age = scanner.nextInt();
scanner.nextLine(); // Consumes the dangling newline delimiter!
String name = scanner.nextLine(); // Now correctly waits for user input
```

---

#### Mistake 2: Not Closing Resources (Resource Leaking)
```java
// BUGGY CODE:
public void writeReport(String filename, String data) throws IOException {
    FileWriter writer = new FileWriter(filename);
    writer.write(data);
    // BUG: Missing writer.close() or try-with-resources!
}
```
* **Mechanics:** Java objects live on the heap and are reclaimed by the Garbage Collector. However, underlying operating system file descriptors (allocated in the OS kernel's open file table) are **not** managed by the Java GC. If resources are not closed:
  1. Internal memory buffers are never flushed, leading to truncated or zero-byte files on disk.
  2. On Windows, open files are locked by the OS kernel, preventing other processes or threads from deleting, renaming, or reading them.
  3. Under heavy load, the process exhausts the OS per-process file descriptor limit (`EMFILE: Too many open files`), crashing the JVM.
* **The Fix:** Always use try-with-resources.
```java
public void writeReport(String filename, String data) throws IOException {
    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filename), StandardCharsets.UTF_8)) {
        writer.write(data);
    }
}
```

---

#### Mistake 3: Using `Scanner` for Large File Reading
```java
// BUGGY CODE: Horrendously slow on a 500MB log file
public static int countWords(File file) throws FileNotFoundException {
    int words = 0;
    try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNext()) { // Evaluates regex on every single token!
            scanner.next();
            words++;
        }
    }
    return words;
}
```
* **Mechanics:** `Scanner` matches regular expressions for every token. For a 10-million-word document, `Scanner` will execute regular expression state machine transitions 10 million times, allocating millions of temporary objects and thrashing the CPU cache.
* **The Fix:** Use `BufferedReader` with string splitting, or a custom tokenizer.
```java
public static int countWordsFast(Path path) throws IOException {
    int words = 0;
    try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
        String line;
        while ((line = reader.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            words += st.countTokens();
        }
    }
    return words;
}
```

---

#### Mistake 4: Forgetting to Import `Scanner`
```java
// BUGGY CODE:
public class Main {
    public static void main(String[] args) {
        // COMPILATION ERROR: cannot find symbol (class Scanner)
        Scanner scanner = new Scanner(System.in);
    }
}
```
* **Mechanics:** Classes in the core `java.lang` package (like `String`, `System`, `Math`) are imported implicitly. Classes in `java.util` or `java.io` are not.
* **The Fix:** Add `import java.util.Scanner;` at the top of the compilation unit.

---

#### Mistake 5: Not Handling `InputMismatchException`
```java
// BUGGY CODE:
Scanner scanner = new Scanner(System.in);
System.out.print("Enter your rating (1-5): ");
int rating = scanner.nextInt(); // Crashes if user types "five" or "4.5"!
```
* **Mechanics:** If the user enters non-integer text, `nextInt()` throws `InputMismatchException` (an unchecked runtime exception). If unhandled, the thread terminates immediately with an ungraceful stack trace.
* **The Fix:** Guard using `scanner.hasNextInt()` or catch `InputMismatchException` and discard the invalid token.

---

#### Mistake 6: Using `System.console()` Inside an IDE
```java
// BUGGY CODE:
Console console = System.console();
// In IntelliJ IDEA, Eclipse, or NetBeans Run window: console is NULL!
String username = console.readLine("Username: "); // Throws NullPointerException!
```
* **Mechanics:** `System.console()` returns a non-null `Console` object **only if** the JVM is attached to an interactive terminal/TTY. When running inside an IDE's standard run window, or when stdin/stdout are redirected via pipes (`java App < input.txt`), the operating system does not assign an interactive TTY device. `System.console()` returns `null`.
* **The Fix:** Check for null before dereferencing, and provide a fallback to `Scanner(System.in)`.

---

#### Mistake 7: Using `\n` Instead of `%n` in `printf`
```java
// BUGGY CODE:
System.out.printf("Row 1\nRow 2\n");
```
* **Mechanics:** In `printf`, the escape sequence `\n` is interpreted by the compiler as a literal byte `0x0A`. On Windows systems expecting CRLF (`\r\n`), files generated with `\n` will have inconsistent or broken line endings.
* **The Fix:** In format strings, always use `%n`, which delegates directly to `System.lineSeparator()`.
```java
System.out.printf("Row 1%nRow 2%n");
```

---

#### Mistake 8: Not Flushing Output
```java
// BUGGY CODE:
PrintWriter writer = new PrintWriter(new FileWriter("output.txt")); // autoFlush is FALSE
writer.print("Operation complete.");
// System terminates or crashes before close()
```
* **Mechanics:** `PrintWriter` buffers written characters in an internal memory array. If the application terminates abruptly or pauses for long intervals without closing or flushing the writer, the data remains trapped in RAM and never reaches persistent storage.
* **The Fix:** Explicitly call `writer.flush()`, or construct with auto-flush enabled: `new PrintWriter(writer, true)`.

---

#### Mistake 9: Comparing Scanner Input with `==` Instead of `.equals()`
```java
// BUGGY CODE:
Scanner scanner = new Scanner(System.in);
System.out.print("Continue? (y/n): ");
String answer = scanner.next();
if (answer == "y") { // ALWAYS FALSE!
    System.out.println("Proceeding...");
}
```
* **Mechanics:** The `==` operator checks **reference identity** (whether both operands point to the exact same memory address on the heap). `scanner.next()` creates a dynamically allocated `String` object on the heap, whereas `"y"` resides in the JVM String Constant Pool. They are distinct heap references, so `==` always evaluates to `false`.
* **The Fix:** Always use `answer.equals("y")` or `answer.equalsIgnoreCase("y")`.

---

#### Mistake 10: Reading from a Closed Scanner
```java
// BUGGY CODE:
Scanner sc1 = new Scanner(System.in);
sc1.close(); // Closes the scanner AND System.in

Scanner sc2 = new Scanner(System.in);
// RUNTIME ERROR: IllegalStateException: Scanner closed
String text = sc2.nextLine(); 
```
* **Mechanics:** Once a `Scanner` is closed, its internal state machine transitions to `closed`. Any subsequent invocation of its read methods throws `IllegalStateException`. Furthermore, because closing `sc1` closed `System.in`, `sc2` cannot read from the underlying stream anyway.
* **The Fix:** Do not close scanners wrapping `System.in`, and do not create multiple wrapping scanners over standard input in the same process.

---

### 7.2 Intermediate Mistakes (7)

#### Mistake 1: Not Specifying Charset (Platform-Dependent Behavior)
```java
// BUGGY CODE:
FileReader reader = new FileReader("data.txt"); // Pre-Java 11: uses platform default!
FileWriter writer = new FileWriter("out.txt");
```
* **Mechanics:** Prior to Java 11, the `FileReader` and `FileWriter` constructors did not even accept a `Charset` argument! They hardcoded the use of `Charset.defaultCharset()`. Files encoded in UTF-8 on Linux would be garbled when read on Windows systems configured for Japanese Shift-JIS or European Windows-1252.
* **The Fix:** Use the Java 11 `FileReader`/`FileWriter` constructors that accept a `Charset`, or use `Files.newBufferedReader(path, StandardCharsets.UTF_8)`.

---

#### Mistake 2: Closing `System.in`
```java
// BUGGY CODE:
public void promptAge() {
    try (Scanner sc = new Scanner(System.in)) {
        int age = sc.nextInt();
    } // Closes System.in forever!
}

public void promptName() {
    try (Scanner sc = new Scanner(System.in)) {
        String name = sc.nextLine(); // Throws NoSuchElementException / IOException!
    }
}
```
* **Mechanics:** The try-with-resources statement invokes `close()` on the declared resource. For `Scanner(System.in)`, this cascades into `System.in.close()`. The native OS file descriptor 0 is permanently closed for the lifetime of the JVM process.
* **The Fix:** Never wrap `System.in` in a local try-with-resources statement. Hold a single, long-lived `Scanner` instance at the application entry level.

---

#### Mistake 3: Scanner Locale Issues (Comma vs. Period for Decimals)
```java
// BUGGY CODE: Runs fine in US, crashes in Germany/France
Scanner scanner = new Scanner("3.14");
double pi = scanner.nextDouble(); // Throws InputMismatchException in de_DE!
```
* **Mechanics:** `Scanner` is locale-sensitive. By default, it uses the host JVM's default locale (`Locale.getDefault()`). In countries like Germany, France, or Brazil, the decimal separator is a comma (`,`) rather than a period (`.`). When running in such a locale, `"3.14"` is rejected because the scanner expects `"3,14"`.
* **The Fix:** Explicitly set the locale on the scanner when parsing standardized machine-readable data:
```java
Scanner scanner = new Scanner("3.14").useLocale(Locale.US);
double pi = scanner.nextDouble(); // Deterministic regardless of host country
```

---

#### Mistake 4: `BufferedReader` Not Handling `null` Return (End of Stream)
```java
// BUGGY CODE:
BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
String line;
// Anti-pattern: reading without checking for null inside loop body
while (!(line = reader.readLine()).isEmpty()) { // Crashes with NullPointerException at EOF!
    process(line);
}
```
* **Mechanics:** Unlike `Scanner`, which provides `hasNextLine()` to check availability, `BufferedReader.readLine()` signals End-Of-File (EOF) by returning `null`. If a file ends with no blank line, or is completely empty, `readLine()` returns `null`. Attempting to call `.isEmpty()` on `null` immediately throws `NullPointerException`.
* **The Fix:** Idiomatic loop assignment idiom:
```java
String line;
while ((line = reader.readLine()) != null) {
    if (line.isEmpty()) continue;
    process(line);
}
```

---

#### Mistake 5: `printf` Format Mismatch (Wrong Specifier for Type)
```java
// BUGGY CODE:
double balance = 1250.50;
System.out.printf("Balance: %d%n", balance); // RUNTIME ERROR!
```
* **Mechanics:** In C/C++, format strings are often checked at compile time by GCC/Clang, or cause raw memory reinterpretations. In Java, format strings are parsed at runtime by `java.util.Formatter`. If you pass a floating-point number (`double`) to an integer specifier (`%d`), Java throws:
`java.util.IllegalFormatConversionException: d != java.lang.Double`.
* **The Fix:** Ensure format conversion specifiers strictly align with Java types:
  * Integers (`byte`, `short`, `int`, `long`, `BigInteger`): `%d`
  * Floating-point (`float`, `double`, `BigDecimal`): `%f`
  * Strings and arbitrary Objects: `%s`
  * Booleans: `%b`

---

#### Mistake 6: Not Using Try-With-Resources for Nested Readers
```java
// BUGGY CODE: Pre-Java 7 style nested instantiation
FileInputStream fis = null;
InputStreamReader isr = null;
BufferedReader br = null;
try {
    fis = new FileInputStream("data.txt");
    isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
    br = new BufferedReader(isr);
    // ...
} finally {
    if (br != null) br.close(); // What if 'isr' constructor threw an exception? fis leaked!
}
```
* **Mechanics:** If the constructor of `InputStreamReader` throws an exception (e.g., `OutOfMemoryError`), `br` is never assigned. The `finally` block attempts to close `br`, which is null. `fis` remains unclosed and leaks.
* **The Fix:** Modern Java eliminates constructor chains by using factory methods, or declaring all intermediary streams in try-with-resources:
```java
try (BufferedReader br = Files.newBufferedReader(Path.of("data.txt"), StandardCharsets.UTF_8)) {
    // Perfectly safe; factory handles internal unwinding on failure
}
```

---

#### Mistake 7: Ignoring `PrintWriter` Errors (`checkError()`)
```java
// BUGGY CODE:
PrintWriter writer = new PrintWriter(new FileOutputStream("/dev/full"));
writer.println("Data to persist");
// Looks successful, but NO DATA WAS WRITTEN!
```
* **Mechanics:** By design, `PrintWriter` and `PrintStream` **suppress all `IOException`s** from their underlying streams. Their methods (`print`, `println`, `write`) will never throw `IOException`. If the underlying disk is full, the network socket disconnects, or file permissions are revoked, the methods silently fail!
* **The Fix:** When using `PrintWriter` for critical output, you must explicitly poll `writer.checkError()`:
```java
writer.println("Critical transaction payload");
if (writer.checkError()) {
    throw new IOException("PrintWriter encountered an unrecoverable underlying I/O error");
}
```

---

### 7.3 Senior-Level Pitfalls (5)

#### Pitfall 1: Charset Encoding Bugs in Production (UTF-8 vs Platform Default)
A high-throughput payment processing gateway ingesting JSON messages from an international banking partner intermittently threw JSON syntax errors under peak load. The root cause was traced to a microservice deployed to Windows nodes without an explicit charset:

```java
// VULNERABLE PRODUCTION CODE:
public String decodePayload(byte[] rawBytes) {
    return new String(rawBytes); // Implicit platform default!
}
```
* **Deep Mechanics:** When `rawBytes` contained multi-byte UTF-8 sequences (e.g., German umlauts `ä` [0xC3, 0xA4], or Chinese currency symbols `¥` [0xC2, 0xA5]), the Windows-1252 decoder treated each byte as an independent Latin-1 character, decoding 0xC3 as `Ã` and 0xA4 as `¤`. This mutated strings, destroyed HMAC signature hashes, and corrupted database entries.
* **The Fix:** Enforce `StandardCharsets.UTF_8` at every string decoding and encoding boundary:
```java
public String decodePayload(byte[] rawBytes) {
    return new String(rawBytes, StandardCharsets.UTF_8);
}
```

---

#### Pitfall 2: Resource Leaks in Exception Paths
Consider this subtle constructor leak pattern frequently found in legacy codebases:

```java
// VULNERABLE CODE:
public static void writeData(String path, byte[] data) throws IOException {
    OutputStream out = new BufferedOutputStream(new FileOutputStream(path));
    try (out) { // Java 9 variable-in-try syntax
        out.write(data);
    }
}
```
* **Deep Mechanics:** Look closely at `new BufferedOutputStream(new FileOutputStream(path))`.
  1. `new FileOutputStream(path)` succeeds, allocating an OS file descriptor.
  2. Next, `new BufferedOutputStream(...)` runs. It attempts to allocate an 8KB byte array on the heap.
  3. If the JVM is low on memory and throws an `OutOfMemoryError` during `BufferedOutputStream` initialization, `out` is never assigned!
  4. The try block is never entered. The underlying `FileOutputStream` is **orphaned and leaked**. The OS file descriptor remains locked until the JVM process dies.
* **The Fix:** Never nest resource constructors. Use the modern NIO.2 `Files` API:
```java
public static void writeData(Path path, byte[] data) throws IOException {
    try (OutputStream out = Files.newOutputStream(path);
         BufferedOutputStream bout = new BufferedOutputStream(out)) {
        bout.write(data);
    }
}
```

---

#### Pitfall 3: Thread-Safety and Contention of `System.out` in Concurrent Programs
A low-latency financial trading system running 64 worker threads suffered severe latency spikes (p99 latency degraded from 20 microseconds to 15 milliseconds). A thread dump revealed dozens of threads blocked in `WAITING` or `BLOCKED` states on `java.io.PrintStream`.

```
"pool-1-thread-4" java.lang.Thread.State: BLOCKED (on object monitor)
    at java.io.PrintStream.println(PrintStream.java:822)
    - waiting to lock <0x0000000712ab0800> (a java.io.PrintStream)
    at com.engine.OrderProcessor.process(OrderProcessor.java:45)
```

* **Deep Mechanics:**
  `System.out` is an instance of `PrintStream`. If you inspect the OpenJDK source code for `PrintStream`:
  ```java
  public void println(String x) {
      synchronized (this) { // SYNCHRONIZED ON THE PRINTSTREAM INSTANCE!
          print(x);
          newLine();
      }
  }
  ```
  Every single `System.out.print()` and `System.out.println()` call acquires an intrinsic monitor lock on the shared global `System.out` instance. When multiple threads log debug messages to `System.out`, they are serialized.
  Furthermore, with the introduction of **Virtual Threads** in Java 21 (Project Loom), executing a `synchronized` block that blocks on an I/O operation can **pin** the underlying carrier thread (the platform thread) to the virtual thread, starving the ForkJoinPool scheduler!
* **The Fix:** Never use `System.out` in multi-threaded production code. Use asynchronous, non-blocking logging frameworks (e.g., Log4j2 with LMAX Disruptor ring buffers) that dispatch log events to background worker threads without blocking request threads.

---

#### Pitfall 4: Performance Bottlenecks: `Scanner` vs `BufferedReader` vs Files API
Consider an application processing 10-gigabyte CSV files. Using naive I/O constructs cripples throughput:

| Approach | Architecture | Throughput | Heap Allocation Rate |
| :--- | :--- | :--- | :--- |
| **`Scanner(file)`** | Tokenized Regex Parsing | ~15 MB/s | Extremely High (> 1.2 GB/s garbage) |
| **`BufferedReader`** | In-Memory 8KB Char Buffering | ~180 MB/s | Moderate (String allocations per line) |
| **`Files.newByteChannel`** | Direct Buffer / Memory Map | ~950 MB/s | Zero (Primitive buffer reuse) |

```java
// SENIOR ARCHITECTURE: Zero-copy direct memory mapping for extreme throughput
public static long processMassiveFile(Path path) throws IOException {
    long checksum = 0;
    try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
        long size = channel.size();
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, size);
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            checksum += b;
        }
    }
    return checksum;
}
```

---

#### Pitfall 5: Security: `String` vs `char[]` for Passwords and Keys
Storing passwords as `java.lang.String` violates fundamental security engineering principles:

```java
// CATASTROPHIC SECURITY FLAW:
public void login() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Password: ");
    String secret = scanner.nextLine(); // Leaked into heap memory!
    performAuth(secret);
}
```
* **Deep Mechanics:**
  1. **Heap Immutability:** Strings in Java are immutable backed by `byte[]` arrays. There is no public API to clear, zero out, or mutate the contents of a `String`.
  2. **String Deduplication & Interning:** If the JVM runs with `-XX:+UseStringDeduplication`, strings with identical contents share underlying character arrays, extending their lifetime indefinitely.
  3. **Memory Scraping:** If an attacker compromises the server or forces a JVM crash, they can capture a heap dump (`jcmd <pid> GC.heap_dump`). Tools like Eclipse MAT or `strings /proc/<pid>/mem` will instantly reveal every password typed since process boot in plain text!
* **The Fix:** Always read passwords as `char[]` using `System.console().readPassword()`, and wipe the array immediately:
```java
char[] secret = console.readPassword("Password: ");
try {
    performAuth(secret);
} finally {
    Arrays.fill(secret, '\0'); // Overwrites memory addresses with zeros
}
```

---

## PART 8: DEBUGGING JAVA I/O

```
+-------------------------------------------------------------------------+
|                        I/O DEBUGGING TOOLCHAIN                          |
|                                                                         |
|  [HIGH LEVEL: IntelliJ IDEA]                                            |
|   - Static Inspections: unclosed streams, redundant flush, bad format   |
|   - Debugger Buffer Drilling: char buffer pointers (cb, nChars)        |
|   - Test Stream Redirection: System.setIn() / System.setOut()           |
|                                                                         |
|  [RUNTIME EXCEPTIONS: Root Cause Diagnosis]                             |
|   - FileNotFoundException vs NoSuchFileException                        |
|   - InputMismatchException vs NoSuchElementException                    |
|   - IllegalFormatConversionException vs MissingFormatArgumentException |
|                                                                         |
|  [JVM & OS LEVEL: Native Diagnostics]                                   |
|   - OS File Descriptors: lsof -p <pid>, /proc/<pid>/fd                  |
|   - JDK Flight Recorder (JFR): jdk.FileRead, jdk.SocketRead             |
|   - Thread Dumps: threads blocked on native socketRead0 or writeBytes   |
+-------------------------------------------------------------------------+
```

---

### 8.1 IntelliJ IDEA Tooling for I/O Operations

IntelliJ IDEA provides industry-leading static inspection engines and runtime debugging utilities designed specifically for Java I/O.

#### 1. Crucial I/O Inspections to Enable
Navigate to `Settings | Editor | Inspections | Java | Resource management` and ensure the following are enabled:
* **"AutoCloseable used without 'try-with-resources'"**: Flags any instantiation of an `AutoCloseable` class that is not governed by a try-with-resources statement. Set severity to **Error**.
* **"I/O stream not closed"**: Flags legacy manual try-finally streams where close calls are missing or unreachable.
* **"Redundant flush"**: Identifies explicit calls to `flush()` immediately before a `close()` statement (`close()` automatically flushes underlying buffers).
* **"Malformed format string"**: Statically parses format strings inside `printf`, `String.format`, and `System.out.printf`, flagging type mismatches (e.g., `%d` matching a `Double`) directly in the editor before compilation!

#### 2. Drilling into Stream Buffers in the Debugger
When troubleshooting missing data or newline problems:
1. Set a breakpoint on the read line.
2. In the **Variables** pane, expand the `BufferedReader` instance:
   * `cb`: The internal character array (`char[8192]`). You can directly inspect the cached characters pulled from disk.
   * `nextChar`: The cursor index pointing to the next character to be read.
   * `nChars`: The total count of valid characters currently held in the buffer.
3. For a `Scanner`:
   * `buf`: The internal character buffer (`CharBuffer`).
   * `position`: Current scan offset.
   * `matcher`: The internal regex matcher engine state.

#### 3. Redirecting `System.in` and `System.out` in Automated Unit Tests
When testing CLI-based interactive methods, never prompt the developer during a continuous integration (CI) build. Use stream redirection:

```java
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleInteractionTest {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        // CRITICAL: Always restore original system streams to prevent test pollution!
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void testUserPrompt() {
        // Simulate simulated user typing "42\n"
        String simulatedInput = "42" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        Scanner scanner = new Scanner(System.in);
        int result = scanner.nextInt();

        assertEquals(42, result);
    }
}
```

---

### 8.2 Common I/O Exceptions and Root Cause Analysis

| Exception Class | Exact Trigger Condition | Root Cause Analysis | Corrective Action |
| :--- | :--- | :--- | :--- |
| **`FileNotFoundException`** | `new FileInputStream(file)` fails | File path does not exist, points to a directory, or OS denied read permissions. | Verify path resolution (`Path.toAbsolutePath()`); check file permissions. |
| **`NoSuchFileException`** | NIO.2 `Files.newInputStream(path)` fails | Modern NIO equivalent of `FileNotFoundException`. | Ensure parent directories exist before creating files; verify working directory. |
| **`InputMismatchException`** | `scanner.nextInt()` receives non-digits | Next token does not conform to the expected lexical type or regex. | Guard with `hasNextInt()`; check `Scanner.useLocale()`. |
| **`NoSuchElementException`** | `scanner.next()` on exhausted stream | Scanner attempted to read past End-Of-File (EOF) or an empty input stream. | Always check `scanner.hasNext()` or `scanner.hasNextLine()` before calling `next*()`. |
| **`IllegalStateException`** | `scanner.nextInt()` on closed scanner | Attempted read operation on a `Scanner` after its `close()` method was called. | Restructure code to keep scanner open; avoid closing `System.in`. |
| **`IllegalFormatConversionException`** | `printf("%d", 3.14)` | Format conversion character does not correspond to argument type. | Align specifier with data type (`%f` for floating-point). |
| **`MissingFormatArgumentException`** | `printf("%s %s", "OnlyOne")` | Format string specifies more format specifiers than provided arguments. | Ensure arguments match specifier count. |
| **`CharacterCodingException` / `MalformedInputException`** | `CharsetDecoder.decode()` | Byte sequence is invalid according to the specified Charset (e.g., non-UTF-8 bytes). | Verify producer encoding; specify `CodingErrorAction.REPLACE` if loss is acceptable. |

---

### 8.3 JVM Inspection and Diagnostics for I/O Operations

When diagnosing production issues such as hanging socket connections, high CPU utilization in I/O loops, or `Too many open files` errors, leverage low-level JVM diagnostics.

#### 1. Tracking Open File Descriptors at the OS Level
On Linux / macOS:
```bash
# Find JVM Process ID
jps -v

# List all open file descriptors held by the JVM
lsof -p <PID> | grep -E 'REG|CHR|FIFO|IPv4|IPv6'

# Count total open descriptors
lsof -p <PID> | wc -l

# Check operating system per-process limit
ulimit -n
```
If the count continuously increases without dropping, your application has a resource leak where `close()` is not being invoked.

#### 2. Investigating Hanging Threads with `jcmd`
If your application hangs during an I/O operation:
```bash
jcmd <PID> Thread.print
```
Inspect the stack traces for threads blocked on native socket or file calls:
```text
"Worker-Thread-1" #24 prio=5 os_prio=0 cpu=12.45ms elapsed=420.12s tid=0x00007f9c84001800 nid=0x1a03 runnable [0x00007f9c8d5fe000]
   java.lang.Thread.State: RUNNABLE
        at java.io.FileInputStream.readBytes(java.base@21.0.1/Native Method)
        at java.io.FileInputStream.read(java.base@21.0.1/FileInputStream.java:287)
        at java.io.BufferedInputStream.fill(java.base@21.0.1/BufferedInputStream.java:255)
        at java.io.BufferedInputStream.read(java.base@21.0.1/BufferedInputStream.java:274)
```
Notice `java.lang.Thread.State: RUNNABLE` while inside `FileInputStream.readBytes (Native Method)`. In the HotSpot JVM, a thread blocked on a synchronous native read system call is reported by the JVM thread state as `RUNNABLE`, because as far as the JVM scheduler is concerned, the thread has yielded control to the OS kernel and is awaiting return.

#### 3. Analyzing I/O Events with JDK Flight Recorder (JFR)
JDK Flight Recorder introduces virtually zero overhead (< 1% in production) and captures detailed file and network read/write events:
```bash
# Start a 60-second diagnostic recording
jcmd <PID> JFR.start duration=60s filename=io_profile.jfr settings=profile
```
Open `io_profile.jfr` in **JDK Mission Control (JMC)**. Inspect the **File I/O** and **Socket I/O** tabs:
* Identifies slowest I/O operations (duration > 20ms).
* Highlights threads spending the highest percentage of time in blocking I/O calls.
* Lists the exact file paths causing disk I/O bottlenecks.

---

## SUMMARY CHECKLIST: THE SENIOR ENGINEER'S I/O MANIFESTO

1. **Guard with try-with-resources:** Every `AutoCloseable` resource must be declared in a try-with-resources header.
2. **Buffer every stream:** Never read or write byte-by-byte or char-by-char. Use `BufferedReader`, `BufferedWriter`, or direct buffers.
3. **Specify Charset explicitly:** Always pass `StandardCharsets.UTF_8`. Never allow the host operating system to determine encoding.
4. **Confine `Scanner` to simple CLIs:** Never use `Scanner` for bulk file processing or high-throughput network streams.
5. **Never close `System.in`:** Standard input is a process singleton. Closing it permanently destroys descriptor 0.
6. **Protect sensitive data with `char[]`:** Never read passwords into `String`. Use `System.console().readPassword()` and zero out the buffer immediately.
7. **Adopt modern NIO.2 APIs:** Replace `java.io.File` with `Path` and `Files` (`Files.readString`, `Files.writeString`, `Files.lines`).
8. **Format portably:** Always use `%n` or `System.lineSeparator()`. Never hardcode `\n` in output format strings.
9. **Validate user tokens defensively:** Call `hasNextInt()` before `nextInt()`, and always consume invalid tokens on error.
10. **Flush explicitly when buffering:** Ensure network streams and non-autoflushing writers are flushed to prevent protocol deadlocks.
