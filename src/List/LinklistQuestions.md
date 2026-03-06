# ***Linklist Questions***

##  *1.Node 和 linklist的建立*

在研究linklist问题之前，我们需要将基本的ListNode和Linklist构建出来：

```java
public class ListNode {
    int data;
    ListNode next;
    public ListNode(int data){
        this.data = data;
        this.next = null;
    }
}
```

这里我们默认每一个node都含有data。

```java
public class Linklist {
    ListNode head;

    public Linklist(int data){
        this.head = new ListNode(data);
    }

    public Linklist(){
        this.head = null;
    }
```

Linklist的建立可以设置头节点为空或不为空。

## *2.Basic method*

我们设置基本方法：print/add/rmParam/reverse便于我们之后测试。对于这四个basic method，由于都是只遍历一次list，并且都是in place，所以时间复杂度都是O(n)，空间复杂度是O(1)。

### `print`

```java
//print the list
public void print(){
    ListNode current = head;
    while(current != null){
        System.out.print(current.data + "->");
        current = current.next;
    }
    System.out.print("null");
}
```

### `add`

`add`细分为两种特殊情况，一种是正常输入参数data，在list末尾构建新的node，一种是把现有的node直接连接在list末尾。

```java
//add node in the end of linklist
public void add(int data){
    //special condition
    if(head == null){
        head = new ListNode(data);
    }else{
        ListNode current = head;
        while(current.next != null){
            current = current.next;
        }
        current.next = new ListNode(data);
    }
}

//if we want to add exist node to the linklist
public void add(ListNode node){
    if(head == null){
        head = node;
    }
    ListNode current = head;
    while(current.next != null){
        current = current.next;
    }
    current.next = node;
}
```

### `rmParam`

`rmParam`用于删除node，我们输入参数data，根据data删除相关的node。

```java
//remove the nodes which has data as parameter we enter
public void rmParam(int param){
     if(head == null) return;
     //if head should be removed
     while(head.data == param){
         head = head.next;
     }
     //normal
     ListNode current = head;
     //always delete the next node(if it should be removed)
     while(current.next != null){
         if(current.next.data == param){
             //remove the next node
             current.next = current.next.next;
         }else{
             current = current.next;
         }
     }
}
```

### `reverse`

```java
//reverse the linklist
public void reverse(){
     if(head == null || head.next == null) return;
     //normal
     ListNode current = head;
     ListNode prev = null;
     while(current != null){
         //create nxt to record next node avoiding missing it
         ListNode nxt = current.next;
         current.next = prev;
         //updates prev and current
         prev = current;
         current = nxt;
     }
     //updates head
     head = prev;
}
```

我们这里通过`list.reverse()`将整个list反转。





## *3.Simple linklist problems*

### `findMid` ：找出中间node

Example1 : 1->2->3->null        return: 2

Example2 : 1->2->3->4->null   return: 3

思路：用快慢指针来做，慢指针每次移动一个node，快指针每次移动两个nodes，当快指针到list的最后一个node/null，此时慢指针所在的位置就是middle node。

```java
//find the middle node in linklist
//for example: 1->2->null, mid = 2 and 1->2->3->null, mid = 2
public static ListNode findMid(ListNode head){
     if(head == null || head.next == null) return head;
     ListNode slow = head;
     ListNode fast = head;
     while(fast != null && fast.next != null){
         fast = fast.next.next;
         slow = slow.next;
     }
     return slow;
}
```

时间复杂度: O(n)，空间复杂度: O(1)

### `mergesort`：归并排序

即对于一个list进行递归地拆分成sublists，再通过对sublists里的nodes比大小，最终merge成一个sorting list的操作。

先完成`merge`这一部份。

#### `merge`

Example:  list1: 1->2->4->6->null

​		  list2: 1->3->5->7->null

`merge` => list:   1->1->2->3->4->5->6->7->null

思路：先创建新的Linklist，然后分别在两个list里放入指针分别遍历，对每个node比大小，把小的放入新list里。

```java
//merge: making a sorting linklist based on two given sorting linklist
public static Linklist merge(Linklist l1, Linklist l2){
     if(l1.head == null) return l2;
     if(l2.head == null) return l1;
     //create merge list with dummy -1
     Linklist merge = new Linklist(-1);
     ListNode cur1 = l1.head;
     ListNode cur2 = l2.head;
     ListNode cur3 = merge.head;
     while(cur1 != null && cur2 != null){
         if(cur1.data <= cur2.data){
             cur3.next = new ListNode(cur1.data);
             cur1 = cur1.next;
             cur3 = cur3.next;
         }else{
             cur3.next = new ListNode(cur2.data);
             cur2 = cur2.next;
             cur3 = cur3.next;
         }
     }
     //add leave nodes directly
    while(cur1 != null){
        cur3.next = new ListNode(cur1.data);
        cur1 = cur1.next;
        cur3 = cur3.next;
    }
    while(cur2 != null){
        cur3.next = new ListNode(cur2.data);
        cur2 = cur2.next;
        cur3 = cur3.next;
    }
    //delete dummy
    merge.head = merge.head.next;
    return merge;
}
```

时间复杂度: O(n+m)，空间复杂度: O(n+m)



#### `mergesort`

Example: 1->7->3->5->2->4->6->1->null

`mergesort`=> 1->1->2->3->4->5->6->7->null

思路：采用Divide and conquer的方法，先找出middle node然后分成两个sublists，再递归继续分，最后调用`merge`的方法得到sorting list。

```java
//combine merge and findMid to finish mergesort
public static Linklist mergesort(Linklist list){
     if(list.head == null || list.head.next == null) return list;
     //divide based on mid
     ListNode mid = findMid(list.head);
     Linklist left = new Linklist();
     Linklist right = new Linklist();
     ListNode current = list.head;
     //divide into two parts
     while(current != mid){
         left.add(current.data);
         current = current.next;
     }
     while(current != null){
         right.add(current.data);
         current = current.next;
     }
     //merge by recursion
     Linklist leftSort = mergesort(left);
     Linklist rightSort = mergesort(right);
     return merge(leftSort,rightSort);
}
```

时间复杂度: O(nlogn)，空间复杂度: O(nlogn)



### `oddFront`:  奇数在前

即我们把一个list里的所有奇数都放在前面，偶数都放在后面。

Example：1->2->3->4->5->null

`oddFront` => 1->3->5->2->4->null

思路：创建两个list，odd和even，用一个指针遍历list，把奇数放到odd，偶数放到even，最后把even接到odd之后。

```java
//move odd in the front of list
public static Linklist oddFront(Linklist list){
     if(list.head == null || list.head.next == null) return list;
     Linklist odd = new Linklist();
     Linklist even = new Linklist();
     ListNode current = list.head;
     //divide list to odd part and even part
     while(current != null){
         if(current.data % 2 != 0){
             odd.add(current.data);
             current = current.next;
         }else{
             even.add(current.data);
             current = current.next;
         }
     }
     //add even to the end of odd part
     current = even.head;
     while(current != null){
         odd.add(current.data);
         current = current.next;
     }
     return odd;
}
```

时间复杂度: O(n)，空间复杂度: O(n)



### `resort`： 后半段reverse再间隔连接

Example：1->2->3->4->5->null

`resort` => 1->5->2->4->3->null

思路：将前后段分开，后段取出单独reverse，在重新连接list

```java

public static Linklist resort(Linklist list){
     if(list.head == null || list.head.next == null) return list;
     ListNode mid = findMid(list.head);
     //spilt
     Linklist right = new Linklist();
     right.head = mid.next;
     //break point avoiding unstoppable loop
     mid.next = null;
     Linklist left = new Linklist();
     left.head = list.head;
     //reverse part
     right.reverse();
     //connection part
     ListNode first = left.head;
     ListNode second = right.head;
     //based on findMid, we know left is always larger than right
     //therefore second will run out first
     while(second != null){
         //record next node
         ListNode nxtFirst = first.next;
         ListNode nxtSecond = second.next;
         //connect
         first.next = second;
         second.next = nxtFirst;
         //updates
         first = nxtFirst;
         second = nxtSecond;
     }
     return left;
}
```

时间复杂度: O(n)，空间复杂度: O(n)



### `commonNode`: 寻找公共node

假设有两个list穿过一个common node，我们需要return这个node。

思路：我们设置两个指针分别从两个list的head开始往后遍历，当遍历完该list之后，紧接着连接另一个list，当两个指针相交的时候，该节点就是common node。

```java
//find a common node for two linklist
public static ListNode commonNode(Linklist l1, Linklist l2){
     if(l1.head == null || l2.head == null) return null;
     ListNode cur1 = l1.head;
     ListNode cur2 = l2.head;
     //when cur1 meet cur2 that node is common node
     while(cur1 != cur2){
         cur1 = (cur1 == null)? l2.head : cur1.next;
         cur2 = (cur2 == null)? l1.head : cur2.next;
     }
     return cur1;
}
```

时间复杂度: O(n+m)，空间复杂度: O(1)



### `isRing`: 判断list是否成环

思路：运用快慢指针，如果list成环，快指针一定会重新与慢指针相交

```java
//judge whether linklist is ring or not
public static boolean isRing(Linklist list){
     if(list.head == null || list.head.next == null) return false;
     //fast and slow start at different node in order to get in loop
     ListNode fast = list.head.next;
     ListNode slow = list.head;
     while(fast != slow){
         if(fast == null || fast.next == null) return false;
         fast = fast.next.next;
         slow = slow.next;
     }
     return true;
}
```

时间复杂度: O(n)，空间复杂度: O(1)



### `ringEntryNode`: 找出入环点

思路：基于`isRing`的方法，当快慢指针相遇之后，我们让快指针重新回到head，但这次跟慢指针一样one by one遍历，当他们第二次相交的时候，该节点就是入环点。

```java
//find the ring entry node
public static ListNode ringEntryNode(Linklist list){
     if(!isRing(list)) return null;
     ListNode fast = list.head.next;
     ListNode slow = list.head;
     //first round
     while(fast != slow){
         //slow must go first
         slow = slow.next;
         fast = fast.next.next;
     }
     //reset fast
     fast = list.head;
     slow = slow.next;
     //second round
     while(fast != slow){
         slow = slow.next;
         fast = fast.next;
     }
     return fast;
}
```

时间复杂度: O(n)，空间复杂度: O(1)



## *4. A Little complicated Linklist problems*

这一节的算法题主要麻烦在链表的拆分和连接上。

### `reverseByCouple`: reverse two by two

Example：1->2->3->4->5->null

`reverseByCouple`=> 2->1->4->3->5->null

```java
public static Linklist reverseByCouple(Linklist list){
     if(list.head == null || list.head.next == null) return list;
     //set dummy to record head
     ListNode dummy = new ListNode(-1);
     dummy.next = list.head;
     //set pointer
     ListNode prev = dummy;
     while(prev.next != null && prev.next.next != null){
         ListNode current = prev.next;
         ListNode nxt = prev.next.next;
         //connect
         prev.next = nxt;
         current.next = nxt.next;
         nxt.next = current;
         //updates prev
         prev = current;
     }
     Linklist res = new Linklist();
     res.head = dummy.next;
     return res;
}
```

时间复杂度: O(n)，空间复杂度: O(1)



### `reverseByK`: reverse k by k

Example: if k = 2 `reverseByK` => `reverseByCouple`

​		 if k = 3，1->2->3->4->5->null `reverseByk`=> 3->2->1->4->5->null

```java
//reverse depend on k
//example: if k=2 reverse by couple, if k=3 then 1->2->3->4 => 3->2->1->4
//updates reverse method which can help us reverse only based on given head and tail
public static ListNode[] reverse(ListNode head, ListNode tail){
    //reverse the whole linklist
    ListNode prev = null;
    ListNode current = head;
    while(current != null){
        ListNode nxt = current.next;
        current.next = prev;
        prev = current;
        current = nxt;
    }
    //now 1->2->3 => 1<-2<-3, what we need to do is to return new "head" and "tail"
    return new ListNode[]{tail,head};
}
public static Linklist reverseByK(Linklist list, int k){
     if(list.head == null || list.head.next == null) return list;
     if(k<2) return list;
     Linklist res = new Linklist();
     //set dummy to record head
     ListNode dummy = new ListNode(-1);
     dummy.next = list.head;
     ListNode prev = dummy;
     while(prev.next != null && prev.next.next != null){
         ListNode tail = prev;
         ListNode head = prev.next;
         //find tail depend on k
         for (int i = 0; i < k; i++) {
             tail = tail.next;
             //if remain is not enough
             if(tail == null){
                 res.head = dummy.next;
                 return res;
             }
         }
         //record next node
         ListNode nxt = tail.next;
         //break head and tail
         prev.next = null;
         tail.next = null;
         //reverse
         ListNode[] reverse = reverse(head,tail);
         head = reverse[0];
         tail = reverse[1];
         //connect
         prev.next = head;
         tail.next = nxt;
         //updates
         prev = tail;
     }
     res.head = dummy.next;
     return res;
}
```

时间复杂度: O(n)，空间复杂度: O(1)
