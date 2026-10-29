package module04.examples;

/**
 * Demonstrates boolean and null literal behavior.
 */
public class BooleanAndNull {
    public static void main(String[] args) {
        boolean t = true;
        boolean f = false;

        String s = null;
        Object obj = null;

        System.out.println("Boolean: " + t);
        System.out.println("String concat with null: " + ("Value: " + s));
        
        try {
            s.length(); // Throws NPE
        } catch (NullPointerException e) {
            System.out.println("NPE caught as expected.");
        }
    }
}
