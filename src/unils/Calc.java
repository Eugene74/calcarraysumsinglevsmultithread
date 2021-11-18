package unils;

public class Calc extends Thread {
    int[] arr;
    int start, end;
    long sum;

    public Calc(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    public void run() {
        for (int i = start; i < end; i++)
            sum += arr[i];
    }

    public long get() {
        return sum;
    }
}
