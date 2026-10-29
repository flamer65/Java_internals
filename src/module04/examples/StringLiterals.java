package module04.examples;

/**
 * Demonstrates the string pool and literal rules.
 */
public class StringLiterals {
    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = "hello";
        String s3 = new String("hello");

        System.out.println("Literal == Literal: " + (s1 == s2)); // true
        System.out.println("Literal == new String(): " + (s1 == s3)); // false
        System.out.println("Literal == interned: " + (s1 == s3.intern())); // true
    }
}
