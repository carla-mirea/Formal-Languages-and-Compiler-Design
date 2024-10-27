import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedList;
import java.util.List;

public class HashTable<K, V> {
    private LinkedList<Entry<K, V>>[] items;
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

    private int hashValue(K key) {
        int hashValue = -1;
        if (key instanceof Integer) {
            hashValue = hash((int) key);
        } else if (key instanceof String) {
            hashValue = hash((String) key);
        }
        return hashValue;
    }

    public Pair<Integer, Integer> add(K key, V value) throws Exception {
        int hashValue = hashValue(key);
        LinkedList<Entry<K, V>> bucket = items[hashValue];

        for(Entry<K, V> entry: bucket) {
            if(entry.getKey().equals(key)) {
                throw new Exception("Key " + key + " is already in the table!");
            }
        }

        bucket.add(new Entry<>(key, value));
        return new ImmutablePair<>(hashValue, bucket.indexOf(new Entry<>(key, value)));
    }

    public boolean contains(K key) {
        int hashValue = hashValue(key);
        LinkedList<Entry<K, V>> bucket = items[hashValue];

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    public Pair<Integer, Integer> getPosition(K key) {
        if(this.contains(key)) {
            int hashValue = hashValue(key);
            LinkedList<Entry<K, V>> bucket = items[hashValue];

            for(Entry<K, V> entry: bucket) {
                if(entry.getKey().equals(key)) {
                    return new ImmutablePair<>(hashValue, bucket.indexOf(entry));
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
            List<Entry<K, V>> bucket = items[i];
            if (!bucket.isEmpty()) {
                sb.append("  Bucket ").append(i).append(": ");
                for (Entry<K, V> entry : bucket) {
                    sb.append("[").append(entry.getKey()).append(" -> ").append(entry.getValue()).append("] ");
                }
                sb.append("\n");
            }
        }

        sb.append("}");
        return sb.toString();
    }
}
