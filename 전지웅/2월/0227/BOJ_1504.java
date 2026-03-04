import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main {
	static PriorityQueue<int[]> pq;
	static boolean[] check;
	static List<List<int[]>> node;
	static int[] length;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		int N = Integer.parseInt(st.nextToken()); // 정점 개수
		int E = Integer.parseInt(st.nextToken()); // 간선 개수

		// 정점번호<연결된 정점, 거리>
		node = new ArrayList<>();

		// 정점은 1번부터 N번까지 존재함
		for (int i = 0; i <= N; i++) {
			node.add(new ArrayList<>());
		}

		// 그래프 Input
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			node.get(a).add(new int[] { b, c });
			node.get(b).add(new int[] { a, c });
		}

		st = new StringTokenizer(br.readLine(), " ");

		// 거쳐야 하는 정점들
		int[] goal = new int[3];
		goal[0] = Integer.parseInt(st.nextToken());
		goal[1] = Integer.parseInt(st.nextToken());
		goal[2] = N;

		// 최단거리인지 체크할 배열
		check = new boolean[N + 1];

		// 1번 정점부터 각 노드까지의 거리 저장할 배열
		length = new int[N + 1];

		// 거리 오름차순 정렬
		pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);

		int[] res = new int[2];

		// turn[0] = { 출발지 -> 정점 V1 -> 정점 V2 -> 도착지 }
		// turn[1] = { 출발지 -> 정점 V2 -> 정점 V1 -> 도착지 }
		int[][][] turn = { { { 1, goal[0] }, { goal[0], goal[1] }, { goal[1], goal[2] } },
				{ { 1, goal[1] }, { goal[0], goal[1] }, { goal[0], goal[2] } } };

		// 각 노드 사이의 거리를 다익스트라로 탐색
		for (int i = 0; i < turn.length; i++) {
			int n = 0;
			int cnt = 0;

			while (n != -1 && cnt < 3) {
				n = func(turn[i][cnt][0], turn[i][cnt][1]);
				res[i] += n;
				cnt++;
			}
			if (n == -1) {
				System.out.println(-1);
				return;
			}
		}

		System.out.println(Math.min(res[0], res[1]));
	}

	// 다익스트라
	public static int func(int start, int end) {

		pq.offer(new int[] { start, 0 });

		// 배열 거리 초기화
		Arrays.fill(length, Integer.MAX_VALUE);
		length[start] = 0;

		// 최단거리 확인 배열 초기화
		Arrays.fill(check, false);

		while (!pq.isEmpty()) {

			int[] next = pq.poll();

			// 최단거리 확정된 정점이라면 건너뛰기
			if (check[next[0]]) {
				continue;
			}

			check[next[0]] = true;

			for (int i = 0; i < node.get(next[0]).size(); i++) {
				int[] cur = node.get(next[0]).get(i);

				// 이미 확정된 정점으로 향하는 분기는 제거
				if (check[cur[0]]) {
					continue;
				}

				// 기존에 저장된 거리보다 짧으면 거리 갱신하고 큐에 넣기
				if (length[cur[0]] > cur[1] + next[1]) {
					length[cur[0]] = cur[1] + next[1];
					pq.offer(new int[] { cur[0], length[cur[0]] });
				}
			}
		}

		// 연결되지 않은 정점이라면 -1 출력
		return length[end] == Integer.MAX_VALUE ? -1 : length[end];
	}
}