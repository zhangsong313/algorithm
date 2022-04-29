package com.zs.tixi.class37;

import java.util.TreeMap;

/**
 * Size Balanced Tree
 * 平衡性规定：子树大小比侄子树小，违规。例如：左子树大小比右子树的左树小。
 *
 * 与AVL区别：
 *      1.平衡性规定不同。平衡因子变为树大小。要求侄
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
 * 同样logN效率实现有序表。
 * 是一种不同于二叉树的现代的数据结构。
 * 每新加一个数据。创建节点后随机建层，每次随机数小于0.5允许向上建层。
 * 根据随机原理，在大数据量下，每层数据量约为下层的1/2。
 * 代码：
 *      1.节点结构：key，val，nextNodes（保存向后的链表数组），isLessKey,isEqualsKey（由于有空节点head，需要考虑空节点情况进行比较。）
 *      2.跳表结构：PROBABILITY（随机因子，默认0.5），head（头节点），size（节点数），maxLevel（总层高）
 *      3.初始化：需要给head的第0层添加一个空链表。
 *      4.提供两个函数：mostRightLessNodeInTree 表中比key小的最后一个节点，从cur开始比key小的最后一个节点。
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
 * SBT代码总结:
 * 1.SBTNode节点信息
 *      泛型：<K extends Comparable<K>, V>
 *      k : key
 *      v : value
 *      l : 左子树
 *      r : 右子树
 *      size : 树大小
 *      构造函数:
 *      设置key，设置value，size设置为1
 * 2.SBTreeMap结构:
 *      泛型：<K extends Comparable<K>, V>
 *      root : 根节点
 * 3.SBTreeMap公开方法:
 *      (1) put(K, V): void 设置指定key的值
 *              如果key为空 返回
 *              调用findLastIndex(key)返回last节点
 *              如果last不为空 且 key等于last.k
 *                  last.v更新为value
 *              否则：
 *                  调用add(root, key, value)更新root节点
 *      (2) get(K): V 获取指定key的值
 *              如果key为空 返回null
 *              调用findLastIndex(key)返回last节点
 *              如果last不为空 且 key等于last.k
 *                  返回 last.v
 *              返回null
 *      (3) remove(K): void 删除指定key的节点
 *      (4) containsKey(K): boolean 是否包含指定key
 *      (5) size(): int map的大小
 *      (6) floorKey(K): K 不大于当前key的最大key
 *      (7) ceilingKey(K): K 不小于当前key的最小key
 *      (8) firstKey(): K 第一个key
 *      (9) lastKey(): K 最后一个key
 *      (10) getIndexKey(int): K 返回指定下标位置的key（系统api不支持）
 *      (11) getIndexValue(int): V 返回指定下标位置的value（系统api不支持）
 * 4.SBTreeMap私有方法:
 *      (1) findLastIndex(K): SBTNode 返回查询过程中等于key或者最后一个节点的key
 *              定义ans为空
 *              定义cur为root
 *              循环：cur不为空
 *                  如果：key等于cur.k 返回cur
 *                  ans指向cur
 *                  如果：key小于cur.k
 *                      cur指向cur.l
 *                  否则：
 *                      cur指向cur.r
 *              返回ans
 *      (2) add(SBTNode, K, V): SBTNode 在指定子树上添加一个新节点
 *              如果node为空 新建节点返回
 *              node.size++
 *              如果: key小于node.k
 *                  调用add(node.l, key, value)更新node.l
 *              否则：
 *                  调用add(node.r, key, value)更新node.r
 *              返回 maintain(node)
 *      (3) maintain(SBTNode): SBTNode 检查节点平衡性并调整
 *              如果node为空 返回null
 *              定义左树大小ls, 设置为getSize(node.l)
 *              定义左左树大小ls为0
 *              定义左右树大小lrs为0
 *              如果: node.l不为空
 *                  lls更新为getSize(node.l.l)
 *                  lrs更新为getSize(node.l.r)
 *              定义右树大小rs,设置为getSize(node.r)
 *              定义右右树大小rrs为0
 *              定义右左树大小rls为0
 *              如果：node.r不为空
 *                  rrs更新为getSize(node.r.r)
 *                  rls更新为getSize(node.r.l)
 *              如果：lls大于rs
 *                  调用rightRotate(node)更新node
 *                  调用maintain(node.r)更新node.r
 *                  调用maintain(node)更新node
 *              如果：lrs大于rs
 *                  node.l更新为leftRotate(node.l)
 *                  node更新为rightRotate(node)
 *                  node.l更新为maintain(node.l)
 *                  node.r更新为maintain(node.r)
 *                  node更新为maintain(node)
 *              如果：rrs大于ls
 *                  node更新为leftRotate(node)
 *                  node.l更新为maintain(node.l)
 *                  node更新为maintain(node)
 *              返回node
 *      (4) leftRotate(SBTNode): SBTNode 左旋调整
 *              定义right为node.r
 *              node.r指向right.l
 *              right.l指向node
 *              updateSize(node)更新node.size
 *              updateSize(right)更新right.size
 *              返回right
 *      (5) rightRotate(SBTNode): SBTNode 右旋调整
 *              定义left为node.l
 *              node.l指向left.r
 *              left.r指向node
 *              updateSize(node)更新node.size
 *              udpateSize(left)更新left.size
 *              返回left
 *      (6) updateSize(SBTNode): int 更新树大小
 *      (7) getSize(SBTNode): int 获取树大小
 *      (8) delete(SBTNode): SBTNode 在指定子树上删除指定key对应的节点
 *      (9) findLastNoBigIndex(K): SBTNode 返回不大于key的最大key对应的节点
 *      (10) findLastNoSmallIndex(K): SBTNode 返回不小于key的最小key对应的节点
 *      (11) getIndex(SBTNode, int): SBTNode 返回node树上第kth个元素
 *
 * SkipList代码总结:
 * 1.SkipListNode节点信息:
 *      泛型:<K extends Comparable<K>, V>
 *          k : key
 *          v : value
 *          nextNodes : 向右的节点列表(ArrayList)
 *          构造函数：
 *          设置key，设置value，nextNodes设置为空链表
 * 2.SkipListMap结构:
 *      root : 最左侧的根节点。
 *      size : map的大小
 *      PROBABILITY : 向上建层的概率临界值。
 *      maxLvl : 最高层，也是root节点的层数.
 *      构造函数：
 *          root初始化一个key,value都为空的节点，只作为检索起点不保存任何数据。
 *          root.nextNodes加入null，表示初始化了第0层，第0层root右侧没有节点。
 * 3.SkipListMap公开方法：
 *      (1) put(K, V): void 设置key的value
 *      (2) get(K): V 查询key的value
 *      (3) remove(K): void 删除key对应的节点.
 *      (4) size(): int map的大小
 *      (5) containsKey(K): boolean 是否包含指定key
 *      (6) floorKey(K): K 返回不大于key的最大key.
 *      (7) ceilingKey(K): K 返回不小于key的最大key
 *      (8) firstKey(): K 返回第一个key
 *      (9) lastKey(): K 返回最后一个key
 * 4.SkipListMap私有方法:
 *      (1) mostRightLessNodeInTree(K): SkipListNode<K, V> map中比key小的最右节点。
 *      (2) mostRightLessNodeInLvl(K, SkipListNode<K, V>, int): SkipListNode<K, V> 从指定层的指定节点出发，当前层比key小的最右节点。
 *      (3) add(K, V): void 在map中新增一个节点。
 */
public class T {
    public static void main(String[] args) {
        TreeMap map = new TreeMap();
    }
}
