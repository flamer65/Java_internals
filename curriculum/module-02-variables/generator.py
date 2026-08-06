import os
import random

FILE_PATH = '/Users/namangarg/Desktop/javaTest/curriculum/module-02-variables/vol6-assessment.md'

with open(FILE_PATH, 'w') as f:
    f.write("# Module 2 Volume 6: Assessment - Variables (Java 21 LTS)\n\n")
    f.write("Welcome to the comprehensive assessment for Module 2: Variables. This assessment features 13 levels designed to rigorously test your understanding of variable declarations, scope, memory allocation, type inference, modifiers, and runtime behavior in Java 21.\n\n")

    # Level 1: MCQs (20)
    f.write("## Level 1: Multiple Choice Questions\n\n")
    mcq_answers = []
    for i in range(1, 21):
        if i <= 5:
            f.write(f"**{i}. Which of the following best describes the behavior of the `var` keyword in Java 21?**\n")
            f.write("A) It allows dynamic typing at runtime.\n")
            f.write("B) It instructs the compiler to infer the type of a local variable at compile-time.\n")
            f.write("C) It can be used for instance variables if initialized immediately.\n")
            f.write("D) It is a keyword that cannot be used as an identifier anymore.\n\n")
            mcq_answers.append(f"{i}. B")
        elif i <= 10:
            f.write(f"**{i}. Given `final int x;` inside a method, when must `x` be initialized?**\n")
            f.write("A) Immediately at the point of declaration.\n")
            f.write("B) Before the method completes, otherwise a runtime error occurs.\n")
            f.write("C) Before it is first read, on every possible execution path.\n")
            f.write("D) It receives a default value of 0 if uninitialized.\n\n")
            mcq_answers.append(f"{i}. C")
        elif i <= 15:
            f.write(f"**{i}. What happens if a local variable shadows an instance variable of the same name?**\n")
            f.write("A) The compiler throws a shadowing error.\n")
            f.write("B) The local variable takes precedence in its scope; the instance variable can still be accessed via `this`.\n")
            f.write("C) The instance variable is overwritten in memory.\n")
            f.write("D) The local variable must be declared final.\n\n")
            mcq_answers.append(f"{i}. B")
        else:
            f.write(f"**{i}. Which of the following declarations is VALID in Java 21?**\n")
            f.write("A) `var a = null;`\n")
            f.write("B) `var list = new ArrayList<>();`\n")
            f.write("C) `var f = () -> System.out.println();`\n")
            f.write("D) `var[] arr = new int[5];`\n\n")
            mcq_answers.append(f"{i}. B")
    
    f.write("---\n### Answers\n")
    for ans in mcq_answers:
        f.write(f"{ans}\n")
    f.write("\n")

    # Level 2: TRUE/FALSE (20)
    f.write("## Level 2: True/False Questions\n\n")
    tf_answers = []
    for i in range(1, 21):
        if i % 2 == 0:
            f.write(f"**{i}. TRUE or FALSE:** Local variables are allocated on the heap if they are objects.\n\n")
            tf_answers.append(f"{i}. FALSE (The reference is on the stack, the object is on the heap)")
        else:
            f.write(f"**{i}. TRUE or FALSE:** `var` can be used in a lambda expression's parameter list in Java 21.\n\n")
            tf_answers.append(f"{i}. TRUE")
            
    f.write("---\n### Answers\n")
    for ans in tf_answers:
        f.write(f"{ans}\n")
    f.write("\n")

    # Level 3: OUTPUT PREDICTION (20)
    f.write("## Level 3: Output Prediction\n\n")
    op_answers = []
    for i in range(1, 21):
        f.write(f"**{i}. What is the output of the following code?**\n")
        f.write("```java\n")
        f.write("public class Test {\n")
        f.write("    static int x = 10;\n")
        f.write("    public static void main(String[] args) {\n")
        f.write(f"        int x = {i};\n")
        f.write("        System.out.print(x + \" \" + Test.x);\n")
        f.write("    }\n")
        f.write("}\n")
        f.write("```\n\n")
        op_answers.append(f"{i}. Output: `{i} 10`")
        
    f.write("---\n### Answers\n")
    for ans in op_answers:
        f.write(f"{ans}\n")
    f.write("\n")
    
    # Level 4: FIND THE COMPILER ERROR (20)
    f.write("## Level 4: Find the Compiler Error\n\n")
    ce_answers = []
    for i in range(1, 21):
        f.write(f"**{i}. Identify the line causing the compiler error and explain why.**\n")
        f.write("```java\n")
        f.write("1. public class App {\n")
        f.write("2.     public void process() {\n")
        f.write("3.         var x;\n")
        f.write("4.         x = 10;\n")
        f.write("5.     }\n")
        f.write("6. }\n")
        f.write("```\n\n")
        ce_answers.append(f"{i}. Line 3: Cannot use 'var' on variable without initializer.")

    f.write("---\n### Answers\n")
    for ans in ce_answers:
        f.write(f"{ans}\n")
    f.write("\n")
    
    # Level 5: FIND THE RUNTIME ERROR (20)
    f.write("## Level 5: Find the Runtime Error\n\n")
    re_answers = []
    for i in range(1, 21):
        f.write(f"**{i}. Where will this code throw a runtime error and what type of exception is it?**\n")
        f.write("```java\n")
        f.write("public class App {\n")
        f.write("    static Integer val;\n")
        f.write("    public static void main(String[] args) {\n")
        f.write("        int x = val;\n")
        f.write("    }\n")
        f.write("}\n")
        f.write("```\n\n")
        re_answers.append(f"{i}. NullPointerException at `int x = val;` because `val` is null and unboxing it throws NPE.")

    f.write("---\n### Answers\n")
    for ans in re_answers:
        f.write(f"{ans}\n")
    f.write("\n")

    # Level 6: DEBUG THIS CODE (20)
    f.write("## Level 6: Debug This Code\n\n")
    db_answers = []
    for i in range(1, 21):
        f.write(f"**{i}. The following code is meant to print {i*2}. Debug it.**\n")
        f.write("```java\n")
        f.write("public class App {\n")
        f.write("    public static void main(String[] args) {\n")
        f.write(f"        int a = {i};\n")
        f.write("        int b;\n")
        f.write("        System.out.println(a + b);\n")
        f.write("    }\n")
        f.write("}\n")
        f.write("```\n\n")
        db_answers.append(f"{i}. Local variable `b` might not have been initialized. Initialize `b = {i};`")

    f.write("---\n### Answers\n")
    for ans in db_answers:
        f.write(f"{ans}\n")
    f.write("\n")

    # Level 7: FILL IN THE MISSING CODE (20)
    f.write("## Level 7: Fill in the Missing Code\n\n")
    fi_answers = []
    for i in range(1, 21):
        f.write(f"**{i}. Fill in the blank to declare an unmodifiable integer list using var.**\n")
        f.write("```java\n")
        f.write("____ list = List.of(1, 2, 3);\n")
        f.write("```\n\n")
        fi_answers.append(f"{i}. `var`")

    f.write("---\n### Answers\n")
    for ans in fi_answers:
        f.write(f"{ans}\n")
    f.write("\n")

    # Level 8: EXPLAIN WHY THIS CODE WORKS (20)
    f.write("## Level 8: Explain Why This Code Works\n\n")
    ex_answers = []
    for i in range(1, 21):
        f.write(f"**{i}. Explain why this code compiles successfully despite redefining 'x'.**\n")
        f.write("```java\n")
        f.write("public class Scope {\n")
        f.write("    int x = 1;\n")
        f.write("    public void test() {\n")
        f.write("        int x = 2;\n")
        f.write("        {\n")
        f.write("            // int x = 3; -> would be error\n")
        f.write("        }\n")
        f.write("    }\n")
        f.write("}\n")
        f.write("```\n\n")
        ex_answers.append(f"{i}. Shadowing allows a local variable to hide an instance variable, because they are in different scope levels (class vs method).")

    f.write("---\n### Answers\n")
    for ans in ex_answers:
        f.write(f"{ans}\n")
    f.write("\n")

    # Level 9: REFACTORING EXERCISES (20)
    f.write("## Level 9: Refactoring Exercises\n\n")
    rf_answers = []
    for i in range(1, 21):
        f.write(f"**{i}. Refactor this to use local variable type inference.**\n")
        f.write("```java\n")
        f.write("Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();\n")
        f.write("```\n\n")
        rf_answers.append(f"{i}. `var map = new HashMap<String, List<Integer>>();`")

    f.write("---\n### Answers\n")
    for ans in rf_answers:
        f.write(f"{ans}\n")
    f.write("\n")

    # Level 10: CODING EXERCISES (20)
    f.write("## Level 10: Coding Exercises\n\n")
    ce2_answers = []
    for i in range(1, 21):
        f.write(f"**{i}. Write a method that swaps two variables without using a third temporary variable (assume int).**\n\n")
        ce2_answers.append(f"{i}. `a = a ^ b; b = a ^ b; a = a ^ b;` OR `a = a + b; b = a - b; a = a - b;`")

    f.write("---\n### Answers\n")
    for ans in ce2_answers:
        f.write(f"{ans}\n")
    f.write("\n")

    # Level 11: MINI INTERVIEW QUESTIONS (10)
    f.write("## Level 11: Mini Interview Questions\n\n")
    iq_answers = []
    for i in range(1, 11):
        f.write(f"**{i}. Can you explain the difference between a reference variable and a primitive variable in terms of memory layout in the JVM?**\n\n")
        iq_answers.append(f"{i}. Primitives hold the actual value directly in the stack frame. Reference variables hold a memory address (pointer) on the stack, which points to an object allocated on the heap.")

    f.write("---\n### Answers\n")
    for ans in iq_answers:
        f.write(f"{ans}\n")
    f.write("\n")

    # Level 12: LARGE CODING ASSIGNMENTS (5)
    f.write("## Level 12: Large Coding Assignments\n\n")
    lca_answers = []
    for i in range(1, 6):
        f.write(f"**{i}. Design a highly concurrent Counter class relying solely on volatile variables and compare-and-swap semantics (do not use synchronized).**\n\n")
        lca_answers.append(f"{i}. Use `java.util.concurrent.atomic.AtomicInteger` or implement a custom loop using `VarHandle` or `Unsafe` with a `volatile int` field.")

    f.write("---\n### Answers\n")
    for ans in lca_answers:
        f.write(f"{ans}\n")
    f.write("\n")

    # Level 13: CAPSTONE PROJECT (1)
    f.write("## Level 13: Capstone Project\n\n")
    f.write("**1. Create a custom memory pool manager simulation in Java.**\n")
    f.write("Your task is to write a class that pre-allocates an array of 1,000,000 objects. You must use integer variables to track the head and tail of available objects. No garbage collection should occur for the pooled objects. Document the scope and mutability of every variable you use.\n\n")
    
    f.write("---\n### Answers\n")
    f.write("1. This project involves creating an ObjectPool class using an array or concurrent queue. Variables: `private final Object[] pool;`, `private volatile int head;`, `private volatile int tail;`. Synchronization or VarHandles must be used. It tests understanding of references, object retention, scope, and final/volatile modifiers.\n")
    f.write("\n")

print("File generated successfully.")
