package module05.examples;

/**
 * Demonstrates autoboxing rules, widening + boxing compatibility,
 * and autoboxing performance gotchas.
 */
public class AutoboxingConversion {
    public static void main(String[] args) {
        // 1. Valid Autoboxing + Widening chain
        int primitiveInt = 100;
        Integer wrapperInt = primitiveInt; // Autoboxing
        Number numberRef = wrapperInt;     // Widening Reference
        Object objRef = numberRef;         // Widening Reference
        
        System.out.println("Boxed and widened object: " + objRef);
        
        // 2. Widening vs Boxing precedence limitations
        // Long l1 = 10; // COMPILER ERROR: Can't widen int to long, then box to Long.
        Long l2 = 10L;   // Correct!
        
        // 3. NullPointerException Trap
        Integer nullableWrapper = null;
        try {
            // Unboxing null throws NPE
            int trap = nullableWrapper; 
        } catch (NullPointerException e) {
            System.out.println("\nCaught NPE from unboxing a null wrapper!");
        }
        
        // 4. Identity vs Equality
        Integer a = 128;
        Integer b = 128;
        System.out.println("\n128 == 128 (identity): " + (a == b)); // False (outside cache)
        
        Long c = 128L;
        System.out.println("128 equals 128L: " + a.equals(c)); // False (different classes)
        
        // 5. Performance penalty visualization (Conceptual)
        long startTime = System.nanoTime();
        Long sum = 0L;
        for (int i = 0; i < 10_000; i++) {
            sum += i; // Unboxes sum, adds i, boxes back to sum
        }
        long endTime = System.nanoTime();
        System.out.println("\nTime taken with wrapper (bad): " + (endTime - startTime) + " ns");
    }
}
