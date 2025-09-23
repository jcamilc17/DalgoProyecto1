import java.util.Arrays;

public class tester {
    public static void main(String[] args) {
        testCorrectitud();
        //testPerformance();
    }

    public static void testCorrectitud() {
        while (true) {
            int n = (int)(Math.random() * 1000) + 1;
            int k = (int)(Math.random() * 100) + 1;
            int P0 = (int)(Math.random() * 100);
            int P1 = (int)(Math.random() * 100);
            int P2 = (int)(Math.random() * 100);
            int P3 = (int)(Math.random() * 100);
            int P4 = (int)(Math.random() * 100);
            int[] P = new int[]{P0, P1, P2, P3, P4};

            int res1 = SolucionDPclasico.noDigitDP(n, k, P);
            int res2 = dpOptimizado.knapsackOKN(n, k, P);

            if (res1 != res2) {
                System.out.println("Error!");
                System.out.println("n: " + n + " k: " + k + " P: " + Arrays.toString(P));
                System.out.println("DPclasico: " + res1);
                System.out.println("dpOptimizado: " + res2);
                break;
            } else {
                System.out.println("OK n: " + n + " k: " + k + " P: " + Arrays.toString(P) + " res: " + res1);
            }
        }
    }

    public static void testPerformance() {
        for (int i = 0; i < 10; i++) {
            int n = (int)(Math.random() * 100000) + 1;
            int k = (int)(Math.random() * 10000) + 1;
            int P0 = (int)(Math.random() * 100);
            int P1 = (int)(Math.random() * 100);
            int P2 = (int)(Math.random() * 100);
            int P3 = (int)(Math.random() * 100);
            int P4 = (int)(Math.random() * 100);
            int[] P = new int[]{P0, P1, P2, P3, P4};

            long startTime2 = System.currentTimeMillis();
            int res2 = dpOptimizado.knapsackOKN(n, k, P);
            long endTime2 = System.currentTimeMillis();

            System.out.println("n: " + n + " k: " + k + " P: " + Arrays.toString(P));
            System.out.println("dpOptimizado: " + res2 + " Time: " + (endTime2 - startTime2) + " ms");
        }
    }
}
