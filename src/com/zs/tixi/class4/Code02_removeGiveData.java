package com.zs.tixi.class4;

/**
 * 链表删除给定值。
 */
public class Code02_removeGiveData {

    public static void main(String[] args) {
        TestUtil.checkRemoveGiveData(1000, 99, 99, Code02_removeGiveData::removeGiveData);
    }

    /**
     * 链表删除给定值。
     *
     * prev为上一个不是num的节点
     * head为当前节点。
     * 如果head.data与num相等，prev.next不更新. head跳到下一位置。（相等位置被越过了）
     * 否则：prev.next指向当前head，prev来到下一位置，head跳到下一位置。
     *
     * @param head 给定的链表
     * @param num 给定需要从链表中删除的值。
     * @return 返回删除后的链表结果。
     */
    public static Node removeGiveData(Node head, int num){
        Node res = null;
        Node prev = null;
        while (head != null){
            if (head.data != num){
                if (res == null) res = head; // 如果res为空，设置res为head。
                if (prev!=null) prev.next = head; // 如果prev不为空，设置prev的next为head。
                prev = head; // prev移动到head位置。
            }
            head = head.next; // head移动到下一位置。
        }
        if (prev!=null) prev.next = null; // 删除结尾位置。
        return res;
    }
}
