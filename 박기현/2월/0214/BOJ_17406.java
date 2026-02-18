import java.io.*;
import java.util.*;

public class BOJ_17406 {
    static int N, M, K;
    static int[][] map;
    static int[][] op_commands;
    static int[] p; // 순열을 저장할 배열
    static boolean[] visited; // 순열 생성 시 방문 여부
    static int minAns = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N + 1][M + 1]; // 1번 인덱스부터 사용
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        op_commands = new int[K][3];
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            op_commands[i][0] = Integer.parseInt(st.nextToken()); // r
            op_commands[i][1] = Integer.parseInt(st.nextToken()); // c
            op_commands[i][2] = Integer.parseInt(st.nextToken()); // s
        }

        p = new int[K];
        visited = new boolean[K];
        
        // 1. 연산 순서 정하기 (순열)
        permutation(0);

        System.out.println(minAns);
    }

    // 모든 연산 순서를 탐색하는 순열 함수
    static void permutation(int cnt) {
        if (cnt == K) {
            // 2. 결정된 순서대로 배열 회전 시뮬레이션
            solve();
            return;
        }

        for (int i = 0; i < K; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            p[cnt] = i;
            permutation(cnt + 1);
            visited[i] = false;
        }
    }

    static void solve() {
        // 원본 배열 복사
        int[][] temp = new int[N + 1][M + 1];
        for (int i = 1; i <= N; i++) {
            temp[i] = map[i].clone();
        }

        // 정해진 순서(p 배열)대로 회전 연산 수행
        for (int i = 0; i < K; i++) {
            int r = op_commands[p[i]][0];
            int c = op_commands[p[i]][1];
            int s = op_commands[p[i]][2];
            rotate(temp, r, c, s);
        }

        // 배열의 값(각 행의 합 중 최솟값) 계산
        for (int i = 1; i <= N; i++) {
            int sum = 0;
            for (int j = 1; j <= M; j++) {
                sum += temp[i][j];
            }
            minAns = Math.min(minAns, sum);
        }
    }

    static void rotate(int[][] arr, int r, int c, int s) {
        // s개의 정사각형 껍질을 각각 회전시킴
        for (int layer = 1; layer <= s; layer++) {
            int top = r - layer;
            int left = c - layer;
            int bottom = r + layer;
            int right = c + layer;

            int first = arr[top][left]; // 가장 왼쪽 위 값 저장

            // 1. 왼쪽 변 (아래에서 위로)
            for (int i = top; i < bottom; i++) arr[i][left] = arr[i + 1][left];
            // 2. 아래쪽 변 (오른쪽에서 왼쪽으로)
            for (int i = left; i < right; i++) arr[bottom][i] = arr[bottom][i + 1];
            // 3. 오른쪽 변 (위에서 아래로)
            for (int i = bottom; i > top; i--) arr[i][right] = arr[i - 1][right];
            // 4. 위쪽 변 (왼쪽에서 오른쪽으로)
            for (int i = right; i > left + 1; i--) arr[top][i] = arr[top][i - 1];
            
            arr[top][left + 1] = first; // 저장해둔 값 넣기
        }
    }
}