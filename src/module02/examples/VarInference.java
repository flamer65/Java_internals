package module02.examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Shows all valid var uses, bytecode equivalence, diamond operator gotcha.
 */
public class VarInference {
    public static void main(String[] args) {
        // 1. Basic Local Variable Type Inference
        var number = 42;          // Infers int
        var price = 19.99;        // Infers double
        var message = "Hello";    // Infers String
        
        System.out.println("Number: " + number + ", Price: " + price + ", Message: " + message);
        
        // 2. Improved Readability with Complex Types
        // Instead of: HashMap<String, List<Integer>> map = new HashMap<String, List<Integer>>();
        var map = new HashMap<String, List<Integer>>(); 
        map.put("Scores", List.of(100, 90, 80));
        System.out.println("Inferred complex map: " + map);
        
        // 3. Diamond Operator Gotcha
        // BAD: Infers ArrayList<Object> because the diamond <> lacks type context on the right
        var badList = new ArrayList<>(); 
        badList.add("String");
        badList.add(10); // Allowed because it's Object!
        System.out.println("Bad list inferred as Object: " + badList);
        
        // GOOD: Provide type argument on the right
        var goodList = new ArrayList<String>();
        goodList.add("String");
        // goodList.add(10); // COMPILER ERROR: incompatible types
        
        // 4. var with anonymous classes
        var runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Running inferred runnable");
            }
        };
        runnable.run();
    }
}
