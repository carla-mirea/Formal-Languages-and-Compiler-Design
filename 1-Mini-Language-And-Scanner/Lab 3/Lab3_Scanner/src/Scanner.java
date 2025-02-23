import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner {
    private SymbolTable symbolTable;
    private List<Pair<String, Pair<Integer, Integer>>> PIF = new ArrayList<>();
    private final List<String> tokens = new ArrayList<>();
    private final List<String> reservedWords = new ArrayList<>();
    private final String filePath;
    private String program;
    private int currentLine = 1;
    private int index = 0;

    public Scanner(String filePath) {
        this.filePath = filePath;
        this.symbolTable = new SymbolTable(50);
        try {
            readFileAndTokenize();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void readFileAndTokenize() throws IOException {
        File file = new File("./token.in");
        BufferedReader reader = Files.newBufferedReader(file.toPath());
        String line;
        while((line = reader.readLine()) != null) {
            String[] splits = line.split(" ");
            switch(splits[0]) {
                case "be", "number", "integer", "bool", "string", "char", "const", "check", "else", "readFromConsole", "showInConsole", "stopWhen", "function", "for" -> reservedWords.add(splits[0]);
                default -> tokens.add(splits[0]);
            }
        }
    }

    private boolean checkIfIdentifier() {
        Pattern identifierPattern = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*");
        Matcher matcher = identifierPattern.matcher(program.substring(index));

        if (!matcher.find()) {
            return false;
        }

        String identifier = matcher.group();
        if (!checkIfValid(identifier, program.substring(index))) {
            return false;
        }

        index += identifier.length();
        Pair<Integer, Integer> position;
        try {
            position = symbolTable.addIdentifier(identifier);
        } catch (Exception e) {
            position = symbolTable.getPositionIdentifier(identifier);
        }
        PIF.add(new ImmutablePair<>("identifier", position));
        return true;
    }

    private boolean checkIfIntegerConstant() {
        Pattern integerPattern = Pattern.compile("^[+-]?\\d+");
        Matcher matcher = integerPattern.matcher(program.substring(index));

        if (!matcher.find()) {
            return false;
        }

        String integerConstant = matcher.group();
        if (Pattern.compile("^[+-]?\\d+[a-zA-Z_]").matcher(program.substring(index)).find()) {
            return false;
        }

        index += integerConstant.length();
        Pair<Integer, Integer> position;
        try {
            position = symbolTable.addIntegerConstant(Integer.parseInt(integerConstant));
        } catch (Exception e) {
            position = symbolTable.getPositionIntegerConstant(Integer.parseInt(integerConstant));
        }
        PIF.add(new ImmutablePair<>("integer constant", position));
        return true;
    }

    private boolean checkIfStringConstant() throws Exception {
        Pattern stringPattern = Pattern.compile("^\"[a-zA-Z0-9_ ?:*^+=.!]*\"");
        Matcher matcher = stringPattern.matcher(program.substring(index));

        if (!matcher.find()) {
            if (Pattern.compile("^\"[^\"]").matcher(program.substring(index)).find()) {
                throw new Exception("Invalid string constant at line " + currentLine);
            }
            if (Pattern.compile("^\"[^\"]").matcher(program.substring(index)).find()) {
                throw new Exception("Unclosed string constant at line " + currentLine);
            }
            return false;
        }

        String stringConstant = matcher.group();
        index += stringConstant.length();
        Pair<Integer, Integer> position;
        try {
            position = symbolTable.addStringConstant(stringConstant);
        } catch (Exception e) {
            position = symbolTable.getPositionStringConstant(stringConstant);
        }
        PIF.add(new ImmutablePair<>("string constant", position));
        return true;
    }

    private boolean checkIfToken() {
        String tokenToCheck = program.substring(index).split(" ")[0];
        for(var reservedWord: reservedWords) {
            if(tokenToCheck.startsWith(reservedWord)) {
                var regex = "^" + "[a-zA-Z0-9_]*" + reservedWord + "[a-zA-Z0-9_]+";
                if(Pattern.compile(regex).matcher(tokenToCheck).find()) {
                    return false;
                }
                index += reservedWord.length();
                PIF.add(new ImmutablePair<>(reservedWord, new ImmutablePair<>(-1, -1)));

                return true;
            }
        }

        for(var token: tokens) {
            if(Objects.equals(token, tokenToCheck)) {
                index += token.length();
                PIF.add(new ImmutablePair<>(token, new ImmutablePair<>(-1, -1)));

                return true;
            }
            else if(tokenToCheck.startsWith(token)) {
                index += token.length();
                PIF.add(new ImmutablePair<>(token, new ImmutablePair<>(-1, -1)));

                return true;
            }
        }

        return false;
    }

    private boolean checkIfValid(String identifier, String subProgram) {
        if(reservedWords.contains(identifier)) {
            return false;
        }
        if(Pattern.compile("[A-Za-z_][A-Za-z0-9_]* (integer|char|string|number)").matcher(subProgram).find()) {
            return true;
        }

        return symbolTable.containsIdentifier(identifier);
    }

    private void skipSpacesAndComments() {
        while(index < program.length() && Character.isWhitespace(program.charAt(index))) {
            if(program.charAt(index) == '\n') {
                currentLine++;
            }
            index++;
        }
        while(index < program.length() && program.charAt(index) == '#') {
            while(index < program.length() && program.charAt(index) != '\n') {
                index++;
            }
        }
    }

    public void scan() {
        try {
            Path file = Path.of("./" + filePath);
            this.program = Files.readString(file);
            boolean lexicallyCorrect = true;

            while(index < program.length()) {
                skipSpacesAndComments();
                if(!(checkIfIdentifier() || checkIfIntegerConstant() || checkIfStringConstant() || checkIfToken())) {
                    String incorrectToken = program.substring(index).split("[\\W\\s]+")[0];
                    index += incorrectToken.length();
                    lexicallyCorrect = false;
                    System.out.println("Lexical error at line " + currentLine + " and index " + index + ", incorrect token " + incorrectToken);
                }
            }
            if(lexicallyCorrect) {
                String PIFFileName = "PIF" + filePath.replace(".txt", ".out");
                FileWriter PIFFileWriter = new FileWriter(PIFFileName);
                for(var pair: PIF) {
                    PIFFileWriter.write(pair.getKey() + ": (" + pair.getValue().getKey() + ", " + pair.getValue().getValue() + ")\n");
                }

                String STFileName = "ST" + filePath.replace(".txt", ".out");
                FileWriter STFileWriter = new FileWriter(STFileName);
                STFileWriter.write(symbolTable.toString());

                PIFFileWriter.close();
                STFileWriter.close();

                System.out.println("Program is lexically correct :)");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
