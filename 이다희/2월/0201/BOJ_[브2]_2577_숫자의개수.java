import java.io.*;
import java.util.*;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int x = Integer.parseInt(br.readLine());
		int y = Integer.parseInt(br.readLine());
		int z = Integer.parseInt(br.readLine());
		int total = x * y * z;
		int[] answer = new int[10];
		
		while (total > 0) {
			answer[total % 10]++;
			total /= 10;
		}
		
		for (int a : answer) System.out.println(a);
		
	}
	
}
