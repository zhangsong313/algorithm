package com.zs.tixi.class3;

/**
 * 3 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
 *
 * 4 怎么把一个int类型的数，提取出二进制中最右侧的1来
 *
 * 5 一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
 */
public class Code02_EvenTimesOddTimes {
    /**
     * 一个数组中有一个数出现了奇数次，其它数都出现了偶数次，怎么找到并打印这个数
     * 数组中所有数字异或的结果。同一数偶数次异或会抵消为0。奇数次异或为自身。
     */
    public static int printOddTimesNum(int arr[]){
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            ans ^= arr[i];
        }
        return ans;
    }

    /**
     * 如何把int二进制上最右侧的1提取出来。
     * a & ((~ a)+1)
     */
    public static int getRight1(int a){
        return a & (-a);
    }


    /**
     * 一个数组中有两个数出现了奇数次，其它数都出现了偶数次，怎么找到并打印这个数
     */
    public static int[] printTwoOddTimesNum(int arr[]){
        // 得到a^b的值.
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            ans ^=arr[i];
        }

        // 找到a^b的最右侧1对应的数。
        int right1 = getRight1(ans);

        // 遍历arr，只与right1位置不为1的数进行异或运算。得到a的值。
        int ans2 = ans;
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & right1) == 0){
                ans2 ^=arr[i];
            }
        }

        int[] ansArr = new int[2];
        ansArr[0] = ans2;
        ansArr[1] = ans2 ^ ans; //ans与a异或，得到b的值。
        return ansArr;
    }
}
