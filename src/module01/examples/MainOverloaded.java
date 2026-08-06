package module01.examples;

/**
 * Demonstrates that the main method can be overloaded just like any other method.
 * However, the JVM only calls the exact signature: public static void main(String[] args).
 */
public class MainOverloaded {

    /**
     * The standard entry point. This is what the JVM calls.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        System.out.println("1. Standard main(String[] args) called by the JVM.");

        // Manually calling the overloaded main methods
        main("Hello");
        main(42);
    }

    /**
     * An overloaded main method taking a String.
     * The JVM will NOT call this automatically.
     *
     * @param message A string argument.
     */
    public static void main(String message) {
        System.out.println("2. Overloaded main(String message) called. Message: " + message);
    }

    /**
     * Another overloaded main method taking an int.
     *
     * @param number An int argument.
     */
    public static void main(int number) {
        System.out.println("3. Overloaded main(int number) called. Number: " + number);
    }
}
