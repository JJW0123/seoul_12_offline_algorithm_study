import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    static int N, K;
    static int[][][][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());

        if (N / 2 < K) {
            System.out.println(0);
            return;
        }

        /*
         * dp[first][prev][idx][cnt]
         * first: 0 or 1 첫 번째 색 골랐는지
         * prev: 0 or 1 바로 이전 색 골랐는지
         * idx: 현재 위치
         * cnt: 지금까지 몇 개나 골랐는지
         */
        dp = new int[2][2][N + 1][K + 1];

        // dp 배열 초기화
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k <= N; k++) {
                    for (int l = 0; l <= K; l++) {
                        dp[i][j][k][l] = -1;
                    }
                }
            }
        }

        System.out.println(dfs(0, 0, 1, 0));
    }

    static int dfs(int first, int prev, int idx, int cnt) {

        // K만큼 골랐으면 경우의 수 1 반환
        if (cnt == K) {
            return 1;
        }

        // 마지막 인덱스인데 K만큼 못 골랐으니 경우의 수 0 반환
        if (idx == N + 1) {
            return 0;
        }

        // -1이 아니라면 이전에 방문한 적이 있는 경우이므로 가지치기
        if (dp[first][prev][idx][cnt] != -1) {
            return dp[first][prev][idx][cnt];
        }

        // 현재 인덱스의 색을 선택하는지, 선택하지 않는지 총 2가지 경우가 존재함

        // 경우의 수 저장할 변수 n
        int n = 0;

        // 선택하지 않는 경우
        n = dfs(first, 0, idx + 1, cnt) % 1_000_000_003;

        // 선택하는 경우(이전 색을 고르지 않았어야 하고, 인덱스가 마지막이라면 first를 고르지 않았어야 함)
        boolean flag = true;

        // 이전 색을 고르지 않았어야 하고
        if (prev != 0) {
            flag = false;
        }

        // 인덱스가 마지막이라면 first를 고르지 않았어야 함
        if (idx == N && first == 1) {
            flag = false;
        }

        if (flag) {
            // 만약 인덱스가 1이라면 first 1로 체크해주기
            if (idx == 1) {
                n = (n + dfs(1, 1, idx + 1, cnt + 1)) % 1_000_000_003;
            } else {
                n = (n + dfs(first, 1, idx + 1, cnt + 1)) % 1_000_000_003;
            }
        }

        // dp에 경우의 수 메모하기
        return dp[first][prev][idx][cnt] = n;
    }
}