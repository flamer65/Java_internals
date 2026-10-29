# Module 5 Volume 6: Assessment - Type Conversion & Casting

## Target: Java 21 LTS, IntelliJ IDEA

*(Note: Sections contain representative questions covering the required concepts)*

---

### LEVEL 1: MCQs (10 questions)
1. Which of the following is a narrowing primitive conversion?
   a) `int` to `long`
   b) `float` to `double`
   c) `long` to `float`
   d) `double` to `float`

2. What is the result of `byte b = 10; b = b + 5;`?
   a) `b` becomes 15
   b) Compiler error: possible loss of precision
   c) `b` becomes 5
   d) Runtime exception

3. In Java, what type is the result of `1.0 + 2`?
   a) `int`
   b) `float`
   c) `double`
   d) `short`

4. Which statement about autoboxing is TRUE?
   a) Autoboxing can happen simultaneously with widening (e.g., `int` -> `Double`)
   b) Autoboxing is performed before widening in method resolution
   c) `Integer` can be automatically unboxed to `long`
   d) `int` can be automatically boxed to `Long`

5. What happens when casting a `double` with value `NaN` to an `int`?
   a) Throws `ArithmeticException`
   b) Results in `Integer.MAX_VALUE`
   c) Results in `0`
   d) Results in `Integer.MIN_VALUE`

#### LEVEL 1 ANSWERS
1. d (double to float is narrowing)
2. b (byte + int results in int, cannot be assigned to byte without cast)
3. c (double)
4. c (Integer unboxes to int, which widens to long)
5. c (0)

---

### LEVEL 2: TRUE/FALSE (10 questions)
1. `short s = 32767; s++;` results in `s` becoming `-32768`.
2. An explicit cast is required to convert a `long` to a `float`.
3. `Object obj = new Integer(5); String str = (String) obj;` causes a compiler error.
4. The ternary operator `? :` can cause implicit type promotion on its return values.
5. In Java 21, `switch (obj) { case String s -> ... }` requires `obj` to be explicitly cast inside the case block.

#### LEVEL 2 ANSWERS
1. True (overflow)
2. False (long to float is a widening conversion, implicit)
3. False (compiles fine, throws ClassCastException at runtime)
4. True (e.g., `condition ? 1 : 1.0` results in double)
5. False (Pattern matching automatically casts and binds the variable)

---

### LEVEL 3: OUTPUT PREDICTION (5 questions)
1. `System.out.println((byte) 130);`
2. `System.out.println(1 / 2 * 2.0);`
3. `Object o = null; System.out.println((String) o);`
4. `int x = (int) 3.999; System.out.println(x);`
5. `final int X = 100; byte b = X; System.out.println(b);`

#### LEVEL 3 ANSWERS
1. `-126` (130 - 256)
2. `0.0` (1/2 is integer division = 0, 0 * 2.0 = 0.0)
3. `null` (casting null is valid and results in null)
4. `3` (truncation)
5. `100` (constant expression narrowing)

---

### LEVEL 4: FIND THE COMPILER ERROR (5 questions)
Identify the line causing the error:
1. `float f = 3.5;`
2. `char c = -1;`
3. `List<Number> list = new ArrayList<Integer>();`
4. `byte b1 = 5; byte b2 = 10; byte b3 = b1 + b2;`
5. `Double d = 10;`

#### LEVEL 4 ANSWERS
1. `3.5` is a double literal; needs `3.5f` or `(float) 3.5`.
2. `char` is unsigned (0 to 65535); cannot implicitly narrow negative int literal.
3. Generics are invariant; cannot assign `List<Integer>` to `List<Number>`.
4. `b1 + b2` results in `int`; needs `(byte)(b1 + b2)`.
5. Autoboxing doesn't work with widening simultaneously; `10` is `int`, cannot box to `Double`. Needs `10.0`.

---

### LEVEL 5: FIND THE RUNTIME ERROR (5 questions)
Identify the exception thrown:
1. `Object o = new Object(); String s = (String) o;`
2. `Object[] arr = new String[5]; arr[0] = 5;`
3. `Integer i = null; int x = i;`
4. `int x = Math.toIntExact(3_000_000_000L);`
5. `Number n = 5.5; Integer i = (Integer) n;`

#### LEVEL 5 ANSWERS
1. `ClassCastException`
2. `ArrayStoreException`
3. `NullPointerException` (unboxing null)
4. `ArithmeticException` (integer overflow)
5. `ClassCastException` (Double cannot be cast to Integer)

---

### LEVEL 6: DEBUG THIS CODE (5 questions)
1. **Bug:** `double result = 5 / 2;` output is `2.0`, expected `2.5`.
2. **Bug:** `int price = (int) (19.99 * 100);` output is `1998`, expected `1999`.
3. **Bug:** `public boolean equals(Object o) { MyClass m = (MyClass) o; return this.id == m.id; }` crashes on `null`.

#### LEVEL 6 ANSWERS
1. Change to `5.0 / 2` or `(double) 5 / 2`.
2. Floating point inaccuracy (19.99 * 100 = 1998.9999...). Fix: `(int) Math.round(19.99 * 100)`.
3. Missing `instanceof` check. Fix: `if (!(o instanceof MyClass m)) return false; return this.id == m.id;`

---

### LEVEL 7: FILL IN THE MISSING CODE (5 questions)
1. Convert double `d` to int `i` with rounding: `int i = _________;`
2. Java 21 safe cast using pattern switch: `switch(obj) { case _______ -> System.out.println(s.length()); }`
3. Fix the array assignment: `Object[] objArr = _________; objArr[0] = "Hello";`

#### LEVEL 7 ANSWERS
1. `(int) Math.round(d)`
2. `String s`
3. `new Object[10]` (or `new String[10]`)

---

### LEVEL 8: EXPLAIN WHY THIS CODE WORKS (5 questions)
1. Why does `short s = 5; s += 10;` compile without casting?
2. Why does `final int x = 50; byte b = x;` compile, but removing `final` breaks it?
3. Why does `float f = 1000000000000000000L;` compile without a cast?

#### LEVEL 8 ANSWERS
1. Compound assignment operators (`+=`) contain an implicit hidden cast: `s = (short)(s + 10)`.
2. `final` makes `x` a constant variable. The compiler performs "constant expression narrowing" because it proves `50` fits in a `byte`.
3. `long` to `float` is a widening primitive conversion (as float has a larger range due to exponents), even though it loses precision.

---

### LEVEL 9: REFACTORING EXERCISES (5 questions)
1. Refactor: `if (obj instanceof String) { String s = (String) obj; print(s); }`
2. Refactor: `Integer i = Integer.valueOf(5); int x = i.intValue();`
3. Refactor: Overloaded methods `process(int x)`, `process(String s)` into a single `process(Object o)` using Java 21 switch.

#### LEVEL 9 ANSWERS
1. `if (obj instanceof String s) { print(s); }`
2. `int x = 5;` (rely on autoboxing if objects are needed, or just use primitives).
3. `void process(Object o) { switch(o) { case Integer i -> process(i); case String s -> process(s); default -> throw new IllegalArgumentException(); } }`

---

### LEVEL 10: CODING EXERCISES (5 questions)
1. Write a method `safelyCastToByte(int value)` that returns a `byte` or throws an `IllegalArgumentException` if out of bounds.
2. Write a method using pattern matching switch that takes a `Number` and returns its `double` value regardless of its actual subtype.

#### LEVEL 10 ANSWERS
1.
```java
byte safelyCastToByte(int value) {
    if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) throw new IllegalArgumentException();
    return (byte) value;
}
```
2.
```java
double getDouble(Number n) {
    return switch (n) {
        case Double d -> d;
        case Integer i -> i.doubleValue();
        case Float f -> f.doubleValue();
        case null -> 0.0;
        default -> n.doubleValue();
    };
}
```

---

### LEVEL 11: MINI INTERVIEW QUESTIONS (10)
1. Q: Explain the difference between type conversion and type casting.
2. Q: What is numeric promotion in Java?
3. Q: Can you cast a boolean to an int in Java?
4. Q: Explain "heap pollution" in the context of generic casting.

#### LEVEL 11 ANSWERS
1. Conversion is the general process (often implicit, like widening). Casting is the explicit syntax `(Type)` used by the programmer to force a conversion.
2. Numeric promotion automatically widens smaller operands (byte, short, char) to `int` before applying binary operators (like +, -, *).
3. No, `boolean` cannot be cast to any other primitive type.
4. Heap pollution occurs when a variable of a parameterized type refers to an object that is not of that parameterized type, usually caused by unchecked casts with raw types.

---

### LEVEL 12: LARGE CODING ASSIGNMENTS (5)
1. **Build a `TypeConversionExplorer`:** Write a CLI app that programmatically demonstrates all primitive type conversion paths. Output the before/after binary representations.
2. **Build a `PrecisionLossAnalyzer`:** Create a tool that takes a `long` and calculates exactly how much precision is lost when converting it to a `float` and a `double`.
3. **Build a `SafeCastLibrary`:** Implement type-safe casting utilities: `<T> Optional<T> tryCast(Object obj, Class<T> clazz)`.
4. **Build a `NumericPromotionTracer`:** Write an AST parser or bytecode analyzer that shows step-by-step implicit promotion in complex expressions like `(byte)1 + (short)2 * (float)3.0`.
5. **Build a `ConversionBytecodeAnalyzer`:** Write a script that uses `javap` to inspect `.class` files and counts the occurrences of conversion opcodes (`i2f`, `checkcast`, etc.).

---

### LEVEL 13: CAPSTONE PROJECT
**Project: Java Type Conversion Analyzer & Safe Converter**
- **Description:** Build a robust Java library/tool that accepts source code snippets (using Java Compiler API) containing type conversions.
- **Requirements:**
  - Identifies all implicit and explicit conversions.
  - Reports potential issues: precision loss, truncation, overflow, NPE from unboxing.
  - Provides safe conversion alternatives (e.g., suggesting `Math.toIntExact`).
  - Includes bytecode analysis mode showing the generated JVM conversion instructions.
  - Supports CLI Flags: `--analyze`, `--safe-convert`, `--bytecode`, `--report`.
- **Evaluation Rubric:**
  - Accuracy of implicit conversion detection (25 pts)
  - Edge case handling (overflow, NaN) (25 pts)
  - Bytecode integration (20 pts)
  - Code quality and pattern matching usage (30 pts)
