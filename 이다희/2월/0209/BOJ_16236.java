import java.io.*;
import java.util.*;

class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int N, size, eat, answer, arr[][], now[], first[], temp[];
	static int dir[][] = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
	static boolean check[][];
	static Queue<int[]> queue;
	static ArrayList<int[]> list;
	
	public static void main(String[] args) throws Exception {
		
		// Input
		N = Integer.parseInt(br.readLine());
		size = 2; eat = 0; answer = 0;
		arr = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
				if (arr[i][j] == 9) {
					now = new int[] {i, j, 0};
					arr[i][j] = 0;
				}
			}
		}
		
		// Process
		while (true) {
			list = bfs();
			if (list.isEmpty()) break;
			first = list.get(0);
			for (int i = 1; i < list.size(); i++) {
				temp = list.get(i);
				if (temp[2] < first[2]) first = temp.clone();
				else if (temp[2] == first[2]) {
					if (temp[0] < first[0]) first = temp.clone();
					else if (temp[0] == first[0] && temp[1] < first[1]) first = temp.clone();
				}
			}
			if (++eat == size) {
				size++; eat = 0;
			}
			now = new int[] {first[0], first[1], 0};
			arr[first[0]][first[1]] = 0;
			answer += first[2];
		}
		
		// Output
		System.out.println(answer);
		
	}
	
	public static ArrayList<int[]> bfs() {
		list = new ArrayList<>();
		queue = new ArrayDeque<>();
		check = new boolean[N][N];
		queue.offer(now);
		check[now[0]][now[1]] = true;
		while (!queue.isEmpty()) {
			int[] top = queue.poll();
			int x = top[0]; int y = top[1];
			if (arr[x][y] > 0 && arr[x][y] < size) {
				list.add(top);
				continue;
			}
			for (int[] d : dir) {
				int dx = x + d[0]; int dy = y + d[1];
				if (dx >= 0 && dx < N && dy >= 0 && dy < N && !check[dx][dy] && arr[dx][dy] <= size) {
					queue.offer(new int[] {dx, dy, top[2]+1});
					check[dx][dy] = true;
				}
			}
		}
		return list;
	}
	
}
