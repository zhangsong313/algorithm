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
 *              如果key为空,返回
 *              如果containsKey(key) 调用delete(root, key)更新root
 *      (4) containsKey(K): boolean 是否包含指定key
 *              如果key为空,返回false
 *              调用findLastIndex(key) 返回last节点
 *              如果last不为空 且 key等于last.k
 *                  返回true
 *              返回false
 *      (5) size(): int map的大小
 *              root为空，返回0，否则返回root.size
 *      (6) floorKey(K): K 不大于当前key的最大key
 *              如果key为空，返回null
 *              调用findLastNoBigIndex(key)获取noBig节点
 *              如果noBig节点为空，返回null，否则返回noBig.k
 *      (7) ceilingKey(K): K 不小于当前key的最小key
 *              如果key为空，返回null
 *              调用findLastNoSmallIndex(key)获取noSmall节点
 *              如果noSmall为空,返回null,否则返回noSmall.k
 *      (8) firstKey(): K 第一个key
 *              如果root为空，返回null
 *              定义cur为root。
 *              循环：cur.l不为空
 *                  cur = cur.l
 *              返回cur.k
 *      (9) lastKey(): K 最后一个key
 *              如果root为空,返回null
 *              定义cur为root
 *              循环:cur.r不为空
 *                  cur = cur.r
 *              返回cur.k
 *      (10) getIndexKey(int): K 返回指定下标位置的key（系统api不支持）
 *              如果:index<0 或 index>size()-1 返回null
 *              调用getIndex(root, index+1).k返回
 *      (11) getIndexValue(int): V 返回指定下标位置的value（系统api不支持）
 *              如果：index<0 或 index>size()-1 返回null
 *              调用getIndex(root, index+1).v返回
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
 *              如果：rls大于ls
 *                  node.r更新为rightRotate(node.r)
 *                  node更新为leftRotate(node)
 *                  node.l更新为maintain(node.l)
 *                  node.r更新为maintain(node.r)
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
 *              getSize(node.l)+getSize(node.r)+1;
 *      (7) getSize(SBTNode): int 获取树大小
 *              node==null?0:node.size
 *      (8) delete(SBTNode, K): SBTNode 在指定子树上删除指定key对应的节点
 *              node.size--; // 注意：此处size--不可放在最后.因为删除后node可能已经被替换为子树了。这种情况下size--会发生错误。
 *              如果: key小于node.k
 *                  调用delete(node.l, key)更新node.l
 *              如果: key大于node.k
 *                  调用delete(node.r, key)更新node.r
 *              否则:
 *                  如果: node.l为空 且 node.r为空
 *                      node = null;
 *                  如果: node.l为空
 *                      node = node.r
 *                  如果：node.r为空
 *                      node = node.l
 *                  否则:
 *                      定义next为node.r
 *                      循环：next.l不为空
 *                          next=next.l
 *                      调用delete(node.r, next.k)更新node.r
 *                      next.l更新为node.l
 *                      next.r更新为node.r
 *                      next.size更新为node.size
 *                      node更新为next
 *              返回node // 删除不需要调整平衡性。
 *      (9) findLastNoBigIndex(K): SBTNode 返回不大于key的最大key对应的节点
 *              定义ans为null
 *              定义cur为root
 *              循环:cur不为空
 *                  如果: key等于cur.k，返回cur
 *                  如果：key小于cur.k
 *                      cur=cur.l
 *                  否则:
 *                      ans = cur
 *                      cur=cur.r
 *              返回ans
 *      (10) findLastNoSmallIndex(K): SBTNode 返回不小于key的最小key对应的节点
 *              定义ans为null
 *              定义ans为root
 *              循环：cur不为空
 *                  如果：key等于cur.k, 返回cur
 *                  如果：key小于cur.k
 *                      ans=cur
 *                      cur=cur.l
 *                  否则：
 *                      cur=cur.r
 *              返回ans
 *      (11) getIndex(SBTNode, int): SBTNode 返回node树上第kth个元素
 *              如果kth等于getSize(node.l)+1,返回node
 *              如果kth小于等于getSize(node.l)
 *                  返回getIndex(node.l, kth)
 *              否则：
 *                  返回getIndex(node.r, kth-getSize(node.l)-1)
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
 *              如果key为空，返回
 *              调用mostRightLessNodeInTree(key),获取pre节点
 *              定义next为pre第0层的下一个节点
 *              如果 next不为空 且 key等于next.k
 *                  next.v更新为value
 *              否则
 *                  调用add(key, value)新增一个节点
 *      (2) get(K): V 查询key的value
 *              如果key为空，返回null
 *              调用mostRightLessNodeInTree(key)，获取pre节点
 *              定义next为pre第0层的下一节点
 *              如果 next不为空 且 key等于next.k 返回next.v,否则返回null
 *      (3) remove(K): void 删除key对应的节点.
 *              如果containsKey(key)为false，返回
 *              定义cur为root
 *              定义curLvl为maxLvl
 *              循环：curLvl大于等于0
 *                  调用mostRightLessNodeInLvl(key, cur, curLvl), 获取pre节点。
 *                  定义next为pre第curLvl层的下一个节点。
 *                  如果 next不为空 且 key等于next.k
 *                      pre第curLvl层的下一个节点设置为next第curLvl层的下一个节点
 *                  如果 curLvl!=0 且 pre==root && next == null
 *                      root.nextNodes.remove(curLvl) // 移除当前空层
 *                      maxLvl--
 *                  curLvl--
 *              size--
 *
 *      (4) size(): int map的大小
 *              返回size
 *      (5) containsKey(K): boolean 是否包含指定key
 *              如果key为空，返回false
 *              调用mostRightLessNodeInTree(key)，获取pre节点
 *              定义next为pre第0层的下一节点
 *              如果 next不为空 且 key等于next.k 返回true,否则返回false
 *      (6) floorKey(K): K 返回不大于key的最大key.
 *              如果key为空，返回null
 *              调用mostRightLessNodeInTree(key)，获取pre节点
 *              定义next为pre第0层的下一节点
 *              如果 next不为空 且 key等于next.k 返回next.k,否则返回pre.k
 *      (7) ceilingKey(K): K 返回不小于key的最大key
 *              如果key为空，返回null
 *              调用mostRightLessNodeInTree(key)，获取pre节点
 *              定义next为pre第0层的下一节点
 *              如果 next不为空 返回next.k,否则返回null
 *      (8) firstKey(): K 返回第一个key
 *              定义first为root的第0层的下一节点。
 *              如果first为空，返回null，否则返回first.k
 *      (9) lastKey(): K 返回最后一个key
 *              定义cur为root
 *              定义curLvl为maxLvl
 *              循环：curLvl大于等于0
 *                  定义next为curLvl层的下一节点
 *                  循环：next不为空
 *                      cur=next
 *                      next更新为cur第curLvl层的下一节点
 *                  curLvl--
 *              返回cur.k
 * 4.SkipListMap私有方法:
 *      (1) mostRightLessNodeInTree(K): SkipListNode<K, V> map中比key小的最右节点。
 *              定义cur为root
 *              定义lvl为maxLvl
 *              循环: lvl>=0
 *                  调用mostRightLessNodeInLvl(key, cur , lvl--), 更新cur
 *              返回cur
 *      (2) mostRightLessNodeInLvl(K, SkipListNode<K, V>, int): SkipListNode<K, V> 从指定层的指定节点出发，当前层比key小的最右节点。
 *              定义next为cur节点lvl层的下一个节点
 *              循环：next!=null && key大于next.k
 *                  cur更新为next
 *                  next更新为cur第lvl层的下一个节点
 *              返回cur
 *      (3) add(K, V): void 在map中新增一个节点。
 *              定义newLvl为0，表示新增节点的层数
 *              循环：Math.random() > PROBABILITY
 *                  newLvl++
 *              循环：maxLvl 小于 newLvl
 *                  root.nextNode.add(null) // 新增空层
 *                  maxLvl++
 *              根据key,value创建一个新节点，定义为newNode
 *              循环：0...newLvl
 *                  newNode.nextNode.add(null) // 新增空层
 *              定义cur为root
 *              定义curLvl为maxLvl
 *              循环：curLvl大于等于0
 *                  调用mostRightLessNodeInLvl(key, cur, curLvl)更新cur
 *                  如果：curLvl小于等于newLvl
 *                      newNode.nextNodes.set(curLvl, cur.nextNodes.get(curLvl)) // 新节点当前层的下一个节点设置为cur的下一个节点
 *                      cur.nextNodes.set(curLvl, newNode) // cur节点当前层的下一个节点设置为newNode
 *                  curLvl--
 *              size++
 */
public class T {
    public static void main(String[] args) {
        TreeMap map = new TreeMap();
    }
}
