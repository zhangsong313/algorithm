package com.zs.tixi.class37;

import java.util.ArrayList;

public class Code02_SkipListMap {
    /**
     * 跳表节点
     * key
     * val
     * nextNodes：向右的链表列表。
     */
    public static class SkipListNode<K extends Comparable<K>, V>{
        public K key;
        public V val;
        ArrayList<SkipListNode<K, V>> nextNodes;
        public SkipListNode(K k, V v){
            key = k;
            val = v;
            nextNodes = new ArrayList<>();
        }

        /**
         * 当前key是否小于指定key。
         */
        public boolean isLessKey(K otherKey){
//            return otherKey != null && key.compareTo(otherKey)<0;
            return otherKey != null && ( key==null || key.compareTo(otherKey)<0);
        }

        /**
         * 当前key是否等于指定key
         */
        public boolean isEqualsKey(K otherKey){
            return (key == null && otherKey == null) ||
                    (key !=null && otherKey !=null && key.compareTo(otherKey)==0);
//            return key.compareTo(otherKey)==0;
        }
    }

    /**
     * 跳表
     * PROBABILITY：随机因子。只有小于此数时可以继续尝试随机升层。
     * head：最左侧头节点。
     * size：整表内有多少节点。
     * maxLevel：最大层数。
     *
     */
    public static class SkipListMap<K extends Comparable<K>, V>{
        private static final double PROBABILITY = 0.5;
        private SkipListNode<K, V> head;
        private int size;
        private int maxLevel;
        /**
         * 初始化head。
         * head的列表新增一个null表示第0层。
         */
        public SkipListMap(){
            head = new SkipListNode<>(null, null);
            head.nextNodes.add(null);
            size = 0;
            maxLevel = 0;
        }


        /**
         * 返回整个表中小于key的最右节点。
         * 从head的maxLevel开始
         * level定义为maxLevel
         * cur定义为head。
         * 循环：level>=0
         * 		cur从当前节点开始，在当前层一直向右走，直到小于key的最后一个节点。
         * 返回cur。
         */
        private SkipListNode<K, V> mostRightLessNodeInTree(K key){
            SkipListNode<K, V> cur = head;
            int level = maxLevel;
            while (level>=0){
                cur = mostRightLessNodeInLevel(key, cur, level);
                level--;
            }
            return cur;
        }

        /**
         * 从cur节点开始，在第level层找到小于key的最右一个节点返回。
         * 获取cur的在level层的下一个节点。
         * 循环：next不为空 且 next小于key
         * 		cur来到next。
         * 		next来到leve层的下一个接节点。
         * 返回cur。
         *
         */
        private SkipListNode<K, V> mostRightLessNodeInLevel(K key, SkipListNode<K, V> cur, int level) {
            SkipListNode<K, V> next = cur.nextNodes.get(level);
            while (next!=null && next.isLessKey(key)){
                cur = next;
                next = cur.nextNodes.get(level);
            }
            return cur;
        }

        /**
         * 是否包含key。
         * 找到整表中小于key的最后一个节点。
         * 如果节点的下一个节点不为空 且 下一个节点等于key。
         */
        public boolean containsKey(K key){
            SkipListNode<K, V> pre = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = pre.nextNodes.get(0);
            return next!=null && next.isEqualsKey(key);
        }

        /**
         * 设置key的值为value
         * 找到整表上小于key的最后一个节点。
         * 如果节点的下一个节点不为空 且 等于key。
         * 	设置下一个节点的val为value。
         * 否则
         * 	新增一个节点。
         * 	size加1
         * 	newLevel为新增节点的层数。
         * 	随机层数：循环直到随机数不小于PROBABILITY，每次newLevel加1
         * 	尝试调整maxLevel：循环直到newLevel不大于maxLevel，每次head新加一个空链表，maxLevel加1.
         * 	新增一个节点，节点的添加newLevel个空链表（提前扩充好空间）
         * 	定义pre为head。
         * 	定义level为maxLevel
         * 	循环：level大于等于0
         * 		pre来到pre当前层小于key的最右节点。
         * 		如果level小于等于newLevel，表示新节点要在这一层插入。
         * 			新节点的当前层设置为pre的后一个节点。
         * 			pre的当前层下一个节点设置为新节点。
         * 		level减一。
         *
         * @param key
         * @param value
         */
        public void put(K key, V value){
            SkipListNode<K, V> pre = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = pre.nextNodes.get(0);
            if(next!=null && next.isEqualsKey(key)){
                next.val = value;
                return;
            }
            size++;
            int newLevel = 0;
            while (Math.random()<PROBABILITY){
                newLevel++;
            }
            while (maxLevel<newLevel){
                head.nextNodes.add(null);
                maxLevel++;
            }
            SkipListNode<K, V> newNode = new SkipListNode<>(key, value);
            for (int i = 0; i <= newLevel; i++) {
                newNode.nextNodes.add(null);
            }
            SkipListNode<K, V> cur = head;
            int level = maxLevel;
            while (level>=0){
                cur = mostRightLessNodeInLevel(key, cur, level);
                if(level<=newLevel){
                    newNode.nextNodes.set(level, cur.nextNodes.get(level));
                    cur.nextNodes.set(level, newNode);
                }
                level--;
            }
        }

        /**
         * 返回指定key对应的value。
         * 找到整表上小于key的最后一个节点。
         * 如果节点的下一个不为空 且 等于key。返回下一个节点的值，否则返回空。
         */
        public V get(K key){
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next!=null && next.isEqualsKey(key)?next.val:null;
        }

        /**
         * 从表中移除key对应的节点。
         * 如果判断不包含该key直接返回。
         * size减一。
         * 定义pre为head。
         * 定义level为maxLevel。
         * 循环：level大于等于0
         * 		pre来到当前层小于key的最后一个节点。
         * 		如果pre下一个节点不为空且等于key。
         * 			pre指向下一个节点的下一个节点。
         * 		如果level不为0，且pre为head，且下一个节点为空。说明该层数据为空，需要删层。
         * 			head移除当前层数据。
         * 			maxLevel减一。
         * 		level减一
         * @param key
         */
        public void remove(K key){
            if(!containsKey(key)) return;
            size--;
            SkipListNode<K, V> pre = head;
            int level = maxLevel;
            while (level>=0){
                pre = mostRightLessNodeInLevel(key, pre, level);
                SkipListNode<K, V> next = pre.nextNodes.get(level);
                if(next!=null && next.isEqualsKey(key)){
                    pre.nextNodes.set(level, next.nextNodes.get(level));
                }
                if(level!=0 && pre==head && next==null){
                    maxLevel--;
                    head.nextNodes.remove(level);
                }
                level--;
            }
        }

        /**
         * 返回最小的key
         * head的第0层下一个节点key返回
         * @return
         */
        public K firstKey(){
            return head.nextNodes.get(0)==null?null:head.nextNodes.get(0).key;
        }
        /**
         * 返回最大的key
         * cur定义为head
         * level定义为maxLevel。
         * 循环：level大于等于0
         * 		cur在当前层一直向后直到最后一个节点。
         * 		level减1
         * 返回最后一个节点的key。
         * @return
         */
        public K lastKey(){
            SkipListNode<K, V> cur = head;
            int level = maxLevel;
            while (level>=0){
                SkipListNode<K, V> next = cur.nextNodes.get(level);
                while (next!=null){
                    cur = next;
                    next = cur.nextNodes.get(level);
                }
                level--;
            }
            return cur.key;
        }

        /**
         * 返回不小于key的最小key。
         * 找到小于key的最后一个节点。
         * 如果下一个节点不为空，返回下一个节点的key，否则返回null
         *
         * @param key
         * @return
         */
        public K ceilingKey(K key){
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next!=null?next.key:null;
        }

        /**
         * 返会不大于key的最大key
         * 找到小于key的最后一个节点，
         * 如果下一个节点不为空且等于key，返回下一个节点的key，否则返回找到的节点key。
         * @param key
         * @return
         */
        public K floorKey(K key){
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next!=null && next.isEqualsKey(key)? key : less.key;
        }

        /**
         * 表中节点数量
         * @return
         */
        public int size() {
            return size;
        }
    }


    // for test
    public static void printAll(SkipListMap<String, String> obj) {
        for (int i = obj.maxLevel; i >= 0; i--) {
            System.out.print("Level " + i + " : ");
            SkipListNode<String, String> cur = obj.head;
            while (cur.nextNodes.get(i) != null) {
                SkipListNode<String, String> next = cur.nextNodes.get(i);
                System.out.print("(" + next.key + " , " + next.val + ") ");
                cur = next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SkipListMap<String, String> test = new SkipListMap<>();
        printAll(test);
        System.out.println("======================");
        test.put("A", "10");
        printAll(test);
        System.out.println("======================");
        test.remove("A");
        printAll(test);
        System.out.println("======================");
        test.put("E", "E");
        test.put("B", "B");
        test.put("A", "A");
        test.put("F", "F");
        test.put("C", "C");
        test.put("D", "D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.containsKey("B"));
        System.out.println(test.containsKey("Z"));
        System.out.println(test.firstKey());
        System.out.println(test.lastKey());
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));
        System.out.println("======================");
        test.remove("D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));


    }
}
