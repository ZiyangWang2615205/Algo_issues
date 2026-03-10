package Sort;

public class Review {
    //insertion sort
    public static int[] insertion_sort(int[] arr){
        if(arr.length <= 1) return arr;
        for (int i = 0; i < arr.length; i++) {
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
    //quick sort

    public static void main(String[] args) {
        int[] arr = new int[]{8,6,5,4,3,8,7};
        for(int i : insertion_sort(arr)){
            System.out.print(i);
        }
    }
}
