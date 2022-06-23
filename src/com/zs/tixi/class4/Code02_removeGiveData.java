package com.zs.tixi.class4;

/**
 * 链表删除给定值。
 */
public class Code02_removeGiveData {

    public static void main(String[] args) {
//        int[] arr = MyCompValue.randomIntArr(10, 88);
        int[] arr = new int[]{5, 6, 7, 5, 6, 8, 8};
        Node node = randomLinkedList(arr);
        printLinkedList(node);
        int delIndex = (int)(Math.random()*arr.length);
        System.out.println("del: "+delIndex);
//        Node node2 = removeGiveData(node, arr[delIndex]);
        Node node2 = removeGiveData2(node, 8);
        printLinkedList(node2);

    }

    private static void printLinkedList(Node head){
        while (head!=null){
            System.out.print(head.data+" ");
            head = head.next;
        }
        System.out.println();
    }

    private static Node randomLinkedList(int[] arr){

        if(arr.length==0) return null;
        Node head = new Node();
        head.data = arr[0];
        Node cur = head;
        for (int i = 1; i < arr.length; i++) {
            cur.next = new Node();
            cur.next.data = arr[i];
            cur = cur.next;
        }
        return head;
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
        if (prev!=null) prev.next = head; // 删除结尾位置。
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
