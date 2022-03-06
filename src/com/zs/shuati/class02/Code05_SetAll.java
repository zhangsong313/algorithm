package com.zs.shuati.class02;

import java.util.HashMap;

/**
 * 设计有setAll功能的哈希表，put、get、setAll方法，时间复杂度O(1)
 */
public class Code05_SetAll {
    public static class MyHashMap<K, V>{
        private HashMap<K, MyValue<V>> map;
        private long curTime = 0;
        private long setAllTime = -1;
        private V setAllValue = null;

        public void put(K key, V val){
            map.put(key, new MyValue<>(val, curTime++));
        }
        public V get(K key){
            MyValue<V> value = map.get(key);
            if(value==null) return null;
            if(value.time<setAllTime){
                return setAllValue;
            }else{
                return value.val;
            }
        }
        public void setAll(V val){
            setAllTime = curTime++;
            setAllValue = val;
        }
        private static class MyValue<V>{
            public V val;
            public long time;
            public MyValue(V v, long t){
                val = v;
                time = t;
            }
        }
    }
}
