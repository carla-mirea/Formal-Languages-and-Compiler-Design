import org.apache.commons.lang3.tuple.Pair;

public class SymbolTable {
    private int size;
    private HashTable<String, String> identifiersHashTable;
    private HashTable<String, String> stringConstantsHashTable;
    private HashTable<Integer, Integer> integerConstantsHashTable;

    public SymbolTable(int size) {
        this.size = size;
        this.identifiersHashTable = new HashTable<>(size);
        this.stringConstantsHashTable = new HashTable<>(size);
        this.integerConstantsHashTable = new HashTable<>(size);
    }

    public Pair<Integer, Integer> addIdentifier(String name, String type) throws Exception {
        return identifiersHashTable.add(name, type);
    }

    public Pair<Integer, Integer> addStringConstant(String constant) throws Exception {
        return stringConstantsHashTable.add(constant, constant);
    }

    public Pair<Integer, Integer> addIntegerConstant(int constant) throws Exception {
        return integerConstantsHashTable.add(constant, constant);
    }

    public Pair<Integer, Integer> getPositionIdentifier(String name) {
        return identifiersHashTable.getPosition(name);
    }

    public Pair<Integer, Integer> getPositionStringConstant(String constant) {
        return stringConstantsHashTable.getPosition(constant);
    }

    public Pair<Integer, Integer> getPositionIntegerConstant(int constant) {
        return integerConstantsHashTable.getPosition(constant);
    }

    public boolean containsIdentifier(String name) {
        return identifiersHashTable.contains(name);
    }

    public boolean containsStringConstant(String constant) {
        return stringConstantsHashTable.contains(constant);
    }

    public boolean containsIntegerConstant(int constant) {
        return integerConstantsHashTable.contains(constant);
    }

    @Override
    public String toString() {
        return "SymbolTable: {" + "\nidentifiersHashTable " + identifiersHashTable + "\nintegerConstantsHashTable "
                + integerConstantsHashTable + "\nstringConstantsHashTable " + stringConstantsHashTable + "}";
    }
}
