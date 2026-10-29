package module03.examples;

/**
 * Demonstrates all 8 primitive data types in Java.
 * Covers declaration, ranges, sizes, and default values.
 */
public class PrimitiveTypesDemo {

    // Default values for primitive instance fields
    private byte defaultByte;
    private short defaultShort;
    private int defaultInt;
    private long defaultLong;
    private float defaultFloat;
    private double defaultDouble;
    private char defaultChar;
    private boolean defaultBoolean;

    public static void main(String[] args) {
        System.out.println("--- Primitive Types Demo ---");
        displayRangesAndSizes();
        
        PrimitiveTypesDemo demo = new PrimitiveTypesDemo();
        demo.displayDefaultValues();
    }

    public static void displayRangesAndSizes() {
        System.out.println("1. byte: " + Byte.BYTES + " bytes (" + Byte.SIZE + " bits). Range: " + Byte.MIN_VALUE + " to " + Byte.MAX_VALUE);
        System.out.println("2. short: " + Short.BYTES + " bytes (" + Short.SIZE + " bits). Range: " + Short.MIN_VALUE + " to " + Short.MAX_VALUE);
        System.out.println("3. int: " + Integer.BYTES + " bytes (" + Integer.SIZE + " bits). Range: " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE);
        System.out.println("4. long: " + Long.BYTES + " bytes (" + Long.SIZE + " bits). Range: " + Long.MIN_VALUE + " to " + Long.MAX_VALUE);
        System.out.println("5. float: " + Float.BYTES + " bytes (" + Float.SIZE + " bits). Range: " + Float.MIN_VALUE + " to " + Float.MAX_VALUE);
        System.out.println("6. double: " + Double.BYTES + " bytes (" + Double.SIZE + " bits). Range: " + Double.MIN_VALUE + " to " + Double.MAX_VALUE);
        System.out.println("7. char: " + Character.BYTES + " bytes (" + Character.SIZE + " bits). Range: " + (int)Character.MIN_VALUE + " to " + (int)Character.MAX_VALUE);
        System.out.println("8. boolean: JVM dependent (usually 1 byte). Range: true or false");
    }

    public void displayDefaultValues() {
        System.out.println("\n--- Default Values for Instance Fields ---");
        System.out.println("byte: " + defaultByte);
        System.out.println("short: " + defaultShort);
        System.out.println("int: " + defaultInt);
        System.out.println("long: " + defaultLong);
        System.out.println("float: " + defaultFloat);
        System.out.println("double: " + defaultDouble);
        System.out.println("char: '" + defaultChar + "' (int value: " + (int)defaultChar + ")");
        System.out.println("boolean: " + defaultBoolean);
    }
}
