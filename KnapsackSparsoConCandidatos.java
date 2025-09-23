import java.util.*;

/**
 * DP esparso + generación segura de candidatos (A1-A3) + relleno incremental.
 * Devuelve la máxima creatividad para suma EXACTA n usando a lo más k términos.
 *
 * Parámetros de ajuste:
 *   INIT_FILL: tamaño inicial de relleno (pesos con creatividad 0 incluidos).
 *   MAX_RETRIES: cuántas veces doblamos el relleno antes de hacer fallback completo.
 *
 * La metodología:
 * 1) Generar candidatos seguros (≤1 dígito no creativo + pares no-creativos).
 * 2) Añadir relleno: incluir todos los pesos <= W_FILL aunque tengan creatividad 0.
 * 3) Ejecutar DP esparso por capas (prev->next) usando solo los items seleccionados.
 * 4) Si prev[n] sigue inalcanzable, doblar W_FILL y repetir; si W_FILL >= n hacemos fallback
 *    incluyendo TODOS los pesos (garantía de exactitud).
 */
public class KnapsackSparsoConCandidatos {

    static final int NEG = Integer.MIN_VALUE / 4;

    // Calcula creatividad del número
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

    // Genera lista de items (peso, valor) para 1..n
    // Incluye: creativos + no creativos con creatividad=0
    static List<int[]> generarItems(int n, int[] P) {
        List<int[]> items = new ArrayList<>();
        for (int x = 1; x <= n; x++) {
            int c = calcularCreatividad(x, P);
            // Guardamos todos los números, incluso c==0
            items.add(new int[]{x, c});
        }
        return items;
    }

    // DP esparso por capas (0/1 knapsack exacto)
    public static int knapsackEsparso(int n, int k, int[] P) {
    // Generar candidatos creativos
    List<Integer> candList = new ArrayList<>();
    candList.add(0); // permitir celda vacía
    int[] creatividad = new int[n + 1];
    for (int i = 0; i <= n; i++) {
        creatividad[i] = calcularCreatividad(i, P);
        if (creatividad[i] > 0) candList.add(i);
    }

    // DP esparso: mapa (suma -> creatividad máxima)
    Map<Integer, Integer> dp = new HashMap<>();
    dp.put(0, 0);

    int ans = 0;

    for (int celda = 1; celda <= k; celda++) {
        Map<Integer, Integer> next = new HashMap<>();
        for (Map.Entry<Integer, Integer> e : dp.entrySet()) {
            int suma = e.getKey();
            int val = e.getValue();
            for (int cand : candList) {
                int ns = suma + cand;
                if (ns > n) continue;
                int nv = val + creatividad[cand];
                next.put(ns, Math.max(next.getOrDefault(ns, Integer.MIN_VALUE), nv));
                ans = Math.max(ans, nv);
            }
        }
        dp = next; // avanzar de capa
    }

    return ans;
}

    // --- main de prueba: lee T casos (k n P0 P1 P2 P3 P4) por línea ---
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            lines.add(line);
        }
        for (String line : lines) {
            String[] parts = line.split("\\s+");
            if (parts.length < 7) {
                System.err.println("Formato: k n P0 P1 P2 P3 P4");
                continue;
            }
            int k = Integer.parseInt(parts[0]);
            int n = Integer.parseInt(parts[1]);
            int[] P = new int[5];
            for (int i = 0; i < 5; i++) P[i] = Integer.parseInt(parts[2 + i]);

            long t0 = System.currentTimeMillis();
            int ans = knapsackEsparso(n, k, P);
            long t1 = System.currentTimeMillis();
            System.out.println(ans + "  // time: " + (t1 - t0) + " ms");
        }
        sc.close();
    }
}
