// Integrantes: Sebastián Robles - , Juan Camilo Caldas - 202322445

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class proyectoDalgoP1 {

    public static int calcularCreatividad(int numero, int[] P){
        if (numero == 0) {
            return 0;
        }
        int creatividad = 0;
        int posicion = 0;
        while (numero > 0 && posicion < P.length) {
            int digito = numero % 10;
            if (digito == 3) {
                creatividad += P[posicion];
            } else if (digito == 6) {
                creatividad += 2 * P[posicion];
            } else if (digito == 9) {
                creatividad += 3 * P[posicion];
            }
            numero = numero / 10;
            posicion++;
        }
        return creatividad;
    }

    // ESTRATEGIA 1: DP completo para casos pequeños y medianos
    public static int estrategiaDPComplete(int n, int k, int[] posiciones) {
        // dp[celdas_usadas][suma_actual] = máxima creatividad
        int[][] dp = new int[k + 1][n + 1];
        for (int i = 0; i <= k; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }
        dp[0][0] = 0; // Caso base

        // Precalcular números útiles para optimización
        ArrayList<Integer> numerosUtiles = new ArrayList<>();
        ArrayList<Integer> creatividadesUtiles = new ArrayList<>();
        numerosUtiles.add(0);
        creatividadesUtiles.add(0);
        
        for (int i = 1; i <= n; i++) {
            int creatividad = calcularCreatividad(i, posiciones);
            if (creatividad > 0) {
                numerosUtiles.add(i);
                creatividadesUtiles.add(creatividad);
            }
        }
        
        // DP principal con optimización de transiciones
        for (int celda = 1; celda <= k; celda++) {
            // Recorrer sumas en orden inverso para evitar usar valores ya actualizados
            for (int suma = n; suma >= 0; suma--) {
                if (dp[celda - 1][suma] == Integer.MIN_VALUE) continue;
                
                int creatividadActual = dp[celda - 1][suma];
                
                // Optimización: solo considerar números útiles
                for (int idx = 0; idx < numerosUtiles.size(); idx++) {
                    int numero = numerosUtiles.get(idx);
                    int creatividadNumero = creatividadesUtiles.get(idx);
                    
                    int nuevaSuma = suma + numero;
                    if (nuevaSuma <= n) {
                        int nuevaCreatividad = creatividadActual + creatividadNumero;
                        dp[celda][nuevaSuma] = Math.max(dp[celda][nuevaSuma], nuevaCreatividad);
                    }
                }
            }
        }
        
        // Encontrar la máxima creatividad para suma exacta n
        int resultado = Integer.MIN_VALUE;
        for (int celda = 1; celda <= k; celda++) {
            if (dp[celda][n] != Integer.MIN_VALUE) {
                resultado = Math.max(resultado, dp[celda][n]);
            }
        }
        
        return resultado == Integer.MIN_VALUE ? 0 : resultado;
    }
    
    // ESTRATEGIA 2: Greedy optimizada para casos grandes
    public static int estrategiaGreedy(int n, int k, int[] posiciones) {
        // Recopilar todas las creatividades disponibles
        ArrayList<Integer> creatividades = new ArrayList<>();
        
        for (int i = 1; i <= n; i++) {
            int creatividad = calcularCreatividad(i, posiciones);
            if (creatividad > 0) {
                creatividades.add(creatividad);
            }
        }
        // Verificar factibilidad: ¿es posible formar suma n?
        boolean[] posible = new boolean[n + 1];
        posible[0] = true;
        
        for (int i = 1; i <= n; i++) {
            int creatividad = calcularCreatividad(i, posiciones);
            if (creatividad > 0 || i == 0) {
                for (int j = n; j >= i; j--) {
                    if (posible[j - i]) {
                        posible[j] = true;
                    }
                }
            }
        }
        if (!posible[n]) {
            return 0; // No es posible formar la suma n
        }
        // Ordenar creatividades de mayor a menor y tomar las k mejores
        Collections.sort(creatividades, Collections.reverseOrder());
        int suma = 0;
        int limite = Math.min(k, creatividades.size());
        for (int i = 0; i < limite; i++) {
            suma += creatividades.get(i);
        }
        return suma;
    }

    public static int problemaP1solucion(int n, int k, int P0, int P1, int P2, int P3, int P4) {
        int[] posiciones = new int[]{P0, P1, P2, P3, P4};
        
        // Decidir qué estrategia usar basado en el tamaño del problema
        if (k >= 500 || n > 10000) {
            // Para casos grandes: usar estrategia greedy (O(n²))
            return estrategiaGreedy(n, k, posiciones);
        } else {
            // Para casos pequeños/medianos: usar DP completo (O(k×n²))
            return estrategiaDPComplete(n, k, posiciones);
        }
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
                out.println(resultados[i]);
            }
            out.flush();
        }
    }
}