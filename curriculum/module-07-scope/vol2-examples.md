# Java Mastery Curriculum — Module 7: Scope
## Volume 2: Examples & Bytecode Deep Dive

**Target Environment:** Java 21 LTS (OpenJDK 21 / HotSpot VM) & IntelliJ IDEA  
**Perspective:** Senior Java Engineer, JVM Runtime Engineer, and OpenJDK Compiler Engineer  

---

## Table of Contents
1. [Overview & JVM Scope Architecture](#overview--jvm-scope-architecture)
2. [Simple Examples (10)](#1-simple-examples)
   - [1.1 Block Scope: Variable Inaccessible Outside](#11-block-scope-variable-inaccessible-outside)
   - [1.2 Method Scope: Parameters, Locals, and Stack Frame Teardown](#12-method-scope-parameters-locals-and-stack-frame-teardown)
   - [1.3 For Loop Scope: Control Variable Encapsulation and Reuse](#13-for-loop-scope-control-variable-encapsulation-and-reuse)
   - [1.4 While Loop Scope: Iteration-Local State](#14-while-loop-scope-iteration-local-state)
   - [1.5 If/Else Branch Scope: Mutual Isolation](#15-ifelse-branch-scope-mutual-isolation)
   - [1.6 Instance Field Scope: Accessible Across Instance Methods](#16-instance-field-scope-accessible-across-instance-methods)
   - [1.7 Static Field Scope: Class-Level Accessibility](#17-static-field-scope-class-level-accessibility)
   - [1.8 Static Context Restriction: Inaccessibility of Instance Fields](#18-static-context-restriction-inaccessibility-of-instance-fields)
   - [1.9 Default Initialization: Heap Fields vs Stack Locals](#19-default-initialization-heap-fields-vs-stack-locals)
   - [1.10 Definite Assignment: Conditional Branch Failure](#110-definite-assignment-conditional-branch-failure)
3. [Intermediate Examples (8)](#2-intermediate-examples)
   - [2.1 Shadowing: Parameter Shadows Instance Field (Resolution via `this`)](#21-shadowing-parameter-shadows-instance-field-resolution-via-this)
   - [2.2 Shadowing: Local Variable Shadows Instance Field](#22-shadowing-local-variable-shadows-instance-field)
   - [2.3 Field Hiding in Inheritance: Non-Polymorphic Static Binding](#23-field-hiding-in-inheritance-non-polymorphic-static-binding)
   - [2.4 Static Method Hiding vs Instance Method Overriding](#24-static-method-hiding-vs-instance-method-overriding)
   - [2.5 Switch Fall-Through Scope Leakage (Colon Syntax)](#25-switch-fall-through-scope-leakage-colon-syntax)
   - [2.6 Switch Expression Scope Isolation (Arrow Syntax, Java 14+)](#26-switch-expression-scope-isolation-arrow-syntax-java-14)
   - [2.7 Effectively Final: Lambda Capture and Invalidation Rules](#27-effectively-final-lambda-capture-and-invalidation-rules)
   - [2.8 Pattern Variable Scope: Flow-Sensitive Typing with `&&`](#28-pattern-variable-scope-flow-sensitive-typing-with-)
4. [Advanced Examples (5)](#3-advanced-examples)
   - [3.1 Bytecode Proof: `javap -c -l` LocalVariableTable & Slot Reuse](#31-bytecode-proof-javap--c--l-localvariabletable--slot-reuse)
   - [3.2 Lambda Capture Internals: Synthetic Fields & Desugaring](#32-lambda-capture-internals-synthetic-fields--desugaring)
   - [3.3 Lexical Enclosure & Inner Classes: `Outer.this` Resolution](#33-lexical-enclosure--inner-classes-outerthis-resolution)
   - [3.4 Stack Frame Slot Allocation & Sibling Block Slot Reuse](#34-stack-frame-slot-allocation--sibling-block-slot-reuse)
   - [3.5 Try-With-Resources Scope: Synthetic Finally & Variable Liveness](#35-try-with-resources-scope-synthetic-finally--variable-liveness)
5. [Production Examples (3)](#4-production-examples)
   - [4.1 Fluent Builder Pattern: Scope Encapsulation for Immutability](#41-fluent-builder-pattern-scope-encapsulation-for-immutability)
   - [4.2 Safe Scope Resource Management: AutoCloseable Channels](#42-safe-scope-resource-management-autocloseable-channels)
   - [4.3 Access Modifier Tiering: Encapsulation Through Visibility Scopes](#43-access-modifier-tiering-encapsulation-through-visibility-scopes)
6. [Wrong Examples — Compiler Errors (10)](#5-wrong-examples--compiler-errors)
   - [5.1 Accessing Variable Outside Block Scope](#51-accessing-variable-outside-block-scope)
   - [5.2 Accessing For Loop Variable After Loop Termination](#52-accessing-for-loop-variable-after-loop-termination)
   - [5.3 Re-declaring Variable in Nested Block](#53-re-declaring-variable-in-nested-block)
   - [5.4 Using Local Variable Before Definite Assignment](#54-using-local-variable-before-definite-assignment)
   - [5.5 Accessing Instance Field from Static Context Without Reference](#55-accessing-instance-field-from-static-context-without-reference)
   - [5.6 Using `this` in Static Method or Initializer](#56-using-this-in-static-method-or-initializer)
   - [5.7 Capturing Non-Effectively-Final Local in Lambda](#57-capturing-non-effectively-final-local-in-lambda)
   - [5.8 Referencing Pattern Variable on RHS of `||`](#58-referencing-pattern-variable-on-rhs-of-)
   - [5.9 Duplicate Variable Name in Same Method Scope](#59-duplicate-variable-name-in-same-method-scope)
   - [5.10 Accessing Resource Variable Outside Try-With-Resources](#510-accessing-resource-variable-outside-try-with-resources)
7. [Wrong Examples — Runtime Errors (5)](#6-wrong-examples--runtime-errors)
   - [6.1 NullPointerException from Uninitialized Instance Reference](#61-nullpointerexception-from-uninitialized-instance-reference)
   - [6.2 Reading Hidden Superclass Field via Inappropriate Reference Type](#62-reading-hidden-superclass-field-via-inappropriate-reference-type)
   - [6.3 ClassCastException from Faulty Hierarchical Scope Assumptions](#63-classcastexception-from-faulty-hierarchical-scope-assumptions)
   - [6.4 ConcurrentModificationException via Leaked Iterator Scope](#64-concurrentmodificationexception-via-leaked-iterator-scope)
   - [6.5 StackOverflowError: Unbounded Recursion Exhausting Stack Frames](#65-stackoverflowerror-unbounded-recursion-exhausting-stack-frames)
8. [Edge Cases (6)](#7-edge-cases)
   - [7.1 Empty Block `{ }` Syntactic Validity](#71-empty-block---syntactic-validity)
   - [7.2 Standalone Block for Intermediate Lifetime Management](#72-standalone-block-for-intermediate-lifetime-management)
   - [7.3 Final Local Variable with Deferred Initialization in Branches](#73-final-local-variable-with-deferred-initialization-in-branches)
   - [7.4 Static Field Initialized via Instance Method Call](#74-static-field-initialized-via-instance-method-call)
   - [7.5 Referencing `this` Before `super()` in Constructors](#75-referencing-this-before-super-in-constructors)
   - [7.6 Multi-Variable For Loop Header Scope](#76-multi-variable-for-loop-header-scope)

---

## Overview & JVM Scope Architecture

In the Java programming language, **scope** designates the lexical region of code where a declared entity can be referred to by its simple (unqualified) name ([JLS §6.3](https://docs.oracle.com/javase/specs/jls/se21/html/jls-6.html#jls-6.3)). 

At the OpenJDK and HotSpot VM level, "scope" is enforced along two distinct dimensions:
1. **Compile-Time Lexical Scoping (`javac` AST Attribution):** The OpenJDK compiler builds an Abstract Syntax Tree (AST) and maintains an environment symbol table (`com.sun.tools.javac.comp.Attr`, `com.sun.tools.javac.comp.Resolve`, `com.sun.tools.javac.comp.Flow`). Here, the compiler strictly enforces accessibility rules, definite assignment rules (JLS §16), visibility modifiers, and prohibits illegal redeclarations.
2. **Runtime Execution Frame Scoping (JVM HotSpot):** At runtime, variables do not possess names. Instead, the Java Virtual Machine allocates a **Call Stack Frame** for every method invocation. The frame contains:
   - **Local Variable Table (LVT / Local Variable Array):** Slots `0` through `N - 1`. Instance methods place the receiver `this` at slot `0`. Method arguments occupy subsequent slots, followed by local variables.
   - **Operand Stack:** Evaluates expressions via push/pop opcodes (`iload`, `istore`, `bipush`, `iadd`).
   - Sibling blocks share and reuse identical local variable array slots, minimizing stack frame footprint.
   - Instance and static fields reside outside the stack: instance fields live inside the object layout on the **Java Garbage-Collected Heap**, while static fields reside inside the `java.lang.Class` mirror on the heap with metadata tracked in **Metaspace**.

```
+-------------------------------------------------------------------------+
|                              JVM MEMORY                                 |
+------------------------------------+------------------------------------+
|             THREAD STACK           |              HEAP / GC             |
|  +-------------------------------+ |  +-------------------------------+ |
|  | Frame: compute()              | |  | Object Instance               | |
|  | - Slot 0: this                | |  | - Object Header (Mark+Klass)  | |
|  | - Slot 1: int a (scope: L1-L4)| |  | - Instance Field: int id      | |
|  | - Slot 2: String s            | |  | - Instance Field: double bal  | |
|  |   (Slot 2 reused for int x)   | |  +-------------------------------+ |
|  | - Operand Stack: [ ... ]      | |  +-------------------------------+ |
|  +-------------------------------+ |  | Class Mirror (java.lang.Class)| |
|                                    |  | - Static Field: String APP    | |
+------------------------------------+--+-------------------------------+ |
```

---

## 1. SIMPLE EXAMPLES

### 1.1 Block Scope: Variable Inaccessible Outside
A block is a sequence of statements, local class declarations, and local variable declaration statements within braces `{ ... }` (JLS §14.2). A variable declared inside a block has its scope limited to the remainder of that block.

#### Complete Java Code
```java
package module07.examples;

public class SimpleBlockScope {
    public static void main(String[] args) {
        int threshold = 50;

        {
            int blockResult = threshold * 2;
            System.out.println("Inside block: blockResult = " + blockResult);
            // blockResult is live and in scope
        }

        // System.out.println(blockResult); // COMPILE ERROR: cannot find symbol
        System.out.println("Outside block: threshold = " + threshold);
    }
}
```

#### Explanation
- `threshold` is declared in the method body and remains in scope for all subsequent statements in `main`.
- `blockResult` is introduced inside the inner braces `{ ... }`. Its lexical scope ends precisely at the closing brace `}`. Any access outside yields a symbol lookup failure.

#### Compiler & JVM Mechanics
- **javac:** During the attribution phase (`Attr.java`), `javac` pushes a new scope environment onto its symbol table stack upon seeing `{`. When reading `blockResult`, it resolves it against the top of the symbol table. Upon encountering `}`, it pops the scope. Any subsequent AST node referring to `blockResult` fails symbol lookup: `error: cannot find symbol`.
- **JVM:** The compiler assigns `threshold` to slot `1` (slot `0` holds `args`). `blockResult` is assigned to slot `2`. Once the block finishes, slot `2` is free to be re-allocated for any subsequent variables in the method frame.

#### Expected Output
```text
Inside block: blockResult = 100
Outside block: threshold = 50
```

---

### 1.2 Method Scope: Parameters, Locals, and Stack Frame Teardown
Method parameters are in scope throughout the entire method body. Local variables are in scope from their point of declaration to the closing brace of the method body.

#### Complete Java Code
```java
package module07.examples;

public class SimpleMethodScope {
    public static double computeDiscount(double price, double rate) {
        // 'price' and 'rate' are in scope here
        double discountAmount = price * rate;
        
        if (discountAmount > 100.0) {
            double cap = 100.0;
            return cap; // Method scope teardown begins here
        }
        
        return discountAmount;
    } // discountAmount, price, and rate scopes terminate

    public static void main(String[] args) {
        double finalDiscount = computeDiscount(300.0, 0.40);
        System.out.println("Discount: " + finalDiscount);
    }
}
```

#### Explanation
- `price` and `rate` enter scope at the opening brace of `computeDiscount` and leave scope at its closing brace.
- `cap` is confined to the `if` branch.
- Returning from the method triggers stack frame deallocation.

#### Compiler & JVM Mechanics
- **javac:** Generates descriptor `(DD)D`. Because `double` requires 64 bits (2 JVM slots), `price` occupies slots `0` and `1`, `rate` occupies slots `2` and `3`, and `discountAmount` occupies slots `4` and `5`.
- **JVM:** HotSpot executes `dreturn`. The execution frame is popped off the thread stack; the program counter (`pc`) returns to the caller (`main`), and caller execution resumes.

#### Expected Output
```text
Discount: 100.0
```

---

### 1.3 For Loop Scope: Control Variable Encapsulation and Reuse
The initialization portion of a basic `for` statement introduces loop control variables whose scope is restricted to the initialization expression, condition, update expressions, and loop body (JLS §14.14.1.1).

#### Complete Java Code
```java
package module07.examples;

public class SimpleForLoopScope {
    public static void main(String[] args) {
        // First loop: 'i' is declared and scoped exclusively to this loop
        for (int i = 0; i < 3; i++) {
            System.out.println("First loop index: " + i);
        }

        // 'i' is out of scope here:
        // System.out.println(i); // COMPILE ERROR: cannot find symbol variable i

        // Second loop: Re-declaring 'i' is completely legal
        for (int i = 10; i < 13; i++) {
            System.out.println("Second loop index: " + i);
        }
    }
}
```

#### Explanation
- Encapsulating the iteration index inside `for(int i = 0; ...)` prevents variable pollution in outer code.
- Adjacent loops can declare variables with the same name without collision.

#### Compiler & JVM Mechanics
- **javac:** Treats `for (int i = 0; ...)` as an implicit block scope enclosing the loop condition, update step, and body.
- **JVM:** The slot allocated for `i` in the first loop is reused for `i` in the second loop.

#### Expected Output
```text
First loop index: 0
First loop index: 1
First loop index: 2
Second loop index: 10
Second loop index: 11
Second loop index: 12
```

---

### 1.4 While Loop Scope: Iteration-Local State
Variables declared within the body of a `while` loop are created anew each iteration from a lexical perspective.

#### Complete Java Code
```java
package module07.examples;

public class SimpleWhileLoopScope {
    public static void main(String[] args) {
        int attempt = 0;
        while (attempt < 3) {
            String token = "TOKEN-" + attempt; // Scoped to this iteration
            System.out.println("Processing with " + token);
            attempt++;
        }
        // token is inaccessible here
    }
}
```

#### Explanation
- `token` has a fresh lexical scope in every iteration. It cannot be read or reassigned after the `while` loop ends.

#### Compiler & JVM Mechanics
- **javac:** Emits bytecode that pushes a new String concatenation result into the local variable slot reserved for `token` on every iteration loop back.
- **JVM:** The reference in the local variable table slot is repeatedly overwritten by `astore`. The previous string becomes eligible for garbage collection once no other references exist.

#### Expected Output
```text
Processing with TOKEN-0
Processing with TOKEN-1
Processing with TOKEN-2
```

---

### 1.5 If/Else Branch Scope: Mutual Isolation
Variables declared in an `if` block are not visible in the `else` block, and vice versa.

#### Complete Java Code
```java
package module07.examples;

public class SimpleIfElseScope {
    public static void main(String[] args) {
        boolean authenticated = true;

        if (authenticated) {
            String role = "ADMIN";
            System.out.println("Granted access as: " + role);
        } else {
            // role is NOT accessible here!
            String fallback = "GUEST";
            System.out.println("Access restricted: " + fallback);
        }
        // Neither 'role' nor 'fallback' is accessible here
    }
}
```

#### Explanation
- The true block and false block represent two sibling, mutually exclusive lexical scopes.

#### Compiler & JVM Mechanics
- **javac:** Checks reachability and symbol availability. Branch jumps (`ifeq`, `goto`) bypass alternate branch bytecodes.
- **JVM:** Because both branches are mutually exclusive, the compiler assigns `role` and `fallback` to the exact same local variable slot index.

#### Expected Output
```text
Granted access as: ADMIN
```

---

### 1.6 Instance Field Scope: Accessible Across Instance Methods
Instance fields belong to the object instance on the heap and are visible to every non-static method within the declaring class via implicit or explicit `this` (JLS §8.3).

#### Complete Java Code
```java
package module07.examples;

public class SimpleInstanceFieldScope {
    private int score = 100; // Instance field (Class scope)

    public void addPoints(int delta) {
        score += delta; // Implicit this.score
    }

    public void display() {
        System.out.println("Current score: " + this.score);
    }

    public static void main(String[] args) {
        SimpleInstanceFieldScope demo = new SimpleInstanceFieldScope();
        demo.addPoints(50);
        demo.display();
    }
}
```

#### Explanation
- `score` is tied to the heap allocation of the `SimpleInstanceFieldScope` object. Both `addPoints` and `display` have access to `score`.

#### Compiler & JVM Mechanics
- **javac:** Emits `getfield` and `putfield` instructions referencing the field symbolic reference `#Fieldref ... score:I`.
- **JVM:** The JVM resolves the field offset within the object's memory layout on the heap and updates the memory location at `[object_ref_address + field_offset]`.

#### Expected Output
```text
Current score: 150
```

---

### 1.7 Static Field Scope: Class-Level Accessibility
Static fields belong to the class itself and are accessible from both static and instance methods throughout the entire class (and beyond, subject to access modifiers).

#### Complete Java Code
```java
package module07.examples;

public class SimpleStaticFieldScope {
    public static final String PROTOCOL = "HTTPS";
    public static int connectionCount = 0;

    public void connect() {
        connectionCount++; // Instance method accessing static field
        System.out.println("Connecting via " + PROTOCOL + ", total: " + connectionCount);
    }

    public static void reset() {
        connectionCount = 0; // Static method accessing static field
    }

    public static void main(String[] args) {
        SimpleStaticFieldScope client = new SimpleStaticFieldScope();
        client.connect();
        SimpleStaticFieldScope.reset();
        System.out.println("After reset: " + connectionCount);
    }
}
```

#### Explanation
- `PROTOCOL` and `connectionCount` are loaded during class initialization and persist across all object instances.

#### Compiler & JVM Mechanics
- **javac:** Emits `getstatic` and `putstatic` bytecode instructions.
- **JVM:** Fields are allocated in the `java.lang.Class` object associated with `SimpleStaticFieldScope`. No instance pointer is required.

#### Expected Output
```text
Connecting via HTTPS, total: 1
After reset: 0
```

---

### 1.8 Static Context Restriction: Inaccessibility of Instance Fields
Static methods are invoked without an object instance (`this` does not exist). Directly accessing an instance variable from a static context is an error.

#### Complete Java Code
```java
package module07.examples;

public class SimpleStaticContextError {
    private int instanceData = 42;

    public static void display() {
        // System.out.println(instanceData);
        // COMPILE ERROR: non-static variable instanceData cannot be referenced from a static context

        // System.out.println(this.instanceData);
        // COMPILE ERROR: non-static variable this cannot be referenced from a static context

        // Correction: Access via an explicit heap reference
        SimpleStaticContextError obj = new SimpleStaticContextError();
        System.out.println("Accessible via reference: " + obj.instanceData);
    }

    public static void main(String[] args) {
        display();
    }
}
```

#### Explanation
- A static method has no implicit `this` parameter in slot `0`. Therefore, instance fields cannot be resolved without an explicit reference.

#### Compiler & JVM Mechanics
- **javac:** During symbol resolution in `Attr.java`, the compiler determines that `display` has static flags (`ACC_STATIC`). Looking up an unqualified instance member results in `compiler.err.non-static.cant.be.ref`.
- **JVM:** Static methods have argument slots starting directly at slot `0` for method parameters.

#### Expected Output
```text
Accessible via reference: 42
```

---

### 1.9 Default Initialization: Heap Fields vs Stack Locals
Fields (instance and static) are automatically initialized with JVM default zero-values upon allocation. Local variables are **never** default-initialized.

#### Complete Java Code
```java
package module07.examples;

public class SimpleDefaultInit {
    // Heap fields: automatically initialized by JVM
    int defaultInt;           // 0
    double defaultDouble;     // 0.0
    boolean defaultBoolean;   // false
    String defaultString;     // null

    public void testInit() {
        int uninitLocal; // Stack local variable: NOT initialized!
        
        // System.out.println(uninitLocal); 
        // COMPILE ERROR: variable uninitLocal might not have been initialized

        uninitLocal = 99; // Explicit assignment satisfies definite assignment
        System.out.println("Initialized local: " + uninitLocal);
    }

    public static void main(String[] args) {
        SimpleDefaultInit demo = new SimpleDefaultInit();
        System.out.println("int: " + demo.defaultInt);
        System.out.println("double: " + demo.defaultDouble);
        System.out.println("boolean: " + demo.defaultBoolean);
        System.out.println("String: " + demo.defaultString);
        demo.testInit();
    }
}
```

#### Explanation
- Fields are allocated on the heap during object allocation (`new`), where memory is zeroed out by the JVM allocator.
- Local variables reside on the thread stack and may contain undefined remnants of previous call frames; therefore, Java enforces Definite Assignment.

#### Compiler & JVM Mechanics
- **javac:** Validates definite assignment for local variables using dataflow analysis (`Flow.java`).
- **JVM:** In HotSpot, object allocation (`new` opcode) clears the payload memory of the new object to zeros before constructor invocation.

#### Expected Output
```text
int: 0
double: 0.0
boolean: false
String: null
Initialized local: 99
```

---

### 1.10 Definite Assignment: Conditional Branch Failure
Under JLS §16, every local variable must be **definitely assigned** before any access occurs. If any execution path fails to assign a value, compilation fails.

#### Complete Java Code
```java
package module07.examples;

public class SimpleDefiniteAssignment {
    public static void main(String[] args) {
        int x; // Declared but unassigned
        boolean condition = (args.length > 0);

        if (condition) {
            x = 5;
        }
        // If condition is false, x is undefined here!

        // System.out.println(x);
        // COMPILE ERROR: variable x might not have been initialized

        // Fix: Provide exhaustive assignment across all branches
        int y;
        if (condition) {
            y = 5;
        } else {
            y = 10;
        }
        System.out.println("y is definitely assigned: " + y);
    }
}
```

#### Explanation
- Because the `else` branch was absent for `x`, `javac` proves that there is an execution path where `x` is unassigned.
- Providing an exhaustive `else` guarantees that `y` is definitely assigned on every path.

#### Compiler & JVM Mechanics
- **javac:** Employs a conservative bit-vector dataflow analysis (`Flow.java`). When compiling an access to `x`, if the bit representing `x` is not set in the "assigned-after" set of the preceding statement, compilation halts with an error.

#### Expected Output
```text
y is definitely assigned: 10
```

---

## 2. INTERMEDIATE EXAMPLES

### 2.1 Shadowing: Parameter Shadows Instance Field (Resolution via `this`)
When a method or constructor parameter shares the exact identifier as an instance field, the parameter shadows the field within the method body (JLS §6.4.1).

#### Complete Java Code
```java
package module07.examples;

public class IntermediateShadowingParam {
    private String username;
    private int id;

    public IntermediateShadowingParam(String username, int id) {
        // Without 'this.', 'username = username' is a meaningless self-assignment to the parameter!
        this.username = username;
        this.id = id;
    }

    public void display() {
        System.out.println("User ID: " + this.id + ", Name: " + this.username);
    }

    public static void main(String[] args) {
        IntermediateShadowingParam user = new IntermediateShadowingParam("Alice", 101);
        user.display();
    }
}
```

#### Explanation
- Inside the constructor, `username` resolves to the local parameter (slot `1`).
- To reach the instance field in the object heap, explicit qualification with `this.username` is required.

#### Compiler & JVM Mechanics
- **javac:** Evaluates simple identifier `username` to slot `1` (`aload_1`). Evaluates `this.username` to `aload_0` followed by `putfield #username`.

#### Expected Output
```text
User ID: 101, Name: Alice
```

---

### 2.2 Shadowing: Local Variable Shadows Instance Field
A local variable declared inside a method shadows an instance field of the same name.

#### Complete Java Code
```java
package module07.examples;

public class IntermediateShadowingLocal {
    private int counter = 1000;

    public void runJob() {
        int counter = 5; // Local variable shadows instance field
        System.out.println("Local counter: " + counter);
        System.out.println("Instance counter via this: " + this.counter);
    }

    public static void main(String[] args) {
        new IntermediateShadowingLocal().runJob();
    }
}
```

#### Explanation
- Inside `runJob()`, unqualified `counter` refers to the local variable on the stack frame.
- `this.counter` forces the compiler to emit `getfield` targeting the object on the heap.

#### Compiler & JVM Mechanics
- **javac:** Resolves unqualified `counter` against the current local scope first. If a match is found, lookup stops.

#### Expected Output
```text
Local counter: 5
Instance counter via this: 1000
```

---

### 2.3 Field Hiding in Inheritance: Non-Polymorphic Static Binding
When a subclass declares a field with the same name as a superclass field, it **hides** the superclass field (JLS §8.3). **Fields in Java are NOT polymorphic.** Field access is resolved at compile time based strictly on the declared reference type.

#### Complete Java Code
```java
package module07.examples;

class SuperDevice {
    public String deviceType = "GENERIC_DEVICE";
}

class MobileDevice extends SuperDevice {
    public String deviceType = "MOBILE_DEVICE"; // Hides SuperDevice.deviceType
}

public class IntermediateFieldHiding {
    public static void main(String[] args) {
        MobileDevice phone = new MobileDevice();
        SuperDevice polyRef = phone; // Polymorphic reference to same heap object

        System.out.println("phone.deviceType: " + phone.deviceType);
        System.out.println("polyRef.deviceType: " + polyRef.deviceType);
        System.out.println("Upcasted ((SuperDevice) phone).deviceType: " 
                + ((SuperDevice) phone).deviceType);
    }
}
```

#### Explanation
- Both fields (`SuperDevice.deviceType` and `MobileDevice.deviceType`) exist simultaneously in memory inside the `MobileDevice` heap object.
- Because `polyRef` is declared as `SuperDevice`, accessing `polyRef.deviceType` reads the hidden superclass field!

#### Compiler & JVM Mechanics
- **javac:** Emits `getfield #Fieldref SuperDevice.deviceType:Ljava/lang/String;` when compiling `polyRef.deviceType`. No virtual dispatch occurs.
- **JVM:** The object layout contains memory offsets for both fields. The JVM fetches the value corresponding directly to the field reference emitted in the bytecode constant pool.

#### Expected Output
```text
phone.deviceType: MOBILE_DEVICE
polyRef.deviceType: GENERIC_DEVICE
Upcasted ((SuperDevice) phone).deviceType: GENERIC_DEVICE
```

---

### 2.4 Static Method Hiding vs Instance Method Overriding
Static methods cannot be overridden; they can only be **hidden**. Method overriding resolves at runtime using the virtual method table (vtable), while static methods resolve at compile time based on the declared reference type.

#### Complete Java Code
```java
package module07.examples;

class ServiceBase {
    public static void identify() {
        System.out.println("ServiceBase.identify() [Static]");
    }

    public void execute() {
        System.out.println("ServiceBase.execute() [Virtual]");
    }
}

class CustomService extends ServiceBase {
    // Hides ServiceBase.identify()
    public static void identify() {
        System.out.println("CustomService.identify() [Static Hidden]");
    }

    // Overrides ServiceBase.execute()
    @Override
    public void execute() {
        System.out.println("CustomService.execute() [Virtual Overridden]");
    }
}

public class IntermediateMethodHiding {
    public static void main(String[] args) {
        ServiceBase service = new CustomService();

        // Static method invocation via reference: Compile-time binding!
        service.identify(); 

        // Virtual method invocation: Runtime dynamic dispatch!
        service.execute(); 
    }
}
```

#### Explanation
- `service.identify()` calls `ServiceBase.identify()` because `service` has compile-time type `ServiceBase`.
- `service.execute()` dynamically dispatches to `CustomService.execute()` because the underlying heap object is `CustomService`.

#### Compiler & JVM Mechanics
- **javac:** Emits `invokestatic #Methodref ServiceBase.identify:()V` for `service.identify()`. Emits `invokevirtual #Methodref ServiceBase.execute:()V` for `service.execute()`.
- **JVM:** HotSpot's `invokevirtual` inspects the receiver object's vtable at runtime and invokes `CustomService.execute`.

#### Expected Output
```text
ServiceBase.identify() [Static]
CustomService.execute() [Virtual Overridden]
```

---

### 2.5 Switch Fall-Through Scope Leakage (Colon Syntax)
In legacy `switch` statements with `:` syntax, the entire switch block forms a single lexical scope. A variable declared in `case 1` is visible to `case 2`, creating subtle bugs.

#### Complete Java Code
```java
package module07.examples;

public class IntermediateSwitchFallthroughScope {
    public static void process(int code) {
        switch (code) {
            case 1:
                int temp = 42; // Scoped to the entire remainder of the switch block!
                System.out.println("Case 1: temp = " + temp);
                break;
            case 2:
                // 'temp' is in scope here!
                // Attempting 'int temp = 99;' would cause a DUPLICATE VARIABLE error!
                temp = 99; // Reassigning the variable declared in case 1
                System.out.println("Case 2: temp = " + temp);
                break;
            default:
                System.out.println("Default case");
        }
    }

    public static void main(String[] args) {
        process(1);
        process(2);
    }
}
```

#### Explanation
- The colon `:` in a switch statement acts as a label, **not** a block boundary.
- Thus, `temp` declared in `case 1` remains in scope across subsequent cases.

#### Compiler & JVM Mechanics
- **javac:** Treats the entire switch body as a single scope. When `case 2` runs without entering `case 1`, `temp` is accessible in the symbol table, but reading it without assignment would trigger a definite assignment error.

#### Expected Output
```text
Case 1: temp = 42
Case 2: temp = 99
```

---

### 2.6 Switch Expression Scope Isolation (Arrow Syntax, Java 14+)
Modern switch expressions with `->` syntax provide strictly isolated block scopes for each arm, preventing variable leaks and eliminating fall-through.

#### Complete Java Code
```java
package module07.examples;

public class IntermediateSwitchExpressionScope {
    public static String classify(int score) {
        return switch (score / 10) {
            case 10, 9 -> {
                String grade = "Grade A"; // Scoped strictly to this block
                yield grade;
            }
            case 8 -> {
                String grade = "Grade B"; // Legal! No duplicate symbol collision
                yield grade;
            }
            default -> {
                String grade = "Passing/Remedial";
                yield grade;
            }
        };
    }

    public static void main(String[] args) {
        System.out.println(classify(95));
        System.out.println(classify(82));
    }
}
```

#### Explanation
- Each arrow block `{ ... yield value; }` introduces an independent lexical block scope.
- The name `grade` can be reused in each arm without collision.

#### Compiler & JVM Mechanics
- **javac:** Creates isolated symbol environments for each switch rule. Compiles switch expressions to table lookups (`tableswitch` / `lookupswitch`).

#### Expected Output
```text
Grade A
Grade B
```

---

### 2.7 Effectively Final: Lambda Capture and Invalidation Rules
Under JLS §4.12.4, a local variable is **effectively final** if its value is never modified after initialization. Lambdas can capture local variables only if they are `final` or effectively final.

#### Complete Java Code
```java
package module07.examples;

import java.util.function.Supplier;

public class IntermediateEffectivelyFinal {
    public static void main(String[] args) {
        int timeout = 5000; // Never reassigned -> Effectively final

        Supplier<String> statusReporter = () -> {
            // timeout is safely captured
            return "Timeout configured to: " + timeout + "ms";
        };

        System.out.println(statusReporter.get());

        int retries = 3;
        // retries++; // UNCOMMENTING THIS BREAKS EFFECTIVELY FINAL!

        // Supplier<Integer> retrySupplier = () -> retries;
        // COMPILE ERROR: local variables referenced from a lambda expression must be final or effectively final
    }
}
```

#### Explanation
- A local variable lives on the stack frame of `main`. The lambda instance can outlive `main` and execute asynchronously.
- The JVM copies the value of the variable into the lambda object. If Java permitted modifications, the stack local and the lambda's copied value would become out of sync.

#### Compiler & JVM Mechanics
- **javac:** Verifies via `Flow.java` that no assignments exist after declaration. It generates a synthetic method representing the lambda body and passes `timeout` as a parameter to the bootstrap method via `invokedynamic`.

#### Expected Output
```text
Timeout configured to: 5000ms
```

---

### 2.8 Pattern Variable Scope: Flow-Sensitive Typing with `&&`
Introduced in Java 16+ (JEP 394), pattern variables use **flow-sensitive scoping**. A pattern variable is only in scope where the pattern is guaranteed to have matched.

#### Complete Java Code
```java
package module07.examples;

public class IntermediatePatternScope {
    public static void inspect(Object obj) {
        // 's' is in scope on the RHS of && because 'obj instanceof String' is true there
        if (obj instanceof String s && s.length() > 3) {
            System.out.println("Long string detected: " + s.toUpperCase());
        } else {
            // 's' is NOT in scope here!
            System.out.println("Non-string or short string: " + obj);
        }
    }

    public static void main(String[] args) {
        inspect("OpenJDK");
        inspect("Hi");
        inspect(42);
    }
}
```

#### Explanation
- Short-circuiting `&&` guarantees that the right-hand side executes **only** if the pattern match succeeded. Hence, `s` is safely in scope on the right side of `&&`.
- In the `else` branch, the match failed or was bypassed, so `s` is not in scope.

#### Compiler & JVM Mechanics
- **javac:** Emits `checkcast` and `instanceof`. During AST attribution, `javac` computes a conditional scope set (`whenTrue` vs `whenFalse`). The variable `s` is bound only to the `whenTrue` expression branch.

#### Expected Output
```text
Long string detected: OPENJDK
Non-string or short string: Hi
Non-string or short string: 42
```

---

## 3. ADVANCED EXAMPLES

### 3.1 Bytecode Proof: `javap -c -l` LocalVariableTable & Slot Reuse
The HotSpot VM reuses local variable array slots across non-overlapping scopes. We can inspect this with `javap -c -l`.

#### Complete Java Code
```java
package module07.examples;

public class AdvancedSlotReuseProof {
    public static void demonstrate() {
        {
            String token = "AUTH-999";
            System.out.println(token);
        } // 'token' scope ends here

        {
            int code = 200;
            System.out.println(code);
        } // 'code' scope ends here
    }

    public static void main(String[] args) {
        demonstrate();
    }
}
```

#### Disassembled Bytecode Analysis (`javap -c -l -v`)
```text
  public static void demonstrate();
    Code:
       0: ldc           #7                  // String AUTH-999
       2: astore_0                          // Store in Slot 0 (token)
       3: getstatic     #9                  // Field java/lang/System.out:Ljava/io/PrintStream;
       6: aload_0
       7: invokevirtual #15                 // println:(Ljava/lang/String;)V
      10: sipush        200
      13: istore_0                          // REUSE: Store in Slot 0 (code)!
      14: getstatic     #9                  // Field java/lang/System.out:Ljava/io/PrintStream;
      17: iload_0
      18: invokevirtual #21                 // println:(I)V
      21: return
    LineNumberTable:
      line 6: 0
      line 7: 3
      line 11: 10
      line 12: 14
      line 14: 21
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          3       7     0 token   Ljava/lang/String;
         14       7     0  code   I
```

#### Explanation
- Notice `Slot 0` in the `LocalVariableTable`:
  - `token` occupies `Slot 0` from bytecode offset 3 with length 7 (offsets 3 to 10).
  - `code` occupies `Slot 0` from bytecode offset 14 with length 7 (offsets 14 to 21).
- The exact same physical JVM stack slot is reused for different variables with different data types (`String` reference vs primitive `int`).

#### Expected Output
```text
AUTH-999
200
```

---

### 3.2 Lambda Capture Internals: Synthetic Fields & Desugaring
When a lambda captures an effectively final local variable, how does the JVM pass and store that variable?

#### Complete Java Code
```java
package module07.examples;

import java.util.function.IntSupplier;

public class AdvancedLambdaCaptureInternals {
    public static IntSupplier createSupplier(int base) {
        int multiplier = 10; // Captured local
        return () -> base * multiplier;
    }

    public static void main(String[] args) {
        IntSupplier supplier = createSupplier(5);
        System.out.println("Result: " + supplier.getAsInt());
    }
}
```

#### Explanation & Compiler Desugaring
1. `javac` desugars the lambda body into a synthetic static method:
   ```java
   private static int lambda$createSupplier$0(int base, int multiplier) {
       return base * multiplier;
   }
   ```
2. In `createSupplier`, `javac` emits an `invokedynamic` call to `LambdaMetafactory.metafactory`:
   ```text
   invokedynamic #BootstrapMethod(createSupplier:(II)Ljava/util/function/IntSupplier;)
   ```
3. At runtime, `LambdaMetafactory` generates an inner class implementing `IntSupplier`. This synthetic class has two final fields (`private final int arg$1; private final int arg$2;`). The local variables `base` and `multiplier` are passed to its constructor and copied into these fields.

#### Expected Output
```text
Result: 50
```

---

### 3.3 Lexical Enclosure & Inner Classes: `Outer.this` Resolution
An inner class (non-static nested class) maintains a hidden synthetic reference to its enclosing outer instance, allowing access to outer fields even when shadowed.

#### Complete Java Code
```java
package module07.examples;

public class AdvancedInnerScope {
    private String domain = "GLOBAL";

    public class NestedLevel {
        private String domain = "NESTED";

        public void printDomains(String domain) {
            System.out.println("1. Parameter domain: " + domain);
            System.out.println("2. Nested field domain: " + this.domain);
            System.out.println("3. Outer field domain: " + AdvancedInnerScope.this.domain);
        }
    }

    public static void main(String[] args) {
        AdvancedInnerScope outer = new AdvancedInnerScope();
        NestedLevel nested = outer.new NestedLevel();
        nested.printDomains("METHOD_ARG");
    }
}
```

#### Explanation
- Scope resolution searches innermost to outermost:
  1. Method parameter `domain`
  2. Current instance field `this.domain`
  3. Enclosing instance field `AdvancedInnerScope.this.domain`

#### Compiler & JVM Mechanics
- **javac:** Generates a synthetic field `final AdvancedInnerScope this$0;` inside `NestedLevel.class`. The constructor of `NestedLevel` receives the outer instance reference and assigns it to `this$0`.
- **JVM:** Accessing `AdvancedInnerScope.this.domain` compiles to `aload_0`, `getfield #this$0`, followed by `getfield #domain`.

#### Expected Output
```text
1. Parameter domain: METHOD_ARG
2. Nested field domain: NESTED
3. Outer field domain: GLOBAL
```

---

### 3.4 Stack Frame Slot Allocation & Sibling Block Slot Reuse
The JVM specification dictates that `long` and `double` require **two consecutive slots** in the local variable array, while all other types require a single slot.

#### Complete Java Code
```java
package module07.examples;

public class AdvancedSlotAllocation {
    public static void compute() {
        {
            double highPrecision = 3.1415926535; // Consumes 2 slots (Slots 0 and 1)
            System.out.println("Double: " + highPrecision);
        }

        {
            long largeEpoch = 1700000000000L;    // Reuses Slots 0 and 1!
            System.out.println("Long: " + largeEpoch);
        }

        {
            int status = 1;                     // Reuses Slot 0
            boolean active = true;              // Reuses Slot 1
            System.out.println("Status: " + status + ", Active: " + active);
        }
    }

    public static void main(String[] args) {
        compute();
    }
}
```

#### Explanation
- In static method `compute()`, there is no `this`.
- In block 1: `highPrecision` (`double`) takes slots `0` and `1`.
- In block 2: `largeEpoch` (`long`) reuses slots `0` and `1`.
- In block 3: `status` (`int`) reuses slot `0`; `active` (`boolean`) reuses slot `1`.
- The maximum stack frame local variable footprint for `compute()` is only **2 slots**!

#### Expected Output
```text
Double: 3.1415926535
Long: 1700000000000
Status: 1, Active: true
```

---

### 3.5 Try-With-Resources Scope: Synthetic Finally & Variable Liveness
Variables declared in the resource header of a `try-with-resources` statement are scoped strictly to the `try` block. They are inaccessible in `catch` and `finally` blocks (JLS §14.20.3).

#### Complete Java Code
```java
package module07.examples;

class MockResource implements AutoCloseable {
    private final String name;

    public MockResource(String name) {
        this.name = name;
        System.out.println("Opened: " + name);
    }

    public void read() {
        System.out.println("Reading from " + name);
    }

    @Override
    public void close() {
        System.out.println("Closed: " + name);
    }
}

public class AdvancedTryWithResourcesScope {
    public static void main(String[] args) {
        try (MockResource res = new MockResource("DatabaseConnection")) {
            res.read(); // res is in scope
        } catch (Exception e) {
            // res is NOT in scope here!
            System.out.println("Exception caught: " + e.getMessage());
        } finally {
            // res is NOT in scope here!
            System.out.println("Finally block executed.");
        }
    }
}
```

#### Explanation
- Closing the resource must occur **before** `catch` and `finally` execute. If `res` were accessible in `catch` or `finally`, the developer could invoke methods on an already-closed resource.

#### Compiler & JVM Mechanics
- **javac:** Desugars the try-with-resources block into synthetic try-finally bytecode blocks containing null-checks, primary exception tracking, and suppressed exception handling (`Throwable.addSuppressed`).
- The variable `res` is explicitly scoped so that its liveness terminates before the synthetic exception table handlers run.

#### Expected Output
```text
Opened: DatabaseConnection
Reading from DatabaseConnection
Closed: DatabaseConnection
Finally block executed.
```

---

## 4. PRODUCTION EXAMPLES

### 4.1 Fluent Builder Pattern: Scope Encapsulation for Immutability
In enterprise applications, constructing complex domain models requires immutability combined with fluent validation. Inner builder classes provide scoped construction where intermediate mutable state cannot leak.

#### Complete Java Code
```java
package module07.examples;

public final class UserAccount {
    // Encapsulated immutable fields
    private final String username;
    private final String email;
    private final boolean active;

    // Private constructor: instantiated exclusively by Builder
    private UserAccount(Builder builder) {
        this.username = builder.username;
        this.email = builder.email;
        this.active = builder.active;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public boolean isActive() { return active; }

    public static Builder builder() {
        return new Builder();
    }

    // Static nested builder class: provides scoped mutable staging area
    public static final class Builder {
        private String username;
        private String email;
        private boolean active = true;

        private Builder() {}

        public Builder username(String username) {
            this.username = username; // Shadowing resolved via this
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public UserAccount build() {
            if (username == null || email == null) {
                throw new IllegalStateException("Required fields cannot be null");
            }
            return new UserAccount(this);
        }
    }

    public static void main(String[] args) {
        UserAccount account = UserAccount.builder()
                .username("naman_dev")
                .email("naman@example.com")
                .active(true)
                .build();

        System.out.println("Created account: " + account.getUsername() 
                + " <" + account.getEmail() + ">, Active: " + account.isActive());
    }
}
```

#### Explanation
- `UserAccount` fields are strictly `private final`, ensuring zero mutation after construction.
- The mutable staging fields exist only within the ephemeral lifetime of `UserAccount.Builder`.

---

### 4.2 Safe Scope Resource Management: AutoCloseable Channels
Production I/O streams must guarantee closure without resource leaks, socket exhaustion, or FD (file descriptor) starvation.

#### Complete Java Code
```java
package module07.examples;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ProductionResourceManager {
    public static String processPayload(byte[] rawData) throws IOException {
        // Multiple resources scoped to try block
        try (ByteArrayInputStream in = new ByteArrayInputStream(rawData);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            byte[] buffer = new byte[64]; // Scoped scratch buffer
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            return out.toString(StandardCharsets.UTF_8);
        } // 'in', 'out', and 'buffer' close and become eligible for GC here
    }

    public static void main(String[] args) throws IOException {
        byte[] data = "Mission-Critical Telemetry Data".getBytes(StandardCharsets.UTF_8);
        String result = processPayload(data);
        System.out.println("Processed: " + result);
    }
}
```

#### Explanation
- The input stream and output stream are scoped strictly to the `try(...)` header. Both streams are automatically closed in reverse order of declaration even if an `IOException` occurs.

---

### 4.3 Access Modifier Tiering: Encapsulation Through Visibility Scopes
Java access modifiers define four tiers of scope: `private`, default (package-private), `protected`, and `public`.

#### Complete Java Code
```java
package module07.examples;

public class ProductionSecurityTiering {
    // 1. Private: Scope restricted to this exact class declaration
    private String encryptionKey = "AES_SECRET_KEY";

    // 2. Package-Private (default): Scope open to classes in module07.examples
    int retryLimit = 3;

    // 3. Protected: Scope open to package AND subclasses in any package
    protected void auditLog(String message) {
        System.out.println("[AUDIT LOG] " + message);
    }

    // 4. Public: Scope universally accessible across all modules/packages
    public void authenticate(String token) {
        if (token != null && !token.isBlank()) {
            auditLog("Token authentication successful for token: " + mask(token));
        }
    }

    private String mask(String token) {
        return token.substring(0, Math.min(4, token.length())) + "****";
    }

    public static void main(String[] args) {
        ProductionSecurityTiering sec = new ProductionSecurityTiering();
        sec.authenticate("TKN-987654321");
    }
}
```

#### Explanation
- `encryptionKey` is strictly hidden from outside inspection.
- `auditLog` allows extension by inheriting subclasses while guarding internal logging mechanics from arbitrary external callers.

---

## 5. WRONG EXAMPLES — COMPILER ERRORS

### 5.1 Accessing Variable Outside Block Scope
```java
public class ErrorBlockScope {
    public static void main(String[] args) {
        {
            int secret = 77;
        }
        System.out.println(secret); // COMPILER ERROR
    }
}
```
- **javac diagnostic:** `error: cannot find symbol \n  symbol:   variable secret \n  location: class ErrorBlockScope`
- **Compiler cause:** `secret` was removed from the AST symbol table when exiting the block.

---

### 5.2 Accessing For Loop Variable After Loop Ends
```java
public class ErrorForLoopScope {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            // work
        }
        System.out.println("Final index: " + i); // COMPILER ERROR
    }
}
```
- **javac diagnostic:** `error: cannot find symbol \n  symbol:   variable i \n  location: class ErrorForLoopScope`
- **Compiler cause:** Variable `i` is declared in the for-init statement. Its scope is restricted to the for-statement (JLS §14.14.1.1).

---

### 5.3 Re-declaring Local Variable in Nested Block
Unlike C and C++, Java strictly prohibits declaring a local variable with the same name as a variable in an enclosing block.
```java
public class ErrorNestedRedeclaration {
    public static void main(String[] args) {
        int count = 10;
        {
            int count = 20; // COMPILER ERROR
            System.out.println(count);
        }
    }
}
```
- **javac diagnostic:** `error: variable count is already defined in method main(String[])`
- **Compiler cause:** JLS §14.4.2 mandates that the scope of a local variable includes any nested blocks. Declaring another variable of the same name within that scope is an illegal collision.

---

### 5.4 Using Local Variable Before Initialization (Definite Assignment)
```java
public class ErrorUninitializedLocal {
    public static void main(String[] args) {
        int total;
        boolean condition = (args.length > 5);
        if (condition) {
            total = 100;
        }
        System.out.println(total); // COMPILER ERROR
    }
}
```
- **javac diagnostic:** `error: variable total might not have been initialized`
- **Compiler cause:** `javac`'s flow analysis proves that if `condition` is false, `total` remains unassigned upon reach of the print statement.

---

### 5.5 Accessing Instance Variable from Static Method
```java
public class ErrorStaticAccess {
    private int score = 50;

    public static void resetScore() {
        score = 0; // COMPILER ERROR
    }
}
```
- **javac diagnostic:** `error: non-static variable score cannot be referenced from a static context`
- **Compiler cause:** Static methods are not associated with any heap object instance; there is no `this` reference to pass to `getfield` / `putfield`.

---

### 5.6 Using `this` in Static Context
```java
public class ErrorThisInStatic {
    public static void main(String[] args) {
        System.out.println(this); // COMPILER ERROR
    }
}
```
- **javac diagnostic:** `error: non-static variable this cannot be referenced from a static context`
- **Compiler cause:** The keyword `this` is valid only in the body of an instance method, instance constructor, or instance initializer (JLS §15.8.3).

---

### 5.7 Lambda Capturing Non-Effectively-Final Variable
```java
public class ErrorLambdaEffectivelyFinal {
    public static void main(String[] args) {
        int step = 1;
        Runnable task = () -> System.out.println(step); // COMPILER ERROR
        step = 2; // Mutating 'step' revokes effectively-final status!
    }
}
```
- **javac diagnostic:** `error: local variables referenced from a lambda expression must be final or effectively final`
- **Compiler cause:** `Flow.java` marks `step` as mutated; therefore, it cannot be safely captured by value into the lambda's desugared parameters.

---

### 5.8 Pattern Variable Scope with `||`
```java
public class ErrorPatternOrScope {
    public static void test(Object obj) {
        if (obj instanceof String s || s.length() > 0) { // COMPILER ERROR
            System.out.println("Matched");
        }
    }
}
```
- **javac diagnostic:** `error: cannot find symbol \n  symbol:   variable s \n  location: class ErrorPatternOrScope`
- **Compiler cause:** When evaluating the right side of `||`, the left side evaluated to `false`. If `obj instanceof String` is `false`, `s` cannot be assigned, so `s` is not in scope.

---

### 5.9 Duplicate Variable Name in Same Method Scope
```java
public class ErrorDuplicateVariable {
    public static void main(String[] args) {
        int port = 8080;
        double port = 8080.0; // COMPILER ERROR
    }
}
```
- **javac diagnostic:** `error: variable port is already defined in method main(String[])`
- **Compiler cause:** Two local variables in the same immediate block cannot share the same identifier regardless of their data types.

---

### 5.10 Accessing Resource Variable Outside Try-With-Resources
```java
public class ErrorResourceOutOfScope {
    public static void main(String[] args) {
        try (AutoCloseable res = () -> {}) {
            // res is valid here
        }
        res.close(); // COMPILER ERROR
    }
}
```
- **javac diagnostic:** `error: cannot find symbol \n  symbol:   variable res \n  location: class ErrorResourceOutOfScope`
- **Compiler cause:** Resources declared in `try(...)` are lexically scoped strictly to the try block (JLS §14.20.3).

---

## 6. WRONG EXAMPLES — RUNTIME ERRORS

### 6.1 NullPointerException from Uninitialized Instance Reference
Fields are initialized to `null` by default. Attempting to invoke methods on an uninitialized reference field throws `NullPointerException`.

#### Code
```java
package module07.examples;

public class RuntimeErrorNPE {
    private String title; // Default initialized to null

    public void printLength() {
        System.out.println(this.title.length()); // Throws NullPointerException!
    }

    public static void main(String[] args) {
        new RuntimeErrorNPE().printLength();
    }
}
```
- **Runtime Exception:** `Exception in thread "main" java.lang.NullPointerException: Cannot invoke "String.length()" because "this.title" is null`
- **Root Cause:** Developers relying on field scope forget that unlike local variables, the compiler does not enforce definite assignment on fields.

---

### 6.2 Reading Hidden Field via Wrong Reference Type
Field hiding causes subtle data corruption at runtime because fields are bound statically.

#### Code
```java
package module07.examples;

class SuperConfig {
    public int maxConnections = 10;
}

class ClusterConfig extends SuperConfig {
    public int maxConnections = 500; // Hides superclass field
}

public class RuntimeErrorHiddenFieldAccess {
    public static void main(String[] args) {
        ClusterConfig cluster = new ClusterConfig();
        allocatePool(cluster);
    }

    public static void allocatePool(SuperConfig config) {
        // Developer expects 500, but gets 10!
        System.out.println("Allocating worker pool: " + config.maxConnections);
    }
}
```
- **Behavior:** Prints `10` instead of `500`.
- **Root Cause:** `allocatePool` accepts `SuperConfig`. Bytecode `getfield SuperConfig.maxConnections` executes, reading the wrong field and causing runtime resource under-allocation.

---

### 6.3 ClassCastException from Incorrect Scope Assumption with Inheritance
Assuming an object in a broader scope is always of a specific sub-type without validation causes a `ClassCastException`.

#### Code
```java
package module07.examples;

public class RuntimeErrorClassCast {
    public static void process(Object input) {
        // Unsafe cast assuming input is a String
        String str = (String) input; // Throws ClassCastException for non-strings
        System.out.println("Processed: " + str.toLowerCase());
    }

    public static void main(String[] args) {
        process(Integer.valueOf(12345));
    }
}
```
- **Runtime Exception:** `Exception in thread "main" java.lang.ClassCastException: class java.lang.Integer cannot be cast to class java.lang.String`
- **Fix:** Use Java 16+ pattern matching: `if (input instanceof String s) { ... }`.

---

### 6.4 ConcurrentModificationException via Leaked Iterator Scope
Leaking iteration variables or mutating collections within iterator loops leads to `ConcurrentModificationException`.

#### Code
```java
package module07.examples;

import java.util.ArrayList;
import java.util.List;

public class RuntimeErrorConcurrentMod {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("alpha", "beta", "gamma"));

        for (String item : list) {
            if ("beta".equals(item)) {
                list.remove(item); // Leaked mutation inside enhanced-for scope!
            }
        }
    }
}
```
- **Runtime Exception:** `Exception in thread "main" java.util.ConcurrentModificationException`
- **Root Cause:** The enhanced `for` loop synthesizes an `Iterator`. Mutating the collection directly updates `modCount`, desynchronizing the iterator's `expectedModCount`.

---

### 6.5 StackOverflowError: Unbounded Recursion Exhausting Stack Frames
Every method invocation allocates a new stack frame. Unbounded recursive scoping blows through thread stack memory.

#### Code
```java
package module07.examples;

public class RuntimeErrorStackOverflow {
    public static void infiniteRecurse(int depth) {
        int localA = depth;
        int localB = depth + 1;
        // Each call pushes a new frame with localA and localB
        infiniteRecurse(depth + 1);
    }

    public static void main(String[] args) {
        infiniteRecurse(1);
    }
}
```
- **Runtime Error:** `Exception in thread "main" java.lang.StackOverflowError`
- **JVM Diagnostics:** Each call allocates ~32-64 bytes for frame metadata and local variables. Default HotSpot thread stack size (`-Xss1m`) exhausts after ~10,000–15,000 recursive frames.

---

## 7. EDGE CASES

### 7.1 Empty Block `{ }` Syntactic Validity
An empty block `{ }` is a syntactically valid statement according to JLS §14.2.

#### Complete Java Code
```java
package module07.examples;

public class EdgeCaseEmptyBlock {
    public static void main(String[] args) {
        System.out.println("Before empty block");
        {
            // Syntactically valid block statement
            // Emits zero bytecode instructions
        }
        System.out.println("After empty block");
    }
}
```
- **Explanation:** `javac` accepts `{}` as an empty compound statement. During code generation, it produces no bytecode opcodes.
- **Expected Output:**
  ```text
  Before empty block
  After empty block
  ```

---

### 7.2 Standalone Block for Intermediate Lifetime Management
Developers can place standalone blocks anywhere in a method body to strictly restrict variable lifetime.

#### Complete Java Code
```java
package module07.examples;

public class EdgeCaseStandaloneBlock {
    public static void main(String[] args) {
        long computedChecksum;

        {
            // scratch buffer only needed for computing checksum
            byte[] scratch = new byte[]{1, 2, 3, 4, 5};
            long acc = 0;
            for (byte b : scratch) {
                acc += b;
            }
            computedChecksum = acc;
        } // 'scratch' falls out of scope, releasing reference for GC

        System.out.println("Checksum: " + computedChecksum);
    }
}
```
- **Explanation:** Placing large arrays in standalone blocks drops their reference from active scope immediately upon block exit, making them eligible for GC in long-running methods.

---

### 7.3 Final Local Variable with Deferred Initialization in Branches
A `final` local variable is a **blank final** if declared without an initializer. It can be assigned **once** across conditional branches.

#### Complete Java Code
```java
package module07.examples;

public class EdgeCaseBlankFinal {
    public static void main(String[] args) {
        final int mode; // Blank final local variable
        boolean highPriority = true;

        if (highPriority) {
            mode = 1; // Legal: exactly one assignment
        } else {
            mode = 2; // Legal: exactly one assignment
        }

        // mode = 3; // COMPILE ERROR: cannot assign a value to final variable mode
        System.out.println("Assigned final mode: " + mode);
    }
}
```
- **Explanation:** JLS §16 enforces **definite unassignment** (DU) before the assignment and **definite assignment** (DA) before any read.

---

### 7.4 Static Field Initialized via Instance Method Call
Attempting to initialize a static field by calling an instance method without an instance is an illegal reference.

#### Complete Java Code
```java
package module07.examples;

public class EdgeCaseStaticInitError {
    public int compute() {
        return 42;
    }

    // Attempting:
    // public static int VALUE = compute();
    // COMPILE ERROR: non-static method compute() cannot be referenced from a static context

    // Fix: Make method static OR instantiate an object
    public static int COMPUTE_STATIC() {
        return 42;
    }

    public static int VALUE = COMPUTE_STATIC(); // Legal

    public static void main(String[] args) {
        System.out.println("Value: " + VALUE);
    }
}
```
- **Explanation:** Static initializers execute during class loading (`<clinit>`), long before any instance exists. Calling an instance method requires `this`, which is absent during class loading.

---

### 7.5 Referencing `this` Before `super()` in Constructors
In Java, referencing `this` before the superclass constructor completes is prohibited (JLS §8.8.7.1).

#### Complete Java Code
```java
package module07.examples;

class Parent {
    public Parent(String id) {
        System.out.println("Parent initialized with: " + id);
    }
}

public class EdgeCaseThisBeforeSuper extends Parent {
    private String myId;

    public EdgeCaseThisBeforeSuper(String id) {
        // Attempting:
        // super(this.myId);
        // COMPILE ERROR: cannot reference this before supertype constructor has been called

        super(id); // Legal: pass argument directly
        this.myId = id;
    }

    public static void main(String[] args) {
        new EdgeCaseThisBeforeSuper("DEMO-001");
    }
}
```
- **Compiler / JVM Mechanics:** The JVM requires `invokespecial` to `Super."<init>"` to initialize the supertype fields before allowing arbitrary access to the child instance state. *(Note: Java 22+ preview JEP 447 allows statements before `super()`, but referencing `this` remains strictly restricted).*

---

### 7.6 Multi-Variable For Loop Header Scope
A `for` loop header can declare multiple variables, but they **must all share the same type declaration** (JLS §14.14.1).

#### Complete Java Code
```java
package module07.examples;

public class EdgeCaseMultiVarForLoop {
    public static void main(String[] args) {
        // Legal: both i and j are ints
        for (int i = 0, j = 10; i < j; i++, j--) {
            System.out.println("i = " + i + ", j = " + j);
        }

        // The following is a COMPILE ERROR because types differ:
        // for (int i = 0, double d = 0.5; i < 5; i++) { } // error: <identifier> expected
    }
}
```
- **Explanation:** Comma-separated variable declarations in a `for` loop header share a single type specifier. Both variables exist in the same scope and terminate together when the loop finishes.
- **Expected Output:**
  ```text
  i = 0, j = 10
  i = 1, j = 9
  i = 2, j = 8
  i = 3, j = 7
  i = 4, j = 6
  ```

---

## Scope Rules Summary & Quick Reference Table

| Scope Tier | Lexical Boundary | Storage Location | Lifetime | Shadowing / Hiding Behavior |
| :--- | :--- | :--- | :--- | :--- |
| **Block Scope** | Enclosing `{ ... }` | Stack Frame Slot (Reused) | Block Entry to Block Exit | Shadowing enclosing local variables is **forbidden** |
| **Method Parameter Scope** | Method signature & body | Stack Frame Slots (`0`..`N`) | Method invocation | Cannot redeclare inside method; shadows class fields |
| **Local Variable Scope** | Declaration to end of block | Stack Frame Slots (`0`..`N`) | Point of declaration to block exit | Cannot redeclare in nested blocks; shadows class fields |
| **Loop Control Scope** | `for(...)` header & loop body | Stack Frame Slot | Loop start to loop completion | Sibling loops can reuse same identifier |
| **Instance Field Scope** | Entire declaring class | Object layout on Heap | Object instantiation to Garbage Collection | Subclasses **hide** (not override) via same identifier |
| **Static Field Scope** | Entire declaring class & JVM | `java.lang.Class` on Heap | Class load (`<clinit>`) to ClassLoader unload | Subclasses **hide** via same identifier |
| **Pattern Variable Scope** | Flow-sensitive `whenTrue` | Stack Frame Slot | Where pattern match is proven true | Local to matching branch; cannot be used in `||` |
| **Resource Scope** | `try(...)` header & try block | Stack Frame Slot | Try block entry to synthetic finally | Inaccessible in `catch` and `finally` blocks |

---

## IntelliJ IDEA Scope Inspection Shortcuts
- **Inspect Variable Scope:** `Ctrl + B` / `Cmd + B` (Go to Declaration or Usages)
- **Data Flow Analysis to Here:** `Code` -> `Analyze Code` -> `Data Flow to Here...`
- **Highlight Element Usages in File:** `Ctrl + Shift + F7` / `Cmd + Shift + F7`
- **Show Bytecode with JVM Slots:** `View` -> `Show Bytecode` (or use ASM Bytecode plugin)
