package module01.examples;

/**
 * Demonstrates how to use command-line arguments in Java.
 * The JVM passes arguments provided after the class name as an array of Strings.
 */
public class CommandLineArgs {

    /**
     * Entry point for the program.
     * @param args Array of strings containing the command-line arguments.
     */
    public static void main(String[] args) {
        System.out.println("Demonstrating Command-Line Arguments");
        
        // Check if any arguments were provided
        if (args.length == 0) {
            System.out.println("No arguments were provided. Try running: java module01.examples.CommandLineArgs arg1 arg2");
        } else {
            System.out.println("Number of arguments provided: " + args.length);
            
            // Loop through the arguments and print each one with its index
            for (int i = 0; i < args.length; i++) {
                System.out.println("Argument " + i + ": " + args[i]);
            }
        }
    }
}
