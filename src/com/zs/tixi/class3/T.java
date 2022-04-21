package com.zs.tixi.class3;

import com.zs.tixi.util.RandomUtil;
import com.zs.xiaobai.common.MyCompValue;

import java.util.*;
import java.util.function.Function;

/**

 *
 * 内容：
 *
 * 异或运算的性质
 * 异或运算：^
 * 无尽为相加，交换律，结合律。完成两变量交换。
 *
 * 异或运算的题目
 *
 * 题目：
 *
 * 1 不用额外变量交换两个数的值
 *
 * 2 不用额外变量交换数组中两个数的值
 *
 * 3 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
 *
 * 4 怎么把一个int类型的数，提取出二进制中最右侧的1来
 *
 * 5 一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
 *
 * 6 一个数组中有一种数出现K次，其他数都出现了M次，
 * 已知M > 1，K < M，找到出现了K次的数
 * 要求额外空间复杂度O(1)，时间复杂度O(N)
 */
public class T {

    /**
     *
     * @param arr
     * @param i
     * @param j
     */
    public void swap(int[]arr, int i, int j){
        if (i == j) return;
        arr[i] = arr[i]^arr[j];
        arr[j] = arr[i]^arr[j];
        arr[i] = arr[i]^arr[j];
    }

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

    public static void main(String[] args) {
//        checkPrintOddTimesNum(10000, 88, T::printOddTimesNum);
//        checkPrintTwoOddTimesNum(10000, 5, 5,  T::printTwoOddTimesNum);
        checkPrintKTimesNum(10000, 5, 5, T::printKTimesNum);
    }

    public static void checkPrintKTimesNum(int times, int numKinds, int occurTimes, Function<PrintKTimesNumParam, Integer> fun){
        MyCompValue.times(times, ()->{
            List<Integer> list = new ArrayList<>(); // 测试数组

            // 放入k次数字。
            int k = Math.abs(RandomUtil.getNum(occurTimes, 0));
            int a = RandomUtil.getNum(100);
            for (int i = 0; i < k; i++) {
                list.add(a);
            }

            // 放入m次数字。
            int m = k + Math.abs(RandomUtil.getNum(occurTimes, 0)); // 比k大一个随机正数。
            for (int i = 0; i < numKinds - 1;) {
                int b = RandomUtil.getNum(100, a);
                if(list.contains(b)) continue; // 如果已存在，重新循环一次，i不更新。
                for (int j = 0; j < m; j++) {
                    list.add(b);
                }
                i++;
            }

            // list转数组
            int[] arr = new int[list.size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = list.get(i);
            }

            // 洗牌
            MyCompValue.shuffle(arr);

            PrintKTimesNumParam param = new PrintKTimesNumParam(arr, k, m);
            // 校验结果。
            int ans = fun.apply(param);
            // 对数结果。
            int ansTimes = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == ans) ansTimes++;
            }

            // 判断生成的数组是否符合一个k次，若干个m次。
            Map<Integer, Integer> timesMap = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                if (timesMap.containsKey(arr[i])){
                    timesMap.put(arr[i], timesMap.get(arr[i])+1);
                }else {
                    timesMap.put(arr[i], 1);
                }
            }
            Iterator<Integer> it = timesMap.keySet().iterator();
            int kTimes=0;
            while (it.hasNext()){
                Integer kmTimes = timesMap.get(it.next());
                if(kmTimes != k && kmTimes != m) MyCompValue.printErr(k, m);
                if (kmTimes == k) kTimes++;
            }
            if (kTimes!=1) MyCompValue.printErr(kTimes);

            if(ansTimes != k) {
                MyCompValue.printArr(arr);
                MyCompValue.printErr(k, m, ans);
            }

        });
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

    public static void checkPrintTwoOddTimesNum(int times, int numKinds,int occurTimes,  Function<int[], int[]> fun){
        MyCompValue.times(times, ()->{
            List<Integer> list = new ArrayList<>(); // 测试数组

            // 放入奇数次数字。
            int oddTimes = Math.abs(RandomUtil.getOdd(occurTimes));// 奇数次数.
            int a = RandomUtil.getNum(100);
            for (int i = 0; i < oddTimes; i++) {
                list.add(a);
            }

            // 放入奇数次数字。
            int oddTimes2 = Math.abs(RandomUtil.getOdd(occurTimes));// 奇数次数.
            int a2 = RandomUtil.getNum(100, a);
            for (int i = 0; i < oddTimes2; i++) {
                list.add(a2);
            }

            // 放入偶数次数字.
            for (int i = 0; i < numKinds - 2; i++) {
                int evenTimes = Math.abs(RandomUtil.getEven(occurTimes));//偶数次数
                int b = RandomUtil.getNum(100, a, a2);
                for (int j = 0; j < evenTimes; j++) {
                    list.add(b);
                }
            }

            // list转数组
            int[] arr = new int[list.size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = list.get(i);
            }

            // 洗牌
            MyCompValue.shuffle(arr);

            // 校验结果。
            int[] ans = fun.apply(arr);
            // 对数结果。
            int ansTimes = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == ans[0]) ansTimes++;
            }
            int ansTimes2 = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == ans[1]) ansTimes2++;
            }


            if (ansTimes%2 ==0 || ansTimes2%2 ==0) {
                MyCompValue.printArr(arr);
                MyCompValue.printErr(ans[0], ans[1], oddTimes, oddTimes2);
            }

        });
    }


    /**
     * printOddTimesNum对数器
     * @param times 测试次数
     * @param numKinds 最大多少种数
     * @param fun 测试函数
     */
    public static void checkPrintOddTimesNum(int times, int numKinds, Function<int[], Integer> fun){
        MyCompValue.times(times, ()->{
            List<Integer> list = new ArrayList<>(); // 测试数组

            // 放入奇数次数字。
            int oddTimes = Math.abs(RandomUtil.getOdd(100));// 奇数次数.
            int a = RandomUtil.getNum(100);
            for (int i = 0; i < oddTimes; i++) {
                list.add(a);
            }

            // 放入偶数次数字.
            for (int i = 0; i < numKinds - 1; i++) {
                int evenTimes = Math.abs(RandomUtil.getEven(100));//偶数次数
                int b = RandomUtil.getNum(100);
                if(a==b) b++;
                for (int j = 0; j < evenTimes; j++) {
                    list.add(b);
                }
            }

            // list转数组
            int[] arr = new int[list.size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = list.get(i);
            }

            // 洗牌
            MyCompValue.shuffle(arr);

            // 校验结果。
            Integer ans = fun.apply(arr);
            // 对数结果。
            int ansTimes = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == ans) ansTimes++;
            }
            if (ansTimes%2 ==0 ) throw new RuntimeException("error : "+ans);
        });
    }
}



























