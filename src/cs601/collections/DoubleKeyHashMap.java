package cs601.collections;

import java.util.ArrayList;
import java.util.List;

public class DoubleKeyHashMap<K1,K2,V> implements DoubleKeyMap<K1,K2,V>{
    int size;

    private static class Entry<K1, K2, V> {
        K1 key1;
        K2 key2;
        V value;
        Entry<K1, K2,V> next;
        Entry<K1, K2, V> last;
        protected Entry( K1 ekey1, K2 ekey2,V evalue, Entry<K1,K2,V> next, Entry<K1, K2, V> last) {
            this.key1 = ekey1;
            this.key2 = ekey2;
            this.value = evalue;
            this.next = next;
            this.last = last;
        }
    }

    public int hashfunction(K1 key1, K2 key2) {
        return Math.abs(key1.hashCode() * 37 + key2.hashCode());
    }

    private Entry  myhashtable[];
    public DoubleKeyHashMap() {
        myhashtable = new Entry[1000];
        size = 0;
    }

    public DoubleKeyHashMap(int n) {
        myhashtable = new Entry[1000];
        size = 0;
    }
    //
    @Override
    public V put(K1 key1, K2 key2, V value) {
        if(key1==null || key2==null||value==null ) throw new IllegalArgumentException();
        int hashnum = hashfunction(key1, key2)%1000;
        for(Entry<K1, K2, V> e=myhashtable[hashnum]; e!=null; e=e.next) {
            if (e.key1.equals(key1) && e.key2.equals(key2)) {
                V ov = (V) e.value;
                e.value = value;//lots of people use this method just like pointer
                return ov;
            }
        }
        Entry<K1, K2, V> ee = myhashtable[hashnum];
        myhashtable[hashnum] = new Entry<K1, K2, V>(key1, key2, value, ee, null);//something goes not well here
        if(ee != null)ee.last = myhashtable[hashnum];
        size++;
        return  null;
    }

    //
    @Override
    public V get(K1 key1, K2 key2) {
        if(key1==null||key2==null ) throw new IllegalArgumentException();
        int hashnum = hashfunction(key1, key2)%1000;
        for(Entry<K1, K2, V> e = myhashtable[hashnum]; e != null; e=e.next) {
            if(e.key1.equals(key1) && e.key2.equals(key2))
                return e.value;
        }
        return null;
    }

    @Override
    public V remove(K1 key1, K2 key2) {
        int hashnum = hashfunction(key1, key2) % 1000;
        V ans;
        for(Entry<K1, K2, V> e = myhashtable[hashnum]; e != null; e=e.next) {
            if(e.key1.equals(key1) && e.key2.equals(key2)) {
                ans = e.value;
                if(e.last==null) {
                    myhashtable[hashnum] = myhashtable[hashnum].next;
                    if(myhashtable[hashnum] != null)myhashtable[hashnum].last = null;
                }
                else {
                    myhashtable[hashnum].last.next = myhashtable[hashnum].next;
                    if(myhashtable[hashnum].next != null)myhashtable[hashnum].next.last = myhashtable[hashnum].last;
                }
                size--;
                return ans;
            }
        }
        return null;
    }


    //
    @Override
    public boolean containsKey(K1 key1, K2 key2) {
        if(key1 == null || key2 == null ) return  false;
        int hashnum = hashfunction(key1, key2) % 1000;
        for(Entry<K1, K2, V> e = myhashtable[hashnum]; e != null; e = e.next) {
            if(e.key1.equals(key1) && e.key2.equals(key2)) return  true;
        }
        return false;
    }

    @Override
    public List<V> values() {
        ArrayList<V> li = new ArrayList<V>();
        for(int i=0; i<999; i++) {
            Entry<K1, K2, V> E = myhashtable[i];
            while (E != null) {
                li.add((V) E.value);
                E = E.next;
            }
        }
        return li;
    }

    @Override
    public int size() {
        return size;
    }
    @Override
    public void clear() {
        for(int i=0; i<1000; i++) {
            myhashtable[i] = null;
            size = 0;
        }
    }
}


