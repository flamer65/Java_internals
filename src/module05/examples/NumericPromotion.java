package module05.examples;

/**
 * Demonstrates Binary Numeric Promotion in Java expressions.
 */
public class NumericPromotion {
    public static void main(String[] args) {
        // 1. Basic Promotion
        byte b1 = 10;
        byte b2 = 20;
        
        // The following line would cause a compiler error:
        // byte sum = b1 + b2; 
        int sum = b1 + b2; // Promoted to int
        System.out.println("byte + byte promoted to int: " + sum);
        
        // 2. Mixed type promotion
        int i = 50;
        long l = 100L;
        double d = 2.5;
        
        // Entire expression promoted to double, the widest type
        double mixedResult = i * l + d;
        System.out.println("\nMixed promotion result (int*long+double): " + mixedResult);
        
        // 3. Compound Assignment implicit casting
        short s = 5;
        // s = s + 5; // Compiler error
        s += 5; // Works! Equivalent to: s = (short)(s + 5)
        System.out.println("\nCompound assignment: " + s);
        
        // 4. Constant Expression Narrowing
        // The compiler implicitly casts if the literal is a constant and fits
        byte b3 = 100; // Works
        final int constantInt = 50;
        byte b4 = constantInt; // Works because constantInt is 'final' and fits
        
        System.out.println("\nConstant expression assignments work perfectly.");
        
        // 5. Ternary Operator Promotion
        Object ternaryResult = true ? 1 : 1.0; 
        System.out.println("\nTernary Promotion (true ? 1 : 1.0): " + ternaryResult.getClass().getName()); // Double
    }
}
