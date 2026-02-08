import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws Exception {
		
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		
		Queue<Integer> queue = new ArrayDeque<>();
		
		for (int i = 1; i <= n; i++) {
			queue.add(i);
		}
		
		while (queue.size() > 1) {
			
			queue.poll();
			queue.add(queue.poll());
			
		}
		
		System.out.println(queue.poll());
		
	}
	
}
