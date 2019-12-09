package ren.util;

import java.util.Collection;
import java.util.Iterator;

public class Array {

    static public char[] toCharArray(Collection c) {
        char[] array = new char[c.size()];
        Iterator iter = c.iterator();
        int index = 0;
        while (iter.hasNext()) {
            array[index] = (char) iter.next();
            index++;
        }
        return array;
    }

    static public byte[] toByteArray(Collection c) {
        byte[] array = new byte[c.size()];
        Iterator iter = c.iterator();
        int index = 0;
        while (iter.hasNext()) {
            array[index] = (byte) iter.next();
            index++;
        }
        return array;
    }
}
