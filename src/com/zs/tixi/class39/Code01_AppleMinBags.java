package com.zs.tixi.class39;

/**
 * 小虎去买苹果，商店只提供两种类型的塑料袋，每种类型都有任意数量
 * 1）能装下6个苹果的袋子
 * 2）能装下8个苹果的袋子
 * 小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，他要求自己使用的袋子数量必须最少，
 * 且使用的每个袋子必须装满，给定一个正整数N，返回至少使用多少袋子。如果N无法让使用的每个袋子必须装满，返回-1
 */
public class Code01_AppleMinBags {
    /**
     * 暴力模拟
     * 尝试全部用8号贷装。
     * 循环：如果不成功（余数不为0）
     *      8号袋减一，剩下的尝试全部由六号袋子装。
     *      求出余数。
     */
    public static int minBags(int apple) {
        int rest = apple%8; // 使用指定的袋子最终剩余多少苹果无法装下。
        int bag8 = apple/8; // 8号袋子数量
        while (bag8>=0){
            if (rest%6==0){
                return bag8+(rest/6);
            }else {
                bag8--;
                rest+=8;
            }
        }
        return -1;
    }

    /**
     * 规律表达
     * 0返回0
     * 奇数返回-1
     * 小于18:6,8返回1，12，14，16返回2.
     * 减去18 / 8 +3
     */
    public static int minBagAwesome(int apple) {
        if(apple==0) return 0;
        if((apple & 1) !=0 ) return -1;
        if (apple<18){
            return (apple==6||apple==8)?1:
                    (apple==12||apple==14||apple==16)?2
                            :-1;
        }
        return ((apple-18)>>3) +3;
    }

    public static void main(String[] args) {
        for(int apple = 0; apple < 200;apple++) {
            System.out.println(apple + " : "+ minBags(apple));
        }
        for(int apple = 0; apple < 10000;apple++) {
            if(minBags(apple) != minBagAwesome(apple)) throw new RuntimeException("错误");
        }
        System.out.println("测试成功");

    }
}
