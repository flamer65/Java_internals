# Module 1 Volume 3: Best Practices, Common Mistakes, and Debugging

This volume covers the practical aspects of writing Java code, avoiding pitfalls, and diagnosing issues when they occur.

## PART 6: BEST PRACTICES

### Project Organization

Organizing your Java project correctly from the start is critical for maintainability and collaboration.

#### Standard Java Project Structure
Modern Java projects almost universally follow the standard directory layout popularized by Maven and Gradle.
- `src/main/java`: This directory contains all your application's source code (`.java` files).
- `src/main/resources`: This is for non-code resources like configuration files (`application.properties`, `.xml`), images, or text files that need to be packaged with your application.
- `src/test/java`: This directory mirrors `src/main/java` but contains your unit tests and integration tests.
- `src/test/resources`: Test-specific configuration files or test data files.

#### IntelliJ IDEA Project Setup
When creating a new Java project in IntelliJ IDEA:
1. Use **File > New > Project**.
2. Select **Java**.
3. Choose the appropriate **Project SDK** (e.g., Java 21). If not present, you can download it directly through IntelliJ.
4. If using a build system (recommended), select Maven or Gradle.
5. In **Project Structure (Cmd+; on Mac / Ctrl+Alt+Shift+S on Windows)**, ensure the Project language level matches your JDK (e.g., SDK: 21, Language Level: 21).

#### Module Structure in IntelliJ
IntelliJ uses the concept of "Modules" (not to be confused with Java 9 JPMS modules) to represent discrete units of functionality that can be compiled, run, and tested independently. A large project might have multiple IntelliJ modules, each with its own `src/main/java` structure.

#### Package Organization Best Practices
Packages prevent naming conflicts and organize related classes.
- **By Feature (Recommended):** `com.company.app.billing`, `com.company.app.users`. All classes related to a feature are together.
- **By Layer (Anti-pattern for large apps):** `com.company.app.controllers`, `com.company.app.services`. This spreads feature code across the entire codebase.

#### .gitignore for Java Projects
Never commit compiled code or IDE-specific settings to version control. A standard `.gitignore` should include:
- `.idea/` (IntelliJ project settings)
- `*.iml` (IntelliJ module files)
- `*.class` (Compiled bytecode)
- `target/` (Maven build output)
- `build/` (Gradle build output)
- `out/` (IntelliJ default compile output)
- `*.jar`, `*.war` (Packaged artifacts)

### Naming Conventions

Java relies heavily on strict naming conventions to ensure code readability.

| Element | Convention | Example (Good) | Example (Bad) |
| :--- | :--- | :--- | :--- |
| Classes | PascalCase | `CustomerOrder` | `customerOrder`, `customer_order` |
| Interfaces | PascalCase | `Runnable`, `List` | `runnable`, `IList` |
| Methods | camelCase | `calculateTotal()` | `CalculateTotal()`, `calculate_total()` |
| Variables | camelCase | `firstName` | `FirstName`, `first_name` |
| Constants | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT` | `maxRetryCount`, `MaxRetryCount` |
| Packages | lowercase, reverse domain | `com.company.billing` | `com.Company.Billing`, `com.company.Billing` |

### Code Style

Consistency in code style is more important than the specific style chosen.

- **Indentation:** Use 4 spaces per indentation level. **Do not use tabs.** This is the standard Java convention.
- **Brace Style:** Use K&R style (specifically, "One True Brace Style" variant in Java) where the opening brace is on the same line as the declaration.
  ```java
  public void doSomething() {
      if (condition) {
          // code
      }
  }
  ```
- **Line Length:** Aim for a maximum of 80-120 characters per line to ensure code is readable without horizontal scrolling.
- **Import Organization:** Avoid wildcard imports (`import java.util.*;`). Explicitly import classes (`import java.util.List;`). IntelliJ will manage this automatically.
- **Javadoc:** Use Javadoc (`/** ... */`) on all public APIs (classes, methods) to generate standard documentation.
- **Style Guides:** Reference the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) as an industry standard.
- **IntelliJ Formatting:** Use `Cmd+Alt+L` (Mac) or `Ctrl+Alt+L` (Windows/Linux) to auto-format code.

### JDK Selection Best Practices

- **Production Version:** Always target a Long-Term Support (LTS) version for production environments. Currently, Java 21 is the standard LTS, with Java 17 and 11 as older LTS releases.
- **When to Upgrade:** Upgrade to the next LTS when it has been available long enough for the ecosystem (frameworks, libraries, build tools) to fully support it (usually 6-12 months after release).

#### JDK Distribution Comparison

| Distribution | Vendor | Characteristics |
| :--- | :--- | :--- |
| **Oracle JDK** | Oracle | Commercial, paid for production use. Free for dev/testing. |
| **Eclipse Temurin** | Adoptium | Free, open-source, highly recommended standard build of OpenJDK. |
| **Amazon Corretto** | Amazon | Free, open-source, includes Amazon's patches (used heavily in AWS). |
| **Azul Zulu** | Azul Systems | Free, open-source, offers very broad platform support (e.g., older OS versions). |
| **GraalVM** | Oracle / Community | Includes a high-performance JIT compiler and Native Image capability (AOT). |

- **JAVA_HOME and PATH:** Ensure your `JAVA_HOME` environment variable points to your JDK installation directory, and `$JAVA_HOME/bin` is added to your system `PATH`.

### Build Tool Best Practices

- **Never `javac` in Production:** Manually compiling and linking files with `javac` is only for learning. Real projects require dependency resolution, test execution, and packaging.
- **Always use Maven or Gradle:** Start using a build tool immediately for anything beyond a single-file script. They provide a standardized project structure and lifecycle.
- **Dependency Management:** Declare all third-party libraries (like testing frameworks or logging libraries) in your `pom.xml` (Maven) or `build.gradle` (Gradle) rather than downloading JAR files manually.

---

## PART 7: COMMON MISTAKES

### Beginner Mistakes

1. **Forgetting semicolons**
   Java requires a semicolon at the end of every statement.
   ```java
   // Bad
   System.out.println("Hello")
   // Good
   System.out.println("Hello");
   ```

2. **Mismatched braces**
   Every `{` must have a corresponding `}`.
   ```java
   public class Main {
       public static void main(String[] args) {
           System.out.println("Missing brace");
   // Missing closing brace for class
   ```

3. **Wrong filename**
   If a class is `public`, the filename must exactly match the class name, including case.
   ```java
   // In file MainApp.java
   public class App { // ERROR: Class 'App' is public, should be declared in a file named 'App.java'
   }
   ```

4. **Running `java HelloWorld.class` instead of `java HelloWorld`**
   The `java` command takes the fully qualified class name, not the file name.
   ```bash
   # Bad
   java HelloWorld.class
   # Good
   java HelloWorld
   ```

5. **Not setting JAVA_HOME or PATH correctly**
   Results in `'java' is not recognized as an internal or external command` (Windows) or `command not found: java` (Mac/Linux).

6. **Confusing JDK, JRE, JVM**
   - JVM: Executes bytecode.
   - JRE: JVM + standard libraries (enough to run programs).
   - JDK: JRE + compiler (`javac`) + development tools (enough to write programs).

7. **Editing .class files instead of .java files**
   `.class` files are compiled binary bytecode. You cannot edit them directly in a text editor. Always edit `.java` and recompile.

8. **Not understanding case sensitivity**
   `String` is not `string`. `System` is not `system`.

9. **Putting code outside of methods**
   Logic (like loops, print statements) must go inside a method, not directly in the class body.
   ```java
   public class Main {
       System.out.println("Hello"); // ERROR
       public static void main(String[] args) {}
   }
   ```

10. **Forgetting `public static void main(String[] args)` signature**
    If it's not exact, the JVM won't find the entry point.
    ```java
    // Fails to run
    public void main(String[] args) { ... } // Missing static
    ```

### Intermediate Mistakes

1. **Not understanding classpath**
   The classpath tells the JVM where to look for user-defined classes and packages. Failing to include required JARs or directories leads to `ClassNotFoundException`.

2. **Package declaration doesn't match directory structure**
   If a file says `package com.app;`, it MUST reside in a directory `com/app/`.

3. **Mixing JDK versions in a project**
   Compiling a library with Java 21 and trying to use it in a Java 17 project will cause an `UnsupportedClassVersionError`.

4. **Not using an IDE**
   Trying to write complex Java in Notepad leads to missing syntax errors until compile-time and drastically slows down refactoring.

5. **Ignoring compiler warnings**
   Warnings (like unchecked casts or deprecated API usage) often indicate potential runtime bugs.

6. **Not understanding compilation vs execution errors**
   Compilation errors (syntax errors) prevent bytecode generation. Execution errors (Exceptions) occur while the JVM is running the bytecode.

7. **Wrong import**
   Importing `java.sql.Date` instead of `java.util.Date` will cause type mismatch errors.

### Senior-Level Pitfalls

1. **Not tuning JVM flags for production**
   Running a heavy backend without setting `-Xms` and `-Xmx` (heap size) or selecting an appropriate Garbage Collector (e.g., G1GC or ZGC) can lead to OutOfMemoryErrors or massive GC pauses.

2. **Ignoring JVM warmup time for benchmarking**
   The JIT compiler takes time to optimize code. Benchmarking Java code immediately upon startup measures interpreted speed, not optimized speed. (Use JMH for benchmarking).

3. **Not understanding class loading implications in modular applications**
   In OSGi, Tomcat, or custom plugin systems, having multiple classloaders can lead to bizarre errors where `ClassA != ClassA` if loaded by different classloaders.

4. **Over-relying on JIT optimizations**
   Assuming the JIT will perfectly inline everything or eliminate all allocations without profiling the actual running system.

5. **Not monitoring JVM metrics in production**
   Failing to hook up JMX, Prometheus, or APM tools to monitor Heap usage, GC pauses, and thread counts.

---

## PART 8: DEBUGGING

### IntelliJ IDEA Debugging

Debugging is the process of inspecting your code as it executes. IntelliJ IDEA provides a world-class debugger.

- **Setting breakpoints:** Click in the grey gutter next to a line number. A red dot appears. Execution will pause *before* executing this line.
- **Running in Debug mode:** Click the green Bug icon, or press `Shift+F9`.
- **Step Over (F8):** Executes the current line and moves to the next line in the current method. If the current line contains a method call, it executes the entire method without diving into its source code.
- **Step Into (F7):** Moves into the method called on the current line to debug it line-by-line.
- **Step Out (Shift+F8):** Executes the rest of the current method and pauses at the caller's next line.
- **Evaluate Expression (Alt+F8):** Opens a dialog where you can execute arbitrary Java code in the context of the paused application (e.g., `user.getName().toUpperCase()`).
- **Variables panel:** Shows the current values of all local variables and object fields in scope.
- **Watches panel:** Allows you to define specific expressions that are constantly evaluated and displayed as you step through the code.
- **Frames panel (call stack):** Shows the chain of method calls that led to the current breakpoint. You can click on previous frames to inspect the state of variables higher up the stack.
- **Conditional breakpoints:** Right-click a breakpoint and set a condition (e.g., `i == 500`). Execution only pauses if the condition is true.
- **Exception breakpoints:** Go to Run > View Breakpoints. You can set the debugger to pause automatically whenever a specific exception (like `NullPointerException`) is thrown anywhere in the code.
- **Hot-reloading code:** You can often change code, recompile (Cmd+F9), and the debugger will "Hot Swap" the new bytecode into the running JVM without restarting (though structural changes like adding methods usually require a restart).

### Common Stack Traces for Module 1

#### 1. ClassNotFoundException
```
java.lang.ClassNotFoundException: com.mysql.cj.jdbc.Driver
    at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:641)
    ...
```
- **Causes:** You are trying to load a class dynamically (e.g., via `Class.forName()`), but the class is not present on the classpath.
- **Fix:** Ensure the JAR containing the class is added to your project's dependencies or classpath.

#### 2. NoClassDefFoundError
```
Exception in thread "main" java.lang.NoClassDefFoundError: org/slf4j/LoggerFactory
    at com.myapp.MyService.<init>(MyService.java:15)
    ...
Caused by: java.lang.ClassNotFoundException: org.slf4j.LoggerFactory
```
- **Causes:** The class was present during *compilation* (so `javac` succeeded), but it is missing at *runtime* when the JVM tries to link the class.
- **Fix:** Ensure all dependencies used during compile-time are also packaged/available in the runtime classpath.

#### 3. NoSuchMethodError
```
Exception in thread "main" java.lang.NoSuchMethodError: 'void org.apache.commons.lang3.StringUtils.isBlank(java.lang.String)'
```
- **Causes:** You compiled your code against version X of a library, which had a specific method. At runtime, version Y of the library is loaded, and that method no longer exists (or its signature changed).
- **Fix:** Resolve dependency conflicts to ensure the runtime version matches the compile-time version.

#### 4. UnsupportedClassVersionError
```
Exception in thread "main" java.lang.UnsupportedClassVersionError: com/myapp/Main has been compiled by a more recent version of the Java Runtime (class file version 65.0), this version of the Java Runtime only recognizes class file versions up to 61.0
```
- **Causes:** You compiled the code with a newer JDK (e.g., Java 21 = version 65) but are trying to run it with an older JRE (e.g., Java 17 = version 61).
- **Fix:** Run the code with a JRE that is equal to or newer than the JDK used to compile it.

#### 5. StackOverflowError
```
Exception in thread "main" java.lang.StackOverflowError
    at com.myapp.Recursion.factorial(Recursion.java:5)
    at com.myapp.Recursion.factorial(Recursion.java:5)
    at com.myapp.Recursion.factorial(Recursion.java:5)
    ...
```
- **Causes:** Infinite recursion. A method calls itself repeatedly without a base case to stop it, exhausting the thread's call stack memory.
- **Fix:** Identify the recursive method and ensure it has a correct terminating condition.

### Logging Basics

- **`System.out.println`:** Good for absolute beginners to see what a program is doing.
- **Why it's bad in production:** `System.out` is synchronous, blocks the thread, cannot be easily redirected, lacks severity levels (INFO, ERROR), and cannot be dynamically enabled/disabled.
- **java.util.logging (JUL):** The built-in Java logging framework. Rarely used in modern applications due to clunky configuration.
- **SLF4J + Logback:** The industry standard. SLF4J is the interface, and Logback is the underlying engine that writes to files, rolls them daily, and formats the output. (Covered extensively in Module 35).

### JVM Inspection

Command-line tools are vital for understanding the environment.

- `java -version`: Shows the runtime version (JRE/JVM).
- `javac -version`: Shows the compiler version (JDK).
- `java -showversion <ClassName>`: Prints the version and then runs the program.
- `java -XshowSettings:all -version`: Displays detailed internal JVM settings (locale, OS properties, default memory limits).
- `javap -v ClassName`: Disassembles a `.class` file to show the readable bytecode instructions, constant pool, and local variable tables.
- `java -verbose:class ClassName`: Prints a log of every class loaded by the JVM and where it was loaded from. Crucial for debugging ClassNotFound issues.
