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

