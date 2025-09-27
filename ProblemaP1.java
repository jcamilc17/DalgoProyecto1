// Juan Sebastian Robles - 202411827, Juan Camilo Caldas - 202322445

import java.util.*;

public class ProblemaP1 {
	static final int KEEP = 10;

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

	// KEEP: cuántos pesos (números) guardar por cada valor de creatividad (ajusta según memoria/precisión)
	public static int[] generarCandidatosComprimidos(int n, int[] P) {
		int[] creatividad = new int[n + 1];
		for (int i = 0; i <= n; i++) creatividad[i] = calcularCreatividad(i, P);

		// Para cada valor de creatividad guardamos un max-heap con los KEEP pesos más pequeños
		Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
		for (int i = 1; i <= n; i++) {
			int c = creatividad[i];
			if (c <= 0) continue;
			PriorityQueue<Integer> pq = map.computeIfAbsent(c, k -> new PriorityQueue<>(Collections.reverseOrder()));
			pq.offer(i);
			if (pq.size() > KEEP) pq.poll(); // mantener sólo los KEEP más pequeños (pq es max-heap)
		}

		List<Integer> candidatos = new ArrayList<>();
		candidatos.add(0);
		for (PriorityQueue<Integer> pq : map.values()) {
			while (!pq.isEmpty()) {
				candidatos.add(pq.poll());
			}
		}
		Collections.sort(candidatos); // orden ascendente (útil para cortar con 'break' si quieres)
		return candidatos.stream().mapToInt(Integer::intValue).toArray();
	}

	// Knapsack O(nk) usando solo candidatos creativos
	public static int knapsack(int n, int k, int[] P) {
		int[] candList = generarCandidatosComprimidos(n, P);

		int[] creatividad = new int[n + 1];
		for (int i = 0; i <= n; i++) creatividad[i] = calcularCreatividad(i, P);

		int[] dp = new int[n + 1];
		Arrays.fill(dp, -1);
		dp[0] = 0;

		int[] explore = new int[]{0};

		for (int celda = 1; celda <= k; celda++) {
			int[] next = Arrays.copyOf(dp, dp.length);
			boolean[] added = new boolean[n + 1]; // para no duplicar en exploreNext
			List<Integer> exploreNext = new ArrayList<>();

			for (int i : explore) {
				if (dp[i] < 0) continue;
				for (int cand : candList) {
					int to = i + cand;
					if (to > n) continue; // candList está ordenado, podrías usar break si confías en el orden
					int val = dp[i] + creatividad[cand];
					if (next[to] < val) {
						next[to] = val;
						if (!added[to]) {
							added[to] = true;
							exploreNext.add(to);
						}
					}
				}
			}

			if (exploreNext.isEmpty()) break;
			dp = next;
			explore = exploreNext.stream().mapToInt(Integer::intValue).toArray();
		}

		return dp[n] < 0 ? 0 : dp[n];
	}

	// Ejemplo de uso
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int t = Integer.parseInt(scanner.nextLine().trim());
		for (int test = 0; test < t; test++) {
			String line = "";
			while (true) {
				if (scanner.hasNextLine()) {
					line = scanner.nextLine();
					if (!line.trim().isEmpty()) break;
				} else {
					line = null;
					break;
				}
			}
			if (line == null || line.trim().isEmpty()) {
				System.out.println("0 0 ms");
				continue;
			}
			String[] parts = line.trim().split("\\s+");
			int[] datos = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
			int k = datos[0];
			int n = datos[1];
			int P0 = datos[2];
			int P1 = datos[3];
			int P2 = datos[4];
			int P3 = datos[5];
			int P4 = datos[6];
			Long startTime = System.currentTimeMillis();
			int resultado = knapsack(n, k, new int[]{P0, P1, P2, P3, P4});
			Long endTime = System.currentTimeMillis();
			System.out.println(resultado + " " + (endTime - startTime) + " ms");
		}
	}
}