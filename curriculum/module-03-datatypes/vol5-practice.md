# Module 3 Volume 5: Practice Exercises (Data Types)

This volume contains practice exercises for Java 21 Data Types. Complete all exercises in IntelliJ IDEA.

## SMALL EXERCISES (10)

1. **Primitive Declarations**
   Write a program that declares one variable of each primitive type (`byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`), assigns it a value, and prints: type name, value, and size in bytes (use `Byte.BYTES`, `Integer.BYTES`, etc.). *Note: boolean does not have a defined size constant.*

2. **Min and Max Values**
   Print `MIN_VALUE` and `MAX_VALUE` for all numeric types (`byte`, `short`, `int`, `long`, `float`, `double`). 

3. **Autoboxing Demonstration**
   Demonstrate autoboxing by assigning a primitive `int` to an `Integer` variable and printing it. Show that you can call a method like `.toString()` on the `Integer` object but not on the primitive.

4. **Unboxing Demonstration**
   Demonstrate unboxing by assigning an `Integer` object to a primitive `int` variable. Perform basic arithmetic with the unboxed value.

5. **Char Arithmetic**
   Show that `char` is numeric: declare two `char` variables, add them together, and print the result as both an `int` and a `char` (using casting).

6. **Float Initialization**
   Show that `float f = 3.14;` fails to compile but `float f = 3.14f;` works. Explain in a comment why this happens.

7. **Boolean Casting**
   Demonstrate that `boolean` cannot be cast to/from `int`. Write the failing code and comment it out with the exact compiler error message.

8. **Integer Identity**
   Use `Integer.valueOf()` to create 5 `Integer` objects (e.g., values 10, 10, 1000, 1000, and 127). Compare them pairwise using both `==` and `.equals()`.

9. **Precision Loss**
   Compute `0.1 + 0.2` using `double` and compare it to `0.3` using `==`. Print the actual result of the addition.

10. **Integer Overflow**
    Demonstrate integer overflow: print `Integer.MAX_VALUE + 1` and explain in a comment why the result is negative.

---

## MEDIUM EXERCISES (10)

1. **Integer Cache Explorer**
   Write a program that uses a loop to find the exact boundary where `Integer.valueOf(n) == Integer.valueOf(n)` changes from `true` to `false`. Start from 0 and go upwards.

2. **Money Calculations: Double vs BigDecimal**
   Write a program calculating the total of 10 items that cost $0.10 each. Calculate it once using `double` and once using `BigDecimal`. Show where `double` fails to equal exactly $1.00.

3. **Widening Conversions**
   Write a program that demonstrates ALL type widening conversions: `byte` -> `short` -> `int` -> `long` -> `float` -> `double`, and `char` -> `int`. Show each conversion and print the values to verify no data loss (except potentially in long->float which you should demonstrate).

4. **Byte Promotion Rule**
   Write a program that demonstrates the `byte + byte = int` promotion rule. Show the compiler error when trying to assign the result back to a `byte`, and how to fix it with an explicit cast.

5. **Memory Footprint**
   Create a program that shows the memory difference between `int[100_000]` and `Integer[100_000]`. Use `Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()` before and after allocation, combined with `System.gc()`.

6. **NaN Behavior**
   Demonstrate `NaN` behavior: create a `NaN` value (e.g., `0.0 / 0.0`), test its equality to itself using `==`, use `Double.isNaN()`, and show the result of arithmetic operations involving `NaN`.

7. **IEEE 754 Binary Representation**
   Use `Float.floatToIntBits()` and `Integer.toBinaryString()` to display the IEEE 754 binary representation of several float values (e.g., 1.0f, -1.0f, 0.1f).

8. **Parsing Utility**
   Write a comprehensive parsing utility class `PrimitiveParser` that provides methods to convert a `String` to all 8 primitive types. Include proper `NumberFormatException` handling.

9. **Null Unboxing Danger**
   Demonstrate the danger of unboxing `null`. Write code that triggers a `NullPointerException` during unboxing, and write a safe version that prevents it with null checks.

10. **Performance Benchmark**
    Compare the performance of a loop that sums 10 million random numbers using `long` primitives vs `Long` wrappers. Measure the time using `System.nanoTime()` and print the difference.

---

## HARD EXERCISES (5)

1. **IEEE754 Analyzer**
   Build an `IEEE754Analyzer` that breaks down any given `float` or `double` into its sign bit, exponent bits, and mantissa bits, and displays them clearly. Compute the value manually from the bits to verify.

2. **Custom Cache**
   Write a custom caching mechanism similar to `IntegerCache` for a custom wrapper class called `MyInt`. Pre-cache values from -128 to 127 upon class loading.

3. **Type-Safe Money Class**
   Create a type-safe `Money` class that uses `BigDecimal` internally. It should support basic arithmetic operations (`add`, `subtract`, `multiply`) and completely hide the `BigDecimal` implementation, strictly preventing any initialization via `float` or `double` (only `String` or `int`/`long`).

4. **Array Benchmark**
   Write a benchmark comparing the sum operation across: primitive arrays (`int[]`), wrapper arrays (`Integer[]`), and `ArrayList<Integer>` containing 1,000,000 elements. Report execution time and estimated memory for each approach.

5. **Narrowing Conversions Explorer**
   Write a program that demonstrates ALL narrowing conversions between numeric types (e.g., `double` -> `float` -> `long` -> `int` -> `short` -> `byte`). Show the data loss for values that don't fit, and use bitwise operations to explain the truncation behavior at the bit level.

---

## PRODUCTION PROBLEMS (3)

1. **Financial System Refactoring**
   A financial system uses `double` for currency and reports incorrect totals when aggregating many small transactions. Refactor the provided buggy `ShoppingCart` class to use `BigDecimal` with proper rounding (`RoundingMode.HALF_UP`).

2. **E-commerce Key Bug**
   An e-commerce system uses `Integer` objects as keys in a `HashMap`. A custom retrieval method uses `==` to compare keys instead of `.equals()`. Some lookups return null unexpectedly for IDs > 127. Diagnose and fix the bug in the provided code snippet.

3. **Garbage Collection Pauses**
   A high-throughput system experiences excessive GC pauses. Profiling shows millions of `Integer` objects allocated from autoboxing in a hot loop that calculates checksums. Fix the provided `ChecksumCalculator` class by replacing wrapper objects with primitive arrays and operations.

---

## REFACTORING TASKS (3)

1. Refactor a complex method that uses multiple wrapper classes and redundant conversions into a streamlined version using only primitives where possible.
2. Refactor a legacy API that returns `float` to safely return `BigDecimal` without breaking downstream consumers (use method overloading/deprecation).
3. Refactor a deeply nested mathematical formula (currently using `int` causing overflow) to use `long` and `Math.addExact()`, `Math.multiplyExact()` for safe execution.

---

## DEBUGGING TASKS (3)

1. Debug a program where a `long` loop counter is accidentally initialized as an `int`, causing an infinite loop.
2. Debug a division operation where two integers are divided (e.g., `1 / 3`), assigned to a `double`, resulting in `0.0` instead of `0.333`. Fix it by casting correctly.
3. Debug a `NullPointerException` thrown from a ternary operator involving an unboxed wrapper class condition.

---

## CODE COMPLETION (3)

1. Complete the `SafeCaster` class that takes a `long` and safely casts it to an `int`, throwing a custom `TypeOverflowException` if the value is out of bounds.
2. Complete a utility method that takes a `byte[]` and converts it into a formatted hexadecimal `String`.
3. Complete the `Fraction` class using primitives, handling reducing the fraction to its simplest form using the greatest common divisor.

---

## OUTPUT PREDICTION (5)

Predict the output of the following snippets before running them:
1. `System.out.println(10 + 20 + "30" + 40 + 50);`
2. `System.out.println((byte) 128);`
3. `System.out.println(0.1f == 0.1d);`
4. `Integer a = 100; Integer b = 100; System.out.println(a == b);`
5. `System.out.println(Float.NaN == Float.NaN);`

---

## ERROR FINDING (3)

Find the compilation or runtime errors in the provided snippets:
1. `short s1 = 10; short s2 = 20; short s3 = s1 + s2;`
2. `long val = 1234567890123;`
3. `Double d = null; double primitiveD = d;`

---

## OPTIMIZATION PROBLEMS (2)

1. Optimize a method that processes a massive 2D array `Double[][] matrix` by converting it to a flattened 1D primitive array `double[] flatMatrix`.
2. Optimize a parser that reads millions of records and creates `Long` objects for IDs. Implement a custom primitive-based long-to-int map to prevent object allocation.
