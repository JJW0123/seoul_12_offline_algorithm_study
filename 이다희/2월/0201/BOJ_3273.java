import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

class Main {
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) arr[i] = Integer.parseInt(st.nextToken());
		Arrays.sort(arr);
		
		int x = Integer.parseInt(br.readLine());
		
		int start = 0;
		int end = n - 1;
		int answer = 0;
		
		while (start < end) {
			if (arr[start] + arr[end] == x) answer++;
			if (arr[start] + arr[end] < x) start++;
			else end--;
		}
		
		System.out.println(answer);
		
	}
	
}
