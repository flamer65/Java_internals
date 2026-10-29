package module03.examples;

/**
 * Demonstrates the Integer Cache behavior in Java.
 * The JVM caches Integer instances for values between -128 and 127 by default.
 * Run with -XX:AutoBoxCacheMax=1000 to extend the cache limit.
 */
public class IntegerCacheDemo {

    public static void main(String[] args) {
        System.out.println("--- Integer Cache Demo ---");

        // Values within the default cache range (-128 to 127)
        Integer a = 127;
        Integer b = 127;

        System.out.println("a (127) == b (127): " + (a == b)); // true, both reference the same cached object

        // Values outside the default cache range
        Integer x = 128;
        Integer y = 128;

        System.out.println("x (128) == y (128): " + (x == y)); // false, different objects are created
        System.out.println("x.equals(y): " + x.equals(y));     // true, logical equality is met

        // Proof that valueOf uses the cache
        System.out.println("Integer.valueOf(127) == Integer.valueOf(127): " + (Integer.valueOf(127) == Integer.valueOf(127)));
        System.out.println("Integer.valueOf(128) == Integer.valueOf(128): " + (Integer.valueOf(128) == Integer.valueOf(128)));
    }
}
