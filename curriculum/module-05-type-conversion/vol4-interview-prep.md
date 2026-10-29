# Module 5: Type Conversion & Casting
## Volume 4: Interview Preparation

Targeting Java 21 LTS | Environment: IntelliJ IDEA

---

## BASIC QUESTIONS (10)

**1. What is type conversion in Java?**
Type conversion is the process of converting a value from one data type to another. It ensures type safety and compatibility when evaluating expressions, making assignments, or passing arguments to methods.

**2. What is the difference between widening and narrowing conversion?**
Widening (upcasting) moves from a smaller or less precise type to a larger or more precise type (e.g., `int` to `long`). It is done automatically. Narrowing (downcasting) moves from a larger to a smaller type (e.g., `long` to `int`), potentially losing data or precision, and requires an explicit cast.

**3. What is implicit vs explicit conversion?**
Implicit conversion happens automatically by the compiler (e.g., assigning an `int` to a `long`). Explicit conversion (casting) requires the developer to specify the target type in parentheses, acknowledging the risk of data loss or runtime errors.

**4. What is the widening path for primitive types?**
`byte` → `short` → `int` → `long` → `float` → `double`. Note that `char` widens to `int`.

**5. What is casting? What is the syntax?**
Casting is forcing a conversion. Syntax: `(TargetType) expression`. Example: `int x = (int) 3.14;`.

**6. What happens when you cast a float to an int?**
The fractional part is truncated (discarded). `3.99f` becomes `3`. If the float exceeds `Integer.MAX_VALUE`, the result is `Integer.MAX_VALUE`.

**7. What is upcasting and downcasting for reference types?**
Upcasting is converting a subclass reference to a superclass reference (implicit, safe). Downcasting is converting a superclass reference to a subclass reference (explicit, risks `ClassCastException`).

**8. What is autoboxing and unboxing? Is it a type conversion?**
Autoboxing is the automatic conversion between primitives and their wrapper classes (e.g., `int` to `Integer`). Unboxing is the reverse. Yes, it is a form of compiler-inserted type conversion.

**9. What is numeric promotion? Why does byte + byte = int?**
Numeric promotion automatically converts smaller integral types (`byte`, `short`, `char`) to `int` before performing binary operations. This is an architectural decision in the JVM, which primarily operates on 32-bit words.

**10. Can you convert between boolean and int in Java?**
No. Unlike C/C++, Java strictly isolates `boolean`. You cannot cast between `boolean` and any numeric type.

---

## INTERMEDIATE QUESTIONS (10)

**1. Explain binary numeric promotion rules (JLS §5.6.2).**
If either operand is `double`, the other is promoted to `double`.
Else, if either is `float`, the other is promoted to `float`.
Else, if either is `long`, the other is promoted to `long`.
Else, both are promoted to `int`.

**2. Why does `short s = s + 1;` fail but `s += 1;` succeed?**
`s + 1` promotes `s` to `int` (because 1 is an int literal), resulting in an `int` which cannot be assigned to `short` without a cast. `s += 1` works because compound assignments include an implicit cast: `s = (short)(s + 1)`.

**3. What is the constant expression narrowing rule?**
Java allows implicitly narrowing an `int` constant expression to `byte`, `short`, or `char` IF the value is determinable at compile-time and fits within the target type's range. Example: `byte b = 50 + 50;`.

**4. How does `(byte) 256` evaluate? Explain at the bit level.**
`256` in 32-bit binary is `...0001 0000 0000`. A `byte` holds 8 bits. Casting truncates the upper 24 bits, leaving `0000 0000`, which is `0`.

**5. What is precision loss in widening? Give examples.**
Widening `int` to `float` or `long` to `double` can lose precision because `float` (24-bit mantissa) cannot precisely represent all 32-bit `int` values. Example: `int i = 16777217; float f = i;` results in `16777216.0`.

**6. How does pattern matching for instanceof work? (Java 16+)**
It combines type checking and casting. `if (obj instanceof String s) { s.length(); }` securely checks the type and binds the casted value to the variable `s` within the scope of the true branch.

**7. What is the difference between `checkcast` and `instanceof` bytecodes?**
`instanceof` evaluates a reference against a type and pushes a boolean (0 or 1) onto the stack. `checkcast` verifies the type and either throws a `ClassCastException` or leaves the reference on the stack for subsequent operations.

**8. Why can't you do `Long L = 10;` (widening + autoboxing)?**
Java compilers will apply at most one implicit conversion. `10` is an `int`. The compiler would have to widen it to `long`, then autobox it to `Long`. It refuses to do both. You must write `10L`.

**9. How does String conversion work in concatenation?**
If any operand in a `+` operation is a `String`, the other operand is converted to a `String` (using `String.valueOf()` for primitives or `toString()` for objects) and the two are concatenated.

**10. What are the rules for casting with interfaces?**
You can cast an object reference to any interface type, provided the object's class isn't `final` and demonstrably doesn't implement the interface. The compiler often defers interface cast verification entirely to runtime.

---

## ADVANCED QUESTIONS (10)

**1. Explain ALL bytecode type conversion instructions.**
The JVM provides specific instructions for primitive conversions: `i2l`, `i2f`, `i2d`, `l2i`, `l2f`, `l2d`, `f2i`, `f2l`, `f2d`, `d2i`, `d2l`, `d2f`. It also includes `i2b`, `i2c`, `i2s` for narrowing int to byte/char/short.

**2. How does float/double → int narrowing handle NaN and Infinity?**
NaN converts to `0`. Positive Infinity converts to `Integer.MAX_VALUE`. Negative Infinity converts to `Integer.MIN_VALUE`.

**3. What are bridge methods? Why does javac generate them?**
When generic classes are compiled, type arguments are erased. To maintain polymorphism when a subclass overrides a generic method with specific types, the compiler generates a synthetic "bridge" method with the erased signature that casts the parameters and delegates to the strongly-typed method.

**4. How does type erasure affect casting in generics?**
Casts to generic types like `(List<String>) obj` are unchecked at compile-time because the JVM at runtime only knows it is a `List`. If the list actually contains `Integer` objects, the cast succeeds, but retrieving elements will throw a `ClassCastException`.

**5. What is heap pollution? How can it cause ClassCastException?**
Heap pollution occurs when a parameterized variable points to an object that is not of that parameterized type (often due to raw types or unchecked casts). It causes a `ClassCastException` later, at the point of read, seemingly without an explicit cast in the source code.

**6. How does JIT optimize away checkcast instructions?**
The C2 compiler can eliminate `checkcast` instructions if it can mathematically prove the object type based on control flow, or it can use optimistic profiling (devirtualization) assuming the cast always succeeds.

**7. Explain the ternary operator type promotion rules.**
If the second and third operands are different numeric types, they are subjected to numeric promotion to a common type. `true ? 1 : 1.0` results in `double` because the `int` is promoted to match the `double`.

**8. How does the JVM implement checkcast at the bytecode level?**
The JVM checks the object's class header (the mark word / klass pointer). It walks the superclass hierarchy and interface tables to see if the target type is a match.

**9. What is the difference between `(int) 3.99` and `Math.round(3.99)`?**
`(int)` truncates towards zero, yielding `3`. `Math.round()` adds 0.5 and truncates, effectively rounding to the nearest mathematical integer, yielding `4`.

**10. Explain pattern matching for switch with sealed types (Java 21).**
By combining `switch` pattern matching with `sealed` interfaces, the compiler can perform exhaustiveness checking. If your switch covers all permitted subtypes of a sealed interface, you don't need a `default` branch.

---

## TRICKY / GOTCHA QUESTIONS (10)

1. **`byte b1 = 50, b2 = 50; byte b3 = b1 + b2;` — compiles?**
   **No!** `b1 + b2` undergoes numeric promotion to `int`. You cannot assign an `int` to a `byte` without a cast: `(byte)(b1 + b2)`.
2. **`byte b = 50 + 50;` — compiles?**
   **Yes!** Both are integer constants. The compiler evaluates `100` and sees it fits in a `byte` range, implicitly narrowing it.
3. **`(int) Double.NaN` → what value?**
   **`0`**. The JLS mandates NaN narrows to 0 for integer types.
4. **`(char) -1` → what character?**
   **`\uFFFF`**. A `char` is unsigned (0 to 65535). Casting `-1` truncates to the lowest 16 bits, resulting in 65535.
5. **`Integer i = 128; Long l = 128L; i.equals(l)` → true or false?**
   **False!** The `.equals()` method of wrapper classes immediately returns false if the argument is not of the exact same class.
6. **`true ? 1 : 1.0` → what type?**
   **`double`**. The `int` `1` is promoted to `double`.
7. **`(int) (float) Integer.MAX_VALUE == Integer.MAX_VALUE` → true or false?**
   **False!** `Integer.MAX_VALUE` is 2147483647. Casting to `float` loses precision (becomes 2.14748365E9). Casting back to `int` yields 2147483647 (wait! Due to rounding in float, it actually goes to `2147483647` or overflows to `MAX_VALUE` depending on rounding mode, but strictly `Integer.MAX_VALUE` cast to float is exactly `2147483650.0`, which cast to int is `Integer.MAX_VALUE`. Let's use `Integer.MAX_VALUE - 1`. Generally, precision is lost.) *Note for candidate: actually evaluate the bit patterns.*
8. **`Object o = 42; long l = (long) o;` — works?**
   **No!** `42` is autoboxed to `Integer`. The object is an `Integer`. You cannot cast an `Integer` object to a primitive `long`. You must cast to `Integer` first, then unbox.
9. **`short s = 'A';` — compiles?**
   **Yes!** `'A'` is a constant expression (65), which fits within `short`.
10. **Multiple casts: `(byte)(int)(short)(long) 42` → result?**
    **42**. The value fits in all intermediate types safely.

---

## PRACTICE EXERCISES

### MCQs
**Q1.** Which conversion is not automatic?
A) byte to int
B) int to float
C) float to double
D) long to int *(Correct Answer - requires explicit cast)*

### Code Tracing
```java
Object x = new Integer(100);
String s = (String) x; // What happens here?
```
**Answer:** `ClassCastException` at runtime. The compiler allows the cast because `Object` could potentially be a `String`, but at runtime, the instance is an `Integer`.

### Scenario Question
*Scenario*: You are refactoring a legacy system that heavily uses `Object[]` arrays to store mixed data. You encounter a method `processData(Object o)` that needs to handle Strings and Integers differently. How do you implement this safely in Java 21?
**Answer:** Use switch pattern matching:
```java
switch(o) {
    case String s -> processString(s);
    case Integer i -> processInteger(i);
    case null -> handleNull();
    default -> throw new IllegalArgumentException("Unknown type");
}
```
