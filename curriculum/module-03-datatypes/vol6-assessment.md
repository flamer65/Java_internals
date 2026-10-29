# Module 3 Volume 6: Assessment (Data Types)

This is the comprehensive assessment for Java 21 Data Types. Complete all levels.

## LEVEL 1: MCQs (20 questions)

1. What is the size of an `int` in Java? A) 16-bit B) 32-bit C) 64-bit D) Depends on OS
2. Which primitive type has a wrapper class named `Character`? A) string B) char C) byte D) short
3. What happens when an `int` exceeds `Integer.MAX_VALUE`? A) Exception B) Overflows to negative C) Expands to long D) Returns NaN
4. Which of these is NOT a primitive type? A) boolean B) byte C) String D) double
5. What is the default value of a boolean instance variable? A) true B) false C) null D) 0
6. How is a `double` represented in memory? A) IEEE 754 64-bit B) IEEE 754 32-bit C) Two's complement D) ASCII
7. What is the Integer cache range by default? A) 0 to 127 B) -128 to 127 C) -256 to 255 D) No cache
8. Which operation causes a compile error? A) byte + byte = byte B) int + int = int C) double + double = double D) char + char = int
9. What is the output of `5.0 / 0.0`? A) ArithmeticException B) 0 C) Infinity D) NaN
10. Which assignment is valid? A) float f = 1.0; B) float f = 1.0d; C) float f = 1; D) float f = 1.0D;
11. How many bytes does a `char` take? A) 1 B) 2 C) 4 D) 8
12. What does `Double.NaN == Double.NaN` evaluate to? A) true B) false C) Compilation error D) RuntimeException
13. Which literal is valid? A) 0xG1 B) 0b102 C) 1_000_000 D) 1._000
14. Which wrapper class does not have a caching mechanism? A) Integer B) Long C) Byte D) Double
15. What exception is thrown by `Integer.parseInt("12a")`? A) ParseException B) NumberFormatException C) ArithmeticException D) CastException
16. Unboxing a `null` wrapper object results in: A) 0 B) null C) NullPointerException D) false
17. The JVM internally treats `boolean` arrays as arrays of: A) bit B) byte C) int D) boolean
18. Which conversion requires an explicit cast? A) int to long B) float to double C) double to float D) char to int
19. `0.1 + 0.2 == 0.3` evaluates to: A) true B) false C) Compile error D) NaN
20. `System.out.println(10 + 20 + "30");` prints: A) 102030 B) 3030 C) 60 D) Compile error

---
### Answers
1-B, 2-B, 3-B, 4-C, 5-B, 6-A, 7-B, 8-A, 9-C, 10-C, 11-B, 12-B, 13-C, 14-D, 15-B, 16-C, 17-B, 18-C, 19-B, 20-B

## LEVEL 2: TRUE/FALSE (20 questions)

1. `byte` is signed in Java.
2. `char` is unsigned in Java.
3. `float` provides more precision than `long`.
4. Widening primitive conversions never lose precision.
5. `Integer x = new Integer(10);` is deprecated in Java 21.
6. `Integer.valueOf(100) == Integer.valueOf(100)` evaluates to `true`.
7. `Integer.valueOf(200) == Integer.valueOf(200)` evaluates to `true`.
8. `Double.valueOf(1.0) == Double.valueOf(1.0)` evaluates to `true`.
9. Arrays of primitives are initialized with default values.
10. Local primitive variables are initialized with default values.
11. Autoboxing has no performance overhead.
12. `Math.addExact()` throws an exception on overflow.
13. `BigDecimal` should be used for currency calculations.
14. `String` is a wrapper class for `char[]`.
15. A `boolean` cannot be cast to an `int`.
16. The modulus operator `%` works on floating-point types in Java.
17. Hexadecimal literals can be assigned to `float`.
18. `int[]` is a subclass of `Object`.
19. `Integer[]` is a subclass of `Object[]`.
20. `int[]` is a subclass of `Object[]`.

---
### Answers
1-T, 2-T, 3-F, 4-F, 5-T, 6-T, 7-F, 8-F, 9-T, 10-F, 11-F, 12-T, 13-T, 14-F, 15-T, 16-T, 17-T, 18-T, 19-T, 20-F

## LEVEL 3: OUTPUT PREDICTION (20 questions)

1. `System.out.println((byte) 128);`
2. `System.out.println(1 / 2);`
3. `System.out.println(1.0 / 2);`
4. `System.out.println(0.1 + 0.2);`
5. `System.out.println((int) 9.99);`
6. `System.out.println('A' + 1);`
7. `System.out.println("" + 'A' + 1);`
8. `System.out.println(Long.MAX_VALUE + 1);`
9. `Integer a = 127, b = 127; System.out.println(a == b);`
10. `Integer a = 128, b = 128; System.out.println(a == b);`
11. `System.out.println(Double.NaN == Double.NaN);`
12. `System.out.println(0.0 / 0.0);`
13. `System.out.println(5.0 / 0.0);`
14. `System.out.println(-5.0 / 0.0);`
15. `short x = 10; x += 5; System.out.println(x);`
16. `short x = 10; x = x + 5; System.out.println(x);`
17. `System.out.println(Float.floatToIntBits(0.0f) == Float.floatToIntBits(-0.0f));`
18. `System.out.println(0.0 == -0.0);`
19. `System.out.println((char) 65);`
20. `System.out.println(Math.abs(Integer.MIN_VALUE));`

---
### Answers
1: -128, 2: 0, 3: 0.5, 4: 0.30000000000000004, 5: 9, 6: 66, 7: A1, 8: -9223372036854775808, 9: true, 10: false, 11: false, 12: NaN, 13: Infinity, 14: -Infinity, 15: 15, 16: Compiler error, 17: false, 18: true, 19: A, 20: -2147483648

## LEVEL 4: FIND THE COMPILER ERROR (20 questions)
*(For brevity, identify the variable with the compiler error)*

1. `int a = 10.5;`
2. `byte b = 130;`
3. `float f = 3.14;`
4. `long l = 10000000000;`
5. `char c = 'AB';`
6. `boolean bool = 1;`
7. `String s = 'Hello';`
8. `short s1 = 10; short s2 = 20; short s3 = s1 + s2;`
9. `int x; System.out.println(x);`
10. `Object obj = new Object(); int x = (int) obj;`
11. `int[] arr = new int[5.5];`
12. `switch(1.5) { case 1.5: break; }`
13. `int x = 1_00_00_.00;`
14. `var v = null;`
15. `Long L = 10;`
16. `Byte b = 128;`
17. `Double d = 10.0f;`
18. `Integer x = "10";`
19. `int x = null;`
20. `void foo(int x) {} foo();`

---
### Answers
1: Cannot convert double to int, 2: 130 exceeds byte, 3: 3.14 is double, 4: integer literal too large (needs L), 5: unclosed char literal, 6: boolean is not int, 7: string needs double quotes, 8: s1+s2 is int, 9: x not initialized, 10: cannot cast Object to int, 11: array size must be int, 12: switch cannot use double, 13: underscore next to decimal, 14: var cannot be null, 15: cannot convert int to Long (needs 10L), 16: 128 exceeds byte, 17: cannot autobox float to Double, 18: String to Integer requires parsing, 19: primitive cannot be null, 20: missing argument

## LEVEL 5: FIND THE RUNTIME ERROR (20 questions)
*(Identify the exception thrown)*

1. `Integer x = null; int y = x;`
2. `int x = Integer.parseInt("10.5");`
3. `int x = 5 / 0;`
4. `Object x = Integer.valueOf(10); String s = (String) x;`
5. `int[] a = new int[5]; a[5] = 10;`
6. `Math.addExact(Integer.MAX_VALUE, 1);`
7. `Long.parseLong(" 10");`
8. `Integer[] arr = new Integer[5]; int x = arr[0];`
9. `Object[] arr = new String[5]; arr[0] = 10;`
10. `Byte.parseByte("128");`
11. `Double.valueOf(null);`
12. `int[] a = null; int x = a.length;`
13. `BigDecimal b = new BigDecimal("1.333"); b.divide(new BigDecimal("1"));` // no rounding on non-terminating
14. `new ArrayList<Integer>().get(0);`
15. `Float.valueOf("NaNf");`
16. `int x = Integer.decode("0xG1");`
17. `Number n = 10.5; int x = (Integer) n;`
18. `short[] s = new short[-5];`
19. `Object x = null; x.toString();`
20. `int[] arr = new int[Integer.MAX_VALUE];` // out of memory

---
### Answers
1: NPE, 2: NumberFormatException, 3: ArithmeticException, 4: ClassCastException, 5: ArrayIndexOutOfBoundsException, 6: ArithmeticException, 7: NumberFormatException, 8: NPE, 9: ArrayStoreException, 10: NumberFormatException, 11: NPE, 12: NPE, 13: ArithmeticException (if non-terminating), 14: IndexOutOfBoundsException, 15: NumberFormatException, 16: NumberFormatException, 17: ClassCastException, 18: NegativeArraySizeException, 19: NPE, 20: OutOfMemoryError

## LEVEL 6: DEBUG THIS CODE (20 questions)
*(Provide the correct fix)*

1. Bug: `double total = 0.1 + 0.2; // 0.30000000000000004`
2. Bug: `int val = 2147483647 + 1; // negative`
3. Bug: `Integer a = 1000; Integer b = 1000; if(a == b)`
4. Bug: `byte b = 100; b = b + 10;`
5. Bug: `long x = 1000000 * 1000000;`
6. Bug: `float f = 1.23456789f; // loss of precision`
7. Bug: `double x = 1/3; // 0.0`
8. Bug: `if(Double.NaN == Double.NaN)`
9. Bug: `BigDecimal d = new BigDecimal(0.1);`
10. Bug: `Integer i = null; if(i > 0)`
11. Bug: `Double d1 = 1.0; Double d2 = 1.0; if(d1 == d2)`
12. Bug: `System.out.println("Sum: " + 10 + 20); // Sum: 1020`
13. Bug: `int x = (int) 3.99; // 3 instead of 4`
14. Bug: `char c = 'a' + 'b';`
15. Bug: `float f = 10e40f;`
16. Bug: `int x = 010; // 8 instead of 10`
17. Bug: `boolean b = 1;`
18. Bug: `short s = 32767; s++;`
19. Bug: `Double d = 0.0/0.0; if (d.equals(Double.NaN))`
20. Bug: `String s = String.valueOf(null);`

---
### Answers
1: Use BigDecimal("0.1").add(new BigDecimal("0.2")), 2: Use long, 3: Use a.equals(b), 4: Use b += 10 or cast (byte)(b+10), 5: Use 1000000L * 1000000L, 6: Use double, 7: Use 1.0/3.0, 8: Use Double.isNaN(val), 9: Use new BigDecimal("0.1"), 10: Check i != null, 11: Use d1.equals(d2), 12: Use (10 + 20), 13: Use Math.round(3.99), 14: Requires cast (char)('a'+'b'), 15: Out of float range, use double, 16: Remove leading zero, 17: Use boolean b = true, 18: Causes overflow, use int, 19: Double.equals(NaN) works but Double.isNaN() is preferred, 20: Causes NPE.

## LEVEL 7: FILL IN THE MISSING CODE (20 questions)

1. `int x = (___) 10.5;` -> int
2. `long x = 10___;` -> L
3. `float f = 10___;` -> f
4. `byte b = (___) 130;` -> byte
5. `Integer x = Integer.valueOf(___);` -> "10" or 10
6. `int y = Integer.parseInt(___);` -> "10"
7. `String s = String.valueOf(___);` -> 10
8. `Double d = Double.valueOf(___);` -> "10.5"
9. `if (Double.___ (myDouble))` -> isNaN
10. `BigDecimal b = new BigDecimal(___);` -> "0.1"
11. `int max = Integer.___;` -> MAX_VALUE
12. `int bytes = Integer.___;` -> BYTES
13. `long x = Math.___(a, b);` -> addExact
14. `System.out.println(Integer.to___String(10));` -> Binary or Hex
15. `char c = (___) 65;` -> char
16. `boolean b = Boolean.parseBoolean(___);` -> "true"
17. `Object o = ___;` -> 10 (autoboxing)
18. `int[] arr = new int[___];` -> 10
19. `Integer[] arr2 = new Integer[___];` -> 10
20. `int val = Float.floatTo___Bits(1.0f);` -> Int

---
### Answers
1: int, 2: L, 3: f, 4: byte, 5: 10, 6: "10", 7: 10, 8: "10.5", 9: isNaN, 10: "0.1", 11: MAX_VALUE, 12: BYTES, 13: addExact, 14: Binary, 15: char, 16: "true", 17: 10, 18: 10, 19: 10, 20: Int

## LEVEL 8: EXPLAIN WHY THIS CODE WORKS (20 questions)
*(Provide brief concepts)*

1. `Integer a=10, b=10; a==b;` -> Integer Cache
2. `byte x = 10; byte y = x++;` -> x++ implies internal casting
3. `x += 5;` vs `x = x + 5;` -> += has implicit cast
4. `0.0 == -0.0` -> IEEE 754 spec
5. `Float.NaN != Float.NaN` -> IEEE 754 spec
6. `(byte)128 == -128` -> Integer overflow/two's complement
7. `"A" + 1 + 2 == "A12"` -> String concatenation left-to-right
8. `1 + 2 + "A" == "3A"` -> Addition first, then concat
9. `char c = 65;` -> Implicit narrowing conversion
10. `int x = 'A';` -> Widening conversion
11. `float f = 10000000000L;` -> Widening (loss of precision allowed)
12. `Math.abs(Integer.MIN_VALUE) == Integer.MIN_VALUE` -> Two's complement asymmetry
13. `int x = 0x10;` -> Hexadecimal literal
14. `int x = 010;` -> Octal literal
15. `int x = 0b10;` -> Binary literal
16. `double d = 1e-4;` -> Scientific notation
17. `Object o = 10;` -> Autoboxing to Integer
18. `System.out.println(new int[0].getClass().getName());` -> `[I` JVM array notation
19. `int x = 1_000_000;` -> Underscores for readability
20. `System.out.println(1.0/0.0);` -> Infinity (IEEE 754)

---
### Answers
See above concepts.

## LEVEL 9: REFACTORING EXERCISES (20 questions)
*(Refactor to best practice)*

1. `new Integer(10)` -> `Integer.valueOf(10)`
2. `Double val = 0.1; Double val2 = 0.2; Double sum = val + val2;` -> `BigDecimal`
3. `int a = 2000000000; int b = 2000000000; long c = a + b;` -> `long c = (long)a + b;`
4. `Float f = Float.valueOf(10.5f); float p = f.floatValue();` -> `float p = 10.5f;`
5. `Boolean b = new Boolean("true");` -> `Boolean.valueOf("true")`
6. `int id = (int) longId;` (unsafe) -> `Math.toIntExact(longId)`
7. `double sum = 0.0; for(int i=0; i<100; i++) sum += 0.1;` -> `BigDecimal sum`
8. `Integer i = getValue(); if(i == 10)` -> `if(i != null && i == 10)`
9. `Byte b = 10; Short s = 20; int c = b + s;` -> `int c = b.intValue() + s.intValue();` (avoid implicit unboxing in complex expressions)
10. `String s = 10 + "";` -> `String.valueOf(10)`
11. `Double d = new Double(1.0);` -> `Double.valueOf(1.0)`
12. `Integer x = Integer.parseInt("10");` -> `int x = Integer.parseInt("10");` (avoid autoboxing)
13. `int x = Integer.valueOf("10");` -> `int x = Integer.parseInt("10");`
14. `long val = 1000 * 60 * 60 * 24 * 365;` -> `long val = 1000L * 60 * 60 * 24 * 365;`
15. `if (myDouble == Double.NaN)` -> `if (Double.isNaN(myDouble))`
16. `BigDecimal b = new BigDecimal(0.1);` -> `BigDecimal b = new BigDecimal("0.1");`
17. `double d = Math.round(1.234 * 100.0) / 100.0;` -> Use `BigDecimal` for exact rounding
18. `int count = 0; while(true) { count++; if(count < 0) break; }` -> Use `long` or `Math.addExact`
19. `boolean isTrue = val == 1 ? true : false;` -> `boolean isTrue = (val == 1);`
20. `if(myBoolean == true)` -> `if(myBoolean)`

---
### Answers
See above refactorings.

## LEVEL 10: CODING EXERCISES (20 questions)
*(Write a snippet doing the following)*

1. Sum two long values safely, throwing exception on overflow.
2. Convert a hexadecimal string `"FF"` to an `int`.
3. Check if a `double` is infinite.
4. Calculate `$1.03 - $0.42` exactly.
5. Create a byte array of size 1MB.
6. Get the underlying bit representation of `-1.0f`.
7. Cast `3.99` to `int` safely rounding mathematically.
8. Parse an `int` from string, returning `-1` if invalid.
9. Multiply a `short` by 2, storing result in the `short`.
10. Compare two `Double` objects for equality.
11. Generate a random `double` between 0 and 1.
12. Find the max of two `int` variables without `Math.max`.
13. Convert `int` 255 to binary string.
14. Create an `Integer` cache array manually for 0-127.
15. Determine if an `int` is even using bitwise operators.
16. Safely cast a `long` to `int` returning `Integer.MAX_VALUE` if it exceeds bounds.
17. Convert a `char` to its ASCII integer value.
18. Create a `NaN` value using `Math`.
19. Compare primitive `float` arrays for equality.
20. Instantiate a `BigDecimal` with exact value of `0.1`.

---
### Answers
1. `Math.addExact(a, b)`
2. `Integer.parseInt("FF", 16)`
3. `Double.isInfinite(val)`
4. `new BigDecimal("1.03").subtract(new BigDecimal("0.42"))`
5. `new byte[1024 * 1024]`
6. `Float.floatToIntBits(-1.0f)`
7. `(int) Math.round(3.99)`
8. `try { return Integer.parseInt(s); } catch (NumberFormatException e) { return -1; }`
9. `s *= 2;`
10. `d1.equals(d2)`
11. `Math.random()`
12. `a > b ? a : b`
13. `Integer.toBinaryString(255)`
14. `Integer[] cache = new Integer[128]; for(int i=0; i<128; i++) cache[i] = i;`
15. `(n & 1) == 0`
16. `n > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) n`
17. `(int) c`
18. `Math.sqrt(-1)` or `0.0 / 0.0`
19. `Arrays.equals(arr1, arr2)`
20. `new BigDecimal("0.1")`

## LEVEL 11: MINI INTERVIEW QUESTIONS (10)

1. **Why does Java have primitive types if it is Object-Oriented?** (Performance, memory footprint, no GC overhead).
2. **Explain the purpose of Wrapper Classes.** (To use primitives in Collections/Generics, provide utility methods).
3. **What is Autoboxing and when is it dangerous?** (Automatic conversion from primitive to wrapper. Dangerous in loops due to massive object creation/GC pressure, and NPEs on null unboxing).
4. **Why should you never use `double` for currency?** (IEEE 754 cannot represent fractions like 0.1 accurately; use `BigDecimal`).
5. **How does the Integer Cache work?** (Caches objects for values -128 to 127 to save memory. `==` works in this range, fails outside).
6. **Explain type promotion rules in expressions.** (`byte`/`short`/`char` are promoted to `int`. If operands are `long`, result is `long`, etc.).
7. **What happens during a narrowing primitive conversion?** (High-order bits are discarded, which can change sign or magnitude).
8. **What is `NaN` and how do you test for it?** (Not a Number. Test with `Double.isNaN()` because `NaN == NaN` is false).
9. **Explain the difference between `parseInt()` and `valueOf()`.** (`parseInt` returns primitive `int`, `valueOf` returns wrapper `Integer`).
10. **What are the memory implications of `boolean[]` vs `boolean` local variable?** (Locals map to `int` in bytecode, arrays map to `byte[]` in HotSpot JVM).

---
### Answers
Model answers provided inline above.

## LEVEL 12: LARGE CODING ASSIGNMENTS (5)

1. **TypeSystemExplorer**: Create a CLI tool that takes a string input and attempts to parse it into all 8 primitive wrappers, outputting which types can hold the value without overflow.
2. **PrecisionAnalyzer**: Create a program that loops 10,000 times, adding `0.1` to a `float`, `double`, and `BigDecimal`. Compare the final results and execution times.
3. **WrapperBenchmark**: Create an application that measures the time and memory taken to sort 10 million elements in `int[]` vs `Integer[]`.
4. **IEEE754Visualizer**: Write a utility that accepts a `float` and prints its 32-bit binary string, color-coded (or split) into: 1 sign bit, 8 exponent bits, 23 mantissa bits.
5. **SafeArithmeticLibrary**: Build a utility class `SafeMath` with methods `add`, `sub`, `mul`, `div` for `long` that guarantees no overflow, underflow, or divide-by-zero silently passing.

## LEVEL 13: CAPSTONE PROJECT

**Project: Java Type System Analyzer & Calculator**
Build a professional Java application that:
- Accepts numeric input and mathematical expressions from the CLI.
- Automatically determines the safest and smallest Java primitive/object type required to evaluate it.
- Demonstrates memory layout (binary/hex).
- Performs safe arithmetic.
- Evaluates performance footprints using JVM flags.
- **Rubric (100 pts):** Architecture (20), Type Safety (20), Edge Cases (20), Documentation (20), Accuracy of Binary Math (20).

---
### Capstone Solutions Note
Expected to use `Scanner`, `Math.*Exact`, `BigDecimal`, `Integer.toBinaryString`, `Runtime.getRuntime().freeMemory()`. Code should be fully object-oriented and well-documented.
