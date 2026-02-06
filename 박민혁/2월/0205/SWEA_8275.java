import java.util.*;
import java.io.*;
public class SWEA_8275 {
    static class Cond {
        int l, r, s; // 0-indexed로 저장
        Cond(int l, int r, int s) { this.l = l; this.r = r; this.s = s; }
    }
    static int N, X, M;
    static Cond[] conds;

    static int[] cur, best;
    static int bestSum;

    static void dfs(int idx, int sumSoFar){
        
        return;
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());
        for(int tc= 1; tc <= t; tc++){
            st= new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            conds = new Cond[m];
            for(int i= 0; i < m;i++){
                int l = Integer.parseInt(st.nextToken()) - 1;
                int r = Integer.parseInt(st.nextToken()) - 1;
                int s = Integer.parseInt(st.nextToken());
                conds[i] = new Cond(l, r, s);
            }
        }
        cur = new int[N];
    }
}
