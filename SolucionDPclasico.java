import java.io.*;
import java.util.Arrays;

public class SolucionDPclasico {
    public static int calcularCreatividad(int n, int[] P) {
        int creatividad = 0;
        int temp = n;
        for (int pos = 0; pos < 5; pos++) {
            int digit = temp % 10;
            if (digit == 3) creatividad += P[pos];
            else if (digit == 6) creatividad += 2 * P[pos];
            else if (digit == 9) creatividad += 3 * P[pos];
            temp /= 10;
            if (temp == 0) break; // ya no quedan dígitos
        }
        return creatividad;
    }

    public static int noDigitDP(int n, int k, int[] P) {
        // Generar candidatos con creatividad > 0
        int[] creatividad = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            creatividad[i] = calcularCreatividad(i, P);
        }
        int[] candVal = new int[n + 1];
        int[] candCreat = new int[n + 1];
        int cnt = 0;
        for (int v = 1; v <= n; v++) {
            if (creatividad[v] > 0) {
                candVal[cnt] = v;
                candCreat[cnt] = creatividad[v];
                cnt++;
            }
        }
        // DP de dos dimensiones: dp[celda][suma]
        int[][] dp = new int[k + 1][n + 1];
        for (int i = 0; i <= k; i++) Arrays.fill(dp[i], Integer.MIN_VALUE / 2);
        dp[0][0] = 0;
        for (int celda = 1; celda <= k; celda++) {
            for (int suma = 0; suma <= n; suma++) {
                for (int idx = 0; idx < cnt; idx++) {
                    int v = candVal[idx];
                    int c = candCreat[idx];
                    if (suma >= v && dp[celda - 1][suma - v] != Integer.MIN_VALUE / 2) {
                        dp[celda][suma] = Math.max(dp[celda][suma], dp[celda - 1][suma - v] + c);
                    }
                }
                // También puede no usar ningún candidato en esta celda
                dp[celda][suma] = Math.max(dp[celda][suma], dp[celda - 1][suma]);
            }
        }
        
        return dp[k][n] < 0 ? 0 : dp[k][n];
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
                resultados[test] = noDigitDP(n, k, new int[]{P0, P1, P2, P3, P4});
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
