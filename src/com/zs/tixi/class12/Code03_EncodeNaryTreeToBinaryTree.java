package com.zs.tixi.class12;

import java.util.ArrayList;
import java.util.List;

/*
 * 3.    Leetcode 431.Encode N-ary Tree to Binary Tree
 * 测试链接：https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree
 *      请将一颗多叉树用二叉树表示，并可以还原回多叉树。
 */
public class Code03_EncodeNaryTreeToBinaryTree {

    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // 只提交这个类即可
    class Codec {
        // Encodes an n-ary tree to a binary tree.
        /**
         * 如果传入头节点root为空，返回null
         * 根据root的值构造一个TreeNode。
         * TreeNode的左节点使用en函数获得，需要的参数为root的所有子节点。
         * @param root
         * @return
         */
        public TreeNode encode(Node root){
            // 根据root的值构建TreeNode作为头节点head。
            // root的children调用en方法转换为head的left子树。

            if(root==null) return null;
            TreeNode head = new TreeNode(root.val);
            List<Node> children = root.children;
            head.left = en(children);
            return head;
        }

        /**
         * 将给定的多叉树子节点列表转换为左子树的右边界。
         * 如果children为空，返回null。
         * 定义变量head=null.
         * 定义变量curr=null。
         * 遍历children:
         *      定义变量newNode = 当前元素值构建的TreeNode。
         *      curr指向newNode
         *      如果head== null
         *          head指向newNode
         *      否则
         *          curr的右子树指向newNode
         *      curr的左子树指向递归调用当前元素children的结果。
         * 返回left。
         * @param children
         * @return
         */
        private TreeNode en(List<Node> children){
            // 如果children为空直接返回null。
            // children的每个节点转换为TreeNode，沿着left的右边界一路挂下去。
            // 同时对于沿途每个转换的TreeNode，将TreeNode的children递归转换为TreeNode的左子树。
            if(children==null) return null;
            TreeNode left = null;
            TreeNode curr = null;
            for (Node child : children) {
                TreeNode newNode = new TreeNode(child.val);
                if(left==null){
                    left = newNode;
                }else{
                    curr.right = newNode;
                }
                newNode.left = en(child.children);
                curr = newNode;
            }
            return left;
        }

        // Decodes your binary tree to an n-ary tree.
        /**
         * 如果当前二叉树为空，返回null。
         * 否则，以root的左子树为参数，通过de函数得到root的children。
         * 以root的val和children来构建多叉树头节点。
         * @param root
         * @return
         */
        public Node decode(TreeNode root) {
            // 根据root的值创建出多叉树的头节点head。
            // head的children从root的左子树转换而来。
            if (root == null) {
                return null;
            }
            return new Node(root.val, de(root.left));
        }

        /**
         * 如果当前节点为空，返回空集合。
         * 定义集合childList来保存子节点。
         * 定义curr为root
         * 循环(curr!=null)
         *      left为curr的左子树
         *      将left递归调用de，得到curr的子节点集合。根据子节点集合和当前节点值构建节点newCurr
         *      newCurr加入到childList
         *      curr来到curr的右子树。
         * 返回childList.
         * @param root
         * @return
         */
        public List<Node> de(TreeNode root) {
            // 创建集合children用来收集多叉树子节点。
            // 沿着当前节点的右边界，将二叉树节点转换为多叉树节点，然后加入children。
            // 对于沿途的每个节点，节点的左子树递归转换为多叉树节点的children。
            List<Node> childList = new ArrayList<>();
            TreeNode curr = root;
            while (curr!=null){
                Node newCurr = new Node(curr.val, de(curr.left));
                childList.add(newCurr);
                curr = curr.right;
            }
            return childList;
        }
    }
}