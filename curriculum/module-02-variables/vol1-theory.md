# MODULE 2: VARIABLES — Volume 1: Theory & Internals

> [!IMPORTANT]  
> **Prerequisites:** Completion of Module 1. This module targets **Java 21 LTS** and is designed for an engineering depth of understanding. We will not just look at syntax, but what happens within the `javac` compiler and the JVM at runtime.

---

## PART 1: DEFINITION

### What is a Variable?
At its core, a variable is a **named storage location in memory** that holds a value of a specific, declared type. It is the fundamental unit of state in any imperative programming language. In Java, this state is strongly typed, meaning the compiler must know the type of every variable, which dictates the size of the memory allocated and the operations permitted on that memory.

### Why Do Programming Languages Have Variables?
Variables solve the problem of **memory address abstraction**. 
At the hardware level, the CPU operates on raw memory addresses (e.g., `0x7FFF0042`). Programming directly with memory addresses is not only tedious but extremely error-prone and non-portable. Variables provide human-readable, symbolic names to refer to these memory locations. They allow the compiler and runtime to manage the physical memory layout dynamically, abstracting away register allocation and stack/heap management from the developer.

### Real-World Analogy
Think of variables as **labeled containers or boxes with strict shape constraints**.
- The **label** on the box is the identifier (the name).
- The **shape constraint** of the box is the data type (an `int` box can only hold integers; it physically cannot fit a `String`).
- The **contents** of the box is the value.

### History: How Java's Variable System Differs from C/C++
Java was designed by James Gosling in the mid-1990s as a reaction to the complexities and pitfalls of C and C++.
- **No Pointers:** In C/C++, you can have variables that point to arbitrary memory locations, allowing pointer arithmetic. This leads to buffer overflows, segmentation faults, and massive security vulnerabilities. Java eliminates exposed pointers; it uses managed references.
- **Strict Type Safety:** C allows implicit, unsafe casting between vastly different types. Java's compiler strictly enforces type constraints.
- **No Undefined Behavior (UB):** Reading an uninitialized local variable in C reads whatever garbage data happens to be in that memory location. Java strictly enforces **definite assignment**, preventing compilation if a variable might be read before being written to.
- **Deterministic Sizing:** A C `int` might be 16-bit, 32-bit, or 64-bit depending on the CPU architecture and OS. A Java `int` is *always* exactly 32-bit, guaranteed by the JVM Specification, write-once-run-anywhere (WORA).

### Two Fundamental Categories of Variables
Java has exactly two categories of variables, and understanding the difference is paramount for JVM mastery.

1. **Primitive Variables:** The variable box contains the *actual value* itself. 
2. **Reference Variables:** The variable box contains a *reference* (a managed memory address/handle) that points to an object located elsewhere (on the Heap).

> [!TIP]  
> **Why this distinction matters:** It governs performance, memory layout, and Garbage Collection. Primitive variables are fast and stack-allocated (usually). Reference variables require following a pointer to the heap, which incurs cache misses, and the objects they point to require GC tracking.

```ascii
Primitive vs Reference (Stack vs Heap)
======================================
      THREAD STACK               HEAP MEMORY
      ------------               -----------
      
 [Primitive Variable]
 +------------------+
 | int x = 42       | (Value 42 is right here on the stack)
 +------------------+
 
 [Reference Variable]
 +------------------+           +--------------------+
 | String s =  *----|---------->| Object Header      |
 +------------------+           | char[] "Hello"     |
                                +--------------------+
```

---

## PART 2: SYNTAX — EVERY DETAIL

### Identifiers
An identifier is the symbolic name given to variables, methods, classes, interfaces, and packages. 

**Rules for Valid Identifiers in Java:**
1. **Start Character:** Must begin with a letter (A-Z, a-z), an underscore (`_`), or a dollar sign (`$`).
2. **Subsequent Characters:** Can contain letters, digits (0-9), underscores (`_`), or dollar signs (`$`).
3. **Numbers:** CANNOT start with a digit.
4. **Reserved Words:** CANNOT be a Java keyword or reserved word.
5. **Case:** Identifiers are strictly case-sensitive (`myVar` is different from `MyVar`).
6. **Length:** No specified limit in the Java Language Specification (JLS), though class files have a `CONSTANT_Utf8_info` length limit of 65,535 bytes.
7. **Unicode:** Identifiers support full Unicode. You can name a variable using Arabic, Japanese, or Cyrillic characters if they qualify as `Character.isJavaIdentifierStart()`.

**20+ Valid vs Invalid Identifier Examples:**

| Identifier | Valid? | Reason / Convention |
| :--- | :---: | :--- |
| `count` | ✅ | Valid, standard camelCase convention. |
| `MAX_SIZE` | ✅ | Valid, standard UPPER_SNAKE_CASE convention for constants. |
| `$price` | ✅ | Valid, but `$` is traditionally reserved for compiler-generated code (e.g., inner classes). |
| `_internalState` | ✅ | Valid, common for private fields in some codebases. |
| `1stPlace` | ❌ | Invalid. Cannot start with a digit. |
| `total%` | ❌ | Invalid. Contains an illegal character `%`. |
| `my-var` | ❌ | Invalid. Hyphens are not allowed (treated as minus operator). |
| `public` | ❌ | Invalid. `public` is a reserved keyword. |
| `Public` | ✅ | Valid (Java is case-sensitive), but terrible practice. |
| `String` | ✅ | Valid! `String` is a class name, not a keyword. But highly discouraged. |
| `int` | ❌ | Invalid. `int` is a primitive type keyword. |
| `var` | ✅ | Valid! `var` is a *reserved type name*, not a keyword. |
| `yield` | ✅ | Valid, though it's a restricted identifier/contextual keyword since Java 14. |
| `データ` | ✅ | Valid. Unicode characters are perfectly legal. |
| `_` | ⚠️ | Valid in Java 8, restricted in Java 9+, used for unnamed variables in Java 22+. |
| `foo_bar_123` | ✅ | Valid. |
| `$1` | ✅ | Valid, often used by generated code or regex group references. |
| `java.lang` | ❌ | Invalid. Contains a dot `.`. |
| `goto` | ❌ | Invalid. Reserved keyword (even though it does nothing in Java). |
| `const` | ❌ | Invalid. Reserved keyword (unused). |
| `true` | ❌ | Invalid. Boolean literal. |

> [!CAUTION]  
> **The Underscore `_`:** As of Java 9, a single underscore `_` is a keyword and cannot be used as an identifier name. However, as of **Java 22 (JEP 456)**, `_` is reintroduced specifically as the **unnamed variable** pattern (e.g., `try (var _ = lock.lock()) { ... }`).

**Naming Conventions:**
- Variables and Methods: `camelCase` (e.g., `employeeName`).
- Classes and Interfaces: `PascalCase` (e.g., `ArrayList`).
- Constants (`static final`): `UPPER_SNAKE_CASE` (e.g., `MAX_CAPACITY`).

### Java Keywords

Java has 67 keywords and reserved words (as of Java 21). A keyword is a sequence of characters that has a special, predefined meaning to the `javac` compiler.

| Category | Keywords |
| :--- | :--- |
| **Primitives** | `boolean`, `byte`, `char`, `double`, `float`, `int`, `long`, `short` |
| **Access Modifiers** | `private`, `protected`, `public` |
| **Class/Object** | `class`, `extends`, `implements`, `interface`, `new`, `super`, `this`, `record` (contextual) |
| **Control Flow** | `break`, `case`, `continue`, `default`, `do`, `else`, `for`, `if`, `return`, `switch`, `while`, `yield` (contextual) |
| **Exceptions** | `catch`, `finally`, `throw`, `throws`, `try` |
| **Packages/Modules** | `import`, `package`, `module`, `requires`, `exports`, `opens`, `uses`, `provides`, `with`, `to`, `transitive` (contextual) |
| **Modifiers** | `abstract`, `final`, `native`, `static`, `strictfp`, `synchronized`, `transient`, `volatile`, `sealed`, `non-sealed`, `permits` (contextual) |
| **Misc** | `assert`, `enum`, `instanceof` |
| **Unused/Reserved** | `goto`, `const` |
| **Reserved Type Name** | `var` |

> [!NOTE]  
> **Contextual Keywords:** Words like `module`, `var`, `yield`, `record`, `sealed` are *contextual*. They act as keywords only in specific syntax structures but can still be used as variable names elsewhere. This ensures backward compatibility for older code that might have used `int record = 5;`.

> [!WARNING]  
> **Misconception:** `true`, `false`, and `null` are **NOT** keywords. They are reserved literal values. You cannot name a variable `true`, but technically, they belong to the literal grammar rules, not the keyword list.

### Variable Declaration
Variables must be declared before they can be used.

**Syntax Variations:**
1. **Declaration only:** `type variableName;` (e.g., `int count;`)
2. **Declaration + Initialization:** `type variableName = value;` (e.g., `double price = 19.99;`)
3. **Multiple Declarations:** `int a, b, c;` or `int x = 1, y = 2;`

**What happens under the hood:**
- **In `javac` (Compile Time):** When the compiler sees `int count;`, it enters the identifier `count` into the AST (Abstract Syntax Tree) and symbol table for the current lexical scope, annotating it with the type `int`.
- **In JVM (Runtime):** For local variables, the JVM allocates a specific slot (usually 32 bits, or two slots for 64-bit types like `long`/`double`) in the **Local Variable Array** of the stack frame created for the method execution.

### Primitive Variables (The 8 Types)
Primitive types map directly to hardware-level data sizes. They are not objects; they have no methods, no object headers, and avoid heap allocation overhead (when stored as locals).

| Type | Size (bytes) | Range | Default Value | Wrapper Class | JVM Internal |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `byte` | 1 | -128 to 127 | `0` | `Byte` | Extended to `int` on stack |
| `short` | 2 | -32,768 to 32,767 | `0` | `Short` | Extended to `int` on stack |
| `char` | 2 | `\u0000` to `\uffff` (0 to 65535) | `\u0000` | `Character` | Extended to `int` on stack |
| `int` | 4 | -2^31 to 2^31-1 (~ -2.1B to 2.1B) | `0` | `Integer` | 1 stack slot |
| `long` | 8 | -2^63 to 2^63-1 | `0L` | `Long` | 2 stack slots |
| `float` | 4 | IEEE 754 32-bit floating point | `0.0f` | `Float` | 1 stack slot |
| `double`| 8 | IEEE 754 64-bit floating point | `0.0d` | `Double` | 2 stack slots |
| `boolean`| JVM specific (1 byte in arrays) | true / false | `false` | `Boolean` | Stored as `int` (1 or 0) on stack |

> [!IMPORTANT]  
> **JVM Internals:** The JVM stack operates fundamentally on 32-bit computational units (slots). Therefore, local variables of type `byte`, `short`, `char`, and `boolean` are actually widened and stored as 32-bit `int`s inside the local variable array of the stack frame. Only arrays of these types are packed densely on the heap.

### Reference Variables
A reference variable does not hold the object's data. It holds a pointer-like handle to where the object lives on the heap.

**Size of a Reference:**
The size of a reference variable depends on the JVM architecture and configuration:
- 32-bit JVM: Reference is 32 bits (4 bytes).
- 64-bit JVM: Reference is 64 bits (8 bytes).
- **Compressed Oops:** On 64-bit JVMs with heap sizes under 32GB, the JVM enables `UseCompressedOops` by default. It uses 32-bit offsets (shifted by 3) to address the heap, keeping reference variables at 32 bits (4 bytes) to save memory and improve CPU cache performance.

**The `null` Reference:**
`null` is a special literal representing the absence of an object. At the JVM level, this is typically implemented as a zero pointer (`0x0`). Attempting to dereference it (call a method or access a field) throws a `NullPointerException` (which the JVM traps via OS-level hardware segmentation faults and translates into an exception!).

**Aliasing:**
Multiple reference variables can point to the exact same object in memory. Modifying the object through one reference makes the change visible to the other.

### Declaration vs Initialization vs Assignment
- **Declaration:** Registering the variable with the compiler (`int x;`).
- **Initialization:** The *first* time a value is written to the variable.
- **Assignment:** Writing a value to the variable (includes initialization and subsequent updates, e.g., `x = 10;`).

**Definite Assignment:**
Java enforces **Definite Assignment** for local variables. The compiler performs control-flow analysis to ensure every possible path of execution initializes a local variable before its value is read.
```java
int x;
if (condition) {
    x = 10;
}
// COMPILER ERROR: Variable x might not have been initialized
System.out.println(x); 
```

**Default Values:**
Unlike local variables, instance (object) variables and static (class) variables are automatically initialized to default values when the object/class is created.

| Variable Type | Gets Default Value? | Default Value |
| :--- | :--- | :--- |
| Local Variable | ❌ NO (Compiler Error) | N/A |
| Instance Field | ✅ YES | `0`, `0.0`, `false`, `null` |
| Static Field | ✅ YES | `0`, `0.0`, `false`, `null` |

### Constants (`final`)
The `final` keyword makes a variable's assignment immutable. Once initialized, its value (or reference) cannot change.

- **Local final variables:** Must be initialized exactly once.
- **Instance final variables:** Must be initialized either at the point of declaration, in an initialization block, or in *every* constructor. (Also known as "blank finals" if not initialized at declaration).
- **Static final variables:** Compile-time constants or runtime initialized class constants.

**Compile-time vs Runtime Constants:**
```java
final int MAX_USERS = 100; // Compile-time constant
final long START_TIME = System.currentTimeMillis(); // Runtime constant
```
For compile-time constants (primitive or String literals marked `final`), `javac` performs **Constant Folding**. It completely removes the variable reference and directly injects the literal value `100` into the bytecode wherever `MAX_USERS` is used.

**Final References:**
> [!CAUTION]  
> A `final` reference variable means the *pointer* cannot change. It does NOT mean the object itself is immutable.
```ascii
Final Reference Diagram
=======================
final List<String> list = new ArrayList<>();

[Stack Frame]                 [Heap]
+-----------------+           +--------------------+
| list = 0x88F0 --|-----X---->| (Another ArrayList)| -> CANNOT reassign reference
+-----------------+     |     +--------------------+
                        |
                        V
                      +--------------------+
                      | ArrayList Object   |
                      | size: 0 -> 1       | -> CAN modify object contents (list.add())
                      +--------------------+
```

### `var` (Local Variable Type Inference)
Introduced in Java 10, `var` allows the compiler to infer the type of a local variable based on the right-hand side of the initialization.

- **Syntax:** `var message = "Hello";` (Compiler infers `String`).
- **Where it works:** Local variables with initializers, enhanced `for` loops, try-with-resources.
- **Where it FAILS:** Method parameters, return types, class fields, uninitialized variables (`var x;`), null initialization (`var x = null;`).

**Important internal truth:** `var` is pure syntactic sugar for the compiler. The resulting bytecode is **100% identically typed** to an explicitly declared variable. Java remains strictly, statically typed.

**The Diamond Problem:**
```java
var list = new ArrayList<>(); // DANGER!
```
Because the right side has no explicit generic type, `javac` infers `ArrayList<Object>`. You must provide the type on the right: `var list = new ArrayList<String>();`

### Variable Scope
A variable's scope defines where in the code it is accessible. Its lifetime dictates how long it exists in memory.
1. **Local Scope:** Exists within a method block. Lifetime is the execution of the method.
2. **Block Scope:** Exists within `{}` (e.g., `if`, `for`). Lifetime is the execution of the block.
3. **Instance Scope:** Fields of an object. Lifetime spans from `new` to Garbage Collection.
4. **Static Scope:** Fields of a class. Lifetime spans from class loading to class unloading.

---

## PART 3: INTERNAL WORKING

### How `javac` Handles Variables
When you write `int x = 42;`, the Java compiler goes through several phases:

```mermaid
flowchart TD
    A["Lexical Analysis"] -->|Tokens: 'int', 'x', '=', '42', ';'| B("Parsing")
    B -->|AST Creation| C{"Enter Phase"}
    C -->|Symbol Table Population (Name: x, Type: int)| D["Attr Phase"]
    D -->|Type checking, 'var' inference| E["Flow Analysis"]
    E -->|Definite Assignment Checks| F["Bytecode Gen"]
    F -->|Assign local variable slot| G[(.class File)]
```

### Bytecode for Variables
The JVM is a stack-based machine. It uses specific bytecode instructions to load from and store into the local variable array.

Because the JVM is strictly typed, it has different opcodes for different types:
- Int/Boolean/Byte/Short/Char: `iload`, `istore`
- Float: `fload`, `fstore`
- Double: `dload`, `dstore`
- Long: `lload`, `lstore`
- Reference: `aload`, `astore`

**Example:** `int x = 42;`
```bytecode
 0: bipush        42    // Push byte constant 42 onto operand stack
 2: istore_1            // Pop 42 and store into local variable array at index 1
```

**Local Variable Array Indexing:**
- Slot 0 contains `this` (in instance methods).
- Method parameters take the next slots.
- Local variables take the remaining slots.

### Memory Layout (ASCII Diagrams)

**1. Stack Frame (Local Variables):**
```ascii
Execution of: void calculate(int amount, String user) { double tax = 0.5; }

+-----------------------------------------+
| STACK FRAME                             |
|-----------------------------------------|
| Operand Stack: [ empty ]                |
|                                         |
| Local Variable Array:                   |
| [Slot 0] reference: 'this' (Object)     |
| [Slot 1] int: 'amount'                  |
| [Slot 2] reference: 'user' (String)     |
| [Slot 3] double: 'tax' (Part 1)         |
| [Slot 4] double: 'tax' (Part 2)         | <- Double/Long take 2 slots
+-----------------------------------------+
```

**2. Heap Object Layout (Instance Variables):**
Assuming a 64-bit JVM with Compressed Oops enabled.
```ascii
class Point { int x; int y; }
Point p = new Point();

+-----------------------------------------+
| HEAP MEMORY (Point Object)              |
|-----------------------------------------|
| [Object Header - Mark Word] (8 bytes)   | <- Identity Hash, GC Age, Locks
| [Object Header - Klass Ptr] (4 bytes)   | <- Pointer to Class Metadata
| [Instance Field - int x   ] (4 bytes)   |
| [Instance Field - int y   ] (4 bytes)   |
| [Padding                  ] (4 bytes)   | <- Objects aligned to 8-byte boundaries
+-----------------------------------------+
| TOTAL SIZE: 24 bytes                    |
+-----------------------------------------+
```

**3. Total Memory Overview:**
```ascii
+----------------+      +----------------+      +----------------+
|  JVM STACK     |      |  JVM HEAP      |      | METASPACE      |
| (Per Thread)   |      | (Shared)       |      | (Shared)       |
|                |      |                |      |                |
| [Frame: main]  |      |  [String Obj]  |      | [Class: Main]  |
| local int a=5  |      |   "hello" <-------+   | static int MAX |
| local String s --ref--+->[Person Obj]  |  |   | method code    |
|                |      |   String name -+--+   | constant pool  |
+----------------+      +----------------+      +----------------+
```

### Constant Pool Interaction
The `.class` file contains a Constant Pool.
When you declare `String s = "hello";`, the string literal `"hello"` is placed in the Constant Pool. At runtime, the JVM intern system ensures only one instance of `"hello"` exists in the Heap's String Pool. The variable `s` simply points to it.

For `static final int MAX = 100;`, `javac` completely removes references to `MAX` in other classes during compilation and replaces them with `100`.

### Garbage Collection Implications
- **Primitive Locals:** Have ZERO GC overhead. When the method returns, the stack frame is popped, and memory is instantly reclaimed.
- **Reference Locals:** When the method returns, the reference variables disappear. If no other references exist to the Heap object, that object becomes *eligible* for Garbage Collection.

> [!TIP]  
> **Setting variables to `null`:** Older developers used to write `obj = null;` at the end of large methods to help the GC. In modern JVMs, the JIT (Just-In-Time) compiler performs liveness analysis. It knows exactly when a local variable is read for the last time, and it implicitly makes the object GC-eligible even before the method returns. Setting to `null` is generally unnecessary clutter today unless dealing with arrays/object pools.

---

## PART 4: SUMMARY & DECISION MATRICES

### `var` vs Explicit Typing

| Feature | `var` | Explicit Type (`int`, `String`) |
| :--- | :--- | :--- |
| **Readability (LHS)** | Clean, aligns variable names | Cluttered for long class names |
| **Readability (RHS)** | Requires context on right side | Clear regardless of right side |
| **Compiler behavior** | Inferred during Attr phase | Verified during Attr phase |
| **Bytecode** | Identical | Identical |
| **Best Used When** | `var map = new HashMap<String, List<Integer>>();` | `int count = 0;` (primitives) |

### Definite Assignment Flow

```ascii
[Compiler Definite Assignment Tracking]

   int result;
       |
       v
  [ if (x > 0) ] ---> (True path: result = 1;)
       |                     |
  (False path)               |
       |                     |
       +<--------------------+
       |
[ read result ] --> ERROR! Compiler sees the 'False path' 
                    never initialized the variable.
```

Variables are the bedrock of Java memory management. By understanding how the compiler maps identifiers, how the bytecode interacts with stack slots, and how the physical memory differentiates between primitives and references, you transition from writing syntax to engineering state.
