# MiniLang Compiler

A simple compiler for the MiniLang programming language that performs lexical analysis, syntax analysis, semantic analysis, and intermediate code generation.

## Features

- **Lexical Analysis**: Tokenizes MiniLang source code into keywords, identifiers, numbers, operators, and symbols
- **Syntax Analysis**: Validates the grammatical structure of the program
- **Semantic Analysis**: Checks for variable declarations and usage
- **Intermediate Code Generation**: Generates three-address code for the input program

## Supported MiniLang Syntax

### Data Types
- `int` - Integer variables

### Statements
- **Variable Declaration**: `int variableName;`
- **Assignment**: `variable = expression;`
- **If Statement**: `if (condition) { statements } else { statements }`
- **While Loop**: `while (condition) { statements }`
- **Print Statement**: `print(variable);`

### Expressions
- Variables and numbers
- Binary operations: `+`, `-`, `*`, `/`
- Comparison operations: `>`, `<`, `=`

### Example MiniLang Program
```
int a;
int b;
a = 5;
b = a + 3;
if (a > 0) {
    b = b + 1;
} else {
    b = b - 1;
}
while (b < 10) {
    b = b + 1;
}
print(b);
```

## Project Structure

```
MiniLang Compiler/
├── src/
│   ├── Main.java                    # Main driver class
│   ├── MiniLangLexer.java          # Lexical analyzer
│   ├── MiniLangParser.java         # Syntax analyzer
│   ├── SemanticAnalyzer.java       # Semantic analyzer
│   ├── IntermediateCodeGenerator.java # Code generator
│   └── input.minilang               # Input source file
└── README.md                        # This file
```

## Prerequisites

- **Java Development Kit (JDK)** 8 or higher
- **Command line terminal** or **IDE** with Java support

## Installation and Setup

1. **Download/Clone the project**
   ```bash
   # If using git
   git clone <repository-url>
   
   # Or download and extract the ZIP file
   ```

2. **Navigate to the project directory**
   ```bash
   cd "MiniLang Compiler"
   ```

3. **Create your MiniLang source file**
   - Create a file named `input.minilang` in the `src` directory
   - Write your MiniLang program in this file

## Compilation and Execution

### Method 1: Using Command Line

1. **Navigate to the src directory**
   ```bash
   cd src
   ```

2. **Compile all Java files**
   ```bash
   javac *.java
   ```

3. **Run the compiler**
   ```bash
   java Main
   ```

### Method 2: Using IDE (IntelliJ IDEA, Eclipse, VS Code)

1. **Import the project** into your IDE
2. **Set the main class** to `Main`
3. **Build the project** (usually Ctrl+F9 or Build menu)
4. **Run the Main class**

