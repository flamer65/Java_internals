package module04.examples;

/**
 * Demonstrates char literals and escapes.
 */
public class CharacterLiterals {
    public static void main(String[] args) {
        char letter = 'A';
        char unicodeEscape = '\u0041'; // Evaluated early by lexer

        System.out.println("Letter: " + letter);
        System.out.println("Unicode Match: " + (letter == unicodeEscape));

        // Escapes
        System.out.println("Tab\tNewLine\nQuote\'Slash\\");

        // Surrogate pairs (emoji)
        String emoji = "😀";
        System.out.println("Emoji length: " + emoji.length());
        System.out.println("Code Points: " + emoji.codePointCount(0, emoji.length()));
    }
}
