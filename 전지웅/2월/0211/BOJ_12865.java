import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class BOJ_12865 {
    static int N, K, result;
    static int[][] input;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");

        N = Integer.parseInt(str[0]);
        K = Integer.parseInt(str[1]);

        input = new int[N][2];
        for (int i = 0; i < N; i++) {
            str = br.readLine().split(" ");
            input[i][0] = Integer.parseInt(str[0]);
            input[i][1] = Integer.parseInt(str[1]);
        }

        // dp[인덱스][무게]
        int[][] dp = new int[N + 1][K + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= K; j++) {
                // 무거우면 패스
                if (input[i - 1][0] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], input[i - 1][1] + dp[i - 1][j - input[i - 1][0]]);
                }
            }
        }
        System.out.println(dp[N][K]);
    }
}