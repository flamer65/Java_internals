package module02.examples;

/**
 * Demonstrates block scope, method scope, shadowing, this keyword.
 */
public class VariableScope {
    
    // Instance variable (Class Scope)
    private int value = 100;
    
    public void demonstrateScope() {
        // 1. Method Scope
        int methodVar = 50;
        
        // 2. Shadowing
        // Local variable 'value' shadows instance variable 'value'
        int value = 10; 
        
        System.out.println("Local shadowed value: " + value); // Prints 10
        System.out.println("Instance value using 'this': " + this.value); // Prints 100
        
        // 3. Block Scope
        if (methodVar == 50) {
            int blockVar = 25; // Only exists within this block
            System.out.println("Block var: " + blockVar);
        }
        
        // System.out.println(blockVar); // COMPILER ERROR: cannot find symbol (out of scope)
        
        // 4. Loop Scope
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        // System.out.println(i); // COMPILER ERROR: 'i' only exists inside the loop
    }

    public static void main(String[] args) {
        VariableScope scope = new VariableScope();
        scope.demonstrateScope();
    }
}
