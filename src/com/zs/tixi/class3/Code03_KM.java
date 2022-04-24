package com.zs.tixi.class3;

import com.zs.xiaobai.common.MyCompValue;

/**
 * 6 一个数组中有一种数出现K次，其他数都出现了M次，
 * 已知M > 1，K < M，找到出现了K次的数
 * 要求额外空间复杂度O(1)，时间复杂度O(N)
 */
public class Code03_KM {
    /**
     * 一个数组中有一种数出现了K次，其它数都出现了M次，M>1,K<M。怎么找到并打印出现K次的数，要求空间复杂度O(1)，时间复杂度O(N)
     */
    public static int printKTimesNum(PrintKTimesNumParam param){
        int[] arr = param.arr;
        int k = param.k;
        int m = param.m;
        // 数组中所有数按位相加。
        int[] sumBits = new int[32];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < 32; j++) {
                sumBits[j] += (arr[i] & (1 << 31-j))==0 ? 0 : 1;
            }
        }

        // 每个二进制位对m取余是否为0，如果为0，说明此位置上，k次数为0.
        int[] ansBits = new int[32];
        for (int i = 0; i < sumBits.length; i++) {
            if (sumBits[i]%m == 0) {
                ansBits[i] = 0;
            } else {
                ansBits[i] = 1;
            }
        }

        int kNum = MyCompValue.transBitArrToInt(ansBits);
        return kNum;
    }

    static class PrintKTimesNumParam{
        public int[] arr;
        public int k;
        public int m;
        public PrintKTimesNumParam(int [] arr, int k, int m){
            this.arr = arr;
            this.k = k;
            this.m = m;
        }
    }
}
