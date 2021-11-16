package com.zs.xiaobai.class2;

public class T03_Random {
    public static void main(String[] args) {
        int total = 10000000;
        int count = 0;
        for (int i = 0; i < total; i++) {
            if (Math.random()<0.3){
                count++;
            }
        }
        System.out.println((double) count/(double) total);

        System.out.println("=============================");
        count = 0;
        for (int i = 0; i < total; i++) {
            if (Math.random()*5<3){
                count++;
            }
        }
        System.out.println((double) count/(double) total);
        System.out.println((double) 3/(double) 5);

        int [] counts = new int[10];
        System.out.println("=============================");
        count = 0;
        for (int i = 0; i < total; i++) {
            counts[f1()]++;
        }
        for (int i = 0; i < counts.length; i++) {
            System.out.println(counts[i]);
        }

        counts = new int[10];
        System.out.println("=============================");
        count = 0;
        for (int i = 0; i < total; i++) {
            counts[f2()]++;
        }
        for (int i = 0; i < counts.length; i++) {
            System.out.println(counts[i]);
        }

        counts = new int[10];
        System.out.println("=============================");
        count = 0;
        for (int i = 0; i < total; i++) {
            counts[g2()]++;
        }
        for (int i = 0; i < counts.length; i++) {
            System.out.println(counts[i]);
        }
    }











    private static int g2(){
        int res ;
        do {
            res = g1();
        }while (res==g1());
        return res;
    }

    // 给定固定概率返回【0,1】的函数，得到[0,1]等概率的函数。
    private static int g1(){
        return Math.random()<0.3?0:1;
    }


    // 等概率返回[5-8]上的整数。
    private static int f2(){
        // 等概率返回[0-3]
        int res = (returnRandom01()<<1) + returnRandom01();
        return res+5;
    }

    // 等概率返回【0,1】
    private static int returnRandom01(){
        int res;
        do {
            res = f1();
        } while (res==7);


        if (res<=6){
            return 0;
        } else {
            return 1;
        }
    }

    // 等概率返回[5, 9]上的整数
    private static int f1(){
        return (int)(Math.random()*5)+5;
    }
}
