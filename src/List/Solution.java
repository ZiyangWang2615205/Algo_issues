package List;

public class Solution {
    //findMid:
    // 1->2->3->null return 2
    // 1->2->3->4->null return 3

    public static Node findMid(Linklist list){
        if(list.head== null || list.head.nxt == null) return list.head;
        Node fast = list.head;
        Node slow = list.head;
        //fast stand on last element or null point in final
        while(fast != null && fast.nxt != null){
            fast = fast.nxt.nxt;
            slow = slow.nxt;
        }
        return slow;
    }

    public static void main(String[] args) {
        Linklist list = new Linklist(new Node(1));
        list.add(new Node(2));
        list.add(new Node(3));
        //list.add(new Node(4));
        System.out.println(findMid(list).data);
    }
}
