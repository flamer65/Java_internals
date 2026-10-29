package module05.examples;

/**
 * Visualizes narrowing at the binary level to deeply understand
 * how the JVM truncates data.
 */
public class BitLevelNarrowing {
    public static void main(String[] args) {
        // Narrowing int to byte
        int i = 0x1FF; // 511
        byte b = (byte) i; // Truncates to bottom 8 bits
        
        System.out.println("--- Binary Truncation ---");
        System.out.println("Original int (511): " + to32BitString(i));
        System.out.println("Narrowed byte     : " + to8BitString(b));
        System.out.println("Byte value        : " + b); // -1 (1111 1111 in 2's complement)
        
        // char from -1
        char c = (char) -1;
        System.out.println("\n--- Char underflow ---");
        System.out.println("char value cast from -1 as int: " + (int)c); // 65535
        
        // Negative Zero
        System.out.println("\n--- Zero representations ---");
        System.out.println("(int) -0.0 == (int) 0.0: " + ((int) -0.0 == (int) 0.0));
    }
    
    private static String to32BitString(int i) {
        return String.format("%32s", Integer.toBinaryString(i)).replace(' ', '0');
    }
    
    private static String to8BitString(byte b) {
        String binary = Integer.toBinaryString(b & 0xFF);
        return String.format("%8s", binary).replace(' ', '0');
    }
}
