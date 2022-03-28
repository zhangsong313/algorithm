package com.zs.tixi.class17;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/*
 * 图的拓扑排序算法 https://www.lintcode.com/problem/topological-sorting
 *      深度优先遍历: 统计每个点的最大深度.按最大深度从大到小排序.
 */
public class Code03_TopologicalOrderDFS1 {
    // 不要提交这个类
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    // 提交下面的

    /**
     * 创建map,key为节点,value为节点和最大深度.
     * 遍历graph,把每个点的最大深度都登记到map.
     * 对map中的record列表按照最大深度从大到小排序.
     * 将recodrd转换为节点列表返回
     * @param graph
     * @return
     */
    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, Record> map = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            f(node, map);
        }
        ArrayList<Record> recordList = new ArrayList<>();
        for (Record r : map.values()) {
            recordList.add(r);
        }
        recordList.sort(new MyComparator());
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (Record r : recordList) {
            ans.add(r.node);
        }
        return ans;
    }

    /**
     * 如果map中已经有记录了,直接返回
     *
     * 定义deep表示邻接点的最大深度.
     * 遍历cur的邻接点:
     *      对每个邻接点递归调用当前函数,得道邻接点最大深度.
     *      deep 更新为deep与邻接点最大深度的较大值.
     * 根据deep+1和当前节点构建record,返回
     * @param cur
     * @param order
     * @return
     */
    private static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
        if (order.get(cur)!=null){
            return order.get(cur);
        }
        int deep = 0;
        for (DirectedGraphNode next : cur.neighbors) {
            deep = Math.max(deep, f(next, order).deep);
        }
        Record record = new Record(cur, deep+1);
        order.put(cur, record);
        return record;
    }
    private static class Record {
        public DirectedGraphNode node;
        public int deep;

        public Record(DirectedGraphNode n, int o) {
            node = n;
            deep = o;
        }
    }
    private static class MyComparator implements Comparator<Record> {

        @Override
        public int compare(Record o1, Record o2) {
            return o2.deep - o1.deep;
        }
    }
}
