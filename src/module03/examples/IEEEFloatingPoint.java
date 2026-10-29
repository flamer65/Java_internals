package module03.examples;

import java.math.BigDecimal;

/**
 * Demonstrates IEEE 754 floating-point characteristics in Java,
 * including precision issues, Infinity, and NaN (Not-a-Number).
 */
public class IEEEFloatingPoint {

    public static void main(String[] args) {
        System.out.println("--- IEEE 754 Floating-Point Demo ---");

        // 1. Precision issues (0.1 + 0.2 != 0.3)
        double a = 0.1;
        double b = 0.2;
        System.out.println("0.1 + 0.2 = " + (a + b)); 
        System.out.println("0.1 + 0.2 == 0.3: " + ((a + b) == 0.3)); // false

        // 2. Infinity
        double infinity = 1.0 / 0.0;
        double negInfinity = -1.0 / 0.0;
        System.out.println("1.0 / 0.0 = " + infinity);
        System.out.println("-1.0 / 0.0 = " + negInfinity);
        System.out.println("Double.isInfinite(infinity): " + Double.isInfinite(infinity));

        // 3. NaN (Not a Number)
        double nan = 0.0 / 0.0;
        System.out.println("0.0 / 0.0 = " + nan);
        System.out.println("NaN == NaN: " + (nan == nan)); // false! NaN never equals NaN
        System.out.println("Double.isNaN(nan): " + Double.isNaN(nan)); // true, use this instead

        // 4. Binary Representation
        float f = 3.14f;
        System.out.println("Binary of 3.14f: " + Integer.toBinaryString(Float.floatToIntBits(f)));

        // 5. BigDecimal for precise calculations (e.g., money)
        BigDecimal bd1 = new BigDecimal("0.1");
        BigDecimal bd2 = new BigDecimal("0.2");
        System.out.println("\nUsing BigDecimal: 0.1 + 0.2 = " + bd1.add(bd2));
    }
}
