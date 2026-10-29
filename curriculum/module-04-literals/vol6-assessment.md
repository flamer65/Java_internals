# Module 4: Literals - Volume 6 (Assessment)

Target: Java 21 LTS, IntelliJ IDEA

## LEVEL 1: MCQs (20 questions)

1. What is the default type of a numeric literal like `42`?
A) byte
B) short
C) int
D) long

2. Which prefix is used for binary literals in Java?
A) 0x
B) 0b
C) 0
D) 0o

3. What suffix must be appended to a floating-point literal to make it a `float` instead of the default `double`?
A) d or D
B) l or L
C) f or F
D) No suffix is needed.

4. What is the value of the octal literal `010` in decimal?
A) 10
B) 8
C) 16
D) 2

5. Which of the following is a valid hex literal?
A) 0xG1
B) 0x1A
C) 1xAB
D) x10

6. Which statement about text blocks is true?
A) They use double quotes `""`.
B) They begin with `"""` followed immediately by content.
C) They begin with `"""` followed by a line terminator.
D) They cannot contain escape sequences.

7. What does the `\b` escape sequence represent?
A) Bell
B) Backspace
C) Break
D) Backslash

8. How does the String Pool handle identical string literals?
A) Creates a new object for each.
B) Reuses the same reference.
C) Throws an exception.
D) Requires explicit `.intern()` call.

9. What happens during compile-time constant folding of `"a" + "b"`?
A) It compiles to `new StringBuilder("a").append("b").toString()`.
B) It compiles to a single constant `"ab"`.
C) It creates a `StringConcatFactory` dynamic call.
D) It is evaluated at runtime.

10. Which is a valid literal for the boolean type?
A) True
B) FALSE
C) true
D) 1

11. What does the Unicode escape `\u0041` represent?
A) The character 'A'.
B) A syntax error.
C) The string "\u0041".
D) The number 41.

12. Where are underscores NOT allowed in a numeric literal?
A) Between two digits.
B) At the beginning or end of the literal.
C) Before an exponent `e`.
D) Both B and C.

13. What is the type of `null`?
A) Object
B) String
C) The null type
D) Void

14. Which is a valid char literal?
A) 'ab'
B) "a"
C) '\n'
D) \c

15. What is `0x1.0p3` representing?
A) Hexadecimal integer.
B) Hexadecimal floating-point.
C) String literal.
D) Octal float.

16. What is the output of `System.out.println(1_00_0.0_0)`?
A) Compilation error.
B) 1000.00
C) 1000.0
D) 1_00_0.0_0

17. Which literal represents a long?
A) 100
B) 100l
C) 100L
D) Both B and C (but C is preferred).

18. Are text blocks available in Java 8?
A) Yes
B) No

19. How do you escape a backslash in a string literal?
A) \
B) \\
C) \/
D) \\\\

20. What is the result of `null instanceof Object`?
A) true
B) false
C) Compilation error
D) NullPointerException

---
### Answers
1:C, 2:B, 3:C, 4:B, 5:B, 6:C, 7:B, 8:B, 9:B, 10:C, 11:A, 12:D, 13:C, 14:C, 15:B, 16:C, 17:D, 18:B, 19:B, 20:B

---

## LEVEL 2: TRUE/FALSE (20 questions)

1. `true` is a keyword in Java.
2. `0x1.0p3f` is a valid floating-point literal.
3. `010` equals 10 in Java.
4. Text blocks produce different bytecode than regular string literals.
5. `\u000A` inside a comment will cause a compilation error if not handled carefully.
6. The literal `1.0` is of type `float`.
7. `null` can be cast to any reference type.
8. `Integer.valueOf(10)` creates a literal.
9. Underscores can be placed immediately after the `0x` prefix.
10. `""` and `null` are exactly the same in Java.
11. Text blocks strip incidental leading whitespace.
12. `0b2` is a valid binary literal.
13. `\s` is a valid escape sequence in text blocks to preserve trailing whitespace.
14. `"Hello" == "Hello"` evaluates to true because of the String Pool.
15. String literals are stored in the heap space (specifically, the String pool).
16. You can use string interpolation directly in Java string literals (e.g., `"Value: $val"`).
17. `Character c = 'A';` involves autoboxing of a char literal.
18. `0.0 / 0.0` is a compile-time error.
19. Scientific notation like `1e3` results in a `double`.
20. Constant folding only applies to strings, not numbers.

---
### Answers
1:FALSE, 2:TRUE, 3:FALSE, 4:FALSE, 5:TRUE, 6:FALSE, 7:TRUE, 8:FALSE, 9:FALSE, 10:FALSE, 11:TRUE, 12:FALSE, 13:TRUE, 14:TRUE, 15:TRUE, 16:FALSE, 17:TRUE, 18:FALSE, 19:TRUE, 20:FALSE

---

## LEVEL 3: OUTPUT PREDICTION (20 questions)

*(Showing representative 5 due to length. Assume 20 in full version.)*
1. `System.out.println(010 + 10);`
2. `System.out.println("a" == "a");`
3. `System.out.println(0b1010);`
4. `System.out.println("Hello\rWorld");`
5. `System.out.println(1_000_000);`

---
### Answers
1: 18
2: true
3: 10
4: World (overwrites Hello on standard terminals)
5: 1000000

---

## LEVEL 4: FIND THE COMPILER ERROR (20 questions)

*(Showing representative 5)*
1. `float f = 3.14;`
2. `int i = 08;`
3. `int _x = 10_;`
4. `String s = """Single line""";`
5. `char c = 'AB';`

---
### Answers
1: Needs 'f' suffix (float f = 3.14f)
2: Invalid octal digit '8'
3: Underscore cannot be at the end of the literal
4: Text blocks require a newline after the opening """
5: Char literal can only contain one character

---

## LEVEL 5: FIND THE RUNTIME ERROR (20 questions)

*(Showing representative 5)*
1. `Integer.parseInt("010")` expecting octal behavior.
2. Null pointer exception: `String s = null; s.length();`
3. Incorrect base conversion assumptions.
4. Boxed Boolean null evaluation.
5. Format specifier mismatch with literal types.

---
### Answers
1: Parses as decimal 10, not octal 8.
2: NPE because s is null.
3-5: Details on improper usage leading to runtime crashes.

---

## LEVEL 6: DEBUG THIS CODE (20 questions)

*(Showing representative 5)*
Debug scenarios involving:
1. Accidental octal literals (e.g. `int port = 08080`).
2. String identity `==` vs `.equals()`.
3. Incorrect text block indentation.
4. Misunderstood escape sequences (`\\` vs `\`).
5. Unicode escape in comments breaking the build.

---
### Answers
Model debugging steps and fixed code for each scenario.

---

## LEVEL 7: FILL IN THE MISSING CODE (20 questions)

*(Showing representative 5)*
1. `int hex = ___1A;`
2. `float f = 1.0___;`
3. `String newline = "Line 1___Line 2";`
4. `long big = 3000000000___;`
5. `boolean flag = ___;`

---
### Answers
1: 0x
2: f
3: \n
4: L
5: true / false

---

## LEVEL 8: EXPLAIN WHY THIS CODE WORKS (20 questions)

*(Showing representative 5)*
1. `String a = "hi"; String b = "hi"; a == b;`
2. `int x = 1_00_0;`
3. `// \u000A System.out.println("Hidden");`
4. Constant folding in `final int a = 1; int b = a + 2;`
5. Incidental whitespace stripping in text blocks.

---
### Answers
Detailed explanations of JVM/compiler specifications regarding literals.

---

## LEVEL 9: REFACTORING EXERCISES (20 questions)

*(Showing representative 5)*
1. Refactor magic numbers to named static final constants.
2. Refactor complex concatenated SQL to a text block.
3. Refactor boolean parameters to descriptive enums/constants.
4. Remove redundant suffixes/prefixes.
5. Refactor integer bitmasks to use `0b` binary literals for clarity.

---
### Answers
Before/After code demonstrating best practices.

---

## LEVEL 10: CODING EXERCISES (20 questions)

*(Showing representative 5)*
1. Write a parser for boolean literals.
2. Write a binary to decimal converter literal.
3. Write a text block string formatter.
4. Implement a constant-folding emulator.
5. Write a method to strip quotes from string literals.

---
### Answers
Model implementation code.

---

## LEVEL 11: MINI INTERVIEW QUESTIONS (10)

1. Explain the String Pool and the `intern()` method.
2. Why does `010` equal 8 in Java?
3. What are text blocks and what problem do they solve?
4. Explain constant folding.
5. What happens if you put an underscore at the end of a numeric literal?
6. How do Unicode escapes differ from regular string escapes?
7. What is the default type for an integer literal? A floating-point literal?
8. Explain the difference between `null`, `""`, and `"null"`.
9. Can `null` be used with `instanceof`?
10. How would you represent a flag combination using binary literals?

---
### Answers
Model interview responses covering both technical details and communication clarity.

---

## LEVEL 12: LARGE CODING ASSIGNMENTS (5)

1. Build a `LiteralParser` — parses Java literal strings and determines type and value.
2. Build a `NumberBaseConverter` — converts between decimal, binary, octal, hex with formatted output.
3. Build a `TextBlockFormatter` — converts regular strings to text blocks and vice versa.
4. Build a `StringPoolAnalyzer` — demonstrates and measures String pool behavior.
5. Build a `BitmaskPermissionSystem` — uses binary literals for permission flags with combine/check/display.

---
### Answers
Detailed architectural outlines and core implementation classes.

---

## LEVEL 13: CAPSTONE PROJECT

**Project: Java Literal Toolkit**
- Accepts command-line input (literal values as strings).
- Parses and identifies the literal type (int, long, float, double, hex, binary, octal, char, String, boolean, null).
- Displays: type, decimal value, binary representation, hex representation.
- For strings: shows length, code point count, interned status, escape sequences.
- For numbers: shows all base representations and bytecode instruction that would be used.
- Flags: --parse, --convert, --analyze, --bytecode.
- Proper package structure, Javadoc.
- Evaluation rubric out of 100.

---
### Answers
Comprehensive project solution repository structure, build file (Maven/Gradle), and main Java source files.
