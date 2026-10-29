package module06.examples;

/**
 * Demonstrates bitwise operations with binary visualization.
 */
public class BitwiseOps {
    public static void main(String[] args) {
        int a = 0b1010; // 10
        int b = 0b1100; // 12
        
        System.out.println("a:   " + toBin(a));
        System.out.println("b:   " + toBin(b));
        System.out.println("&:   " + toBin(a & b)); // 1000
        System.out.println("|:   " + toBin(a | b)); // 1110
        System.out.println("^:   " + toBin(a ^ b)); // 0110
        System.out.println("~a:  " + toBin(~a));
    }
    
    private static String toBin(int n) {
        return String.format("%32s", Integer.toBinaryString(n)).replace(' ', '0');
    }
}
