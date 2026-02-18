import java.io.*;
import java.util.*;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int N, M, K, A[][], com[][], arr[], temp[][], ans;
	static int dir[][] = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 왼쪽, 아래쪽, 오른쪽, 위쪽
	static boolean visit[];
	static ArrayList<int[]> list;
	
	public static void main(String[] args) throws Exception {
		
		// Input
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		A = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		com = new int[K][3];
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int s = Integer.parseInt(st.nextToken());
			com[i] = new int[] {r, c, s};
		}
		
		// Process
		// 1. 회전 연산 순서 순열 생성
		list = new ArrayList<>();
		arr = new int[K];
		visit = new boolean[K];
		per(0);
		// 2. 배열 회전
		ans = Integer.MAX_VALUE;
		for (int[] l : list) {
			// 2-1. 배열 복사
			temp = new int[N][M];
			for (int j = 0; j < N; j++) { // 이중 배열 clone (X) -> 행 clone (O)
				temp[j] = A[j].clone();
			}
			// 2-2. 배열 회전
			for (int n : l) {
				move(com[n][0], com[n][1], com[n][2]);
			}
			// 2-3. 최솟값 확인
			for (int j = 0; j < N; j++) {
				int total = 0;
				for (int t : temp[j]) {
					total += t;
				}
				ans = Math.min(ans, total);
			}
		}

		// Output
		System.out.println(ans);

	}
	
	public static void per(int cnt) {
		
		if (cnt == K) {
			list.add(arr.clone());
		}
		
		for (int i = 0; i < K; i++) {
			if (!visit[i]) {
				arr[cnt] = i;
				visit[i] = true;
				per(cnt + 1);
				visit[i] = false;
			}
		}
		
	}
	
	public static void move(int r, int c, int s) {
		
		int x = r - s;
		int y = c - s;
		int before;  // 이전 값
		int now;  // 현재 값
		
		for (int i = s; i >= 1; i--) { // 바깥쪽 -> 안쪽
			before = temp[x][y];
			for (int[] d : dir) { // 왼쪽, 아래쪽, 오른쪽, 위쪽
				for (int j = 0; j < i*2; j++) { // 이동 횟수 항상 짝수
					x += d[0];
					y += d[1];
					now = temp[x][y];
					temp[x][y] = before;
					before = now;
				}
			}
			x++;
			y++;
		}
		
	}

}
