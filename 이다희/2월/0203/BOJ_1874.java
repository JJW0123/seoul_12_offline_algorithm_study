import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String args[]) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int n = Integer.parseInt(br.readLine());
		
		Stack<Integer> stack = new Stack<>();
		
		int current = 1;
		boolean tf = true;
		
		for (int i = 0; i < n; i++) {
			
			int target = Integer.parseInt(br.readLine());
			
			if (current <= target) {
				for (; current <= target; current++) {
					stack.push(current);
					sb.append("+").append("\n");
				}
				stack.pop();
				sb.append("-").append("\n");
			} else {
				if (stack.peek() == target) {
					stack.pop();
					sb.append("-").append("\n");
				} else {
					tf = false;
					break;
				}
			}
		}
			
		if (tf) System.out.println(sb);
		else System.out.println("NO");
		
	}
	
}
