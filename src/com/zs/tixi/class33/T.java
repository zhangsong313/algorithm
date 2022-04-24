package com.zs.tixi.class33;

/**
 * AC自动机
 *      解决在一个大字符串中,找到多个候选字符串的问题.
 *      (大文章内找敏感词.)
 *
 * AC自动机算法核心:
 *      1)把所有匹配串生成一颗前缀树
 *      2)前缀树节点增加fail指针
 *      3)fail指针的含义:如果必须以当前字符结尾.当前形成的路径是str,剩下的哪一个字符串的前缀和str的后缀,
 *      拥有最大的匹配长度.fail指针就指向哪个字符串的最后一个字符所对应的节点.
 *
 * IndexTree
 *
 *      对于一个数组数据,支持如下功能:
 *          1.update(i, c);更新i位置的数为c.时间复杂度O(logN)
 *          2.query(i, j);查询i..j区间和.时间复杂度O(logN)
 *      特点:
 *      1)支持区间查询
 *      2)没有线段树那么强,但是非常容易改成一维,二维,三维结构.
 *      3)只支持单点更新
 *
 *      改写二维IndexTree:
 *      对于一个二维数组,要求支持以下功能:
 *          1.update(i, j, c); 更新(i, j)位置的数为c.时间复杂度O(logN*logM)
 *          2.query(i1, j1, i2, j2); 查询(i1, j1)到(i2, j2)这个矩形范围内的累加和.时间复杂度O(logN*logM)
 */
public class T {
}
