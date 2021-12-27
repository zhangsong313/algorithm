package com.zs.tixi.class17;

import com.zs.tixi.class8.HeapGreater;

import java.util.Comparator;
import java.util.HashMap;

/*
 * Dijkstra算法
 *      1)Dijkstra算法必须指定一个源点。
 *      2）生成一个源点到各个点的最小距离表，一开始只有一条记录，即原点到自己的最小距离为0，源点到其它所有点的最小距离都为正无穷大。
 *      3）从距离表中拿出没拿过记录里的最小记录，通过这个点出发的边，更新源点到各个点的最小距离表，不断重复这一步。
 *      4）源点到所有点的记录如果都被拿过一遍，过程停止，最小距离表得到了。
 */
public class Code06_Dijkstra {
    /**
     * 创建map用来记录from点到各个点的距离。
     * map新增(from, 0)
     * 创建小根堆distanceHeap，用来保存那些没有做过跳转点的点的距离信息。
     * from加入distanceHeap
     *
     * 循环：distanceHeap不为空
     *      取出堆顶点curr
     *      从map中取出curr的距离，minDistance
     *      遍历curr的所有邻接边
     *          取出当前邻接边的出点out
     *          如果out没在map中。
     *              把minDistance+当前边长度
     *              map加入out信息。
     *              distanceHeap加入out的信息
     *          否则：
     *              更新out的距离为原距离和minDistance+当前长度的较小值。
     *              distanceHeap更新out的信息。
     *      distanceHeap删除curr。
     * 返回map
     * @param from
     * @return
     */
    public static HashMap<Node, Integer> dijkstra1(Node from) {
        HashMap<Node, Integer> ans = new HashMap<>();
        ans.put(from, 0);
        HeapGreater<DistanceInfo> distanceHeap = new HeapGreater<>(new InfoComparator());
        HashMap<Node, DistanceInfo> nodeToDisMap = new HashMap<>(); // 节点-> 距离信息 map
        nodeToDisMap.put(from, new DistanceInfo(from, 0));
        distanceHeap.add(nodeToDisMap.get(from));

        while (!distanceHeap.isEmpty()){
            DistanceInfo poll = distanceHeap.poll();
            Node curr = poll.node;
            Integer minDistace = ans.get(curr);
            for (Edge edge : curr.edges) {
                Node to = edge.to;
                if (!ans.containsKey(to)){
                    ans.put(to, minDistace+edge.weight);
                    nodeToDisMap.put(to, new DistanceInfo(to,minDistace+edge.weight));
                    distanceHeap.add(nodeToDisMap.get(to));
                } else {
                    ans.put(to, Math.min(minDistace+edge.weight, ans.get(to)));
                    nodeToDisMap.get(to).distance=Math.min(minDistace+edge.weight, ans.get(to));
                    distanceHeap.resign(nodeToDisMap.get(to));
                }
            }
            distanceHeap.remove(poll);
            nodeToDisMap.remove(curr);
        }
        return ans;
    }

    private static class DistanceInfo {
        private Node node;
        private int distance;
        public DistanceInfo(Node node, int distance){
            this.node = node;
            this.distance = distance;
        }
    }
    private static class InfoComparator implements Comparator<DistanceInfo>{

        @Override
        public int compare(DistanceInfo o1, DistanceInfo o2) {
            return o1.distance-o2.distance;
        }
    }
}
