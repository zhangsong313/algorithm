package com.zs.tixi.class4;

/**
 * 链表删除给定值。
 */
public class Code02_removeGiveData {
    /**
     * 链表删除给定值。
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
        return res;
    }

    /**
     * 好的写法.
     * @param head
     * @param num
     * @return
     */
    public static Node removeGiveData2(Node head, int num){
        // 将链表开始部分的给定值跳过。
        while (head!=null){
            if (head.data !=num){
                break;
            }
            head = head.next;
        }

        Node prev = null;
        Node curr = head;
        while (curr!=null){

            if(curr.data != num){
                prev.next = curr; // 非删除值情况下更新prev.next
                prev = curr; // prev只有当值为非删除值时才移动,否则不移动
            }

            curr = curr.next; /// curr一直向后移动
        }
        return head;
    }

    /**
     * 好的写法.
     * @param head
     * @param num
     * @return
     */
    public static Node removeGiveData3(Node head, int num){
        // 将链表开始部分的给定值跳过。
        while (head!=null){
            if (head.data !=num){
                break;
            }
            head = head.next;
        }

        Node prev = head;
        Node curr = head;
        while (curr!=null){

            if(curr.data == num){
                prev.next = curr.next; // 更新prev指向下一个值.
            } else {
                prev = curr; // prev只有当值为非删除值时才移动,否则不移动
            }

            curr = curr.next; /// curr一直向后移动
        }
        return head;
    }
}
