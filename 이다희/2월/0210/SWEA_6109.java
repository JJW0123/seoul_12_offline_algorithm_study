import java.io.*;
import java.util.*;

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int T, N, M, arr[][], temps[][];
    static String str;
    
    public static void main(String[] args) throws Exception {
        
        T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            
            // Input
        	st = new StringTokenizer(br.readLine());
        	N = Integer.parseInt(st.nextToken());
        	str = st.nextToken();
        	arr = new int[N][N];
    		for (int i = 0; i < N; i++) {
    			st = new StringTokenizer(br.readLine());
    		    for (int j = 0; j < N; j++) arr[i][j] = Integer.parseInt(st.nextToken());
    		}
    		switch (str) {
    		case "left": M = 0; break;
    		case "up": M = 1; break;
    		case "right": M = 2; break;
    		case "down": M = 3; break;
    		}
    		for (int i = 0; i < M; i++) move();
            
            // Process
        	sort();
        	for (int i = 0; i < N; i++) {
        		for (int j = 0; j < N - 1; j++) {
        			if (arr[i][j] == arr[i][j+1]) {
        				arr[i][j] *= 2; arr[i][j+1] = 0;
        				j++;
        			}
        		}
        	}
        	sort();
    		switch (str) {
    		case "left": M = 0; break;
    		case "up": M = 3; break;
    		case "right": M = 2; break;
    		case "down": M = 1; break;
    		}
    		for (int i = 0; i < M; i++) move();
            
            // Output
            sb.append("#").append(test_case).append("\n");
        	for (int i = 0; i < N; i++) {
        		for (int j = 0; j < N; j++) {
        			sb.append(arr[i][j]).append(" ");
        		}
        		sb.append("\n");
        	}
            
        }
        
        System.out.println(sb);
        
    }
    
    public static void sort() {
    	int temp = -1;
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if (arr[i][j] == 0) {
					temp = -1;
    				for (int k = j + 1; k < N; k++) {
    					if (arr[i][k] > 0) {
    						temp = arr[i][k];
    						arr[i][k] = 0;
    						break;
    					}
    				}
    				if (temp > 0) arr[i][j] = temp;
    			}
			}
    	}
    }
    
    public static void move() {
    	temps = new int[N][N];
    	for (int i = 0; i < N; i++) temps[i] = Arrays.copyOf(arr[i], N);
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) arr[i][j] = temps[j][N-i-1];
    	}
    }

}
