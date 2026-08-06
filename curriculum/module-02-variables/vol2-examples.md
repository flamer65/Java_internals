# Module 2 Volume 2: Variables - Examples

This document contains an exhaustive collection of examples demonstrating Java variables, their behavior at compile time (javac) and runtime (JVM), memory layout, and common pitfalls. 

---

## 1. SIMPLE EXAMPLES (8+)

### 1.1 Declaring and Initializing All 8 Primitive Types
```java
public class Primitives {
    public static void main(String[] args) {
        byte b = 10;        // 1 byte  (-128 to 127)
        short s = 1000;     // 2 bytes (-32,768 to 32,767)
        int i = 100000;     // 4 bytes (-2^31 to 2^31 - 1)
        long l = 1000000L;  // 8 bytes (-2^63 to 2^63 - 1)
        float f = 10.5f;    // 4 bytes (IEEE 754 single precision)
        double d = 20.99;   // 8 bytes (IEEE 754 double precision)
        char c = 'A';       // 2 bytes (0 to 65,535) - unsigned Unicode
        boolean bool = true;// Size is JVM-dependent (usually 1 byte/int internally)
    }
}
```
* **Explanation:** Demonstrates initialization of all 8 primitives. Note the `L` for long and `f` for float to tell the compiler the literal type.
* **Compiler (javac):** Verifies values fit within the declared type's boundaries.
* **JVM (Runtime):** Allocates space on the current thread's call stack (in the local variable array of the current frame).
* **Expected Output:** No output, just memory allocation.

### 1.2 Declaring Reference Variables (String, arrays)
```java
String text = "Hello";
int[] numbers = new int[]{1, 2, 3};
```
* **Explanation:** `text` and `numbers` are references (pointers) stored on the stack. The actual objects (`"Hello"` from String Pool, and the array) live on the Heap.
* **Compiler:** Ensures type safety (e.g., cannot assign `text = 5`).
* **JVM:** Pushes reference to stack, allocates object on heap, links stack reference to heap memory address.

### 1.3 Multiple Variable Declaration
```java
int a = 1, b = 2, c = 3;
```
* **Explanation:** Comma-separated declarations of the same type.
* **Compiler:** Expands this internally into three separate variable declarations.

### 1.4 Variable Assignment and Reassignment
```java
int score = 50;
score = 100;
```
* **Explanation:** The value inside the stack slot for `score` is overwritten.
* **JVM:** Replaces `50` with `100` in the local variable array slot.

### 1.5 Using `final` with Primitives
```java
final int MAX_SPEED = 120;
// MAX_SPEED = 130; // COMPILER ERROR
```
* **Explanation:** `final` prevents reassignment.
* **Compiler:** Emits an error if reassignment is attempted. Often inlines this value if it's a compile-time constant.

### 1.6 Using `var` for Local Variables
```java
var id = 1;                 // int
var price = 9.99;           // double
var name = "Java";          // String
var list = new ArrayList<String>(); // ArrayList<String>
```
* **Explanation:** Type inference for local variables (Java 10+).
* **Compiler:** Infers the type from the right-hand side during compilation. Emits identical bytecode to explicit typing.

### 1.7 Default Values: Instance vs Local
```java
public class DefaultDemo {
    int instanceVar; // Defaults to 0
    public void demo() {
        int localVar;
        // System.out.println(localVar); // COMPILER ERROR: might not have been initialized
    }
}
```
* **Explanation:** Instance variables get default values (0, false, null) when the object is created. Local variables do not.
* **Compiler:** Performs definite assignment analysis for local variables.

### 1.8 Character Variable with Unicode
```java
char c = '\u0041'; // Hex 41 = Decimal 65 = 'A'
```
* **Explanation:** Java chars use UTF-16 encoding.

### 1.9 Boolean Variable in Conditions
```java
boolean isActive = true;
if (isActive) { /* ... */ }
```
* **JVM:** Often compiles boolean checks into integer comparisons (1 for true, 0 for false) using instructions like `ifeq` or `ifne`.

---

## 2. INTERMEDIATE EXAMPLES (6+)

### 2.1 `final` Reference Variable
```java
final StringBuilder sb = new StringBuilder("A");
sb.append("B"); // Valid - mutating the object
// sb = new StringBuilder("C"); // Invalid - changing the reference
```
* **Explanation:** `final` fixes the reference (the pointer), not the referenced object's internal state.

### 2.2 Variable Shadowing
```java
public class Shadow {
    int val = 10;
    void doWork() {
        int val = 20; // Shadows instance variable
        System.out.println(this.val); // 10
        System.out.println(val);      // 20
    }
}
```

### 2.3 Type Inference with `var` Bytecode Equivalence
```java
var x = 10;
int y = 10;
```
* **Compiler:** Generates exactly the same bytecode (`bipush 10`, `istore_1`, etc.). `var` is syntactic sugar.

### 2.4 Definite Assignment and Branches
```java
int result;
if (Math.random() > 0.5) result = 1;
else result = 0;
System.out.println(result); // OK, compiler proves it's initialized on all paths
```

### 2.5 Constant Folding
```java
final int X = 10;
int y = X + 5;
```
* **Compiler:** Javac evaluates `10 + 5` at compile time. The bytecode directly assigns `15` to `y`.

### 2.6 Aliasing (Multiple References)
```java
int[] arr1 = {1};
int[] arr2 = arr1;
arr2[0] = 99;
System.out.println(arr1[0]); // 99
```
* **Explanation:** Both references point to the same heap object.

### 2.7 Scope Demonstration
```java
{
    int scoped = 5;
}
// System.out.println(scoped); // ERROR: out of scope
```

---

## 3. ADVANCED EXAMPLES (4+)

### 3.1 Object Layout Inspection
```java
String s1 = new String("Hi");
String s2 = new String("Hi");
System.out.println(System.identityHashCode(s1) == System.identityHashCode(s2)); // false
```
* **Explanation:** Two distinct objects in the heap with identical contents but different memory locations.

### 3.2 `var` with Diamond Operator Gotcha
```java
var list = new ArrayList<>(); // Infers ArrayList<Object>!
list.add("String");
list.add(10); // Allowed!
```
* **Fix:** Use explicit type on RHS: `var list = new ArrayList<String>();`

### 3.3 Compile-Time Constant Inlining
```java
public static final String VERSION = "1.0";
```
* **Compiler:** Any other class referencing `VERSION` gets `"1.0"` embedded in its own `.class` file. Recompiling just this class doesn't update the others!

### 3.4 LocalVariableTable and Slots
```java
void func() {
    double d = 1.0; // takes slot 1 and 2
    int i = 5;      // takes slot 3
}
```
* **JVM:** `long` and `double` take two slots in the local variable array.

### 3.5 Escape Analysis
```java
void process() {
    Point p = new Point(1, 2);
    // p doesn't escape method
}
```
* **JVM:** The JIT compiler may allocate `p` on the stack instead of the heap to avoid GC overhead.

---

## 4. PRODUCTION EXAMPLES (3+)

### 4.1 Proper Constant Definition
```java
private static final int MAX_RETRY_ATTEMPTS = 3;
```

### 4.2 `var` in Complex Generics
```java
// Instead of:
Map<String, List<CompletableFuture<Response>>> map = new HashMap<>();
// Use:
var map = new HashMap<String, List<CompletableFuture<Response>>>();
```

### 4.3 Immutable Configuration Class
```java
public final class AppConfig {
    private final String host;
    public AppConfig(String host) { this.host = host; }
}
```

---

## 5. WRONG EXAMPLES — COMPILER ERRORS (12+)
1. `int x; System.out.println(x);` -> Variable x might not have been initialized
2. `final int x = 10; x = 20;` -> Cannot assign a value to final variable x
3. `int 2ndPlace = 5;` -> Illegal start of expression
4. `int class = 5;` -> Not a statement (reserved keyword)
5. `var x;` -> Cannot infer type for local variable
6. `void foo(var x)` -> 'var' is not allowed here
7. `class X { var x = 10; }` -> 'var' is not allowed here
8. `var x = null;` -> Cannot infer type for local variable
9. `var fn = (x) -> x * 2;` -> Lambda expression needs an explicit target type
10. `int x = 1; int x = 2;` -> Variable x is already defined
11. `int x = "hello";` -> Incompatible types
12. `int x = 10` -> ';' expected

---

## 6. WRONG EXAMPLES — RUNTIME ERRORS (5+)
1. `String s = null; s.length();` -> **NullPointerException**
2. `int[] arr = new int[3]; arr[5] = 10;` -> **ArrayIndexOutOfBoundsException**
3. `void recurse() { recurse(); }` -> **StackOverflowError**
4. `Object obj = "hello"; Integer num = (Integer) obj;` -> **ClassCastException**
5. `int z = 10 / 0;` -> **ArithmeticException**

---

## 7. EDGE CASES (6+)
1. **`var` as an identifier:** `int var = 42;` works because `var` is a restricted identifier, not a keyword.
2. **Underscore `_`:** `int _ = 5;` (error in modern Java, restricted keyword).
3. **Char math:** `char c = 65;` (Compiles, prints 'A').
4. **Boolean size:** Not defined by JVM spec (usually compiled to int).
5. **Overflow:** `int x = Integer.MAX_VALUE + 1;` (wraps to `Integer.MIN_VALUE`).
6. **Blank finals:** `final int x; if(true) x=1;` (perfectly valid).

---

## 8. PERFORMANCE EXAMPLES
* **Stack vs Heap:** Primitives on stack are cache-friendly and cleaned up instantly when the frame pops. Objects on heap require pointer chasing and Garbage Collection.
* **Constant Folding:** `final static int DAYS = 7;` saves memory reads at runtime since `7` is baked directly into instructions like `bipush`.

---

## 9. INTERVIEW EXAMPLES
* **Q:** Difference between primitive and reference?
  * **A:** Primitive stores the actual value in its memory slot. Reference stores a memory address pointing to an object on the heap.
* **Q:** What does `final` mean for references?
  * **A:** The pointer cannot be changed to point to another object, but the object itself can be mutated.
* **Q:** Does `var` make Java dynamically typed?
  * **A:** No. `var` is strictly compile-time type inference. The bytecode is identical to explicitly typed code.
