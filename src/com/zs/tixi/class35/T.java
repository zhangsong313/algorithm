package com.zs.tixi.class35;
// 本章并无code，因为资源限制类题目输入需要的条件较多并且真的实现代码量巨大
// 面试中这类题目出现也就和面试官聊解法，不会有代码实现的要求

/**
 * 1）布隆过滤器用于集合的建立与查询，并可以节省大量空间（已讲）
 * 2）一致性哈希解决数据服务器的负载管理问题（已讲）
 * 3）利用并查集结构做岛问题的并行计算（已讲）
 * 4）哈希函数可以把数据按照种类均匀分流
 * 5）位图解决某一范围上数字的出现情况，并可以节省大量空间
 *      位图只能记录[是和否]的信息.
 * 6）利用分段统计思想、并进一步节省大量空间
 * 7）利用堆、外排序来做多个处理单元的结果合并
 *
 * 题目一:(出现次数)
 *      32位无符号整数的范围是0~4,294,967,295，
 *      现在有一个正好包含40亿个无符号整数的文件，
 *      可以使用最多1GB的内存，怎么找到出现次数最多的数？
 * 题目二:(是否出现)
 *      32位无符号整数的范围是0~4,294,967,295，
 *      现在有一个正好包含40亿个无符号整数的文件，
 *      所以在整个范围中必然存在没出现过的数。
 *      可以使用最多1GB的内存，怎么找到所有未出现过的数？
 *      【进阶】
 *      内存限制为 3KB，但是只用找到一个没出现过的数即可
 * 题目三:(是否重复/出现两次以上)
 *      有一个包含100亿个URL的大文件，假设每个URL占用64B，
 *      请找出其中所有重复的URL
 *      【补充】
 *      某搜索公司一天的用户搜索词汇是海量的(百亿数据量)，
 *      请设计一种求出每天热门Top100词汇的可行办法
 * 题目四:(出现两次的数)
 *      32位无符号整数的范围是0~4294967295，
 *      现在有40亿个无符号整数，
 *      可以使用最多1GB的内存，
 *      找出所有出现了两次的数。
 * 题目五:
 *      32位无符号整数的范围是0~4294967295，现在有40亿个无符号整数
 *      可以使用最多3K的内存，怎么找到这40亿个整数的中位数？
 * 题目六:
 *      32位无符号整数的范围是0~4294967295，
 *      有一个10G大小的文件，每一行都装着这种类型的数字，
 *      整个文件是无序的，给你5G的内存空间，
 *      请你输出一个10G大小的文件，就是原文件所有数字排序的结果
 */
public class T {
    public static void main(String[] args) {
        System.out.println(Long.MAX_VALUE);
    }
}
