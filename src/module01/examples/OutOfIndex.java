package module01.examples;

public class OutOfIndex {
    public static void main(String[] args) {
        try {
            System.out.println(args[24]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid index: " + e.getMessage());
        }
    }
}
