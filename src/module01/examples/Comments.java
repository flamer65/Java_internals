package module01.examples;

/**
 * Demonstrates Java comments.
 */
public class Comments {
    /**
     * @param args  accepts multiple arguments
     */
     public static void main(String[] args){
        System.out.println("Hello World");
        // Hello world comment
        /*
        * Hi there this mulitline comment
         */
         System.out.println("Java Version Property: " + System.getProperty("java.version"));
         System.out.println("Java Vendor: " + System.getProperty("java.vendor"));
    }
}
