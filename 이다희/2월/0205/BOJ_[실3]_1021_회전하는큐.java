import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		LinkedList<Integer> queue = new LinkedList<Integer>();
		for (int i = 0; i < n; i++) queue.add((i+1));
		
		st = new StringTokenizer(br.readLine());
		int answer = 0;
		for (int i = 0; i < m; i++) {
			int now = Integer.parseInt(st.nextToken());
			int before = queue.indexOf(now);
			int after = queue.size() - before;
			
			if (before <= after) {
				for (int j = 0; j < before; j++) queue.add(queue.poll());
				answer += before;
				queue.poll();
			} else {
				for (int j = 0; j < after; j++) queue.addFirst((queue.pollLast()));
				answer += after;
				queue.poll();
			}
		}
		
		System.out.println(answer);
		
	}
	
}
