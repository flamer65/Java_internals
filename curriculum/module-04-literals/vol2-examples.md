# Java Mastery Curriculum Module 4: Literals - Volume 2 (Examples)

This extremely comprehensive document provides real-world, theoretical, and edge-case examples of literals in Java. Each example contains code, line-by-line explanation, javac/JVM behavior, and expected output.

## SIMPLE EXAMPLES (8+)

### 1. All Integer Literal Bases
```java
public class Bases {
    public static void main(String[] args) {
        int dec = 42;
        int bin = 0b101010;
        int oct = 052;
        int hex = 0x2A;
        System.out.println(dec + " " + bin + " " + oct + " " + hex);
    }
}
```
**Explanation:** Assigns the decimal value 42 using decimal, binary (`0b`), octal (`0`), and hex (`0x`) notations.
**javac/JVM:** The compiler converts all of these into identical `bipush 42` instructions. The JVM does not know which base was used in source.
**Output:** `42 42 42 42`

### 2. Long Literal with L Suffix
```java
public class LongLit {
    public static void main(String[] args) {
        long valid = 2_147_483_648L;
        // long invalid = 2_147_483_648; // Compile error
    }
}
```
**Explanation:** 2,147,483,648 exceeds the max `int` value. Appending `L` specifies a long literal.
**javac/JVM:** `ldc2_w` pushes the long constant from the constant pool. Without `L`, `javac` throws "integer number too large".
**Output:** Valid compilation.

### 3. Float vs Double
```java
public class Floats {
    public static void main(String[] args) {
        double d = 3.14;
        float f = 3.14f; 
        // float bad = 3.14; // Error
    }
}
```
**Explanation:** `3.14` is a double. To assign to a float, you must use the `f` suffix.
**javac/JVM:** Stores in CONSTANT_Double and CONSTANT_Float respectively.
**Output:** Valid compilation.

### 4. Scientific Notation
```java
public class Scientific {
    public static void main(String[] args) {
        double d1 = 1.5e3;
        double d2 = 2.5E-4;
        double d3 = 1e10;
        System.out.println(d1 + ", " + d2 + ", " + d3);
    }
}
```
**Explanation:** Uses `e` or `E` for powers of 10.
**javac/JVM:** Pre-computed at compile-time as standard double primitives.
**Output:** `1500.0, 2.5E-4, 1.0E10`

### 5. Character Literals & Unicode Escapes
```java
public class Chars {
    public static void main(String[] args) {
        char a = 'A';
        char unicode = '\u0041';
        System.out.println(a == unicode);
    }
}
```
**Explanation:** `\u0041` represents 'A'.
**javac/JVM:** The compiler eagerly evaluates `\u0041` during lexing. Both are compiled as integer 65.
**Output:** `true`

### 6. All Escape Sequences
```java
public class Escapes {
    public static void main(String[] args) {
        System.out.println("T\tN\nR\r'\"\\B\bF\f");
    }
}
```
**Explanation:** Demonstrates tab, newline, carriage return, single/double quotes, backslash, backspace, and form feed.
**javac/JVM:** Escape sequences are replaced by their actual ASCII byte representations in the String pool.
**Output:** Varies (Terminal formatting).

### 7. Boolean Literals
```java
public class Bools {
    public static void main(String[] args) {
        boolean t = true;
        boolean f = false;
        // int x = true; // Error
    }
}
```
**Explanation:** true/false are reserved literals. Cannot be cast to numeric types like in C++.
**javac/JVM:** Compiled as `iconst_1` (1) and `iconst_0` (0) in bytecode.
**Output:** N/A.

### 8. The `null` Literal
```java
public class Nulls {
    public static void main(String[] args) {
        String s = null;
        Object[] arr = null;
        // s.length(); // NPE
    }
}
```
**Explanation:** `null` is a special literal representing a lack of reference.
**javac/JVM:** Bytecode uses the `aconst_null` instruction.
**Output:** NullPointerException if dereferenced.

### 9. Underscores in Numeric Literals
```java
public class Underscores {
    public static void main(String[] args) {
        int mil = 1_000_000;
        int hex = 0xFF_EC;
        int bin = 0b0010_0101;
    }
}
```
**Explanation:** Underscores improve readability.
**javac/JVM:** The compiler strips all underscores. They do not appear in bytecode.
**Output:** N/A

## INTERMEDIATE EXAMPLES (6+)

### 1. Text Blocks (Multi-line)
```java
public class TextBlocks {
    public static void main(String[] args) {
        String json = """
            {
                "key": "value"
            }
            """;
        System.out.println(json);
    }
}
```
**Explanation:** Multi-line string literal. Strips incidental leading whitespace.
**javac/JVM:** Equivalent to `"{\n    \"key\": \"value\"\n}\n"` in the class constant pool.
**Output:** Formatted JSON.

### 2. Text Block Escapes (`\s`, `\`)
```java
public class BlockEscapes {
    public static void main(String[] args) {
        String text = """
            Line 1\s
            Line 2 \
            Continued
            """;
        System.out.println(text);
    }
}
```
**Explanation:** `\s` preserves trailing space. `\` suppresses newline.
**javac/JVM:** The compiler evaluates these during constant string evaluation.
**Output:** "Line 1 \nLine 2 Continued\n"

### 3. String Pool Demonstration
```java
public class Pool {
    public static void main(String[] args) {
        System.out.println("hello" == "hello");
        System.out.println(new String("hello") == "hello");
        System.out.println(new String("hello").intern() == "hello");
    }
}
```
**Explanation:** Literals go to the string pool. `new String()` forces heap allocation.
**javac/JVM:** `ldc "hello"` fetches from the pool. `intern()` adds/checks the pool dynamically.
**Output:** `true \n false \n true`

### 4. Constant Folding Proof
```java
public class Folding {
    public static void main(String[] args) {
        String s = "A" + "B" + "C";
    }
}
```
**Explanation:** String concatenation with constants.
**javac/JVM:** `javac` folds this to `"ABC"`. `javap -c` shows a single `ldc "ABC"`.
**Output:** N/A

### 5. Hex Floating-Point
```java
public class HexFloat {
    public static void main(String[] args) {
        double d = 0x1.0p3; // 1.0 * 2^3 = 8.0
        double d2 = 0x1.8p1; // 1.5 * 2^1 = 3.0
    }
}
```
**Explanation:** Base-16 mantissa, base-2 exponent. `p` instead of `e`.
**javac/JVM:** Processed into standard IEEE 754 representations.
**Output:** N/A

### 6. The Octal Trap
```java
public class OctalTrap {
    public static void main(String[] args) {
        int[] ports = {80, 443, 8080, 010};
        System.out.println(ports[3]);
    }
}
```
**Explanation:** Leading 0 means octal. `010` is 8, not 10.
**javac/JVM:** Compiled as value 8.
**Output:** `8`

### 7. Numeric Literal Type Determination
```java
public class TypeDemo {
    public static void main(String[] args) {
        System.out.println(((Object)42).getClass()); // Integer
        System.out.println(((Object)42L).getClass()); // Long
        System.out.println(((Object)42.0).getClass()); // Double
        System.out.println(((Object)42.0f).getClass()); // Float
    }
}
```
**Explanation:** Autoboxing reveals the implicit type of literals.
**javac/JVM:** Values are boxed to their respective Wrapper classes using `valueOf()`.
**Output:** Class names printed.

## ADVANCED EXAMPLES (4+)

### 1. Unicode Escape Danger
```java
public class UnicodeDanger {
    public static void main(String[] args) {
        // \u000A System.out.println("executed!");
    }
}
```
**Explanation:** `\u000A` is a newline. The lexer replaces it before parsing comments, so the next code runs!
**javac/JVM:** The code acts exactly as if a newline broke the comment.
**Output:** `executed!`

### 2. Bytecode for All Literal Types
*Use `javap -c` on a class containing various literals.*
**javac/JVM:**
- `int`: `iconst_X` or `bipush`
- `long`: `ldc2_w`
- `float`/`String`: `ldc`
- `double`: `ldc2_w`
- `boolean`: `iconst_1` / `iconst_0`
- `null`: `aconst_null`

### 3. Supplementary Unicode Characters
```java
public class Emoji {
    public static void main(String[] args) {
        String s = "😀";
        System.out.println(s.length()); 
        System.out.println(s.codePointCount(0, s.length()));
    }
}
```
**Explanation:** Emoji are outside the BMP and use 2 `char`s (surrogate pair).
**javac/JVM:** String length is char-array length (2), but it's 1 logical code point.
**Output:** `2 \n 1`

### 4. Constant Pool Inspection
Run `javap -v` to see how literals are physically stored:
- `CONSTANT_String`
- `CONSTANT_Integer`
- `CONSTANT_Float`
- `CONSTANT_Long`
- `CONSTANT_Double`

### 5. String Concat (invokedynamic)
```java
public class Concat {
    public static void main(String[] args) {
        int x = 42;
        String s = "" + x;
    }
}
```
**Explanation:** Mixing empty string literal with variable.
**javac/JVM:** Since Java 9, uses `invokedynamic` (StringConcatFactory) instead of `StringBuilder`.
**Output:** N/A

## PRODUCTION EXAMPLES (3+)

### 1. Configuration Constants
```java
public static final int MAX_CONNECTIONS = 100;
public static final String API_URL = "https://api.example.com";
```
*Best practice:* Uppercase, `final`, well-defined literals.

### 2. Text Blocks for SQL/JSON
```java
String sql = """
    SELECT id, name
    FROM users
    WHERE active = true
    """;
```
*Best practice:* Keeps complex strings readable without massive concatenation.

### 3. Binary Masks
```java
static final int READ = 0b001;
static final int WRITE = 0b010;
static final int EXEC = 0b100;
```
*Best practice:* Directly reflects the bitwise nature of the flags.

## WRONG EXAMPLES — COMPILER ERRORS (12+)
1. `float f = 3.14;` (double to float without suffix)
2. `long x = 2147483648;` (integer literal too large)
3. `int x = 0b10102;` (invalid binary digit '2')
4. `int x = 08;` (invalid octal digit '8')
5. `char c = '';` (empty character literal)
6. `char c = 'AB';` (too many characters)
7. `int x = _100;` (underscore at start)
8. `int x = 100_;` (underscore at end)
9. `float f = 3_.14f;` (underscore adjacent to decimal)
10. `boolean b = 1;` (int cannot be converted to boolean)
11. `int x = null;` (null cannot be assigned to primitive)
12. `String s = 'A';` (char literal assigned to String)
13. `String tb = """text"""` (text block opening must be followed by newline)

## WRONG EXAMPLES — RUNTIME ERRORS (5+)
1. `String s = null; s.length();` → `NullPointerException`
2. `String s = null; System.out.println("" + s);` → Prints `"null"` (Surprise!)
3. `int x = Integer.MAX_VALUE + 1;` → Silently wraps to negative (overflow).
4. `Integer.parseInt("0xFF")` → `NumberFormatException` (use radix 16).
5. `char c = (char) -1;` → `\uFFFF` (Surprising wrap).

## EDGE CASES (6+)
1. `0.` (=0.0), `.5` (=0.5), `3.` (=3.0) are all valid.
2. `0x0.0p0` is a valid hexadecimal floating-point for `0.0`.
3. `1___000___000` multiple underscores are valid.
4. `"" != null` but `"" == ""` (empty strings are pooled).
5. `'\0'` is null char (0), NOT `null`.
6. `true == true`, `null == null` return `true`.
7. `0L == 0` evaluates to true (widening conversion).

## PERFORMANCE EXAMPLES (2+)
1. `"a" + "b"` is concatenated at compile time (no runtime cost). `s1 + s2` incurs runtime allocation.
2. `String.intern()` adds to pool, saving memory but costing CPU.

## INTERVIEW EXAMPLES (3+)
1. **"String literal vs new String()?"** -> Literal goes to pool. `new` creates a distinct heap object.
2. **"How many objects created: `new String("hello")`?"** -> 1 (if "hello" is in pool) or 2 (pool + heap).
3. **"What is `010 + 1`?"** -> 9 (Octal 8 + 1).
