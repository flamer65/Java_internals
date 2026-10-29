# Module 4 Volume 4: Literals - Interview Preparation

Target: Java 21 LTS | Environment: IntelliJ IDEA

This volume contains a comprehensive set of interview questions designed for Junior, Mid-Level, and Senior Java engineers, specifically focusing on Literals.

---

## BASIC QUESTIONS

**1. What is a literal in Java?**
A literal is a source code representation of a fixed value. It is a value written directly into the code without requiring computation or instantiation, such as `10`, `3.14`, `'A'`, `"Hello"`, or `true`.

**2. What are the different types of literals?**
- Integer literals (`int`, `long`)
- Floating-point literals (`float`, `double`)
- Boolean literals (`true`, `false`)
- Character literals (`char`)
- String literals (`String` and Text Blocks)
- The Null literal (`null`)

**3. What is the default type of integer and floating-point literals?**
By default, any whole number literal (like `42`) is of type `int`. Any decimal number literal (like `3.14`) is of type `double`.

**4. How do you specify a long literal? A float literal?**
- For a `long`, append an `L` or `l` (uppercase is strongly preferred for readability): `100L`.
- For a `float`, append an `F` or `f`: `3.14f`.

**5. What are the different number bases for integer literals?**
- **Decimal** (base 10): Default (e.g., `10`)
- **Hexadecimal** (base 16): Prefix `0x` or `0X` (e.g., `0xA`)
- **Octal** (base 8): Prefix `0` (e.g., `012`)
- **Binary** (base 2): Prefix `0b` or `0B` (e.g., `0b1010`)

**6. What is a text block? How is it different from a regular string?**
A text block (introduced in Java 15) is a multi-line string literal enclosed in `"""`. It automatically preserves newlines and handles incidental whitespace (indentation) stripping, making it ideal for embedding JSON, HTML, or SQL. Regular strings (`""`) must be concatenated and manually escaped for newlines (`\n`).

**7. What escape sequences does Java support?**
Common escape sequences include:
- `\n` (newline)
- `\t` (tab)
- `\r` (carriage return)
- `\"` (double quote)
- `\'` (single quote)
- `\\` (backslash)

**8. What is the `null` literal? Can it be assigned to primitives?**
`null` represents the absence of an object reference. It can only be assigned to reference types (Objects, arrays, Strings). It cannot be assigned to primitive types (`int`, `boolean`, etc.).

**9. What is the difference between `'A'` and `"A"`?**
`'A'` is a character literal (primitive type `char`, 16-bit integer). `"A"` is a String literal (an instance of the `java.lang.String` class containing one character).

**10. What are boolean literals? Are they keywords?**
The boolean literals are `true` and `false`. Technically, in the Java Language Specification, they are boolean literals, not keywords, though they are reserved words that cannot be used as identifiers.

---

## INTERMEDIATE QUESTIONS

**1. Explain the String Pool. How do string literals interact with it?**
The String Pool is a special storage area in the Java heap space. When a string literal is created, the JVM checks the pool. If an identical string exists, the reference to the existing string is returned. If not, a new string is created and placed in the pool. This conserves memory and allows fast identity (`==`) comparisons.

**2. What is the difference between `String s = "hello"` and `String s = new String("hello")`?**
- `"hello"` uses the String Pool. If `"hello"` already exists, no new object is created.
- `new String("hello")` explicitly forces the JVM to create a new String object on the heap, completely bypassing the pool for the new object (though the literal `"hello"` itself is resolved from the pool).

**3. What is constant folding? How does javac handle literal expressions?**
Constant folding is a compile-time optimization. If the compiler sees an expression consisting entirely of literals (e.g., `int x = 5 * 10;`), it evaluates it at compile time and outputs the result (`int x = 50;`) directly into the bytecode.

**4. How are integer literals stored in bytecode?**
The JVM has optimized instructions for loading literals to save space:
- `iconst_m1` to `iconst_5`: Loads values -1 to 5.
- `bipush`: Loads byte values (-128 to 127).
- `sipush`: Loads short values (-32768 to 32767).
- `ldc`: Loads larger constants from the Constant Pool.

**5. What is the octal literal trap? Give an example.**
Prefixing an integer literal with `0` makes it octal (base 8). A developer might write `010` to align numbers visually, but `010` evaluates to decimal `8`, causing logical bugs.

**6. Explain text block indentation stripping.**
The Java compiler calculates a common whitespace prefix for all non-empty lines and the closing `"""`. It strips this exact amount of whitespace from the beginning of every line in the text block, allowing you to indent the block to align with your code without baking those spaces into the actual string value.

**7. How does Java handle Unicode escapes? When are they processed?**
Unicode escapes (e.g., `\u0041` for 'A') are processed at the very beginning of compilation, *before* lexical analysis. This means a Unicode escape for a newline (`\u000A`) inside a comment will actually terminate the comment and push subsequent code to a new line, potentially breaking compilation.

**8. What is `String.intern()` and how does it relate to the String Pool?**
The `.intern()` method ensures a string is in the pool. When called, if the pool already contains a string equal to this `String` object, the string from the pool is returned. Otherwise, this `String` object is added to the pool.

**9. How many objects are created by `String s = new String("hello")`?**
Generally, two objects. First, the literal `"hello"` creates an object in the String pool (if it didn't exist). Second, the `new` keyword creates a distinct String object on the regular heap space.

**10. What is the difference between `null`, `""`, and `"null"`?**
- `null`: A literal representing no object reference.
- `""`: A valid String object with a length of 0.
- `"null"`: A valid String object with a length of 4, containing the characters n, u, l, l.

---

## ADVANCED QUESTIONS

**1. Explain how Unicode escapes are processed before lexical analysis. What security implications does this have?**
Because Unicode escapes are evaluated first, malicious code can be hidden in string literals or comments. For example:
```java
// Check if admin \u000A isAdmin = true;
```
The `\u000A` is processed as a newline, terminating the comment, and `isAdmin = true;` becomes executable code.

**2. How does the JVM store string constants in the constant pool?**
In the `.class` file, strings are stored using two structures: `CONSTANT_Utf8_info`, which holds the actual byte array representing the string text in Modified UTF-8 format, and `CONSTANT_String_info`, which acts as a pointer to the Utf8 entry.

**3. What is the text block `\s` and `\` escape? When were they introduced?**
Introduced alongside text blocks:
- `\s`: Represents a single space. Useful for preventing the compiler from stripping trailing whitespace.
- `\`: Acts as a line continuation character, preventing the inclusion of a newline at that point in the resulting string.

**4. How does javac select bytecode instructions for integer literals based on value range?**
- `[-1, 5]`: Uses `iconst_<i>` (1 byte instruction, fast).
- `[-128, 127]`: Uses `bipush` (2 byte instruction).
- `[-32768, 32767]`: Uses `sipush` (3 byte instruction).
- Outside this range: Added to the Constant Pool and loaded via `ldc` (Load Constant).

**5. Explain hexadecimal floating-point literals. What is the binary exponent?**
Hex floating-point literals represent exact binary values, avoiding decimal-to-binary conversion errors. They start with `0x`, contain a hex fraction, and use a `p` (instead of `e`) followed by a decimal exponent that represents a power of 2. Example: `0x1.0p3` is 1.0 * 2^3 = 8.0.

**6. What are supplementary Unicode characters? How does Java handle them?**
Characters outside the Basic Multilingual Plane (BMP) (like emojis) take up more than 16 bits. Java's `char` is 16-bit. Therefore, supplementary characters are represented by a "surrogate pair" — two `char` values. You cannot express an emoji using a single `char` literal; it requires a `String`.

**7. How does `StringConcatFactory` handle string concatenation with literals since Java 9?**
Prior to Java 9, `javac` converted `"a" + var + "b"` to `StringBuilder` calls. Since Java 9, it uses `invokedynamic` to call `StringConcatFactory.makeConcatWithConstants`. This defers the exact concatenation strategy to JVM runtime, allowing for powerful optimizations (like pre-sizing arrays) without recompiling code.

**8. Explain constant pool deduplication for string literals.**
At compile time, `javac` ensures that identical string literals within the same class resolve to a single Constant Pool entry. At runtime, the JVM's String Pool ensures identical literals across *all* classes resolve to the same heap object.

**9. What is `-XX:StringTableSize` and how does it affect String Pool performance?**
The String Pool is implemented as a fixed-size hash table. If you have millions of unique interned strings and a small table size, hash collisions increase drastically, making `.intern()` and pool lookups O(n) instead of O(1). `-XX:StringTableSize` tunes the number of hash buckets.

**10. How does GC interact with the String Pool?**
Prior to Java 7, the pool was in PermGen (rarely garbage collected, leading to OutOfMemoryErrors). Since Java 7, the String Pool lives in the regular Heap space. Unreferenced interned strings are eligible for garbage collection just like regular objects.

---

## TRICKY / GOTCHA QUESTIONS

**1. What does `010 + 1` evaluate to?**
`9`. The leading zero makes `010` an octal literal, equal to decimal 8. 8 + 1 = 9.

**2. Is `true` a keyword in Java?**
No. `true`, `false`, and `null` are literal values, though they are reserved words and cannot be used as identifiers.

**3. What is the output of `System.out.println("" + null);`?**
`"null"`. String concatenation handles `null` references by converting them to the string literal `"null"`. No NullPointerException is thrown.

**4. Is `String s = null; s += "hello";` valid? What does s contain?**
Yes, it is valid. `s` will contain `"nullhello"`.

**5. How many objects are created by: `String s = "a" + "b" + "c";`?**
Just one. The compiler performs constant folding and compiles this directly to `String s = "abc";`.

**6. What is `'\u0027'`?**
It represents a single quote character. However, using it in code like `char c = '\u0027';` will cause a compilation error because it evaluates early to `char c = ''';` (an unescaped quote), breaking the parser.

**7. Is `float f = 0;` valid?**
Yes. `0` is an `int` literal, and Java allows implicit widening conversions from `int` to `float`.

**8. What is `1_000 + 0x3E8 + 0b1111101000 + 01750`?**
`4000`. All four literals represent the decimal number 1000 in different bases (Decimal, Hex, Binary, Octal).

**9. Is `"" == ""` true?**
Yes. Empty string literals are still string literals, meaning they are placed in the String pool. Both references point to the exact same object.

**10. What does `(int) 1e2` evaluate to?**
`100`. `1e2` is scientific notation for `1 * 10^2`. It is implicitly a `double` (100.0), which is then cast to an `int` (100).

---

## MCQs (Sample)

**1. Which of the following is a valid long literal?**
A) 123456789.0L
B) 123,456,789L
C) 123_456_789L
D) 0x123L.0
*Answer: C. Underscores are valid separators. Commas are invalid. Decimals make it a floating-point number, rendering L invalid.*

**2. What is the default type of `3.14`?**
A) float
B) double
C) BigDecimal
D) Object
*Answer: B. Decimal literals without a suffix are explicitly `double` in Java.*

*(Instructor Note: Expand this section in live delivery to include Output Prediction, Code Tracing, and Scenario Questions as outlined in the curriculum guide.)*
