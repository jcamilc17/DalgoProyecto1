// Integrantes: Sebastián Robles - , Juan Camilo Caldas - 202322445

import java.util.ArrayList;
import java.util.HashMap;

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

    public static ArrayList<Integer> calcularCreatividades(ArrayList<Integer> candidatos, int P0, int P1, 
        int P2, int P3, int P4){ 
        int [] P = {P0, P1, P2, P3, P4};
        ArrayList<Integer> creatividades = new ArrayList<>();
        
        for (int candidato : candidatos) {
            int creatividad = calcularCreatividad(candidato, P);
            creatividades.add(creatividad);
        }
        
        return creatividades;
    }

    public static int problemaP1solucion(int n, int k, int P0, int P1, int P2, int P3, int P4){
        ArrayList<Integer> candidatos = new ArrayList<>();
        for (int i=0; i<=n; i++) {
            candidatos.add(i); // todos los números
        }
        ArrayList<Integer> creatividades = calcularCreatividades(candidatos, P0, P1, P2, P3, P4);
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
                for (int idx = 0; idx < candidatos.size(); idx++) {
                    int c = candidatos.get(idx);           // Obtener candidato
                    int creatividad = creatividades.get(idx); // Obtener su creatividad
                    
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
}
