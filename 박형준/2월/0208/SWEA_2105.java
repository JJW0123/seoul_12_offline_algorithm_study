import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SWEA_2105 {
    static int[][] directions = {{-1, -1},{-1, 1}, {1, 1}, {1, -1}};
    static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            int n = Integer.parseInt(br.readLine());

            map = new int[n][n];
            for (int i = 0; i < n; i++) {
                map[i] = Arrays.stream(br.readLine().split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }

            int[] result = {-1};
            for (int i = 1; i < n; i++) {
                for (int j = 1; j < i; j++) {
                    countCafe(i, j, n, 1, 0, result, new HashSet<>(), i, j);
                }
            }

            System.out.println("#" + tc + " " + result[0]);
        }
    }

    private static void countCafe(int x, int y, int n, int count, int idx, int[] result, Set<Integer> distinct , int startX, int startY) {
        distinct.add(map[x][y]);

        int[] direction = directions[idx];
        int dx = direction[0];
        int dy = direction[1];

        if (idx == 3 && x + dx == startX && y +dy == startY) {
            result[0] = Math.max(result[0], count);
        }

        if (x + dx >= 0 && x + dx < n && y + dy >= 0 && y + dy < n && !distinct.contains(map[x + dx][y + dy])) {
            countCafe(x + dx, y + dy, n, count + 1, idx, result, distinct, startX, startY);
            if (idx != 3) {
                countCafe(x + dx, y + dy, n, count + 1, idx + 1, result, distinct, startX, startY);
            }
            distinct.remove(map[x + dx][y + dy]);
        }
    }
}
