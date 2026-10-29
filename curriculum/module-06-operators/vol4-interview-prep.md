# Module 6 Volume 4: Operators - Interview Preparation

*Authored by a JVM Engineer / OpenJDK Contributor.*

This volume prepares you for Java engineering interviews ranging from junior developer to principal engineer roles. We cover fundamental definitions, intermediate nuances, advanced JVM internal behavior, and the tricky "gotcha" questions beloved by interviewers. Target platform: Java 21 LTS.

---

## 1. BASIC QUESTIONS (10)

**Q1: What are the different categories of operators in Java?**
*Answer:* Java operators are categorized into:
1. Arithmetic (`+`, `-`, `*`, `/`, `%`)
2. Relational / Comparison (`==`, `!=`, `>`, `<`, `>=`, `<=`)
3. Logical (`&&`, `||`, `!`)
4. Bitwise (`&`, `|`, `^`, `~`, `<<`, `>>`, `>>>`)
5. Assignment (`=`, `+=`, `-=`, etc.)
6. Unary (`++`, `--`, `+`, `-`, `!`, `~`)
7. Ternary (`? :`)
8. Type comparison (`instanceof`)

**Q2: What is operator precedence? Give the high-level order.**
*Answer:* Operator precedence determines the order in which operations are evaluated in an expression. The general order (highest to lowest) is:
1. Postfix (`expr++`, `expr--`)
2. Unary (`++expr`, `-expr`, `!`, `~`)
3. Multiplicative (`*`, `/`, `%`)
4. Additive (`+`, `-`)
5. Shift (`<<`, `>>`, `>>>`)
6. Relational (`<`, `>`, `instanceof`)
7. Equality (`==`, `!=`)
8. Bitwise AND, XOR, OR (`&`, `^`, `|`)
9. Logical AND, OR (`&&`, `||`)
10. Ternary (`? :`)
11. Assignment (`=`, `+=`, etc.)

**Q3: What is the difference between `==` and `.equals()`?**
*Answer:* `==` is an operator that compares object identity (memory location / reference) for objects, and value for primitives. `.equals()` is a method inherited from `Object` designed to be overridden to compare logical equality (state/value) of two objects.

**Q4: What is the difference between `&&` and `&` in logical expressions?**
*Answer:* `&&` is the logical short-circuit AND. If the left operand is false, the right operand is never evaluated. `&` is the bitwise AND, but when applied to booleans, it acts as a logical AND that *always* evaluates both operands.

**Q5: What is the difference between `>>` and `>>>`?**
*Answer:* `>>` is the signed right shift. It preserves the sign bit (fills the left with 1s if the number is negative, 0s if positive). `>>>` is the unsigned right shift. It always fills the leftmost bits with 0s, regardless of the original sign.

**Q6: What are pre-increment and post-increment? How do they differ?**
*Answer:* `++x` (pre) increments the value and evaluates to the *new* value. `x++` (post) increments the value but evaluates to the *original* value before the increment occurred.

**Q7: What happens when you divide an integer by zero? A double by zero?**
*Answer:* Integer division (`1 / 0`) throws a runtime `ArithmeticException`. Floating-point division (`1.0 / 0.0`) conforms to IEEE 754 and evaluates to `Infinity` (or `NaN` if `0.0 / 0.0`), without throwing an exception.

**Q8: What is the ternary operator?**
*Answer:* `condition ? exprIfTrue : exprIfFalse`. It is a shorthand for an `if-else` statement that returns a value.

**Q9: What does the `instanceof` operator do?**
*Answer:* It tests whether the object referenced on the left side is an instance of the class/interface (or a subclass/implementer) on the right side. In Java 16+, it also supports pattern matching: `if (obj instanceof String s)`.

**Q10: What is the modulus operator? How does it work with negative numbers?**
*Answer:* `%` returns the remainder of division. In Java, the sign of the result matches the sign of the *dividend* (the left operand). `-5 % 3` is `-2`. `5 % -3` is `2`.

---

## 2. INTERMEDIATE QUESTIONS (10)

**Q1: Explain binary numeric promotion in arithmetic operations.**
*Answer:* When two operands of different types are operated on, Java promotes them to a common type. If either is `double`, the other is promoted to `double`. Else if `float`, to `float`. Else if `long`, to `long`. Otherwise, *both* operands are promoted to `int` (even if they are `byte` or `short`).

**Q2: Why does compound assignment include an implicit cast?**
*Answer:* `a += b` is equivalent to `a = (Type of a)(a + b)`. This prevents compilation errors when adding smaller types. For example, `byte b = 1; b += 2;` compiles, but `b = b + 2;` fails because `b + 2` is promoted to `int`.

**Q3: How does short-circuit evaluation work? Give a practical example.**
*Answer:* It stops evaluating the expression as soon as the result is determined. Example: `if (str != null && !str.isEmpty())`. If `str` is null, the left side is false. The JVM knows the whole `&&` must be false, so it skips `.isEmpty()`, preventing a `NullPointerException`.

**Q4: Explain bitwise operators and their use in permission systems.**
*Answer:* Bitwise operators operate on binary representations. You can represent permissions as powers of 2 (1, 2, 4, 8). You combine permissions with OR (`admin = READ | WRITE`), check for permission with AND (`if ((userPerm & WRITE) == WRITE)`), and remove permissions with XOR or AND-NOT (`userPerm &= ~WRITE`).

**Q5: What is the output of `1 + 2 + "3" + 4 + 5`?**
*Answer:* `"3345"`. Operations evaluate left to right. `1+2` is int addition (3). `3 + "3"` becomes String concatenation ("33"). From then on, everything is appended as a string: `"33" + 4` -> `"334"`, plus `5` -> `"3345"`.

**Q6: How does Java handle `NaN` in comparisons?**
*Answer:* `Float.NaN` and `Double.NaN` are considered unordered. Any relational comparison (`<`, `<=`, `>`, `>=`) involving `NaN` returns `false`. `NaN == NaN` is also `false`. `NaN != NaN` is `true`.

**Q7: What is the Integer cache and how does it affect `==`?**
*Answer:* Java caches `Integer` objects for values between -128 and 127. `Integer a = 100, b = 100;` implies `a == b` is `true` because they point to the exact same cached object. `Integer x = 1000, y = 1000;` means `x == y` is `false` (different objects).

**Q8: Explain shift operator modulo behavior.**
*Answer:* For 32-bit `int`, shift operators mask the right operand with `0x1F` (31). So `x << 33` is equivalent to `x << 1`. For 64-bit `long`, the mask is `0x3F` (63).

**Q9: What bytecode instructions implement `&&`?**
*Answer:* There is no `&&` instruction in bytecode. The compiler uses branching instructions (`ifeq`, `ifne`). It evaluates the first expression; if false, it jumps to the end (short-circuit). Otherwise, it evaluates the second.

**Q10: How does the ternary operator determine its result type?**
*Answer:* It uses complex type promotion rules. If both operands are the same type, that's the result. If one is primitive and the other its wrapper, the wrapper is unboxed. If they are numeric primitives of different types, the smaller is promoted to the larger.

---

## 3. ADVANCED QUESTIONS (JVM/Compiler focus) (10)

**Q1: Explain `fcmpg` vs `fcmpl` bytecode instructions.**
*Answer:* Both compare two floats. If they are comparable, they push 1 (greater), -1 (less), or 0 (equal). If one of them is `NaN`, `fcmpg` pushes 1, while `fcmpl` pushes -1. The Java compiler chooses `fcmpg` for `>` and `>=` operators, and `fcmpl` for `<` and `<=`, ensuring that any expression involving `NaN` evaluates to `false`.

**Q2: How does `invokedynamic` implement String concatenation since Java 9?**
*Answer:* Previously, `a + b` was compiled to `new StringBuilder().append(a).append(b).toString()`. Java 9+ (JEP 280) uses `invokedynamic` to call `StringConcatFactory`. This defers the exact concatenation strategy to the JVM at runtime, allowing optimizations like using `MethodHandle` chains and pre-allocating byte arrays, which is drastically faster and creates less garbage.

**Q3: What JIT optimizations apply to operators?**
*Answer:* 
- **Constant Folding**: `final int x = 3 * 4;` is compiled straight to `12`.
- **Strength Reduction**: The JIT replaces expensive operations with cheaper ones, like replacing `x * 8` with `x << 3`.
- **Dead Code Elimination**: If an operator calculates a value that is never used, the JVM drops the instruction entirely.

**Q4: How does `iinc` bytecode work for increment/decrement?**
*Answer:* `iinc` increments a local variable directly without needing to load it onto the operand stack and push the result back. This makes `i++` extremely efficient at the bytecode level compared to `i = i + 1` (though JIT optimizes both to identical assembly).

**Q5: Explain `Math.floorDiv()` vs `/` for negative division.**
*Answer:* The JVM's `/` instruction (`idiv`) truncates towards zero. `Math.floorDiv()` implements mathematical modulo (truncation towards negative infinity). It does this by checking if the signs of the operands differ and if there is a remainder; if so, it subtracts 1 from the zero-truncated result.

**Q6: How does Java handle unsigned arithmetic without unsigned types?**
*Answer:* The bit patterns for signed and unsigned addition/subtraction/multiplication are identical (Two's Complement magic). For division and comparison, Java 8 introduced `Integer.divideUnsigned()` and `Integer.compareUnsigned()` which treat the sign bit as the 32nd magnitude bit.

**Q7: What is the bytecode for `x++ + ++x`? (Assume x=5 initially)**
*Answer:* 
1. `iload` x (pushes 5 to stack).
2. `iinc` x by 1 (x is now 6 in local vars, stack still has 5).
3. `iinc` x by 1 (x is now 7).
4. `iload` x (pushes 7 to stack).
5. `iadd` (pops 5 and 7, pushes 12).
Result: 12.

**Q8: How does the JVM implement `instanceof` at bytecode level?**
*Answer:* It uses the `instanceof` opcode. Internally, the JVM checks the object's class pointer in its object header against the target class. The JVM uses secondary super type caches and constant-time primary super type arrays to resolve inheritance trees exceptionally fast.

**Q9: Explain operator evaluation order.**
*Answer:* Operands are evaluated strictly left-to-right, *then* operators are applied based on precedence. In `a() + b() * c()`, Java guarantees `a()` executes, then `b()`, then `c()`. Then the multiplication happens, then the addition. C/C++ do not guarantee operand execution order!

**Q10: What are the performance implications of autoboxing in operator expressions?**
*Answer:* Expressions like `Integer sum = 0; sum += 5;` result in a hidden `intValue()` call, an `iadd`, and a hidden `Integer.valueOf()` call. In a tight loop, this generates massive amounts of heap garbage, causing frequent GC pauses.

---

## 4. TRICKY / GOTCHA QUESTIONS (10)

**Q1: `System.out.println(1 + 2 + "3" + 4 + 5);`**
*Output:* `"3345"` (See Intermediate Q5).

**Q2: `int x = 5; System.out.println(x++ + ++x);`**
*Output:* `12`. (5 + 7).

**Q3: `System.out.println(Integer.MIN_VALUE == -Integer.MIN_VALUE);`**
*Output:* `true`. Two's complement integer negation is `~x + 1`. For MIN_VALUE (1000...000), `~x` is 0111...111. Adding 1 yields 1000...000. It overflows right back to MIN_VALUE.

**Q4: `System.out.println(1 << 32);`**
*Output:* `1`. The shift amount is masked by 31. `32 & 31 = 0`. So it's `1 << 0`, which is 1.

**Q5: `System.out.println(-1 >>> 1);`**
*Output:* `2147483647` (`Integer.MAX_VALUE`). `-1` is all 1s in binary. Unsigned right shift by 1 pushes a 0 into the sign bit, leaving 31 bits of 1s, which is MAX_VALUE.

**Q6: `System.out.println(Double.NaN < 1 || Double.NaN >= 1);`**
*Output:* `false`. Both relational comparisons evaluate to false.

**Q7: `Integer val = true ? null : 0; int primitive = val;`**
*Output:* `NullPointerException`. The ternary operator determines the return type is `Integer`. It returns `null`. But wait! If you assign it to a primitive `int`, it attempts to unbox `null.intValue()`, causing an NPE.

**Q8: `byte b = 127; b += 1; System.out.println(b);`**
*Output:* `-128`. `b += 1` implicitly casts back to `byte`. 127 + 1 overflows 8-bit signed limits and wraps around to -128.

**Q9: `System.out.println("" + 1 + 2);` vs `System.out.println(1 + 2 + "");`**
*Output:* `"12"` and `"3"`. In the first, it starts as String concatenation. In the second, it starts as integer arithmetic, evaluates to 3, then becomes a String.

**Q10: `double d = 0.0; System.out.println(1/d);`**
*Output:* `Infinity`. (No ArithmeticException for floats).

---

## 5. MCQs, OUTPUT PREDICTION, CODE TRACING, AND SCENARIOS

*(Note to interviewee: Practice tracing these exactly as the JVM would on paper.)*

### MCQs (Selection)
**1. Which operator cannot be overloaded in Java?**
A. `+`
B. `==`
C. `[]`
D. None can be overloaded
*Answer:* D. Java does not support operator overloading (except for string concatenation `+` baked into the compiler).

**2. What is the type of `x` in `var x = 5.0f * 2;`?**
A. `int`
B. `double`
C. `float`
D. `long`
*Answer:* C. The `int` (2) is promoted to `float`.

### Code Tracing (Example)
```java
boolean a = true, b = false, c = true;
System.out.println(a || b && c);
```
*Trace:* `&&` has higher precedence than `||`. The expression groups as `a || (b && c)`. 
`b && c` evaluates to `false && true` = `false`.
`a || false` evaluates to `true || false` = `true`.
*Answer:* `true`.

### Scenario Question
**Scenario:** You have a performance-critical loop calculating network packet checksums. You notice huge memory allocation rates. You have the code:
`Long checksum = 0L; for(long b : bytes) { checksum += b; }`
*What is the problem and how do you fix it using operator knowledge?*
*Answer:* The `+=` operator is being applied to a `Long` wrapper. Each loop iteration unboxes `checksum`, performs primitive addition, and *re-boxes* a brand new `Long` object. This destroys heap memory. Fix it by declaring `long checksum = 0L;` as a primitive.
