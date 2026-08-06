# Module 2 Volume 5: Practice Exercises - Variables

## Small Exercises (10)
1. Declare a variable that is strictly bound to a hardware register (Hint: Is this possible in Java? If not, how do we emulate it?).
2. Define a `var` inside a lambda expression that shadows a local variable, and explain why it compiles or fails.
3. Declare a constant using `static final` vs a constant in an interface. Explain the bytecode difference in how they are resolved at runtime.
4. Write a snippet that causes a `StackOverflowError` solely through local variable allocations (no recursion). Is it possible?
5. Initialize a `byte` variable with a value that requires an explicit cast, and explain what happens at the bitwise level during the cast.
6. Declare a `String` variable and initialize it using string interpolation (Java 21 String Templates feature) and explain its evaluation time.
7. Create a `ThreadLocal` variable and explain when its initial value is actually computed.
8. Declare a `volatile` variable and describe the exact memory barrier instructions emitted for a write on an x86 architecture.
9. Use pattern matching for switch (Java 21) to declare a variable that is only in scope for one case branch.
10. Declare a variable inside a `for` loop initialization block and explain its exact scope and lifecycle in bytecode.

## Medium Exercises (10)
1. Write a snippet demonstrating the "publication" of a variable. How does `final` affect the publication safety of an object reference?
2. Declare an unnamed variable (Java 21 `_` feature) in a catch block. What are the bytecode implications of not binding the exception to a name?
3. Compare the memory footprint and alignment padding of a class with `byte`, `long`, `boolean`, `int` variables declared in that exact order vs ordered by size.
4. Create an instance where variable shadowing causes a subtle bug involving an inner class and a lambda expression capturing different scopes.
5. Demonstrate the difference between effectively final variables and final variables when captured by an anonymous inner class vs a lambda.
6. Write code that forces the JIT compiler to perform escape analysis and scalar replacement on a local object variable.
7. Explain the initialization order of static variables, instance variables, and local variables in a class with multiple static and instance initialization blocks.
8. Declare a variable using `var` that is inferred as a non-denotable intersection type.
9. Implement a scenario where a stale value of a non-volatile variable is read by another thread, and explain the exact JVM mechanics that allow this.
10. Use `MethodHandles.VarHandle` to perform an atomic compare-and-exchange on an instance variable.

## Hard Exercises (5)
1. Design a class layout that deliberately causes false sharing between two volatile variables. How would you pad the variables to prevent it?
2. Write a program that uses `Unsafe` (or the new FFM API in Java 21) to allocate a variable directly in off-heap memory and manipulate its bytes.
3. Analyze the bytecode generated for a method with 256 local variables vs 257 local variables. How does the `iload` instruction change?
4. Create a scenario where a final variable is observed to change its value during object construction. (Hint: think about circular initialization or reflection).
5. Implement a custom garbage collection root by pinning a variable in memory using JNI, demonstrating the interaction between native code and JVM variables.

## Production Problems (3)
1. A production application is experiencing a memory leak. Heap dumps reveal millions of instances of a `Session` object retained by a `ThreadLocal` variable in a thread pool. Design a fix for this variable lifecycle issue.
2. An algorithmic trading system uses a `double` variable to represent currency amounts. Describe the exact loss of precision that occurs and refactor the variable declaration to use a production-safe type.
3. A high-throughput logging framework is using a shared `volatile int` counter, leading to massive cache coherence traffic and CPU spikes. Redesign the variable architecture using `LongAdder` or padded atomic variables.

## Refactoring Tasks (3)
1. Refactor a legacy method with a 500-line body containing 20 mutable local variables into smaller, pure functions using effectively final variables and modern Java streams.
2. Convert a sprawling class with numerous primitive configuration variables into a Java Record to ensure immutability and compact initialization.
3. Refactor a switch statement that assigns to a mutable variable into a Java 21 Switch Expression that assigns to a `final var`.

## Debugging Tasks (3)
1. You are given a core dump where a boolean variable seems to hold the value `3`. Explain how this is possible at the JVM level and how you would debug the native code causing the corruption.
2. A multi-threaded test occasionally fails because a variable initialization (`x = new Resource()`) appears incomplete to another thread. Diagnose the issue and propose the exact JMM (Java Memory Model) fix.
3. A variable declared as `var` is causing a compilation error when a lambda expression changes. Trace the type inference resolution algorithm and explain the failure.

## Code Completion (3)
1. Complete the code: `VarHandle vh = MethodHandles.lookup().findVarHandle(MyClass.class, "state", _____);` (Provide the correct class type and explain the resolution).
2. Complete the pattern matching snippet: `if (obj instanceof String s && _________) { System.out.println(s.length()); }` (Provide a condition that guarantees `s` is not empty without using `s.isEmpty()`).
3. Complete the initialization: `final int result; try { result = compute(); } catch (Exception e) { _________ }` (Ensure `result` is always initialized without compiler errors).

## Output Prediction (5)
1. Predict the output of a program where a static variable is modified inside a static initialization block that throws an exception, which is caught in a static initializer of a subclass.
2. Predict the output: `int x = 5; x = x++ + ++x; System.out.println(x);` and explain the evaluation order in bytecode.
3. Predict the behavior of reading a `volatile long` variable on a 32-bit JVM under heavy concurrency.
4. Predict the exact output when a floating-point variable initialized to `Float.NaN` is compared using `==`, `>`, and `<` against another variable holding `Float.NaN`.
5. Predict the compilation result of a class where a variable is named `_` (underscore) in Java 8 vs Java 21.

## Error Finding (3)
1. Identify the hidden concurrency bug in this snippet: `public class Counter { private int count = 0; public void increment() { count++; } }` (Explain atomicity vs visibility).
2. Find the error in a generic class where a static variable attempts to use the generic type parameter `T`.
3. Locate the error in a method that attempts to assign a value to a `final` variable inside a loop.

## Optimization Problems (2)
1. Optimize a hot loop that heavily modifies an array of object variables. Explain how loop unrolling and hoisting invariant variables can be manually applied or left to the JIT compiler.
2. Reduce the object header overhead and variable alignment padding of a large caching class to pack more instances into the CPU L1 cache.
