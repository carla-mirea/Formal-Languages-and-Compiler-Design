import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Scanner {
    private String program;
    private final List<String> tokens;
    private final List<String> reservedWords;
    private SymbolTable symbolTable;
    private List<Pair<String, Pair<Integer, Integer>>> PIF;
    private int index = 0;
    private int currentLine = 1;

    public Scanner() {
        this.symbolTable = new SymbolTable(47);
        this.PIF = new ArrayList<>();
        this.reservedWords = new ArrayList<>();
        this.tokens = new ArrayList<>();
        try {
            readTokens();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setProgram(String program) {
        this.program = program;
    }

    private void readTokens() throws IOException {
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

    private void skipSpaces() {
        while(index < program.length() && Character.isWhitespace(program.charAt(index))) {
            if(program.charAt(index) == '\n') {
                currentLine++;
            }
            index++;
        }
    }

    private void skipComments() {
        while(index < program.length() && program.charAt(index) == '#') {
            while(index < program.length() && program.charAt(index) != '\n') {
                index++;
            }
        }
    }

    private boolean scanStringConstant() {
        var regexForStringConstant = Pattern.compile("^\"[a-zA-z0-9_ ?:*^+=.!]*\"");
        var matcher = regexForStringConstant.matcher(program.substring(index));
        if(!matcher.find()) {
            if(Pattern.compile("^\"[^\"]\"").matcher(program.substring(index)).find()) {
                throw new ScannerException("Invalid string constant at line " + currentLine);
            }
            if(Pattern.compile("^\\\"[^\\\"]").matcher(program.substring(index)).find()) {
                throw new ScannerException("Missing \" at line " + currentLine);
            }
            return false;
        }
        var stringConstant = matcher.group(0);
        index += stringConstant.length();
        Pair<Integer, Integer> position;
        try {
            position = symbolTable.addStringConstant(stringConstant);
        } catch (Exception e) {
            position = symbolTable.getPositionStringConstant(stringConstant);
        }
        PIF.add(new ImmutablePair<>("string const", position));
        return true;
    }

    private boolean scanIntegerConstant() {
        var regexForIntegerConstant = Pattern.compile("^([+-]?[1-9][0-9]*|0)");
        var matcher = regexForIntegerConstant.matcher(program.substring(index));
        if(!matcher.find()) {
            return false;
        }
        if (Pattern.compile("^([+-]?[1-9][0-9]*|0)[a-zA-z_]").matcher(program.substring(index)).find()) {
            return false;
        }
        var integerConstant = matcher.group(1);
        index += integerConstant.length();
        Pair<Integer, Integer> position;
        try {
            position = symbolTable.addIntegerConstant(Integer.parseInt(integerConstant));
        } catch (Exception e) {
            position = symbolTable.getPositionIntegerConstant(Integer.parseInt(integerConstant));
        }
        PIF.add(new ImmutablePair<>("integer const", position));
        return true;
    }

    private boolean checkIfValid(String possibleIdentifier, String programSubstring) {
        if(reservedWords.contains(possibleIdentifier)) {
            return false;
        }
        if(Pattern.compile("[A-Za-z_][A-Za-z0-9_]* (integer|char|string|number)").matcher(programSubstring).find()) {
            return true;
        }
        return symbolTable.containsIdentifier(possibleIdentifier);
    }

    private boolean scanIdentifier() {
        var regexForIdentifier = Pattern.compile("^([a-zA-Z_][a-zA-Z0-9_]*)");
        var matcher = regexForIdentifier.matcher(program.substring(index));
        if(!matcher.find()) {
            return false;
        }
        var identifier = matcher.group(1);
        if(!checkIfValid(identifier, program.substring(index))) {
            return false;
        }
        index += identifier.length();
        Pair<Integer, Integer> position;
        try {
            position = symbolTable.addIdentifier(identifier, "unknown");
        } catch (Exception e) {
            position = symbolTable.getPositionIdentifier(identifier);
        }
        PIF.add(new ImmutablePair<>("identifier", position));
        return true;
    }

    private boolean scanFromTokens() {
        String possibleToken = program.substring(index).split(" ")[0];
        for(var reservedWord: reservedWords) {
            if(possibleToken.startsWith(reservedWord)) {
                var regex = "^" + "[a-zA-Z0-9_]*" + reservedWord + "[a-zA-Z0-9_]+";
                if(Pattern.compile(regex).matcher(possibleToken).find()) {
                    return false;
                }
                index += reservedWord.length();
                PIF.add(new ImmutablePair<>(reservedWord, new ImmutablePair<>(-1, -1)));
                return true;
            }
        }
        for(var token: tokens) {
            if(Objects.equals(token, possibleToken)) {
                index += token.length();
                PIF.add(new ImmutablePair<>(token, new ImmutablePair<>(-1, -1)));
                return true;
            }
            else if(possibleToken.startsWith(token)) {
                index += token.length();
                PIF.add(new ImmutablePair<>(token, new ImmutablePair<>(-1, -1)));
                return true;
            }
        }
        return false;
    }

    private void nextToken() throws ScannerException {
        skipSpaces();
        skipComments();
        if(index == program.length()) {
            return;
        }
        if(scanIdentifier()) {
            return;
        }
        if(scanStringConstant()) {
            return;
        }
        if(scanIntegerConstant()) {
            return;
        }
        if(scanFromTokens()) {
            return;
        }
        throw new ScannerException("Lexical error: invalid token at line " + currentLine + ", index " + index);
    }

    public void scan(String programFileName) {
        try {
            Path file = Path.of("./" + programFileName);
            setProgram(Files.readString(file));
            index = 0;
            PIF = new ArrayList<>();
            symbolTable = new SymbolTable(47);
            currentLine = 1;
            while(index < program.length()) {
                nextToken();
            }
            FileWriter fileWriter = new FileWriter("PIF" + programFileName.replace(".txt", ".out"));
            for(var pair: PIF) {
                fileWriter.write(pair.getKey() + " -> (" + pair.getValue().getKey() + ", " + pair.getValue().getValue() + ")\n");
            }
            fileWriter.close();
            fileWriter = new FileWriter("ST" + programFileName.replace(".txt", ".out"));
            fileWriter.write(symbolTable.toString());
            fileWriter.close();
            System.out.println("Lexically correct");
        } catch (IOException | ScannerException e) {
            System.out.println(e.getMessage());
        }
    }
}
