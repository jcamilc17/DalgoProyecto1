import java.io.PrintWriter;
import java.util.Arrays;

public class ProblemaP1 {

    // Calcula la creatividad de un número
    public static int calcularCreatividad(int numero, int[] P) {
        if (numero == 0) return 0;
        int creatividad = 0;
        int posicion = 0;
        while (numero > 0 && posicion < P.length) {
            int digito = numero % 10;
            if (digito == 3) creatividad += P[posicion];
            else if (digito == 6) creatividad += 2 * P[posicion];
            else if (digito == 9) creatividad += 3 * P[posicion];
            numero /= 10;
            posicion++;
        }
        return creatividad;
    }

    public static int problemaP1solucion(int n, int k, int P0, int P1, int P2, int P3, int P4) {
        int[] P = {P0, P1, P2, P3, P4};

        // Generar candidatos (número, creatividad)
        int[][] candidatos = new int[n + 1][2];
        for (int i = 0; i <= n; i++) {
            int c = calcularCreatividad(i, P);
            if (c > 0 || i == 0) {
                candidatos[i][0] = i;
                candidatos[i][1] = c;
            }
        }

        // dp[j] = mejor creatividad para suma j con "i" elementos
        int[] dp = new int[n + 1];
        int[] next = new int[n + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0; // caso base

        for (int i = 1; i <= k; i++) {
            Arrays.fill(next, -1);
            for (int j = 0; j <= n; j++) {
                if (dp[j] == -1) continue;
                for (int[] cand : candidatos) {
                    int valor = cand[0];
                    int crea = cand[1];
                    if (j + valor <= n) {
                        next[j + valor] = Math.max(next[j + valor], dp[j] + crea);
                    }
                }
            }
            // swap
            int[] tmp = dp;
            dp = next;
            next = tmp;
        }

        return Math.max(0, dp[n]);
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
                resultados[test] = problemaP1solucion(n, k, P0, P1, P2, P3, P4);
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
