# Module 1 Volume 5: Practice Exercises

## SMALL EXERCISES (10)
1. Write a `HelloWorld` program, compile it from the command line, and run it.
2. Write a program that prints your name, age, and favorite programming language on separate lines.
3. Write a program that accepts 3 command-line arguments and prints them in reverse order.
4. Create two classes in the same file and have one reference the other.
5. Write a program with all three types of comments (single-line, multi-line, Javadoc).
6. Use `javap -c` to disassemble your HelloWorld and identify 3 bytecode instructions.
7. Write a program that prints the Java version and JVM name using `System.getProperty()`.
8. Create a program with a package declaration, compile with `-d`, and run with `-cp`.
9. Write a program that prints the exact string: `She said "Hello, World!"`
10. Create an empty main method program, compile and run it — verify nothing prints.

## MEDIUM EXERCISES (10)
1. Create a multi-file project: `Greeter.java` has a `greet(String name)` method, `Main.java` calls it.
2. Write a program that catches `ArrayIndexOutOfBoundsException` when accessing `args` incorrectly.
3. Create a project with the package structure `com.myapp.core.Main`.
4. Write a program that measures its own startup time using `System.nanoTime()`.
5. Use `javap -v` on a compiled class and identify the magic number, Java version, constant pool size, super class.
6. Write a program with a static initializer block that prints 'Initializing...' before main runs.
7. Create a program that demonstrates both compilation errors and runtime errors (two separate files).
8. Write a program with multiple overloaded main methods.
9. Set up a proper IntelliJ IDEA project with src folder, create a Java class, run it, and debug it with a breakpoint.
10. Write a program that uses `ProcessBuilder` to run `javap -c` on itself.

## HARD EXERCISES (5)
1. Write a custom `ClassLoader` that loads a class from a byte array.
2. Create a build script that compiles a multi-package Java project, creates a JAR file, and runs it.
3. Write a program that uses reflection to find and invoke the main method of another class by name.
4. Investigate JIT compilation with `-XX:+PrintCompilation` flag.
5. Create a modular Java project with two modules.

## PRODUCTION PROBLEMS (3)
1. Diagnose `UnsupportedClassVersionError` from a JAR file.
2. Debug `ClassNotFoundException` on CI server.
3. Set up a professional Java 21 project in IntelliJ IDEA.

## REFACTORING TASKS (3)
1. **Unclear Variables:**
   ```java
   public class Calc {
       public static void main(String[] args) {
           int a = 10; int b = 5;
           int c = a + b;
           System.out.println(c);
       }
   }
   ```
   *Exercise:* Refactor the above code to use descriptive variable names and proper indentation.

2. **Spaghetti Code:**
   ```java
   public class App {
       public static void main(String[] args) {
           if (args.length > 0) {
               if (args[0].equals("run")) {
                   System.out.println("Running");
               }
           }
       }
   }
   ```
   *Exercise:* Refactor to reduce nesting using early returns.

3. **Magic Numbers:**
   ```java
   public class Circle {
       public static void main(String[] args) {
           double r = 5.0;
           System.out.println(3.14159 * r * r);
       }
   }
   ```
   *Exercise:* Refactor to use a constant for PI.

## DEBUGGING TASKS (3)
1. **Missing Semicolon:**
   ```java
   public class Bug1 {
       public static void main(String[] args) {
           System.out.println("Hello")
       }
   }
   ```
   *Exercise:* Find and fix the compilation error.

2. **Incorrect String Comparison:**
   ```java
   public class Bug2 {
       public static void main(String[] args) {
           String a = new String("test");
           if (a == "test") {
               System.out.println("Match!");
           }
       }
   }
   ```
   *Exercise:* Fix the bug so that it correctly prints "Match!".

3. **Array Out of Bounds:**
   ```java
   public class Bug3 {
       public static void main(String[] args) {
           int[] arr = {1, 2, 3};
           for (int i = 0; i <= arr.length; i++) {
               System.out.println(arr[i]);
           }
       }
   }
   ```
   *Exercise:* Fix the loop boundary to prevent a runtime exception.

## CODE COMPLETION (3)
1. **Complete the Loop:**
   ```java
   public class Loop {
       public static void main(String[] args) {
           for (int i = 0; ______; i++) {
               System.out.println(i); // Should print 0 to 4
           }
       }
   }
   ```
   *Exercise:* Fill in the blank.

2. **Complete the Method:**
   ```java
   public class MethodCall {
       public static void main(String[] args) {
           ______("Hello from method!");
       }
       public static void printMsg(String msg) {
           System.out.println(msg);
       }
   }
   ```
   *Exercise:* Fill in the blank to call `printMsg`.

3. **Complete the Array:**
   ```java
   public class ArraysEx {
       public static void main(String[] args) {
           int[] numbers = ______ {1, 2, 3};
           System.out.println(numbers[0]);
       }
   }
   ```
   *Exercise:* Fill in the blank to initialize the array properly.

## OUTPUT PREDICTION (5)
1. **Pre-increment:**
   ```java
   int x = 5;
   System.out.println(++x);
   ```
   *Exercise:* Predict the output.

2. **String Concatenation:**
   ```java
   System.out.println("5" + 5 + 5);
   ```
   *Exercise:* Predict the output.

3. **Math precedence:**
   ```java
   System.out.println(10 + 5 * 2);
   ```
   *Exercise:* Predict the output.

4. **Boolean Logic:**
   ```java
   boolean a = true, b = false;
   System.out.println(a || b && false);
   ```
   *Exercise:* Predict the output.

5. **Char Arithmetic:**
   ```java
   char c = 'A';
   System.out.println((char)(c + 1));
   ```
   *Exercise:* Predict the output.

## ERROR FINDING (3)
1. **Class Name Mismatch:**
   ```java
   // File: Main.java
   public class MyProgram {
       public static void main(String[] args) {}
   }
   ```
   *Exercise:* What is the error here and how do you fix it?

2. **Static Context:**
   ```java
   public class Test {
       int x = 10;
       public static void main(String[] args) {
           System.out.println(x);
       }
   }
   ```
   *Exercise:* What is the error here?

3. **Missing Main Array:**
   ```java
   public class App {
       public static void main(String args) {}
   }
   ```
   *Exercise:* Why will the JVM fail to run this class?

## OPTIMIZATION PROBLEMS (2)
1. **String concatenation in a loop:**
   ```java
   public class Opt1 {
       public static void main(String[] args) {
           String result = "";
           for(int i=0; i<10000; i++) {
               result += i;
           }
       }
   }
   ```
   *Exercise:* Optimize this code to run significantly faster.

2. **Redundant calculations:**
   ```java
   public class Opt2 {
       public static void main(String[] args) {
           int[] data = new int[1000];
           for (int i = 0; i < data.length; i++) {
               data[i] = i * Math.max(50, 100);
           }
       }
   }
   ```
   *Exercise:* Optimize this loop to avoid redundant operations.
