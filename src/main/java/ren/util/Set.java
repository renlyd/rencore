package ren.util;

import java.util.Iterator;

public class Set<K> {

    private java.util.Set<K> set;
    private Iterator<K> iterator;
    private int index;

    public Set(java.util.Set<K> set) {
        init(set);
    }

    public void init(java.util.Set<K> set) {
        this.set = set;
        this.iterator = set.iterator();
        this.index = -1;
    }

    public int size() {
        return set.size();
    }

    public K get(int index) {
        if (index > this.index) {
            this.index = -1;
        }
        K k = null;
        while (index > this.index) {
            k = iterator.next();
            this.index++;
        }
        return k;
    }

    public void set(int index, K k) {
        // Remove original object
        set.remove(get(index));
        // Add new object in new order
        set.add(k);
    }
}
