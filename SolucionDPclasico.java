import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolucionDPclasico {

    public static int noDigitDP(int n, int k, int[] P) {
        // Precalcular la creatividad de todos los números 1..n
        int[] creat = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            creat[i] = calcularCreatividad(i, P);
        }
        
        // Encontrar los números con creatividad > 0
        List<Integer> validNumbers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (creat[i] > 0) {
                validNumbers.add(i);
            }
        }
        
        // dp[i][j] = máxima creatividad usando i números para sumar j
        int[][] dp = new int[k + 1][n + 1];
        for (int i = 0; i <= k; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp[0][0] = 0;
        
        for (int num : validNumbers) {
            for (int i = k; i >= 1; i--) {
                for (int j = n; j >= num; j--) {
                    if (dp[i - 1][j - num] != -1) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - num] + creat[num]);
                    }
                }
            }
        }
        
        return Math.max(0, dp[k][n]);
    }
    
    public static int calcularCreatividad(int numero, int[] P) {
        if (numero == 0) return 0;
        
        int creatividad = 0;
        int posicion = 0;
        int temp = numero;
        
        while (temp > 0 && posicion < P.length) {
            int digito = temp % 10;
            if (digito == 3) {
                creatividad += P[posicion];
            } else if (digito == 6) {
                creatividad += 2 * P[posicion];
            } else if (digito == 9) {
                creatividad += 3 * P[posicion];
            }
            temp = temp / 10;
            posicion++;
        }
        return creatividad;
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
