import java.io.*;
import java.util.*;

public class Main {
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        
        // Input
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] arr = new int[N];
        int plus = 0;
        int minus = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
        	arr[i] = Integer.parseInt(st.nextToken());
        	if (arr[i] > 0) plus++;
        	else minus++;
        }
        Arrays.sort(arr);
        
        // Process
        int ans = 0;
        int max = Math.max(Math.abs(arr[0]), Math.abs(arr[N-1]));
        int idxP = N - 1;
        int idxM = 0;
        while (plus > 0) {
        	ans += arr[idxP] * 2;
        	idxP -= M;
        	plus -= M;
        }
        while (minus > 0) {
        	ans += Math.abs(arr[idxM]) * 2;
        	idxM += M;
        	minus -= M;
        }
        ans -= max;
        
        // Output
        System.out.println(ans);
        
    }

}
