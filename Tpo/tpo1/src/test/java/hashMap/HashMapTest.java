package hashMap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HashMapTest {

    @Test
    void testHashMapSize(){
        HashMap hm = new HashMap(3);
        assertEquals(hm.getSize(), 3);
    }

    @Test
    void testHashMapAdd(){
        HashMap hm = new HashMap(3);
        hm.put(1, 1);
        assertTrue(hm.containsKey(1));
        assertEquals(hm.get(1), 1);
        assertFalse(hm.containsKey(2));
        hm.put(48, 2);
        assertTrue(hm.containsKey(48));
        assertEquals(hm.get(48), 2);
        assertFalse(hm.containsKey(2));
        hm.put(3, 3);
        assertTrue(hm.containsKey(3));
        assertEquals(hm.get(3), 3);
        assertFalse(hm.containsKey(2));
        hm.put(2, 3);
        assertFalse(hm.containsKey(2));
    }


    @Test
    void testHashMapReAdd(){
        HashMap hm = new HashMap(3);
        hm.put(1, 1);
        hm.put(1, 2);
        assertTrue(hm.containsKey(1));
        assertEquals(hm.get(1), 2);
    }

    @Test
    void testHashMapIndex(){
        HashMap hm = new HashMap(29);
        int i = 19;
        assertEquals(hm.index(48), i);
        int j = 1;
        assertEquals(hm.index(1), j);
    }

    @Test
    void testHashMapHash(){
        HashMap hm = new HashMap(5);
        int onSize = 2;
        int overSize = 15;
        assertEquals(hm.hash(2), onSize);
        assertEquals(hm.hash(15), overSize);
    }

    @Test
    void testHashMapKeysAndValues(){
        HashMap hm = new HashMap(5);
        hm.put(0, 0);
        hm.put(1, 1);
        hm.put(2, 2);
        hm.put(3, 3);
        hm.put(4, 4);
        int[] expKeys = {0, 1, 2, 3, 4};
        int[] expValues = {0, 1, 2, 3, 4};
        int[] inHMKeys = hm.getKeys();
        int[] inHMValues = hm.getValues();
        assertArrayEquals(expKeys, inHMKeys);
        assertArrayEquals(expValues, inHMValues);
    }

    @Test
    void testHashMapKeysAndValuesOverSize(){
        HashMap hm = new HashMap(3);
        hm.put(0, 0);
        hm.put(1, 1);
        hm.put(2, 2);
        hm.put(3, 3);
        hm.put(4, 4);
        int[] expKeys = {0, 1, 2};
        int[] expValues = {0, 1, 2};
        int[] inHMKeys = hm.getKeys();
        int[] inHMValues = hm.getValues();
        assertArrayEquals(expKeys, inHMKeys);
        assertArrayEquals(expValues, inHMValues);
    }

    @Test
    void testHashMapKeysAndValuesBigKeys(){
        HashMap hm = new HashMap(5);
        hm.put(7, 0);
        hm.put(8, 1);
        hm.put(28, 2);
        hm.put(100, 3);
        hm.put(2000, 4);
        int[] expKeys = {100, 2000, 7, 8, 28};
        int[] expValues = {3, 4, 0, 1, 2};
        int[] inHMKeys = hm.getKeys();
        int[] inHMValues = hm.getValues();
        assertArrayEquals(expKeys, inHMKeys);
        assertArrayEquals(expValues, inHMValues);
    }

    @Test
    void testHashMapKeysAndValuesLessSize(){
        HashMap hm = new HashMap(5);
        hm.put(1, 1);
        hm.put(6, 1);
        int[] expKeys = {-2147483648, 1, 6, -2147483648, -2147483648};
        int[] expValues = {0, 1, 1, 0, 0};
        int[] inHMKeys = hm.getKeys();
        int[] inHMValues = hm.getValues();
        assertArrayEquals(expKeys, inHMKeys);
        assertArrayEquals(expValues, inHMValues);
    }

}
