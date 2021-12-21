package com.zs.tixi.class13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/*
 * 7. 二叉树的递归套路深度实践：二叉树上任意两节点都存在距离。返回整棵二叉树的最大距离。
 *      提示：两个节点的距离为：两个节点的最低公共祖先到两个节点一路上经过的节点之和。
 */
public class Code06_MaxDistance {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }


    /**
     * 收集所有节点到集合arr
     * 定义max=0.
     * 遍历arr:
     *      查当前节点到后续所有节点的距离。
     *      如果大于之前的最大距离，更新max。
     * 返回max
     * @param head
     * @return
     */
    public static int maxDistance1(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = getPrelist(head);
        HashMap<Node, Node> parentMap = getParentMap(head);
        int max = 0;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i; j < arr.size(); j++) {
                max = Math.max(max, distance(parentMap, arr.get(i), arr.get(j)));
            }
        }
        return max;
    }

    // 返回当前二叉树的先序遍历收集的节点集合。
    public static ArrayList<Node> getPrelist(Node head) {
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        return arr;
    }
    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    /**
     * 遍历整颗二叉树，收集每个节点对应父节点的map。
     * @param head
     * @return
     */
    public static HashMap<Node, Node> getParentMap(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        map.put(head, null);
        fillParentMap(head, map);
        return map;
    }
    public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    /**
     * 返回两个节点的最小距离。
     * 收集o1到根节点的所有节点。组成集合o1Set.
     * 从o2开始，一直向上，在遇到的第一个o1Set内的节点停下。
     * 此时来到了o1和o2的最低公共祖先o3位置。
     * 从o1到o3的距离 + 从o2到o3的距离 -1
     * 减一是因为o3多计算了一次
     *
     * @param parentMap
     * @param o1
     * @param o2
     * @return
     */
    public static int distance(HashMap<Node, Node> parentMap, Node o1, Node o2) {
        HashSet<Node> o1Set = new HashSet<>();
        Node cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        Node lowestAncestor = cur;
        cur = o1;
        int distance1 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance1++;
        }
        cur = o2;
        int distance2 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance2++;
        }
        return distance1 + distance2 - 1;
    }

    /**
     *
     * @param head
     * @return
     */
    public static int maxDistance2(Node head) {
        return process(head).maxDistance;
    }

    /**
     * 返回当前二叉树的最大距离和高度。
     * 当前二叉树的高度等于左右子树的较大高度加1.
     * 如果当前二叉树的最大距离经过当前节点，那么一定是经过左树高度到当前节点再到右树高度。即左树高度加右树高度加1.
     * 如果当前二叉树的最大距离不经过当前节点，那么一定是左树和右树最大距离中较大的那个。
     * 比较以上三个最大距离，最大的即为当前二叉树的最大距离。
     * @param head
     * @return
     */
    private static Info process(Node head) {
        if ( head == null){
            return new Info(0, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height)+1;
        int p1 = leftInfo.maxDistance;
        int p2 = rightInfo.maxDistance;
        int p3 = leftInfo.height+rightInfo.height+1;
        int maxDistance = Math.max(p1 , Math.max(p2, p3));

        return new Info(maxDistance, height);
    }

    public static class Info {
        public int maxDistance;
        public int height;

        public Info(int m, int h) {
            maxDistance = m;
            height = h;
        }

    }


    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxDistance1(head) != maxDistance2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
