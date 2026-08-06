# Module 1 Volume 4: Interview Preparation

This document provides a comprehensive set of questions and answers to test your deep understanding of Java fundamentals, the JVM, and the execution model.

## BASIC QUESTIONS

**1. What is Java?**
Java is a high-level, class-based, object-oriented programming language designed to have as few implementation dependencies as possible. It is a general-purpose programming language intended to let application developers "write once, run anywhere" (WORA), meaning that compiled Java code can run on all platforms that support Java without the need for recompilation.

**2. What is the JVM?**
The Java Virtual Machine (JVM) is an abstract computing machine that enables a computer to run a Java program. It provides a runtime environment in which Java bytecode can be executed. The JVM is responsible for converting bytecode into machine-specific code, memory management (Garbage Collection), and providing a secure execution environment.

**3. What is the JDK?**
The Java Development Kit (JDK) is a software development environment used for developing Java applications and applets. It physically exists and contains the JRE + development tools like the compiler (`javac`), an archiver (`jar`), a documentation generator (`javadoc`), and other tools needed to write and compile Java programs.

**4. What is the JRE?**
The Java Runtime Environment (JRE) is an installation package that provides an environment to *run* (not develop) a Java program. It contains the JVM, core classes, and supporting files. If you only want to run a Java program, you just need the JRE. (Note: Since Java 11, Oracle no longer provides a separate JRE download; developers use tools like `jlink` to create custom runtimes).

**5. What does 'Write Once, Run Anywhere' mean?**
This is a slogan created by Sun Microsystems to illustrate Java's cross-platform benefits. It means that you can compile a Java program on one platform (like Windows) into a `.class` file (bytecode), and that exact same `.class` file can be executed on any other platform (like Linux or Mac) provided a JVM is installed on that target platform. The JVM acts as a translator between the universal bytecode and the specific hardware.

**6. Is Java compiled or interpreted?**
Java is both compiled and interpreted. First, the source code (`.java`) is **compiled** by `javac` into bytecode (`.class`). Then, at runtime, the JVM **interprets** this bytecode instruction by instruction. Furthermore, the JVM utilizes a Just-In-Time (JIT) compiler that dynamically compiles heavily used bytecode segments down to native machine code at runtime for performance.

**7. What is bytecode?**
Bytecode is a highly optimized set of instructions designed to be executed by the JVM. It is the intermediate representation of Java code. It is platform-independent, meaning the bytecode itself does not contain instructions specific to Intel, ARM, or any other physical architecture.

**8. What is the main method signature and why each keyword?**
`public static void main(String[] args)`
- `public`: The JVM needs to access the method from outside the class to initiate execution.
- `static`: Allows the JVM to invoke the method without having to instantiate the class first.
- `void`: The method does not return a value to the JVM.
- `main`: The configured name the JVM searches for as the entry point.
- `String[] args`: An array of strings capturing command-line arguments passed to the application.

**9. What is the difference between JDK, JRE, and JVM?**
- **JVM** is the execution engine (abstract specification + concrete implementation).
- **JRE** is JVM + Core Libraries (Runtime environment).
- **JDK** is JRE + Development Tools like `javac` (Development environment).

**10. What are the different editions of Java?**
- **Java SE (Standard Edition):** Core Java APIs (collections, threading, IO). Used for desktop and general-purpose applications.
- **Jakarta EE (formerly Java EE - Enterprise Edition):** Built on top of SE, provides APIs for enterprise apps (Servlets, JPA, JMS).
- **Java ME (Micro Edition):** Historically used for resource-constrained devices (mobile phones, embedded systems), largely obsolete now.

---

## INTERMEDIATE QUESTIONS

**1. Explain the Java compilation and execution pipeline end-to-end.**
A developer writes `Source.java`. The JDK compiler (`javac`) performs lexical analysis, parsing, semantic analysis, and bytecode generation, resulting in `Source.class`. To execute, the command `java Source` starts the JVM. The ClassLoader loads `Source.class` into memory. The Bytecode Verifier ensures the code is safe. The Execution Engine then begins interpreting the `main` method. As the program runs, the JIT compiler monitors execution, compiling "hot" methods into native machine code for faster subsequent executions. The Garbage Collector simultaneously manages heap memory.

**2. What is JIT compilation? How does it differ from AOT?**
Just-In-Time (JIT) compilation happens *during* runtime. The JVM interprets bytecode initially, but translates frequently executed bytecode blocks into native machine code on-the-fly. This allows for aggressive optimizations based on actual runtime profiling data.
Ahead-Of-Time (AOT) compilation happens *before* runtime. Tools like GraalVM Native Image compile Java directly into a native executable (`.exe` or ELF) before it runs. AOT provides blazing fast startup times and low memory footprint but lacks the dynamic runtime optimizations of JIT.

**3. What is the difference between C1 and C2 compilers?**
Within the HotSpot JVM JIT compiler:
- **C1 (Client Compiler):** Compiles code quickly with light optimizations. Used for quick startup and methods that are moderately "hot".
- **C2 (Server Compiler):** Takes longer to compile but performs heavy, aggressive optimizations (like escape analysis, loop unrolling). Used for very "hot" methods where the compilation time investment pays off in long-term execution speed.

**4. What is tiered compilation?**
Tiered compilation is the strategy used by modern HotSpot JVMs to use both C1 and C2. Code starts by being interpreted (Tier 0). If it gets warm, it is compiled by C1 with basic profiling (Tiers 1-3) for quick performance gains. If it becomes very hot, it is recompiled by C2 (Tier 4) using the profiling data gathered by C1 to generate highly optimized machine code.

**5. Explain the class loading mechanism and delegation model.**
The ClassLoader subsystem is responsible for loading `.class` files into memory. It follows the **Delegation Hierarchy Principle**. When a class needs to be loaded, the current classloader first delegates the request to its parent classloader. This goes all the way up to the Bootstrap ClassLoader.
1. **Bootstrap ClassLoader:** Loads core Java classes (e.g., `java.lang.Object`) from the JRE `lib` directory.
2. **Extension (Platform) ClassLoader:** Loads extensions.
3. **Application (System) ClassLoader:** Loads user-defined classes from the application classpath.
If the Bootstrap cannot find it, the Extension tries, then the Application tries.

**6. What is the constant pool in a .class file?**
The constant pool is a table structure within a `.class` file that contains symbolic references to classes, interfaces, field names, and method names, as well as literal constant values (like strings or integers) used within that class. When the JVM executes bytecode, it uses the constant pool to resolve these symbolic references to actual memory addresses.

**7. What are the JVM memory areas? Explain each.**
- **Method Area (Metaspace):** Stores class structures, constant pools, method data, and constructors. (Shared across threads).
- **Heap:** Stores all object instances and arrays created via the `new` keyword. Managed by the Garbage Collector. (Shared across threads).
- **Stack (Thread Stack):** Stores local variables, method call frames, and partial results. Each thread has its own private stack.
- **PC Register:** Holds the address of the currently executing JVM instruction for a specific thread.
- **Native Method Stack:** Similar to the Java Stack, but for executing native code (e.g., C/C++ libraries via JNI).

**8. What is Metaspace and how does it differ from PermGen?**
Prior to Java 8, class metadata was stored in an area called **PermGen** (Permanent Generation), which had a fixed maximum size (leading to `OutOfMemoryError: PermGen space`).
In Java 8, PermGen was removed and replaced by **Metaspace**. Metaspace utilizes native system memory rather than the JVM heap. By default, Metaspace can auto-grow to use available system memory, heavily reducing class metadata OOM errors (though its maximum size can still be capped via JVM flags).

**9. What is the difference between ClassNotFoundException and NoClassDefFoundError?**
- `ClassNotFoundException`: A checked exception thrown when an application tries to load a class at runtime using `Class.forName()` or `ClassLoader.loadClass()`, but the class cannot be found in the classpath.
- `NoClassDefFoundError`: A fatal `Error` thrown when the JVM tries to link a class (usually because it was referenced directly in code like `new MyClass()`), and the class was present during compilation but is missing at runtime.

**10. Can you have multiple main methods in a Java application?**
Yes. You can have a `public static void main(String[] args)` method in every single class in your application. Which one gets executed depends entirely on which class name you pass to the `java` command line tool (e.g., `java ClassA` runs ClassA's main, `java ClassB` runs ClassB's main).

---

## ADVANCED QUESTIONS

**1. Explain the internal structure of a .class file.**
A `.class` file contains a strictly defined binary format. It begins with the magic number `0xCAFEBABE`, followed by minor and major version numbers indicating the JDK compiler version. Next is the Constant Pool, containing all literal values and symbolic references. This is followed by access flags (public, final, etc.), the `this_class` and `super_class` indexes, and an interfaces table. Finally, it contains the definitions of fields, methods (which contain the actual bytecode instructions in `Code` attributes), and class-level attributes.

**2. How does the JVM verify bytecode?**
The Bytecode Verifier is a crucial security component that runs during the class linking phase. It ensures that the bytecode does not violate JVM constraints. It checks that branches always point to valid locations, data is initialized before use, access modifiers are respected (e.g., not accessing a private field illegally), and the operand stack does not overflow or underflow. If verification fails, a `VerifyError` is thrown, preventing execution of malicious or corrupted code.

**3. What is On-Stack Replacement (OSR)?**
OSR is an advanced JIT compilation technique. Normally, JIT compiles a method and the next time the method is called, the compiled version is used. However, what if a method contains a long-running loop and takes minutes to finish? The JVM cannot wait for the method to exit to use the optimized version. OSR allows the JVM to pause execution, compile the loop into native code, swap out the execution frame on the call stack, and resume execution seamlessly using the newly compiled native code right in the middle of the method execution.

**4. How does the JVM decide when to JIT-compile a method?**
The JVM uses counters associated with methods. There is an Invocation Counter (how many times the method is called) and a Back-Edge Counter (how many times a loop iterates within the method). When the sum of these counters exceeds a specific threshold, the method is queued for JIT compilation. In tiered compilation, different thresholds trigger C1 vs. C2 compilation.

**5. What are the phases of the javac compiler?**
1. **Parse and Enter:** Reads source code, builds Abstract Syntax Trees (ASTs), and enters all symbols into the symbol table.
2. **Annotation Processing:** Executes any annotation processors attached to the build, which might generate new source files (forcing a loop back to phase 1).
3. **Semantic Analysis:** Performs type checking, flow analysis (e.g., checking that all paths return a value, variables are initialized), and syntactic sugar desugaring (like converting `foreach` loops into Iterator logic).
4. **Generate:** Converts the analyzed AST into JVM bytecode and writes it to `.class` files.

**6. Explain escape analysis and its optimization implications.**
Escape Analysis is an optimization performed by the C2 compiler. It analyzes whether an object created inside a method "escapes" the method's scope (e.g., by being returned, passed to another method, or assigned to a static field). If the compiler proves an object does *not* escape, it can perform **Scalar Replacement**: it deconstructs the object and allocates its individual fields directly on the Thread Stack or in CPU registers instead of the Heap. This drastically reduces Garbage Collection pressure, as the data is automatically cleaned up when the method frame pops.

**7. What is TLAB (Thread-Local Allocation Buffer)?**
Heap memory is shared across all threads. If multiple threads try to allocate objects simultaneously, they would need synchronization to avoid overwriting each other, causing massive contention. To solve this, the JVM assigns a small, private block of heap memory to each thread, called a TLAB. Threads allocate new objects exclusively within their own TLAB without requiring locks. Only when a thread's TLAB is full does it require synchronization to fetch a new TLAB block from the main heap.

**8. How does GraalVM differ from HotSpot JVM?**
HotSpot is the traditional JVM written in C++. GraalVM introduces a new JIT compiler ("Graal") written entirely in Java. GraalVM can be plugged into HotSpot via the JVM Compiler Interface (JVMCI). Furthermore, GraalVM offers the "Native Image" tool, which performs aggressive AOT compilation, performing static analysis to find all reachable code and compiling it into a standalone binary that does not require a JVM to run, offering sub-millisecond startup times.

**9. What is Ahead-of-Time (AOT) compilation in Java?**
While Java has historically relied on JIT, AOT compilation (primarily via GraalVM Native Image) compiles Java bytecode directly to OS-specific machine code at build time. The advantages are instant startup times and much lower memory usage, making it ideal for Serverless functions and microservices. The disadvantages are longer build times, reduced peak throughput (as it lacks runtime profiling data), and difficulties with dynamic Java features like Reflection and Dynamic Proxies, which must be explicitly configured at build time.

**10. Explain the Java Module System and its impact on class loading.**
Introduced in Java 9 (Project Jigsaw), the module system (`module-info.java`) provides strong encapsulation. Unlike the classpath, where everything is accessible, a module must explicitly `exports` packages for them to be visible, and must `requires` other modules it depends on. This impacts class loading: the JVM now knows exactly which module contains a class, preventing "JAR Hell" and allowing the JVM to construct a minimal runtime containing only the modules an application actually needs.

---

## TRICKY / GOTCHA QUESTIONS

**1. What is 0xCAFEBABE?**
It is the "magic number" that appears as the first 4 bytes of every valid compiled Java `.class` file. The JVM uses this to quickly verify that a file is indeed a Java class file before attempting to parse it.

**2. Can `main` method be `private`?**
It will compile fine. However, at runtime, the JVM will throw a `NoSuchMethodError: main` or say `Main method not found` because the JVM requires the entry point to be `public`.

**3. Can you run a Java program without `main`?**
Historically, yes (using static initialization blocks in a class loaded by a custom launcher or Applets), but in standard modern Java SE execution using the `java` command, no. The `main` method is mandatory.

**4. What happens if you compile with Java 21 but run on Java 17?**
The JVM will immediately throw an `UnsupportedClassVersionError`. You cannot run newer bytecode on an older runtime. (The reverse, however, works perfectly due to backward compatibility).

**5. Is `String[] args` or `String args[]` — both valid?**
Yes, both are syntactically valid in Java. `String[] args` is preferred as it clearly shows the type is an array of Strings, but C-style array declaration `String args[]` is supported.

**6. Can the main method return `int` instead of `void`?**
It will compile, but the JVM will not recognize it as the entry point. The JVM explicitly looks for a method returning `void`. If you need to exit with a specific status code, you must call `System.exit(int)`.

**7. What is the difference between `java` and `javaw`?**
On Windows, `java.exe` runs a console application and keeps a terminal window open. `javaw.exe` runs the application without a console window, which is used for launching GUI applications (like Swing or JavaFX) silently.

**8. Can you have a class named `main`?**
Yes. Java is case-sensitive. By convention classes should be capitalized (`Main`), but a class named `main` is perfectly legal.

**9. Why is `main` static?**
Because it must be invoked by the JVM before any objects of the class are instantiated. If it weren't static, the JVM would have to create an instance of the class first, requiring rules about which constructor to call.

**10. What is the `-server` vs `-client` JVM flag?**
Historically (32-bit JVMs), `-client` prioritized quick startup and small footprint (using C1 compiler), while `-server` prioritized peak performance (using C2 compiler). In modern 64-bit JVMs, `-server` is usually the default and implicit, and `-client` is largely ignored as tiered compilation handles both needs dynamically.

---

## MCQs

**1. Which component is responsible for converting bytecode to machine code at runtime?**
A) javac
B) JIT Compiler
C) ClassLoader
D) Bytecode Verifier
**Answer: B.** The JIT compiler translates bytecode to native machine code during execution.

**2. What is the default value of a local variable in Java?**
A) null
B) 0
C) Garbage value
D) It must be explicitly initialized
**Answer: D.** Local variables in Java do not get default values. The compiler will throw an error if you try to use an uninitialized local variable.

**3. Which of the following is true about the JVM?**
A) It is platform-independent.
B) It is platform-dependent.
C) It converts source code to bytecode.
D) It is an API.
**Answer: B.** While bytecode is platform-independent, the JVM itself is a native program written for specific hardware/OS (Windows JVM is different from Linux JVM).

**4. Where are object instances stored in JVM memory?**
A) Stack
B) Heap
C) Metaspace
D) PC Register
**Answer: B.** All objects and arrays are dynamically allocated on the Heap.

**5. Which exception is thrown when a required JAR file is missing from the classpath at runtime?**
A) NoClassDefFoundError
B) ClassNotFoundException
C) ClassCastException
D) MissingResourceException
**Answer: B.** Usually manifests as `ClassNotFoundException` when the classloader fails to locate the definition.

**6. What does `System.gc()` do?**
A) Immediately reclaims unused memory.
B) Throws an exception.
C) Suggests to the JVM that garbage collection should run.
D) Kills the application.
**Answer: C.** It is only a hint/suggestion. The JVM is free to ignore it.

**7. Which phase of the Java compiler checks for correct syntax?**
A) Semantic Analysis
B) Lexical Analysis
C) Parsing
D) Code Generation
**Answer: C.** Parsing builds the AST and verifies grammatical structure.

**8. What is the size of `boolean` in Java?**
A) 1 bit
B) 1 byte
C) 4 bytes
D) Not precisely defined
**Answer: D.** The JVM specification does not explicitly mandate the size of a boolean. In arrays, they are usually 1 byte, but as isolated fields, they may be padded to integer size (4 bytes) depending on the JVM implementation.

**9. Which of the following is NOT a valid entry point signature?**
A) `public static void main(String[] args)`
B) `public static void main(String... args)`
C) `static public void main(String args[])`
D) `public void static main(String[] args)`
**Answer: D.** `void` is the return type and must immediately precede the method name.

**10. What replaced PermGen in Java 8?**
A) Heap Space
B) Survivor Space
C) Metaspace
D) Code Cache
**Answer: C.** Metaspace utilizes native memory for class metadata.

**11. Which classloader loads the `java.lang.String` class?**
A) Application ClassLoader
B) Extension ClassLoader
C) Bootstrap ClassLoader
D) Custom ClassLoader
**Answer: C.** Core Java API classes are loaded by the Bootstrap ClassLoader.

**12. What optimization technique allocates objects on the thread stack instead of the heap?**
A) Loop Unrolling
B) Escape Analysis (Scalar Replacement)
C) Dead Code Elimination
D) Method Inlining
**Answer: B.** If an object doesn't escape the method, it can be decomposed onto the stack.

**13. A `.class` file starts with which magic number?**
A) `0xDEADBEEF`
B) `0xCAFEBABE`
C) `0xBAADF00D`
D) `0xFACEFEED`
**Answer: B.**

**14. What allows a running JVM to swap out a method currently executing a long loop with an optimized version?**
A) AOT Compilation
B) Class Reloading
C) On-Stack Replacement (OSR)
D) Garbage Collection
**Answer: C.**

**15. If a thread exhausts its call stack space (e.g., infinite recursion), what occurs?**
A) OutOfMemoryError
B) StackOverflowError
C) RuntimeException
D) The JVM crashes silently
**Answer: B.**

---

## OUTPUT PREDICTION

**Snippet 1**
```java
public class Test {
    public static void main(String[] args) {
        int x;
        System.out.println(x);
    }
}
```
**Output:** Compilation Error. Local variable `x` is not initialized before use.

**Snippet 2**
```java
public class Test {
    static int x;
    public static void main(String[] args) {
        System.out.println(x);
    }
}
```
**Output:** `0`. Static and instance fields are assigned default values.

**Snippet 3**
```java
public class Test {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
    public static void main(String args) {
        System.out.println("World");
    }
}
```
**Output:** `Hello`. The JVM specifically looks for the `main(String[])` array signature. Overloading the main method is legal, but the other versions won't be called by the JVM startup sequence.

**Snippet 4**
```java
public class Test {
    public static void main(String[] args) {
        System.out.println(args.length);
    }
}
// Run with: java Test
```
**Output:** `0`. An empty array is passed, not null.

**Snippet 5**
```java
public class test {
    public static void main(String[] args) {
        System.out.println("Valid");
    }
}
```
**Output:** `Valid`. Class names starting with lowercase are bad practice but perfectly legal syntax.

**Snippet 6**
```java
class A {
    public static void main(String[] args) {
        System.out.println("A");
    }
}
class B {
    public static void main(String[] args) {
        System.out.println("B");
    }
}
// Command: java B
```
**Output:** `B`.

**Snippet 7**
```java
public class Test {
    public static void main(String[] args) {
        main(args);
    }
}
```
**Output:** `Exception in thread "main" java.lang.StackOverflowError`. Infinite recursion.

**Snippet 8**
```java
public class Test {
    static {
        System.out.println("Static Block");
    }
    public static void main(String[] args) {
        System.out.println("Main Method");
    }
}
```
**Output:**
```text
Static Block
Main Method
```
Static initialization blocks run when the class is loaded by the ClassLoader, before the `main` method is executed.

**Snippet 9**
```java
public class Test {
    public static void Main(String[] args) {
        System.out.println("Started");
    }
}
```
**Output:** Runtime error: `Main method not found in class Test`. The method name `Main` is capitalized, Java expects lowercase `main`.

**Snippet 10**
```java
public class Test {
    public static final void main(String[] args) {
        System.out.println("Works");
    }
}
```
**Output:** `Works`. Adding `final` to `main` is perfectly legal, it just means the method cannot be overridden in a subclass (which usually doesn't matter for `main`).

---

## CODE TRACING

**Program 1: Recursion**
```java
public class Trace {
    public static void main(String[] args) {
        System.out.println(calc(3));
    }
    static int calc(int n) {
        if (n == 0) return 1;
        return n * calc(n - 1);
    }
}
```
**Trace:**
- `main` calls `calc(3)`
- `calc(3)` returns `3 * calc(2)`
- `calc(2)` returns `2 * calc(1)`
- `calc(1)` returns `1 * calc(0)`
- `calc(0)` returns `1`
- Unwinding: `calc(1) = 1*1 = 1` -> `calc(2) = 2*1 = 2` -> `calc(3) = 3*2 = 6`
**Output:** `6`

**Program 2: Execution Flow**
```java
public class Trace2 {
    static int val = 5;
    public static void main(String[] args) {
        int val = 10;
        System.out.println(val);
        System.out.println(Trace2.val);
    }
}
```
**Trace:**
- Class loaded, static field `val` initialized to 5.
- `main` starts, local variable `val` shadows the static field.
- `System.out.println(val)` prints the local variable (10).
- `System.out.println(Trace2.val)` explicitly accesses the static field (5).
**Output:**
```
10
5
```

**Program 3: Command Line Args**
```java
public class ArgsTrace {
    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.print(args[0]);
        }
        System.out.println("End");
    }
}
// Run with: java ArgsTrace Hello World
```
**Trace:**
- JVM passes `["Hello", "World"]` as `args`.
- `args.length` is 2. Condition is true.
- Prints `args[0]` which is "Hello" (using `print`, no newline).
- Prints "End" (with newline).
**Output:** `HelloEnd`

**Program 4: Stack Trace Reading**
```java
public class ExceptionTrace {
    public static void main(String[] args) {
        methodA();
    }
    static void methodA() {
        methodB();
    }
    static void methodB() {
        throw new RuntimeException("Boom");
    }
}
```
**Trace:**
- `main` calls `methodA`.
- `methodA` calls `methodB`.
- `methodB` throws Exception.
- Stack unwinds. The trace will read bottom-up in execution order, but prints top-down.
**Output:**
```
Exception in thread "main" java.lang.RuntimeException: Boom
    at ExceptionTrace.methodB(ExceptionTrace.java:9)
    at ExceptionTrace.methodA(ExceptionTrace.java:6)
    at ExceptionTrace.main(ExceptionTrace.java:3)
```

**Program 5: String array manipulation**
```java
public class ArrayTrace {
    public static void main(String[] args) {
        String[] data = new String[2];
        System.out.println(data[0]);
        data[0] = "Java";
        System.out.println(data[0]);
    }
}
```
**Trace:**
- Array of size 2 is created. Object arrays default initialize to `null`.
- First print outputs `null`.
- Index 0 is assigned reference to "Java" string.
- Second print outputs `Java`.
**Output:**
```
null
Java
```

---

## SCENARIO QUESTIONS

**Scenario 1:** You are deploying a Java application to a production Linux server. You compile it on your Mac, upload the `.class` files, and run `java MyApp`. You get `UnsupportedClassVersionError`. What happened and how do you fix it?
**Answer:** The JDK installed on your Mac is a newer version (e.g., Java 21) than the JRE installed on the Linux server (e.g., Java 17). Java bytecode is forward-compatible but not backward-compatible. You fix this by either upgrading the JRE on the Linux server to match your JDK, or configuring your Mac's compiler (`javac --release 17`) to target the older Java version.

**Scenario 2:** You start a Java program, and it immediately prints `Error: Could not find or load main class com.example.Main`. You are running the command from inside the `com/example` directory like this: `java Main`. Why is this failing?
**Answer:** When dealing with packages, you must run the `java` command from the root of the classpath (the directory containing the `com` folder), and you must provide the fully qualified class name. The correct command should be run from the parent directory of `com`: `java com.example.Main`.

**Scenario 3:** Your Java application runs fine, but after a few days, it crashes with `OutOfMemoryError: Java heap space`. As a beginner, what are the first two things you should check?
**Answer:** First, check if the application was started with sufficiently large heap memory flags (e.g., `-Xmx2G`). If it's using default limits, it might just need more memory. Second, if memory is adequate, this indicates a memory leak (e.g., keeping references to objects in a static `List` or `Map` forever without clearing them).

**Scenario 4:** You are debugging a program where a specific method takes 2 milliseconds to run the first 10,000 times, but suddenly starts taking 0.1 milliseconds to run. What JVM mechanism caused this?
**Answer:** This is the JIT (Just-In-Time) compiler in action. Initially, the JVM interpreted the bytecode (slower). Once the method was invoked enough times to hit a threshold, the JVM identified it as a "hot" method and compiled it into highly optimized native machine code. Subsequent executions use the fast native code.

**Scenario 5:** A colleague suggests using a `.jar` file they created. They send you the file, you put it in a folder, and run your app using `java -cp . MyProgram`. Your program crashes with `ClassNotFoundException` looking for a class inside their JAR. Why?
**Answer:** Adding a directory (like `.`) to the classpath only includes `.class` files directly inside that directory. To include a JAR file, you must explicitly list the JAR file itself in the classpath string. The correct command is `java -cp ".:their-library.jar" MyProgram` (on Mac/Linux) or `java -cp ".;their-library.jar" MyProgram` (on Windows).
