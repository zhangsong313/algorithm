package com.zs.tixi.class7;

import com.zs.xiaobai.common.MyCompValue;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * 1.比较器
 * 2.堆
 * 3.堆排序
 * 4.O(N)复杂度建堆
 * 5.arr数组无序,但每个数距离有序后位置不超过K,排序使得数组有序,复杂度小于O(N*logN)
 *
 */
public class T {
    public static void main(String[] args) {
//        MyCompValue.checkSort(10000, 100, 100, Code03_HeapSort::heapSort);
        checkSortArrDistanceLessK(10000, 100 ,100, Code04_SortArrayDistanceLessK::sortArrDistanceListK);
    }

    public static class LessKArr{
        public int[] arr;
        public int K;
        public LessKArr(int[] arr, int K){
            this.arr = arr;
            this.K = K;
        }
    }

    public static void checkSortArrDistanceLessK(int times, int arrMaxLength, int arrMaxVal, Consumer<LessKArr> run){
        MyCompValue.times(times, ()->{
            int[] arr = MyCompValue.randomIntArr(arrMaxLength, arrMaxVal);
//            System.out.println("原始数组 : "+MyCompValue.toStr(arr));
            Arrays.sort(arr);
//            System.out.println("排序后数组 : "+MyCompValue.toStr(arr));
            int K = (int)(Math.random()*(arr.length));
            boolean[] isSwapedArr = new boolean[arr.length];
            for (int i = 0; i < arr.length; i++) {
                int currK = (int)(Math.random()*(2*K+1)) - K;
                if (i+currK<arr.length && i+currK>=0 && isSwapedArr[i+currK] == false && isSwapedArr[i] == false){
                    MyCompValue.swap(arr, i, i+currK);
//                    System.out.println("交换 ： "+i+" "+(i+currK));
                    isSwapedArr[i]=true;
                    isSwapedArr[i+currK]=true;
                }
            }

            LessKArr lessKArr = new LessKArr(arr, K);
//            System.out.println("小于K乱序后数组 : "+MyCompValue.toStr(arr));
//            System.out.println("K : "+K);
            int[] clone = arr.clone();

            run.accept(lessKArr);

            if (MyCompValue.isSorted(lessKArr.arr) == false){

                MyCompValue.printArr(clone);
                System.out.println("K : "+K);
                MyCompValue.printArr(lessKArr.arr);
                MyCompValue.printErr("checkSortArrDistanceLessK 排序错误");
            }
        });
    }
}
