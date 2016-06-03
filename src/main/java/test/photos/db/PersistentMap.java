package test.photos.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.sun.tools.javac.jvm.ByteCodes.ret;

/**
 * TODO: Document me
 *
 * @author Ivan Verhun (ivanver@jfrog.com)
 */
public class PersistentMap<K, V> implements Map<K, V> {
    private HashMap<K, V> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override public boolean isEmpty() {
        return storage.isEmpty();
    }

    @Override public boolean containsKey(Object key) {
        return storage.containsKey(key);
    }

    @Override public boolean containsValue(Object value) {
        return storage.containsValue(value);
    }

    @Override public V get(Object key) {
        return storage.get(key);
    }

    // TODO
    @Override public V put(K key, V value) {
        return null;
    }

    // TODO
    @Override public V remove(Object key) {
        V removedValue = storage.remove(key);
        // TODO: persist
        return removedValue;
    }

    // TODO
    @Override public void putAll(Map<? extends K, ? extends V> m) {

    }

    // TODO
    @Override public void clear() {

    }

    @Override public Set<K> keySet() {
        return storage.keySet();
    }

    @Override public Collection<V> values() {
        return storage.values();
    }

    @Override public Set<Entry<K, V>> entrySet() {
        return storage.entrySet();
    }
}
