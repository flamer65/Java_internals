package module07.examples;

/**
 * Demonstrates variable shadowing and its disambiguation in Java 21 LTS.
 *
 * <p>Key concepts covered:
 * <ul>
 *   <li>Parameter shadowing: constructor/method parameters sharing the name of an instance field.</li>
 *   <li>Disambiguation using {@code this}: explicitly referencing the current instance's field.</li>
 *   <li>Local variable shadowing: local variables declared inside a method shadowing instance fields.</li>
 *   <li>Static field shadowing: local variables or parameters shadowing static fields and disambiguation using {@code ClassName.field}.</li>
 *   <li>Inner class shadowing: member variables of an inner class shadowing outer class variables, resolved via {@code OuterClass.this}.</li>
 * </ul>
 *
 * @author Java Mastery Curriculum
 * @version 21.0
 */
public class ShadowingDemo {

    // Instance fields
    private String name = "Default Instance Name";
    private int value = 100;

    // Static field
    private static int counter = 500;

    /**
     * Demonstrates constructor parameter shadowing.
     *
     * @param name  shadows this.name
     * @param value shadows this.value
     */
    public ShadowingDemo(String name, int value) {
        // Without 'this.', assigning name = name would be a no-op self-assignment of the parameter!
        this.name = name;
        this.value = value;
    }

    /**
     * Demonstrates method parameter and local variable shadowing of instance and static fields.
     *
     * @param value shadows instance field this.value
     */
    public void demonstrateShadowing(int value) {
        System.out.println("=== 1. Parameter Shadowing ===");
        System.out.println("Parameter 'value': " + value);
        System.out.println("Instance 'this.value': " + this.value);

        System.out.println("\n=== 2. Local Variable Shadowing ===");
        // Local variable 'name' shadows instance field 'this.name'
        String name = "Local Scope Name";
        System.out.println("Local 'name': " + name);
        System.out.println("Instance 'this.name': " + this.name);

        System.out.println("\n=== 3. Static Field Shadowing ===");
        // Local variable 'counter' shadows class field 'ShadowingDemo.counter'
        int counter = 999;
        System.out.println("Local 'counter': " + counter);
        System.out.println("Static 'ShadowingDemo.counter': " + ShadowingDemo.counter);
    }

    /**
     * Inner class demonstrating multi-level shadowing and lexical scoping resolution.
     */
    public class InnerScope {
        private String name = "Inner Class Name";

        public void printAllLevels(String name) {
            System.out.println("\n=== 4. Multi-Level Shadowing in Inner Class ===");
            System.out.println("Parameter 'name': " + name);
            System.out.println("Inner class 'this.name': " + this.name);
            System.out.println("Outer class 'ShadowingDemo.this.name': " + ShadowingDemo.this.name);
        }
    }

    public static void main(String[] args) {
        ShadowingDemo demo = new ShadowingDemo("Custom Instance Name", 42);
        demo.demonstrateShadowing(777);

        InnerScope inner = demo.new InnerScope();
        inner.printAllLevels("Passed Method Arg");
    }
}
