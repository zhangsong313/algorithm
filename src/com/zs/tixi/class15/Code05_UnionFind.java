package com.zs.tixi.class15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static class UnionFind<V>{
        private Map<V, V> parents; // 当前对象到父对象
        private Map<V, Integer> size; // 当前代表对象所属集合大小
        public UnionFind(){
            parents = new HashMap<>();
            size = new HashMap<>();
        }

        /**
         * 加入并查集
         */
        public void add(V v){
            parents.put(v, v);
            size.put(v, 1);
        }

        /**
         * 查询代表元素
         */
        public V findHead(V v){
            List<V> help = new ArrayList<>();
            while (v!=parents.get(v)){
                help.add(v);
                v = parents.get(v);
            }
            for (int i = 0; i < help.size(); i++) {
                parents.put(help.get(i), v);
            }
            return v;
        }

        /**
         * 合并
         */
        public void union(V v1, V v2){
            V head1 = findHead(v1);
            V head2 = findHead(v2);
            if (head1!=head2){
                Integer size1 = size.get(head1);
                Integer size2 = size.get(head2);
                V big = size1>=size2?head1:head2;
                V small= big == head1?head2:head1;
                parents.put(small, big);
                size.remove(small);
                size.put(big, size1+size2);
            }
        }

        /**
         * 查询两个元素是否是同一集合。
         */
        public boolean isSameSet(V v1, V v2){
            return findHead(v1)==findHead(v2);
        }

        /**
         * 返回并查集集合数
         */
        public int sets(){
            return size.size();
        }
    }
}
