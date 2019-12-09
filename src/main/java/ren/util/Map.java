package ren.util;

public class Map<K, V> {

    private java.util.Map<K, V> map;
    private Set<K> set;

    public Map(java.util.Map<K, V> map) {
        init(map);
    }

    public void init(java.util.Map<K, V> map) {
        this.map = map;
        this.set = new Set(map.keySet());
    }

    public int size() {
        return set.size();
    }

    public K getKey(int index) {
        return set.get(index);
    }

    public V getValue(int index) {
        return map.get(getKey(index));
    }
}
