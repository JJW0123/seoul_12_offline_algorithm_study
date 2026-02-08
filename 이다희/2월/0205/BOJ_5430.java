import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int test_case = 1; test_case <= T; test_case++) {
			
			String func = br.readLine();
			int n = Integer.parseInt(br.readLine());
			StringTokenizer st = new StringTokenizer(br.readLine(), "[],");
			Deque<String> deque = new ArrayDeque<String>();
			for (int i = 0; i < n; i++) deque.offer(st.nextToken());
			boolean back = false;
			boolean error = false;
			
			for (int i = 0; i < func.length(); i++) {
				
				char c = func.charAt(i);
				
				if (c == 'R') {
					back = !back;
				} else {
					if (deque.size() == 0) {
						error = true;
						sb.append("error\n");
						break;
					}
					if (back == false) {
						deque.poll();
					} else {
						deque.pollLast();
					}
				}
				
			}
			
			if (!error) {
				sb.append("[");
				while (!deque.isEmpty()) {
					if (back == false) sb.append(deque.poll());
					else sb.append(deque.pollLast());
					if (!deque.isEmpty()) sb.append(",");
				}
				sb.append("]\n");
			}
			
		}
		
		System.out.println(sb);
		
	}
	
}
