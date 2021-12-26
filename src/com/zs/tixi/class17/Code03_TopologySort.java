package com.zs.tixi.class17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/*
 * 图的拓扑排序算法 https://www.lintcode.com/problem/topological-sorting
 *      1）在图中找到所有入度为0的点输出。
 *      2）把所有入度为0的点在图中删掉，继续找入度为0的点输出，周而复始。
 *      3）图的所有点都被删除后，依次输出的排序就是拓扑排序。
 *      要求：有向图且没有环。
 *      应用：事件安排，编译顺序。
 *
 *      总结:
 *      笔试时可以通过转换成算法练习的图结果来处理.也能过,但是:
 *          需要把自己图的定义,写到提交的类里.
 *          需要写转换方法.
 *          如果题目的返回值需要原始结构,还需要再转回题目定义的结构....
 *      面试时还是讲清楚思路后使用题目给的结构来实现算法.
 *
 */
public class Code03_TopologySort {

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
    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        Graph myGraph = trans(graph);
        List<Node> ans = sortedTopology(myGraph);

        ArrayList<DirectedGraphNode> backGraph = new ArrayList<>();
        Map<Integer, DirectedGraphNode> map = new HashMap<>();
        for(DirectedGraphNode curr : graph){
            map.put(curr.label, curr);
        }
        for(Node curr : ans){
            DirectedGraphNode node = map.get(curr.value);
            backGraph.add(node);
        }
        return backGraph;
    }

    /**
     * 把题目给的图结构转换为算法练习的结构
     * 创建需要返回的新结构myGraph
     * 给定结构的每个点,可以确定:curr
     *      如果myGraph中根据curr的label没有找到from点,创建from点加入myGraph.
     *     从myGraph中取出from点.
     *     from点的出度设置为curr的邻接点列表大小
     *     遍历curr的邻接点列表:next
     *          如果myGraph的点列表中没有next,根据next的label值创建出to点,加入myGraph的点列表
     *          从myGraph中根据next值取出to点
     *          根据from点和to点创建边edge.加入myGraph
     *          to点的入度加1
     *          from点的邻接点加入to
     *          from点的临接边加入edge
     *
     *
     * @param graph
     * @return
     */
    private static Graph trans(ArrayList<DirectedGraphNode> graph){
        Graph myGraph = new Graph();
        for(DirectedGraphNode curr : graph){
            if (!myGraph.nodes.containsKey(curr.label)){
                myGraph.nodes.put(curr.label, new Node(curr.label));
            }
            Node from = myGraph.nodes.get(curr.label);
            from.out = curr.neighbors.size();

            for(DirectedGraphNode next : curr.neighbors){
                if (!myGraph.nodes.containsKey(next.label)){
                    myGraph.nodes.put(next.label, new Node(next.label));
                }
                Node to = myGraph.nodes.get(next.label);
                Edge edge = new Edge(0, from, to);
                myGraph.edges.add(edge);
                to.in++;
                from.nexts.add(to);
                from.edges.add(edge);
            }
        }
        return myGraph;
    }

    /**
     * 思路:宽度优先遍历,每次先打印那些入度为0的点,每个点打印后,更新邻接点入度-1
     *
     * 定义map保存图节点对应的入度.(不要直接修改图本身节点的入度值)
     * 定义队列queue,用来存放入度为0的点.
     * 定义列表ans用来收集结果.
     * 遍历图的所有节点
     *      map保存当前节点的入度
     *      如果当前节点入度为0,放入queue.
     * 遍历queue:
     *      从队列中取出一个节点curr.
     *      curr加入ans.
     *      遍历curr的所有临接点:next
     *          map中next的入度减一.
     *          如果next的入度为0:
     *              next加入queue
     *
     * 返回ans集合
     *
     * @param graph
     * @return
     */
    public static List<Node> sortedTopology(Graph graph) {
        Map<Node, Integer> map = new HashMap<>();
        Queue<Node> zeroQueue = new LinkedList<>();
        List<Node> ans = new ArrayList<>();
        for(Node curr : graph.nodes.values()){
            map.put(curr, curr.in);
            if (curr.in==0) zeroQueue.add(curr);
        }
        while (!zeroQueue.isEmpty()){
            Node curr = zeroQueue.poll();
            ans.add(curr);
            for(Node next :curr.nexts){
                map.put(next, map.get(next)-1);
                if (map.get(next).equals(0)){
                    zeroQueue.add(next);
                }
            }
        }

        return ans;
    }

    private static class Graph {
        public HashMap<Integer, Node> nodes; // 图的所有节点 ,key为值,value为对应的节点对象.
        public HashSet<Edge> edges; // 图的所有边

        public Graph() {
            nodes = new HashMap<>();
            edges = new HashSet<>();
        }
    }

    private static class Edge {
        public int weight; // 当前边的权重值
        public Node from; // 当前边从哪个点来
        public Node to; // 当前边指向哪个点

        public Edge(int weight, Node from, Node to) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }

    }

    // 点结构的描述
    private static class Node {
        public int value; // 值
        public int in; // 入度,有多少条边指向该点.
        public int out; // 出度,从当前点有多少条边出去.
        public ArrayList<Node> nexts; // 临接点,当前点出发直接路径可以到哪些点.
        public ArrayList<Edge> edges; // 临接边,当前点出发有哪些边

        public Node(int value) {
            this.value = value;
            in = 0;
            out = 0;
            nexts = new ArrayList<>();
            edges = new ArrayList<>();
        }
    }
}
