package com.zs.tixi.class17;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
 * 图的拓扑排序算法 https://www.lintcode.com/problem/topological-sorting
 *      深度优先遍历 统计每个点向后的点次,按点次大小从大到小依次排序.
 */
public class Code03_TopologicalOrderDFS2 {
    // 不要提交这个类
    private static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

//    public static void main(String[] args) {
//        ArrayList<DirectedGraphNode> graph = new ArrayList<>();
//        Map<Integer, DirectedGraphNode> map = new HashMap<>();
//        for (int i = 0; i < 10; i++) {
//            DirectedGraphNode node = new DirectedGraphNode(i);
//            if(null!=map.get(i-1)){
//                node.neighbors.add(map.get(i-1));
//            }
//            map.put(i, node);
//            graph.add(node);
//        }
//        for (int i = 100; i < 110; i++) {
//            DirectedGraphNode node = new DirectedGraphNode(i);
//            if(null!=map.get(i-1)){
//                node.neighbors.add(map.get(i-1));
//            }
//            map.put(i, node);
//            graph.add(node);
//        }
//        ArrayList<DirectedGraphNode> list = topSort(graph);
//        for (DirectedGraphNode l : list) {
//            System.out.print(l.label+" ");
//        }
//    }


    // 提交下面的
    /**
     * 创建map,key为节点,value为点次和节点对象.
     * 遍历graph:
     *      当前节点curr.
     *      调用f函数,在map中设置当前节点的点次.
     * 将map的values按照点次从大到小排序.
     * 根据排序后的列表生成节点列表
     * @param graph
     * @return
     */
    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, Record> map  = new HashMap<>();
        for (DirectedGraphNode curr : graph) {
            f(curr, map);
        }
        ArrayList<Record> recordArr = new ArrayList<>();
        for (Record r : map.values()) {
            recordArr.add(r);
        }
        recordArr.sort(new MyComparator());
        ArrayList<DirectedGraphNode> ans = new ArrayList<DirectedGraphNode>();
        for (Record r : recordArr) {
            ans.add(r.node);
        }
        return ans;
    }

    public static class MyComparator implements Comparator<Record> {

        @Override
        public int compare(Record o1, Record o2) {
            return o1.nodes == o2.nodes ? 0 : (o1.nodes > o2.nodes ? -1 : 1);
        }
    }


    public static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur);
        }
        // cur的点次之前没算过！
        long nodes = 0;
        for (DirectedGraphNode next : cur.neighbors) {
            nodes += f(next, order).nodes;
        }
        Record ans = new Record(cur, nodes + 1);
        order.put(cur, ans);
        return ans;
    }


    public static class Record {
        public DirectedGraphNode node;
        public long nodes;

        public Record(DirectedGraphNode n, long o) {
            node = n;
            nodes = o;
        }
    }

    /**
     * 深度优先统计当前节点的点次,order为缓存表.
     * 如果order中存在当前节点,直接返回.
     * 创建stack
     * 将当前节点压栈.
     * 遍历stack:
     *      当前节点curr弹出
     *      定义变量allChildDone,表示所有子节点是否都已在map中了.设置为true.
     *      遍历curr的邻接点:next
     *          如果next不在map中
     *              curr压栈
     *              next压栈
     *              allChildDone更新为false
     *              break;
     *      如果allChildDone为true.
     *      将当前节点添加到map中.
     *      record设置为当前点的所有邻接点的record+1的和.
     * @return
     */
    public static Record f2(DirectedGraphNode cur, Map<DirectedGraphNode, Record> order) {
        if (order.containsKey(cur)){
            return order.get(cur);
        }

        Stack<DirectedGraphNode> stack = new Stack<>();
        stack.push(cur);
        while (!stack.isEmpty()){
            DirectedGraphNode curr = stack.pop();
            boolean isAllChildDone = true;
            for (DirectedGraphNode next : curr.neighbors) {
                if (!order.containsKey(next)){
                    stack.push(curr);
                    stack.push(next);
                    isAllChildDone = false;
                    break;
                }
            }
            if (isAllChildDone){
                int nodes = 0;
                for (DirectedGraphNode neighbor : curr.neighbors) {
                    nodes+=order.get(neighbor).nodes;
                }
                order.put(curr, new Record(curr, nodes+1));
            }
        }
        return order.get(cur);
    }

}
