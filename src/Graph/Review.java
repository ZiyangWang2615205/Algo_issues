package Graph;

public class Review {
    //setZero in place
    public static void setZeroInPlace(int[][] matrix){
        if(matrix.length == 0) return;
        int right = matrix[0].length;
        int bot = matrix.length;
        int left = 0, top = 0;

        //check heading
        boolean col = false;
        boolean row = false;

        for (int i = top; i < bot; i++) {
            if (matrix[i][0] == 0) {
                col = true;
                break;
            }
        }

        for (int i = left; i < right; i++) {
            if (matrix[0][i] == 0) {
                row = true;
                break;
            }
        }

        for (int i = top+1; i < bot; i++) {
            for (int j = left+1; j < right; j++) {
                if(matrix[i][j] == 0){
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        for (int i = top; i < bot; i++) {
            for (int j = left; j < right; j++) {
                if(matrix[i][0] == 0 || matrix[0][j] == 0){
                    matrix[i][j] = 0;
                }
            }
        }

        //heading
        if(col){
            for (int i = top; i < bot; i++) {
                matrix[i][0] = 0;
            }
        }

        if(row){
            for (int i = left; i < right; i++) {
                matrix[0][i] = 0;
            }
        }

        return;
    }

    //find max area
    public static int maxArea(int[][] graph){
        if (graph.length == 0) return 0;
        int res = 0;
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                if(graph[i][j] == 1){
                    res = Math.max(res,dfsMaxArea(graph,i,j));
                }
            }
        }
        return res;
    }
    public static int dfsMaxArea(int[][] graph, int row, int col){
        if(row < 0 || row >= graph.length || col < 0 || col >= graph[0].length) return 0;
        if(graph[row][col] == 0 || graph[row][col] == 2) return 0;
        graph[row][col] = 2;
        return 1 + dfsMaxArea(graph,row-1,col) + dfsMaxArea(graph,row+1,col) + dfsMaxArea(graph,row,col-1) + dfsMaxArea(graph,row,col+1);
    }
    //find num of land

    public static void main(String[] args) {
        int[][] map = new int[][]{{1,0,1},{1,0,1},{0,1,0}};
        System.out.println(maxArea(map));
    }
}
