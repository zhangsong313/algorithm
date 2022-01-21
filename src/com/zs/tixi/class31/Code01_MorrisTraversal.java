package com.zs.tixi.class31;

/*
 * Morris遍历
 *      一种遍历二叉树的方式,并且时间复杂度为O(N),额外空间复杂度为O(1)
 *      通过利用原树中大量空闲指针的方式,达到节省空间的目的.
 *
 * Morris遍历细节:
 *      假设当前来到节点cur,开始时cur来到头节点位置
 *      1)如果cur没有左孩子,cur向右移动(cur = cur.right)
 *      2)如果cur有左孩子,找到左子树上最右的节点mostRiGht;
 *          a.如果mostRight的右指针指向空,让其指向cur,
 *              然后cur向左移动(cur = cur.left)
 *          b.如果mostRight的右指针指向cur,让其指向null,
 *              然后cur向右移动(cur = cur.right)
 *      3)cur为空时停止遍历
 *
 * Morris遍历实质
 *  建立一种机制:
 *      对于没有左子树的节点只达到一次
 *      对于有左子树的节点会达到两次.
 *      morris遍历的时间复杂度依然是O(N)
 */
public class Code01_MorrisTraversal {
    public static class Node {
        public int value;
        Node left;
        Node right;

        public Node(int data) {
            this.value = data;
        }
    }
}
