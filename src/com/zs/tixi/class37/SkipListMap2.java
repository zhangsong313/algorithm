package com.zs.tixi.class37;

import java.util.ArrayList;

// 跳表
public class SkipListMap2 {

    /**
     * k : key
     * v :  value
     * nextNodes : 向右的链表列表
     */
    public static class SkipListNode<K extends Comparable<K>, V>{
        public K k;
        public V v;
        public ArrayList<SkipListNode<K, V>> nextNodes;
        public SkipListNode(K key, V value){
            k = key;
            v = value;
            nextNodes = new ArrayList<>();
        }
    }

    /**
     * root : 根节点
     * size : map大小
     * PROBABILITY : 随机因子,向上建层的概率临界值
     * maxLvl : 最高层数
     */
    public static class SkipListMap<K extends Comparable<K>, V>{
        private SkipListNode<K, V> root;
        private int size;
        private static final double PROBABILITY = 0.5;
        private int maxLvl;

        /**
         * 头节点 K V 固定为空
         * 设置第0层添加一个空节点
         * 为什么要做这样的初始化 ?
         */
        public SkipListMap(){
            root = new SkipListNode<>(null, null);
            root.nextNodes.add(null);
        }

        /**
         * 设置key对应的value。
         */
        public void put(K key, V value){
            if(key==null) return;
            SkipListNode<K, V> pre = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = pre.nextNodes.get(0);
            if(next != null && key.compareTo(next.k)==0){
                next.v = value;
            }else {
                add(key, value);
            }
        }

        /**
         * 查询key对应的value
         */
        public V get(K key){
            if(key==null) return null;
            SkipListNode<K, V> pre = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = pre.nextNodes.get(0);
            return next != null && key.compareTo(next.k)==0 ? next.v : null;
        }

        /**
         * 删除key对应的节点
         */
        public void remove(K key){
            if(key==null) return;
            if(!containsKey(key)) return;

            SkipListNode<K, V> cur = root;
            int curLvl = maxLvl;
            while (curLvl>=0){
                SkipListNode<K, V> pre = mostRightLessNodeInLvl(key, cur, curLvl);
                SkipListNode<K, V> next = pre.nextNodes.get(curLvl);
                if(next!=null && key.compareTo(next.k)==0){
                    pre.nextNodes.set(curLvl, next.nextNodes.get(curLvl));
                }
                if(curLvl!=0 && pre==root && next==null){
                    root.nextNodes.remove(curLvl);
                    maxLvl--;
                }
                curLvl--;
            }
            size--;
        }

        /**
         * map大小
         */
        public int size(){
            return size;
        }

        /**
         * 是否包含指定key
         */
        public boolean containsKey(K key){
            if (key==null) return false;
            SkipListNode<K, V> pre = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = pre.nextNodes.get(0);
            return next != null && key.compareTo(next.k)==0;
        }

        /**
         * 小于等于key的最大key
         */
        public K floorKey(K key){
            if(key==null) return null;
            SkipListNode<K, V> pre = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = pre.nextNodes.get(0);
            return next!=null && key.compareTo(next.k)==0 ? next.k : pre.k;
        }

        /**
         * 大于等于key的最小key
         */
        public K ceilingKey(K key){
            if(key==null) return null;
            SkipListNode<K, V> pre = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = pre.nextNodes.get(0);
            return next==null ? null : next.k;
        }

        /**
         * 第一个key
         */
        public K firstKey(){
            SkipListNode<K, V> first = root.nextNodes.get(0);
            return first==null ? null : first.k;
        }

        /**
         * 最后一个key
         */
        public K lastKey(){
            SkipListNode<K, V> cur = this.root;
            int curLvl = this.maxLvl;
            while (curLvl>=0){
                SkipListNode<K, V> next = cur.nextNodes.get(curLvl);
                while (next!=null){
                    cur = next;
                    next = cur.nextNodes.get(curLvl);
                }
                curLvl--;
            }
            return cur.k;
        }


        /**
         * 返回整个表中小于key的最右节点
         */
        private SkipListNode<K,V> mostRightLessNodeInTree(K key) {
            SkipListNode<K, V> cur = root;
            int lvl = maxLvl;
            while (lvl>=0){
                cur = mostRightLessNodeInLvl(key, cur, lvl--);
            }
            return cur;
        }

        /**
         * 返回当前层中小于key的最右节点
         */
        private SkipListNode<K, V> mostRightLessNodeInLvl(K key, SkipListNode<K, V> cur, int lvl) {
            SkipListNode<K, V> next = cur.nextNodes.get(lvl);
            while (next!=null && key.compareTo(next.k) > 0){
                cur = next;
                next = cur.nextNodes.get(lvl);
            }
            return cur;
        }

        /**
         * 新增一个节点
         */
        private void add(K key, V value) {
            int newLvl = 0;
            while (Math.random() > PROBABILITY){
                newLvl++;
            }

            while (maxLvl < newLvl){
                root.nextNodes.add(null);
                maxLvl++;
            }

            SkipListNode<K, V> newNode = new SkipListNode<>(key, value);
            for (int i = 0; i <= newLvl; i++) {
                newNode.nextNodes.add(null);
            }

            SkipListNode<K, V> cur = root;
            int curLvl = maxLvl;
            while (curLvl>=0){
                cur = mostRightLessNodeInLvl(key, cur, curLvl);
                if (curLvl <= newLvl){
                    newNode.nextNodes.set(curLvl, cur.nextNodes.get(curLvl));
                    cur.nextNodes.set(curLvl, newNode);
                }
                curLvl--;
            }

            size++;
        }

    }
}
