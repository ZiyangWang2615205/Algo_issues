package Tree;

import java.util.*;

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
    public static int path_sum(Node root, int prev){
        if(root == null) return 0;
        int cur = prev * 10 + root.data;
        if(root.left == null && root.right == null){
            return cur;
        }else{
            int leftSum = path_sum(root.left,cur);
            int rightSum = path_sum(root.right,cur);
            return leftSum+rightSum;
        }
    }
    public static int calc_path_sum(Node root){
        return path_sum(root,0);
    }

    //target_Eql_pathSum:
    //Example: 	1
    //
    //		2		3
    //
    //target = 3
    //
    //=>`targetEqualPS`  return 1
    //calc how many pathSum = target
    public static int num_Of_paths(Node root, int target, int prev){
        if(root == null) return 0;
        int count = 0;
        int cur = root.data + prev;
        if(root.left == null && root.right == null){
            if(cur == target) count++;
        }else{
            count += num_Of_paths(root.left,target,cur);
            count += num_Of_paths(root.right,target,cur);
        }
        return count;
    }
    public static int target_Eql_pathSum(Node root, int target){
        return num_Of_paths(root,target,0);
    }

    //target_pathSum_anyway
    //Same as the last one, but it does not require calculating the path sum starting from the root.
    //Example: 	1
    //
    //		2		3
    //
    //target = 3
    //
    //=>`target_pathSum_anyway` return 2
    public static int target_pathSum_anyway (Node root, int target){
        if(root == null) return 0;
        int sum = num_Of_paths(root,target,0);
        sum += target_pathSum_anyway(root.left,target);
        sum += target_pathSum_anyway(root.right,target);
        return sum;
    }

    //BST_to_preorder
    //Example: 	3					3
    //
    //		1		2 	   =>			1
    //
    //									    2
    public static List<Integer> BST_to_preorder(Node root){
        //preorder BST and add each node in list
        return stackPreorder(root);
    }

    public static void BST_to_preorder_bruce(Node root){
        if(root == null) return;
        if(root.left == null && root.right == null) return;

        Node cur = root;
        while(cur != null){
            if(cur.left != null){
                //record break point
                Node leftBreak = cur.left;
                Node rightConnect = cur.left;

                //find the rightest points
                while(rightConnect.right != null){
                    rightConnect = rightConnect.right;
                }

                //connect
                rightConnect.right = cur.right;
                cur.left = null;
                cur.right = leftBreak;
            }else{
                cur = cur.right;
            }
        }
    }

    //kth_smallestOfBST:
    //find the node of BST which is the k th smallest
    public static Node kth_smallestOfBST(Node root, int k){
        if(root == null || k < 1) return null;
        if(root.left == null && root.right == null) return root;

        Stack<Node> stack = new Stack<>();
        int count = 0;
        Node cur = root;

        while(!stack.isEmpty() || cur != null){
            while(cur != null){
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.pop();
            count++;
            if(count == k) return cur;
            cur = cur.right;
        }

        return null;
    }

    //commonAncestor_BST
    public static Node commonAncestor_BST(Node root,Node n1,Node n2){
        if(root == null) return null;
        //don't consider equal situation, otherwise we will miss this:
        //          20
        //
        //      10(n1)
        //
        //  5(n2)
        if(root.data > n1.data && root.data > n2.data){
            return commonAncestor_BST(root.right,n1,n2);
        }
        if(root.data < n1.data && root.data < n2.data){
            return commonAncestor_BST(root.left,n1,n2);
        }
        return root;
    }

    public static Node commonAncestor_BST_iter(Node root, Node n1, Node n2){
        if(root == null) return null;
        Node cur = root;
        while(cur != null){
            //right side
            if(cur.data < n1.data && cur.data < n2.data){
                cur = cur.right;
            }else if(cur.data > n1.data && cur.data > n2.data){
                cur = cur.left;
            }else{
                break;
            }
        }
        return cur;
    }

    //commonAncestor
    //Example: 	2
    //
    //		1		3
    //
    //    4   5
    //
    //Enter: 4,3=>`commonAncestor` return 2 ; Enter: 4,5 => return 1
    public static Node commonAncestor(Node root, Node n1, Node n2){
        //termination
        if(root == null) return null;
        if(root == n1 || root == n2) return root;

        //recursion
        Node left = commonAncestor(root.left,n1,n2);
        Node right = commonAncestor(root.right,n1,n2);

        //no res
        if(left == null && right == null) return null;
        //one side
        if(left == null) return right;
        if(right == null) return left;
        //both side
        return root;
    }

    //level order
    //Example: 	1
    //
    //		2		3
    //
    //=>`level_order` return [1,2,3]
    public static List<Integer> level_order(Node root){
        if(root == null) return new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            Node cur = queue.poll();
            res.add(cur.data);
            if(cur.left != null) queue.offer(cur.left);
            if(cur.right != null) queue.offer(cur.right);
        }
        return res;
    }

    //level order
    //Example: 	1
    //
    //		2		3
    //
    //=>`level_order` return [[1],[2,3]]
    public static List<List<Integer>> levelOrder_layer(Node root){
        if(root == null) return new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int length = queue.size();
            List<Integer> layer = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                Node cur = queue.poll();
                layer.add(cur.data);
                if(cur.left != null) queue.offer(cur.left);
                if(cur.right != null) queue.offer(cur.right);
            }
            res.add(layer);
        }
        return res;
    }

    //serialize
    //input: level order tree => output: string
    //Example: 	1
    //
    //		2		3
    //
    //    4            5
    //=>`serialize` return "1,2,3,4,null,null,5,null,null,null,null"
    public static String serialize_bfs(Node root){
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
                queue.offer(cur.left);
                queue.offer(cur.right);
            }
            sb.append(",");
        }
        return sb.substring(0,sb.length()-1);
    }

    //serialize
    //input: pre order tree => output: string
    //Example: 	1
    //
    //		2		3
    //
    //    4            5
    //=>`serialize` return "1,2,4,null,null,null,3,null,5,null,null"

    public static String serialize_dfs(Node root){
        if(root == null) return "null";
        return root.data + "," + serialize_dfs(root.left) + "," + serialize_dfs(root.right);
    }

    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.right.right = new Node(5);
        System.out.println(levelOrder_layer(root));
        System.out.println(serialize_bfs(root));
        System.out.println(serialize_dfs(root));
    }
}
