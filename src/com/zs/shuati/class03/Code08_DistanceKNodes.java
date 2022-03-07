package com.zs.shuati.class03;

import java.util.List;

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
        return null;
    }
}
