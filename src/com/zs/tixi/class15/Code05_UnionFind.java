package com.zs.tixi.class15;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/*
 * 5. 并查集
 *      需要解决的问题：
 *      1）有若干个样本a,b,c,d......类型假设是V
 *      2）在并查集中中一开始认为每个样本都在单独的集合里。
 *      3）用户可以在任何时候调用如下两个方法：
 *          boolean isSameSet(V x, V y): 查询样本x和样本y是否属于同一个集合。
 *          void union(V x, V y):把x和y各自所在的集合的所有样本合并为一个集合。
 *      4）isSameSet和union方法的代价越低越好。
 *      并查集原理：
 *      1）每个节点都有一条往上指的指针。
 *      2）节点a往上找到的头节点，叫做a所在集合的代表节点。
 *      3）查询x和y是否属于同一集合，就是看找到的代表节点是否是同一个。
 *      4）把x和y各自所在集合的所有节点合并成一个集合。只需要小集合的代表节点挂在大集合的代表点的下方即可。
 *      并查集优化：
 *      1）节点往上找代表节点的过程，把沿途中的链变成扁平的。
 *      2）小集合挂在大集合的下面。
 *      3）如果方法调用很频繁，那么单次调用的代价为O(1)，两个方法都是如此。
 *      并查集应用：
 *      解决两大块区域的合并问题。
 *      常用在图等领域中.
 */
public class Code05_UnionFind {
    private static class Node<V> {
        V value;
        public Node(V v) {
            value = v;
        }
    }

    public static class UnionFind<V> {
        private HashMap<V, Node<V>> nodes = new HashMap<>(); // 根据给定值找到对应节点。
        private HashMap<Node<V>, Node<V>> parents = new HashMap<>(); // 找到指定节点的父节点。
        private HashMap<Node<V>, Integer> sizeMap = new HashMap<>(); // 代表节点对应的集合大小。

        /**
         * 初始化并查集时，提供所有值列表。
         * @param values
         */
        public UnionFind(List<V> values) {
            for(V v : values){
                Node<V> n = new Node<>(v);
                nodes.put(v, n);
                parents.put(n, n);
                sizeMap.put(n, 1);
            }
        }

        /**
         * 返回指定节点的代表节点。
         * @param cur
         * @return
         */
        private Node<V> findFather(Node<V> cur) {
            Stack<Node> stack = new Stack<>();
            while (cur!=parents.get(cur)){
                stack.push(cur);
                cur = parents.get(cur);
            }
            while (!stack.isEmpty()){
                parents.put(stack.pop(), cur);
            }
            return cur;
        }

        /**
         * a和b是否在同一集合内
         * @param a
         * @param b
         * @return
         */
        public boolean isSameSet(V a, V b) {
            return findFather(nodes.get(a))==findFather(nodes.get(b));
        }

        /**
         * 合并a和b所在的两个集合。
         * @param a
         * @param b
         */
        public void union(V a, V b) {
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            if (aHead!=bHead){
                Integer aSize = sizeMap.get(aHead);
                Integer bSize = sizeMap.get(bHead);
                Node<V> small = aSize<bSize?aHead:bHead;
                Node<V> big = small==aHead?bHead:aHead;
                parents.put(small, big);
                sizeMap.remove(small);
                sizeMap.put(big, aSize+bSize);
            }
        }

        /**
         * 并查集中有多少个集合。
         * @return
         */
        public int sets() {
            return sizeMap.size();
        }

    }
}
