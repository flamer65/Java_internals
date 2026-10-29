package module06.examples;

/**
 * Demonstrates pre and post increment/decrement behavior.
 */
public class IncrementDecrement {
    public static void main(String[] args) {
        int x = 5;
        int a = x++; // a=5, x=6
        int b = ++x; // x=7, b=7
        System.out.println("a=" + a + ", b=" + b + ", x=" + x);
        
        int c = 10;
        System.out.println(c++ + ++c); // 10 + 12 = 22
    }
}
