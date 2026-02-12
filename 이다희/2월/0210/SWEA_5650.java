import java.io.*;
import java.util.*;

class Solution {
	
	static Scanner sc = new Scanner(System.in);
	
	static int T, N, answer, arr[][], hole[][];
	static int dir[][] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
	
	public static void main(String args[]) throws Exception {
		
		T = sc.nextInt();
		
		for (int tc = 1; tc <= T; tc++) {
			
			// Input
			N = sc.nextInt();
			answer = 0;
			arr = new int[N][N];
			hole = new int[11][5]; // cnt, x1, y1, x2, y2
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					arr[i][j] = sc.nextInt();
					if (arr[i][j] > 5) {
						int idx;
						hole[arr[i][j]][0]++;
						if (hole[arr[i][j]][0] == 1) idx = 1;
						else idx = 3;
						hole[arr[i][j]][idx] = i; hole[arr[i][j]][idx+1] = j;
					}
				}
			}
			
			// Process
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (arr[i][j] == 0) move(i, j);
				}
			}
			
			// Output
			System.out.println("#" + tc + " " + answer);
			
		}
		
	}

	private static void move(int startX, int startY) {
		for (int d = 0; d < 4; d++) {
			int cnt = 0;
			int look = d;
			int x = startX;
			int y = startY;
			while (true) {
				x += dir[look][0]; y += dir[look][1];
				if (x < 0 || x >= N || y < 0 || y >= N) {
					++cnt;
					x -= dir[look][0]; y -= dir[look][1];
					look = (look + 2) % 4;
				}
				if (arr[x][y] == -1 || (x == startX && y == startY)) break;
				if (arr[x][y] > 0 && arr[x][y] <= 5) {
					look = check(look, arr[x][y]);
					++cnt;
				}
				else if (arr[x][y] > 5) {
					int a = arr[x][y];
					if (hole[a][1] == x && hole[a][2] == y) {
						x = hole[a][3]; y = hole[a][4];
					} else {
						x = hole[a][1]; y = hole[a][2];
					}
				}
			}
			answer = Math.max(answer, cnt);
		}
		
	}
	
	public static int check(int look, int shape) {
		switch (shape) {
		case 1:
			if (look == 2) return 1;
			else if (look == 3) return 0;
			else return (look + 2) % 4;
		case 2:
			if (look == 0) return 1;
			else if (look == 3) return 2;
			else return (look + 2) % 4;
		case 3:
			if (look == 0) return 3;
			else if (look == 1) return 2;
			else return (look + 2) % 4;
		case 4:
			if (look == 1) return 0;
			else if (look == 2) return 3;
			else return (look + 2) % 4;
		case 5:
			return (look + 2) % 4;
		}
		return -1;
	}
	
}
