package List;

public class Review {
    public static Node[] helperK(Node start, Node tail){
        Linklist flip = new Linklist(start);
        flip.reverse();
        return new Node[]{tail,start};
    }
    public static Linklist reverse_by_k(Linklist list, int k){
        if(list.head == null || list.head.nxt == null || k < 2){
            return list;
        }
        Linklist res = new Linklist();
        Node dummy = new Node(-1);
        dummy.nxt = list.head;
        Node prev = dummy;
        while(prev != null && prev.nxt != null){
            Node tail = prev;
            //find tail
            for (int i = 0; i < k; i++) {
                tail = tail.nxt;
                //if remain not enough
                if(tail == null){
                    res.head = dummy.nxt;
                    return res;
                }
            }
            //disconnect
            Node next = tail.nxt;
            tail.nxt = null;
            Node start = prev.nxt;
            prev.nxt = null;
            //get reverse part
            Node[] flip = helperK(start,tail);
            //connect
            prev.nxt = flip[0];
            flip[1].nxt = next;

            prev = flip[1];
        }
        res.head = dummy.nxt;
        return  res;
    }

    public static void main(String[] args) {
        Linklist test = new Linklist(new Node(1));
        test.add(new Node(2));
        test.add(new Node(3));
        test.add(new Node(4));
        test.add(new Node(5));
        reverse_by_k(test,3).print();
    }
}
