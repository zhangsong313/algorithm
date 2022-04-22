package com.zs.shuati.class05;

import com.zs.xiaobai.common.MyCompValue;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 2 如果一个节点X，它左树结构和右树结构完全一样，
 * 那么我们说以X为头的树是相等树，给定一棵二叉树的头节点head，返回head整棵树上有多少棵相等子树
 */
public class Code02_LeftRightSameTreeNumber {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }



    // 时间复杂度O(N * logN)

    /**
     * 递归：
     * 左右树分别返回相等子树的数量，然后比较左子树和右子树是否完全相同。
     */
    public static int sameNumber1(Node head) {
        return process1(head).sameCount;
    }

    /**
     * 当前来到node节点，请返回需要的Info信息
     */
    public static Info process1(Node node){
        if (node==null) return new Info(0, "#");
        Info leftInfo = process1(node.left);
        Info rightInfo = process1(node.right);
        int sameCount = leftInfo.serializeStr.equals(rightInfo.serializeStr) ? 1 : 0;
        sameCount += leftInfo.sameCount + rightInfo.sameCount;
        String serializeStr = node.value+leftInfo.serializeStr+rightInfo.serializeStr;
        return new Info(sameCount, serializeStr);
    }
    private static class Info{
        public int sameCount; // 有多少相等子树
        public String serializeStr; // 当前树序列化后的结果,空值用#代替
        public Info(int c, String s){
            sameCount = c;
            serializeStr = s;
        }
    }

    /**
     * 左右树分别返回相等子树的数量，然后比较左子树和右子树是否完全相同。
     * 比较两子树相同时不进行序列化采用递归比较。
     * 优点：减少了字符串拼接消耗，实际测试耗时最少。
     */
    public static int sameNumber2(Node head){
        if(head == null) return 0;
        return sameNumber2(head.left) + sameNumber2(head.right) + (isSame(head.left, head.right) ? 1 : 0) ;
    }

    /**
     * 判断两颗子树是否相同：
     * 如果 一个为空一个不为空，返回false
     * 如果 都为空 返回true。
     * 两树都不为空：判断两树头节点是否等同，且递归判断左树是否相同，右树是否相同。
     */
    private static boolean isSame(Node left, Node right) {
        if(left == null ^ right == null){
            return false;
        }
        if (left == null && right == null){
            return true;
        }
        return left.value== right.value && isSame(left.left, right.left) && isSame(left.right, right.right);
    }

    /**
     * 左右树分别返回相等子树的数量，然后比较左子树和右子树是否完全相同。
     * 比较两子树相同时不进行序列化采用哈希形式比较（）。
     * 优点：哈希长度固定，时间复杂度降低了
     * 缺点：(1)有极小概率发生碰撞，
     *      (2)java中的hash函数在security包中，算法笔试无法直接使用。
     *      (3)实际压测结果表明hash函数的常数时间过大，耗时是三种方法中最久的。
     */
    public static int sameNumber3(Node head){
        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return process2(head, hash).sameCount;
    }

    private static Info2 process2(Node node, MessageDigest hash){
        if(node==null) return new Info2(0, hashcode(hash, "#"));
        Info2 left = process2(node.left, hash);
        Info2 right = process2(node.right, hash);
        int sameCount = left.hashStr.equals(right.hashStr) ? 1 : 0;
        sameCount += left.sameCount + right.sameCount;
        String hashStr = hashcode(hash, node.value + left.hashStr + right.hashStr);
        return new Info2(sameCount, hashStr);
    }

    private static String hashcode(MessageDigest hash, String content){
        byte[] digest = hash.digest(content.getBytes(StandardCharsets.UTF_8));
        return String.valueOf(digest);
    }
    private static class Info2{
        public int sameCount; // 有多少相等子树
        public String hashStr; // 当前树哈希后的结果
        public Info2(int c, String s){
            sameCount = c;
            hashStr = s;
        }
    }

    public static Node randomBinaryTree(int restLevel, int maxValue) {
        if (restLevel == 0) {
            return null;
        }
        Node head = Math.random() < 0.2 ? null : new Node((int) (Math.random() * maxValue));
        if (head != null) {
            head.left = randomBinaryTree(restLevel - 1, maxValue);
            head.right = randomBinaryTree(restLevel - 1, maxValue);
        }
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 8;
        int maxValue = 4;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            Node head = randomBinaryTree(maxLevel, maxValue);
            int ans1 = sameNumber1(head);
            int ans2 = sameNumber2(head);
            int ans3 = sameNumber2(head);
            if (ans1 != ans2 || ans3!= ans2) {
                System.out.println("出错了！");
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
            }
        }
        System.out.println("测试结束");
        System.out.println("性能测试开始");
        Node head = randomBinaryTree(12, maxValue);
        System.out.println("sameNumber1 : ");
        MyCompValue.printUseTime(()->{
            MyCompValue.times(10000, ()->{
                sameNumber1(head);
            });
        });
        System.out.println("sameNumber2 : ");
        MyCompValue.printUseTime(()->{
            MyCompValue.times(10000, ()->{
                sameNumber2(head);
            });
        });
        System.out.println("sameNumber3 : ");
        MyCompValue.printUseTime(()->{
            MyCompValue.times(10000, ()->{
                sameNumber3(head);
            });
        });
        System.out.println("性能测试结束");

    }
}
