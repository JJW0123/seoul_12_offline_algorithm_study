import java.io.*;
import java.util.*;

public class Main {
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        // Input
        int N = Integer.parseInt(br.readLine());
        int K = Integer.parseInt(br.readLine());
        
        // Process
        int[][] dp = new int[N+1][K+1];
        for (int i = 1; i <= N; i++) {
        	dp[i][0] = 1; // 아무 것도 안 고르는 경우
        	dp[i][1] = i; // 1개 고르는 경우
        	for (int j = 2; j <= K; j++) {
        		if (i > 1) {
        			dp[i][j] = (dp[i-2][j-1] + dp[i-1][j]) % 1000000003;
        		}
        	}
        }
        
        // Output
        System.out.println((dp[N-3][K-1] + dp[N-1][K]) % 1000000003);
        
    }

}
