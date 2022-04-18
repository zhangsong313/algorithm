package com.zs.shuati.class04;

/**
 * 6.生成长度为size的达标数组，什么叫达标？对于任意的i<k<j，满足[i]+[j]!=[k]*2。给定一个正数size，返回长度为size的达标数组
 */
public class Code06_MakeNo {

    /**
     * 思路：
     * 如果[a, b, c]符合要求
     * 那么[2a, 2b, 2c, 2a+1, 2b+1, 2c+1]也符合要求.
     * 原因：左半部分符合要求，右半部分符合要求。左边为偶数，右边为奇数，奇数和偶数和必为奇数，那么从左右两边混合选择也符合要求。
     * 提供一个种子数组:{1}
     * 种子数组不断扩张：
     * {2, 3}
     * {4, 6, 5, 7},
     * {8, 12, 10, 14, 9, 13, 11, 15}
     * ...
     * 超过规定大小后，裁剪数组到指定大小即可。
     */
    public static int[] makeNo(int size){
        if (size == 0) return new int[0];
        int[] seed = {1};
        int[] arr = seed;
        while (arr.length < size){
            arr = twiceArr(arr);
        }
        int[] ans = arr;
        if(arr.length>size){
            ans = new int[size];
            for (int i = 0; i < size; i++) {
                ans[i] = arr[i];
            }
        }
        return ans;
    }

    /**
     * 将数组扩张为两倍长度。
     * 左半部分为2倍值，右半部分为2倍加1.
     */
    private static int[] twiceArr(int[] arr) {
        int N = arr.length;
        int[] twice = new int[N*2];
        for (int i = 0; i < N; i++) {
            twice[i] = arr[i]*2;
            twice[i+N] = arr[i]*2+1;
        }
        return twice;
    }

    // 检验函数
    public static boolean isValid(int[] arr) {
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            for (int k = i + 1; k < N; k++) {
                for (int j = k + 1; j < N; j++) {
                    if (arr[i] + arr[j] == 2 * arr[k]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int N = 1; N < 1000; N++) {
            int[] arr = makeNo(N);
            if (!isValid(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
        System.out.println(isValid(makeNo(1042)));
        System.out.println(isValid(makeNo(2981)));
    }
}
