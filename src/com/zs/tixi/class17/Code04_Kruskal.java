package com.zs.tixi.class17;

import java.util.*;

/*
 * 最小生成树算法之Kruskal：
 *      思路:
 *      并查集加入所有点.
 *      收集图中所有边,按照权重值排序.
 *      按照排序后顺序遍历所有边:
 *          如果当入点和出点不在同一集合内
 *              并查集合并两个点,
 *              当前边加入答案列表.
 */
public class Code04_Kruskal {

    /**
     * 创建ans作为返回的集合.
     * 把图中所有点加入并查集,每个点作为独立集合.
     * 收集图中所有边.按照权重值从小到大依次排序.
     * 遍历排序后的边集合:
     *      如果当前表的from点和to点,并查集判断不在一个集合内
     *          并查集合并from和to
     *          把当前边加入ans
     * 返回ans
     * @param graph
     * @return
     */
    public static Set<Edge> kruskalMST(Graph graph) {
        UnionFind<Node> unionFind = new UnionFind<>();
        for (Node node : graph.nodes.values()) {
            unionFind.add(node);
        }
        List<Edge> list = new ArrayList<>();
        for (Edge edge : graph.edges) {
            list.add(edge);
        }
        list.sort(new EdgeComparator());
        Set<Edge> ans = new HashSet<>();
        for (Edge edge : list) {
            if(!unionFind.isSameSet(edge.from, edge.to)){
                unionFind.union(edge.from, edge.to);
                ans.add(edge);
            }
        }
        return ans;
    }

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

    public static class EdgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }

    }
}
