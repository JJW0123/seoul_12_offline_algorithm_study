import java.io.*;
import java.util.*;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	static int T, N;
	static String arr[], ans;
	
	public static void main(String[] args) throws Exception {
		
		T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			
			// Input
			N = Integer.parseInt(br.readLine());
			arr = new String[N];
			for (int i = 0; i < N; i++) {
				arr[i] = br.readLine();
			}
			
			// Process
			ans = "YES";
			Arrays.sort(arr);
			for (int i = 0; i < N - 1; i++) {
				int size = arr[i].length();
				if (size <= arr[i+1].length() && arr[i].equals(arr[i+1].substring(0, size))) {
					ans = "NO";
					break;
				}
			}
			
			// Output
			sb.append(ans).append("\n");
			
		}
		
		System.out.println(sb);

	}

}
