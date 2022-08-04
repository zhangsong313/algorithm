package com.zs.tixi.class12;
/*
 * 6. 二叉树结构如下定义：
 *      Class Node {
 *          V value;
 *          Node left;
 *          Node right;
 *          Node parent;
 *      }
 *      给你二叉树中的某个节点，返回后继节点。
 * 后继节点：中序遍历中当前节点的下一节点。
 * 思路：如果节点为空，只能返回空。
 *      如果节点有右子树。返回右子树的最左节点。
 *      如果节点父节点为空，或是父节点的左子树。返回父节点。否则：来到父节点的父节点重新判断。
 */
public class Code06_SuccessorNode {

    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     *
     * @param node
     * @return
     */
    public static Node getSuccessorNode(Node node){
        // 当前节点为null，直接返回null（无法根据空节点查找）
        // 中序遍历的顺序为：左子树，头节点，右子树。
        // 情况一：将node看作头节点，如果右子树不为空，后继节点为右子树的最左节点。
        // 情况二：如果父节点不为空，且node为左子树，后继节点为父节点。
        // 情况三：如果父节点不为空，且node为右子树，将父节点整体作为左子树考察情况二：
        //          考虑父节点的父节点不为空且父节点为左子树的情况，直到父节点为空。
        if (node == null) return null;
        if (node.right != null){
            return getMostLeftNode(node.right);
        }
        Node parent = node.parent;
        while (parent!=null && node == parent.right){
            node = parent;
            parent = node.parent;
        }
        return parent;
    }

    /**
     * 返回指定二叉树的最左节点。
     *
     * @param node
     * @return
     */
    private static Node getMostLeftNode(Node node) {
        while (node.left != null){
            node = node.left;
        }
        return node;
    }

    public static void main(String[] args) {
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;

        Node test = head.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.right.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.right; // 10's next is null
        System.out.println(test.value + " next: " + getSuccessorNode(test));
    }

}
