package com.zs.shuati.class06;

/**
 * 1 数组中所有数都异或起来的结果，叫做异或和。给定一个数组arr，返回arr的最大子数组异或和
 */
public class Code01_MaxXOR {

    /**
     * O(N^2)
     * 生成前缀异或和数组。
     * 每次遍历检查0..1, 0..2, 0..3等的前缀异或和。
     * 找到和0..i异或和 进行异或后的最大结果。
     * 例如:0..i-5的异或和 与0..i的异或和 进行异或后的结果最大，
     * 那么i-4..i是可以以i为结尾达到最大异或和的子数组。
     */
    public static int maxXorSubarray1(int[] arr){
        if(arr==null || arr.length==0) return 0;
        int N = arr.length;
        int[] help = new int[N];
        help[0] = arr[0];
        for (int i = 1; i < N; i++) {
            help[i] = help[i-1] ^ arr[i];
        }

        int ans = Integer.MIN_VALUE; // 不考虑子数组为空数组的情况。(空数组情况下异或和为0)
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= i; j++) { // 0..j-1异或和
                ans = Math.max(ans, j==0 ? help[i] : help[i] ^ help[j-1]); // 0..i 与 0..j异或后得到 j+1..i
            }
        }
        return ans;
    }

    /**
     * 将异或前缀和help数组以二进制形式添加到前缀树中。
     * 求每个位置结尾的最大异或和只需要走一遍前缀树(32步），就能找到需要的最大前缀异或和.
     */
    public static int maxXorSubarray2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        NumTrie trie = new NumTrie();
        trie.add(0); // 先加入一个0，保证线段树为空也能返回答案。
        int ans = Integer.MIN_VALUE;
        int xor = 0; // 0..i异或和.
        for (int i = 0; i < N; i++) { // 每次循环：线段树保持0..i返回内的异或前缀和，xor为0..i异或和
            xor ^= arr[i];
            ans = Math.max(ans, trie.maxXor(xor));
            trie.add(xor);

        }
        return ans;
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

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 30;
        int maxValue = 50;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int comp = maxXorSubarray1(arr);
            int res = maxXorSubarray2(arr);
            if (res != comp) {
                succeed = false;
                printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
