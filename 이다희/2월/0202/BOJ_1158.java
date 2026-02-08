import java.io.*;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws Exception {
		
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		int k = sc.nextInt();
		int idx = 0;
		int cnt = 0;
		int[] arr = new int[n];
		
		LinkedList<Integer> list = new LinkedList<Integer>();
		for (int i = 1; i <= n; i++) list.add(i);
		int size = n;
		
		while (!list.isEmpty()) {
			
			idx = (idx + k - 1) % size--;
			arr[cnt++] = list.remove(idx);

		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		for (int i = 0; i < n; i++) {
			sb.append(arr[i]);
			if (i < n - 1) {
				sb.append(", ");
			}
		}
		sb.append(">");
		
		System.out.println(sb);
		
	}
	
}
