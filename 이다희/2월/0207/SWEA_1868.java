import java.io.*;
import java.util.*;

class Solution {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	static int T, N, answer;
	static int arr[][];
	static int dir[][] = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1},
			{0, 1}, {1, -1}, {1, 0}, {1, 1}};

	public static void main(String[] args) throws Exception {
	
		T = Integer.parseInt(br.readLine());
		
		for (int test_case = 1; test_case <= T; test_case++) {
			
			// Input
			N = Integer.parseInt(br.readLine());
			answer = 0;
			arr = new int[N][N];
			for (int i = 0; i < N; i++) {
				String str = br.readLine();
				for (int j = 0; j < N; j++) {
					if (str.charAt(j) == '*') arr[i][j] = -1;
					else arr[i][j] = -2;
					
				}
			}
			
			//Process
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (arr[i][j] == -2) check(i, j);
				}
			}
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (arr[i][j] == 0) {
						dfs(i, j);
						answer++;;
					}
				}
			}
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (arr[i][j] > 0) answer++;
				}
			}
			
			// Output
			sb.append("#" + test_case + " " + answer + "\n");
	 		
		 }

		System.out.println(sb);
		
	}
	
	public static void check(int x, int y) {
		int cnt = 0;
		for (int[] d : dir) {
			int dx = x + d[0];
			int dy = y + d[1];
			if (dx >= 0 && dx < N && dy >= 0 && dy < N && arr[dx][dy] == -1) cnt++;
		}
		arr[x][y] = cnt;
	}
	
	public static void dfs(int x, int y) {
		arr[x][y] = -2;
		for (int[] d : dir) {
			int dx = x + d[0];
			int dy = y + d[1];
			if (dx >= 0 && dx < N && dy >= 0 && dy < N && arr[dx][dy] > -1) {
				if (arr[dx][dy] == 0) dfs(dx, dy);
				else arr[dx][dy] = -2;
			}
		}
	}
	
}
