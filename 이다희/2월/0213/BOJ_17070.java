import java.io.*;
import java.util.*;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int N, ans;
	static int[][] arr;
	static int[][] dir = {{0, 1}, {1, 0}, {1, 1}}; // 가로, 세로, 대각선
	
	public static void main(String[] args) throws Exception {
		
		// Input
		N = Integer.parseInt(br.readLine());
		ans = 0;
		arr = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// Process
		dfs(0, 0, 1);
		
		// Output
		System.out.println(ans);

	}
	
	public static void dfs(int before, int x, int y) {
		
		if (x == N - 1 && y == N - 1) {
			ans++;
			return;
		}
		
		for (int i = 0; i < dir.length; i++) {
			if (before == 0 && i == 1) continue; // 가로 + 세로 (X)
			if (before == 1 && i == 0) continue; // 세로 + 가로 (X)
			int dx = x + dir[i][0];
			int dy = y + dir[i][1];
			if (check(i, dx, dy)) { // 벽 확인
				dfs(i, dx, dy);
			}
		}
		
	}

	private static boolean check(int now, int dx, int dy) {
		
		if (dx >= N || dy >= N) {
			return false;
		}
		
		switch (now) {
		case 0: // 좌우 확인
			if (arr[dx][dy-1] == 0 && arr[dx][dy] == 0) {
				return true;
			}
			break;
		case 1: // 상하 확인
			if (arr[dx-1][dy] == 0 && arr[dx][dy] == 0) {
				return true;
			}
			break;
		case 2: // 상하좌우 확인
			if (arr[dx-1][dy-1] == 0 && arr[dx][dy-1] == 0 && arr[dx-1][dy] == 0 && arr[dx][dy] == 0) {
				return true;
			}
			break;
		}
		
		return false;
	}

}
