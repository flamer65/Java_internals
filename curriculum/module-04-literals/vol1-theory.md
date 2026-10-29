# MODULE 4: LITERALS — Volume 1: Theory & Internals

## PART 1: DEFINITION

### What is a literal?
A **literal** is a fixed value that appears directly in source code. It represents a constant value of a specific type. In Java, literals are the most fundamental building blocks of expressions.

### Why do programming languages have literals?
Programming languages require literals to express constant values directly without the need for variables, computation, or object instantiation. They provide an immediate way to represent data.

### Real-world analogy
Literals are like writing a number or word directly on a piece of paper. Variables are like labeled boxes. A literal is the actual content you put inside the box.

### Literals vs. Constants
A literal is the raw value itself (e.g., `42`). A constant is a variable (usually declared with `final`) that holds a value and cannot be changed (e.g., `final int MAX = 42;`).

### Literals vs. Expressions
`42` is a literal. `40 + 2` is an expression. Expressions evaluate to values; literals are the values themselves.

### Inherent Type
Every literal in Java has an inherent type determined by its form and context.

## PART 2: SYNTAX — EVERY DETAIL

### Integer Literals

#### Decimal Literals (base 10)
- Default integer literal type is `int`
- Range: -2,147,483,648 to 2,147,483,647
- Examples: `0`, `42`, `-100`, `2_147_483_647`
- Suffix `L` or `l` for `long` literals (always use uppercase `L` — lowercase `l` looks like `1`)
- `long x = 2_147_483_648L;` (exceeds int range, MUST have L suffix)
- What happens if you write `2_147_483_648` without L? → Compiler error: integer number too large

#### Binary Literals (base 2) — Java 7+
- Prefix: `0b` or `0B`
- Only digits 0 and 1 allowed
- Examples: `0b1010` (10), `0b11111111` (255), `0B0000_1111` (15)
- Useful for bit manipulation, flags, masks
- Show how to represent byte values, permissions, etc.

#### Octal Literals (base 8)
- Prefix: leading `0` (zero)
- Digits 0-7 only
- Examples: `010` (8 in decimal!), `077` (63), `0777` (511)
> [!WARNING]
> **THE OCTAL TRAP**: `010 != 10`! Leading zero makes it octal!
- `int x = 08;` → COMPILE ERROR: 8 is not a valid octal digit
- `int x = 09;` → COMPILE ERROR: same reason
- Why octal exists: historical (Unix file permissions: `chmod 0755`)

#### Hexadecimal Literals (base 16)
- Prefix: `0x` or `0X`
- Digits 0-9 and a-f (or A-F)
- Examples: `0xFF` (255), `0xCAFEBABE` (magic number!), `0xDEAD_BEEF`
- Case-insensitive: `0xff` == `0xFF` == `0xFf`
- Common use: colors (`0xFF0000` = red), memory addresses, byte values
- Hex floating-point: `0x1.0p3` = 1.0 × 2³ = 8.0 (binary exponent with `p`/`P`)

#### Underscore in Numeric Literals (Java 7+)
- Purpose: readability for large numbers
- Valid: `1_000_000`, `0xFF_EC_DE_5E`, `0b0010_0101`, `3.14_15_92`
- INVALID placement rules (with examples for each):
  - At beginning: `_100` ❌
  - At end: `100_` ❌
  - Adjacent to decimal point: `3_.14` ❌, `3._14` ❌
  - Before/after prefix: `0_x1A` ❌, `0x_1A` ❌
  - Before suffix: `100_L` ❌, `3.14_f` ❌
  - Multiple consecutive underscores ARE valid: `1___000` ✓

### Floating-Point Literals

#### Double Literals (default)
- Any number with a decimal point defaults to `double`
- Examples: `3.14`, `.5` (= 0.5), `3.` (= 3.0), `3.0d`, `3.0D`
- Optional `d` or `D` suffix (default anyway)

#### Float Literals
- MUST have `f` or `F` suffix
- `3.14f`, `.5F`, `3.0f`
- `float f = 3.14;` → COMPILE ERROR: incompatible types (3.14 is double!)
- Why this is a compile error: potential precision loss

#### Scientific Notation (E notation)
- `1.5e3` = 1.5 × 10³ = 1500.0 (double)
- `2.5E-4` = 2.5 × 10⁻⁴ = 0.00025 (double)
- `1.5e3f` = 1500.0f (float with suffix)
- `e` or `E` for exponent
- The exponent is always base 10

#### Hexadecimal Floating-Point Literals
- `0x1.0p3` = 1.0 × 2³ = 8.0 (binary exponent!)
- `p` or `P` for binary exponent (NOT `e`)
- `0x1.8p1` = 1.5 × 2¹ = 3.0
- Rarely used but important for precise floating-point representation
- Used in IEEE 754 debugging

### Character Literals

#### Basic Character Literals
- Enclosed in single quotes: `'A'`, `'z'`, `'0'`, `' '`
- Exactly ONE character between quotes
- `char` is 16-bit unsigned (0 to 65,535) — UTF-16 encoding
- `''` → COMPILE ERROR: empty character literal
- `'AB'` → COMPILE ERROR: too many characters

#### Escape Sequences (ALL of them)
| Escape | Character | Unicode | Description |
|---|---|---|---|
| `\t` | Tab | \u0009 | Horizontal tab |
| `\n` | Newline | \u000A | Line feed |
| `\r` | Return | \u000D | Carriage return |
| `\'` | Single quote | \u0027 | Used inside char literals |
| `\"` | Double quote | \u0022 | Used inside strings |
| `\\` | Backslash | \u005C | Literal backslash |
| `\b` | Backspace | \u0008 | Backspace |
| `\f` | Form feed | \u000C | Form feed |
| `\0` | Null | \u0000 | Null character |

#### Unicode Escape Sequences
- `\u0041` = 'A' (Unicode code point U+0041)
- `\u03B1` = 'α' (Greek alpha)
- `\u4E16` = '世' (Chinese character)
- Format: `\uXXXX` where XXXX is exactly 4 hex digits
- IMPORTANT: Unicode escapes are processed BEFORE lexical analysis by javac!
  - `// \u000A` → This creates a NEWLINE in the source code! (\u000A = newline)
  - This can break comments: `// comment \u000A System.exit(0);` → the System.exit runs!
  - This is a security concern and a common gotcha

#### Supplementary Characters (Beyond BMP)
- Unicode code points above U+FFFF (emoji, rare scripts)
- Cannot fit in a single `char` (16-bit limit)
- Represented as surrogate pairs: two `char` values
- Example: 😀 (U+1F600) → `\uD83D\uDE00` (high surrogate + low surrogate)
- Use `Character.toChars(0x1F600)` to get the char array
- `String.codePointAt()` and `String.codePoints()` for proper handling

### String Literals

#### Basic String Literals
- Enclosed in double quotes: `"Hello, World!"`
- Can contain escape sequences: `"Line1\nLine2"`
- Can contain Unicode escapes: `"\u0048ello"` = `"Hello"`
- Empty string: `""` is valid (length 0, NOT null)
- `null` is NOT a String literal
- String literals are automatically interned in the String Pool

#### Text Blocks (Java 13+, standard in Java 15)
- Triple double-quotes: `"""` ... `"""`
- Multi-line strings without escape sequences for newlines
- Opening `"""` MUST be followed by a newline
- Closing `"""` position determines indentation stripping
```java
String json = """
        {
            "name": "Java",
            "version": 21
        }
        """;
```
- Incidental whitespace stripping algorithm
- Essential whitespace preserved
- Escape sequences still work inside text blocks
- `\s` escape (Java 14): preserves trailing space
- `\` at end of line: line continuation (no newline inserted)
- Show bytecode: text blocks compile to the same constant as regular strings

### Boolean Literals
- Only two values: `true` and `false`
- NOT keywords (they are literals!)
- Cannot be assigned numeric values: `boolean b = 1;` → COMPILE ERROR
- Cannot be cast to/from any other type
- Case-sensitive: `True`, `TRUE`, `FALSE` are NOT boolean literals (they're identifiers)

### The `null` Literal
- `null` is a literal of the "null type"
- Can be assigned to ANY reference type: `String s = null;`, `Object o = null;`, `int[] a = null;`
- CANNOT be assigned to primitive types: `int x = null;` → COMPILE ERROR
- `null` is NOT an object — it's the absence of an object
- `null instanceof X` → always `false` for any X
- Calling any method on null → NullPointerException
- `null == null` → `true`
- `null` is NOT the same as `""` (empty string) or `0` or `false`

## PART 3: INTERNAL WORKING

### How javac Handles Literals

#### Lexical Analysis (Scanner)
- Scanner reads source characters and produces tokens
- Integer literal → `INTLITERAL` token with value
- Long literal → `LONGLITERAL` token
- Float literal → `FLOATLITERAL` token
- Double literal → `DOUBLELITERAL` token
- Character literal → `CHARLITERAL` token
- String literal → `STRINGLITERAL` token
- Boolean literal → `TRUE` or `FALSE` token (handled as keywords by scanner)
- `null` → `NULL` token
- Radix conversion happens during scanning (hex/octal/binary → internal int value)

#### AST Representation
- Literal nodes in AST: `JCLiteral` with `typetag` (INT, LONG, FLOAT, DOUBLE, CHAR, CLASS for String, BOT for null)
- Value stored as Java object (Integer, Long, Float, Double, Character, String)

#### Constant Folding at Compile Time
- javac evaluates constant expressions at compile time
- `int x = 3 + 4;` → stored as `7` in bytecode (no runtime addition)
- `String s = "Hello" + " " + "World";` → stored as `"Hello World"` in constant pool
- `final int X = 10; int y = X + 5;` → `y = 15` in bytecode
- Only compile-time constants are folded (literals, final variables initialized with constant expressions)

### Bytecode for Literals

#### Integer Constants
- `iconst_m1` through `iconst_5`: push -1 to 5 directly (most efficient, 1 byte)
- `bipush <byte>`: push -128 to 127 (2 bytes)
- `sipush <short>`: push -32768 to 32767 (3 bytes)
- `ldc <index>`: push from constant pool (for larger values) (2-3 bytes)

#### Long Constants
- `lconst_0`, `lconst_1`: push long 0 or 1 (1 byte)
- `ldc2_w <index>`: push long from constant pool

#### Float Constants
- `fconst_0`, `fconst_1`, `fconst_2`: push float 0.0, 1.0, 2.0
- `ldc <index>`: push from constant pool

#### Double Constants
- `dconst_0`, `dconst_1`: push double 0.0, 1.0
- `ldc2_w <index>`: push from constant pool

#### String Constants
- `ldc <index>`: push String reference from constant pool
- String is stored in constant pool as `CONSTANT_String_info` → `CONSTANT_Utf8_info`
- At runtime, JVM interns the string (String Pool)

#### Null
- `aconst_null`: push null reference (1 byte)

#### Boolean
- `true` → `iconst_1` (JVM uses int for boolean!)
- `false` → `iconst_0`

Show `javap -c` output for a program using various literal types.

### Constant Pool Storage
- All literal values that don't have dedicated bytecode instructions are stored in the .class file constant pool
- `CONSTANT_Integer_info`: 4-byte int
- `CONSTANT_Long_info`: 8-byte long
- `CONSTANT_Float_info`: 4-byte float
- `CONSTANT_Double_info`: 8-byte double
- `CONSTANT_String_info` → points to `CONSTANT_Utf8_info`
- Show constant pool section from `javap -v` output

### Memory Layout
- Primitive literals: loaded onto operand stack, stored in local variable slots (stack)
- String literals: stored in String Pool (special area of heap, was PermGen before Java 7, now heap)
- String Pool deduplication: identical string literals share the same object
- Text blocks: no special memory treatment — same as equivalent regular string

### String Pool and Interning
- All string literals are automatically interned
- `"hello" == "hello"` → true (same pool reference)
- `new String("hello") == "hello"` → false (new object vs pool)
- `new String("hello").intern() == "hello"` → true (intern returns pool reference)
- String pool moved from PermGen to heap in Java 7 (important JVM change)
- `-XX:StringTableSize` flag to configure hash table size

## PART 4: VISUAL DIAGRAMS

1. Table: All literal types with examples, default type, and bytecode instruction used
2. ASCII: Integer literal → bytecode instruction selection flowchart (value range → iconst/bipush/sipush/ldc)
3. Mermaid: Literal processing pipeline in javac (Source chars → Scanner → Token → Parser → AST JCLiteral → Constant folding → Bytecode)
4. ASCII: String Pool diagram (heap with String Pool area, showing literal sharing vs new String())
5. ASCII: Text block indentation stripping algorithm visualization
6. Table: All escape sequences with Unicode, description, and example
7. Mermaid: Unicode escape processing order (BEFORE lexical analysis — show the danger)
8. Table: Underscore placement rules (valid vs invalid with ✓/✗ marks)
9. ASCII: Constant pool structure showing how different literal types are stored
10. Table: Comparison of number bases (decimal, binary, octal, hex) with prefixes and examples

## PART 5: BEST PRACTICES
- Use Text Blocks (`"""`) for multiline strings like SQL queries or JSON to improve readability.
- Always use uppercase `L` for long literals to avoid visual confusion with the digit `1`.
- Group long numeric literals with underscores to make them readable, e.g. `1_000_000`.

## PART 6: PERFORMANCE IMPLICATIONS
- Constant folding ensures literal expressions (like `3 * 60 * 60`) carry zero runtime overhead.
- String pooling provides significant memory savings by avoiding duplicate string creations.

## PART 7: PITFALLS & GOTCHAS
- **The Octal Trap**: Accidental leading zeros (e.g. `0123`) result in wrong values.
- **Precision Loss**: Omitting `L` when assigning an out-of-bounds `int` to a `long` throws a compile error instead of wrapping.
- **Unicode Escape Issues**: Invisible characters or unexpected newlines inside strings can cause bizarre runtime behaviors or break comments entirely.

## PART 8: COMMON INTERVIEW QUESTIONS
1. *Can `null` be assigned to primitive types?* No, only to object references.
2. *Explain String interning.* The JVM stores only one copy of each distinct String literal in the String Pool to save memory.
3. *What is constant folding?* The compile-time evaluation of constant expressions by javac.

## PART 9: LAB EXERCISES
- **Exercise 1**: Write a program to assign the largest possible `int` and `long` literals and observe the compiler behavior if `L` is missing.
- **Exercise 2**: Experiment with binary and hex literals to mask out specific bits of an integer.
- **Exercise 3**: Write a program that uses text blocks and trailing spaces using the `\s` escape sequence.

## PART 10: REAL-WORLD USAGE
- **Magic Numbers**: Using `0xCAFEBABE` to identify Java `.class` files.
- **Bitwise Operations**: Using binary literals (`0b11110000`) to create masks for low-level protocol parsing or permission flags.
- **Configuration**: Storing default configurations internally using large text block literals.
