package org.gmfbilu.superapp.module_java.dataStructure.单链表反转;


/**
 * 反转链表是有2种方法(递归法，遍历法)实现的，面试官最爱考察的算法无非是斐波那契数列和单链表反转，递归方法实现链表反转比较优雅
 */
public class SingleLinked {


    public static void main(String[] arge) {
    }


    /**
     * 单链表反转
     * 递归
     * 递归法是从最后一个Node开始，在弹栈的过程中将指针顺序置换的
     *
     * @param head
     * @return
     */
    public Node reverse(Node head) {
        if (head == null || head.next == null)
            return head;
        Node temp = head.next;
        Node newHead = reverse(temp);
        temp.next = head;
        head.next = null;
        return newHead;
    }
}
