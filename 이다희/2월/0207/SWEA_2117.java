import java.io.*;
import java.util.*;

public class Solution {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static int T, N, M, lemit, answer;
	static int [][] arr, dir = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
	static boolean [][] visit;
	static ArrayList<int[]> list;
	
	public static void main(String[] args) throws Exception {
		
		T = Integer.parseInt(br.readLine());
		
		for (int test_case = 1; test_case <= T; test_case++) {
			
			// Input
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken()); M = Integer.parseInt(st.nextToken());
			arr = new int[N][N];
			lemit = 0;
			answer = 0;
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					arr[i][j] = Integer.parseInt(st.nextToken());
					if (arr[i][j] == 1) lemit++;
				}
			}
			lemit *= M;

			
			// Process
			for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) {
				if (arr[i][j] == 1) {
					if (M - 1 >= 0 && answer < 1) answer = 1;
				}
				visit = new boolean[N][N];
				visit[i][j] = true;
				dfs(2);
			}
			
			// Output
			sb.append("#" + test_case + " " + answer + "\n");
			
		}
		
		System.out.println(sb);

	}
	
	public static void dfs(int K) {
		
		int cost = K * K + (K - 1) * (K - 1);
		
		if (cost > lemit) return;
		
		list = new ArrayList<int[]>();
		
		for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) {
			if (visit[i][j]) {
				for (int[] d : dir) {
					int x = i + d[0];
					int y = j + d[1];
					if (x >= 0 && x < N && y >= 0 && y < N) {
						int [] temp = {x, y};
						list.add(temp);
					}
				}
			}
		}
		
		for (int[] l : list) {
			visit[l[0]][l[1]] = true;
		}
		
		int cnt = 0;
		
		for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) {
			if (visit[i][j] && arr[i][j] == 1) cnt++;
		}
		
		int income = cnt * M;
		
		if (income - cost >= 0 && cnt > answer) answer = cnt;
		
		dfs(K + 1);
		
	}

}
