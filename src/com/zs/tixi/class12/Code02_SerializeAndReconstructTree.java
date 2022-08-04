package com.zs.tixi.class12;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


/*
 * 2.
 *      二叉树可以通过先序，后序或按层遍历实现序列化和反序列化。请依次实现。
 */
public class Code02_SerializeAndReconstructTree {
    public static class Node{
        public int value;
        public Node left;
        public Node right;
        public Node(int value){ this.value = value;}
    }

    /**
     * 将给定二叉树通过先序遍历实现序列化。
     * 准备一个字符串队列queue。
     * 从head开始
     * 如果当前节点为null，queue加入一个null。
     * 否则：
     *      queue加入当前节点的value。
     *      递归处理左子节点。
     *      递归处理右子节点。
     * @param head
     * @return
     */
    public static Queue<String> preSerial(Node head){
        // 二叉树带着队列queue做先序遍历。
        // 如果当前节点为null，将null加入queue。
        // 当前节点不为null，将当前节点值加入queue，然后递归处理子节点。
        Queue<String> queue = new LinkedList<>();
        pre(head, queue);
        return queue;
    }

    private static void pre(Node head, Queue<String> queue) {
        if (head == null) {
            queue.add(null);
        } else {
            queue.add(String.valueOf(head.value));
            pre(head.left, queue);
            pre(head.right, queue);
        }
    }

    /**
     * 将给定的字符串序列按照先序遍历的方式反序列化为二叉树。
     * 从prelist弹出当前节点curr。
     * 如果为空
     *      返回null
     * 定义遍历head表示当前头节点
     * head指向一个新节点，节点值为curr的值。
     * head的左节点递归调用当前方法得到。
     * head的右节点递归当前方法得到。
     * @param prelist
     * @return
     */
    public static Node preDeserial(Queue<String> prelist) {
        // 从队列serialQueue中取出节点，
        // 如果节点为空，返回null。
        // 节点不为空，用当前值构建节点，作为头节点。
        // 递归调用构建left子节点。
        // 递归调用构建right子节点。
        if (prelist==null) return null;
        String curr = prelist.poll();
        if (curr == null){
            return null;
        }
        Node head  = new Node(Integer.valueOf(curr));
        head.left = preDeserial(prelist);
        head.right = preDeserial(prelist);
        return head;
    }

    /**
     * 将给定的二叉树通过后序遍历实现序列化
     * 准备一个字符串队列queue
     * 从head开始
     * 如果当前节点为null，queue加入null。
     * 否则：
     *      递归处理左子树。
     *      递归处理右子树。
     *      queue加入当前节点的value。
     * @param head
     * @return
     */
    public static Queue<String> posSerial(Node head){
        // 带着队列queue做后序遍历。
        // 如果当前节点为null，将null加入queue。
        // 当前节点不为null，递归处理左子节点。递归处理右子节点，当前节点加入队列queue。
        Queue<String> queue = new LinkedList<>();
        pos(head, queue);
        return queue;
    }

    private static void pos(Node head, Queue<String> queue) {
        if (head == null){
            queue.add(null);
        } else {
            pos(head.left, queue);
            pos(head.right, queue);
            queue.add(String.valueOf(head.value));
        }
    }

    /**
     * 将给定字符串序列按照后序遍历的方式进行反序列化
     * 由于后序遍历，头节点在末尾，先打印的是左右子树。
     * 因此将poslist进行反转。得到头，右，左的遍历序列。
     * 根据头，右，左的顺序进行递归遍历即可。
     * @param poslist
     * @return
     */
    public static Node posDeserial(Queue<String> poslist){
        // 使用栈stack将队列中的节点全部加入。
        // 带着栈stack做先序遍历。和先序反序列化的流程一样。
        if (poslist==null) return null;
        Stack<String> reverseList = new Stack<>();
        while (!poslist.isEmpty()){
            reverseList.add(poslist.poll());
        }
        return posD(reverseList);
    }

    /**
     * poslist弹出字符串curr
     * 如果curr为空
     *      返回null
     * head为新建节点，值为curr.value
     * head的右节点为递归结果
     * head的左节点为递归结果
     * 返回head
     * @param reverseList
     * @return
     */
    private static Node posD(Stack<String> reverseList){
        String curr = reverseList.pop();
        if (curr == null) return null;
        Node head = new Node(Integer.valueOf(curr));
        head.right = posD(reverseList);
        head.left = posD(reverseList);
        return head;
    }

    /**
     * 将给定二叉树按层遍历实现序列化。
     * 准备一个字符串队列queue。
     * 准备一个Node队列nodeQueue。
     * 将head放入nodeQueue.
     * 遍历nodeQueue:
     *      从nodeQueue中取出一个节点：curr。
     *      如果curr为空:
     *          queue加入null
     *      否则:
     *          queue加入curr.value
     *          nodeQueue加入curr.left
     *          nodeQueue加入curr.right
     * @param head
     * @return
     */
    public static Queue<String> levelSerial(Node head){
        // 带着queue做按层遍历。
        // 如果当前节点为null，将null加入queue。
        // 当前节点不为空，将当前节点值加入queue。
        Queue<String> queue = new LinkedList<>();
        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.add(head);
        while (!nodeQueue.isEmpty()){
            Node curr = nodeQueue.poll();
            if (curr == null){
                queue.add(null);
            } else {
                queue.add(String.valueOf(curr.value));
                nodeQueue.add(curr.left);
                nodeQueue.add(curr.right);
            }
        }
        return queue;
    }

    /**
     * 将给定字符串序列按照按层遍历的方式反序列化为二叉树。
     * 定义nodeQueue按层存放节点。(存放上一层节点)
     * 从levelList中取一个字符串curr。
     * 如果为空:
     *      返回null
     * nodeQueue加入一个新节点currNode，节点值为curr。
     *
     * 遍历nodeQueue：
     *      当前节点currNode
     *      从levelList中取出一个字符串left。
     *      如果left不为空；
     *          为currNode创建左节点，值为left
     *          将left节点加入nodeQueue。
     *      从levelList中取出一个字符串Right。
     *      如果right不为空：
     *          为currNode创建右节点，值为right
     *          将right节点加入nodeQueue。
     * 返回currNode
     * @param levelList
     * @return
     */
    public static Node levelDeSerial(Queue<String> levelList){
        // 从序列化的队列serialQueue中依次弹出节点值。
        // 定义用来按层构建二叉树的队列queue。
        // 从serialQueue中取出一个节点作为第一层节点放入queue中，然后采用按层遍历的方式处理queue。
        // 在queue按层构建二叉树的过程中，从serialQueue中取出需要的节点作为子节点。直到queue为空。
        if (levelList==null) return null;
        Queue<Node> lastLvlQueue = new LinkedList<>();
        String curr = levelList.poll();
        if (curr == null) return null;
        Node head = new Node(Integer.valueOf(curr));
        lastLvlQueue.add(head);
        while (!lastLvlQueue.isEmpty()){
            Node currNode = lastLvlQueue.poll();
            String left = levelList.poll();
            String right = levelList.poll();
            if(left!=null) {
                currNode.left = new Node(Integer.valueOf(left));
                lastLvlQueue.add(currNode.left);
            }
            if (right!=null){
                currNode.right = new Node(Integer.valueOf(right));
                lastLvlQueue.add(currNode.right);
            }
        }
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Queue<String> pre = preSerial(head);
            Queue<String> pos = posSerial(head);
            Queue<String> level = levelSerial(head);
            Node preBuild = preDeserial(pre);
            Node posBuild = posDeserial(pos);
            Node levelBuild = levelDeSerial(level);
            if (!isSameValueStructure(preBuild, levelBuild)
                    || !isSameValueStructure(posBuild, levelBuild)
            ) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");
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

    // for test
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
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }
}
