import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class SWEA_1868 {
    static int[] dx = new int[] { -1, 0, 1, -1, 1, -1, 0, 1 };
    static int[] dy = new int[] { 1, 1, 1, 0, 0, -1, -1, -1 };
    static int N, res;
    static char[][] map;
    static boolean[][] visited;

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            // 맵 크기
            N = Integer.parseInt(br.readLine());

            // 맵 채우기
            map = new char[N][N];
            for (int i = 0; i < N; i++) {
                map[i] = br.readLine().toCharArray();
            }

            // 모든 좌표에서 8방향 탐색 돌리고
            // 지뢰 없다면 0으로 교체
            // 이후 bfs 돌리면서 0그룹 제거하면서 visited 체크
            // 지뢰 방문처리하고 남은 visited 세기
            visited = new boolean[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    boolean flag = false;
                    for (int k = 0; k < 8; k++) {
                        int x = i + dx[k];
                        int y = j + dy[k];
                        if (x >= 0 && x < N && y >= 0 && y < N && map[x][y] == '*') {
                            flag = true;
                        }
                    }
                    if (!flag && map[i][j] != '*') {
                        map[i][j] = '0';
                    }
                }
            }

            res = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j] == '0' && !visited[i][j]) {
                        visited[i][j] = true;
                        bfs(i, j);
                    }
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (!visited[i][j] && map[i][j] != '*') {
                        res++;
                    }
                }
            }
            sb.append("#" + tc + " " + res + "\n");
        }
        System.out.println(sb);
    }

    static void bfs(int x, int y) {
        Deque<int[]> deque = new ArrayDeque<>();
        deque.offer(new int[] { x, y });

        while (!deque.isEmpty()) {
            int[] next = deque.poll();
            for (int i = 0; i < 8; i++) {
                int nx = next[0] + dx[i];
                int ny = next[1] + dy[i];
                if (nx >= 0 && nx < N && ny >= 0 && ny < N && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    if (map[nx][ny] == '0') {
                        deque.offer(new int[] { nx, ny });
                    }
                }
            }
        }
        res++;
    }
}