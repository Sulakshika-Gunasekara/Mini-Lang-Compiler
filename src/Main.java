import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            String input = Files.readString(Path.of("input.minilang"));

            System.out.println("=== Tokens ===");
            List<MiniLangLexer.Token> tokens = MiniLangLexer.tokenize(input);
            for (MiniLangLexer.Token token : tokens) {
                System.out.println(token);
            }

            System.out.println("\n=== Syntax Analysis ===");
            MiniLangParser parser = new MiniLangParser(tokens);
            boolean syntaxValid = parser.parse();
            System.out.println(syntaxValid ? "Syntax analysis passed." : "Syntax analysis failed.");

            System.out.println("\n=== Semantic Analysis ===");
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(tokens);
            boolean semanticValid = semanticAnalyzer.analyze();
            semanticAnalyzer.printErrors();

            if (syntaxValid && semanticValid) {
                System.out.println("\n=== Intermediate Code ===");
                IntermediateCodeGenerator codeGen = new IntermediateCodeGenerator(tokens);
                codeGen.generate();
                codeGen.printCode();
            } else {
                System.out.println("\nCompilation aborted due to errors.");
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}