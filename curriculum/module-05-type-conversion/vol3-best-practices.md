# Module 5: Type Conversion & Casting
## Volume 3: Best Practices, Common Mistakes, and Debugging

Targeting Java 21 LTS | Environment: IntelliJ IDEA

---

## PART 6: BEST PRACTICES

### Avoiding Unnecessary Casts
- **Use generics to avoid casts**: Prioritize parameterized types. Use `List<String>` instead of raw `List` to eliminate the need for downcasting when retrieving elements.
- **Use pattern matching instanceof (Java 16+)**: Instead of checking type and then casting (`if (obj instanceof String) { String s = (String) obj; }`), use pattern matching (`if (obj instanceof String s) { ... }`).
- **Prefer polymorphism over downcasting**: If you find yourself checking types and casting to call specific methods, redesign your object hierarchy to use overridden polymorphic methods.
- **Never cast just to suppress compiler warnings**: Address the root cause of the type mismatch rather than blindly casting to silence the compiler.

### Safe Casting Patterns
- **Always check with `instanceof` before downcasting**: Downcasting without a prior check risks `ClassCastException` at runtime.
- **Switch pattern matching for multiple types (Java 21)**: Use `switch (obj) { case String s -> ...; case Integer i -> ...; }` for clean, safe type checks across multiple possibilities.
- **Generic type tokens**: When building frameworks, use `Class<T>` for type-safe casting (e.g., `public <T> T getBean(Class<T> type)`).
- **Optional-based safe cast utility**: Consider a utility method like `public static <T> Optional<T> safeCast(Object o, Class<T> clazz)` to elegantly handle failed casts.

### Numeric Conversion Best Practices
- **Be aware of precision loss**: Conversions from `int` to `float` or `long` to `float/double` can lose precision. Example: `int x = 16777217; float f = x; // f is 16777216.0`.
- **Use `Math.toIntExact()`**: For safe `long` to `int` narrowing, this method throws an `ArithmeticException` instead of silently truncating if the value overflows.
- **Use `Math.round()` instead of `(int)` for rounding**: A cast `(int) 3.9` truncates to `3`. If you want mathematical rounding, use `Math.round(3.9)` which yields `4`.
- **Use `BigDecimal` when exact decimal conversion matters**: For currency or high-precision calculations, avoid `double` or `float` entirely.
- **Explicit cast for intentional narrowing**: When you deliberately narrow (e.g., masking a hash down to a `byte`), document the *why* in comments so future maintainers know it's not a bug.

### Autoboxing Best Practices
- **Prefer primitives in computations**: Autoboxing in loops creates massive object churn, triggering frequent Garbage Collection.
- **Never mix `==` with wrapper types**: Always use `.equals()` for `Integer`, `Long`, etc. `Integer a = 128, b = 128; a == b` is `false`.
- **Watch for NPE from unboxing null**: An `Integer` that is `null` will throw a `NullPointerException` if implicitly unboxed to an `int`.
- **Know autoboxing + widening rules**: Java will not automatically widen and autobox simultaneously (e.g., `int` to `Long` fails).

### String Conversion Best Practices
- **Use `String.valueOf(x)` over `"" + x`**: It clearly communicates intent and creates fewer intermediate `StringBuilder` objects under the hood in complex expressions.
- **Use `Integer.parseInt()` over `Integer.valueOf().intValue()`**: `parseInt` directly yields a primitive, avoiding unnecessary object allocation.
- **Always handle `NumberFormatException`**: Never trust string input when parsing numeric types.
- **Use `String.format()`**: For complex formatting, prefer `String.format` or `java.util.Formatter` over huge concatenation chains.

---

## PART 7: COMMON MISTAKES

### Beginner Mistakes (10)
1. **`byte b = b1 + b2;`**: Forgetting numeric promotion to `int`. Both operands are promoted to `int`, requiring an explicit cast to assign back to `byte`.
2. **`float f = 3.14;`**: Forgetting the `f` suffix. `3.14` is a `double` literal; assigning it to `float` requires narrowing.
3. **Using `(int)` to "round" a double**: It truncates, it does not round. `(int) 4.99` is `4`.
4. **Casting between unrelated types**: e.g., `String s = (String) 42;`. This is a compile-time error.
5. **Forgetting `instanceof` check before downcast**: Leads directly to `ClassCastException`.
6. **`boolean b = (boolean) 1;`**: Thinking Java works like C/C++. Java `boolean` is strictly separated from numeric types.
7. **Comparing wrappers with `==`**: Due to the Integer Cache, `==` might work for small numbers (-128 to 127) but fail for larger ones.
8. **`Long L = 10;`**: Expecting `int` to autobox to `Long`. It tries to box to `Integer`, which can't be assigned to `Long`. Requires `10L`.
9. **Losing data silently**: `(byte) 300` evaluates to `44` without any exception due to bit truncation.
10. **Not understanding `"" + null`**: Results in the string `"null"`, not a `NullPointerException`.

### Intermediate Mistakes (7)
1. **Precision loss in `int → float`**: Occurs silently for large `int` values because `float` has only 24 bits of precision.
2. **Not knowing compound assignment includes implicit cast**: `b += 1` works on a byte because it's equivalent to `b = (byte)(b + 1)`.
3. **Ternary type promotion surprises**: `true ? 1 : 1.0` yields a `double` (`1.0`) because the branches must align types.
4. **`Integer.equals(Long)`**: Always evaluates to `false` because `equals` checks if the type matches first.
5. **Unchecked cast warnings with generics**: Ignoring them can lead to heap pollution and runtime `ClassCastException` far from the actual cast.
6. **Using `(int) Math.random()`**: `Math.random()` returns `0.0` to `0.999...`. Casting to `int` truncates to `0` always.
7. **Not using `Math.toIntExact()`**: For safe narrowing from `long` to `int` when dealing with unpredictable data.

### Senior-Level Pitfalls (5)
1. **Type erasure hiding ClassCastException**: Generics are erased at runtime. A `List<String>` is just a `List`. Improper casts can insert an `Integer`, causing a crash later when retrieving.
2. **Covariant return types**: Method overriding allows more specific return types, but under the hood, the JVM handles this with bridge methods.
3. **Bridge methods generated by javac**: Hidden methods created to preserve polymorphism with type erasure. Can confuse reflection.
4. **JIT devirtualization assumptions**: Highly optimized checkcasts can be deoptimized if an unexpected subtype suddenly appears in a hot loop.
5. **Module system (JPMS) restrictions**: Reflective casts across module boundaries are strictly enforced and can fail at runtime if `exports`/`opens` are not configured.

---

## PART 8: DEBUGGING

### IntelliJ IDEA for Type Conversion Issues
- **Inspection: redundant casts**: IntelliJ highlights unnecessary casts in gray. `Alt+Enter` to remove them.
- **Inspection: unchecked cast warnings**: Highlighted in yellow. Fix the underlying generic design rather than ignoring them.
- **Inspection: numeric overflow**: Highlights literals that overflow their target type.
- **Quick-fix**: Use `Alt+Enter` to add explicit casts or replace old `instanceof` patterns with Java 16+ pattern matching.
- **Evaluate Expression**: Use `Alt+F8` during debug to test casts or view `instanceof` results in real-time.

### Common Error Messages
- **`incompatible types: X cannot be converted to Y`**: Compile-time error indicating no known conversion path (e.g., `String` to `int`).
- **`possible lossy conversion from X to Y`**: Compile-time error when you attempt to narrow a numeric type without an explicit cast.
- **`inconvertible types: X cannot be cast to Y`**: Compile-time error for explicit casting between unrelated hierarchies (e.g., `String` and `Integer`).
- **`ClassCastException: X cannot be cast to Y`**: Runtime error. The object's actual class is not compatible with the cast target.
- **`unchecked cast`**: Compile-time warning when casting to a parameterized type whose type arguments are erased at runtime.

### JVM Inspection
- **`javap -c`**: Disassemble your `.class` files to see actual bytecode instructions like `i2l` (int to long), `d2i` (double to int), and `checkcast`.
- **Enhanced ClassCastException**: Since Java 14/17, CCE messages clearly state *what* type was provided and *what* type was expected (e.g., "class A cannot be cast to class B").
- **Debugging type erasure**: Use `javap -v` to inspect synthetic bridge methods generated by the compiler.
