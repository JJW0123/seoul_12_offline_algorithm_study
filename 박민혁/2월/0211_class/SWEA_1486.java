import java.io.*;
import java.util.*;
public class SWEA_1486 {
    static int N, B;
    static int[] hs;
    static int Best;
    static void dfs(int idx, int sum){
        if(sum>= B){
            Best= Math.min(Best, sum- B);
            return;
        }
        if(idx==N) return;
        dfs(idx+1, sum+ hs[idx]);
        dfs(idx+1, sum);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());
        for(int tc = 1; tc<=t ; tc++){
            Best= 10000000;
            st= new StringTokenizer(br.readLine());
            N= Integer.parseInt(st.nextToken());
            B= Integer.parseInt(st.nextToken());
            hs= new int[N];
            st= new StringTokenizer(br.readLine());
            for(int i = 0; i< N; i++){
                hs[i]= Integer.parseInt(st.nextToken());
            }
            dfs(0, 0);
            System.out.printf("#%d %d\n", tc, Best);
        }
    }
}
