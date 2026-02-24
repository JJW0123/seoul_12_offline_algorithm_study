import java.io.*;
import java.util.*;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static int N, Q, arr[], idx, x;
	static TreeSet<Integer> set;
	
	public static void main(String[] args) throws Exception {
		
		// N, Q
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		
		// arr
		arr = new int[N];
		set = new TreeSet<>();
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			if (arr[i] == 1) {
				set.add(i);
			}
		}
		
		// query
		idx = 0;
		for (int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine());
			int query = Integer.parseInt(st.nextToken());
			switch (query) {
			case 1:
				x = Integer.parseInt(st.nextToken()) - 1;
				if (arr[x] == 0) {
					arr[x] = 1;
					set.add(x);
				}
				else {
					arr[x] = 0;
					set.remove(x);
				}
				break;
			case 2:
				x = Integer.parseInt(st.nextToken());
				idx = (idx + x) % N;
				break;
			case 3:
				if (set.isEmpty()) {
					sb.append(-1);
				} else if (set.ceiling(idx) != null) {
					sb.append(set.ceiling(idx) - idx);
				} else {
					sb.append(N - idx + set.first());
				}
				sb.append("\n");
				break;
			}
		}
		
		System.out.println(sb);
		
	}

}
