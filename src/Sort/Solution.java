package Sort;

public class Solution {
    //insertion sort
    public static int[] insertion(int[] arr){
        if(arr.length <= 1) return arr;
        for (int i = 1; i < arr.length-1; i++) {
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

    public static void main(String[] args) {
        int[] arr = new int[]{8,6,5,4,3,8,7};
        for(int i : insertion(arr)){
            System.out.print(i);
        }
    }
}
