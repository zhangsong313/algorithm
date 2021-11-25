package com.zs.tixi.class10;

/**
 * 1. 排序算法总结：
 *              | 时间复杂度     | 空间复杂度 | 稳定性   |
 *      选择排序 | O(N^2)       | O(1)      | 无     |
 *      冒泡排序 | O(N^2)       | O(1)      | 有     |
 *      插入排序 | O(N^2)       | O(1)      | 有     |
 *      归并排序 | O(N*logN)    | O(N)      | 有     |
 *      快速排序 | O(N*logN)    | O(logN)   | 无     |
 *      堆排序  | O(N*logN)    | O(1)      | 无     |
 *      ======================================
 *      计数排序 | O(N)         | O(M)      | 有     |
 *      基数排序 | O(N)         | O(N)      | 有     |
 *
 * 2. 各种排序算法的选择
 *      1) 不基于比较的排序，对样本有要求，不易改写。
 *      2） 基于比较的排序，只要规定好两个样本怎么比大小就可以直接复用
 *      3） 基于比较的排序，时间复杂度的极限时O（N*logN）
 *      4) 时间复杂度O(N*logN)，额外空间复杂度低于O(N)，且稳定的排序是不存在的。
 *      5） 为了绝对的速度选快排，为了空间选堆，为了稳定性选归并。
 * 3.
 *      常见的坑：
 *      1） 归并排序的额外空间复杂度可以变成O(1)， ”归并排序 内部缓存法“， 但是将变得不稳定。
 *          （不如直接使用堆排序）
 *      2） ”原地归并排序“是垃圾贴，会让时间复杂度变为O（N*2）
 *          （不如使用冒泡或插入）
 *      3) 快速排序稳定性改进，”01 stable sort“， 但是会对样本数据要求更多。
 *          （同样对样布数据有要求且稳定的排序有计数排序和基数排序，时间复杂度还更好）
 *      4）在整型数组中，请把奇数放在数组左边，偶数放在数组右边，要求所有奇数之间原始相对的次序不变，所有偶数之间原始相对次序不变，
 *          时间复杂度要求O(N)，额外空间复杂度做到O(1)。
 *          （如果可以实现按照不同条件把数组分成两部分，时间O(N)，空间O(1)，且稳定的方法，快速排序的partition也不会不稳定了。）
 * 4. 工程上对排序算法的改进
 *      1）稳定性的考虑：基础类型使用不稳定的快排，非基础类型使用稳定的归并排序
 *      2）充分利用O(N*logN)和O(N^2)排序的各自优势：数据量小的排序使用常数时间更小的排序，而不是时间复杂度更低的排序。
 *          需要进行大量实验来确定在什么数据量级上选择什么样的排序。
 * 5. 链表问题：
 *      1）对于笔试，不用在乎空间复杂度，一切为了时间复杂度，尽可能使用容器方案加快答题速度。
 *      2）对于面试，时间复杂度依然排在第一位，但是一定要找到空间最省的方法。
 * 6. 常见解题技巧：
 *      1）使用容器（哈希表，数组等）
 *      2）快慢指针
 * 7. 快慢指针练习
 *      1）输入链表头节点，奇数长度返回中点，偶数长度返回上中点。
 *      2）输入链表头节点，奇数长度返回中点，偶数返回下中点。
 *      3）输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个。
 *      4）输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个。
 * 8. 常见面试题：
 *      1）给定一个单链表头节点，判断该链表是否为回文结构。
 *          哈希表解决很简单。（笔试用）
 *          改变原链表方式需要注意边界（面试用）
 *      2）将单向链表按某值划分成左边小中间相等，右边大的形式。
 *          将链表放入数组，对数组做partition（笔试用）
 *          分成小，中，大三部分，再把各个部分串起来。（面试用）
 *      3）一种特殊的单链表节点类描述如下：
 *          class Node{ int val; Node next; Node rand;}
 *          rand指针是单链表节点中新增的指针，可能指向链表中的任意节点，也可能指向Null。
 *          给定一个由Node节点组成的无环单链表的头节点Head，请完成一个函数实现这个链表的复制，并返回复制新链表的头节点。
 *          要求：时间复杂度O(N)，额外空间复杂度O(1)
 */
public class T {
}
