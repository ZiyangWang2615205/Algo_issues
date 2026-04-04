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
    public static int landsNum(int[][] graph){
        if(graph.length == 0) return 0;
        int count = 0;
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                if(dfsLandsNum(graph,i,j)){
                    count++;
                }
            }
        }
        return count;
    }
    public static boolean dfsLandsNum(int[][] graph, int row, int col){
        if(row < 0 || row >= graph.length || col < 0 || col >= graph[0].length) return false;
        if(graph[row][col] == 0 || graph[row][col] == 2) return false;
        graph[row][col] = 2;
        //fill lands
        dfsLandsNum(graph,row-1,col);
        dfsLandsNum(graph,row+1,col);
        dfsLandsNum(graph,row,col-1);
        dfsLandsNum(graph,row,col+1);
        return true;
    }

    public static int perimeter(int[][] graph){
        if(graph.length == 0) return 0;
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                if(graph[i][j] == 1){
                    return dfsPerimeterLand(graph,i,j);
                }
            }
        }
        return 0;
    }
    public static int dfsPerimeterLand(int[][] graph, int row, int col){
        if(row < 0 || col < 0 || row >= graph.length || col >= graph[0].length) return 1;
        if(graph[row][col] == 0) return 1;
        if(graph[row][col] == -1) return 0;
        graph[row][col] = -1;
        return dfsPerimeterLand(graph,row-1,col) + dfsPerimeterLand(graph,row+1,col) + dfsPerimeterLand(graph,row,col-1) + dfsPerimeterLand(graph,row,col+1);
    }

    //pour water
    public static boolean existWater(int container_A, int container_B, int target){
        if(target == 0) return true;
        return dfsExistWater(container_A,0,container_B,0,target);
    }
    public static boolean dfsExistWater(int container_A, int current_A, int container_B, int current_B, int target){
        //boundary
        if(current_A > container_A || current_B > container_B) return false;
        //terminal
        if(current_A == target || current_B == target) return true;
        //container A full of water
        boolean fullA = dfsExistWater(container_A,container_A,container_B,current_B,target);
        //container B full of water
        boolean fullB = dfsExistWater(container_A,current_A,container_B,container_B,target);

        //A -> B
        if(current_A + current_B <= container_B){
            current_B += current_A;
            boolean clearA =  dfsExistWater(container_A,0,container_B,current_B,target);
        }
        if(current_A + current_B > container_B){
            int diff = container_B - current_B;
            boolean AToB = dfsExistWater(container_A,current_A-diff,container_B,container_B,target);
        }

        //B -> A
        if(current_A + current_B <= container_A){
            current_A += current_B;
            boolean clearB =  dfsExistWater(container_A,current_A,container_B,0,target);
        }
        if(current_A + current_B > container_A){
            int diff = container_A - current_A;
            boolean BToA = dfsExistWater(container_A,container_A,container_B,current_B-diff,target);
        }

    }
    public static void main(String[] args) {
        int[][] map = new int[][]{{1,0,1},{1,0,1},{0,1,0}};
        System.out.println(landsNum(map));
        System.out.println(perimeter(new int[][]{{0,1,0,0},{1,1,1,0},{0,1,0,0},{0,1,0,0}}));
    }
}
