package module06.examples;

/**
 * Demonstrates comparison operators, wrapper comparisons, and edge cases.
 */
public class ComparisonOps {
    public static void main(String[] args) {
        // Primitive comparison
        System.out.println(5 == 5); // true
        System.out.println(5 != 10); // true
        
        // Wrapper comparison
        Integer i1 = 127;
        Integer i2 = 127;
        System.out.println("127 == 127: " + (i1 == i2)); // true (cached)
        
        Integer i3 = 128;
        Integer i4 = 128;
        System.out.println("128 == 128: " + (i3 == i4)); // false
        
        // Float edge cases
        System.out.println("NaN == NaN: " + (Double.NaN == Double.NaN)); // false
        System.out.println("0.0 == -0.0: " + (0.0 == -0.0)); // true
    }
}
