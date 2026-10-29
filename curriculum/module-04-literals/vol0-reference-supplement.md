# Module 4: Literals - Volume 0 (Reference Supplement)

Target: Java 21 LTS, IntelliJ IDEA

This supplement provides deep, implementation-level explanations for advanced topics, internals, and edge cases referenced in the practice and assessment materials but not fully detailed in the core theory volumes.

## 1. String Pool Internals & Implementation Details

### The `StringTable` Hash Table
The String Pool is implemented as a fixed-size hash table (an array of buckets containing linked lists of string entries) called `StringTable` within the JVM (written in C++).
When a string literal is encountered or `String.intern()` is called, the JVM computes the hash code of the string and looks up the corresponding bucket. 
- If an identical string exists, its reference is returned.
- If not, the string object is added to the pool.

> [!CAUTION]
> Because it uses open hashing with linked lists, hash collisions can degrade lookup time from $O(1)$ to $O(N)$ where $N$ is the length of the list in that bucket. 

### `-XX:StringTableSize` and Performance
Prior to Java 7, the String Pool lived in the PermGen space (which was small and rarely garbage-collected). Since Java 7, it resides in the main Heap.
The default `StringTableSize` in modern Java (Java 11+) is typically 65,536 or 60,013 buckets. 
If your application interns millions of dynamic strings (e.g., user IDs, generated JSON keys), you will cause severe hash collisions.

> [!TIP]
> You can tune this via `-XX:StringTableSize=N`. You can also monitor the pool using `-XX:+PrintStringTableStatistics` on JVM exit to see bucket lengths and distribution.

### Garbage Collection Interaction
Strings in the String Pool are not immune to Garbage Collection. If a string was added dynamically via `intern()` and there are no longer any active references to it from the application code, the GC will remove it from the `StringTable` to free heap space. However, literal strings defined directly in the code (e.g., `"Hello"`) are referenced by the loaded class's Constant Pool, meaning they will not be garbage collected until the class itself is unloaded.

### Proving Identity with `System.identityHashCode()`
To definitively prove that two string references point to the exact same object in the String Pool without relying on `==`, you can use `System.identityHashCode(Object)`.

```java
String a = "test";
String b = new String("test").intern();
String c = new String("test");

System.out.println(System.identityHashCode(a)); // e.g. 1550089733
System.out.println(System.identityHashCode(b)); // e.g. 1550089733 (Matches!)
System.out.println(System.identityHashCode(c)); // e.g. 865113938 (Different!)
```

### Runtime Memory Measurement for String Pool Analysis
To analyze the memory footprint of interning strings, you can measure heap usage before and after interning a large dataset.

```java
Runtime rt = Runtime.getRuntime();
rt.gc(); // Suggest GC to clean up
long before = rt.totalMemory() - rt.freeMemory();

for (int i = 0; i < 1_000_000; i++) {
    String s = String.valueOf(i).intern();
}

long after = rt.totalMemory() - rt.freeMemory();
System.out.println("Memory used: " + (after - before) / 1024 / 1024 + " MB");
```

## 2. Text Block Indentation Stripping Algorithm

The compiler processes text blocks using the equivalent of the `String.stripIndent()` method (introduced in Java 15). The algorithm is precise:

1. **Re-indentation**: It finds the line with the least amount of leading whitespace. This "minimum indentation" becomes the baseline.
2. The compiler removes exactly that baseline amount of whitespace from every line.
3. **Trailing whitespace**: It completely strips trailing whitespace from all lines, unless explicitly preserved using the `\s` escape sequence.
4. **Closing Quote Position**: If the closing `"""` is on its own line and shifted to the left, *its* whitespace determines the minimum indentation for the entire block.

> [!IMPORTANT]
> The exact rules can be emulated dynamically on regular strings using `myString.stripIndent()`.

## 3. `StringConcatFactory` and `invokedynamic`

Historically (Java 8 and earlier), compiling `"a" + var + "c"` resulted in `new StringBuilder().append("a").append(var).append("c").toString()`. 

Since Java 9 (JEP 280), the compiler uses `invokedynamic`.
Instead of generating bytecode for `StringBuilder`, javac emits an `invokedynamic` instruction pointing to `java.lang.invoke.StringConcatFactory`.
At runtime, the JVM dynamically links this call to the most optimal concatenation strategy available (e.g., pre-allocating a byte array of the exact required size based on the variables).

> [!NOTE]
> This means String concatenation using `+` is significantly faster and creates less garbage in Java 9+ compared to Java 8, sometimes making manual `StringBuilder` usage unnecessary for simple expressions.

## 4. `javap -v` Constant Pool Entry Formats

When inspecting compiled bytecode with `javap -v`, literals appear in the Constant Pool.

| Literal Type | `javap` Constant Pool Tag | Internal Structure |
|---|---|---|
| `int`, `short`, `byte`, `boolean` | `CONSTANT_Integer` | `bytes`: 4-byte big-endian int |
| `float` | `CONSTANT_Float` | `bytes`: IEEE 754 single-precision |
| `long` | `CONSTANT_Long` | `high_bytes`, `low_bytes` (occupies 2 slots in pool) |
| `double` | `CONSTANT_Double` | `high_bytes`, `low_bytes` (occupies 2 slots in pool) |
| `String` | `CONSTANT_String` | `string_index` (Points to a `CONSTANT_Utf8`) |
| Characters inside String | `CONSTANT_Utf8` | `length`, `bytes` (Modified UTF-8 encoding) |

### Programmatic Inspection using `ProcessBuilder`
To build a tool that analyzes bytecode dynamically (like the Capstone Project in Vol 6), you can invoke `javap` from within Java using `ProcessBuilder`.

```java
ProcessBuilder pb = new ProcessBuilder("javap", "-v", "-c", "MyClass.class");
Process process = pb.start();
try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
    String line;
    while ((line = reader.readLine()) != null) {
        if (line.contains("CONSTANT_String")) {
            System.out.println("Found String Literal: " + line);
        }
    }
}
```

## 5. Numeric Literal Parsing & Formatting

When building a literal analyzer or converter, specific API methods are required to handle Java's base prefixes.

### Formatting Numbers
- `Integer.toBinaryString(int i)`
- `Integer.toHexString(int i)`
- `Integer.toOctalString(int i)`

### Parsing Strings with Prefixes (`Integer.decode()`)
While `Integer.parseInt(String s, int radix)` is common, it requires you to manually strip the `0x` or `0b` prefix and pass the radix.
`Integer.decode(String nm)` automatically detects the prefix (`0x` for hex, `0` for octal) and parses accordingly.

> [!WARNING]
> `Integer.decode()` does NOT support the `0b` binary prefix. For binary literals, you must manually check `if (s.startsWith("0b"))` and use `Integer.parseInt(s.substring(2), 2)`.

### NumberFormatException Handling
When parsing user input or configuration literals, `NumberFormatException` (a `RuntimeException`) is thrown for invalid formats.
```java
try {
    int port = Integer.parseInt("08080"); // Error! 8 is not octal
} catch (NumberFormatException e) {
    System.err.println("Invalid numeric literal provided.");
}
```

## 6. Unicode Encoding and Supplementary Characters

### UTF-8 vs UTF-16 vs Code Points
- **UTF-8**: Used externally (files, network). Variable length (1-4 bytes).
- **UTF-16**: Used internally by Java `char` and `String` (prior to Java 9 Compact Strings). Characters in the Basic Multilingual Plane (BMP) take 16 bits (1 char).
- **Code Point**: An integer representing a logical Unicode character.

### Surrogate Pairs (Emojis)
Characters outside the BMP (like emojis, e.g., 😀 U+1F600) cannot fit into a single 16-bit `char`. They are encoded as two `char` values (a "surrogate pair").

- `String.length()` returns the number of 16-bit `char`s (code units). For "😀", `length()` is `2`.
- `String.codePointCount(0, s.length())` returns the actual number of logical characters. For "😀", it returns `1`.

### APIs for Supplementary Characters
```java
int codePoint = 0x1F600; // 😀
// Convert code point to char array (handles surrogate pairs automatically)
char[] chars = Character.toChars(codePoint); 
String emojiString = new String(chars);

// Extracting code point from String
int extractedPoint = emojiString.codePointAt(0);
System.out.println(extractedPoint == 0x1F600); // true
```
