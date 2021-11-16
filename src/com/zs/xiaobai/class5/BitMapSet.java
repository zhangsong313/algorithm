package com.zs.xiaobai.class5;

// 使用位图实现一个保存指定范围数字的set。
public class BitMapSet {
    private int[] setArr;

    public BitMapSet(int maxValue){
        int length = maxValue/32;
        if (maxValue%32 != 0){
            length += maxValue%32;
        }
        setArr = new int[length];
    }


}
