package com.zs.tixi.util;

import java.util.Random;

/**
 * 与随机相关的工具类。
 */
public class RandomUtil {

    /**
     * 返回最大绝对值值为maxAbs的奇数。
     * @param maxAbs
     * @return
     */
    public static int getOdd(int maxAbs) {
        int num = getNum(maxAbs);
        if (num%2 == 0){ // 非奇数重新随机。
            return getOdd(maxAbs);
        }else {
            return num;
        }
    }


    public static void main(String[] args) {
//        for (int i = 0; i < 100; i++) {
//            int odd = getOdd(100);
//            if (Math.abs(odd)>100 || odd%2!=0){
//                throw new RuntimeException("asdf : "+odd);
//            }
//            System.out.println(odd);
//        }
    }

    /**
     * 返回最大绝对值值为maxAbs的偶数。
     * @param maxAbs
     * @return
     */
    public static int getEven(int maxAbs) {
        int num = getNum(maxAbs);
        if (num%2 != 0){ // 非偶数重新随机。
            return getEven(maxAbs);
        }else {
            return num;
        }
    }

    public static int getNum(int maxAbs) {
        int num = (int)(Math.random()*(maxAbs*2+1))+1; // 1~maxAbs+1
        num -= maxAbs+1; // -maxAbs~maxAbs
        return num;
    }

    public static int getNum(int max, int... excludeArr) {
        int num = getNum(max);
        for (int i = 0; i < excludeArr.length; i++) {
            if(num == excludeArr[i]) return getNum(max, excludeArr);
        }
        return num;
    }
}
