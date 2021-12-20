package com.zs.tixi.class13;

/**
 * 1. 判断二叉树是否是完全二叉树（不需要使用二叉树的递归套路）
 *      Complate Binary Tree
 *      完全二叉树：二叉树每层从左到右，如果遇到空节点，则该层后序节点一定都为空，且该层为二叉树最后一层。
 * 2. 二叉树的递归套路：
 *      可以解决面试中遇到的绝大多数二叉树问题，尤其是树形dp问题。
 *      本质是利用递归遍历二叉树的便利性。
 *
 *      递归套路：
 *          1）假设以X节点为头，假设可以向X左树和右树要任何信息。
 *          2）在上一步的假设下，讨论以X为头节点的树，得到答案的可能性（最重要）
 *          3）列出所有可能性后，确定到底需要向左树和右树要什么信息。
 *          4)把左树信息和右树信息求全集，就是任何一颗子树需要返回的信息S
 *          5）递归函数返回S，每一棵子树都这么要求。
 *          6）写代码，在代码中考虑如何把左树的信息和右树的信息整合出整棵树的信息。
 * 3. 二叉树的递归套路深度实践：判断一棵树是否是搜索二叉树。
 *      Binary Search Tree 二叉搜索树：
 *      所有节点满足：左子树的所有节点比当前节点小，右子树的所有节点比当前节点大。
 * 4. 二叉树的递归套路深度实践：判断一棵树是否是平衡二叉树。
 *      Balance Tree BT: 任意节点的子树高度差都小于等于1.
 * 5. 二叉树的递归套路深度实践：判断一棵树是否是满二叉树。
 *      Full Binary Tree FBT : 一个二叉树，如果每一个层的节点数都达到最大值，这个二叉树是满二叉树。
 *      如果二叉树的高度^2 -1为节点数。说明是满二叉树。
 * 6. 二叉树的递归套路深度实践：返回一颗二叉树中最大的二叉搜索子树的大小。
 * 7. 二叉树的递归套路深度实践：二叉树上任意两节点都存在距离。返回整棵二叉树的最大距离。
 *
 */
public class T {
}
