import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n];
		int idx = 0;
		int answer = 0;
		
		for (int i = 0; i < n; i++) {
			int a = Integer.parseInt(br.readLine());
			if (a == 0) arr[--idx] = 0;
			else arr[idx++] = a;
		}
		
		for (int i = 0; i < idx; i++) answer += arr[i];
		System.out.println(answer);
		
	}
	
}
