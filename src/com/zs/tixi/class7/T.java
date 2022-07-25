package com.zs.tixi.class7;

import com.zs.xiaobai.common.MyCompValue;

import java.util.Arrays;

/**
 * 1.比较器
 * 2.堆
 * 3.堆排序
 * 4.O(N)复杂度建堆
 * 5.arr数组无序,但每个数距离有序后位置不超过K,排序使得数组有序,复杂度小于O(N*logN)
 *
 */
public class T {
    /*
        笔记整理：
            1. 堆：
                概念：
                    （1）是一颗完全二叉树
                    （2）对于大根堆：父节点总是大于等于两个子节点。对于小根堆：父节点总是小于等于两个子节点。
                实现：
                    一般用数组代替二叉树实现堆数据的存储。
                    父子节点关系：
                        当前节点下标：index
                        当前节点的父节点: (index -1) / 2
                        当前节点的左子节点 : index * 2 + 1
                    成员变量：
                        定义int[]保存数据， size表示当前大小， capacity表示容量大小
                    构造方法：
                        指定capacity方法
                    void add(int e);
                        // 新加入的数据放入size位置。
                        // 对size位置执行heapInsert，尝试将size位置的数据向堆顶移动。size++
                        // 处理size达到容量上限的情况。
                    int poll();
                        // 取0位置的值作为返回值。
                        // 交换size-1位置和0位置的值， size--
                        // 对0位置调用heapify，尝试将0位置的值向堆底移动。
                    int peek();
                        // 返回0位置的值，处理size==0的情况。
                    int size();
                    private void heapInsert(int[] elementData, int index)
                        // 循环：如果当前节点值小于父节点，交换当前节点和父节点，当前节点来到父节点位置。
                    private void heapify(int[] elementData, int index, int size)
                        // 定义left为左子节点。
                        // 循环：left小于size，找到两个子节点的较小节点与当前节点比较。
                        // 当前节点小于等于较小节点，返回。
                        // 否则，交换当前节点和较小节点。
                        // 更新当前节点为较小节点，更新左子节点的位置。
            2.堆排序
                // 根据数组创建大根堆：从后向前初始化使用heapify，或从前向后初始化使用heapInsert。
                // 定义堆大小heapSize。
                // 循环：每次将0位置与heapSize位置交换，heapSize--
                // 对0位置执行heapify。
            3.距离小于K的排序
                题目：arr数组无序,但每个数距离有序后位置不超过K,排序使得数组有序,复杂度小于O(N*logN)
                思路：排序后第0位置的数，必定是[0, K]范围内的最小值。因此第0位置的数必定是[0, K]返回内组成的小根堆的堆顶值。
                // 小根堆弹出堆顶值放入0位置，然后小根堆加入第K+1位置的数，弹出堆顶数放在1位置，依次类推，直到每个位置都填好。
                // 小根堆规模为K，每次调整的代价为O（K），总调整代价为O(N*logK)
                实现：
                // 如果K<=0，说明已经是arr已经是有序的，直接返回。
                // 如果K>=arr.length，说明数组是完全无序的，直接调用排序算法排序即可。
                // 将前K个数加入小根堆。
                // 循环：index=[K+1, arr.length-1]，小根堆每次弹出堆顶值放入第i位置（i从0开始每次加1），将index位置的数加入小根堆。
                // 将堆中剩下的数依次弹出放入arr的i位置中，每次i++。
             4. 最大线段重合问题（用堆实现）:
                给定很多线段，每个线段用两个数[start, end]表达开始位置和结束位置.
                1) 线段开始和结束位置是整数
                2) 线段重合区域必须大于等于1.
                要求返回线段重合区域中，最多包含了几条线段
                思路：
                // 先将线段集合排序，排序规则：按start排序。
                // 创建集合set，每次加入一个线段。
                // 将集合中end小于等于加入线段start的线段全部删除。集合内的线段数量为此时的重合线段数。
                // 由于每次需要遍历集合内的线段，因此时间复杂度为O(N^2)
                // 使用堆替代集合set，每次从堆顶弹出end小于等于新加入的start的线段，直到没有符合条件的位置。
                // 堆总计弹出N次，每次代价为logN
                // 因此总的时间复杂度为O(N*logN)

     */
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
    public static interface TwoArgsNoReturn<A1, A2>{
        void apply(A1 a1, A2 a2);
    }

    public static void checkSortArrDistanceLessK(int times, int arrMaxLength, int arrMaxVal, TwoArgsNoReturn<int[], Integer> fun){
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

            fun.apply(arr, K);

            if (MyCompValue.isSorted(lessKArr.arr) == false){

                MyCompValue.printArr(clone);
                System.out.println("K : "+K);
                MyCompValue.printArr(lessKArr.arr);
                MyCompValue.printErr("checkSortArrDistanceLessK 排序错误");
            }
        });
    }
}
