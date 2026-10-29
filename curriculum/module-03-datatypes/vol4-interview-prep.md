# Java Mastery Curriculum: Module 3 - Data Types
## Volume 4: Interview Preparation

This document provides a comprehensive set of interview questions and practical exercises designed to test and reinforce your understanding of Java data types.

---

### BASIC QUESTIONS

**1. What are the 8 primitive data types in Java?**
**Answer:** `byte`, `short`, `int`, `long`, `float`, `double`, `char`, and `boolean`.

**2. What is the difference between primitive and reference types?**
**Answer:** Primitive types store their values directly in memory (typically on the stack if they are local variables). Reference types (objects) store a memory address (reference) that points to an object on the heap. Primitives cannot be `null` and do not have methods.

**3. What are wrapper classes? Why do they exist?**
**Answer:** Wrapper classes (`Integer`, `Double`, `Boolean`, etc.) encapsulate a primitive value in an object. They exist because Java's Collections framework and Generics require objects; they cannot handle primitive types directly. They also provide utility methods (like `Integer.parseInt()`).

**4. What is autoboxing and unboxing?**
**Answer:** Autoboxing is the automatic conversion the Java compiler makes between primitive types and their corresponding wrapper classes (e.g., `int` to `Integer`). Unboxing is the reverse process (e.g., `Integer` to `int`).

**5. What are the default values of all primitive types?**
**Answer:** For class/instance variables: `byte`, `short`, `int`, `long` default to `0` / `0L`. `float`, `double` default to `0.0f` / `0.0d`. `char` defaults to `\u0000` (null character). `boolean` defaults to `false`. (Local variables must be initialized explicitly).

**6. What is the range of `int`, `long`, `byte`, `short`?**
**Answer:**
- `byte`: 8-bit (-128 to 127)
- `short`: 16-bit (-32,768 to 32,767)
- `int`: 32-bit (~-2.14 billion to ~2.14 billion)
- `long`: 64-bit (~-9 quintillion to ~9 quintillion)

**7. Why can't you use primitive types with generics?**
**Answer:** Generics in Java are implemented using type erasure. At runtime, generic types are replaced with `Object`. Since primitives do not inherit from `Object`, they cannot be used as generic type arguments.

**8. What is the difference between `float` and `double`?**
**Answer:** `float` is a single-precision 32-bit IEEE 754 floating-point number. `double` is a double-precision 64-bit IEEE 754 floating-point number. `double` is more precise and has a larger range.

**9. What is the size of `boolean` in Java?**
**Answer:** The Java Virtual Machine specification does not mandate a precise size for `boolean`. In arrays, it typically occupies 1 byte per element. As a standalone field, it is often padded to 4 bytes for alignment.

**10. What is the `null` type in Java?**
**Answer:** `null` is a special literal in Java that represents the absence of a value or reference. It can be assigned to any reference type, but cannot be assigned to any primitive type.

---

### INTERMEDIATE QUESTIONS

**1. Explain the Integer cache. What range is cached?**
**Answer:** To save memory and improve performance, Java caches `Integer` instances for values between `-128` and `127` (inclusive) by default. When `Integer.valueOf(x)` is called within this range, it returns the cached instance instead of creating a new object. The upper bound can be modified via the `-XX:AutoBoxCacheMax` JVM flag.

**2. Why should you never use `float`/`double` for monetary calculations?**
**Answer:** Floating-point numbers use base-2 fractions and cannot precisely represent many base-10 fractions (like 0.1). This leads to rounding errors during arithmetic operations. Money requires absolute precision, so `BigDecimal` or integers representing the smallest currency unit (e.g., cents) should be used.

**3. What is two's complement? How does Java represent negative integers?**
**Answer:** Two's complement is the mathematical representation used for signed binary numbers. To get the negative of a number, invert all the bits and add 1. The most significant bit acts as the sign bit (0 for positive, 1 for negative). Java uses two's complement for all integer types.

**4. What happens when integer overflow occurs in Java?**
**Answer:** Java does not throw an exception on integer overflow. It silently wraps around according to two's complement arithmetic. For example, `Integer.MAX_VALUE + 1` results in `Integer.MIN_VALUE`.

**5. How does autoboxing work at the bytecode level?**
**Answer:** The compiler replaces autoboxing with a call to the static `valueOf()` method of the wrapper class (e.g., `Integer.valueOf(5)`). Unboxing is replaced with a call to the corresponding primitive-returning method (e.g., `intValue()`).

**6. What is the memory overhead of wrapper objects vs primitives?**
**Answer:** A primitive `int` takes 4 bytes. An `Integer` object takes at least 16 bytes on a 64-bit JVM (12 bytes for the object header, 4 bytes for the int value, plus potential padding). This is a 4x or greater memory overhead, not including the memory taken by the reference pointing to the object.

**7. Why does `byte + byte` return `int`?**
**Answer:** The JVM does not have bytecode instructions for arithmetic operations on `byte`, `short`, or `char`. These types are implicitly widened to `int` before operations are performed, so the result is of type `int`.

**8. What is IEEE 754? How does Java represent floating-point numbers?**
**Answer:** IEEE 754 is the standard for floating-point arithmetic. Java represents `float` with 1 sign bit, 8 exponent bits, and 23 fraction (mantissa) bits. `double` uses 1 sign bit, 11 exponent bits, and 52 fraction bits.

**9. Explain `NaN` behavior in Java.**
**Answer:** `NaN` (Not a Number) represents an undefined or unrepresentable floating-point result (like 0.0/0.0). `NaN` is not equal to anything, including itself. Therefore, `Float.NaN == Float.NaN` evaluates to `false`. Use `Float.isNaN()` to check for it.

**10. What is the difference between `Integer.valueOf()` and `new Integer()`?**
**Answer:** `valueOf()` uses the Integer cache, returning existing instances for values between -128 and 127. `new Integer()` *always* creates a new object on the heap, wasting memory. The constructor is deprecated for removal in modern Java versions.

---

### ADVANCED QUESTIONS

**1. How does the JVM handle `boolean` at the bytecode level?**
**Answer:** The JVM represents `boolean` as an `int`. `0` represents `false` and `1` represents `true`. Bytecode instructions for `int` are used to manipulate `boolean` local variables.

**2. Explain compressed oops and its impact on reference types.**
**Answer:** On 64-bit JVMs, object references (`oops` - ordinary object pointers) are normally 64-bit (8 bytes). To save memory, "compressed oops" are enabled by default for heaps under 32GB, reducing references to 32-bit (4 bytes). This makes wrappers slightly less memory-intensive, but they still have significant overhead compared to primitives.

**3. What are the JVM bytecode type descriptors for each primitive?**
**Answer:** `B` (byte), `C` (char), `D` (double), `F` (float), `I` (int), `J` (long), `S` (short), `Z` (boolean).

**4. How does the JVM handle narrowing conversions at the bytecode level?**
**Answer:** The JVM provides specific bytecode instructions for narrowing conversions, such as `i2b` (int to byte), `i2s` (int to short), and `d2i` (double to int). These instructions truncate the higher-order bits or convert floating-point values toward zero.

**5. What is the memory layout of an Integer object on the heap?**
**Answer:** With compressed oops enabled: 12-byte object header (Mark Word + Klass Pointer) + 4-byte `int` field. Total 16 bytes. Without compressed oops: 16-byte header + 4-byte `int` field + 4 bytes padding to align to 8-byte boundaries. Total 24 bytes.

**6. How does escape analysis affect autoboxed values?**
**Answer:** The C2 JIT compiler performs escape analysis to determine if an object's reference escapes the current method or thread. If an autoboxed object does not escape, the JIT can apply "scalar replacement", decomposing the object into its primitive fields and storing it in CPU registers or on the stack, eliminating heap allocation.

**7. Explain the IEEE 754 special values (subnormals, infinities, NaN) and their arithmetic rules.**
**Answer:** 
- **Infinities:** Result from operations like `1.0 / 0.0`. `Infinity + 1 = Infinity`. 
- **NaN:** Results from `0.0 / 0.0` or `Math.sqrt(-1)`. Any arithmetic with `NaN` yields `NaN`.
- **Subnormals:** Values extremely close to zero where the exponent is all zeros but the mantissa is non-zero, allowing graceful underflow instead of abruptly becoming zero, though arithmetic is much slower.

**8. What is TLAB and how does it relate to wrapper object allocation?**
**Answer:** Thread-Local Allocation Buffer (TLAB) is a small block of memory in the young generation dedicated to a specific thread. Fast path object allocation (like rapidly creating wrapper objects in a loop without scalar replacement) occurs in the TLAB using bump-the-pointer, making allocation extremely fast (just a pointer addition), though GC overhead still applies.

**9. How does JIT optimize autoboxing in hot loops?**
**Answer:** Through inlining and escape analysis, the JIT can often optimize away autoboxing entirely, converting operations on wrappers back to pure primitive operations in machine code, provided the wrappers don't escape. However, it's brittle—if the loop exceeds inline limits or interacts with generic collections, the optimization fails.

**10. What is the difference between identity and equality for wrapper objects?**
**Answer:** Identity (`==`) checks if two references point to the exact same memory location on the heap. Equality (`.equals()`) checks if the primitive values held by the wrappers are the same. Because of the cache, identity may arbitrarily mirror equality for small numbers, but fail for large numbers.

---

### TRICKY / GOTCHA QUESTIONS

**1. `Integer a=127, b=127; a==b` vs `Integer a=128, b=128; a==b` — explain**
**Answer:** The first expression is `true` due to the Integer cache (-128 to 127). The second is `false` because 128 is outside the cache, resulting in two distinct objects on the heap with different memory addresses.

**2. `0.1 + 0.2 == 0.3` — true or false? Why?**
**Answer:** `false`. Due to base-2 floating-point precision, `0.1 + 0.2` evaluates to `0.30000000000000004`.

**3. `Float.NaN == Float.NaN` — true or false?**
**Answer:** `false`. By IEEE 754 definition, `NaN` is not equal to anything, including itself.

**4. `new Integer(5) == new Integer(5)` — true or false?**
**Answer:** `false`. The `new` keyword always forces the creation of a new object, ignoring the cache. The references are different.

**5. `new Integer(5).equals(new Long(5))` — true or false?**
**Answer:** `false`. The `.equals()` method of wrapper classes first checks if the argument is an instance of the *same class*. Since `Integer` is not `Long`, it returns `false` immediately without comparing the values.

**6. `byte b1=50, b2=50; byte b3 = b1+b2;` — compiles?**
**Answer:** No. Arithmetic operations on `byte` promote the operands to `int`. The result is an `int`, which cannot be assigned back to a `byte` variable without an explicit cast: `byte b3 = (byte) (b1 + b2);`.

**7. `int i = Integer.MAX_VALUE + 1;` — what is i?**
**Answer:** `i` becomes `Integer.MIN_VALUE` (-2147483648) due to silent integer overflow.

**8. `double d = 1.0/0.0;` — exception or result?**
**Answer:** Result. It assigns `Double.POSITIVE_INFINITY` to `d`. (Only integer division by zero throws an `ArithmeticException`).

**9. `int i = (int) Double.NaN;` — what is i?**
**Answer:** `0`. When casting `NaN` to an integer type, the JLS specifies the result is `0`.

**10. `char c = (char) -1;` — what is c?**
**Answer:** `\uFFFF`. `char` is an unsigned 16-bit type. Casting `-1` sets all 16 bits to 1, which represents the maximum char value (65535).

---

### MCQs (Multiple Choice Questions)

**1. Which of the following is NOT a primitive data type?**
A. char
B. String
C. boolean
D. float
*Answer: B* (String is an object/reference type)

**2. What is the size of a `long` in Java?**
A. 4 bytes
B. 8 bytes
C. 16 bytes
D. JVM dependent
*Answer: B* (Always 64 bits / 8 bytes)

**3. Which literal notation is valid for an integer in Java?**
A. `0x1A` (Hexadecimal)
B. `0b1010` (Binary)
C. `012` (Octal)
D. All of the above
*Answer: D*

**4. What will be the output of `System.out.println(1.0 / 0);`?**
A. ArithmeticException
B. NaN
C. Infinity
D. 0.0
*Answer: C* (Double arithmetic supports infinity)

**5. Which data type should be used for exact decimal representations, such as currency?**
A. double
B. float
C. BigDecimal
D. long
*Answer: C*

**6. What is the output of `System.out.println((byte) 128);`?**
A. 128
B. -128
C. 0
D. Compile error
*Answer: B* (Integer 128 overflows the byte range, wrapping around to the minimum negative value)

**7. Which of the following allows checking for NaN correctly?**
A. `if (d == Double.NaN)`
B. `if (d.equals(Double.NaN))`
C. `if (Double.isNaN(d))`
D. `if (d != d)`
*Answer: C and D* (C is standard API, D works but is tricky)

**8. Which statement is true regarding the `boolean` type?**
A. It is exactly 1 bit in size
B. It can be explicitly cast from an int
C. Its size is JVM dependent
D. Its default value is `true`
*Answer: C*

**9. What is the result of `int a = 'A';`?**
A. Compile error
B. a = 65
C. a = 97
D. a = 0
*Answer: B* (Implicit widening conversion from char to int)

**10. How many bits are dedicated to the fraction in a double?**
A. 23
B. 52
C. 53
D. 64
*Answer: B*

**11. What does the JVM use to store the boolean values in an array?**
A. 1 bit per element
B. 1 byte per element
C. 4 bytes per element
D. 8 bytes per element
*Answer: B* (typically, though JVM dependent)

**12. When comparing two objects of type `Integer`, you should use:**
A. `==`
B. `compareTo()` or `.equals()`
C. `=`
D. `===`
*Answer: B*

**13. Which of the following types does NOT have a corresponding wrapper class?**
A. int
B. byte
C. String
D. boolean
*Answer: C* (String is already an object)

**14. What happens when you do `System.out.println(10.0 % 0.0);`?**
A. ArithmeticException
B. NaN
C. Infinity
D. 0.0
*Answer: B*

**15. Can an `int` variable hold the value `2147483648`?**
A. Yes
B. No, it exceeds the max value
C. Yes, but it will be automatically converted to long
D. No, it will throw an exception at runtime
*Answer: B* (Requires a long literal `2147483648L`)

---

### OUTPUT PREDICTION

**Snippet 1:**
```java
int a = 2147483647;
System.out.println(a + 1);
```
**Output:** `-2147483648` (Integer overflow).

**Snippet 2:**
```java
System.out.println(10 / 3);
```
**Output:** `3` (Integer division truncates the decimal).

**Snippet 3:**
```java
Integer x = 100;
Integer y = 100;
Integer z = 500;
Integer w = 500;
System.out.println((x == y) + " " + (z == w));
```
**Output:** `true false` (Cache applies to 100, but not 500).

**Snippet 4:**
```java
double a = 0.02;
double b = 0.03;
double c = b - a;
System.out.println(c == 0.01);
```
**Output:** `false` (Precision issues make `c` something like `0.009999999999999998`).

**Snippet 5:**
```java
long l = 1234567890123L;
int i = (int) l;
System.out.println(i);
```
**Output:** `-1093242827` (Lossy narrowing conversion truncates the upper 32 bits).

**Snippet 6:**
```java
System.out.println((float) (1 << 24) == ((float) (1 << 24) + 1));
```
**Output:** `true` (A `float` has only 24 bits of precision. Adding 1 at this magnitude is lost to precision error).

**Snippet 7:**
```java
char c = 'A';
c++;
System.out.println(c);
```
**Output:** `B` (char operations are valid and map to Unicode code points).

**Snippet 8:**
```java
Object num = 10;
System.out.println(num.getClass().getName());
```
**Output:** `java.lang.Integer` (Autoboxing wraps the primitive literal in an Integer object).

**Snippet 9:**
```java
System.out.println(Double.compare(0.0, -0.0));
```
**Output:** `1` (`Double.compare` distinguishes between positive and negative zero, unlike `==`).

**Snippet 10:**
```java
System.out.println(0.0 == -0.0);
```
**Output:** `true` (The `==` operator considers positive and negative zero mathematically equal).

---

### CODE TRACING

**Program 1: The Boxing Trap**
```java
public class BoxingTest {
    public static void main(String[] args) {
        Long sum = 0L;
        for (int i = 0; i < 1000; i++) {
            sum = sum + i;
        }
        System.out.println(sum);
    }
}
```
*Trace:* The loop runs 1000 times. `sum` is a `Long`. In `sum + i`, `sum` is unboxed to `long`, `i` is widened to `long`, addition occurs, and the result is autoboxed back into a *new* `Long` object. This creates 1000 unnecessary `Long` objects on the heap.

**Program 2: Short Addition**
```java
public class ShortTest {
    public static void main(String[] args) {
        short s1 = 10;
        short s2 = 10;
        // short s3 = s1 + s2; // Line A
        short s3 = (short) (s1 + s2); // Line B
        s1 += s2; // Line C
    }
}
```
*Trace:* Line A would not compile because `s1 + s2` results in an `int`. Line B explicitly casts back to `short`, which works. Line C (`s1 += s2`) works implicitly because compound assignment operators contain a hidden cast.

**Program 3: Wrapper Equality**
```java
public class WrapperEquality {
    public static void main(String[] args) {
        Integer i1 = 10;
        Integer i2 = 10;
        Double d1 = 10.0;
        
        System.out.println(i1.equals(d1));
    }
}
```
*Trace:* The output is `false`. The `.equals()` method of `Integer` first checks `if (obj instanceof Integer)`. Because `d1` is a `Double`, it fails the type check immediately and returns false.

**Program 4: Null Unboxing**
```java
public class UnboxingTest {
    static Integer getCount() { return null; }
    
    public static void main(String[] args) {
        int count = getCount();
        System.out.println(count);
    }
}
```
*Trace:* `getCount()` returns `null`. The assignment `int count = getCount();` implicitly calls `.intValue()` on the returned reference. Since the reference is `null`, it throws a `NullPointerException`.

**Program 5: String Concatenation vs Addition**
```java
public class ConcatTest {
    public static void main(String[] args) {
        System.out.println(10 + 20 + "30" + 40 + 50);
    }
}
```
*Trace:* Expressions evaluate left to right. `10 + 20` is `30`. `30 + "30"` becomes `"3030"`. `"3030" + 40` becomes `"303040"`. `"303040" + 50` becomes `"30304050"`. Output is `30304050`.

---

### SCENARIO QUESTIONS

**Scenario 1:** You are building a High-Frequency Trading application. You need to calculate moving averages of stock prices rapidly. Would you use `Double`, `double`, or `BigDecimal`?
**Answer:** While `BigDecimal` is strictly required for *storing* monetary balances to avoid lost pennies, high-frequency *statistical calculations* (like moving averages) where latency is paramount and slight rounding at the tail end is acceptable for the formula, primitive `double` is often used. Wrapper `Double` is avoided due to GC overhead.

**Scenario 2:** An API consumes JSON where a `discountPercent` field can be omitted entirely. How do you map this to a Java class field?
**Answer:** Use the wrapper class `Integer` or `Double`. A primitive `int` would default to `0`, which makes it impossible to distinguish between "0% discount explicitly provided" and "field not provided". The wrapper allows the field to remain `null`.

**Scenario 3:** You notice your application is spending 20% of its CPU time in Garbage Collection. Profiling shows millions of `Integer` arrays being created. How do you resolve this?
**Answer:** Refactor the codebase to use primitive `int[]` arrays instead of `Integer[]`. You may need to replace generic collections like `ArrayList<Integer>` with primitive-specialized collections (e.g., from libraries like Trove, Fastutil, or Eclipse Collections).

**Scenario 4:** A junior developer wrote a method that checks if two `Float` objects are equal using `==`. Sometimes it works, sometimes it fails. Why?
**Answer:** `==` compares object references. The JVM does not aggressively cache `Float` or `Double` wrappers like it does for small `Integer`s. Therefore, autoboxed `Float` values will generally result in distinct objects, causing `==` to return `false` even if the wrapped values are mathematically equal.

**Scenario 5:** You need to represent a massive dataset of a billion boolean values in memory to track visited nodes in a graph. What is the most memory-efficient approach?
**Answer:** Using a `boolean[]` would consume approximately 1 GB (1 byte per boolean). Using an array of `Boolean` objects would consume far more. The most efficient approach is to use a `java.util.BitSet`, which packs 64 boolean values into a single `long`, reducing the memory footprint to just ~125 MB (1 bit per boolean).
