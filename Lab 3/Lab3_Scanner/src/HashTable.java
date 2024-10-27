import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedList;
import java.util.List;

public class HashTable<V> {
    private LinkedList<V>[] items;
    private int size;

    public HashTable(int size) {
        this.size = size;
        this.items = new LinkedList[size];
        for(int i = 0; i < size; i++)
            this.items[i] = new LinkedList<>();
    }

    private int hash(int key) {
        return key % size;
    }

    private int hash(String key) {
        int sum = 0;
        for(int i = 0; i < key.length(); i++) {
            sum += key.charAt(i);
        }

        return sum % size;
    }

    private int hashValue(Object key) {
        int hashValue = -1;
        if (key instanceof Integer) {
            hashValue = hash((int) key);
        } else if (key instanceof String) {
            hashValue = hash((String) key);
        }
        return hashValue;
    }

    public Pair<Integer, Integer> add(V value) throws Exception {
        int hashValue = hashValue(value);
        LinkedList<V> bucket = items[hashValue];

        for(V v: bucket) {
            if(v.equals(value)) {
                throw new Exception("Value " + value + " is already in the table!");
            }
        }

        bucket.add(value);
        return new ImmutablePair<>(hashValue, bucket.indexOf(value));
    }

    public boolean contains(V value) {
        int hashValue = hashValue(value);
        LinkedList<V> bucket = items[hashValue];

        for (V v : bucket) {
            if (v.equals(value)) {
                return true;
            }
        }

        return false;
    }

    public Pair<Integer, Integer> getPosition(V value) {
        if(this.contains(value)) {
            int hashValue = hashValue(value);
            LinkedList<V> bucket = items[hashValue];

            for(V v: bucket) {
                if(v.equals(value)) {
                    return new ImmutablePair<>(hashValue, bucket.indexOf(value));
                }
            }
        }
        return new ImmutablePair<>(-1, -1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        for (int i = 0; i < items.length; i++) {
            List<V> bucket = items[i];
            if (!bucket.isEmpty()) {
                sb.append("  Bucket ").append(i).append(": ");
                for (V value : bucket) {
                    sb.append("[").append(value).append("] ");
                }
                sb.append("\n");
            }
        }

        sb.append("}");
        return sb.toString();
    }
}
