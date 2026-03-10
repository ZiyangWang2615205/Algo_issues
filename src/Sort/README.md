# _Sort Questions_

## _1. Classify Sorting Method_

sorting method以`decision tree`作为分界线。

理由是sort原理基于`decision tree`的`time-complexity`上限为**O(nlogn)**。
反之，可能**高于O(nlogn)**或者**低于O(nlogn)**。因此`decision tree`可以作为一个很好的分界点。

## _2. Basic Sort Method_

### Insertion Sort

原理最简单的sorting method之一。

一个指针遍历数组，一个指针负责检查当前element是否小于前面已经排序好的subarray。

一旦发现小于前面的element，就要将当前element往前移动，直到到达合适的位置。


```java
 //insertion sort
    public static int[] insertion_sort(int[] arr){
        if(arr.length <= 1) return arr;
        for (int i = 0; i < arr.length; i++) {
            //prev used for check current element if it smaller than previous
            int prev = i-1;
            int cur = arr[i];
            while(prev >= 0 && cur <= arr[prev]){
                arr[prev+1] = arr[prev];
                prev--;
            }
            arr[prev+1] = cur;
        }
        return arr;
    }
```

**time**-complexity: **O(n^2)**

**space**-complexity: **O(1)/in-place**

---
### Mergesort

我们最早接触使用`divide and conquer`思想的sorting method。

正如`divide and conquer`的定义，mergesort会先把原array分成subarray。

然后对两个subarray里的element同时比大小，放入新array里。

for example: array被分成了A，B两个subarray， 对比方式为 A1/B1，若A1小则A1放入新array，
接着比A2/B1...

在Linklist Questions中我们也涉及过该方法，具体见[Direct to Linklist Mergesort](Tree/README.md/###mergesort:归并排序)

```java
 //merge sort -- divide and conquer
    public static int[] conquer(int[] left, int[] right){
        if(left.length == 0) return right;
        if(right.length == 0) return left;
        int[] res = new int[left.length+right.length];
        int lp = 0;
        int rp = 0;
        int index= 0;
        while(lp < left.length && rp < right.length){
            if(left[lp] <= right[rp]){
                res[index] = left[lp];
                lp++;
            }else{
                res[index] = right[rp];
                rp++;
            }
            index++;
        }
        while(lp < left.length){
            res[index] = left[lp];
            lp++;
            index++;
        }

        while(rp < right.length){
            res[index] = right[rp];
            rp++;
            index++;
        }
        return res;
    }
    public static int[] merge_sort(int[] arr){
        if(arr.length <= 1) return arr;
        int mid = arr.length/2;
        int[] left = Arrays.copyOfRange(arr,0,mid);
        int[] right = Arrays.copyOfRange(arr,mid,arr.length);
        left = merge_sort(left);
        right = merge_sort(right);
        return conquer(left,right);
    }
```
**time**-complexity: **O(nlogn)**

**space**-complexity: **O(n)**