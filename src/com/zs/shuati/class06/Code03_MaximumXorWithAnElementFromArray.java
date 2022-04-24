package com.zs.shuati.class06;

/**
 * 3 给定一个非负整数组成的数组nums。另有一个查询数组queries，其中queries[i]=[xi, mi]
 * 第i个查询的答案是xi和任何nums数组中不超过mi的元素按位异或（XOR）得到的最大值
 * 换句话说，答案是max(nums[j] XOR xi)，其中所有j均满足nums[j]<= mi。如果nums中的所有元素都大于mi，最终答案就是-1
 * 返回一个整数数组answer作为查询的答案，其中answer.length==queries.length且answer[i]是第i个查询的答案
 * Leetcode题目：https://leetcode.com/problems/maximum-xor-with-an-element-from-array/
 */
public class Code03_MaximumXorWithAnElementFromArray {

    /**
     * 将nums数组中的数以二进制形式加入前缀树.
     * 遍历queries
     * 前缀树返回与xi异或结果的最大值，要求异或的数小于mi
     */
    public static int[] maximizeXor(int[] nums, int[][] queries) {
        NumTrie trie = new NumTrie();
        for (int i = 0; i < nums.length; i++) {
            trie.add(nums[i]);
        }
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            ans[i] = trie.maxXor(queries[i][0], queries[i][1]);
        }
        return ans;
    }

    static class Node{
        public Node[] next = new Node[2]; // 走向后续的路。
        public int min = Integer.MAX_VALUE;
    }

    /**
     * 将数字的二进制信息加入前缀树
     */
    static class NumTrie{ // 前缀树
        public Node head = new Node(); // 头节点

        /**
         * 注意：------------
         * 前缀树叶节点也需要设置最小值。
         * @param num
         */
        public void add(int num){ // 新加入一个数字
            Node cur = head;
            head.min = Math.min(head.min, num);
            for (int i = 30; i >=0; i--) { // 二进制位
                int path = (num >> i) & 1;
                if (cur.next[path] == null){ // 之前没有对应的路就新建
                    cur.next[path] = new Node();
                }
                cur = cur.next[path];
                cur.min = Math.min(cur.min, num);
            }
        }

        /**
         * 返回num在线段树中最大的异或结果
         * 符号位尽量为0.
         * 其他位尽量为1.
         */
        public int maxXor(int xi, int mi){
            if(mi < head.min) return -1;

            Node cur = head;
            int ans = 0; // 最终异或的结果
            for (int i = 30; i>=0; i--) { // 二进制位(不考虑符号位,题目已经说明了非负)
                int path =(xi >> i) & 1;
                int best = path ^ 1; // 0则取1，1则取0.
                if(cur.next[best] == null ||
                        cur.next[best].min > mi
                ){ // 如果当前位没有期望的数，或大于mi当前位 走另一条路。
                    best = best ^ 1;
                }
                ans |= (path ^ best) << i; // 当前位的数异或到答案上。
                cur = cur.next[best];
            }
            return ans;
        }
    }

    /**
     * [5,2,4,6,6,3]
     * [[12,4],[8,1],[6,3]]
     * @param args
     */
    public static void main(String[] args) {
        int[] nums = {5,2,4,6,6,3};
        int[][] queries = {
                {12, 4},
                {8, 1},
                {6, 3}
        };
        System.out.println(maximizeXor(nums, queries));
    }
}
