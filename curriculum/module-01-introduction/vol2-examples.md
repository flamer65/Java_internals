# Module 1 Volume 2: Examples

This document contains an extensive collection of practical examples to illustrate the concepts covered in Module 1: Introduction to Java. It targets Java 21 LTS and the IntelliJ IDEA environment.

---

## 1. SIMPLE EXAMPLES

### 1.1 Classic HelloWorld
The most fundamental Java program.
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```
**Explanation:**
- `public class HelloWorld`: Declares a public class. File must be named `HelloWorld.java`.
- `public static void main(String[] args)`: The entry point method signature required by the JVM.
- `System.out.println`: Standard output stream method that prints and appends a newline.

**What javac does:**
Reads `HelloWorld.java` and produces `HelloWorld.class`.

**What the JVM does:**
Loads `HelloWorld.class`, verifies the bytecode, initializes the class, and invokes the `main` method.

**Expected Output:**
```text
Hello, World!
```

**Bytecode Analysis (`javap -c HelloWorld`):**
```text
Compiled from "HelloWorld.java"
public class HelloWorld {
  public HelloWorld();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: getstatic     #7                  // Field java/lang/System.out:Ljava/io/PrintStream;
       3: ldc           #13                 // String Hello, World!
       5: invokevirtual #15                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       8: return
}
```
*   `0: getstatic`: Fetches the static `out` field from `System`.
*   `3: ldc`: Pushes the string literal "Hello, World!" onto the operand stack from the constant pool.
*   `5: invokevirtual`: Invokes the instance method `println`.

### 1.2 HelloWorld with package declaration
```java
package com.example.app;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello from a package!");
    }
}
```
**Explanation:** Places the class in the `com.example.app` namespace. Must reside in `com/example/app/HelloWorld.java`.
**Output:** `Hello from a package!`

### 1.3 Multiple System.out.println statements
```java
public class OutputDemo {
    public static void main(String[] args) {
        System.out.println("Line 1");
        System.out.print("Line 2");
        System.out.println(" (still line 2)");
    }
}
```
**Explanation:** Shows `println` (prints with newline) vs `print` (prints without newline).
**Output:**
```
Line 1
Line 2 (still line 2)
```

### 1.4 Command-line arguments
```java
public class ArgsDemo {
    public static void main(String[] args) {
        System.out.println("First arg: " + args[0]);
    }
}
```
**Explanation:** Accesses the `args` array populated by the JVM from shell arguments.
**Run:** `java ArgsDemo foo`
**Output:** `First arg: foo`

### 1.5 Comments
```java
/**
 * Javadoc comment: Documents the class for external HTML generation.
 */
public class CommentsDemo {
    public static void main(String[] args) {
        // Single line comment
        System.out.println("Comments"); /* Multi-line
                                           comment */
    }
}
```

### 1.6 Special Characters and Escapes
```java
public class EscapeDemo {
    public static void main(String[] args) {
        System.out.println("Tab\tSeparated\nNew Line");
        System.out.println("Quotes: \"Hello\"");
    }
}
```

---

## 2. INTERMEDIATE EXAMPLES

### 2.1 Multiple classes in same file
```java
public class MultiClass {
    public static void main(String[] args) {
        Helper h = new Helper();
        h.sayHi();
    }
}
class Helper {
    void sayHi() { System.out.println("Hi"); }
}
```
**Compilation:** `javac MultiClass.java` produces `MultiClass.class` and `Helper.class`. The `Helper` class is package-private.

### 2.2 Multi-file program
*ClassA.java*
```java
public class ClassA {
    public static void main(String[] args) { ClassB.sayHello(); }
}
```
*ClassB.java*
```java
public class ClassB {
    public static void sayHello() { System.out.println("Hello from B"); }
}
```
**Compilation:** `javac ClassA.java ClassB.java`. Class A depends on Class B.

### 2.3 Java command variations (JEP 330)
- `java HelloWorld` - Runs a compiled `.class` file.
- `java -cp . HelloWorld` - Specifies the classpath explicitly.
- `java HelloWorld.java` - Runs directly from source (compiles in memory and executes).

### 2.4 JVM Information
*(See JVMInfo.java)* Uses `Runtime.version()` and `System.getProperty()` to fetch memory and OS metadata.

### 2.5 Processing String[] args
*(See CommandLineArgs.java)* Iterates over array elements cleanly checking `args.length`.

### 2.6 Static initializer block
*(See StaticInitDemo.java)* Executes strictly at class initialization time, before `main`.

---

## 3. ADVANCED EXAMPLES

### 3.1 Examining Constant Pool
```java
public class ConstantPoolDemo {
    public static final String MSG = "Hello";
}
```
Run `javap -v ConstantPoolDemo.class`.
It shows the Constant Pool section which stores literals, field references, and method references. E.g., `#1 = Methodref`, `#7 = String`.

### 3.2 Packages & Directories
Project structure:
```
src/
  com/
    acme/
      App.java
```
`javac -d out src/com/acme/App.java` puts compiled classes in `out/com/acme/App.class`.
Run with: `java -cp out com.acme.App`

### 3.3 Deliberate Class Loading
*(See ClassLoadingDemo.java)* Using `Class.forName("ClassName")` to invoke static initialization on demand.

---

## 4. PRODUCTION EXAMPLES

### 4.1 Real Project Structure
Maven/Gradle style structure: `src/main/java/com/example/Main.java`. Uses strict packaging, access modifiers, and clear dependency management.

### 4.2 Javadoc Generation
Run `javadoc -d docs src/module01/examples/HelloWorld.java`. This parses `/** ... */` blocks into browsable HTML.

---

## 5. WRONG EXAMPLES — COMPILER ERRORS

1. **Missing Semicolon**
   ```java
   int x = 5
   ```
   **Error:** `error: ';' expected`
   **Fix:** Add `;`.

2. **Missing Main Method**
   If compiled as entry point, it compiles, but gives *runtime* error. To force compiler error, use syntax errors.
   
3. **Wrong Main Signature**
   ```java
   public void main(String[] args) {} // missing static
   ```
   *Compiles*, but JVM rejects it at runtime.

4. **Filename Mismatch**
   File `Test.java`: `public class Hello {}`
   **Error:** `class Hello is public, should be declared in a file named Hello.java`
   **Fix:** Rename file or remove `public`.

5. **Two Public Classes**
   File `Test.java`: `public class A {} public class B {}`
   **Error:** `class B is public, should be declared in a file named B.java`
   **Fix:** Make one package-private.

6. **Missing Closing Brace**
   ```java
   public class A {
   ```
   **Error:** `reached end of file while parsing`
   **Fix:** Add `}`.

7. **Reserved Keyword**
   ```java
   int class = 5;
   ```
   **Error:** `not a statement` or `<identifier> expected`
   **Fix:** Rename to `clazz` or `myClass`.

8. **Missing Return Type**
   ```java
   public doWork() {}
   ```
   **Error:** `invalid method declaration; return type required`
   **Fix:** Add `void`.

9. **Import Wrong Package**
   ```java
   import java.util.ScannerNotFound;
   ```
   **Error:** `cannot find symbol class ScannerNotFound`
   **Fix:** Import `java.util.Scanner`.

10. **Lowercase Class Name**
    ```java
    public class hello {}
    ```
    *Compiles fine*, but violates standard naming conventions (PascalCase).

11. **Typo in API Method**
    ```java
    System.out.Println("hi");
    ```
    **Error:** `cannot find symbol method Println(String)`
    **Fix:** Use `println` (lowercase p).

---

## 6. WRONG EXAMPLES — RUNTIME ERRORS

1. **NoClassDefFoundError**
   Running `java App` when App is in package `com.app`. Must run `java com.app.App`.
2. **Main Class Not Found**
   Running in wrong directory without `-cp` configured.
3. **Including .class**
   `java App.class` -> JVM looks for class `class` inside package `App`. Error: `Could not find or load main class App.class`.
4. **ArrayIndexOutOfBoundsException**
   Accessing `args[1]` when run with no arguments.
5. **StackOverflowError**
   ```java
   public static void main(String[] args) { main(args); }
   ```
   Infinite recursion blows the thread stack.

---

## 7. EDGE CASES

1. **Empty Main Method**: `public static void main(String[] args) {}` — Compiles and exits instantly with exit code 0.
2. **Varargs**: `public static void main(String... args)` — Perfectly valid entry point signature.
3. **Modifiers**: `public final static strictfp void main(String[] args)` — Valid; `strictfp` ensures consistent floating point ops.
4. **Shadowing core classes**: `class String {}` — Will break your code heavily as local `String` takes precedence over `java.lang.String`.
5. **Unicode identifiers**: `int \u0394 = 5;` (delta) — Java fully supports Unicode variable names.
6. **JEP 330**: Running `java --source 21 App.java` runs without an explicit `.class` footprint.

---

## 8. PERFORMANCE EXAMPLES

1. **Startup Time**: Use `System.nanoTime()` at the very first line of `main` to gauge basic JVM bootstrap overhead.
2. **JIT Warmup**: *(See JITWarmupDemo.java)* Execution in loops gets significantly faster after C1/C2 compilation tiers kick in.

---

## 9. INTERVIEW EXAMPLES

1. **What happens on `java App`?**
   OS spawns JVM -> JVM finds classpath -> App class loaded -> Bytecode verified -> Statics initialized -> `main` thread started -> `main(String[])` invoked.
2. **Multiple main methods in one project?**
   Perfectly fine. The `java` command requires the specific class name to invoke its specific main method.
3. **Overloading main?**
   *(See MainOverloaded.java)* Yes, you can overload it, but the JVM *always* looks for the `String[]` (or `String...`) signature.
