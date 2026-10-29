# Module 4 Volume 3: Literals - Best Practices, Common Mistakes, and Debugging

Target: Java 21 LTS | Environment: IntelliJ IDEA

As a Senior Java Engineer, writing clean, efficient, and bug-free code is paramount. Literals are the building blocks of Java programs, and how you use them directly impacts the readability, maintainability, and performance of your application. This volume covers the best practices for using literals, common pitfalls at all experience levels, and how to debug literal-related issues.

---

## PART 6: BEST PRACTICES

### Numeric Literal Readability

1. **Always use underscores for large numbers**: Introduced in Java 7, underscores (`_`) drastically improve the readability of large numeric literals.
   - **Good**: `1_000_000_000`
   - **Bad**: `1000000000` (is that one billion or one hundred million?)
2. **Use hex for colors, memory addresses, and byte values**: Hexadecimal naturally aligns with bitwise operations and memory layouts.
   - **Good**: `int red = 0xFF0000;`
3. **Use binary for bit masks and flags**: Binary literals (`0b`) make it immediately obvious which bits are set.
   - **Good**: `int flags = 0b0010_0101;`
4. **Prefer decimal for general-purpose numbers**: Unless there is a domain-specific reason to use hex, octal, or binary, stick to decimal for cognitive ease.
5. **Always use uppercase `L` for long suffix**: The lowercase `l` looks identical to the number `1` in many fonts.
   - **Good**: `long id = 123456789L;`
   - **Bad**: `long id = 123456789l;`
6. **Always use lowercase `f` for float suffix**: While uppercase `F` is valid, `f` is the universal convention in the Java ecosystem.
   - **Good**: `float pi = 3.14f;`

### String Literal Best Practices

1. **Use text blocks for multi-line strings**: Java 15 introduced text blocks (`"""`). Use them for JSON, SQL, HTML, and XML instead of concatenating strings with `+` and `\n`.
   - **Good**:
     ```java
     String query = """
             SELECT id, name
             FROM users
             WHERE status = 'ACTIVE'
             """;
     ```
2. **Use text blocks with `\` line continuation for long single-line strings**: If a string is too long for one line but shouldn't contain newlines, use a text block and the `\` escape character.
3. **Be careful with text block trailing whitespace**: Use the `\s` escape sequence to explicitly preserve trailing spaces if your domain requires it.
4. **Prefer String literals over `new String()`**: String literals are automatically interned by the JVM, saving memory and speeding up comparisons.
   - **Good**: `String s = "hello";`
   - **Bad**: `String s = new String("hello");` (creates an unnecessary object on the heap).
5. **Use `String.format()` or `.formatted()`**: For complex string construction involving literals and variables, prefer `formatted()` (Java 15+).
   - **Good**: `"User %s has %d points".formatted(name, score)`

### Character Literal Best Practices

1. **Use character literals for known characters**: Use `'A'`, `'\n'`, etc., rather than their integer equivalents.
2. **Use named constants for special characters**:
   - **Good**: `static final char NEWLINE = '\n';`
3. **Use `Character.isXxx()` methods**: Instead of comparing against literal ranges (e.g., `if (c >= 'a' && c <= 'z')`), use `Character.isLowerCase(c)` for better Unicode support.
4. **Be aware of supplementary character limitations**: A `char` literal can only represent characters in the Basic Multilingual Plane (BMP). Emojis and other supplementary characters require two `char`s (a surrogate pair) or a `String`.

### Boolean Literal Best Practices

1. **Never compare booleans to `true` or `false`**:
   - **Good**: `if (isValid)` or `if (!isValid)`
   - **Bad**: `if (isValid == true)`
2. **Use meaningful boolean variable names**: Variable names should read naturally as a question or state.
   - **Good**: `isConnected`, `hasPermission`, `isDone`

### `null` Best Practices

1. **Minimize use of null**: Prefer `java.util.Optional` for return types that might not have a value.
2. **Check for null before dereferencing**: Always protect against `NullPointerException`.
3. **Use `Objects.requireNonNull()`**: Fail fast during parameter validation.
   - `this.name = Objects.requireNonNull(name, "Name cannot be null");`
4. **Use `Objects.requireNonNullElse()`**: For providing default values.
5. **Effective Java Item 54**: Always return empty collections or arrays, never `null`.

### Constant Definition Best Practices

1. **Use `static final` for true constants**: Naming convention is `UPPER_SNAKE_CASE`.
   - `static final int MAX_RETRIES = 3;`
2. **Use `enum` instead of integer constants**: Provides type safety and namespace.
   - **Good**: `enum Status { OPEN, CLOSED }`
   - **Bad**: `int STATUS_OPEN = 1;`
3. **Group related constants**: Put them in a dedicated final class with a private constructor.

---

## PART 7: COMMON MISTAKES

### Beginner Mistakes (10)

1. **The octal trap**: Prefixing a number with `0` makes it octal. `int x = 010;` is `8`, not `10`.
2. **Missing `f` suffix**: `float f = 3.14;` fails to compile because `3.14` is a `double` and cannot implicitly narrow to `float`. Fix: `3.14f`.
3. **Missing `L` suffix for large longs**: `long max = 3000000000;` fails because the literal is treated as an `int` first, and 3 billion is out of bounds for an `int`. Fix: `3000000000L`.
4. **Confusing `char` and `String`**: `'A'` is a `char` (primitive, 16-bit). `"A"` is a `String` (Object). They are completely incompatible.
5. **Confusing `null` with `"null"`**: `null` is a reference to nothing. `"null"` is a string containing 4 characters.
6. **Confusing empty string `""` with `null`**: `""` is an instantiated String object with 0 length.
7. **Using `==` for String comparison**: `==` checks identity (memory address). Always use `.equals()` to check state (value).
8. **Wrong escape sequence**: `'\N'` is invalid. Escape sequences are case-sensitive (use `'\n'`).
9. **Putting code after `//` containing `\u000A`**: The Unicode escape `\u000A` is processed *before* lexical analysis. It physically acts as a newline, pushing code after it to a new line, which can break compilation.
10. **Using `new String("literal")`**: It wastes memory by creating a new String instance on the heap instead of using the String pool.

### Intermediate Mistakes (7)

1. **Underscore placement errors**: Underscores cannot be placed at the beginning or end of a literal, or adjacent to a decimal point or suffix (e.g., `_10`, `10.0_`, `10_f` are invalid).
2. **Text block indentation errors**: Not understanding the incidental whitespace stripping algorithm. The compiler strips whitespace up to the leftmost non-whitespace character or the closing `"""`.
3. **String concatenation in loops**: Using `+` in a `for` loop creates intermediate String objects, causing GC pressure. Use `StringBuilder` instead.
4. **Assuming `String.intern()` is always beneficial**: Interning strings aggressively can bloat the String table, slowing down intern lookups and garbage collection.
5. **Not knowing that `"" + null` produces `"null"`**: String concatenation with a null reference converts it to the string `"null"`.
6. **Precision assumptions with floating-point literals**: `double` and `float` cannot represent all decimal fractions exactly (e.g., `0.1 + 0.2` is `0.30000000000000004`).
7. **Forgetting text block opening `"""` rules**: The opening `"""` must be followed by a line terminator. `String s = """hello""";` is invalid.

### Senior-Level Pitfalls (5)

1. **String pool memory pressure**: In high-throughput applications processing millions of unique strings (e.g., parsed IDs), calling `.intern()` will severely degrade performance.
2. **Constant folding side effects**: `static final` primitives and Strings are resolved at compile time and inlined into calling classes. If you update the constant in a library and recompile *only* the library, clients using the old JAR will retain the old inlined value.
3. **Unicode escape security vulnerabilities**: Because Unicode escapes are processed early, malicious code can be hidden in comments or strings.
4. **Supplementary character handling**: `String.length()` counts UTF-16 code units (16-bit `char`s), not actual Unicode characters (code points). An emoji counts as `2`. Use `codePointCount()` for true character length.
5. **StringTable tuning**: For applications with large String pools, failing to tune `-XX:StringTableSize` can lead to long hash collision chains and degraded performance.

---

## PART 8: DEBUGGING

### IntelliJ IDEA for Literals

- **Warnings**: IntelliJ flags integer literals with leading zeros (`010`) warning you of accidental octal values.
- **Inspections**: Highlights unnecessary `new String("...")` calls and offers a quick fix to remove them.
- **Quick-fixes**: Press `Alt+Enter` on concatenated strings to convert them to Text Blocks automatically.
- **Readability**: Offers a quick fix to inject underscore separators into large numeric literals.
- **Highlighting**: Invalid escape sequences are highlighted in red instantly.
- **Evaluate Expression**: During debugging (`Alt+F8`), you can test how complex literals (like hex/binary math) evaluate in the current context.

### Common Error Messages

- `integer number too large`: You forgot the `L` suffix on a long literal.
- `incompatible types: possible lossy conversion from double to float`: You forgot the `f` suffix on a float literal.
- `illegal escape character`: You used an unrecognized backslash sequence.
- `empty character literal`: You wrote `''`. A char must contain exactly one character.
- `unclosed character literal`: You wrote `'A` instead of `'A'`.
- `unclosed string literal`: You missed the closing double quote `"`.
- `illegal line end in string literal`: You pressed Enter inside a regular `""` string. Use `\n` or a Text Block `"""`.

### JVM Inspection

- `javap -v MyClass.class`: Decompiles the bytecode to show the Constant Pool. You can see how string literals and large numbers are stored in `CONSTANT_String_info`, `CONSTANT_Integer_info`, etc.
- `javap -c MyClass.class`: Shows the bytecode instructions. You can see `ldc` (load constant), `bipush` (push byte), `iconst_1` (load integer 1), revealing how the JVM optimizes literal loading based on value size.
- `-XX:+PrintStringTableStatistics`: A JVM flag that dumps statistics about the String pool upon JVM exit, crucial for diagnosing memory pressure from interned literals.
