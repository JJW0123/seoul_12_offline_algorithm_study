import java.io.*;
import java.util.*;
 
public class Solution {
 
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
 
    public static void main(String[] args) throws Exception {
         
        int T = Integer.parseInt(br.readLine());
         
        for (int tc = 1; tc <= T; tc++) {
             
            // Input
            int N = Integer.parseInt(br.readLine());
            int M = Integer.parseInt(br.readLine());
            boolean[][] arr = new boolean[N][N]; // 인접 행렬
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken()) - 1;
                int b = Integer.parseInt(st.nextToken()) - 1;
                arr[a][b] = true;
            }
            int ans = 0;
             
            // Process
            // 1. 플로이드-워셜
            for (int k = 0; k < N; k++) { // 중간 정점
                for (int i = 0; i < N; i++) { // 출발 정점
                    for (int j = 0; j < N; j++) { // 도착 정점
                        if (arr[i][k] && arr[k][j]) {
                            arr[i][j] = true;
                        }
                    }
                }
            }
            // 2. 정점 개수 확인
            for (int i = 0; i < N; i++) {
                int sum = 0;
                for (int j = 0; j < N; j++) {
                    if (arr[i][j]) { // 나보다 키 작은 사람 수
                        sum++;
                    }
                    if (arr[j][i]) { // 나보다 키 큰 사람 수
                        sum++;
                    }
                }
                if (sum == N - 1) {
                    ans++;
                }
            }
             
            // Output
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
             
        }
 
        System.out.println(sb);
         
    }
 
}
