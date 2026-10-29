# Module 3 Volume 2: Data Types (Examples)

This document provides a comprehensive set of examples demonstrating Java's primitive and wrapper types, compiler behaviors, runtime quirks, and performance implications. All examples are designed for Java 21 LTS and run on the HotSpot JVM.

## Table of Contents
1. [Simple Examples](#simple-examples)
2. [Intermediate Examples](#intermediate-examples)
3. [Advanced Examples](#advanced-examples)
4. [Production Examples](#production-examples)
5. [Wrong Examples — Compiler Errors](#wrong-examples--compiler-errors)
6. [Wrong Examples — Runtime Errors](#wrong-examples--runtime-errors)
7. [Edge Cases](#edge-cases)
8. [Performance Examples](#performance-examples)
9. [Interview Examples](#interview-examples)

---

## Simple Examples

### 1. Declaring and Printing Primitive Types
Java has 8 primitive types. Their sizes are strictly defined by the JVM specification.
```java
System.out.println(Byte.BYTES);       // 1
System.out.println(Short.BYTES);      // 2
System.out.println(Integer.BYTES);    // 4
System.out.println(Long.BYTES);       // 8
System.out.println(Float.BYTES);      // 4
System.out.println(Double.BYTES);     // 8
System.out.println(Character.BYTES);  // 2
// boolean size is JVM-dependent (no Boolean.BYTES)
```

### 2. Default Values for Primitive Instance Fields
Uninitialized instance fields (class level) receive default values.
```java
class DefaultDemo {
    int i;       // defaults to 0
    double d;    // defaults to 0.0
    boolean b;   // defaults to false
    char c;      // defaults to '\u0000'
}
```
*Note: Local variables in methods do NOT get default values and must be explicitly initialized.*

### 3. Wrapper Class Creation
```java
Integer a = Integer.valueOf(42); // Recommended
Integer b = new Integer(42);     // Deprecated since Java 9
```
*Explanation:* `valueOf()` utilizes the internal IntegerCache for values between -128 and 127. The constructor forces object allocation.

### 4. Autoboxing (Bytecode Proof)
```java
Integer x = 42;
```
*Compiler Behavior:* Translates to `Integer.valueOf(42)`. You can verify this via `javap -c`.

### 5. Unboxing (Bytecode Proof)
```java
int y = Integer.valueOf(42);
```
*Compiler Behavior:* Translates to `Integer.valueOf(42).intValue()`.

### 6. Character as Numeric
```java
char c = 'A';
int n = c; // Implicit cast
System.out.println(n); // Output: 65
```

### 7. Boolean Usage
```java
boolean isTrue = true;
// int x = (int) isTrue; // ERROR: Cannot cast boolean to int
```
Unlike C/C++, Java's boolean is completely distinct from integer types.

### 8. Min and Max Values
```java
System.out.println(Integer.MIN_VALUE); // -2147483648
System.out.println(Integer.MAX_VALUE); // 2147483647
```

### 9. String Conversion
```java
String s1 = String.valueOf(42);
String s2 = String.valueOf(true);
```

---

## Intermediate Examples

### 1. The Integer Cache
```java
Integer a = 127; Integer b = 127;
System.out.println(a == b); // true

Integer x = 128; Integer y = 128;
System.out.println(x == y); // false
```
*Explanation:* JVM caches wrappers for bytes, shorts, ints, and longs from -128 to 127. Values outside this allocate new objects. Use `.equals()` to compare wrapper objects.

### 2. IEEE 754 Quirks
```java
System.out.println(0.1 + 0.2); // Output: 0.30000000000000004
double nan = Double.NaN;
System.out.println(nan == nan); // false
System.out.println(Double.isNaN(nan)); // true
```

### 3. Integer Overflow
```java
int max = Integer.MAX_VALUE;
System.out.println(max + 1); // -2147483648 (Silent Overflow)
Math.addExact(max, 1); // Throws ArithmeticException
```

### 4. Autoboxing in Expressions
```java
Integer a = 1; Integer b = 2;
Integer c = a + b;
```
*Sequence:* Unbox `a` and `b` -> add primitive ints -> box result via `Integer.valueOf(3)`.

### 5. Unboxing NPE Trap
```java
Integer x = null;
int y = x; // NullPointerException at runtime (x.intValue())
```

### 6. Char Arithmetic
```java
int val = 'A' + 1; // 66
char next = (char) ('A' + 1); // 'B'
```

### 7. Type Promotion
```java
byte b1 = 10, b2 = 20;
// byte b3 = b1 + b2; // ERROR: Result is promoted to int
int result = b1 + b2; // OK
```

---

## Advanced Examples

### 1. Memory Measurement (int[] vs Integer[])
A primitive `int` takes 4 bytes. An `Integer` object takes 16 bytes (object header) + 4 bytes (payload) + 4 bytes (reference in array). 
1 million `int[]` = ~4 MB. 
1 million `Integer[]` = ~24 MB.

### 2. Autoboxing Performance Trap
```java
Long sum = 0L;
for (long i = 0; i < Integer.MAX_VALUE; i++) {
    sum += i; // Creates 2 billion Long objects!
}
```
*Fix:* Use `long sum = 0L;`

### 3. Extending the Integer Cache
Run the JVM with:
`java -XX:AutoBoxCacheMax=1000`
Now `Integer.valueOf(500) == Integer.valueOf(500)` returns `true`.

### 4. IEEE 754 Bit Representation
```java
int bits = Float.floatToIntBits(3.14f);
System.out.println(Integer.toBinaryString(bits));
```

### 5. BigDecimal vs Double
```java
BigDecimal a = new BigDecimal("0.1");
BigDecimal b = new BigDecimal("0.2");
System.out.println(a.add(b)); // Output: 0.3
```

---

## Production Examples

### 1. Type-Safe Money Calculation
```java
BigDecimal price = new BigDecimal("19.99");
BigDecimal tax = new BigDecimal("0.08");
BigDecimal total = price.add(price.multiply(tax)).setScale(2, RoundingMode.HALF_EVEN);
```

### 2. Robust Parsing
```java
public int parseAge(String input) {
    try {
        return Integer.parseInt(input);
    } catch (NumberFormatException e) {
        return -1; // Or throw custom exception
    }
}
```

### 3. Configuration Object Design
```java
public class Config {
    private Integer port; // Nullable - indicates "not set"
    private int timeout = 5000; // Primitive - always has a value
}
```

---

## Wrong Examples — Compiler Errors

1. `int x = 3.14;` (Incompatible types: possible lossy conversion from double to int)
2. `float f = 3.14;` (Needs 'f' suffix, 3.14 is a double literal)
3. `byte b = 128;` (Out of range for byte, max is 127)
4. `boolean b = 1;` (int cannot be converted to boolean)
5. `int x = true;` (boolean cannot be converted to int)
6. `char c = -1;` (char is unsigned, 0 to 65535)
7. `List<int> list = new ArrayList<>();` (Generics require objects/wrappers)
8. `String s = 42;` (int cannot be converted to String)
9. `Integer i = "hello";` (String cannot be converted to Integer)
10. `byte b1 = 50, b2 = 50; byte b3 = b1 + b2;` (Result of + is int)
11. `int x = 1; short s = 'A' + x;` (Variables prevent compile-time constant narrowing)

---

## Wrong Examples — Runtime Errors

1. **Unboxing Null:** `Integer x = null; int y = x;` (NPE)
2. **Invalid String Parsing:** `Integer.parseInt("abc");` (NumberFormatException)
3. **Integer Div by Zero:** `int x = 10 / 0;` (ArithmeticException)
4. **Exact Overflow:** `Math.addExact(Integer.MAX_VALUE, 1);` (ArithmeticException)
5. **ClassCastException:** `Object o = "hello"; Integer i = (Integer) o;`

---

## Edge Cases

1. `double d = 1.0 / 0;` (Output: `Infinity`. Floating point div-by-zero does not throw exceptions).
2. `float f = Float.NaN; System.out.println(f == f);` (false)
3. `char c = 0;` (Valid. It represents the null character `\u0000`).
4. `boolean` size mystery: The JVM spec does not mandate the size of boolean arrays vs fields (fields are often 1 byte, array elements might be mapped to byte arrays).
5. `Integer.valueOf(0) == Integer.valueOf(0)` (true) vs `new Integer(0) == new Integer(0)` (false).
6. `new Integer(5) == 5` (true, the object is unboxed for primitive comparison).
7. `byte b = (byte) 256;` (Output: 0. Truncation discards higher bits).

---

## Performance Examples

1. **Loop Sum:** Mentioned in advanced (using wrapper in a loop allocates objects continuously, causing 10x-50x slowdowns).
2. **Array Allocation:** Primitive arrays contiguous memory; Wrapper arrays are arrays of references to objects scattered in the heap.

---

## Interview Examples

### 1. "Why does `Integer.valueOf(127) == Integer.valueOf(127)` return true but 128 returns false?"
*Answer:* The JVM maintains an internal cache (`IntegerCache`) for Integer objects representing values from -128 to 127. `valueOf` reuses these cached objects. 128 is outside the default cache range, resulting in a new object allocation.

### 2. "Why can't you use `==` to compare two Integer objects?"
*Answer:* `==` checks for reference equality (if they point to the exact same memory location). For values outside the cache, this returns false. Always use `.equals()` to check logical value equivalence.

### 3. "Why does `0.1 + 0.2 != 0.3` in Java?"
*Answer:* This is not a Java issue, but an IEEE 754 standard issue. Floating-point numbers are represented in binary (base-2) fractions. Decimals like 0.1 cannot be represented precisely in binary, resulting in tiny precision errors. Use `BigDecimal` when precision is critical (e.g., currency).
