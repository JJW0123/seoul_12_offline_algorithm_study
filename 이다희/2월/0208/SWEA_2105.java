import java.io.*;
import java.util.*;

class Solution {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static int T, N, answer, arr[][];
	static int dir[][] = {{-1, 1}, {1, 1}, {1, -1}, {-1, -1}};
	
	public static void main(String args[]) throws Exception {
		
		T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			
			// Input
			N = Integer.parseInt(br.readLine());
			answer = 0;
			arr = new int[N][N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) arr[i][j] = Integer.parseInt(st.nextToken());
			}
			
			// Process
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					check(i, j);
				}
			}
			if (answer == 0) answer = -1;
			
			// Output
			sb.append("#").append(tc).append(" ").append(answer).append("\n");
			
		}
		
		System.out.println(sb);
		
	}

	private static void check(int a, int b) {
		int repeat = 0;
		for (int i = 1; i <= N - 1; i++) {
			for (int j = 1; j <= N - 1; j++) {
				boolean stop = false;
				int x = a; int y = b;
				int sum = 0;
				int[] cnt = new int[101]; 
				for (int k = 0; k < 4; k++) {
					if (k % 2 == 0) repeat = i;
					else repeat = j;
					for (int r = 0; r < repeat; r++) {
						int dx = x + dir[k][0];
						int dy = y + dir[k][1];
						if (dx < 0 || dx >= N || dy < 0 || dy >= N) {
							stop = true; break;
						} else if (++cnt[arr[dx][dy]] > 1) {
							--cnt[arr[dx][dy]];
							stop = true; break;
						} else {
							x = dx; y = dy;
							++sum;
						}
					}
					if (stop) break;
				}
				if (!stop) answer = Math.max(answer, sum);
			}
			
		}
	}
	
}
