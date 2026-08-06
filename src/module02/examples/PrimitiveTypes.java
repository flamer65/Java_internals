package module02.examples;

/**
 * Demonstrates all 8 primitive types in Java.
 * Shows declaration, initialization, printing, and memory size info.
 */
public class PrimitiveTypes {
    public static void main(String[] args) {
        // 1. byte (1 byte, 8 bits)
        // Range: -128 to 127
        byte byteVar = 100;
        System.out.println("byte value: " + byteVar);
        
        // 2. short (2 bytes, 16 bits)
        // Range: -32,768 to 32,767
        short shortVar = 30000;
        System.out.println("short value: " + shortVar);
        
        // 3. int (4 bytes, 32 bits)
        // Default choice for integers
        int intVar = 2000000000;
        System.out.println("int value: " + intVar);
        
        // 4. long (8 bytes, 64 bits)
        // Requires 'L' or 'l' suffix if outside int range
        long longVar = 9000000000000000000L;
        System.out.println("long value: " + longVar);
        
        // 5. float (4 bytes, 32-bit IEEE 754)
        // Requires 'f' or 'F' suffix
        float floatVar = 3.14159f;
        System.out.println("float value: " + floatVar);
        
        // 6. double (8 bytes, 64-bit IEEE 754)
        // Default choice for floating point
        double doubleVar = 2.718281828459;
        System.out.println("double value: " + doubleVar);
        
        // 7. char (2 bytes, 16-bit unsigned Unicode)
        // Range: 0 to 65,535
        char charVar = 'A';
        char unicodeChar = '\u0041'; // Hex representation of 'A'
        System.out.println("char value: " + charVar + " (Unicode: " + unicodeChar + ")");
        
        // 8. boolean (Size JVM-dependent, conceptually 1 bit)
        // Only true or false
        boolean booleanVar = true;
        System.out.println("boolean value: " + booleanVar);
    }
}
