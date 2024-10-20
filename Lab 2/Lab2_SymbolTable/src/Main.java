import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class Main {
    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable(5);
        Pair<Integer, Integer> identifier = new ImmutablePair<>(-1, -1);
        Pair<Integer, Integer> integerConstant = new ImmutablePair<>(-1, -1);
        Pair<Integer, Integer> stringConstant = new ImmutablePair<>(-1, -1);

        try {
            identifier = symbolTable.addIdentifier("abc", "int");
            System.out.println("abc: " + identifier);
            System.out.println("bca: " + symbolTable.addIdentifier("bca", "char"));
            System.out.println("cab: " + symbolTable.addIdentifier("cab", "char"));
            System.out.println("cba: " + symbolTable.addIdentifier("cba", "char"));

            integerConstant = symbolTable.addIntegerConstant(12);
            System.out.println("12: " + integerConstant);
            System.out.println("21: " + symbolTable.addIntegerConstant(21));
            System.out.println("26: " + symbolTable.addIntegerConstant(26));

            stringConstant = symbolTable.addStringConstant("hello sir");
            System.out.println("hello sir: " + stringConstant);
            System.out.println("hi: " + symbolTable.addStringConstant("hi"));
            System.out.println("wow: " + symbolTable.addStringConstant("wow"));

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println(symbolTable);
        System.out.println("-----------------------------------------------------------------------------------------");

        try {
            assert symbolTable.getPositionIdentifier("abc").equals(identifier) : "abc does not have position" + identifier;
            assert symbolTable.getPositionIdentifier("catchau").equals(new ImmutablePair<>(-1, -1)) : "catchau exists in the table";
            assert symbolTable.getPositionIdentifier("cba").equals(new ImmutablePair<>(4, 1)) : "cba does not have position" + new ImmutablePair<>(4, 1);

            assert symbolTable.getPositionIntegerConstant(26).equals(new ImmutablePair<>(1, 1)) : "26 does not have position (1, 1)";
            assert symbolTable.getPositionIntegerConstant(12).equals(integerConstant) : "12 does not have position" + integerConstant;
            assert symbolTable.getPositionIntegerConstant(21).equals(new ImmutablePair<>(1, 0)): "21 does not have position (1, 0)";

            assert symbolTable.getPositionStringConstant("wow").equals(new ImmutablePair<>(4, 1)) : "wow does not have position" + new ImmutablePair<>(4, 1);
            assert symbolTable.getPositionStringConstant("hello sir").equals(stringConstant) : "hello sir does not have position" + stringConstant;

        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
    }
}