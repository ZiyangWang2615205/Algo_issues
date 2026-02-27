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

    //mergesort
    public static Linklist mergesort(Linklist list){
        if(list.head == null || list.head.nxt == null) return list;
        //Divide config:
        Node mid = findMid(list);
        Linklist left = new Linklist();
        Linklist right = new Linklist();
        //Divide to left and right
        Node cur = list.head;
        while(cur != mid){
            left.add(new Node(cur.data));
            cur = cur.nxt;
        }
        right.head = cur;
        //recursion
        Linklist leftSort = mergesort(left);
        Linklist rightSort = mergesort(right);
        return merge(leftSort,rightSort);
    }

    //oddFront: odd num always in the front of list
    //example: 1->2->3->4->null => 1->3->2->4->null
    public static Linklist oddFront(Linklist list){
        if(list.head == null || list.head.nxt == null) return list;
        Linklist odd = new Linklist();
        Linklist even = new Linklist();
        Node cur = list.head;
        while(cur != null){
            if(cur.data % 2 == 0){
                even.add(new Node(cur.data));
            }else{
                odd.add(new Node(cur.data));
            }
            cur = cur.nxt;
        }
        //connect odd->even
        Node oddEnd = odd.head;
        while(oddEnd.nxt != null){
            oddEnd = oddEnd.nxt;
        }
        oddEnd.nxt = even.head;
        return odd;
    }

    //resort: divide list based on mid and reverse latter. Then connect two list one by one.
    //example: 1->2->3->4->5->null => 1->2->3 & 4->5 => 1->2->3 & 5->4 => 1->5->2->4->3->null
    public static Node resort1(Linklist list){
        if(list.head == null || list.head.nxt == null) return list.head;
        //divide
        Node mid = findMid(list);
        Linklist left = new Linklist();
        Linklist right = new Linklist();
        Node cur = list.head;
        while(cur != mid.nxt){
            left.add(new Node(cur.data));
            cur = cur.nxt;
        }
        right.head = cur;
        //reverse right list
        right.reverse();

        Node lp = left.head;
        Node rp = right.head;
       while(rp != null){
           Node l_next = lp.nxt;
           Node r_next = rp.nxt;
           //connect one by one
           lp.nxt = rp;
           rp.nxt = l_next;
           lp = l_next;
           rp = r_next;
       }
       return left.head;
    }

    public static Linklist resort(Linklist list){
        if(list.head == null || list.head.nxt == null) return list;
        Linklist res = new Linklist();
        //divide
        Node mid = findMid(list);
        Linklist left = new Linklist();
        Linklist right = new Linklist();
        Node cur = list.head;
        while(cur != mid.nxt){
            left.add(new Node(cur.data));
            cur = cur.nxt;
        }
        right.head = cur;
        //reverse right list
        right.reverse();
        Node lp = left.head;
        Node rp = right.head;
        while(rp != null){
            res.add(new Node(lp.data));
            lp = lp.nxt;
            res.add(new Node(rp.data));
            rp = rp.nxt;
        }
        //don't forget the last element of left part
        res.add(new Node(lp.data));
        return res;
    }
    public static void main(String[] args) {
        Linklist list = new Linklist(new Node(1));
        list.add(new Node(2));
        list.add(new Node(3));
        list.add(new Node(4));
        list.add(new Node(5));
        resort(list).print();
    }
}
