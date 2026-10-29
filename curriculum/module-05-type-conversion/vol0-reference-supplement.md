# Module 5: Type Conversion & Casting
## Volume 0: Reference Supplement

Targeting Java 21 LTS | Environment: IntelliJ IDEA

This volume serves as a rigorous supplement to Volumes 1-3, providing deep explanations for advanced and edge-case topics heavily tested in Volume 5 (Practice) and Volume 6 (Assessment). 

---

## 1. Advanced Reference Conversions & Generics

### Type Erasure and Cast Insertion
Java implements generics using **Type Erasure**. At compile time, generic type parameters (`<T>`) are erased and replaced by their bounds (usually `Object`). 

When you retrieve a value from a generic collection, `javac` implicitly inserts a `checkcast` instruction at the call site.

```java
List<String> list = new ArrayList<>();
list.add("Hello");
// What you write:
String s = list.get(0);

// What javac generates (equivalent):
// String s = (String) list.get(0); // get() returns Object at runtime
```

### Heap Pollution
**Heap Pollution** occurs when a parameterized type variable refers to an object that is not of that parameterized type. This often happens when mixing raw types with parameterized types or using unchecked casts.

> [!WARNING]
> Heap pollution leads to `ClassCastException` occurring far away from the actual buggy assignment, making debugging extremely difficult. The compiler inserts a synthetic cast upon retrieval, which fails at runtime.

```java
List rawList = new ArrayList<Integer>(); // Raw type
rawList.add(42);

// Unchecked assignment warning here
List<String> stringList = rawList; 

// Heap pollution! The list contains an Integer, but is typed as List<String>.
// ClassCastException occurs HERE, not at the assignment:
String s = stringList.get(0); 
```

#### The `@SafeVarargs` and Varargs Pollution
Varargs (`...`) are implemented as arrays. Because arrays are reified and generic types are erased, mixing them creates unsafe arrays.

```java
// Warning: Possible heap pollution from parameterized vararg type
static void addToList(List<String>... lists) {
    Object[] array = lists;
    // We can sneak a List<Integer> into the array!
    array[0] = Arrays.asList(42);
    // ClassCastException when lists[0].get(0) is called
}
```
Use `@SafeVarargs` only if the method does not store anything into the varargs array and does not let its reference escape.

### Bridge Methods and Covariant Return Types
When a class implements a generic interface or overrides a method with a **covariant return type** (returning a more specific subtype), `javac` generates **synthetic bridge methods** to maintain polymorphism in the erased bytecode.

```java
class Node<T> {
    public void setData(T data) {}
}

class StringNode extends Node<String> {
    @Override
    public void setData(String data) {} 
    
    // javac generates a bridge method internally:
    // public /* synthetic bridge */ void setData(Object data) {
    //     setData((String) data); // checkcast instruction here!
    // }
}
```
You can detect bridge methods using Reflection: `method.isBridge()`.

### Array Covariance and ArrayStoreException
In Java, arrays are **covariant**. If `String` is a subtype of `Object`, then `String[]` is a subtype of `Object[]`. 

> [!CAUTION]
> Array covariance is mathematically unsound because arrays are mutable. The JVM enforces type safety at runtime by performing a type check (`aastore` instruction) every time an element is stored.

```java
String[] strings = new String[5];
Object[] objects = strings; // Valid array covariance

// Compiles fine, but throws ArrayStoreException at runtime!
objects[0] = 42; // The JVM knows 'objects' is actually a String[]
```

---

## 2. Advanced Method Resolution

### Widening vs. Autoboxing in Method Overloading
When resolving overloaded methods, `javac` tries to find the most specific match. The priority order is:
1. Exact match (or Subtype match)
2. Widening primitive conversion (e.g., `int` -> `long`)
3. Autoboxing/Unboxing (e.g., `int` -> `Integer`)
4. Varargs (`...`)

```java
public void process(long l) { System.out.println("long"); }
public void process(Integer i) { System.out.println("Integer"); }
public void process(int... i) { System.out.println("varargs"); }

public void test() {
    int val = 5;
    process(val); // Calls process(long) -> Widening beats Autoboxing!
}
```

---

## 3. The `Math` Safe Conversion API

When narrowing primitives manually (e.g., `(int) myLong`), data is silently truncated. Java provides the `Math` API for safe and precise numeric conversions.

> [!TIP]
> Always use `Math.toIntExact()` when converting `long` to `int` in business-critical systems to fail fast on overflow.

| Method | Behavior | Use Case |
|---|---|---|
| `Math.toIntExact(long)` | Returns `int` or throws `ArithmeticException` if out of bounds. | Safe narrowing from `long` to `int`. |
| `Math.round(float/double)` | Returns `int`/`long` rounded to nearest integer (adds 0.5, truncates). | Accurate decimal to integer conversion. |
| `Math.ceil(double)` | Returns `double` rounded UP to positive infinity. | Rounding up partial units (e.g., pagination). |
| `Math.floor(double)` | Returns `double` rounded DOWN to negative infinity. | Rounding down. |

---

## 4. Modern Java: Switch, Patterns, and Exhaustiveness

### Pattern Matching `switch` (Java 21)
Java 21 introduces record patterns and guarded patterns in `switch`.

**Guarded Patterns** allow you to add conditions (`when`) to cases. 
**Dominance** dictates that more specific patterns must appear before more general ones.

```java
Object obj = "Hello Java";

String result = switch (obj) {
    case String s when s.length() > 5 -> "Long string: " + s; // Guarded pattern
    case String s -> "Short string";
    case Integer i -> "Int: " + i;
    case null -> "Null object";
    default -> "Unknown";
};
```

### Record Patterns
You can automatically extract (deconstruct) components from a Record.

```java
record Point(int x, int y) {}

Object obj = new Point(10, 20);

if (obj instanceof Point(int x, int y)) {
    System.out.println(x + ", " + y); // No manual cast or accessor calls!
}
```

### Sealed Types and Exhaustiveness
When switching on a `sealed` interface, the compiler knows all possible implementations. You do not need a `default` branch if you cover all permitted subclasses.

```java
sealed interface Shape permits Circle, Square {}
final class Circle implements Shape {}
final class Square implements Shape {}

Shape shape = new Circle();
// Exhaustive switch - no default needed!
int sides = switch (shape) {
    case Circle c -> 0;
    case Square s -> 4;
};
```

---

## 5. JVM Internals & Reflection

### `Class.cast()` and `Class.isInstance()`
For dynamic type conversions where the target type is not known until runtime, use the `Class` object's reflection methods.

```java
public <T> T dynamicCast(Object obj, Class<T> type) {
    if (type.isInstance(obj)) {
        return type.cast(obj); // Safe dynamic cast
    }
    throw new IllegalArgumentException();
}
```

### Optional Safe Cast Pipeline
Combine `Optional` with method references for elegant, functional type casting:

```java
Object unknown = "Test";
Optional<String> optString = Optional.ofNullable(unknown)
    .filter(String.class::isInstance)
    .map(String.class::cast);
```

### `checkcast` and Enhanced Exceptions (JEP 358)
The `checkcast` bytecode instruction walks the class hierarchy and interface tables. If the check fails, Java 14+ provides enhanced `ClassCastException` messages.

**Old Error:** `java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String`
**Enhanced Error:** `java.lang.ClassCastException: class java.lang.Integer cannot be cast to class java.lang.String (java.lang.Integer and java.lang.String are in module java.base of loader 'bootstrap')`
