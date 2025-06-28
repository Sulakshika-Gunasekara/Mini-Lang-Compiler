import java.util.*;

public class IntermediateCodeGenerator {
    private List<MiniLangLexer.Token> tokens;
    private List<String> code = new ArrayList<>();
    private int tempCount = 0;

    public IntermediateCodeGenerator(List<MiniLangLexer.Token> tokens) {
        this.tokens = tokens;
    }

    public List<String> generate() {
        for (int i = 0; i < tokens.size(); i++) {
            MiniLangLexer.Token token = tokens.get(i);
            
            // Handle variable declarations
            if (token.type == MiniLangLexer.TokenType.KEYWORD && token.value.equals("int")) {
                if (i + 1 < tokens.size() && tokens.get(i + 1).type == MiniLangLexer.TokenType.IDENTIFIER) {
                    code.add("declare " + tokens.get(i + 1).value);
                    i++; // skip identifier
                }
            }
            // Handle assignments
            else if (token.type == MiniLangLexer.TokenType.IDENTIFIER) {
                if (i + 2 < tokens.size()
                    && tokens.get(i + 1).type == MiniLangLexer.TokenType.OPERATOR && tokens.get(i + 1).value.equals("=")
                    && (tokens.get(i + 2).type == MiniLangLexer.TokenType.NUMBER || tokens.get(i + 2).type == MiniLangLexer.TokenType.IDENTIFIER)) {

                    String left = token.value;
                    String right = tokens.get(i + 2).value;

                    // Check for binary expression (a = b + c)
                    if (i + 4 < tokens.size()
                        && tokens.get(i + 3).type == MiniLangLexer.TokenType.OPERATOR
                        && (tokens.get(i + 4).type == MiniLangLexer.TokenType.NUMBER || tokens.get(i + 4).type == MiniLangLexer.TokenType.IDENTIFIER)) {

                        String tempVar = newTemp();
                        code.add(tempVar + " = " + right + " " + tokens.get(i + 3).value + " " + tokens.get(i + 4).value);
                        code.add(left + " = " + tempVar);
                        i += 4;
                    } else {
                        code.add(left + " = " + right);
                        i += 2;
                    }
                }
            }
            // Handle print statements
            else if (token.type == MiniLangLexer.TokenType.KEYWORD && token.value.equals("print")) {
                if (i + 3 < tokens.size() 
                    && tokens.get(i + 1).type == MiniLangLexer.TokenType.SYMBOL && tokens.get(i + 1).value.equals("(")
                    && tokens.get(i + 2).type == MiniLangLexer.TokenType.IDENTIFIER
                    && tokens.get(i + 3).type == MiniLangLexer.TokenType.SYMBOL && tokens.get(i + 3).value.equals(")")) {
                    code.add("print " + tokens.get(i + 2).value);
                    i += 3;
                }
            }
            // Handle control structures (simplified)
            else if (token.type == MiniLangLexer.TokenType.KEYWORD && token.value.equals("if")) {
                code.add("label L" + (tempCount + 1));
                code.add("if_false goto L" + (tempCount + 2));
                tempCount += 2;
            }
            else if (token.type == MiniLangLexer.TokenType.KEYWORD && token.value.equals("while")) {
                code.add("label L" + (tempCount + 1));
                code.add("if_false goto L" + (tempCount + 2));
                tempCount += 2;
            }
        }
        return code;
    }

    private String newTemp() {
        return "t" + (++tempCount);
    }

    public void printCode() {
        System.out.println("Intermediate Code:");
        for (String line : code) {
            System.out.println(line);
        }
    }
}