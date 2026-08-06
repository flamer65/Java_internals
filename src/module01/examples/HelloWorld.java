package module01.examples;

/**
 * A simple classic HelloWorld program to demonstrate the absolute basics of Java.
 * This class prints a greeting message to the standard output.
 */
public class HelloWorld {

    /**
     * The main method, which is the entry point for the JVM.
     * @param args command-line arguments passed to the program
     */
    public static void main(String[] args) {
        // Print "Hello, World!" followed by a newline to standard output
        System.out.println("Hello "+ args[0] + " age" + args[1]);
    }
}
