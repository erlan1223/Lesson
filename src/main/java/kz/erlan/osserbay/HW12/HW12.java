package kz.erlan.osserbay.HW12;

import java.util.Arrays;
public class HW12 {
    private static final int SIZE = 10000000;
    private static final int HALF_SIZE = SIZE / 2;

    public float[] calculate(float[] arr) {
        for (int i = 0; i < arr.length; i++)
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5.0) * Math.cos(0.2f + i / 5.0) * Math.cos(0.4f + i / 2.0));
        return arr;
    }

    public void runOneThread() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1.0f);
        long a = System.currentTimeMillis();
        calculate(arr);
        System.out.println("Метод с одним потоком завершился за: " + (System.currentTimeMillis() - a) + " мс");
    }

    public void runTwoThreads() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1.0f);

        float[] arr1 = new float[HALF_SIZE];
        float[] arr2 = new float[HALF_SIZE];

        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, arr1, 0, HALF_SIZE);
        System.arraycopy(arr, HALF_SIZE, arr2, 0, HALF_SIZE);

        Thread t1 = new Thread(() -> {
            calculate(arr1);
        });

        Thread t2 = new Thread(() -> {
            calculate(arr2);
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(arr1, 0, arr, 0, HALF_SIZE);
        System.arraycopy(arr2, 0, arr, HALF_SIZE, HALF_SIZE);

        System.out.println("Метод с двумя потоками завершился за: " + (System.currentTimeMillis() - a) + " мс");
    }

    public static void main(String[] args) {
        HW12 o = new HW12();
        o.runOneThread();
        o.runTwoThreads();
    }
}
