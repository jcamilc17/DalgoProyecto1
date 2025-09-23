import java.util.Arrays;

public class tester {
    public static void main(String[] args) {
        testCorrectitud();
        //testPerformance();
    }

    // public static void testCorrectitud() {
    //     while (true) {
    //         int n = (int)(Math.random() * 100) + 1;
    //         int k = (int)(Math.random() * 100) + 1;
    //         // int P0 = (int)(Math.random() * 100);
    //         // int P1 = (int)(Math.random() * 100);
    //         // int P2 = (int)(Math.random() * 100);
    //         // int P3 = (int)(Math.random() * 100);
    //         // int P4 = (int)(Math.random() * 100);
    //         // int[] P = new int[]{P0, P1, P2, P3, P4};
    //         int[] P = new int[]{1, 2, 3, 4, 5};

    //         //int res1 = SolucionDPclasico.noDigitDP(n, k, P);
    //         int[] res3 = KnapsackFix.knapsackOKN(n, k, P);
    //         int[] res2 = dpOptimizado.knapsack(n, k, P);

    //         for (int i = 0; i <= n; i++) {
    //             if ( res2[i] != res3[i]) {
    //                 System.out.println("Error!");
    //                 System.out.println("n: " + n + " k: " + k + " P: " + Arrays.toString(P));
    //                 //System.out.println("DPclasico: " + res1);
    //                 System.out.println("dpOptimizado: " + res2[i] + " at " + i);
    //                 System.out.println("KnapsackFix: " + res3[i] + " at " + i);
    //                 return;
    //             }
    //         }
    //     }
    // }

    public static void testCorrectitud() {
        while (true) {
            int n = (int)(Math.random() * 10000) + 1;
            int k = (int)(Math.random() * 1000) + 1;
            int P0 = (int)(Math.random() * 100);
            int P1 = (int)(Math.random() * 100);
            int P2 = (int)(Math.random() * 100);
            int P3 = (int)(Math.random() * 100);
            int P4 = (int)(Math.random() * 100);
            int[] P = new int[]{P0, P1, P2, P3, P4};

            //int res1 = SolucionDPclasico.noDigitDP(n, k, P);
            int res3 = KnapsackFix.knapsackOKN(n, k, P);
            int res2 = dpOptimizado.knapsack(n, k, P);

            if ( res2 != res3) {
                System.out.println("Error!");
                System.out.println("n: " + n + " k: " + k + " P: " + Arrays.toString(P));
                //System.out.println("DPclasico: " + res1);
                System.out.println("dpOptimizado: " + res2);
                System.out.println("KnapsackFix: " + res3);
                break;
            } else {
                System.out.println("OK n: " + n + " k: " + k + " P: " + Arrays.toString(P) + " res: " + res2);
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
            int res2 = dpOptimizado.knapsack(n, k, P);
            long endTime2 = System.currentTimeMillis();

            System.out.println("n: " + n + " k: " + k + " P: " + Arrays.toString(P));
            System.out.println("dpOptimizado: " + res2 + " Time: " + (endTime2 - startTime2) + " ms");
        }
    }
}
