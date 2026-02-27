package List;

public class Linklist {
    Node head;

    //constructor
    public Linklist(Node head){
        this.head = head;
    }
    public Linklist(){
        this.head = null;
    }

    //Basic method

    //print example : 1->2->3->null
    public void print(){
        if(head == null) return;
        Node cur = head;
        while(cur != null){
            System.out.print(cur.data + "->");
            cur = cur.nxt;
        }
        System.out.print("null");
    }

    //add
    public void add(Node addNode){
        if(head == null){
            head = addNode;
            return;
        }
        Node cur = head;
        while(cur.nxt != null){
            cur = cur.nxt;
        }
        cur.nxt = addNode;
    }

    //remove example : 1->2->3->2, enter 2, list become 1->3
    public void remove(int data){
        if(head == null) return;
        //if head should be removed
        while(head.data == data){
            head = head.nxt;
        }
        Node cur = head;
        while(cur.nxt != null){
            //1->2->3->2 => 1->3->2
            if(cur.nxt.data == data){
                cur.nxt = cur.nxt.nxt;
            }else{
                //avoiding null pointer
                cur = cur.nxt;
            }
        }
    }

    //reverse example : 1->2->3->null => 3->2->1->null
    public void reverse(){
       if(head == null || head.nxt == null) return;
       Node cur = head;
       Node prev = null;
       while(cur != null){
           Node next = cur.nxt;
           cur.nxt = prev;
           prev = cur;
           cur = next;
       }
       head = prev;
    }
}
