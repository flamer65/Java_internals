package module01.examples;

/**
 * Demonstrates how to access and print information about the JVM
 * and the environment it runs in using Runtime and System properties.
 */
public class JVMInfo {

    /**
     * Entry point for the JVM.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        System.out.println("=== JVM Information ===");

        // Print Java version using Runtime.version() (Java 9+)
        System.out.println("Java Version (Runtime): " + Runtime.version());

        System.out.println("\n=== System Properties ===");
        // Print standard System properties
        System.out.println("Java Version Property: " + System.getProperty("java.version"));
        System.out.println("Java Vendor: " + System.getProperty("java.vendor"));
        System.out.println("OS Name: " + System.getProperty("os.name"));
        System.out.println("OS Architecture: " + System.getProperty("os.arch"));
        System.out.println("User Directory: " + System.getProperty("user.dir"));

        System.out.println("\n=== Memory Limits ===");
        // Print memory limits from Runtime
        Runtime runtime = Runtime.getRuntime();
        System.out.println("Max Memory (bytes): " + runtime.maxMemory());
        System.out.println("Total Memory (bytes): " + runtime.totalMemory());
        System.out.println("Free Memory (bytes): " + runtime.freeMemory());
    }
}
