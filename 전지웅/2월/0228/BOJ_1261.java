import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		int M = Integer.parseInt(st.nextToken()); // 가로 길이
		int N = Integer.parseInt(st.nextToken()); // 세로 길이

		char[][] map = new char[N + 1][M + 1];

		// 미로 input
		for (int i = 1; i <= N; i++) {
			String str = br.readLine();
			for (int j = 1; j <= M; j++) {
				map[i][j] = str.charAt(j - 1);
			}
		}

		// 거리 초기화
		int[][] length = new int[N + 1][M + 1];
		for (int i = 0; i <= N; i++) {
			Arrays.fill(length[i], Integer.MAX_VALUE);
		}

		length[1][1] = 0;

		// 확정된 노드인지
		boolean[][] confirm = new boolean[N + 1][M + 1];

		int[] dx = { 1, -1, 0, 0 };
		int[] dy = { 0, 0, 1, -1 };

		// pq = {x좌표, y좌표, 벽 개수}, 벽 개수 오름차순 정렬
		PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);
		pq.offer(new int[] { 1, 1, 0 });
		while (!pq.isEmpty()) {

			int[] next = pq.poll();
			// 확정된 노드라면 건너뛰기
			if (confirm[next[0]][next[1]]) {
				continue;
			}

			// 확정되지 않은 노드라면 확정시키기
			confirm[next[0]][next[1]] = true;

			// 상하좌우 4방향 탐색
			for (int i = 0; i < 4; i++) {
				int nx = next[0] + dx[i];
				int ny = next[1] + dy[i];

				// 미로를 벗어나진 않는지 확인
				if (nx > 0 && nx <= N && ny > 0 && ny <= M) {
					int wall = next[2];
					// 벽이라면 비용 추가하기
					if (map[nx][ny] == '1') {
						wall++;
					}
					// 더 짧은 비용이 든다면 큐에 넣기
					if (length[nx][ny] > wall) {
						length[nx][ny] = wall;
						pq.offer(new int[] { nx, ny, wall });
					}
				}
			}
		}
		System.out.println(length[N][M]);
	}
}