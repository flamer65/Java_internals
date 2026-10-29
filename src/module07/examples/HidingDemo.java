package module07.examples;

/**
 * Demonstrates field hiding, static method hiding, and contrasts them with
 * instance method overriding (polymorphism) in Java 21 LTS.
 *
 * <p>Key JVM and Compiler concepts covered:
 * <ul>
 *   <li>Field hiding: subclass fields with the same name as superclass fields do NOT override them.
 *       Fields are resolved statically at compile time based on the <i>reference type</i> (via {@code getfield}).</li>
 *   <li>Static method hiding: static methods with identical signatures in super and subclass are hidden,
 *       not overridden. They are invoked via {@code invokestatic} determined by the declared reference type.</li>
 *   <li>Polymorphic dispatch: instance methods use dynamic method dispatch (vtable lookup via {@code invokevirtual}),
 *       resolving according to the actual runtime object class.</li>
 * </ul>
 *
 * @author Java Mastery Curriculum
 * @version 21.0
 */
public class HidingDemo {

    /**
     * Superclass representing a generic compute node.
     */
    public static class SuperNode {
        // Superclass field
        public String role = "SUPER_NODE";
        public int capacity = 100;

        // Static method (subject to hiding)
        public static void identify() {
            System.out.println("SuperNode.identify() [Static Method]");
        }

        // Instance method (subject to polymorphic overriding)
        public void process() {
            System.out.println("SuperNode.process() [Instance Method] - Role: " + this.role);
        }
    }

    /**
     * Subclass hiding superclass fields and static method, but overriding instance method.
     */
    public static class SubNode extends SuperNode {
        // Field hiding: hides SuperNode.role and SuperNode.capacity
        public String role = "SUB_NODE";
        public int capacity = 500;

        // Static method hiding: hides SuperNode.identify()
        public static void identify() {
            System.out.println("SubNode.identify() [Static Method Hidden]");
        }

        // Instance method overriding: polymorphic!
        @Override
        public void process() {
            System.out.println("SubNode.process() [Overridden Method] - Sub role: " + this.role
                    + ", Super role via super.role: " + super.role);
        }

        public void printDirectAccess() {
            System.out.println("SubNode internal access: this.capacity = " + this.capacity
                    + ", super.capacity = " + super.capacity);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Direct SubNode Reference ===");
        SubNode sub = new SubNode();
        System.out.println("sub.role: " + sub.role);          // "SUB_NODE"
        System.out.println("sub.capacity: " + sub.capacity);  // 500
        sub.printDirectAccess();
        SubNode.identify();
        sub.process();

        System.out.println("\n=== 2. Polymorphic Reference (SuperNode ref = new SubNode()) ===");
        SuperNode polymorphicRef = new SubNode();

        // FIELD ACCESS IS NOT POLYMORPHIC!
        // javac resolves field access based on the declared type (SuperNode) -> emits getfield for SuperNode.role
        System.out.println("polymorphicRef.role (Declared as SuperNode): " + polymorphicRef.role); // "SUPER_NODE"
        System.out.println("polymorphicRef.capacity (Declared as SuperNode): " + polymorphicRef.capacity); // 100

        // STATIC METHOD CALL IS NOT POLYMORPHIC!
        // javac binds static call to SuperNode.identify() at compile time
        polymorphicRef.identify(); // Calls SuperNode.identify()

        // INSTANCE METHOD CALL IS POLYMORPHIC!
        // JVM uses invokevirtual to dynamically dispatch to SubNode.process()
        polymorphicRef.process(); // Calls SubNode.process()

        System.out.println("\n=== 3. Resolving Hidden Field via Explicit Upcast ===");
        System.out.println("Casting sub to SuperNode: " + ((SuperNode) sub).role); // "SUPER_NODE"
    }
}
