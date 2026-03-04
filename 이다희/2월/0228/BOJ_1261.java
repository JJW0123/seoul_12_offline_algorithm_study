import java.io.*;
import java.util.*;

class Node implements Comparable<Node> {
	
	int x;
	int y;
	int dist;

	public Node(int x, int y, int dist) {
		this.x = x;
		this.y = y;
		this.dist = dist;
	}

	@Override
	public int compareTo(Node o) {
		return this.dist - o.dist;
	}
	
}

class Main {
	
	static int N, M, arr[][], distance[][];
	static boolean visit[][];
	static PriorityQueue<Node> pq;
	static int dir[][] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	
	public static void main(String args[]) throws Exception {
		
		// Input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		arr = new int[N][M];
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				arr[i][j] = str.charAt(j) - '0';
			}
		}
		
		// Process
		dijkstra();
		
		// Output
		if (distance[N-1][M-1] == Integer.MAX_VALUE) distance[N-1][M-1] = 0;
		System.out.println(distance[N-1][M-1]);
		
	}
	
	public static void dijkstra() {
		
		visit = new boolean[N][M];
		distance = new int[N][M];
		for (int i = 0; i < N; i++) {
			Arrays.fill(distance[i], Integer.MAX_VALUE);
		}
		distance[0][0] = 0;
		pq = new PriorityQueue<>();
		pq.add(new Node(0, 0, 0));
		
		while (!pq.isEmpty()) {
			Node now = pq.poll();
			if (visit[now.x][now.y]) continue;
			visit[now.x][now.y] = true;
			for (int[] d : dir) {
				int dx = now.x + d[0];
				int dy = now.y + d[1];
				if (dx < 0 || dx >= N || dy < 0 || dy >= M) continue;
				if (distance[dx][dy] > distance[now.x][now.y] + arr[dx][dy]) {
					distance[dx][dy] = distance[now.x][now.y] + arr[dx][dy];
					pq.add(new Node(dx, dy, distance[dx][dy]));
				}
			}
			
		}
		
	}
	
}
