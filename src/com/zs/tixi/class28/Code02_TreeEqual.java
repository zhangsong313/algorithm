package com.zs.tixi.class28;

/*
 * 题目一:
 *      给定两颗二叉树的头节点head1和head2
 *      想知道head1中是否有某个子树的结构和head2完全一样.
 */
public class Code02_TreeEqual {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 由于二叉树序列化后可以完整的表示当前整棵树.
     * 将head1和head2分别序列化为字符串,判断head2是否是head1的子串.
     */
    public static boolean containsTree1(Node head1, Node head2) {
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        serialize(head1, sb1);
        serialize(head2, sb2);
        String s1 = sb1.toString();
        String s2 = sb2.toString();
        return Code01_KMP.kmp(s1, s2)!=-1;
    }

    /**
     * 将给定的二叉树序列化为字符串(先序)
     * 节点间用逗号间隔,null也需要序列化进去.
     */
    private static void serialize(Node head, StringBuffer sb) {
        if(head==null) {
            sb.append("null,");
            return;
        }
        sb.append(head.value+",");
        serialize(head.left, sb);
        serialize(head.right, sb);
    }

    public static boolean containsTree2(Node big, Node small) {
        if (small == null) {
            return true;
        }
        if (big == null) {
            return false;
        }
        if (isSameValueStructure(big, small)) {
            return true;
        }
        return containsTree1(big.left, small) || containsTree1(big.right, small);
    }

    public static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (head1.value != head2.value) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left)
                && isSameValueStructure(head1.right, head2.right);
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
        int bigTreeLevel = 7;
        int smallTreeLevel = 4;
        int nodeMaxValue = 5;
        int testTimes = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node big = generateRandomBST(bigTreeLevel, nodeMaxValue);
            Node small = generateRandomBST(smallTreeLevel, nodeMaxValue);
            boolean ans1 = containsTree1(big, small);
            boolean ans2 = containsTree2(big, small);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }
}
