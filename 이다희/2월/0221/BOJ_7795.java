import java.io.*;
import java.util.*;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws Exception {
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			
			// Input
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			int[] A = new int[N];
			int[] B = new int[M];
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				A[i] = Integer.parseInt(st.nextToken());
			}
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < M; i++) {
				B[i] = Integer.parseInt(st.nextToken());
			}
			
			// Process
			int ans = 0;
			int idx = M - 1;
			Arrays.sort(A);
			Arrays.sort(B);
			for (int i = N - 1; i >= 0; i--) {
				while (A[i] <= B[idx]) {
					idx--;
					if (idx < 0) break;
				}
				if (idx < 0) break;
				ans += idx + 1;
			}
			
			// Output
			sb.append(ans).append("\n");
			
		}
		
		System.out.println(sb);
		
	}
	
}
