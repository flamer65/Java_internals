package module02.examples;

/**
 * Shows default values for instance/static fields vs no defaults for locals.
 */
public class DefaultValues {
    
    // Instance variables get default values
    int instanceInt;          // 0
    boolean instanceBoolean;  // false
    double instanceDouble;    // 0.0
    String instanceString;    // null
    
    public void displayDefaults() {
        System.out.println("--- Instance Variables ---");
        System.out.println("int: " + instanceInt);
        System.out.println("boolean: " + instanceBoolean);
        System.out.println("double: " + instanceDouble);
        System.out.println("String: " + instanceString);
        
        // Local variables DO NOT get default values
        int localInt;
        
        // System.out.println(localInt); // COMPILER ERROR: variable localInt might not have been initialized
        
        // Definite assignment requirement
        localInt = 42; // Must explicitly initialize
        System.out.println("--- Local Variables ---");
        System.out.println("localInt initialized: " + localInt);
    }

    public static void main(String[] args) {
        new DefaultValues().displayDefaults();
    }
}
