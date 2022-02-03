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
    public static void morris(Node head){
        if(head == null) return;
        Node cur = head;
        Node mostRight = null;
        while (cur != null){
            System.out.print(cur.value+" ");
            if(cur.left != null){
                mostRight = cur.left;
                while (mostRight.right!=null && mostRight.right!=cur){
                    mostRight = mostRight.right;
                }
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            cur = cur.right;
        }
        System.out.println();
    }

    /**
     * 定义变量cur表示当前来到的节点,初始化为head.
     * 定义变量mostRight表示左子树的最右节点,初始化为null
     * 循环:cur不为空
     *      如果:cur.left不为空
     *          mostRight设置为cur.left
     *          循环:mostRight.right不为空 且 mostRight.right不等于cur
     *              mostRight设置为mostRight.right
     *          如果mostRight.right等于空
     *              mostRight.right设置为cur
     *              cur设置为cur.left
     *              进入下次循环..
     *          否则
     *              mostRight.right设置为空
     *      cur设置为cur.right
     */
    public static void morris2(Node head){
        Node cur = head;
        Node mr = null;
        while (cur != null){
            System.out.print(cur.value+" ");
            if(cur.left != null){
                mr = cur.left;
                while (mr.right != null && mr.right != cur){
                    mr = mr.right;
                }
                if(mr.right == null){
                    mr.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mr.right = null;
                }
            }
            cur = cur.right;
        }
        System.out.println();
    }

    /**
     * morris实现先序遍历.
     * 经过两次的节点在第一次时打印.
     */
//    public static void morrisPre(Node head){
//        if(head == null) return;
//        Node cur = head;
//        Node mostRight = null;
//        while (cur != null){
//            mostRight = cur.left;
//            if(mostRight != null){
//                while (mostRight.right!=null && mostRight.right!=cur){
//                    mostRight = mostRight.right;
//                }
//                if(mostRight.right == null){
//                    System.out.print(cur.value+" ");
//                    mostRight.right = cur;
//                    cur = cur.left;
//                    continue;
//                } else {
//                    mostRight.right = null;
//                }
//            } else {
//                System.out.print(cur.value+" ");
//            }
//            cur = cur.right;
//        }
//        System.out.println();
//    }

    /**
     * morris实现先序:
     * 在第一次遇到节点时打印,
     * 有两种情况:
     *      1. 有左树,mr.right为空
     *      2. 无左树
     */
    public static void morrisPre(Node head){
        Node cur = head;
        Node mr = null;
        while (cur != null){
            if(cur.left != null){
                mr = cur.left;
                while (mr.right != null && mr.right != cur){
                    mr = mr.right;
                }
                if (mr.right == null){
                    System.out.print(cur.value+" ");
                    mr.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mr.right = null;
                }
            } else {
                System.out.print(cur.value+" ");
            }
            cur = cur.right;
        }
        System.out.println();
    }
    public static void pre(Node head){
        processPre(head);
        System.out.println();
    }
    private static void processPre(Node head){
        if(head==null){
            return;
        }
        System.out.print(head.value+" ");
        processPre(head.left);
        processPre(head.right);
    }

    /**
     * morris实现中序打印.
     * 经过两次的节点在第二次开始打印.
     */
    public static void morrisIn2(Node head){
        if(head == null) return;
        Node cur = head;
        Node mostRight = null;
        while (cur != null){
            if(cur.left != null){
                mostRight = cur.left;
                while (mostRight.right!=null && mostRight.right!=cur){
                    mostRight = mostRight.right;
                }
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            System.out.print(cur.value+" ");
            cur = cur.right;
        }
        System.out.println();
    }

    /**
     * morris实现中序:
     * 在第二次来到节点时打印
     * 有两种情况:
     *      1. 有左树,且mr.right等于cur
     *      2. 无左树
     */
    public static void morrisIn(Node head){
        Node cur = head;
        Node mr = null;
        while (cur != null){
            if(cur.left != null){
                mr = cur.left;
                while (mr.right != null && mr.right != cur){
                    mr = mr.right;
                }
                if (mr.right == null){
                    mr.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mr.right = null;
                }
            }
            System.out.print(cur.value+" ");
            cur = cur.right;
        }
        System.out.println();
    }
    public static void in(Node head){
        processIn(head);
        System.out.println();
    }
    private static void processIn(Node head){
        if (head==null) return;
        processIn(head.left);
        System.out.print(head.value+" ");
        processIn(head.right);
    }

    /**
     * morris实现后序打印.
     * 第二次来到节点时,逆序打印当前节点左子树右边界.
     * 结束后打印整棵树的右边界.
     */
    public static void morrisPost2(Node head){
        if(head == null) return;
        Node cur = head;
        Node mostRight = null;
        while (cur != null){
            if(cur.left != null){
                mostRight = cur.left;
                while (mostRight.right!=null && mostRight.right!=cur){
                    mostRight = mostRight.right;
                }
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                    reversePrintRight(cur.left);
                }
            }
            cur = cur.right;
        }
        reversePrintRight(head);
        System.out.println();
    }

    /**
     * morris实现后序遍历:
     * 第二次来到节点,逆序打印节点左树右边界
     *      只有一种情况:
     *          1. 有左树,且mr.right等于cur
     * 逆序打印整颗树的右边界.
     */
    public static void morrisPost(Node head){
        Node cur = head;
        Node mr = null;
        while (cur != null){
            if (cur.left != null){
                mr = cur.left;
                while (mr.right != null && mr.right != cur){
                    mr = mr.right;
                }
                if(mr.right == null){
                    mr.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mr.right = null;
                    reversePrintRight(cur.left);
                }
            }
            cur = cur.right;
        }
        reversePrintRight(head);
        System.out.println();
    }

    public static void post(Node head){
        processPost(head);
        System.out.println();
    }
    private static void processPost(Node head){
        if(head == null) return;
        processPost(head.left);
        processPost(head.right);
        System.out.print(head.value+" ");
    }

    /**
     * morris遍历是到达当前节点后先遍历左子树,借助左子树的最右节点的右指针回到当前节点后,再遍历右子树.
     * 所以遍历顺序是:头,左,头,右.
     * 而递归序的遍历顺序是:头,左,头,右,头.
     * 递归序可以很自然的改写出先序,中序,后序.而morris改后序比较麻烦.
     * 能不能既保留morris遍历的有点(空间复杂度O(1)),又可以实现:头,左,头,右,头的遍历顺序.
     *
     * 思路:
     *      来到当前节点.
     *      找到当前节点左树最右节点mostRight和右树最左节点mostLeft.
     *      如果mostRight为空 且 mostLeft为空,说明无子树:
     *          返回;
     *      如果mostRight.right和mostLeft.left都指向null或者自身为空.说明第一次来到当前节点:
     *          如果mostRight不为空:
     *              将mostRight.right指向当前节点.当前节点来到左子树位置.
     *          否则:
     *              当前节点来到右子树.
     *      如果mostRight.right指向当前节点,mostLeft.left指向null或自身为空.说明第二次来到当前节点:
     *          将mostRight.right指向null,
     *          如果mostLeft不为空:
     *              mostLeft.left指向当前节点,
     *          当前节点来到右子树.
     *      如果mostRight.right指向null或自身为空,mostLeft.left指向当前节点,说明第三次来到当前节点:
     *          如果most
     *
     * 思路错误!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *      同时使用左子树的最节点右指针和右子树最左节点左指针,会引起混乱.
     *
     */
    public static void zsMorris(Node head){
        if(head == null) return;

    }

    /**
     * 反转二叉树右边界链表后逆序打印.
     */
    private static void reversePrintRight(Node head) {
        Node pre = null;
        Node cur = head;
        Node next = head;
        while (cur != null){
            next = cur.right;
            cur.right = pre;
            pre = cur;
            cur = next;
        }
        cur = pre;
        pre = null;
        next = cur;
        while (cur != null){
            System.out.print(cur.value+" ");
            next = cur.right;
            cur.right = pre;
            pre = cur;
            cur = next;
        }
    }

    // for test -- print tree
    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    public static boolean isBST(Node head) {
        if (head == null) {
            return true;
        }
        Node cur = head;
        Node mostRight = null;
        Integer pre = null;
        boolean ans = true;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            if (pre != null && pre >= cur.value) {
                ans = false;
            }
            pre = cur.value;
            cur = cur.right;
        }
        return ans;
    }

    public static void main(String[] args) {
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(7);
//        printTree(head);
        System.out.println("先序: ");
        pre(head);
        System.out.println("morris先序: ");
        morrisPre(head);

        System.out.println("中序: ");
        in(head);
        System.out.println("morris中序: ");
        morrisIn(head);

        System.out.println("后序: ");
        post(head);
        System.out.println("morris后序: ");
        morrisPost(head);
        printTree(head);

        morris(head);
        morris2(head);

    }
}
