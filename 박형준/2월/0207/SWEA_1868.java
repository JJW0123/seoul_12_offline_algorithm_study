import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class SWEA_1868 {
    static int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            int n = Integer.parseInt(br.readLine());
            char[][] map = new char[n][n];
            boolean[][] visited = new boolean[n][n];

            int result = 0;
            for (int i = 0; i < n; i++) {
                map[i] = br.readLine().toCharArray();
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    countMine(map, i, j, n);
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (map[i][j] == '0' && !visited[i][j]) {
                        result++;
                        dfs(map, i, j , visited , n);
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (map[i][j] != '*' && !visited[i][j]) {
                        visited[i][j] = true;
                        result++;
                    }
                }
            }

            sb.append("#").append(tc).append(" ").append(result).append("\n");
        }
        System.out.println(sb);
    }

    private static void dfs(char[][] map, int x, int y, boolean[][] visited, int n) {
        visited[x][y] = true;
        if (map[x][y] == '0') {
            for (int[] direction : directions) {
                int dx = direction[0];
                int dy = direction[1];

                if (x + dx >= 0 && x + dx < n && y + dy >= 0 && y + dy < n && !visited[x + dx][y + dy]) {
                    if (map[x + dx][y + dy] != '*') {
                        dfs(map, x+dx, y+dy, visited, n);
                    }
                }
            }
        }
    }

    private static void countMine(char[][] map, int x, int y, int n) {
        int count = 0;
        if (map[x][y] == '*') {
            return;
        }
        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];

            if (x + dx >= 0 && x + dx < n && y + dy >= 0 && y + dy < n && map[x + dx][y + dy] == '*') {
                count++;
            }
        }
        map[x][y] = Character.forDigit(count, 10);
    }
}
