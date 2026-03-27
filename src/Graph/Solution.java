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

    public static int[][] setZero_in_place(int[][] matrix){
        if(matrix.length == 0) return new int[][]{};
        //used for check heading set zero
        boolean col = false, row = false;
        for (int i = 0; i < matrix.length; i++) {
            if(matrix[i][0] == 0){
                col = true;
                break;
            }
        }
        for (int i = 0; i < matrix[0].length; i++) {
            if(matrix[0][i] == 0){
                row = true;
                break;
            }
        }

        //transverse subgraph
        for (int i = 1; i < matrix.length ; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if(matrix[i][j] == 0){
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        //set zero of subgraph
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if(matrix[i][0] == 0 || matrix[0][j] == 0) matrix[i][j] = 0;
            }
        }

        //set zero of heading
        if(col){
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }

        if(row){
            for (int i = 0; i < matrix[0].length; i++) {
                matrix[0][i] = 0;
            }
        }

        return matrix;
    }

    //Sea and Land graph problems
    //knowing that sea-0, land-1

    //maxLandArea: return the maximum land area based on given graph
    public static int dfsMaxLand(int[][] graph, int row, int col){
        if(row >= graph.length || col >= graph[0].length || row < 0 || col < 0) return 0;
        //except for sea and visited land
        if(graph[row][col] == 0 || graph[row][col] == 2) return 0;

        graph[row][col] = 2;
        return 1+dfsMaxLand(graph,row-1,col)+dfsMaxLand(graph,row+1,col)+dfsMaxLand(graph,row,col-1)+dfsMaxLand(graph,row,col+1);
    }
    public static int maxLandArea(int[][] graph){
        if(graph.length == 0) return 0;
        int res = 0;
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                if(graph[i][j] == 1){
                    res = Math.max(res,dfsMaxLand(graph,i,j));
                }
            }
        }
        return res;
    }

    //calc num of land in graph
    public static int numOfLand(int[][] graph){
        if(graph.length == 0) return 0;
        int count = 0;
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                if(dfsNumOfLand(graph,i,j)){
                    count++;
                }
            }
        }
        return count;
    }
    public static boolean dfsNumOfLand(int[][]graph, int row, int col){
        //over edge or sea or accessed land will be passed
        if(row >= graph.length || col >= graph[0].length || row < 0 || col < 0) return false;
        if(graph[row][col] == 0 || graph[row][col] == 2) return false;
        //if it is land label itself and its neighbor
        graph[row][col] = 2;
        dfsNumOfLand(graph,row-1,col);
        dfsNumOfLand(graph,row+1,col);
        dfsNumOfLand(graph,row,col-1);
        dfsNumOfLand(graph,row,col+1);
        return true;
    }

    //find the word in a char board (the char should be adjacent to others)
    public static boolean findWord(char[][] charBoard, String word){
        if(charBoard.length == 0) return false;
        //used record accessed element
        boolean[][] accessed = new boolean[charBoard.length][charBoard[0].length];
        for (int i = 0; i < charBoard.length; i++) {
            for (int j = 0; j < charBoard[0].length; j++) {
                if(dfsFindWord(charBoard,word,accessed,i,j,0)){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean dfsFindWord(char[][] charBoard, String word, boolean[][] accessed, int row, int col, int index){
        //terminal
        if(index == word.length()) return true;
        //boundary
        if(row < 0 || row >= charBoard.length || col < 0 || col >= charBoard[0].length) return false;
        if(accessed[row][col] || charBoard[row][col] != word.charAt(index)) return false;
        //label
        accessed[row][col] = true;
        boolean res = dfsFindWord(charBoard,word,accessed,row-1,col,index+1) ||
                      dfsFindWord(charBoard,word,accessed,row+1,col,index+1) ||
                      dfsFindWord(charBoard,word,accessed,row,col-1,index+1) ||
                      dfsFindWord(charBoard,word,accessed,row,col+1,index+1);
        //backtrack label
        accessed[row][col] = false;
        return res;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1,0,3},{8,9,4},{7,6,5}};
        //1.check clockwise
        System.out.println("spiralOrder: ");
        System.out.print(spiralOrder(matrix));
        //2.search target in incremental matrix
        int[][] increment = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        System.out.print("search target: ");
        System.out.println(searchTarget(increment, 2));
        //3.check build matrix
        System.out.print("build matrix: ");
        System.out.println(spiralOrder(buildMatrix(4)));
        //4.5.check set zero
        System.out.print("set zero: ");
        //System.out.println(spiralOrder(setZero(matrix)));
        System.out.println(spiralOrder(setZero_in_place(matrix)));
        //6.check max land area
        System.out.print("max land area: ");
        System.out.println(maxLandArea(new int[][]{{1, 0, 1}, {0, 0, 0}, {0, 0, 1}}));
        //7.check land num
        System.out.println("num of land: ");
        System.out.println(numOfLand(new int[][]{{1,0,1},{0,0,0},{0,0,1}}));
        //8.find the word in charBoard
        System.out.println("The word in board");
        System.out.println(findWord(new char[][]{{'a','b','c'},{'d','e','f'},{'g','h','j'}},"abehj"));
    }
}
