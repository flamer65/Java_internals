package module04.examples;

/**
 * Demonstrates floating point literals, including scientific and hex notation.
 */
public class FloatingPointLiterals {
    public static void main(String[] args) {
        float f = 3.14f; // 'f' suffix required
        double d = 3.14; // Default double

        double sci1 = 1.5e3;
        double sci2 = 2.5E-4;
        double sci3 = 1e10;

        // Hex floating point: mantissa in hex, exponent in base-2
        double hexFloat = 0x1.0p3; // 1.0 * 2^3 = 8.0

        System.out.println("Float: " + f);
        System.out.println("Sci1 (1.5e3): " + sci1);
        System.out.println("Sci2 (2.5E-4): " + sci2);
        System.out.println("Hex Float (0x1.0p3): " + hexFloat);
    }
}
