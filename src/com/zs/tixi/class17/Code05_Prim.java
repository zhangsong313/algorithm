package com.zs.tixi.class17;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/*
 * 最小生成树算法之Prim
 *      1）可以从任意节点出发寻找最小生成树。
 *      2）某个点加入到被选中的点中后，解锁这个点出发的所有新的边。
 *      3）在所有解锁的边中选最小的边，然后看这个边会不会形成环。
 *      4）如果会，不要当前边，继续考察剩下解锁边中最小的边，重复3）
 *      5）如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2）
 *      6）当所有的点都被选取，最小生成树就被得到了。
 */
public class Code05_Prim {
    /**
     * 创建小根堆unlockEdgeHeap用来保存当前解锁的边。
     * 创建集合nodeSet用来保存当前解锁的点。
     * 创建集合ans用来保存最后保留的边。
     * 从图中取出第一个点。
     * 把当前点放入nodeSet
     * 把当前点的所有邻接边加入unlockEdgeHeap
     * 循环：unlockEdgeHeap不为空
     *      弹出堆顶元素minEdge
     *      如果minEdge的出点out没有解锁
     *          把minEdge放入ans.
     *          把out放入nodeSet.
     *          把out的所有邻接边放入unlockEdgeHeap
     * @param graph
     * @return
     */
    public static Set<Edge> primMST(Graph graph) {
        PriorityQueue<Edge> unlockEdgeHeap = new PriorityQueue<>(new EdgeComparator());
        Set<Node> nodeSet = new HashSet<>();
        Set<Edge> ans = new HashSet<>();
        for (Node node : graph.nodes.values()) {
            if (!nodeSet.contains(node)){
                nodeSet.add(node);
                for (Edge edge : node.edges) {
                    unlockEdgeHeap.add(edge);
                }
            }
            while (!unlockEdgeHeap.isEmpty()){
                Edge minEdge = unlockEdgeHeap.poll();
                if (!nodeSet.contains(minEdge.to)){
                    ans.add(minEdge);
                    nodeSet.add(minEdge.to);
                    for (Edge edge : minEdge.to.edges) {
                        unlockEdgeHeap.add(edge);
                    }
                }
            }
        }

        return ans;
    }
    private static class EdgeComparator implements Comparator<Edge>{
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight-o2.weight;
        }
    }
}

