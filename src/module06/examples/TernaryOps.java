package module06.examples;

/**
 * Demonstrates ternary operators, nested ternaries, and type promotion.
 */
public class TernaryOps {
    public static void main(String[] args) {
        // Basic
        int max = (10 > 5) ? 10 : 5;
        System.out.println(max);
        
        // Type promotion
        Object obj = true ? 1 : 1.0;
        System.out.println(obj.getClass().getName()); // Double
        
        // Null danger
        Integer i = true ? null : 1; // ok
        // int x = true ? null : 1; // NPE on unboxing!
    }
}
