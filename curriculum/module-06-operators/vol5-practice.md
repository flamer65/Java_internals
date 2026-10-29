# Module 6: Operators - Volume 5 (Practice Exercises)

Target: Java 21 LTS | IntelliJ IDEA

*Note: These are prompts only. No solutions are provided in this volume.*

## 1. SMALL EXERCISES (10)
1. **Arithmetic Showcase**: Write a program that demonstrates ALL arithmetic operators (`+`, `-`, `*`, `/`, `%`) with both integers and doubles. Show integer division truncation explicitly.
2. **Pre vs Post Increment**: Demonstrate pre-increment (`++x`) vs post-increment (`x++`) in expressions. Write code that traces the values step by step.
3. **Equality Check**: Show the difference between `==` and `.equals()` for `String`, `Integer` (include both cached values like 100 and non-cached values like 1000), and custom objects.
4. **Short-Circuit Logic**: Demonstrate short-circuit `&&`/`||` vs non-short-circuit `&`/`|` using methods with side effects (e.g., methods that print statements when evaluated).
5. **Bitwise Basics**: Write a program using ALL bitwise operators (`&`, `|`, `^`, `~`) with binary output using `Integer.toBinaryString()`.
6. **Shifting Bits**: Demonstrate ALL shift operators (`<<`, `>>`, `>>>`) with both positive and negative numbers.
7. **Compound Cast**: Show compound assignment implicit cast behavior: `byte b = 10; b += 200;` and print the resulting overflow value.
8. **Ternary Type Promotion**: Write a program showing ternary operator type promotion, proving that `true ? 1 : 1.0` evaluates to a double.
9. **Instanceof & Pattern Matching**: Demonstrate `instanceof` with `null`, subtypes, and Java 16+ pattern matching (`if (obj instanceof String s)`).
10. **Precedence Power**: Show the full operator precedence by writing complex expressions that produce different results with and without parentheses.

## 2. MEDIUM EXERCISES (10)
1. **Permission System**: Build a Unix-style permission system using bitwise operators (`rwx` for owner/group/others).
2. **Shift Modulo**: Write a program that proves shift distance is modulo 32 (for `int`) and 64 (for `long`).
3. **String Concatenation Under the Hood**: Explore string concatenation by showing `1+2+"3"` vs `"1"+2+3`. Then, compile it and use `javap -c` to observe the `invokedynamic` instruction.
4. **Stack Tracer**: Build an expression evaluator that traces the operand stack state for simple arithmetic expressions, simulating JVM behavior.
5. **Division Edge Cases**: Demonstrate ALL edge cases of division: integer division by zero, float division by zero, `0.0/0.0`, and `Integer.MIN_VALUE / -1`.
6. **Floor vs Standard Div/Mod**: Write a program comparing `Math.floorDiv()` and `Math.floorMod()` with `/` and `%` for negative numbers.
7. **NaN Nonsense**: Demonstrate NaN behavior: prove `NaN == NaN` is false, `NaN < x` is false, and show the proper usage of `Float.isNaN()` and `Double.compare()`.
8. **Comparison Comprehensive**: Build a complete comparison demo showing the differences between: `==`, `.equals()`, `Objects.equals()`, `Comparable.compareTo()`, and `Comparator`.
9. **Ternary NPE Danger**: Show the ternary null danger: `Integer a = null; int x = cond ? a : 0;` which throws a NullPointerException. Show the fix.
10. **Bitwise Utilities**: Implement an efficient power-of-2 checker, an is-even/odd checker, and a bit counter (population count) using ONLY bitwise operators.

## 3. HARD EXERCISES (5)
1. **Bitwise Calculator**: Build a calculator that accepts two integers and a bitwise/logical operator. It should display both operands and the result in decimal, binary, and hexadecimal formats.
2. **Operator Bytecode Analyzer**: Write a program analyzing bytecode for ALL operator types using `ProcessBuilder` to run `javap -c` on compiled classes and parsing the output.
3. **Operator Precedence Quiz**: Build a console application that generates random complex expressions and asks the user to predict the result based on precedence rules.
4. **Overflow Detector**: Create a comprehensive arithmetic overflow detector utilizing the `Math.addExact()`, `Math.multiplyExact()`, etc., methods.
5. **XOR Encryption**: Implement XOR encryption: write a program to encrypt and decrypt a string message using XOR with a given key.

## 4. PRODUCTION PROBLEMS (3)
1. **The Over-127 Bug**: A system uses `==` to compare user IDs stored as `Integer` objects. Some comparisons inexplicably fail for IDs > 127. Diagnose the cache issue and write the fix.
2. **The Intermittent NPE**: A null check uses `&` instead of `&&` (`if (obj != null & obj.isValid())`), causing intermittent NullPointerExceptions. Reproduce the bug and fix it.
3. **The Missing Cents**: An integer division loses fractional cents in a financial calculation (`totalCents / 100`). Fix the code using proper casting and transition the logic to `BigDecimal`.

## 5. REFACTORING TASKS (3)
1. **Nested Ternary Hell**: Refactor a deeply nested, unreadable ternary operation (`a ? b : c ? d : e ? f : g`) into clean, readable `if-else` or `switch` expressions.
2. **Excessive Parentheses**: Refactor a codebase where developers put parentheses around every single operation out of fear of precedence rules. Remove unnecessary parentheses while keeping the logic identical.
3. **Magic Bitmasks**: Refactor a class using raw integer magic numbers for state flags (`1`, `2`, `4`, `8`) into a clean `EnumSet` or well-named constant bitmasks.

## 6. DEBUGGING TASKS (3)
1. **The Infinite Loop**: Debug a `while` loop that uses a floating-point counter (`for (double d = 0.0; d != 1.0; d += 0.1)`) and never terminates due to precision issues.
2. **The Wrong Assignment**: Debug a bug where `if (status = true)` was written instead of `if (status == true)`, causing all checks to pass and status to be mutated.
3. **The Unintended Sign Extension**: Debug a byte-to-int conversion issue where `int i = byteValue` causes negative numbers due to sign extension instead of zero extension (`& 0xFF`).

## 7. CODE COMPLETION (3)
1. **Complete the Bit Setter**: Complete a method `public int setBit(int target, int position)` using bitwise OR and shifts.
2. **Complete the Pattern Match**: Complete an `equals` method using Java 16+ `instanceof` pattern matching to safely cast and compare fields.
3. **Complete the Safe Divider**: Complete a method that performs division but safely handles both integer and floating-point zero denominators without throwing exceptions.

## 8. OUTPUT PREDICTION (5)
1. Predict the output: `System.out.println(10 + 20 + "Java" + 10 + 20);`
2. Predict the output: `int x = 5; System.out.println(x++ + ++x);`
3. Predict the output: `System.out.println(-1 >>> 1);`
4. Predict the output: `System.out.println(true | false & true ^ false);`
5. Predict the output: `Integer a = 128; Integer b = 128; System.out.println(a == b);`

## 9. ERROR FINDING (3)
1. Find the error: `byte b1 = 10; byte b2 = 20; byte b3 = b1 + b2;`
2. Find the error: `int x = 5; boolean b = x instanceof Integer;`
3. Find the error: `int[] arr = {1, 2, 3}; int index = 0; arr[index] = index++;` (Identify the undefined/confusing behavior).

## 10. OPTIMIZATION PROBLEMS (2)
1. **Modulo to Bitwise**: Optimize `x % 8` by replacing it with an equivalent, faster bitwise operation.
2. **Multiply by 10**: Optimize `x * 10` using only shift operators (`<<`) and addition (`+`).
