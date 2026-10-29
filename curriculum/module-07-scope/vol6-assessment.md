# Module 7: Scope — Volume 6 (Assessment)

**Target:** Java 21 LTS | IntelliJ IDEA  
**Author:** Senior Java Engineer, JVM Engineer & OpenJDK Compiler Engineer  

---

## LEVEL 1: MCQs (20 Questions)

### Q1.1
What is the precise scope of a local variable declared in the initialization clause of a basic `for` loop (e.g., `for (int i = 0; i < 10; i++)`) according to JLS §6.3?
A) The entire enclosing method body containing the `for` loop.  
B) The `for` statement itself, including its initialization, termination condition, increment expression, and statement body.  
C) Only the statement body enclosed between the curly braces `{ ... }` of the loop.  
D) From the point of declaration until the next local variable declaration in the enclosing block.  

---

### Q1.2
Consider the following code snippet:
```java
public class ShadowDemo {
    static int x = 10;
    public static void main(String[] args) {
        int x = 20;
        {
            int x = 30; // Line 6
            System.out.println(x);
        }
    }
}
```
What is the compiler behavior at Line 6?
A) Compiles successfully and prints `30`.  
B) Compiler error: variable `x` is already defined in the scope of method `main`.  
C) Compiles successfully and shadows both outer `x` variables.  
D) Runtime exception: `DuplicateLocalVariableError`.  

---

### Q1.3
Under what condition does a non-static class member variable (field) become shadowed?
A) When a subclass declares an instance variable with the identical name and type.  
B) When a subclass declares a static variable with the identical name.  
C) When a method parameter or local variable within the class shares the identical identifier.  
D) When an interface implemented by the class defines a constant with the same identifier.  

---

### Q1.4
What occurs when a subclass declares a field with the exact same identifier and type as an inherited accessible field in its superclass?
A) The superclass field is overridden dynamically at runtime.  
B) The superclass field is hidden, and field access is resolved statically based on the compile-time reference type.  
C) A compilation error occurs due to ambiguous field declarations.  
D) The subclass field shadows the superclass field only within subclass static methods.  

---

### Q1.5
Which statement accurately describes the scope of a pattern variable introduced by an `instanceof` expression?
A) Pattern variables are unconditionally scoped to the entire enclosing method.  
B) Pattern variables are scoped to the statement in which the pattern test succeeds (flow-sensitive scoping).  
C) Pattern variables are strictly scoped to the block where the `if` statement resides.  
D) Pattern variables behave identically to traditional local variables declared before the `if` statement.  

---

### Q1.6
Given the following code:
```java
public class PatternScopeCheck {
    public static void check(Object obj) {
        if (obj instanceof String s || s.length() > 0) { // Line 3
            System.out.println(s);
        }
    }
}
```
What is the result of attempting to compile this code?
A) Compiles cleanly and evaluates using short-circuit logic.  
B) Compiler error on Line 3: `cannot find symbol: variable s` because `s` is not in scope on the right side of `||`.  
C) Compiles, but throws `NullPointerException` at runtime if `obj` is not a String.  
D) Compiles, but causes an `AmbiguousPatternException`.  

---

### Q1.7
What is the scope of a resource variable declared within the header of a try-with-resources statement: `try (FileReader fr = new FileReader("test.txt")) { ... }`?
A) The `try` block, all associated `catch` blocks, and the `finally` block.  
B) The `try` block, but not any associated `catch` or `finally` blocks.  
C) The entire method containing the try-with-resources statement.  
D) The `try` block and associated `catch` blocks, but excluded from `finally`.  

---

### Q1.8
In Java 21, what defines an "effectively final" local variable according to JLS §4.12.4?
A) A variable declared with the explicit `final` keyword and assigned a compile-time constant.  
B) A variable that is never assigned after its initialization, even though it lacks the explicit `final` keyword.  
C) Any variable accessed from within a synchronized block.  
D) A variable whose object state is immutable regardless of pointer reassignment.  

---

### Q1.9
What is the lifecycle and memory scope of a local primitive variable declared inside a method?
A) It is allocated in the JVM Metaspace and remains until class unloading.  
B) It is allocated on the heap inside the Thread's TLAB (Thread Local Allocation Buffer).  
C) It is allocated inside a slot in the current execution frame's Local Variable Array on the Java Virtual Machine Stack.  
D) It is stored directly in the Native Method Stack.  

---

### Q1.10
Examine the following code:
```java
public class DefiniteAssignScope {
    public static void main(String[] args) {
        int x;
        if (args.length > 0) {
            x = 10;
        } else if (args.length == 0) {
            x = 20;
        }
        System.out.println(x); // Line 9
    }
}
```
What is the compilation outcome of Line 9?
A) Compiles cleanly because `args.length` can only be greater than zero or equal to zero.  
B) Compiles with a warning regarding constant folding.  
C) Compiler error: `variable x might not have been initialized` because the compiler's flow analysis does not evaluate arithmetic completeness across separate `else if` conditions.  
D) Compiles and prints `20`.  

---

### Q1.11
Which of the following members of an outer class is NOT visible to a non-static member inner class?
A) Private static methods.  
B) Private instance fields.  
C) Private constructors.  
D) None of the above; all outer members are accessible to inner classes regardless of access modifiers.  

---

### Q1.12
How does the `this` reference resolve within a lambda expression versus an anonymous inner class?
A) In both, `this` refers to the generated runtime instance of the functional interface.  
B) In a lambda expression, `this` refers to the enclosing class instance; in an anonymous inner class, `this` refers to the anonymous inner class instance itself.  
C) In a lambda expression, `this` is illegal and causes a compiler error.  
D) In both, `this` refers to the enclosing method frame.  

---

### Q1.13
Consider the legacy switch statement below:
```java
switch (type) {
    case 1:
        int value = 42;
        break;
    case 2:
        value = 99; // Line 6
        System.out.println(value);
        break;
}
```
Which statement correctly describes the scope and initialization of `value` at Line 6?
A) `value` is out of scope and Line 6 fails to compile.  
B) `value` is in scope across the entire switch block; Line 6 compiles and executes without issue.  
C) `value` is in scope, but Line 6 fails with `variable value might not have been initialized` if `type == 2`.  
D) `value` is re-instantiated in a new stack frame slot for each `case` label.  

---

### Q1.14
What is the default value assigned to an uninitialized local variable of type `int` upon entering a method?
A) `0`  
B) `null`  
C) `Integer.MIN_VALUE`  
D) There is no default value; reading an uninitialized local variable causes a compile-time error under Definite Assignment rules.  

---

### Q1.15
What access level is granted to a class member declared without any access modifier (`public`, `protected`, `private`)?
A) Accessible everywhere within the same module only.  
B) Accessible only within the declaring class (private).  
C) Package-private: accessible by any class residing in the exact same package.  
D) Accessible by all subclasses across any package, but not unrelated classes.  

---

### Q1.16
A protected member of class `Alpha` in package `p1` is accessed by class `Beta` in package `p2`, where `Beta extends Alpha`. How can `Beta` legitimately access this member?
A) Only through an instance of `Beta` or a subclass of `Beta`, or via `super`.  
B) Through any instance of `Alpha` created inside `Beta`.  
C) Through any third-party subclass of `Alpha` residing in package `p3`.  
D) Protected members can never be accessed outside the declaring package under any circumstance.  

---

### Q1.17
What is the effect of slot reuse in the JVM's `LocalVariableTable`?
A) It permits the Garbage Collector to reallocate bytecode memory dynamically.  
B) It allows non-overlapping block-scoped variables to share the same local variable array index, minimizing frame stack size.  
C) It allows two variables in the same block to share memory if they have identical values.  
D) It prevents JIT compilers from performing register allocation.  

---

### Q1.18
Consider the following record declaration in Java 21:
```java
public record User(String username, int age) {
    public User {
        if (age < 0) throw new IllegalArgumentException();
        username = username.toLowerCase(); // Line 4
    }
}
```
In the compact constructor shown, what is the scope and nature of `username` on Line 4?
A) It refers directly to the private final field `this.username`.  
B) It refers to the constructor parameter `username`, which will implicitly be assigned to the component field at the end of the constructor.  
C) It causes a compile error because record component fields are `final` and cannot be assigned.  
D) It shadows the field, making the record unable to store the lower-cased value.  

---

### Q1.19
What is the scope of a type parameter `T` in a generic method declaration: `public static <T extends Comparable<T>> void sort(List<T> list)`?
A) The entire enclosing class, including all static and instance methods.  
B) The method signature and the method body only.  
C) The package in which the enclosing class is defined.  
D) Any subclass that overrides the method.  

---

### Q1.20
Which component does NOT create its own independent lexical scope in Java?
A) A labeled block statement: `label: { int a = 1; }`  
B) The initialization statement of a `for` loop: `for (int i = 0; ...)`  
C) An `if` statement condition expression: `if (booleanExpression)` (excluding pattern matching)  
D) A catch clause block: `catch (IOException ex) { ... }`  

---

### LEVEL 1 ANSWERS & DEEP EXPLANATIONS

1. **B** — JLS §6.3: The scope of a local variable declared in the `ForInit` part of a basic `for` statement includes the `ForInit`, the `Expression` (condition), the `ForUpdate` (increment expressions), and the contained `Statement` (body).
2. **B** — JLS §6.4: Unlike C++, Java explicitly prohibits declaring a local variable in an inner block if it has the same identifier as another local variable or parameter in the enclosing method scope.
3. **C** — JLS §6.4.1: Shadowing occurs when an identifier declared in an inner scope (such as a method parameter or local variable) shares the same name as an entity in an enclosing scope (such as an instance field).
4. **B** — JLS §8.3 / §8.4.8: Fields in Java are not polymorphic. When a subclass declares a field with the same name as a superclass field, the subclass field *hides* the superclass field. Access is resolved at compile time based on the static reference type.
5. **B** — JLS §14.30.1: Pattern variables use flow-sensitive scoping; a pattern variable is introduced into scope only where the pattern matching predicate is guaranteed to have evaluated to `true`.
6. **B** — Under flow scoping, if `obj instanceof String s` evaluates to `false`, the right-hand side of `||` is evaluated. But in that case, `s` was NOT matched, so `s` is not in scope in the right operand or within the `if` body.
7. **B** — JLS §14.20.3: Resources declared in a try-with-resources header are closed automatically *before* any `catch` or `finally` blocks execute; therefore, their lexical scope is confined strictly to the `try` block body.
8. **B** — JLS §4.12.4: A local variable whose value is never changed after initialization is effectively final, making it eligible for capture by lambdas and anonymous inner classes without explicit `final`.
9. **C** — JVM Specification §2.6.1: Each frame contains an array of variables known as its local variables. Primitives and references are assigned slots in this array during compilation.
10. **C** — JLS §16: Definite assignment analysis operates on syntactic control flow without mathematical evaluation of non-constant conditional expressions. Because neither branch is an unconditional `else`, `x` is not definitely assigned after the `if` construct.
11. **D** — JLS §8.1.3: An inner class possesses an implicit enclosing instance reference and has full visibility into all members of the enclosing class, including `private` fields, methods, and constructors.
12. **B** — Lambdas do not introduce a new lexical scope for `this`; `this` inside a lambda refers to the enclosing instance where the lambda was written. Anonymous inner classes are distinct class instances, so `this` refers to the inner instance.
13. **B** — In a traditional `switch`, all case labels share a single lexical scope. `value` is declared in `case 1`, so it remains in scope in `case 2`. Line 6 assigns 99 to the already-declared variable.
14. **D** — JLS §4.12.5 & §16: Local variables are not zero-initialized by the JVM. A local variable must be explicitly assigned a value before it can be read; otherwise, a compile-time error occurs.
15. **C** — JLS §6.6.1: Default access (no modifier) provides package-private access; members are accessible to all classes declared within the same package.
16. **A** — JLS §6.6.2: Access to a protected instance member from outside its package is permitted only if the access is performed through a reference whose compile-time type is the subclass itself (or one of its subclasses).
17. **B** — JVM Bytecode Specification: When two disjoint blocks declare local variables, the compiler maps them to the same slot in the `LocalVariableTable`, reducing stack frame size and optimizing memory footprint.
18. **B** — In a compact record constructor, the parameters correspond directly to the components. Modifying `username` reassigns the parameter, which is subsequently assigned to the component field implicitly by the compiler.
19. **B** — JLS §6.3: The scope of a method's type parameter is the entire method declaration, encompassing the type parameter section, return type, formal parameters, throws clause, and method body.
20. **C** — A standard boolean condition in an `if` statement does not establish a distinct lexical scope for variable declarations (unlike pattern variables or block statements).

---

## LEVEL 2: TRUE/FALSE (20 Questions)

### Q2.1
In Java, an inner code block `{ ... }` inside a method can legitimately declare a local variable `int count;` even if the enclosing method already has a local variable named `count`.  
*(True / False)*

---

### Q2.2
A subclass method can override an instance variable inherited from its superclass to provide dynamic runtime polymorphic field dispatch.  
*(True / False)*

---

### Q2.3
Fields declared in a class (both static and instance) are guaranteed by the Java Virtual Machine to be initialized to default values (`0`, `0.0`, `false`, `null`) prior to any user code execution.  
*(True / False)*

---

### Q2.4
A static method in an inner class can reference instance variables of the enclosing outer class directly without an explicit instance reference.  
*(True / False)*

---

### Q2.5
In the enhanced `for` loop `for (String s : list)`, the variable `s` remains in scope after the loop statement terminates.  
*(True / False)*

---

### Q2.6
A pattern variable declared in an `if` statement using `if (obj instanceof String s)` can remain in scope outside the `if` block if the `if` block unconditionally throws an exception or returns.  
*(True / False)*

---

### Q2.7
Lambda expressions introduce their own lexical scope, meaning declaring a parameter `(x) -> ...` is valid even if `x` is already declared as a local variable in the enclosing method.  
*(True / False)*

---

### Q2.8
The `this` keyword can be used inside a static method as long as it is explicitly cast to the enclosing class type.  
*(True / False)*

---

### Q2.9
In Java bytecode, two local variables declared in consecutive, non-nested blocks within the same method can occupy the exact same slot index in the frame's `LocalVariableTable`.  
*(True / False)*

---

### Q2.10
A variable declared inside the body of a `try` block is accessible inside the corresponding `catch` and `finally` blocks.  
*(True / False)*

---

### Q2.11
Static fields are scoped per `ClassLoader`, meaning two different classloaders loading the identical class file maintain completely distinct, isolated copies of that class's static fields.  
*(True / False)*

---

### Q2.12
Non-static inner classes retain an implicit hidden reference to their enclosing outer class instance, which can prevent the outer instance from being garbage collected.  
*(True / False)*

---

### Q2.13
Under JLS §16 (Definite Assignment), declaring `final int x;` allows `x` to be assigned multiple times as long as each assignment occurs in a different iteration of a `while` loop.  
*(True / False)*

---

### Q2.14
A `protected` field declared in class `A` (in package `p1`) can be accessed from class `B` (in package `p2`) via `new A().field` as long as `B extends A`.  
*(True / False)*

---

### Q2.15
Switch rules using arrow syntax (`case A -> { ... }`) isolate their lexical scope, allowing identical variable names to be declared across different case rule blocks.  
*(True / False)*

---

### Q2.16
A method parameter shadows any instance variable in the same class that shares the same name unless the instance variable is explicitly qualified with `this.`.  
*(True / False)*

---

### Q2.17
Anonymous inner classes cannot define their own instance fields because they lack a declared class name.  
*(True / False)*

---

### Q2.18
If a local variable is declared `final`, it is guaranteed to be stored in CPU registers rather than the JVM stack frame.  
*(True / False)*

---

### Q2.19
A static nested class does NOT have access to the private static members of its enclosing class.  
*(True / False)*

---

### Q2.20
In a constructor, calling `this(...)` must be the first statement; any local variable declarations prior to `this(...)` are rejected by the compiler.  
*(True / False)*

---

### LEVEL 2 ANSWERS & DEEP EXPLANATIONS

1. **False** — JLS §6.4: Java does not permit shadowing a local variable or parameter with another local variable in an inner block within the same method.
2. **False** — Member variables are never polymorphic in Java. Fields are hidden, not overridden, and field access is resolved statically at compile time.
3. **True** — JLS §4.12.5: All class fields (static and instance) are initialized to binary zero/null during class initialization and object allocation before constructors run.
4. **False** — Static contexts do not possess an enclosing instance reference (`this`), so they cannot access instance fields directly.
5. **False** — JLS §14.14.2: The scope of the loop variable in an enhanced `for` loop is limited exclusively to the loop body.
6. **True** — Flow scoping (JLS §14.30): If `if (!(obj instanceof String s)) { return; }` exits the method, `s` is definitely matched for all statements following the `if`, and remains in scope.
7. **False** — JLS §15.27: A lambda body does not introduce an isolated lexical scope for identifier re-declaration; declaring a lambda parameter with the same name as an active local variable causes a compilation error.
8. **False** — JLS §15.8.3: The keyword `this` is illegal in any static method or static initializer; it results in a compile-time error.
9. **True** — The JVM optimizes stack frame allocations by reusing `LocalVariableTable` slots for disjoint variable lifespans.
10. **False** — JLS §14.20: Variables declared in a `try` block are lexically scoped strictly to that block and cannot be accessed in `catch` or `finally`.
11. **True** — A runtime class in the JVM is defined by the pair `(fully-qualified class name, defining ClassLoader)`. Each ClassLoader maintains its own Metaspace structures and static fields.
12. **True** — Non-static inner classes hold a synthetic reference `this$0` pointing to the enclosing instance, which retains the outer object on the heap until the inner instance is collected.
13. **False** — JLS §16: A blank final variable cannot be assigned if it might already be assigned. In a loop, the second iteration violates definite unassignment, causing a compile error.
14. **False** — JLS §6.6.2: A subclass in another package can only access a protected member of its superclass through an expression of the subclass's own type (e.g., `new B().field`), not through a bare superclass instance (`new A().field`).
15. **True** — Each switch rule arm with a block `{ ... }` forms its own independent lexical scope.
16. **True** — Within the method body, the parameter identifier shadows the field identifier. Qualification with `this.fieldName` is required to reference the instance field.
17. **False** — Anonymous inner classes can define instance fields, instance initializers, and methods; they simply cannot define explicit constructors.
18. **False** — `final` is a language-level semantic constraint affecting compiler checks; it does not dictate physical hardware register allocation.
19. **False** — JLS §8.5: A static nested class has full access to all private static members of its enclosing class.
20. **True** — Prior to Java 22 / Flexible Constructor Bodies, calling `this(...)` or `super(...)` must be strictly the first statement in a constructor. (Under Java 21 LTS, statements prior to `this()` are not permitted).


---

## LEVEL 3: OUTPUT PREDICTION (20 Questions)

*Predict the exact console output of each program upon execution. Line-by-line analyses are provided at the end of this level.*

### Q3.1
```java
public class ScopePuzzler1 {
    static int x = 10;
    int y = 20;

    public void process(int x) {
        int y = 30;
        System.out.println(x + " " + this.y + " " + y + " " + ScopePuzzler1.x);
    }

    public static void main(String[] args) {
        new ScopePuzzler1().process(5);
    }
}
```

---

### Q3.2
```java
class SuperBase {
    String name = "SUPER";
    static String type = "BASE_STATIC";
    void print() { System.out.println(name + " " + type); }
}

class SubChild extends SuperBase {
    String name = "SUB";
    static String type = "CHILD_STATIC";
    @Override void print() { System.out.println(name + " " + type); }
}

public class ScopePuzzler2 {
    public static void main(String[] args) {
        SuperBase obj = new SubChild();
        System.out.println(obj.name + " " + obj.type);
        obj.print();
    }
}
```

---

### Q3.3
```java
public class ScopePuzzler3 {
    int count = 100;

    void execute() {
        int count = 200;
        Runnable r = new Runnable() {
            int count = 300;
            @Override
            public void run() {
                int count = 400;
                System.out.println(count + " " + this.count + " " + ScopePuzzler3.this.count);
            }
        };
        r.run();
    }

    public static void main(String[] args) {
        new ScopePuzzler3().execute();
    }
}
```

---

### Q3.4
```java
public class ScopePuzzler4 {
    int val = 10;

    void test() {
        int val = 20;
        Runnable r = () -> {
            System.out.println(val + " " + this.val);
        };
        r.run();
    }

    public static void main(String[] args) {
        new ScopePuzzler4().test();
    }
}
```

---

### Q3.5
```java
public class ScopePuzzler5 {
    static int a = 1;
    int b = 2;

    static {
        a = 10;
        // int b = 20;
    }

    {
        b = 30;
        a = 40;
    }

    public ScopePuzzler5() {
        b = 50;
    }

    public static void main(String[] args) {
        System.out.print(ScopePuzzler5.a + " ");
        ScopePuzzler5 s = new ScopePuzzler5();
        System.out.println(ScopePuzzler5.a + " " + s.b);
    }
}
```

---

### Q3.6
```java
public class ScopePuzzler6 {
    public static void main(String[] args) {
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            int val = i * 10;
            sum += val;
        }
        for (int i = 0; i < 3; i++) {
            int val = i * 100;
            sum += val;
        }
        System.out.println(sum);
    }
}
```

---

### Q3.7
```java
public class ScopePuzzler7 {
    public static void test(Object obj) {
        if (!(obj instanceof String s)) {
            System.out.print("NOT_STRING ");
            return;
        }
        System.out.print(s.toUpperCase() + " ");
    }

    public static void main(String[] args) {
        test(123);
        test("java");
    }
}
```

---

### Q3.8
```java
public class ScopePuzzler8 {
    int x;
    boolean b;
    String s;

    void print() {
        int x;
        boolean b;
        String s;
        // Reading class fields:
        System.out.println(this.x + " " + this.b + " " + this.s);
    }

    public static void main(String[] args) {
        new ScopePuzzler8().print();
    }
}
```

---

### Q3.9
```java
public class ScopePuzzler9 {
    public static void main(String[] args) {
        int code = 1;
        int result = switch (code) {
            case 1 -> {
                int temp = 10;
                yield temp * 2;
            }
            case 2 -> {
                int temp = 20;
                yield temp * 3;
            }
            default -> 0;
        };
        System.out.println(result);
    }
}
```

---

### Q3.10
```java
public class ScopePuzzler10 {
    public static void main(String[] args) {
        int x = 5;
        {
            int y = 10;
            x += y;
        }
        {
            int y = 20;
            x += y;
        }
        System.out.println(x);
    }
}
```

---

### Q3.11
```java
class NodeA {
    public int id = 1;
}

class NodeB extends NodeA {
    public int id = 2;
}

public class ScopePuzzler11 {
    public static void main(String[] args) {
        NodeB b = new NodeB();
        NodeA a = b;
        System.out.println(b.id + " " + a.id + " " + ((NodeA) b).id);
    }
}
```

---

### Q3.12
```java
public class ScopePuzzler12 {
    public static void main(String[] args) {
        int val = 0;
        try {
            val = 10;
            throw new RuntimeException();
        } catch (Exception e) {
            int valCatch = 20;
            val += valCatch;
        } finally {
            val += 5;
        }
        System.out.println(val);
    }
}
```

---

### Q3.13
```java
public class ScopePuzzler13 {
    static int num = 10;

    static void modify(int num) {
        num = num * 2;
        ScopePuzzler13.num += num;
    }

    public static void main(String[] args) {
        modify(5);
        System.out.println(num);
    }
}
```

---

### Q3.14
```java
public class ScopePuzzler14 {
    int value = 42;

    public static void main(String[] args) {
        ScopePuzzler14 obj1 = new ScopePuzzler14();
        ScopePuzzler14 obj2 = new ScopePuzzler14();
        obj1.value = 99;
        System.out.println(obj1.value + " " + obj2.value);
    }
}
```

---

### Q3.15
```java
public class ScopePuzzler15 {
    private static int counter = 0;

    public static class CounterHolder {
        public static void increment() {
            counter++;
        }
        public static int get() {
            return counter;
        }
    }

    public static void main(String[] args) {
        CounterHolder.increment();
        CounterHolder.increment();
        System.out.println(CounterHolder.get());
    }
}
```

---

### Q3.16
```java
public class ScopePuzzler16 {
    static String status = "GLOBAL";

    public void check() {
        String status = "LOCAL";
        if (status.equals("LOCAL")) {
            System.out.print(status + " " + ScopePuzzler16.status + " ");
        }
    }

    public static void main(String[] args) {
        new ScopePuzzler16().check();
    }
}
```

---

### Q3.17
```java
public class ScopePuzzler17 {
    public static void main(String[] args) {
        Object val = "Java Mastery";
        if (val instanceof String str && str.startsWith("Java")) {
            System.out.println(str.toUpperCase());
        }
    }
}
```

---

### Q3.18
```java
public class ScopePuzzler18 {
    int data = 10;

    void calculate(int data) {
        data = data; // Note self-assignment!
    }

    public static void main(String[] args) {
        ScopePuzzler18 p = new ScopePuzzler18();
        p.calculate(50);
        System.out.println(p.data);
    }
}
```

---

### Q3.19
```java
public class ScopePuzzler19 {
    public static void main(String[] args) {
        int x = 10;
        int y = (x > 5) ? 100 : 200;
        System.out.println(y);
    }
}
```

---

### Q3.20
```java
public class ScopePuzzler20 {
    static int n = 5;

    public static void main(String[] args) {
        int n = 10;
        for (int i = 0; i < 1; i++) {
            System.out.print(n + " ");
        }
        System.out.println(ScopePuzzler20.n);
    }
}
```

---

### LEVEL 3 ANSWERS & LINE-BY-LINE ANALYSIS

1. **`5 20 30 10`** — In `process(int x)`: parameter `x` is 5. `this.y` references instance field `y` (20). Local `y` is 30. `ScopePuzzler1.x` is static field `x` (10).
2. **`SUPER BASE_STATIC` followed by `SUB CHILD_STATIC`** — Fields and static methods do not exhibit polymorphic dispatch. `obj.name` and `obj.type` are resolved using the compile-time type (`SuperBase`), yielding `"SUPER BASE_STATIC"`. The virtual method `obj.print()` dynamically invokes `SubChild.print()`, accessing `SubChild`'s members and printing `"SUB CHILD_STATIC"`.
3. **`400 300 100`** — Inside `run()`: local variable `count` is 400. `this.count` refers to the anonymous inner class's instance field (300). `ScopePuzzler3.this.count` qualifies the enclosing outer instance field (100).
4. **`20 10`** — A lambda does not create its own scope for `this`. `val` refers to the effectively final local variable in method `test()` (20). `this.val` references the enclosing class instance field (10).
5. **`10 40 50`** — Accessing `ScopePuzzler5.a` triggers class loading. Static initializer sets `a = 10`. Printing `ScopePuzzler5.a` outputs `10`. Instantiating `new ScopePuzzler5()` triggers instance initializers: `b = 30`, `a = 40`. Then constructor sets `b = 50`. Thus `a` is 40, and `s.b` is 50.
6. **`330`** — First loop: `i=0` (0), `i=1` (10), `i=2` (20) -> sum = 30. Second loop: `i=0` (0), `i=1` (100), `i=2` (200) -> sum = 30 + 300 = 330.
7. **`NOT_STRING JAVA `** — `123` is not a String, so `test(123)` prints `NOT_STRING ` and returns. `"java"` is a String, so flow scoping introduces `s`, printing `JAVA `.
8. **`0 false null`** — The uninitialized local variables are never read. Reading `this.x`, `this.b`, and `this.s` reads instance fields, which are initialized by default to `0`, `false`, and `null`.
9. **`20`** — Switch rule branch `case 1` executes with local `temp = 10`, yielding `10 * 2 = 20`.
10. **`35`** — `x` starts at 5. In first block, `y=10` is added (`x=15`). In second block, `y=20` is added (`x=35`).
11. **`2 1 1`** — Field resolution is static. `b.id` accesses `NodeB.id` (2). `a.id` accesses `NodeA.id` (1) because `a` has static type `NodeA`. `((NodeA) b).id` casts to `NodeA`, evaluating to 1.
12. **`35`** — In `try`, `val` becomes 10, exception thrown. In `catch`, `valCatch` is 20, `val` becomes 10 + 20 = 30. In `finally`, `val` becomes 30 + 5 = 35.
13. **`20`** — Parameter `num = 5`. `num = num * 2` sets parameter `num = 10`. `ScopePuzzler13.num += 10` adds 10 to static `num` (10 + 10 = 20).
14. **`99 42`** — `value` is an instance field. Mutating `obj1.value` has no effect on `obj2.value`.
15. **`2`** — Static nested class `CounterHolder` has direct lexical visibility to outer class private static field `counter`. Both increments update `counter`.
16. **`LOCAL GLOBAL `** — Local `status` is `"LOCAL"`. `ScopePuzzler16.status` references static field `"GLOBAL"`.
17. **`JAVA MASTERY`** — Pattern match succeeds, `str` is in scope within the `&&` condition and the `if` body, printing uppercase string.
18. **`10`** — In `calculate`, `data = data` is a self-assignment of the parameter. Instance field `this.data` is untouched and remains 10.
19. **`100`** — Ternary condition `10 > 5` is true, evaluating to 100.
20. **`10 5`** — Inside `main`, local variable `n` shadows static field `n`. The loop prints local `n` (10). Then `ScopePuzzler20.n` prints static `n` (5).

---

## LEVEL 4: FIND THE COMPILER ERROR (20 Questions)

*Identify the exact line number, compiler error message, and JLS rule violation for each snippet.*

### Q4.1
```java
public class ErrorFinder1 {
    public static void main(String[] args) {
        int a = 10;
        {
            int a = 20; // Line 5
            System.out.println(a);
        }
    }
}
```

---

### Q4.2
```java
public class ErrorFinder2 {
    public static void main(String[] args) {
        int total;
        if (args.length > 0) {
            total = 100;
        }
        System.out.println(total); // Line 7
    }
}
```

---

### Q4.3
```java
public class ErrorFinder3 {
    int instanceVal = 50;

    public static void compute() {
        System.out.println(instanceVal); // Line 5
    }
}
```

---

### Q4.4
```java
public class ErrorFinder4 {
    public static void test() {
        int multiplier = 2;
        Runnable r = () -> {
            System.out.println(multiplier);
        };
        multiplier = 3; // Line 7
        r.run();
    }
}
```

---

### Q4.5
```java
public class ErrorFinder5 {
    public static void evaluate(Object obj) {
        if (obj instanceof String s || s.isEmpty()) { // Line 3
            System.out.println("Empty string");
        }
    }
}
```

---

### Q4.6
```java
public class ErrorFinder6 {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            int step = i * 2;
        }
        System.out.println(step); // Line 6
    }
}
```

---

### Q4.7
```java
public class ErrorFinder7 {
    public static void main(String[] args) {
        try (java.io.StringReader reader = new java.io.StringReader("test")) {
            System.out.println(reader.read());
        } finally {
            reader.close(); // Line 6
        }
    }
}
```

---

### Q4.8
```java
public class ErrorFinder8 {
    public static void main(String[] args) {
        int code = 1;
        switch (code) {
            case 1:
                int x = 10;
                break;
            case 2:
                int x = 20; // Line 9
                break;
        }
    }
}
```

---

### Q4.9
```java
public class ErrorFinder9 {
    final int limit; // Line 2

    public void setLimit(int val) {
        limit = val; // Line 5
    }
}
```

---

### Q4.10
```java
public class ErrorFinder10 {
    public static void loop() {
        final int max;
        for (int i = 0; i < 3; i++) {
            max = 10; // Line 5
            System.out.println(max);
        }
    }
}
```

---

### Q4.11
```java
public class ErrorFinder11 {
    public static void run() {
        int val = 10;
        Runnable r = (val) -> { // Line 4
            System.out.println(val);
        };
    }
}
```

---

### Q4.12
```java
public class ErrorFinder12 {
    static int a = b + 1; // Line 2
    static int b = 10;    // Line 3
}
```

---

### Q4.13
```java
public class ErrorFinder13 {
    public static void main(String[] args) {
        int target = 100;
        if (true) {
            target = 200;
        } else {
            int target = 300; // Line 7
        }
    }
}
```

---

### Q4.14
```java
package p2;
import p1.Base;

public class ErrorFinder14 extends Base {
    public void test() {
        Base other = new Base();
        System.out.println(other.protectedField); // Line 8
    }
}
```
*(Assume `Base` is in package `p1` with `protected int protectedField;`)*

---

### Q4.15
```java
public class ErrorFinder15 {
    public static void test(Object obj) {
        if (!(obj instanceof String s)) {
            System.out.println("Not a string");
        }
        System.out.println(s.length()); // Line 6
    }
}
```

---

### Q4.16
```java
public class ErrorFinder16 {
    public static void calculate() {
        int x;
        while (false) {
            x = 10;
        }
        System.out.println(x); // Line 7
    }
}
```

---

### Q4.17
```java
public class ErrorFinder17 {
    public static void main(String[] args) {
        label1: {
            int a = 1;
        }
        System.out.println(a); // Line 5
    }
}
```

---

### Q4.18
```java
public class ErrorFinder18 {
    public static void check() {
        int a;
        boolean flag = Math.random() > 0.5;
        if (flag) {
            a = 1;
        } else if (!flag) {
            a = 2;
        }
        int b = a + 1; // Line 9
    }
}
```

---

### Q4.19
```java
public class ErrorFinder19 {
    public static void main(String[] args) {
        int val = switch (args.length) {
            case 0 -> 10;
            case 1 -> {
                int score = 20;
                // Missing yield or expression
            }
        };
    }
}
```

---

### Q4.20
```java
public class ErrorFinder20 {
    public void execute() {
        int x = 10;
        class Inner {
            void run() {
                x = 20; // Line 6
            }
        }
    }
}
```

---

### LEVEL 4 ANSWERS & ERROR EXPLANATIONS

1. **Line 5**: `variable a is already defined in method main(String[])` — JLS §6.4: Local variable shadowing in an inner block is prohibited.
2. **Line 7**: `variable total might not have been initialized` — JLS §16: Definite assignment fails because there is no `else` branch.
3. **Line 5**: `non-static variable instanceVal cannot be referenced from a static context` — JLS §8.4.3.2: Static methods have no `this` reference.
4. **Line 7**: `local variables referenced from a lambda expression must be final or effectively final` — Reassigning `multiplier` on Line 7 invalidates effectively final status required by the lambda capture on Line 4.
5. **Line 3**: `cannot find symbol: variable s` — In an `||` condition, if the left side fails, the right side is evaluated where `s` is not defined.
6. **Line 6**: `cannot find symbol: variable step` — `step` is scoped exclusively to the body of the `for` loop.
7. **Line 6**: `cannot find symbol: variable reader` — Resources declared in try-with-resources headers are scoped only to the `try` block, not to `finally`.
8. **Line 9**: `variable x is already defined in method main(String[])` — All case statements in a traditional switch share a single block scope.
9. **Line 5**: `cannot assign a value to final variable limit` — Blank final instance fields must be initialized in constructors or instance initializers, not in arbitrary mutator methods.
10. **Line 5**: `variable max might already have been assigned` — Assigning a `final` local variable inside a loop violates definite unassignment on subsequent iterations.
11. **Line 4**: `variable val is already defined in method run()` — Lambda parameters cannot shadow local variables in the enclosing scope.
12. **Line 2**: `illegal forward reference` — JLS §8.3.3: Field initializers cannot reference fields declared later in textual order.
13. **Line 7**: `variable target is already defined in method main(String[])` — Cannot redeclare `target` in the `else` block because outer `target` is already in scope.
14. **Line 8**: `protectedField has protected access in p1.Base` — Access to a protected member from another package cannot be made via an instance of superclass `Base`; it must be accessed via a reference of type `p2.ErrorFinder14`.
15. **Line 6**: `cannot find symbol: variable s` — The `if` block did not exit (`return`/`throw`), so after the `if`, `s` is out of scope.
16. **Line 7**: `variable x might not have been initialized` (and `unreachable statement` on line 5) — The `while (false)` loop never executes; `x` is never initialized.
17. **Line 5**: `cannot find symbol: variable a` — Variable `a` is strictly scoped to the labeled block `{ ... }`.
18. **Line 9**: `variable a might not have been initialized` — Compiler flow analysis does not evaluate boolean relationships between `flag` and `!flag`; it requires an unconditional `else`.
19. **Line 5**: `switch expression does not yield a value from this branch` — A block arm in a switch expression must use `yield` to produce a result.
20. **Line 6**: `local variables referenced from an inner class must be final or effectively final` — Assigning to `x` from within the local class mutates the enclosing local variable, which is illegal.


---

## LEVEL 5: FIND THE RUNTIME ERROR (20 Questions)

*Identify the exact runtime exception or error, the triggering line, and the root cause related to scope, default values, or shadowing.*

### Q5.1
```java
public class RuntimeError1 {
    static Integer count; // Defaults to null

    public static void main(String[] args) {
        int next = count + 1; // Line 5
        System.out.println(next);
    }
}
```

---

### Q5.2
```java
public class RuntimeError2 {
    private String data;

    public RuntimeError2(String data) {
        data = data; // Line 5: self-assignment
    }

    public void process() {
        System.out.println(data.toUpperCase()); // Line 9
    }

    public static void main(String[] args) {
        new RuntimeError2("payload").process();
    }
}
```

---

### Q5.3
```java
public class RuntimeError3 {
    public static void main(String[] args) {
        String[] items = new String[3]; // All elements null
        for (String item : items) {
            System.out.println(item.length()); // Line 5
        }
    }
}
```

---

### Q5.4
```java
class SuperInit {
    SuperInit() {
        print(); // Line 3
    }
    void print() { System.out.println("Super"); }
}

class SubInit extends SuperInit {
    String message = "Hello";
    @Override
    void print() {
        System.out.println(message.length()); // Line 11
    }
}

public class RuntimeError4 {
    public static void main(String[] args) {
        new SubInit();
    }
}
```

---

### Q5.5
```java
public class RuntimeError5 {
    static int[] values;

    public static void main(String[] args) {
        System.out.println(values[0]); // Line 5
    }
}
```

---

### Q5.6
```java
public class RuntimeError6 {
    private boolean initialized;

    public void run() {
        if (!initialized) {
            boolean initialized = true; // Shadows field!
        }
        if (!this.initialized) {
            throw new IllegalStateException("Not ready"); // Line 9
        }
    }

    public static void main(String[] args) {
        new RuntimeError6().run();
    }
}
```

---

### Q5.7
```java
public class RuntimeError7 {
    int index;

    public void recurse() {
        int index = 0;
        if (this.index < 5) {
            recurse(); // Infinite recursion!
        }
    }

    public static void main(String[] args) {
        new RuntimeError7().recurse();
    }
}
```

---

### Q5.8
```java
public class RuntimeError8 {
    static ThreadLocal<String> userSession = new ThreadLocal<>();

    public static void main(String[] args) {
        System.out.println(userSession.get().toLowerCase()); // Line 5
    }
}
```

---

### Q5.9
```java
public class RuntimeError9 {
    Boolean active;

    public void check() {
        if (active) { // Line 5
            System.out.println("Active");
        }
    }

    public static void main(String[] args) {
        new RuntimeError9().check();
    }
}
```

---

### Q5.10
```java
public class RuntimeError10 {
    public static void main(String[] args) {
        Object num = Integer.valueOf(42);
        String str = (String) num; // Line 4
        System.out.println(str);
    }
}
```

---

### Q5.11
```java
class BaseResource {
    public Object getResource() { return null; }
}

class ExtendedResource extends BaseResource {
    public String getResource() { return "Config"; }
}

public class RuntimeError11 {
    public static void main(String[] args) {
        BaseResource res = new BaseResource();
        ExtendedResource ext = (ExtendedResource) res; // Line 12
        System.out.println(ext.getResource());
    }
}
```

---

### Q5.12
```java
public class RuntimeError12 {
    int[] cache;

    public void init() {
        int[] cache = new int[10]; // Shadows field!
    }

    public void access() {
        System.out.println(cache.length); // Line 9
    }

    public static void main(String[] args) {
        RuntimeError12 obj = new RuntimeError12();
        obj.init();
        obj.access();
    }
}
```

---

### Q5.13
```java
public class RuntimeError13 {
    static int size;

    public static void main(String[] args) {
        int[] arr = new int[size - 1]; // Line 5 (size is 0)
    }
}
```

---

### Q5.14
```java
public class RuntimeError14 {
    static int divisor;

    public static void main(String[] args) {
        int result = 100 / divisor; // Line 5
    }
}
```

---

### Q5.15
```java
public class RuntimeError15 {
    java.util.List<String> list;

    public void add(String s) {
        list.add(s); // Line 5
    }

    public static void main(String[] args) {
        new RuntimeError15().add("Java");
    }
}
```

---

### Q5.16
```java
public class RuntimeError16 {
    static String greeting;

    static {
        greeting = greeting.concat("Welcome"); // Line 5
    }

    public static void main(String[] args) {
        System.out.println(greeting);
    }
}
```

---

### Q5.17
```java
public class RuntimeError17 {
    public static void main(String[] args) {
        java.util.function.Supplier<String> sup = null;
        System.out.println(sup.get()); // Line 4
    }
}
```

---

### Q5.18
```java
class OuterSelfRef {
    Inner inner = new Inner(); // Line 2

    class Inner {
        OuterSelfRef outer = new OuterSelfRef(); // Line 5
    }
}

public class RuntimeError18 {
    public static void main(String[] args) {
        new OuterSelfRef();
    }
}
```

---

### Q5.19
```java
public class RuntimeError19 {
    int capacity;

    public void setCapacity(int capacity) {
        capacity = capacity;
    }

    public static void main(String[] args) {
        RuntimeError19 obj = new RuntimeError19();
        obj.setCapacity(10);
        int[] arr = new int[obj.capacity]; // Line 11 (capacity is 0)
        System.out.println(arr[0]); // Line 12
    }
}
```

---

### Q5.20
```java
public class RuntimeError20 {
    static Runnable task;

    public static void main(String[] args) {
        task.run(); // Line 5
    }
}
```

---

### LEVEL 5 ANSWERS & RUNTIME ERROR ANALYSIS

1. **`NullPointerException` at Line 5** — `static Integer count` defaults to `null`. Attempting to unbox it via `count + 1` invokes `count.intValue()`, throwing `NullPointerException`.
2. **`NullPointerException` at Line 9** — `data = data` in constructor self-assigns the parameter; instance field `this.data` remains `null`. Calling `.toUpperCase()` on `null` fails.
3. **`NullPointerException` at Line 5** — Objects in array are initialized to default `null`. Calling `.length()` on `null` throws `NullPointerException`.
4. **`NullPointerException` at Line 11** — During superclass constructor execution, overridden method `print()` is dispatched before `SubInit`'s field `message` is initialized (`message` is still `null`). Calling `message.length()` throws `NullPointerException`.
5. **`NullPointerException` at Line 5** — Static array reference `values` defaults to `null`. Array index access `values[0]` throws NPE.
6. **`IllegalStateException` at Line 9** — Line 5 declares a local variable `initialized` which shadows field `this.initialized`. The field remains `false`, triggering the exception on Line 9.
7. **`StackOverflowError`** — `this.index` is 0 and is never modified because `int index = 0` creates an unread local variable. Condition `this.index < 5` remains true forever, exhausting call stack frames.
8. **`NullPointerException` at Line 5** — Initial value of an unset `ThreadLocal` is `null`. Calling `.toLowerCase()` on the result of `get()` throws NPE.
9. **`NullPointerException` at Line 5** — Wrapper `Boolean active` defaults to `null`. Evaluating it inside `if (active)` attempts unboxing via `active.booleanValue()`, throwing NPE.
10. **`ClassCastException` at Line 4** — `num` points to an `Integer`. Direct cast to `String` throws `ClassCastException`.
11. **`ClassCastException` at Line 12** — `res` is an instance of `BaseResource`, not `ExtendedResource`. Downcasting throws `ClassCastException`.
12. **`NullPointerException` at Line 9** — In `init()`, `int[] cache = ...` shadows the field. The instance field `cache` remains `null`, throwing NPE on Line 9.
13. **`NegativeArraySizeException` at Line 5** — Static `size` defaults to 0. `size - 1` evaluates to `-1`, throwing `NegativeArraySizeException`.
14. **`ArithmeticException: / by zero` at Line 5** — Static primitive `divisor` defaults to 0. Integer division by zero throws `ArithmeticException`.
15. **`NullPointerException` at Line 5** — Field `list` is not initialized (defaults to `null`). Calling `list.add()` throws NPE.
16. **`ExceptionInInitializerError` caused by `NullPointerException` at Line 5** — Static `greeting` is `null` when static initializer runs; invoking `greeting.concat()` throws NPE during class initialization.
17. **`NullPointerException` at Line 4** — `sup` is explicitly `null`. Calling `sup.get()` throws NPE.
18. **`StackOverflowError`** — `OuterSelfRef` allocates an `Inner`, which in its field initializer allocates a new `OuterSelfRef`, causing mutual infinite recursive construction.
19. **`ArrayIndexOutOfBoundsException: Index 0 out of bounds for length 0` at Line 12** — Due to self-assignment in `setCapacity`, `capacity` remains 0. Array is created with length 0, so accessing index 0 throws `ArrayIndexOutOfBoundsException`.
20. **`NullPointerException` at Line 5** — Static reference `task` defaults to `null`. Invoking `task.run()` throws NPE.

---

## LEVEL 6: DEBUG THIS CODE (20 Questions)

*Each snippet compiles and runs without unhandled exceptions, but produces unintended logic, incorrect calculations, or silent data bugs due to scope subtleties. Identify the bug and provide the fix.*

### Q6.1: Accidental Field Shadowing in Setter
```java
public class UserAccount {
    private String username;

    public void setUsername(String username) {
        username = username; // Bug: self-assignment
    }

    public String getUsername() { return username; }
}
```

---

### Q6.2: Switch Statement Scope Bleed
```java
public class DiscountProcessor {
    public static double applyDiscount(int tier, double amount) {
        double discounted = amount;
        switch (tier) {
            case 1:
                double rate = 0.10;
                discounted = amount * (1 - rate);
            case 2: // Intended: tier 2 gets 20%
                rate = 0.20; // Bug: falls through from case 1!
                discounted = amount * (1 - rate);
                break;
            default:
                break;
        }
        return discounted;
    }
}
```

---

### Q6.3: Loop Variable Leakage and Stale State
```java
public class BatchCalculator {
    public static int sumBatches(int[][] matrix) {
        int total = 0;
        int i = 0;
        for (i = 0; i < matrix.length; i++) {
            for (i = 0; i < matrix[i].length; i++) { // Bug: reuses outer loop variable i!
                total += matrix[i][i];
            }
        }
        return total;
    }
}
```

---

### Q6.4: Subclass Field Hiding Stale Value
```java
class Vehicle {
    protected int speedLimit = 60;
    public void printSpeed() {
        System.out.println("Speed limit: " + speedLimit);
    }
}

class Truck extends Vehicle {
    protected int speedLimit = 45; // Hides superclass field
}
// Driver: new Truck().printSpeed() prints "Speed limit: 60" instead of 45!
```

---

### Q6.5: Lambda Mutability Workaround Leak
```java
public class TransactionCounter {
    public static int countValid(List<String> items) {
        int[] counter = new int[1];
        items.parallelStream().forEach(item -> {
            if (item != null && !item.isEmpty()) {
                counter[0]++; // Bug: data race across threads!
            }
        });
        return counter[0];
    }
}
```

---

### Q6.6: Broad Variable Scope in Long-Running Loop
```java
public class DataProcessor {
    public void processAll(List<String> rawData) {
        String parsed = null;
        for (String raw : rawData) {
            if (raw.startsWith("DATA:")) {
                parsed = raw.substring(5);
            }
            // Bug: parsed retains previous iteration's value if raw didn't start with DATA:
            save(parsed);
        }
    }
    void save(String s) {}
}
```

---

### Q6.7: Shadowed Metric in Inner Event Handler
```java
public class JobRunner {
    private int processedCount = 0;

    public void start() {
        new Thread(() -> {
            int processedCount = 0; // Shadow!
            while (hasMore()) {
                processNext();
                processedCount++;
            }
        }).start();
    }
    public int getProcessedCount() { return processedCount; } // Always returns 0!
    boolean hasMore() { return false; }
    void processNext() {}
}
```

---

### Q6.8: Misplaced Resource Declaration Outside Loop
```java
public class LineCounter {
    public static int countAll(List<File> files) throws IOException {
        int total = 0;
        BufferedReader reader = null; // Bug: scope spans entire loop
        for (File f : files) {
            reader = new BufferedReader(new FileReader(f));
            while (reader.readLine() != null) total++;
            // reader is not closed per file!
        }
        if (reader != null) reader.close();
        return total;
    }
}
```

---

### Q6.9: Static Context Sharing State Across Instances
```java
public class OrderSession {
    private static String currentOrderId; // Bug: static instead of instance!

    public void setOrderId(String id) {
        currentOrderId = id;
    }
    public String getOrderId() {
        return currentOrderId;
    }
}
```

---

### Q6.10: Accidental Local Declaration in Initializer Block
```java
public class ConnectionConfig {
    private int port = 8080;

    {
        int port = 9090; // Bug: shadows field, field port remains 8080
    }

    public int getPort() { return port; }
}
```

---

### Q6.11: ThreadLocal Scope Leakage in Thread Pool
```java
public class SecurityContextHolder {
    private static final ThreadLocal<String> user = new ThreadLocal<>();

    public static void setUser(String u) { user.set(u); }
    public static String getUser() { return user.get(); }
    // Bug: Missing clear/remove, leading to user identity bleeding in thread pool!
}
```

---

### Q6.12: False Polymorphism with Hidden Static Methods
```java
class Alert {
    public static String getPrefix() { return "[ALERT]"; }
}
class UrgentAlert extends Alert {
    public static String getPrefix() { return "[URGENT]"; }
}

public class AlertService {
    public void notify(Alert a) {
        System.out.println(a.getPrefix() + " Notification"); // Bug: always prints [ALERT]!
    }
}
```

---

### Q6.13: Double-Checked Locking Scope Leak
```java
public class SingletonRegistry {
    private static SingletonRegistry instance;

    public static SingletonRegistry getInstance() {
        if (instance == null) {
            synchronized (SingletonRegistry.class) {
                SingletonRegistry instance = new SingletonRegistry(); // Bug: shadows field!
            }
        }
        return instance; // Bug: returns null!
    }
}
```

---

### Q6.14: Shadowing in Nested Enhanced For-Loop
```java
public class MatrixSearch {
    public static boolean contains(List<List<String>> matrix, String target) {
        for (List<String> row : matrix) {
            for (String target1 : row) {
                // Typo in inner variable name causing logic bug
                if (target1.equals(target)) return true;
            }
        }
        return false;
    }
}
```

---

### Q6.15: Anonymous Class Variable Scope Confusion
```java
public class ButtonHandler {
    private String label = "Cancel";

    public void configure() {
        String label = "OK";
        Runnable action = new Runnable() {
            @Override
            public void run() {
                // Intended: print "OK"
                System.out.println(label); // Works, but if changed to this.label prints compile error
            }
        };
    }
}
```

---

### Q6.16: Stale Pattern Variable Flow Assumption
```java
public class Validator {
    public static boolean isValid(Object input) {
        boolean valid = false;
        if (input instanceof String s) {
            valid = s.length() > 0;
        }
        // Bug: if input is not String, valid is false, but what if empty string logic evolves?
        return valid;
    }
}
```

---

### Q6.17: Accidental Parameter Reassignment
```java
public class TaxService {
    public double calculateTax(double grossIncome, final double rate) {
        if (grossIncome > 100_000) {
            grossIncome = grossIncome - 10_000; // Mutating parameter creates confusion
        }
        return grossIncome * rate;
    }
}
```

---

### Q6.18: Instance Variable Masked by Local Block
```java
public class TimerTask {
    private long startTime;

    public void start() {
        {
            long startTime = System.currentTimeMillis(); // Bug: local shadows field!
        }
    }
    public long getStartTime() { return startTime; } // Returns 0!
}
```

---

### Q6.19: Overly Broad Scope of Mutation Lock
```java
public class SafeCounter {
    private int count = 0;
    private final Object lock = new Object();

    public void increment() {
        // Broad scope: lock held during expensive validation
        synchronized (lock) {
            expensiveValidation();
            count++;
        }
    }
    void expensiveValidation() { /* 100ms delay */ }
}
```

---

### Q6.20: Closure Capturing Mutable Object Internal State
```java
public class FilterFactory {
    public static Predicate<String> createLengthFilter(int[] minLengthHolder) {
        return s -> s.length() >= minLengthHolder[0]; // Bug: state can be mutated externally!
    }
}
```

---

### LEVEL 6 ANSWERS & CODE REPAIRS

1. **Fix:** Use `this.username = username;`. Disambiguates instance field from parameter.
2. **Fix:** Add `break;` before `case 2:`, or refactor to Java 21 switch rules: `case 1 -> amount * 0.90; case 2 -> amount * 0.80;`.
3. **Fix:** Use an independent inner loop variable: `for (int j = 0; j < matrix[i].length; j++)`.
4. **Fix:** Remove field `speedLimit` from `Truck` and pass the speed to `super(speedLimit)` constructor, or override getter method `public int getSpeedLimit()`.
5. **Fix:** Avoid parallel mutation of a shared array. Use `items.parallelStream().filter(s -> s != null && !s.isEmpty()).count();`.
6. **Fix:** Narrow `parsed` variable scope to inside the loop body: `String parsed = raw.startsWith("DATA:") ? raw.substring(5) : null;`.
7. **Fix:** Remove `int processedCount = 0;` inside the `Runnable`. Use `AtomicInteger` for the field to ensure thread safety.
8. **Fix:** Move try-with-resources inside the loop: `for (File f : files) { try (BufferedReader reader = new BufferedReader(new FileReader(f))) { ... } }`.
9. **Fix:** Change `private static String currentOrderId;` to `private String currentOrderId;` so each session instance has its own state.
10. **Fix:** Change `{ int port = 9090; }` to `{ this.port = 9090; }` or `{ port = 9090; }`.
11. **Fix:** Always wrap execution in a try-finally and call `user.remove()` inside `finally`.
12. **Fix:** Static methods are not polymorphic. Refactor `getPrefix()` to an instance method or call `Alert.getPrefix()` directly.
13. **Fix:** Change `SingletonRegistry instance = ...` to `instance = new SingletonRegistry();` so the static field is assigned.
14. **Fix:** Consistently name target search variables and use standard iteration naming `for (String item : row) if (item.equals(target)) ...`.
15. **Fix:** To reference outer instance members unambiguously, use `ButtonHandler.this.label`.
16. **Fix:** Combine into direct boolean expression: `return input instanceof String s && !s.isEmpty();`.
17. **Fix:** Mark parameters `final`: `(final double grossIncome, final double rate)` and declare a new local variable `double adjustedIncome = ...`.
18. **Fix:** Change `long startTime = ...` to `this.startTime = System.currentTimeMillis();`.
19. **Fix:** Narrow the synchronized block to only wrap `count++`: execute `expensiveValidation()` outside `synchronized (lock)`.
20. **Fix:** Copy the primitive value: `int min = minLengthHolder[0]; return s -> s.length() >= min;`.


---

## LEVEL 7: FILL IN THE MISSING CODE (20 Questions)

*Fill in the blank `___` with the exact keyword, qualifier, access modifier, or expression to make the code compile and function correctly according to Java scope rules.*

### Q7.1
*Qualify the instance field assignment to prevent parameter shadowing self-assignment:*
```java
public class Employee {
    private String name;
    public Employee(String name) {
        ___ .name = name;
    }
}
```

---

### Q7.2
*Access the outer enclosing instance's field `tag` from an inner class:*
```java
public class OuterHost {
    private String tag = "HOST";
    class InnerWorker {
        private String tag = "WORKER";
        public void printOuter() {
            System.out.println( ___ .tag);
        }
    }
}
```

---

### Q7.3
*Allow a subclass in another package to access a field while keeping it hidden from unrelated classes:*
```java
package com.security;
public class AuthToken {
    ___ String tokenSecret = "SECRET_KEY";
}
```

---

### Q7.4
*Complete the pattern matching condition to bind the variable `text` and test its length in one expression:*
```java
public void process(Object obj) {
    if (obj ___ String text && text.length() > 3) {
        System.out.println(text);
    }
}
```

---

### Q7.5
*Ensure the local variable `resource` is automatically closed at the end of the block:*
```java
___ (FileReader resource = new FileReader("data.txt")) {
    int data = resource.read();
}
```

---

### Q7.6
*Guarantee that a blank local variable can be assigned at most once across all branches:*
```java
___ int maxThreshold;
if (mode == 1) {
    maxThreshold = 10;
} else {
    maxThreshold = 20;
}
```

---

### Q7.7
*Yield a value from a block within a switch expression:*
```java
int result = switch (score) {
    case 100 -> {
        int bonus = 50;
        ___ score + bonus;
    }
    default -> score;
};
```

---

### Q7.8
*Invoke a hidden static method `log()` defined on superclass `BaseLogger`:*
```java
class CustomLogger extends BaseLogger {
    public static void log() {
        ___ .log(); // Call BaseLogger's hidden static method
    }
}
```

---

### Q7.9
*Access a static member `DB_URL` directly without class qualification:*
```java
import ___ com.database.Config.DB_URL;
```

---

### Q7.10
*Declare a member class that does NOT maintain an implicit reference to the outer enclosing instance:*
```java
public class NetworkClient {
    public ___ class ConnectionStats {
        int packetsSent;
    }
}
```

---

### Q7.11
*Specify a type variable on a generic static utility method:*
```java
public static < ___ > List<T> singleton(T item) {
    return List.of(item);
}
```

---

### Q7.12
*Complete a compact constructor for a record where the component parameter `email` is trimmed:*
```java
public record Account(String email) {
    public Account {
        email = email. ___ ();
    }
}
```

---

### Q7.13
*Disambiguate cast to read the superclass's hidden instance field `id`:*
```java
ChildClass c = new ChildClass();
int parentId = (( ___ ) c).id;
```

---

### Q7.14
*Declare a variable whose value cannot be reassigned after initialization:*
```java
___ double PI = 3.141592653589793;
```

---

### Q7.15
*Complete the flow scoping guard clause so that `str` remains in scope for the remainder of the method:*
```java
public void handle(Object obj) {
    if ( ___ (obj instanceof String str)) {
        return;
    }
    System.out.println(str.toLowerCase());
}
```

---

### Q7.16
*Ensure a class cannot be instantiated outside its own file by restricting its constructor:*
```java
public class KeyVault {
    ___ KeyVault() {}
}
```

---

### Q7.17
*Break out of an outer loop from within a nested inner loop using a label:*
```java
___ : for (int i = 0; i < 10; i++) {
    for (int j = 0; j < 10; j++) {
        if (i * j == 25) break outer;
    }
}
```

---

### Q7.18
*Prevent a class member from being inherited or overridden in any subclass:*
```java
public class SecurityManager {
    public ___ void validateToken() {}
}
```

---

### Q7.19
*Access a superclass constructor explicitly from a subclass constructor:*
```java
public class Manager extends Employee {
    public Manager(String name) {
        ___ (name);
    }
}
```

---

### Q7.20
*Capture and handle an exception in a multi-catch statement while giving the parameter variable minimum necessary type:*
```java
try {
    performIO();
} catch (IOException | SQLException ___ ) {
    log.error("Failed", ex);
}
```

---

### LEVEL 7 ANSWERS & EXPLANATIONS

1. `this` — `this.name` qualifies the instance field.
2. `OuterHost.this` — Explicit enclosing class instance qualifier.
3. `protected` — Grants package-private plus subclass visibility across packages.
4. `instanceof` — Java pattern matching keyword introducing variable `text`.
5. `try` — Initiates try-with-resources statement.
6. `final` — Enforces definite assignment and prevents subsequent reassignment.
7. `yield` — Java switch expression keyword yielding a value from a block.
8. `BaseLogger` — Static methods are invoked directly via their declaring class identifier.
9. `static` — Static import syntax: `import static com.database.Config.DB_URL;`.
10. `static` — Static nested classes do not retain an enclosing outer instance reference.
11. `T` — Generic type parameter declaration.
12. `trim` — Calls `.trim()` on the component parameter before implicit assignment.
13. `ParentClass` — Explicit cast to superclass type to read hidden field.
14. `final` — Immutability keyword.
15. `!` — Logical negation inverting the pattern test; with `return`, flow scoping preserves `str`.
16. `private` — Prevents constructor invocation outside the declaring class.
17. `outer` — Target label identifier matching `break outer;`.
18. `final` — Prevents method overriding.
19. `super` — Calls superclass constructor.
20. `ex` — Formal parameter name for the union catch block.

---

## LEVEL 8: EXPLAIN WHY THIS CODE WORKS (20 Questions)

*Provide the exact JLS and JVM architectural rationale for why each snippet compiles and behaves as described.*

### Q8.1: Pattern Variable Scope Following Inverted Flow Check
```java
public static void printString(Object obj) {
    if (!(obj instanceof String s)) {
        throw new IllegalArgumentException("Not a string");
    }
    System.out.println(s.toUpperCase()); // Why does 's' compile here?
}
```

---

### Q8.2: Slot Reuse for Sequential Scopes in JVM Bytecode
```java
public void compute() {
    {
        int x = 10;
        System.out.println(x);
    }
    {
        int y = 20;
        System.out.println(y);
    }
}
```
*Why do `x` and `y` both occupy slot index 1 in the compiled `LocalVariableTable`?*

---

### Q8.3: Anonymous Class Access to Enclosing Private Fields
```java
public class OuterSecret {
    private String secret = "ALPHA_SECRET";

    public Runnable getWorker() {
        return new Runnable() {
            @Override
            public void run() {
                System.out.println(secret); // Accesses private field of outer class!
            }
        };
    }
}
```
*Why does the JVM permit an anonymous class to read private members of its outer class?*

---

### Q8.4: Multiple Re-declarations of `i` in Consecutive For-Loops
```java
for (int i = 0; i < 5; i++) {}
for (int i = 0; i < 5; i++) {}
```
*Why does `javac` accept two declarations of `int i` in the same method without collision?*

---

### Q8.5: Disambiguation of Hidden Fields via Upcasting
```java
class SuperNode { int id = 1; }
class SubNode extends SuperNode { int id = 2; }
// ((SuperNode) new SubNode()).id evaluates to 1
```
*Why does casting an object reference change which field value is accessed?*

---

### Q8.6: Lambda Access to `this` Without Enclosing Class Name
```java
public class Window {
    int width = 800;
    public void resize() {
        Runnable r = () -> System.out.println(this.width);
        r.run();
    }
}
```
*Why does `this` inside the lambda refer to `Window` rather than an instance of `Runnable`?*

---

### Q8.7: Record Compact Constructor Parameter Scoping
```java
public record Range(int start, int end) {
    public Range {
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }
    }
}
```
*Why does swapping `start` and `end` in the compact constructor successfully set the final record fields?*

---

### Q8.8: Static Methods Cannot Be Overridden, Only Hidden
```java
class P { static void test() {} }
class C extends P { static void test() {} }
```
*Why is this legal, and why does the compiler prohibit `@Override` on `C.test()`?*

---

### Q8.9: ClassLoader Isolation of Static Fields
```java
Class<?> c1 = loader1.loadClass("Config");
Class<?> c2 = loader2.loadClass("Config");
// c1 != c2, and their static fields are completely separate in memory
```
*Why are static fields isolated between different ClassLoaders even if loaded from the identical `.class` byte stream?*

---

### Q8.10: Definite Assignment with Unconditional Returns
```java
public int calculate(int mode) {
    final int value;
    if (mode == 1) {
        value = 100;
    } else {
        return -1;
    }
    return value; // Why is value definitely assigned here?
}
```

---

### Q8.11: Non-Leakage of Try-With-Resources Variables into Catch
```java
try (BufferedReader br = new BufferedReader(new FileReader("f"))) {
    br.readLine();
} catch (IOException e) {
    // br is out of scope here!
}
```
*Why is `br` strictly out of scope inside the `catch` block?*

---

### Q8.12: Access to Outer Class from Static Nested Class
```java
public class Outer {
    private static int counter = 0;
    public static class Nested {
        void inc() { counter++; }
    }
}
```
*Why does `Nested` have access to `private static int counter`?*

---

### Q8.13: Effectively Final Capture in Lambdas
```java
String delimiter = ",";
list.forEach(item -> System.out.print(item + delimiter));
```
*Why does Java permit capturing `delimiter` without the explicit `final` modifier?*

---

### Q8.14: Method Parameter Overriding Superclass Field Name
```java
class SuperA { int count = 10; }
class SubB extends SuperA {
    void process(int count) {
        System.out.println(count);
    }
}
```
*Why does `count` print the parameter rather than `SuperA.count`?*

---

### Q8.15: Isolated Scope Across Switch Rule Branches
```java
switch (val) {
    case 1 -> { int x = 10; yield x; }
    case 2 -> { int x = 20; yield x; }
}
```
*Why can variable `x` be declared in both branches without a compiler error?*

---

### Q8.16: Protected Field Access via Subclass Reference
```java
package p2;
import p1.Base;
public class Sub extends Base {
    void test(Sub s) { System.out.println(s.protectedField); }
}
```
*Why does accessing `s.protectedField` compile, but `((Base) s).protectedField` fails?*

---

### Q8.17: Pattern Matching in Short-Circuit `&&` Expressions
```java
if (obj instanceof String s && s.length() > 0) {}
```
*Why is `s` guaranteed to be in scope on the right-hand side of `&&`?*

---

### Q8.18: Instance Field Initialization Before Constructor Body
```java
class Demo {
    int x = 10;
    Demo() {
        System.out.println(x); // Prints 10
    }
}
```
*Why is `x` already 10 before the constructor body executes?*

---

### Q8.19: Package-Private Access within Split Packages (Pre-Java 9 Modules)
*Why can two classes in the same package name access each other's package-private members when loaded from the same classpath?*

---

### Q8.20: Labeled Block Early Exit and Variable Destruction
```java
block: {
    int temp = 42;
    if (temp > 0) break block;
    int dead = 99;
}
```
*Why does the compiler guarantee that neither `temp` nor `dead` exists outside the label?*

---

### LEVEL 8 ANSWERS & JLS / JVM SPECIFICATION EXPLANATIONS

1. **JLS §14.30.1**: The pattern variable `s` is introduced into the scope of statements following the `if` because the condition tested `!instanceof`, and the `if` body unconditionally abruptly terminates via `throw`. Therefore, the continuation of the method can only be reached if `obj instanceof String` was true.
2. **JVM Spec §2.6.1 & `javap` LocalVariableTable**: The lexical scopes of the two blocks are disjoint (non-overlapping). The Java compiler reuses stack frame slot 1 for both variables, optimizing frame memory allocation.
3. **JLS §8.1.3 & JVM Nestmates (JEP 181)**: Inner classes are member entities of the enclosing class nest. Since Java 11, the JVM natively supports Nest-Based Access Control, permitting direct private access via bytecode instructions `getfield`/`invokevirtual` without synthetic bridge methods.
4. **JLS §6.3**: The scope of a `ForInit` variable declaration ends with the conclusion of that specific `for` statement. Once the first loop ends, identifier `i` is no longer in scope, allowing subsequent declarations.
5. **JLS §15.11.1**: Field access expressions are resolved statically at compile time based strictly on the compile-time type of the qualifying expression. Casting `(SuperNode)` instructs the compiler to emit a `getfield` pointing to `SuperNode.id`.
6. **JLS §15.27.2**: A lambda expression is lexically scoped within its containing class. Unlike an anonymous class, it does not define an independent class instance scope for `this`. `this` always resolves to the enclosing instance.
7. **JLS §8.10.4**: In a compact record constructor, the parameters implicitly represent the record components. Mutating `start` and `end` mutates the constructor parameters; at the end of the compact constructor, `javac` emits bytecode assigning those parameters to the respective final fields.
8. **JLS §8.4.8.2**: Static methods cannot be overridden because they are bound statically (via `invokestatic`) at compile time. They can only be *hidden*. The `@Override` annotation requires an overridden instance method, causing compilation to fail.
9. **JVM Spec §5.3**: A runtime class type is uniquely identified by the tuple `(ClassLoader, BinaryName)`. Two classes loaded by distinct ClassLoaders represent distinct Class objects in Metaspace, each possessing its own independent static storage.
10. **JLS §16**: Flow analysis verifies that execution can only reach `return value;` via the `if (mode == 1)` branch because the `else` branch executes an abrupt completion (`return -1`). Thus, `value` is definitely assigned on the only path reaching the return.
11. **JLS §14.20.3**: Resources declared in try-with-resources are closed automatically *before* any `catch` block is entered. If an exception occurs, the resource is already closed; providing access inside `catch` would expose an already-closed or invalid resource.
12. **JLS §8.5**: Member types (including static nested classes) have full access to all static members of their enclosing top-level class, including `private` static fields and methods.
13. **JLS §4.12.4**: The compiler checks whether a local variable is ever reassigned after initialization. If never mutated, it treats the variable as effectively final, satisfying closure safety without needing explicit `final`.
14. **JLS §6.4.1**: Formal parameters shadow all accessible fields of the same name declared in superclasses or enclosing classes within the method body.
15. **JLS §14.11**: Switch rule branches with blocks `{ ... }` create independent lexical scopes. The scope of variable `x` declared in one branch terminates at the closing brace of that branch.
16. **JLS §6.6.2**: Protected instance members can only be accessed from outside their defining package by a subclass if the access expression's type is that subclass or a subtype thereof. An upcast to `Base` strips this guarantee, violating the specification.
17. **JLS §14.30.1**: Short-circuit operator `&&` only evaluates the right operand if the left operand evaluates to `true`. Because the left operand establishes `obj instanceof String s`, `s` is definitively matched on the right side.
18. **JLS §12.5**: Field initializers and instance initializer blocks are compiled directly into the constructor method (`<init>`) immediately following the invocation of `super()`, before any statements of the constructor body execute.
19. **JLS §6.6.1**: Package-private access is granted based on package identity. Without module boundaries, classes sharing the exact same package string loaded by the same ClassLoader can access each other's default-access members.
20. **JLS §14.7**: Labeled blocks introduce a bounded lexical scope. Breaking out of the block transfers control to the statement immediately following the block, discarding all variables allocated within that scope.


---

## LEVEL 9: REFACTORING EXERCISES (20 Exercises)

*Refactor each problematic code snippet from anti-patterns to modern Java 21 best practices, eliminating unnecessary scope exposure, accidental shadowing, or resource leaks.*

### Refactoring 9.1: Overly Broad Loop Control Variable
```java
// Problem: 'i' and 'len' leak into the entire method
public void printItems(List<String> items) {
    int i;
    int len = items.size();
    for (i = 0; i < len; i++) {
        System.out.println(items.get(i));
    }
    // 50 more lines of code where 'i' and 'len' remain in scope
}
```

---

### Refactoring 9.2: Method Local Variable Unnecessarily Promoted to Class Field
```java
// Problem: 'tempCalculation' is stored as a class field, making the class stateful and not thread-safe
public class PriceEngine {
    private double tempCalculation;

    public double computeTax(double price) {
        tempCalculation = price * 0.08;
        return tempCalculation;
    }
}
```

---

### Refactoring 9.3: Legacy Switch with Fall-Through and Scope Bleed
```java
// Problem: Shared lexical scope and fall-through bug risks
public String getStatusDescription(int code) {
    String desc;
    switch (code) {
        case 200:
            desc = "OK";
            break;
        case 404:
            desc = "Not Found";
            break;
        default:
            desc = "Unknown";
            break;
    }
    return desc;
}
```

---

### Refactoring 9.4: Broad Resource Scope in Legacy Try-Finally
```java
// Problem: Connection, Statement, ResultSet declared at method top; verbose and error-prone cleanup
public void executeQuery(DataSource ds) throws SQLException {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
        conn = ds.getConnection();
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT count(*) FROM users");
        if (rs.next()) System.out.println(rs.getInt(1));
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException e) {}
        if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
        if (conn != null) try { conn.close(); } catch (SQLException e) {}
    }
}
```

---

### Refactoring 9.5: Accidental Parameter Self-Assignment
```java
// Problem: Constructor parameters shadow fields without 'this.' qualification
public class Configuration {
    private String host;
    private int port;

    public Configuration(String host, int port) {
        host = host;
        port = port;
    }
}
```

---

### Refactoring 9.6: Non-Static Inner Class Retaining Outer Instance
```java
// Problem: OrderHandler holds implicit reference to large OrderService instance
public class OrderService {
    private byte[] largeMetadata = new byte[10_000_000];

    public Runnable createHandler(String orderId) {
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("Processing " + orderId);
            }
        };
    }
}
```

---

### Refactoring 9.7: Overly Broad Synchronized Block
```java
// Problem: Synchronization scope encompasses non-critical operations
public class CounterManager {
    private int count = 0;

    public synchronized void processAndIncrement(String payload) {
        validatePayload(payload);
        parseJson(payload);
        count++;
        sendAuditLog(payload);
    }
    void validatePayload(String p) {}
    void parseJson(String p) {}
    void sendAuditLog(String p) {}
}
```

---

### Refactoring 9.8: Mutable Static Context in Multi-Threaded Service
```java
// Problem: Global static field used for per-request authentication context
public class SecurityHolder {
    public static String currentUser;

    public static void set(String u) { currentUser = u; }
    public static String get() { return currentUser; }
}
```

---

### Refactoring 9.9: Manual Cast Following Instanceof Check
```java
// Problem: Legacy instanceof requires separate cast, creating redundant local variable
public void handlePayload(Object payload) {
    if (payload instanceof String) {
        String s = (String) payload;
        System.out.println("String of length: " + s.length());
    }
}
```

---

### Refactoring 9.10: Variable Reused for Multiple Unrelated Purposes
```java
// Problem: 'temp' is reused across unrelated calculations, obscuring domain meaning
public void runCalculations() {
    int temp = calculateInitialScore();
    System.out.println("Initial: " + temp);

    temp = getCustomerAge();
    System.out.println("Age: " + temp);

    temp = computeDaysActive();
    System.out.println("Days: " + temp);
}
int calculateInitialScore() { return 10; }
int getCustomerAge() { return 25; }
int computeDaysActive() { return 100; }
```

---

### Refactoring 9.11: Leaking Loop Index After Search
```java
// Problem: 'foundIndex' scope spans entire method instead of narrow search logic
public int findFirstPositive(int[] numbers) {
    int foundIndex = -1;
    for (int i = 0; i < numbers.length; i++) {
        if (numbers[i] > 0) {
            foundIndex = i;
            break;
        }
    }
    return foundIndex;
}
```

---

### Refactoring 9.12: Unclosed ThreadLocal Memory Leak
```java
// Problem: ThreadLocal values are never removed, leaking memory in thread pools
public class WebContextFilter {
    public static final ThreadLocal<RequestContext> CTX = new ThreadLocal<>();

    public void doFilter(Request req) {
        CTX.set(new RequestContext(req));
        processRequest(req);
        // Missing removal!
    }
    void processRequest(Request r) {}
}
class Request {}
class RequestContext { RequestContext(Request r) {} }
```

---

### Refactoring 9.13: Deeply Nested Conditional Scopes
```java
// Problem: Arrow anti-pattern with excessive indentation and widened variable scopes
public void processUser(User user) {
    if (user != null) {
        if (user.isActive()) {
            if (user.hasPermission("ADMIN")) {
                performAdminTask(user);
            }
        }
    }
}
class User { boolean isActive() { return true; } boolean hasPermission(String s) { return true; } }
void performAdminTask(User u) {}
```

---

### Refactoring 9.14: Field Hiding in Subclass Hierarchy
```java
// Problem: Subclass re-declares field, masking superclass field and causing confusion
class BaseEntity {
    protected Long id;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}

class UserEntity extends BaseEntity {
    protected Long id; // Accidental field hiding!
}
```

---

### Refactoring 9.15: Wide Scope for Temporary Buffer
```java
// Problem: Large buffer stays referenced during long-running network operation
public void processFile(File f) throws IOException {
    byte[] buffer = new byte[50 * 1024 * 1024];
    readFile(f, buffer);
    transform(buffer);
    performSyncNetworkCall(); // Buffer held live on stack frame for 5 minutes!
}
void readFile(File f, byte[] b) {}
void transform(byte[] b) {}
void performSyncNetworkCall() {}
```

---

### Refactoring 9.16: Accidental Shadowing in Builder Pattern
```java
// Problem: Builder field not assigned due to lack of 'this.'
public class ServerConfig {
    private String host;
    public static class Builder {
        private String host;
        public Builder host(String host) {
            host = host; // Bug!
            return this;
        }
        public ServerConfig build() {
            ServerConfig sc = new ServerConfig();
            sc.host = this.host;
            return sc;
        }
    }
}
```

---

### Refactoring 9.17: Nested Ternary Expression Obscuring Variable Lifecycles
```java
// Problem: Nested ternary expressions make flow scoping unreadable
public String classify(int score) {
    return score >= 90 ? "A" : score >= 80 ? "B" : score >= 70 ? "C" : "F";
}
```

---

### Refactoring 9.18: Mutable Closure State
```java
// Problem: Using single-element array to bypass effectively-final restriction in lambda
public int countMatches(List<String> list, String query) {
    final int[] count = {0};
    list.forEach(item -> {
        if (item.contains(query)) count[0]++;
    });
    return count[0];
}
```

---

### Refactoring 9.19: Exposing Internal Mutable Collection Scope
```java
// Problem: Exposes internal collection directly, breaking encapsulation scope
public class Registry {
    private List<String> entries = new ArrayList<>();
    public List<String> getEntries() {
        return entries; // Callers can mutate internal state
    }
}
```

---

### Refactoring 9.20: Parameter Reassignment in Calculation Method
```java
// Problem: Mutating incoming parameters creates confusing variable states
public double calculateFinalPrice(double price, double discount) {
    if (price > 1000) {
        discount += 0.05; // Mutating parameter!
    }
    price = price * (1 - discount); // Mutating parameter!
    return price;
}
```

---

### LEVEL 9 REFACTORING SOLUTIONS & RATIONALE

1. **Refactored 9.1:**
   ```java
   public void printItems(List<String> items) {
       for (String item : items) {
           System.out.println(item);
       }
   }
   ```
   *Rationale:* Enhanced `for` loop restricts the iteration variable scope exclusively to the loop body and eliminates index maintenance.

2. **Refactored 9.2:**
   ```java
   public class PriceEngine {
       public double computeTax(double price) {
           double tax = price * 0.08;
           return tax;
       }
   }
   ```
   *Rationale:* Demotes field to local variable, rendering the method pure, thread-safe, and stateless.

3. **Refactored 9.3:**
   ```java
   public String getStatusDescription(int code) {
       return switch (code) {
           case 200 -> "OK";
           case 404 -> "Not Found";
           default -> "Unknown";
       };
   }
   ```
   *Rationale:* Java 21 switch expression guarantees exhaustive return, isolates rule scope, and eliminates fall-through.

4. **Refactored 9.4:**
   ```java
   public void executeQuery(DataSource ds) throws SQLException {
       String sql = "SELECT count(*) FROM users";
       try (Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
           if (rs.next()) {
               System.out.println(rs.getInt(1));
           }
       }
   }
   ```
   *Rationale:* Try-with-resources scopes handles strictly to the execution block and guarantees LIFO deterministic closure.

5. **Refactored 9.5:**
   ```java
   public class Configuration {
       private final String host;
       private final int port;

       public Configuration(String host, int port) {
           this.host = host;
           this.port = port;
       }
   }
   ```
   *Rationale:* Explicitly qualifies fields with `this.` and marks fields `final` to ensure immutability and compiler-enforced initialization.

6. **Refactored 9.6:**
   ```java
   public class OrderService {
       private byte[] largeMetadata = new byte[10_000_000];

       public Runnable createHandler(String orderId) {
           return new OrderHandler(orderId);
       }

       private static class OrderHandler implements Runnable {
           private final String orderId;
           OrderHandler(String orderId) { this.orderId = orderId; }
           @Override public void run() { System.out.println("Processing " + orderId); }
       }
   }
   ```
   *Rationale:* Static nested class severs the implicit `this$0` outer reference, allowing `OrderService` instances to be collected.

7. **Refactored 9.7:**
   ```java
   public class CounterManager {
       private int count = 0;
       private final Object lock = new Object();

       public void processAndIncrement(String payload) {
           validatePayload(payload);
           parseJson(payload);
           synchronized (lock) {
               count++;
           }
           sendAuditLog(payload);
       }
       void validatePayload(String p) {}
       void parseJson(String p) {}
       void sendAuditLog(String p) {}
   }
   ```
   *Rationale:* Narrows synchronization scope to only the shared state mutation, maximizing concurrency.

8. **Refactored 9.8:**
   ```java
   public final class SecurityHolder {
       private static final ScopedValue<String> CURRENT_USER = ScopedValue.newInstance();

       public static ScopedValue<String> currentUser() { return CURRENT_USER; }
   }
   ```
   *Rationale:* Utilizes Java 21 `ScopedValue` (or alternatively `ThreadLocal` with try-finally cleanup) to bind thread context strictly to bounded call hierarchies.

9. **Refactored 9.9:**
   ```java
   public void handlePayload(Object payload) {
       if (payload instanceof String s) {
           System.out.println("String of length: " + s.length());
       }
   }
   ```
   *Rationale:* Pattern matching eliminates redundant casting and scopes `s` cleanly to the matched block.

10. **Refactored 9.10:**
    ```java
    public void runCalculations() {
        int initialScore = calculateInitialScore();
        System.out.println("Initial: " + initialScore);

        int customerAge = getCustomerAge();
        System.out.println("Age: " + customerAge);

        int daysActive = computeDaysActive();
        System.out.println("Days: " + daysActive);
    }
    ```
    *Rationale:* Dedicated, descriptive `final` variables narrow semantic scope and eliminate variable reuse bugs.

11. **Refactored 9.11:**
    ```java
    public int findFirstPositive(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > 0) {
                return i;
            }
        }
        return -1;
    }
    ```
    *Rationale:* Immediate return eliminates the leaked status variable.

12. **Refactored 9.12:**
    ```java
    public void doFilter(Request req) {
        try {
            CTX.set(new RequestContext(req));
            processRequest(req);
        } finally {
            CTX.remove();
        }
    }
    ```
    *Rationale:* Guaranteed `remove()` prevents memory leaks and cross-request state contamination in pooled threads.

13. **Refactored 9.13:**
    ```java
    public void processUser(User user) {
        if (user == null || !user.isActive() || !user.hasPermission("ADMIN")) {
            return;
        }
        performAdminTask(user);
    }
    ```
    *Rationale:* Guard clauses reduce nesting depth and restrict processing scope.

14. **Refactored 9.14:**
    ```java
    class UserEntity extends BaseEntity {
        // Removed redundant 'id' field declaration; relies on inherited BaseEntity.id
    }
    ```
    *Rationale:* Eliminates field hiding, ensuring single source of truth for entity identifiers.

15. **Refactored 9.15:**
    ```java
    public void processFile(File f) throws IOException {
        loadAndTransform(f);
        performSyncNetworkCall();
    }

    private void loadAndTransform(File f) throws IOException {
        byte[] buffer = new byte[50 * 1024 * 1024];
        readFile(f, buffer);
        transform(buffer);
    }
    ```
    *Rationale:* Extracting buffer logic into a helper method terminates its stack frame slot before the long-running call begins, enabling GC reclamation.

16. **Refactored 9.16:**
    ```java
    public Builder host(String host) {
        this.host = host;
        return this;
    }
    ```
    *Rationale:* Resolves parameter shadowing with `this.`.

17. **Refactored 9.17:**
    ```java
    public String classify(int score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        return "F";
    }
    ```
    *Rationale:* Clear linear branching replaces unreadable ternary chains.

18. **Refactored 9.18:**
    ```java
    public long countMatches(List<String> list, String query) {
        return list.stream().filter(item -> item.contains(query)).count();
    }
    ```
    *Rationale:* Declarative stream pipeline eliminates mutable array closures.

19. **Refactored 9.19:**
    ```java
    public class Registry {
        private final List<String> entries = new ArrayList<>();
        public List<String> getEntries() {
            return Collections.unmodifiableList(entries);
        }
    }
    ```
    *Rationale:* Protects internal scope by returning an unmodifiable view.

20. **Refactored 9.20:**
    ```java
    public double calculateFinalPrice(final double price, final double discount) {
        double effectiveDiscount = (price > 1000) ? discount + 0.05 : discount;
        return price * (1.0 - effectiveDiscount);
    }
    ```
    *Rationale:* Marking parameters `final` and declaring descriptive intermediate variables maintains pure functional flow.

---

## LEVEL 10: CODING EXERCISES (20 Questions)

*Solve each progressive coding challenge focusing on strict scope boundaries, encapsulation, and modern Java 21 architecture.*

### Exercise 10.1: Scope-Guarded Execution Block
*Write a utility method `executeWithScopeGuard(Runnable task)` that executes a task, measures elapsed nanoseconds using variables scoped strictly to the measurement block, and returns the elapsed time without leaking state.*

---

### Exercise 10.2: Fluent Disambiguating Person Builder
*Write a `Person` class with `id`, `name`, and `email` fields. Implement a static inner `Builder` where every parameter shares the exact identifier as the builder's fields and the `Person` fields, correctly disambiguating all scopes.*

---

### Exercise 10.3: Inheritance Field Isolation Laboratory
*Implement classes `BaseMetric` and `ExtendedMetric` where both declare `public final String code`. Provide an accessor in `ExtendedMetric` that explicitly returns both the superclass's `code` and the subclass's `code`.*

---

### Exercise 10.4: Pattern Flow Scoping Validator
*Write a method `validateJsonPayload(Object input)` that validates whether `input` is a non-empty `Map<String, Object>`. Use pattern flow scoping so that the validated map is returned directly without casting or extra variables.*

---

### Exercise 10.5: LocalVariableTable Slot Inspection Model
*Write a class modeling variable slot assignments with fields: `variableName`, `slotIndex`, `startPc`, `length`. Provide a method `boolean overlaps(SlotAssignment other)` to determine if two variables contend for the same frame slot.*

---

### Exercise 10.6: AutoCloseable ThreadLocal Scope Context
*Create a class `ScopeContext implements AutoCloseable` wrapping a `ThreadLocal<String>`. Implement `ScopeContext open(String user)` so it can be used in try-with-resources: `try (var ctx = ScopeContext.open("admin")) { ... }` ensuring guaranteed removal upon exit.*

---

### Exercise 10.7: Dual-Resource Try-With-Resources Pipeline
*Write a method that copies bytes from an input file to an output file using try-with-resources with both `FileInputStream` and `FileOutputStream` declared in the resource specification header.*

---

### Exercise 10.8: Decoupled Memory Listener with WeakReference
*Write a class `ListenerDecoupler` with an inner listener that observes events without retaining an implicit or explicit hard reference to the host class instance.*

---

### Exercise 10.9: Pattern Matching Record Evaluator
*Define sealed interface `ArithmeticExpr` with records `Const(double val)`, `Negate(ArithmeticExpr expr)`, and `Add(ArithmeticExpr left, ArithmeticExpr right)`. Write an `eval(ArithmeticExpr expr)` method using a switch expression.*

---

### Exercise 10.10: Lexical Closure Counter Factory
*Write a method `Supplier<Integer> createCounter(int initialValue)` returning a closure that increments and returns the counter on each invocation.*

---

### Exercise 10.11: Labeled Loop Matrix Searcher
*Write a method `Point findCoordinate(int[][] matrix, int target)` that scans a 2D matrix using a labeled loop to immediately break out of nested iteration upon locating the target.*

---

### Exercise 10.12: Blank Final Definite Assignment Verifier
*Write a method demonstrating definite assignment where a `final int result;` is assigned across three disjoint conditional paths, proving compilation succeeds.*

---

### Exercise 10.13: Sealed Class Pattern Router
*Create a sealed hierarchy `Command` with records `Start(int port)`, `Stop(String reason)`, and `Restart(int port, boolean force)`. Implement `String route(Command cmd)` using Java 21 pattern matching.*

---

### Exercise 10.14: Definite Unassignment Guard
*Write a method showing how a `final` local variable is verified as definitely unassigned before assignment inside a branch.*

---

### Exercise 10.15: Nested Record Pattern Deconstruction
*Given `record Point(int x, int y)` and `record Box(Point upperLeft, Point lowerRight)`, write a method `int area(Box b)` using Java 21 nested record pattern matching to extract `x1, y1, x2, y2` in a single switch case.*

---

### Exercise 10.16: Lambda Lexical Scope Verifier
*Write a class demonstrating that a lambda does not shadow enclosing parameters, and verify the compiler diagnostic when duplicate naming is attempted.*

---

### Exercise 10.17: Java 21 ScopedValue Demonstration
*Implement a simple workflow using Java 21 `ScopedValue` to pass an authentication token down a call stack without parameter passing.*

---

### Exercise 10.18: Interface Private Method Helper
*Create an interface `StringValidator` with public default methods `isValidEmail` and `isValidUrl` that delegate to a private helper method `checkNotEmpty` scoped strictly to the interface.*

---

### Exercise 10.19: Dynamic Proxy Invocation Scope Binder
*Implement an `InvocationHandler` that intercepts method calls and binds execution time metrics strictly within the invocation method scope.*

---

### Exercise 10.20: Hierarchical Lexical Scope Symbol Table
*Build an in-memory `SymbolTable` class supporting nested scopes: `enterScope()`, `exitScope()`, `define(String name, String type)`, and `lookup(String name)` that traverses parent scopes outward.*

---

### LEVEL 10 COMPLETE REFERENCE SOLUTIONS

#### Solution 10.1
```java
public class ScopeGuard {
    public static long executeWithScopeGuard(Runnable task) {
        long start = System.nanoTime();
        try {
            task.run();
        } finally {
            long duration = System.nanoTime() - start;
            return duration;
        }
    }
}
```

#### Solution 10.2
```java
public class Person {
    private final String id;
    private final String name;
    private final String email;

    private Person(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
    }

    public static class Builder {
        private String id;
        private String name;
        private String email;

        public Builder id(String id) {
            this.id = id;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Person build() {
            return new Person(this);
        }
    }
}
```

#### Solution 10.3
```java
class BaseMetric {
    public final String code = "BASE_METRIC";
}

class ExtendedMetric extends BaseMetric {
    public final String code = "EXTENDED_METRIC";

    public String[] getCodes() {
        return new String[] { super.code, this.code };
    }
}
```

#### Solution 10.4
```java
public class FlowValidator {
    @SuppressWarnings("unchecked")
    public static Map<String, Object> validateJsonPayload(Object input) {
        if (!(input instanceof Map<?, ?> map) || map.isEmpty()) {
            throw new IllegalArgumentException("Invalid payload");
        }
        return (Map<String, Object>) map;
    }
}
```

#### Solution 10.5
```java
public record SlotAssignment(String variableName, int slotIndex, int startPc, int length) {
    public boolean overlaps(SlotAssignment other) {
        if (this.slotIndex != other.slotIndex) return false;
        int thisEnd = this.startPc + this.length;
        int otherEnd = other.startPc + other.length;
        return this.startPc < otherEnd && other.startPc < thisEnd;
    }
}
```

#### Solution 10.6
```java
public class ScopeContext implements AutoCloseable {
    private static final ThreadLocal<String> CURRENT_USER = new ThreadLocal<>();

    public static ScopeContext open(String user) {
        CURRENT_USER.set(user);
        return new ScopeContext();
    }

    public static String currentUser() {
        return CURRENT_USER.get();
    }

    @Override
    public void close() {
        CURRENT_USER.remove();
    }
}
```

#### Solution 10.7
```java
public class FilePipeline {
    public static void copy(File src, File dest) throws IOException {
        try (InputStream in = new FileInputStream(src);
             OutputStream out = new FileOutputStream(dest)) {
            in.transferTo(out);
        }
    }
}
```

#### Solution 10.8
```java
public class ListenerDecoupler {
    private final String name;

    public ListenerDecoupler(String name) { this.name = name; }

    public static class DecoupledListener implements Runnable {
        private final WeakReference<ListenerDecoupler> hostRef;

        public DecoupledListener(ListenerDecoupler host) {
            this.hostRef = new WeakReference<>(host);
        }

        @Override
        public void run() {
            ListenerDecoupler host = hostRef.get();
            if (host != null) {
                System.out.println("Processing event for: " + host.name);
            }
        }
    }
}
```

#### Solution 10.9
```java
public class ExprEngine {
    public sealed interface ArithmeticExpr permits Const, Negate, Add {}
    public record Const(double val) implements ArithmeticExpr {}
    public record Negate(ArithmeticExpr expr) implements ArithmeticExpr {}
    public record Add(ArithmeticExpr left, ArithmeticExpr right) implements ArithmeticExpr {}

    public static double eval(ArithmeticExpr expr) {
        return switch (expr) {
            case Const(double v) -> v;
            case Negate(ArithmeticExpr e) -> -eval(e);
            case Add(ArithmeticExpr l, ArithmeticExpr r) -> eval(l) + eval(r);
        };
    }
}
```

#### Solution 10.10
```java
public class ClosureFactory {
    public static Supplier<Integer> createCounter(int initialValue) {
        final AtomicInteger counter = new AtomicInteger(initialValue);
        return counter::incrementAndGet;
    }
}
```

#### Solution 10.11
```java
public record Point(int row, int col) {}

public class MatrixScanner {
    public static Point findCoordinate(int[][] matrix, int target) {
        search: for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[r].length; c++) {
                if (matrix[r][c] == target) {
                    return new Point(r, c);
                }
            }
        }
        return null;
    }
}
```

#### Solution 10.12
```java
public class DefiniteAssignmentDemo {
    public static int resolve(int flag) {
        final int result;
        if (flag == 1) {
            result = 10;
        } else if (flag == 2) {
            result = 20;
        } else {
            result = 30;
        }
        return result;
    }
}
```

#### Solution 10.13
```java
public class CommandRouter {
    public sealed interface Command permits Start, Stop, Restart {}
    public record Start(int port) implements Command {}
    public record Stop(String reason) implements Command {}
    public record Restart(int port, boolean force) implements Command {}

    public static String route(Command cmd) {
        return switch (cmd) {
            case Start(int p) -> "Starting on port " + p;
            case Stop(String r) -> "Stopping due to " + r;
            case Restart(int p, boolean f) -> "Restarting on " + p + (f ? " (FORCED)" : "");
        };
    }
}
```

#### Solution 10.14
```java
public class DefiniteUnassignmentGuard {
    public static int compute(boolean condition) {
        final int value;
        if (condition) {
            value = 100;
        } else {
            value = 200;
        }
        return value;
    }
}
```

#### Solution 10.15
```java
public class BoxAreaCalculator {
    public record Point(int x, int y) {}
    public record Box(Point upperLeft, Point lowerRight) {}

    public static int area(Box box) {
        return switch (box) {
            case Box(Point(int x1, int y1), Point(int x2, int y2)) -> 
                Math.abs((x2 - x1) * (y2 - y1));
        };
    }
}
```

#### Solution 10.16
```java
public class LambdaScopeVerifier {
    public void test(String name) {
        // Consumer<String> c = name -> System.out.println(name); // FAILS: variable name is already defined
        Consumer<String> c = newName -> System.out.println(newName + " " + name);
        c.accept("Hello");
    }
}
```

#### Solution 10.17
```java
public class ScopedValueDemo {
    private static final ScopedValue<String> CONTEXT = ScopedValue.newInstance();

    public static void runInContext(String user, Runnable task) {
        ScopedValue.where(CONTEXT, user).run(task);
    }

    public static void printUser() {
        System.out.println("Current Scoped User: " + CONTEXT.get());
    }
}
```

#### Solution 10.18
```java
public interface StringValidator {
    default boolean isValidEmail(String s) {
        return checkNotEmpty(s) && s.contains("@");
    }

    default boolean isValidUrl(String s) {
        return checkNotEmpty(s) && s.startsWith("http");
    }

    private boolean checkNotEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }
}
```

#### Solution 10.19
```java
public class MetricProxyHandler implements InvocationHandler {
    private final Object target;

    public MetricProxyHandler(Object target) { this.target = target; }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.nanoTime();
        try {
            return method.invoke(target, args);
        } finally {
            long duration = System.nanoTime() - start;
            System.out.println(method.getName() + " took " + duration + "ns");
        }
    }
}
```

#### Solution 10.20
```java
public class SymbolTable {
    private static class Scope {
        final Map<String, String> symbols = new HashMap<>();
        final Scope parent;
        Scope(Scope parent) { this.parent = parent; }
    }

    private Scope currentScope = new Scope(null);

    public void enterScope() {
        currentScope = new Scope(currentScope);
    }

    public void exitScope() {
        if (currentScope.parent == null) throw new IllegalStateException("Cannot exit root scope");
        currentScope = currentScope.parent;
    }

    public void define(String name, String type) {
        currentScope.symbols.put(name, type);
    }

    public String lookup(String name) {
        Scope s = currentScope;
        while (s != null) {
            if (s.symbols.containsKey(name)) return s.symbols.get(name);
            s = s.parent;
        }
        return null;
    }
}
```


---

## LEVEL 11: MINI INTERVIEW QUESTIONS (10 Questions)

*Senior Java, JVM & OpenJDK Compiler Engineer Interview Questions with Exhaustive Reference Answers.*

### Q11.1
**Interviewer:** *How does the Java Language Specification (JLS §6.4) strictly distinguish between "shadowing", "hiding", and "obscuring"?*

---

### Q11.2
**Interviewer:** *In the JVM bytecode specification, how does the stack frame's `LocalVariableTable` attribute manage variable lifetimes and memory layout, and how does the HotSpot JIT compiler exploit this via Live-Range Analysis?*

---

### Q11.3
**Interviewer:** *Why does Java require local variables captured by lambdas or anonymous classes to be "final or effectively final", whereas languages like JavaScript or C# allow capturing mutable local variables via full lexical closures?*

---

### Q11.4
**Interviewer:** *Explain the flow scoping mechanism introduced with Pattern Matching (`instanceof` in JEP 394 and `switch` in JEP 441). How does the OpenJDK compiler determine whether a pattern variable is definitely in scope?*

---

### Q11.5
**Interviewer:** *How do ClassLoaders enforce runtime scope boundaries for `static` fields and methods? Can two classes with the exact same fully qualified binary name share a static variable?*

---

### Q11.6
**Interviewer:** *Why does Java forbid declaring a local variable in an inner block that has the same identifier as a variable in an outer block of the same method, whereas C++ and C# allow it?*

---

### Q11.7
**Interviewer:** *How does the Nest-Based Access Control model (JEP 181 / Java 11) change how the JVM resolves the scope of `private` members between outer and inner classes at the bytecode level compared to synthetic bridge methods (`access$000`)?*

---

### Q11.8
**Interviewer:** *Under JLS §16 (Definite Assignment), explain the distinction between "definitely assigned" and "definitely unassigned". Why is definite unassignment mandatory for `final` variable compilation?*

---

### Q11.9
**Interviewer:** *In Java 21 Structured Concurrency and Project Loom, what architectural problems with `ThreadLocal` lexical and lifecycle scope led to the introduction of `ScopedValue` (JEP 446)?*

---

### Q11.10
**Interviewer:** *How does member access resolution work in bytecode for fields (`getfield` / `getstatic`) versus methods (`invokevirtual` / `invokestatic`) when dealing with subclass hierarchies that re-declare matching member signatures?*

---

### LEVEL 11 MASTERCLASS INTERVIEW ANSWERS

#### Answer 11.1
- **Shadowing (JLS §6.4.1):** Occurs when a declaration in an inner scope (such as a local variable, method parameter, or inner class member) has the same name as an entity in an enclosing lexical scope. Shadowing is resolved lexically; the shadowed entity can still be accessed through qualification (e.g., `this.field`, `EnclosingClass.this.field`).
- **Hiding (JLS §8.3 / §8.4.8.2):** Occurs within inheritance hierarchies. When a subclass declares a field or static method with the same name (and for methods, identical signature) as an accessible member in its superclass, the subclass member *hides* the superclass member. Hiding is resolved statically at compile time based on the reference type, never dynamically.
- **Obscuring (JLS §6.4.2):** Occurs when an identifier could syntactically refer to a package, type, field, or variable. Java defines a rigid precedence rule: a variable declaration obscures a type declaration of the same name, and a type declaration obscures a package declaration.

#### Answer 11.2
- In class file format, a method's `Code` attribute contains `max_locals` and an optional `LocalVariableTable`.
- The table stores tuples of `(start_pc, length, name_index, descriptor_index, slot_index)`.
- Disjoint scopes allow the compiler to assign non-overlapping variables to the identical slot index (slot reuse).
- The HotSpot C2 compiler constructs a Static Single Assignment (SSA) intermediate representation, computes Def-Use chains, and performs Interference Graph Coloring. If two variables' live ranges do not overlap, they receive the same physical CPU register or frame spill offset, minimizing L1 cache pressure and frame footprint.

#### Answer 11.3
- Java thread stacks are independent from the heap. Local variables reside on the execution stack frame. If a lambda or anonymous class could capture a mutable local primitive/reference directly, when the enclosing method returns, its stack frame is popped, destroying the variable's physical memory.
- Languages like JavaScript allocate function activation records on the heap as environment records. C# transforms captured mutable local variables by generating an implicit heap-allocated display class.
- Java made an explicit design choice not to invisibly promote stack frames to heap allocations. Instead, Java *copies* the value into a synthetic field of the lambda/inner class instance. To prevent confusing semantic divergence where modifying the local variable would not reflect in the copy (or vice versa), Java mandates that captured variables must be immutable (`final` or effectively final).

#### Answer 11.4
- Pattern matching replaces traditional lexical block scoping with flow-sensitive scoping (Flow Analysis).
- The OpenJDK compiler tracks two boolean sets for every expression: `inScopeWhenTrue` and `inScopeWhenFalse`.
- For `e = (x instanceof String s)`:
  - `s` is added to `inScopeWhenTrue(e)`.
  - For `e1 && e2`: `inScopeWhenTrue(e1 && e2) = inScopeWhenTrue(e1) ∪ inScopeWhenTrue(e2)`, and `s` is in scope during evaluation of `e2`.
  - For `!e`: `inScopeWhenTrue(!e) = inScopeWhenFalse(e)`. Thus, `if (!(x instanceof String s)) return;` leaves `s` in scope for the remainder of the block, because the continuation of the method is reachable only if the condition was false.

#### Answer 11.5
- In the JVM, a class is identified by the pair `(ClassLoader, BinaryName)`.
- Static fields are stored within the `java.lang.Class` instance in Metaspace / GC heap.
- Two distinct `ClassLoader` instances (e.g., custom `URLClassLoader` or OSGi bundles) that load the exact same `.class` file create two completely distinct `Class<?>` instances.
- Consequently, static fields are strictly isolated per ClassLoader; they cannot share values, locks, or state.

#### Answer 11.6
- Java's designers (James Gosling et al.) deliberately rejected inner-block local variable shadowing to eliminate a prolific source of silent bugs common in C/C++.
- In C/C++, a developer intending to modify an outer loop accumulator can inadvertently type `int total = ...` inside an inner block, creating a distinct local variable that silently leaves the outer accumulator unmodified.
- By enforcing that a local variable's name remains reserved across all child blocks within that method, Java converts this common logic trap into an immediate compile-time diagnostic.

#### Answer 11.7
- Prior to Java 11, the JVM bytecode verifier enforced strict class-level isolation: class `A` could not directly execute `getfield` on a `private` member of class `A$Inner`. The compiler worked around this by generating synthetic package-private accessor methods (`access$000(A)`).
- JEP 181 introduced Nest-Based Access Control. The class file format now records `NestHost` and `NestMembers` attributes.
- The JVM bytecode verifier natively recognizes nestmates and permits direct `invokevirtual`, `invokestatic`, and `getfield`/`putfield` calls on `private` members across nestmates without synthetic bridge methods, reducing class size and improving JIT inlining heuristics.

#### Answer 11.8
- **Definitely Assigned (DA):** A variable is DA at a given program point if every possible control flow path leading to that point contains an assignment to that variable. Accessing a local variable requires it to be DA.
- **Definitely Unassigned (DU):** A variable is DU at a given program point if no possible control flow path leading to that point contains an assignment to that variable.
- A `final` variable must be DU before any assignment statement. If a program path could potentially execute the assignment twice (e.g., inside an unrolled loop or non-exclusive branch), DU is violated, and the compiler rejects the assignment.

#### Answer 11.9
- `ThreadLocal` introduces unbounded lifetime scope: because worker threads in modern thread pools (and virtual threads) persist indefinitely, unset `ThreadLocal` entries leak memory and cause security cross-talk between requests.
- `ThreadLocal.get()` requires hash table lookups and supports unrestricted mutability.
- `ScopedValue` (JEP 446) introduces strictly bounded, immutable lexical context: `ScopedValue.where(KEY, value).run(task)`. When the `run` method finishes, the binding is automatically destroyed. In virtual threads, `ScopedValue` bindings are inherited cheaply via shallow tree references without cloning overhead.

#### Answer 11.10
- Field access instructions (`getfield`, `putfield`, `getstatic`, `putstatic`) encode the exact symbolic reference of the declaring class determined at compile time. The JVM does not perform polymorphic lookup; it reads the offset directly associated with that class definition.
- Virtual method instructions (`invokevirtual`, `invokeinterface`) perform dynamic dispatch: the JVM dereferences the object header's `vtable` (virtual method table) pointer at runtime and invokes the most specific override for the receiver instance.
- Static methods use `invokestatic`, which, like fields, resolves strictly according to the compile-time symbolic class reference.

---

## LEVEL 12: LARGE CODING ASSIGNMENTS (5 Assignments)

---

### Assignment 12.1: ScopeVisualizer
- **Goal:** Build an application that accepts Java source files and produces a comprehensive visual timeline and ASCII map of every variable's active lexical scope.
- **Requirements:**
  1. Parse nested blocks `{ ... }`, method parameters, for-loop headers, and pattern match variables.
  2. Compute each variable's `DeclarationLine`, `FirstUseLine`, `LastUseLine`, and `ScopeExitLine`.
  3. Render an ASCII chart showing the active live range of each identifier across source line numbers.
  4. Flag any variable whose scope extends > 10 lines past its `LastUseLine` (dead scope warning).
- **Architecture & CLI Flags:**
  - Class: `com.curriculum.scope.ScopeVisualizer`
  - Flags: `--source <file.java>`, `--visualize`, `--warn-dead-scope`
- **Reference Solution Outline:**
  ```java
  public class ScopeVisualizer {
      record VariableSpan(String name, String type, int declared, int firstUse, int lastUse, int scopeEnd) {}

      public static void main(String[] args) throws Exception {
          // Scanner / Tokenizer to parse braces and declarations
          // Build interval map and print ASCII timeline
      }
  }
  ```

---

### Assignment 12.2: ShadowingDetector
- **Goal:** Build a static analysis linter using the Java Compiler API (`javax.tools`) that scans codebases for all forms of shadowing and hiding.
- **Requirements:**
  1. Detect local variables shadowing instance fields.
  2. Detect constructor/method parameters shadowing instance fields without `this.` qualification.
  3. Detect subclass fields hiding superclass fields.
  4. Detect subclass static methods hiding superclass static methods.
  5. Output reports categorized by severity: `CRITICAL` (self-assignment or unassigned fields), `WARNING` (field hiding), `INFO` (parameter shadowing with proper `this.` qualification).
- **Architecture & CLI Flags:**
  - Class: `com.curriculum.scope.ShadowingDetector`
  - Flags: `--scan <directory>`, `--severity <CRITICAL|WARNING|ALL>`, `--format <TEXT|JSON>`

---

### Assignment 12.3: AccessModifierTester
- **Goal:** Create an automated harness that verifies Java's package and access modifier rules dynamically across compilation boundaries.
- **Requirements:**
  1. Programmatically generate four classes across two separate packages (`pkg.a` and `pkg.b`).
  2. In class `Parent` in `pkg.a`, declare `public`, `protected`, package-private, and `private` members.
  3. In `Sub` (in `pkg.b`), `Neighbor` (in `pkg.a`), and `Stranger` (in `pkg.b`), attempt to generate source code accessing each member.
  4. Use `JavaCompiler` to compile all 16 combinations, capturing whether compilation succeeded or failed.
  5. Assert that the observed compiler results match the theoretical JLS access matrix with 100% fidelity.

---

### Assignment 12.4: VariableLifetimeTracker
- **Goal:** Build a runtime agent and profiler comparing the memory footprint and GC eligibility of method-scoped versus block-scoped references.
- **Requirements:**
  1. Allocate a series of large objects (e.g., 50MB buffers) in three distinct configurations:
     - Method scope without nulling.
     - Method scope with explicit nulling (`ref = null;`).
     - Narrow inner block scope `{ byte[] buf = ... }`.
  2. Execute a simulated long-running task after each allocation.
  3. Use `ManagementFactory.getMemoryMXBean()` and `WeakReference` markers to measure exactly when the memory becomes eligible for reclamation.
  4. Prove through logged timestamps that narrow block scoping allows earlier collection without explicit nulling under JIT execution.

---

### Assignment 12.5: ScopeRefactoringTool
- **Goal:** Create an automated refactoring engine that parses Java methods and automatically rewrites code to minimize variable scope.
- **Requirements:**
  1. Identify variables declared at the start of a method that are not used until later statements.
  2. Move the declaration downward to immediately precede its first usage line.
  3. Convert traditional counted loops with external variables (`int i; for (i = 0; ...)`) into self-contained loop declarations.
  4. Refactor eligible legacy `switch` statements into Java 21 switch expressions with isolated rule blocks.
  5. Write the refactored code back to disk with an accompanying diff summary.

---

### LEVEL 12 REFERENCE IMPLEMENTATIONS

#### Complete Reference Code: Assignment 12.1 (ScopeVisualizer Core Engine)
```java
package com.curriculum.scope;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class ScopeVisualizer {
    public record Span(String name, String type, int declared, int lastUse, int scopeEnd) {}

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: java ScopeVisualizer <SourceFile.java>");
            return;
        }
        Path path = Path.of(args[0]);
        List<String> lines = Files.readAllLines(path);
        List<Span> spans = analyze(lines);

        System.out.println("\n=== VARIABLE SCOPE VISUALIZATION ===");
        System.out.printf("%-15s %-10s %-8s %-8s %-8s %s\n", "Variable", "Type", "Decl", "LastUse", "EndScope", "Timeline");
        System.out.println("--------------------------------------------------------------------------------");

        for (Span s : spans) {
            StringBuilder timeline = new StringBuilder();
            for (int line = 1; line <= lines.size(); line++) {
                if (line < s.declared || line > s.scopeEnd) {
                    timeline.append(" ");
                } else if (line == s.declared) {
                    timeline.append("D");
                } else if (line == s.lastUse) {
                    timeline.append("U");
                } else if (line < s.lastUse) {
                    timeline.append("-");
                } else {
                    timeline.append("."); // Dead scope zone
                }
            }
            System.out.printf("%-15s %-10s %-8d %-8d %-8d [%s]\n", 
                s.name, s.type, s.declared, s.lastUse, s.scopeEnd, timeline);
        }
    }

    public static List<Span> analyze(List<String> lines) {
        List<Span> spans = new ArrayList<>();
        Pattern declPattern = Pattern.compile("\\b(int|long|double|float|boolean|String|[A-Z]\\w*)\\s+([a-z]\\w*)\\s*=");
        Stack<Integer> blockStack = new Stack<>();
        Map<String, Integer> currentDeclLine = new HashMap<>();
        Map<String, String> currentType = new HashMap<>();
        Map<String, Integer> lastUsage = new HashMap<>();

        for (int i = 0; i < lines.size(); i++) {
            int lineNum = i + 1;
            String line = lines.get(i);

            for (char c : line.toCharArray()) {
                if (c == '{') blockStack.push(lineNum);
                else if (c == '}' && !blockStack.isEmpty()) {
                    int blockStart = blockStack.pop();
                    // Close variables belonging to this block
                    for (String var : new ArrayList<>(currentDeclLine.keySet())) {
                        int decl = currentDeclLine.get(var);
                        if (decl >= blockStart) {
                            int last = lastUsage.getOrDefault(var, decl);
                            spans.add(new Span(var, currentType.get(var), decl, last, lineNum));
                            currentDeclLine.remove(var);
                            currentType.remove(var);
                            lastUsage.remove(var);
                        }
                    }
                }
            }

            Matcher m = declPattern.matcher(line);
            while (m.find()) {
                String type = m.group(1);
                String name = m.group(2);
                currentDeclLine.put(name, lineNum);
                currentType.put(name, type);
                lastUsage.put(name, lineNum);
            }

            for (String var : currentDeclLine.keySet()) {
                if (line.contains(var) && !line.contains(currentType.get(var) + " " + var)) {
                    lastUsage.put(var, lineNum);
                }
            }
        }
        return spans;
    }
}
```

---

## LEVEL 13: CAPSTONE PROJECT

### Java Scope Analyzer & Refactoring Tool (`ScopeCraft`)

#### Project Overview
`ScopeCraft` is a production-grade static analysis CLI and programmatic auditing tool designed to inspect Java 21 codebases, construct concrete lexical scope trees, identify scope anti-patterns, detect shadowing/hiding hazards, and emit refactored source code.

#### Architectural Requirements
1. **Scope Tree Construction:**
   - Model `RootScope`, `ClassScope`, `MethodScope`, `BlockScope`, and `PatternScope`.
   - Maintain parent-child links and symbol tables at every node.
2. **Detection Engines:**
   - **Shadowing Linter:** Identifies parameter-field shadowing, local-parameter collision attempts, and nested inner-class masking.
   - **Hiding Linter:** Identifies field hiding and static method hiding across class hierarchies.
   - **Scope-Wideness Audit:** Flags variables declared > 5 lines before first read.
   - **Try-With-Resources Audit:** Flags unmanaged or leaked stream handles.
3. **Refactoring Engine:**
   - Automatically shrinks variable live ranges by moving declarations downward.
   - Refactors qualifying legacy switch blocks into arrow switch expressions.
4. **Command-Line Interface:**
   - `--analyze <file>`: Emits complete scope hierarchy and variable catalog.
   - `--shadow-check <file>`: Emits all detected shadowing and hiding issues with line numbers.
   - `--refactor <file>`: Generates refactored Java code with minimized scopes.
   - `--tree <file>`: Prints an ASCII tree of all lexical blocks.

#### Evaluation Rubric (100 Points Total)
- **Scope Hierarchy Tree Precision (25 pts):** Correctly models blocks, loops, lambdas, and pattern variables.
- **Shadowing & Hiding Detection (25 pts):** Zero false negatives on member hiding, parameter self-assignment, and inner-class masking.
- **Scope Narrowing & Refactoring Engine (20 pts):** Valid AST or token transformation preserving semantics while minimizing live ranges.
- **Java 21 Modernization (15 pts):** Support for switch expressions, record patterns, and flow scoping.
- **Code Quality, Architecture & CLI Usability (15 pts):** Clean separation of concerns, exhaustive error handling, and intuitive CLI output.

---

### LEVEL 13 REFERENCE IMPLEMENTATION: `ScopeCraft.java`

```java
package com.curriculum.scope.capstone;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class ScopeCraft {

    public enum ScopeType { ROOT, CLASS, METHOD, BLOCK, SWITCH_ARM }

    public static class ScopeNode {
        public final ScopeType type;
        public final String name;
        public final int startLine;
        public int endLine;
        public final ScopeNode parent;
        public final List<ScopeNode> children = new ArrayList<>();
        public final Map<String, String> symbols = new HashMap<>(); // name -> type

        public ScopeNode(ScopeType type, String name, int startLine, ScopeNode parent) {
            this.type = type;
            this.name = name;
            this.startLine = startLine;
            this.parent = parent;
            if (parent != null) parent.children.add(this);
        }

        public boolean isVisible(String varName) {
            if (symbols.containsKey(varName)) return true;
            return parent != null && parent.isVisible(varName);
        }
    }

    public static class ScopeHierarchy {
        public final ScopeNode root = new ScopeNode(ScopeType.ROOT, "Global", 1, null);
        public final List<String> issues = new ArrayList<>();

        public void printTree(ScopeNode node, String indent) {
            System.out.printf("%s[%s] %s (lines %d-%d)\n", indent, node.type, node.name, node.startLine, node.endLine);
            for (Map.Entry<String, String> e : node.symbols.entrySet()) {
                System.out.printf("%s  -> %s : %s\n", indent, e.getKey(), e.getValue());
            }
            for (ScopeNode child : node.children) {
                printTree(child, indent + "    ");
            }
        }
    }

    public static ScopeHierarchy parse(List<String> lines) {
        ScopeHierarchy hierarchy = new ScopeHierarchy();
        ScopeNode current = hierarchy.root;

        Pattern classPattern = Pattern.compile("\\b(class|record|interface)\\s+([A-Z]\\w*)");
        Pattern methodPattern = Pattern.compile("\\b(public|protected|private|static|void|[A-Z]\\w*)\\s+([a-z]\\w*)\\s*\\(");
        Pattern varPattern = Pattern.compile("\\b(int|long|double|float|boolean|String|[A-Z]\\w*)\\s+([a-z]\\w*)\\s*(=|;)");

        for (int i = 0; i < lines.size(); i++) {
            int lineNum = i + 1;
            String line = lines.get(i).trim();

            if (line.startsWith("//") || line.startsWith("*")) continue;

            Matcher cm = classPattern.matcher(line);
            if (cm.find()) {
                current = new ScopeNode(ScopeType.CLASS, cm.group(2), lineNum, current);
            }

            Matcher mm = methodPattern.matcher(line);
            if (mm.find() && !line.contains("class ")) {
                current = new ScopeNode(ScopeType.METHOD, mm.group(2) + "()", lineNum, current);
            }

            Matcher vm = varPattern.matcher(line);
            while (vm.find()) {
                String type = vm.group(1);
                String name = vm.group(2);

                // Shadowing check
                if (current.parent != null && current.parent.isVisible(name)) {
                    hierarchy.issues.add(String.format("Line %d: Variable '%s' shadows an outer definition!", lineNum, name));
                }
                current.symbols.put(name, type);
            }

            if (line.contains("{")) {
                if (!cm.find() && !mm.find()) {
                    current = new ScopeNode(ScopeType.BLOCK, "Block@" + lineNum, lineNum, current);
                }
            }

            if (line.contains("}")) {
                current.endLine = lineNum;
                if (current.parent != null) {
                    current = current.parent;
                }
            }
        }
        hierarchy.root.endLine = lines.size();
        return hierarchy;
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("ScopeCraft — Java Scope Analyzer & Refactoring Tool");
            System.out.println("Usage: java ScopeCraft <--tree|--shadow-check|--analyze|--refactor> <SourceFile.java>");
            return;
        }

        String flag = args[0];
        Path path = Path.of(args[1]);
        if (!Files.exists(path)) {
            System.err.println("File not found: " + path);
            return;
        }

        List<String> lines = Files.readAllLines(path);
        ScopeHierarchy hierarchy = parse(lines);

        switch (flag) {
            case "--tree" -> {
                System.out.println("\n=== LEXICAL SCOPE TREE ===");
                hierarchy.printTree(hierarchy.root, "");
            }
            case "--shadow-check" -> {
                System.out.println("\n=== SHADOWING & HIDING REPORT ===");
                if (hierarchy.issues.isEmpty()) {
                    System.out.println("No shadowing or hiding hazards detected.");
                } else {
                    hierarchy.issues.forEach(System.out::println);
                }
            }
            case "--analyze" -> {
                System.out.println("\n=== COMPREHENSIVE SCOPE AUDIT ===");
                System.out.println("Total Source Lines: " + lines.size());
                System.out.println("Discovered Scopes: " + countNodes(hierarchy.root));
                System.out.println("Warnings Found: " + hierarchy.issues.size());
                hierarchy.issues.forEach(issue -> System.out.println("  [WARN] " + issue));
            }
            case "--refactor" -> {
                System.out.println("\n=== REFACTORED SOURCE PROPOSAL ===");
                System.out.println("// Suggested Scope Minimization:");
                for (String line : lines) {
                    // Refactor legacy switch arrows if found
                    if (line.matches(".*case\\s+\\d+:\\s*")) {
                        line = line.replace(":", " ->");
                    }
                    System.out.println(line);
                }
            }
            default -> System.err.println("Unknown flag: " + flag);
        }
    }

    private static int countNodes(ScopeNode node) {
        int count = 1;
        for (ScopeNode child : node.children) count += countNodes(child);
        return count;
    }
}
```
