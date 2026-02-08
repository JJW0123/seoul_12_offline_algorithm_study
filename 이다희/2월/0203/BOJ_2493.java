import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		Stack<int[]> stack = new Stack<>();
		
		for (int i = 1; i <= n; i++) {

			int[] now = {Integer.parseInt(st.nextToken()), i};
			
			while (!stack.isEmpty() && stack.peek()[0] < now[0]) {
				stack.pop();
			}
			
			if (stack.isEmpty()) {
				sb.append(0 + " ");
			} else {
				sb.append(stack.peek()[1] + " ");
			}
			
			stack.push(now);
			
		}
		
		System.out.println(sb);
		
	}
	
}
