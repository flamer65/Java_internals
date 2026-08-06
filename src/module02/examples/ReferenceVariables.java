package module02.examples;

/**
 * Shows reference vs object, aliasing, and null references.
 */
public class ReferenceVariables {
    public static void main(String[] args) {
        // 1. Primitive Assignment (Copy by value)
        int a = 10;
        int b = a; // Copying the value
        b = 20;    // Modifying b does not affect a
        System.out.println("Primitive a: " + a); // Prints 10
        System.out.println("Primitive b: " + b); // Prints 20

        // 2. Reference Assignment (Aliasing)
        // 'arr1' is a reference on the stack, the array is on the heap
        int[] arr1 = {10, 20, 30};
        int[] arr2 = arr1; // arr2 now points to the SAME array object on the heap
        
        arr2[0] = 999; // Modifying the array through arr2
        
        // Since arr1 and arr2 point to the same object, changes are visible through both
        System.out.println("Array aliasing - arr1[0]: " + arr1[0]); // Prints 999
        System.out.println("Array aliasing - arr2[0]: " + arr2[0]); // Prints 999

        // 3. Null References
        String str = null;
        System.out.println("Null reference: " + str); // Prints null
        
        try {
            str.length(); // Throws NullPointerException
        } catch (NullPointerException e) {
            System.out.println("Caught NPE as expected when calling method on null reference.");
        }
    }
}
