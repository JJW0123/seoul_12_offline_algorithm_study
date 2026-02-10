import java.io.*;
import java.util.*;

public class Solution {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static int T, H, W, N, now[], look[], bomb[];
	static char field[][], command[];
	
	public static void main(String[] args) throws Exception {
		
		T = Integer.parseInt(br.readLine());

		for (int test_case = 1; test_case <= T; test_case++) {
			
			// Input
			st = new StringTokenizer(br.readLine());
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			now = new int[2];
			look = new int[2];
			field = new char[H][W];
			for (int i = 0; i < H; i++) {
				field[i] = br.readLine().toCharArray();
				for (int j = 0; j < W; j++) {
					if (field[i][j] == '^') {
						look = new int[] {-1, 0}; now[0] = i; now[1] = j;
					} else if (field[i][j] == '<') {
						look = new int[] {0, -1}; now[0] = i; now[1] = j;
					}
					else if (field[i][j] == '>') {
						look = new int[] {0, 1}; now[0] = i; now[1] = j;
					} else if (field[i][j] == 'v') {
						look = new int[] {1, 0}; now[0] = i; now[1] = j;
					}
				}
			}
			N = Integer.parseInt(br.readLine());
			command = br.readLine().toCharArray();
			
			// Process
			for (int i = 0; i < N; i++) check(command[i]);
			
			// Output
			sb.append("#").append(test_case).append(" ");
			for (int i = 0; i < H; i++) {
				for (int j = 0; j < W; j++) sb.append(field[i][j]);
				sb.append("\n");
			}
			
		}
		
		System.out.println(sb);
		
	}
	
	public static void shoot() {
		bomb = new int[] {now[0] + look[0], now[1] + look[1]};
		while (bomb[0] >= 0 && bomb[0] < H && bomb[1] >= 0 && bomb[1] < W) {
			if (field[bomb[0]][bomb[1]] == '*') {
				field[bomb[0]][bomb[1]] = '.';
				break;
			} else if (field[bomb[0]][bomb[1]] == '#') {
				break;
			}
			bomb[0] += look[0]; bomb[1] += look[1];
		}
	}
	
	public static void check(char c) {
		int x = now[0]; int y = now[1];
		switch(c) {
		case 'U':
			field[now[0]][now[1]] = '^';
			look[0] = -1; look[1] = 0;
			x--;
			if (x >= 0 && field[x][y] == '.') {
				field[now[0]][now[1]] = '.';
				now[0]--;
				field[x][y] = '^';
			}
			return;
		case 'D':
			field[now[0]][now[1]] = 'v';
			look[0] = 1; look[1] = 0;
			x++;
			if (x < H && field[x][y] == '.') {
				field[now[0]][now[1]] = '.';
				now[0]++;
				field[x][y] = 'v';
			}
			return;
		case 'L':
			field[now[0]][now[1]] = '<';
			look[0] = 0; look[1] = -1;
			y--;
			if (y >= 0 && field[x][y] == '.') {
				field[now[0]][now[1]] = '.';
				now[1]--;
				field[x][y] = '<';
			}
			return;
		case 'R':
			field[now[0]][now[1]] = '>';
			look[0] = 0; look[1] = 1;
			y++;
			if (y < W && field[x][y] == '.') {
				field[now[0]][now[1]] = '.';
				now[1]++;
				field[x][y] = '>';
			}
			return;
		case 'S':
			shoot();
			return;
		default:
			return;
		}
	}

}
