import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class SWEA_2117 {
    static final int[] dx = new int[] { 1, -1, 0, 0 };
    static final int[] dy = new int[] { 0, 0, 1, -1 };
    static int N; // 맵 크기
    static int M; // 지불 비용
    static char[][] map; // 맵
    static int res;

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            String[] str = br.readLine().split(" ");
            N = Integer.parseInt(str[0]); // 맵 크기
            M = Integer.parseInt(str[1]); // 지불 비용

            // 맵 채우기
            map = new char[N][N];
            for (int i = 0; i < N; i++) {
                char[] ch = br.readLine().toCharArray();
                for (int j = 0, index = 0; j < N; j++, index += 2) {
                    map[i][j] = ch[index];
                }
            }

            // bfs 돌리기
            res = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    bfs(i, j);
                }
            }
            sb.append("#" + tc + " " + res + "\n");
        }
        System.out.println(sb);
    }

    static void bfs(int x, int y) {
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];

        // 시작점 처리 (K=1 일 때)
        q.offer(new int[] { x, y });
        visited[x][y] = true;
        int houseCount = 0;
        if (map[x][y] == '1')
            houseCount++;

        // K=1일 때 비용 체크
        int K = 1;
        int cost = K * K + (K - 1) * (K - 1);
        if (houseCount * M - cost >= 0) {
            res = Math.max(res, houseCount);
        }

        // bfs 탐색
        while (!q.isEmpty()) {
            K++; // 거리를 1 늘림
            int size = q.size(); // 큐에 있는 노드 개수만큼 반복

            for (int i = 0; i < size; i++) {
                int[] next = q.poll();

                for (int d = 0; d < 4; d++) {
                    int nx = next[0] + dx[d];
                    int ny = next[1] + dy[d];

                    // 방문한적 없으면
                    if (nx >= 0 && nx < N && ny >= 0 && ny < N && !visited[nx][ny]) {
                        visited[nx][ny] = true;
                        if (map[nx][ny] == '1') {
                            houseCount++;
                        }
                        q.offer(new int[] { nx, ny });
                    }
                }
            }

            // 이번 K 단계(마름모 껍질) 확장이 끝난 후 손익 계산
            cost = K * K + (K - 1) * (K - 1);

            // 손해가 없다면 최대 집 개수 갱신
            if (houseCount * M - cost >= 0) {
                res = Math.max(res, houseCount);
            }
        }
    }
}