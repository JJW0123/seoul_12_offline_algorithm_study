import java.io.*;
import java.util.*;

class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int N, K, arr[][], dp[][];
	
	public static void main(String[] args) throws Exception {
		
		// Input
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		arr = new int[N+1][2];
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		dp = new int[N+1][K+1];
		
		// Process
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= K; j++) {
				if (arr[i][0] > j) { // 물건 담기 불가능
					dp[i][j] = dp[i-1][j];
				} else { // 물건 담기 가능
					dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-arr[i][0]] + arr[i][1]);
				}
			}
		}
		
		// Output
		System.out.println(dp[N][K]);
		
	}
	
}
