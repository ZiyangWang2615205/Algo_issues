# _Graph Questions_

## 1. Basic Questions
首先是一些比较简单的问题，作为我们练习的切入点：
+ **print the graph clockwise**
+ **find the target in incremental matrix**
+ **create the matrix and val is its own serial num**
+ **if grid apper zero set its col and row as zero as well**

### `spiralOrder`
顾名思义，使用clockwise的顺序遍历graph即可。
```java
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
```
**time**-complexity: **O(mn)**

**space**-complexity: **O(mn)**

---
### `searchTarget`
即在一个**incremental martix**里寻找是否存在input值，若存在返回`true`，不存在返回`false`。
由于我们的matrix是**increment**的，因此我们可以采用 **_binary search_** 的思路缩小时间复杂度，分别对row/column进行搜索。
```java
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

```

**time**-complexity = **O(log m + log n)**

**space**-complexity = **O(1)**

---

### `buildMatrix`
思路就是按照spiral order的顺序添加elements即可

```java
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
```
**time**-complexity : **O(n^2)**

**space**-complexity : **O(n^2)**

---
### `setZero`
即如果当前元素为0，则他所在的column和row都要设置为0。
我们采用创建数组record需要存0的column和row:

```java
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
```
**time**-complexity: **O(mn)**

**space**-complexity: **O(m+n)**

当然鉴于这道题我暂时没有想到减小*time complexity*的办法，因此我试图去减少*space complexity*。

我们不再使用额外的数组去记录某一row/column是否应该清零，而是用the first row和the first column去替代先前额外数组的作用。

**但是要注意，the first column and row本身也可能需要清零，因此使用两个boolean判断一下是否需要清零。**

```java
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
```
**time**-complexity: **O(mn)**

**space**-complexity: **O(1)**

---
## 2. DFS Used Questions
接着是我们在graph里用dfs解决的一些问题：
+ **find the maximum land area in graph**
+ **calc the num of lands in graph**
+ **find the word in char board**
+ **fill the same colour in the same val grid**
+ **calc the perimeter of land**

---
## 3. Backtrack Used Questions
即在graph里使用backtrack解决的问题：