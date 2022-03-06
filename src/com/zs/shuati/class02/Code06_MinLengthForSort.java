package com.zs.shuati.class02;

/**
 * 给定一个数组arr，只能对arr中的一个子数组排序，但是想让arr整体都有序，返回满足这一设定的子数组中最短的是多长
 */
public class Code06_MinLengthForSort {
    /**
     * 从左到右遍历数组.
     * 记录历史最大值.
     * 每次判断当前位置数是否小于历史最大值,如果小于,标记为false,否则标记为true.
     * 记录下最后连续为true的左边界位置.此位置向后的所有数据都是排序后应该待的位置.
     *
     * 记录历史最小值.
     * 从右向左遍历数据,每次判断当前数是否大于历史最小值.如大于,标记为false,否则标记为true.
     * 记录下最后连续标记为true的右边界位置,此位置向后的所有数据都是排序后应该待的位置.
     *
     * 两位置之间的范围就是最小排序子数组的范围.
     */
    public static int findUnsortedSubarray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int rightTrue = nums.length;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i]>=max){
                max = nums[i];
                if(rightTrue==nums.length){
                    rightTrue = i;
                }
            }else {
                rightTrue = nums.length;
            }
        }

        int min = Integer.MAX_VALUE;
        int leftTrue = -1;
        for (int i = nums.length-1; i >=0 ; i--) {
            if (nums[i]<=min){
                min = nums[i];
                if(leftTrue==-1){
                    leftTrue = i;
                }
            }else {
                leftTrue=-1;
            }
        }
//        System.out.println(rightTrue);
//        System.out.println(leftTrue);
        return rightTrue<=leftTrue?0:rightTrue-leftTrue-1;
    }

    public static void main(String[] args) {
        findUnsortedSubarray(new int[]{2,6,4,8,10,9,15});
    }
}
