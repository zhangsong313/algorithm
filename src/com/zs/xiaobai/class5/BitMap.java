package com.zs.xiaobai.class5;

public class BitMap {
    public static void main(String[] args) {
        int i = (int)(Math.random()*100 + 1);
        System.out.println(i % 66);
        System.out.println(i & 65);
        System.out.println(1 << 32);
//        System.out.println(1L << 32);
    }
}
