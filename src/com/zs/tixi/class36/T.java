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
 *
 * AVL树代码总结：
 * 1. AVLNode节点信息:
 *      泛型设置:<K extends Comparable<K>, V>
 *      k : key
 *      v ： value
 *      l ：左子树
 *      r : 右子树
 *      h : 高度（平衡因子）
 *      构造函数:设置key和value，h设置为1.
 * 2. AVLTreeMap信息:
 *      泛型设置:<K extends Comparable<K>, V>
 *      root : 根节点
 *      size : map大小
 * 3.AVLTreeMap公开方法:
 *      (1) put(K, V): void 设置指定key的值
 *          如果key为空，返回.
 *          调用findLastIndex(key)返回last节点.
 *          如果last节点不为空 且 last.k与key相等
 *              修改last.v为value
 *          否则：
 *              调用add(root, key, value)更新root节点。
 *              size++
 *      (2) get(K): V 获取指定key的值
 *          如果key为空，返回null
 *          调用findLastIndex(key)获取最接近key的节点last
 *          如果：last不为空 且 key等于last.k
 *              返回 last.v
 *          返回null
 *      (3) remove(K): void 删除指定key的节点
 *          如果key为空 返回
 *          如果：containsKey(key)为true
 *              调用delete(root, key) 更新root
 *              size--
 *      (4) containsKey(K): boolean 是否包含指定key
 *          如果key为空，返回false
 *          调用findLastIndex(key)返回last节点
 *          如果: last不为空 且 key等于last.k
 *              返回true
 *          返回false
 *      (5) size(): int map的大小
 *          返回size
 *      (6) floorKey(K): K 不大于当前key的最大key
 *          调用findLastNoBigIndex(key) 返回noBig节点
 *          如果 noBig不为空 返回noBig.k
 *          否则 返回null
 *      (7) ceilingKey(K): K 不小于当前key的最小key
 *          调用findLastNoSmallIndex(key) 返回noSmall节点
 *          如果noSmall不为空 返回noSmall.k
 *          否则 返回null
 *      (8) firstKey(): K 第一个key
 *          如果root为空，返回null
 *          定义cur指向root
 *          循环：cur.l不为空
 *              cur指向cur.l
 *          返回cur.l
 *      (9) lastKey(): K 最后一个key
 *          如果root为空, 返回null
 *          定义cur指向root
 *          循环：cur.r不为空
 *              cur指向cur.r
 *          返回cur.r
 *
 * 4.AVLTreeMap私有方法:
 *      (1) findLastIndex(K): AVLNode 返回查询过程中等于key或者最后一个节点的key
 *          定义ans节点为null
 *          定义cur节点为root
 *          循环：cur不为空
 *              如果key和cur.k相等, 返回cur节点
 *              ans = cur
 *              如果key比cur.k小
 *                  cur = cur.l
 *              否则：
 *                  cur = cur.r
 *          返回ans
 *      (2) add(AVLNode, K, V): AVLNode 在指定子树上添加一个新节点
 *          如果节点为空，新建节点返回.
 *          如果key比node.k小
 *              去左树添加节点，左树递归调用add
 *          否则
 *              去右树添加，右树递归调用add
 *          调用updateHeight更新node的高度
 *          调用maintain检查node平衡性并调整，返回调平衡后的结果。
 *      (3) maintain(AVLNode): AVLNode 检查节点平衡性并调整
 *          如果node为空返回null;(删除场景下节点可能为空)
 *          调用getHeight(node.l)获取左树高度lH
 *          调用getHeight(node.r)获取右树高度rH
 *          如果：lH和rH差的绝对值超过1
 *              如果：lH大于rH
 *                  getHeight(node.l.l)获取左左树高度llH
 *                  getHeight(node.l.r)获取左右树高度lrH
 *                  如果：llH大于等于lrH
 *                      调用rightRotate(node)右旋,更新node
 *                  否则：
 *                      leftRotate(node.l)左旋,更新node.l
 *                      rightRotate(node)右旋，更新node
 *              否则：
 *                  getHeight(node.r.r)获取右右树高度rrH
 *                  getHeight(node.r.l)获取右左树高度rlH
 *                  如果: rrH大于等于rlH
 *                      leftRotate(node)左旋，更新node
 *                  否额:
 *                      rightRotate(node.r)右旋,更新node.r
 *                      leftRotate(node)左旋，更新node
 *          返回node
 *      (4) leftRotate(AVLNode): AVLNode 左旋调整
 *          定义right为node.r
 *          node.r指向right.l
 *          right.l指向node
 *          updateHeight(node)更新node.h
 *          updateHeight(right)更新right.h
 *          返回right
 *      (5) rightRotate(AVLNode): AVLNode 右旋调整
 *          定义left为node.l
 *          node.l指向left.r
 *          left.r指向node
 *          updateHeight(node)更新node.h
 *          updateHeight(left)更新left.h
 *          返回left
 *      (6) updateHeight(AVLNode): int 更新高度
 *          调用getHeight获取node的左树和右树高度较大值,然后加1.
 *      (7) getHeight(AVLNode): int 获取高度
 *          如果node为空，返回0，否则返回node.h
 *      (8) delete(AVLNode): AVLNode 在指定子树上删除指定key对应的节点
 *          如果key小于node.k
 *              递归调用delete(node.l, key)更新node.l
 *          如果 key大于node.k
 *              递归调用delete(node.r, key)更新node.r
 *          否额:
 *              如果：ndoe.l为空 且 node.r为空
 *                  node设置为null
 *              如果：node.l为空
 *                  node设置为node.r
 *              如果：node.r为空
 *                  node设置为node.l
 *              否则：
 *                  定义next为node.r
 *                  循环：next.l不为空
 *                      next指向next.l
 *                  递归调用delete(node.r, next.k)更新node.r
 *                  next.l指向node.l
 *                  next.r指向node.r
 *                  node设置为next
 *          如果node不为空 updateHeight(node)更新node.h
 *          返回 maintain(node)
 *      (9) findLastNoBigIndex(K): AVLNode 返回不大于key的最大key对应的节点
 *          定义ans为null
 *          定义cur指向root
 *          循环：cur不为空
 *              如果 key等于cur.k 返回cur.k
 *              如果 key小于cur.k
 *                  cur指向cur.l
 *              否则
 *                  ans指向cur
 *                  cur指向cur.r
 *          返回ans
 *      (10) findLastNoSmallIndex(K): AVLNode 返回不小于key的最小key对应的节点
 *          定义ans为null
 *          定义cur指向root
 *          循环：cur不为空
 *              如果 key等于cur.k 返回cur.k
 *              如果 key小于cur.k
 *                  ans指向cur
 *                  cur指向cur.l
 *              否则
 *                  cur指向cur.r
 *          返回ans
 */
public class T {
}
