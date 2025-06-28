/* SemanticAnalyzer.java */
import java.util.*;

public class SemanticAnalyzer {
    private List<MiniLangLexer.Token> tokens;
    private Set<String> declaredVariables = new HashSet<>();
    private List<String> errors = new ArrayList<>();

    public SemanticAnalyzer(List<MiniLangLexer.Token> tokens) {
        this.tokens = tokens;
    }

    public boolean analyze() {
        for (int i = 0; i < tokens.size(); i++) {
            MiniLangLexer.Token token = tokens.get(i);

            if (token.type == MiniLangLexer.TokenType.KEYWORD && token.value.equals("int")) {
                if (i + 1 < tokens.size() && tokens.get(i + 1).type == MiniLangLexer.TokenType.IDENTIFIER) {
                    String varName = tokens.get(i + 1).value;
                    if (declaredVariables.contains(varName)) {
                        errors.add("Variable already declared: " + varName);
                    } else {
                        declaredVariables.add(varName);
                    }
                    i++; // skip identifier
                } else {
                    errors.add("Expected identifier after 'int'");
                }
            } else if (token.type == MiniLangLexer.TokenType.IDENTIFIER) {
                if (!declaredVariables.contains(token.value)) {
                    errors.add("Undeclared variable: " + token.value);
                }
            }
        }
        return errors.isEmpty();
    }

    public void printErrors() {
        if (errors.isEmpty()) {
            System.out.println("Semantic analysis passed.");
        } else {
            System.out.println("Semantic Errors:");
            for (String error : errors) {
                System.out.println(" - " + error);
            }
        }
    }
}
