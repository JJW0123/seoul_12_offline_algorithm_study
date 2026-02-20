package feb._0207;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SWEA_2117 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            String[] nAndM = br.readLine().split(" ");
            int n = Integer.parseInt(nAndM[0]);
            int m = Integer.parseInt(nAndM[1]);

            List<Integer> coordinateX = new ArrayList<>();
            List<Integer> coordinateY = new ArrayList<>();
            int maxHouse = 0;
            for (int i = 0; i < n; i++) {
                int[] line = Arrays.stream(br.readLine().split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                for (int j = 0; j < n; j++) {
                    if (line[j] == 1) {
                        coordinateX.add(i);
                        coordinateY.add(j);
                        maxHouse++;
                    }
                }
            }

            int result = 0;
            result = getResult(n, coordinateX, coordinateY, m, result, maxHouse);

            sb.append("#").append(tc).append(" ").append(result).append("\n");
        }
        System.out.print(sb);
    }

    private static int getResult(int n, List<Integer> coordinateX, List<Integer> coordinateY, int m, int result,
                                 int maxHouse) {
        for (int k = 0; k < 2 * n; k++) {
            int cost = k * k + (k + 1) * (k + 1);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int profitCount = 0;
                    for (int houseIndex = 0; houseIndex < coordinateX.size(); houseIndex++) {
                        int x = coordinateX.get(houseIndex);
                        int y = coordinateY.get(houseIndex);

                        int distance = Math.abs(i - x) + Math.abs(j - y);
                        if (k >= distance) {
                            profitCount++;
                        }
                    }
                    if (cost > profitCount * m) {
                        continue;
                    }

                    result = Math.max(result, profitCount);
                    if (result == maxHouse) {
                        return result;
                    }
                }
            }
        }
        return result;
    }
}
