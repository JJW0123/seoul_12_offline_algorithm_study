import java.io.*;
import java.util.*;

class Node implements Comparable<Node> {

	int idx;
	int dist;

	public Node(int idx, int dist) {
		super();
		this.idx = idx;
		this.dist = dist;
	}

	@Override
	public int compareTo(Node o) {
		return this.dist - o.dist;
	}
	
}

public class Main {
	
	static int INF = 200_000_000; // Integer.MAX_VALUE (X)
	static int N, E, distance[];
	static boolean visit[];
	static ArrayList<Node> arr[];
	static PriorityQueue<Node> queue;
	
	public static void main(String args[]) throws Exception {
		
		// Input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		arr = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			arr[i] = new ArrayList<>();
		}
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken());
			arr[a].add(new Node(b, c));
			arr[b].add(new Node(a, c));
		}
		st = new StringTokenizer(br.readLine());
		int v1 = Integer.parseInt(st.nextToken()) - 1;
		int v2 = Integer.parseInt(st.nextToken()) - 1;
		
		// Process
		int first = dijkstra(0, v1) + dijkstra(v1, v2) + dijkstra(v2, N - 1);
		int second = dijkstra(0, v2) + dijkstra(v2, v1) + dijkstra(v1, N - 1);
		int result = Integer.min(first, second);
		if (first >= INF && second >= INF) {
			result = -1;
		}
		
		// Output
		System.out.println(result);
		
	}
	
	public static int dijkstra(int start, int end) {
		
		distance = new int[N];
		Arrays.fill(distance, INF);
		distance[start] = 0;
		visit = new boolean[N];
		queue = new PriorityQueue<>();
		queue.add(new Node(start, distance[start]));
		
		while (!queue.isEmpty()) {
			
			Node now = queue.poll();
			if (visit[now.idx]) continue;
			visit[now.idx] = true;
			
			for (Node node : arr[now.idx]) {
				if (distance[node.idx] > distance[now.idx] + node.dist) {
					distance[node.idx] = distance[now.idx] + node.dist;
					queue.add(new Node(node.idx, distance[node.idx]));
				}
			}
			
		}
		
		return distance[end];
		
	}
	
}
