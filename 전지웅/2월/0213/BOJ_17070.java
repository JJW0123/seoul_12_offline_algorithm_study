import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

class Main {
    static char[][] map;
    static int res = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        map = new char[N][N];

        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for (int j = 0, index = 0; j < N; j++, index += 2) {
                map[i][j] = str.charAt(index);
            }
        }

        Deque<int[]> deque = new ArrayDeque<>();

        // x좌표, y좌표, dir
        // dir -> 0:가로 1:세로, 2:대각선
        deque.offer(new int[] { 0, 1, 0 });

        // 우, 하, 우하 대각선 3케이스 BFS
        while (!deque.isEmpty()) {
            int[] next = deque.poll();
            int x = next[0];
            int y = next[1];
            int dir = next[2];

            // 목표 지점에 도착했다면
            if (x == N - 1 && y == N - 1) {
                res++;

            } else {
                // 케이스1 (우)
                if (y + 1 < N && dir != 1 && map[x][y + 1] != '1')
                    deque.offer(new int[] { x, y + 1, 0 });
                // 케이스2 (하)
                if (x + 1 < N && dir != 0 && map[x + 1][y] != '1')
                    deque.offer(new int[] { x + 1, y, 1 });
                // 케이스3 (우하)
                if (x + 1 < N && y + 1 < N && map[x + 1][y] != '1' && map[x][y + 1] != '1'
                        && map[x + 1][y + 1] != '1') {
                    deque.offer(new int[] { x + 1, y + 1, 2 });
                }
            }
        }
        System.out.println(res);
    }
}
