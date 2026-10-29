package module04.examples;

/**
 * Demonstrates valid uses of underscores in numeric literals.
 */
public class UnderscoreLiterals {
    public static void main(String[] args) {
        int million = 1_000_000;
        int hex = 0xFF_EC;
        int bin = 0b0010_0101;
        long multi = 1___000___000L;

        System.out.println("Million: " + million);
        System.out.println("Hex: " + hex);
        System.out.println("Multi: " + multi);
    }
}
