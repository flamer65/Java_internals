package module07.examples;

/**
 * Demonstrates class-level scoping: instance fields, static (class) fields,
 * default initialization, access rules from static vs instance contexts,
 * and access modifier visibility in Java 21 LTS.
 *
 * <p>Key concepts covered:
 * <ul>
 *   <li>Instance fields: bound to heap object instances; accessible from any instance method via {@code this}.</li>
 *   <li>Static fields: bound to class metadata; accessible from both static and instance contexts.</li>
 *   <li>Default initialization: fields (instance and static) automatically receive JVM zero-values (0, 0.0, false, null).</li>
 *   <li>Static context restrictions: static methods have no implicit {@code this} and cannot directly access instance fields.</li>
 *   <li>Visibility scopes: {@code private}, package-private (default), {@code protected}, and {@code public}.</li>
 * </ul>
 *
 * @author Java Mastery Curriculum
 * @version 21.0
 */
public class ClassScope {

    // === Class (Static) Fields ===
    public static final String APPLICATION_NAME = "JavaMastery";
    private static int activeInstanceCount = 0;

    // === Instance Fields (Default-initialized by JVM during object allocation) ===
    private int id;                // Defaults to 0
    private double balance;        // Defaults to 0.0
    private boolean verified;      // Defaults to false
    private String username;       // Defaults to null (reference)

    // Access modifier demonstration fields
    public String publicTag = "PUBLIC_TAG";
    protected String protectedRegion = "PROTECTED_REGION";
    String packagePrivateNote = "PACKAGE_NOTE";
    private String secretKey = "SECRET_123";

    /**
     * Constructor initializing the instance.
     *
     * @param id       identifier
     * @param username user name
     */
    public ClassScope(int id, String username) {
        this.id = id;
        this.username = username;
        activeInstanceCount++;
    }

    /**
     * Default constructor leaving instance fields to their JVM default values.
     */
    public ClassScope() {
        activeInstanceCount++;
        // id, balance, verified, username remain their zero/null defaults
    }

    /**
     * Instance method: can access both instance fields and static fields directly.
     */
    public void printInstanceDetails() {
        System.out.println("Instance method accessing instance fields:");
        System.out.println(" - id: " + this.id);
        System.out.println(" - username: " + this.username);
        System.out.println(" - balance: " + this.balance);
        System.out.println(" - verified: " + this.verified);

        System.out.println("Instance method accessing static fields directly:");
        System.out.println(" - APPLICATION_NAME: " + APPLICATION_NAME);
        System.out.println(" - activeInstanceCount: " + activeInstanceCount);
    }

    /**
     * Static method: class-level scope.
     * Can access static fields directly, but CANNOT access instance fields directly.
     */
    public static void printStaticSummary() {
        System.out.println("Static method accessing static fields:");
        System.out.println(" - App Name: " + APPLICATION_NAME);
        System.out.println(" - Active Instances: " + activeInstanceCount);

        // The following lines would cause compile-time errors:
        // System.out.println(id); // COMPILE ERROR: non-static variable id cannot be referenced from a static context
        // System.out.println(this.username); // COMPILE ERROR: non-static variable this cannot be referenced from a static context
    }

    /**
     * Static method accessing instance fields VIA an explicit object reference.
     *
     * @param target the target instance
     */
    public static void inspectViaReference(ClassScope target) {
        System.out.println("Static method accessing instance field via explicit reference:");
        // Legal because 'target' reference provides the heap pointer
        System.out.println(" - target.id: " + target.id);
        System.out.println(" - target.secretKey (private in same class): " + target.secretKey);
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Default Initialization Proof ===");
        ClassScope uninit = new ClassScope();
        uninit.printInstanceDetails();

        System.out.println("\n=== 2. Configured Instance Details ===");
        ClassScope user1 = new ClassScope(101, "Alice");
        user1.balance = 540.75;
        user1.verified = true;
        user1.printInstanceDetails();

        System.out.println("\n=== 3. Static Context Execution ===");
        ClassScope.printStaticSummary();

        System.out.println("\n=== 4. Static Access via Object Pointer ===");
        ClassScope.inspectViaReference(user1);
    }
}
