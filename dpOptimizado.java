import java.util.*;

public class dpOptimizado {
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

	// Genera candidatos creativos
	// Genera candidatos: todos los números con creatividad > 0
	public static int[] generarCandidatosCreativos(int n, int[] P) {
		List<Integer> candidatos = new ArrayList<>();
		candidatos.add(0); // incluir el 0 como candidato válido
		for (int i = 1; i <= n; i++) {
			if (calcularCreatividad(i, P) > 0) candidatos.add(i);
		}
		return candidatos.stream().mapToInt(Integer::intValue).toArray();
	}

	// Knapsack O(nk) usando solo candidatos creativos
	public static int knapsack(int n, int k, int[] P) {
		int[] candList = generarCandidatosCreativos(n, P);

		int[] creatividad = new int[n + 1];
		for (int i = 0; i <= n; i++) creatividad[i] = calcularCreatividad(i, P);

		int[] dp = new int[n + 1];
		Arrays.fill(dp, -1);
		dp[0] = 0;

		int[] explore = new int[]{0}; // estados alcanzados en la capa actual

		for (int celda = 1; celda <= k; celda++) {
			int[] next = Arrays.copyOf(dp, dp.length);
			List<Integer> exploreNext = new ArrayList<>();

			for (int i : explore) {
				for (int cand : candList) {
					if (i + cand <= n) {
						int c = creatividad[cand];
						if (next[i + cand] < dp[i] + c) {
							next[i + cand] = dp[i] + c;
							exploreNext.add(i + cand);
						}
					} else break;
				}
			}

			dp = next;
			explore = exploreNext.stream().mapToInt(Integer::intValue).toArray();
			if (explore.length == 0) break; // no hay más transiciones posibles
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
			long startTime = System.currentTimeMillis();
			int resultado = knapsack(n, k, new int[]{P0, P1, P2, P3, P4});
			long endTime = System.currentTimeMillis();
			System.out.println(resultado + " " + (endTime - startTime) + " ms");
		}
	}
}