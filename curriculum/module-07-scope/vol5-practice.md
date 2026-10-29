# Module 7: Scope — Volume 5 (Practice Exercises)

**Target:** Java 21 LTS | IntelliJ IDEA  
**Author:** Senior Java Engineer, JVM Engineer & OpenJDK Compiler Engineer  

> *Note: These exercises contain problem descriptions, technical constraints, JVM mechanics, and verification criteria only. No solutions are provided in this volume.*

---

## 1. SMALL EXERCISES (10)

### Exercise 1.1: Block Scope and Lexical Boundaries
- **Objective:** Construct a standalone Java class (`BlockScopeLab`) demonstrating lexical visibility across three nested levels of blocks: an outer method block, an intermediate block, and an inner block.
- **Requirements:**
  1. Declare an integer `level1` in the outer method block.
  2. Open an explicit block `{ ... }` and declare `level2`. Demonstrate reading both `level1` and `level2`.
  3. Open a third nested block `{ ... }` and declare `level3`. Read all three variables.
  4. At each level boundary exit, demonstrate which variables cease to be accessible.
  5. Include commented-out statements attempting to access `level3` from the intermediate block and `level2` from the outer block, explaining the exact compilation failure emitted by `javac` (e.g., `cannot find symbol`).
- **Verification:** Compile with `javac -Xlint:all BlockScopeLab.java` and execute to verify proper output values.

---

### Exercise 1.2: For-Loop Index Variable Lifecycle
- **Objective:** Demonstrate the exact scope boundaries of loop control variables in standard `for` loops and enhanced `for-each` loops.
- **Requirements:**
  1. Write a standard counted loop: `for (int i = 0; i < 5; i++)`. Print `i`.
  2. Immediately following the loop, write a commented line attempting `System.out.println(i);`. Document the compiler diagnostic.
  3. Declare a second consecutive counted loop using the identifier `int i = 0;` to prove that the identifier `i` can be legitimately declared again because the prior `i` went out of scope.
  4. Write an enhanced `for` loop over a `List<String>` and verify the iteration variable's visibility outside the loop.
  5. Contrast this with declaring `int j;` *before* the loop and using `for (j = 0; j < 5; j++)`, showing how `j` leaks beyond the loop construct.
- **Verification:** Ensure the source file compiles cleanly when illegal access lines are commented out.

---

### Exercise 1.3: Definite Assignment and Local Variable Initialization
- **Objective:** Prove Java Language Specification (JLS §16) Definite Assignment rules for local variables.
- **Requirements:**
  1. Declare a local variable `int uninitializedLocal;` without an initializer.
  2. Attempt to print `uninitializedLocal` immediately and capture the compiler error message (`variable uninitializedLocal might not have been initialized`).
  3. Introduce a boolean condition `boolean condition = args.length > 0;`.
  4. Assign `uninitializedLocal = 100;` inside the `if` block, but provide no `else` branch. Attempt to read `uninitializedLocal` after the conditional block and verify the compilation error.
  5. Provide the fix: guarantee initialization in both `if` and `else` branches, or supply a default assignment at declaration time.
- **Verification:** Verify that `javac` successfully compiles only when every execution path guarantees initialization prior to first read.

---

### Exercise 1.4: Default Initialization of Fields vs. Local Variables
- **Objective:** Contrast heap-allocated class/instance field zero-initialization with stack-allocated local variable uninitialized states.
- **Requirements:**
  1. Create a class `FieldDefaultsLab` declaring uninitialized fields of types: `byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`, and `Object` (reference).
  2. In an instance method, print all fields without assigning values to confirm JVM specification defaults (`0`, `0.0`, `\u0000`, `false`, `null`).
  3. In the same method, declare local variables corresponding to each of those types without initializers.
  4. Show that attempting to print any of the local variables results in compile-time errors.
  5. Explain in source code comments why the JVM guarantees zeroing for heap allocations (security and memory sandbox safety) while local variable stack frames avoid zeroing overhead for performance.
- **Verification:** Output must show formatted primitive defaults alongside reference `null`s.

---

### Exercise 1.5: Field Shadowing and the `this` Qualifier
- **Objective:** Demonstrate variable shadowing where a method parameter or local variable masks an instance field with the identical identifier.
- **Requirements:**
  1. Create a class `Account` with private instance fields `id` (String) and `balance` (double).
  2. Implement a constructor `public Account(String id, double balance)`.
  3. Inside the constructor, write `id = id;` and `balance = balance;` without `this.`.
  4. Instantiate the object and demonstrate that `balance` remained `0.0` and `id` remained `null` because the parameters assigned to themselves (self-assignment).
  5. Refactor the constructor using `this.id = id;` and `this.balance = balance;` to properly qualify field writes.
  6. Add a setter method where a local variable also shadows the field, and use `this` to update the state.
- **Verification:** Run a test printing instance variables before and after qualifying with `this`.

---

### Exercise 1.6: Static vs. Instance Lexical Scope Boundaries
- **Objective:** Demonstrate the lexical constraints separating static contexts from instance contexts.
- **Requirements:**
  1. Create a class `ScopeContextLab` with an instance field `int instanceCounter = 1;` and a static field `static int globalCounter = 10;`.
  2. Define an instance method `void displayInstance()` that reads both `instanceCounter` and `globalCounter`.
  3. Define a static method `static void displayStatic()` that attempts to read `instanceCounter` directly and calls `displayInstance()` directly.
  4. Document the exact compiler diagnostics (`non-static variable instanceCounter cannot be referenced from a static context`).
  5. Fix the static method by explicitly instantiating `ScopeContextLab lab = new ScopeContextLab();` and accessing `lab.instanceCounter`.
  6. Attempt to use the `this` keyword inside `displayStatic()` and document the resulting compiler error.
- **Verification:** The class must cleanly compile and print both static and instance state via the instantiated reference.

---

### Exercise 1.7: The Switch Fall-Through Scope Leak
- **Objective:** Illustrate the lexical scoping hazard of the legacy switch statement syntax where variables declared in one case label bleed into subsequent labels.
- **Requirements:**
  1. Write a method `processStatus(int statusCode)` using legacy switch syntax (`switch (statusCode) { case 1: ... case 2: ... }`).
  2. Under `case 1:`, declare `int payload = 100;` without enclosing braces.
  3. Under `case 2:`, declare another variable `int payload = 200;`. Show that the compiler rejects this duplicate declaration (`variable payload is already defined in method ...`).
  4. Under `case 2:`, remove the declaration and simply read `payload`. Show that if `statusCode == 2`, execution jumps to `case 2`, where `payload` is in scope but potentially uninitialized if the declaration lacked an accessible initialization.
  5. Enclose `case 1:` inside explicit braces `{ int payload = 100; ... }` to prove that block scoping isolates the variable.
- **Verification:** Execute with different status codes to observe control flow and lexical scope boundaries.

---

### Exercise 1.8: Switch Expressions and Isolated Scope Rules
- **Objective:** Refactor legacy switch statements to Java 21 switch expressions with arrow syntax (`->`), demonstrating isolated branch scoping.
- **Requirements:**
  1. Write a method `evaluateToken(String token)` that utilizes a switch expression returning an integer score.
  2. In rule branch `"ALPHA" -> { ... }`, declare a local variable `int weight = 5;` and yield a computed result.
  3. In rule branch `"BETA" -> { ... }`, declare another local variable named `int weight = 10;` and yield a result.
  4. Demonstrate that the compiler allows identical variable names across branches because each rule arm `{ ... }` forms an independent lexical scope.
  5. For single-expression branches (`"GAMMA" -> 42;`), show that no local variables can be declared without a block.
- **Verification:** Compile with Java 21 flags and verify clean compilation without scope collisions.

---

### Exercise 1.9: Effectively Final Local Variables and Closures
- **Objective:** Demonstrate variable capture semantics in lambda expressions and anonymous inner classes under JLS §4.12.4.
- **Requirements:**
  1. Define a method `createGreeter(String prefix)`.
  2. Declare a local variable `String punctuation = "!";`.
  3. Return a `Function<String, String>` lambda: `name -> prefix + " " + name + punctuation`.
  4. Immediately after defining the lambda, write `punctuation = "?";`.
  5. Observe and document the compiler error: `local variables referenced from a lambda expression must be final or effectively final`.
  6. Revert the reassignment to restore "effectively final" status.
  7. Show how to work around the restriction legitimately when mutability is required (e.g., using an `AtomicReference` or a single-element array `String[]`).
- **Verification:** Execute the lambda through a test driver and print the captured greeting.

---

### Exercise 1.10: Pattern Variable Flow Scoping
- **Objective:** Explore the flow-sensitive scoping rules of Java 16+ / 21 pattern variables (`instanceof`).
- **Requirements:**
  1. Write a method `processInput(Object obj)`.
  2. Use pattern matching: `if (obj instanceof String s && s.length() > 5)`. Demonstrate that `s` is immediately available in the right-hand operand of `&&`.
  3. Contrast this with `if (obj instanceof String s || s.length() > 5)` and document why the compiler rejects `s` in the right-hand operand of `||`.
  4. In the `then` block, invoke `s.toUpperCase()`.
  5. In the `else` block, attempt to reference `s` and document the compiler error (`cannot find symbol`).
  6. In a separate method, demonstrate early-exit flow scoping:
     ```java
     if (!(obj instanceof String s)) {
         return;
     }
     // Show that 's' remains in scope here for the rest of the method!
     System.out.println(s.toLowerCase());
     ```
- **Verification:** Execute the method passing various object types (`String`, `Integer`, `null`) and verify runtime flow.

---

## 2. MEDIUM EXERCISES (10)

### Exercise 2.1: The Unified Scope Matrix
- **Objective:** Implement a comprehensive class `UnifiedScopeMatrix` showcasing all five levels of Java scope interacting within a single file.
- **Requirements:**
  1. Define a class containing:
     - Class/Static scope variable: `static int counter = 100;`
     - Instance scope variable: `int counter = 200;`
     - Method parameter scope: `void execute(int counter)`
     - Method local variable scope: inner block re-scoping
     - Loop iteration scope: `for (int counter = 0; ...)`
  2. In method `execute`, demonstrate disambiguation of all five values:
     - Access parameter directly: `counter`
     - Access instance variable: `this.counter`
     - Access static variable: `UnifiedScopeMatrix.counter`
  3. Inside an internal block `{ ... }`, demonstrate whether Java permits re-declaring `int counter = 300;` when a parameter named `counter` is already in scope (verify compiler rejection).
  4. Print all values systematically to the console.
- **Verification:** Compile and run, ensuring clear distinction between all five scope tiers.

---

### Exercise 2.2: Shadowing vs. Hiding in Inheritance
- **Objective:** Build an inheritance hierarchy proving that instance fields and static fields/methods are *hidden*, while local variables *shadow* fields.
- **Requirements:**
  1. Create class `SuperDevice` with:
     - `public String model = "SuperDevice-v1";`
     - `public static String category = "GeneralDevice";`
     - `public static void identify() { System.out.println("SuperDevice Static"); }`
     - `public void describe() { System.out.println("SuperDevice Instance: " + model); }`
  2. Create subclass `SubDevice extends SuperDevice` with:
     - `public String model = "SubDevice-v2";` (field hiding)
     - `public static String category = "SmartDevice";` (static field hiding)
     - `public static void identify() { System.out.println("SubDevice Static"); }` (method hiding)
     - `@Override public void describe() { System.out.println("SubDevice Instance: " + model); }` (method overriding)
  3. In a driver method, execute calls on `SubDevice dev = new SubDevice();` and `SuperDevice poly = dev;`.
  4. Compare output of `poly.model`, `poly.category`, `poly.identify()`, and `poly.describe()`.
- **Verification:** Explain why `describe()` dispatches dynamically (virtual invocation) while fields and static methods dispatch statically based on reference type.

---

### Exercise 2.3: Non-Polymorphic Field Resolution
- **Objective:** Write a targeted experiment demonstrating that member variables in Java are never polymorphic.
- **Requirements:**
  1. Create a class `BaseNode` with `public int priority = 1;`.
  2. Create class `PriorityNode extends BaseNode` with `public int priority = 99;`.
  3. Instantiate `PriorityNode node = new PriorityNode();`.
  4. Print `node.priority`.
  5. Cast `((BaseNode) node).priority` and print the value.
  6. Define a method `int getPriority()` in both classes (overridden in `PriorityNode`).
  7. Print `((BaseNode) node).getPriority()` and observe that polymorphic method dispatch overrides the reference cast, whereas field access does not.
- **Verification:** Output must show `99` for `node.priority`, `1` for `((BaseNode) node).priority`, and `99` for `((BaseNode) node).getPriority()`.

---

### Exercise 2.4: Bytecode Inspection with `javap -l`
- **Objective:** Analyze the JVM `LocalVariableTable` attribute of a compiled class to understand scope ranges and variable lifetime offsets.
- **Requirements:**
  1. Write a class `BytecodeScopeTracker` containing a method:
     ```java
     public void compute() {
         int a = 10;
         if (a > 5) {
             int b = 20;
             System.out.println(a + b);
         }
         int c = 30;
         System.out.println(a + c);
     }
     ```
  2. Compile with debug symbols enabled: `javac -g BytecodeScopeTracker.java`.
  3. Execute `javap -l -c BytecodeScopeTracker` from the terminal.
  4. Extract and document the `LocalVariableTable`.
  5. Identify the `Start` bytecode offset, `Length` valid range, and local variable `Slot` index assigned to variables `this`, `a`, `b`, and `c`.
- **Verification:** Submit the annotated `javap` text output explaining why `b` and `c` share or do not share a slot.

---

### Exercise 2.5: Variable Slot Reuse in Sequential Blocks
- **Objective:** Prove that the JVM bytecode compiler optimizes stack frame allocation by reusing local variable array slots for non-overlapping block scopes.
- **Requirements:**
  1. Write a method `slotReclamation()` containing two consecutive blocks:
     ```java
     public void slotReclamation() {
         {
             String block1Var = "Scope 1";
             System.out.println(block1Var);
         }
         {
             Double block2Var = 3.14159;
             System.out.println(block2Var);
         }
     }
     ```
  2. Compile using `javac -g` and run `javap -v` to inspect `max_locals` and the `LocalVariableTable`.
  3. Note the slot index allocated to `block1Var` versus `block2Var`.
  4. Note that `Double` occupies two stack slots (category 2 computational type). Explain how slot alignment behaves when slot reuse occurs with 64-bit data types.
- **Verification:** Confirm from bytecode that `block2Var` begins at the exact slot vacated by `block1Var`.

---

### Exercise 2.6: Try-With-Resources Scoping vs. Traditional Try-Finally
- **Objective:** Compare the scope and accessibility of resources declared in modern `try (...)` headers against traditional manual management in `try-finally`.
- **Requirements:**
  1. Create a dummy resource `MockResource implements AutoCloseable`.
  2. Write method `traditionalScope()` declaring `MockResource res = null;` outside a try block, initializing it inside `try`, and closing it in `finally`.
     - Show that `res` leaks into the method scope after the `try-finally` block completes.
  3. Write method `modernScope()` using `try (MockResource res = new MockResource()) { ... }`.
     - Attempt to access `res` in a `catch` block.
     - Attempt to access `res` in a `finally` block.
     - Attempt to access `res` after the `try` statement.
     - Confirm that in all three locations, `res` is out of scope.
  4. Explain why closing before `catch`/`finally` execution in try-with-resources prevents resource-leak bugs during exception recovery.
- **Verification:** Demonstrate clean compilation with proper lifecycle output printing to stdout.

---

### Exercise 2.7: Lexical Scope and the Semantics of `this` in Inner Classes vs. Lambdas
- **Objective:** Demonstrate that inner classes introduce their own lexical scope and redefine `this`, whereas lambdas are lexically scoped to their enclosing class.
- **Requirements:**
  1. Create an outer class `ScopeContainer` with an instance field `String tag = "OUTER";`.
  2. In a method `testScopes()`, instantiate an anonymous `Runnable` inner class:
     - Declare an instance field `String tag = "INNER_ANON";` inside the anonymous class.
     - Print `this.tag`.
     - Print `ScopeContainer.this.tag`.
  3. In the same method, define a `Runnable` lambda:
     - Print `this.tag`.
     - Attempt to declare `String tag` inside the lambda body without braces or as a field (demonstrate that lambdas cannot have member fields).
     - Show that `this` in a lambda refers exclusively to the enclosing `ScopeContainer` instance.
- **Verification:** Execute both runnables and verify that anonymous class `this` prints `"INNER_ANON"`, while lambda `this` prints `"OUTER"`.

---

### Exercise 2.8: Comprehensive Access Modifier Matrix across Packages
- **Objective:** Create a multi-package hierarchy testing the visibility scope of `public`, `protected`, package-private (default), and `private` members.
- **Requirements:**
  1. Create package `pkg.alpha` with class `ParentClass`:
     - Declare four fields with values: `publicField`, `protectedField`, `defaultField`, `privateField`.
  2. Create class `AlphaNeighbor` in `pkg.alpha`:
     - Test accessibility of all four fields on an instance of `ParentClass`.
  3. Create package `pkg.beta` with class `SubClassInOtherPackage extends pkg.alpha.ParentClass`:
     - Test accessibility of all four fields via inheritance (`super.` or inherited member) versus via instance reference (`new ParentClass().field`).
     - Prove that `protected` members are accessible to subclasses in different packages *only* through inheritance or references of the subclass type, not via arbitrary base class references.
  4. Create class `BetaUnrelated` in `pkg.beta`:
     - Test accessibility of all four fields on an instance of `ParentClass`.
- **Verification:** Compile both packages and document the access table in an embedded text matrix.

---

### Exercise 2.9: Definite Assignment with Conditional Branches and `final`
- **Objective:** Test compiler verification of definite assignment (JLS §16) and definite unassignment on `final` local variables across complex control flow.
- **Requirements:**
  1. Declare a blank final local variable `final int MAX_LIMIT;`.
  2. Write a nested `if-else` tree where:
     - Path A assigns `MAX_LIMIT = 100;`
     - Path B assigns `MAX_LIMIT = 200;`
     - Path C assigns `MAX_LIMIT = 300;`
  3. Ensure all code paths converge and print `MAX_LIMIT`. Verify compilation succeeds.
  4. Introduce a fourth path without assignment and verify `javac` fails with `variable MAX_LIMIT might not have been initialized`.
  5. Attempt to reassign `MAX_LIMIT = 400;` inside a loop or after initialization, demonstrating compiler error `variable MAX_LIMIT might already have been assigned`.
- **Verification:** The class compiles cleanly only when the assignment is exhaustive and exactly once across all paths.

---

### Exercise 2.10: Pattern Variable Flow Scoping with Negation and Control Flow
- **Objective:** Demonstrate advanced flow scoping of pattern variables with negation, early return, loop guards, and boolean expressions.
- **Requirements:**
  1. Write a method `validateAndProcess(Object payload)`:
     - Guard clause: `if (!(payload instanceof Map<?, ?> map && !map.isEmpty())) throw new IllegalArgumentException();`
     - Below the guard clause, directly call `map.keySet()` to prove `map` remains in scope for the rest of the method block.
  2. Write a `while` loop:
     - Use a pattern variable in a conditional check combined with an iterator or queue.
  3. Write a ternary expression:
     - `String result = (obj instanceof String s) ? s.toUpperCase() : "DEFAULT";`
     - Attempt to access `s` in the false expression `(obj instanceof String s) ? s.toUpperCase() : s.toLowerCase();` and show compilation error.
- **Verification:** Compile and test with valid maps, empty maps, non-maps, and nulls.

---

## 3. HARD EXERCISES (5)

### Exercise 3.1: Lexical Scope Tree Analyzer
- **Objective:** Build a Java program `LexicalScopeAnalyzer` that reads a `.java` source file and builds a hierarchical tree of scopes, cataloging all variable declarations and their active character/line ranges.
- **Requirements:**
  1. Parse curly braces `{` and `}`, tracking nesting depth and line numbers.
  2. Detect variable declarations of primitive types and reference types using regular expressions or manual tokenization.
  3. Record each variable's identifier, type, declaration line, and closing brace line of its enclosing scope.
  4. Detect when an inner scope declares a variable that shadows an outer scope variable.
  5. Print an indented ASCII tree of scopes showing:
     - Scope ID, start line, end line, parent scope ID
     - Declared symbols
     - Warnings for any shadowed symbols
- **Verification:** Run your analyzer against `UnifiedScopeMatrix.java` (from Exercise 2.1) and verify that all scopes and shadowing instances are accurately mapped.

---

### Exercise 3.2: Programmatic `javap` Parser via `ProcessBuilder`
- **Objective:** Write a utility `BytecodeScopeExtractor` that uses Java's `ProcessBuilder` API to compile a target source file, execute `javap -v -l`, and parse the raw `LocalVariableTable`.
- **Requirements:**
  1. Accept a class name as an argument.
  2. Spawn `javac -g Target.java` via `ProcessBuilder`. Ensure exit code is 0.
  3. Spawn `javap -v -l Target` and capture the standard output stream via `InputStreamReader` and `BufferedReader`.
  4. Parse the `LocalVariableTable` section line by line.
  5. For each method, extract a structured table:
     - Variable Name | Slot Index | Start PC | Length | Signature
  6. Compute whether any two variables share the same slot index, reporting slot reuse optimizations directly to the console.
- **Verification:** Execute on a target class containing sequential blocks and verify reported slot reuses.

---

### Exercise 3.3: ClassLoader Scope Isolation and Static Field Segregation
- **Objective:** Prove that `static` fields are scoped to the `java.lang.Class` instance and its defining `ClassLoader`, rather than being globally unique per JVM.
- **Requirements:**
  1. Create a class `GlobalConfig` with `public static String ENVIRONMENT = "DEFAULT";` and a static method to set and get it.
  2. Compile `GlobalConfig.java` to a separate standalone directory outside the application classpath.
  3. Create two independent `URLClassLoader` instances (`loader1` and `loader2`) pointing to that directory, with their parent classloader set to the bootstrap or platform loader to avoid delegation to the app classpath.
  4. Load `GlobalConfig` via `loader1` and set `ENVIRONMENT = "PRODUCTION"`.
  5. Load `GlobalConfig` via `loader2` and set `ENVIRONMENT = "STAGING"`.
  6. Read and print `ENVIRONMENT` from both loaded class representations via reflection.
  7. Show that both static values coexist independently in JVM memory without overwriting each other.
- **Verification:** Console output must prove `loader1` reads `"PRODUCTION"` while `loader2` reads `"STAGING"`.

---

### Exercise 3.4: Non-Static Inner Class Memory Leak & WeakReference Mitigation
- **Objective:** Reproduce a classic production heap leak caused by implicit outer instance scope capture in non-static inner classes, and implement its architectural remedy.
- **Requirements:**
  1. Create a class `HeavyWindow` containing a large payload (`byte[] memoryHog = new byte[50 * 1024 * 1024];` — 50MB).
  2. Inside `HeavyWindow`, create a non-static inner class `EventConsumer` and register an instance of it with a long-lived global singleton `EventBus`.
  3. In a loop, instantiate 10 `HeavyWindow` instances, register their consumers, and immediately set the window local references to `null`.
  4. Trigger `System.gc()`. Monitor memory using `Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()`. Notice that memory is NOT reclaimed because `EventConsumer` holds an implicit outer reference (`HeavyWindow.this`).
  5. Refactor `EventConsumer` to be a `static nested class` that maintains only a `WeakReference<HeavyWindow>` to the outer instance.
  6. Re-run the loop and prove that all 500MB of allocated arrays are collected by GC.
- **Verification:** Print retained heap memory before and after GC to confirm full reclamation under the static nested approach.

---

### Exercise 3.5: Exhaustive JLS Scope Specification Test Suite
- **Objective:** Construct a comprehensive JUnit 5 parameterized test suite that asserts 15 distinct compiler behaviors and scope edge cases using the Java Compiler API (`javax.tools.JavaCompiler`).
- **Requirements:**
  1. Use `ToolProvider.getSystemJavaCompiler()`.
  2. Implement an in-memory string-based Java file object (`SimpleJavaFileObject`).
  3. Write test cases that feed source code snippets into the compiler and assert whether compilation succeeds or fails with specific error diagnostic codes for:
     - Out-of-scope variable access
     - Shadowing method parameter by local block variable
     - Forward reference in field initializers (`int a = b; int b = 10;`)
     - Definite assignment failure in loop structures
     - Pattern variable access on the wrong side of `||`
     - Lambda capture of non-effectively final variable
     - Duplicate variable declaration in switch case without block
     - Protected access across packages without subclass reference
- **Verification:** All 15 tests must pass green under `mvn test` or IntelliJ JUnit runner.

---

## 4. PRODUCTION PROBLEMS (3)

### Problem 4.1: The Production Switch Fall-Through State Corruption Bug
- **Scenario:** An online payment gateway processes transaction records through an event loop. A legacy switch statement handles transaction states: `AUTHORIZE`, `CAPTURE`, `REFUND`. Due to missing break statements and unbracketed local variable declarations, a transient authorization token declared in `case AUTHORIZE:` bleeds into `case CAPTURE:`.
- **Task:**
  1. Reproduce the bug using a minimal test harness where a `CAPTURE` transaction accidentally uses state left in stack memory or falls through into execution logic.
  2. Diagnose how the JLS permits variables to span across fall-through labels when not enclosed in `{ ... }`.
  3. Refactor the entire processor using Java 21 enhanced switch expressions (`switch (status) ->`), eliminating fall-through vulnerabilities and isolating lexical scope.
- **Acceptance Criteria:** Zero possibility of cross-branch variable leakage; 100% test pass on concurrent transaction batches.

---

### Problem 4.2: The Leaking GUI / Listener Retained Heap Bug
- **Scenario:** A desktop trading terminal suffers an `OutOfMemoryError: Java heap space` after 4 hours of active trading. Heap dumps show millions of `OrderPanel` instances retained in memory, even though users closed their tabs. The root cause is an anonymous `ActionListener` created inside `OrderPanel` registering itself into a static `MarketDataBroadcastService`.
- **Task:**
  1. Create a simulated reproducible environment where closing tabs fails to free memory.
  2. Analyze the heap using jcmd or programmatic inspection (`Runtime`) to verify that the anonymous class retains `OrderPanel.this`.
  3. Fix the leak by refactoring the listener into a static nested class decoupling the callback with explicit lifecycle deregistration (`unsubscribe()`) and `WeakReference`.
- **Acceptance Criteria:** Memory consumption remains constant across thousands of open/close panel cycles.

---

### Problem 4.3: The Mutable Static Field Race Condition
- **Scenario:** A high-throughput REST API tracks user security context using a static field: `public static UserContext currentContext;` inside a utility class `SecurityHolder`. Under high concurrent load, User A's thread reads User B's authentication identity, causing severe security isolation violations.
- **Task:**
  1. Write a multithreaded test case simulating 50 concurrent worker threads updating and verifying their respective `currentContext`. Observe cross-thread context contamination.
  2. Explain the scope difference between static fields (shared across the entire classloader across all threads) versus thread-local scope.
  3. Refactor the implementation using `ThreadLocal<UserContext>` or Java 21 `ScopedValue<UserContext>` (Structured Concurrency JEP 446).
  4. Ensure proper cleanup (`ThreadLocal.remove()`) to avoid thread-pool memory leaks.
- **Acceptance Criteria:** All concurrent assertions pass with 0% data cross-talk across 100,000 simulated requests.

---

## 5. REFACTORING TASKS (3)

### Task 5.1: De-scoping the Monolithic "God Method"
- **Scenario:** A legacy payroll calculation method `calculateDisbursements()` is 250 lines long. At line 3, it declares 20 local variables (`tempSum`, `taxRate`, `deduction`, `employeeId`, `flag`, etc.). These variables are reused across unrelated calculation blocks, causing accidental state bleeding, race conditions during partial refactoring, and hindering JIT escape analysis.
- **Requirements:**
  1. Analyze variable life ranges. Identify which variables are strictly local to specific calculation phases.
  2. Move each variable declaration to the narrowest possible block scope, immediately prior to its first use.
  3. Extract isolated calculation phases into private helper methods where inputs and outputs are explicitly defined via parameters and return values.
  4. Replace reusable mutable flags with `final` local variables.
- **Verification:** Ensure zero change to calculation outputs while reducing average variable live range from 250 lines to under 15 lines.

---

### Task 5.2: Accidental Shadowing Elimination
- **Scenario:** An entity class `Order` has fields `status`, `totalAmount`, `discount`. Its builder class and domain methods have parameter names that shadow fields, but developers frequently omit `this.` in complex nested helper methods, causing parameters to be discarded and fields to retain null/zero values.
- **Requirements:**
  1. Inspect the codebase with compiler linting flags `-Xlint:shadowing` (or IntelliJ inspection "Variable shadows field").
  2. Disambiguate all shadowed instances.
  3. Rename parameters or enforce explicit `this.` qualification across all setters, constructors, and mutators.
  4. Make all unshadowed method parameters `final` to prevent accidental reassignment within method bodies.
- **Verification:** Write unit tests asserting that all setters mutate instance fields reliably.

---

### Task 5.3: Broad Resource Scope Narrowing
- **Scenario:** A legacy JDBC data access object declares `Connection conn = null;`, `PreparedStatement ps = null;`, and `ResultSet rs = null;` at the top of the method. The `try-catch-finally` block spans 80 lines, and nested queries re-assign `ps` and `rs`, leading to premature stream closure and unclosed cursors.
- **Requirements:**
  1. Refactor the method to use try-with-resources blocks with minimal scope.
  2. Separate outer query processing from inner query execution to ensure each `ResultSet` has a strictly scoped lifespan.
  3. Verify that database handles are released immediately upon block exit rather than waiting for method completion.
- **Verification:** Mock JDBC resources and verify that `.close()` is called in strict LIFO order.

---

## 6. DEBUGGING TASKS (3)

### Task 6.1: The Phantom Zero Bug
- **Code Snippet:**
  ```java
  public class DiscountCalculator {
      private double finalDiscount;

      public double calculate(double basePrice, boolean vip) {
          double finalDiscount = 0.0;
          if (vip) {
              double discount = basePrice * 0.20;
              finalDiscount = discount;
          } else {
              double discount = basePrice * 0.05;
              finalDiscount = discount;
          }
          return this.finalDiscount; // <-- Look closely here!
      }
  }
  ```
- **Task:**
  1. Identify why `calculate(100.0, true)` returns `0.0` instead of `20.0`.
  2. Trace how local variable `finalDiscount` shadows instance field `this.finalDiscount`.
  3. Fix the method so that it either properly updates the instance field or eliminates the field altogether in favor of pure functional local return.

---

### Task 6.2: The Subclass Field Masking Mystery
- **Code Snippet:**
  ```java
  class UserSession {
      protected int timeoutSeconds = 300;

      public void refreshSession() {
          System.out.println("Session timeout: " + timeoutSeconds + "s");
      }
  }

  class ExtendedSession extends UserSession {
      protected int timeoutSeconds = 3600;

      public void extend() {
          timeoutSeconds += 600;
          super.refreshSession();
      }
  }
  ```
- **Task:**
  1. Execute `new ExtendedSession().extend()`.
  2. Explain why the printed timeout remains `300s` even though `timeoutSeconds` was extended to `4200`.
  3. Detail how field hiding prevents `super.refreshSession()` from seeing `ExtendedSession.timeoutSeconds`.
  4. Provide the correct object-oriented design eliminating the redundant field.

---

### Task 6.3: The Broken Stream Accumulator
- **Code Snippet:**
  ```java
  public class MetricAggregator {
      public int sumMetrics(List<Integer> values) {
          int total = 0;
          values.stream().forEach(v -> {
              total += v; // Compilation failure!
          });
          return total;
      }
  }
  ```
- **Task:**
  1. Document why the compiler rejects `total += v;`.
  2. Explain why Java requires local variables captured by lambdas to be effectively final.
  3. Provide two distinct idiomatic fixes:
     - Using `IntStream.sum()` or `values.stream().reduce(...)`.
     - Using an accumulator object (`LongAdder` or `AtomicInteger`) if streaming side effects are necessary.

---

## 7. CODE COMPLETION (3)

### Task 7.1: Complete the Lexically Isolated Cache Store
- **Description:** Complete the following thread-safe cache store class, ensuring that the lock, internal map, and expiry scanning variables are strictly isolated to their minimal functional scopes.
- **Template:**
  ```java
  public class IsolatedCache<K, V> {
      // TODO: Declare a private final Map and ReentrantReadWriteLock

      public V getOrCompute(K key, Function<K, V> loader) {
          // TODO: Acquire read lock. Check if key exists.
          // TODO: If missing, release read lock, acquire write lock.
          // TODO: Re-check (double-checked locking) and compute.
          // CRITICAL: Ensure lock unlock is ALWAYS guaranteed in finally,
          // and lock variables do not leak beyond the acquisition block.
          return null;
      }
  }
  ```

---

### Task 7.2: Complete the Non-Polymorphic Field Dispatch Demo
- **Description:** Complete the class such that printing the fields and methods produces the exact target trace demonstrating static binding vs dynamic dispatch.
- **Template:**
  ```java
  class Alpha {
      public String version = "1.0";
      public String getVersion() { return version; }
  }

  class Beta extends Alpha {
      public String version = "2.0";
      @Override public String getVersion() { return version; }
  }

  public class DispatchDriver {
      public static void main(String[] args) {
          Alpha ref = new Beta();
          // TODO: Write four print statements demonstrating:
          // 1. Direct field access via ref (Expected: 1.0)
          // 2. Direct field access cast to Beta (Expected: 2.0)
          // 3. Method call via ref (Expected: 2.0)
          // 4. Method call cast to Alpha (Expected: 2.0)
      }
  }
  ```

---

### Task 7.3: Complete the Pattern Matching Flow Scoper
- **Description:** Complete the method using Java 21 pattern matching over sealed hierarchies, ensuring pattern variables are correctly flow-scoped without shadowing or compilation errors.
- **Template:**
  ```java
  sealed interface Expr permits Constant, Add, Multiply {}
  record Constant(int value) implements Expr {}
  record Add(Expr left, Expr right) implements Expr {}
  record Multiply(Expr left, Expr right) implements Expr {}

  public class Evaluator {
      public static int eval(Expr e) {
          // TODO: Complete using a switch expression with record patterns:
          // case Constant(int v) -> ...
          // case Add(Expr l, Expr r) -> ...
          // case Multiply(Expr l, Expr r) -> ...
          return 0;
      }
  }
  ```

---

## 8. OUTPUT PREDICTION (5)

*Predict the exact console output of each program without running it. Explain the underlying JLS scope and dispatch rules.*

### Snippet 8.1: Static Import vs. Enclosing Class Scope
```java
package test;

class OuterScope {
    static int VALUE = 10;

    static class InnerScope {
        static int VALUE = 20;

        void print() {
            int VALUE = 30;
            System.out.println(VALUE);
            System.out.println(InnerScope.VALUE);
            System.out.println(OuterScope.VALUE);
        }
    }

    public static void main(String[] args) {
        new InnerScope().print();
    }
}
```

---

### Snippet 8.2: Initializer Block Execution and Field Shadowing
```java
public class InitScopeOrder {
    int x = 1;
    {
        int x = 2;
        System.out.print(x + " ");
    }

    public InitScopeOrder(int x) {
        System.out.print(x + " ");
        System.out.print(this.x + " ");
    }

    public static void main(String[] args) {
        new InitScopeOrder(3);
    }
}
```

---

### Snippet 8.3: Sequential Block Stack Slot Reuse
```java
public class SlotTracing {
    public static void main(String[] args) {
        int a = 1;
        {
            int b = a + 2;
            System.out.print(b + " ");
        }
        {
            int c = a + 4;
            System.out.print(c + " ");
        }
    }
}
```

---

### Snippet 8.4: Negated Pattern Match Flow Scoping
```java
public class PatternScopingTrace {
    public static void main(String[] args) {
        printLength("OpenTelemetry");
        printLength(12345);
    }

    static void printLength(Object obj) {
        if (!(obj instanceof String s)) {
            System.out.print("NOT_STRING ");
            return;
        }
        System.out.print(s.length() + " ");
    }
}
```

---

### Snippet 8.5: Triple-Nested Class `this` Disambiguation
```java
public class NestingScope {
    int val = 10;

    class LevelA {
        int val = 20;

        class LevelB {
            int val = 30;

            void dump() {
                int val = 40;
                System.out.println(val + " " + LevelB.this.val + " " 
                    + LevelA.this.val + " " + NestingScope.this.val);
            }
        }
    }

    public static void main(String[] args) {
        new NestingScope().new LevelA().new LevelB().dump();
    }
}
```

---

## 9. ERROR FINDING (3)

*Identify the exact compile-time error, the offending line number, and the JLS rule violated.*

### Error Snippet 9.1: C++ vs. Java Inner Block Variable Re-declaration
```java
public class DuplicateCheck {
    public static void main(String[] args) {
        int index = 0;
        for (int i = 0; i < 5; i++) {
            int index = i; // Will this compile?
            System.out.println(index);
        }
    }
}
```

---

### Error Snippet 9.2: Pattern Variable Leaking Across Disjunction (`||`)
```java
public class PatternDisjunction {
    public static void test(Object input) {
        if (input instanceof String text || text.length() > 0) {
            System.out.println("Valid: " + text);
        }
    }
}
```

---

### Error Snippet 9.3: Blank Final Variable in Loop Body
```java
public class FinalInLoop {
    public static void main(String[] args) {
        final int threshold;
        int[] numbers = {10, 20, 30};
        for (int n : numbers) {
            threshold = n * 2; // Will this compile?
            System.out.println(threshold);
        }
    }
}
```

---

## 10. OPTIMIZATION PROBLEMS (2)

### Optimization Problem 10.1: Scope Reduction for Garbage Collection Eligibility
- **Context:** In high-throughput JVM applications, declaring large byte arrays or buffer references at method scope causes them to remain referenced in local variable stack slots across subsequent long-running operations, preventing the Garbage Collector from reclaiming memory.
- **Problem Scenario:**
  ```java
  public void processBatch(List<File> files) throws Exception {
      byte[] largeBuffer = new byte[100 * 1024 * 1024]; // 100MB buffer
      readIntoBuffer(files.get(0), largeBuffer);
      transformData(largeBuffer);
      
      // Notice: largeBuffer is never used below this point!
      // But its reference remains live in slot 2 of the stack frame!
      runLongRunningNetworkSync(); // Takes 10 minutes
  }
  ```
- **Task:**
  1. Refactor the method to ensure `largeBuffer` becomes eligible for Garbage Collection *before* `runLongRunningNetworkSync()` begins execution.
  2. Contrast two approaches:
     - Setting `largeBuffer = null;` explicitly.
     - Enclosing the buffer's allocation and use inside an isolated block `{ ... }` or helper method.
  3. Analyze which approach JIT compilers optimize more aggressively through live-range analysis.

---

### Optimization Problem 10.2: Local Variable Live-Range Minimization and JIT Register Allocation
- **Context:** The HotSpot C2 JIT compiler performs register allocation using graph coloring. Methods with excessive simultaneously active local variables suffer from register spilling (variables forced onto the native stack rather than CPU registers).
- **Task:**
  1. Analyze an unoptimized method that maintains 16 concurrent primitive variables alive across three independent computation stages.
  2. Refactor the method into isolated lexical blocks or separate non-inlined methods to limit the maximum number of live variables at any instruction point to 4.
  3. Show how narrowing variable scope directly reduces HotSpot stack frame size and eliminates register spilling.
