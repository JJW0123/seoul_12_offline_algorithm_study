import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int n = Integer.parseInt(br.readLine());
		
		Queue<Integer> queue = new ArrayDeque<>();
		
		int head = -2;
		int tail = -2;
		
		for (int i = 0; i < n; i++) {
			
			String[] str = br.readLine().split(" ");
			
			switch (str[0]) {
			
			case "push":
				tail = Integer.parseInt(str[1]);
				queue.add(tail);
				break;
				
			case "pop":
				if (queue.isEmpty()) head = -1;
				else head = queue.poll();
				sb.append(head).append("\n");
				break;
				
			case "size":
				sb.append(queue.size()).append("\n");
				break;
				
			case "empty":
				int tf;
				if (queue.isEmpty()) tf = 1;
				else tf = 0;
				sb.append(tf).append("\n");
				break;
				
			case "front":
				if (queue.isEmpty()) head = -1;
				else head = queue.peek();
				sb.append(head).append("\n");
				break;
				
			case "back":
				if (queue.isEmpty()) tail = -1;
				sb.append(tail).append("\n");
				break;

			}
			
		}	
		
		System.out.println(sb);
		
	}
	
}
