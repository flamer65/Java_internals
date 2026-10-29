# Module 6 Volume 3: Operators - Best Practices, Common Mistakes, and Debugging

*Authored from the perspective of a Senior JVM/Compiler Engineer.*

Welcome to Volume 3 of the Java Mastery Curriculum's module on Operators. After 25+ years of working on the JVM, analyzing JIT compilation logs, and diagnosing production failures, I can assure you that operators are not just simple math symbols—they are the fundamental instructions that drive the engine of the Java Virtual Machine. Understanding how to use them correctly, safely, and optimally is what separates a junior coder from a senior engineer. 

This volume covers the industry best practices, the most insidious mistakes (from beginner syntax flubs to senior-level JIT pitfalls), and how to debug operator issues using IntelliJ IDEA and JVM tooling. Target platform: Java 21 LTS.

---

## PART 6: BEST PRACTICES

### Arithmetic Operators

**1. Use Parentheses to Make Precedence Explicit**
Never rely on memorized operator precedence rules in complex expressions. While the compiler knows that `*` evaluates before `+`, humans reading your code at 3 AM during an incident do not.
```java
// BAD - Requires mental overhead
int result = a + b << c * d & e;

// GOOD - Explicit and maintainable
int result = ((a + b) << (c * d)) & e;
```

**2. Never Divide Integers Expecting a Decimal Result**
Integer division truncates towards zero. If you need a fractional result, cast at least one operand to `double` or `float` *before* the division.
```java
int hits = 4, total = 5;
double ratio = hits / total; // Evaluates to 0.0! The division happens as int first.
double correctRatio = (double) hits / total; // 0.8
```

**3. Use `Math.floorDiv()` and `Math.floorMod()` for Negative Division**
The standard `/` and `%` operators truncate towards zero. In mathematical contexts (like wrapping coordinates or grid calculations), you often want floor rounding (truncation towards negative infinity). Java 8+ provides `Math.floorDiv` and `Math.floorMod`.
```java
System.out.println(-5 / 3);          // -1
System.out.println(Math.floorDiv(-5, 3)); // -2 (Correct mathematical behavior)
```

**4. Use `Exact` Math Methods for Overflow-Safe Arithmetic**
Standard arithmetic operators silently overflow. If you are calculating financial data, array bounds, or critical metrics, silent overflow is a critical bug. Use `Math.addExact()`, `Math.multiplyExact()`, etc., which throw an `ArithmeticException` on overflow.
```java
long safeResult = Math.multiplyExact(1_000_000, 3_000_000); // Throws ArithmeticException
```

**5. Avoid Floating-Point for Exact Calculations**
Operators `+`, `-`, `*`, `/` on `float` and `double` are governed by IEEE-754. They are fast but imprecise. Never use them for currency. Use `BigDecimal`.

### Comparison Operators

**1. ALWAYS use `.equals()` for Object Comparison, NOT `==`**
The `==` operator compares memory addresses (object identity), not state. Always use `.equals()` for Strings, wrappers (Integer, Double), and custom objects.
*Exception:* `== null` is the correct way to check for nullity.

**2. Use `Objects.equals(a, b)` for Null-Safe Comparison**
To avoid `NullPointerException` when the left-hand side might be null, use `java.util.Objects.equals()`.
```java
// Null-safe, clear, concise
if (Objects.equals(str1, str2)) { ... }
```

**3. Use Epsilon Comparison for Floating-Point**
Due to precision loss, `0.1 + 0.2 == 0.3` evaluates to `false`. Compare floating points using an epsilon (a tiny tolerance value) or `Double.compare()`.
```java
final double EPSILON = 1e-9;
if (Math.abs(a - b) < EPSILON) { ... }
```

**4. Never Compare Booleans with `==`**
Writing `if (flag == true)` is redundant, amateurish, and introduces the risk of accidental assignment `if (flag = true)`. Just write `if (flag)`.

### Logical Operators

**1. ALWAYS Use Short-Circuit Operators (`&&`, `||`)**
Unless you have a hyper-specific reason to evaluate both operands (e.g., executing two methods with required side effects—which is a bad design anyway), never use `&` and `|` for boolean logic. Short-circuit operators save CPU cycles and prevent exceptions.

**2. Put Cheap and Null Checks FIRST**
Short-circuiting works left-to-right. Guard expensive operations or potential NPEs by placing the safety checks first.
```java
// JVM evaluates the cheap null check first. If null, isValid() is never called (preventing NPE).
if (obj != null && obj.isValid() && performExpensiveDbCheck(obj)) { ... }
```

**3. Use De Morgan's Laws for Simplification**
Deeply nested logical negations are hard to read.
`!(a && b)` is exactly equivalent to `!a || !b`.
`!(a || b)` is exactly equivalent to `!a && !b`.

### Bitwise Operators

**1. Use Named Constants for Bit Flags**
Magic numbers in bitwise operations are unmaintainable.
```java
static final int FLAG_READ    = 1 << 0; // 1
static final int FLAG_WRITE   = 1 << 1; // 2
static final int FLAG_EXECUTE = 1 << 2; // 4
```

**2. Prefer `EnumSet` Over Manual Bitwise Flags**
While bitwise flags (`int flags = FLAG_READ | FLAG_WRITE`) are fast, they lack type safety. In Java, `EnumSet` is internally represented as a single `long` (for up to 64 enums) and offers the same performance with full type safety.

**3. Use `Integer.toBinaryString()` for Debugging**
When bitwise logic fails, print the binary representation immediately. It reveals the exact bit layout the JVM sees.

### Assignment & Increment Operators

**1. Don't Use Assignment as an Expression in Conditions**
While legal, `if ((x = getValue()) > 0)` is highly unreadable and prone to errors. Separate the assignment from the condition.

**2. Use Compound Assignment when Possible**
`x += 5` is more concise than `x = x + 5`. However, remember that compound assignment includes an implicit cast: `byte b = 1; b += 5;` is legal, whereas `b = b + 5;` fails compilation.

**3. Avoid Complex `++`/`--` Expressions**
`a = b++ + ++c;` is an abomination. It forces the reader to mentally trace the `iinc` bytecode instructions. Put increments on their own lines.

---

## PART 7: COMMON MISTAKES

### Beginner Mistakes (10)

1. **Using `=` instead of `==` in conditions:** 
   `if (x = 5)` assigns 5 to x and returns 5. Since Java requires a `boolean` in `if`, this thankfully fails to compile unless `x` is a boolean.
2. **Chaining comparisons:** 
   `1 < x < 10` is valid in Python, but illegal in Java. You must write `1 < x && x < 10`.
3. **Using `==` to compare Strings:** 
   `"foo" == new String("foo")` is `false`. String pools trick beginners into thinking `==` works for strings.
4. **Integer division giving 0:** 
   `int result = 1 / 3;` yields `0`, not `0.333`.
5. **Precedence misunderstanding:** 
   `2 + 3 * 4` evaluates to `14`, not `20`.
6. **Using `|` instead of `||`:** 
   `if (obj != null | obj.isValid())` will throw an NPE if `obj` is null because `|` does not short-circuit.
7. **Post-increment confusion:** 
   `int x = 5; int y = x++;` results in `y = 5` and `x = 6`. `y` gets the *old* value.
8. **Implicit primitive promotion:** 
   `byte a = 1, b = 2; byte c = a + b;` -> Compiler error! `a + b` promotes to `int`.
9. **Division by zero:** 
   `10 / 0` throws `ArithmeticException`. (Note: `10.0 / 0.0` does *not*, it yields `Infinity`).
10. **Negating `Integer.MIN_VALUE`:** 
    `-Integer.MIN_VALUE` is still `-2147483648` due to two's complement integer overflow.

### Intermediate Mistakes (7)

1. **The Integer Cache trap:** 
   `Integer.valueOf(100) == Integer.valueOf(100)` is `true`. `Integer.valueOf(128) == Integer.valueOf(128)` is `false`. The JVM caches boxed integers from -128 to 127 by default.
2. **NaN identity:** 
   `Double.NaN == Double.NaN` is `false`. By IEEE 754 rules, NaN is not equal to anything, including itself. Use `Double.isNaN()`.
3. **Signed Zeroes:** 
   `0.0 == -0.0` evaluates to `true`. However, `Double.compare(0.0, -0.0)` evaluates to `1` (greater than). They have different binary representations.
4. **Shift masking:** 
   `1 << 32` evaluates to `1`, not `0`. The JVM masks the shift amount to 5 bits for integers (`amount & 31`), meaning shifting by 32 is exactly the same as shifting by 0.
5. **Ternary type promotion:** 
   `true ? 1 : 1.0` results in `1.0`. The ternary operator determines a single common return type. `1` is promoted to a `double`.
6. **Division Overflow:** 
   `Integer.MIN_VALUE / -1` evaluates to `Integer.MIN_VALUE`. It overflows the maximum positive integer limit, wrapping back around silently.
7. **String concatenation with null:** 
   `"" + null` does not throw an NPE. It returns the string `"null"`.

### Senior-Level Pitfalls (5)

1. **JIT Reordering:** 
   In concurrent environments without proper `volatile` boundaries or synchronization, the JIT compiler and CPU can reorder independent operator instructions. Code that looks perfectly sequenced logically may be executed out-of-order at runtime.
2. **Unsigned Arithmetic Confusion:** 
   Java natively lacks unsigned primitives. Treating a signed `int` as unsigned when doing bitwise operations is perilous. You must use `Integer.parseUnsignedInt()` and `Integer.divideUnsigned()` to prevent signed bits from destroying calculations.
3. **`fcmpg` vs `fcmpl` behavior:** 
   When comparing floats at the bytecode level, the JVM has two instructions. `fcmpg` pushes `1` if either value is `NaN`, while `fcmpl` pushes `-1`. The compiler chooses based on whether the source code used `>` or `<`. A deep misunderstanding of how the compiler maps your Java operators to these bytecodes can lead to subtle NaN-related bugs in native interop.
4. **StringConcatFactory Overhead:** 
   Since Java 9, `+` for strings uses `invokedynamic` (StringConcatFactory) instead of `StringBuilder`. While vastly superior, using `+` *inside* a tight loop still generates garbage. `StringBuilder` remains mandatory for loop-based concatenation.
5. **Escape Analysis Defeating Side Effects:** 
   If you rely on an operator throwing an exception (e.g., `/ 0`) to trigger logic, but the result of the calculation is never used, the C2 JIT compiler's Escape Analysis and Dead Code Elimination might remove the calculation entirely, meaning the exception is never thrown in production!

---

## PART 8: DEBUGGING

### IntelliJ IDEA for Operator Issues

IntelliJ is your pair-programming JVM expert. Enable and heed these inspections:
*   **"Number comparison using == instead of equals()"**: Prevents the Integer cache trap.
*   **"Pointless boolean expression"**: Highlights `if (x == true)` and redundant `|| false` logic.
*   **"Integer division in floating-point context"**: Catches the `double ratio = 1 / 3` bug.
*   **"Shift operation by out of bounds amount"**: Flags `x << 32`.

**Evaluate Expression (Alt+F8):**
During a debug session, you can execute operators dynamically in the current frame. If you suspect an overflow or precedence issue, highlight the exact sub-expression and evaluate it independently of the whole line.

### Common Error Messages Decoded

*   **`bad operand types for binary operator`**: You are applying an operator to incompatible types, e.g., `boolean + boolean` or `String - String`.
*   **`operator && cannot be applied to int, int`**: Unlike C/C++, Java does not treat integers as booleans. `1 && 2` is illegal.
*   **`ArithmeticException: / by zero`**: You did integer division by zero. Check your denominators. (Remember, float division yields `Infinity`).
*   **`NullPointerException` during unboxing**: Evaluating `int x = 5 + wrappedInteger;` where `wrappedInteger` is `null`. The JVM invisibly calls `wrappedInteger.intValue()`, causing the NPE.

### JVM Inspection Tools

**Viewing Bytecode (`javap -c`)**
To truly understand operators, read the bytecode.
```bash
javac MyClass.java
javap -c MyClass
```
You will see exactly how `+` translates to `iadd`, how post-increment uses `iinc`, and how `&&` creates branching (`ifeq`).

**Viewing JIT Optimizations**
Run your code with `-XX:+PrintCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining`.
You will witness "strength reduction" in real-time. For instance, the JVM will often optimize multiplication by powers of two (`x * 8`) into bitwise shifts (`x << 3`) natively, proving that you shouldn't ruin your source code readability trying to "optimize" operators yourself—the JVM does it better.
