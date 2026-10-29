package module05.examples;

/**
 * Demonstrates reference upcasting, downcasting, and ClassCastException.
 */
public class ReferenceCasting {
    public static void main(String[] args) {
        // 1. Upcasting (Implicit Widening)
        String text = "Hello World";
        Object obj = text; // Safe, implicit
        System.out.println("Upcasted: " + obj);
        
        // 2. Downcasting (Explicit Narrowing)
        if (obj instanceof String) {
            String downcasted = (String) obj;
            System.out.println("Downcasted length: " + downcasted.length());
        }
        
        // 3. ClassCastException Scenario
        Object numberObj = 42; // Autoboxed Integer
        
        try {
            System.out.println("\nAttempting illegal downcast...");
            String invalidCast = (String) numberObj;
        } catch (ClassCastException e) {
            System.out.println("Caught ClassCastException!");
            // In Java 17+, this message provides detailed "cannot cast X to Y" insight
            System.out.println("Message: " + e.getMessage());
        }
        
        // 4. Array covariance edge case
        Object[] objectArray = new String[5]; // Allowed by array covariance
        try {
            objectArray[0] = 42; // Throws ArrayStoreException
        } catch (ArrayStoreException e) {
            System.out.println("\nCaught ArrayStoreException during covariant insertion.");
        }
    }
}
