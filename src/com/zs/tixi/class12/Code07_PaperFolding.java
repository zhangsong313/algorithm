package com.zs.tixi.class12;
/*
 * 7. 请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开。
 *      此时折痕是凹下去的，即折痕突起的方向指向纸条的背面。如果从纸条的下边向上方连续对折两次，
 *      压出折痕后展开，此时有三条折痕，从上到下依次是凹折痕，凹折痕，凸折痕。
 *      给定一个输入参数N，代表纸条都从下边向上方连续对折N次。请从上到下打印所有折痕的方向。
 *      例如：N=1时，打印 “凹” . N=2时，打印 “凹”， “凹”， “凸”。
 *
 *      纸条折痕实际上就是一颗以第一次的中间折痕为头节点的二叉树。
 *      “凹”和“凸”可以看作二叉树节点的两种值。
 *      每个节点的左节点是“凹”，右节点是“凸”。
 *      只要指定了折叠的次数，也就指定了二叉树的层数。执行一次先序遍历打印节点值就可以了。
 */
public class Code07_PaperFolding {

    /**
     * N为折叠的次数。
     * down为true表示当前折痕为凹折痕。否则为凸折痕。
     * @param N
     * @param down
     */
    private static void printAllFold(int N, boolean down) {
        if(N==0) return;
        printAllFold(N-1, true);
        System.out.print(down?"凹 ":"凸 ");
        printAllFold(N-1, false);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            printAllFold(i, true);
            System.out.println();
        }

    }
}
