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
**time**-complexity: **O(n^2)**

**space**-complexity: **O(n^2)**

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