import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 0; tc < T; tc++) {
			
			String str = br.readLine();
			int n = str.length();
			
			Stack<Character> left = new Stack<Character>();
			Stack<Character> right = new Stack<Character>();
			
			for (int i = 0; i < n; i++) {
				
				if (str.charAt(i) == '-') {
					if (left.size() > 0) left.pop();
				} else if (str.charAt(i) == '<') {
					if (left.size() > 0) right.add(left.pop());
				} else if (str.charAt(i) == '>') {
					if (right.size() > 0) left.add(right.pop());
				} else {
					left.add(str.charAt(i));
				}
			}
			
			StringBuilder sb = new StringBuilder();
			
			for (char l : left) sb.append(l);
			while (!right.isEmpty()) sb.append(right.pop());
			System.out.println(sb);
			
		}
		
	}
	
}
