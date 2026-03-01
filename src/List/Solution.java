package List;

import java.util.List;

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

    //divide:
    //1->2->3->4 => 1->2 & 3->4
    //1->2->3->4->5 => 1->2 & 3->4->5

    public static Linklist[] divide(Linklist list){
        if(list.head == null || list.head.nxt == null) return new Linklist[]{list};
        Node mid = findMid(list);
        Linklist left = new Linklist();
        Linklist right = new Linklist();
        Node cur = list.head;
        while(cur != mid){
            left.add(new Node(cur.data));
            cur = cur.nxt;
        }
        right.head = cur;
        return new Linklist[]{left,right};
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
        Linklist[] divideList = divide(list);
        Linklist left = divideList[0];
        Linklist right = divideList[1];
        //recursion
        Linklist leftSort = mergesort(left);
        Linklist rightSort = mergesort(right);
        return merge(leftSort,rightSort);
    }

    //oddFront:
    // odd num always in the front of list
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

    //resort:
    // divide list based on mid and reverse latter. Then connect two list one by one.
    //example: 1->2->3->4->5->null => 1->2 & 3->4->5 => 1->2 & 5->4->3 => 1->5->2->4->3->null

    public static Linklist resort(Linklist list){
        if(list.head == null || list.head.nxt == null) return list;
        Linklist res = new Linklist();
        Linklist[] div = divide(list);
        //reverse right list
        div[1].reverse();
        //connection
        Node lp = div[0].head;
        Node rp = div[1].head;
        while (lp != null && rp != null){
            res.add(new Node(lp.data));
            lp = lp.nxt;
            res.add(new Node(rp.data));
            rp = rp.nxt;
        }
        while(rp != null){
            res.add(new Node(rp.data));
            rp = rp.nxt;
        }
        return res;
    }


    // first_common:
    // After the two linked lists meet at the first common node,
    // they share the same tail (i.e., all subsequent nodes are identical).
    //example:
    //A: 1 → 2 → 3
    //              ↘
    //                7 → 8 → 9
    //              ↗
    //B:    4 → 5 → 6

    public static Node first_common(Linklist l1, Linklist l2){
        if(l1.head == null || l2.head == null) return null;
        Node p1 = l1.head;
        Node p2 = l2.head;
        while(p1 != p2){
            p1 = (p1 == null)? l2.head : p1.nxt;
            p2 = (p2 == null)? l1.head : p2.nxt;
        }
        return p1;
    }

    //isRing:
    //judge if list is ring

    public static boolean isRing(Linklist list){
        if(list.head == null || list.head.nxt == null) return false;
        Node slow = list.head;
        Node fast = list.head.nxt;
        while(slow != fast){
            if(fast == null || fast.nxt == null) return false;
            fast = fast.nxt.nxt;
            slow = slow.nxt;
        }
        return true;
    }

    //entryRing:
    //find the entry point of ring
    public static Node entryRing(Linklist list){
       if(!isRing(list)) return null;
       Node fast = list.head.nxt;
       Node slow = list.head;
       while(slow != fast){
           fast = fast.nxt.nxt;
           slow = slow.nxt;
       }
       fast = list.head;
       slow = slow.nxt;
       while(slow != fast){
           fast = fast.nxt;
           slow = slow.nxt;
       }
       return slow;
    }

    //reverse_by_couple:
    //1->2->3->4->5 => 2->1->4->3->5
    public static Linklist reverse_by_couple(Linklist list){
        if(list.head == null || list.head.nxt == null) return list;
        Linklist res = new Linklist();
        Node dummy = new Node(-1);
        dummy.nxt = list.head;
        Node prev = dummy;
        while(prev.nxt != null && prev.nxt.nxt != null){
            Node cur = prev.nxt;
            Node next = prev.nxt.nxt;
            //connection
            prev.nxt = next;
            cur.nxt = next.nxt;
            next.nxt = cur;
            //iter
            prev = cur;
        }
        res.head = dummy.nxt;
        return res;
    }
    public static void main(String[] args) {
        Linklist list = new Linklist(new Node(1));
        list.add(new Node(2));
        list.add(new Node(3));
        list.add(new Node(4));
        list.add(new Node(5));
        reverse_by_couple(list).print();
    }
}
