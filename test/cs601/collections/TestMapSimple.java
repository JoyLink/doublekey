package cs601.collections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestMapSimple {
    @Test
    public void testAssignmentExample1(){
        DoubleKeyMap<Integer,Integer,Integer> m =
                new DoubleKeyHashMap<Integer, Integer, Integer>();
        m.put(12,12,12);
        m.put(31,21, 11);

    }
}