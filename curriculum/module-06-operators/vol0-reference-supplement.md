# Module 6: Operators — Volume 0: Reference Supplement

This volume provides a comprehensive reference for topics referenced in practice exercises and assessments but not exhaustively covered in the primary theory, examples, or best practices volumes.

## 1. Math.floorDiv() and Math.floorMod()

While standard division (`/`) and modulo (`%`) in Java truncate towards zero, mathematical contexts often require truncation towards negative infinity (floor rounding). 

> [!NOTE]
> `floorDiv` and `floorMod` are essential when dealing with negative coordinates, cyclic arrays, or grid wrapping.

### API Reference
- `Math.floorDiv(int x, int y)`
- `Math.floorDiv(long x, int y)` 
- `Math.floorDiv(long x, long y)`
- `Math.floorMod(int x, int y)`
- `Math.floorMod(long x, int y)`
- `Math.floorMod(long x, long y)`

### Examples: Negative Numbers Difference
```java
// Standard Division (truncates to zero)
System.out.println(-7 / 2);     // -3
System.out.println(-7 % 2);     // -1

// Floor Division (truncates to negative infinity)
System.out.println(Math.floorDiv(-7, 2)); // -4
System.out.println(Math.floorMod(-7, 2)); // 1
```

## 2. Math.xxxExact() Methods

Standard integer arithmetic in Java silently overflows based on two's complement behavior. The `Math.*Exact` methods detect overflow and throw an `ArithmeticException`.

> [!IMPORTANT]
> Use these methods in financial or critical path applications where silent overflow could corrupt data.

### API Reference
- `Math.addExact(int x, int y)` (also `long`)
- `Math.subtractExact(int x, int y)` (also `long`)
- `Math.multiplyExact(int x, int y)` (also `long`)
- `Math.incrementExact(int a)` (also `long`)
- `Math.decrementExact(int a)` (also `long`)
- `Math.negateExact(int a)` (also `long`)
- `Math.toIntExact(long value)`

### Overflow Examples
```java
int max = Integer.MAX_VALUE; // 2147483647

// Silent Overflow
System.out.println(max + 1); // -2147483648

// Exact Method
try {
    Math.addExact(max, 1);
} catch (ArithmeticException e) {
    System.out.println("Overflow caught!");
}
```

## 3. Unsigned Arithmetic (Integer.toUnsignedXxx)

Java primitives (`int`, `long`, `byte`, `short`) are inherently signed. Java 8 introduced methods in the `Integer` and `Long` wrapper classes to treat these types as unsigned.

> [!WARNING]
> Unsigned methods only interpret the bits differently; they do not change the underlying type (which remains a signed `int` or `long`).

### API Reference
- `Integer.parseUnsignedInt(String s)`
- `Integer.toUnsignedLong(int x)` (Converts an unsigned int into a `long`)
- `Integer.toUnsignedString(int i)` (also with radix)
- `Integer.divideUnsigned(int dividend, int divisor)`
- `Integer.remainderUnsigned(int dividend, int divisor)`
- `Integer.compareUnsigned(int x, int y)`

### Examples
```java
int a = -1; // 1111...1111 (All 32 bits set)

// Standard interpretation
System.out.println(a); // -1

// Unsigned interpretation
System.out.println(Integer.toUnsignedString(a)); // 4294967295

// Unsigned division
int dividend = Integer.parseUnsignedInt("4294967295");
int divisor = 2;
System.out.println(Integer.divideUnsigned(dividend, divisor)); // 2147483647
```

## 4. Null-Safe Utilities: Objects.equals(), Objects.hash(), Objects.compare()

These utility methods in `java.util.Objects` prevent `NullPointerException`s when invoking methods on potentially null references.

### API Reference
- `Objects.equals(Object a, Object b)`: Returns `true` if the arguments are equal to each other, `false` otherwise. Safely handles `null == null` (returns `true`) and `a == null` cases.
- `Objects.hash(Object... values)`: Generates a hash code for a sequence of input values. Ideal for implementing `hashCode()`.
- `Objects.compare(T a, T b, Comparator<? super T> c)`: Returns 0 if the arguments are identical, otherwise uses the provided comparator (safe from NPE if the comparator handles nulls).

```java
String s1 = null;
String s2 = "test";

// Unsafe
// boolean same = s1.equals(s2); // NPE!

// Safe
boolean same = Objects.equals(s1, s2); // false
```

## 5. Comparable and Comparator

The comparison framework in Java provides the standard way to order objects.

### Comparable (`java.lang.Comparable`)
Implemented by the class itself (e.g., `String`, `Integer`). Defines the **natural ordering**.
- `int compareTo(T o)`: Returns negative (less than), zero (equal), or positive (greater than).

### Comparator (`java.util.Comparator`)
External strategy for comparing objects.
- `int compare(T o1, T o2)`
- `Comparator.naturalOrder()`: Returns a comparator that compares `Comparable` objects in natural order.
- `Comparator.reverseOrder()`
- `Comparator.nullsFirst(Comparator c)`
- `Comparator.comparing(Function keyExtractor)`

```java
List<String> names = Arrays.asList("Zack", null, "Alice");
names.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
// Output: [null, "Alice", "Zack"]
```

## 6. EnumSet: Type-Safe Bitwise Flags

Using primitive `int` for bitwise flags is efficient but lacks type safety. `EnumSet` is a highly optimized set implementation specifically for enum types, internally backed by a bit vector (a `long` for enums with up to 64 instances).

> [!TIP]
> `EnumSet` provides the performance of bitwise operations with the safety and readability of proper objects.

### API Reference
- `EnumSet.of(E e1, E e2)`
- `EnumSet.allOf(Class<E> elementType)`
- `EnumSet.noneOf(Class<E> elementType)`

```java
enum Permission { READ, WRITE, EXECUTE }

EnumSet<Permission> perms = EnumSet.of(Permission.READ, Permission.WRITE);

// Adding a flag (Bitwise OR equivalent)
perms.add(Permission.EXECUTE);

// Checking a flag (Bitwise AND equivalent)
boolean canWrite = perms.contains(Permission.WRITE);
```

## 7. De Morgan's Laws in Java

De Morgan's Laws are critical for refactoring and simplifying complex logical expressions.
1. `!(a && b) == (!a || !b)`
2. `!(a || b) == (!a && !b)`

```java
// Complex and hard to read
if (!(user != null && user.isActive())) { ... }

// Simplified using De Morgan's Law
if (user == null || !user.isActive()) { ... }
```

## 8. Operator Evaluation Order (JLS §15.7)

Java evaluates operands from **left to right** before applying the operator. Precedence dictates which operator is applied first, but left-to-right evaluation dictates the order in which method calls and side effects occur.

> [!CAUTION]
> `f() + g() * h()`: According to left-to-right operand evaluation, `f()` executes FIRST, then `g()`, then `h()`. Once all return values are obtained, precedence kicks in, and the multiplication of `g() * h()` happens before the addition.

```java
int a = 1;
// evaluate left side of + (which is a=1), then right side (++a makes a=2)
int result = a + ++a; // result is 1 + 2 = 3
```

## 9. ProcessBuilder (Programmatic javap)

To analyze operator bytecode programmatically, use `ProcessBuilder`.

```java
ProcessBuilder pb = new ProcessBuilder("javap", "-c", "MyClass.class");
Process process = pb.start();

try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(process.getInputStream()))) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
}
```

## 10. Bit Manipulation Tricks

The `Integer` wrapper class provides numerous intrinsic methods for bit manipulation.

### Common Methods
- `Integer.bitCount(int i)`: Returns the number of 1-bits (population count).
- `Integer.highestOneBit(int i)`: Returns an `int` with only the highest-order 1-bit set.
- `Integer.lowestOneBit(int i)`: Returns an `int` with only the lowest-order 1-bit set.
- `Integer.numberOfLeadingZeros(int i)`
- `Integer.numberOfTrailingZeros(int i)`
- `Integer.reverse(int i)`: Reverses the order of bits.
- `Integer.rotateLeft(int i, int distance)`

## 11. Binary and Hex Debugging

When dealing with bitwise operators, always debug using proper string formatting.
- `Integer.toBinaryString(int i)`
- `Integer.toHexString(int i)`
- `Integer.toOctalString(int i)`

```java
int mask = 0b1010;
System.out.println(String.format("%32s", Integer.toBinaryString(mask)).replace(' ', '0'));
```

## 12. XOR Properties and Tricks

The Bitwise XOR (`^`) operator returns 1 if bits differ, and 0 if they match.

### Key Properties
1. `a ^ a = 0` (Self-inverse)
2. `a ^ 0 = a` (Identity)
3. Associative and Commutative (`a ^ b ^ c = c ^ a ^ b`)

### The Swap Trick
Swaps two variables without a temporary variable.
```java
int x = 10, y = 15;
x ^= y;
y ^= x;
x ^= y;
```

### Basic Encryption
Because `(data ^ key) ^ key = data`, XOR is the foundation of many symmetric encryption algorithms.
```java
char data = 'A';
char key = 'K';
char encrypted = (char) (data ^ key);
char decrypted = (char) (encrypted ^ key); // Returns 'A'
```

## 13. StringBuilder vs String Concatenation (+)

While modern Java (9+) optimizes `a + b` using `invokedynamic` (StringConcatFactory), doing this in a loop is highly inefficient because it dynamically allocates memory on each iteration.

> [!WARNING]
> Inside loops, ALWAYS use `StringBuilder.append()` directly to avoid `O(n^2)` memory allocation.

```java
// BAD: Creates a new factory and String object on each iteration
String result = "";
for (int i = 0; i < 1000; i++) {
    result += i; 
}

// GOOD: Single buffer allocation
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i);
}
String result = sb.toString();
```

## 14. fcmpg vs fcmpl (Float/Double Comparison Bytecode)

When comparing floats or doubles, Java handles `NaN` specially. The JVM provides two bytecode instructions (`fcmpg` and `fcmpl`) to evaluate conditions.

- `fcmpg` (Compare Greater): Pushes `1` onto the stack if either operand is `NaN`.
- `fcmpl` (Compare Less): Pushes `-1` onto the stack if either operand is `NaN`.

This ensures that `x < y` and `x > y` both correctly evaluate to `false` when one of the operands is `NaN`, conforming to IEEE 754 specifications.

```java
double a = Double.NaN;
double b = 5.0;

// Uses fcmpg or fcmpl internally, jump evaluates to false
if (a > b) { ... }
if (a < b) { ... }
```

## 15. OptionalDouble (Safe Division)

To implement safe division without throwing exceptions or relying on IEEE 754 `Infinity` or `NaN`, returning an `OptionalDouble` is the standard best practice.

```java
public static OptionalDouble safeDivide(int numerator, int denominator) {
    if (denominator == 0) {
        return OptionalDouble.empty();
    }
    return OptionalDouble.of((double) numerator / denominator);
}
```
