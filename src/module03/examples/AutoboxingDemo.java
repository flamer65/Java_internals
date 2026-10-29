package module03.examples;

/**
 * Demonstrates autoboxing and unboxing in Java,
 * including potential pitfalls like NullPointerException and performance issues.
 */
public class AutoboxingDemo {

    public static void main(String[] args) {
        System.out.println("--- Autoboxing and Unboxing Demo ---");

        // 1. Autoboxing (Primitive -> Wrapper)
        Integer boxed = 42; // Compiler translates to: Integer.valueOf(42)
        
        // 2. Unboxing (Wrapper -> Primitive)
        int unboxed = boxed; // Compiler translates to: boxed.intValue()
        
        // 3. Expressions with Autoboxing/Unboxing
        Integer a = 10;
        Integer b = 20;
        // Compiler unboxes a and b, adds them, then boxes the result
        // Integer c = Integer.valueOf(a.intValue() + b.intValue());
        Integer c = a + b; 
        System.out.println("a + b = " + c);

        // 4. Unboxing NPE Trap
        try {
            Integer nullInt = null;
            int dangerousUnbox = nullInt; // Throws NPE because it calls nullInt.intValue()
        } catch (NullPointerException e) {
            System.out.println("Caught NPE due to unboxing null!");
        }

        // 5. Performance Trap
        measurePerformanceTrap();
    }

    private static void measurePerformanceTrap() {
        System.out.println("\nMeasuring Performance Trap (Wrapper vs Primitive loop sum):");
        
        long start = System.nanoTime();
        Long sumWrapper = 0L;
        for (long i = 0; i < 1_000_000; i++) {
            sumWrapper += i; // Unboxes sumWrapper, adds i, boxes result (creates 1M objects)
        }
        long end = System.nanoTime();
        System.out.println("Using Long wrapper took: " + (end - start) / 1_000_000.0 + " ms");

        start = System.nanoTime();
        long sumPrimitive = 0L;
        for (long i = 0; i < 1_000_000; i++) {
            sumPrimitive += i; // Simple primitive addition
        }
        end = System.nanoTime();
        System.out.println("Using long primitive took: " + (end - start) / 1_000_000.0 + " ms");
    }
}
