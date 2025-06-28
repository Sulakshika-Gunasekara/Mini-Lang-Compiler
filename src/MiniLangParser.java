/* MiniLangParser.java */
import java.util.*;

public class MiniLangParser {
    private List<MiniLangLexer.Token> tokens;
    private int position = 0;

    public MiniLangParser(List<MiniLangLexer.Token> tokens) {
        this.tokens = tokens;
    }

    public boolean parse() {
        try {
            while (position < tokens.size()) {
                if (!statement()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean statement() {
        MiniLangLexer.Token token = peek();
        if (token == null) return false;

        if (match("int")) {
            return matchIdentifier() && matchSymbol(";");

        } else if (token.type == MiniLangLexer.TokenType.IDENTIFIER) {
            return assignment();

        } else if (match("if")) {
            return matchSymbol("(") && expression() && matchSymbol(")") && block() && 
                   (peek() != null && peek().value.equals("else") ? (match("else") && block()) : true);

        } else if (match("while")) {
            return matchSymbol("(") && expression() && matchSymbol(")") && block();

        } else if (match("print")) {
            return matchSymbol("(") && matchType(MiniLangLexer.TokenType.IDENTIFIER) && matchSymbol(")") && matchSymbol(";");

        } else {
            return false;
        }
    }

    private boolean assignment() {
        if (!matchType(MiniLangLexer.TokenType.IDENTIFIER)) return false;
        if (!matchOperator("=")) return false;
        if (!expression()) return false;
        return matchSymbol(";");
    }

    private boolean expression() {
        // Handle simple expressions: identifier, number, or binary operations
        if (matchType(MiniLangLexer.TokenType.IDENTIFIER) || matchType(MiniLangLexer.TokenType.NUMBER)) {
            // Check for binary operator
            MiniLangLexer.Token next = peek();
            if (next != null && next.type == MiniLangLexer.TokenType.OPERATOR && 
                (next.value.equals("+") || next.value.equals("-") || next.value.equals("*") || 
                 next.value.equals("/") || next.value.equals(">") || next.value.equals("<"))) {
                position++; // consume operator
                return matchType(MiniLangLexer.TokenType.IDENTIFIER) || matchType(MiniLangLexer.TokenType.NUMBER);
            }
            return true;
        }
        return false;
    }

    private boolean block() {
        if (!matchSymbol("{")) return false;
        
        // Parse statements inside the block
        while (peek() != null && !peek().value.equals("}")) {
            if (!statement()) {
                return false;
            }
        }
        
        return matchSymbol("}");
    }

    private MiniLangLexer.Token peek() {
        return position < tokens.size() ? tokens.get(position) : null;
    }

    private boolean match(String expectedValue) {
        if (peek() != null && peek().value.equals(expectedValue)) {
            position++;
            return true;
        }
        return false;
    }

    private boolean matchType(MiniLangLexer.TokenType type) {
        if (peek() != null && peek().type == type) {
            position++;
            return true;
        }
        return false;
    }

    private boolean matchIdentifier() {
        return matchType(MiniLangLexer.TokenType.IDENTIFIER);
    }

    private boolean matchOperator(String op) {
        return peek() != null && peek().type == MiniLangLexer.TokenType.OPERATOR && match(op);
    }

    private boolean matchSymbol(String symbol) {
        return peek() != null && peek().type == MiniLangLexer.TokenType.SYMBOL && match(symbol);
    }
}
