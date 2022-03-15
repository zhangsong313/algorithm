package com.zs.shuati.class03;

import java.util.*;

/**
 * 给定三个参数，二叉树的头节点head，树上某个节点target，正数K。从target开始，可以向上走或者向下走，返回与target的距离是K的所有节点
 */
public class Code08_DistanceKNodes {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 使用map记录节点的父节点。
     * 这样节点的左子树和右子树和父节点都可以知道。
     * 问题变为了图的BFS遍历。
     * 需要注意分层问题。
     *
     */
    public static List<Node> distanceKNodes(Node root, Node target, int K) {
        HashMap<Node, Node> parentMap = new HashMap<>();
        pre(root, parentMap);

        Queue<Node> queue = new LinkedList<>();

        queue.add(target);

        int step = 0;
        int curLvlCount = 1;
        List<Node> ans = new ArrayList<>();
        while (!queue.isEmpty()){
            int nextLvlCount = 0;
            for (int i = 0; i < curLvlCount; i++) {
                Node poll = queue.poll();
                Node pNode = parentMap.get(poll);
                if(pNode!=null){
                    queue.add(pNode);
                    nextLvlCount++;
                }
                if(poll.left!=null){
                    queue.add(poll.left);
                    nextLvlCount++;
                }
                if(poll.right!=null){
                    queue.add(poll.right);
                    nextLvlCount++;
                }
            }
            curLvlCount = nextLvlCount;
            step++;
            if(step == K){
                for (Node node : queue) {
                    ans.add(node);
                }
            }
        }

        return ans;
    }

    /**
     * 收集cur树上的父子节点关系。
     */
    private static void pre(Node cur, HashMap<Node, Node> map) {
        if(cur.left!=null){
            map.put(cur.left, cur);
            pre(cur.left, map);
        }
        if(cur.right!=null){
            map.put(cur.right, cur);
            pre(cur.right, map);
        }
    }
}
