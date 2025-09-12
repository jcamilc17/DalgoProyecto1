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
}
