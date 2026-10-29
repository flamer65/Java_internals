package module07.examples;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Demonstrates effectively final variable capture in lambdas and anonymous inner classes
 * in Java 21 LTS, explaining what breaks effectively final and compiler desugaring.
 *
 * <p>Key JVM and Compiler concepts covered:
 * <ul>
 *   <li>Definition (JLS §4.12.4): A variable is effectively final if its value is never modified after initialization.</li>
 *   <li>Capture semantics: Local primitives/references captured by a lambda or inner class are <i>copied by value</i>
 *       into synthetic constructor parameters and stored in synthetic fields.</li>
 *   <li>Stack vs Heap lifetime: Local variables die when the stack frame pops; lambdas may outlive the method.
 *       Java prevents synchronization illusions by mandating that captured locals are effectively final.</li>
 *   <li>What breaks effectively final: reassignment, pre/post increments, or conditional updates.</li>
 *   <li>Workarounds and concurrency implications: single-element array wrappers or {@link AtomicInteger}.</li>
 *   <li>Field capture contrast: capturing an instance field captures {@code this}, allowing field mutations.</li>
 * </ul>
 *
 * @author Java Mastery Curriculum
 * @version 21.0
 */
public class EffectivelyFinal {

    private int instanceCounter = 0; // Instance field can be mutated via lambda capturing 'this'

    public static void main(String[] args) {
        EffectivelyFinal demo = new EffectivelyFinal();
        demo.demonstrateValidCapture();
        demo.demonstrateWhatBreaksEffectivelyFinal();
        demo.demonstrateWorkarounds();
        demo.demonstrateInstanceFieldCapture();
    }

    /**
     * Demonstrates legal capture of an effectively final local variable.
     */
    public void demonstrateValidCapture() {
        System.out.println("=== 1. Valid Effectively Final Capture ===");
        int port = 8080; // Not explicitly declared 'final', but never reassigned -> effectively final

        Runnable serverTask = () -> {
            // port is captured by value
            System.out.println("Server running on port: " + port);
        };

        serverTask.run();
    }

    /**
     * Demonstrates what would cause compiler errors if a captured variable is mutated.
     */
    public void demonstrateWhatBreaksEffectivelyFinal() {
        System.out.println("\n=== 2. What Breaks Effectively Final ===");
        int attempts = 0;

        // If we uncomment attempts++ or attempts = 1:
        // attempts++; // Mutates attempts!

        // Supplier<Integer> supplier = () -> attempts;
        // COMPILE ERROR: local variables referenced from a lambda expression must be final or effectively final

        System.out.println("Reassigning or modifying a local variable revokes its effectively-final status.");
    }

    /**
     * Demonstrates standard workarounds (array wrapper, AtomicInteger) when an enclosing
     * mutation is required across lambda invocations.
     */
    public void demonstrateWorkarounds() {
        System.out.println("\n=== 3. Workarounds for Mutable State in Closures ===");

        // Workaround A: Single-element array (heap allocated)
        // The array reference 'box' is effectively final; the element box[0] is mutated.
        int[] box = new int[]{0};
        Runnable incrementBox = () -> box[0]++;

        incrementBox.run();
        incrementBox.run();
        System.out.println("Boxed value after 2 runs: " + box[0]);

        // Workaround B: AtomicInteger (thread-safe and idiomatic for concurrent tasks)
        AtomicInteger counter = new AtomicInteger(10);
        Consumer<Integer> adder = amount -> counter.addAndGet(amount);

        adder.accept(5);
        adder.accept(15);
        System.out.println("Atomic counter after additions: " + counter.get());
    }

    /**
     * Demonstrates that instance fields can be mutated inside a lambda because
     * the lambda captures the {@code this} reference, not the field directly.
     */
    public void demonstrateInstanceFieldCapture() {
        System.out.println("\n=== 4. Capturing Instance Fields via 'this' ===");

        Runnable modifier = () -> {
            this.instanceCounter += 10; // Legal! Captures the 'this' reference.
            System.out.println("Inside lambda: instanceCounter = " + this.instanceCounter);
        };

        modifier.run();
        modifier.run();
        System.out.println("Final instanceCounter in caller: " + this.instanceCounter);
    }
}
