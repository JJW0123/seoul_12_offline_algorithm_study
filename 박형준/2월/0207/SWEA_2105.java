import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SWEA_2105 {
    static int[][] directions = {{-1, 1}, {1, 1}, {1, -1}, {-1, -1}};
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

            int result = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int count = countCafe(i, j, n);
                    result = Math.max(result, count);
                }
            }

            System.out.println("#" + tc + " " + result);
        }
    }

    private static int countCafe(int x, int y, int n) {
        Set<Integer> distinct = new HashSet<>();
        int currentX = x;
        int currentY = y;

        int count = 1;
        for (int[] direction : directions) {
            while (true) {
                int dx = direction[0];
                int dy = direction[1];

                if (x + dx >= 0 && x + dx < n && y + dy >= 0 && y + dy < n && !distinct.contains(map[x + dx][y + dy])) {
                    distinct.add(map[x + dx][y + dy]);
                    count++;
                } else {
                    break;
                }
            }
        }
        return count;
    }
}
