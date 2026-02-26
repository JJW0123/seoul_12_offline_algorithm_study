import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Main {
    static int[][] dp, map;
    static int N, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        M = Integer.parseInt(st.nextToken()); // 지도 세로 크기
        N = Integer.parseInt(st.nextToken()); // 지도 가로 크기

        map = new int[M][N];

        // 지도 input
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // dp[x][y] : x,y 좌표에서 도착지까지 갈 수 있는 경로의 개수
        dp = new int[M][N];

        // 방문하지 않은 경로는 -1로 초기화
        for (int i = 0; i < M; i++) {
            Arrays.fill(dp[i], -1);
        }

        // 결과 출력
        System.out.println(dfs(0, 0));
    }

    static int[] dx = { 1, -1, 0, 0 };
    static int[] dy = { 0, 0, 1, -1 };

    static int dfs(int x, int y) {

        // 도착지라면 1 반환
        if (x == M - 1 && y == N - 1) {
            return 1;
        }

        // 방문했던 경로라면 가지치기
        if (dp[x][y] != -1) {
            return dp[x][y];
        }

        // 방문한 경로는 -1에서 0으로 바꿔줌
        dp[x][y] = 0;

        // 4방향 탐색
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx >= 0 && nx < M && ny >= 0 && ny < N && map[x][y] > map[nx][ny]) {
                // dp[x][y] 주위의 4 방향에서 경로의 개수 받아와 더해줌
                dp[x][y] += dfs(nx, ny);
            }
        }

        // x,y 좌표에서 도착지까지의 경우의 수 리턴
        return dp[x][y];
    }
}