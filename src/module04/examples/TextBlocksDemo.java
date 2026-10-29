package module04.examples;

/**
 * Demonstrates text blocks (Java 13/15+) and special escapes.
 */
public class TextBlocksDemo {
    public static void main(String[] args) {
        String json = """
            {
                "name": "Java",
                "version": 21
            }
            """;
        
        String escapes = """
            Keep this trailing space \s
            Don't break this \
            line.
            """;

        System.out.println("JSON:\n" + json);
        System.out.println("Escapes:\n" + escapes);
    }
}
