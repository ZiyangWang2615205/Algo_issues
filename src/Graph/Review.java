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
    //find num of land

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1,0,3},{4,5,6},{7,8,9}};
        setZeroInPlace(matrix);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j]);
            }
        }
    }
}
