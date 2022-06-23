package com.zs.tixi.class3;

import com.zs.xiaobai.common.MyCompValue;

import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * 1 不用额外变量交换两个数的值
 *
 * 2 不用额外变量交换数组中两个数的值
 */
public class Code01_Swap {

    /**
     * 不用额外变量交换两个数的值
     */
    public static int[] swapNum(int a, int b){
        a = a^b;
        b = a^b;
        a = a^b;
        return new int[]{a, b};
    }

    /**
     * 不用额外变量交换数组中两个数的值
     */
    public static void swap(int[]arr, int i, int j){
        if (i == j) return; // 注意位置相同时直接返回。
        arr[i] = arr[i]^arr[j];
        arr[j] = arr[i]^arr[j];
        arr[i] = arr[i]^arr[j];
    }

    public static void main(String[] args) {
        checkSwapNum(1000, 999, Code01_Swap::swapNum);
        checkSwapArrIndex(1000, 999, Code01_Swap::swap);
    }

    private static void checkSwapNum(int times, int maxVal, BiFunction<Integer, Integer, int[]> fun){
        MyCompValue.times(times, ()->{
            int a = (int)(Math.random()*maxVal)+1;
            int b = (int)(Math.random()*maxVal)+1;
            int[] ans = fun.apply(a, b);
            if(ans[0]!=b || ans[1]!=a){
                System.out.println("test error:");
                MyCompValue.printArr(ans);
                throw new RuntimeException();
            }
        });
    }
    private static interface ThreeArgFun<T1, T2, T3>{
        void apply(T1 t1, T2 t2, T3 t3);
    }

    private static void checkSwapArrIndex(int times, int maxVal, ThreeArgFun<int[], Integer, Integer> threeArgFun){
        MyCompValue.times(times, ()->{
            int[] arr = null;
            do{
                arr = MyCompValue.randomIntArr(100, maxVal);
            }while (arr.length==0);
            int N = arr.length;
            int i = (int)(Math.random()*N);
            int j = (int)(Math.random()*N);
            int a = arr[i];
            int b = arr[j];
            threeArgFun.apply(arr, i, j);
            if(arr[i]!=b || arr[j] != a){
                MyCompValue.printErr("test error: ", Arrays.asList(arr), i, j, a, b);
            }
        });
    }
}
