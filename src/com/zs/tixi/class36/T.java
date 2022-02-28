package com.zs.tixi.class36;

/**
 * 有序表 上
 *
 * 有序表和数据库索引
 *
 * 二叉树:搜索,平衡.
 *      左旋.右旋:修正平衡性且不破坏搜索性.
 *      搜索二叉树的加入和删除.
 *      AVL树,新增,删除节点后,检查平衡性并调整.
 *          四种破坏平衡的情况:
 *              LL:右旋
 *              LR:先左旋,后右旋
 *              RR:左旋
 *              RL:先右旋,再左旋
 *              既LL又LR:与LL处理相同
 *          检查平衡性:
 *              新增节点:从当前节点检查四种情况,向上一直到根节点.
 *              删除:
 *                  无左无右,有左无右,有右无左:直接删除节点，将子树替换当前节点。从父节点向上查到根节点.
 *                  有左有右:将当前节点的后继节点(右树的最左节点)替换到当前位置,从后继节点的父节点一直向上查到根节点.
 *
 * 有序表和AVL树关系:
 *      有序表是接口,AVL树是具体实现.
 */
public class T {
}
