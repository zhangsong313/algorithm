package com.zs.tixi.class17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/*
 * 图的拓扑排序算法 https://www.lintcode.com/problem/topological-sorting
 *      宽度优先遍历 :每次先打印那些入度为0的点,每个点打印后,更新邻接点入度-1
 *
 */
public class Code03_TopologicalOrderBFS {

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
     * 创建一个map用来记录每个点的入度.
     *
     * 创建ans列表用来收集答案.
     * 遍历graph:
     *      把每个点装入map,入度设置为0.
     * 遍历graph:
     *      遍历当前点的所有邻接点列表:
     *          每个邻接点的入度+1
     * 创建一个队列queue,用来存放入度为0的点.
     * 遍历map:
     *      如果当前点入度为0,加入queue
     * 遍历queue:curr
     *      curr加入ans.
     *      遍历curr所有邻接点:
     *          邻接点入度减一,
     *          如果入度变为0,加入map
     *
     * 返回ans
     * @param graph
     * @return
     */
    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        Map<DirectedGraphNode, Integer> inMap = new HashMap<>();
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (DirectedGraphNode node : graph) {
            inMap.put(node, 0);
        }
        for (DirectedGraphNode node : graph) {
            for (DirectedGraphNode next : node.neighbors) {
                inMap.put(next, inMap.get(next)+1);
            }
        }
        Queue<DirectedGraphNode> queue = new LinkedList<>();
        for (DirectedGraphNode node : inMap.keySet()) {
            if (inMap.get(node)==0){
                queue.add(node);
            }
        }
        while (!queue.isEmpty()){
            DirectedGraphNode curr = queue.poll();
            ans.add(curr);
            for (DirectedGraphNode next : curr.neighbors) {
                inMap.put(next, inMap.get(next)-1);
                if (inMap.get(next)==0) queue.add(next);
            }
        }
        return ans;
    }
}
