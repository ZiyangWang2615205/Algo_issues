package Tree;

import java.util.*;

public class Review {
    public static List<Integer> order(Node root){
        if(root == null) return new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        Node cur = root;
        while(cur != null || !stack.isEmpty()){
            //push left
            while(cur != null){
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            res.add(cur.data);
            cur = cur.right;
        }
        return res;
    }

    //arrToBST
    public static Node toBST(int[] arr, int left, int right){
        if(left > right) return null;
        int mid = (left+right)/2;
        Node root = new Node(arr[mid]);
        root.left = toBST(arr, left, mid-1);
        root.right = toBST(arr, mid+1,right);
        return root;
    }
    public static Node arrToBST(int[] arr){
        if(arr.length == 0) return null;
        Arrays.sort(arr);
        return toBST(arr,0,arr.length-1);
    }

    //isSymmetric
    public static boolean symmetric_aux(Node n1, Node n2){
        if(n1 == null && n2 == null) return true;
        if(n1 == null || n2 == null) return false;
        if(n1.data != n2.data) return false;
        return symmetric_aux(n1.left,n2.right) && symmetric_aux(n1.right,n2.left);
    }
    public static boolean isSymmetric(Node root){
        if(root == null) return true;
        if(root.left == null && root.right == null) return true;
        return symmetric_aux(root.left,root.right);
    }

    //calc_path_sum
    public static int auxOfPS(Node root, int prev){
        if(root == null) return 0;
        int cur = prev * 10 + root.data;
        if(root.left == null && root.right == null){
            return cur;
        }else{
            int leftSum = auxOfPS(root.left,cur);
            int rightSum = auxOfPS(root.right,cur);
            return leftSum+rightSum;
        }
    }
    public static int calcPathSum(Node root){
        return auxOfPS(root,0);
    }

    //targetEql
    public static int auxOfTarget(Node root, int prev, int target){
        if(root == null) return 0;
        int count = 0;
        int cur = prev + root.data;
        if(root.left == null && root.right == null){
            if(target == cur){
                count++;
            }
        }else {
            count += auxOfTarget(root.left,cur,target);
            count += auxOfTarget(root.right,cur,target);
        }
        return count;
    }
    public static int targetEql(Node root, int target){
        return auxOfTarget(root,0,target);
    }

    //target2
    public static int targetEql2(Node root, int target){
        if(root == null) return 0;
        int sum = auxOfTarget(root,0,target);
        sum += targetEql2(root.left,target);
        sum += targetEql2(root.right,target);
        return sum;
    }

    //bruce flatten
    public static void bruce_flatten(Node root){
        if(root == null) return;
        if(root.left == null && root.right == null) return;
        Node cur = root;
        while(cur != null){
            if(cur.left != null){
                Node leftBreak = cur.left;
                Node rightConnect = cur.left;

                while(rightConnect.right != null){
                    rightConnect = rightConnect.right;
                }

                rightConnect.right = cur.right;
                cur.left = null;
                cur.right = leftBreak;
            }else {
                cur = cur.right;
            }
        }
        return;
    }

    //commonAncestor
    public static Node commonAncestor(Node root, Node n1, Node n2){
        if(root == null) return null;
        if(root == n1 || root == n2) return root;

        Node left = commonAncestor(root.left,n1,n2);
        Node right = commonAncestor(root.right,n1,n2);

        if(left == null && right == null) return null;
        if(left == null) return right;
        if(right == null) return left;
        return root;
    }
    //commonAncestor_BST: both iter and recur method
    public static Node commonAncestor_iter_BST(Node root, Node n1, Node n2){
        if(root == null) return null;
        Node cur = root;
        while(cur != null){
           if(cur.data > n1.data && cur.data > n2.data){ //both left
               cur = cur.left;
           }else if(cur.data < n1.data && cur.data < n2.data){ //both right
               cur = cur.right;
           }else {
               break;
           }
       }
        return cur;
    }

    public static Node commonAncestor_recur_BST(Node root, Node n1, Node n2){
        if(root == null) return null;
        if(root.data < n1.data && root.data < n2.data){//both right
            return commonAncestor_recur_BST(root.right,n1,n2);
        }
        if(root.data > n1.data && root.data > n2.data){//both left
            return commonAncestor_recur_BST(root.left,n1,n2);
        }
        return root;
    }

    //serialize(bfs)
    public static String serialize(Node root){
        if(root == null) return "";
        StringBuilder sb = new StringBuilder();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            Node cur = queue.poll();
            if(cur == null){
                sb.append("null");
            }else{
                sb.append(cur.data);
                if(cur.left != null || cur.right != null){
                    queue.offer(cur.left);
                    queue.offer(cur.right);
                }
            }
            sb.append(",");
        }
        return sb.substring(0,sb.length()-1);
    }
    //deserialize
    //stackPostorder

    public static void main(String[] args) {
        Node root = new Node(3);
        root.left = new Node(2);
        root.left.left = new Node(1);
        root.right = new Node(5);
        System.out.println(serialize(root));
    }
}
