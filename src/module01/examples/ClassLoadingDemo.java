package module01.examples;

/**
 * Demonstrates lazy class loading and deliberate triggering using Class.forName().
 */
public class ClassLoadingDemo {

    public static void main(String[] args) {
        System.out.println("Starting main method. TargetClass is NOT loaded yet.");
        
        try {
            // Sleep to simulate some work before class loading
            Thread.sleep(1000);
            
            System.out.println("Deliberately loading TargetClass now...");
            // Use Class.forName to trigger class loading (and initialization)
            Class.forName("module01.examples.TargetClass");
            
            System.out.println("TargetClass has been loaded.");
            
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Class not found - " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * Target class to be loaded dynamically.
 * Its static block will run exactly when the class is initialized.
 */
class TargetClass {
    static {
        System.out.println(">>> TargetClass STATIC BLOCK executed! TargetClass is now loaded and initialized.");
    }
}
