# Module 6: Operators - Volume 6 (Assessment)

Target: Java 21 LTS | IntelliJ IDEA

Complete this 13-level assessment on Java Operators. Answers are provided at the END of each level.

## LEVEL 1: MCQs (20 questions)
1. Which operator has the highest precedence? a) `*` b) `++` c) `+` d) `&&`
2. What is the result of `15 % -4`? a) `3` b) `-3` c) `4` d) `-4`
3. Which of these is a short-circuit operator? a) `&` b) `|` c) `&&` d) `^`
4. What is the result of `5 ^ 3`? a) `6` b) `8` c) `2` d) `15`
5. Which operator cannot be overloaded in Java? (Trick question!) a) `+` b) `*` c) `==` d) None can be overloaded
*(Questions 6-20 omitted for brevity in template, assuming full 20 questions covering numeric promotion, logical operators, ternary operators, assignment, and comparison.)*

### LEVEL 1 ANSWERS
1. b (postfix/prefix increments)
2. a (remainder takes sign of dividend)
3. c
4. a (0101 ^ 0011 = 0110 = 6)
5. d (Java does not support operator overloading)
*(Answers 6-20)*

## LEVEL 2: TRUE/FALSE (20 questions)
1. `>>>` is the unsigned right shift operator. (T/F)
2. `byte += int` requires an explicit cast to compile. (T/F)
3. The `==` operator on strings compares their content. (T/F)
4. `Float.NaN == Float.NaN` evaluates to true. (T/F)
5. Short-circuit `||` will not evaluate the right side if the left side is true. (T/F)
*(Questions 6-20 omitted for brevity)*

### LEVEL 2 ANSWERS
1. True
2. False (Compound assignments perform implicit casting)
3. False (Compares reference identity, `.equals()` compares content)
4. False (NaN is never equal to itself)
5. True
*(Answers 6-20)*

## LEVEL 3: OUTPUT PREDICTION (20 questions)
1. `int x = 5; System.out.println(x++ + ++x);`
2. `System.out.println("Result: " + 10 + 20);`
3. `System.out.println(10 + 20 + " Result");`
4. `int a = -1; System.out.println(a >>> 29);`
5. `boolean a = true; boolean b = false; System.out.println(a | b & a);`
*(Questions 6-20 omitted for brevity)*

### LEVEL 3 ANSWERS
1. `12` (5 + 7)
2. `"Result: 1020"`
3. `"30 Result"`
4. `7` (32-bit all 1s shifted right 29 times gives 111 binary)
5. `true` (bitwise AND evaluates before bitwise OR)
*(Answers 6-20)*

## LEVEL 4: FIND THE COMPILER ERROR (20 questions)
1. `byte a = 5; byte b = 10; byte c = a + b;`
2. `int x = 10; boolean result = x instanceof Integer;`
3. `String s = "123"; if (s == 123) {}`
4. `int val = true ? 10 : 20.0;`
5. `int arr[] = {1,2}; arr[1.0] = 5;`
*(Questions 6-20 omitted for brevity)*

### LEVEL 4 ANSWERS
1. `a + b` results in an `int`, must be explicitly cast to `byte`.
2. Cannot use `instanceof` with primitive `int`.
3. Cannot compare `String` and `int` using `==`.
4. Ternary promotion makes the result a `double`, cannot assign to `int`.
5. Array index must be an integer, not a float/double.
*(Answers 6-20)*

## LEVEL 5: FIND THE RUNTIME ERROR (20 questions)
1. `Integer x = null; int y = true ? x : 0;`
2. `int z = 10 / 0;`
3. `int[] arr = new int[5]; int y = arr[5];`
4. `Object obj = new Integer(5); String s = (String) obj;`
5. `int a = Integer.MAX_VALUE; int b = Math.addExact(a, 1);`
*(Questions 6-20 omitted for brevity)*

### LEVEL 5 ANSWERS
1. `NullPointerException` (unboxing null `Integer`).
2. `ArithmeticException` (division by zero).
3. `ArrayIndexOutOfBoundsException`.
4. `ClassCastException`.
5. `ArithmeticException` (overflow caught by Exact method).
*(Answers 6-20)*

## LEVEL 6: DEBUG THIS CODE (20 questions)
1. Code checking if valid: `if (isValid = true) { ... }`
2. Division losing fraction: `double res = 5 / 2;`
3. Null checking failing: `if (obj != null & obj.method())`
4. Bitwise checking: `if ((flags & 4 == 4))`
5. Precedence logic error: `if (!a && b)` intended as `if (!(a && b))`
*(Questions 6-20 omitted for brevity)*

### LEVEL 6 ANSWERS
1. Re-assignment, should be `==`.
2. Integer division happens first, should be `5.0 / 2`.
3. Non-short-circuit `&` evaluates right side even if null, should be `&&`.
4. Precedence of `==` is higher than `&`, should be `if ((flags & 4) == 4)`.
5. Missing parentheses around `a && b`.
*(Answers 6-20)*

## LEVEL 7: FILL IN THE MISSING CODE (20 questions)
1. `int remainder = 17 ___ 5;` // Result should be 2
2. `x = x ___ 1;` // Multiply x by 2 efficiently
3. `if (obj ___ String s)` // Java 16 Pattern Matching
4. `int c = a ___ b;` // Logical XOR operation
5. `a ___ b;` // Swap bits in place (first step of XOR swap)
*(Questions 6-20 omitted for brevity)*

### LEVEL 7 ANSWERS
1. `%`
2. `<<`
3. `instanceof`
4. `^`
5. `^=`
*(Answers 6-20)*

## LEVEL 8: EXPLAIN WHY THIS CODE WORKS (20 questions)
1. `long x = 10; x += 5.5;`
2. `Integer a = 100; Integer b = 100; System.out.println(a == b); // true`
3. `int x = -1 >> 1; // x is still -1`
4. `System.out.println(0.1 + 0.2 == 0.3); // false`
5. `int x = 1 << 35; // Works without exception`
*(Questions 6-20 omitted for brevity)*

### LEVEL 8 ANSWERS
1. Compound assignment includes an implicit cast (`(long)(x + 5.5)`).
2. Integer cache caches values from -128 to 127.
3. `>>` preserves the sign bit (sign extension).
4. Floating-point precision error (IEEE 754 standard).
5. Shift operators apply a modulo to the right operand (35 % 32 = 3), effectively doing `1 << 3`.
*(Answers 6-20)*

## LEVEL 9: REFACTORING EXERCISES (20 questions)
1. Refactor `if (b == true)`
2. Refactor `x = x + 1;`
3. Refactor `if (val % 2 == 0)` using bitwise.
4. Refactor `a ? true : false`
5. Refactor `if (a != null && a instanceof String) { String s = (String) a; ... }`
*(Questions 6-20 omitted for brevity)*

### LEVEL 9 ANSWERS
1. `if (b)`
2. `x++;` or `++x;` or `x += 1;`
3. `if ((val & 1) == 0)`
4. `a`
5. `if (a instanceof String s) { ... }`
*(Answers 6-20)*

## LEVEL 10: CODING EXERCISES (20 questions)
1. Write a method to swap two variables without a temp using XOR.
2. Write a method to count the number of set bits (1s) in an integer.
3. Write a safe division method returning an `OptionalDouble`.
4. Write a method to determine if a number is a power of 2 using bitwise operators.
5. Write a function evaluating basic algebraic strings using operator precedence.
*(Questions 6-20 omitted for brevity)*

### LEVEL 10 ANSWERS
1. `a ^= b; b ^= a; a ^= b;`
2. `Integer.bitCount(n)` or loop `while(n > 0) { count += n & 1; n >>= 1; }`
3. `return b == 0 ? OptionalDouble.empty() : OptionalDouble.of((double)a/b);`
4. `return n > 0 && (n & (n - 1)) == 0;`
5. *(Implementation using Shunting Yard or Stack)*
*(Answers 6-20)*

## LEVEL 11: MINI INTERVIEW QUESTIONS (10)
1. Explain the difference between `==` and `.equals()`.
2. Why is it dangerous to use `==` with `Float.NaN` or `Double.NaN`?
3. Explain the concept of short-circuit evaluation.
4. What happens when an integer overflow occurs during addition in Java?
5. How does the JVM handle string concatenation with the `+` operator in Java 9+?
*(Questions 6-10 omitted)*

### LEVEL 11 ANSWERS
1. `==` checks identity (memory address); `.equals()` checks logical value equivalence.
2. `NaN == NaN` is defined as `false` by IEEE 754 standard.
3. Logical operators `&&` and `||` stop evaluating the right-hand expression if the left-hand resolves the outcome.
4. It wraps around implicitly due to two's complement arithmetic unless `Math.addExact()` is used.
5. It uses `invokedynamic` to defer the concatenation strategy to runtime (StringConcatFactory).
*(Answers 6-10)*

## LEVEL 12: LARGE CODING ASSIGNMENTS (5)
1. **Build an `ExpressionEvaluator`**: Parses and evaluates arithmetic expressions with correct precedence.
2. **Build a `BitwisePermissionManager`**: Complete permission system with set/check/clear/toggle functionality using masks.
3. **Build an `OperatorBytecodeAnalyzer`**: Use Java Process API to run `javap -c` and show bytecode for dynamic expressions.
4. **Build a `ComparisonToolkit`**: Demonstrates deep equality, `Comparable`, `Comparator`, and `Objects.equals()`.
5. **Build a `BitManipulationLibrary`**: Implementation of common bit hacks (count, reverse, highest/lowest bit).

### LEVEL 12 ANSWERS
*(These are open-ended coding tasks; standard reference solutions provided in the curriculum repository codebase.)*

## LEVEL 13: CAPSTONE PROJECT
**Project: Java Operator Workbench**
- **Description**: Interactive terminal application to thoroughly test and analyze Java operator expressions.
- **Features**:
  - Evaluates and displays: result, data types involved, and precedence tree.
  - Binary/Hex display mode for bitwise and shift operations.
  - Linter: Detects potential issues (integer division, overflow, NaN comparison, `==` on objects).
  - Bytecode display mode showing JVM instructions for the provided operation.
  - Flags: `--evaluate`, `--precedence`, `--binary`, `--bytecode`, `--lint`.
- **Evaluation Rubric (out of 100)**:
  - Accuracy of Evaluation (30 pts)
  - Precedence Logic Implementation (20 pts)
  - Linter Issue Detection (20 pts)
  - Bytecode Mapping (15 pts)
  - Code Quality & Edge Case Handling (15 pts)

### LEVEL 13 ANSWERS
*(The capstone requires a complete multi-class CLI utility implementation. A reference solution should encompass the Java Compiler API or AST Parsing for advanced features.)*
