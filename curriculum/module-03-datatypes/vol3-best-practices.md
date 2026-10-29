# Java Mastery Curriculum: Module 3 - Data Types
## Volume 3: Best Practices, Common Mistakes, and Debugging

### PART 6: BEST PRACTICES

#### Type Selection
Choosing the right data type is the cornerstone of robust and performant Java applications. As a Senior Java Engineer, you must look beyond mere functionality to understand how the JVM optimizes these types.

- **Use `int` for general-purpose integers:** The JVM is highly optimized for 32-bit `int` arithmetic. Local variables and operand stack values of type `byte`, `short`, and `char` are implicitly promoted to `int` before operations. Use `int` unless you have a specific reason not to.
- **Use `long` for timestamps, IDs, and large counts:** For values that can exceed 2 billion (like database sequences, epoch milliseconds, or file sizes), default to `long`.
- **Use `double` for general floating-point arithmetic:** Modern 64-bit architectures handle `double` precision arithmetic as fast as, or faster than, `float`. Use `double` for scientific calculations or graphics where exact decimal representation is not required.
- **NEVER use `float`/`double` for monetary values:** Always use `BigDecimal` (or an integer/long representing cents/minor units). Floating-point arithmetic is subject to IEEE 754 precision loss and rounding errors, which are unacceptable in financial systems.
- **Use `boolean` for flags:** Java enforce type safety; do not use C-style integers (0 or 1) for truth values. `boolean` explicitly communicates intent.
- **Use `char` strictly for single UTF-16 code units:** Prefer `String` for any text data. Be aware that characters outside the Basic Multilingual Plane (BMP) require two `char` values (a surrogate pair).

| Scenario | Recommended Type | Explanation |
| :--- | :--- | :--- |
| Loop counters | `int` | Standard JVM optimization size. |
| DB Primary Keys | `long` / `Long` | Prevents overflow in high-scale systems. |
| Financial amounts | `BigDecimal` | Exact arbitrary-precision decimal arithmetic. |
| File sizes | `long` | Files can easily exceed 2GB. |
| Bitmasks | `int` or `long` | Standard sizes for bitwise operations. |
| Truth values | `boolean` | Semantic clarity; strict JVM typing. |
| Large raw data blocks| `byte[]` | Minimum memory footprint per element. |

#### Wrapper vs Primitive
Primitive types (`int`, `long`, etc.) store values directly, whereas wrapper classes (`Integer`, `Long`, etc.) are objects holding a reference to a value on the heap.
- **Prefer primitives:** They are significantly faster, have no memory overhead (wrappers add object headers), avoid null-pointer risks, and do not suffer from identity vs. equality confusion.
- **Use wrappers only when necessary:**
  - When storing elements in Collections/Generics (e.g., `List<Integer>`).
  - When representing data that can be strictly `null` (e.g., an optional JSON field or an unset database column).
  - When using objects as keys in a `Map`.
- **Effective Java Item 61:** *Prefer primitive types to boxed primitives.* The performance penalty and cognitive load of dealing with potential `NullPointerException`s and reference identity issues make primitives the safer, faster default.

| Use Case | Choice | Why? |
| :--- | :--- | :--- |
| Local math calculations | Primitive | Stack-allocated, fast arithmetic, no GC overhead. |
| `List`, `Set`, `Map` elements | Wrapper | Generics in Java do not (yet) support primitives (Valhalla pending). |
| Optional database field | Wrapper | Can represent `NULL` values directly. |
| DTO boolean flag | Primitive | If a default `false` is acceptable and `null` is invalid. |

#### Autoboxing/Unboxing Best Practices
Autoboxing and unboxing implicitly convert between primitives and wrappers. While convenient, they hide object creation and method calls (`Integer.valueOf()`, `intValue()`).
- **Be aware of implicit boxing in loops:** Using a wrapper class as an accumulator in a loop (e.g., `Long sum = 0L; sum += i;`) creates a new object in every iteration. Always use primitives for accumulators.
- **Never use `==` to compare wrapper objects:** `==` compares memory addresses (identity), not values. Due to the Integer Cache, `Integer.valueOf(100) == Integer.valueOf(100)` is true, but `Integer.valueOf(200) == Integer.valueOf(200)` is false. Always use `.equals()`.
- **Watch for NPEs from unboxing null:** If a wrapper reference is `null`, implicitly converting it to a primitive throws a `NullPointerException`.
- **Use `Objects.equals()` for null-safe comparison:** Instead of checking for nulls manually when comparing two wrappers, use `java.util.Objects.equals(val1, val2)`.
- **IntelliJ Inspections:** Enable and heed the 'Unboxing of boxed type' and 'Auto-boxing' warnings in performance-critical code sections.

#### Floating-Point Best Practices
- **Use `BigDecimal` for financial calculations:** Always specify a `RoundingMode` when dividing or scaling `BigDecimal`.
- **Epsilon Comparison:** Never compare floating-point numbers with `==`. Use an epsilon tolerance: `Math.abs(a - b) < 1e-9`. Alternatively, use `Double.compare(d1, d2) == 0`.
- **Check for special values properly:** Use `Float.isNaN(f)` or `Double.isNaN(d)` instead of `f == Float.NaN` (which is always false).
- **Check for infinity:** Use `Double.isInfinite(d)`.

#### Constants
- **Use wrapper class constants:** Instead of hardcoding bounds, use `Integer.MAX_VALUE`, `Integer.MIN_VALUE`, `Double.NaN`, `Float.POSITIVE_INFINITY`. This improves readability and prevents transcription errors.
- **Define domain constants clearly:**
  ```java
  public static final BigDecimal TAX_RATE = new BigDecimal("0.18");
  public static final int MAX_RETRIES = 3;
  ```

---

### PART 7: COMMON MISTAKES

#### Beginner Mistakes (10)
1. **Using `float`/`double` for money:** Results in pennies being lost or fabricated due to precision errors.
2. **Comparing wrappers with `==` instead of `.equals()`:** Fails intermittently depending on value ranges.
3. **Not knowing integer overflow is silent:** Exceeding `Integer.MAX_VALUE` quietly wraps around to `Integer.MIN_VALUE` without throwing an exception.
4. **Thinking `boolean` is exactly 1 byte always:** The JVM specification does not dictate the size of `boolean` (though boolean arrays typically use 1 byte per element, individual booleans are often aligned to 4 bytes).
5. **`byte b = 128;` — out of range:** `byte` is signed (-128 to 127). This will not compile.
6. **`float f = 3.14;` — missing `f` suffix:** `3.14` defaults to `double`. You must write `3.14f` or cast it.
7. **Confusing `int` and `Integer`:** Assuming they have identical semantics and can be swapped arbitrarily without performance/nullability consequences.
8. **Using `new Integer()` instead of `valueOf()`:** `new Integer(5)` always allocates a new object, bypassing the cache. (Deprecated since Java 9, marked for removal).
9. **Not handling `NumberFormatException` when parsing:** Calling `Integer.parseInt(userInput)` without a try-catch block for bad input.
10. **Assuming `char` can hold any Unicode character:** Java uses UTF-16. Emojis and other supplementary characters require two `char` variables (surrogate pairs).

#### Intermediate Mistakes (7)
1. **Autoboxing performance in loops:**
   ```java
   Long sum = 0L;
   for (long i = 0; i < Integer.MAX_VALUE; i++) {
       sum += i; // Creates 2 billion Long objects!
   }
   ```
2. **Integer cache surprise with `==`:** Assuming `-128` to `127` caching applies to all wrapper types identically or relying on it for business logic.
3. **Unboxing null → NPE:** Passing a null `Integer` to a method expecting an `int` causes a runtime crash.
4. **`byte + byte = int`:**
   ```java
   byte a = 10, b = 20;
   byte c = a + b; // Compiler Error! Result is promoted to int.
   ```
5. **Loss of precision: `int` → `float`:** For large integers (e.g., > 16 million), converting `int` to `float` loses precision because `float` only has 24 bits of significand.
6. **Silent overflow in `int * int`:** When calculating bounds, `int index = array.length * offset;` can silently overflow if both operands are `int`, even if `index` is assigned to a `long`. Fix: `long index = (long) array.length * offset;`.
7. **Comparing `NaN` with `==`:** `Double.NaN == Double.NaN` is specified to return `false`.

#### Senior-Level Pitfalls (5)
1. **Memory overhead of wrapper arrays vs primitive arrays:** An `Integer[]` of 1 million elements consumes significantly more memory (object headers, padding, references) compared to an `int[]` (which only takes 4MB). At scale, this stresses the Garbage Collector.
2. **False sharing in primitive arrays across cache lines:** Concurrent modification of adjacent primitive elements in an array by different threads causes CPU cache invalidation overhead.
3. **Compressed oops impact on reference-heavy data structures:** On 64-bit JVMs, `-XX:+UseCompressedOops` limits references to 32 bits (if heap < 32GB). Heavily wrapping primitives means hitting the GC limit much faster than if using flat primitive layouts.
4. **Escape analysis limitations for autoboxed values:** The JIT compiler can scalar-replace boxed objects if they don't escape the method. However, complex control flows or passing them to other methods disables this optimization, forcing heap allocation.
5. **Trusting `float`/`double` equality in hash codes:** Using floating-point values as keys in HashMaps is dangerous because `NaN` values hash to the same bucket, but `0.0` and `-0.0` have different binary representations despite being mathematically equal.

---

### PART 8: DEBUGGING

#### IntelliJ for Data Type Issues
- **Inspections:** Configure IntelliJ to show 'Auto-boxing' as a Warning in performance-critical modules. Turn on 'Number objects compared using ==' to catch identity comparison bugs early.
- **Data type mismatch highlighting:** IntelliJ underlines lossy conversions (e.g., assigning a `long` to an `int`).
- **Quick-fix suggestions:** Alt+Enter on type errors often suggests casting or changing the variable type.
- **Evaluate Expression:** During a debug session, use Alt+F8 to evaluate `Objects.equals(a, b)` or cast variables to inspect underlying binary values (`Integer.toBinaryString(value)`).

#### Common Error Messages
- **`incompatible types: possible lossy conversion from double to int`**
  - *Cause:* Assigning a larger/more precise type to a smaller one.
  - *Fix:* Explicitly cast `(int) myDouble` if truncation is intended, or change the variable type.
- **`incompatible types: Object cannot be converted to int`**
  - *Cause:* Retrieving from a non-generic collection without casting.
  - *Fix:* Use generics (`List<Integer>`) or cast `(Integer) obj`.
- **`NullPointerException` (during unboxing)**
  - *Cause:* Attempting to use a `null` wrapper as a primitive.
  - *Fix:* Check for null before arithmetic/assignment, or use `Optional`.
- **`NumberFormatException: For input string: "123a"`**
  - *Cause:* `Integer.parseInt()` called on a string containing non-numeric characters.
  - *Fix:* Validate input, trim whitespace (`.trim()`), or use regex before parsing.
- **`ArithmeticException: / by zero`**
  - *Cause:* Integer division by zero. (Note: Floating-point division by zero results in `Infinity` or `NaN`, not an exception).
  - *Fix:* Check the denominator before division.

#### JVM Inspection
- **`javap -c`:** Use the JDK disassembler to view bytecode. You will explicitly see `invokestatic #Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;` for boxing, helping you understand where object allocations happen.
- **Memory Analysis Tools (JOL):** Use Java Object Layout (JOL) to inspect the exact memory footprint of wrappers vs primitives in your specific JVM environment.
- **JMH (Java Microbenchmark Harness):** When deciding between primitives and wrappers in hot loops, don't guess. Write a JMH benchmark to observe the exact throughput difference and GC overhead.
