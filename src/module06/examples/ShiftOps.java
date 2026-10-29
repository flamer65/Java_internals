package module06.examples;

/**
 * Demonstrates left shift, signed/unsigned right shift, and modulo behavior.
 */
public class ShiftOps {
    public static void main(String[] args) {
        int val = -8;
        System.out.println("val:       " + Integer.toBinaryString(val));
        System.out.println("val << 1:  " + Integer.toBinaryString(val << 1) + " = " + (val << 1));
        System.out.println("val >> 1:  " + Integer.toBinaryString(val >> 1) + " = " + (val >> 1));
        System.out.println("val >>> 1: 0" + Integer.toBinaryString(val >>> 1) + " = " + (val >>> 1));
        
        // Shift modulo behavior
        System.out.println("1 << 32 == " + (1 << 32)); // 1
    }
}
