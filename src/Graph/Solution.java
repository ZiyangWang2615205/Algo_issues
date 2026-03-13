package Graph;

import java.util.*;

public class Solution {
    //clockwise_print
    public static List<Integer> spiralOrder(int[][] matrix){
        if(matrix.length == 0) return new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        int left =0 ,top = 0;
        int right = matrix[0].length-1;
        int bot = matrix.length-1;

        while(true){
            //from left to right
            for (int i = left; i <= right; i++) {
                res.add(matrix[top][i]);
            }
            if(++top > bot) break;

            //from top to bottom
            for (int i = top; i <= bot; i++) {
                res.add(matrix[i][right]);
            }
            if(--right < left) break;

            //from right to left
            for (int i = right; i >= left; i--) {
                res.add(matrix[bot][i]);
            }
            if(--bot < top) break;

            //from bot to top
            for (int i = bot; i >= top; i--) {
                res.add(matrix[i][left]);
            }
            if(++left > right) break;
        }
        return res;
    }

    //searchTarget: search target in incremental matrix(right>left,bot>top)
    public static boolean searchTarget(int[][] matrix, int target){
        if(matrix.length == 0) return false;
        //matrix info
        int top = 0, left = 0;
        int bot = matrix.length-1;
        int right = matrix[0].length-1;

        //search col
        int low = top;
        int high = bot;
        while(low <= high){
            int mid = (low+high)/2;
            if(matrix[mid][0] == target) return true;
            if(matrix[mid][0] < target){
                low = mid+1;
            }else{
                high = mid-1;
            }
        }
        int col = high;

        //search row
        low = left;
        high = right;
        while(low <= high){
            int mid = (low+high)/2;
            if(matrix[col][mid] == target) return true;
            if(matrix[col][mid] < target){
                low = mid+1;
            }else{
                high = mid-1;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1,2,3},{8,9,4},{7,6,5}};
        //check clockwise
        System.out.println(spiralOrder(matrix));
        //search target in incremental matrix
        int[][] increment = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        if(searchTarget(increment,6)){
            System.out.println("true");
        }
    }
}
