import java.io.*;
import java.util.*;
	
public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int N, M, arr[][], label, bridge[][], ans;
	static int dir[][] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 상하좌우
	static Queue<int[]> queue = new ArrayDeque<>();
	static ArrayList<Set<Integer>> list = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {

		// Input
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// Process
		// 1. bfs (각 섬 구분 및 테두리 저장)
		label = 2;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (arr[i][j] == 1) {
					bfs(i, j);
				}
			}
		}
		// 2. 다리길이 최솟값 저장
		label -= 2;
		bridge = new int[label][label]; // 인접 행렬
		int x; int y; int z;
		for (int i = 0; i < list.size(); i++) {
			for (int t : list.get(i)) {
				if (t == 0) {
					x = y = z = 0;
				} else {
					x = t / 10000;
					y = (t / 100) % 100;
					z = t % 100;
				}
				count(i, x, y, z);
			}
		}
		// 3. Prim
		ans = prim();
		
		// Output
		System.out.println(ans);

	}
	
	public static int prim() {
		
		int cnt = 0;
		int total = 0;
		boolean[] visit = new boolean[label];
		visit[0] = true; // 인덱스 0부터 시작
		
		for (int k = 0; k < label - 1; k++) {
			int minIdx = -1;
			int minValue = Integer.MAX_VALUE;
			for (int i = 0; i < label; i++) {
				if (!visit[i]) continue; // i 방문 (O)
				for (int j = 0; j < label; j++) {
					if (visit[j]) continue; // j 방문 (X)
					if (bridge[i][j] > 0 && bridge[i][j] < minValue) { // 다리길이 최소인 섬 선택
						minIdx = j;
						minValue = bridge[i][j];
					}
				}
			}
			if (minIdx == -1) { // 인접한 섬이 없을 때
				break;
			} else {
				visit[minIdx] = true;
				total += minValue;
				cnt++;
			}
		}
		
		if (cnt == label - 1) {
			return total;
		} else {
			return -1;
		}
		
	}

	public static void count(int idx, int x, int y, int z) {
		
		int cnt = 0;
		
		while (x >= 0 && x < N && y >= 0 && y < M) {
			if (arr[x][y] > 1) { // 섬을 만났을 때
				if (cnt > 1) { // 다리 길이가 1 이상일 때
					if (bridge[idx][arr[x][y]-2] == 0) {
						bridge[idx][arr[x][y]-2] = cnt;
						bridge[arr[x][y]-2][idx] = cnt;
					} else {
						int min = Math.min(bridge[idx][arr[x][y]-2], cnt);
						bridge[idx][arr[x][y]-2] = min;
						bridge[arr[x][y]-2][idx] = min;
					}
				}
				break;
			}
			cnt++;
			x += dir[z][0];
			y += dir[z][1];
		}

	}

	private static void bfs(int x, int y) {
		
		Set<Integer> temp = new HashSet();
		int[] now;
		
		queue.add(new int[] {x, y});
		
		while (!queue.isEmpty()) {
			now = queue.poll();
			arr[now[0]][now[1]] = label;
			for (int i = 0; i < dir.length; i++) {
				int dx = now[0] + dir[i][0];
				int dy = now[1] + dir[i][1];
				if (dx >= 0 && dx < N && dy >= 0 && dy < M) {
					if (arr[dx][dy] == 1) {
						queue.add(new int[] {dx, dy});
					} else if (arr[dx][dy] == 0) { // 섬 테두리 저장 (x, y, 이동방향)
						temp.add(dx * 10000 + dy * 100 + i); // int[] 중복 확인 불가 -> int 변환 (x0y0i)
					}
				}
			}
		}
		
		list.add(temp);
		label++;
		
	}

}
