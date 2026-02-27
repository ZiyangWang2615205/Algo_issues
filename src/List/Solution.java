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

    //merge:
    // 1->2->4->6 and 1->3->5->7
    // => 1->1->2->3->4->5->6->7
    public static Linklist merge(Linklist l1, Linklist l2){
        if(l1 == null) return l2;
        if(l2 == null) return l1;
        //config
        Node p1 = l1.head;
        Node p2 = l2.head;
        Linklist merged = new Linklist();
        //compare and add
        while(p1 != null && p2 != null){
            if(p1.data >= p2.data){
                merged.add(new Node(p2.data));
                p2 = p2.nxt;
            }else{
                merged.add(new Node(p1.data));
                p1 = p1.nxt;
            }
        }
        //add left elements
        if(p1 == null){
            while(p2 != null){
                merged.add(new Node(p2.data));
                p2 = p2.nxt;
            }
        }else{
            while(p1 != null){
                merged.add(new Node(p1.data));
                p1 = p1.nxt;
            }
        }
        return merged;
    }
    public static void main(String[] args) {
        Linklist list = new Linklist(new Node(1));
        list.add(new Node(3));
        list.add(new Node(5));
        list.add(new Node(7));
        Linklist l2 = new Linklist(new Node(1));
        l2.add(new Node(2));
        l2.add(new Node(4));
        l2.add(new Node(6));
        Linklist l3 = merge(list,l2);
        l3.print();
    }
}
