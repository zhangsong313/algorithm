package com.zs.xiaobai.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 对数器
 */
public class MyCompValue {
    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            int num = (int) Math.random()*10000;
            int[] ints = transIntToBitArr(num);
            int num2 = transBitArrToInt(ints);
            if (num != num2) printErr(num, num2);
        }
    }

    public static void printUseTime(Runnable run){
        long start = System.currentTimeMillis();
        run.run();
        long end = System.currentTimeMillis();
        System.out.println(end-start + "ms");
    }

    public static void times(int times, Runnable run){
        for (int i = 0; i < times; i++) {
            if (i%(times/10) == 0 ) System.out.println("已测试完成【"+i+"】次");
            run.run();
        }
        System.out.println("已测试完成【"+times+"】次");
    }

    public static int[] randomValueArr(int length, int maxValue){
        int l = length;
        int[] arr = new int[l];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*(maxValue+1));
        }
        return arr;
    }

    public static int[] randomIntArr(int maxLength, int maxValue){
        int l = (int)(Math.random()*(maxLength+1));
        int[] arr = new int[l];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*(2*maxValue+1))-maxValue;
        }
        return arr;
    }

    public static int[] randomPositiveIntArr(int maxLength, int maxValue){
        int l = (int)(Math.random()*(maxLength+1));
        int[] arr = new int[l];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*(maxValue+1));
        }
        return arr;
    }

    public static void printArr(int[] arr){
        System.out.print("[");
        for (int i : arr){
            System.out.print(i + " ");
        }
        System.out.print("]");
        System.out.println();
    }

    public static void printErr(Object... arr){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(String.valueOf(arr[i]));
        }
        String join = String.join(", ", list);
        throw new RuntimeException("error : "+join);
    }

    public static String toStr(int... arr){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i : arr){
            sb.append(i + " ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void swap(int[]arr, int i, int j){
        if (i == j) return;
        arr[i] = arr[i]^arr[j];
        arr[j] = arr[i]^arr[j];
        arr[i] = arr[i]^arr[j];
    }





    public static void shuffle(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            int index = (int) (Math.random()*arr.length);
            swap(arr, i, index);
        }
    }

    // 检查排序
    public static void checkSort(int times , int arrMaxLength, int arrMaxValue, Consumer<int[]> sortFun){
        for (int i = 0; i < times; i++) {
            if (i%(times/10) == 0 ) System.out.println("已测试完成【"+i+"】次");
            int[] arr = MyCompValue.randomIntArr(arrMaxLength, arrMaxValue);
            int[] copyArr = Arrays.copyOf(arr, arr.length);
            sortFun.accept(arr);
            if (MyCompValue.isSorted(arr) == false){
                System.out.println("排序有误");
                printArr(copyArr);
                return;
            }
        }
        System.out.println("已测试完成【"+times+"】次");
    }

    // 检查有序数组二分查找是否正确。
    public static void checkFind(int times , int arrMaxLength, int arrMaxValue, BiFunction<int[], Integer, Integer> sortFun){
        for (int i = 0; i < times; i++) {
            if (i%(times/10) == 0 ) System.out.println("已测试完成【"+i+"】次");
            int[] arr = MyCompValue.randomIntArr(arrMaxLength, arrMaxValue);
            selectSort(arr);
            int num = (int)(Math.random()*arrMaxValue+1);
            int index = sortFun.apply(arr, num);

            if (index == -1){
                if (arr != null && arr.length!=0){
                    if (isExist(arr, num)){
                        System.out.println("二分查找有误");
                        printArr(arr);
                        System.out.println(" num :" + num);
                        System.out.println(" index :" + index);
                        return;
                    }
                }
            } else if (index>arr.length-1){
                System.out.println("二分查找有误");
                printArr(arr);
                System.out.println(" num :" + num);
                System.out.println(" index :" + index);
            } else if (arr[index] != num){
                System.out.println("二分查找有误");
                printArr(arr);
                System.out.println(" num :" + num);
                System.out.println(" index :" + index);
                return;
            }
        }
        System.out.println("已测试完成【"+times+"】次");
    }

    // 有序数组找到大于等于num的最左边的位置。
    public static void checkFindLeftestLargerIndex(int times , int arrMaxLength, int arrMaxValue, BiFunction<int[], Integer, Integer> sortFun){
        for (int i = 0; i < times; i++) {
            if (i%(times/10) == 0 ) System.out.println("已测试完成【"+i+"】次");
            int[] arr = MyCompValue.randomIntArr(arrMaxLength, arrMaxValue);
            selectSort(arr);
            int num = (int)(Math.random()*arrMaxValue+1);
            int index = sortFun.apply(arr, num);

            int ans  =isFirstLargerOrEquals(arr, num);
            if(ans != index){
                System.out.println("有序数组找到大于等于num的最左边的位置有误");
                printArr(arr);
                System.out.println(" num :" + num);
                System.out.println(" index :" + index);
                System.out.println("答案 : "+ans);
                return;
            }
        }
        System.out.println("已测试完成【"+times+"】次");
    }

    // 无序数组，且相邻数不同。找出一个局部最小值。
    public static void checkFindAreaMostSmall(int times , int arrMaxLength, int arrMaxValue, Function<int[], Integer> sortFun){
        for (int i = 0; i < times; i++) {
            if (i%(times/10) == 0 ) System.out.println("已测试完成【"+i+"】次");
            int[] arr = MyCompValue.randomNotEqualLastIntArr(arrMaxLength, arrMaxValue);
            int index = sortFun.apply(arr);

            if(isMostSmall(arr, index) == false){
                System.out.println("无序数组，且相邻数不同。找出一个局部最小值 有误");
                printArr(arr);
                System.out.println(" index :" + index);
                return;
            }
        }
        System.out.println("已测试完成【"+times+"】次");
    }

    private static int[] randomNotEqualLastIntArr(int maxLength, int maxValue){
        int l = (int)(Math.random()*maxLength);
        int [] arr = new int[l];
        if(l==0)return arr;

        arr[0] = (int)(Math.random()*maxValue);
        for (int i = 1; i < arr.length; i++) {
            int num = (int)(Math.random()*maxValue);
            while (num==arr[i-1]){
                num = (int)(Math.random()*maxValue);
            }
            arr[i] = num;
        }
        return arr;
    }



    public static boolean isSorted(int[] arr){
        if(arr == null || arr.length<2){
            return true;
        }
        for (int i = 1; i < arr.length; i++) {
            if (arr[i-1]>arr[i]){
                return false;
            }
        }
        return true;
    }
    private static boolean isMostSmall(int[] arr, int index){
        if (arr == null || arr.length==0){
            return index==-1;
        }

        boolean lefSmall = index-1>=0?arr[index]<arr[index-1]:true;
        boolean rightSmall = index+1<arr.length?arr[index]<arr[index+1]:true;
        return lefSmall&&rightSmall;
    }
    public static int isFirstLargerOrEquals(int[] arr, int num){
        if (arr == null || arr.length==0 || arr[arr.length-1] < num){
            return -1;
        }
        for (int i = 0; i<arr.length; i++){
            if(arr[i]>=num){
                return i;
            }
        }
        printArr(arr);
        System.out.println(" num :" + num);
        throw new RuntimeException("isFirstLargerOrEquals");
    }



    private static boolean isExist(int[] arr, int num){
        for (int i : arr ){
           if (i==num){
               return true;
           }
        }
        return false;
    }


    // 正确的选择排序。
    private static void selectSort(int[] arr){
        if(arr == null || arr.length<2){
            return;
        }


        for (int i = 0; i < arr.length; i++) {

            int mixIndex = i;
            for (int j = i+1; j < arr.length; j++) {
                mixIndex = arr[j]<arr[mixIndex]? j : mixIndex;
            }

            swap(arr, mixIndex, i);
        }


    }
//    private static void swap(int[]arr, int i, int j){
//        int tmp = arr[j];
//        arr[j] = arr[i];
//        arr[i] = tmp;
//    }

    public static int[] transIntToBitArr(int num){
        int[] arr = new int[32];
        for (int i = 31; i >=0; i--) {
            arr[i] = (num & (1 << i))==0 ? 0 : 1;
        }
        return arr;
    }

    public static int transBitArrToInt(int[] arr){
        int num = 0;
        for (int i = 31; i >=0; i--) {
            if(arr[i] == 1)
                num += 1<<(31-i);
        }
        return num;
    }


}
