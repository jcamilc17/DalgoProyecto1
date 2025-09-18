import java.io.*;
import java.util.Arrays;

public class SoulucionDigitDP {

    public static int solucionProyecto(int n, int k, int[]P) {
        //Calcular creatividades
        // [0, R]-[0, L] => [L, R]
        // DP
        int[][] dp = new int[7][k + 1];
        for (int i = 0; i < dp.length; i++) Arrays.fill(dp[i], Integer.MIN_VALUE);
        dp[0][0] = 0;

        int[] digits = new int[6];
        int temp = n;
        for (int i = 0; i < 6; i++) {
            digits[i] = temp % 10;
            temp /= 10;
        }

        for (int pos = 0; pos < 6; pos++) {
            for (int cin = 0; cin <= k; cin++) {
                if (dp[pos][cin] == Integer.MIN_VALUE) continue;
                for (int cout = 0; cout <= k; cout++) {
                    int sp = digits[pos] + 10 * cout - cin;
                    if (sp < 0 || sp > 9 * k) continue;
                    int creatividad = 0;
                    if (pos < 5) {
                        creatividad = P[pos] * (sp / 3);
                    }
                    dp[pos + 1][cout] = Math.max(dp[pos + 1][cout], dp[pos][cin] + creatividad);
                }
            }
        }

        return dp[6][0];
    }

    public static void main(String[] args) throws Exception {
        try (
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            PrintWriter out = new PrintWriter(System.out);
        ) {
            // Leer número de casos de prueba
            int t = Integer.parseInt(scanner.nextLine().trim());
            int[] resultados = new int[t];
            long[] tiempos = new long[t];

            for (int test = 0; test < t; test++) {
                String line = "";
                // Saltar líneas vacías y asegurar que se lee una línea válida
                while (true) {
                    if (scanner.hasNextLine()) {
                        line = scanner.nextLine();
                        if (!line.trim().isEmpty()) {
                            break;
                        }
                    } else {
                        line = null;
                        break;
                    }
                }
                if (line == null || line.trim().isEmpty()) {
                    // Si no hay más líneas, pero no se han leído todos los casos, continuar con ceros
                    resultados[test] = 0;
                    tiempos[test] = 0;
                    continue;
                }

                // Parsear la línea: k n P0 P1 P2 P3 P4
                String[] parts = line.trim().split("\\s+");
                int[] datos = Arrays.stream(parts)
                    .mapToInt(Integer::parseInt)
                    .toArray();

                int k = datos[0];   // número de celdas
                int n = datos[1];   // suma total de energía
                int P0 = datos[2];  // creatividad posición 0
                int P1 = datos[3];  // creatividad posición 1
                int P2 = datos[4];  // creatividad posición 2
                int P3 = datos[5];  // creatividad posición 3
                int P4 = datos[6];  // creatividad posición 4

                long startTime = System.currentTimeMillis();
                resultados[test] = solucionProyecto(n, k, new int[]{P0, P1, P2, P3, P4});
                long endTime = System.currentTimeMillis();
                tiempos[test] = endTime - startTime;
            }

            // Escribir resultados al archivo de salida
            for (int i = 0; i < resultados.length; i++) {
                out.println(resultados[i] + " " + tiempos[i] + " ms");
            }
            out.flush();
        }
    }
}
