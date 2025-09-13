// Integrantes: Sebastián Robles - , Juan Camilo Caldas - 202322445

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class proyectoDalgoP1 {

    public static int calcularCreatividad(int numero, int[] P){
        if (numero == 0) {
            return 0;
        }
        int creatividad = 0;
        int posicion = 0;
        //int numeroOriginal = numero; // Guardar el número original para el mensaje
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
        // System.out.println("Creatividad del número: " + creatividad + " para el número: " + numeroOriginal);
        return creatividad;
    }

    public static int[] calcularCreatividades(int[] candidatos, int P0, int P1, 
        int P2, int P3, int P4){ 
        int [] P = {P0, P1, P2, P3, P4};
        int[] creatividades = new int[candidatos.length];
        
        for (int i = 0; i < candidatos.length; i++) {
            int candidato = candidatos[i];
            int creatividad = calcularCreatividad(candidato, P);
            creatividades[i] = creatividad;
        }
        
        return creatividades;
    }

    public static int problemaP1solucion(int n, int k, int P0, int P1, int P2, int P3, int P4){
        int[] candidatos = new int[n + 1];
        for (int i=0; i<=n; i++) {
            candidatos[i] = i; // todos los números
        }
        int[] creatividades = calcularCreatividades(candidatos, P0, P1, P2, P3, P4);
        int [][] dp = new int[k + 1][n + 1];
        for (int i = 0; i <= k; i++) {
            for (int j = 0; j <= n; j++) {
                dp[i][j] = -1;
            }
        }
        dp[0][0] = 0; //caso base

        for (int i = 1; i <= k; i++) {
            for (int j = 0; j <= n; j++) {
                // CORRECCIÓN 3: Usar índices para acceder a ArrayList
                for (int idx = 0; idx < candidatos.length; idx++) {
                    int c = candidatos[idx];           // Obtener candidato
                    int creatividad = creatividades[idx]; // Obtener su creatividad

                    if (c <= j && dp[i-1][j-c] != -1) {
                        dp[i][j] = Math.max(dp[i][j], dp[i-1][j-c] + creatividad);
                    }
                }
            }
        }
           
        if (dp[k][n] == -1) {
            return 0;
        } else {
            return dp[k][n];
        }
    }

    public static void main(String[] args) throws Exception {
        try (
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(System.out);
        ) {
            // Leer número de casos de prueba
            String line = br.readLine();
            int t = Integer.parseInt(line.trim());
            int[] resultados = new int[t];
            
            for (int test = 0; test < t; test++) {
                line = br.readLine();
                // Saltar líneas vacías
                while (line != null && line.trim().isEmpty()) {
                    line = br.readLine();
                }
                if (line == null) break;
                
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
                
                resultados[test] = problemaP1solucion(n, k, P0, P1, P2, P3, P4);
            }
            
            // Escribir resultados al archivo de salida
            for (int res : resultados) {
                out.println(res);
            }
            out.flush();
        }
    }
}
