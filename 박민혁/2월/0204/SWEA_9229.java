import java.util.*;
import java.io.*;
public class SWEA_9229 {
    public static void main(String[] args)throws IOException {
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());

        for(int tc = 1; tc<= t; tc++){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            int[] arr = new int [n];
            for(int i = 0; i< n; i++){
                arr[i]= Integer.parseInt(st.nextToken());
            }
            int mx= 0;
            for(int i = 0; i< n; i++){
                int cur = arr[i];
                if(cur >= m) continue;
                for(int j = i+1; j< n;j++){
                    cur+=arr[j];
                    if(cur <= m && mx< cur ) mx=cur;
                    cur-=arr[j];
                }
            }
            if(mx == 0) mx= -1;
            System.out.printf("#%d %d",tc, mx);
        }

    }
}
