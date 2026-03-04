package Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Solution {
    //order method:
    //preorder: root-left-right
    public static void preorder(Node root){
        if(root != null){
            System.out.print(root.data);
            preorder(root.left);
            preorder(root.right);
        }
    }

    //inorder: left-root-right
    public static void inorder(Node root){
        if(root != null){
            inorder(root.left);
            System.out.print(root.data);
            inorder(root.right);
        }
    }

    //postorder: left-right-root
    public static void postorder(Node root){
        if(root != null){
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.data);
        }
    }

    //stackPreorder: use stack to finish preorder
    //left-root-right
    public static List<Integer> stackPreorder(Node root){
        if(root == null) return new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            Node cur = stack.pop();
            res.add(cur.data);
            //check right and left children
            //pay attention to order of adding
            if(cur.right != null) stack.push(cur.right);
            if(cur.left != null) stack.push(cur.left);
        }
        return res;
    }

    //stackInorder: left-root-right
    public static List<Integer> stackInorder(Node root){
        if(root == null) return new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        Node cur = root;
        while(cur != null || !stack.isEmpty()){
            //iter add left children
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

    //maxLevel:
    //          1
    //
    //		2		3
    //
    //					6
    //return level = 3
    public static int maxLevel(Node root){
        if(root == null) return 0;
        int leftDepth = maxLevel(root.left);
        int rightDepth = maxLevel(root.right);
        return Math.max(leftDepth,rightDepth) + 1;
    }

    //isBalance:
    //the difference of level between left children and right children should <= 1
    //level(l) - level(r) <= 1
    public static boolean isBalance(Node root){
        if(root == null) return true;
        //don't forget judge other node recursively
        return Math.abs(maxLevel(root.left)-maxLevel(root.right)) <= 1 && isBalance(root.left) && isBalance(root.right);
    }

    //isBST:
    //if tree is binary search tree then left child < root < right child
    //therefore, it should be incremented if output with inorder
    public static boolean isBST(Node root){
        if(root == null) return true;
        Stack<Node> stack = new Stack<>();
        int prev = Integer.MIN_VALUE;
        Node cur = root;
        //use stack to do inorder
        while(cur != null || !stack.isEmpty()){
            while(cur != null){
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if(cur.data <= prev){
                return false;
            }
            prev = cur.data;
            cur = cur.right;
        }
        return true;
    }

    //isBST could also be done by recursion
    static int prev = Integer.MIN_VALUE;
    public static boolean isBST_recur(Node root){
        if(root == null) return true;
        if(!isBST_recur(root.left)){
            return false;
        }
        if(root.data <= prev){
            return false;
        }
        prev = root.data;
        return isBST_recur(root.right);
    }

    //arrToBST:
    //change array to binary search tree
    public static Node toBSThelper(int[] arr, int left, int right){
        if(left > right) return null;
        int mid = (left+right)/2;
        Node root = new Node(arr[mid]);
        root.left = toBSThelper(arr,left,mid-1);
        root.right = toBSThelper(arr,mid+1,right);
        return root;
    }
    public static Node arrToBST(int[] arr){
        if(arr.length == 0) return null;
        Arrays.sort(arr);
        return toBSThelper(arr,0,arr.length-1);
    }

    //combineTree:
    //combine two trees by adding the values of nodes at the same positions
    public static Node combineTree(Node t1, Node t2){
        if(t1 == null) return t2;
        if(t2 == null) return t1;
        Node combine = new Node(t1.data + t2.data);
        combine.left = combineTree(t1.left,t2.left);
        combine.right = combineTree(t1.right,t2.right);
        return combine;
    }

    //isSymmetric:
    public static boolean symmetricHelper(Node node1, Node node2){
        //don't forget check boundary of two nodes at first
        if(node1 == null && node2 == null) return true;
        if(node1 == null || node2 == null) return false;
        if(node1.data != node2.data) return false;
        return symmetricHelper(node1.left,node2.right) && symmetricHelper(node1.right,node2.left);
    }
    public static boolean isSymmetric(Node root){
        if(root == null) return true;
        if(root.left == null && root.right == null) return true;
        return symmetricHelper(root.left,root.right);
    }

    //flipTree:
    //Example:	1					1
    //
    //		2		3	=>	    3		2
    public static Node flipTree(Node root){
        if(root == null) return null;
        if(root.left == null && root.right == null) return root;
        Node ansLeft = flipTree(root.right);
        Node ansRight = flipTree(root.left);
        root.left = ansLeft;
        root.right = ansRight;
        return root;
    }

    //calc_path_sum
    //Example: 	1
    //
    //		2		3
    //
    //=>`calcPathSum`  return 12+13=25

    public static int pathSum_aux(Node root, int prevSum){
        if(root == null) return 0;
        prevSum = prevSum * 10 + root.data;
        if(root.left == null && root.right == null){
            return prevSum;
        }else {
            int leftSum = pathSum_aux(root.left,prevSum);
            int rightSum = pathSum_aux(root.right,prevSum);
            return leftSum + rightSum;
        }
    }
    public static int calc_path_sum(Node root){
        if(root == null) return 0;
        return pathSum_aux(root,0);
    }

    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        System.out.println(calc_path_sum(root));
    }
}
