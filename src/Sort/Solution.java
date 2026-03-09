package Sort;

import java.util.Arrays;

public class Solution {
    //insertion sort
    public static int[] insertion(int[] arr){
        if(arr.length <= 1) return arr;
        for (int i = 1; i < arr.length; i++) {
            int j = i-1;
            int cur = arr[i];
            while(j >= 0 && cur < arr[j]){
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = cur;
        }
        return arr;
    }

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

    public static void main(String[] args) {
        int[] arr = new int[]{8,6,5,4,3,8,7};
        for(int i : insertion(arr)){
            System.out.print(i);
        }
        System.out.println();
        for(int i : merge_sort(arr)){
            System.out.print(i);
        }
    }
}
