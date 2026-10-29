# Java Mastery Curriculum - Module 6: Operators
## Volume 2: Examples

This document contains comprehensive examples of Java operators, covering simple to advanced concepts, common errors, edge cases, and performance considerations.

### SIMPLE EXAMPLES (10+)

#### 1. All arithmetic operators
```java
int a = 10, b = 3;
System.out.println("a + b = " + (a + b)); // 13
System.out.println("a - b = " + (a - b)); // 7
System.out.println("a * b = " + (a * b)); // 30
System.out.println("a / b = " + (a / b)); // 3 (integer division truncates)
System.out.println("a % b = " + (a % b)); // 1

double c = 10.0, d = 3.0;
System.out.println("c / d = " + (c / d)); // 3.3333333333333335
```

#### 2. Pre vs post increment
```java
int x = 5;
int a = x++; // a gets 5, then x becomes 6
int b = ++x; // x becomes 7, then b gets 7
System.out.println("a=" + a + ", b=" + b + ", x=" + x); // a=5, b=7, x=7
```

#### 3. All comparison operators
```java
int x = 5, y = 10;
System.out.println(x == y); // false
System.out.println(x != y); // true
System.out.println(x < y);  // true
System.out.println(x > y);  // false
System.out.println(x <= 5); // true
System.out.println(y >= 10);// true

String s1 = new String("A");
String s2 = new String("A");
System.out.println(s1 == s2); // false (reference comparison)
```

#### 4. Short-circuit `&&` vs non-short-circuit `&`
```java
boolean sideEffect() {
    System.out.println("Side effect executed");
    return false;
}
boolean res1 = false && sideEffect(); // short-circuits, sideEffect() NOT called
boolean res2 = false & sideEffect();  // evaluates both, sideEffect() IS called
```

#### 5. Short-circuit `||` vs non-short-circuit `|`
```java
boolean res3 = true || sideEffect(); // short-circuits, sideEffect() NOT called
boolean res4 = true | sideEffect();  // evaluates both, sideEffect() IS called
```

#### 6. All bitwise operators
```java
int a = 0b1010; // 10
int b = 0b1100; // 12
System.out.println(Integer.toBinaryString(a & b)); // 1000 (8)
System.out.println(Integer.toBinaryString(a | b)); // 1110 (14)
System.out.println(Integer.toBinaryString(a ^ b)); // 0110 (6)
System.out.println(Integer.toBinaryString(~a));    // ...11110101 (-11)
```

#### 7. All shift operators
```java
int val = -8;
System.out.println(val << 1);  // -16 (left shift)
System.out.println(val >> 1);  // -4 (signed right shift, preserves sign)
System.out.println(val >>> 1); // 2147483644 (unsigned right shift, fills with 0)
```

#### 8. Compound assignment
```java
int x = 10;
x += 5;  // 15
x -= 2;  // 13
x *= 2;  // 26
x /= 2;  // 13
x %= 5;  // 3
x <<= 1; // 6
x >>= 1; // 3
```

#### 9. Ternary operator basics
```java
int a = 10, b = 20;
int max = (a > b) ? a : b; // 20
```

#### 10. `instanceof` with pattern matching
```java
Object obj = "Hello";
if (obj instanceof String s) {
    System.out.println(s.length()); // 5
}
```

### INTERMEDIATE EXAMPLES (8+)

#### 1. `==` on wrapper types
```java
Integer a = 127;
Integer b = 127;
System.out.println(a == b); // true (Integer cache -128 to 127)

Integer c = 128;
Integer d = 128;
System.out.println(c == d); // false (outside cache)

Integer e = new Integer(5); // Deprecated but valid example
System.out.println(e == 5); // true (auto-unboxing happens before ==)
```

#### 2. Complex precedence
```java
int result = 2 + 3 * 4 - 1; 
// 3 * 4 is evaluated first (12)
// 2 + 12 = 14
// 14 - 1 = 13
System.out.println(result == (2 + (3 * 4)) - 1); // true
```

#### 3. Bitwise flag system
```java
int READ = 1, WRITE = 2, EXEC = 4;
int permissions = READ | WRITE; // Set: 0011 (3)
boolean canRead = (permissions & READ) == READ; // Check: true
permissions &= ~WRITE; // Clear write: 0001 (1)
permissions ^= EXEC;   // Toggle exec: 0101 (5)
```

#### 4. Signed vs unsigned right shift
```java
int val = -8; // 1111...1000
System.out.println(Integer.toBinaryString(val >> 1));  // 1111...1100 (-4)
System.out.println(Integer.toBinaryString(val >>> 1)); // 0111...1100 (2147483644)
```

#### 5. Compound assignment implicit cast
```java
byte b = 10;
// b = b + 200; // ERROR: required byte, found int
b += 200; // Compiles! Implicitly does: b = (byte)(b + 200)
System.out.println(b); // -46 (Overflow)
```

#### 6. Ternary type promotion
```java
Object obj = true ? 1 : 1.0;
System.out.println(obj.getClass().getName()); // java.lang.Double (1 promoted to 1.0)
```

#### 7. String concatenation with `+`
```java
System.out.println(1 + 2 + "3"); // "33" (1+2=3, then 3+"3"="33")
System.out.println("1" + 2 + 3); // "123" ("1"+2="12", then "12"+3="123")
```

#### 8. Division edge cases
```java
System.out.println(Integer.MIN_VALUE / -1 == Integer.MIN_VALUE); // true (overflow!)
System.out.println(10.0 / 0); // Infinity
System.out.println(-10.0 / 0); // -Infinity
System.out.println(0.0 / 0); // NaN
```

### ADVANCED EXAMPLES (5+)

#### 1. Bytecode for pre vs post increment
```java
int x = 0;
int a = x++;
// iload_1 (load x to stack)
// iinc 1, 1 (increment x in local vars)
// istore_2 (store stack to a)

int b = ++x;
// iinc 1, 1 (increment x in local vars)
// iload_1 (load x to stack)
// istore_3 (store stack to b)
```

#### 2. Bytecode for short-circuit `&&`
```java
// if (a && b) translates to:
// iload_1 (load a)
// ifeq LABEL (if a is 0/false, jump to end)
// iload_2 (load b)
// ifeq LABEL
// ... body
```

#### 3. String concatenation bytecode
```java
String s = a + " " + b;
// Uses invokedynamic (StringConcatFactory.makeConcatWithConstants) in Java 9+
```

#### 4. Complex expression evaluation
```java
int a = 1, b = 2, c = 3, d = 4;
int x = a++ + ++b * c-- - --d;
// 1 + 3 * 3 - 3
// 1 + 9 - 3 = 7
// a=2, b=3, c=2, d=3
```

#### 5. Shift modulo behavior
```java
System.out.println(1 << 32); // 1 (32 % 32 = 0, so 1 << 0)
System.out.println(1 << 33); // 2 (33 % 32 = 1, so 1 << 1)
System.out.println(1L << 64); // 1L (64 % 64 = 0)
```

### PRODUCTION EXAMPLES (3+)

#### 1. Permission system using bitwise operators
```java
int PERMS = 0b111101100; // rwxr-xr--
boolean groupWrite = (PERMS & 0b000010000) != 0; // Check group write
```

#### 2. Efficient power-of-2 check
```java
boolean isPowerOfTwo = (n > 0) && ((n & (n - 1)) == 0);
// 8 (1000) & 7 (0111) = 0000 -> true
```

#### 3. Null-safe comparison utility
```java
Comparator<String> nullSafeStringComparator = Comparator.nullsFirst(String::compareTo);
```

### WRONG EXAMPLES — COMPILER ERRORS (10+)

1. `5++` — cannot increment a literal
2. `boolean b = 5 > 3 > 1;` — cannot chain comparisons
3. `int x = true && false;` — boolean cannot be assigned to int
4. `if (x = 5)` — assignment instead of comparison (returns int, if needs boolean)
5. `"hello" instanceof Integer` — inconvertible types
6. `byte b = b1 + b2;` — numeric promotion to int
7. `short s = s + 1;` — int cannot be assigned to short
8. `boolean b = !5;` — ! only works on boolean
9. `System.out.println(1 / 0);` — compiles! (Not a compiler error, see Runtime Errors)
10. `int x = null == null;` — result is boolean, not int

### WRONG EXAMPLES — RUNTIME ERRORS (5+)

1. `10 / 0` → ArithmeticException
2. `Integer.MIN_VALUE / -1` → overflow (no exception, wrong result)
3. `Integer a = null; int b = a + 1;` → NullPointerException (unboxing)
4. `String s = null; if (s != null & s.length() > 0)` → NullPointerException (non-short-circuit eval)
5. `obj instanceof String s` (obj is null) → false, no NPE.

### EDGE CASES (8+)

1. `Double.NaN == Double.NaN` → false; `Double.NaN != Double.NaN` → true
2. `0.0 == -0.0` → true; `Double.compare(0.0, -0.0)` → 1 (non-zero)
3. `Integer.MIN_VALUE == -Integer.MIN_VALUE` → true (overflow!)
4. `~0 == -1` → true (all bits flipped)
5. `1 << 31 == Integer.MIN_VALUE` → true
6. `-1 >>> 1 == Integer.MAX_VALUE` → true
7. `'A' + 1 == 66` → true (char promoted to int)
8. `"" + null == "null"` → true

### PERFORMANCE EXAMPLES (2+)

1. `x << 1` vs `x * 2` — JIT compiler optimizes both to the same assembly. Use `* 2` for readability.
2. Short-circuit: Put cheap checks first. `if (notNull(obj) && expensiveCheck(obj))`

### INTERVIEW EXAMPLES (3+)

1. "What is `1 + 2 + "3" + 4 + 5`?" → "3345"
2. "Difference between `==` and `.equals()`?" → `==` checks reference equality (memory address), `.equals()` checks value equality.
3. "Why `byte + byte` returns `int`?" → Java numeric promotion rules promote byte/short/char to int before binary operations to prevent overflow on small types on 32-bit JVMs.
