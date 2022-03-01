package com.zs.tixi.class37;

import java.util.TreeMap;

/**
 * Size Balanced Tree
 * 平衡性规定：子树大小比侄子树小，违规。例如：左子树大小比右子树的左树小。
 *
 * 与AVL区别：
 *      1.平衡性规定不同。平衡因子变为树大小。
 *      2.左右旋后调整size，而不是高度。
 *      3.平衡性检查和调整：
 *          （1）获取左右子树大小和它们的子树大小，分别为LH,RH,LLH,LRH,RRH,RLH
 *          (2)判断：LH<RRH,LH<RLH,RH<LLH,RH<LRH
 *          (2)调整：类似AVL。
 *              LL型:右旋后,递归调整头节点和右子树。
 *              RR型：左旋后，递归调整头节点和左子树。
 *              LR型:左旋加右旋，递归调整头节点和左右子树。
 *              RL型：右旋加左旋，递归调整头节点和左右子树。
 *      4.删除：删除后无需调整平衡性。
 *      5.由于平衡因子是节点个数，可以很方便改写出第k个数相关需求的接口。
 *
 * 有序表改写:
 *      返回小于等于指定key有几个？系统接口不提供。
 *
 * 跳表：
 * 同样由logN效率实现有序表。
 * 是一种不同于二叉树的现代的数据结构。
 * 每新加一个数据。创建节点后随机建层，每次随机数小于0.5允许向上建层。
 * 根据随机原理，在大数据量下，每层数据量约为下层的1/2。
 * 代码：
 *      1.节点结构：key，val，nextNodes（保存向后的链表数组），isLessKey,isEqualsKey（由于有空节点head，需要考虑空节点情况进行比较。）
 *      2.跳表结构：PROBABILITY（随机因子，默认0.5），head（头节点），size（节点数），maxLevel（总层高）
 *      3.初始化：需要给head的第0层添加一个空链表。
 *      4.提供两个函数：mostRightLessNodeInTree 表中比key小的最后一个节点，从cur开始leve层比key小的最后一个节点。
 *      5.put：找到mostRightLessNodeInTree的下一个节点，如果是key，修改值返回。否则新增节点。
 *          新增节点：随机建层。maxLevel尝试更新，创建新节点并每层设置空。
 *              从head的maxLevel开始每层找到比key小的最后一个节点，如果当前层在newLevel内，将新节点插入。
 *      6.remove：不包含指定key直接返回。否则删除节点。
 *          删除节点：从head的maxLevel开始每层找到比key小的最后一个节点，如果下一个节点next等于key。前一个节点指向next的下一个节点。
 *              如果当前不是第0层，且只找到head 且 head的下一个节点为空。删除head中当前层链表，maxLevel减1.
 *
 * 积压结构：
 *      ArrayList，哈希表，SBT这些结构都适合硬盘写入的结构。减小硬盘IO，只有少数时刻会迎来瓶颈。
 *
 */
public class T {
    public static void main(String[] args) {
        TreeMap map = new TreeMap();
    }
}
