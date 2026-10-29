package module03.examples;

/**
 * Demonstrates the memory overhead of using Wrapper arrays vs Primitive arrays.
 * Note: Results vary based on JVM and GC timing, but the overhead is always significant.
 */
public class MemoryComparisonDemo {

    public static void main(String[] args) {
        System.out.println("--- Memory Comparison Demo ---");
        
        // Run garbage collection to start clean
        System.gc();
        long memBefore = getUsedMemory();
        
        // Allocate primitive array
        int size = 1_000_000;
        int[] primitiveArray = new int[size];
        
        long memAfterPrimitive = getUsedMemory();
        long primitiveUsed = memAfterPrimitive - memBefore;
        
        // Cleanup
        primitiveArray = null;
        System.gc();
        
        memBefore = getUsedMemory();
        
        // Allocate wrapper array
        Integer[] wrapperArray = new Integer[size];
        for (int i = 0; i < size; i++) {
            wrapperArray[i] = i; // Creates many objects (above cache limit)
        }
        
        long memAfterWrapper = getUsedMemory();
        long wrapperUsed = memAfterWrapper - memBefore;
        
        System.out.println("Memory used by int[" + size + "]: ~" + (primitiveUsed / 1024 / 1024) + " MB");
        System.out.println("Memory used by Integer[" + size + "]: ~" + (wrapperUsed / 1024 / 1024) + " MB");
        System.out.println("Overhead factor: " + ((double)wrapperUsed / primitiveUsed) + "x");
    }

    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
