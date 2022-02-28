package com.zs.tixi.class34;

/**
 *
 * 哈希函数: out f(in data)
 *      1.输入data长度可能是无穷大的,例如字符串.
 *      2.输出是有限长度.
 *      3.固定输入的输出固定.没有任何随机.
 *      4.输出会随着输入不同均匀分布.与输入内容和相似性无关.
 *      作用:根据值的不同,把数据均匀分为N份.
 *
 * 哈希表的设计:
 *      插入,检索的时间复杂度O(1)
 *      
 * 布隆过滤器:
 *  数据量100亿,失误率0.01%
 *      布隆过滤器重要的三个公式
 *      1，假设数据量为n，预期的失误率为p（布隆过滤器大小和每个样本的大小无关）
 *      2，根据n和p，算出Bloom Filter一共需要多少个bit位，向上取整，记为m
 *      3，根据m和n，算出Bloom Filter需要多少个哈希函数，向上取整，记为k
 *      4，根据修正公式，算出真实的失误率p_true
 *
 * 已知有两个哈希函数f1和f2:请生成n个哈希函数.
 *  i*f1+f2
 *
 * 一致性哈希:
 *      hashkey的选择.
 *      初始多台机器上环,后续增加机器和减少机器只迁移部分数据即可.
 *      逻辑层保留路由表:
 *          哈希环上机器的哈希值排序后,取得大于等于当前数据哈希值最左的位置,可以二分.(有序表)
 *      机器上环如何保证均匀性?
 *          虚拟节点
 *          可以根据不同机器性能按比例决定虚拟节点数量.
 */
public class T {
}
