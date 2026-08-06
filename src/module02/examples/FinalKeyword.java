package module02.examples;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates final primitives, final references, blank finals, compile-time constants.
 */
public class FinalKeyword {
    
    // Compile-time constant (inlined by javac)
    public static final String APP_VERSION = "2.0";
    
    public static void main(String[] args) {
        // 1. Final Primitives
        final int maxSpeed = 120;
        // maxSpeed = 130; // COMPILER ERROR: cannot assign a value to final variable maxSpeed
        System.out.println("Final primitive: " + maxSpeed);
        
        // 2. Final References
        // The reference is final (cannot point to a new List), but the list contents can mutate.
        final List<String> list = new ArrayList<>();
        list.add("Java"); // Valid: Mutating the object on the heap
        list.add("Kotlin");
        System.out.println("Final reference contents: " + list);
        // list = new ArrayList<>(); // COMPILER ERROR: cannot assign a value to final variable list
        
        // 3. Blank Finals
        // A final variable that is not initialized at declaration.
        final int dynamicFinal;
        boolean condition = System.currentTimeMillis() % 2 == 0;
        
        if (condition) {
            dynamicFinal = 1;
        } else {
            dynamicFinal = 0;
        }
        // Compiler guarantees dynamicFinal is initialized exactly once before use.
        System.out.println("Blank final value: " + dynamicFinal);
    }
}
