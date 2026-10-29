# Module 5: Type Conversion & Casting (Volume 2 - Examples)

This document provides an **EXTREMELY comprehensive** list of examples for Type Conversion and Casting in Java. It covers primitive types, reference types, edge cases, compiler/runtime errors, performance aspects, and typical interview questions.

## 1. SIMPLE EXAMPLES

**1. Widening Chain: `byte → short → int → long → float → double`**
```java
byte b = 10;
short s = b;
int i = s;
long l = i;
float f = l;
double d = f;
// Expected Output: 10.0 (at double level)
// JVM behavior: uses i2l, l2f, f2d opcodes. No explicit cast required.
```

**2. Narrowing with Cast: Truncation and Wrapping**
```java
byte narrowedByte = (byte) 300; // 300 = 0x12C -> 0x2C = 44
short narrowedShort = (short) 70000; // 70000 = 0x11170 -> 0x1170 = 4464
int narrowedInt = (int) 3.99; // Truncation to 3
// JVM behavior: f2i drops fractional part. i2b truncates top 24 bits.
```

**3. `char` → `int` Widening**
```java
char c = 'A';
int i = c; 
// Output: 65
// char is 16-bit unsigned, int is 32-bit signed. Fits perfectly.
```

**4. `int` → `char` Narrowing**
```java
char c = (char) 65;
// Output: 'A'
// Explicit cast required because int is larger.
```

**5. Automatic Numeric Promotion**
```java
byte b1 = 10;
byte b2 = 20;
// byte sum = b1 + b2; // ERROR: Incompatible types (int to byte)
int sum = b1 + b2;
// The operands are promoted to int before addition.
```

**6. Compound Assignment Implicit Cast**
```java
short s = 10;
s += 5; // Works! Equivalent to: s = (short)(s + 5)
// s = s + 5; // ERROR: s + 5 is int
```

**7. Constant Expression Narrowing**
```java
byte b1 = 100; // Works (100 is literal within -128..127 range)
// byte b2 = 128; // ERROR: 128 doesn't fit in byte
```

**8. Upcasting (Reference Widening)**
```java
String s = "hello";
Object obj = s; // Implicit, always safe
```

**9. Downcasting (Reference Narrowing)**
```java
Object obj = "hello";
String s = (String) obj; // Explicit cast required. checkcast bytecode.
```

## 2. INTERMEDIATE EXAMPLES

**1. Precision Loss in Widening**
```java
int big = 123_456_789;
float f = big; // 1.23456792E8 (Lost exact units)
// float has 24 bits of mantissa, int has 32. Lower bits are rounded.
```

**2. Float/Double to Int Special Values**
```java
int nanToInt = (int) Double.NaN; // Output: 0
int infToInt = (int) Double.POSITIVE_INFINITY; // Output: Integer.MAX_VALUE (2147483647)
```

**3. Pattern Matching `instanceof` (Java 16+)**
```java
// Old way
if (obj instanceof String) {
    String s = (String) obj;
    System.out.println(s.length());
}
// Pattern matching
if (obj instanceof String s) {
    System.out.println(s.length()); // Safe, no cast needed!
}
```

**4. Pattern Matching `switch` (Java 21)**
```java
return switch(obj) {
    case Integer i -> "Int: " + i;
    case String s -> "String: " + s.toUpperCase();
    case null -> "Null!";
    default -> "Unknown";
};
```

**5. Autoboxing + Widening Chain**
```java
int val = 42;
Integer wrapper = val; // Autoboxing
Number num = wrapper; // Upcasting
Object obj = num; // Upcasting
// Works perfectly!
```

**6. Forbidden Widening + Autoboxing**
```java
// Long L = 10; // ERROR! 10 is int. Widening int->long and autoboxing long->Long cannot happen simultaneously.
Long L = 10L; // Works. long literal -> Long autoboxing.
```

**7. String Conversion**
```java
String s1 = "" + 42; // "42" (primitive to String)
String s2 = "" + null; // "null" (null literal appended)
String s3 = "" + new Object(); // "java.lang.Object@..." (toString called)
```

## 3. ADVANCED EXAMPLES

**1. Bytecode Proof of Conversions**
When compiling `long l = 10; float f = l;`:
```text
 0: ldc2_w        #7                  // long 10l
 3: lstore_1
 4: lload_1
 5: l2f                               // Converts long to float
 6: fstore_3
```

**2. Binary-Level Narrowing**
```java
int i = 0x1FF; // 511 (0000 0000 0000 0000 0000 0001 1111 1111)
byte b = (byte) i; // Extracts bottom 8 bits -> 1111 1111 (-1 in 2's complement)
```

**3. Enhanced ClassCastException (Java 17+)**
```java
Object obj = 42; // Integer
String s = (String) obj; 
// Error: Cannot cast java.lang.Integer to java.lang.String
```

**4. Generics and Type Erasure**
```java
List raw = new ArrayList<Integer>();
raw.add("Not an int");
List<Integer> clean = raw; // Unchecked assignment warning
Integer fail = clean.get(0); // ClassCastException at runtime due to synthetic cast inserted by compiler!
```

**5. Numeric Promotion in Ternary Operator**
```java
Object result = true ? 1 : 1.0;
// result is a Double (1.0). The int 1 is promoted to match 1.0!
```

## 4. PRODUCTION EXAMPLES

**1. Safe Casting Utility with Optional**
```java
public static <T> Optional<T> safeCast(Object obj, Class<T> type) {
    return type.isInstance(obj) ? Optional.of(type.cast(obj)) : Optional.empty();
}
```

**2. Type-Safe Heterogeneous Container**
```java
private Map<Class<?>, Object> container = new HashMap<>();
public <T> void putFavorite(Class<T> type, T instance) {
    container.put(type, type.cast(instance));
}
```

**3. Parsing User Input Safely**
```java
public int parseOrDefault(String input, int defaultVal) {
    try {
        return Integer.parseInt(input);
    } catch (NumberFormatException e) {
        return defaultVal;
    }
}
```

## 5. WRONG EXAMPLES — COMPILER ERRORS
1. `byte b = b1 + b2;` — int cannot be assigned to byte.
2. `float f = 3.14;` — double cannot be assigned to float.
3. `long l = 2_147_483_648;` — integer too large (missing L suffix).
4. `int x = obj;` — Object cannot be assigned to int (requires cast & unboxing).
5. `String s = 42;` — int cannot be assigned to String.
6. `Long L = 10;` — cannot box int into Long.
7. `boolean b = (boolean) 1;` — boolean is incompatible with numeric types.
8. `int x = 100; byte b = x;` — x is not a constant variable (needs final).
9. `String s = (String) new Integer(5);` — inconvertible types.
10. `short s = s + 1;` — s+1 resolves to int, cannot assign back to short.

## 6. WRONG EXAMPLES — RUNTIME ERRORS
1. **ClassCastException**: `Object obj = 5; String s = (String) obj;`
2. **NullPointerException**: `Integer i = null; int x = i;` (Fails at `i.intValue()`)
3. **NumberFormatException**: `Integer.parseInt("abc");`
4. **ArithmeticException**: `Math.toIntExact(Long.MAX_VALUE);` (Overflows instead of truncating)
5. **ArrayStoreException**: `Object[] arr = new String[5]; arr[0] = 42;` (Array covariance failure)

## 7. EDGE CASES
1. `(byte)(int)(short)(long) 42` → Output: `42`. Chained casts execute right to left.
2. `char c = (char) -1;` → `\uFFFF`. (65535, largest unsigned char).
3. `(int) Float.NaN == (int) Double.NaN` → `true`. Both evaluate to 0.
4. `long l = (long) Long.MAX_VALUE; float f = l; long l2 = (long) f;` → `l2` is `Long.MAX_VALUE`. It rounds to the nearest boundary.
5. `Integer i = 128; Long l = 128L; i.equals(l);` → `false`. `equals()` requires exact same class.
6. `(int) -0.0` → `0`, `(int) 0.0` → `0`. Primitive zeros behave identically when cast to int.

## 8. PERFORMANCE EXAMPLES
1. **Autoboxing in loops:**
   ```java
   Long sum = 0L; // Bad practice!
   for (int i = 0; i < 1000000; i++) sum += i; // Unboxes, adds, and boxes every iteration
   ```
2. **Checkcast elimination:** The JIT compiler can optimize away explicit casts inside a loop if it proves the types are homogeneous, skipping the overhead of `checkcast` bytecode.

## 9. INTERVIEW EXAMPLES
1. **"Why does byte + byte return int?"**
   Because the JVM execution engine is optimized for 32-bit (int) and 64-bit operations. Operands are widened via numeric promotion to avoid silent overflows in intermediate calculations.
2. **"Difference between widening and narrowing?"**
   Widening goes to a larger/wider type, requires no explicit cast, and typically preserves magnitude. Narrowing goes to a smaller type, risks losing information (truncation), and demands an explicit `(type)` cast.
3. **"Can you cast a boolean to int?"**
   No. In Java, `boolean` is fundamentally distinct from numeric types, unlike C/C++ where false is 0 and true is 1.
