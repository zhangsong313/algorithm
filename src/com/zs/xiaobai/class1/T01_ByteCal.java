package com.zs.xiaobai.class1;

/**
 * 1、如何打印出int数值的二进制位情况。
 * 2、了解位运算：与，或，非，异或
 */
public class T01_ByteCal {
    private static void print(int num){
        for (int i = 31 ;i >=0 ; i--) {
            System.out.print( (num & 1<<i) == 0?"0":"1");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int a = 23234;
        int b = 9778;
        print(a);
        print(b);

        System.out.println("=======&====与====");
        print(a & b);
        System.out.println("=======|====或====");
        print(a | b);
        System.out.println("=======~a====非====");
        print(~ a);
        System.out.println("=======~b=====非===");
        print(~ b);
        System.out.println("=======^====异或====");
        print(a ^ b);
    }
}
