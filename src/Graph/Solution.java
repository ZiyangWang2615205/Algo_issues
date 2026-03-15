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

    //create the matrix and val is its own serial num by clockwise
    //example: enter n = 3:
    //         [1,2,3]
    //         [8,9,4]
    //         [7,6,5]
    public static int[][] buildMatrix(int n){
        if(n == 0) return new int[][]{};
        int[][] matrix = new int[n][n];
        //create 1->n*n
        int[] arr = new int[n*n];
        for (int i = 0; i < n*n; i++) {
            arr[i] = i+1;
        }
        //spiral order adding
        int left = 0, top = 0;
        int right = matrix[0].length-1;
        int bot = matrix.length-1;
        int index = 0;
        while(true){
            //l -> r
            for (int i = left; i <= right; i++) {
                matrix[top][i] = arr[index++];
            }
            if(++top > bot) break;
            //t -> b
            for (int i = top; i <= bot; i++) {
                matrix[i][right] = arr[index++];
            }
            if(--right < left) break;
            //r -> l
            for (int i = right; i >= left; i--) {
                matrix[bot][i] = arr[index++];
            }
            if(--bot < top) break;
            //b -> t
            for (int i = bot; i >= top; i--) {
                matrix[i][left] = arr[index++];
            }
            if(++left > right) break;
        }
        return matrix;
    }

    //setZero: if val of current grid = 0 then all grids of its col and row should be zero
    //example:
    //         [1,0,3]
    //         [8,9,4]
    //         [7,6,5]
    //
    // =>      [0,0,0]
    //         [8,0,4]
    //         [7,0,5]

    public static int[][] setZero(int[][] matrix){
        if(matrix.length == 0) return new int[][]{};
        int y_length = matrix.length;
        int x_length = matrix[0].length;
        boolean[] rowSetZero = new boolean[y_length];
        boolean[] colSetZero = new boolean[x_length];

        for (int i = 0; i < y_length; i++) {
            for (int j = 0; j < x_length; j++) {
                if(matrix[i][j] == 0){
                    rowSetZero[i] = true;
                    colSetZero[j] = true;
                }
            }
        }

        for (int i = 0; i < y_length; i++) {
            for (int j = 0; j < x_length; j++) {
                if(rowSetZero[i] || colSetZero[j]){
                    matrix[i][j] = 0;
                }
            }
        }

        return matrix;
    }
    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1,0,3},{8,9,4},{7,6,5}};
        //check clockwise
        System.out.println(spiralOrder(matrix));
        //search target in incremental matrix
        int[][] increment = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        //check build matrix
        System.out.println(spiralOrder(buildMatrix(4)));
        //check set zero
        System.out.println(spiralOrder(setZero(matrix)));
    }
}
