/* MiniLangLexer.java */
import java.util.*;
// import java.util.regex.*;

public class MiniLangLexer {
    public enum TokenType {
        KEYWORD, IDENTIFIER, NUMBER, OPERATOR, SYMBOL, WHITESPACE
    }

    public static class Token {
        public TokenType type;
        public String value;

        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        public String toString() {
            return type + ": " + value;
        }
    }

    public static List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        String[] keywords = {"int", "if", "else", "while", "print"};
        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        
        int i = 0;
        while (i < input.length()) {
            char c = input.charAt(i);
            
            // Skip whitespace
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }
            
            // Numbers
            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < input.length() && Character.isDigit(input.charAt(i))) {
                    sb.append(input.charAt(i));
                    i++;
                }
                tokens.add(new Token(TokenType.NUMBER, sb.toString()));
                continue;
            }
            
            // Identifiers and keywords
            if (Character.isLetter(c) || c == '_') {
                StringBuilder sb = new StringBuilder();
                while (i < input.length() && (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '_')) {
                    sb.append(input.charAt(i));
                    i++;
                }
                String word = sb.toString();
                if (keywordSet.contains(word)) {
                    tokens.add(new Token(TokenType.KEYWORD, word));
                } else {
                    tokens.add(new Token(TokenType.IDENTIFIER, word));
                }
                continue;
            }
            
            // Operators
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '=' || c == '>' || c == '<') {
                tokens.add(new Token(TokenType.OPERATOR, String.valueOf(c)));
                i++;
                continue;
            }
            
            // Symbols
            if (c == '{' || c == '}' || c == '(' || c == ')' || c == ';') {
                tokens.add(new Token(TokenType.SYMBOL, String.valueOf(c)));
                i++;
                continue;
            }
            
            // Unknown character, skip it
            i++;
        }
        
        return tokens;
    }
}