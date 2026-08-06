package module01.examples;

/**
 * Demonstrates having multiple classes in a single source file.
 * Only one class can be declared 'public' and its name must match the file name.
 */
public class MultipleClasses {
    public static void main(String[] args) {
        System.out.println("Inside the public MultipleClasses class.");
        
        // Instantiate the package-private helper class
        HelperClass helper = new HelperClass();
        helper.doWork();
    }
}

/**
 * A package-private helper class in the same file.
 * The compiler will generate a separate MultipleClasses.class and HelperClass.class file.
 */
class HelperClass {
    void doWork() {
        System.out.println("Inside the package-private HelperClass.");
    }
}
