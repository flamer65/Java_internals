# Module 4: Literals - Volume 5 (Practice Exercises)

Target: Java 21 LTS, IntelliJ IDEA

## SMALL EXERCISES (10)
1. Write a program that declares the same number (1000) in decimal, binary, octal, and hexadecimal — verify all are equal.
2. Write a program using every escape sequence (`\t`, `\n`, `\r`, `\'`, `\"`, `\\`, `\b`, `\f`) and print the result.
3. Demonstrate that `float f = 3.14;` fails and `float f = 3.14f;` works — explain why in comments.
4. Use text blocks to format a multi-line JSON object and a multi-line SQL query.
5. Show that `true` and `false` cannot be used as identifiers but `True` and `False` can.
6. Demonstrate `null` behavior: assign to String, Object, array; show `null == null`, `null instanceof Object` compilation/runtime behavior.
7. Write a program using underscores in numeric literals for readability: credit card number, phone number, IP address as ints.
8. Show the difference between `""` (empty string), `null`, and `"null"` (the string).
9. Print all characters from 'A' to 'Z' using a for loop with char literals.
10. Use scientific notation to represent: Avogadro's number (6.022e23), Planck's constant (6.626e-34).

## MEDIUM EXERCISES (10)
1. Build a "Number Base Converter": accept an integer and print it in decimal, binary, octal, hex using `Integer.toBinaryString()` etc.
2. Demonstrate String Pool behavior: create strings with literals, `new String()`, `.intern()` — compare with `==` and `.equals()`.
3. Prove constant folding: write `String s = "Hello" + " " + "World";` — use `javap -c` to show it's compiled to a single constant.
4. Text block indentation experiment: show how moving the closing `"""` left/right changes the resulting string indentation.
5. Unicode escape danger: demonstrate that `// \u000A` creates a real newline — show a comment that "executes code".
6. Write a program demonstrating ALL invalid underscore placements (each in a comment showing the error).
7. Hexadecimal floating-point: write examples of `0x1.0p0`, `0x1.0p3`, `0x1.8p1` and verify the decimal values.
8. Supplementary character handling: store an emoji in a String, show `.length()` vs `.codePointCount()`, iterate by code points.
9. Write a program that demonstrates the octal trap: create an array `{100, 200, 300, 010}` and print all values.
10. Compare string creation performance: literal vs `new String()` vs `StringBuilder` in a loop of 100,000 iterations.

## HARD EXERCISES (5)
1. Build a "Literal Analyzer" tool: given a string representation of a Java literal, determine its type (int, long, float, double, char, String, boolean, null) and value.
2. Write a program that inspects the String Pool: intern many strings and use `-XX:+PrintStringTableStatistics` to observe pool statistics.
3. Create a comprehensive text block formatter: accept raw text and produce properly formatted Java text block syntax with correct indentation.
4. Write a program that demonstrates ALL constant pool entry types for literals using `javap -v` and explains each entry.
5. Create a binary flags/permissions system using binary literals: define permissions with `0b` literals, combine with OR, check with AND, display with formatted binary output.

## PRODUCTION PROBLEMS (3)
1. A developer uses `010` for a port number in configuration, causing the app to connect to the wrong port. Diagnose and fix.
2. A multi-line SQL query uses string concatenation with `+`. Refactor to use text blocks.
3. String pool memory issues: an application interns millions of unique strings, causing memory pressure. Diagnose and fix.

## REFACTORING TASKS (3)
1. Refactor magic numbers to named constants with appropriate literal types.
2. Refactor string concatenation to text blocks.
3. Refactor integer flags to binary literals with named constants and an enum.

## DEBUGGING TASKS (3)
1. Program uses octal literals accidentally — find and fix the bugs.
2. Program has escape sequence errors in strings — find and fix.
3. Program has text block indentation issues — fix the formatting.

## CODE COMPLETION (3)
1. Complete the missing binary and hex prefixes to make the assertions pass.
2. Fill in the correct suffixes to prevent integer overflow in the calculations.
3. Complete the text block syntax to match the exact expected multi-line output.

## OUTPUT PREDICTION (5)
1. Predict the output of mixed base arithmetic: `0x10 + 010 + 0b10 + 10`.
2. Predict the string identity: `String a = "test"; String b = "te" + "st"; System.out.println(a == b);`.
3. Predict the result of Unicode escapes in comments and strings.
4. Predict the length of a string containing surrogate pairs (emojis).
5. Predict the output of text block white-space stripping edge cases.

## ERROR FINDING (3)
1. Identify the invalid floating-point literal in a financial calculation.
2. Find the illegal underscore placement in a large numeric literal.
3. Spot the missing escape character in a complex regex string.

## OPTIMIZATION PROBLEMS (2)
1. Optimize a loop that creates a new String object each iteration instead of using string literals.
2. Optimize a permissions check that uses multiple boolean variables into a single bitmask using binary literals.
