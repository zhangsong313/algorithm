package com.zs.shuati.class06;

/**
 * 2 数组中所有数都异或起来的结果，叫做异或和。给定一个数组arr，想知道arr中哪两个数的异或结果最大，返回最大的异或结果
 * Leetcode题目：https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/
 */
public class Code02_MaximumXorOfTwoNumbersInAnArray {

    /**
     * 从0..arr.length 把i之前的数加入前缀树，i位置的数根据前缀树取最大异或结果。
     */
    public static int findMaximumXOR(int[] arr) {
        if(arr == null || arr.length<2) return 0;

        int N = arr.length;
        NumTrie numTrie = new NumTrie();

        for (int i = 0; i < N-1; i++) {
            numTrie.add(arr[i]);
        }
        return numTrie.maxXor(arr[N-1]);
    }

    static class Node{
        public Node[] next = new Node[2]; // 走向后续的路。
    }

    /**
     * 将数字的二进制信息加入前缀树
     */
    static class NumTrie{ // 前缀树
        public Node head = new Node(); // 头节点

        public void add(int num){ // 新加入一个数字
            Node cur = head;
            for (int i = 31; i >=0; i--) { // 二进制位
                int path = (num >> i) & 1;
                if (cur.next[path] == null){ // 之前没有对应的路就新建
                    cur.next[path] = new Node();
                }
                cur = cur.next[path];
            }
        }

        /**
         * 返回num在线段树中最大的异或结果
         * 符号位尽量为0.
         * 其他位尽量为1.
         */
        public int maxXor(int num){
            Node cur = head;
            int ans = 0; // 最终异或的结果
            for (int i = 31; i>=0; i--) { // 二进制位
                int path =(num >> i) & 1;
                int best = i==31 ? path :path ^ 1; // 符号位不变，其他位0则取1，1则取0.
                if(cur.next[best] == null){ // 如果当前位没有期望的数，走另一条路。
                    best = best ^ 1;
                }
                ans |= (path ^ best) << i; // 当前位的数异或到答案上。
                cur = cur.next[best];
            }
            return ans;
        }
    }


}
