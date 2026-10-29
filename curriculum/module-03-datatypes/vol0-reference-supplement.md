# MODULE 3 — SUPPLEMENTARY REFERENCE

---

## 1. BIGDECIMAL API — COMPLETE GUIDE

### Why BigDecimal?
> [!IMPORTANT]
> `float` and `double` use IEEE 754 binary floating-point, which CANNOT represent decimal fractions exactly.

- `0.1 + 0.2 = 0.30000000000000004` in double
- `BigDecimal` stores numbers as: `unscaledValue × 10^(-scale)`
- Enables EXACT decimal arithmetic

### Constructor Pitfalls

> [!WARNING]
> Never use the `double` constructor for `BigDecimal` unless you explicitly want the exact representation of the `double`!

```java
// BAD — still uses imprecise double!
new BigDecimal(0.1); // → 0.1000000000000000055511151231257827021181583404541015625

// GOOD — parses exact string
new BigDecimal("0.1"); // → 0.1

// GOOD — converts double correctly
BigDecimal.valueOf(0.1); // → 0.1 (uses Double.toString() internally)
```

### Arithmetic Methods
```java
BigDecimal a = new BigDecimal("10.50");
BigDecimal b = new BigDecimal("3.00");

a.add(b)          // 13.50
a.subtract(b)      // 7.50
a.multiply(b)      // 31.500
a.divide(b, 2, RoundingMode.HALF_UP)  // 3.50
a.remainder(b)     // 1.50
a.pow(2)           // 110.2500
a.negate()         // -10.50
a.abs()            // 10.50
```

### Division — The Tricky Part

> [!CAUTION]
> Always specify a scale and rounding mode when dividing `BigDecimal` objects!

```java
// THIS THROWS ArithmeticException!
new BigDecimal("10").divide(new BigDecimal("3"))
// → ArithmeticException: Non-terminating decimal expansion

// You MUST specify scale and rounding mode:
new BigDecimal("10").divide(new BigDecimal("3"), 10, RoundingMode.HALF_UP)
// → 3.3333333333
```

### RoundingMode (ALL values explained)
| RoundingMode | Behavior | Example: 2.5 → | Example: -2.5 → |
|---|---|---|---|
| `UP` | Away from zero | 3 | -3 |
| `DOWN` | Toward zero (truncate) | 2 | -2 |
| `CEILING` | Toward positive infinity | 3 | -2 |
| `FLOOR` | Toward negative infinity | 2 | -3 |
| `HALF_UP` | Nearest, 0.5 rounds up | 3 | -3 |
| `HALF_DOWN` | Nearest, 0.5 rounds down | 2 | -2 |
| `HALF_EVEN` | Nearest, 0.5 rounds to even (banker's) | 2 | -2 |
| `UNNECESSARY` | Assert exact result — throws if rounding needed | Exception | Exception |

### Scale and Precision
> [!NOTE]
> - `scale()`: number of digits after the decimal point
> - `precision()`: total number of significant digits
> - `setScale(newScale, roundingMode)`: change scale

### Comparison

> [!WARNING]
> NEVER use `.equals()` for `BigDecimal` value comparison! It compares both value AND scale.

```java
// NEVER use .equals() for BigDecimal value comparison!
new BigDecimal("2.0").equals(new BigDecimal("2.00")) // → false! (different scale)

// Use .compareTo() for value comparison:
new BigDecimal("2.0").compareTo(new BigDecimal("2.00")) // → 0 (equal value)
```

### Production Money Pattern
```java
public class Money {
    private final BigDecimal amount;
    private final Currency currency;
    
    public Money(String amount, Currency currency) {
        this.amount = new BigDecimal(amount).setScale(2, RoundingMode.HALF_EVEN);
        this.currency = currency;
    }
    
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) throw new IllegalArgumentException("Currency mismatch");
        return new Money(this.amount.add(other.amount).toPlainString(), this.currency);
    }
}
```

---

## 2. FLOAT/DOUBLE BIT CONVERSION APIs

### Float.floatToIntBits(float) / Float.intBitsToFloat(int)
```java
float f = 3.14f;
int bits = Float.floatToIntBits(f);
String binary = String.format("%32s", Integer.toBinaryString(bits)).replace(' ', '0');
System.out.println("Binary: " + binary);
// Binary: 01000000010010001111010111000011

// Decompose:
int sign = (bits >> 31) & 0x1;           // 0 = positive
int exponent = (bits >> 23) & 0xFF;       // 128 (biased)
int mantissa = bits & 0x7FFFFF;           // fraction bits

System.out.println("Sign: " + sign);        // 0
System.out.println("Exponent: " + exponent + " (unbiased: " + (exponent - 127) + ")"); // 128, unbiased: 1
System.out.println("Mantissa: " + Integer.toBinaryString(mantissa)); // fraction bits
```

### Double.doubleToLongBits(double) / Double.longBitsToDouble(long)
```java
double d = 3.14;
long bits = Double.doubleToLongBits(d);
String binary = String.format("%64s", Long.toBinaryString(bits)).replace(' ', '0');

int sign = (int)((bits >> 63) & 0x1);
int exponent = (int)((bits >> 52) & 0x7FF);
long mantissa = bits & 0xFFFFFFFFFFFFFL;
```

### Special Values
| Value | Float bits | Double bits |
|---|---|---|
| +0.0 | `0x00000000` | `0x0000000000000000` |
| -0.0 | `0x80000000` | `0x8000000000000000` |
| +Infinity | `0x7F800000` | `0x7FF0000000000000` |
| -Infinity | `0xFF800000` | `0xFFF0000000000000` |
| NaN | `0x7FC00000` | `0x7FF8000000000000` |

> [!TIP]
> `NaN` can actually have many different bit representations. `Float.isNaN(f)` is safer than checking bits.

---

## 3. IEEE 754 — WORKED NUMERICAL EXAMPLES

### Converting 3.14f to IEEE 754 Binary (step by step)

**Step 1**: Convert to binary
- Integer part: 3 = 11 in binary
- Fractional part: 0.14 → multiply by 2 repeatedly:
  - 0.14 × 2 = 0.28 → 0
  - 0.28 × 2 = 0.56 → 0  
  - 0.56 × 2 = 1.12 → 1
  - 0.12 × 2 = 0.24 → 0
  - 0.24 × 2 = 0.48 → 0
  - ... (continues, non-terminating)
- 3.14 ≈ 11.001000111101... in binary

**Step 2**: Normalize to 1.xxx × 2^n
- 11.001000111101... = 1.1001000111101... × 2^1

**Step 3**: Determine components
- Sign: 0 (positive)
- Exponent: 1 + 127 (bias) = 128 = 10000000 in binary
- Mantissa: 1001000111101... (23 bits, implicit leading 1 dropped)

**Step 4**: Assemble
```text
| 0 | 10000000 | 10010001111010111000011 |
|sign| exponent |       mantissa          |
```

### Why 0.1 + 0.2 ≠ 0.3
- 0.1 in binary: 0.000110011001100110011... (infinite repeating)
- 0.2 in binary: 0.001100110011001100110... (infinite repeating)
- Both are TRUNCATED to 52 mantissa bits (for double)
- Sum of truncated values: 0.30000000000000004
- 0.3 in binary is ALSO truncated differently
- So the truncated sum ≠ the truncated 0.3

### Denormalized (Subnormal) Numbers
> [!NOTE]
> Denormalized numbers occur when the exponent bits are ALL ZEROS.
> - No implicit leading 1 (it's 0 instead)
> - Allows representing very small numbers near zero
> - Provides "gradual underflow" to zero instead of a hard jump

---

## 4. MATH EXACT ARITHMETIC METHODS

### Complete Suite
```java
Math.addExact(int a, int b)       // throws ArithmeticException on overflow
Math.addExact(long a, long b)
Math.subtractExact(int a, int b)
Math.subtractExact(long a, long b)
Math.multiplyExact(int a, int b)
Math.multiplyExact(long a, long b)
Math.incrementExact(int a)         // a + 1 with overflow check
Math.incrementExact(long a)
Math.decrementExact(int a)         // a - 1 with overflow check
Math.decrementExact(long a)
Math.negateExact(int a)            // -a with overflow check (MIN_VALUE!)
Math.negateExact(long a)
Math.toIntExact(long a)            // narrowing with overflow check
Math.multiplyFull(int a, int b)    // returns long (no overflow possible)
Math.multiplyHigh(long a, long b)  // upper 64 bits of 128-bit product
```

### Why negateExact exists
> [!WARNING]
> `Math.abs()` and negation can silently overflow for `MIN_VALUE`!

```java
Math.negateExact(Integer.MIN_VALUE) // ArithmeticException!
// Because -(-2,147,483,648) = 2,147,483,648 > Integer.MAX_VALUE
// Math.abs(Integer.MIN_VALUE) returns MIN_VALUE (negative!) — silent bug
```

---

## 5. RADIX CONVERSION APIs

```java
// Int to String in various bases
Integer.toBinaryString(42)    // "101010"
Integer.toOctalString(42)     // "52"
Integer.toHexString(42)       // "2a"
Integer.toString(42, 16)      // "2a" (arbitrary radix 2-36)
Integer.toString(42, 2)       // "101010"

// Long equivalents
Long.toBinaryString(42L)      // "101010"
Long.toHexString(42L)         // "2a"

// String to int with radix
Integer.parseInt("101010", 2)  // 42 (binary to decimal)
Integer.parseInt("2a", 16)     // 42 (hex to decimal)
Integer.parseInt("52", 8)      // 42 (octal to decimal)

// Decode: auto-detects prefix
Integer.decode("0x2a")         // 42 (hex)
Integer.decode("052")          // 42 (octal)
Integer.decode("42")           // 42 (decimal)
```

---

## 6. WRAPPER CLASS — COMPLETE METHOD REFERENCE

### Parsing (String → primitive)
```java
Byte.parseByte("127")           // 127
Short.parseShort("32767")       // 32767
Integer.parseInt("42")          // 42
Long.parseLong("42")            // 42L
Float.parseFloat("3.14")        // 3.14f
Double.parseDouble("3.14")      // 3.14
Boolean.parseBoolean("true")    // true (case-insensitive)
Boolean.parseBoolean("yes")     // false (only "true" returns true)
```

> [!IMPORTANT]
> All numeric parse methods throw `NumberFormatException` for invalid input. `Boolean.parseBoolean` NEVER throws — it simply returns false for anything that's not exactly `"true"` (case-insensitive).

### valueOf (String → wrapper object)
```java
Integer.valueOf("42")           // Integer object (may use cache)
Integer.valueOf(42)             // Integer object (may use cache)
```

> [!TIP]
> `valueOf` uses the wrapper cache for small values. Prefer it over the constructor (which is deprecated). `parseInt` returns a primitive (no object allocation), while `valueOf` returns a wrapper object.

### xxxValue (wrapper → primitive)
```java
Integer i = 42;
i.intValue()     // 42
i.longValue()    // 42L
i.floatValue()   // 42.0f
i.doubleValue()  // 42.0
i.byteValue()    // 42
i.shortValue()   // 42
```

### Comparison
```java
Integer.compare(a, b)         // returns negative, 0, or positive
Double.compare(1.0, 2.0)      // negative
Float.compare(Float.NaN, 1.0f) // NaN is considered greater

// Instance method
new Integer(5).compareTo(new Integer(3)) // positive
```

### Utility Checks
```java
Double.isNaN(d)          // true if NaN
Double.isInfinite(d)     // true if ±Infinity
Double.isFinite(d)       // true if not NaN and not Infinity
Float.isNaN(f)
Float.isInfinite(f)
```

---

## 7. TYPE PROMOTION — COMPOUND ASSIGNMENTS & OPERATORS

### Compound Assignment Implicit Cast

> [!IMPORTANT]
> Compound assignment operators (`+=`, `-=`, `*=`, `/=`, `%=`) and increment/decrement (`++`, `--`) include an IMPLICIT narrowing cast. This is defined in JLS §15.26.2.

```java
short s = 10;
s = s + 5;       // COMPILE ERROR! (s + 5) is int, can't assign to short
s += 5;          // COMPILES! Equivalent to: s = (short)(s + 5)
s++;             // COMPILES! Equivalent to: s = (short)(s + 1)
```

### Floating-Point Modulus
```java
5.5 % 2.0    // 1.5 (works with doubles!)
10.0 % 0.0   // NaN (not ArithmeticException — only int div by zero throws)
-7.5 % 2.0   // -1.5 (sign follows dividend)
```

### Bitwise & Shift Operators
```java
int a = 0b1010;  // 10
int b = 0b1100;  // 12

a & b   // 0b1000 = 8  (AND)
a | b   // 0b1110 = 14 (OR)
a ^ b   // 0b0110 = 6  (XOR)
~a      // inverts all bits

a << 2  // 0b101000 = 40  (left shift, multiply by 4)
a >> 1  // 0b0101 = 5     (signed right shift, divide by 2)
a >>> 1 // 0b0101 = 5     (unsigned right shift, fills with 0)
```

### Byte Masking
> [!TIP]
> When converting a `byte` to an `int`, sign extension happens automatically. Use a mask to prevent this.

```java
byte b = (byte) 0xFF;  // -1 in signed byte
int i = b;             // -1 (sign extension!)
int j = b & 0xFF;      // 255 (mask prevents sign extension)
```

---

## 8. JMH BENCHMARKING BASICS

### Why Not Just System.nanoTime()?
- JIT may eliminate dead code (unused results)
- JIT warmup not accounted for
- OS thread scheduling noise
- Constant folding may compute at compile time

### Maven Setup
```xml
<dependency>
    <groupId>org.openjdk.jmh</groupId>
    <artifactId>jmh-core</artifactId>
    <version>1.37</version>
</dependency>
<dependency>
    <groupId>org.openjdk.jmh</groupId>
    <artifactId>jmh-generator-annprocess</artifactId>
    <version>1.37</version>
</dependency>
```

### Basic Benchmark
```java
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(2)
public class PrimitiveVsWrapperBenchmark {

    @Benchmark
    public long sumPrimitive() {
        long sum = 0;
        for (int i = 0; i < 1_000_000; i++) {
            sum += i;
        }
        return sum; // Return to prevent dead code elimination
    }

    @Benchmark
    public Long sumWrapper() {
        Long sum = 0L;
        for (int i = 0; i < 1_000_000; i++) {
            sum += i; // Autoboxing on every iteration!
        }
        return sum;
    }
}
```

### Key Annotations
- `@Benchmark`: marks the benchmark method
- `@State(Scope.Thread)`: each thread gets its own state instance
- `@BenchmarkMode(Mode.AverageTime)`: measure average time per operation
- `@OutputTimeUnit`: unit for results
- `@Warmup`: JIT warmup iterations
- `@Measurement`: actual measurement iterations
- `@Fork`: number of separate JVM forks
- `Blackhole.consume(result)`: prevent dead code elimination for void methods

---

## 9. ADDITIONAL MISSING TOPICS

### Autoboxing + Widening Restriction
> [!WARNING]
> Java does NOT combine widening + autoboxing in a single step. `int` cannot implicitly widen to `long` AND then autobox to `Long`.

```java
Long L = 10;     // COMPILE ERROR! int 10 cannot autobox to Long
Long L = 10L;    // OK — long literal autoboxes to Long
Long L = (long) 10; // OK — explicit widening then autoboxing
```

### Octal Literal Trap
> [!CAUTION]
> A leading zero denotes an octal literal!

```java
int x = 010;  // x = 8, NOT 10! (octal literal due to leading 0)
int y = 08;   // COMPILE ERROR! 8 is not a valid octal digit
```

### Hex Floating-Point Literals
```java
float f = 0x1.0p3f;   // 1.0 × 2^3 = 8.0f
double d = 0x1.8p1;   // 1.5 × 2^1 = 3.0
// p or P indicates the binary exponent
```

### Underscore Placement Rules in Numeric Literals
```java
int valid = 1_000_000;     // OK
int valid2 = 0xFF_EC_DE;   // OK
int invalid1 = _100;       // ERROR: starts with underscore
int invalid2 = 100_;       // ERROR: ends with underscore
float invalid3 = 3_.14f;   // ERROR: adjacent to decimal point
float invalid4 = 3._14f;   // ERROR: adjacent to decimal point
long invalid5 = 99_L;      // ERROR: before suffix
int invalid6 = 0_x1A;      // ERROR: after prefix
```

### Array Subtyping & Covariance
```java
Object[] arr = new String[5];  // OK — arrays are covariant
arr[0] = "hello";              // OK
arr[1] = 42;                   // ArrayStoreException! (runtime)

// int[] does NOT extend Object[]
Object[] arr2 = new int[5];    // COMPILE ERROR!
// But int[] extends Object
Object obj = new int[5];       // OK

// Array class names
new int[0].getClass().getName()         // "[I"
new String[0].getClass().getName()      // "[Ljava.lang.String;"
new int[0][0].getClass().getName()      // "[[I"
```

### Switch Type Restrictions
```java
// Allowed: byte, short, char, int, String, enum, Byte, Short, Character, Integer
switch(intVar)    // OK
switch(stringVar) // OK (Java 7+)
switch(enumVar)   // OK

// NOT allowed:
switch(longVar)   // COMPILE ERROR
switch(floatVar)  // COMPILE ERROR  
switch(doubleVar) // COMPILE ERROR
switch(boolVar)   // COMPILE ERROR
```

### Ternary Operator Type Promotion NPE
> [!CAUTION]
> When one branch of a ternary operator is a primitive and the other is a wrapper, the wrapper is unboxed. This can cause a NullPointerException if the wrapper is null.

```java
Integer a = null;
int result = (true) ? 0 : a;  // OK — 0 is used
int result2 = (false) ? 0 : a; // NPE! a is unboxed to int, but a is null
// The type of the ternary is int (binary numeric promotion)
// So BOTH branches are expected to provide int
// null Integer unboxing → NPE
```

### Math.abs(Integer.MIN_VALUE) Trap
```java
Math.abs(Integer.MIN_VALUE) // Returns Integer.MIN_VALUE (negative!)
// Because |MIN_VALUE| = 2,147,483,648 > MAX_VALUE = 2,147,483,647
// Two's complement asymmetry: one more negative than positive
// Use Math.absExact() in Java 15+ to throw ArithmeticException instead
```

### Integer Division Truncation
```java
double d = 1 / 2;     // d = 0.0 (NOT 0.5!)
// 1 / 2 is integer division → 0, then widened to 0.0
double d2 = 1.0 / 2;  // d2 = 0.5 (at least one operand is double)
double d3 = (double) 1 / 2; // d3 = 0.5 (explicit cast before division)
```

### Number Hierarchy & Inter-Wrapper Casting
> [!WARNING]
> Wrappers are SIBLING classes under `Number`, not parent-child. You cannot cast between them directly.

```java
// All numeric wrappers extend java.lang.Number
Number n = 42;           // OK — Integer autoboxed, assigned to Number
int i = n.intValue();    // OK — Number has intValue()

// BUT: wrappers are SIBLING classes, not parent-child
Integer j = (Integer) (Number) 10.5;  // ClassCastException!
// 10.5 → Double (autoboxed) → Number (upcast) → Integer (downcast FAILS)
// Double is NOT an Integer — they are siblings under Number
```
