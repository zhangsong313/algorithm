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
 * 无进位相加，交换律，结合律。完成两变量交换。
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
     * 笔记整理：
     *  一、异或运算：
     *      定义：两个数按位异或，都是0或都是1的位置结果为0，一个是0一个是1的位置结果为1.
     *      实质是无进位相加。
     *      满足规律：
     *          交换律：a^b = b^a
     *          结合律: (a^b)^c = a^(b^c)
     *          如果a^b=c,则： c^a=b, c^b=a。可以通过0^1=1, 0^0=0, 1^1=0验证得到
     *          同一数偶数次异或会抵消为0。奇数次异或为自身。
     *
     *  一、不适用额外变量交换值
     *      1.利用异或运算。
     *          a = a^b; // a设置为异或的结果
     *          b = a^b; // b设置为a。
     *          a = a^b; // a设置为b。
     *      2. 如果是交换数组中两个位置的数，要注意判断两数的下标是否为同一位置，如果是则直接返回。
     *      3. 其实利用异或运算交换值的想法就是将a和b运算的中间值计算出来，
     *          这个中间值可以和a计算生成b，也可以和b计算生成a。比如：加法或者乘法也可以。
     *
     *  二、在数组中出现特定次数的数.
     *      1. 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
     *          数组中所有数字异或的结果就是出现了奇数次的数.
     *      4. 怎么把一个int类型的数，提取出二进制中最右侧的1来
     *          a & (~a +1)
     *          a & (-a)
     *      5. 一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
     *          思路：
     *          数组中所有数异或的结果为a^b的值.
     *          找到a^b的最右侧1对应的数。说明在这个位置，只有a或只有b的二进制位为1.
     *          遍历arr，只与right1位置不为1的数进行异或运算。得到a的值。
     *          a^b的值与a异或，得到b的值。
     *      6. 一个数组中有一种数出现K次，其他数都出现了M次，
     *          已知M > 1，K < M，找到出现了K次的数
     *          要求额外空间复杂度O(1)，时间复杂度O(N)
     *          思路：
     *              数组中所有数按位相加。
     *              每个二进制位对m取余是否为0，如果为0，说明此位置上，k次数为0.否则k次数的此位置为1
     *              统计得到k次数的二进制位数组。
     *              将二进制位数组转化为整数。
     */

    public static void main(String[] args) {
        // a^b=c
        // c^a=b
        // c^b=a
        // 0^1=1
        // 0^0=0
        // 1^1=0
        int a = (int)(Math.random()*999);
        int b = (int)(Math.random()*999);
        MyCompValue.printArr(MyCompValue.transIntToBitArr(a));
        MyCompValue.printArr(MyCompValue.transIntToBitArr(b));
        MyCompValue.printArr(MyCompValue.transIntToBitArr(a^b));


        checkPrintOddTimesNum(1000, 88, Code02_EvenTimesOddTimes::printOddTimesNum);
        checkPrintTwoOddTimesNum(1000, 5, 5,  Code02_EvenTimesOddTimes::printTwoOddTimesNum);
        checkPrintKTimesNum(1000, 5, 5, Code03_KM::printKTimesNum);

    }

    private static interface ThreeArgReturnFun<Arg1, Arg2, Arg3, R>{
        R apply(Arg1 arg1, Arg2 arg2, Arg3 arg3);
    }
    public static void checkPrintKTimesNum(int times, int numKinds, int occurTimes, ThreeArgReturnFun<int[], Integer, Integer, Integer> fun){
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

            // 校验结果。
            int ans = fun.apply(arr, k, m);
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



























