package module01.examples;

/**
 * Demonstrates the execution order of static initialization blocks.
 * Static blocks are executed when the class is loaded by the ClassLoader,
 * before any instance is created and before the main method runs.
 */
public class StaticInitDemo {

    // First static block
    static {
        System.out.println("1. First static block executed.");
    }
    
    // A static variable
    static int value = initializeValue();
    
    // Method used to initialize the static variable
    private static int initializeValue() {
        System.out.println("2. Static variable 'value' initialized.");
        return 42;
    }
    
    // Second static block
    static {
        System.out.println("3. Second static block executed. Value is: " + value);
    }

    /**
     * Main method. Notice this prints AFTER all static blocks.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        System.out.println("4. Main method executed.");
    }
}
