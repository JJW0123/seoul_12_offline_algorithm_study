import java.io.*;
import java.util.*;

public class Main {
	
	static int N, M;
	static int[][] map, flow, dp, dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	
	public static void main(String[] args) throws Exception {
		
		// Input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		flow = new int[N*M][];
		int idx = 0;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				flow[idx++] = new int[] {i, j, map[i][j]};
			}
		}
		
		// Process
		Arrays.sort(flow, (o1, o2) -> o2[2] - o1[2]); // 내림차순
		dp = new int[N][M];
		dp[0][0] = 1;
		for (int[] f : flow) {
			if (f[0] == N - 1 && f[1] == M - 1) break;
			if (dp[f[0]][f[1]] == 0) continue;
			for (int[] d : dir) {
				int x = f[0] + d[0];
				int y = f[1] + d[1];
				if (x >= 0 && x < N && y >= 0 && y < M && map[x][y] < f[2]) {
					dp[x][y] += dp[f[0]][f[1]];
				}
			}
		}
		
		// Output
		System.out.println(dp[N-1][M-1]);
		
	}
	
}
