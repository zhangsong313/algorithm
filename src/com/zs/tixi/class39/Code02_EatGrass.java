package com.zs.tixi.class39;

/**
 * 给定一个正整数N，表示有N份青草统一堆放在仓库里，有一只牛和一只羊，牛先吃，羊后吃，它俩轮流吃草
 * 不管是牛还是羊，每一轮能吃的草量必须是：1，4，16，64…(4的某次方)
 * 谁最先把草吃完，谁获胜，假设牛和羊都绝顶聪明，都想赢，都会做出理性的决定。根据唯一的参数N，返回谁会赢
 */
public class Code02_EatGrass {

    /**
     * 先手先吃，
     * 从从4的0次方开始尝试吃，吃完后，递归让后手先吃。
     * 如果递归后后手获胜，返回“先手”。
     * 如果一直尝试到超过N先手都没有赢的机会，返回“后手”。
     */
    public static String eatGrass(int N){
        int eat = 1;
        while (N>=eat){
            if(eatGrass(N-eat).equals("后手")) return "先手";
            eat *= 4;
        }
        return "后手";
    }

    /**
     * 观察eatGrass运行规律可以得知，规律满足每5个数答案为“后 先 后 先 先”的规律。
     * 计算N%5
     * 0 : 后手
     * 1 : 先手
     * 2 ： 后手
     * 3 ：先手
     * 4 : 先手
     * @param N
     * @return
     */
    public static String eatGrass2(int N){
        return N%5==0||N%5==2?"后手":"先手";
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 50; i++) {
            System.out.println(i + " : " + eatGrass(i));
        }
        for (int i = 0; i < 50; i++) {
            System.out.println(i);
            if(!eatGrass(i).equals(eatGrass2(i))) throw new RuntimeException("测试失败");
        }
        System.out.println("测试成功");
    }
}
