package com.zs.tixi.class37;

import java.util.TreeMap;

public class SizeBalancedTreeMap {

    /**
     * k : key
     * v : value
     * l : 左子树
     * r : 右子树
     * size : 树节点个数，平衡因子
     */
    class SBTNode<K extends Comparable<K>, V>{
        public K k;
        public V v;
        public SBTNode<K, V> l;
        public SBTNode<K, V> r;
        public int size;
        public SBTNode(K key, V value){
            k = key;
            v = value;
            size = 1;
            TreeMap
        }
    }

    class SBTMap<K extends Comparable<K>, V>{
        private SBTNode<K, V> root;
        /**
         * 设置指定key的值
         */
        public void put(K key, V value){

        }

        /**
         * 查询指定key的值
         */
        public V get(K key){}

        /**
         * 删除指定key的节点
         */
        public void remove(K key){}

        /**
         * 返回有序表大小
         */
        public int size(){}

        /**
         * 是否包含指定key
         */
        public boolean containsKey(K key){}

        /**
         * 不大于指定key的最大key
         */
        public K floorKey(K key){}

        /**
         * 不小于指定key的最小key
         */
        public K ceilingKey(K key){}

        /**
         * 第一个key
         */
        public K firstKey(){}

        /**
         * 最后一个key
         */
        public K lastKey(){}

        /**
         * 返回指定下标位置的key。(系统api不支持)
         */
        public K getIndexKey(int index){}

        /**
         * 返回指定下标位置的value。(系统api不支持)
         */
        public V getIndexValue(int index){}
    }
}
