package module01.examples;

/**
 * Demonstrates the Just-In-Time (JIT) compiler warmup.
 * Shows how method execution time decreases as the JVM optimizes hot code.
 */
public class JITWarmupDemo {

    public static void main(String[] args) {
        System.out.println("Demonstrating JIT Compiler Warmup");
        
        // Run the method enough times to trigger JIT compilation (usually 10,000 times for C2 compiler)
        int iterations = 20_000;
        
        long totalInitialTime = 0;
        long totalFinalTime = 0;
        
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();
            performComputation();
            long endTime = System.nanoTime();
            
            long duration = endTime - startTime;
            
            if (i < 10) {
                totalInitialTime += duration;
                if (i == 9) System.out.println("Avg time for first 10 runs (interpreted): " + (totalInitialTime / 10) + " ns");
            }
            
            if (i >= iterations - 10) {
                totalFinalTime += duration;
                if (i == iterations - 1) System.out.println("Avg time for last 10 runs (compiled): " + (totalFinalTime / 10) + " ns");
            }
        }
    }

    /**
     * A dummy computational method that will be JIT compiled after sufficient invocations.
     */
    private static int performComputation() {
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            sum += i * i;
        }
        return sum;
    }
}
