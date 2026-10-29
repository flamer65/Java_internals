# Module 8: Input and Output — Volume 6 (Assessment)

**Target:** Java 21 LTS | IntelliJ IDEA  
**Author:** Senior Java Engineer, JVM Engineer & OpenJDK Compiler Engineer  

---

## LEVEL 1: MCQs (20 Questions)

### Q1.1
What is the default delimiter pattern used by `java.util.Scanner` when tokenizing an input stream?
A) Any single comma or semicolon (`,|;`)  
B) Any sequence of whitespace characters matching the regular expression `\p{javaWhitespace}+`  
C) Exactly one newline character (`\n` or `\r\n`)  
D) Any non-alphanumeric ASCII character matching `[^a-zA-Z0-9]`  

---

### Q1.2
Consider the following code snippet:
```java
Scanner scanner = new Scanner("42 100\nJava Mastery");
int a = scanner.nextInt();
String line = scanner.nextLine();
System.out.println("a=" + a + ", line='" + line + "'");
```
What is the exact output printed to the console?
A) `a=42, line='100'`  
B) `a=42, line=' 100'`  
C) `a=42, line='Java Mastery'`  
D) `a=42, line=''`  

---

### Q1.3
What is the default internal character buffer size of `java.io.BufferedReader` when initialized via `new BufferedReader(reader)` without specifying a capacity?
A) 1024 characters  
B) 4096 characters  
C) 8192 characters  
D) 16384 characters  

---

### Q1.4
In the `printf` / `String.format` conversion specifier syntax `%[argument_index$][flags][width][.precision]conversion`, which component is MANDATORY?
A) `flags`  
B) `width`  
C) `precision`  
D) `conversion`  

---

### Q1.5
Why is the format specifier `%n` strongly preferred over literal `\n` in `System.out.printf` format strings?
A) `%n` is faster because the JVM optimizes it into a native CPU instruction.  
B) `%n` emits the host platform's specific line separator (`\r\n` on Windows, `\n` on Unix/Linux/macOS), whereas `\n` always emits a literal LF (`0x0A`).  
C) `%n` automatically flushes the underlying `PrintStream` buffer, whereas `\n` does not.  
D) `\n` is illegal in format strings and causes `UnknownFormatConversionException`.  

---

### Q1.6
What is the return value of `System.console()` when the Java Virtual Machine is launched without an attached interactive terminal (for example, inside IntelliJ IDEA's default Run window, inside Docker, or via a CI/CD background daemon)?
A) An instance of `NullConsole` that discards output  
B) A `Console` instance bound to `System.in` and `System.out`  
C) `null`  
D) Throws `UnsupportedOperationException`  

---

### Q1.7
Why does `java.io.Console.readPassword()` return a `char[]` rather than a `java.lang.String`?
A) Strings cannot contain non-ASCII characters entered during password input.  
B) `char[]` avoids loading the `String` class into JVM Metaspace.  
C) Strings are immutable and interned in heap memory, preventing application code from wiping sensitive credentials from RAM; `char[]` can be immediately zeroed (`Arrays.fill(pwd, '0')`).  
D) Operating system terminal APIs require C-style character arrays.  

---

### Q1.8
What occurs when an application executes `scanner.close()` on a `Scanner` instance that was constructed via `new Scanner(System.in)`?
A) Only the `Scanner` object's internal buffer is cleared; `System.in` remains open and readable.  
B) The underlying `System.in` stream is closed, permanently disabling standard input reads for the remainder of the JVM process.  
C) An `UnsupportedOperationException` is thrown because `System.in` is protected by the JVM.  
D) `System.in` is automatically reopened upon the next attempt to read.  

---

### Q1.9
Under what condition does `System.out` (a `PrintStream` configured with auto-flush) automatically flush its internal buffer?
A) Only when `System.out.flush()` is explicitly invoked by user code.  
B) Whenever a byte array is written, or when a newline character (`\n`) is written via `println()` or `printf()`.  
C) Automatically every 500 milliseconds via a background daemon thread.  
D) Whenever the internal buffer exceeds 256 bytes.  

---

### Q1.10
What is the behavioral contract of `Scanner.hasNextInt()`?
A) It reads the next integer from the stream and stores it in an internal cache.  
B) It checks if the next token can be interpreted as an `int` without consuming any characters from the input stream.  
C) It blocks until the user presses Enter, regardless of whether characters have been typed.  
D) It skips non-integer tokens until a valid integer is encountered.  

---

### Q1.11
What does `InputStream.read()` return when the end of the stream (EOF) is encountered?
A) `0`  
B) `null`  
C) `-1`  
D) Throws `EOFException`  

---

### Q1.12
Starting in Java 18 (JEP 400), what is the default character set used by standard I/O readers and writers (`FileReader`, `InputStreamReader`, etc.) if none is explicitly specified?
A) Platform-dependent OS charset (`windows-1252` on Windows, `UTF-8` on Linux)  
B) `US-ASCII`  
C) `UTF-8` unconditionally across all platforms  
D) `UTF-16`  

---

### Q1.13
How does the Java 15 instance method `"Value: %d".formatted(42)` differ internally from `String.format("Value: %d", 42)`?
A) `.formatted()` bypasses `java.util.Formatter` and uses direct bytecode concatenation.  
B) `.formatted()` is a convenience instance method that delegates directly to `String.format(this, args)`.  
C) `.formatted()` works only on multiline Text Blocks and cannot be called on standard string literals.  
D) `.formatted()` executes at compile-time via an annotation processor.  

---

### Q1.14
Suppose the host system's default locale is `Locale.GERMANY`. What occurs when executing:
```java
Scanner sc = new Scanner("10.50");
double val = sc.nextDouble();
```
A) It parses `val = 10.50` successfully because period is the universal decimal point.  
B) It throws `InputMismatchException` because German locale expects a comma (`,`) as the decimal separator.  
C) It ignores the decimal and parses `val = 1050.0`.  
D) It converts the number to integer `10`.  

---

### Q1.15
What is the result of evaluating `String.format("%b", "false")`?
A) `"false"`  
B) `"true"`  
C) Throws `IllegalFormatConversionException`  
D) `""`  

---

### Q1.16
Which statement regarding `PrintStream` error handling is correct?
A) `PrintStream` methods like `print()` and `println()` declare `throws IOException`.  
B) `PrintStream` methods catch internal `IOException` instances and set an internal error flag, which can be queried via `checkError()`.  
C) `PrintStream` converts all `IOException` instances into unchecked `RuntimeException`s.  
D) `PrintStream` terminates the JVM upon encountering an I/O error.  

---

### Q1.17
What is the behavior of `BufferedReader.lines()`?
A) It eagerly reads the entire file into an in-memory `ArrayList<String>`.  
B) It returns a lazily populated `Stream<String>` that reads lines from the underlying `BufferedReader` on demand as terminal stream operations pull elements.  
C) It automatically closes the underlying reader as soon as the stream pipeline is constructed.  
D) It can only be consumed sequentially and throws an exception if `.parallel()` is invoked.  

---

### Q1.18
What exception is thrown by `System.out.printf("%d", 3.14159)`?
A) `NumberFormatException`  
B) `ClassCastException`  
C) `IllegalFormatConversionException`  
D) Compiles and truncates to `3`  

---

### Q1.19
What does the argument index format specifier `%<s` signify in `System.out.printf("%s %<S", "duke")`?
A) It formats the argument immediately following the current argument.  
B) It formats the first argument in the argument list.  
C) It re-uses the argument that was consumed by the previous format specifier.  
D) It indicates that the argument should be aligned to the left edge of the terminal.  

---

### Q1.20
Which method call atomically redirects the standard output stream for the entire JVM process?
A) `System.out = new PrintStream(baos);`  
B) `System.setOut(new PrintStream(baos));`  
C) `System.redirectOut(new PrintStream(baos));`  
D) `System.setProperty("sys.stdout", baos.toString());`  

---

### LEVEL 1 ANSWERS & DEEP EXPLANATIONS

1. **B** — JLS & OpenJDK `java.util.Scanner`: The default delimiter is defined by the regular expression `\p{javaWhitespace}+`, which matches any Unicode code point where `Character.isWhitespace(c)` returns true (spaces, tabs, newlines, carriage returns, form feeds).
2. **B** — When `scanner.nextInt()` reads `42`, it stops immediately after the characters `4` and `2`. The input pointer is now at the space preceding `100`. When `scanner.nextLine()` is subsequently called, it reads everything from the current cursor position up to the newline character. Thus, it captures `" 100"`.
3. **C** — OpenJDK `java.io.BufferedReader`: The default buffer size constant `defaultCharBufferSize` is explicitly declared as `8192` characters (16 KB in UTF-16 representation), designed to align with common OS disk page cache block sizes.
4. **D** — The conversion character (such as `d`, `f`, `s`, `c`, `b`, `%`, `n`) is the only mandatory component in a format specifier. All other components (argument index, flags, width, precision) are optional.
5. **B** — A hardcoded `\n` in a string literal always translates to ASCII byte `0x0A` (LF). On Windows platforms, the standard line terminator is CRLF (`0x0D 0x0A`). `%n` dynamically queries `System.lineSeparator()`, guaranteeing platform compliance across Windows, Linux, and macOS.
6. **C** — `System.console()` queries the operating system for an attached TTY/PTY interactive device. In non-interactive environments (IDEs running through redirected subprocess pipes, background systemd services, CI/CD runners), no TTY is attached, and the JVM returns `null`.
7. **C** — Java `String` instances are immutable and stored in the heap; their underlying byte array cannot be overwritten and remains in memory until garbage collection and compaction occurs. A `char[]` is mutable, enabling security-conscious developers to immediately overwrite the sensitive data with zeroes (`Arrays.fill(pwd, '\0')`).
8. **B** — `Scanner.close()` delegates down its stream wrapper chain and invokes `close()` on the underlying `Readable` / `InputStream`. Because `System.in` is an OS-level file descriptor (FD 0), closing it permanently invalidates the descriptor for the JVM process lifecycle.
9. **B** — In `PrintStream`, when initialized with `autoFlush = true` (which `System.out` is by default), flushing occurs whenever a byte array is written, one of the `println()` methods is invoked, or a newline character (`\n`) is written in a byte sequence.
10. **B** — `hasNextInt()` performs lookahead tokenization: it scans the input buffer to determine if the next token can be successfully parsed into an integer, without advancing the scanner's reading cursor.
11. **C** — `InputStream.read()` returns an `int` representing an unsigned byte (`0` to `255`), or `-1` to signal End Of File (EOF).
12. **C** — JEP 400 (JDK 18+) designated `UTF-8` as the universal default charset across all operating systems and runtimes, eliminating historical inconsistencies where Windows defaulted to `windows-1252` and Linux to `UTF-8`.
13. **B** — In Java 15+, `String.formatted(Object... args)` is defined simply as: `public String formatted(Object... args) { return String.format(this, args); }`. It provides clean, fluent syntax, especially when chaining on multiline Text Blocks.
14. **B** — `Scanner` is locale-aware and uses `Locale.getDefault(Locale.Category.FORMAT)` by default. In `Locale.GERMANY`, numbers use comma `,` as the decimal mark; encountering `.` causes `InputMismatchException`.
15. **B** — In `java.util.Formatter`, the `%b` specifier checks: if the argument is `null`, the result is `"false"`. If the argument is a `Boolean` or `boolean`, the result is the string value of that boolean. If the argument is ANY other non-null Object, it unconditionally evaluates to `"true"`.
16. **B** — By design, `PrintStream` never throws checked `java.io.IOException` from its output methods (unlike `OutputStream`). Instead, any caught exception sets an internal boolean flag, inspectable via `System.out.checkError()`.
17. **B** — `BufferedReader.lines()` constructs a lazy stream backed by a `LinesSpliterator`. It pulls lines from the underlying reader only as elements are requested by downstream stream terminal operations.
18. **C** — The `%d` specifier requires an integer argument (such as `byte`, `short`, `int`, `long`, `BigInteger`). Passing a floating-point type (`float`, `double`, `Double`) violates the conversion contract and throws `java.util.IllegalFormatConversionException: d != java.lang.Double`.
19. **C** — The `<` flag in a format specifier causes the formatter to re-evaluate the argument consumed by the previous specifier, enabling multiple formatting styles on a single argument without repeating it in the argument list.
20. **B** — `System.setOut(PrintStream)` is a native method that updates the static final field `System.out` via internal JVM privileged access. Direct assignment (`System.out = ...`) fails at compile-time because `out` is declared `final`.

---

## LEVEL 2: TRUE/FALSE (20 Questions)

*Determine whether each statement is TRUE or FALSE. Deep explanations follow at the end of this level.*

### Q2.1
Invoking `scanner.close()` on a `Scanner` constructed with `System.in` will close `System.in`, preventing any subsequent `Scanner` or `Reader` from reading standard input in that JVM process.

---

### Q2.2
The expression `String.format("%b", "false")` evaluates to `"false"`.

---

### Q2.3
`System.console()` is guaranteed to return a non-null `Console` reference when executed inside IntelliJ IDEA's default Run tool window.

---

### Q2.4
`java.io.BufferedReader` is synchronized on an internal lock object and is therefore safe for concurrent read operations across multiple threads.

---

### Q2.5
`java.util.Scanner` is thread-safe and can be safely shared across multiple concurrent virtual threads without external synchronization.

---

### Q2.6
Using literal `\n` in `System.out.printf()` is guaranteed to produce platform-appropriate line endings on both Windows and Linux operating systems.

---

### Q2.7
Under Java 21 LTS (JEP 400), the default character set for `FileReader` and `InputStreamReader` is `UTF-8` regardless of operating system configuration.

---

### Q2.8
`PrintStream.println()` throws a checked `java.io.IOException` if the underlying output stream is closed or encounters a disk write error.

---

### Q2.9
`Scanner.nextInt()` consumes the trailing newline character generated when the user presses the Enter key.

---

### Q2.10
`Console.readPassword()` returns a `char[]` rather than a `String` to allow sensitive credentials to be immediately overwritten in memory.

---

### Q2.11
`System.out` and `System.err` share the same underlying operating system file descriptor.

---

### Q2.12
The format specifier `%s` throws a `NullPointerException` if passed a `null` argument reference.

---

### Q2.13
In `printf`, the format flag `0` (zero-padding) can be combined with the `-` (left-justified) flag in a single specifier, such as `%-010d`.

---

### Q2.14
`BufferedReader.readLine()` retains the trailing line-termination characters (`\n` or `\r\n`) in the returned `String`.

---

### Q2.15
`InputStream.readAllBytes()` introduced in Java 9 blocks until all bytes are read or end-of-stream (EOF) is reached.

---

### Q2.16
`Scanner.useLocale(Locale.GERMANY)` will expect `3,14` rather than `3.14` when parsing floating-point numbers.

---

### Q2.17
`Files.readString(Path)` is recommended for reading multi-gigabyte log files because it streams lines efficiently without exhausting JVM heap memory.

---

### Q2.18
`System.in.available()` is guaranteed to return the total size of the input stream when reading from standard input.

---

### Q2.19
The format specifier `%x` can format both primitive integers and floating-point numbers in hexadecimal format.

---

### Q2.20
`PrintStream.checkError()` flushes the stream buffer prior to checking and returning the error status.

---

### LEVEL 2 ANSWERS & DEEP EXPLANATIONS

1. **TRUE** — `Scanner.close()` closes its underlying `Readable` source. When wrapping `System.in`, closing it invalidates the OS file descriptor (FD 0), causing all subsequent reads from `System.in` to throw `NoSuchElementException` or return EOF immediately.
2. **FALSE** — `Formatter` rules state that any non-null Object that is not an instance of `java.lang.Boolean` evaluates to `"true"`. Because `"false"` is a non-null `String`, `String.format("%b", "false")` evaluates to `"true"`.
3. **FALSE** — The IntelliJ IDEA Run tool window redirects standard I/O through pipes rather than allocating a true pseudoterminal (PTY). Consequently, `System.console()` returns `null`.
4. **TRUE** — `BufferedReader` synchronizes all read operations on an internal lock object (`lock`), making concurrent reads thread-safe (though calling threads may read interleaved sequences of characters).
5. **FALSE** — `java.util.Scanner` is explicitly not thread-safe. Concurrent access without external synchronization will corrupt internal buffer pointers and match states.
6. **FALSE** — Literal `\n` emits byte `0x0A` (LF) regardless of OS. On Windows, standard lines terminate with `0x0D 0x0A` (CRLF). `%n` must be used for platform-appropriate line separation.
7. **TRUE** — JEP 400 in Java 18 established UTF-8 as the universal default charset for standard Java APIs across all platforms.
8. **FALSE** — `PrintStream` suppresses all checked `IOException`s. Callers must invoke `checkError()` to detect failures.
9. **FALSE** — `nextInt()` consumes only numeric digits matching integer syntax, leaving trailing delimiter characters (including the newline `\n`) in the input buffer.
10. **TRUE** — `char[]` is mutable, allowing immediate zero-filling (`Arrays.fill(pwd, '0')`) to prevent sensitive credentials from lingering in JVM heap dumps.
11. **FALSE** — `System.out` is connected to OS File Descriptor 1 (standard output), while `System.err` is connected to OS File Descriptor 2 (standard error).
12. **FALSE** — The `%s` conversion specifier converts `null` arguments to the literal string `"null"` without throwing `NullPointerException`.
13. **FALSE** — Combining `0` (zero-padding) with `-` (left-justification) is illegal and throws `java.util.IllegalFormatFlagsException` at runtime.
14. **FALSE** — `BufferedReader.readLine()` strips line-termination characters (`\n`, `\r`, or `\r\n`) and returns only the line content.
15. **TRUE** — `InputStream.readAllBytes()` reads all remaining bytes from the stream until EOF is reached, returning a single byte array.
16. **TRUE** — In German locale, comma (`,`) is the standard decimal mark. `Scanner.useLocale(Locale.GERMANY)` configures the numeric parser accordingly.
17. **FALSE** — `Files.readString()` loads the entire file into a single `String` object on the heap, which will trigger `OutOfMemoryError` on multi-gigabyte files.
18. **FALSE** — `InputStream.available()` returns an estimate of bytes that can be read *without blocking*. For interactive consoles, it typically returns `0`.
19. **FALSE** — `%x` / `%X` is strictly for integer types (`byte`, `short`, `int`, `long`, `BigInteger`). Formatting floating-point numbers in hexadecimal requires `%a` / `%A`.
20. **TRUE** — According to the OpenJDK specification, `PrintStream.checkError()` automatically flushes the stream before returning the internal error state.


---

## LEVEL 3: OUTPUT PREDICTION (20 Questions)

*Predict the exact console output of each code snippet without executing it. Line-by-line analyses and exact outputs follow at the end of this level.*

### Q3.1
```java
public class Trace3_1 {
    public static void main(String[] args) {
        System.out.printf("[%5d] [%-5d] [%05d]%n", 42, 42, 42);
    }
}
```

---

### Q3.2
```java
public class Trace3_2 {
    public static void main(String[] args) {
        System.out.printf("[%8.2f] [%-8.2f] [%+08.2f]%n", 3.14159, 3.14159, 3.14159);
    }
}
```

---

### Q3.3
```java
public class Trace3_3 {
    public static void main(String[] args) {
        System.out.printf("%.4s | %-6.3s%n", "Antigravity", "Java");
    }
}
```

---

### Q3.4
```java
public class Trace3_4 {
    public static void main(String[] args) {
        System.out.printf("%1$s %2$s %1$S %<s%n", "alpha", "beta");
    }
}
```

---

### Q3.5
```java
public class Trace3_5 {
    public static void main(String[] args) {
        String s = null;
        Object obj = "false";
        boolean flag = false;
        System.out.printf("%b %b %b%n", s, obj, flag);
    }
}
```

---

### Q3.6
```java
public class Trace3_6 {
    public static void main(String[] args) {
        String input = "10\n20\n30";
        Scanner sc = new Scanner(input);
        int a = sc.nextInt();
        String b = sc.nextLine();
        int c = sc.nextInt();
        System.out.println("a=" + a + ", b='" + b + "', c=" + c);
    }
}
```

---

### Q3.7
```java
public class Trace3_7 {
    public static void main(String[] args) {
        System.out.printf("%x %X %#x %#X%n", 255, 255, 255, 255);
    }
}
```

---

### Q3.8
```java
public class Trace3_8 {
    public static void main(String[] args) {
        System.out.printf(Locale.US, "%,12d | %,10.2f%n", 123456789L, 9876.5432);
    }
}
```

---

### Q3.9
```java
public class Trace3_9 {
    public static void main(String[] args) {
        System.out.printf("%(d | %(d | %(d%n", 100, -100, 0);
    }
}
```

---

### Q3.10
```java
public class Trace3_10 {
    public static void main(String[] args) {
        System.out.printf("%+d | %+d | % d | % d%n", 50, -50, 50, -50);
    }
}
```

---

### Q3.11
```java
public class Trace3_11 {
    public static void main(String[] args) {
        String data = "red:green,blue:yellow";
        Scanner sc = new Scanner(data).useDelimiter("[,:]");
        while (sc.hasNext()) {
            System.out.print(sc.next() + " ");
        }
        System.out.println();
    }
}
```

---

### Q3.12
```java
public class Trace3_12 {
    public static void main(String[] args) {
        System.out.printf("%c %c%n", 65, 'b');
    }
}
```

---

### Q3.13
```java
public class Trace3_13 {
    public static void main(String[] args) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, false);
        ps.print("Hello");
        System.out.print(baos.size() + " ");
        ps.println();
        System.out.print(baos.size() + " ");
        ps.flush();
        System.out.println(baos.size());
    }
}
```

---

### Q3.14
```java
public class Trace3_14 {
    public static void main(String[] args) {
        String formatted = """
            Value: %04d
            Name:  %-6s
            """.formatted(7, "Duke");
        System.out.print(formatted);
    }
}
```

---

### Q3.15
```java
public class Trace3_15 {
    public static void main(String[] args) {
        double val = 1234.5;
        System.out.printf(Locale.GERMANY, "%.2f%n", val);
    }
}
```

---

### Q3.16
```java
public class Trace3_16 {
    public static void main(String[] args) {
        String data = "One 10 Two 20 Three 30";
        Scanner sc = new Scanner(data);
        int sum = 0;
        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                sum += sc.nextInt();
            } else {
                sc.next();
            }
        }
        System.out.println("sum=" + sum);
    }
}
```

---

### Q3.17
```java
public class Trace3_17 {
    public static void main(String[] args) {
        System.out.printf("%e | %.2E%n", 12345.678, 12345.678);
    }
}
```

---

### Q3.18
```java
public class Trace3_18 {
    public static void main(String[] args) {
        System.out.printf("[%1$5s] [%1$-5s]%n", "hi");
    }
}
```

---

### Q3.19
```java
public class Trace3_19 {
    public static void main(String[] args) {
        String input = "100 false null 42.5";
        Scanner sc = new Scanner(input);
        System.out.print(sc.nextInt() + " ");
        System.out.print(sc.nextBoolean() + " ");
        System.out.print(sc.next() + " ");
        System.out.println(sc.nextDouble());
    }
}
```

---

### Q3.20
```java
public class Trace3_20 {
    public static void main(String[] args) {
        System.out.printf("%% %n %%%n");
    }
}
```

---

### LEVEL 3 ANSWERS & LINE-BY-LINE ANALYSIS

1. **Output:** `[   42] [42   ] [00042]`  
   *Analysis:* `%5d` right-aligns `42` in 5 spaces. `%-5d` left-aligns in 5 spaces. `%05d` pads with leading zeros to 5 digits.
2. **Output:** `[    3.14] [3.14    ] [+0003.14]`  
   *Analysis:* `%8.2f` right-aligns `3.14` (4 chars) with 4 leading spaces. `%-8.2f` left-aligns with 4 trailing spaces. `%+08.2f` includes the plus sign `+` and pads with zeros to width 8 (`+0003.14`).
3. **Output:** `Anti | Jav   `  
   *Analysis:* `%.4s` truncates "Antigravity" to 4 characters (`Anti`). `%-6.3s` truncates "Java" to 3 characters (`Jav`) and left-aligns in a 6-space field (`Jav   `).
4. **Output:** `alpha beta ALPHA ALPHA`  
   *Analysis:* `%1$s` is "alpha", `%2$s` is "beta", `%1$S` converts "alpha" to uppercase "ALPHA", and `%<s` reuses the previous argument ("ALPHA").
5. **Output:** `false true false`  
   *Analysis:* `%b` on `null` produces `false`. `%b` on non-null String `"false"` produces `true`. `%b` on boolean `false` produces `false`.
6. **Output:** `a=10, b='', c=20`  
   *Analysis:* `sc.nextInt()` consumes `10`. The input cursor sits before `\n`. `sc.nextLine()` immediately reads to the end of the line, capturing empty string `""`. Next `sc.nextInt()` consumes `20`.
7. **Output:** `ff FF 0xff 0XFF`  
   *Analysis:* `%x` is lowercase hex (`ff`). `%X` is uppercase (`FF`). `%#x` includes radix prefix (`0xff`). `%#X` includes uppercase prefix (`0XFF`).
8. **Output:** ` 123,456,789 |   9,876.54`  
   *Analysis:* `%,12d` produces 11 chars with commas plus 1 leading space. `%,10.2f` formats 8 chars plus 2 leading spaces.
9. **Output:** `100 | (100) | 0`  
   *Analysis:* The `(` flag encloses negative numbers in parentheses. Positive numbers and zero are formatted normally without parentheses.
10. **Output:** `+50 | -50 |  50 | -50`  
    *Analysis:* `%+d` always includes sign (`+` or `-`). `% d` (space flag) includes a leading space for positive numbers and `-` for negative.
11. **Output:** `red green blue yellow `  
    *Analysis:* The regex `[,:]` matches either colon or comma, yielding the four tokens.
12. **Output:** `A b`  
    *Analysis:* Integer `65` is mapped to ASCII character `'A'`. Character `'b'` is printed as `'b'`.
13. **Output:** `0 0 7` (or `0 0 6` depending on platform line ending length)  
    *Analysis:* `PrintStream` is created with `autoflush=false`. Neither `print` nor `println` flushes bytes to the underlying stream. Only `ps.flush()` flushes the internal buffered 7 bytes ("Hello\r\n" or "Hello\n").
14. **Output:**  
    ```text
    Value: 0007
    Name:  Duke  
    ```  
    *Analysis:* Text block preserves indentation; `%04d` formats `0007`, `%-6s` formats `Duke  `.
15. **Output:** `1234,50`  
    *Analysis:* In German locale, the decimal separator is a comma `,`.
16. **Output:** `sum=60`  
    *Analysis:* `hasNextInt()` matches `10`, `20`, and `30`. Non-integer tokens ("One", "Two", "Three") are skipped via `sc.next()`. Sum is `10 + 20 + 30 = 60`.
17. **Output:** `1.234568e+04 | 1.23E+04`  
    *Analysis:* Computerized scientific notation. `%e` defaults to 6 decimal places with rounding.
18. **Output:** `[   hi] [hi   ]`  
    *Analysis:* Reuses argument 1 twice; first right-aligned with width 5, then left-aligned with width 5.
19. **Output:** `100 false null 42.5`  
    *Analysis:* Reads int `100`, boolean `false`, string `"null"`, and double `42.5`.
20. **Output:**  
    ```text
    % 
     %
    ```  
    *Analysis:* `%%` escapes literal percent sign. `%n` inserts platform newline.

---

## LEVEL 4: FIND THE COMPILER ERROR (20 Questions)

*Identify the exact compile-time error and the reason `javac` rejects each snippet. Detailed explanations follow at the end of this level.*

### Q4.1
```java
public class CompError4_1 {
    public static void main(String[] args) {
        FileReader fr = new FileReader("config.txt"); // Line 3
        System.out.println("Opened");
    }
}
```

---

### Q4.2
```java
public class CompError4_2 {
    public static void main(String[] args) {
        Console console = new Console(); // Line 3
        console.printf("Hello");
    }
}
```

---

### Q4.3
```java
public class CompError4_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String pass = sc.readPassword(); // Line 4
    }
}
```

---

### Q4.4
```java
public class CompError4_4 {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(System.in); // Line 3
    }
}
```

---

### Q4.5
```java
public class CompError4_5 {
    public static void main(String[] args) {
        Console console = System.console();
        if (console != null) {
            String password = console.readPassword("Enter: "); // Line 5
        }
    }
}
```

---

### Q4.6
```java
public class CompError4_6 {
    public static void main(String[] args) {
        System.out = new PrintStream(new ByteArrayOutputStream()); // Line 3
    }
}
```

---

### Q4.7
```java
public class CompError4_7 {
    public static void main(String[] args) {
        String content = Files.readString("data.txt"); // Line 3
    }
}
```

---

### Q4.8
```java
public class CompError4_8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner("10,20,30");
        sc.useDelimiter(','); // Line 4
    }
}
```

---

### Q4.9
```java
public class CompError4_9 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int age = sc.nextDouble(); // Line 4
    }
}
```

---

### Q4.10
```java
public class CompError4_10 {
    public static void main(String[] args) {
        String line = System.in.readLine(); // Line 3
    }
}
```

---

### Q4.11
```java
public class CompError4_11 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (sc) {
            sc = new Scanner("foo"); // Line 5
            System.out.println(sc.next());
        }
    }
}
```

---

### Q4.12
```java
public class CompError4_12 {
    public static void main(String[] args) {
        InputStreamReader isr = new InputStreamReader(System.in, "UTF-99"); // Line 3
    }
}
```

---

### Q4.13
```java
public class CompError4_13 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int val = sc.readInt(); // Line 4
    }
}
```

---

### Q4.14
```java
public class CompError4_14 {
    public static void main(String[] args) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(baos); // Line 4
    }
}
```

---

### Q4.15
```java
public class CompError4_15 {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new StringReader("a\nb"));
        List<String> list = br.lines().toList(); // Line 4
        // Unhandled checked exception on closing?
        br.close(); // Line 6
    }
}
```

---

### Q4.16
```java
public class CompError4_16 {
    public static void main(String[] args) {
        PrintStream ps = System.out;
        int b = ps.read(); // Line 4
    }
}
```

---

### Q4.17
```java
public class CompError4_17 {
    public static void main(String[] args) {
        String s = "Hello \c World"; // Line 3
        System.out.println(s);
    }
}
```

---

### Q4.18
```java
public class CompError4_18 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            int input = sc.nextInt();
            int input = 10; // Line 6
        }
    }
}
```

---

### Q4.19
```java
public class CompError4_19 {
    public static void main(String[] args) {
        AutoCloseable ac = new Scanner(System.in);
        ac.close(); // Line 4
    }
}
```

---

### Q4.20
```java
public class CompError4_20 {
    public static void main(String[] args) {
        PrintStream ps = new PrintStream(); // Line 3
    }
}
```

---

### LEVEL 4 ANSWERS & ERROR EXPLANATIONS

1. **Line 3:** `unreported exception FileNotFoundException; must be caught or declared to be thrown`. `FileReader(String)` declares checked exception `FileNotFoundException`.
2. **Line 3:** `Console() has private access in java.io.Console`. `Console` has a private constructor; instances must be obtained via `System.console()`.
3. **Line 4:** `cannot find symbol: method readPassword() in class Scanner`. `readPassword()` is a method on `java.io.Console`, not `java.util.Scanner`.
4. **Line 3:** `no suitable constructor found for BufferedReader(InputStream)`. `BufferedReader` accepts a character `Reader`, not an `InputStream`. It must be wrapped in `new InputStreamReader(System.in)`.
5. **Line 5:** `incompatible types: char[] cannot be converted to String`. `Console.readPassword()` returns `char[]`, which cannot be assigned directly to a `String`.
6. **Line 3:** `cannot assign a value to final variable out`. `System.out` is declared as `public static final PrintStream out;`. Redirection must be performed via `System.setOut(PrintStream)`.
7. **Line 3:** `no suitable method found for readString(String)`. `Files.readString()` requires a `java.nio.file.Path` object (e.g., `Path.of("data.txt")`), and also throws checked `IOException`.
8. **Line 4:** `no suitable method found for useDelimiter(char)`. `useDelimiter()` accepts either a `java.lang.String` (regex) or `java.util.regex.Pattern`, not a primitive `char`. Use `","`.
9. **Line 4:** `incompatible types: possible lossy conversion from double to int`. `sc.nextDouble()` returns a `double` which cannot be assigned to an `int` without an explicit cast.
10. **Line 3:** `cannot find symbol: method readLine() in class InputStream`. `System.in` is an `InputStream` (raw byte stream) and has no `readLine()` method.
11. **Line 5:** `cannot assign a value to final variable sc`. A variable used in a try-with-resources statement is implicitly or explicitly `final` and cannot be reassigned.
12. **Line 3:** `unreported exception UnsupportedEncodingException; must be caught or declared to be thrown`. When passing a string charset name to `InputStreamReader`, it declares checked `UnsupportedEncodingException`. (Use `StandardCharsets.UTF_8` to avoid checked exceptions).
13. **Line 4:** `cannot find symbol: method readInt() in class Scanner`. The correct method is `nextInt()`.
14. **Line 4:** `incompatible types: ByteArrayOutputStream cannot be converted to PrintStream`. `System.setOut()` requires a `PrintStream` argument (`new PrintStream(baos)`).
15. **Line 6:** `unreported exception IOException; must be caught or declared to be thrown`. `Reader.close()` declares checked `IOException`.
16. **Line 4:** `cannot find symbol: method read() in class PrintStream`. `PrintStream` is an output stream; it does not have a `read()` method.
17. **Line 3:** `illegal escape character in string literal`. `\c` is not a valid Java character escape sequence.
18. **Line 6:** `variable input is already defined in method main(String[])`. Redefining `int input` in the same scope is prohibited.
19. **Line 4:** `unreported exception Exception; must be caught or declared to be thrown`. `AutoCloseable.close()` declares `throws Exception` (unlike `Closeable.close()` which declares `IOException`).
20. **Line 3:** `no suitable constructor found for PrintStream()`. `PrintStream` does not provide a no-argument constructor; it requires a destination target (`OutputStream`, `File`, `String` path).


---

## LEVEL 5: FIND THE RUNTIME ERROR (20 Questions)

*Identify the exact runtime exception (including class name) that will be thrown when executing each code snippet. Detailed exception analyses follow at the end of this level.*

### Q5.1
```java
public class RunError5_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner("Antigravity");
        int num = sc.nextInt(); // Line 4
    }
}
```

---

### Q5.2
```java
public class RunError5_2 {
    public static void main(String[] args) {
        Console console = System.console();
        // Running inside a headless test runner where console is null
        console.printf("Welcome!"); // Line 5
    }
}
```

---

### Q5.3
```java
public class RunError5_3 {
    public static void main(String[] args) {
        double balance = 50.25;
        System.out.printf("Balance: %d%n", balance); // Line 4
    }
}
```

---

### Q5.4
```java
public class RunError5_4 {
    public static void main(String[] args) {
        System.out.printf("%s %s %s%n", "Alpha", "Beta"); // Line 3
    }
}
```

---

### Q5.5
```java
public class RunError5_5 {
    public static void main(String[] args) {
        System.out.printf("%q%n", 42); // Line 3
    }
}
```

---

### Q5.6
```java
public class RunError5_6 {
    public static void main(String[] args) {
        System.out.printf("%-010d%n", 42); // Line 3
    }
}
```

---

### Q5.7
```java
public class RunError5_7 {
    public static void main(String[] args) {
        System.out.printf("%.2d%n", 42); // Line 3
    }
}
```

---

### Q5.8
```java
public class RunError5_8 {
    public static void main(String[] args) {
        System.out.printf("%++d%n", 42); // Line 3
    }
}
```

---

### Q5.9
```java
public class RunError5_9 {
    public static void main(String[] args) {
        Scanner sc = new Scanner("Hello");
        sc.next();
        sc.next(); // Line 5
    }
}
```

---

### Q5.10
```java
public class RunError5_10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner("Hello");
        sc.close();
        sc.next(); // Line 5
    }
}
```

---

### Q5.11
```java
public class RunError5_11 {
    public static void main(String[] args) {
        int val = Integer.parseInt(" 42 "); // Line 3
    }
}
```

---

### Q5.12
```java
public class RunError5_12 {
    public static void main(String[] args) {
        System.out.printf("%,b%n", true); // Line 3
    }
}
```

---

### Q5.13
```java
public class RunError5_13 {
    public static void main(String[] args) {
        System.out.printf(Locale.GERMANY, "%.2f%n", Double.parseDouble("12.34"));
        Scanner sc = new Scanner("12.34").useLocale(Locale.GERMANY);
        double d = sc.nextDouble(); // Line 5
    }
}
```

---

### Q5.14
```java
public class RunError5_14 {
    public static void main(String[] args) {
        System.out.printf((String) null, 42); // Line 3
    }
}
```

---

### Q5.15
```java
public class RunError5_15 {
    public static void main(String[] args) {
        System.out.printf("%0$s%n", "Invalid"); // Line 3
    }
}
```

---

### Q5.16
```java
public class RunError5_16 {
    public static void main(String[] args) {
        Scanner sc = new Scanner("test");
        sc.useDelimiter("[a-"); // Line 4
        sc.next();
    }
}
```

---

### Q5.17
```java
public class RunError5_17 {
    public static void main(String[] args) {
        Scanner sc = new Scanner("");
        String line = sc.nextLine(); // Line 4
    }
}
```

---

### Q5.18
```java
public class RunError5_18 {
    public static void main(String[] args) {
        System.out.printf("%#d%n", 42); // Line 3
    }
}
```

---

### Q5.19
```java
public class RunError5_19 {
    public static void main(String[] args) {
        System.out.printf("%-f%n", 3.14); // Line 3
    }
}
```
*Wait: Is `%-f` a runtime error? Check flag rules: `-` left justifies, but width is missing? Let's check: `%<s` without prior argument:*
```java
public class RunError5_19_Fix {
    public static void main(String[] args) {
        System.out.printf("%<s%n", "orphan"); // Line 3
    }
}
```

---

### Q5.20
```java
public class RunError5_20 {
    public static void main(String[] args) {
        ByteArrayInputStream bais = new ByteArrayInputStream("abc".getBytes());
        bais.read(new byte[10], 5, 10); // Line 4
    }
}
```

---

### LEVEL 5 ANSWERS & RUNTIME ERROR ANALYSIS

1. **Line 4:** `java.util.InputMismatchException`. The next token is `"Antigravity"`, which cannot be parsed as an integer.
2. **Line 5:** `java.lang.NullPointerException: Cannot invoke "java.io.Console.printf(String, Object[])" because "console" is null`. In headless environments, `System.console()` returns `null`.
3. **Line 4:** `java.util.IllegalFormatConversionException: d != java.lang.Double`. The `%d` specifier does not accept floating-point arguments.
4. **Line 3:** `java.util.MissingFormatArgumentException: Format specifier '%s'`. Format string expects 3 arguments, but only 2 were provided.
5. **Line 3:** `java.util.UnknownFormatConversionException: Conversion = 'q'`. The conversion character `'q'` is not a recognized format specifier.
6. **Line 3:** `java.util.IllegalFormatFlagsException: Flags = '-0'`. The `-` (left-align) and `0` (zero-pad) flags are mutually exclusive.
7. **Line 3:** `java.util.IllegalFormatPrecisionException: 2`. Precision is not supported for integer `%d` specifiers.
8. **Line 3:** `java.util.DuplicateFormatFlagsException: Flags = '+'`. Repeating the same flag throws duplicate format flags exception.
9. **Line 5:** `java.util.NoSuchElementException`. The single token `"Hello"` was already consumed; no more tokens exist.
10. **Line 5:** `java.lang.IllegalStateException: Scanner closed`. Invoking `next()` on a closed scanner is illegal.
11. **Line 3:** `java.lang.NumberFormatException: For input string: " 42 "`. `Integer.parseInt()` does not trim leading or trailing whitespace.
12. **Line 3:** `java.util.FormatFlagsConversionMismatchException: Conversion = b, Flags = ,`. The comma grouping flag `,` is invalid for boolean conversions.
13. **Line 5:** `java.util.InputMismatchException`. In `Locale.GERMANY`, decimal separator is `,`. Encounting `.` causes parsing to fail.
14. **Line 3:** `java.lang.NullPointerException: Format string cannot be null`. Passing `null` format string to `printf` throws NPE.
15. **Line 3:** `java.util.IllegalFormatFlagsException` / `java.util.UnknownFormatConversionException` (or `MissingFormatArgumentException: Format specifier '%0$s'`). Argument indices are 1-based; index `0$` is illegal.
16. **Line 4:** `java.util.regex.PatternSyntaxException: Illegal character range near index 3 [a-`. Invalid regex syntax in `useDelimiter`.
17. **Line 4:** `java.util.NoSuchElementException: No line found`. Calling `nextLine()` on an empty stream throws `NoSuchElementException`.
18. **Line 3:** `java.util.FormatFlagsConversionMismatchException: Conversion = d, Flags = #`. The `#` (alternate form) flag is not valid for decimal `%d`.
19. **Line 3:** `java.util.MissingFormatArgumentException: Format specifier '%<s'`. There is no prior argument to reference with `<`.
20. **Line 4:** `java.lang.IndexOutOfBoundsException`. Offset 5 plus length 10 equals 15, which exceeds array length 10.

---

## LEVEL 6: DEBUG THIS CODE (20 Questions)

*Diagnose the specific I/O bug in each snippet and provide the corrected code. Detailed code repairs follow at the end of this level.*

### Debug 6.1
```java
// Bug: Prompt for name is skipped when user enters age followed by Enter
public class Registration {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Age: ");
        int age = sc.nextInt();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.println("User: " + name + " (" + age + ")");
    }
}
```

---

### Debug 6.2
```java
// Bug: Second call to readNumber() crashes with NoSuchElementException
public class NumberPrompter {
    public static int readNumber(String msg) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print(msg);
            return sc.nextInt();
        }
    }
    public static void main(String[] args) {
        int x = readNumber("First: ");
        int y = readNumber("Second: ");
    }
}
```

---

### Debug 6.3
```java
// Bug: Output file is completely empty when program terminates
public class FastWriter {
    public static void main(String[] args) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));
        bw.write("Crucial Data Transaction 98765");
        // Program exits without closing or flushing bw
    }
}
```

---

### Debug 6.4
```java
// Bug: Infinite loop when user enters non-numeric input
public class AgeValidator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter age: ");
        while (!sc.hasNextInt()) {
            System.out.print("Invalid! Enter age again: ");
            // Missing token consumption
        }
        int age = sc.nextInt();
    }
}
```

---

### Debug 6.5
```java
// Bug: System.console() crashes on developer laptops running inside IntelliJ IDEA
public class LoginApp {
    public static void main(String[] args) {
        Console console = System.console();
        char[] pwd = console.readPassword("Password: ");
        System.out.println("Password entered.");
        Arrays.fill(pwd, '0');
    }
}
```

---

### Debug 6.6
```java
// Bug: Line endings do not match Windows standard when comparing files in CI/CD
public class CrossPlatformReport {
    public static void main(String[] args) {
        System.out.printf("HEADER\nROW 1\nROW 2\n");
    }
}
```

---

### Debug 6.7
```java
// Bug: Last item on each row fails to parse when using delimiter ","
public class CsvLineParser {
    public static void main(String[] args) {
        String data = "1,Alice,Engineer\n2,Bob,Architect";
        Scanner sc = new Scanner(data).useDelimiter(",");
        while (sc.hasNext()) {
            System.out.println("Token: " + sc.next());
        }
    }
}
```

---

### Debug 6.8
```java
// Bug: Sensitive password remains on the JVM heap for hours after execution
public class InsecureCredentials {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Admin Password: ");
        String password = sc.nextLine();
        authenticate(password);
        password = null; // Developer believes this scrubs memory
    }
    static void authenticate(String p) { /* check */ }
}
```

---

### Debug 6.9
```java
// Bug: Capturing output via ByteArrayOutputStream returns garbled text on non-UTF8 OS
public class EncodingCapture {
    public static void main(String[] args) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        ps.print("日本語 and Café");
        ps.flush();
        String output = baos.toString(); // Uses platform default encoding
        System.out.println(output);
    }
}
```

---

### Debug 6.10
```java
// Bug: Reading binary file corrupts executable bytes
public class BinaryFileCopier {
    public static void main(String[] args) throws IOException {
        FileReader reader = new FileReader("app.bin");
        FileWriter writer = new FileWriter("app_copy.bin");
        int c;
        while ((c = reader.read()) != -1) {
            writer.write(c);
        }
        reader.close();
        writer.close();
    }
}
```

---

### Debug 6.11
```java
// Bug: Loop writes stale duplicate bytes at end of copied file
public class ByteStreamCopy {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("source.dat");
        FileOutputStream fos = new FileOutputStream("dest.dat");
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            fos.write(buffer); // Bug: writes full 1024 bytes every iteration!
        }
        fis.close();
        fos.close();
    }
}
```

---

### Debug 6.12
```java
// Bug: Silent failure; writer encounters broken pipe but application reports success
public class SilentPipeFailure {
    public static void main(String[] args) {
        PrintStream ps = System.out;
        ps.println("Sending vital payload...");
        // Did it fail? No check performed!
    }
}
```

---

### Debug 6.13
```java
// Bug: CSV export generates numbers with commas (e.g. 12,50) in France, breaking CSV columns
public class CsvExport {
    public static void main(String[] args) {
        double price = 12.50;
        String csvRow = String.format("%d,%s,%.2f", 101, "Widget", price);
        System.out.println(csvRow);
    }
}
```

---

### Debug 6.14
```java
// Bug: String truncation expected, but String.format pads instead
public class TruncationBug {
    public static void main(String[] args) {
        String longTitle = "Senior Executive Vice President of Cloud Systems";
        // Developer wants at most 15 characters
        String display = String.format("%15s", longTitle);
        System.out.println("Display: '" + display + "'");
    }
}
```

---

### Debug 6.15
```java
// Bug: Reader terminates prematurely before slow network stream finishes transmitting
public class ReadyLoopBug {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // Bug: ready() is false when data hasn't arrived yet, terminating loop immediately
        while (reader.ready()) {
            System.out.println(reader.readLine());
        }
    }
}
```

---

### Debug 6.16
```java
// Bug: Scanner fails with InputMismatchException on valid integer "1,000,000"
public class CommaNumberReader {
    public static void main(String[] args) {
        String input = "1,000,000";
        Scanner sc = new Scanner(input);
        int val = sc.nextInt(); // Throws InputMismatchException!
        System.out.println("Val: " + val);
    }
}
```

---

### Debug 6.17
```java
// Bug: Double closing of System.in causes unexpected stream state
public class NestedResourceClose {
    public static void main(String[] args) throws IOException {
        try (InputStream in = System.in;
             Scanner sc = new Scanner(in)) {
            System.out.println(sc.next());
        }
    }
}
```

---

### Debug 6.18
```java
// Bug: UTF-8 file with Byte Order Mark (BOM) has phantom char on first token
public class BomIssue {
    public static void main(String[] args) throws IOException {
        // File starts with EF BB BF
        BufferedReader br = Files.newBufferedReader(Path.of("bom_file.txt"), StandardCharsets.UTF_8);
        String firstLine = br.readLine();
        if (firstLine.startsWith("CONFIG")) {
            System.out.println("Matches");
        } else {
            System.out.println("Failed match due to BOM: " + (int) firstLine.charAt(0));
        }
    }
}
```

---

### Debug 6.19
```java
// Bug: System.out and System.err interleaving causes garbled console logs
public class OutOfOrderLogging {
    public static void main(String[] args) {
        System.out.println("Step 1: Starting");
        System.err.println("Warning: Resource low");
        System.out.println("Step 2: Processing");
    }
}
```

---

### Debug 6.20
```java
// Bug: Memory leak caused by holding large file lines stream open
public class UnclosedLinesStream {
    public static void main(String[] args) throws IOException {
        // Files.lines() returns an AutoCloseable stream that keeps the file handle open
        long count = Files.lines(Path.of("huge_log.txt"))
                          .filter(l -> l.contains("ERROR"))
                          .count();
        // File handle remains open until GC finalization!
    }
}
```

---

### LEVEL 6 ANSWERS & CODE REPAIRS

1. **Repair 6.1:** Add `sc.nextLine()` immediately after `sc.nextInt()` to consume the leftover newline, or use `Integer.parseInt(sc.nextLine())`.
   ```java
   int age = Integer.parseInt(sc.nextLine());
   String name = sc.nextLine();
   ```
2. **Repair 6.2:** Do not wrap `System.in` in a try-with-resources inside a helper method. Pass `Scanner` as a parameter or leave `System.in` unclosed:
   ```java
   public static int readNumber(Scanner sc, String msg) {
       System.out.print(msg);
       return sc.nextInt();
   }
   ```
3. **Repair 6.3:** Enclose in try-with-resources so `bw.close()` is guaranteed, flushing the buffer to disk:
   ```java
   try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt", StandardCharsets.UTF_8))) {
       bw.write("Crucial Data Transaction 98765");
   }
   ```
4. **Repair 6.4:** Consume the invalid token using `sc.next()` inside the loop:
   ```java
   while (!sc.hasNextInt()) {
       System.out.println("Invalid input: " + sc.next());
       System.out.print("Enter age again: ");
   }
   int age = sc.nextInt();
   ```
5. **Repair 6.5:** Check for `null` and provide a fallback reader:
   ```java
   Console console = System.console();
   char[] pwd;
   if (console != null) {
       pwd = console.readPassword("Password: ");
   } else {
       System.out.print("Password (echo visible): ");
       pwd = new Scanner(System.in).nextLine().toCharArray();
   }
   ```
6. **Repair 6.6:** Replace `\n` with `%n`:
   ```java
   System.out.printf("HEADER%nROW 1%nROW 2%n");
   ```
7. **Repair 6.7:** Configure delimiter to include comma or newline: `sc.useDelimiter(",|\\r?\\n");`.
8. **Repair 6.8:** Use `Console.readPassword()` and zero out the array immediately:
   ```java
   char[] pwd = System.console().readPassword("Admin Password: ");
   authenticate(pwd);
   Arrays.fill(pwd, '\0');
   ```
9. **Repair 6.9:** Specify `StandardCharsets.UTF_8`:
   ```java
   String output = baos.toString(StandardCharsets.UTF_8);
   ```
10. **Repair 6.10:** Use byte streams (`FileInputStream` and `FileOutputStream` or `Files.copy()`), never character readers/writers for binary files:
    ```java
    try (InputStream in = new FileInputStream("app.bin");
         OutputStream out = new FileOutputStream("app_copy.bin")) {
        in.transferTo(out);
    }
    ```
11. **Repair 6.11:** Specify actual bytes read: `fos.write(buffer, 0, bytesRead);`.
12. **Repair 6.12:** Check error state:
    ```java
    ps.println("Sending vital payload...");
    if (ps.checkError()) {
        throw new IOException("Write failed on output stream!");
    }
    ```
13. **Repair 6.13:** Pass `Locale.US` explicitly:
    ```java
    String csvRow = String.format(Locale.US, "%d,%s,%.2f", 101, "Widget", price);
    ```
14. **Repair 6.14:** Use precision to truncate strings: `String.format("%.15s", longTitle)`.
15. **Repair 6.15:** Read until `readLine()` returns `null`:
    ```java
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
    ```
16. **Repair 6.16:** Remove commas before parsing or configure custom regex:
    ```java
    int val = Integer.parseInt(input.replace(",", ""));
    ```
17. **Repair 6.17:** Do not close `System.in`. Avoid try-with-resources on `System.in`.
18. **Repair 6.18:** Strip Unicode BOM (`\uFEFF`) if present:
    ```java
    if (firstLine.startsWith("\uFEFF")) firstLine = firstLine.substring(1);
    ```
19. **Repair 6.19:** Synchronize or route errors and standard out through a unified logger to prevent console interleaving race conditions.
20. **Repair 6.20:** Use try-with-resources on `Files.lines()`:
    ```java
    try (Stream<String> lines = Files.lines(Path.of("huge_log.txt"))) {
        long count = lines.filter(l -> l.contains("ERROR")).count();
    }
    ```


---

## LEVEL 7: FILL IN THE MISSING CODE (20 Questions)

*Fill in the blank marked with `/* ___ */` to make each program compile and behave according to specifications. Complete answers and explanations follow at the end of this level.*

### Q7.1
Read all lines using try-with-resources with explicit UTF-8 character encoding:
```java
try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in, /* ___ */))) {
    String line = br.readLine();
}
```

---

### Q7.2
Configure Scanner to split on either commas or Windows/Unix line breaks:
```java
Scanner sc = new Scanner(csvData).useDelimiter(/* ___ */);
```

---

### Q7.3
Format a floating point price to right-align in 10 characters with 2 decimal places and thousands commas:
```java
System.out.printf(/* ___ */, price);
```

---

### Q7.4
Safely zero out a captured password stored in a character array:
```java
char[] password = console.readPassword("Pass: ");
/* ___ */(password, '0');
```

---

### Q7.5
Redirect standard output to capture text in a byte array:
```java
ByteArrayOutputStream baos = new ByteArrayOutputStream();
PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8);
/* ___ */(ps);
```

---

### Q7.6
Check if the next token can be parsed as a 64-bit integer without advancing the stream cursor:
```java
if (scanner./* ___ */()) {
    long id = scanner.nextLong();
}
```

---

### Q7.7
Stream lines from a `BufferedReader` and filter for error messages:
```java
List<String> errors = reader./* ___ */()
                            .filter(l -> l.contains("ERROR"))
                            .toList();
```

---

### Q7.8
Format a `ZonedDateTime` to display full ISO date (YYYY-MM-DD) followed by 24-hr time (HH:MM:SS) using argument reuse:
```java
System.out.printf(/* ___ */, now);
```

---

### Q7.9
Read an entire text file into a `String` using Java 11 NIO convenience method:
```java
String text = Files./* ___ */(Path.of("app.log"), StandardCharsets.UTF_8);
```

---

### Q7.10
Transfer all bytes directly from an `InputStream` to an `OutputStream`:
```java
try (InputStream in = new FileInputStream("in.dat");
     OutputStream out = new FileOutputStream("out.dat")) {
    in./* ___ */(out);
}
```

---

### Q7.11
Format a string such that the first argument is printed in lowercase, then uppercase, without passing it twice:
```java
System.out.printf(/* ___ */, "hello");
```

---

### Q7.12
Format negative numbers enclosed in parentheses, positive numbers normally, with 2 decimal places:
```java
System.out.printf(/* ___ */, balance);
```

---

### Q7.13
Write a string to a file in append mode using Java 11:
```java
Files.writeString(Path.of("audit.log"), entry + "\n", 
    StandardCharsets.UTF_8, StandardOpenOption.CREATE, /* ___ */);
```

---

### Q7.14
Format an integer as an 8-character uppercase hexadecimal string padded with leading zeroes:
```java
System.out.printf(/* ___ */, 255);
```

---

### Q7.15
Verify whether an unhandled error occurred on `System.out`:
```java
System.out.println("Payload");
if (System.out./* ___ */()) {
    System.err.println("Failed to write to stdout!");
}
```

---

### Q7.16
Truncate a name string to a maximum of 12 characters while right-aligning it in a 15-character field:
```java
System.out.printf(/* ___ */, fullName);
```

---

### Q7.17
Format a positive integer to always display a leading plus sign:
```java
System.out.printf(/* ___ */, count);
```

---

### Q7.18
Modern multiline string formatting on a text block:
```java
String msg = """
    User: %s
    Score: %d
    """./* ___ */("Alice", 100);
```

---

### Q7.19
Read all remaining bytes from an `InputStream` into a byte array:
```java
byte[] allBytes = in./* ___ */();
```

---

### Q7.20
Format a floating point number using US decimal rules regardless of default system locale:
```java
String formatted = String.format(/* ___ */, "%.2f", 3.14159);
```

---

### LEVEL 7 ANSWERS & EXPLANATIONS

1. `StandardCharsets.UTF_8` — Standard constant avoiding string-based encoding lookups and checked exceptions.
2. `",|\\r?\\n"` — Matches commas and optional carriage return followed by newline.
3. `"%,10.2f%n"` — `%` starts format, `,` enables thousands grouping, `10` sets width, `.2` sets precision, `f` converts float, `%n` creates platform newline.
4. `Arrays.fill` — Zeroes memory in-place.
5. `System.setOut` — Native method redirecting standard output.
6. `hasNextLong` — Non-advancing predicate for long values.
7. `lines` — Returns `Stream<String>` lazily backed by lines from the reader.
8. `"%1$tF %1$tT%n"` — `%1$tF` formats date as YYYY-MM-DD; `%1$tT` formats time as HH:MM:SS.
9. `readString` — Java 11 method reading an entire file to string.
10. `transferTo` — Java 9 method transferring stream bytes directly.
11. `"%1$s %1$S%n"` (or `"%s %<S%n"`) — Reuses the argument in uppercase.
12. `"%(,.2f%n"` — `(` flag encloses negative values in parentheses.
13. `StandardOpenOption.APPEND` — Appends bytes to end of existing file.
14. `"%08X%n"` — `0` pads with zeros, `8` width, `X` uppercase hex.
15. `checkError` — Flushes and checks error state of `PrintStream`.
16. `"%15.12s%n"` — Width 15, precision 12 (max 12 characters).
17. `"%+d%n"` — `+` flag requires sign for both positive and negative values.
18. `formatted` — Java 15 instance method on String.
19. `readAllBytes` — Java 9 method reading stream to byte array.
20. `Locale.US` — Explicit locale passed as the first argument to `String.format`.

---

## LEVEL 8: EXPLAIN WHY THIS CODE WORKS (20 Questions)

*Provide the JVM, OpenJDK internal, and JLS architectural reasons explaining why each code snippet functions as described. Exhaustive explanations follow at the end of this level.*

### Q8.1
Why does `System.out.println("test")` NEVER throw checked `java.io.IOException` even if writing to a disconnected console or closed pipe?

---

### Q8.2
Why does `java.util.Scanner` exhibit significantly higher CPU and garbage collection overhead than `BufferedReader` when reading numeric tokens?

---

### Q8.3
How does the `PrintStream` auto-flush mechanism determine when to flush its byte buffer, and why does `print("text\n")` flush while `print("text")` does not?

---

### Q8.4
Why does `System.console()` return `null` when an application is executed from within an IDE's Run/Debug tool window or inside a Docker container without `-t`?

---

### Q8.5
Why does security compliance (PCI-DSS, OWASP) strictly forbid storing passwords in `java.lang.String` and require `char[]` wiped via `Arrays.fill()`?

---

### Q8.6
Why does closing a `Scanner` constructed with `new Scanner(System.in)` permanently disable standard input for the entire JVM process?

---

### Q8.7
How does the Java 15 `String.formatted()` method work internally without introducing a new bytecode instruction?

---

### Q8.8
Why does the `printf` specifier `%n` dynamically adapt to host operating systems while `\n` remains static?

---

### Q8.9
Why does `BufferedReader.readLine()` strip both `\r` and `\n` from the returned String, and how does it detect line termination across different platforms?

---

### Q8.10
How does `InputStream.transferTo(OutputStream)` achieve near-kernel zero-copy performance on supported platforms when channels are involved?

---

### Q8.11
How does `Scanner.hasNextInt()` perform lookahead without consuming input characters from the underlying stream?

---

### Q8.12
Why does `String.format("%b", "false")` evaluate to `"true"` according to JLS and Formatter specifications?

---

### Q8.13
Why does referencing `StandardCharsets.UTF_8` perform faster and safer than passing `"UTF-8"` as a String?

---

### Q8.14
Why is `System.out` declared as `public static final PrintStream out`, yet `System.setOut(PrintStream)` is able to reassign it without throwing an `IllegalAccessException`?

---

### Q8.15
Why is the default buffer size of `BufferedReader` set to 8192 characters (8 KB / 16 KB) rather than a smaller buffer like 128 characters?

---

### Q8.16
How does `java.util.Formatter` resolve relative argument indexing (`%<s`) without keeping multiple copies of the argument list in memory?

---

### Q8.17
How does `Locale.Category.FORMAT` differ from `Locale.Category.DISPLAY` in Java I/O formatting?

---

### Q8.18
Why does a naive byte-by-byte `FileInputStream.read()` loop take 50x longer to copy a 10MB file than reading into an 8KB byte array?

---

### Q8.19
Why does `Files.readString(Path)` throw `OutOfMemoryError` on 4GB files on a 2GB JVM heap, whereas `Files.lines(Path)` does not?

---

### Q8.20
Why can messages printed to `System.err` appear out of chronological order when interleaved with messages printed to `System.out` in terminal output?

---

### LEVEL 8 ANSWERS & JLS / JVM SPECIFICATION EXPLANATIONS

#### Answer 8.1
`PrintStream` was designed in Java 1.0 specifically for standard console output. Because writing to a terminal was deemed a ubiquitous operation where forcing developers to handle checked `IOException` on every line would cause excessive boilerplate, `PrintStream` catches all internal `IOException`s in private `catch` blocks and records the failure in a private boolean field `trouble = true`. Callers can query this flag via `checkError()`.

#### Answer 8.2
`Scanner` is a regular-expression-based tokenizer. Every invocation of `nextInt()`, `nextDouble()`, or `next()` compiles or matches against internal `java.util.regex.Pattern` instances. This requires allocating `Matcher` state, string substrings, and token buffers. `BufferedReader`, by contrast, simply reads raw characters into a fixed reusable `char[]` buffer and performs zero regex operations, keeping allocation overhead near zero.

#### Answer 8.3
When constructed with `autoFlush = true`, `PrintStream.write(int)` checks if the written byte is `'\n'`. If so, it immediately calls its internal `flush()`. Furthermore, all `println()` variants invoke `flush()` upon completion. Standard `print("text")` writes characters without a newline and does not trigger the auto-flush condition, leaving bytes buffered in memory until the buffer fills or `flush()` is explicitly invoked.

#### Answer 8.4
The JVM native method `Java_java_io_Console_init` checks the C runtime library function `isatty(fileno(stdin))` and `isatty(fileno(stdout))`. If either standard in or standard out is redirected to a pipe, socket, or file (as IDE run windows and CI containers do to capture subprocess logs), the OS reports that no interactive terminal device is attached. The JVM then sets its internal console field to `null`.

#### Answer 8.5
`java.lang.String` is immutable. Its internal `byte[]` value array is private and cannot be mutated or overwritten by application code. The String will reside in heap memory until the garbage collector runs and overwrites or collects the page. If an attacker dumps heap memory (via `jcmd`, core dump, or heartbleed-style vulnerability), the plaintext credentials are fully readable. A `char[]` is mutable; calling `Arrays.fill(pwd, '\0')` immediately zeroes the physical memory cells in the JVM heap.

#### Answer 8.6
`Scanner` implements `AutoCloseable` and `Closeable`. Its `close()` method invokes `((Closeable) source).close()`. When wrapping `System.in`, this closes the underlying OS file descriptor 0 (`/dev/stdin`). Because the JVM process owns only one standard input file descriptor shared by all threads, closing it invalidates the descriptor for the life of the OS process; any subsequent read attempt from `System.in` immediately yields EOF.

#### Answer 8.7
`String.formatted(Object... args)` was introduced in Java 15 as an instance method on `java.lang.String`. It does not require bytecode changes: its implementation simply invokes `String.format(this, args)`, which constructs a `java.util.Formatter` passing `this` as the format template string.

#### Answer 8.8
The conversion specifier `%n` in `java.util.Formatter` delegates directly to `System.lineSeparator()`. This system property is queried from the host OS during JVM bootstrap (`\r\n` on Windows, `\n` on Unix/Linux/macOS). In contrast, literal `\n` in a Java string literal is compiled directly into the binary bytecode constant pool as ASCII byte `0x0A`.

#### Answer 8.9
`BufferedReader.readLine()` scans its character buffer looking for `\n` (LF) or `\r` (CR). If it encounters `\r`, it immediately inspects the next character: if followed by `\n`, it consumes both (CRLF). The method then constructs a `String` containing only the characters accumulated *before* the line terminator, omitting the delimiter itself.

#### Answer 8.10
When both streams wrap file descriptors or socket channels, OpenJDK implementations of `transferTo` inspect the underlying stream classes. If both implement `FileChannel` or channel interfaces, the transfer is executed via native OS system calls such as Linux `sendfile(2)` or `splice(2)`, transferring data directly inside kernel space without copying bytes into JVM user-space memory buffers.

#### Answer 8.11
`Scanner` maintains an internal `CharBuffer` and integer cursor positions (`position` and `limit`). `hasNextInt()` saves the current position, applies the integer regex pattern against the buffer, records whether a match was found, and then restores the cursor position back to the saved state, leaving the stream unconsumed.

#### Answer 8.12
JLS §15.18.1 and the `java.util.Formatter` specification state that for the `%b` conversion: if the argument `arg` is `null`, the result is `"false"`. If `arg` is an instance of `java.lang.Boolean`, the result is `arg.toString()`. For ANY other argument type, the result is unconditionally `"true"`. Because `"false"` is an instance of `java.lang.String` (not `Boolean`), it evaluates to `"true"`.

#### Answer 8.13
Passing `"UTF-8"` as a String requires `Charset.forName("UTF-8")`, which performs hash table lookups, string normalizations, case-insensitive checks, and synchronization on internal charset provider caches. `StandardCharsets.UTF_8` is a statically initialized constant reference to the `sun.nio.cs.UTF_8` singleton instance, eliminating all lookup overhead and avoiding `UnsupportedEncodingException`.

#### Answer 8.14
`System.out` is declared `public static final PrintStream out;`. The Java compiler prevents direct assignment in application source code. `System.setOut(PrintStream)` is a native JVM method implemented in C (`Java_java_lang_System_setOut0`). Inside the JVM runtime, native code has direct pointer access to write to class static memory slots, bypassing the Java language-level `final` modifier checks.

#### Answer 8.15
Modern file systems and operating systems transfer data from disk to kernel cache in blocks (pages), typically 4096 bytes (4 KB) or 8192 bytes (8 KB). An 8192-char buffer (16 KB) ensures that every read syscall fills an entire multiple of OS memory pages, maximizing disk read burst throughput and amortizing the cost of user-to-kernel context switches.

#### Answer 8.16
`Formatter` processes format specifiers sequentially from left to right. It tracks a single integer variable `last = -1` representing the index of the most recently consumed argument. When encountering `<` (relative index), it re-uses `args[last]`. It does not clone or store additional copies of arguments.

#### Answer 8.17
Java 7 introduced `Locale.Category` to separate locale usage: `Locale.Category.FORMAT` governs rules for dates, numbers, currencies, and percentages (e.g., decimal comma vs period). `Locale.Category.DISPLAY` governs linguistic translations (e.g., UI menu labels, localized month names). `Scanner` and `Formatter` use `Locale.getDefault(Locale.Category.FORMAT)`.

#### Answer 8.18
Each single-byte `in.read()` invocation on an unbuffered `FileInputStream` requires a transition from JVM user mode to OS kernel mode via a `read(2)` system call. Copying 10MB requires 10,485,760 context switches. Reading into an 8KB buffer requires only 1,280 system calls, reducing kernel context switch overhead by 99.98%.

#### Answer 8.19
`Files.readString()` must allocate a contiguous byte array for the entire file length, then decode it into a contiguous `char[]` or Latin-1 `byte[]` inside a single `java.lang.String` object. On a 4GB file, this requires multiple gigabytes of contiguous heap space, triggering `OutOfMemoryError`. `Files.lines()` streams lines lazily one at a time; each line is garbage collected once processed, requiring only a few kilobytes of active heap memory.

#### Answer 8.20
`System.out` and `System.err` are two distinct, uncoordinated streams connected to different OS file descriptors (FD 1 and FD 2). `System.out` is buffered with auto-flush, while `System.err` is typically unbuffered. Because they do not share an internal lock or buffer, output operations execute concurrently and race to write to the terminal console, resulting in non-deterministic interleaved line ordering.


---

## LEVEL 9: REFACTORING EXERCISES (20 Exercises)

*Refactor each legacy or problematic I/O code snippet to modern Java 21 LTS idiomatic standards, eliminating resource leaks, performance bottlenecks, locale bugs, and security risks. Complete refactored solutions follow at the end of this level.*

### Refactoring 9.1: Unprotected System.in Scanner
```java
// Problem: Scanner on System.in is closed inside a utility method, breaking future input
public class InputUtil {
    public static String ask(String question) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print(question);
            return sc.nextLine();
        }
    }
}
```

---

### Refactoring 9.2: Brittle String Concatenation Report
```java
// Problem: String concatenation makes columns misalign when data lengths change
public String makeRow(int id, String name, double salary) {
    return "ID: " + id + " | Name: " + name + " | Salary: $" + salary;
}
```

---

### Refactoring 9.3: Slow Byte-by-Byte Stream Copy
```java
// Problem: Reading single bytes causes millions of kernel context switches
public void copy(InputStream in, OutputStream out) throws IOException {
    int b;
    while ((b = in.read()) != -1) {
        out.write(b);
    }
}
```

---

### Refactoring 9.4: Heap-Exposed Password Ingestion
```java
// Problem: Sensitive password stored as String remains readable in memory dumps
public String readSecret() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Password: ");
    return sc.nextLine();
}
```

---

### Refactoring 9.5: Hardcoded Unix Newline in Printf
```java
// Problem: Literal \n breaks file format verification on Windows platforms
public void printInvoice(long id, double amount) {
    System.out.printf("INVOICE #%d\nTOTAL: $%.2f\nSTATUS: PAID\n", id, amount);
}
```

---

### Refactoring 9.6: Scanner in High-Throughput Batch Processing
```java
// Problem: Scanner regex matching slows down processing of 500,000 log records
public void processLogs(File logFile) throws FileNotFoundException {
    Scanner sc = new Scanner(logFile);
    while (sc.hasNextLine()) {
        parseLine(sc.nextLine());
    }
    sc.close();
}
```

---

### Refactoring 9.7: Locale-Sensitive Number Formatting in CSV
```java
// Problem: Emits commas for decimals in European locales, destroying CSV columns
public String toCsv(int id, double price) {
    return String.format("%d,%.2f", id, price);
}
```

---

### Refactoring 9.8: Brittle While-True Input Validation
```java
// Problem: Repeated boilerplate exception catching for basic integer validation
public int getAge(Scanner sc) {
    while (true) {
        try {
            System.out.print("Age: ");
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number!");
        }
    }
}
```

---

### Refactoring 9.9: Verbose Pre-Java 7 Stream Cleanup
```java
// Problem: Manual finally block stream closing prone to null checks and suppressed exceptions
public void writeData(String path, byte[] data) throws IOException {
    FileOutputStream fos = null;
    try {
        fos = new FileOutputStream(path);
        fos.write(data);
    } finally {
        if (fos != null) {
            fos.close();
        }
    }
}
```

---

### Refactoring 9.10: Silent Failure on Output Stream
```java
// Problem: Silent data loss when writing to broken pipes or full disks
public void emitAudit(PrintStream out, String msg) {
    out.println(msg);
}
```

---

### Refactoring 9.11: Reading Entire File via Low-Level Stream
```java
// Problem: 15 lines of boilerplate to read a small text configuration file
public String loadConfig(File file) throws IOException {
    StringBuilder sb = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
    }
    return sb.toString();
}
```

---

### Refactoring 9.12: Appending to File via FileOutputStream
```java
// Problem: Low-level stream management when appending simple log lines
public void appendLog(String filename, String log) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(filename, true)) {
        fos.write((log + "\n").getBytes(StandardCharsets.UTF_8));
    }
}
```

---

### Refactoring 9.13: Naive CSV Splitting
```java
// Problem: String.split(",") fails when rows have varied delimiters or spaces
public List<String> parseTokens(String line) {
    return Arrays.asList(line.split(","));
}
```

---

### Refactoring 9.14: Thread-Unsafe Console Output
```java
// Problem: Unsynchronized concurrent prints interleave lines
public void logMultiThreaded(String message) {
    System.out.println(Thread.currentThread().getName() + " - " + message);
}
```

---

### Refactoring 9.15: Multi-Line Println Cascade
```java
// Problem: 8 consecutive println statements where a text block is cleaner
public void printWelcome() {
    System.out.println("================================");
    System.out.println("   WELCOME TO JAVA MASTERY      ");
    System.out.println("   Version: 21 LTS              ");
    System.out.println("   Author:  Senior JVM Engineer ");
    System.out.println("================================");
}
```

---

### Refactoring 9.16: String-Based Charset Name
```java
// Problem: String charset name lookups add overhead and throw checked exception
public Reader openReader(InputStream in) throws UnsupportedEncodingException {
    return new InputStreamReader(in, "UTF-8");
}
```

---

### Refactoring 9.17: Manual Date String Formatting
```java
// Problem: Manual date string construction instead of format specifiers
public String formatTimestamp(LocalDateTime dt) {
    return dt.getYear() + "-" + String.format("%02d", dt.getMonthValue()) 
        + "-" + String.format("%02d", dt.getDayOfMonth());
}
```

---

### Refactoring 9.18: Reading Entire File When Only First Line is Needed
```java
// Problem: Reads 500MB into memory just to check a header tag
public boolean hasValidHeader(Path path) throws IOException {
    List<String> lines = Files.readAllLines(path);
    return !lines.isEmpty() && lines.get(0).startsWith("#!MAGIC");
}
```

---

### Refactoring 9.19: Reading Character by Character from Reader
```java
// Problem: Manual character buffer accumulation instead of lines stream
public List<String> findKeywords(BufferedReader br) throws IOException {
    List<String> matches = new ArrayList<>();
    String line;
    while ((line = br.readLine()) != null) {
        if (line.contains("CRITICAL")) matches.add(line);
    }
    return matches;
}
```

---

### Refactoring 9.20: Unformatted Floating Point Money Display
```java
// Problem: Prints $10.5 or $10.55555555 instead of standard financial $10.50
public void displayPrice(double price) {
    System.out.println("Price: $" + price);
}
```

---

### LEVEL 9 REFACTORING SOLUTIONS & RATIONALE

1. **Refactored 9.1:** Pass `Scanner` as an external dependency or do not close it:
   ```java
   public static String ask(Scanner sc, String question) {
       System.out.print(question);
       return sc.nextLine();
   }
   ```
   *Rationale:* Prevents invalidating `System.in` for the entire JVM.

2. **Refactored 9.2:** Use `String.formatted()`:
   ```java
   public String makeRow(int id, String name, double salary) {
       return "ID: %06d | Name: %-20.20s | Salary: $%,10.2f".formatted(id, name, salary);
   }
   ```
   *Rationale:* Enforces fixed column widths, zero-padding, string truncation, and currency formatting.

3. **Refactored 9.3:** Use `in.transferTo(out)`:
   ```java
   public void copy(InputStream in, OutputStream out) throws IOException {
       in.transferTo(out);
   }
   ```
   *Rationale:* Leverages internal 8KB buffer and OS zero-copy channel transfer where supported.

4. **Refactored 9.4:** Use `System.console().readPassword()`:
   ```java
   public char[] readSecret() {
       Console console = System.console();
       if (console != null) {
           return console.readPassword("Password: ");
       }
       System.out.print("Password: ");
       return new Scanner(System.in).nextLine().toCharArray();
   }
   ```
   *Rationale:* Mutable `char[]` enables immediate memory scrubbing.

5. **Refactored 9.5:** Replace `\n` with `%n`:
   ```java
   public void printInvoice(long id, double amount) {
       System.out.printf("INVOICE #%d%nTOTAL: $%.2f%nSTATUS: PAID%n", id, amount);
   }
   ```
   *Rationale:* Guarantees platform-compliant line breaks.

6. **Refactored 9.6:** Use `BufferedReader` with try-with-resources:
   ```java
   public void processLogs(File logFile) throws IOException {
       try (BufferedReader br = Files.newBufferedReader(logFile.toPath(), StandardCharsets.UTF_8)) {
           String line;
           while ((line = br.readLine()) != null) {
               parseLine(line);
           }
       }
   }
   ```
   *Rationale:* Eliminates regex overhead; 5x to 10x faster.

7. **Refactored 9.7:** Specify `Locale.US`:
   ```java
   public String toCsv(int id, double price) {
       return String.format(Locale.US, "%d,%.2f", id, price);
   }
   ```
   *Rationale:* Enforces `.` decimal separator regardless of host country locale.

8. **Refactored 9.8:** Use non-throwing `hasNextInt()` validation:
   ```java
   public int getAge(Scanner sc) {
       System.out.print("Age: ");
       while (!sc.hasNextInt()) {
           System.out.printf("Invalid! '%s' is not an integer. Age: ", sc.next());
       }
       int age = sc.nextInt();
       sc.nextLine(); // Drain newline
       return age;
   }
   ```
   *Rationale:* Avoids exception generation on standard user typos.

9. **Refactored 9.9:** Use try-with-resources:
   ```java
   public void writeData(String path, byte[] data) throws IOException {
       try (FileOutputStream fos = new FileOutputStream(path)) {
           fos.write(data);
       }
   }
   ```
   *Rationale:* Deterministic closure with exception suppression handling.

10. **Refactored 9.10:** Check error state:
    ```java
    public void emitAudit(PrintStream out, String msg) throws IOException {
        out.println(msg);
        if (out.checkError()) throw new IOException("I/O error during audit print!");
    }
    ```
    *Rationale:* Exposes silent failures on `PrintStream`.

11. **Refactored 9.11:** Use Java 11 `Files.readString()`:
    ```java
    public String loadConfig(File file) throws IOException {
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    }
    ```
    *Rationale:* Reduces 15 lines of boilerplate to 1 safe line.

12. **Refactored 9.12:** Use `Files.writeString()`:
    ```java
    public void appendLog(String filename, String log) throws IOException {
        Files.writeString(Path.of(filename), log + System.lineSeparator(), 
            StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
    ```
    *Rationale:* Concise, platform-separated atomic append.

13. **Refactored 9.13:** Use `Scanner` with custom delimiter regex:
    ```java
    public List<String> parseTokens(String line) {
        List<String> list = new ArrayList<>();
        try (Scanner sc = new Scanner(line).useDelimiter("\\s*,\\s*")) {
            while (sc.hasNext()) list.add(sc.next());
        }
        return list;
    }
    ```
    *Rationale:* Handles surrounding token whitespace cleanly.

14. **Refactored 9.14:** Synchronize output:
    ```java
    private static final Object CONSOLE_LOCK = new Object();
    public void logMultiThreaded(String message) {
        synchronized (CONSOLE_LOCK) {
            System.out.println(Thread.currentThread().getName() + " - " + message);
        }
    }
    ```
    *Rationale:* Prevents interleaved multi-threaded console output.

15. **Refactored 9.15:** Use multiline Text Block:
    ```java
    public void printWelcome() {
        System.out.print("""
            ================================
               WELCOME TO JAVA MASTERY      
               Version: 21 LTS              
               Author:  Senior JVM Engineer 
            ================================
            """);
    }
    ```
    *Rationale:* Single atomic I/O write, highly readable.

16. **Refactored 9.16:** Use `StandardCharsets.UTF_8`:
    ```java
    public Reader openReader(InputStream in) {
        return new InputStreamReader(in, StandardCharsets.UTF_8);
    }
    ```
    *Rationale:* Eliminates checked exception and charset string lookup overhead.

17. **Refactored 9.17:** Use `%tF`:
    ```java
    public String formatTimestamp(LocalDateTime dt) {
        return "%tF".formatted(dt);
    }
    ```
    *Rationale:* Standard ISO date format specifier.

18. **Refactored 9.18:** Use `BufferedReader.readLine()` or stream `findFirst()`:
    ```java
    public boolean hasValidHeader(Path path) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String header = br.readLine();
            return header != null && header.startsWith("#!MAGIC");
        }
    }
    ```
    *Rationale:* Reads only the first line without loading 500MB into memory.

19. **Refactored 9.19:** Use Java Stream API:
    ```java
    public List<String> findKeywords(BufferedReader br) {
        return br.lines().filter(line -> line.contains("CRITICAL")).toList();
    }
    ```
    *Rationale:* Idiomatic declarative stream processing.

20. **Refactored 9.20:** Use `printf` with `%.2f`:
    ```java
    public void displayPrice(double price) {
        System.out.printf("Price: $%.2f%n", price);
    }
    ```
    *Rationale:* Standard two-decimal currency formatting.

---

## LEVEL 10: CODING EXERCISES (20 Questions)

*Progressive hands-on coding challenges ranging from foundational input handling to advanced stream interceptors. Reference solutions follow at the end of this level.*

### Exercise 10.1: Numbered Line Echoer
Write a class `NumberedEcho` that reads lines from `System.in` and outputs each line prefixed with a 4-digit zero-padded line number (e.g. `0001: Hello`) until the user types `QUIT`.

---

### Exercise 10.2: Floating Point Accumulator
Write a method `double sumInputs(InputStream in)` that reads floating-point numbers separated by whitespace and returns the total sum, terminating gracefully when non-numeric input is encountered.

---

### Exercise 10.3: Aligned Grid Generator
Write a method `String renderGrid(int rows, int cols)` that returns an ASCII multiplication grid where all row and column numbers are right-aligned in 5-character wide columns.

---

### Exercise 10.4: Binary Hex Viewer
Write a method `void printHexDump(byte[] data)` that prints binary data in standard 16-bytes-per-line format with offset, hexadecimal pairs, and ASCII representation.

---

### Exercise 10.5: CSV to Markdown Converter
Write a method `String csvToMarkdown(String csvText)` that parses comma-separated data and produces an aligned GitHub-flavored Markdown table.

---

### Exercise 10.6: Password Strength Auditor
Write a class `ConsolePasswordAuditor` that reads a password via `System.console()`, checks for length >= 12, uppercase, digit, and symbol, scores it 1-100, and immediately zeroes the memory.

---

### Exercise 10.7: Log File Severity Filter
Write a method `List<String> filterLogs(Path logPath, String minLevel)` using `Files.lines()` that extracts and returns only logs matching or exceeding `minLevel` (`INFO`, `WARN`, `ERROR`).

---

### Exercise 10.8: Polyglot Base Converter
Write a method `void printBases(long value)` that outputs `value` in decimal, hex (`0x...`), octal (`0...`), and binary (`64-bit zero padded`).

---

### Exercise 10.9: Execution Output Capturer
Write a method `String captureOutput(Runnable task)` that redirects `System.out`, executes the task, restores `System.out`, and returns the captured output as a UTF-8 String.

---

### Exercise 10.10: Typed Prompt Engine
Implement `PromptReader`:
```java
public class PromptReader {
    public int readInt(String prompt, int min, int max) { ... }
    public String readRegex(String prompt, String pattern, String errorMsg) { ... }
}
```

---

### Exercise 10.11: Timezone Schedule Formatter
Write a method `void printSchedule(ZonedDateTime meeting)` that formats the meeting instant in UTC, New York (`America/New_York`), and Tokyo (`Asia/Tokyo`) using `%t` specifiers.

---

### Exercise 10.12: High-Frequency Word Counter
Write a method `Map<String, Integer> countTopWords(Path path, int limit)` that streams words from a file using `Scanner` or `BufferedReader` and returns the top `limit` frequent words.

---

### Exercise 10.13: Zero-Allocation Integer Parser
Write a class `FastIntReader` that parses integers directly from an `InputStream` without allocating any intermediate `String` or `Pattern` objects.

---

### Exercise 10.14: Config Property Updater
Write a method `void setProperty(Path confPath, String key, String value)` that reads a config file, updates or appends the key-value pair, and writes back using `Files.readString` and `Files.writeString`.

---

### Exercise 10.15: ANSI Terminal Decorator
Write a utility `AnsiConsole` that prints text formatted in ANSI red, green, bold, and underline using `System.out.printf`.

---

### Exercise 10.16: Non-Closing System.in Shield
Write an `InputStream` wrapper `UnclosableInputStream` that delegates all reads to an underlying stream but suppresses `close()` calls.

---

### Exercise 10.17: Batch File Renamer CLI
Write a CLI method that prompts the user `Rename <old> to <new>? (y/n): ` and processes a list of files with confirmed renaming.

---

### Exercise 10.18: In-Place Terminal Progress Bar
Write a method `void renderProgress(int percent)` that uses carriage return `\r` without newline to update a terminal progress bar `[=====>    ] 50%` in place.

---

### Exercise 10.19: Charset Transcoding Utility
Write a method `void transcode(Path source, Charset from, Path dest, Charset to)` that reads and writes between distinct charsets handling replacement on malformed characters.

---

### Exercise 10.20: Stream JSON-Like Key Extractor
Write a method `List<String> extractJsonKeys(BufferedReader br, String targetKey)` that streams text and extracts values for `"targetKey": "value"` without a full JSON parser library.

---

### LEVEL 10 REFERENCE SOLUTIONS

#### Solution 10.1: Numbered Line Echoer
```java
public class NumberedEcho {
    public static void run(Scanner sc) {
        int lineNum = 1;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if ("QUIT".equalsIgnoreCase(line.trim())) break;
            System.out.printf("%04d: %s%n", lineNum++, line);
        }
    }
}
```

#### Solution 10.2: Floating Point Accumulator
```java
public static double sumInputs(InputStream in) {
    Scanner sc = new Scanner(in).useLocale(Locale.US);
    double sum = 0.0;
    while (sc.hasNextDouble()) {
        sum += sc.nextDouble();
    }
    return sum;
}
```

#### Solution 10.3: Aligned Grid Generator
```java
public static String renderGrid(int rows, int cols) {
    StringBuilder sb = new StringBuilder();
    for (int r = 1; r <= rows; r++) {
        for (int c = 1; c <= cols; c++) {
            sb.append("%5d".formatted(r * c));
        }
        sb.append(System.lineSeparator());
    }
    return sb.toString();
}
```

#### Solution 10.4: Binary Hex Viewer
```java
public static void printHexDump(byte[] data) {
    for (int i = 0; i < data.length; i += 16) {
        System.out.printf("%08X  ", i);
        for (int j = 0; j < 16; j++) {
            if (i + j < data.length) {
                System.out.printf("%02X ", data[i + j]);
            } else {
                System.out.print("   ");
            }
        }
        System.out.print(" |");
        for (int j = 0; j < 16 && (i + j) < data.length; j++) {
            byte b = data[i + j];
            System.out.print((b >= 32 && b <= 126) ? (char) b : '.');
        }
        System.out.println("|");
    }
}
```

#### Solution 10.5: CSV to Markdown Converter
```java
public static String csvToMarkdown(String csvText) {
    String[] lines = csvText.split("\\r?\\n");
    if (lines.length == 0) return "";
    StringBuilder sb = new StringBuilder();
    String[] headers = lines[0].split(",");
    sb.append("| ").append(String.join(" | ", headers)).append(" |\n");
    sb.append("|").append(" --- |".repeat(headers.length)).append("\n");
    for (int i = 1; i < lines.length; i++) {
        String[] row = lines[i].split(",");
        sb.append("| ").append(String.join(" | ", row)).append(" |\n");
    }
    return sb.toString();
}
```

#### Solution 10.6: Password Strength Auditor
```java
public class ConsolePasswordAuditor {
    public static void audit() {
        Console console = System.console();
        if (console == null) return;
        char[] pwd = console.readPassword("Enter password to test: ");
        int score = 0;
        if (pwd.length >= 12) score += 40;
        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        for (char c : pwd) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        if (hasUpper) score += 20;
        if (hasDigit) score += 20;
        if (hasSpecial) score += 20;
        System.out.printf("Password Security Score: %d/100%n", score);
        Arrays.fill(pwd, '\0');
    }
}
```

#### Solution 10.7: Log File Severity Filter
```java
public static List<String> filterLogs(Path logPath, String minLevel) throws IOException {
    List<String> validLevels = List.of("INFO", "WARN", "ERROR");
    int minIndex = validLevels.indexOf(minLevel);
    try (Stream<String> lines = Files.lines(logPath, StandardCharsets.UTF_8)) {
        return lines.filter(line -> {
            for (int i = minIndex; i < validLevels.size(); i++) {
                if (line.contains("[" + validLevels.get(i) + "]")) return true;
            }
            return false;
        }).toList();
    }
}
```

#### Solution 10.8: Polyglot Base Converter
```java
public static void printBases(long value) {
    String binary = Long.toBinaryString(value);
    String paddedBinary = String.format("%64s", binary).replace(' ', '0');
    System.out.printf("Decimal: %,d%n", value);
    System.out.printf("Hex:     0x%016X%n", value);
    System.out.printf("Octal:   0%022o%n", value);
    System.out.printf("Binary:  %s%n", paddedBinary);
}
```

#### Solution 10.9: Execution Output Capturer
```java
public static String captureOutput(Runnable task) {
    PrintStream originalOut = System.out;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8)) {
        System.setOut(ps);
        task.run();
    } finally {
        System.setOut(originalOut);
    }
    return baos.toString(StandardCharsets.UTF_8);
}
```

#### Solution 10.10: Typed Prompt Engine
```java
public class PromptReader {
    private final Scanner scanner;
    public PromptReader(Scanner scanner) { this.scanner = scanner; }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.printf("%s [%d-%d]: ", prompt, min, max);
            if (scanner.hasNextInt()) {
                int val = scanner.nextInt();
                scanner.nextLine();
                if (val >= min && val <= max) return val;
            } else {
                scanner.next();
            }
            System.out.println("Invalid input!");
        }
    }
}
```

#### Solution 10.11: Timezone Schedule Formatter
```java
public static void printSchedule(ZonedDateTime meeting) {
    ZonedDateTime ny = meeting.withZoneSameInstant(ZoneId.of("America/New_York"));
    ZonedDateTime tokyo = meeting.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
    System.out.printf("UTC:      %1$tF %1$tT %1$tZ%n", meeting.withZoneSameInstant(ZoneId.of("UTC")));
    System.out.printf("New York: %1$tF %1$tT %1$tZ%n", ny);
    System.out.printf("Tokyo:    %1$tF %1$tT %1$tZ%n", tokyo);
}
```

#### Solution 10.12: High-Frequency Word Counter
```java
public static Map<String, Integer> countTopWords(Path path, int limit) throws IOException {
    Map<String, Integer> counts = new HashMap<>();
    try (Scanner sc = new Scanner(path, StandardCharsets.UTF_8).useDelimiter("[^a-zA-Z0-9]+")) {
        while (sc.hasNext()) {
            counts.merge(sc.next().toLowerCase(), 1, Integer::sum);
        }
    }
    return counts.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .limit(limit)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
}
```

#### Solution 10.13: Zero-Allocation Integer Parser
```java
public class FastIntReader {
    private final InputStream in;
    public FastIntReader(InputStream in) { this.in = in; }

    public int nextInt() throws IOException {
        int b = in.read();
        while (b <= ' ' && b != -1) b = in.read();
        if (b == -1) throw new NoSuchElementException();
        boolean neg = (b == '-');
        if (neg) b = in.read();
        int val = 0;
        while (b >= '0' && b <= '9') {
            val = val * 10 + (b - '0');
            b = in.read();
        }
        return neg ? -val : val;
    }
}
```

#### Solution 10.14: Config Property Updater
```java
public static void setProperty(Path confPath, String key, String value) throws IOException {
    List<String> lines = Files.exists(confPath) ? Files.readAllLines(confPath, StandardCharsets.UTF_8) : new ArrayList<>();
    boolean replaced = false;
    for (int i = 0; i < lines.size(); i++) {
        if (lines.get(i).startsWith(key + "=")) {
            lines.set(i, key + "=" + value);
            replaced = true;
            break;
        }
    }
    if (!replaced) lines.add(key + "=" + value);
    Files.write(confPath, lines, StandardCharsets.UTF_8);
}
```

#### Solution 10.15: ANSI Terminal Decorator
```java
public class AnsiConsole {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BOLD = "\u001B[1m";

    public static void printSuccess(String msg) {
        System.out.printf("%s%s[SUCCESS]%s %s%n", BOLD, GREEN, RESET, msg);
    }
    public static void printError(String msg) {
        System.out.printf("%s%s[ERROR]%s %s%n", BOLD, RED, RESET, msg);
    }
}
```

#### Solution 10.16: Non-Closing System.in Shield
```java
public class UnclosableInputStream extends FilterInputStream {
    public UnclosableInputStream(InputStream in) { super(in); }
    @Override public void close() { /* Suppress close */ }
}
```

#### Solution 10.17: Batch File Renamer CLI
```java
public static void batchRename(List<Path> targets, Function<String, String> renamer, Scanner sc) throws IOException {
    for (Path p : targets) {
        String newName = renamer.apply(p.getFileName().toString());
        System.out.printf("Rename '%s' to '%s'? (y/n): ", p.getFileName(), newName);
        if ("y".equalsIgnoreCase(sc.nextLine().trim())) {
            Files.move(p, p.resolveSibling(newName));
            System.out.println("Renamed.");
        }
    }
}
```

#### Solution 10.18: In-Place Terminal Progress Bar
```java
public static void renderProgress(int percent) {
    int totalBars = 20;
    int filled = (percent * totalBars) / 100;
    String bar = "=".repeat(filled) + (filled < totalBars ? ">" : "") + " ".repeat(Math.max(0, totalBars - filled - 1));
    System.out.printf("\r[%s] %3d%%", bar, percent);
    if (percent >= 100) System.out.println();
}
```

#### Solution 10.19: Charset Transcoding Utility
```java
public static void transcode(Path source, Charset from, Path dest, Charset to) throws IOException {
    CharsetDecoder decoder = from.newDecoder().onMalformedInput(CodingErrorAction.REPLACE);
    CharsetEncoder encoder = to.newEncoder().onMalformedInput(CodingErrorAction.REPLACE);
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(source), decoder));
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(dest), encoder))) {
        reader.transferTo(writer);
    }
}
```

#### Solution 10.20: Stream JSON-Like Key Extractor
```java
public static List<String> extractJsonKeys(BufferedReader br, String targetKey) {
    Pattern pattern = Pattern.compile("\"" + Pattern.quote(targetKey) + "\"\\s*:\\s*\"([^\"]+)\"");
    return br.lines()
             .map(pattern::matcher)
             .filter(Matcher::find)
             .map(m -> m.group(1))
             .toList();
}
```


---

## LEVEL 11: MINI INTERVIEW QUESTIONS (10 Questions)

*Senior Java, JVM & OpenJDK Compiler Engineer Interview Questions with Exhaustive Reference Answers.*

### Q11.1
**Interviewer:** *How does the OpenJDK JVM bootstrap standard I/O streams (`System.in`, `System.out`, `System.err`) during runtime initialization, and why are their fields marked `public static final` even though `System.setOut()` can mutate them?*

---

### Q11.2
**Interviewer:** *Compare the architectural design, memory allocations, and throughput characteristics of `java.util.Scanner` versus `java.io.BufferedReader` in high-throughput data pipelines.*

---

### Q11.3
**Interviewer:** *Why does `System.console()` return `null` in headless, CI/CD, or IDE environments, and what low-level operating system mechanism determines whether a TTY/PTY is present?*

---

### Q11.4
**Interviewer:** *Explain how `java.util.Formatter` parses format strings. How does it handle argument indices, width, precision, and flags at the data structure level?*

---

### Q11.5
**Interviewer:** *From a JVM memory layout, Garbage Collector, and security standpoint, why is `char[]` required for sensitive credential capture over `java.lang.String`?*

---

### Q11.6
**Interviewer:** *What architectural differences exist between traditional blocking byte streams (`FileInputStream`/`FileOutputStream`) and NIO channels (`FileChannel`), specifically regarding OS context switches and zero-copy transfer mechanisms?*

---

### Q11.7
**Interviewer:** *What did JEP 400 (UTF-8 by Default in Java 18) accomplish, and what subtle bugs existed in cross-platform Java applications prior to its implementation?*

---

### Q11.8
**Interviewer:** *Describe the auto-flush semantics of `PrintStream`. What triggers an automatic flush, and why can this cause significant performance bottlenecks in high-frequency logging?*

---

### Q11.9
**Interviewer:** *Why does invoking `close()` on a `Scanner` constructed with `System.in` invalidate standard input for the entire JVM process, and what design patterns should be applied to prevent this?*

---

### Q11.10
**Interviewer:** *Explain the exact semantic and binary difference between `%n` and `\n` in formatted output strings, and how line separators are handled across diverse POSIX and Windows operating systems.*

---

### LEVEL 11 MASTERCLASS INTERVIEW ANSWERS

#### Answer 11.1
- **JVM Bootstrap Phase:** During early JVM startup in `initPhase1` (`java.lang.System.initPhase1()`), standard file descriptors 0 (stdin), 1 (stdout), and 2 (stderr) are wrapped. Standard in is wrapped in a `BufferedInputStream(new FileInputStream(FileDescriptor.in))`. Standard out and error are wrapped in `PrintStream(new BufferedOutputStream(new FileOutputStream(FileDescriptor.out)), true)`.
- **`final` Field Mutation:** `System.in`, `System.out`, and `System.err` are declared `public static final`. At the Java language level, the compiler rejects assignments. However, `System.setOut()` and `System.setIn()` call private native methods (`setOut0`, `setIn0`). These C++ native methods in OpenJDK directly write to the static field memory addresses in the JVM Metaspace / Class mirror, bypassing Java language-level `final` enforcement.

#### Answer 11.2
- **Scanner:** Designed as a high-level lexer/tokenizer. It relies on `java.util.regex.Pattern` matching on every token read. Reading primitives (`nextInt()`, `nextDouble()`) allocates `String` substrings, creates `Matcher` internal state, and maintains a dynamically resizing `CharBuffer`. This generates intense garbage collection pressure and CPU instruction overhead. Furthermore, `Scanner` is unsynchronized.
- **BufferedReader:** Designed purely for character buffering with a fixed 8192-char array. It performs no regex parsing and allocates no intermediate objects when reading lines, reading directly from the underlying stream into the buffer. Public methods synchronize on an internal lock object (`lock`). In ingestion benchmarks, `BufferedReader` typically achieves 5x to 10x higher throughput than `Scanner`.

#### Answer 11.3
- **Operating System TTY/PTY:** In Unix-like systems and Windows, an interactive console is represented as a character device (teletype / pseudo-terminal). The OpenJDK native layer invokes the POSIX C library function `isatty(STDIN_FILENO)` and `isatty(STDOUT_FILENO)`.
- **Headless & IDE Environments:** When an application is launched inside IntelliJ IDEA, Maven, Gradle, Docker without `-t`, or CI/CD runners (GitHub Actions, Jenkins), standard I/O is redirected to anonymous pipes or socket streams to allow the parent process to capture output. Because an anonymous pipe is not a TTY (`isatty()` returns 0), the JVM sets the internal `Console` singleton to `null`.

#### Answer 11.4
- **Lexing Phase:** `java.util.Formatter` processes format strings sequentially using a compiled regular expression (`formatSpecifier = Pattern.compile(...)`).
- **Parsing Structure:** Each matched token is parsed into a `FormatSpecifier` object containing parsed bitmask flags (`-`, `+`, `0`, `,`), integer width, integer precision, boolean argument index, and conversion character.
- **Argument Resolution:** `Formatter` tracks an ordinary index (`next()`) and a relative index (`last`). If an explicit index is specified (e.g. `%2$s`), it indexes directly into the `Object[] args` array. Semantics validation ensures that incompatible combinations (e.g., `#` flag with `%d` conversion) throw runtime format exceptions.

#### Answer 11.5
- **String Immutability:** `java.lang.String` in Java 9+ is backed by a `byte[] coder` array marked `final`. The String cannot be cleared or modified. It persists on the JVM heap until GC collects it. If heap memory is dumped, inspected via JMX, or swapped to OS disk storage, plaintext passwords remain readable indefinitely.
- **char[] Memory Scrubbing:** A `char[]` or `byte[]` is mutable. The application can read credentials, hash them with salt, and immediately invoke `Arrays.fill(pwd, '\0')`. This overwrites the physical heap memory cells immediately, minimizing the window of vulnerability to microseconds.

#### Answer 11.6
- **Blocking Byte Streams:** `FileInputStream.read()` transfers data from disk into the OS page cache, then performs a kernel-to-user memory copy into a JVM byte array. Each read system call incurs user-kernel CPU mode context switches.
- **FileChannel & Zero-Copy:** NIO `FileChannel` supports direct byte buffers allocated outside the JVM heap (`Unsafe.allocateMemory()`). For stream-to-stream transfer (`channel.transferTo()`), OpenJDK delegates to native OS system calls (e.g. Linux `sendfile(2)`, Windows `TransmitFile`), routing bytes directly from the file system cache to the network socket buffer entirely within OS kernel space without entering user space.

#### Answer 11.7
- **Historical Hazard:** Prior to Java 18, `Charset.defaultCharset()` resolved to the host operating system's locale encoding. A file saved on Windows (`windows-1252`) produced corrupted text (Mojibake) when read on a Linux server (`UTF-8`), and unit tests passed on developer Mac laptops but failed on Windows CI runners.
- **JEP 400 Resolution:** Java 18 made `UTF-8` the universal standard charset for all standard I/O APIs unless explicitly configured otherwise via `-Dfile.encoding=COMPAT`.

#### Answer 11.8
- **Auto-Flush Semantics:** When `PrintStream` is constructed with `autoFlush = true` (default for `System.out`), the stream automatically invokes `flush()` if:
  1. A byte array (`byte[]`) is written.
  2. Any of the `println()` methods are invoked.
  3. A newline character (`\n`) is written in a byte sequence.
- **Performance Impact:** In high-frequency logging, flushing on every line forces synchronous buffer flushes down to the OS kernel, invalidating write-combining buffers and incurring severe system call overhead.

#### Answer 11.9
- **Stream Ownership Chain:** `Scanner` implements `AutoCloseable`. Calling `scanner.close()` cascades to its underlying `Readable` / `InputStream`. Since `System.in` wraps OS File Descriptor 0, closing it closes the process-level input handle.
- **Mitigation Architectures:**
  1. Manage a single application-wide `Scanner` instance and never close it.
  2. Wrap `System.in` in a non-closing decorator (`FilterInputStream` overriding `close()` as a no-op).

#### Answer 11.10
- **Literal `\n`:** Compiles to ASCII byte `0x0A` (Line Feed). On POSIX systems (Linux, macOS), this is the standard newline. On Windows, standard text files require Carriage Return + Line Feed (`0x0D 0x0A`).
- **Format Specifier `%n`:** Evaluates at runtime to `System.lineSeparator()`. On Windows, it emits CRLF (`\r\n`); on Unix/Linux, it emits LF (`\n`). Using `%n` ensures strict cross-platform compatibility when writing reports or files meant for local operating system editors.

---

## LEVEL 12: LARGE CODING ASSIGNMENTS (5 Assignments)

---

### Assignment 12.1: InteractiveMenuSystem
- **Goal:** Build an enterprise console application featuring nested menus, typed prompt readers, and input validation.
- **Requirements:**
  1. Model menu nodes with titles, callbacks, and parent links.
  2. Support navigating into sub-menus and returning with `[B] Back` and `[Q] Quit`.
  3. Provide `PromptReader` with bounds checking on integers and regex matching on strings.
  4. Ensure `System.in` is never closed.
- **Reference Solution:**
```java
package com.curriculum.io.menu;

import java.util.*;
import java.util.function.Consumer;

public class InteractiveMenuSystem {
    public static class MenuItem {
        private final String key;
        private final String label;
        private final Runnable action;
        private final List<MenuItem> subItems = new ArrayList<>();
        private MenuItem parent;

        public MenuItem(String key, String label, Runnable action) {
            this.key = key;
            this.label = label;
            this.action = action;
        }

        public void addSubItem(MenuItem item) {
            item.parent = this;
            subItems.add(item);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuItem root = new MenuItem("ROOT", "Main Menu", null);
        
        MenuItem users = new MenuItem("1", "User Management", null);
        users.addSubItem(new MenuItem("1", "List Users", () -> System.out.println("-> Listing 12 active users...")));
        users.addSubItem(new MenuItem("2", "Add User", () -> promptAddUser(scanner)));
        
        MenuItem system = new MenuItem("2", "System Diagnostics", null);
        system.addSubItem(new MenuItem("1", "Memory Usage", () -> System.out.printf("Free Memory: %,d MB%n", Runtime.getRuntime().freeMemory() / (1024 * 1024))));
        
        root.addSubItem(users);
        root.addSubItem(system);
        
        runMenuLoop(root, scanner);
    }

    private static void promptAddUser(Scanner sc) {
        System.out.print("Enter username: ");
        String name = sc.nextLine();
        System.out.printf("User '%s' created successfully.%n", name);
    }

    private static void runMenuLoop(MenuItem current, Scanner sc) {
        while (true) {
            System.out.printf("%n=== %s ===%n", current.label);
            for (MenuItem item : current.subItems) {
                System.out.printf(" [%s] %s%n", item.key, item.label);
            }
            if (current.parent != null) System.out.println(" [B] Back");
            System.out.println(" [Q] Quit");
            System.out.print("Selection: ");
            String choice = sc.nextLine().trim();

            if ("Q".equalsIgnoreCase(choice)) return;
            if ("B".equalsIgnoreCase(choice) && current.parent != null) {
                current = current.parent;
                continue;
            }

            MenuItem selected = current.subItems.stream()
                .filter(i -> i.key.equalsIgnoreCase(choice))
                .findFirst().orElse(null);

            if (selected != null) {
                if (selected.action != null) selected.action.run();
                if (!selected.subItems.isEmpty()) current = selected;
            } else {
                System.out.println("Invalid selection. Try again.");
            }
        }
    }
}
```

---

### Assignment 12.2: FormattedReportGenerator
- **Goal:** Build an academic and financial transcript reporting tool generating aligned ASCII tables with borders, percentages, and currencies.
- **Requirements:**
  1. Model `TranscriptRecord(String courseCode, String courseName, int credits, double score)`.
  2. Calculate letter grades (`A`, `B`, `C`, `D`, `F`) and GPA points (`4.0` down to `0.0`).
  3. Format columns cleanly using `printf` alignment flags.
- **Reference Solution:**
```java
package com.curriculum.io.report;

import java.util.*;

public class FormattedReportGenerator {
    public record CourseGrade(String code, String name, int credits, double score) {
        public String letterGrade() {
            if (score >= 90) return "A";
            if (score >= 80) return "B";
            if (score >= 70) return "C";
            if (score >= 60) return "D";
            return "F";
        }
        public double gradePoints() {
            return switch (letterGrade()) {
                case "A" -> 4.0;
                case "B" -> 3.0;
                case "C" -> 2.0;
                case "D" -> 1.0;
                default -> 0.0;
            };
        }
    }

    public static void generateReport(String studentName, String studentId, List<CourseGrade> grades) {
        System.out.println("================================================================================");
        System.out.printf(" STUDENT TRANSCRIPT | %-25s | ID: %-15s%n", studentName, studentId);
        System.out.println("================================================================================");
        System.out.printf("| %-8s | %-30s | %-7s | %-7s | %-5s |%n", "CODE", "COURSE NAME", "CREDITS", "SCORE", "GRADE");
        System.out.println("|----------+--------------------------------+---------+---------+-------|");

        int totalCredits = 0;
        double totalPoints = 0.0;

        for (CourseGrade g : grades) {
            System.out.printf("| %-8s | %-30.30s | %7d | %7.2f | %-5s |%n",
                g.code(), g.name(), g.credits(), g.score(), g.letterGrade());
            totalCredits += g.credits();
            totalPoints += g.credits() * g.gradePoints();
        }

        System.out.println("================================================================================");
        double gpa = totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
        System.out.printf(" TOTAL CREDITS: %-5d | CUMULATIVE GPA: %-4.2f%n", totalCredits, gpa);
        System.out.println("================================================================================");
    }

    public static void main(String[] args) {
        List<CourseGrade> courses = List.of(
            new CourseGrade("CS101", "Introduction to Computer Science", 4, 94.5),
            new CourseGrade("CS201", "Data Structures & Algorithms", 4, 88.0),
            new CourseGrade("MATH301", "Multivariate Calculus & Linear Algebra", 3, 76.5),
            new CourseGrade("ENG102", "Technical Composition", 2, 91.0)
        );
        generateReport("Grace Hopper", "GH-1906", courses);
    }
}
```

---

### Assignment 12.3: IOBenchmarkSuite
- **Goal:** Comprehensive performance benchmark evaluating `Scanner`, `BufferedReader`, `Files.readString`, and `FileChannel` on multi-megabyte payloads.
- **Requirements:**
  1. Generate synthetic test dataset (50,000 lines).
  2. Implement warm-up cycles to trigger JIT compilation.
  3. Measure elapsed nanoseconds and calculate MB/s throughput.
- **Reference Solution:**
```java
package com.curriculum.io.benchmark;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class IOBenchmarkSuite {
    private static final int LINE_COUNT = 50_000;

    public static void main(String[] args) throws Exception {
        Path tempFile = Files.createTempFile("io_bench_", ".txt");
        try {
            System.out.println("Generating test dataset (" + LINE_COUNT + " lines)...");
            try (BufferedWriter bw = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8)) {
                for (int i = 0; i < LINE_COUNT; i++) {
                    bw.write("RecordID=" + i + ",Timestamp=" + System.currentTimeMillis() + ",Payload=TestExecutionSamplePayloadData\n");
                }
            }
            long fileSize = Files.size(tempFile);
            System.out.printf("File Size: %,d bytes (%.2f MB)%n%n", fileSize, fileSize / (1024.0 * 1024.0));

            // Warmup
            benchBufferedReader(tempFile);

            System.out.println("--- BENCHMARK RESULTS ---");
            long t1 = benchScanner(tempFile);
            long t2 = benchBufferedReader(tempFile);
            long t3 = benchFilesReadString(tempFile);
            long t4 = benchFileChannel(tempFile);

            printRow("Scanner (nextLine)", t1, fileSize);
            printRow("BufferedReader (readLine)", t2, fileSize);
            printRow("Files.readString", t3, fileSize);
            printRow("FileChannel (NIO direct)", t4, fileSize);

        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    private static void printRow(String name, long nanos, long bytes) {
        double ms = nanos / 1_000_000.0;
        double mbps = (bytes / (1024.0 * 1024.0)) / (ms / 1000.0);
        System.out.printf("%-30s: %8.2f ms | %8.2f MB/s%n", name, ms, mbps);
    }

    private static long benchScanner(Path path) throws IOException {
        long start = System.nanoTime();
        try (Scanner sc = new Scanner(path, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) sc.nextLine();
        }
        return System.nanoTime() - start;
    }

    private static long benchBufferedReader(Path path) throws IOException {
        long start = System.nanoTime();
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            while (br.readLine() != null);
        }
        return System.nanoTime() - start;
    }

    private static long benchFilesReadString(Path path) throws IOException {
        long start = System.nanoTime();
        String content = Files.readString(path, StandardCharsets.UTF_8);
        return System.nanoTime() - start;
    }

    private static long benchFileChannel(Path path) throws IOException {
        long start = System.nanoTime();
        try (FileChannel fc = FileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
            while (fc.read(buffer) > 0) {
                buffer.clear();
            }
        }
        return System.nanoTime() - start;
    }
}
```

---

### Assignment 12.4: OutputCaptureTester
- **Goal:** Build an automated testing framework for redirecting and asserting `System.out` and `System.err`.
- **Requirements:**
  1. Implement `AutoCloseable` spy class.
  2. Atomically redirect and restore system streams.
  3. Provide assertion methods: `assertContains()`, `assertLines()`, `assertMatches()`.
- **Reference Solution:**
```java
package com.curriculum.io.testing;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class OutputCaptureTester implements AutoCloseable {
    private final PrintStream originalOut;
    private final PrintStream originalErr;
    private final ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();
    private final ByteArrayOutputStream capturedErr = new ByteArrayOutputStream();

    public OutputCaptureTester() {
        this.originalOut = System.out;
        this.originalErr = System.err;
        System.setOut(new PrintStream(capturedOut, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(capturedErr, true, StandardCharsets.UTF_8));
    }

    public String getStdOut() {
        return capturedOut.toString(StandardCharsets.UTF_8);
    }

    public String getStdErr() {
        return capturedErr.toString(StandardCharsets.UTF_8);
    }

    public void assertOutContains(String substring) {
        if (!getStdOut().contains(substring)) {
            throw new AssertionError("Expected stdout to contain: '" + substring + "', but was:\n" + getStdOut());
        }
    }

    @Override
    public void close() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    public static void main(String[] args) {
        try (OutputCaptureTester tester = new OutputCaptureTester()) {
            System.out.printf("Hello, %s! Total: $%06.2f%n", "Tester", 42.5);
            tester.assertOutContains("Hello, Tester!");
            tester.assertOutContains("$042.50");
        }
        System.out.println("OutputCaptureTester verification passed successfully.");
    }
}
```

---

### Assignment 12.5: CharsetExplorer
- **Goal:** CLI tool exploring text encoding differences, binary hex bytes, and Mojibake generation.
- **Requirements:**
  1. Accept input text via CLI argument.
  2. Encode across UTF-8, ISO-8859-1, UTF-16, US-ASCII.
  3. Render hex dumps and show decoding corruption.
- **Reference Solution:**
```java
package com.curriculum.io.charset;

import java.nio.ByteBuffer;
import java.nio.charset.*;
import java.util.List;

public class CharsetExplorer {
    private static final List<Charset> CHARSETS = List.of(
        StandardCharsets.UTF_8,
        StandardCharsets.ISO_8859_1,
        StandardCharsets.US_ASCII,
        StandardCharsets.UTF_16BE
    );

    public static void explore(String text) {
        System.out.println("=== CHARSET EXPLORER ===");
        System.out.println("Input Text: \"" + text + "\"");
        System.out.printf("Character count: %d%n%n", text.length());

        for (Charset cs : CHARSETS) {
            byte[] encoded = text.getBytes(cs);
            System.out.printf("Charset: %-15s | Byte Length: %-3d | Hex: %s%n",
                cs.name(), encoded.length, bytesToHex(encoded));
            
            // Cross-decode with UTF-8
            String decodedAsUtf8 = new String(encoded, StandardCharsets.UTF_8);
            System.out.printf("  Decoded via UTF-8: \"%s\"%n", decodedAsUtf8);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        String test = args.length > 0 ? args[0] : "Hello, 世界! ☕";
        explore(test);
    }
}
```

---

## LEVEL 13: CAPSTONE PROJECT

### Java I/O Toolkit (`JavaIoToolkit`)

#### Project Overview
`JavaIoToolkit` is an industrial-grade, production-ready command-line tool and API engine providing unified input reading, stream transformation, formatted report rendering, I/O performance benchmarking, and charset transcoding.

#### Architecture & Requirements
1. **Unified Reading Engine (`--read <source> [arg]`):**
   - `console`: Reads interactively from keyboard with prompt.
   - `file <path>`: Reads from local disk file using buffered UTF-8 stream.
   - `string <text>`: Ingests raw string literal.
   - `url <http-url>`: Streams response from remote HTTP/HTTPS endpoint.
2. **Unified Output Engine (`--write <dest> [arg]`):**
   - `console`: Writes to standard output.
   - `file <path>`: Writes to disk file with atomic append or overwrite.
   - `string`: Accumulates in memory and prints summary.
3. **Template Formatting Engine (`--format <template>`):**
   - Applies `printf` format strings with automatic token extraction.
4. **Input Validation Framework (`--validate <type>`):**
   - Supports `int`, `double`, `email`, and `regex=<pattern>`.
5. **High-Performance Benchmark Mode (`--benchmark <file>`):**
   - Compares `Scanner`, `BufferedReader`, `Files.readString`, and `FileChannel` on the target file.
6. **Charset Transcoding Mode (`--charset --from <cs1> --to <cs2> <file>`):**
   - Transcodes text files across character encodings with replacement policies.

#### Evaluation Rubric (100 Points Total)
- **Unified Reading Capabilities (20 pts):** Clean support for console, file, string, and URL sources.
- **Output & Formatting Precision (20 pts):** Flawless format template interpolation and cross-platform line breaks.
- **Robust Validation & Error Handling (20 pts):** Resilient input loops, graceful recovery on malformed input or network failures.
- **Benchmarking & Charset Engine (20 pts):** Accurate high-resolution nanosecond timing and robust charset transcoding.
- **Code Quality & Architecture (20 pts):** Clean separation of concerns, zero resource leaks, strict adherence to Java 21 LTS standards.

---

### LEVEL 13 COMPLETE REFERENCE IMPLEMENTATION: `JavaIoToolkit.java`

```java
package com.curriculum.io.toolkit;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class JavaIoToolkit {

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        try {
            Map<String, List<String>> flags = parseArgs(args);

            if (flags.containsKey("--benchmark")) {
                String path = flags.get("--benchmark").get(0);
                runBenchmark(Path.of(path));
                return;
            }

            if (flags.containsKey("--charset")) {
                String from = flags.get("--from").get(0);
                String to = flags.get("--to").get(0);
                String file = flags.get("--charset").get(0);
                runCharsetConversion(Path.of(file), Charset.forName(from), Charset.forName(to));
                return;
            }

            // Standard Read -> Transform -> Write pipeline
            String content = executeRead(flags);

            if (flags.containsKey("--validate")) {
                String type = flags.get("--validate").get(0);
                boolean valid = validateContent(content, type);
                System.out.printf("Validation [%s]: %s%n", type, valid ? "PASSED" : "FAILED");
                if (!valid) return;
            }

            if (flags.containsKey("--format")) {
                String template = flags.get("--format").get(0);
                content = formatContent(content, template);
            }

            executeWrite(flags, content);

        } catch (Exception e) {
            System.err.printf("[ERROR] Operation failed: %s%n", e.getMessage());
        }
    }

    private static String executeRead(Map<String, List<String>> flags) throws IOException {
        if (!flags.containsKey("--read")) return "";
        List<String> readArgs = flags.get("--read");
        String mode = readArgs.get(0);

        return switch (mode.toLowerCase()) {
            case "console" -> {
                System.out.print("Enter text (EOF to finish): ");
                Scanner sc = new Scanner(System.in);
                yield sc.hasNextLine() ? sc.nextLine() : "";
            }
            case "file" -> {
                Path p = Path.of(readArgs.get(1));
                yield Files.readString(p, StandardCharsets.UTF_8);
            }
            case "string" -> readArgs.get(1);
            case "url" -> {
                URI uri = URI.create(readArgs.get(1));
                try (InputStream in = uri.toURL().openStream();
                     ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    in.transferTo(baos);
                    yield baos.toString(StandardCharsets.UTF_8);
                }
            }
            default -> throw new IllegalArgumentException("Unknown read source: " + mode);
        };
    }

    private static void executeWrite(Map<String, List<String>> flags, String content) throws IOException {
        if (!flags.containsKey("--write")) {
            System.out.println(content);
            return;
        }
        List<String> writeArgs = flags.get("--write");
        String mode = writeArgs.get(0);

        switch (mode.toLowerCase()) {
            case "console" -> System.out.println(content);
            case "file" -> {
                Path p = Path.of(writeArgs.get(1));
                Files.writeString(p, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.printf("Successfully wrote %,d bytes to %s%n", content.length(), p);
            }
            case "string" -> System.out.printf("[In-Memory Buffer] Length: %d chars%n", content.length());
            default -> throw new IllegalArgumentException("Unknown write destination: " + mode);
        }
    }

    private static boolean validateContent(String content, String type) {
        return switch (type.toLowerCase()) {
            case "int" -> content.trim().matches("^-?\\d+$");
            case "double" -> content.trim().matches("^-?\\d+(\\.\\d+)?$");
            case "email" -> content.trim().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
            default -> {
                if (type.startsWith("regex=")) {
                    yield Pattern.compile(type.substring(6)).matcher(content.trim()).matches();
                }
                yield false;
            }
        };
    }

    private static String formatContent(String content, String template) {
        String[] tokens = content.trim().split("\\s+");
        try {
            return String.format(template, (Object[]) tokens);
        } catch (Exception e) {
            return template.replace("%s", content);
        }
    }

    private static void runBenchmark(Path file) throws IOException {
        System.out.printf("=== BENCHMARKING: %s (%,d bytes) ===%n", file.getFileName(), Files.size(file));

        long t1 = bench(file, p -> {
            try (Scanner sc = new Scanner(p, StandardCharsets.UTF_8)) {
                while (sc.hasNextLine()) sc.nextLine();
            }
        });

        long t2 = bench(file, p -> {
            try (BufferedReader br = Files.newBufferedReader(p, StandardCharsets.UTF_8)) {
                while (br.readLine() != null);
            }
        });

        long t3 = bench(file, p -> {
            String s = Files.readString(p, StandardCharsets.UTF_8);
        });

        long t4 = bench(file, p -> {
            try (FileChannel fc = FileChannel.open(p, StandardOpenOption.READ)) {
                ByteBuffer buf = ByteBuffer.allocateDirect(16 * 1024);
                while (fc.read(buf) > 0) buf.clear();
            }
        });

        long size = Files.size(file);
        printBenchResult("Scanner", t1, size);
        printBenchResult("BufferedReader", t2, size);
        printBenchResult("Files.readString", t3, size);
        printBenchResult("FileChannel (NIO)", t4, size);
    }

    @FunctionalInterface
    interface BenchmarkAction { void run(Path p) throws Exception; }

    private static long bench(Path p, BenchmarkAction action) {
        try {
            // Warmup
            action.run(p);
            long start = System.nanoTime();
            action.run(p);
            return System.nanoTime() - start;
        } catch (Exception e) {
            return -1;
        }
    }

    private static void printBenchResult(String name, long nanos, long bytes) {
        double ms = nanos / 1_000_000.0;
        double mbps = (bytes / (1024.0 * 1024.0)) / (ms / 1000.0);
        System.out.printf("%-20s: %8.2f ms | %8.2f MB/s%n", name, ms, mbps);
    }

    private static void runCharsetConversion(Path file, Charset from, Charset to) throws IOException {
        Path dest = Path.of(file.toString() + "." + to.name().toLowerCase());
        System.out.printf("Transcoding %s from %s to %s -> %s%n", file, from.name(), to.name(), dest);

        CharsetDecoder decoder = from.newDecoder().onMalformedInput(CodingErrorAction.REPLACE);
        CharsetEncoder encoder = to.newEncoder().onMalformedInput(CodingErrorAction.REPLACE);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(file), decoder));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(dest), encoder))) {
            reader.transferTo(writer);
        }
        System.out.println("Transcoding complete.");
    }

    private static Map<String, List<String>> parseArgs(String[] args) {
        Map<String, List<String>> map = new LinkedHashMap<>();
        String currentFlag = null;
        for (String arg : args) {
            if (arg.startsWith("--")) {
                currentFlag = arg;
                map.put(currentFlag, new ArrayList<>());
            } else if (currentFlag != null) {
                map.get(currentFlag).add(arg);
            }
        }
        return map;
    }

    private static void printUsage() {
        System.out.println("""
            Java I/O Toolkit (Java 21 LTS)
            Usage:
              java JavaIoToolkit --read <console|file <p>|string <text>|url <u>> [--validate <type>] [--format <tmpl>] [--write <console|file <p>|string>]
              java JavaIoToolkit --benchmark <filePath>
              java JavaIoToolkit --charset <filePath> --from <charset> --to <charset>
            """);
    }
}
```
