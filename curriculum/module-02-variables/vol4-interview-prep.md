# Module 2, Volume 4: Variables – Interview Preparation

Welcome to Volume 4 of the Java Mastery Curriculum for Module 2: Variables. This document is designed to rigorously prepare you for technical interviews, spanning from entry-level syntax questions to senior-level JVM internals and performance engineering scenarios. 

---

## 1. BASIC QUESTIONS (Syntax & Fundamentals)

**1. What is a variable in Java?**
A variable is a named memory location used to store data that can be manipulated during program execution. In Java, every variable has a specific type that determines its size in memory, the range of values it can hold, and the operations that can be performed on it.

**2. What are the 8 primitive data types?**
Java has 8 primitive data types built into the language. They are not objects.
*   **`byte`**: 8-bit signed integer. (-128 to 127)
*   **`short`**: 16-bit signed integer. (-32,768 to 32,767)
*   **`int`**: 32-bit signed integer. (-2^31 to 2^31 - 1)
*   **`long`**: 64-bit signed integer. (-2^63 to 2^63 - 1)
*   **`float`**: 32-bit floating-point (IEEE 754).
*   **`double`**: 64-bit floating-point (IEEE 754).
*   **`char`**: 16-bit unsigned Unicode character (0 to 65,535).
*   **`boolean`**: True or false (size is JVM dependent, usually 1 byte).

**3. What is the difference between primitive and reference variables?**
Primitive variables store the actual value directly in the memory location allocated for the variable (typically on the stack if it's a local variable). Reference variables, however, do not store the object itself. They store the memory address (a pointer) of the object, which resides on the heap.

**4. What are default values in Java?**
Instance and static variables are automatically initialized by the JVM if no explicit value is provided. 
*   Numeric primitives (`byte`, `short`, `int`, `long`, `float`, `double`) default to `0` or `0.0`.
*   `char` defaults to `\u0000` (the null character).
*   `boolean` defaults to `false`.
*   All Reference variables (including String, arrays, objects) default to `null`.
*   *Note: Local variables do NOT have default values and must be initialized before use.*

**5. What is the `final` keyword for variables?**
When applied to a variable, `final` means the variable can only be assigned exactly once. For primitives, the value cannot change. For references, the variable cannot point to a different object, though the internal state of the object it points to can still be modified (unless the object itself is immutable).

**6. What is `var` in Java? Is Java dynamically typed?**
`var` (introduced in Java 10) allows Local Variable Type Inference. The compiler determines the type based on the right-hand side of the assignment during compilation. Java remains strongly and statically typed. `var` is just syntactic sugar; the type is fixed at compile time and cannot change later.

**7. What is variable scope?**
Scope defines the visibility and lifetime of a variable.
*   *Class/Static scope*: Available to all instances and static methods. Lives for the life of the classloader.
*   *Instance scope*: Available to a specific object. Lives as long as the object is not garbage collected.
*   *Local scope*: Declared in a method or block. Exists only while that block is executing.

**8. What is the difference between declaration, initialization, and assignment?**
*   *Declaration*: Stating the type and name (`int x;`).
*   *Initialization*: The very first assignment of a value (`int x = 5;` or `x = 5;` immediately after declaration).
*   *Assignment*: Changing the value of an already initialized variable (`x = 10;`).

**9. What is an identifier in Java? What are the naming rules?**
An identifier is the name given to a variable, class, or method. Rules:
1. Must begin with a letter, dollar sign (`$`), or underscore (`_`).
2. Subsequent characters can be numbers.
3. Cannot be a Java reserved keyword.
4. Case-sensitive.

**10. Can you name a variable `var`, `true`, `null`, or `_`?**
*   `var`: Yes, `var` is a *reserved type name*, not a strict keyword. You can have `int var = 5;` (though it's bad practice).
*   `true` / `null`: No, these are reserved literals.
*   `_`: As of Java 9, a single underscore `_` is a keyword and cannot be used as a variable name.

---

## 2. INTERMEDIATE QUESTIONS (Memory & Mechanics)

**1. Explain the memory layout of primitive vs reference variables (stack vs heap).**
When declared as local variables inside a method, both the primitive value and the reference variable itself are stored on the thread's execution Stack. However, the object that the reference variable points to is allocated on the Heap. When declared as instance variables, both primitives and reference variables are stored on the Heap inside the memory block allocated for the parent object.

**2. What is the difference between `final` primitive and `final` reference?**
A `final` primitive means the actual underlying bits cannot change. A `final` reference means the bits representing the memory address cannot change (it cannot point to a new object). It does not protect the object itself from modification.

**3. What is constant folding? Give an example with bytecode proof.**
Constant folding is a compiler optimization where static, final, primitive (or String) values known at compile-time are substituted directly into the bytecode where they are referenced, rather than looking up the variable at runtime.
*Example:* `static final int X = 5; int y = X * 2;`
*Bytecode Proof:* The bytecode will show `bipush 10` (pushing the value 10 directly) instead of an `iload` followed by a multiplication instruction.

**4. What is definite assignment analysis? Why does Java have it?**
It is a compiler process that ensures every local variable is assigned a value before it is read on every possible execution path. Java enforces this to prevent reading uninitialized memory, which is a major source of unpredictable bugs and security vulnerabilities in C/C++.

**5. What is variable shadowing? How does `this` resolve it?**
Shadowing occurs when a local variable or method parameter has the exact same name as an instance variable of the class. Within that scope, the local variable hides the instance variable. The keyword `this` acts as a reference to the current object, allowing you to explicitly target the instance variable (e.g., `this.name = name;`).

**6. Where does `var` work and where doesn't it? Why these restrictions?**
*Works:* Local variables with initializers, enhanced for-loops, regular for-loops.
*Doesn't Work:* Class fields, method parameters, return types, uninitialized variables, variables assigned to null.
*Why:* The compiler needs local, immediate context to infer the type safely without causing cascading dependency issues or altering public API signatures, which must remain explicit and stable.

**7. What are compile-time constants in Java? How does javac handle them?**
A compile-time constant is a variable declared as `final`, of primitive or String type, initialized with a constant expression. `javac` inlines these values into the `.class` files of any class that references them. This creates a dependency hazard: if the constant's value changes, all referring classes must be recompiled to see the new value.

**8. What is the size of a reference variable in JVM? (compressed oops)**
On a 32-bit JVM, it is 32 bits (4 bytes). On a 64-bit JVM, it is natively 64 bits (8 bytes). However, modern 64-bit JVMs enable `UseCompressedOops` by default for heaps under ~32GB, which compresses references back down to 32 bits to save massive amounts of memory bandwidth and cache space.

**9. What happens when you assign `null` to a reference variable?**
The variable's bits are zeroed out (representing no address). The object it previously pointed to loses a strong reference. If no other strong references to that object exist, the object becomes eligible for Garbage Collection.

**10. How does the JVM store local variables? (stack frame local variable array)**
When a method is invoked, a new Stack Frame is created. This frame contains a Local Variable Array. Variables are mapped to index slots in this array. `int`, `float`, and references take one slot; `long` and `double` take two contiguous slots. 

---

## 3. ADVANCED QUESTIONS (JVM Internals & Concurrency)

**1. Explain the bytecode instructions for variable operations.**
*   `istore_n`: Stores an integer from the operand stack into local variable slot `n`.
*   `iload_n`: Loads an integer from local variable slot `n` onto the operand stack.
*   `astore_n` / `aload_n`: Same operations, but for Object references (`a` stands for address/reference).
*   `putfield` / `getfield`: Sets or gets an instance variable from an object on the heap.

**2. What is escape analysis and how does it affect variable allocation?**
Escape Analysis is a JIT compiler optimization phase. The compiler analyzes the scope of a newly created object. If the object never "escapes" the method (e.g., isn't returned, passed to an external method, or assigned to a static field), the JIT may optimize away the heap allocation entirely and allocate the object's scalar fields directly onto the thread's stack (Scalar Replacement), drastically reducing Garbage Collection overhead.

**3. Explain the Java Memory Model guarantees for `final` fields.**
The JMM ensures "Safe Publication" for `final` fields. If an object is constructed correctly (the `this` reference doesn't escape during the constructor), any thread that acquires a reference to that object is guaranteed to see the initialized values of its `final` fields without needing explicit synchronization (`volatile` or `synchronized`).

**4. What is TLAB and how does it relate to variable/object allocation?**
TLAB stands for Thread Local Allocation Buffer. It is a small chunk of the heap dedicated exclusively to one thread. When a thread executes `new Object()`, it allocates the memory sequentially within its TLAB. This avoids lock contention on the heap, making object allocation in Java blisteringly fast—essentially just a pointer bump.

**5. How does the JVM decide between stack allocation and heap allocation?**
In standard Java, all objects are heap-allocated, and all local primitives/references are stack-allocated. However, at runtime, if the JIT compiler's Escape Analysis proves an object doesn't escape the method, it will perform Scalar Replacement, unrolling the object into its constituent primitives and placing *those* on the stack.

**6. What is the object header in Java? (mark word, klass pointer, padding)**
Every object on the heap has overhead. The object header consists of:
*   **Mark Word (8 bytes):** Stores runtime data like hash code, GC age, and synchronization lock status.
*   **Klass Pointer (4 bytes with compressed oops, 8 without):** A pointer to the class metadata (the `java.lang.Class` instance in Metaspace) to identify the object's type.
*   **Padding:** Objects are padded to 8-byte boundaries for CPU cache alignment.

**7. Explain compressed oops and when they are used.**
"Oops" stands for Ordinary Object Pointers. On a 64-bit JVM, 64-bit pointers waste cache space. Compressed Oops encode a 64-bit address into 32 bits by leveraging the fact that Java objects are 8-byte aligned. The 32-bit pointer acts as an offset, multiplied by 8 by the CPU hardware, allowing a 32-bit pointer to address up to 32GB of heap.

**8. How does constant pool interning work for String variables?**
When a String literal (e.g., `String s = "hello"`) is encountered, the JVM checks a dedicated memory area called the String Pool. If "hello" already exists, `s` points to the existing instance. If not, a new instance is created in the pool. This saves memory. Strings created via `new String("hello")` bypass this pool unless `.intern()` is called.

**9. What is the difference between `static final` compile-time constant and runtime constant?**
*   *Compile-time constant:* Primitive or String initialized with a literal. Inlined by `javac` into all referring classes.
*   *Runtime constant:* Initialized via a method call (e.g., `static final int ID = generateId();`). Not inlined. Referring classes will look up the value at runtime.

**10. How does JIT handle `final` variables differently from non-final?**
The JIT compiler assumes `final` fields are immutable after construction. It will aggressively hoist reads of `final` fields out of loops and inline their values directly into machine code. Modifying a `final` field via Reflection violates this assumption and can cause the JVM to perform a costly deoptimization of compiled code.

---

## 4. TRICKY / GOTCHA QUESTIONS

**1. What is the output? `int x = 0; x = x++;`**
*Answer:* `x is 0`. `x++` evaluates to the current value (`0`), increments the local variable to `1`, but then the assignment `=` overwrites `x` back to the evaluated value of `0`.

**2. Is `var` a keyword? Can you use it as a variable name, class name, method name?**
*Answer:* It is a *reserved type name*, not a keyword. You can use it as a variable name (`int var = 1;`) or method name, but NOT as a class or interface name.

**3. What is the output? `final int[] arr = {1,2,3}; arr[0] = 99;`**
*Answer:* It compiles and runs perfectly. The `final` keyword applies to the reference `arr`, meaning `arr = new int[3]` is illegal, but modifying the array's contents is allowed.

**4. What happens if you use `float f = 3.14;`?**
*Answer:* Compiler error. `3.14` is a `double` literal. You cannot implicitly narrow a double to a float. You must write `3.14f` or `(float) 3.14`.

**5. `byte b = 127; b++;` — what is b?**
*Answer:* `-128`. The maximum positive value of a byte is 127. Incrementing it causes integer overflow, wrapping around to the minimum negative value.

**6. What's the default value of a local `int` variable?**
*Answer:* There is no default value. If you attempt to use it without explicitly initializing it, the compiler will throw an error: `variable might not have been initialized`.

**7. `char c = 'A'; int i = c;` — what is i?**
*Answer:* `65`. Java performs implicit widening from `char` to `int`. It assigns the Unicode/ASCII integer value of 'A' to `i`.

**8. Can you have a `final` variable that is assigned inside an if-else?**
*Answer:* Yes, this is called a "blank final". As long as the compiler's definite assignment analysis proves it is assigned *exactly once* on every execution path, it is valid.

**9. `String s1 = "hello"; String s2 = "hello"; s1 == s2` — true or false?**
*Answer:* True. Because both are literals, they point to the exact same object in the String Pool due to interning.

**10. What is the result of `Integer.MAX_VALUE + 1`?**
*Answer:* `Integer.MIN_VALUE` (-2147483648). Silent integer overflow.

---

## 5. SCENARIO QUESTIONS

**Scenario 1: A team member uses `var` for all local variables. Is this good or bad? Justify.**
*Answer:* Bad. `var` should be used to reduce clutter when the type is obvious (e.g., `var list = new ArrayList<String>()`). If used everywhere (e.g., `var result = calculateData()`), it obscures the type, forcing readers to jump to method definitions to understand what `result` is, harming code readability and maintainability.

**Scenario 2: You find a bug where a value changed unexpectedly. You discover two reference variables point to the same object. Explain aliasing.**
*Answer:* Aliasing occurs when multiple reference variables hold the same memory address (pointer). Modifying the object state through one reference (`ref1.setAge(30)`) instantly alters the state observed by the other reference (`ref2.getAge() == 30`), because there is only one object in the heap. 

**Scenario 3: A static final String constant was changed in a library, but your application still shows the old value. What happened?**
*Answer:* The constant was inlined at compile time. Because it was a `static final String`, `javac` treated it as a compile-time constant and hardcoded the literal string value into your application's `.class` file. You must recompile your application against the new library version to pick up the change.

**Scenario 4: Your application crashes with NullPointerException on a line where you call a method on an object. How does Java 17+ help you debug this?**
*Answer:* Java 14+ introduced Helpful NullPointerExceptions (JEP 358). Instead of just pointing to the line number, the JVM analyzes the bytecode and outputs a detailed message explaining exactly *which* variable or method return value was null. Example: `Cannot invoke "String.toLowerCase()" because the return value of "User.getName()" is null`.

**Scenario 5: A junior developer asks: does `final` make an object immutable? How do you explain?**
*Answer:* I would explain that `final` only protects the *reference*, not the object. I would draw a diagram showing a `final` pointer to a box on the heap. `final` means the pointer cannot move to a different box. However, the contents inside the box (the object's fields or array elements) can still be changed unless the class itself is designed to be immutable (like `String` or a `Record`).

---
*End of Volume 4: Interview Preparation.*
