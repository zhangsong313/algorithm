package com.zs.tixi.class18;

/*
 *      1. 打印n层汉诺塔从最左边移动到最右边的全部过程。
 *      汉诺塔问题每次向下派发两条子任务，所以是2^n-1次步骤
 */
public class Code02_Hanoi {
    public static int hanoi(int n){
        if (n<=0) return 0;
        return process("left", "right", "mid", n);
    }

    /**
     * 把n层汉诺塔从from位置借助other位置移动到to位置。
     * 如果n==1，直接把1层的汉诺塔从from移动到to。返回。
     * 先递归调用把n-1层汉诺塔从from移动到other。
     * 然后把第n层的汉诺塔从from移动到to。
     * 然后递归调用把n-1层的汉诺塔从other移动到to。
     */
    private static int process(String from, String to, String other, int n) {
        if (n==1){
//            System.out.println(String.format("move %s from %s to %s", n, from, to));
            return 1;
        }
        int step = 0;
        step+=process(from, other, to, n-1);
//        System.out.println(String.format("move %s from %s to %s", n, from, to));
        step++;
        step+=process(other, to, from, n-1);
        return step;
    }

    public static void main(String[] args) {
        System.out.println(hanoi(15));
    }
}
